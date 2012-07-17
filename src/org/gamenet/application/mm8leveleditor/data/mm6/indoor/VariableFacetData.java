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

package org.gamenet.application.mm8leveleditor.data.mm6.indoor;

import java.util.ArrayList;
import java.util.List;

import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class VariableFacetData
{
    private static final int VARIABLE_FACET_DATA_RECORD_LENGTH = 36;
    
    private byte variableFacetData[] = null;

    public VariableFacetData()
    {
        super();
        this.variableFacetData = new byte[VARIABLE_FACET_DATA_RECORD_LENGTH];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        System.arraycopy(dataSrc, offset, this.variableFacetData, 0, this.variableFacetData.length);
        offset += this.variableFacetData.length;
        
        return offset;
    }

    public static int populateObjects(byte[] data, int offset, List variableFacetDataList, int variableFacetDataCount)
    {
        for (int variableFacetDataIndex = 0; variableFacetDataIndex < variableFacetDataCount; ++variableFacetDataIndex)
        {
            VariableFacetData variableFacetData = new VariableFacetData();
            variableFacetDataList.add(variableFacetData);
            offset = variableFacetData.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateDataWithCount(byte[] newData, int offset, List variableFacetDataList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(variableFacetDataList.size(), newData, offset);
        offset += 4;

        return updateDataWithoutCount(newData, offset, variableFacetDataList);
    }
    
    public static int updateDataWithoutCount(byte[] newData, int offset, List variableFacetDataList)
    {
        for (int variableFacetDataIndex = 0; variableFacetDataIndex < variableFacetDataList.size(); ++variableFacetDataIndex)
        {
            VariableFacetData variableFacetData = (VariableFacetData)variableFacetDataList.get(variableFacetDataIndex);
            System.arraycopy(variableFacetData.getVariableFacetData(), 0, newData, offset, variableFacetData.getVariableFacetData().length);
            offset += variableFacetData.getVariableFacetData().length;
        }
        
        return offset;
    }

    public byte[] getVariableFacetData()
    {
        return this.variableFacetData;
    }


    // Unknown things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(0, VARIABLE_FACET_DATA_RECORD_LENGTH, ComparativeTableControl.REPRESENTATION_INT_DEC));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List variableFacetDataList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return variableFacetDataList.size();
            }

            public byte[] getData(int dataRow)
            {
                VariableFacetData variableFacetData = (VariableFacetData)variableFacetDataList.get(dataRow);
                return variableFacetData.getVariableFacetData();
            }

            public int getAdjustedDataRowIndex(int dataRow)
            {
                return dataRow;
            }
            
            public String getIdentifier(int dataRow)
            {
                return "";
            }

            public int getOffset(int dataRow)
            {
                return 0;
            }
        };
    }
}
