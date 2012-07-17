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

package org.gamenet.application.mm8leveleditor.data.mm6.outdoor;

import java.util.ArrayList;
import java.util.List;

import org.gamenet.application.mm8leveleditor.data.DataSizeException;
import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.application.mm8leveleditor.data.mm6.ChestContents;
import org.gamenet.application.mm8leveleditor.data.mm6.Creature;
import org.gamenet.application.mm8leveleditor.data.mm6.Item;
import org.gamenet.application.mm8leveleditor.data.mm6.MapNote;
import org.gamenet.util.ByteConversions;

public class DeltaOutdoorDataMap
{
    private static final int MAP_RESET_COUNT_OFFSET = 0; // 4 bytes
    private static final int LAST_RESET_DAY_OFFSET = 4; // 4 bytes

    // MM7 and MM8 values
    private static final int STEALING_DIFFICULTY_ADJUSTMENT_OFFSET = 8; // 4 bytes // MM7,MM8
    private static final int MAP_ON_ALERT_OFFSET = 12; // 4? 2? 1? bytes // MM7,MM8
    // These values must match values in DataHeader!
    private static final int TOTAL_NUMBER_OF_FACETS_IN_D3OBJECTS_OFFSET = 16; // 4 bytes // MM7,MM8, padding otherwise
    private static final int TOTAL_NUMBER_OF_SPRITES_OFFSET = 20; // 4 bytes // MM7,MM8, padding otherwise
    private static final int TOTAL_NUMBER_OF_D3OBJECTS_OFFSET = 24; // 4 bytes // MM7,MM8, padding otherwise
    private static final int MAPNOTES_OFFSET_OFFSET = 28; // 4 bytes // MM8, padding otherwise
    private static final int PADDING_OFFSET = 32; // 8 bytes
    
    public static final int VISIBLE_MAP_PIXELS_PER_BYTE = 8;
    public static final int VISIBLE_MAP_WIDTH = 11; 
    public static final int VISIBLE_MAP_HEIGHT = 88;
    
    // private static final int CREATURE_COUNT_OFFSET = 0x0798; // 4 bytes (1944)
    
    private int gameVersion = 0;
    
    private int mapResetCount = 0;
    private int lastResetDay = 0;
    private int stealingDifficultyAdjustment = 0;
    private int mapAlertStatus = 0;
    private int totalNumberOfFacetsInD3Objects = 0;
    private int totalNumberOfSprites = 0;
    private int totalNumberOfD3Objects = 0;
    private int mapNotesOffset = 0;
    
    private int reserved1 = 0;
    private int reserved2 = 0;
    
    private byte visibleMapData1[][] = null;
    private byte visibleMapData2[][] = null;
    
    private int facetAttributeArray[] = null;
    private short spriteAttributeArray[] = null;
    
    private List creatureList = null;
    private List itemList = null;
    
    private /*ChestContents*/ List chestContentsList = null;
    
    private static final int NUMBER_OF_MAP_BITS = 200;
    private byte mapBits[] = null;
    
    private long lastVisitedTime;
    
    private static final int SKY_BITMAP_MAX_LENGTH = 12;
    private String skyBitmapName;
    
    private int dayAttribute;
    private int fogRange1;
    private int fogRange2;
    
    private int attributes; // MM8
    private int ceiling;// MM8
    
    private static final int REMAINING_DATA_LENGTH = 16;
    private byte remainingData[] = null; // unused?
    
    private long zeroesDataOffset = 0;
    private long creatureDataOffset = 0;
    private long itemDataOffset = 0;
    private long chestContentsOffset = 0;
    private long remainingDataOffset = 0;

    private static final int NUMBER_OF_MAP_NOTES = 100;
    private List mapNoteList;
    private int activeMapNoteCount;
    
    public DeltaOutdoorDataMap(int gameVersion, byte data[])
    {
        this(gameVersion, data, -1, -1);
    }
    
