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

import org.gamenet.application.mm8leveleditor.data.mm6.ActiveSpell;
import org.gamenet.swing.controls.LongValueHolder;

public class ActiveSpellControl extends JPanel
{
    private ActiveSpell activeSpell = null;
    
    public ActiveSpellControl(ActiveSpell srcActiveSpell, String spellName)
    {
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.activeSpell = srcActiveSpell;

        this.add(new JLabel("Spell: " + spellName));
        this.add(new JLabel("End Date:"));
        DateTimeControl endDateTimeControl = new DateTimeControl(new LongValueHolder()
                {
                    public long getValue() { return activeSpell.getEndDateTime(); }
                    public void setValue(long value) { activeSpell.setEndDateTime(value); }
                });
        this.add(endDateTimeControl);

        this.add(new JLabel("Power:"));
        JFormattedTextField powerTextField = new JFormattedTextField(new Short(activeSpell.getPower()));
        powerTextField.setColumns(5);
        powerTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        activeSpell.setPower(((Number)((JFormattedTextField)e.getSource()).getValue()).shortValue());
                    }
                });
        this.add(powerTextField);

        this.add(new JLabel("Rank:"));
        JFormattedTextField rankTextField = new JFormattedTextField(new Short(activeSpell.getRank()));
        rankTextField.setColumns(5);
        rankTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        activeSpell.setRank(((Number)((JFormattedTextField)e.getSource()).getValue()).shortValue());
                    }
                });
        this.add(rankTextField);

    }

    public ActiveSpell getActiveSpell()
    {
        return activeSpell;
    }
}
