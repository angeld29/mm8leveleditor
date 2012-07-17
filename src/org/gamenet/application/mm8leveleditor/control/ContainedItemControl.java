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

import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.application.mm8leveleditor.data.mm6.ContainedItem;
import org.gamenet.swing.controls.IntTextField;
import org.gamenet.swing.controls.IntValueHolder;
import org.gamenet.swing.controls.LongValueHolder;
import org.gamenet.swing.controls.StringComboBox;

public class ContainedItemControl extends JPanel
{
    private ContainedItem containedItem = null;
    
    public ContainedItemControl(ContainedItem srcItem)
    {
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.containedItem = srcItem;

        this.add(new JLabel("ID:"));
        this.add(new IntTextField(3, new IntValueHolder() {
            public int getValue() { return containedItem.getItemNumber(); }
            public void setValue(int value) { containedItem.setItemNumber(value); }
        }));

        this.add(new JLabel("standard magic class:"));
        this.add(new IntTextField(3, new IntValueHolder() {
            public int getValue() { return containedItem.getStandardMagicClass(); }
            public void setValue(int value) { containedItem.setStandardMagicClass(value); }
        }));

        this.add(new JLabel("value modifier:"));
        this.add(new IntTextField(3, new IntValueHolder() {
            public int getValue() { return containedItem.getValueModifier(); }
            public void setValue(int value) { containedItem.setValueModifier(value); }
        }));

        this.add(new JLabel("special magic class:"));
        this.add(new IntTextField(3, new IntValueHolder() {
            public int getValue() { return containedItem.getSpecialMagicClass(); }
            public void setValue(int value) { containedItem.setSpecialMagicClass(value); }
        }));

        this.add(new JLabel("amount of gold:"));
        this.add(new IntTextField(3, new IntValueHolder() {
            public int getValue() { return containedItem.getGoldAmount(); }
            public void setValue(int value) { containedItem.setGoldAmount(value); }
        }));

        this.add(new JLabel("charges:"));
        this.add(new IntTextField(3, new IntValueHolder() {
            public int getValue() { return containedItem.getCharges(); }
            public void setValue(int value) { containedItem.setCharges(value); }
        }));

        this.add(new JLabel("attributes:"));
        this.add(new IntTextField(3, new IntValueHolder() {
            public int getValue() { return containedItem.getAttributes(); }
            public void setValue(int value) { containedItem.setAttributes(value); }
        }));

        this.add(new JLabel("location:"));
        this.add(new StringComboBox(containedItem.getBodyLocationOptions(), new IntValueHolder() {
            public int getValue() { return containedItem.getBodyLocation(); }
            public void setValue(int value) { containedItem.setBodyLocation(value); }
        }));

        this.add(new JLabel("maximum charges:"));
        this.add(new IntTextField(3, new IntValueHolder() {
            public int getValue() { return containedItem.getMaximumCharges(); }
            public void setValue(int value) { containedItem.setMaximumCharges(value); }
        }));

        this.add(new JLabel("owner:"));
        this.add(new IntTextField(3, new IntValueHolder() {
            public int getValue() { return containedItem.getOwner(); }
            public void setValue(int value) { containedItem.setOwner(value); }
        }));
        
        if (GameVersion.MM6 != containedItem.getGameVersion())
        {
            this.add(new JLabel("time:"));
            this.add(new DateTimeControl(new LongValueHolder() {
                public long getValue() { return containedItem.getTime(); }
                public void setValue(long value) { containedItem.setTime(value); }
            }));
        }
    }

    public Object getContainedItem()
    {
        return containedItem;
    }
}
