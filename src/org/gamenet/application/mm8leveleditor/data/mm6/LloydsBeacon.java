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

public class LloydsBeacon
{
    private static final int LLOYDS_BEACON_RECORD_SIZE = 28;
    
    private static int LLOYDS_BEACON_SPELL_END_TIMESTAMP_OFFSET = 0;
    private static int LLOYDS_BEACON_SPELL_X_OFFSET = 8;
    private static int LLOYDS_BEACON_SPELL_Y_OFFSET = 12;
    private static int LLOYDS_BEACON_SPELL_Z_OFFSET = 16;
    private static int LLOYDS_BEACON_SPELL_FACING_OFFSET = 20;
    private static int LLOYDS_BEACON_SPELL_TILT_OFFSET = 22;
    private static int LLOYDS_BEACON_SPELL_UNK1_OFFSET = 24;
    private static int LLOYDS_BEACON_SPELL_DESTINATION_OFFSET = 26;
    // destinations:
    //  0 - castle alamos
    //  1 - castle darkmore
    //  2 - castle kreigspire
    //  3 - goblinwatch
    //  4 - abandoned temple
    //  5 - shadow guild hideout
    //  6 - hall of the fire lord
    //  7 - snergle's caverns
    //  8 - dragoon's caverns
    //  9 - silver helm outpost
    // 10 - shadow guild
    // 11 - snergle's iron mines
    // 12 - dragoons' keep
    // 13 - corlagon's estate
    // 14 - silver helm stronghold
    // 15 - the monolith

    private int lloydsBeaconOffset = 0;
    private byte lloydsBeaconData[] = null;

    public LloydsBeacon()
    {
        super();
    }

    public LloydsBeacon(String fileName)
    {
        super();
        this.lloydsBeaconData = new byte[LLOYDS_BEACON_RECORD_SIZE];
    }

    public long getEndDateTime()
    {
        return ByteConversions.getLongInByteArrayAtPosition(lloydsBeaconData, LLOYDS_BEACON_SPELL_END_TIMESTAMP_OFFSET);
    }
    public void setEndDateTime(long value)
    {
        ByteConversions.setLongInByteArrayAtPosition(value, lloydsBeaconData, LLOYDS_BEACON_SPELL_END_TIMESTAMP_OFFSET);
    }
    
    public int getX()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(lloydsBeaconData, LLOYDS_BEACON_SPELL_X_OFFSET);
    }    
    public void setX(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, lloydsBeaconData, LLOYDS_BEACON_SPELL_X_OFFSET);
    }
    
    public int getY()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(lloydsBeaconData, LLOYDS_BEACON_SPELL_Y_OFFSET);
    }
    public void setY(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, lloydsBeaconData, LLOYDS_BEACON_SPELL_Y_OFFSET);
    }
    
    public int getZ()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(lloydsBeaconData, LLOYDS_BEACON_SPELL_Z_OFFSET);
    }
    public void setZ(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, lloydsBeaconData, LLOYDS_BEACON_SPELL_Z_OFFSET);
    }
    
    public short getFacing()
    {
        return ByteConversions.getShortInByteArrayAtPosition(lloydsBeaconData, LLOYDS_BEACON_SPELL_FACING_OFFSET);
    }    
    public void setFacing(short value)
    {
        ByteConversions.setShortInByteArrayAtPosition(value, lloydsBeaconData, LLOYDS_BEACON_SPELL_FACING_OFFSET);
    }
    
    public short getTilt()
    {
        return ByteConversions.getShortInByteArrayAtPosition(lloydsBeaconData, LLOYDS_BEACON_SPELL_TILT_OFFSET);
    }    
    public void setTilt(short value)
    {
        ByteConversions.setShortInByteArrayAtPosition(value, lloydsBeaconData, LLOYDS_BEACON_SPELL_TILT_OFFSET);
    }
    
    public short getDestination()
    {
        return ByteConversions.getShortInByteArrayAtPosition(lloydsBeaconData, LLOYDS_BEACON_SPELL_DESTINATION_OFFSET);
    }    
    public void setDestination(short value)
    {
        ByteConversions.setShortInByteArrayAtPosition(value, lloydsBeaconData, LLOYDS_BEACON_SPELL_DESTINATION_OFFSET);
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.lloydsBeaconOffset = offset;
        this.lloydsBeaconData = new byte[LLOYDS_BEACON_RECORD_SIZE];
        System.arraycopy(dataSrc, offset, this.lloydsBeaconData, 0, this.lloydsBeaconData.length);
        offset += this.lloydsBeaconData.length;
        
        return offset;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
        offset += LLOYDS_BEACON_RECORD_SIZE;
        
        return (offset == expectedNewOffset);
    }
    
    public static int populateObjects(byte[] data, int offset, List lloydsBeaconList, int lloydsBeaconCount)
    {
        for (int lloydsBeaconIndex = 0; lloydsBeaconIndex < lloydsBeaconCount; ++lloydsBeaconIndex)
        {
            LloydsBeacon lloydsBeacon = new LloydsBeacon();
            lloydsBeaconList.add(lloydsBeacon);
            offset = lloydsBeacon.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List lloydsBeaconList)
    {
        for (int lloydsBeaconIndex = 0; lloydsBeaconIndex < lloydsBeaconList.size(); ++lloydsBeaconIndex)
        {
            LloydsBeacon lloydsBeacon = (LloydsBeacon)lloydsBeaconList.get(lloydsBeaconIndex);
            System.arraycopy(lloydsBeacon.getLloydsBeaconData(), 0, newData, offset, lloydsBeacon.getLloydsBeaconData().length);
            offset += lloydsBeacon.getLloydsBeaconData().length;
        }
        
        return offset;
    }

    public byte[] getLloydsBeaconData()
    {
        return this.lloydsBeaconData;
    }
    public int getLloydsBeaconOffset()
    {
        return this.lloydsBeaconOffset;
    }

    public static int getRecordSize()
    {
        return LLOYDS_BEACON_RECORD_SIZE;
    }


    // LloydsBeacon things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(LLOYDS_BEACON_SPELL_END_TIMESTAMP_OFFSET, 8, ComparativeTableControl.REPRESENTATION_INT_DEC, "End DateTime"));
        offsetList.add(new ComparativeTableControl.OffsetData(LLOYDS_BEACON_SPELL_X_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "X"));
        offsetList.add(new ComparativeTableControl.OffsetData(LLOYDS_BEACON_SPELL_Y_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(LLOYDS_BEACON_SPELL_Z_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Z"));
        offsetList.add(new ComparativeTableControl.OffsetData(LLOYDS_BEACON_SPELL_FACING_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Facing"));
        offsetList.add(new ComparativeTableControl.OffsetData(LLOYDS_BEACON_SPELL_TILT_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Tilt"));
        offsetList.add(new ComparativeTableControl.OffsetData(LLOYDS_BEACON_SPELL_UNK1_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC));
        offsetList.add(new ComparativeTableControl.OffsetData(LLOYDS_BEACON_SPELL_DESTINATION_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Destination"));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List lloydsBeaconList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return lloydsBeaconList.size();
            }

            public byte[] getData(int dataRow)
            {
                LloydsBeacon lloydsBeacon = (LloydsBeacon)lloydsBeaconList.get(dataRow);
                return lloydsBeacon.getLloydsBeaconData();
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
