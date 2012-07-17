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

public class DOverlayBin
{
    // Overlay Description List
    private static final int DOVERLAY_BIN_RECORD_SIZE = 8;
    
    private int dOverlayBinOffset = 0;
    private byte dOverlayBinData[] = null;

    public DOverlayBin()
    {
        super();
    }

    public DOverlayBin(String fileName)
    {
        super();
        this.dOverlayBinData = new byte[DOVERLAY_BIN_RECORD_SIZE];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.dOverlayBinOffset = offset;
        this.dOverlayBinData = new byte[DOVERLAY_BIN_RECORD_SIZE];
        System.arraycopy(dataSrc, offset, this.dOverlayBinData, 0, this.dOverlayBinData.length);
        offset += this.dOverlayBinData.length;
        
        return offset;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
        int count = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        offset += count * DOVERLAY_BIN_RECORD_SIZE;
        
        return (offset == expectedNewOffset);
    }
    
    public static int populateObjects(byte[] data, int offset, List dOverlayBinList)
    {
        int dOverlayBinCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int dOverlayBinIndex = 0; dOverlayBinIndex < dOverlayBinCount; ++dOverlayBinIndex)
        {
            DOverlayBin dOverlayBin = new DOverlayBin();
            dOverlayBinList.add(dOverlayBin);
            offset = dOverlayBin.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List dOverlayBinList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(dOverlayBinList.size(), newData, offset);
        offset += 4;

        for (int dOverlayBinIndex = 0; dOverlayBinIndex < dOverlayBinList.size(); ++dOverlayBinIndex)
        {
            DOverlayBin dOverlayBin = (DOverlayBin)dOverlayBinList.get(dOverlayBinIndex);
            System.arraycopy(dOverlayBin.getDOverlayBinData(), 0, newData, offset, dOverlayBin.getDOverlayBinData().length);
            offset += dOverlayBin.getDOverlayBinData().length;
        }
        
        return offset;
    }

    public byte[] getDOverlayBinData()
    {
        return this.dOverlayBinData;
    }
    public int getDOverlayBinOffset()
    {
        return this.dOverlayBinOffset;
    }

    public static int getRecordSize()
    {
        return DOVERLAY_BIN_RECORD_SIZE;
    }


    // DOverlayBin things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(0, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "ID #"));
        offsetList.add(new ComparativeTableControl.OffsetData(2, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Type"));
        offsetList.add(new ComparativeTableControl.OffsetData(4, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Dsft ID #"));
        offsetList.add(new ComparativeTableControl.OffsetData(6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "unused?"));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List dOverlayBinList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return dOverlayBinList.size();
            }

            public byte[] getData(int dataRow)
            {
                DOverlayBin dOverlayBin = (DOverlayBin)dOverlayBinList.get(dataRow);
                return dOverlayBin.getDOverlayBinData();
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
