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

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;


public class IntTextFieldArrayControl extends JPanel
{
    private int intArray[] = null;
    
    public IntTextFieldArrayControl(int srcIntArray[], String fieldName)
    {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        this.intArray = srcIntArray;
        
        for (int index = 0; index < intArray.length; ++index)
        {
            JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            this.add(controlPanel);

            controlPanel.add(new JLabel(fieldName + " #" + String.valueOf(index + 1) + ":"));

            final int finalizedIndex = index;
            IntTextField intTextField = new IntTextField(new IntValueHolder()
            {
                public int getValue() { return intArray[finalizedIndex]; }
                public void setValue(int value) { intArray[finalizedIndex] = value; }
            });
            controlPanel.add(intTextField);
        }
    }

    public int[] getIntArray()
    {
        return intArray;
    }
}
