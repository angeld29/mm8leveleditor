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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.gamenet.application.mm8leveleditor.control.DeltaIndoorDataMapControl;
import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.application.mm8leveleditor.data.mm6.indoor.DeltaIndoorDataMap;
import org.gamenet.application.mm8leveleditor.data.mm6.indoor.IndoorDataMap;
import org.gamenet.application.mm8leveleditor.lod.LodEntry;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.application.mm8leveleditor.mm6.MM6NewLodFile;
import org.gamenet.application.mm8leveleditor.mm7.MM7NewLodFile;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnsupportedFileFormatException;

import com.mmbreakfast.unlod.lod.InvalidLodFileException;
import com.mmbreakfast.unlod.lod.LodFile;
import com.mmbreakfast.unlod.lod.NoSuchEntryException;
import com.mmbreakfast.unlod.lod.RandomAccessFileInputStream;

public class DlvHandler extends AbstractBaseHandler implements LodResourceHandler
{
    private LodResource sourceLodResource = null;
    private byte sourceDataCache[] = null;
    private DeltaIndoorDataMap dlv = null;
    
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
            throw new InterruptedException("DlvHandler.getComponentFor() was interrupted.");

        int totalSpriteCount = 0;
        int variableDoorDataSize = 0;
        int totalFacetsCount = 0;

        // Really ugly hack to read total number of data from odm file
        LodEntry ddmLodEntry = (LodEntry)lodResource;
        LodFile lodFile = ddmLodEntry.getLodFile();
        
        Exception exceptionThrown = null;
        try
        {
            LodEntry blvLodEntry;
            try
            {
                blvLodEntry = lodFile.findLodEntryByFileName(lodResource.getName().substring(0, lodResource.getName().length() - 3) + "blv");
            }
            catch (NoSuchEntryException exception)
            {
                File file = new File(lodFile.getFile().getParentFile().getParentFile(), "data/games.lod");
                RandomAccessFileInputStream lodInputStream = new RandomAccessFileInputStream(new RandomAccessFile(file, "r"));

                try
                {
                    lodFile = new MM6NewLodFile(file, lodInputStream);
                }
                catch (InvalidLodFileException invalidLodFileException)
                {
                    lodFile = new MM7NewLodFile(file, lodInputStream);
                }
                blvLodEntry = lodFile.findLodEntryByFileName(lodResource.getName().substring(0, lodResource.getName().length() - 3) + "blv");
            }
            
            byte blvSourceDataCache[];
            try
            {
                blvSourceDataCache = blvLodEntry.getData();
            }
            catch (IOException anIOException)
            {
                Throwable throwable = new Throwable("Unable to extract data from blv.", anIOException);
                throwable.printStackTrace();
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter, true);
                throwable.printStackTrace(printWriter);
                
                Component component = new JTextArea(stringWriter.toString());

                panel.add(component, BorderLayout.CENTER);
                return panel;
            }

            IndoorDataMap blv;
            if (IndoorDataMap.checkDataIntegrity(GameVersion.MM6, blvSourceDataCache, 0, blvSourceDataCache.length))
            {
                blv = new IndoorDataMap(GameVersion.MM6);
                blv.initialize(blvSourceDataCache, 0);
                variableDoorDataSize = blv.getVariableDoorDataSize();
                if (DeltaIndoorDataMap.checkDataIntegrity(GameVersion.MM6, sourceDataCache, 0, sourceDataCache.length, variableDoorDataSize))
                {
                    dlv = new DeltaIndoorDataMap(GameVersion.MM6, this.sourceDataCache, variableDoorDataSize);
                }
                else
                {
                    throw new UnsupportedFileFormatException("Not an expected mm6 dlv format.");
                }

            }
            else if (IndoorDataMap.checkDataIntegrity(GameVersion.MM7, blvSourceDataCache, 0, blvSourceDataCache.length))
            {
                blv = new IndoorDataMap(GameVersion.MM7);
                blv.initialize(blvSourceDataCache, 0);
                variableDoorDataSize = blv.getVariableDoorDataSize();
                totalSpriteCount = blv.getSpriteList().size();
                totalFacetsCount = blv.getFaceList().size();
                if (DeltaIndoorDataMap.checkDataIntegrity(GameVersion.MM7, sourceDataCache, 0, sourceDataCache.length, totalSpriteCount, totalFacetsCount, variableDoorDataSize))
                {
                    dlv = new DeltaIndoorDataMap(GameVersion.MM7, this.sourceDataCache, totalSpriteCount, totalFacetsCount, variableDoorDataSize);
                }
                else
                {
                    throw new UnsupportedFileFormatException("Not an expected mm7 dlv format.");
                }
            }
            else if (IndoorDataMap.checkDataIntegrity(GameVersion.MM8, blvSourceDataCache, 0, blvSourceDataCache.length))
            {
                blv = new IndoorDataMap(GameVersion.MM8);
                blv.initialize(blvSourceDataCache, 0);
                variableDoorDataSize = blv.getVariableDoorDataSize();
                totalSpriteCount = blv.getSpriteList().size();
                totalFacetsCount = blv.getFaceList().size();
               if (DeltaIndoorDataMap.checkDataIntegrity(GameVersion.MM8, sourceDataCache, 0, sourceDataCache.length, totalSpriteCount, totalFacetsCount, variableDoorDataSize))
                {
                    dlv = new DeltaIndoorDataMap(GameVersion.MM8, this.sourceDataCache, totalSpriteCount, totalFacetsCount, variableDoorDataSize);
                }
                else
                {
                    throw new UnsupportedFileFormatException("Not an expected mm8 dlv format.");
                }
            }
            else
            {
                throw new UnsupportedFileFormatException("Not an expected mm7 or mm8 blv format.");
            }
        }
        catch (NoSuchEntryException exception)
        {
            exceptionThrown = exception;
        }
        catch (FileNotFoundException exception)
        {
            exceptionThrown = exception;
        }
        catch (IOException exception)
        {
            exceptionThrown = exception;
        }
        catch (InvalidLodFileException exception)
        {
            exceptionThrown = exception;
        }
        
        if (null != exceptionThrown)
        {
            Throwable throwable = new Throwable("Unable to extract data.", exceptionThrown);
            throwable.printStackTrace();
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter, true);
            throwable.printStackTrace(printWriter);
            
            Component component = new JTextArea(stringWriter.toString());

            panel.add(component, BorderLayout.CENTER);
            return panel;
        }
 
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DlvHandler.getComponentFor() was interrupted.");

        panel.add(new DeltaIndoorDataMapControl(dlv), BorderLayout.CENTER);
		
		return panel;
    }		
    
    
    public LodResource getUpdatedLodResource()
    {
        byte newData[] = this.dlv.updateData(this.sourceDataCache);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
