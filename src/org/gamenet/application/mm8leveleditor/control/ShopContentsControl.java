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

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.gamenet.application.mm8leveleditor.data.mm6.ContainedItem;
import org.gamenet.application.mm8leveleditor.data.mm6.ShopContents;

public class ShopContentsControl extends JPanel
{
    private ShopContents shopContents = null;
    
    public ShopContentsControl(ShopContents srcShopContents)
    {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        this.shopContents = srcShopContents;

        ContainedItem containedItemArray[] = shopContents.getContainedItemArray();
        for (int index = 0; index < containedItemArray.length; index++)
        {
            ContainedItem item = containedItemArray[index];
            
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(new JLabel("#" + String.valueOf(index + 1)));
            panel.add(new ContainedItemControl(item));
            this.add(panel);
        }
    }

    public ShopContents getShopContents()
    {
        return shopContents;
    }
}
