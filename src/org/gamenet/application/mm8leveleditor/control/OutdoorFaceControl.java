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

import java.awt.Component;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.annotation.gui.CollapsablePanel;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.OutdoorFace;
import org.gamenet.swing.controls.ByteDataTableControl;
import org.gamenet.swing.controls.ShortTextField;
import org.gamenet.swing.controls.ShortValueHolder;

public class OutdoorFaceControl extends JPanel
{
    private OutdoorFace face = null;
    
    public OutdoorFaceControl(OutdoorFace srcFace)
    {
        super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));

        this.face = srcFace;

	    CollapsablePanel.IndirectDataSource facetsIndirectDataSource = new CollapsablePanel.IndirectDataSource()
        {
	        public Component getComponent()
            {
	    	    return makeNonStretchedPanelFor(new ByteDataTableControl(face.getFacetArray(), face.getFacetArray().length, face.getOffset()));
	        }
        };
	    this.add(makeNonStretchedPanelFor(new CollapsablePanel("Facets", facetsIndirectDataSource, true)));

	    final int vertexNumberList[] = face.getVertexNumberList();
	    JPanel vertexListPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("IntVertex List: "));
        for (int vertexNumberIndex = 0; vertexNumberIndex < vertexNumberList.length; ++vertexNumberIndex)
        {
            final int finalVertexNumberIndex = vertexNumberIndex;
		    JFormattedTextField vertexListTextField = new JFormattedTextField(new Integer(vertexNumberList[vertexNumberIndex]));
		    vertexListTextField.setColumns(3);
		    vertexListTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                    {
                        public void propertyChange(PropertyChangeEvent e)
                        {
                            vertexNumberList[finalVertexNumberIndex] = (((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                        }
                    });
		    vertexListPanel.add(vertexListTextField);
        }
        this.add(vertexListPanel);
        


        JPanel bitmapPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Ordering: "));
        bitmapPanel.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return face.getOrdering(); }
            public void setValue(short value) { face.setOrdering(value); }
        }));

        bitmapPanel.add(new JLabel("Bitmap: "));
        JFormattedTextField bitmapTextField = new JFormattedTextField(face.getBitmapName());
        bitmapTextField.setColumns(10);
        bitmapTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        face.setBitmapName(((String)((JFormattedTextField)e.getSource()).getValue()));
                    }
                });
        bitmapPanel.add(bitmapTextField);
        this.add(bitmapPanel);
    }

    public Object getFace()
    {
        return face;
    }
    
    protected JPanel makeNonStretchedPanelFor(Component component)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        if (null != component)  panel.add(component);
        return panel;
    }
    
}
