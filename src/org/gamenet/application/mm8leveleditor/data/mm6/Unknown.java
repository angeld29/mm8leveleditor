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

package org.gamenet.application.mm8leveleditor.data.mm6;

import java.util.ArrayList;
import java.util.List;

import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class Unknown
{
    private int unknownOffset = 0;
    private byte unknownData[] = null;

    public Unknown()
    {
        super();
    }

    public Unknown(int size)
    {
        super();
        this.unknownData = new byte[size];
    }

    public int initialize(byte dataSrc[], int offset, int size)
    {
        this.unknownOffset = offset;
        this.unknownData = new byte[size];
        System.arraycopy(dataSrc, offset, this.unknownData, 0, this.unknownData.length);
        offset += this.unknownData.length;
        
        return offset;
    }

    public static int populateObjects(byte[] data, int offset, List unknownList, int size)
    {
        int unknownCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        return populateObjects(data, offset, unknownList, unknownCount, size);
    }

    public static int populateObjects(byte[] data, int offset, List unknownList, int unknownCount, int size)
    {
        for (int unknownIndex = 0; unknownIndex < unknownCount; ++unknownIndex)
        {
            Unknown unknown = new Unknown();
            unknownList.add(unknown);
            offset = unknown.initialize(data, offset, size);
        }
        
        return offset;
    }
    
    public static int updateDataWithCount(byte[] newData, int offset, List unknownList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(unknownList.size(), newData, offset);
        offset += 4;

        return updateDataWithoutCount(newData, offset, unknownList);
    }
    
    public static int updateDataWithoutCount(byte[] newData, int offset, List unknownList)
    {
        for (int unknownIndex = 0; unknownIndex < unknownList.size(); ++unknownIndex)
        {
            Unknown unknown = (Unknown)unknownList.get(unknownIndex);
            System.arraycopy(unknown.getUnknownData(), 0, newData, offset, unknown.getUnknownData().length);
            offset += unknown.getUnknownData().length;
        }
        
        return offset;
    }

    public byte[] getUnknownData()
    {
        return this.unknownData;
    }
    public int getUnknownOffset()
    {
        return this.unknownOffset;
    }


    // Unknown things to decode

    public static List getOffsetList(int size)
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(0, size, ComparativeTableControl.REPRESENTATION_INT_DEC));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List unknownList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return unknownList.size();
            }

            public byte[] getData(int dataRow)
            {
                Unknown unknown = (Unknown)unknownList.get(dataRow);
                return unknown.getUnknownData();
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
