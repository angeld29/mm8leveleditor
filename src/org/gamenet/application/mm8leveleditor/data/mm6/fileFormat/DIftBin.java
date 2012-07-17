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

package org.gamenet.application.mm8leveleditor.data.mm6.fileFormat;

import java.util.ArrayList;
import java.util.List;

import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class DIftBin
{
    // Icon Frame Table
    private static final int DIFT_BIN_RECORD_SIZE = 32;
    
	public final static int ATTRIBUTE__NOT_LAST_FRAME = 	0x00000001; 
	public final static int ATTRIBUTE__NEW_FRAME = 		0x00000004; 

    private int dIftBinOffset = 0;
    private byte dIftBinData[] = null;

    public DIftBin()
    {
        super();
    }

    public DIftBin(String fileName)
    {
        super();
        this.dIftBinData = new byte[DIFT_BIN_RECORD_SIZE];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.dIftBinOffset = offset;
        this.dIftBinData = new byte[DIFT_BIN_RECORD_SIZE];
        System.arraycopy(dataSrc, offset, this.dIftBinData, 0, this.dIftBinData.length);
        offset += this.dIftBinData.length;
        
        return offset;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
        int count = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        offset += count * DIFT_BIN_RECORD_SIZE;
        
        return (offset == expectedNewOffset);
    }
    
    public static int populateObjects(byte[] data, int offset, List dIftBinList)
    {
        int dIftBinCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int dIftBinIndex = 0; dIftBinIndex < dIftBinCount; ++dIftBinIndex)
        {
            DIftBin dIftBin = new DIftBin();
            dIftBinList.add(dIftBin);
            offset = dIftBin.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List dIftBinList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(dIftBinList.size(), newData, offset);
        offset += 4;

        for (int dIftBinIndex = 0; dIftBinIndex < dIftBinList.size(); ++dIftBinIndex)
        {
            DIftBin dIftBin = (DIftBin)dIftBinList.get(dIftBinIndex);
            System.arraycopy(dIftBin.getDIftBinData(), 0, newData, offset, dIftBin.getDIftBinData().length);
            offset += dIftBin.getDIftBinData().length;
        }
        
        return offset;
    }

    public byte[] getDIftBinData()
    {
        return this.dIftBinData;
    }
    public int getDIftBinOffset()
    {
        return this.dIftBinOffset;
    }

    public static int getRecordSize()
    {
        return DIFT_BIN_RECORD_SIZE;
    }


    // DIftBin things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        
        offsetList.add(new ComparativeTableControl.OffsetData(0, 12, ComparativeTableControl.REPRESENTATION_STRING, "Set Name"));
        offsetList.add(new ComparativeTableControl.OffsetData(12, 12, ComparativeTableControl.REPRESENTATION_STRING, "Icon Name"));
        offsetList.add(new ComparativeTableControl.OffsetData(24, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "frame display time")); // 1/16 of a second
        offsetList.add(new ComparativeTableControl.OffsetData(26, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "total animation time"));
        offsetList.add(new ComparativeTableControl.OffsetData(28, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "attributes"));
        offsetList.add(new ComparativeTableControl.OffsetData(30, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "unused runtime index"));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List dIftBinList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return dIftBinList.size();
            }

            public byte[] getData(int dataRow)
            {
                DIftBin dIftBin = (DIftBin)dIftBinList.get(dataRow);
                return dIftBin.getDIftBinData();
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
