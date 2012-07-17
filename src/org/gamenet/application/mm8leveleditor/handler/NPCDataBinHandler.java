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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.NPCDataBin;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.swing.controls.ResizingJTable;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnsupportedFileFormatException;

public class NPCDataBinHandler extends AbstractBaseHandler
{
    private LodResource sourceLodResource = null;
    private byte sourceDataCache[] = null;
    private List npcDataList = null;
    
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
            throw new InterruptedException("DdmHandler.getComponentFor() was interrupted.");

        int npcDataCount = this.sourceDataCache.length / 60;
        npcDataList = new ArrayList();
        
        int offset = 0;
        if (false == NPCDataBin.checkDataIntegrity(this.sourceDataCache, offset, this.sourceDataCache.length, npcDataCount))
        {
            throw new UnsupportedFileFormatException("Not an expected mm6 NPCData.bin format.");
        }
        offset = NPCDataBin.populateObjects(this.sourceDataCache, offset, npcDataList, npcDataCount);
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DdmHandler.getComponentFor() was interrupted.");

		JPanel componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));

		Component component = componentPanel;
		
		TableModel npcDataModel = new AbstractTableModel()
        {
            public String getColumnName(int column)
            {
                switch(column)
                {
                    case 0:
                        return "#";
                    case 1:
                        return "unknown 1";
                    case 2:
                        return "pic";
                    case 3:
                        return "state";
                    case 4:
                        return "fame";
                    case 5:
                        return "rep";
                    case 6:
                        return "2DLoc";
                    case 7:
                        return "prof";
                    case 8:
                        return "join";
                    case 9:
                        return "news";
                    case 10:
                        return "Event A";
                    case 11:
                        return "Event B";
                    case 12:
                        return "Event C";
                    case 13:
                        return "unknown 2";
                    case 14:
                        return "unknown 3";
                    case 15:
                        return "unknown 4";
                    default:
                        throw new ArrayIndexOutOfBoundsException("Should never reach column #" + column);
                }
            }

            public int getColumnCount()
            {
                return 16;
            }

            public int getRowCount()
            {
                return npcDataList.size() + 1;
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                if (0 == rowIndex)  return false;
                if (0 == columnIndex)  return false;
                
                return true;
            }

            public Object getValueAt(int rowIndex, int columnIndex)
            {
                if (0 == rowIndex)  return getColumnName(columnIndex);
                
                if (0 == columnIndex)  return String.valueOf(rowIndex - 1);

                NPCDataBin npcData = (NPCDataBin)npcDataList.get(rowIndex - 1);
                
                switch(columnIndex)
                {
                    case 0:
                        return getColumnName(columnIndex);
                    case 1:
                        return new Integer(npcData.getUnknown1());
                    case 2:
                        return new Integer(npcData.getPicture());
                    case 3:
                        return new Integer(npcData.getBtbCheckState());
                    case 4:
                        return new Integer(npcData.getFame());
                    case 5:
                        return new Integer(npcData.getReputation());
                    case 6:
                        return new Integer(npcData.getCurrent2DLocation());
                    case 7:
                        return new Integer(npcData.getProfession());
                    case 8:
                        return new Integer(npcData.getJoin());
                    case 9:
                        return new Integer(npcData.getNews());
                    case 10:
                        return new Integer(npcData.getEventA());
                    case 11:
                        return new Integer(npcData.getEventB());
                    case 12:
                        return new Integer(npcData.getEventC());
                    case 13:
                        return new Integer(npcData.getUnknown2());
                    case 14:
                        return new Integer(npcData.getUnknown3());
                    case 15:
                        return new Integer(npcData.getUnknown4());
                    default:
                        throw new ArrayIndexOutOfBoundsException("Should never reach column #" + columnIndex);
                }
            }

            public void setValueAt(Object aValue, int rowIndex, int columnIndex)
            {
                NPCDataBin npcData = (NPCDataBin)npcDataList.get(rowIndex - 1);
                
                switch(columnIndex)
                {
                    case 1:
                        npcData.setUnknown1(Integer.parseInt((String)aValue));
                    case 2:
                        npcData.setPicture(Integer.parseInt((String)aValue));
                    case 3:
                        npcData.setBtbCheckState(Integer.parseInt((String)aValue));
                    case 4:
                        npcData.setFame(Integer.parseInt((String)aValue));
                    case 5:
                        npcData.setReputation(Integer.parseInt((String)aValue));
                    case 6:
                        npcData.setCurrent2DLocation(Integer.parseInt((String)aValue));
                    case 7:
                        npcData.setProfession(Integer.parseInt((String)aValue));
                    case 8:
                        npcData.setJoin(Integer.parseInt((String)aValue));
                    case 9:
                        npcData.setNews(Integer.parseInt((String)aValue));
                    case 10:
                        npcData.setEventA(Integer.parseInt((String)aValue));
                    case 11:
                        npcData.setEventB(Integer.parseInt((String)aValue));
                    case 12:
                        npcData.setEventC(Integer.parseInt((String)aValue));
                    case 13:
                        npcData.setUnknown2(Integer.parseInt((String)aValue));
                    case 14:
                        npcData.setUnknown3(Integer.parseInt((String)aValue));
                    case 15:
                        npcData.setUnknown4(Integer.parseInt((String)aValue));
                    default:
                        throw new ArrayIndexOutOfBoundsException("Should never reach column #" + columnIndex);
                }
            }
        };
		
		componentPanel.add(makeNonStretchedPanelFor(new JLabel("# of NPCs: " + String.valueOf(npcDataList.size()))));

		ResizingJTable npcDataTable = new ResizingJTable(npcDataModel);
        componentPanel.add(makeNonStretchedPanelFor(npcDataTable));
		
        panel.add(component, BorderLayout.CENTER);
		
		return panel;
    }		
    
    public LodResource getUpdatedLodResource()
    {
        byte[] newData = new byte[npcDataList.size() * 60];
        int offset = NPCDataBin.updateData(newData, 0, npcDataList);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
