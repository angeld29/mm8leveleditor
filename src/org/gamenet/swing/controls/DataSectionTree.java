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
package org.gamenet.swing.controls;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.gamenet.application.mm8leveleditor.BinaryFileCompare;
import org.gamenet.application.mm8leveleditor.lod.ProgressDisplayer;
import org.gamenet.util.MonitoredTaskThread;
import org.gamenet.util.SequenceNumberIterator;
import org.gamenet.util.TaskObserver;


public class DataSectionTree extends JTree
{
    private boolean multithreaded = true;
    private ProgressDisplayer progressDisplayer;
    
    private DataSectionable root;
    private String rootName;

    private JPopupMenu popup = null;
    private JMenuItem compareWithExternalFileMenuItem = null;
    private JMenuItem compareWithEachOtherMenuItem = null;

    class DataSectionRenderer extends DefaultTreeCellRenderer
    {
        public DataSectionRenderer()
        {
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row,
                boolean hasFocus)
        {
            String labelName = ((NamedDataSectionable)value).getLabel();
            
            Component renderer = super.getTreeCellRendererComponent(tree,
                    value, sel, expanded, leaf, row, hasFocus);
            ((JLabel) renderer).setText(labelName);

            return renderer;
            
        }
    }
    
    private static final int INDEX_STEP_COUNT = 10;

    interface NamedDataSectionable extends DataSectionable
    {
        public String getLabel();
    }
    
    static class NamedDataSectionableImpl implements NamedDataSectionable
    {
        private DataSectionable dataSectionable;
        private String label;
        
        public boolean equals(Object dataSectionableObject)
        {
            if (false == dataSectionableObject instanceof NamedDataSectionableImpl)  return false;
        
            NamedDataSectionableImpl namedDataSectionableImpl = (NamedDataSectionableImpl)dataSectionableObject;
            if (false == namedDataSectionableImpl.getDataSectionable().equals(dataSectionable))  return false;
            if (false == namedDataSectionableImpl.getLabel().equals(label))  return false;
            return true;
        }
        
        public NamedDataSectionableImpl(DataSectionable dataSectionable, String label)
        {
            this.dataSectionable = dataSectionable;
            this.label = label;
        }
        
        public String toString()
        {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(hashCode());
            stringBuffer.append(":dataSectionable=" + dataSectionable);
            stringBuffer.append(",label=" + label);
            return stringBuffer.toString();
        }
        public DataSectionable getDataSectionable()
        {
            return this.dataSectionable;
        }
        public String getLabel()
        {
            return this.label;
        }

        public DataSection[] getStaticDataSections()
        {
            return dataSectionable.getStaticDataSections();
        }

        public Object getData()
        {
            return dataSectionable.getData();
        }

        public Object getDataForDataSection(DataSection dataSection)
        {
            return dataSectionable.getDataForDataSection(dataSection);
        }

        public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
        {
            return dataSectionable.getComponentForDataSection(taskObserver, dataSectionName);
        }

        public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
        {
            return dataSectionable.getListComponentForDataSection(taskObserver, dataSectionName, list, indexIterator);
        }
    }
    
    static class LeafDataSectionable implements NamedDataSectionable
    {
        public Object data;
        public String label;
        
        public boolean equals(Object dataSectionable)
        {
            if (false == dataSectionable instanceof LeafDataSectionable)  return false;
        
            LeafDataSectionable leafDataSectionable = (LeafDataSectionable)dataSectionable;
            
            if (null == leafDataSectionable.getData())
            {
                if (null != data)  return false;
            }
            else
            {
                if (false == leafDataSectionable.getData().equals(data))  return false;
            }
            
            if (false == leafDataSectionable.getLabel().equals(label))  return false;
            return true;
        }
        
        public LeafDataSectionable(Object data, String label)
        {
            super();
            this.data = data;
            this.label = label;
        }
        
        public String getLabel()
        {
            return this.label;
        }

        public DataSection[] getStaticDataSections()
        {
            return null;
        }

        public Object getData()
        {
            return data;
        }

