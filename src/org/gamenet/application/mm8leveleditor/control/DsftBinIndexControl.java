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
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gamenet.swing.controls.IntTextField;
import org.gamenet.swing.controls.IntValueHolder;

public class DsftBinIndexControl extends JPanel
{
    private List list;
    private int index;
    
    public DsftBinIndexControl(List listSrc, int indexSrc)
    {
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.index = indexSrc;
        this.list = listSrc;
        
        this.add(new JLabel("Index #" + index + ":"));
        this.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return ((Integer)list.get(index)).intValue(); }
            public void setValue(int value) {
                list.remove(index);
                list.add(index, new Integer(value));
            }
        }));
    }

    public List getList()
    {
        return list;
    }

    public int getIndex()
    {
        return index;
    }
}
