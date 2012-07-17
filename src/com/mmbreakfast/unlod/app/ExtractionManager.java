/*
 *  com/mmbreakfast/unlod/app/ExtractionManager.java
 *
 *  Copyright (C) 2000 Sil Veritas (sil_the_follower_of_dark@hotmail.com)
 */

/*  This file is part of Unlod.
 *
 *  Unlod is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Unlod is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Unlod; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/*  Unlod
 *
 *  Copyright (C) 2000 Sil Veritas. All Rights Reserved. This work is
 *  distributed under the W3C(R) Software License [1] in the hope that it
 *  will be useful, but WITHOUT ANY WARRANTY; without even the implied
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 */

package com.mmbreakfast.unlod.app;

import java.awt.*;
import java.io.*;

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

import org.gamenet.application.mm8leveleditor.lod.LodEntry;

import java.util.*;
import com.mmbreakfast.unlod.lod.*;


public class ExtractionManager {
    protected static final int PROGRESS_MIN = 0;
	protected static final int PROGRESS_MAX = 250;
	protected static final int DECIDE = 1;
	protected static final int LIMIT  = 1;

	protected Extractor extractor;
	protected Component parent;
	protected volatile ProgressMonitor monitor;

   protected volatile boolean timerAlive = false;
   protected static final int DELAY = 100;

   protected Thread timer;

   protected Object lock = new Object();

   protected ExtractorThread job;

	public ExtractionManager(Component parent) {
		this.parent = parent;
	}

    public void setFileExtractor(Extractor lodfileextractor)
    {
        extractor = lodfileextractor;
    }

   public void extractLodEntries(LodEntry[] entries, File dir, boolean convertData) {
      this.stopExtraction();

      synchronized (lock) {
         if (entries.length == 0) {
            return;
         }

         timerAlive = true;

   		monitor = new ProgressMonitor(parent, "Please wait...", "Extracting selection", PROGRESS_MIN, PROGRESS_MAX);
   		monitor.setMillisToDecideToPopup(DECIDE);
   		monitor.setMillisToPopup(LIMIT);

   		timer = new Thread(new ExtractionCancellationMonitor());
   		timer.start();

   		job = new ExtractorThread(Arrays.asList(entries), dir, convertData);

   		new Thread(job).start();
		}
   }

	public void extract(LodFile lodFile, File dir, boolean convertData) {
        this.stopExtraction();

        synchronized (lock)
        {
            timerAlive = true;

            monitor = new ProgressMonitor(parent, "Please wait...", "Extracting LOD File", PROGRESS_MIN, PROGRESS_MAX);
            monitor.setProgress(0);
            monitor.setMillisToDecideToPopup(DECIDE);
            monitor.setMillisToPopup(LIMIT);

            timer = new Thread(new ExtractionCancellationMonitor());
            timer.start();

            job = new ExtractorThread(lodFile.getLodEntries().values(), dir, convertData);

            new Thread(job).start();
       }
	}

	public void startExtraction(LodEntry entry) {
		this.stopExtraction();

      synchronized (lock) {
   		timerAlive = true;

   		monitor = new ProgressMonitor(parent, "Please wait...", "Extracting " + entry.getName(), PROGRESS_MIN, PROGRESS_MAX);
        monitor.setProgress(0);
   		monitor.setMillisToDecideToPopup(DECIDE);
   		monitor.setMillisToPopup(LIMIT);

   		timer = new Thread(new ExtractionCancellationMonitor());
   		timer.start();
		}
	}

    private int lastProgress = -1;
	protected void setProgress(int progress) {
        if (progress != lastProgress)
        {
            monitor.setProgress(progress);
            lastProgress = progress;
        }
	}

    private String lastNote = null;
	protected void setNote(String name) {
       if (false == name.equals(lastNote))
       {
           EventQueue.invokeLater(new EntryUpdater(name));
           lastNote = name;
       }
	}