    public DeltaOutdoorDataMap(int gameVersion, byte data[], int totalSpriteCount, int totalD3ObjectFacetsCount)
    {
        super();
        this.gameVersion = gameVersion;
        
        int offset = 0;
        this.mapResetCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        this.lastResetDay = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        if (GameVersion.MM6 != gameVersion)
        {
            this.stealingDifficultyAdjustment = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;

            this.mapAlertStatus = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;

            this.totalNumberOfFacetsInD3Objects = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;

            this.totalNumberOfSprites = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;

            this.totalNumberOfD3Objects = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;

            // mapNotesOffset not used for MM7
            this.mapNotesOffset = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;

            this.reserved1 = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;

            this.reserved2 = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
        }

        visibleMapData1 = new byte[VISIBLE_MAP_HEIGHT][VISIBLE_MAP_WIDTH];
        for (int row = 0; row < VISIBLE_MAP_HEIGHT; row++)
        {
            System.arraycopy(data, offset, visibleMapData1[row], 0, VISIBLE_MAP_WIDTH);
            offset += VISIBLE_MAP_WIDTH;
        }
        
        visibleMapData2 = new byte[VISIBLE_MAP_HEIGHT][VISIBLE_MAP_WIDTH];
        for (int row = 0; row < VISIBLE_MAP_HEIGHT; row++)
        {
            System.arraycopy(data, offset, visibleMapData2[row], 0, VISIBLE_MAP_WIDTH);
            offset += VISIBLE_MAP_WIDTH;
        }
        
        if (GameVersion.MM6 != gameVersion)
        {
            if (-1 == totalD3ObjectFacetsCount)
                totalD3ObjectFacetsCount = totalNumberOfFacetsInD3Objects;

            if (-1 == totalSpriteCount)
                totalSpriteCount = totalNumberOfSprites;
            
            facetAttributeArray = new int[totalD3ObjectFacetsCount];
            for (int d3ObjectIndex = 0; d3ObjectIndex < totalD3ObjectFacetsCount; d3ObjectIndex++)
            // for (int d3ObjectIndex = 0; d3ObjectIndex < d3ObjectCount; d3ObjectIndex++)
            {
                // for (int facetIndex = 0; facetIndex < d3Object[d3ObjectIndex].facetCount; facetIndex++)
                {
                    facetAttributeArray[d3ObjectIndex] = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
                    offset += 4;

                    // d3Object[d3ObjectIndex].getFacet(facetIndex).setAttribute(attribute);
                }
            }
            
            spriteAttributeArray = new short[totalSpriteCount];
            for (int spriteIndex = 0; spriteIndex < totalSpriteCount; spriteIndex++)
            // for (int spriteIndex = 0; spriteIndex < spriteCount; spriteIndex++)
            {
                // read one short for every Sprite.
                spriteAttributeArray[spriteIndex] = ByteConversions.getShortInByteArrayAtPosition(data, offset);
                offset += 2;

                // sprite[spriteIndex].setAttribute(attribute);
            }
        }
        
        // Creatures
        creatureList = new ArrayList();
        offset = Creature.populateObjects(gameVersion, data, offset, creatureList);
        
        // Items
        itemList = new ArrayList();
        this.itemDataOffset = offset;
        offset = Item.populateObjects(gameVersion, data, offset, itemList);
        
        // Chest Contents
        chestContentsList = new ArrayList();
        offset = ChestContents.populateObjects(gameVersion, data, offset, chestContentsList);
        
        this.mapBits = new byte[NUMBER_OF_MAP_BITS];
        this.mapResetCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += NUMBER_OF_MAP_BITS;

        this.lastVisitedTime = ByteConversions.getLongInByteArrayAtPosition(data, offset);
        offset += 8;
        
        this.skyBitmapName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, offset, SKY_BITMAP_MAX_LENGTH);
        offset += 12;
        
        this.dayAttribute = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;
        
        this.fogRange1 = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;
        
        this.fogRange2 = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;
        
        this.attributes = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;
        
        this.ceiling = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;
        
