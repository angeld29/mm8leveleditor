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

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.gamenet.application.mm8leveleditor.data.mm6.ChestContents;
import org.gamenet.swing.controls.ByteDataTableControl;
import org.gamenet.swing.controls.IntTextField;
import org.gamenet.swing.controls.IntValueHolder;
import org.gamenet.swing.controls.StringComboBox;

public class ChestContentsControl extends JPanel
{
    private ChestContents chestContents = null;
    
    public ChestContentsControl(ChestContents srcChestContents)
    {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        this.chestContents = srcChestContents;

        JPanel chestContentDetailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.add(chestContentDetailsPanel);
        
        chestContentDetailsPanel.add(new JLabel("DChestID"));
        chestContentDetailsPanel.add(new IntTextField(1, new IntValueHolder() {
            public int getValue() { return chestContents.getDChestIDNumber(); }
            public void setValue(int value) { chestContents.setDChestIDNumber(value); }
        }));

        chestContentDetailsPanel.add(new JLabel("opened status:"));
        
        final IntValueHolder openedStatusIntValueDataSource = new IntValueHolder()
        {
            public int getValue()
            {
                return chestContents.getOpenedStatus();
            }

            public void setValue(int value)
            {
                chestContents.setOpenedStatus(value);
            }
        };
		chestContentDetailsPanel.add(new StringComboBox(ChestContents.getOpenStatuses(), openedStatusIntValueDataSource));

		this.add(new ItemContainerControl(chestContents.getItemContainer()));

        boolean remainingDataIsZero = true;
        for (int remainingDataIndex = 0; remainingDataIndex < chestContents.getRemainingData().length; remainingDataIndex++)
        {
            if (0 != chestContents.getRemainingData()[remainingDataIndex])
            {
                remainingDataIsZero = false;
                break;
            }
        }
        if (remainingDataIsZero)
        {
            this.add(makeNonStretchedPanelFor(new JLabel("Remaining Data is zeros.")));
        }
        else
        {
            this.add(makeNonStretchedPanelFor(new JLabel("Remaining Data:")));
    		ByteDataTableControl remainingDataBDTC = new ByteDataTableControl(chestContents.getRemainingData(), 32, chestContents.getChestContentsDataOffset() + ChestContents.getRecordSize(chestContents.getGameVersion()) - chestContents.getRemainingData().length);
            this.add(makeNonStretchedPanelFor(remainingDataBDTC));
        }
    }

    public Object getChestContents()
    {
        return chestContents;
    }

    protected JPanel makeNonStretchedPanelFor(Component component)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        if (null != component)  panel.add(component);
        return panel;
    }
}
