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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.gamenet.application.mm8leveleditor.control.OutdoorFaceControl;
import org.gamenet.application.mm8leveleditor.control.VertexControl;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.D3Object;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.IntVertex;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.OutdoorFace;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.DataSection;
import org.gamenet.swing.controls.DataSectionable;
import org.gamenet.swing.controls.Vertex3DTextFieldPanel;
import org.gamenet.swing.controls.Vertex3DValueHolder;
import org.gamenet.util.TaskObserver;

public class D3ObjectDataSectionable extends BaseDataSectionable implements DataSectionable
{
    private D3Object d3Object = null;
    
    public D3ObjectDataSectionable(D3Object srcD3Object)
    {
        super();
        
        this.d3Object = srcD3Object;
    }

    public Object getData()
    {
        return d3Object;
    }

    public Component getInfoPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Info", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getInfoPanel() was interrupted.");

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        JFormattedTextField name1TextField = new JFormattedTextField(d3Object.getName1());
        name1TextField.setColumns(32);
        JFormattedTextField name2TextField = new JFormattedTextField(d3Object.getName2());
        name2TextField.setColumns(36);
      
        name1TextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        d3Object.setName1((String)((JFormattedTextField)e.getSource()).getValue());
                    }
                });
                
        name2TextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        d3Object.setName2((String)((JFormattedTextField)e.getSource()).getValue());
                    }
                });

        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(new JLabel("Name1: "));
        labelPane.add(new JLabel("Name2: "));
        labelPane.add(new JLabel("Offset: "));
        labelPane.add(new JLabel("# of vertexes: "));
        labelPane.add(new JLabel("Vertexes offset: "));
        labelPane.add(new JLabel("# of faces: "));
        labelPane.add(new JLabel("Faces offset: "));

        //Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(name1TextField);
        fieldPane.add(name2TextField);
        fieldPane.add(new JLabel(String.valueOf(d3Object.getOffset())));
        fieldPane.add(new JLabel(String.valueOf(d3Object.getVertexList().size())));
        fieldPane.add(new JLabel(String.valueOf(d3Object.getVertexesOffset())));
        fieldPane.add(new JLabel(String.valueOf(d3Object.getFacetList().size())));
        fieldPane.add(new JLabel(String.valueOf(d3Object.getFacesOffset())));

        //Put the panels in this panel, labels on left,
        //text fields on right.
        JPanel labelAndFieldPanel = new JPanel(new BorderLayout());
        labelAndFieldPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        labelAndFieldPanel.add(labelPane, BorderLayout.CENTER);
        labelAndFieldPanel.add(fieldPane, BorderLayout.LINE_END);

        JPanel wrappingLabelAndFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrappingLabelAndFieldPanel.add(labelAndFieldPanel);
        
        infoPanel.add(wrappingLabelAndFieldPanel);

        JPanel vertexMinAttributePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        vertexMinAttributePanel.add(new JLabel("IntVertex Minimum ("));
	    
        vertexMinAttributePanel.add(new Vertex3DTextFieldPanel(new Vertex3DValueHolder()
        {
            public int getX()
            {
                return d3Object.getXMin();
            }

            public void setX(int x)
            {
                d3Object.setXMin(x);
            }

            public int getY()
            {
                return d3Object.getYMin();
            }

            public void setY(int y)
            {
                d3Object.setYMin(y);
            }

            public int getZ()
            {
                return d3Object.getZMin();
            }

            public void setZ(int z)
            {
                d3Object.setZMin(z);
            }
        }));

        JFormattedTextField xMinTextField = new JFormattedTextField(new Integer(d3Object.getXMin()));
        xMinTextField.setColumns(5);
        xMinTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        d3Object.setXMin(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        vertexMinAttributePanel.add(xMinTextField);
        vertexMinAttributePanel.add(new JLabel(","));

        JFormattedTextField yMinTextField = new JFormattedTextField(new Integer(d3Object.getYMin()));
        yMinTextField.setColumns(5);
        yMinTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        d3Object.setYMin(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        vertexMinAttributePanel.add(yMinTextField);
        vertexMinAttributePanel.add(new JLabel(","));

        JFormattedTextField heightMinTextField = new JFormattedTextField(new Integer(d3Object.getZMin()));
        heightMinTextField.setColumns(3);
        heightMinTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        d3Object.setZMin(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        vertexMinAttributePanel.add(heightMinTextField);
        vertexMinAttributePanel.add(new JLabel(")"));
	              		    
        infoPanel.add(vertexMinAttributePanel);
        
        return infoPanel;
    }

    protected Component getUnknownsPanel(TaskObserver taskObserver) throws InterruptedException
    {
        JPanel unknownsPanel = new JPanel();
        unknownsPanel.setLayout(new BoxLayout(unknownsPanel, BoxLayout.Y_AXIS));
        unknownsPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        final ComparativeTableControl dataComparativeByteDataTableControl;
        dataComparativeByteDataTableControl = new ComparativeTableControl(D3Object.getOffsetList(),
                D3Object.getComparativeDataSource(Collections.singletonList(d3Object)));

        unknownsPanel.add(makeNonStretchedPanelFor(new JLabel(" D3Object Unknowns: ")));
        unknownsPanel.add(dataComparativeByteDataTableControl);
		
        return unknownsPanel;
    }

    public IntVertex createNewVertex()
    {
        int x = 0;
        int y = 0;
        int z = 0;
        return new IntVertex(x, y, z);
    }
    
    public OutdoorFace createNewFace()
    {
        short ordering = 0;
        String bitmapName = "";
        return new OutdoorFace(ordering, bitmapName);
    }
    
    public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
    {
        if (DATA_SECTION_INFO == dataSectionName) { return getInfoPanel(taskObserver); }
        else if (DATA_SECTION_UNKNOWNS == dataSectionName) { return getUnknownsPanel(taskObserver); }
        return super.getComponent(dataSectionName, taskObserver);
    }

    public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
    {
        return super.getListComponent(dataSectionName, taskObserver, list, indexIterator);
    }
    
    public static final String DATA_SECTION_INFO = "Info";
    public static final String DATA_SECTION_VERTEXES = "Vertexes";
    public static final String DATA_SECTION_FACES = "Faces";
    public static final String DATA_SECTION_UNKNOWNS = "Unknowns";

    public static DataSection[] getDataSections()
    {
        return new DataSection[] {
                new DataSection(DATA_SECTION_INFO),
                new DataSection(DATA_SECTION_VERTEXES, IntVertex.class, OutdoorVertexDataSectionable.class),
                new DataSection(DATA_SECTION_FACES, OutdoorFace.class, OutdoorFaceDataSectionable.class),
                new DataSection(DATA_SECTION_UNKNOWNS)
         };
    }
    
    public DataSection[] getStaticDataSections() { return getDataSections(); }

    public Object getDataForDataSection(DataSection dataSection)
    {
        if (DATA_SECTION_INFO == dataSection.getDataSectionName())
        {
            return null;
        }
        else if (DATA_SECTION_VERTEXES == dataSection.getDataSectionName())
        {
            return d3Object.getVertexList();
        }
        else if (DATA_SECTION_FACES == dataSection.getDataSectionName())
        {
            return d3Object.getFacetList();
        }
        else if (DATA_SECTION_UNKNOWNS == dataSection.getDataSectionName())
        {
            return d3Object.getRemainingData();
        }
        else throw new IllegalStateException("No data sections: " + dataSection);
    }

    public DataTypeInfo getDataTypeInfo(String dataSectionName)
    {
        if (dataSectionName == DATA_SECTION_VERTEXES) { return vertexDataTypeInfo; }
        if (dataSectionName == DATA_SECTION_FACES) { return facetDataTypeInfo; }
        else throw new IllegalStateException("No data sections: " + dataSectionName);
    }
    
    private DataTypeInfo vertexDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Vertex"; }
        public String getDataTypeNamePlural() 	{ return "Vertexes"; }
        public List getDataList() 				{ return d3Object.getVertexList(); }
        public List getOffsetList() 			{ return null; }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return null; }
        public Component getDataControl(int dataIndex) {
            									return new VertexControl((IntVertex)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((VertexControl)component).getVertex()); }
        public Component createNewDataControl() {
            									return new VertexControl(createNewVertex()); }
    };
    
    private DataTypeInfo facetDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Facet"; }
        public String getDataTypeNamePlural() 	{ return "Facets"; }
        public List getDataList() 				{ return d3Object.getFacetList(); }
        public List getOffsetList() 			{ return OutdoorFace.getOffsetList(); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return OutdoorFace.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new OutdoorFaceControl((OutdoorFace)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((OutdoorFaceControl)component).getFace()); }
        public Component createNewDataControl() {
            									return new OutdoorFaceControl(createNewFace()); }
    };
    
}
