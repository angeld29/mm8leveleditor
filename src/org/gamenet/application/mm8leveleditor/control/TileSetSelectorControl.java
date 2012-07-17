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

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.TileSetSelector;
import org.gamenet.swing.controls.ShortTextField;
import org.gamenet.swing.controls.ShortValueHolder;

public class TileSetSelectorControl extends JPanel
{
    private TileSetSelector tileSetSelector = null;
    
    public TileSetSelectorControl(TileSetSelector srcTileSetSelector)
    {
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.tileSetSelector = srcTileSetSelector;
        
        this.add(new JLabel("DTile ID #"));
        this.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return tileSetSelector.getDtileIdNumber(); }
            public void setValue(short value) { tileSetSelector.setDtileIdNumber(value); }
        }));
        
        this.add(new JLabel("Group"));
        this.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return tileSetSelector.getGroup(); }
            public void setValue(short value) { tileSetSelector.setGroup(value); }
        }));

    }

    public Object getTileSetSelector()
    {
        return tileSetSelector;
    }
}
