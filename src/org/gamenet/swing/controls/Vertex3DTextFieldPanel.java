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

import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Vertex3DTextFieldPanel extends JPanel
{
    public Vertex3DTextFieldPanel(final Vertex3DValueHolder vertexDataSource)
    {
        super(new FlowLayout(FlowLayout.LEFT));

        final JLabel hexX = new JLabel(Integer.toHexString(vertexDataSource.getX()));
        final JLabel hexY = new JLabel(Integer.toHexString(vertexDataSource.getY()));
        final JLabel hexZ = new JLabel(Integer.toHexString(vertexDataSource.getZ()));
        
        this.add(new JLabel("("));
        JFormattedTextField xTextField = new JFormattedTextField(new Integer(vertexDataSource.getX()));
        xTextField.setColumns(5);
        xTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        vertexDataSource.setX(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                        hexX.setText(Integer.toHexString(vertexDataSource.getX()));
                    }
                });
        this.add(xTextField);
        this.add(new JLabel(","));

        JFormattedTextField yTextField = new JFormattedTextField(new Integer(vertexDataSource.getY()));
        yTextField.setColumns(5);
        yTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        vertexDataSource.setY(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                        hexY.setText(Integer.toHexString(vertexDataSource.getY()));
                    }
                });
        this.add(yTextField);
        this.add(new JLabel(","));

        JFormattedTextField zTextField = new JFormattedTextField(new Integer(vertexDataSource.getZ()));
        zTextField.setColumns(5);
        zTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        vertexDataSource.setZ(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                        hexZ.setText(Integer.toHexString(vertexDataSource.getZ()));
                    }
                });
        this.add(zTextField);
        this.add(new JLabel(")"));

        this.add(new JLabel("("));
        this.add(hexX);
        this.add(new JLabel(","));
        this.add(hexY);
        this.add(new JLabel(","));
        this.add(hexZ);
        this.add(new JLabel(")"));

    }
}