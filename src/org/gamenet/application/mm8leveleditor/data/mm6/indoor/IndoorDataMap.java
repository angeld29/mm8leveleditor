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

package org.gamenet.application.mm8leveleditor.data.mm6.indoor;

import java.util.ArrayList;
import java.util.List;

import org.gamenet.application.mm8leveleditor.data.DataSizeException;
import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.application.mm8leveleditor.data.mm6.BSPNode;
import org.gamenet.application.mm8leveleditor.data.mm6.SpawnPoint;
import org.gamenet.application.mm8leveleditor.data.mm6.Sprite;
import org.gamenet.util.ByteConversions;

public class IndoorDataMap
{
    private static final int LEVEL_NUMBER_OFFSET = 0; // 4 bytes

    private static final int LEVEL_NAME_OFFSET = 4;
    private static final int LEVEL_NAME_MAX_LENGTH = 60;

    private static final int PALETTES_OFFSET = 64; // 4 bytes
    private static final int NUMBER_OF_PALETTES = 4;
    
    private static final int SONG_NAME_OFFSET = 80;
    private static final int SONG_NAME_MAX_LENGTH = 24;

    private static final int VARIABLE_FACET_DATA_SIZE_OFFSET = 104; // 4 bytes
    private static final int VARIABLE_ROOM_DATA_SIZE_OFFSET = 108; // 4 bytes 
    private static final int VARIABLE_ROOM_LIGHT_DATA_SIZE_OFFSET = 112; // 4 bytes
    private static final int VARIABLE_DOOR_DATA_SIZE_OFFSET = 116; // 4 bytes

    private static final int SKY_BITMAP_NAME_OFFSET = 120;
    private static final int SKY_BITMAP_NAME_MAX_LENGTH = 12;
    private static final int SKY_INDEX_OFFSET = 132;

    private static final int VERTEXES_COUNT_OFFSET = 136; // short vertex coord list
    
    private int gameVersion = GameVersion.MM6;

    int levelNumber;
    String levelName;
    int  palettes[];
    String songName;
    // int  variableFacetDataSize;
    // int  variableRoomDataSize;
    // int  variableRoomLightDataSize;
    int  variableDoorDataSize;
    String skyBitmapName;
    int  skyIndex;

    private List vertexList = null;    
    private List faceList = null;
    
    private List indoorFacetExtraDataList = null;
    
    // IMPLEMENT: needs to be merged into one object
    private List roomList = null;
    private byte variableRoomData[] = null;
    private byte variableRoomLightData[] = null;

    private int numberOfDoors;
    
    private List spriteList = null;

    private List lightSourcesList = null;

    private List bspNodeList = null;

    private List spawnPointList = null;

    private List mapOutlineList = null;

    public IndoorDataMap(int gameVersion)
    {
        super();
        
        this.gameVersion = gameVersion;
    }
    
    // public int initialize(byte dataSrc[], int offset)
    public int initialize(byte dataSrc[], int offset)
    {
        levelNumber = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset);
        offset += 4;
        
        levelName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(dataSrc, offset, LEVEL_NAME_MAX_LENGTH);
        offset += LEVEL_NAME_MAX_LENGTH;
        