        public Object getDataForDataSection(DataSection dataSection)
        {
            return null;
        }

        public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
        {
            return null;
        }

        public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
        {
            return null;
        }
    }
    
    static class ListItemDataSectionable implements NamedDataSectionable
    {
        private DataSectionable parentDataSectionable;
        private String listID;
        private int index;
        
        public boolean equals(Object dataSectionableObject)
        {
            if (false == dataSectionableObject instanceof ListItemDataSectionable)  return false;
        
            ListItemDataSectionable listItemDataSectionable = (ListItemDataSectionable)dataSectionableObject;
            if (false == listItemDataSectionable.getParentDataSectionable().equals(parentDataSectionable))  return false;
            if (false == listItemDataSectionable.getListID().equals(listID))  return false;
            if (listItemDataSectionable.getIndex() != index)  return false;
            return true;
        }
        
        public ListItemDataSectionable(DataSectionable parentDataSectionable, String listID, int index)
        {
            this.parentDataSectionable = parentDataSectionable;
            this.listID = listID;
            this.index = index;
        }
        
        public String toString()
        {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(hashCode());
            stringBuffer.append(":dataSectionable=" + parentDataSectionable);
            stringBuffer.append(",listID=" + listID);
            stringBuffer.append(",index=" + index);
            return stringBuffer.toString();
        }
        public DataSectionable getParentDataSectionable()
        {
            return this.parentDataSectionable;
        }
        public int getIndex()
        {
            return this.index;
        }
        public String getLabel()
        {
            return listID + "[" + String.valueOf(index) + "]";
        }

        public String getListID()
        {
            return this.listID;
        }
        /**
         * @return list
         */
        public List getList()
        {
            DataSection childDataSection = getDataSectionForParentAndName(parentDataSectionable, listID);
            return (List)parentDataSectionable.getDataForDataSection(childDataSection);
        }
        
        public NamedDataSectionable getDataSectionable()
        {
            return getDataSectionableForParentNameAndIndex(parentDataSectionable, listID, index);
        }

        public DataSection[] getStaticDataSections()
        {
            return getDataSectionable().getStaticDataSections();
        }

        public Object getData()
        {
            return getDataSectionable().getData();
        }

        public Object getDataForDataSection(DataSection dataSection)
        {
            return getDataSectionable().getDataForDataSection(dataSection);
        }

        public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
        {
            return getDataSectionable().getComponentForDataSection(taskObserver, dataSectionName);
        }

        public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
        {
            return getDataSectionable().getListComponentForDataSection(taskObserver, dataSectionName, list, indexIterator);
        }
    }
    
    static class ListDataSectionable implements NamedDataSectionable
    {
        private DataSectionable parentDataSectionable;
        private String listID;
        private int startIndex;
        private int endIndex;
        private int indexStep;
        
        public boolean equals(Object dataSectionable)
        {
            if (false == dataSectionable instanceof ListDataSectionable)  return false;
        
            ListDataSectionable listDataSectionable = (ListDataSectionable)dataSectionable;
            if (false == listDataSectionable.getParentDataSectionable().equals(parentDataSectionable))  return false;
            if (listDataSectionable.getStartIndex() != startIndex)  return false;
            if (listDataSectionable.getEndIndex() != endIndex)  return false;
            if (listDataSectionable.getIndexStep() != indexStep)  return false;
            return true;
        }
        
        public String getLabel()
        {
            if (-1 == endIndex)
                return listID + "[no elements]";

            if (startIndex == endIndex)
                return listID + "[" + String.valueOf(endIndex) + "]";
            return listID + "[" + String.valueOf(startIndex) + ".." + String.valueOf(endIndex) + "]";
        }

        private ListDataSectionable(DataSectionable parentDataSectionable, String listID, int startIndex, int endIndex, int indexStep)
        {
            this.parentDataSectionable = parentDataSectionable;
            this.listID = listID;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.indexStep = indexStep;
            if (startIndex == endIndex)
            {
                throw new RuntimeException("Should never reach here: startIndex=" + startIndex + ", endIndex=" + endIndex);
            }
        }

