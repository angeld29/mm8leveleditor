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
import org.gamenet.application.mm8leveleditor.data.mm6.SpawnPoint;
import org.gamenet.application.mm8leveleditor.data.mm6.Sprite;
import org.gamenet.util.ByteConversions;

public class OutdoorDataMap
{    
    private static final int BLANK_OFFSET = 0; // 32 bytes
    private static final int BLANK_MAX_LENGTH = 32;
    private static final int DEFAULT_ODM_OFFSET = 32; // 32 bytes
    private static final int DEFAULT_ODM_MAX_LENGTH = 32;
    private static final int EDITOR_OFFSET = 64; // 32 bytes
    private static final int EDITOR_MAX_LENGTH_MM6 = 32;
    private static final int EDITOR_MAX_LENGTH_MM8 = 31;
    private static final int SKY_BITMAP_OFFSET = 96; // 32 bytes
    private static final int SKY_BITMAP_MAX_LENGTH = 32;
    private static final int GROUND_BITMAP_OFFSET = 128; // 32 bytes
    private static final int GROUND_BITMAP_MAX_LENGTH = 32;
    
    // 0xa0 appears to be ground-area related
    // 0xa4 appears to be water-area related
    private static final int TILE_SET_SELECTOR_OFFSET = 160; 
    
    private static final int NUMBER_OF_TILE_SET_SELECTORS = 4;
    private TileSetSelector tileSetSelectorArray[] = null;
    

    private static final int MAP_WIDTH = 128;
    private static final int MAP_HEIGHT = 128;
    
    private static final int HEIGHT_MAP_OFFSET = 0x00B0; // 0x4000 bytes
    private static final int HEIGHT_MAP_LENGTH = 0x4000;
    
    // icons.lod / dtiles.bin appear to be the values
    private static final int TILE_MAP_OFFSET = 0x40B0; // 0x4000 bytes
    private static final int TILE_MAP_LENGTH = 0x4000;
    
    // On MM8, the first 4 bytes appear to contain data
    private static final int ATTRIBUTE_MAP_OFFSET = 0x80B0; // 0x4000 bytes
    private static final int ATTRIBUTE_MAP_LENGTH = 0x4000;
    
    private int gameVersion = GameVersion.MM6;

    private String blankString = null;
    private String defaultOdmString = null;
    private String editorString = null; // 32 bytes -- mm6/mm7, 31 bytes -- mm8
	private byte masterTile;			// mm8

	private String skyBitmapName = null;
    private String groundBitmapName = null;
    
	private int attributes; // 4 bytes // mm8

    // heightmap value * 512 = height coordinate
    private byte heightMap[][] = null;

    private byte tileMap[][] = null;
    
    private byte attributeMap[][] = null;
    
    private TerrainNormalMapData terrainNormalMapData = null;
    
    private List d3ObjectList = null;
    
    private List spriteList = null;
    
    private UnknownMapData unknownMapData = null;
    
    private List spawnPointList = null;
    
    public OutdoorDataMap(int gameVersion, byte data[])
    {
        this.gameVersion = gameVersion;
        
        int offset = 0;

        blankString = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, BLANK_OFFSET, BLANK_MAX_LENGTH);
        offset += BLANK_MAX_LENGTH;
        
