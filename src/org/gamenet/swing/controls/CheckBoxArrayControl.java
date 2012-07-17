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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;


public class CheckBoxArrayControl extends JPanel
{
    private StateArrayValueHolder stateArrayValueHolder = null;
    
    public CheckBoxArrayControl(StateArrayValueHolder srcStateArrayValueHolder)
    {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        this.stateArrayValueHolder = srcStateArrayValueHolder;
        
        for (int index = stateArrayValueHolder.getStartIndex(); index <= stateArrayValueHolder.getEndIndex(); ++index)
        {
            final int finalizedIndex = index;
            JCheckBox checkbox = new JCheckBox(stateArrayValueHolder.getValueLabel(index));
            checkbox.setSelected(stateArrayValueHolder.getValue(index));
            this.add(checkbox);
            
            checkbox.addItemListener(new ItemListener()
            {
                public void itemStateChanged(ItemEvent e)
                {
                    if (e.getStateChange() == ItemEvent.DESELECTED)
                    {
                        stateArrayValueHolder.setValue(false, finalizedIndex);
                    }
                    else if (e.getStateChange() == ItemEvent.SELECTED)
                    {
                        stateArrayValueHolder.setValue(true, finalizedIndex);
                    }
                }
            });
        }
    }

    public StateArrayValueHolder getStateArrayValueHolder()
    {
        return stateArrayValueHolder;
    }
}
