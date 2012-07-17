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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class VariableTabularStringTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	
	private boolean columnZeroImmutable = false;
    private String textData[][] = null;
    
    public VariableTabularStringTableModel(List<List<String>> lineList)
    {
        super();
            
        int maxColumnCount = 0;
        Iterator<List<String>> linesIterator = lineList.iterator();
        while (linesIterator.hasNext())
        {
            List<String> columnList = linesIterator.next();
            if (columnList.size() > maxColumnCount)  maxColumnCount = columnList.size();
        }

        textData = new String[lineList.size()][maxColumnCount];
        
        int row = 0;
        Iterator<List<String>> rowIterator = lineList.iterator();
        while (rowIterator.hasNext())
        {
            int column = 0;
            List<String> columnList = rowIterator.next();
            Iterator<String> columnIterator = columnList.iterator();
            while (columnIterator.hasNext())
            {
                String aString = columnIterator.next();
                textData[row][column] = aString;
            
                ++column;
            }
            while (column < textData[row].length)
            {
                textData[row][column] = null;
                ++column;
            }
            
            ++row;
        }
        
        columnZeroImmutable = false;
    }
        
    public VariableTabularStringTableModel(String strings[])
    {
        super();
            
        textData = new String[strings.length][2];
        
        for (int row = 0; row < strings.length; ++row)
        {
            textData[row][0] = String.valueOf(row);
            textData[row][1] = strings[row];
        }
        
        columnZeroImmutable = true;
    }
        
    public String getColumnName(int col)
    {
        if (col >= textData[0].length)  return null;
        
        return textData[0][col];
    }
    public int getRowCount()
    {
        return textData.length;
    }
    public int getColumnCount()
    {
        return textData[0].length;
    }
    public Object getValueAt(int row, int col)
    {
        return textData[row][col];
    }

    public boolean isCellEditable(int row, int col)
    {
        if ((columnZeroImmutable) && (0 == col))  return false;

        return true;
    }
    public void setValueAt(Object value, int row, int col)
    {
        textData[row][col] = value.toString();
        fireTableCellUpdated(row, col);
    }

    public List<List<String>> getDataAsListOfListOfStrings()
    {
        List<List<String>> lineList = new ArrayList<List<String>>();
        for (int row = 0; row < textData.length; ++row)
        {
            List<String> columnList = new ArrayList<String>();
            for (int column = 0; column < textData[row].length; ++column)
            {
                columnList.add(textData[row][column]);
            }
            lineList.add(columnList);
        }
        
        return lineList;
    }
    
    public String[] getDataAsStringArray()
    {
        String strings[] = new String[textData.length];
        for (int row = 0; row < textData.length; ++row)
        {
            strings[row] = textData[row][1];
        }
        
        return strings;
    }
}