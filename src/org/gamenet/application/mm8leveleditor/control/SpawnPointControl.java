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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.gamenet.application.mm8leveleditor.data.mm6.SpawnPoint;
import org.gamenet.swing.controls.AbstractStringListCellRenderer;
import org.gamenet.swing.controls.IntTextField;
import org.gamenet.swing.controls.IntValueHolder;
import org.gamenet.swing.controls.Vertex3DTextFieldPanel;

public class SpawnPointControl extends JPanel
{
    private SpawnPoint monster = null;
    
    public SpawnPointControl(SpawnPoint srcMonster)
    {
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.monster = srcMonster;
        
        this.add(new JLabel("Coords:"));
        this.add(new Vertex3DTextFieldPanel(monster));
      
        this.add(new JLabel("Radius:"));
        this.add(new IntTextField(2, new IntValueHolder()
        {
            public int getValue() { return monster.getRadius(); }
            public void setValue(int value) { monster.setRadius(value); }
        }));
      
        this.add(new JLabel("SpawnPoint Class:"));
		JComboBox monsterClassSelector = new JComboBox(SpawnPoint.getMonsterClassValues());
		this.add(monsterClassSelector);
		monsterClassSelector.setSelectedIndex(SpawnPoint.getMonsterClassIndexForValue(monster.getMonsterClass()));
         
        ListCellRenderer monsterClassSelectorRenderer = new AbstractStringListCellRenderer()
        {
            protected String getStringForValue(Object value)
            {
                return SpawnPoint.getMonsterClassLabelForObject(value);
            }
        };
        monsterClassSelector.setRenderer(monsterClassSelectorRenderer);

        monsterClassSelector.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
                    JComboBox cb = (JComboBox)e.getSource();
			        Object item = cb.getSelectedItem();
                    monster.setMonsterClassForItem(item);
			    }
			}
		);
    }

    public Object getMonster()
    {
        return monster;
    }
}
