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

import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.Vertex3DValueHolder;
import org.gamenet.util.ByteConversions;

public class Sprite implements Vertex3DValueHolder
{
    private static final short DECLIST_ID_OFFSET = 0; // two bytes
    private static final short AI_ATTRIBUTE_MARKERS_OFFSET = 2; // two bytes

    // 0, 0, 0 is center
    // short coords from -0x58 (-88) to 0x58 (88) are reachable, 
    private static final int EAST_WEST_OFFSET = 4; // four signed bytes, neg is west
    private static final int NORTH_SOUTH_OFFSET = 8; // four signed bytes, neg is south
    private static final int HEIGHT_OFFSET = 12; // four signed bytes
    private static final int FACING_OFFSET = 16; // four signed bytes
    
    private static final int EVENT1_OFFSET = 20;  // two bytes
    private static final int EVENT2_OFFSET = 22;  // two bytes

    private static final int VARIABLE1_OFFSET = 24;  // two bytes
    private static final int VARIABLE2_OFFSET = 26;  // two bytes -- sound counter?
    
    private static final int SPRITE_OBJECT_DATA_RECORD_LENGTH_MM6 = 28;
    
    private static final int SPECIAL_TRIGGER_OFFSET = 28;  // two bytes // mm7 & 8
    private static final int PADDING_OFFSET = 30;  // two bytes // mm7 & 8

    private static final int SPRITE_OBJECT_DATA_RECORD_LENGTH_MM7 = 32;
    
    private static final int NAME_MAX_LENGTH = 32;
    
    
    // following that is
    // xfe570 - 1041776
    // object maps
    // A = 4 byte record count
    // A * 28-byte records
    // A * 32-byte sprite resource names

    private int gameVersion = GameVersion.MM6;
    
    private long spriteDataOffset = 0;
    private long spriteNameOffset = 0;

    private byte spriteData[] = null;
    private String spriteName = null;
    
    public Sprite(int gameVersion)
    {
        super();
        
        this.gameVersion = gameVersion;
    }

    public Sprite(int gameVersion, String spriteName, int x, int y, int z, int eventNumber)
    {
        this(gameVersion);
        
        if (GameVersion.MM6 == gameVersion)
            this.spriteData = new byte[SPRITE_OBJECT_DATA_RECORD_LENGTH_MM6];
        else this.spriteData = new byte[SPRITE_OBJECT_DATA_RECORD_LENGTH_MM7];


        setSpriteName(spriteName);
        setX(x);
        setY(y);
        setZ(z);
        setEventNumber(eventNumber);
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.spriteDataOffset = offset;
        
        if (GameVersion.MM6 == gameVersion)
            this.spriteData = new byte[SPRITE_OBJECT_DATA_RECORD_LENGTH_MM6];
        else this.spriteData = new byte[SPRITE_OBJECT_DATA_RECORD_LENGTH_MM7];

        System.arraycopy(dataSrc, offset, this.spriteData, 0, this.spriteData.length);
        offset += this.spriteData.length;
        
        return offset;
    }

    /**
     * @param data
     * @param offset
     * @param spriteArray
     * @return
     */
    public static int populateObjects(int gameVersion, byte[] data, int offset, List spriteList)
    {
        int spriteCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int spriteIndex = 0; spriteIndex < spriteCount; ++spriteIndex)
        {
            Sprite sprite = new Sprite(gameVersion);
            offset = sprite.initialize(data, offset);
            spriteList.add(sprite);
        }
        
