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

public class DTftBin
{
    // Texture Frame Table
    private static final int DTFT_BIN_RECORD_SIZE = 20;
    
	public final static int ATTRIBUTE__NOT_LAST_FRAME = 	0x0001; 
	public final static int ATTRIBUTE__NEW_FRAME = 		0x0002; 

    private int dTftBinOffset = 0;
    private byte dTftBinData[] = null;

    public DTftBin()
    {
        super();
    }

    public DTftBin(String fileName)
    {
        super();
        this.dTftBinData = new byte[DTFT_BIN_RECORD_SIZE];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.dTftBinOffset = offset;
        this.dTftBinData = new byte[DTFT_BIN_RECORD_SIZE];
        System.arraycopy(dataSrc, offset, this.dTftBinData, 0, this.dTftBinData.length);
        offset += this.dTftBinData.length;
        
        return offset;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
        int count = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        offset += count * DTFT_BIN_RECORD_SIZE;
        
        return (offset == expectedNewOffset);
    }
    
    public static int populateObjects(byte[] data, int offset, List dTftBinList)
    {
        int dTftBinCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int dTftBinIndex = 0; dTftBinIndex < dTftBinCount; ++dTftBinIndex)
        {
            DTftBin dTftBin = new DTftBin();
            dTftBinList.add(dTftBin);
            offset = dTftBin.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List dTftBinList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(dTftBinList.size(), newData, offset);
        offset += 4;

        for (int dTftBinIndex = 0; dTftBinIndex < dTftBinList.size(); ++dTftBinIndex)
        {
            DTftBin dTftBin = (DTftBin)dTftBinList.get(dTftBinIndex);
            System.arraycopy(dTftBin.getDTftBinData(), 0, newData, offset, dTftBin.getDTftBinData().length);
            offset += dTftBin.getDTftBinData().length;
        }
        
        return offset;
    }

    public byte[] getDTftBinData()
    {
        return this.dTftBinData;
    }
    public int getDTftBinOffset()
    {
        return this.dTftBinOffset;
    }

    public static int getRecordSize()
    {
        return DTFT_BIN_RECORD_SIZE;
    }


    // DTftBin things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(0, 12, ComparativeTableControl.REPRESENTATION_STRING, "Texture Name"));
        offsetList.add(new ComparativeTableControl.OffsetData(12, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "runtime index ptr"));
        offsetList.add(new ComparativeTableControl.OffsetData(14, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "frame display time")); // 1/32 of a second
        offsetList.add(new ComparativeTableControl.OffsetData(16, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "total animation time"));
        offsetList.add(new ComparativeTableControl.OffsetData(18, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "attributes"));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List dTftBinList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return dTftBinList.size();
            }

            public byte[] getData(int dataRow)
            {
                DTftBin dTftBin = (DTftBin)dTftBinList.get(dataRow);
                return dTftBin.getDTftBinData();
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
