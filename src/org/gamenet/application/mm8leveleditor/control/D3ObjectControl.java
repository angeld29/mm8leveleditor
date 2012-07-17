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
package org.gamenet.application.mm8leveleditor.control;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.annotation.gui.CollapsablePanel;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.D3Object;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.OutdoorFace;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.IntVertex;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.ComponentArrayPanel;
import org.gamenet.swing.controls.Vertex3DValueHolder;
import org.gamenet.swing.controls.Vertex3DTextFieldPanel;

public class D3ObjectControl extends JPanel
{
    private D3Object d3Object = null;
    
    public D3ObjectControl(D3Object srcD3Object)
    {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));

        this.d3Object = srcD3Object;


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
        
		this.add(wrappingLabelAndFieldPanel);

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
	              		    
	    this.add(vertexMinAttributePanel);

	    CollapsablePanel.IndirectDataSource vertexesIndirectDataSource = new CollapsablePanel.IndirectDataSource()
        {
	        public Component getComponent()
            {
                try
                {
                    final List vertexList = d3Object.getVertexList();

                    JPanel vertexesPanel = new JPanel();
                    vertexesPanel.setLayout(new BoxLayout(vertexesPanel, BoxLayout.Y_AXIS));
                    final JLabel vertexCountLabel = new JLabel("# of Vertexs: " + String.valueOf(vertexList.size()));
                    vertexesPanel.add(makeNonStretchedPanelFor(vertexCountLabel));

                    List vertexControlList = new ArrayList(vertexList.size());
                    for (int vertexIndex = 0; vertexIndex < vertexList.size(); ++vertexIndex)
                    {
                        IntVertex vertex = (IntVertex)vertexList.get(vertexIndex);
                        vertexControlList.add(new VertexControl(vertex));
                    }
                    vertexesPanel.add(makeNonStretchedPanelFor(new ComponentArrayPanel(vertexControlList, new ComponentArrayPanel.ComponentDataSource()
                            {
                                public Component createComponent(int componentIndex)
                                {
                                    IntVertex newVertex = createNewVertex();
                                    return new VertexControl(newVertex);
                                }
                                
                                public void fireComponentAdded(int componentIndex, Component component)
                                {
                                    VertexControl vertexControl = (VertexControl)component;
                                    vertexList.add(componentIndex, vertexControl.getVertex());
                                    vertexCountLabel.setText("# of Vertexs: " + String.valueOf(vertexList.size()));
                                }
                                public void fireComponentDeleted(int componentIndex, Component component)
                                {
                                    VertexControl vertexControl = (VertexControl)component;
                                    vertexList.remove(componentIndex);
                                    vertexCountLabel.setText("# of Vertexs: " + String.valueOf(vertexList.size()));
                                }
                                public void fireComponentMovedUp(int componentIndex, Component component)
                                {
                                    IntVertex vertex = (IntVertex)vertexList.remove(componentIndex);
                                    vertexList.add(componentIndex - 1, vertex);
                                }
                                public void fireComponentMovedDown(int componentIndex, Component component)
                                {
                                    IntVertex vertex = (IntVertex)vertexList.remove(componentIndex);
                                    vertexList.add(componentIndex + 1, vertex);
                                }
                            })));
                    
                    return vertexesPanel;
                }
                catch (InterruptedException exception)
                {
                    // IMPLEMENT: replace this with a progress monitor
                    exception.printStackTrace();
                    
                    return new JLabel("Error: User cancelled operation.");
                }
            }
        };
	    this.add(makeNonStretchedPanelFor(new CollapsablePanel("Vertexes", vertexesIndirectDataSource, true)));
	    

	    CollapsablePanel.IndirectDataSource facesIndirectDataSource = new CollapsablePanel.IndirectDataSource()
        {
            public Component getComponent()
            {
                try
                {
                    final List faceList = d3Object.getFacetList();

                    JPanel facesPanel = new JPanel();
                    facesPanel.setLayout(new BoxLayout(facesPanel, BoxLayout.Y_AXIS));
                    final JLabel faceCountLabel = new JLabel("# of Faces: " + String.valueOf(faceList.size()));
                    facesPanel.add(makeNonStretchedPanelFor(faceCountLabel));

                    List OutdoorFaceControlList = new ArrayList(faceList.size());
                    for (int faceIndex = 0; faceIndex < faceList.size(); ++faceIndex)
                    {
                        OutdoorFace face = (OutdoorFace)faceList.get(faceIndex);
                        OutdoorFaceControlList.add(new OutdoorFaceControl(face));
                    }
                    facesPanel.add(makeNonStretchedPanelFor(new ComponentArrayPanel(OutdoorFaceControlList, new ComponentArrayPanel.ComponentDataSource()
                            {
                                public Component createComponent(int componentIndex)
                                {
                                    OutdoorFace newFace = createNewFace();
                                    return new OutdoorFaceControl(newFace);
                                }
                                
                                public void fireComponentAdded(int componentIndex, Component component)
                                {
                                    OutdoorFaceControl OutdoorFaceControl = (OutdoorFaceControl)component;
                                    faceList.add(componentIndex, OutdoorFaceControl.getFace());
                                    faceCountLabel.setText("# of Faces: " + String.valueOf(faceList.size()));
                                }
                                public void fireComponentDeleted(int componentIndex, Component component)
                                {
                                    OutdoorFaceControl OutdoorFaceControl = (OutdoorFaceControl)component;
                                    faceList.remove(componentIndex);
                                    faceCountLabel.setText("# of Faces: " + String.valueOf(faceList.size()));
                                }
                                public void fireComponentMovedUp(int componentIndex, Component component)
                                {
                                    OutdoorFace face = (OutdoorFace)faceList.remove(componentIndex);
                                    faceList.add(componentIndex - 1, face);
                                }
                                public void fireComponentMovedDown(int componentIndex, Component component)
                                {
                                    OutdoorFace face = (OutdoorFace)faceList.remove(componentIndex);
                                    faceList.add(componentIndex + 1, face);
                                }
                            })));
                    
            		ComparativeTableControl facetCBDTC = new ComparativeTableControl(OutdoorFace.getOffsetList(), OutdoorFace.getComparativeDataSource(faceList));
            		facesPanel.add(facetCBDTC);

            		return facesPanel;
                }
                catch (InterruptedException exception)
                {
                    // IMPLEMENT: replace this with a progress monitor
                    exception.printStackTrace();
                    
                    return new JLabel("Error: User cancelled operation.");
                }
            }
        };	    
	    this.add(makeNonStretchedPanelFor(new CollapsablePanel("Faces", facesIndirectDataSource, true)));

    }

    public Object getD3Object()
    {
        return d3Object;
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
    
    protected JPanel makeNonStretchedPanelFor(Component component)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        if (null != component)  panel.add(component);
        return panel;
    }
}