        for (int spriteIndex = 0; spriteIndex < spriteList.size(); ++spriteIndex)
        {
            Sprite sprite = (Sprite)spriteList.get(spriteIndex);
            sprite.spriteNameOffset = offset;
            sprite.spriteName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, offset, NAME_MAX_LENGTH);
            offset += NAME_MAX_LENGTH;
        }
        
        return offset;
    }
    
    /**
     * @param newData
     * @param offset
     * @param spriteArray
     * @return offset
     */
    public static int updateData(byte[] newData, int offset, List spriteList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(spriteList.size(), newData, offset);
        offset += 4;

        for (int spriteIndex = 0; spriteIndex < spriteList.size(); ++spriteIndex)
        {
            Sprite sprite = (Sprite)spriteList.get(spriteIndex);
            System.arraycopy(sprite.getSpriteData(), 0, newData, offset, sprite.getObjectRecordSize());
            offset += sprite.getObjectRecordSize();
        }
        
        for (int spriteIndex = 0; spriteIndex < spriteList.size(); ++spriteIndex)
        {
            Sprite sprite = (Sprite)spriteList.get(spriteIndex);
            ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(sprite.getSpriteName(), newData, offset, NAME_MAX_LENGTH);
            offset += NAME_MAX_LENGTH;
        }
        
        return offset;
    }

    public byte[] getSpriteData()
    {
        return this.spriteData;
    }
    
    public long getSpriteDataOffset()
    {
        return spriteDataOffset;
    }
    
    public String getSpriteName()
    {
        return this.spriteName;
    }
    public void setSpriteName(String spriteName)
    {
        this.spriteName = spriteName;
    }
    
    public long getSpriteNameOffset()
    {
        return spriteNameOffset;
    }

    public int getX()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getSpriteData(), EAST_WEST_OFFSET);
    }
    
    public void setX(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getSpriteData(), EAST_WEST_OFFSET);
    }
    
    public int getY()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getSpriteData(), NORTH_SOUTH_OFFSET);
    }
    
    public void setY(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getSpriteData(), NORTH_SOUTH_OFFSET);
    }
    
    public int getZ()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getSpriteData(), HEIGHT_OFFSET);
    }
    
    public void setZ(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getSpriteData(), HEIGHT_OFFSET);
    }

    public int getEventNumber()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getSpriteData(), EVENT2_OFFSET);
    }
    
    public void setEventNumber(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getSpriteData(), EVENT2_OFFSET);
    }

    public static int getRecordSize(int gameVersion)
    {
        if (GameVersion.MM6 == gameVersion)
            return SPRITE_OBJECT_DATA_RECORD_LENGTH_MM6 + NAME_MAX_LENGTH;
        else return SPRITE_OBJECT_DATA_RECORD_LENGTH_MM7 + NAME_MAX_LENGTH;
    }

    public int getObjectRecordSize()
    {
        if (GameVersion.MM6 == gameVersion)
            return SPRITE_OBJECT_DATA_RECORD_LENGTH_MM6;
        else return SPRITE_OBJECT_DATA_RECORD_LENGTH_MM7;
    }

    // Unknown things to decode

    public static List getOffsetList(int gameVersion)
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(DECLIST_ID_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "DecList ID #"));
        offsetList.add(new ComparativeTableControl.OffsetData(AI_ATTRIBUTE_MARKERS_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "attributes"));
        offsetList.add(new ComparativeTableControl.OffsetData(EAST_WEST_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "X"));
        offsetList.add(new ComparativeTableControl.OffsetData(NORTH_SOUTH_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(HEIGHT_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Z"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACING_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "facing"));
        offsetList.add(new ComparativeTableControl.OffsetData(EVENT1_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Event1 #"));
        offsetList.add(new ComparativeTableControl.OffsetData(EVENT2_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Event2 #"));
        offsetList.add(new ComparativeTableControl.OffsetData(VARIABLE1_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "variable1"));
        offsetList.add(new ComparativeTableControl.OffsetData(VARIABLE2_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "variable2"));

        if (GameVersion.MM6 != gameVersion)
        {
            offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_TRIGGER_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "special event trigger"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "unused padding"));
        }

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List spriteList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return spriteList.size();
            }

            public byte[] getData(int dataRow)
            {
                Sprite sprite = (Sprite)spriteList.get(dataRow);
                return sprite.getSpriteData();
            }

            public int getAdjustedDataRowIndex(int dataRow)
            {
                return dataRow;
            }
            
            public String getIdentifier(int dataRow)
            {
                Sprite sprite = (Sprite)spriteList.get(dataRow);
                return sprite.getSpriteName();
            }

            public int getOffset(int dataRow)
            {
                return 0;
            }
        };
    }

}
