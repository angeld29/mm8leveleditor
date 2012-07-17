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

import org.gamenet.application.mm8leveleditor.control.DDecListBinControl;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.DDecListBinMM6;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.ComponentArrayPanel;
import org.gamenet.util.TaskObserver;

public class DDecListBinMM6Handler extends AbstractBaseSubHandler
{
    private LodResource sourceLodResource = null;
    private byte sourceDataCache[] = null;
    private List dDecListBinList = null;
    
    public Component getComponentFor(LodResource lodResource, byte sourceDataCache[], TaskObserver taskObserver) throws InterruptedException
    {
        this.sourceLodResource = lodResource;
        this.sourceDataCache = sourceDataCache;
        
        int offset = 0;
        dDecListBinList = new ArrayList();
        offset = DDecListBinMM6.populateObjects(this.sourceDataCache, offset, dDecListBinList);
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DDecListBinHandler.getComponentFor() was interrupted.");

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea description = new JTextArea(lodResource.getTextDescription());
        panel.add(description, BorderLayout.PAGE_START);

        JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));

		Component component = componentPanel;
		
		// DDecListBin
        JPanel unknowns2Panel = new JPanel();
        unknowns2Panel.setLayout(new BoxLayout(unknowns2Panel, BoxLayout.Y_AXIS));

        final JLabel dDecListBinCountLabel = new JLabel("# of DDecListBins: " + String.valueOf(dDecListBinList.size()));
        unknowns2Panel.add(makeNonStretchedPanelFor(dDecListBinCountLabel));

        final ComparativeTableControl dDecListBinComparativeByteDataTableControl = new ComparativeTableControl(DDecListBinMM6.getOffsetList(), DDecListBinMM6.getComparativeDataSource(dDecListBinList));

        List dDecListBinControlList = new ArrayList(dDecListBinList.size());
        for (int dDecListBinIndex = 0; dDecListBinIndex < dDecListBinList.size(); ++dDecListBinIndex)
        {
            DDecListBinMM6 dDecListBin = (DDecListBinMM6)dDecListBinList.get(dDecListBinIndex);
            dDecListBinControlList.add(new DDecListBinControl(dDecListBin));
        }
 
        unknowns2Panel.add(makeNonStretchedPanelFor(new JLabel("DDecListBin Unknowns: ")));
        unknowns2Panel.add(dDecListBinComparativeByteDataTableControl);

        unknowns2Panel.add(makeNonStretchedPanelFor(new ComponentArrayPanel(dDecListBinControlList, new ComponentArrayPanel.ComponentDataSource()
                {
                    public Component createComponent(int componentIndex)
                    {
                        DDecListBinMM6 newDDecListBin = createNewDDecListBin();
                        return new DDecListBinControl(newDDecListBin);
                    }
                    
                    public void fireComponentAdded(int componentIndex, Component component)
                    {
                        DDecListBinControl unknownControl = (DDecListBinControl)component;
                        dDecListBinList.add(componentIndex, unknownControl.getDDecListBin());
                        dDecListBinComparativeByteDataTableControl.getTableModel().fireTableRowsInserted(componentIndex, componentIndex);
                        dDecListBinCountLabel.setText("# of DDecListBins: " + String.valueOf(dDecListBinList.size()));
                    }
                    public void fireComponentDeleted(int componentIndex, Component component)
                    {
                        DDecListBinControl unknownControl = (DDecListBinControl)component;
                        dDecListBinList.remove(componentIndex);
                        dDecListBinComparativeByteDataTableControl.getTableModel().fireTableRowsDeleted(componentIndex, componentIndex);
                        dDecListBinCountLabel.setText("# of DDecListBins: " + String.valueOf(dDecListBinList.size()));
                    }
                    public void fireComponentMovedUp(int componentIndex, Component component)
                    {
                        DDecListBinMM6 unknown = (DDecListBinMM6)dDecListBinList.remove(componentIndex);
                        dDecListBinList.add(componentIndex - 1, unknown);
                        
                        dDecListBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                    public void fireComponentMovedDown(int componentIndex, Component component)
                    {
                        DDecListBinMM6 unknown = (DDecListBinMM6)dDecListBinList.remove(componentIndex);
                        dDecListBinList.add(componentIndex + 1, unknown);
                        
                        dDecListBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                })));
        
        componentPanel.add(makeNonStretchedPanelFor(unknowns2Panel));
		
        panel.add(component, BorderLayout.CENTER);
		
		return panel;
    }		
    
    private DDecListBinMM6 createNewDDecListBin()
    {
        String soundName = "";

        DDecListBinMM6 newDDecListBin = new DDecListBinMM6(soundName);
        
        return newDDecListBin;
    }

    public LodResource getUpdatedLodResource()
    {
        byte[] newData = new byte[dDecListBinList.size() * DDecListBinMM6.getRecordSize()];
        int offset = DDecListBinMM6.updateData(newData, 0, dDecListBinList);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