        palettes = new int[NUMBER_OF_PALETTES];
        for (int paletteIndex = 0; paletteIndex < palettes.length; paletteIndex++)
        {
            palettes[paletteIndex] = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset);
            offset += 4;
        }
        
        songName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(dataSrc, offset, SONG_NAME_MAX_LENGTH);
        offset += SONG_NAME_MAX_LENGTH;
        
        int variableFacetDataSize = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset);
        offset += 4;
        
        int variableRoomDataSize = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset);
        offset += 4;
        
        int variableRoomLightDataSize = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset);
        offset += 4;
        
        variableDoorDataSize = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset);
        offset += 4;
        
        skyBitmapName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(dataSrc, offset, SKY_BITMAP_NAME_MAX_LENGTH);
        offset += SKY_BITMAP_NAME_MAX_LENGTH;
        
        skyIndex = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset);
        offset += 4;

        this.vertexList = new ArrayList();
        offset = ShortVertex.populateObjects(dataSrc, offset, this.vertexList);

        this.faceList = new ArrayList();
        offset = IndoorFace.populateObjects(gameVersion, dataSrc, offset, this.faceList, variableFacetDataSize);

        this.indoorFacetExtraDataList = new ArrayList();
        offset = IndoorFacetExtraData.populateObjects(dataSrc, offset, this.indoorFacetExtraDataList);
        
        this.roomList = new ArrayList();
        offset = Room.populateObjects(gameVersion, dataSrc, offset, this.roomList, variableRoomDataSize, variableRoomLightDataSize);
        
        this.numberOfDoors = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset);
        offset += 4;
        
        // Sprites
        this.spriteList = new ArrayList();
        offset = Sprite.populateObjects(gameVersion, dataSrc, offset, this.spriteList);
        
        this.lightSourcesList = new ArrayList();
        offset = LightSource.populateObjects(gameVersion, dataSrc, offset, this.lightSourcesList);
               
        this.bspNodeList = new ArrayList();
        offset = BSPNode.populateObjects(dataSrc, offset, this.bspNodeList);

        this.spawnPointList = new ArrayList();
        offset = SpawnPoint.populateObjects(gameVersion, dataSrc, offset, this.spawnPointList);

        this.mapOutlineList = new ArrayList();
        offset = MapOutlineLine.populateObjects(dataSrc, offset, this.mapOutlineList);

        return offset;
    }

    public static boolean checkDataIntegrity(int gameVersion, byte[] data, int offset, int expectedNewOffset)
    {
        try
        {
            offset += 4;
            
            offset += LEVEL_NAME_MAX_LENGTH;
            
            offset += 4 * NUMBER_OF_PALETTES;
            
            offset += SONG_NAME_MAX_LENGTH;
            
            int variableFacetDataSize = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            
            int variableRoomDataSize = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            
            int variableRoomLightDataSize = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            
            int variableDoorDataSize = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            
            offset += SKY_BITMAP_NAME_MAX_LENGTH;
            
            offset += 4;

            int vertexCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            offset += vertexCount * ShortVertex.getRecordSize();

            offset = IndoorFace.computeDataSize(gameVersion, data, offset, variableFacetDataSize);

            int facetExtraCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            offset += facetExtraCount * IndoorFacetExtraData.getRecordSize();

            offset = Room.computeDataSize(gameVersion, data, offset, variableRoomDataSize, variableRoomLightDataSize);
            
            offset += 4;

            int spriteCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            offset += spriteCount * Sprite.getRecordSize(gameVersion);
            
            int lightSourceCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            offset += lightSourceCount * LightSource.getRecordSize(gameVersion);
            
            int bspNodeCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            offset += bspNodeCount * BSPNode.getRecordSize();
            
            int spawnPointCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            offset += spawnPointCount * SpawnPoint.getRecordSize(gameVersion);
            
            int mapOutlineLineCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
            offset += 4;
            offset += mapOutlineLineCount * MapOutlineLine.getRecordSize();

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
        newDataSize += LEVEL_NAME_MAX_LENGTH;
        newDataSize += 4 * NUMBER_OF_PALETTES;
        newDataSize += SONG_NAME_MAX_LENGTH;
        newDataSize += 4;
        newDataSize += 4;
        newDataSize += 4;
        newDataSize += 4;
        newDataSize += SKY_BITMAP_NAME_MAX_LENGTH;
        
        newDataSize += 4 + vertexList.size() * ShortVertex.getRecordSize();
        newDataSize += 4 + IndoorFace.getRecordSize(faceList);
        newDataSize += 4 + indoorFacetExtraDataList.size() * IndoorFacetExtraData.getRecordSize();
        
        newDataSize = Room.computeDataSize(gameVersion, roomList);
        
        newDataSize += 4;
        newDataSize += 4 + spriteList.size() * Sprite.getRecordSize(gameVersion);
        newDataSize += 4 + lightSourcesList.size() * LightSource.getRecordSize(gameVersion);
        newDataSize += 4 + bspNodeList.size() * BSPNode.getRecordSize();
        newDataSize += 4 + spawnPointList.size() * SpawnPoint.getRecordSize(gameVersion);
        newDataSize += 4 + mapOutlineList.size() * MapOutlineLine.getRecordSize();
        
        byte newData[] = new byte[newDataSize];
        int offset = 0;
        
        ByteConversions.setIntegerInByteArrayAtPosition(levelNumber, newData, offset);
        offset += 4;
        
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(levelName, newData, offset, LEVEL_NAME_MAX_LENGTH);
        offset += LEVEL_NAME_MAX_LENGTH;
        
        palettes = new int[NUMBER_OF_PALETTES];
        for (int paletteIndex = 0; paletteIndex < palettes.length; paletteIndex++)
        {
            ByteConversions.setIntegerInByteArrayAtPosition(palettes[paletteIndex], newData, offset);
            offset += 4;
        }
        
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(songName, newData, offset, SONG_NAME_MAX_LENGTH);
        offset += SONG_NAME_MAX_LENGTH;
        
        ByteConversions.setIntegerInByteArrayAtPosition(IndoorFace.getVariableFacetDataSize(faceList), newData, offset);
        offset += 4;
        
        ByteConversions.setIntegerInByteArrayAtPosition(variableRoomData.length, newData, offset);
        offset += 4;
        
        ByteConversions.setIntegerInByteArrayAtPosition(variableRoomLightData.length, newData, offset);
        offset += 4;
        
        ByteConversions.setIntegerInByteArrayAtPosition(variableDoorDataSize, newData, offset);
        offset += 4;
        
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(skyBitmapName, newData, offset, SKY_BITMAP_NAME_MAX_LENGTH);
        offset += SKY_BITMAP_NAME_MAX_LENGTH;
        
        ByteConversions.setIntegerInByteArrayAtPosition(skyIndex, newData, offset);
        offset += 4;

        offset = ShortVertex.updateData(newData, offset, vertexList);

        offset = IndoorFace.updateData(newData, offset, faceList);

        offset = IndoorFacetExtraData.updateData(newData, offset, indoorFacetExtraDataList);

        offset = Room.updateData(newData, offset, roomList);

        ByteConversions.setIntegerInByteArrayAtPosition(numberOfDoors, newData, offset);
        offset += 4;
        
        // sprites
        offset = Sprite.updateData(newData, offset, spriteList);
        
        // LightSources
        offset = LightSource.updateData(newData, offset, lightSourcesList);

        offset = BSPNode.updateData(newData, offset, bspNodeList);

        // Monsters
        offset = SpawnPoint.updateData(newData, offset, spawnPointList);

        // MapOutlineLine
        offset = MapOutlineLine.updateData(newData, offset, mapOutlineList);

        return newData;
    }
    
    public int getGameVersion()
    {
        return this.gameVersion;
    }

    public List getVertexList()
    {
        return this.vertexList;
    }
    
    public List getFaceList()
    {
        return this.faceList;
    }
    
    public List getRoomList()
    {
        return this.roomList;
    }
    public byte[] getVariableRoomData()
    {
        return this.variableRoomData;
    }
    public byte[] getVariableRoomLightData()
    {
        return this.variableRoomLightData;
    }

    public List getSpriteList()
    {
        return this.spriteList;
    }
    public List getMapOutlineList()
    {
        return this.mapOutlineList;
    }
    public List getLightSourcesList()
    {
        return this.lightSourcesList;
    }
    public List getBspNodeList()
    {
        return this.bspNodeList;
    }
    public List getSpawnPointList()
    {
        return this.spawnPointList;
    }
    
    public int getLevelNumber()
    {
        return this.levelNumber;
    }
    public void setLevelNumber(int levelNumber)
    {
        this.levelNumber = levelNumber;
    }
    public int getNumberOfDoors()
    {
        return this.numberOfDoors;
    }
    public void setNumberOfDoors(int numberOfDoors)
    {
        this.numberOfDoors = numberOfDoors;
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
        return SKY_BITMAP_NAME_MAX_LENGTH;
    }
    public int getSkyIndex()
    {
        return this.skyIndex;
    }
    public void setSkyIndex(int skyIndex)
    {
        this.skyIndex = skyIndex;
    }
    public String getSongName()
    {
        return this.songName;
    }
    public void setSongName(String songName)
    {
        this.songName = songName;
    }
    public int getSongNameMaxLength()
    {
        return SONG_NAME_MAX_LENGTH;
    }
    public int[] getPaletteArray()
    {
        return this.palettes;
    }
    public String getLevelName()
    {
        return this.levelName;
    }
    public void setLevelName(String levelName)
    {
        this.levelName = levelName;
    }
    public int getLevelNameMaxLength()
    {
        return LEVEL_NAME_MAX_LENGTH;
    }
    public int getVariableDoorDataSize()
    {
        return this.variableDoorDataSize;
    }
    public void setVariableDoorDataSize(int variableDoorDataSize)
    {
        this.variableDoorDataSize = variableDoorDataSize;
    }
    public List getIndoorFacetExtraDataList()
    {
        return this.indoorFacetExtraDataList;
    }
}
