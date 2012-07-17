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

package org.gamenet.application.mm8leveleditor.data.mm8.fileFormat;

import java.util.ArrayList;
import java.util.List;

import org.gamenet.application.mm8leveleditor.data.DMonListBin;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class DMonListBinMM8 implements DMonListBin
{
    // Race Name	Stand	Walk	Fidget	Attack	Wince	Death	Dead	Height	Speed	Max Rad	Avg Rad	Alpha	Red	Green	Blue	SFX?	Attack	Die	Stun	Fidget

    private static final int DMONLIST_BIN_RECORD_SIZE = 184;
    
    private int dMonListBinOffset = 0;
    private byte dMonListBinData[] = null;

    public DMonListBinMM8()
    {
        super();
    }

    public DMonListBinMM8(String fileName)
    {
        super();
        this.dMonListBinData = new byte[DMONLIST_BIN_RECORD_SIZE];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.dMonListBinOffset = offset;
        this.dMonListBinData = new byte[DMONLIST_BIN_RECORD_SIZE];
        System.arraycopy(dataSrc, offset, this.dMonListBinData, 0, this.dMonListBinData.length);
        offset += this.dMonListBinData.length;
        
        return offset;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
        int dMonListBinCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        offset += dMonListBinCount * DMONLIST_BIN_RECORD_SIZE;
        
        return (offset == expectedNewOffset);
    }
    
    public static int populateObjects(byte[] data, int offset, List dMonListBinList)
    {
        int dMonListBinCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int dMonListBinIndex = 0; dMonListBinIndex < dMonListBinCount; ++dMonListBinIndex)
        {
            DMonListBinMM8 dMonListBin = new DMonListBinMM8();
            dMonListBinList.add(dMonListBin);
            offset = dMonListBin.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List dMonListBinList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(dMonListBinList.size(), newData, offset);
        offset += 4;

        for (int dMonListBinIndex = 0; dMonListBinIndex < dMonListBinList.size(); ++dMonListBinIndex)
        {
            DMonListBinMM8 dMonListBin = (DMonListBinMM8)dMonListBinList.get(dMonListBinIndex);
            System.arraycopy(dMonListBin.getDMonListBinData(), 0, newData, offset, dMonListBin.getDMonListBinData().length);
            offset += dMonListBin.getDMonListBinData().length;
        }
        
        return offset;
    }

    public byte[] getDMonListBinData()
    {
        return this.dMonListBinData;
    }
    public int getDMonListBinOffset()
    {
        return this.dMonListBinOffset;
    }

    public static int getRecordSize()
    {
        return DMONLIST_BIN_RECORD_SIZE;
    }


    // DMonListBinMM8 things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(0, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "height?"));
        offsetList.add(new ComparativeTableControl.OffsetData(2, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "max radius?"));
        offsetList.add(new ComparativeTableControl.OffsetData(4, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "speed?"));
        offsetList.add(new ComparativeTableControl.OffsetData(6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "avg radius?"));
        offsetList.add(new ComparativeTableControl.OffsetData(8, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "tint blue?"));
        offsetList.add(new ComparativeTableControl.OffsetData(9, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "tint green?"));
        offsetList.add(new ComparativeTableControl.OffsetData(10, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "tint red?"));
        offsetList.add(new ComparativeTableControl.OffsetData(11, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "tint alpha?"));
        offsetList.add(new ComparativeTableControl.OffsetData(12, 8, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "sfx?"));
        offsetList.add(new ComparativeTableControl.OffsetData(20, 64, ComparativeTableControl.REPRESENTATION_STRING, "Race Name"));
        offsetList.add(new ComparativeTableControl.OffsetData(84, 10, ComparativeTableControl.REPRESENTATION_STRING, "Stand"));
        offsetList.add(new ComparativeTableControl.OffsetData(94, 10, ComparativeTableControl.REPRESENTATION_STRING, "Walk"));
        offsetList.add(new ComparativeTableControl.OffsetData(104, 10, ComparativeTableControl.REPRESENTATION_STRING, "Attack"));
        offsetList.add(new ComparativeTableControl.OffsetData(114, 10, ComparativeTableControl.REPRESENTATION_STRING, "Attack"));
        offsetList.add(new ComparativeTableControl.OffsetData(124, 10, ComparativeTableControl.REPRESENTATION_STRING, "Wince"));
        offsetList.add(new ComparativeTableControl.OffsetData(134, 10, ComparativeTableControl.REPRESENTATION_STRING, "Dying"));
        offsetList.add(new ComparativeTableControl.OffsetData(144, 10, ComparativeTableControl.REPRESENTATION_STRING, "Dead"));
        offsetList.add(new ComparativeTableControl.OffsetData(154, 10, ComparativeTableControl.REPRESENTATION_STRING, "Fidget"));
        offsetList.add(new ComparativeTableControl.OffsetData(164, 20, ComparativeTableControl.REPRESENTATION_INT_DEC));
        //	Height	Speed	Max Rad	Avg Rad	Alpha	Red	Green	Blue	SFX?	Attack	Die	Stun	Fidget

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List dMonListBinList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return dMonListBinList.size();
            }

            public byte[] getData(int dataRow)
            {
                DMonListBinMM8 dMonListBin = (DMonListBinMM8)dMonListBinList.get(dataRow);
                return dMonListBin.getDMonListBinData();
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
