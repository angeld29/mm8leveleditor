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

import org.gamenet.application.mm8leveleditor.data.DObjListBin;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class DObjListBinMM6 implements DObjListBin
{
    //  Name	Label	Type	Radius	Height	Lifetime	Speed	Attributes -

    private static final int ATTRIBUTE_NO_SPRITE = 0x0001;
    private static final int ATTRIBUTE_NO_EFFECT_ON_MOVEMENT = 0x0002;
    private static final int ATTRIBUTE_LIMITED_LIFESPAN = 0x0004;
    private static final int ATTRIBUTE_LIFESPAN_BASED_ON_SPRITE_ANIMATION_PROPERTY = 0x0008;
    private static final int ATTRIBUTE_FIXED_IN_PLACE = 0x0010; // cannot be picked up by player
    private static final int ATTRIBUTE_NOT_AFFECTED_BY_GRAVITY = 0x0020;
    private static final int ATTRIBUTE_ACTION_ON_CONTACT = 0x0040; // object does something when it hits
    private static final int ATTRIBUTE_BOUNCES = 0x0080;
    private static final int ATTRIBUTE_TRAILS_BITS = 0x0100; // object trails bits

    private static final int ATTRIBUTE_MM6_TRAILS_BITS_HORIZONTALLY = 0x0200;
    private static final int ATTRIBUTE_MM6_TRAILS_BITS_VERTICALLY = 0x0400;

    private static final int DOBJLIST_BIN_RECORD_SIZE = 52;
    
    private int dObjListBinOffset = 0;
    private byte dObjListBinData[] = null;

    public DObjListBinMM6()
    {
        super();
    }

    public DObjListBinMM6(String fileName)
    {
        super();
        this.dObjListBinData = new byte[DOBJLIST_BIN_RECORD_SIZE];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.dObjListBinOffset = offset;
        this.dObjListBinData = new byte[DOBJLIST_BIN_RECORD_SIZE];
        System.arraycopy(dataSrc, offset, this.dObjListBinData, 0, this.dObjListBinData.length);
        offset += this.dObjListBinData.length;
        
        return offset;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
        int count = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        offset += count * DOBJLIST_BIN_RECORD_SIZE;
        
        return (offset == expectedNewOffset);
    }
    
    public static int populateObjects(byte[] data, int offset, List dObjListBinList)
    {
        int dObjListBinCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int dObjListBinIndex = 0; dObjListBinIndex < dObjListBinCount; ++dObjListBinIndex)
        {
            DObjListBinMM6 dObjListBin = new DObjListBinMM6();
            dObjListBinList.add(dObjListBin);
            offset = dObjListBin.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List dObjListBinList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(dObjListBinList.size(), newData, offset);
        offset += 4;

        for (int dObjListBinIndex = 0; dObjListBinIndex < dObjListBinList.size(); ++dObjListBinIndex)
        {
            DObjListBinMM6 dObjListBin = (DObjListBinMM6)dObjListBinList.get(dObjListBinIndex);
            System.arraycopy(dObjListBin.getDObjListBinData(), 0, newData, offset, dObjListBin.getDObjListBinData().length);
            offset += dObjListBin.getDObjListBinData().length;
        }
        
        return offset;
    }

    public byte[] getDObjListBinData()
    {
        return this.dObjListBinData;
    }
    public int getDObjListBinOffset()
    {
        return this.dObjListBinOffset;
    }

    public static int getRecordSize()
    {
        return DOBJLIST_BIN_RECORD_SIZE;
    }


    // DObjListBinMM6 things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();

        offsetList.add(new ComparativeTableControl.OffsetData(0, 32, ComparativeTableControl.REPRESENTATION_STRING, "Name"));
        offsetList.add(new ComparativeTableControl.OffsetData(32, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "type?"));
        offsetList.add(new ComparativeTableControl.OffsetData(34, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "radius?"));
        offsetList.add(new ComparativeTableControl.OffsetData(36, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "height?"));
        offsetList.add(new ComparativeTableControl.OffsetData(38, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "attrib?"));
        offsetList.add(new ComparativeTableControl.OffsetData(40, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Dsft ID #"));
        offsetList.add(new ComparativeTableControl.OffsetData(42, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "lifetime"));
        offsetList.add(new ComparativeTableControl.OffsetData(44, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "particle color"));
        offsetList.add(new ComparativeTableControl.OffsetData(46, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "speed?"));
        offsetList.add(new ComparativeTableControl.OffsetData(48, 4, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "4-bit color?"));

//        offsetList.add(new ComparativeTableControl.OffsetData(42, 8, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "colorbit?"));
//        offsetList.add(new ComparativeTableControl.OffsetData(50, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "speed?"));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List dObjListBinList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return dObjListBinList.size();
            }

            public byte[] getData(int dataRow)
            {
                DObjListBinMM6 dObjListBin = (DObjListBinMM6)dObjListBinList.get(dataRow);
                return dObjListBin.getDObjListBinData();
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
