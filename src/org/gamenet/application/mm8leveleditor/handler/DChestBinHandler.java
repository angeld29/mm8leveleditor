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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.gamenet.application.mm8leveleditor.control.DChestBinControl;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.DChestBin;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.ComponentArrayPanel;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnsupportedFileFormatException;

public class DChestBinHandler extends AbstractBaseHandler
{
    private LodResource sourceLodResource = null;
    private byte sourceDataCache[] = null;
    private List dChestBinList = null;
    
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
            throw new InterruptedException("DChestBinHandler.getComponentFor() was interrupted.");

        dChestBinList = new ArrayList();
        int offset = 0;
        if (false == DChestBin.checkDataIntegrity(this.sourceDataCache, offset, this.sourceDataCache.length))
        {
            throw new UnsupportedFileFormatException("Not an expected mm6 DMonList.bin format.");
        }
        
        offset = DChestBin.populateObjects(this.sourceDataCache, offset, dChestBinList);
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DChestBinHandler.getComponentFor() was interrupted.");

		JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));

		Component component = componentPanel;
		
		// DChestBin
        JPanel unknowns2Panel = new JPanel();
        unknowns2Panel.setLayout(new BoxLayout(unknowns2Panel, BoxLayout.Y_AXIS));

        final JLabel dChestBinCountLabel = new JLabel("# of DChestBins: " + String.valueOf(dChestBinList.size()));
        unknowns2Panel.add(makeNonStretchedPanelFor(dChestBinCountLabel));

        final ComparativeTableControl dChestBinComparativeByteDataTableControl = new ComparativeTableControl(DChestBin.getOffsetList(), DChestBin.getComparativeDataSource(dChestBinList));

        List dChestBinControlList = new ArrayList(dChestBinList.size());
        for (int dChestBinIndex = 0; dChestBinIndex < dChestBinList.size(); ++dChestBinIndex)
        {
            DChestBin dChestBin = (DChestBin)dChestBinList.get(dChestBinIndex);
            dChestBinControlList.add(new DChestBinControl(dChestBin));
        }
 
        unknowns2Panel.add(makeNonStretchedPanelFor(new JLabel("DChestBin Unknowns: ")));
        unknowns2Panel.add(dChestBinComparativeByteDataTableControl);

        unknowns2Panel.add(makeNonStretchedPanelFor(new ComponentArrayPanel(dChestBinControlList, new ComponentArrayPanel.ComponentDataSource()
                {
                    public Component createComponent(int componentIndex)
                    {
                        DChestBin newDChestBin = createNewDChestBin();
                        return new DChestBinControl(newDChestBin);
                    }
                    
                    public void fireComponentAdded(int componentIndex, Component component)
                    {
                        DChestBinControl unknownControl = (DChestBinControl)component;
                        dChestBinList.add(componentIndex, unknownControl.getDChestBin());
                        dChestBinComparativeByteDataTableControl.getTableModel().fireTableRowsInserted(componentIndex, componentIndex);
                        dChestBinCountLabel.setText("# of DChestBins: " + String.valueOf(dChestBinList.size()));
                    }
                    public void fireComponentDeleted(int componentIndex, Component component)
                    {
                        DChestBinControl unknownControl = (DChestBinControl)component;
                        dChestBinList.remove(componentIndex);
                        dChestBinComparativeByteDataTableControl.getTableModel().fireTableRowsDeleted(componentIndex, componentIndex);
                        dChestBinCountLabel.setText("# of DChestBins: " + String.valueOf(dChestBinList.size()));
                    }
                    public void fireComponentMovedUp(int componentIndex, Component component)
                    {
                        DChestBin unknown = (DChestBin)dChestBinList.remove(componentIndex);
                        dChestBinList.add(componentIndex - 1, unknown);
                        
                        dChestBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                    public void fireComponentMovedDown(int componentIndex, Component component)
                    {
                        DChestBin unknown = (DChestBin)dChestBinList.remove(componentIndex);
                        dChestBinList.add(componentIndex + 1, unknown);
                        
                        dChestBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                })));
        
        componentPanel.add(makeNonStretchedPanelFor(unknowns2Panel));
		
        panel.add(component, BorderLayout.CENTER);
		
		return panel;
    }		
    
    private DChestBin createNewDChestBin()
    {
        String soundName = "";

        DChestBin newDChestBin = new DChestBin(soundName);
        
        return newDChestBin;
    }

    public LodResource getUpdatedLodResource()
    {
        byte[] newData = new byte[dChestBinList.size() * DChestBin.getRecordSize()];
        int offset = DChestBin.updateData(newData, 0, dChestBinList);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
