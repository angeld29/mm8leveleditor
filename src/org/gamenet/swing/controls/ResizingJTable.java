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

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class ResizingJTable extends JTable
{
    public interface ResizingJTableAdvice
    {
        public boolean shouldRecalcColumnWidths();
    }

    public ResizingJTable(TableModel tableModel)
    {
        super(tableModel);

        if (tableModel instanceof ResizingJTableAdvice)
        {
            if (((ResizingJTableAdvice)tableModel).shouldRecalcColumnWidths())
                recalcColumnWidths();
        }
        else
        {
            recalcColumnWidths();
        }
    }

    public void tableChanged(TableModelEvent tableModelEvent) {
        
        TableModel tableModel = (TableModel)tableModelEvent.getSource();
        if (tableModel instanceof ResizingJTableAdvice)
        {
            if (((ResizingJTableAdvice)tableModel).shouldRecalcColumnWidths())
                recalcColumnWidths();
        }
        else
        {
            recalcColumnWidths();
        }
        
        super.tableChanged(tableModelEvent);
    }

    public void recalcColumnWidths()
    {
        JTableHeader header = this.getTableHeader();

        TableCellRenderer defaultHeaderRenderer = null;

        if (header != null)
            defaultHeaderRenderer = header.getDefaultRenderer();

        TableColumnModel columns = this.getColumnModel();
        TableModel data = this.getModel();

        int margin = columns.getColumnMargin(); // only JDK1.3

        int rowCount = data.getRowCount();

        int totalWidth = 0;

        for (int i = columns.getColumnCount() - 1; i >= 0; --i)
        {
            TableColumn column = columns.getColumn(i);
            
            int columnIndex = column.getModelIndex();
            
            int width = -1; 

            TableCellRenderer h = column.getHeaderRenderer();
          
            if (h == null)
                h = defaultHeaderRenderer;
            
            if (h != null) // Not explicitly impossible
            {
                Component c = h.getTableCellRendererComponent
                       (this, column.getHeaderValue(),
                        false, false, -1, i);
                    
                width = c.getPreferredSize().width;
            }
       
            for (int row = rowCount - 1; row >= 0; --row)
            {
                TableCellRenderer r = this.getCellRenderer(row, i);
                 
                Component c = r.getTableCellRendererComponent
                   (this,
                    data.getValueAt(row, columnIndex),
                    false, false, row, i);
        
                    width = Math.max(width, c.getPreferredSize().width);
            }

            if (width >= 0)
                column.setPreferredWidth(width + margin); // <1.3: without margin
            else
                ; // ???
            
            totalWidth += column.getPreferredWidth();
        }
    }    
}
