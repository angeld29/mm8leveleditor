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

package org.gamenet.application.mm8leveleditor.data.mm7.fileFormat;

import java.util.ArrayList;
import java.util.List;

import org.gamenet.application.mm8leveleditor.data.DSoundsBin;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class DSoundsBinMM7 implements DSoundsBin
{
    private static final int DSOUNDS_BIN_RECORD_SIZE = 120;
    
    private static final int SOUND_TYPE_LOAD_ON_LEVEL_INIT = 0;
    private static final int SOUND_TYPE_LOAD_ON_GAME_INIT = 1;
    private static final int SOUND_TYPE_LOAD_ON_DEMAND_SWAPPABLE = 2;
    private static final int SOUND_TYPE_LOAD_ON_LEVEL_INIT_OR_DEMAND_SWAPPABLE = 3;
    private static final int SOUND_TYPE_KEEP_IN_MEMORY = 4;
    
    private static final int SOUND_ATTRIBUTE_TEMPORARILY_LOCKED_IN_MEMORY_FOR_LEVEL = 0x0001;
    private static final int SOUND_ATTRIBUTE_SOUND_HAS_3D_COMPONENT = 0x0002;

    private int dSoundsBinOffset = 0;
    private byte dSoundsBinData[] = null;

    public DSoundsBinMM7()
    {
        super();
    }

    public DSoundsBinMM7(String fileName)
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
            DSoundsBinMM7 dSoundsBin = new DSoundsBinMM7();
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
            DSoundsBinMM7 dSoundsBin = (DSoundsBinMM7)dSoundsBinList.get(dSoundsBinIndex);
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


    // DSoundsBinMM7 things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(0, 32, ComparativeTableControl.REPRESENTATION_STRING, "sound file name"));
        offsetList.add(new ComparativeTableControl.OffsetData(32, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "id"));
        offsetList.add(new ComparativeTableControl.OffsetData(36, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "type"));
        offsetList.add(new ComparativeTableControl.OffsetData(40, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "attributes"));
        offsetList.add(new ComparativeTableControl.OffsetData(44, 68, ComparativeTableControl.REPRESENTATION_STRING, "runtime pointers to data"));
        offsetList.add(new ComparativeTableControl.OffsetData(112, 4, ComparativeTableControl.REPRESENTATION_STRING, "runtime 3d data pointer"));
        offsetList.add(new ComparativeTableControl.OffsetData(116, 4, ComparativeTableControl.REPRESENTATION_STRING, "runtime decompression/free indicator"));

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
                DSoundsBinMM7 dSoundsBin = (DSoundsBinMM7)dSoundsBinList.get(dataRow);
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
