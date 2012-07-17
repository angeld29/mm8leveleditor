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

import org.gamenet.application.mm8leveleditor.control.DTftBinControl;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.DTftBin;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.ComponentArrayPanel;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnsupportedFileFormatException;

public class DTftBinHandler extends AbstractBaseHandler
{
    private LodResource sourceLodResource = null;
    private byte sourceDataCache[] = null;
    private List dTftBinList = null;
    
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
            throw new InterruptedException("DTftBinHandler.getComponentFor() was interrupted.");

        int offset = 0;
        if (false == DTftBin.checkDataIntegrity(this.sourceDataCache, offset, this.sourceDataCache.length))
        {
            throw new UnsupportedFileFormatException("Not an expected mm6 DTft.bin format.");
        }
        dTftBinList = new ArrayList();
        offset = DTftBin.populateObjects(this.sourceDataCache, offset, dTftBinList);
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DTftBinHandler.getComponentFor() was interrupted.");

		JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));

		Component component = componentPanel;
		
		// DTftBin
        JPanel unknowns2Panel = new JPanel();
        unknowns2Panel.setLayout(new BoxLayout(unknowns2Panel, BoxLayout.Y_AXIS));

        final JLabel dTftBinCountLabel = new JLabel("# of DTftBins: " + String.valueOf(dTftBinList.size()));
        unknowns2Panel.add(makeNonStretchedPanelFor(dTftBinCountLabel));

        final ComparativeTableControl dTftBinComparativeByteDataTableControl = new ComparativeTableControl(DTftBin.getOffsetList(), DTftBin.getComparativeDataSource(dTftBinList));

        List dTftBinControlList = new ArrayList(dTftBinList.size());
        for (int dTftBinIndex = 0; dTftBinIndex < dTftBinList.size(); ++dTftBinIndex)
        {
            DTftBin dTftBin = (DTftBin)dTftBinList.get(dTftBinIndex);
            dTftBinControlList.add(new DTftBinControl(dTftBin));
        }
 
        unknowns2Panel.add(makeNonStretchedPanelFor(new JLabel("DTftBin Unknowns: ")));
        unknowns2Panel.add(dTftBinComparativeByteDataTableControl);

        unknowns2Panel.add(makeNonStretchedPanelFor(new ComponentArrayPanel(dTftBinControlList, new ComponentArrayPanel.ComponentDataSource()
                {
                    public Component createComponent(int componentIndex)
                    {
                        DTftBin newDTftBin = createNewDTftBin();
                        return new DTftBinControl(newDTftBin);
                    }
                    
                    public void fireComponentAdded(int componentIndex, Component component)
                    {
                        DTftBinControl unknownControl = (DTftBinControl)component;
                        dTftBinList.add(componentIndex, unknownControl.getDTftBin());
                        dTftBinComparativeByteDataTableControl.getTableModel().fireTableRowsInserted(componentIndex, componentIndex);
                        dTftBinCountLabel.setText("# of DTftBins: " + String.valueOf(dTftBinList.size()));
                    }
                    public void fireComponentDeleted(int componentIndex, Component component)
                    {
                        DTftBinControl unknownControl = (DTftBinControl)component;
                        dTftBinList.remove(componentIndex);
                        dTftBinComparativeByteDataTableControl.getTableModel().fireTableRowsDeleted(componentIndex, componentIndex);
                        dTftBinCountLabel.setText("# of DTftBins: " + String.valueOf(dTftBinList.size()));
                    }
                    public void fireComponentMovedUp(int componentIndex, Component component)
                    {
                        DTftBin unknown = (DTftBin)dTftBinList.remove(componentIndex);
                        dTftBinList.add(componentIndex - 1, unknown);
                        
                        dTftBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                    public void fireComponentMovedDown(int componentIndex, Component component)
                    {
                        DTftBin unknown = (DTftBin)dTftBinList.remove(componentIndex);
                        dTftBinList.add(componentIndex + 1, unknown);
                        
                        dTftBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                })));
        
        componentPanel.add(makeNonStretchedPanelFor(unknowns2Panel));
		
        panel.add(component, BorderLayout.CENTER);
		
		return panel;
    }		
    
    private DTftBin createNewDTftBin()
    {
        String soundName = "";

        DTftBin newDTftBin = new DTftBin(soundName);
        
        return newDTftBin;
    }

    public LodResource getUpdatedLodResource()
    {
        byte[] newData = new byte[dTftBinList.size() * DTftBin.getRecordSize()];
        int offset = DTftBin.updateData(newData, 0, dTftBinList);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