        this.remainingDataOffset = offset;
        this.remainingData = new byte[REMAINING_DATA_LENGTH];
        System.arraycopy(data, offset, remainingData, 0, remainingData.length);
        offset += remainingData.length;
        
        if ((GameVersion.MM6 != gameVersion) && (GameVersion.MM7 != gameVersion))
        {
            // Map Notes
            this.mapNoteList = new ArrayList();
            offset = MapNote.populateObjects(data, offset, this.mapNoteList, NUMBER_OF_MAP_NOTES);
            
            // Sometimes not there????
            if (data.length != offset)
            {
                this.activeMapNoteCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
                offset += 4;
            }
        }
        
        // Insure we've read all the data
        if (offset != data.length)  throw new RuntimeException("offset<" + offset + "> != file.length<" + data.length + ">");
    }
    
    public static boolean checkDataIntegrity(int gameVersion, byte[] data, int offset, int expectedNewOffset)
    {
        if (GameVersion.MM6 != gameVersion)
            throw new RuntimeException("Integrity check failed for game version=" + gameVersion + ", must specify total counts.");
       
        return checkDataIntegrity(gameVersion, data, offset, expectedNewOffset, 0, 0);
    }
    
    public static boolean checkDataIntegrity(int gameVersion, byte[] data, int offset, int expectedNewOffset, int totalSpriteCount, int totalD3ObjectFacetsCount)
    {
        try
        {
            offset += 4;
            offset += 4;
            
            if (GameVersion.MM6 != gameVersion)
            {
                offset += 4;
                offset += 4;

                int value = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
                if (-1 == totalD3ObjectFacetsCount)
                    totalD3ObjectFacetsCount = value;
                offset += 4;

                value = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
                if (-1 == totalSpriteCount)
                    totalSpriteCount = value;
                offset += 4;

                offset += 4;
                offset += 4;
                offset += 4;
                offset += 4;
            }
            
            offset += VISIBLE_MAP_HEIGHT * VISIBLE_MAP_WIDTH;
            offset += VISIBLE_MAP_HEIGHT * VISIBLE_MAP_WIDTH;
            
            if (GameVersion.MM6 != gameVersion)
            {
                offset += totalD3ObjectFacetsCount * 4;
                offset += totalSpriteCount * 2;
            }
            
            int creatureCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            offset += creatureCount * Creature.getRecordSize(gameVersion);
            
            int itemCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            offset += itemCount * Item.getRecordSize(gameVersion);
            
            int chestContentsCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            offset += chestContentsCount * ChestContents.getRecordSize(gameVersion);
            
            offset += NUMBER_OF_MAP_BITS;

            offset += 8;
            
            offset += SKY_BITMAP_MAX_LENGTH;
            
            offset += 4;
            
            offset += 4;
            
            offset += 4;
            
            offset += 4;
            
            offset += 4;
            
            offset += REMAINING_DATA_LENGTH;

            if ((GameVersion.MM6 != gameVersion) && (GameVersion.MM7 != gameVersion))
            {
                offset += NUMBER_OF_MAP_NOTES * MapNote.getRecordSize();
                
                // Sometimes not there????
                if (expectedNewOffset != offset)
                {
                    offset += 4;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException exception)
        {
            new RuntimeException("Integrity check failed for game version=" + gameVersion + ".", exception).printStackTrace();
            return false;
        }
        catch (DataSizeException exception)
        {
            new RuntimeException("Integrity check failed for game version=" + gameVersion + ".", exception).printStackTrace();
            return false;
        }
        
        if (offset != expectedNewOffset)
        {
            new RuntimeException("Integrity check failed for game version=" + gameVersion + ": offset<" + offset + "> != expectedNewOffset<" + expectedNewOffset + ">").printStackTrace();
        }
        
        return (offset == expectedNewOffset);
    }
    
    public byte[] updateData(byte oldData[])
    {
        int newDataSize = 0;
        
        newDataSize += 4;
        newDataSize += 4;
        
        if (GameVersion.MM6 != gameVersion)
        {
            newDataSize += 4;
            newDataSize += 4;

            newDataSize += 4;

            newDataSize += 4;

            newDataSize += 4;
            newDataSize += 4;
            newDataSize += 4;
            newDataSize += 4;
        }
        
        newDataSize += VISIBLE_MAP_HEIGHT * VISIBLE_MAP_WIDTH;
        newDataSize += VISIBLE_MAP_HEIGHT * VISIBLE_MAP_WIDTH;
        
        if (GameVersion.MM6 != gameVersion)
        {
            newDataSize += facetAttributeArray.length * 4;
            newDataSize += spriteAttributeArray.length * 2;
        }
        
        newDataSize += 4;
        newDataSize += creatureList.size() * Creature.getRecordSize(gameVersion);
        
        newDataSize += 4;
        newDataSize += itemList.size() * Item.getRecordSize(gameVersion);
        
        newDataSize += 4;
        newDataSize += chestContentsList.size() * ChestContents.getRecordSize(gameVersion);
        
        newDataSize += NUMBER_OF_MAP_BITS;

        newDataSize += 8;
        
        newDataSize += SKY_BITMAP_MAX_LENGTH;
        
        newDataSize += 4;
        
        newDataSize += 4;
        
        newDataSize += 4;
        
        newDataSize += 4;
        
        newDataSize += 4;
        
        newDataSize += REMAINING_DATA_LENGTH;

        if ((GameVersion.MM6 != gameVersion) && (GameVersion.MM7 != gameVersion))
        {
            // Map Notes
            newDataSize += mapNoteList.size() * MapNote.getRecordSize();
            newDataSize += 4;
        }

        byte newData[] = new byte[newDataSize];
        
        int offset = 0;
        
        ByteConversions.setIntegerInByteArrayAtPosition(mapResetCount, newData, offset);
        offset += 4;
        
        ByteConversions.setIntegerInByteArrayAtPosition(lastResetDay, newData, offset);
        offset += 4;

        if (GameVersion.MM6 != gameVersion)
        {
            ByteConversions.setIntegerInByteArrayAtPosition(this.stealingDifficultyAdjustment, newData, offset);
            offset += 4;

            ByteConversions.setIntegerInByteArrayAtPosition(this.mapAlertStatus, newData, offset);
            offset += 4;

            ByteConversions.setIntegerInByteArrayAtPosition(this.facetAttributeArray.length, newData, offset);
            offset += 4;

            ByteConversions.setIntegerInByteArrayAtPosition(this.spriteAttributeArray.length, newData, offset);
            offset += 4;

            ByteConversions.setIntegerInByteArrayAtPosition(this.totalNumberOfD3Objects, newData, offset);
            offset += 4;

            // mapNotesOffset not used for MM7
            ByteConversions.setIntegerInByteArrayAtPosition(this.mapNotesOffset, newData, offset);
            offset += 4;

            ByteConversions.setIntegerInByteArrayAtPosition(this.reserved1, newData, offset);
            offset += 4;

            ByteConversions.setIntegerInByteArrayAtPosition(this.reserved2, newData, offset);
            offset += 4;
        }

        for (int row = 0; row < visibleMapData1.length; row++)
        {
            System.arraycopy(visibleMapData1[row], 0, newData, offset, VISIBLE_MAP_WIDTH);
            offset += VISIBLE_MAP_WIDTH;
        }
        
        for (int row = 0; row < visibleMapData2.length; row++)
        {
            System.arraycopy(visibleMapData2[row], 0, newData, offset, VISIBLE_MAP_WIDTH);
            offset += VISIBLE_MAP_WIDTH;
        }
        
        if (GameVersion.MM6 != gameVersion)
        {
            for (int d3ObjectIndex = 0; d3ObjectIndex < facetAttributeArray.length; d3ObjectIndex++)
            // for (int d3ObjectIndex = 0; d3ObjectIndex < d3ObjectCount; d3ObjectIndex++)
            {
                // for (int facetIndex = 0; facetIndex < d3Object[d3ObjectIndex].facetCount; facetIndex++)
                {
                    ByteConversions.setIntegerInByteArrayAtPosition(facetAttributeArray[d3ObjectIndex], newData, offset);
                    offset += 4;

                    // d3Object[d3ObjectIndex].getFacet(facetIndex).setAttribute(attribute);
                }
            }
            
            for (int spriteIndex = 0; spriteIndex < spriteAttributeArray.length; spriteIndex++)
            // for (int spriteIndex = 0; spriteIndex < spriteCount; spriteIndex++)
            {
                // read one short for every Sprite.
                ByteConversions.setShortInByteArrayAtPosition(spriteAttributeArray[spriteIndex], newData, offset);
                offset += 2;

                // sprite[spriteIndex].setAttribute(attribute);
            }
        }
        
        offset = Creature.updateData(newData, offset, creatureList);
        
        offset = Item.updateData(newData, offset, itemList);
        
        offset = ChestContents.updateData(newData, offset, chestContentsList);
        
        System.arraycopy(mapBits, 0, newData, offset, mapBits.length);
        offset += mapBits.length;

        ByteConversions.setLongInByteArrayAtPosition(this.lastVisitedTime, newData, offset);
        offset += 8;
        
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(this.skyBitmapName, newData, offset, SKY_BITMAP_MAX_LENGTH);
        offset += 12;
        
        ByteConversions.setIntegerInByteArrayAtPosition(this.dayAttribute, newData, offset);
        offset += 4;
        
        ByteConversions.setIntegerInByteArrayAtPosition(this.fogRange1, newData, offset);
        offset += 4;
        
        ByteConversions.setIntegerInByteArrayAtPosition(this.fogRange2, newData, offset);
        offset += 4;

        ByteConversions.setIntegerInByteArrayAtPosition(this.attributes, newData, offset);
        offset += 4;
        
        ByteConversions.setIntegerInByteArrayAtPosition(this.ceiling, newData, offset);
        offset += 4;

        System.arraycopy(remainingData, 0, newData, offset, remainingData.length);
        offset += remainingData.length;
        
        if ((GameVersion.MM6 != gameVersion) && (GameVersion.MM7 != gameVersion))
        {
            // Map Notes
            offset = MapNote.updateData(newData, offset, this.mapNoteList);
            
            ByteConversions.setIntegerInByteArrayAtPosition(this.activeMapNoteCount, newData, offset);
            offset += 4;
        }
        
        return newData;
    }

    public int getMapResetCount()
    {
        return this.mapResetCount;
    }
    public void setMapResetCount(int unknown1)
    {
        this.mapResetCount = unknown1;
    }
    public int getLastResetDay()
    {
        return this.lastResetDay;
    }
    public void setLastResetDay(int unknown2)
    {
        this.lastResetDay = unknown2;
    }

    public byte[][] getVisibleMapData1()
    {
        return this.visibleMapData1;
    }
    public byte[][] getVisibleMapData2()
    {
        return this.visibleMapData2;
    }
    public List getCreatureList()
    {
        return this.creatureList;
    }
    public List getItemList()
    {
        return this.itemList;
    }

    public List getChestContentsList()
    {
        return this.chestContentsList;
    }

    public byte[] getRemainingData()
    {
        return this.remainingData;
    }

    public long getCreatureDataOffset()
    {
        return this.creatureDataOffset;
    }
    public long getRemainingDataOffset()
    {
        return this.remainingDataOffset;
    }
    public long getChestContentsOffset()
    {
        return this.chestContentsOffset;
    }
    public long getZeroesDataOffset()
    {
        return this.zeroesDataOffset;
    }
    
    public int getDayAttribute()
    {
        return this.dayAttribute;
    }
    public void setDayAttribute(int dayAttribute)
    {
        this.dayAttribute = dayAttribute;
    }
    public int getFogRange1()
    {
        return this.fogRange1;
    }
    public void setFogRange1(int fogRange1)
    {
        this.fogRange1 = fogRange1;
    }
    public int getFogRange2()
    {
        return this.fogRange2;
    }
    public void setFogRange2(int fogRange2)
    {
        this.fogRange2 = fogRange2;
    }
    public long getLastVisitedTime()
    {
        return this.lastVisitedTime;
    }
    public void setLastVisitedTime(long lastVisitedTime)
    {
        this.lastVisitedTime = lastVisitedTime;
    }
    public String getSkyBitmapName()
    {
        return this.skyBitmapName;
    }
    public void setSkyBitmapName(String skyBitmapName)
    {
        this.skyBitmapName = skyBitmapName;
    }
    
    public int getSkyBitmapNameMaxLength()
    {
        return SKY_BITMAP_MAX_LENGTH;
    }
    
    public byte[] getMapBits()
    {
        return this.mapBits;
    }
    
    public int getMapBitsCount()
    {
        return NUMBER_OF_MAP_BITS;
    }
    
    // 0-based mapBits
    public byte getMapBit(int mapBitNumber)
    {
        return this.mapBits[mapBitNumber];
    }

    public void setMapBit(byte value, int mapBitNumber)
    {
        this.mapBits[mapBitNumber] = value;
    }
    public int getGameVersion()
    {
        return this.gameVersion;
    }
    public int getAttributes()
    {
        return this.attributes;
    }
    public void setAttributes(int attributes)
    {
        this.attributes = attributes;
    }
    public int getCeiling()
    {
        return this.ceiling;
    }
    public void setCeiling(int ceiling)
    {
        this.ceiling = ceiling;
    }
    public int[] getFacetAttributeArray()
    {
        return this.facetAttributeArray;
    }
    public List getMapNoteList()
    {
        return this.mapNoteList;
    }
    public short[] getSpriteAttributeArray()
    {
        return this.spriteAttributeArray;
    }
    public int getActiveMapNoteCount()
    {
        return this.activeMapNoteCount;
    }
    public void setActiveMapNoteCount(int activeMapNoteCount)
    {
        this.activeMapNoteCount = activeMapNoteCount;
    }
    public int getMapAlertStatus()
    {
        return this.mapAlertStatus;
    }
    public void setMapAlertStatus(int mapAlertStatus)
    {
        this.mapAlertStatus = mapAlertStatus;
    }
    public int getMapNotesOffset()
    {
        return this.mapNotesOffset;
    }
    public void setMapNotesOffset(int mapNotesOffset)
    {
        this.mapNotesOffset = mapNotesOffset;
    }
    public int getReserved1()
    {
        return this.reserved1;
    }
    public void setReserved1(int reserved1)
    {
        this.reserved1 = reserved1;
    }
    public int getReserved2()
    {
        return this.reserved2;
    }
    public void setReserved2(int reserved2)
    {
        this.reserved2 = reserved2;
    }
    public int getStealingDifficultyAdjustment()
    {
        return this.stealingDifficultyAdjustment;
    }
    public void setStealingDifficultyAdjustment(int stealingDifficultyAdjustment)
    {
        this.stealingDifficultyAdjustment = stealingDifficultyAdjustment;
    }
    public int getTotalNumberOfD3Objects()
    {
        return this.totalNumberOfD3Objects;
    }
    public void setTotalNumberOfD3Objects(int totalNumberOfD3Objects)
    {
        this.totalNumberOfD3Objects = totalNumberOfD3Objects;
    }
    public int getTotalNumberOfFacetsInD3Objects()
    {
        return this.totalNumberOfFacetsInD3Objects;
    }
    public void setTotalNumberOfFacetsInD3Objects(
            int totalNumberOfFacetsInD3Objects)
    {
        this.totalNumberOfFacetsInD3Objects = totalNumberOfFacetsInD3Objects;
    }
    public int getTotalNumberOfSprites()
    {
        return this.totalNumberOfSprites;
    }
    public void setTotalNumberOfSprites(int totalNumberOfSprites)
    {
        this.totalNumberOfSprites = totalNumberOfSprites;
    }
}
