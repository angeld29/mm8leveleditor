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

import org.gamenet.application.mm8leveleditor.control.DIftBinControl;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.DIftBin;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.ComponentArrayPanel;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnsupportedFileFormatException;

public class DIftBinHandler extends AbstractBaseHandler
{
    private LodResource sourceLodResource = null;
    private byte sourceDataCache[] = null;
    private List dIftBinList = null;
    
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
            throw new InterruptedException("DIftBinHandler.getComponentFor() was interrupted.");

        dIftBinList = new ArrayList();        
        int offset = 0;
        if (false == DIftBin.checkDataIntegrity(this.sourceDataCache, offset, this.sourceDataCache.length))
        {
            throw new UnsupportedFileFormatException("Not an expected mm6 DIft.bin format.");
        }
        offset = DIftBin.populateObjects(this.sourceDataCache, offset, dIftBinList);
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DIftBinHandler.getComponentFor() was interrupted.");

		JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));

		Component component = componentPanel;
		
		// DIftBin
        JPanel unknowns2Panel = new JPanel();
        unknowns2Panel.setLayout(new BoxLayout(unknowns2Panel, BoxLayout.Y_AXIS));

        final JLabel dIftBinCountLabel = new JLabel("# of DIftBins: " + String.valueOf(dIftBinList.size()));
        unknowns2Panel.add(makeNonStretchedPanelFor(dIftBinCountLabel));

        final ComparativeTableControl dIftBinComparativeByteDataTableControl = new ComparativeTableControl(DIftBin.getOffsetList(), DIftBin.getComparativeDataSource(dIftBinList));

        List dIftBinControlList = new ArrayList(dIftBinList.size());
        for (int dIftBinIndex = 0; dIftBinIndex < dIftBinList.size(); ++dIftBinIndex)
        {
            DIftBin dIftBin = (DIftBin)dIftBinList.get(dIftBinIndex);
            dIftBinControlList.add(new DIftBinControl(dIftBin));
        }
 
        unknowns2Panel.add(makeNonStretchedPanelFor(new JLabel("DIftBin Unknowns: ")));
        unknowns2Panel.add(dIftBinComparativeByteDataTableControl);

        unknowns2Panel.add(makeNonStretchedPanelFor(new ComponentArrayPanel(dIftBinControlList, new ComponentArrayPanel.ComponentDataSource()
                {
                    public Component createComponent(int componentIndex)
                    {
                        DIftBin newDIftBin = createNewDIftBin();
                        return new DIftBinControl(newDIftBin);
                    }
                    
                    public void fireComponentAdded(int componentIndex, Component component)
                    {
                        DIftBinControl unknownControl = (DIftBinControl)component;
                        dIftBinList.add(componentIndex, unknownControl.getDIftBin());
                        dIftBinComparativeByteDataTableControl.getTableModel().fireTableRowsInserted(componentIndex, componentIndex);
                        dIftBinCountLabel.setText("# of DIftBins: " + String.valueOf(dIftBinList.size()));
                    }
                    public void fireComponentDeleted(int componentIndex, Component component)
                    {
                        DIftBinControl unknownControl = (DIftBinControl)component;
                        dIftBinList.remove(componentIndex);
                        dIftBinComparativeByteDataTableControl.getTableModel().fireTableRowsDeleted(componentIndex, componentIndex);
                        dIftBinCountLabel.setText("# of DIftBins: " + String.valueOf(dIftBinList.size()));
                    }
                    public void fireComponentMovedUp(int componentIndex, Component component)
                    {
                        DIftBin unknown = (DIftBin)dIftBinList.remove(componentIndex);
                        dIftBinList.add(componentIndex - 1, unknown);
                        
                        dIftBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                    public void fireComponentMovedDown(int componentIndex, Component component)
                    {
                        DIftBin unknown = (DIftBin)dIftBinList.remove(componentIndex);
                        dIftBinList.add(componentIndex + 1, unknown);
                        
                        dIftBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                })));
        
        componentPanel.add(makeNonStretchedPanelFor(unknowns2Panel));
		
        panel.add(component, BorderLayout.CENTER);
		
		return panel;
    }		
    
    private DIftBin createNewDIftBin()
    {
        String soundName = "";

        DIftBin newDIftBin = new DIftBin(soundName);
        
        return newDIftBin;
    }

    public LodResource getUpdatedLodResource()
    {
        byte[] newData = new byte[dIftBinList.size() * DIftBin.getRecordSize()];
        int offset = DIftBin.updateData(newData, 0, dIftBinList);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
