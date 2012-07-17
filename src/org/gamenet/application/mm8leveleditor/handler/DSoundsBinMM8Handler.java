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

import org.gamenet.application.mm8leveleditor.control.DSoundsBinControl;
import org.gamenet.application.mm8leveleditor.data.mm8.fileFormat.DSoundsBinMM8;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.ComponentArrayPanel;
import org.gamenet.util.TaskObserver;

public class DSoundsBinMM8Handler extends AbstractBaseSubHandler
{
    private LodResource sourceLodResource = null;
    private byte sourceDataCache[] = null;
    private List dSoundsBinList = null;
    
    public Component getComponentFor(LodResource lodResource, byte sourceDataCache[], TaskObserver taskObserver) throws InterruptedException
    {
        this.sourceLodResource = lodResource;
        
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea description = new JTextArea(lodResource.getTextDescription());
        panel.add(description, BorderLayout.PAGE_START);
 
        this.sourceDataCache = sourceDataCache;
        
        int offset = 0;
        dSoundsBinList = new ArrayList();
        offset = DSoundsBinMM8.populateObjects(this.sourceDataCache, offset, dSoundsBinList);
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DSoundsBinHandler.getComponentFor() was interrupted.");

		JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));

		Component component = componentPanel;
		
		// DSoundsBin
        JPanel unknowns2Panel = new JPanel();
        unknowns2Panel.setLayout(new BoxLayout(unknowns2Panel, BoxLayout.Y_AXIS));

        final JLabel dSoundsBinCountLabel = new JLabel("# of DSoundsBins: " + String.valueOf(dSoundsBinList.size()));
        unknowns2Panel.add(makeNonStretchedPanelFor(dSoundsBinCountLabel));

        final ComparativeTableControl dSoundsBinComparativeByteDataTableControl = new ComparativeTableControl(DSoundsBinMM8.getOffsetList(), DSoundsBinMM8.getComparativeDataSource(dSoundsBinList));

        List dSoundsBinControlList = new ArrayList(dSoundsBinList.size());
        for (int dSoundsBinIndex = 0; dSoundsBinIndex < dSoundsBinList.size(); ++dSoundsBinIndex)
        {
            DSoundsBinMM8 dSoundsBin = (DSoundsBinMM8)dSoundsBinList.get(dSoundsBinIndex);
            dSoundsBinControlList.add(new DSoundsBinControl(dSoundsBin));
        }
 
        unknowns2Panel.add(makeNonStretchedPanelFor(new JLabel("DSoundsBin Unknowns: ")));
        unknowns2Panel.add(dSoundsBinComparativeByteDataTableControl);

        unknowns2Panel.add(makeNonStretchedPanelFor(new ComponentArrayPanel(dSoundsBinControlList, new ComponentArrayPanel.ComponentDataSource()
                {
                    public Component createComponent(int componentIndex)
                    {
                        DSoundsBinMM8 newDSoundsBin = createNewDSoundsBin();
                        return new DSoundsBinControl(newDSoundsBin);
                    }
                    
                    public void fireComponentAdded(int componentIndex, Component component)
                    {
                        DSoundsBinControl unknownControl = (DSoundsBinControl)component;
                        dSoundsBinList.add(componentIndex, unknownControl.getDSoundsBin());
                        dSoundsBinComparativeByteDataTableControl.getTableModel().fireTableRowsInserted(componentIndex, componentIndex);
                        dSoundsBinCountLabel.setText("# of DSoundsBins: " + String.valueOf(dSoundsBinList.size()));
                    }
                    public void fireComponentDeleted(int componentIndex, Component component)
                    {
                        DSoundsBinControl unknownControl = (DSoundsBinControl)component;
                        dSoundsBinList.remove(componentIndex);
                        dSoundsBinComparativeByteDataTableControl.getTableModel().fireTableRowsDeleted(componentIndex, componentIndex);
                        dSoundsBinCountLabel.setText("# of DSoundsBins: " + String.valueOf(dSoundsBinList.size()));
                    }
                    public void fireComponentMovedUp(int componentIndex, Component component)
                    {
                        DSoundsBinMM8 unknown = (DSoundsBinMM8)dSoundsBinList.remove(componentIndex);
                        dSoundsBinList.add(componentIndex - 1, unknown);
                        
                        dSoundsBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                    public void fireComponentMovedDown(int componentIndex, Component component)
                    {
                        DSoundsBinMM8 unknown = (DSoundsBinMM8)dSoundsBinList.remove(componentIndex);
                        dSoundsBinList.add(componentIndex + 1, unknown);
                        
                        dSoundsBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                })));
        
        componentPanel.add(makeNonStretchedPanelFor(unknowns2Panel));
		
        panel.add(component, BorderLayout.CENTER);
		
		return panel;
    }		
    
    private DSoundsBinMM8 createNewDSoundsBin()
    {
        String soundName = "";

        DSoundsBinMM8 newDSoundsBin = new DSoundsBinMM8(soundName);
        
        return newDSoundsBin;
    }

    public LodResource getUpdatedLodResource()
    {
        byte[] newData = new byte[dSoundsBinList.size() * DSoundsBinMM8.getRecordSize()];
        int offset = DSoundsBinMM8.updateData(newData, 0, dSoundsBinList);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
