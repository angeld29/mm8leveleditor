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
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.gamenet.application.mm8leveleditor.control.DeltaOutdoorDataMapControl;
import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.D3Object;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.DeltaOutdoorDataMap;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.OutdoorDataMap;
import org.gamenet.application.mm8leveleditor.lod.LodEntry;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnsupportedFileFormatException;

import com.mmbreakfast.unlod.lod.LodFile;
import com.mmbreakfast.unlod.lod.NoSuchEntryException;

public class DdmHandler extends AbstractBaseHandler implements LodResourceHandler
{
    private LodResource sourceLodResource = null;
    private byte sourceDataCache[] = null;
    private DeltaOutdoorDataMap ddm = null;
    
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
            throw new InterruptedException("DdmHandler.getComponentFor() was interrupted.");

        if (DeltaOutdoorDataMap.checkDataIntegrity(GameVersion.MM6, sourceDataCache, 0, sourceDataCache.length))
        {
            ddm = new DeltaOutdoorDataMap(GameVersion.MM6, this.sourceDataCache);
        }
        else
        {
            int totalSpriteCount = 0;
            int totalD3ObjectCount = 0;
            int totalD3ObjectFacetsCount = 0;

            // Really ugly hack to read total number of data from odm file
            LodEntry ddmLodEntry = (LodEntry)lodResource;
            LodFile lodFile = ddmLodEntry.getLodFile();
            try
            {
                LodEntry odmLodEntry = lodFile.findLodEntryByFileName(lodResource.getName().substring(0, lodResource.getName().length() - 3) + "odm");
                
                byte odmSourceDataCache[];
                try
                {
                    odmSourceDataCache = odmLodEntry.getData();
                }
                catch (IOException anIOException)
                {
                    Throwable throwable = new Throwable("Unable to extract data from odm.", anIOException);
                    throwable.printStackTrace();
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(stringWriter, true);
                    throwable.printStackTrace(printWriter);
                    
                    Component component = new JTextArea(stringWriter.toString());

                    panel.add(component, BorderLayout.CENTER);
                    return panel;
                }

                OutdoorDataMap odm;
                if (OutdoorDataMap.checkDataIntegrity(GameVersion.MM7, odmSourceDataCache, 0, odmSourceDataCache.length))
                {
                    odm = new OutdoorDataMap(GameVersion.MM7, odmSourceDataCache);
                }
                else if (OutdoorDataMap.checkDataIntegrity(GameVersion.MM8, odmSourceDataCache, 0, odmSourceDataCache.length))
                {
                    odm = new OutdoorDataMap(GameVersion.MM8, odmSourceDataCache);
                }
                else
                {
                    throw new UnsupportedFileFormatException("Not an expected mm7 or mm8 odm format.");
                }
                
                totalSpriteCount = odm.getSpriteList().size();
                totalD3ObjectCount = odm.getD3ObjectList().size();
                totalD3ObjectFacetsCount = 0;
                Iterator d3ObjectIterator = odm.getD3ObjectList().iterator();
                while (d3ObjectIterator.hasNext())
                {
                    D3Object d3Object = (D3Object) d3ObjectIterator.next();
                    totalD3ObjectFacetsCount += d3Object.getFacetList().size();
                }
            }
            catch (NoSuchEntryException exception)
            {
                totalSpriteCount = -1;
                totalD3ObjectCount = -1;
                totalD3ObjectFacetsCount = -1;
            }
     
            if (DeltaOutdoorDataMap.checkDataIntegrity(GameVersion.MM7, sourceDataCache, 0, sourceDataCache.length, totalSpriteCount, totalD3ObjectFacetsCount))
            {
                ddm = new DeltaOutdoorDataMap(GameVersion.MM7, this.sourceDataCache, totalSpriteCount, totalD3ObjectFacetsCount);
            }
            else if (DeltaOutdoorDataMap.checkDataIntegrity(GameVersion.MM8, sourceDataCache, 0, sourceDataCache.length, totalSpriteCount, totalD3ObjectFacetsCount))
            {
                ddm = new DeltaOutdoorDataMap(GameVersion.MM8, this.sourceDataCache, totalSpriteCount, totalD3ObjectFacetsCount);
            }
            else
            {
                throw new UnsupportedFileFormatException("Not an expected mm6, mm7, or mm8 ddm format.");
            }
        }
            
            
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DdmHandler.getComponentFor() was interrupted.");

        panel.add(new DeltaOutdoorDataMapControl(ddm), BorderLayout.CENTER);

		return panel;
    }		
    
    public LodResource getUpdatedLodResource()
    {
        byte newData[] = this.ddm.updateData(this.sourceDataCache);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