        public int getEndIndex()
        {
            return this.endIndex;
        }
        public int getIndexStep()
        {
            return this.indexStep;
        }
        public String getListID()
        {
            return this.listID;
        }
        public int getStartIndex()
        {
            return this.startIndex;
        }

        public int size()
        {
            return ((endIndex - startIndex) / indexStep) + 1;
        }

        public NamedDataSectionable getChild(int index) // ind = 2, start=100, end=199, step = 10  => [120..129, 1]
        {
            if (1 == indexStep)
            {
                return new ListItemDataSectionable(parentDataSectionable, listID, startIndex + index);
            }
            else
            {
                int childStartIndex = (index * indexStep) + startIndex;
                int childEndIndex = Math.min(endIndex, childStartIndex + (indexStep - 1));
                int childIndexStep = Math.max(1, indexStep / INDEX_STEP_COUNT);
                
                if (childStartIndex == childEndIndex)
                {
                    return new ListItemDataSectionable(parentDataSectionable, listID, childEndIndex);
                }

                return new ListDataSectionable(parentDataSectionable, listID, childStartIndex, childEndIndex, childIndexStep);
            }
        }
        
        public String toString()
        {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(hashCode());
            stringBuffer.append(":listID=" + listID);
            stringBuffer.append(",startIndex=" + String.valueOf(startIndex));
            stringBuffer.append(",endIndex=" + String.valueOf(endIndex));
            stringBuffer.append(",indexStep=" + String.valueOf(indexStep));
            return stringBuffer.toString();
        }
        
        public DataSection[] getStaticDataSections()
        {
            return parentDataSectionable.getStaticDataSections();
        }
        
        /**
         * @return list
         */
        public List getList()
        {
            DataSection childDataSection = getDataSectionForParentAndName(parentDataSectionable, listID);
            return (List)parentDataSectionable.getDataForDataSection(childDataSection);
        }
        
        public Object getData()
        {
            return parentDataSectionable.getData();
        }
        public Object getDataForDataSection(DataSection dataSection)
        {
            return parentDataSectionable.getDataForDataSection(dataSection);
        }
        public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
        {
            return parentDataSectionable.getComponentForDataSection(taskObserver, dataSectionName);
        }
        public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
        {
            // IMPLEMENT: probably need own list index iterator
            return parentDataSectionable.getListComponentForDataSection(taskObserver, dataSectionName, list, indexIterator);
        }
        public DataSectionable getParentDataSectionable()
        {
            return this.parentDataSectionable;
        }

        public NamedDataSectionable getDataSectionableForIndex(int index)
        {
            return getDataSectionableForParentNameAndIndex(parentDataSectionable, listID, index);
        }
    }
    
    public static NamedDataSectionable getDataSectionableForParentNameAndIndex(DataSectionable parentDataSectionable, String childSectionName, int index)
    {
        DataSection childDataSection = getDataSectionForParentAndName(parentDataSectionable, childSectionName);
        
        Class childDataSectionableClass = childDataSection.getDataSectionableClass();
        List childObject = (List)parentDataSectionable.getDataForDataSection(childDataSection);
        Object object = childObject.get(index);
        
        String sectionName = childSectionName + "[" + String.valueOf(index) + "]";
        if (null == childDataSectionableClass)
            return new LeafDataSectionable(object, sectionName);
        else return new NamedDataSectionableImpl(constructDataSectionableForNonListObject(childDataSectionableClass, object), sectionName);
    }

    public static DataSection getDataSectionForParentAndName(DataSectionable parentDataSectionable, String sectionName)
    {
        DataSection dataSectionArray[] = parentDataSectionable.getStaticDataSections();
        for (int index = 0; index < dataSectionArray.length; index++)
        {
            DataSection section = dataSectionArray[index];
            if (section.getDataSectionName() == sectionName)  return section;
        }
        
        return null;
    }
    
