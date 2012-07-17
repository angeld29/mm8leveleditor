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

import org.gamenet.application.mm8leveleditor.data.mm6.Creature;
import org.gamenet.swing.controls.Vertex3DTextFieldPanel;

public class CreatureControl extends JPanel
{
    private Creature creature = null;
    
    public CreatureControl(Creature srcCreature)
    {
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.creature = srcCreature;

        this.add(new JLabel("Name:"));
        JFormattedTextField creatureNameTextField = new JFormattedTextField(creature.getCreatureMouseOverName());
        creatureNameTextField.setColumns(12);
        creatureNameTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        creature.setCreatureMouseOverName((String)((JFormattedTextField)e.getSource()).getValue());
                    }
                });
        this.add(creatureNameTextField);

        this.add(new JLabel("HP:"));
        JFormattedTextField hpTextField = new JFormattedTextField(new Integer(creature.getCurrentHitPoints()));
        hpTextField.setColumns(5);
        hpTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        creature.setCurrentHitPoints(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        this.add(hpTextField);

        this.add(new JLabel("Coords:"));
	    this.add(new Vertex3DTextFieldPanel(creature));
    }

    public Object getCreature()
    {
        return creature;
    }
}
