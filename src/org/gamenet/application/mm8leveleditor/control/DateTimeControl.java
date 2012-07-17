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

import org.gamenet.application.mm8leveleditor.data.DateTime;
import org.gamenet.swing.controls.IntTextField;
import org.gamenet.swing.controls.IntValueHolder;
import org.gamenet.swing.controls.LongValueHolder;
import org.gamenet.swing.controls.StringComboBox;

public class DateTimeControl extends JPanel
{
    private LongValueHolder longValueDataSource = null;
    
    public DateTimeControl(LongValueHolder srclongValueDataSource)
    {
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.longValueDataSource = srclongValueDataSource;

        this.add(new StringComboBox(DateTime.getMonthOptions(), new IntValueHolder() {
            public int getValue() { return DateTime.getMonthOfYearForValue(longValueDataSource.getValue()); }
            public void setValue(int value) { longValueDataSource.setValue(DateTime.updateMonthOfYearForValue(value, longValueDataSource.getValue())); }
        }));
        
        this.add(new IntTextField(2, new IntValueHolder() {
            public int getValue() { return 1 + DateTime.getDayOfMonthForValue(longValueDataSource.getValue()); }
            public void setValue(int value) { longValueDataSource.setValue(DateTime.updateDayOfMonthForValue((value - 1), longValueDataSource.getValue())); }
        }));
        this.add(new JLabel(","));
        this.add(new IntTextField(4, new IntValueHolder() {
            public int getValue() { return DateTime.getYearForValue(longValueDataSource.getValue()); }
            public void setValue(int value) { longValueDataSource.setValue(DateTime.updateYearForValue(value, longValueDataSource.getValue())); }
        }));
        
        this.add(new IntTextField(2, new IntValueHolder() {
            public int getValue() { return DateTime.getHourOfDayForValue(longValueDataSource.getValue()); }
            public void setValue(int value) { longValueDataSource.setValue(DateTime.updateHourOfDayForValue(value, longValueDataSource.getValue())); }
        }));
        this.add(new JLabel(":"));
        this.add(new IntTextField(2, new IntValueHolder() {
            public int getValue() { return DateTime.getMinuteOfHourForValue(longValueDataSource.getValue()); }
            public void setValue(int value) { longValueDataSource.setValue(DateTime.updateMinuteOfHourForValue(value, longValueDataSource.getValue())); }
        }));
        this.add(new JLabel(":"));
        this.add(new IntTextField(3, new IntValueHolder() {
            public int getValue() { return DateTime.getTickOfMinuteForValue(longValueDataSource.getValue()); }
            public void setValue(int value) { longValueDataSource.setValue(DateTime.updateTickOfMinuteForValue(value, longValueDataSource.getValue())); }
        }));
        
    }
}
