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

import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gamenet.application.mm8leveleditor.data.mm6.Item;
import org.gamenet.swing.controls.Vertex3DTextFieldPanel;

public class ItemControl extends JPanel
{
    private Item item = null;
    
    public ItemControl(Item srcItem)
    {
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.item = srcItem;

        JFormattedTextField itemIDTextField = new JFormattedTextField(new Integer(item.getItemNumber()));
        itemIDTextField.setColumns(5);
        itemIDTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        item.setItemNumber(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });

        JFormattedTextField stdMagicClassTextField = new JFormattedTextField(new Integer(item.getStandardMagicClass()));
        stdMagicClassTextField.setColumns(3);
        stdMagicClassTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        item.setStandardMagicClass(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });

        JFormattedTextField stdMagicBonusTextField = new JFormattedTextField(new Integer(item.getStandardMagicBonus()));
        stdMagicBonusTextField.setColumns(3);
        stdMagicBonusTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        item.setStandardMagicBonus(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });

        JFormattedTextField specialMagicClassTextField = new JFormattedTextField(new Integer(item.getSpecialMagicClass()));
        specialMagicClassTextField.setColumns(3);
        specialMagicClassTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        item.setSpecialMagicClass(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });

        JFormattedTextField chargesTextField = new JFormattedTextField(new Integer(item.getCharges()));
        chargesTextField.setColumns(3);
        chargesTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        item.setCharges(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        
        this.add(new JLabel("Coords:"));
        this.add(new Vertex3DTextFieldPanel(item));

        this.add(new JLabel("ID:"));
        this.add(itemIDTextField);
        
        this.add(new JLabel("std magic class:"));
        this.add(stdMagicClassTextField);
        
        this.add(new JLabel("std magic bonus:"));
        this.add(stdMagicBonusTextField);
        
        this.add(new JLabel("special magic class:"));
        this.add(specialMagicClassTextField);
        
        this.add(new JLabel("charges:"));
        this.add(chargesTextField);
    }

    public Object getItem()
    {
        return item;
    }
}
