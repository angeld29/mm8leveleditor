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

public class Follower
{
    private static final int FOLLOWER_RECORD_LENGTH = 60;

    private static int FOLLOWER_PICTURE_NUMBER_OFFSET = 4; // 4 bytes
    private static int FOLLOWER_PROFESSION_NUMBER_OFFSET = 24; // 4 bytes

    private static int HIRE_FOLLOWER1a_OFFSET = 23348; // 4x1? bytes  104,104,91,1
    private static int FOLLOWER1_PICTURE_NUMBER_OFFSET = 23352; // 4 bytes
    private static int HIRE_FOLLOWER1b_OFFSET = 23356; // 4? bytes  131 or -125
    private static int HIRE_FOLLOWER1c_OFFSET = 23364; // 4? bytes  -44?
    private static int FOLLOWER1_PROFESSION_NUMBER_OFFSET = 23372; // 4 bytes
    private static int HIRE_FOLLOWER1d_OFFSET = 23376; // 4? bytes  1
    private static int HIRE_FOLLOWER1e_OFFSET = 23380; // 4? bytes  1
    private static int HIRE_FOLLOWER1g_OFFSET = 23404; // 4? bytes  14
    
    private static int HIRE_FOLLOWER2a_OFFSET = 23408; // (191 or -65), 93, 91, 1
    private static int FOLLOWER2_PICTURE_NUMBER_OFFSET = 23412; // 4 bytes
    private static int HIRE_FOLLOWER2b_OFFSET = 23416; // 129 or -127
    private static int FOLLOWER2_PROFESSION_NUMBER_OFFSET = 23432;
    private static int HIRE_FOLLOWER2d_OFFSET = 23436; // 1
    private static int HIRE_FOLLOWER2e_OFFSET = 23440; // 1
    private static int HIRE_FOLLOWER2f_OFFSET = 23456; // 1
    private static int HIRE_FOLLOWER2g_OFFSET = 23464; // 18

    private static int FOLLOWER_NAME_MAXLENGTH = 100;

    private int followerOffset = 0;
    private byte followerData[] = null;
    private String followerName = null;

    public Follower()
    {
        super();
    }

    public Follower(int size)
    {
        super();
        this.followerData = new byte[size];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.followerOffset = offset;
        this.followerData = new byte[FOLLOWER_RECORD_LENGTH];
        System.arraycopy(dataSrc, offset, this.followerData, 0, this.followerData.length);
        offset += this.followerData.length;
        
        return offset;
    }

    public int initializeName(byte dataSrc[], int offset)
    {
        followerName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(dataSrc, offset, FOLLOWER_NAME_MAXLENGTH);
        for (int index = followerName.length(); index < FOLLOWER_NAME_MAXLENGTH; ++index)
        {
            if (dataSrc[offset + index] != 0)
            {
                System.out.println("Unexpectedly found non-zero character <" + String.valueOf(dataSrc[offset + index]) + "> at index <" + String.valueOf(index) + ">, offset <" + String.valueOf(offset + index) + "> after follower name.");
            }
        }
        offset += FOLLOWER_NAME_MAXLENGTH;
        
        return offset;
    }

    public String getFollowerName()
    {
        return this.followerName;
    }
    public void setFollowerName(String followerName)
    {
        this.followerName = followerName;
    }

    public int getPictureNumber()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getFollowerData(), FOLLOWER_PICTURE_NUMBER_OFFSET);
    }
    public void setPictureNumber(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getFollowerData(), FOLLOWER_PICTURE_NUMBER_OFFSET);
    }
    
    public int getProfessionNumber()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getFollowerData(), FOLLOWER_PROFESSION_NUMBER_OFFSET);
    }
    public void setProfessionNumber(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getFollowerData(), FOLLOWER_PROFESSION_NUMBER_OFFSET);
    }
    
    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
        offset += FOLLOWER_RECORD_LENGTH;
        
        return (offset == expectedNewOffset);
    }
    
    public static boolean checkDataIntegrityNames(byte[] data, int offset, int expectedNewOffset)
    {
        offset += FOLLOWER_NAME_MAXLENGTH;
        
        return (offset == expectedNewOffset);
    }
    
    public static int populateObjects(byte[] data, int offset, List followerList, int followerCount)
    {
        for (int followerIndex = 0; followerIndex < followerCount; ++followerIndex)
        {
            Follower follower = new Follower();
            followerList.add(follower);
            offset = follower.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int addNames(byte[] data, int offset, List followerList)
    {
        for (int followerIndex = 0; followerIndex < followerList.size(); ++followerIndex)
        {
            Follower follower = (Follower)followerList.get(followerIndex);
            offset = follower.initializeName(data, offset);
        }
        
        return offset;
    }
    
    public static int updateDataNames(byte[] newData, int offset, List followerList)
    {
        for (int followerIndex = 0; followerIndex < followerList.size(); ++followerIndex)
        {
            Follower follower = (Follower)followerList.get(followerIndex);
            ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(follower.getFollowerName(), newData, offset, FOLLOWER_NAME_MAXLENGTH);
            offset += FOLLOWER_NAME_MAXLENGTH;
        }
        
        return offset;
    }

    public static int updateData(byte[] newData, int offset, List followerList)
    {
        for (int followerIndex = 0; followerIndex < followerList.size(); ++followerIndex)
        {
            Follower follower = (Follower)followerList.get(followerIndex);
            System.arraycopy(follower.getFollowerData(), 0, newData, offset, follower.getFollowerData().length);
            offset += follower.getFollowerData().length;
        }
        
        return offset;
    }

    public byte[] getFollowerData()
    {
        return this.followerData;
    }
    public int getFollowerOffset()
    {
        return this.followerOffset;
    }

    public static int getRecordSize()
    {
        return FOLLOWER_RECORD_LENGTH;
    }

    public static int getRecordSizeName()
    {
        return FOLLOWER_NAME_MAXLENGTH;
    }

    // Follower things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(0, 4, ComparativeTableControl.REPRESENTATION_BYTE_DEC));
        offsetList.add(new ComparativeTableControl.OffsetData(8, 16, ComparativeTableControl.REPRESENTATION_INT_DEC));
        offsetList.add(new ComparativeTableControl.OffsetData(28, 32, ComparativeTableControl.REPRESENTATION_INT_DEC));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List followerList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return followerList.size();
            }

            public byte[] getData(int dataRow)
            {
                Follower follower = (Follower)followerList.get(dataRow);
                return follower.getFollowerData();
            }

            public int getAdjustedDataRowIndex(int dataRow)
            {
                return dataRow;
            }
            
            public String getIdentifier(int dataRow)
            {
                Follower follower = (Follower)followerList.get(dataRow);
                return "#" + String.valueOf(dataRow + 1) + ":" + follower.getFollowerName();
            }

            public int getOffset(int dataRow)
            {
                return 0;
            }
        };
    }
}