	protected void updateEntry(String name) {
	   monitor.setNote("Extracting " + name);
	}

	protected void updateProgress(int progress) {
		EventQueue.invokeLater(new ProgressMonitorUpdater(progress));
	}

	public void stopExtraction() {
	   this.cancelExtraction();
		timerAlive = false;
		if (timer != null) {
		   timer.interrupt();
		}
		if (monitor != null) {
		   //this.updateProgress(PROGRESS_MAX);
		   monitor.close();
		}
	}

	public void cancelExtraction() {
        if (null == extractor)  return;
	   extractor.cancelExtraction();
	   //this.stopExtraction();
	}


	protected class ProgressMonitorUpdater implements Runnable {
	   private int progress;
       
	   public ProgressMonitorUpdater(int progress) {
         this.progress = progress;
	   }

	   public void run() {
			ExtractionManager.this.setProgress(progress);

		}
	}

	protected class ExtractionCancellationMonitor implements Runnable {
		public void run() {
			synchronized (lock) {
   			while (timerAlive && !monitor.isCanceled())
            {
                try
                {
   					Thread.sleep(DELAY);
   				} catch (InterruptedException e) {}
   			}

   			if (monitor.isCanceled()) {
   				ExtractionManager.this.cancelExtraction();
   			}

   			timerAlive = false;

			}
		}
	}

	protected class ProgressExtractionObserver implements ExtractionObserver {
        public void directoryCreated() {}
        private int range = PROGRESS_MAX - PROGRESS_MIN;
        private int lastProgressUpdate = -1;
        private String lastIdentifier = null;
        
        public int getRange()
        {
            return range;
        }
        
        public void extractionProgress(String identifier, float percentageDone)
        {
            int trackableProgress = Math.round(percentageDone * range);
            if ((trackableProgress > lastProgressUpdate) || (identifier != lastIdentifier))
            {
                setNote(identifier);
                updateProgress(PROGRESS_MIN + trackableProgress);
                lastProgressUpdate = trackableProgress;
                lastIdentifier = identifier;
            }
        }

        public void extractionStarted(String identifier) {
           setNote(identifier);
           updateEntry(identifier);
        }

        public void extractionFinished(String identifier) {
            timerAlive = false;
            ExtractionManager.this.setNote(identifier);
            updateProgress(PROGRESS_MAX);
        }

        public void extractionCancelled(String identifier) {
           if (job != null) {
              job.cancel();
           }
           ExtractionManager.this.updateProgress(PROGRESS_MAX);
        }

        public void exceptionCaught(Exception e) {
           e.printStackTrace();              /// DEBUG
        }
        
        public void directoryCreated(File dir) {}
    }

    protected class EntryUpdater implements Runnable {
       protected String name;

       public EntryUpdater(String name) {
          this.name = name;
       }

       public void run() {
          ExtractionManager.this.updateEntry(name);
       }
    }

	protected class ExtractorThread implements Runnable
    {
        protected Collection lodEntries;

        protected File dir;

        protected ExtractionJob ejob;

        protected boolean convertData = true;

        public ExtractorThread(Collection lodEntries, File dir,
                boolean convertData)
        {
            this.lodEntries = lodEntries;
            this.dir = dir;
            this.convertData = convertData;
        }

        public void cancel()
        {
            if (ejob != null)
            {
                ejob.cancelJob();
            }
        }

        public void run()
        {
            try
            {
                Thread.currentThread().setPriority(
                        Thread.currentThread().getPriority() - 1);
                ArrayList list = new ArrayList(lodEntries);
                Collections.sort(list);
                ejob = new ExtractionJob(list.iterator(), list.size());
                ejob.extractToFiles(dir, new ProgressExtractionObserver(),
                        true, convertData);
            }
            catch (final Throwable exception)
            {
                exception.printStackTrace();
                EventQueue.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        JOptionPane.showMessageDialog(parent, exception.getMessage(), "Extraction Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
        }
    }
}