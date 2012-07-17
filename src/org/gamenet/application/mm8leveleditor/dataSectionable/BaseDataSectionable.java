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
package org.gamenet.application.mm8leveleditor.dataSectionable;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gamenet.application.mm8leveleditor.data.ByteData;
import org.gamenet.swing.controls.ByteDataTableControl;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.ComponentArrayPanel;
import org.gamenet.util.SubTaskObserverImpl;
import org.gamenet.util.TaskObserver;

public abstract class BaseDataSectionable
{
    protected List listForIterator(Iterator iterator)
    {
        List integerList = new ArrayList();
        while (iterator.hasNext())
        {
            integerList.add(iterator.next());
        }
        return integerList;
    }
    
    protected List createListForSequence(int size)
    {
        return createListForSequence(0, size - 1);
    }
    
    protected List createListForSequence(int start, int stop)
    {
        List integerList = new ArrayList();
        for (int i = start; i <= stop; i++)
        {
            integerList.add(new Integer(i));
        }
        return integerList;
    }
    
    protected JPanel makeNonStretchedPanelFor(Component component)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        if (null != component)  panel.add(component);
        return panel;
    }
    
    public interface DataTypeInfo
    {
        public String getDataTypeNameSingular();
        public String getDataTypeNamePlural();
        public String getNumberOfDataTypeNameLabel();
        public List getDataList();
        public List getOffsetList();
        public ComparativeTableControl.DataSource getComparativeDataSource();
        public Component getDataControl(int dataIndex);
        public Component createNewDataControl();
        public void addDataAtIndexFromComponent(int index, Component component);
        public void removeDataAtIndex(int index);
        public void moveDataAtIndexUp(int index);
        public void moveDataAtIndexDown(int index);
    }
    
    public abstract class AbstractDataTypeInfo implements DataTypeInfo
    {
        public abstract String getDataTypeNameSingular();
        public abstract String getDataTypeNamePlural();
        public abstract List getDataList();
        public abstract List getOffsetList();
        public abstract ComparativeTableControl.DataSource getComparativeDataSource();
        public abstract Component getDataControl(int dataIndex);
        public abstract Component createNewDataControl();
        public abstract void addDataAtIndexFromComponent(int index, Component component);

        // these methods don't require modification
        
        public String getNumberOfDataTypeNameLabel() {
            return "# of " + getDataTypeNamePlural() + ": " + String.valueOf(getDataList().size());
        }
        
        public void removeDataAtIndex(int index) {
            getDataList().remove(index);
        }

        public void moveDataAtIndexUp(int index) {
            getDataList().add(index - 1, getDataList().remove(index));
        }

        public void moveDataAtIndexDown(int index) {
            getDataList().add(index + 1, getDataList().remove(index));
        }
    }
    
    protected Component getPanel(TaskObserver taskObserver, List indexNumberList, final DataTypeInfo dataTypeInfo) throws InterruptedException
    {
        List dataList = dataTypeInfo.getDataList();
		if (null == indexNumberList)  indexNumberList = createListForSequence(dataList.size());

		JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        final JLabel dataCountLabel = new JLabel("# of " + dataTypeInfo.getDataTypeNameSingular() + " Objects: " + String.valueOf(dataList.size()));
        dataPanel.add(makeNonStretchedPanelFor(dataCountLabel));

        final ComparativeTableControl dataComparativeByteDataTableControl;
        if (null != dataTypeInfo.getOffsetList())
        {
            dataComparativeByteDataTableControl = new ComparativeTableControl(dataTypeInfo.getOffsetList(),
                    new ComparativeTableControl.PartialComparativeDataSource(dataTypeInfo.getComparativeDataSource(), indexNumberList));
        }
        else dataComparativeByteDataTableControl = null;

        taskObserver.taskProgress(dataTypeInfo.getDataTypeNamePlural(), 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getPanel(" + dataTypeInfo.getDataTypeNamePlural() + ") was interrupted.");

        List dataControlList = new ArrayList(indexNumberList.size());
		Iterator indexIterator = indexNumberList.iterator();
        while (indexIterator.hasNext())
        {
            Integer indexInteger = (Integer) indexIterator.next();
            int dataIndex = indexInteger.intValue();

            dataControlList.add(dataTypeInfo.getDataControl(dataIndex));
            
            taskObserver.taskProgress(dataTypeInfo.getDataTypeNamePlural(), 0.1f + (((0.7f - 0.1f) / indexNumberList.size()) * dataIndex));
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("getPanel(" + dataTypeInfo.getDataTypeNamePlural() + ") was interrupted.");
        }
        dataPanel.add(makeNonStretchedPanelFor(new ComponentArrayPanel(new SubTaskObserverImpl(taskObserver, dataTypeInfo.getDataTypeNamePlural(), 0.7f, .99f), dataControlList, new ComponentArrayPanel.ComponentDataSource()
                {
                    public Component createComponent(int componentIndex)
                    {
                        return dataTypeInfo.createNewDataControl();
                    }
                    
                    public void fireComponentAdded(int componentIndex, Component component)
                    {
                        dataTypeInfo.addDataAtIndexFromComponent(componentIndex, component);
                        if (null != dataComparativeByteDataTableControl)
                            dataComparativeByteDataTableControl.getTableModel().fireTableRowsInserted(componentIndex, componentIndex);
                        dataCountLabel.setText(dataTypeInfo.getNumberOfDataTypeNameLabel());
                    }
                    public void fireComponentDeleted(int componentIndex, Component component)
                    {
                        dataTypeInfo.removeDataAtIndex(componentIndex);
                        if (null != dataComparativeByteDataTableControl)
                            dataComparativeByteDataTableControl.getTableModel().fireTableRowsDeleted(componentIndex, componentIndex);
                        dataCountLabel.setText(dataTypeInfo.getNumberOfDataTypeNameLabel());
                    }
                    public void fireComponentMovedUp(int componentIndex, Component component)
                    {
                        dataTypeInfo.moveDataAtIndexUp(componentIndex);
                        if (null != dataComparativeByteDataTableControl)
                            dataComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                    public void fireComponentMovedDown(int componentIndex, Component component)
                    {
                        dataTypeInfo.moveDataAtIndexDown(componentIndex);
                        if (null != dataComparativeByteDataTableControl)
                            dataComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }
                })));
        
        if (null != dataComparativeByteDataTableControl)
        {
            dataPanel.add(makeNonStretchedPanelFor(new JLabel(dataTypeInfo.getDataTypeNameSingular() + " Unknowns: ")));
            dataPanel.add(dataComparativeByteDataTableControl);
        }
		
        return dataPanel;
    }

    public abstract DataTypeInfo getDataTypeInfo(String dataSectionName);
    
    public Component getUnknownsPanel(TaskObserver taskObserver, List indexNumberList, List byteDataList) throws InterruptedException
    {
        taskObserver.taskProgress("Unknowns", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getUnknownsPanel() was interrupted.");

		// Unknowns
        JPanel unknownsDataPanel = new JPanel();
        unknownsDataPanel.setLayout(new BoxLayout(unknownsDataPanel, BoxLayout.Y_AXIS));

		if (null == indexNumberList)  indexNumberList = createListForSequence(byteDataList.size());

		Iterator indexIterator = indexNumberList.iterator();
        while (indexIterator.hasNext())
        {
            Integer indexInteger = (Integer) indexIterator.next();
            int byteDataIndex = indexInteger.intValue();

            ByteData byteData = (ByteData)byteDataList.get(byteDataIndex);

    		ByteDataTableControl byteDataTableControl = new ByteDataTableControl(byteData.getData(), 32, byteData.getExternalOffset(), byteData.getStartOffset(), byteData.getEndOffset());
    		unknownsDataPanel.add(makeNonStretchedPanelFor(byteDataTableControl));
            
            taskObserver.taskProgress("Unknowns", 0.1f + (((0.7f - 0.1f) / indexNumberList.size()) * byteDataIndex));
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("getUnknownsPanel() was interrupted.");
		}
        
        return unknownsDataPanel;
    }

    public Component getNonApplicablePanel(TaskObserver taskObserver) throws InterruptedException
    {
        return makeNonStretchedPanelFor(new JLabel("Non-applicable to this game version."));
    }

    public Component getListComponent(String dataSectionName, TaskObserver taskObserver, List list, Iterator indexIterator) throws InterruptedException
    {
        List indexNumberList = listForIterator(indexIterator);
        return getPanel(taskObserver, indexNumberList, getDataTypeInfo(dataSectionName));
    }

    public Component getComponent(String dataSectionName, TaskObserver taskObserver) throws InterruptedException
    {
        return getPanel(taskObserver, null, getDataTypeInfo(dataSectionName));
    }
}
