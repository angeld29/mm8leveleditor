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

public class DTileBin
{
    // Tile Table
    private static final int DTILE_BIN_RECORD_SIZE = 26;
    
    private static final int ATTRIBUTE_WINTER = 				2;
    private static final int ATTRIBUTE_PENDING = 				64;
    private static final int ATTRIBUTE_EDGE_PIECES = 			512;
    private static final int ATTRIBUTE_WINTER_EDGE_PIECES = 	256; // winter edge pieces have it

    private int dTileBinOffset = 0;
    private byte dTileBinData[] = null;

    public DTileBin()
    {
        super();
    }

    public DTileBin(String fileName)
    {
        super();
        this.dTileBinData = new byte[DTILE_BIN_RECORD_SIZE];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.dTileBinOffset = offset;
        this.dTileBinData = new byte[DTILE_BIN_RECORD_SIZE];
        System.arraycopy(dataSrc, offset, this.dTileBinData, 0, this.dTileBinData.length);
        offset += this.dTileBinData.length;
        
        return offset;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
        int count = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        offset += count * DTILE_BIN_RECORD_SIZE;
        
        return (offset == expectedNewOffset);
    }
    
    public static int populateObjects(byte[] data, int offset, List dTileBinList)
    {
        int dTileBinCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int dTileBinIndex = 0; dTileBinIndex < dTileBinCount; ++dTileBinIndex)
        {
            DTileBin dTileBin = new DTileBin();
            dTileBinList.add(dTileBin);
            offset = dTileBin.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List dTileBinList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(dTileBinList.size(), newData, offset);
        offset += 4;

        for (int dTileBinIndex = 0; dTileBinIndex < dTileBinList.size(); ++dTileBinIndex)
        {
            DTileBin dTileBin = (DTileBin)dTileBinList.get(dTileBinIndex);
            System.arraycopy(dTileBin.getDTileBinData(), 0, newData, offset, dTileBin.getDTileBinData().length);
            offset += dTileBin.getDTileBinData().length;
        }
        
        return offset;
    }

    public byte[] getDTileBinData()
    {
        return this.dTileBinData;
    }
    public int getDTileBinOffset()
    {
        return this.dTileBinOffset;
    }

    public static int getRecordSize()
    {
        return DTILE_BIN_RECORD_SIZE;
    }


    // DTileBin things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();

        offsetList.add(new ComparativeTableControl.OffsetData(0, 16, ComparativeTableControl.REPRESENTATION_STRING, "name"));
        offsetList.add(new ComparativeTableControl.OffsetData(16, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "id"));
        offsetList.add(new ComparativeTableControl.OffsetData(18, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "runtime bitmap ptr?"));
        offsetList.add(new ComparativeTableControl.OffsetData(20, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "terrain group"));
        offsetList.add(new ComparativeTableControl.OffsetData(22, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "tile orientation"));
        offsetList.add(new ComparativeTableControl.OffsetData(24, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "attributes"));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List dTileBinList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return dTileBinList.size();
            }

            public byte[] getData(int dataRow)
            {
                DTileBin dTileBin = (DTileBin)dTileBinList.get(dataRow);
                return dTileBin.getDTileBinData();
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
