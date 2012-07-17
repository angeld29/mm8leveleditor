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
import javax.swing.table.AbstractTableModel;

import org.annotation.gui.CollapsablePanel;
import org.gamenet.application.mm8leveleditor.data.mm6.ContainedItem;
import org.gamenet.application.mm8leveleditor.data.mm6.ItemContainer;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.ResizingJTable;

public class ItemContainerControl extends JPanel
{
    private ItemContainer itemContainer = null;
    
    public ItemContainerControl(ItemContainer srcItemContainer)
    {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        this.itemContainer = srcItemContainer;

		// Items 
        final ContainedItem itemsArray[] = itemContainer.getContainedItemArray();
        
        boolean hasItems = false;
        for (int itemIndex = 0; itemIndex < itemsArray.length; itemIndex++)
        {
            if (false == itemsArray[itemIndex].isEmptyItem())
            {
                hasItems = true;
                break;
            }
        }
        
        if (hasItems)
        {
            JPanel itemsPanel = new JPanel();
            itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
            // itemsPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
            this.add(itemsPanel);
            
            final JLabel itemCountLabel = new JLabel("# of Items: " + String.valueOf(itemsArray.length));
            itemsPanel.add(makeNonStretchedPanelFor(itemCountLabel));

            for (int itemIndex = 0; itemIndex < itemsArray.length; ++itemIndex)
            {
                if (itemsArray[itemIndex].isEmptyItem())  continue;
                
                ContainedItem item = itemsArray[itemIndex];

                JPanel itemPanel = makeNonStretchedPanelFor(new JLabel("Item #" + String.valueOf(itemIndex + 1)));
                itemsPanel.add(itemPanel);
                itemPanel.add(new ContainedItemControl(item));
            }
            
    	    CollapsablePanel.IndirectDataSource containedItemIndirectDataSource = new CollapsablePanel.IndirectDataSource()
            {
                public Component getComponent()
                {
                    JPanel containedItemPanel = new JPanel();
                    containedItemPanel.setLayout(new BoxLayout(containedItemPanel, BoxLayout.Y_AXIS));
                    JLabel containedItemCountLabel = new JLabel("# of Contained Items: " + String.valueOf(itemsArray.length));
                    containedItemPanel.add(makeNonStretchedPanelFor(containedItemCountLabel));

            		ComparativeTableControl containedItemCBDTC = new ComparativeTableControl(ContainedItem.getOffsetList(itemContainer.getGameVersion()), ContainedItem.getComparativeDataSource(itemsArray));
            		containedItemPanel.add(containedItemCBDTC);

            		return containedItemPanel;
                }
            };	    
    	    this.add(makeNonStretchedPanelFor(new CollapsablePanel("Contained Items", containedItemIndirectDataSource, true)));

            this.add(makeNonStretchedPanelFor(new JLabel("item placement:")));
            ResizingJTable itemContainerMapBDTC = new ResizingJTable(new AbstractTableModel()
            {
                public int getColumnCount()
                {
                    return itemContainer.getItemLocationMap().getItemLocationMapWidth();
                }

                public int getRowCount()
                {
                    return itemContainer.getItemLocationMap().getItemLocationMapHeight();
                }

                public Object getValueAt(int rowIndex, int columnIndex)
                {
                    return new Integer(itemContainer.getItemLocationMap().getItemLocationMap()[rowIndex][columnIndex]);
                }

                public boolean isCellEditable(int row, int realColumn)
                {
                    return true;
                }

                public void setValueAt(Object value, int rowIndex, int columnIndex)
                {
                    itemContainer.getItemLocationMap().getItemLocationMap()[rowIndex][columnIndex] = Short.parseShort((String)value);
                    fireTableCellUpdated(rowIndex, columnIndex);
                }
            });
            this.add(makeNonStretchedPanelFor(itemContainerMapBDTC));
        }
        else
        {
            this.add(makeNonStretchedPanelFor(new JLabel("empty")));
        }
    }

    public Object getItemContainer()
    {
        return itemContainer;
    }

    protected JPanel makeNonStretchedPanelFor(Component component)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        if (null != component)  panel.add(component);
        return panel;
    }
}
