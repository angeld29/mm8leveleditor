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

import org.gamenet.application.mm8leveleditor.control.DObjListBinControl;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.DObjListBinMM6;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.ComponentArrayPanel;
import org.gamenet.util.TaskObserver;

public class DObjListBinMM6Handler extends AbstractBaseSubHandler
{
    private LodResource sourceLodResource = null;
    private byte sourceDataCache[] = null;
    private List dObjListBinList = null;
    
    public Component getComponentFor(LodResource lodResource, byte sourceDataCache[], TaskObserver taskObserver) throws InterruptedException
    {
        this.sourceLodResource = lodResource;
        this.sourceDataCache = sourceDataCache;
        
        int offset = 0;
        dObjListBinList = new ArrayList();
        offset = DObjListBinMM6.populateObjects(this.sourceDataCache, offset, dObjListBinList);
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DObjListBinHandler.getComponentFor() was interrupted.");

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea description = new JTextArea(lodResource.getTextDescription());
        panel.add(description, BorderLayout.PAGE_START);

        JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));

		Component component = componentPanel;
		
		// DObjListBin
        JPanel unknowns2Panel = new JPanel();
        unknowns2Panel.setLayout(new BoxLayout(unknowns2Panel, BoxLayout.Y_AXIS));

        final JLabel dObjListBinCountLabel = new JLabel("# of DObjListBins: " + String.valueOf(dObjListBinList.size()));
        unknowns2Panel.add(makeNonStretchedPanelFor(dObjListBinCountLabel));

        final ComparativeTableControl dObjListBinComparativeByteDataTableControl = new ComparativeTableControl(DObjListBinMM6.getOffsetList(), DObjListBinMM6.getComparativeDataSource(dObjListBinList));

        List dObjListBinControlList = new ArrayList(dObjListBinList.size());
        for (int dObjListBinIndex = 0; dObjListBinIndex < dObjListBinList.size(); ++dObjListBinIndex)
        {
            DObjListBinMM6 dObjListBin = (DObjListBinMM6)dObjListBinList.get(dObjListBinIndex);
            dObjListBinControlList.add(new DObjListBinControl(dObjListBin));
        }
 
        unknowns2Panel.add(makeNonStretchedPanelFor(new JLabel("DObjListBin Unknowns: ")));
        unknowns2Panel.add(dObjListBinComparativeByteDataTableControl);

        unknowns2Panel.add(makeNonStretchedPanelFor(new ComponentArrayPanel(dObjListBinControlList, new ComponentArrayPanel.ComponentDataSource()
                {
                    public Component createComponent(int componentIndex)
                    {
                        DObjListBinMM6 newDObjListBin = createNewDObjListBin();
                        return new DObjListBinControl(newDObjListBin);
                    }
                    
                    public void fireComponentAdded(int componentIndex, Component component)
                    {
                        DObjListBinControl unknownControl = (DObjListBinControl)component;
                        dObjListBinList.add(componentIndex, unknownControl.getDObjListBin());
                        dObjListBinComparativeByteDataTableControl.getTableModel().fireTableRowsInserted(componentIndex, componentIndex);
                        dObjListBinCountLabel.setText("# of DObjListBins: " + String.valueOf(dObjListBinList.size()));
                    }
                    public void fireComponentDeleted(int componentIndex, Component component)
                    {
                        DObjListBinControl unknownControl = (DObjListBinControl)component;
                        dObjListBinList.remove(componentIndex);
                        dObjListBinComparativeByteDataTableControl.getTableModel().fireTableRowsDeleted(componentIndex, componentIndex);
                        dObjListBinCountLabel.setText("# of DObjListBins: " + String.valueOf(dObjListBinList.size()));
                    }
                    public void fireComponentMovedUp(int componentIndex, Component component)
                    {
                        DObjListBinMM6 unknown = (DObjListBinMM6)dObjListBinList.remove(componentIndex);
                        dObjListBinList.add(componentIndex - 1, unknown);
                        
                        dObjListBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                    public void fireComponentMovedDown(int componentIndex, Component component)
                    {
                        DObjListBinMM6 unknown = (DObjListBinMM6)dObjListBinList.remove(componentIndex);
                        dObjListBinList.add(componentIndex + 1, unknown);
                        
                        dObjListBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                })));
        
        componentPanel.add(makeNonStretchedPanelFor(unknowns2Panel));
		
        panel.add(component, BorderLayout.CENTER);
		
		return panel;
    }		
    
    private DObjListBinMM6 createNewDObjListBin()
    {
        String soundName = "";

        DObjListBinMM6 newDObjListBin = new DObjListBinMM6(soundName);
        
        return newDObjListBin;
    }

    public LodResource getUpdatedLodResource()
    {
        byte[] newData = new byte[dObjListBinList.size() * DObjListBinMM6.getRecordSize()];
        int offset = DObjListBinMM6.updateData(newData, 0, dObjListBinList);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
