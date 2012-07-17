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
package org.gamenet.application.mm8leveleditor.lod;

import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.gamenet.application.mm8leveleditor.handler.DataHandler;
import org.gamenet.application.mm8leveleditor.handler.ErrorHandler;
import org.gamenet.application.mm8leveleditor.handler.LodResourceHandler;
import org.gamenet.util.MonitoredTaskThread;
import org.gamenet.util.UnimplementedMethodException;
import org.gamenet.util.UnsupportedFileFormatException;

public class LodResourceController
{
    private ApplicationController applicationController = null;
    private LodResource lodResource = null;
    private LodResourceHandler handler = null;

    private static LodResourceController myLodResourceController = null;
    private static LodResource myLodResource = null;
    
    public static LodResourceController getLodResourceControllerFor(ApplicationController applicationController, LodResource lodResource)
    {
        if (null == lodResource)  return null;
        
        if (lodResource != myLodResource)
        {
            myLodResource = lodResource;
            myLodResourceController = new LodResourceController(applicationController, lodResource);
        }

        return myLodResourceController;
    }
    
	public LodResourceController(ApplicationController applicationController, LodResource lodResource)
    {
	    super();
	    
	    this.applicationController = applicationController;
	    this.lodResource = lodResource;
        this.handler = applicationController.getLodResourceManager().getHandlerFor(lodResource);
    }
	
	public void setHandler(LodResourceHandler handler)
    {
        this.handler = handler;
    }
    
	public void setDefaultHandler()
    {
        this.handler = applicationController.getLodResourceManager().getHandlerFor(lodResource);
    }
    
	public void updateLodResource()
    {
	    LodResource updatedResource;
        try
        {
            updatedResource = this.handler.getUpdatedLodResource();
        }
        catch (UnimplementedMethodException exception)
        {
            // nothing to update -- use old lod resource
            return;
        }
        
        if (updatedResource instanceof TemplateLodResource)
	    {
	        try
            {
                this.lodResource = new RawDataTemplateLodResource((TemplateLodResource)updatedResource);
            }
            catch (IOException exception)
            {
                exception.printStackTrace();
                // error creating resource -- use old lod resource
                this.lodResource = updatedResource;
            }
	    }
	    else
	    {
	        this.lodResource = updatedResource;
	    }
    }
    
	public void changeToDefaultHandler()
    {
	    updateLodResource();
        setDefaultHandler();
        displayLodResource();
    }
    
	public void changeToHandler(LodResourceHandler handler)
    {
	    updateLodResource();
        setHandler(handler);
        displayLodResource();
    }
    
    private MonitoredTaskThread displayLodEntryThread = null;
    
	public void displayLodResource()
    {
	    final ProgressDisplayer progressDisplayer = applicationController.getProgressDisplayer(); 
        Runnable displayLodEntryRunnable = new Runnable()
        {
            public void run()
            {
        	    displayLodEntryThread.taskProgress(lodResource.getName(), 0);
        	    Component displayComponent = null;
                try
                {
                    displayComponent = handler.getComponentFor(lodResource, displayLodEntryThread);
                }
                catch (InterruptedException exception)
                {
                    displayComponent = new ErrorHandler("Error: User cancelled operation.").getComponentFor(lodResource, displayLodEntryThread);
                }
                catch (UnsupportedFileFormatException unsupportedFileFormatException)
                {
                    if (false == handler instanceof DataHandler)
                    {
                        progressDisplayer.displayErrorPanel("Error while running task: " + unsupportedFileFormatException.getMessage());
                        progressDisplayer.displayErrorPanel("Retrying with Data Display Mode.");
                        try
                        {
                            displayComponent = new DataHandler().getComponentFor(lodResource, displayLodEntryThread);
                        }
                        catch (InterruptedException exception)
                        {
                            displayComponent = new ErrorHandler("Error: User cancelled operation.").getComponentFor(lodResource, displayLodEntryThread);
                        }
                    }
                    else
                    {
                        throw unsupportedFileFormatException;
                    }
                };
                applicationController.setDisplayedComponent(displayComponent);
        	    displayLodEntryThread.taskProgress(lodResource.getName(), 1);
            }
        };

        displayLodEntryThread = new MonitoredTaskThread(applicationController.getProgressDisplayer(), "Please wait.\nRendering data...", "startup", displayLodEntryRunnable);
	    displayLodEntryThread.start();
    }

	public void importData()
	{	
	    File newLodFileToCreate = applicationController.getFileToCreate();
	    if (null == newLodFileToCreate)  return;
	    
	    ProgressDisplayer progressDisplayer = applicationController.getProgressDisplayer();
	    
	    LodResource newLodResource = null;
        try
        {
            newLodResource = handler.getUpdatedLodResource();
        }
        catch (RuntimeException exception)
        {
            exception.printStackTrace();
	        progressDisplayer.displayErrorPanel("Unable to import data: " + exception.getMessage());
	        return;
        }
        
        ImportManager importManager = new ImportManager(progressDisplayer);
	    importManager.importAndMonitorResource(applicationController.getCurrentLodFile(), newLodFileToCreate, newLodResource);
	}

	public void updateByAppendingData()
	{	
	    int result = applicationController.showConfirmDialog("This operation will modify the current file, potentially destroying it.  Are you sure you want to continue?", "DANGER -- Updating LOD File");
	    if (JOptionPane.YES_OPTION != result)
	    {
	        return;
	    }
	    
	    ProgressDisplayer progressDisplayer = applicationController.getProgressDisplayer();
	    
	    LodResource newLodResource = null;
        try
        {
            newLodResource = handler.getUpdatedLodResource();
        }
        catch (RuntimeException exception)
        {
            exception.printStackTrace();
	        progressDisplayer.displayErrorPanel("Unable to update data: " + exception.getMessage());
	        return;
        }
        
        ImportManager importManager = new ImportManager(progressDisplayer);
	    importManager.updateAndMonitorResource(applicationController.getCurrentLodFile(), newLodResource);
	}
}

