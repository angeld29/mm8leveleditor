/*
 * Copyright (c) 2005 (Mike) Maurice Kienenberger (mkienenb@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.gamenet.util;

import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ProgressMonitor;

import org.gamenet.application.mm8leveleditor.lod.ProgressDisplayer;

public class MonitoredTaskThread implements TaskObserver
{
    private static final int PROGRESS_MIN = -1;
    private static final int PROGRESS_MAX = 250;
    
    private volatile ProgressMonitor monitor = null;

    // TODO: probably should reduce this dependency
    private ProgressDisplayer progressDisplayer = null;
    private String title = null;
    private Object message = null;
    private Runnable monitoredTask = null;
    private Thread monitoredTaskThread = null;
    private Timer checkForCancelTimer = null;
    
    public MonitoredTaskThread(ProgressDisplayer progressDisplayer, Object message, String title, Runnable monitoredTask)
    {
        super();
        
        this.monitoredTask = monitoredTask;
        this.title = title;
        this.message = message;
        this.progressDisplayer = progressDisplayer;
    }

    /**
     * @param oldLodFile original lod file
     * @param newLodFileToCreate new lod file based on old lod file + files to import.
     * @param filesToImportList list of File objects to update or add to new lod file.
     */
    public void start()
    {
        monitor = progressDisplayer.getProgressMonitor(message, title, PROGRESS_MIN, PROGRESS_MAX);
        monitor.setProgress(PROGRESS_MIN);  // force progress panel to pop up sooner

        Runnable wrappedMonitoredTask = new Runnable()
        {
            public void run()
            {
                Throwable caughtThrowable = null;
                try
                {
                    monitoredTask.run();
                }
                catch(Throwable throwable)
                {
                    throwable.printStackTrace();
                    caughtThrowable = throwable;
                }

                monitoredTaskFinished(caughtThrowable);
            }
        };
        
        monitoredTaskThread = new Thread(wrappedMonitoredTask);
        monitoredTaskThread.setPriority(monitoredTaskThread.getPriority() - 1);
        monitoredTaskThread.start();

        TimerTask checkForCancelTimerTask = new TimerTask()
        {
            public void run()
            {
                if (monitor.isCanceled())  monitoredTaskThread.interrupt();
            }
        };
        
        checkForCancelTimer = new Timer(true);
        checkForCancelTimer.schedule(checkForCancelTimerTask, 200, 200);
    }

    protected void monitoredTaskFinished(Throwable throwable)
    {
        if (null != monitor)
        {
    		EventQueue.invokeLater(new Runnable()
                    {
                        public void run()
                        {
                            monitor.close();
                        }
                    });
        }

        if (null != checkForCancelTimer)  checkForCancelTimer.cancel();
        
        if (null != throwable)
        {
            if (null != throwable.getMessage())
            {
                progressDisplayer.displayErrorPanel("Error while running task: " + throwable.getLocalizedMessage());
            }
            else if (throwable instanceof OutOfMemoryError)
            {
                progressDisplayer.displayErrorPanel("Error while running task: " + "\n" + "Not enough memory to load data." + "\n" + "Try starting program with more memory:" + "\n" + "for example, to specify 256Mb of memory, use 'java -Xms256M -Xmx256M ...'");
            }
            else
            {
                progressDisplayer.displayErrorPanel("Error while running task: " + throwable);
            }
        }
    }
    
    private int lastProgressUpdate = PROGRESS_MIN - 1;

    public int getRange()
    {
        return PROGRESS_MAX - (PROGRESS_MIN + 1);
    }

    private void sendProgress(String identifier, int trackableProgress)
    {
        final String finalizedNote = identifier;
        final int finalizedProgress = trackableProgress;
        
		EventQueue.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        monitor.setNote(finalizedNote);
                        monitor.setProgress(finalizedProgress);
                    }
                });
    }
    
    public void taskProgress(String identifier, float percentageDone)
    {
        int trackableProgress = Math.round(percentageDone * getRange());
        if (trackableProgress > lastProgressUpdate)
        {
            sendProgress(identifier, trackableProgress);
            lastProgressUpdate = trackableProgress;
        }
    }
}
