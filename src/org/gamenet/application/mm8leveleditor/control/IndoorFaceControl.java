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

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.annotation.gui.CollapsablePanel;
import org.gamenet.application.mm8leveleditor.data.mm6.indoor.IndoorFace;
import org.gamenet.swing.controls.ByteDataTableControl;
import org.gamenet.swing.controls.IntTextField;
import org.gamenet.swing.controls.IntValueHolder;
import org.gamenet.swing.controls.ShortTextField;
import org.gamenet.swing.controls.ShortValueHolder;
import org.gamenet.swing.controls.StringTextField;
import org.gamenet.swing.controls.StringValueHolder;

public class IndoorFaceControl extends JPanel
{
    private IndoorFace indoorFace = null;
    
    public IndoorFaceControl(IndoorFace srcIndoorFace)
    {
        super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));

        this.indoorFace = srcIndoorFace;

	    CollapsablePanel.IndirectDataSource data1IndirectDataSource = new CollapsablePanel.IndirectDataSource()
        {
	        public Component getComponent()
            {
	    	    return makeNonStretchedPanelFor(new ByteDataTableControl(indoorFace.getFacetData(), indoorFace.getFacetData().length, 0));
	        }
        };
	    this.add(makeNonStretchedPanelFor(new CollapsablePanel("Facets", data1IndirectDataSource, true)));

        final int vertexNumberList[] = indoorFace.getVertexIndexArray();
	    JPanel vertexListPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Vertex List: "));
        for (int vertexNumberIndex = 0; vertexNumberIndex < vertexNumberList.length; ++vertexNumberIndex)
        {
            final int finalVertexNumberIndex = vertexNumberIndex;
            vertexListPanel.add(new IntTextField(3, new IntValueHolder() {
                public int getValue() { return vertexNumberList[finalVertexNumberIndex]; }
                public void setValue(int value) { vertexNumberList[finalVertexNumberIndex] = value; }
            }));
        }
        this.add(vertexListPanel);
        

	    final short xDisplacementArray[] = indoorFace.getXDisplacementArray();
	    JPanel xDisplacementPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("X Displacement List: "));
        for (int vertexNumberIndex = 0; vertexNumberIndex < xDisplacementArray.length; ++vertexNumberIndex)
        {
            final int finalVertexNumberIndex = vertexNumberIndex;
            xDisplacementPanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return xDisplacementArray[finalVertexNumberIndex]; }
                public void setValue(short value) { xDisplacementArray[finalVertexNumberIndex] = value; }
            }));
        }
        this.add(xDisplacementPanel);
        
	    final short yDisplacementArray[] = indoorFace.getYDisplacementArray();
	    JPanel yDisplacementPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Y Displacement List: "));
        for (int vertexNumberIndex = 0; vertexNumberIndex < yDisplacementArray.length; ++vertexNumberIndex)
        {
            final int finalVertexNumberIndex = vertexNumberIndex;
            yDisplacementPanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return yDisplacementArray[finalVertexNumberIndex]; }
                public void setValue(short value) { yDisplacementArray[finalVertexNumberIndex] = value; }
            }));
        }
        this.add(yDisplacementPanel);

	    final short zDisplacementArray[] = indoorFace.getZDisplacementArray();
	    JPanel zDisplacementPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Z Displacement List: "));
        for (int vertexNumberIndex = 0; vertexNumberIndex < zDisplacementArray.length; ++vertexNumberIndex)
        {
            final int finalVertexNumberIndex = vertexNumberIndex;
            zDisplacementPanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return zDisplacementArray[finalVertexNumberIndex]; }
                public void setValue(short value) { zDisplacementArray[finalVertexNumberIndex] = value; }
            }));
        }
        this.add(zDisplacementPanel);

	    final short uTextureArray[] = indoorFace.getUTextureArray();
	    JPanel uTexturePanel = (JPanel)makeNonStretchedPanelFor(new JLabel("U Texture List: "));
        for (int vertexNumberIndex = 0; vertexNumberIndex < uTextureArray.length; ++vertexNumberIndex)
        {
            final int finalVertexNumberIndex = vertexNumberIndex;
            uTexturePanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return uTextureArray[finalVertexNumberIndex]; }
                public void setValue(short value) { uTextureArray[finalVertexNumberIndex] = value; }
            }));
        }
        this.add(uTexturePanel);
        
	    final short vTextureArray[] = indoorFace.getVTextureArray();
	    JPanel vTexturePanel = (JPanel)makeNonStretchedPanelFor(new JLabel("V Texture List: "));
        for (int vertexNumberIndex = 0; vertexNumberIndex < vTextureArray.length; ++vertexNumberIndex)
        {
            final int finalVertexNumberIndex = vertexNumberIndex;
            vTexturePanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return vTextureArray[finalVertexNumberIndex]; }
                public void setValue(short value) { vTextureArray[finalVertexNumberIndex] = value; }
            }));
        }
        this.add(vTexturePanel);
        
        JPanel bitmapPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Bitmap: "));
        bitmapPanel.add(new StringTextField(new StringValueHolder() {
            public String getValue() { return indoorFace.getBitmapName(); }
            public void setValue(String value) { indoorFace.setBitmapName(value); }
            public int getMaxLength() { return 0; }
        }));
        this.add(bitmapPanel);
    }

    public Object getIndoorFace()
    {
        return indoorFace;
    }
    
    protected JPanel makeNonStretchedPanelFor(Component component)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        if (null != component)  panel.add(component);
        return panel;
    }
    
}
