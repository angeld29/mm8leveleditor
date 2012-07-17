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

import org.gamenet.application.mm8leveleditor.data.DSoundsBin;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class DSoundsBinMM8 implements DSoundsBin
{
    private static final int DSOUNDS_BIN_RECORD_SIZE = 152;
    
    private int dSoundsBinOffset = 0;
    private byte dSoundsBinData[] = null;

    public DSoundsBinMM8()
    {
        super();
    }

    public DSoundsBinMM8(String fileName)
    {
        super();
        this.dSoundsBinData = new byte[DSOUNDS_BIN_RECORD_SIZE];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.dSoundsBinOffset = offset;
        this.dSoundsBinData = new byte[DSOUNDS_BIN_RECORD_SIZE];
        System.arraycopy(dataSrc, offset, this.dSoundsBinData, 0, this.dSoundsBinData.length);
        offset += this.dSoundsBinData.length;
        
        return offset;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
        int count = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        offset += count * DSOUNDS_BIN_RECORD_SIZE;
        
        return (offset == expectedNewOffset);
    }
    
    public static int populateObjects(byte[] data, int offset, List dSoundsBinList)
    {
        int dSoundsBinCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int dSoundsBinIndex = 0; dSoundsBinIndex < dSoundsBinCount; ++dSoundsBinIndex)
        {
            DSoundsBinMM8 dSoundsBin = new DSoundsBinMM8();
            dSoundsBinList.add(dSoundsBin);
            offset = dSoundsBin.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List dSoundsBinList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(dSoundsBinList.size(), newData, offset);
        offset += 4;

        for (int dSoundsBinIndex = 0; dSoundsBinIndex < dSoundsBinList.size(); ++dSoundsBinIndex)
        {
            DSoundsBinMM8 dSoundsBin = (DSoundsBinMM8)dSoundsBinList.get(dSoundsBinIndex);
            System.arraycopy(dSoundsBin.getDSoundsBinData(), 0, newData, offset, dSoundsBin.getDSoundsBinData().length);
            offset += dSoundsBin.getDSoundsBinData().length;
        }
        
        return offset;
    }

    public byte[] getDSoundsBinData()
    {
        return this.dSoundsBinData;
    }
    public int getDSoundsBinOffset()
    {
        return this.dSoundsBinOffset;
    }

    public static int getRecordSize()
    {
        return DSOUNDS_BIN_RECORD_SIZE;
    }


    // DSoundsBinMM8 things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(0, 64, ComparativeTableControl.REPRESENTATION_STRING));
        offsetList.add(new ComparativeTableControl.OffsetData(64, DSOUNDS_BIN_RECORD_SIZE - 64, ComparativeTableControl.REPRESENTATION_INT_DEC));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List dSoundsBinList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return dSoundsBinList.size();
            }

            public byte[] getData(int dataRow)
            {
                DSoundsBinMM8 dSoundsBin = (DSoundsBinMM8)dSoundsBinList.get(dataRow);
                return dSoundsBin.getDSoundsBinData();
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
