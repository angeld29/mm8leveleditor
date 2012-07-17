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

import org.gamenet.application.mm8leveleditor.data.mm6.MapNote;
import org.gamenet.swing.controls.BooleanValueHolder;
import org.gamenet.swing.controls.CheckBox;
import org.gamenet.swing.controls.ShortTextField;
import org.gamenet.swing.controls.ShortValueHolder;
import org.gamenet.swing.controls.StringTextField;
import org.gamenet.swing.controls.StringValueHolder;

public class MapNoteControl extends JPanel
{
    private MapNote mapNote = null;
    
    public MapNoteControl(MapNote srcMapNote)
    {
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.mapNote = srcMapNote;

        this.add(new JLabel("ID:"));
        this.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return mapNote.getId(); }
            public void setValue(short value) { mapNote.setId(value); }
        }));

        this.add(new CheckBox("Active", new BooleanValueHolder() {
            public boolean getValue() { return mapNote.isActive(); }
            public void setValue(boolean value) { mapNote.setActive(value); }
        }));
        
        this.add(new JLabel("X:"));
        this.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return mapNote.getX(); }
            public void setValue(short value) { mapNote.setX(value); }
        }));
        
        this.add(new JLabel("Y:"));
        this.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return mapNote.getY(); }
            public void setValue(short value) { mapNote.setY(value); }
        }));
        
        this.add(new JLabel("Text:"));
        this.add(new StringTextField(new StringValueHolder() {
            public String getValue() { return mapNote.getText(); }
            public void setValue(String value) { mapNote.setText(value); }
            public int getMaxLength() { return mapNote.getTextMaxLength(); }
        }));
    }

    public Object getMapNote()
    {
        return mapNote;
    }
}