    public static DataSectionable constructDataSectionableForNonListObject(Class dataSectionableClass, Object object)
    {
        if (null == dataSectionableClass)  throw new RuntimeException("dataSectionableClass is null.");
        if (object instanceof List)  throw new RuntimeException("object is List.");
        
        Constructor[] constructorArray = dataSectionableClass.getConstructors();
        if (1 != constructorArray.length)
            throw new RuntimeException(String.valueOf(constructorArray.length) + " constructors found for class " + dataSectionableClass.getName());
        
        Object[] parameterArray = new Object[] { object };
        try
        {
            return (DataSectionable)constructorArray[0].newInstance(parameterArray);
        }
        catch (IllegalArgumentException exception)
        {
            throw new RuntimeException(exception.getMessage(), exception);
        }
        catch (InstantiationException exception)
        {
            throw new RuntimeException(exception.getMessage(), exception);
        }
        catch (IllegalAccessException exception)
        {
            throw new RuntimeException(exception.getMessage(), exception);
        }
        catch (InvocationTargetException exception)
        {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }
    
    static class HierarchicalDataSectionableTreeModel implements TreeModel
    {
        private String rootName;
        private DataSectionable rootDataSectionable;

        public HierarchicalDataSectionableTreeModel(String rootName, DataSectionable root)
        {
            super();
            this.rootName = rootName;
            this.rootDataSectionable = root;
        }

        public void valueForPathChanged(TreePath path, Object newValue)
        {
            // TODO Auto-generated method stub

        }
        
        public void addTreeModelListener(TreeModelListener l)
        {
            // TODO Auto-generated method stub
        }

        public void removeTreeModelListener(TreeModelListener l)
        {
            // TODO Auto-generated method stub
        }

        public Object getRoot()
        {
            return new NamedDataSectionableImpl(rootDataSectionable, rootName);
        }

        public int getChildCount(Object parentNode)
        {
            DataSectionable dataSectionable = ((DataSectionable)parentNode);
            if (dataSectionable instanceof LeafDataSectionable)
            {
                return 0;
            }
            else if (dataSectionable instanceof ListDataSectionable)
            {
                ListDataSectionable listDataSectionable = (ListDataSectionable)dataSectionable;
                return listDataSectionable.size();
            }
            
            if (null == dataSectionable.getStaticDataSections())  return 0;
           
            return dataSectionable.getStaticDataSections().length;
        }

        public boolean isLeaf(Object node)
        {
            if (null == node)  return true;
            
            return (0 == getChildCount(node));
        }

        private static DataSectionable getDataSectionableForParentAndName(DataSectionable parentDataSectionable, String childSectionName)
        {
            DataSection childDataSection = getDataSectionForParentAndName(parentDataSectionable, childSectionName);
            
            Object childObject = parentDataSectionable.getDataForDataSection(childDataSection);
            if (childObject instanceof List)
            {
                List list = (List)childObject;
                int startIndex = 0;
                int endIndex = list.size() - 1;
                
                if (startIndex == endIndex)
                    return new ListItemDataSectionable(parentDataSectionable, childSectionName, startIndex);

                int indexStep = 1;
                int displayedSize = list.size();
                while (displayedSize > INDEX_STEP_COUNT)
                {
                    displayedSize /= INDEX_STEP_COUNT;
                    indexStep *= INDEX_STEP_COUNT;
                }
                return new ListDataSectionable(parentDataSectionable, childSectionName, startIndex, endIndex, indexStep);
            }
            
            Class childDataSectionableClass = childDataSection.getDataSectionableClass();
            if (null == childDataSectionableClass)
                return new LeafDataSectionable(childObject, childSectionName);
            else return new NamedDataSectionableImpl(constructDataSectionableForNonListObject(childDataSectionableClass, childObject), childSectionName);
        }

        public Object getChild(Object parentNode, int index)
        {
            DataSectionable parentDataSectionable = ((DataSectionable)parentNode);
            
            if (parentDataSectionable instanceof ListDataSectionable)
            {
                ListDataSectionable listDataSectionable = (ListDataSectionable)parentDataSectionable;
                return listDataSectionable.getChild(index);
            }
            
            DataSection childDataSection = parentDataSectionable.getStaticDataSections()[index];
            
            return getDataSectionableForParentAndName(parentDataSectionable, childDataSection.getDataSectionName());
        }

        public int getIndexOfChild(Object parentNode, Object childNode)
        {
            DataSectionable parentDataSectionable = ((DataSectionable)parentNode);
            DataSectionable childDataSectionable = ((DataSectionable)childNode);
            
            DataSection dataSections[] = parentDataSectionable.getStaticDataSections();
            for (int index = 0; index < dataSections.length; index++)
            {
                DataSection childDataSection = parentDataSectionable.getStaticDataSections()[index];
                DataSectionable dataSectionable = getDataSectionableForParentAndName(parentDataSectionable, childDataSection.getDataSectionName());

                if (childDataSectionable.equals(dataSectionable))
                    return index;
            }
            return -1;
        }
    }

    class DataSectionTreeSelectionListener implements TreeSelectionListener
    {
        public void valueChanged(TreeSelectionEvent e)
        {
            JTree tree = (JTree) e.getSource();
            TreePath treepath = tree.getSelectionPath();
            if (null == treepath)  return;
            
            NamedDataSectionable parentDataSectionable = null;
            if (treepath.getPathCount() > 1)
            	parentDataSectionable = (NamedDataSectionable)treepath.getPathComponent(treepath.getPathCount() - 2);
            NamedDataSectionable namedDataSectionable = (NamedDataSectionable)treepath.getPathComponent(treepath.getPathCount() - 1);
            
            Object object = namedDataSectionable.getData();
            
            if (namedDataSectionable instanceof ListItemDataSectionable)
            {
                ListItemDataSectionable listItemDataSectionable = (ListItemDataSectionable)namedDataSectionable;
                displayList(listItemDataSectionable.getParentDataSectionable(), listItemDataSectionable.getListID(), listItemDataSectionable.getList(), listItemDataSectionable.getIndex(), listItemDataSectionable.getIndex());
                return;
            }
            else if (namedDataSectionable instanceof ListDataSectionable)
            {
                ListDataSectionable listDataSectionable = (ListDataSectionable)namedDataSectionable;

                displayList(listDataSectionable.getParentDataSectionable(), listDataSectionable.getListID(), listDataSectionable.getList(), listDataSectionable.getStartIndex(), listDataSectionable.getEndIndex());
                return;
            }
            else if (null == parentDataSectionable)
	        {
	            displayPanel.removeAll();
	            displayPanel.add(new JLabel("Nothing selected."));
	            displayPanel.getTopLevelAncestor().validate();
	            return;
	        }
            else
            {
//                if (object instanceof byte[])
//                {
//            		ByteDataTableControl bDTC = new ByteDataTableControl((byte[])object, 32, 0);
//                    displayPanel.removeAll();
//                    displayPanel.add(bDTC);
//                    displayPanel.getTopLevelAncestor().validate();
//                    return;
//                }
                
                DataSection dataSection;
                if (parentDataSectionable instanceof ListDataSectionable)
                {
                    ListDataSectionable listDataSectionable = (ListDataSectionable)parentDataSectionable;
                    dataSection = getDataSectionForParentAndName(parentDataSectionable, listDataSectionable.getListID());
                }
                else
                {
                    dataSection = getDataSectionForParentAndName(parentDataSectionable, namedDataSectionable.getLabel());
                }
                // IMPLEMENT: probably dump the above
                display(parentDataSectionable, dataSection.getDataSectionName());
            }
        }

        private JPanel displayPanel = null;

        public DataSectionTreeSelectionListener(JPanel displayPanel)
        {
            super();

            this.displayPanel = displayPanel;
        }

        private MonitoredTaskThread displayLodEntryThread = null;

        public void displayList(final DataSectionable finalizedDataSectionable, final String finalizedDataSectionName, final List list, final int listStartIndex, final int listEndIndex)
        {
            Runnable displayLodEntryRunnable = new Runnable()
            {
                public void run()
                {
                    if (multithreaded) displayLodEntryThread.taskProgress(finalizedDataSectionName, 0);
                    Component displayComponent = null;
                    try
                    {
                        displayComponent = finalizedDataSectionable.getListComponentForDataSection(displayLodEntryThread, finalizedDataSectionName, list, new SequenceNumberIterator(listStartIndex, listEndIndex));
                    }
                    catch (InterruptedException exception)
                    {
                        displayComponent = new JLabel(
                                "Error: User cancelled operation.");
                    }

                    displayPanel.removeAll();
                    displayPanel.add(displayComponent);
                    displayPanel.getTopLevelAncestor().validate();

                    if (multithreaded) displayLodEntryThread.taskProgress(finalizedDataSectionName, 1);
                }
            };

            if (multithreaded)
            {
                displayLodEntryThread = new MonitoredTaskThread(
                        progressDisplayer,
                        "Please wait.\nRendering data...", "startup",
                        displayLodEntryRunnable);
                displayLodEntryThread.start();
            }
            else
            {
                displayLodEntryRunnable.run();
            }
        }
        
        public void display(final DataSectionable finalizedDataSectionable, final String finalizedDataSectionName)
        {
            Runnable displayLodEntryRunnable = new Runnable()
            {
                public void run()
                {
                    if (multithreaded) displayLodEntryThread.taskProgress(finalizedDataSectionName, 0);
                    Component displayComponent = null;
                    try
                    {
                        displayComponent = finalizedDataSectionable.getComponentForDataSection(displayLodEntryThread, finalizedDataSectionName);
                    }
                    catch (InterruptedException exception)
                    {
                        displayComponent = new JLabel(
                                "Error: User cancelled operation.");
                    }

                    displayPanel.removeAll();
                    displayPanel.add(displayComponent);
                    displayPanel.getTopLevelAncestor().validate();

                    if (multithreaded)  displayLodEntryThread.taskProgress(finalizedDataSectionName, 1);
                }
            };

            if (multithreaded)
            {
                displayLodEntryThread = new MonitoredTaskThread(
                        progressDisplayer,
                        "Please wait.\nRendering data...", "startup",
                        displayLodEntryRunnable);
                displayLodEntryThread.start();
            }
            else
            {
                displayLodEntryRunnable.run();
            }
        }
    };

    protected DataSectionTree(String rootName, DataSectionable root, JPanel displayPanel, ProgressDisplayer progressDisplayer, boolean multithreaded)
    {
        this(rootName, root, displayPanel, progressDisplayer);
        this.multithreaded = multithreaded;
    };

    public DataSectionTree(String rootName, DataSectionable root, JPanel displayPanel, ProgressDisplayer progressDisplayer)
    {
        super(new HierarchicalDataSectionableTreeModel(rootName, root));
        
        this.progressDisplayer = progressDisplayer;

        this.rootName = rootName;
        this.root = root;
        
        this.setCellRenderer(new DataSectionRenderer());
        this.addTreeSelectionListener(new DataSectionTreeSelectionListener(displayPanel));

        popup = new JPopupMenu();

        compareWithExternalFileMenuItem = new JMenuItem(
                "Compare With External Resource");
        compareWithExternalFileMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                byte[] oldData = null;
                try
                {
                    // File selectedFile =
                    // this.selectFile(lodFileManager.getExtractionDirectory(),
                    // this);
                    File selectedFile = new File("z://file");
                    FileInputStream fileInputStream = new FileInputStream(
                            selectedFile);
                    oldData = new byte[(int) selectedFile.length()];
                    fileInputStream.read(oldData);
                }
                catch (FileNotFoundException exception)
                {
                    // TODO Auto-generated catch block
                    exception.printStackTrace();
                }
                catch (IOException exception1)
                {
                    // TODO Auto-generated catch block
                    exception1.printStackTrace();
                }

                byte newData[] = new byte[0];
                BinaryFileCompare.compare(System.err, oldData, newData);
            }
        });
        popup.add(compareWithExternalFileMenuItem);

        compareWithEachOtherMenuItem = new JMenuItem("Compare With Each Other");
        compareWithEachOtherMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JTree tree = (JTree) e.getSource();
                TreePath expandedSelectionPaths[] = expandedSelectionPaths(tree.getSelectionPaths());

                if (2 == expandedSelectionPaths.length)
                {
                    TreePath treepaths[] = tree.getSelectionPaths();
                    DataSection firstNode = (DataSection) expandedSelectionPaths[0].getLastPathComponent();
                    DataSection secondNode = (DataSection) expandedSelectionPaths[1].getLastPathComponent();
                    if (firstNode.getDataSectionClass() == secondNode.getDataSectionClass())
                    {
                        byte firstData[] = new byte[0];
                        byte secondData[] = new byte[0];
                        
                        BinaryFileCompare.compare(System.err, firstData, secondData);
                    }
                }
            }
        });
        popup.add(compareWithEachOtherMenuItem);

        this.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                maybeShowPopup(e);
            }

            public void mouseReleased(MouseEvent e)
            {
                if (false == maybeShowPopup(e))
                {
                    JTree tree = (JTree) e.getSource();
                    NamedDataSectionable node = (NamedDataSectionable) tree.getLastSelectedPathComponent();

                    if (null != node)
                    {
                    }
                    else
                    {
                    }
                }
            }

            private boolean maybeShowPopup(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    JTree tree = (JTree) e.getSource();
                    TreePath expandedSelectionPaths[] = expandedSelectionPaths(tree.getSelectionPaths());
                    
                    NamedDataSectionable namedDataSectionable = (NamedDataSectionable) tree.getLastSelectedPathComponent();
                    if (0 == expandedSelectionPaths.length)
                    {
                        compareWithExternalFileMenuItem.setEnabled(false);
                        compareWithEachOtherMenuItem.setEnabled(false);
                    }
                    else
                    {
                        boolean shouldAllowCompareWithEachOther = false;
                        if (2 == expandedSelectionPaths.length)
                        {
                            NamedDataSectionable firstNode = (NamedDataSectionable)expandedSelectionPaths[0].getLastPathComponent();
                            NamedDataSectionable secondNode = (NamedDataSectionable)expandedSelectionPaths[1].getLastPathComponent();
                            if (firstNode.getClass() == secondNode.getClass())
                            {
                                shouldAllowCompareWithEachOther = true;
                            }
                        }
                        
                        compareWithEachOtherMenuItem
                                .setEnabled(shouldAllowCompareWithEachOther);
                        compareWithExternalFileMenuItem.setEnabled(true);
                    }

                    popup.show(e.getComponent(), e.getX(), e.getY());
                    return true;
                }
                else
                    return false;
            }
        });
    }
    
    protected TreePath[] expandedSelectionPaths(TreePath selectedTreePaths[])
    {
        // Expand all ListDataSectionable paths
        List expandedTreePathList = new ArrayList(selectedTreePaths.length);
        for (int index = 0; index < selectedTreePaths.length; index++)
        {
            TreePath path = selectedTreePaths[index];
            NamedDataSectionable namedDataSectionable = (NamedDataSectionable) path.getLastPathComponent();
            if (namedDataSectionable instanceof ListDataSectionable)
            {
                ListDataSectionable listDataSectionable = (ListDataSectionable)namedDataSectionable;
                for (int listIndex = listDataSectionable.getStartIndex(); listIndex < listDataSectionable.getEndIndex(); listIndex++)
                {
                    expandedTreePathList.add(listDataSectionable.getChild(listIndex - listDataSectionable.getStartIndex()));
                }
            }
            else
            {
                expandedTreePathList.add(path);
            }
        }

        TreePath expandedSelectionPaths[] = new TreePath[expandedTreePathList.size()];
        for (int i = 0; i < expandedTreePathList.size(); i++)
        {
            expandedSelectionPaths[i] = (TreePath)expandedTreePathList.get(i);
        }
        
        return expandedSelectionPaths;
    }
}

