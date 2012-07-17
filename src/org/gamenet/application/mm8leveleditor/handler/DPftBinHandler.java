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

import org.gamenet.application.mm8leveleditor.control.DPftBinControl;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.DPftBin;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.ComponentArrayPanel;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnsupportedFileFormatException;

public class DPftBinHandler extends AbstractBaseHandler
{
    private LodResource sourceLodResource = null;
    private byte sourceDataCache[] = null;
    private List dPftBinList = null;
    
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
            throw new InterruptedException("DPftBinHandler.getComponentFor() was interrupted.");

        
        int offset = 0;
        if (false == DPftBin.checkDataIntegrity(this.sourceDataCache, offset, this.sourceDataCache.length))
        {
            throw new UnsupportedFileFormatException("Not an expected mm6 DPft.bin format.");
        }
        dPftBinList = new ArrayList();
        offset = DPftBin.populateObjects(this.sourceDataCache, offset, dPftBinList);
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DPftBinHandler.getComponentFor() was interrupted.");

		JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));

		Component component = componentPanel;
		
		// DPftBin
        JPanel unknowns2Panel = new JPanel();
        unknowns2Panel.setLayout(new BoxLayout(unknowns2Panel, BoxLayout.Y_AXIS));

        final JLabel dPftBinCountLabel = new JLabel("# of DPftBins: " + String.valueOf(dPftBinList.size()));
        unknowns2Panel.add(makeNonStretchedPanelFor(dPftBinCountLabel));

        final ComparativeTableControl dPftBinComparativeByteDataTableControl = new ComparativeTableControl(DPftBin.getOffsetList(), DPftBin.getComparativeDataSource(dPftBinList));

        List dPftBinControlList = new ArrayList(dPftBinList.size());
        for (int dPftBinIndex = 0; dPftBinIndex < dPftBinList.size(); ++dPftBinIndex)
        {
            DPftBin dPftBin = (DPftBin)dPftBinList.get(dPftBinIndex);
            dPftBinControlList.add(new DPftBinControl(dPftBin));
        }
 
        unknowns2Panel.add(makeNonStretchedPanelFor(new JLabel("DPftBin Unknowns: ")));
        unknowns2Panel.add(dPftBinComparativeByteDataTableControl);

        unknowns2Panel.add(makeNonStretchedPanelFor(new ComponentArrayPanel(dPftBinControlList, new ComponentArrayPanel.ComponentDataSource()
                {
                    public Component createComponent(int componentIndex)
                    {
                        DPftBin newDPftBin = createNewDPftBin();
                        return new DPftBinControl(newDPftBin);
                    }
                    
                    public void fireComponentAdded(int componentIndex, Component component)
                    {
                        DPftBinControl unknownControl = (DPftBinControl)component;
                        dPftBinList.add(componentIndex, unknownControl.getDPftBin());
                        dPftBinComparativeByteDataTableControl.getTableModel().fireTableRowsInserted(componentIndex, componentIndex);
                        dPftBinCountLabel.setText("# of DPftBins: " + String.valueOf(dPftBinList.size()));
                    }
                    public void fireComponentDeleted(int componentIndex, Component component)
                    {
                        DPftBinControl unknownControl = (DPftBinControl)component;
                        dPftBinList.remove(componentIndex);
                        dPftBinComparativeByteDataTableControl.getTableModel().fireTableRowsDeleted(componentIndex, componentIndex);
                        dPftBinCountLabel.setText("# of DPftBins: " + String.valueOf(dPftBinList.size()));
                    }
                    public void fireComponentMovedUp(int componentIndex, Component component)
                    {
                        DPftBin unknown = (DPftBin)dPftBinList.remove(componentIndex);
                        dPftBinList.add(componentIndex - 1, unknown);
                        
                        dPftBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                    public void fireComponentMovedDown(int componentIndex, Component component)
                    {
                        DPftBin unknown = (DPftBin)dPftBinList.remove(componentIndex);
                        dPftBinList.add(componentIndex + 1, unknown);
                        
                        dPftBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                })));
        
        componentPanel.add(makeNonStretchedPanelFor(unknowns2Panel));
		
        panel.add(component, BorderLayout.CENTER);
		
		return panel;
    }		
    
    private DPftBin createNewDPftBin()
    {
        String soundName = "";

        DPftBin newDPftBin = new DPftBin(soundName);
        
        return newDPftBin;
    }

    public LodResource getUpdatedLodResource()
    {
        byte[] newData = new byte[dPftBinList.size() * DPftBin.getRecordSize()];
        int offset = DPftBin.updateData(newData, 0, dPftBinList);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
