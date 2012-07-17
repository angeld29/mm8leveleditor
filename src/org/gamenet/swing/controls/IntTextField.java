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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;


public class IntTextField extends JFormattedTextField implements PropertyChangeListener
{
    private IntValueHolder intValueHolder = null;
    
    public IntTextField(IntValueHolder srcIntValueHolder)
    {
        this(5, srcIntValueHolder);
    }

    public IntTextField(int columns, IntValueHolder srcIntValueHolder)
    {
        super(new Integer(srcIntValueHolder.getValue()));
        
        this.intValueHolder = srcIntValueHolder;
        
        this.setColumns(columns);
        this.addPropertyChangeListener("value", this);
    }

    public void propertyChange(PropertyChangeEvent e)
    {
        intValueHolder.setValue(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
    }

    public IntValueHolder getIntValueHolder()
    {
        return intValueHolder;
    }
}