        defaultOdmString = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, DEFAULT_ODM_OFFSET, DEFAULT_ODM_MAX_LENGTH);
        offset += DEFAULT_ODM_MAX_LENGTH;
        
        if ((6 == gameVersion) || (7 == gameVersion))
        {
            editorString = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, EDITOR_OFFSET, EDITOR_MAX_LENGTH_MM6);
            offset += EDITOR_MAX_LENGTH_MM6;
        }
        else
        {
            editorString = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, EDITOR_OFFSET, EDITOR_MAX_LENGTH_MM8);
            offset += EDITOR_MAX_LENGTH_MM8;
            masterTile = data[offset];
            offset += 1;
        }

        skyBitmapName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, SKY_BITMAP_OFFSET, SKY_BITMAP_MAX_LENGTH);
        offset += SKY_BITMAP_MAX_LENGTH;
        
        groundBitmapName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, GROUND_BITMAP_OFFSET, GROUND_BITMAP_MAX_LENGTH);
        offset += GROUND_BITMAP_MAX_LENGTH;
        
        tileSetSelectorArray = new TileSetSelector[NUMBER_OF_TILE_SET_SELECTORS];
        for (int index = 0; index < tileSetSelectorArray.length; index++)
        {
            short group = ByteConversions.getShortInByteArrayAtPosition(data, TILE_SET_SELECTOR_OFFSET + (index * 4));
            short id = ByteConversions.getShortInByteArrayAtPosition(data, TILE_SET_SELECTOR_OFFSET + ((index * 4) + 2));
            tileSetSelectorArray[index] = new TileSetSelector(group, id);
            offset += 4;
        }

        if ((6 != gameVersion) && (7 != gameVersion))
        {
            attributes = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
        }

        heightMap = new byte[MAP_HEIGHT][MAP_WIDTH];
        for (int heightMapIndex = 0; heightMapIndex < heightMap.length; heightMapIndex++)
        {
            System.arraycopy(data, HEIGHT_MAP_OFFSET + (heightMap[heightMapIndex].length * heightMapIndex), heightMap[heightMapIndex], 0, heightMap[heightMapIndex].length);
            offset += MAP_HEIGHT;
        }
        
        tileMap = new byte[MAP_HEIGHT][MAP_WIDTH];
        for (int tileMapIndex = 0; tileMapIndex < tileMap.length; tileMapIndex++)
        {
            System.arraycopy(data, TILE_MAP_OFFSET + (tileMap[tileMapIndex].length * tileMapIndex), tileMap[tileMapIndex], 0, tileMap[tileMapIndex].length);
            offset += MAP_HEIGHT;
        }
        
        attributeMap = new byte[MAP_HEIGHT][MAP_WIDTH];
        for (int attributeMapIndex = 0; attributeMapIndex < attributeMap.length; attributeMapIndex++)
        {
            System.arraycopy(data, ATTRIBUTE_MAP_OFFSET + (attributeMap[attributeMapIndex].length * attributeMapIndex), attributeMap[attributeMapIndex], 0, attributeMap[attributeMapIndex].length);
            offset += MAP_HEIGHT;
        }
        
        if (6 != gameVersion)
        {
            terrainNormalMapData = new TerrainNormalMapData();
            offset = terrainNormalMapData.initialize(data, offset, MAP_WIDTH, MAP_HEIGHT);
        }

        // 3d objects
        d3ObjectList = new ArrayList();
        offset = D3Object.populateObjects(gameVersion, data, offset, d3ObjectList);
        
        // Sprites
        spriteList = new ArrayList();
        offset = Sprite.populateObjects(gameVersion, data, offset, spriteList);
        
        // some mapping data
        unknownMapData = new UnknownMapData();
        offset = unknownMapData.initialize(data, offset, MAP_WIDTH, MAP_HEIGHT);
        
        // monsters
        spawnPointList = new ArrayList();
        offset = SpawnPoint.populateObjects(gameVersion, data, offset, spawnPointList);

        
        // Insure we've read all the data
        if (offset != data.length)  throw new RuntimeException("offset<" + offset + "> != file.length<" + data.length + ">");
    }

    public static boolean checkDataIntegrity(int gameVersion, byte[] data, int offset, int expectedNewOffset)
    {
        offset = 0;
        
        try
        {
            offset += BLANK_MAX_LENGTH;
            offset += DEFAULT_ODM_MAX_LENGTH;
            if ((GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion))
            {
                offset += EDITOR_MAX_LENGTH_MM6;
            }
            else
            {
                offset += EDITOR_MAX_LENGTH_MM8;
                offset += 1;
            }
            offset += SKY_BITMAP_MAX_LENGTH;
            offset += GROUND_BITMAP_MAX_LENGTH;
            
            offset += NUMBER_OF_TILE_SET_SELECTORS * TileSetSelector.getRecordSize();
            
            if ((GameVersion.MM6 != gameVersion) && (GameVersion.MM7 != gameVersion))
            {
                offset += 4;
            }

            offset += MAP_HEIGHT * MAP_WIDTH;
            offset += MAP_HEIGHT * MAP_WIDTH;
            offset += MAP_HEIGHT * MAP_WIDTH;
            
            if (GameVersion.MM6 != gameVersion)
            {
                int normalsCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
                offset += 4;

                offset += MAP_HEIGHT * MAP_WIDTH * 2 * 4;
                offset += MAP_HEIGHT * MAP_WIDTH * 2 * 2;
                
                offset += IntVertex.getRecordSize() * normalsCount;
            }

            offset = D3Object.computeDataSize(gameVersion, data, offset);
            
            int spriteCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            offset += spriteCount * Sprite.getRecordSize(gameVersion);
            int unknownMapDataCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            offset += unknownMapDataCount * 2 + (MAP_WIDTH * MAP_HEIGHT * 4);
            int monsterCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            offset += monsterCount * SpawnPoint.getRecordSize(gameVersion);
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
        
        newDataSize += BLANK_MAX_LENGTH;
        newDataSize += DEFAULT_ODM_MAX_LENGTH;
        if ((GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion))
        {
            newDataSize += EDITOR_MAX_LENGTH_MM6;
        }
        else
        {
            newDataSize += EDITOR_MAX_LENGTH_MM8;
            newDataSize += 1;
        }
        newDataSize += SKY_BITMAP_MAX_LENGTH;
        newDataSize += GROUND_BITMAP_MAX_LENGTH;
        
        newDataSize += tileSetSelectorArray.length * TileSetSelector.getRecordSize();
        
        if ((GameVersion.MM6 != gameVersion) && (GameVersion.MM7 != gameVersion))
        {
            newDataSize += 4;
        }

        newDataSize += MAP_HEIGHT * MAP_WIDTH;
        newDataSize += MAP_HEIGHT * MAP_WIDTH;
        newDataSize += MAP_HEIGHT * MAP_WIDTH;
        
        if (GameVersion.MM6 != gameVersion)
        {
            newDataSize += terrainNormalMapData.getRecordSize();
        }

        int d3ObjectRecordSize = 0;
        for (int d3ObjectIndex = 0; d3ObjectIndex < d3ObjectList.size(); ++d3ObjectIndex)
        {
            D3Object d3Object = (D3Object)d3ObjectList.get(d3ObjectIndex);
            d3ObjectRecordSize += d3Object.getRecordSize(gameVersion);
        }
        
        newDataSize += 4 + d3ObjectRecordSize;
        
        newDataSize += 4 + spriteList.size() * Sprite.getRecordSize(gameVersion);
        newDataSize += 4 + (unknownMapData.getIdData().length) + (MAP_WIDTH * MAP_HEIGHT * 4);
        newDataSize += 4 + spawnPointList.size() * SpawnPoint.getRecordSize(gameVersion);
        
        byte newData[] = new byte[newDataSize];
        
        int offset = 0;

        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(getBlankString(), newData, offset, BLANK_MAX_LENGTH);
        offset += BLANK_MAX_LENGTH;
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(getDefaultOdmString(), newData, offset, DEFAULT_ODM_MAX_LENGTH);
        offset += DEFAULT_ODM_MAX_LENGTH;
        if ((GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion))
        {
            ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(getEditorString(), newData, offset, EDITOR_MAX_LENGTH_MM6);
            offset += EDITOR_MAX_LENGTH_MM6;
        }
        else
        {
            ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(getEditorString(), newData, offset, EDITOR_MAX_LENGTH_MM8);
            offset += EDITOR_MAX_LENGTH_MM8;
            newData[offset] = masterTile;
            offset += 1;
        }
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(getSkyBitmapName(), newData, offset, SKY_BITMAP_MAX_LENGTH);
        offset += SKY_BITMAP_MAX_LENGTH;
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(getGroundBitmapName(), newData, offset, GROUND_BITMAP_MAX_LENGTH);
        offset += GROUND_BITMAP_MAX_LENGTH;

        // TODO: move into TileSetSelector
        for (int index = 0; index < tileSetSelectorArray.length; index++)
        {
             ByteConversions.setShortInByteArrayAtPosition(tileSetSelectorArray[index].getGroup(), newData, offset + (index * 4));
             ByteConversions.setShortInByteArrayAtPosition(tileSetSelectorArray[index].getDtileIdNumber(), newData, offset + ((index * 4) + 2));
        }
        offset += tileSetSelectorArray.length * TileSetSelector.getRecordSize();

        if ((GameVersion.MM6 != gameVersion) && (GameVersion.MM7 != gameVersion))
        {
            ByteConversions.setIntegerInByteArrayAtPosition(attributes, newData, offset);
            offset += 4;
        }

        for (int heightMapIndex = 0; heightMapIndex < heightMap.length; heightMapIndex++)
        {
            System.arraycopy(heightMap[heightMapIndex], 0, newData, offset + (heightMap[heightMapIndex].length * heightMapIndex), heightMap[heightMapIndex].length);
        }
        offset += MAP_HEIGHT * MAP_WIDTH;
        
        for (int tileMapIndex = 0; tileMapIndex < tileMap.length; tileMapIndex++)
        {
            System.arraycopy(tileMap[tileMapIndex], 0, newData, offset + (tileMap[tileMapIndex].length * tileMapIndex), tileMap[tileMapIndex].length);
        }
        offset += MAP_HEIGHT * MAP_WIDTH;

        for (int attributeMapIndex = 0; attributeMapIndex < attributeMap.length; attributeMapIndex++)
        {
            System.arraycopy(attributeMap[attributeMapIndex], 0, newData, offset + (attributeMap[attributeMapIndex].length * attributeMapIndex), attributeMap[attributeMapIndex].length);
        }
        offset += MAP_HEIGHT * MAP_WIDTH;
        
        if (GameVersion.MM6 != gameVersion)
        {
            offset = terrainNormalMapData.updateData(newData, offset);
        }
        
        // 3d Objects
        offset = D3Object.updateData(newData, offset, d3ObjectList);
        
        // sprites
        offset = Sprite.updateData(newData, offset, spriteList);
        
        // some mapping data
        offset = unknownMapData.updateData(newData, offset);
        
        // Monsters
        offset = SpawnPoint.updateData(newData, offset, spawnPointList);
        
        return newData;
    }
    
    public int getGameVersion()
    {
        return this.gameVersion;
    }

    public String getBlankString()
    {
        return blankString;
    }


    public void setBlankString(String blankString)
    {
        this.blankString = blankString;
    }

    public String getDefaultOdmString()
    {
        return defaultOdmString;
    }

    public void setDefaultOdmString(String defaultOdmString)
    {
        this.defaultOdmString = defaultOdmString;
    }

    public String getEditorString()
    {
        return editorString;
    }

    public void setEditorString(String editorString)
    {
        this.editorString = editorString;
    }

    public byte getMasterTile()
    {
        return this.masterTile;
    }
    public void setMasterTile(byte masterTile)
    {
        this.masterTile = masterTile;
    }

    public String getSkyBitmapName()
    {
        return skyBitmapName;
    }

    public void setSkyBitmapName(String skyBitmapName)
    {
        this.skyBitmapName = skyBitmapName;
    }

    public String getGroundBitmapName()
    {
        return groundBitmapName;
    }

    public void setGroundBitmapName(String groundBitmapName)
    {
        this.groundBitmapName = groundBitmapName;
    }

    public int getAttributes()
    {
        return this.attributes;
    }
    public void setAttributes(int attributes)
    {
        this.attributes = attributes;
    }

    public TileSetSelector[] getTileSetSelectorArray()
    {
        return tileSetSelectorArray;
    }

    public long getTileSetSelectorOffset()
    {
        return TILE_SET_SELECTOR_OFFSET;
    }

    public byte[][] getHeightMap()
    {
        return heightMap;
    }

    public long getHeightMapOffset()
    {
        return HEIGHT_MAP_OFFSET;
    }

    public byte[][] getTileMap()
    {
        return tileMap;
    }

    public long getTileMapOffset()
    {
        return TILE_MAP_OFFSET;
    }

    public byte[][] getAttributeMap()
    {
        return attributeMap;
    }

    public long getAttributeMapOffset()
    {
        return ATTRIBUTE_MAP_OFFSET;
    }

    public List getD3ObjectList()
    {
        return this.d3ObjectList;
    }
    
    public List getSpriteList()
    {
        return this.spriteList;
    }

    public UnknownMapData getUnknownMapData()
    {
        return this.unknownMapData;
    }
    
    public List getSpawnPointList()
    {
        return this.spawnPointList;
    }
    
    public TerrainNormalMapData getTerrainNormalData()
    {
        return this.terrainNormalMapData;
    }
}
