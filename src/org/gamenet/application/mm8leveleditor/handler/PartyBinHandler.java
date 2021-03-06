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

import org.gamenet.application.mm8leveleditor.control.PartyBinControl;
import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.PartyBin;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnsupportedFileFormatException;

public class PartyBinHandler extends AbstractBaseHandler
{
    private LodResource sourceLodResource = null;
    private byte sourceDataCache[] = null;
    private PartyBin partyBin = null;
    
    public Component getComponentFor(LodResource lodResource, TaskObserver taskObserver) throws InterruptedException
    {
        this.sourceLodResource = lodResource;
        
        taskObserver.taskProgress(lodResource.getName(), 1f / (float)taskObserver.getRange());

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea description = new JTextArea(lodResource.getTextDescription());
        panel.add(description, BorderLayout.PAGE_START);
 
        taskObserver.taskProgress(lodResource.getName(), 2f / (float)taskObserver.getRange());

        this.sourceDataCache = null;
        try
        {
            this.sourceDataCache = lodResource.getData();
        }
        catch (IOException anIOException)
        {
            Throwable throwable = new Throwable("Unable to extract data.", anIOException);
            throwable.printStackTrace();
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter, true);
            throwable.printStackTrace(printWriter);
            
            Component component = new JTextArea(stringWriter.toString());

            panel.add(component, BorderLayout.CENTER);
            return panel;
        }
        
        taskObserver.taskProgress(lodResource.getName(), 0.7f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("PartyBinHandler.getComponentFor() was interrupted.");

        int offset = 0;
        if (false == PartyBin.checkDataIntegrity(GameVersion.MM6, this.sourceDataCache, offset, this.sourceDataCache.length))
        {
            throw new UnsupportedFileFormatException("Not an expected mm6 Party.bin format.");
        }
        partyBin = new PartyBin(GameVersion.MM6);
        partyBin.initialize(this.sourceDataCache, offset);
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("PartyBinHandler.getComponentFor() was interrupted.");

        panel.add(new PartyBinControl(partyBin), BorderLayout.CENTER);
		
		return panel;
    }		
    
    public LodResource getUpdatedLodResource()
    {
        byte[] newData = partyBin.updateData(this.sourceDataCache);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
