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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.gamenet.application.mm8leveleditor.control.DMonListBinControl;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.DMonListBinMM6;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.ComponentArrayPanel;
import org.gamenet.util.TaskObserver;

public class DMonListBinMM6Handler extends AbstractBaseSubHandler
{
    private LodResource sourceLodResource = null;
    private byte sourceDataCache[] = null;
    private List dMonListBinList = null;
    
    public Component getComponentFor(LodResource lodResource, byte sourceDataCache[], TaskObserver taskObserver) throws InterruptedException
    {
        this.sourceLodResource = lodResource;
        this.sourceDataCache = sourceDataCache;
        
        int offset = 0;
        dMonListBinList = new ArrayList();
        offset = DMonListBinMM6.populateObjects(this.sourceDataCache, offset, dMonListBinList);
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DMonListBinHandler.getComponentFor() was interrupted.");

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea description = new JTextArea(lodResource.getTextDescription());
        panel.add(description, BorderLayout.PAGE_START);

        JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));

		Component component = componentPanel;
		
		// DMonListBin
        JPanel unknowns2Panel = new JPanel();
        unknowns2Panel.setLayout(new BoxLayout(unknowns2Panel, BoxLayout.Y_AXIS));

        final JLabel dMonListBinCountLabel = new JLabel("# of DMonListBins: " + String.valueOf(dMonListBinList.size()));
        unknowns2Panel.add(makeNonStretchedPanelFor(dMonListBinCountLabel));

        final ComparativeTableControl dMonListBinComparativeByteDataTableControl = new ComparativeTableControl(DMonListBinMM6.getOffsetList(), DMonListBinMM6.getComparativeDataSource(dMonListBinList));

        List dMonListBinControlList = new ArrayList(dMonListBinList.size());
        for (int dMonListBinIndex = 0; dMonListBinIndex < dMonListBinList.size(); ++dMonListBinIndex)
        {
            DMonListBinMM6 dMonListBin = (DMonListBinMM6)dMonListBinList.get(dMonListBinIndex);
            dMonListBinControlList.add(new DMonListBinControl(dMonListBin));
        }
 
        unknowns2Panel.add(makeNonStretchedPanelFor(new JLabel("DMonListBin Unknowns: ")));
        unknowns2Panel.add(dMonListBinComparativeByteDataTableControl);

        unknowns2Panel.add(makeNonStretchedPanelFor(new ComponentArrayPanel(dMonListBinControlList, new ComponentArrayPanel.ComponentDataSource()
                {
                    public Component createComponent(int componentIndex)
                    {
                        DMonListBinMM6 newDMonListBin = createNewDMonListBin();
                        return new DMonListBinControl(newDMonListBin);
                    }
                    
                    public void fireComponentAdded(int componentIndex, Component component)
                    {
                        DMonListBinControl unknownControl = (DMonListBinControl)component;
                        dMonListBinList.add(componentIndex, unknownControl.getDMonListBin());
                        dMonListBinComparativeByteDataTableControl.getTableModel().fireTableRowsInserted(componentIndex, componentIndex);
                        dMonListBinCountLabel.setText("# of DMonListBins: " + String.valueOf(dMonListBinList.size()));
                    }
                    public void fireComponentDeleted(int componentIndex, Component component)
                    {
                        DMonListBinControl unknownControl = (DMonListBinControl)component;
                        dMonListBinList.remove(componentIndex);
                        dMonListBinComparativeByteDataTableControl.getTableModel().fireTableRowsDeleted(componentIndex, componentIndex);
                        dMonListBinCountLabel.setText("# of DMonListBins: " + String.valueOf(dMonListBinList.size()));
                    }
                    public void fireComponentMovedUp(int componentIndex, Component component)
                    {
                        DMonListBinMM6 unknown = (DMonListBinMM6)dMonListBinList.remove(componentIndex);
                        dMonListBinList.add(componentIndex - 1, unknown);
                        
                        dMonListBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                    public void fireComponentMovedDown(int componentIndex, Component component)
                    {
                        DMonListBinMM6 unknown = (DMonListBinMM6)dMonListBinList.remove(componentIndex);
                        dMonListBinList.add(componentIndex + 1, unknown);
                        
                        dMonListBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                })));
        
        componentPanel.add(makeNonStretchedPanelFor(unknowns2Panel));
		
        panel.add(component, BorderLayout.CENTER);
		
		return panel;
    }		
    
    private DMonListBinMM6 createNewDMonListBin()
    {
        String soundName = "";

        DMonListBinMM6 newDMonListBin = new DMonListBinMM6(soundName);
        
        return newDMonListBin;
    }

    public LodResource getUpdatedLodResource()
    {
        byte[] newData = new byte[dMonListBinList.size() * DMonListBinMM6.getRecordSize()];
        int offset = DMonListBinMM6.updateData(newData, 0, dMonListBinList);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
