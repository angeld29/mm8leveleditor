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

import org.gamenet.application.mm8leveleditor.control.DOverlayBinControl;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.DOverlayBin;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.ComponentArrayPanel;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnsupportedFileFormatException;

public class DOverlayBinHandler extends AbstractBaseHandler
{
    private LodResource sourceLodResource = null;
    private byte sourceDataCache[] = null;
    private List dOverlayBinList = null;
    
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
            throw new InterruptedException("DOverlayBinHandler.getComponentFor() was interrupted.");

        int offset = 0;
        if (false == DOverlayBin.checkDataIntegrity(this.sourceDataCache, offset, this.sourceDataCache.length))
        {
            throw new UnsupportedFileFormatException("Not an expected mm6 DOverlay.bin format.");
        }
        dOverlayBinList = new ArrayList();
        offset = DOverlayBin.populateObjects(this.sourceDataCache, offset, dOverlayBinList);
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DOverlayBinHandler.getComponentFor() was interrupted.");

		JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));

		Component component = componentPanel;
		
		// DOverlayBin
        JPanel unknowns2Panel = new JPanel();
        unknowns2Panel.setLayout(new BoxLayout(unknowns2Panel, BoxLayout.Y_AXIS));

        final JLabel dOverlayBinCountLabel = new JLabel("# of DOverlayBins: " + String.valueOf(dOverlayBinList.size()));
        unknowns2Panel.add(makeNonStretchedPanelFor(dOverlayBinCountLabel));

        final ComparativeTableControl dOverlayBinComparativeByteDataTableControl = new ComparativeTableControl(DOverlayBin.getOffsetList(), DOverlayBin.getComparativeDataSource(dOverlayBinList));

        List dOverlayBinControlList = new ArrayList(dOverlayBinList.size());
        for (int dOverlayBinIndex = 0; dOverlayBinIndex < dOverlayBinList.size(); ++dOverlayBinIndex)
        {
            DOverlayBin dOverlayBin = (DOverlayBin)dOverlayBinList.get(dOverlayBinIndex);
            dOverlayBinControlList.add(new DOverlayBinControl(dOverlayBin));
        }
 
        unknowns2Panel.add(makeNonStretchedPanelFor(new JLabel("DOverlayBin Unknowns: ")));
        unknowns2Panel.add(dOverlayBinComparativeByteDataTableControl);

        unknowns2Panel.add(makeNonStretchedPanelFor(new ComponentArrayPanel(dOverlayBinControlList, new ComponentArrayPanel.ComponentDataSource()
                {
                    public Component createComponent(int componentIndex)
                    {
                        DOverlayBin newDOverlayBin = createNewDOverlayBin();
                        return new DOverlayBinControl(newDOverlayBin);
                    }
                    
                    public void fireComponentAdded(int componentIndex, Component component)
                    {
                        DOverlayBinControl unknownControl = (DOverlayBinControl)component;
                        dOverlayBinList.add(componentIndex, unknownControl.getDOverlayBin());
                        dOverlayBinComparativeByteDataTableControl.getTableModel().fireTableRowsInserted(componentIndex, componentIndex);
                        dOverlayBinCountLabel.setText("# of DOverlayBins: " + String.valueOf(dOverlayBinList.size()));
                    }
                    public void fireComponentDeleted(int componentIndex, Component component)
                    {
                        DOverlayBinControl unknownControl = (DOverlayBinControl)component;
                        dOverlayBinList.remove(componentIndex);
                        dOverlayBinComparativeByteDataTableControl.getTableModel().fireTableRowsDeleted(componentIndex, componentIndex);
                        dOverlayBinCountLabel.setText("# of DOverlayBins: " + String.valueOf(dOverlayBinList.size()));
                    }
                    public void fireComponentMovedUp(int componentIndex, Component component)
                    {
                        DOverlayBin unknown = (DOverlayBin)dOverlayBinList.remove(componentIndex);
                        dOverlayBinList.add(componentIndex - 1, unknown);
                        
                        dOverlayBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                    public void fireComponentMovedDown(int componentIndex, Component component)
                    {
                        DOverlayBin unknown = (DOverlayBin)dOverlayBinList.remove(componentIndex);
                        dOverlayBinList.add(componentIndex + 1, unknown);
                        
                        dOverlayBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                })));
        
        componentPanel.add(makeNonStretchedPanelFor(unknowns2Panel));
		
        panel.add(component, BorderLayout.CENTER);
		
		return panel;
    }		
    
    private DOverlayBin createNewDOverlayBin()
    {
        String soundName = "";

        DOverlayBin newDOverlayBin = new DOverlayBin(soundName);
        
        return newDOverlayBin;
    }

    public LodResource getUpdatedLodResource()
    {
        byte[] newData = new byte[dOverlayBinList.size() * DOverlayBin.getRecordSize()];
        int offset = DOverlayBin.updateData(newData, 0, dOverlayBinList);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
