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


package org.gamenet.application.mm8leveleditor.handler;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.DObjListBinMM6;
import org.gamenet.application.mm8leveleditor.data.mm7.fileFormat.DObjListBinMM7;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnsupportedFileFormatException;

public class DObjListBinHandler extends AbstractBaseHandler
{
    private LodResourceSubHandler subHandler = null;

    public Component getComponentFor(LodResource lodResource, TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress(lodResource.getName(), 1f / (float)taskObserver.getRange());

        byte sourceDataCache[] = null;
        try
        {
            sourceDataCache = lodResource.getData();
        }
        catch (IOException anIOException)
        {
            Throwable throwable = new Throwable("Unable to extract data.", anIOException);
            throwable.printStackTrace();
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter, true);
            throwable.printStackTrace(printWriter);
            
            Component component = new JTextArea(stringWriter.toString());

            JPanel panel = new JPanel(new BorderLayout());
            JTextArea description = new JTextArea(lodResource.getTextDescription());
            panel.add(description, BorderLayout.PAGE_START);
     
            panel.add(component, BorderLayout.CENTER);
            return panel;
        }
        
        taskObserver.taskProgress(lodResource.getName(), 0.7f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DObjListBinHandler.getComponentFor() was interrupted.");

        if (DObjListBinMM6.checkDataIntegrity(sourceDataCache, 0, sourceDataCache.length))
        {
            this.subHandler = new DObjListBinMM6Handler();
            
            return this.subHandler.getComponentFor(lodResource, sourceDataCache, taskObserver);
        }
        else if (DObjListBinMM7.checkDataIntegrity(sourceDataCache, 0, sourceDataCache.length))
        {
            this.subHandler = new DObjListBinMM7Handler();
            
            return this.subHandler.getComponentFor(lodResource, sourceDataCache, taskObserver);
        }
        else
        {
            throw new UnsupportedFileFormatException("Not an expected mm6 or mm7 DObjList.bin format.");
        }
    }		
    
    public LodResource getUpdatedLodResource()
    {
        return subHandler.getUpdatedLodResource();
    }
}
