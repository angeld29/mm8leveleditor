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

import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class Room
{
    private static final int ROOM_RECORD_LENGTH_MM6 = 116;
    private static final int ROOM_RECORD_LENGTH_MM8 = 120;

    private static final int ATTRIBUTE_WATER = 0x0001; // 1
    private static final int ATTRIBUTE_SLIME = 0x0002; // 2
    private static final int ATTRIBUTE_MIST = 0x0004; // 4
    private static final int ATTRIBUTE_NON_WALL_PORTAL = 0x0008; // 8
    private static final int ATTRIBUTE_HAS_BSPNODES = 0x0010; // 16

    private static final int ATTRIBUTE_OFFSET = 4; // 4 bytes
    
    private static final int NUMBER_OF_FLOORS_OFFSET_MM6 = 4; // 2 bytes
    private static final int PADDING1_OFFSET_MM6 = 6; // 2 bytes
    private static final int FLOORS_OFFSET_OFFSET_MM6 = 8; // 4 bytes
    
    private static final int NUMBER_OF_WALLS_OFFSET_MM6 = 12; // 2 bytes
    private static final int PADDING2_OFFSET_MM6 = 14; // 2 bytes
    private static final int WALLS_OFFSET_OFFSET_MM6 = 16; // 4 bytes
    
    private static final int NUMBER_OF_CEILINGS_OFFSET_MM6 = 20; // 2 bytes
    private static final int PADDING3_OFFSET_MM6 = 22; // 2 bytes
    private static final int CEILINGS_OFFSET_OFFSET_MM6 = 24; // 4 bytes
    
    private static final int NUMBER_OF_FLUID_FACETS_OFFSET_MM6 = 28; // 2 bytes
    private static final int PADDING4_OFFSET_MM6 = 30; // 2 bytes
    private static final int FLUID_FACETS_OFFSET_OFFSET_MM6 = 32; // 4 bytes
    
    private static final int NUMBER_OF_PORTALS_OFFSET_MM6 = 36; // 2 bytes
    private static final int PADDING5_OFFSET_MM6 = 38; // 2 bytes
    private static final int PORTALS_OFFSET_OFFSET_MM6 = 40; // 4 bytes

    private static final int NUMBER_OF_FACETS_TO_DRAW_OFFSET_MM6 = 44; // 2 bytes
    private static final int NUMBER_OF_NON_BSP_NODE_FACETS_TO_DRAW_OFFSET_MM6 = 46; // 2 bytes
    private static final int DRAWING_ORDER_OFFSET_OFFSET_MM6 = 48; // 4 bytes

    private static final int NUMBER_OF_CYLINDER_FACETS_OFFSET_MM6 = 52; // 2 bytes
    private static final int PADDING6_OFFSET_MM6 = 54; // 2 bytes
    private static final int CYLINDER_FACETS_OFFSET_OFFSET_MM6 = 56; // 4 bytes
    
    private static final int NUMBER_OF_COGS_OFFSET_MM6 = 60; // 2 bytes
    private static final int PADDING7_OFFSET_MM6 = 62; // 2 bytes
    private static final int COGS_OFFSET_OFFSET_MM6 = 64; // 4 bytes
    
    private static final int NUMBER_OF_DECORATIONS_OFFSET_MM6 = 68; // 2 bytes
    private static final int PADDING8_OFFSET_MM6 = 70; // 2 bytes
    private static final int DECORATIONS_OFFSET_OFFSET_MM6 = 72; // 4 bytes
    
    private static final int NUMBER_OF_SPECIAL_MARKERS_OFFSET_MM6 = 76; // 2 bytes
    private static final int PADDING9_OFFSET_MM6 = 78; // 2 bytes
    private static final int SPECIAL_MARKERS_OFFSET_OFFSET_MM6 = 80; // 4 bytes
    
    private static final int NUMBER_OF_PERMANENT_LIGHTS_OFFSET_MM6 = 84; // 2 bytes
    private static final int PADDING10_OFFSET_MM6 = 86; // 2 bytes
    private static final int PERMANENT_LIGHTS_OFFSET_OFFSET_MM6 = 88; // 4 bytes
    
    private static final int WATER_LEVEL_OFFSET_MM6 = 92; // 2 bytes
    private static final int MIST_LEVEL_OFFSET_MM6 = 94; // 2 bytes
    private static final int LIGHT_DISTANCE_MULTIPLIER_OFFSET_MM6 = 96; // 2 bytes
    private static final int MINIMUM_AMBIENT_LIGHT_LEVEL_OFFSET_MM6 = 98; // 2 bytes
    private static final int FIRST_BSP_NODE_INDEX_OFFSET_MM6 = 100; // 2 bytes
    
    private static final int LEVEL_ROOM_EXITS_TO_OFFSET_MM6 = 102; // 1 byte
    private static final int EXIT_TAG_OFFSET_MM6 = 103; // 1 byte

    private static final int BOUNDING_BOX_MIN_X_OFFSET_MM6 = 104; // two bytes
    private static final int BOUNDING_BOX_MAX_X_OFFSET_MM6 = 106; // two bytes
    private static final int BOUNDING_BOX_MIN_Y_OFFSET_MM6 = 108; // two bytes
    private static final int BOUNDING_BOX_MAX_Y_OFFSET_MM6 = 110; // two bytes
    private static final int BOUNDING_BOX_MIN_Z_OFFSET_MM6 = 112; // two bytes
    private static final int BOUNDING_BOX_MAX_Z_OFFSET_MM6 = 114; // two bytes
    

    private static final int EAX_ENVIRONMENT_OFFSET = 4; // 4 bytes, // EAX environment setting // MM8

    private static final int NUMBER_OF_FLOORS_OFFSET_MM8 = 8; // 2 bytes
    private static final int PADDING1_OFFSET_MM8 = 10; // 2 bytes
    private static final int FLOORS_OFFSET_OFFSET_MM8 = 12; // 4 bytes
    
    private static final int NUMBER_OF_WALLS_OFFSET_MM8 = 16; // 2 bytes
    private static final int PADDING2_OFFSET_MM8 = 18; // 2 bytes
    private static final int WALLS_OFFSET_OFFSET_MM8 = 20; // 4 bytes
    
    private static final int NUMBER_OF_CEILINGS_OFFSET_MM8 = 24; // 2 bytes
    private static final int PADDING3_OFFSET_MM8 = 26; // 2 bytes
    private static final int CEILINGS_OFFSET_OFFSET_MM8 = 28; // 4 bytes
    
    private static final int NUMBER_OF_FLUID_FACETS_OFFSET_MM8 = 32; // 2 bytes
    private static final int PADDING4_OFFSET_MM8 = 34; // 2 bytes
    private static final int FLUID_FACETS_OFFSET_OFFSET_MM8 = 36; // 4 bytes
    
    private static final int NUMBER_OF_PORTALS_OFFSET_MM8 = 40; // 2 bytes
    private static final int PADDING5_OFFSET_MM8 = 42; // 2 bytes
    private static final int PORTALS_OFFSET_OFFSET_MM8 = 44; // 4 bytes

    private static final int NUMBER_OF_FACETS_TO_DRAW_OFFSET_MM8 = 48; // 2 bytes
    private static final int NUMBER_OF_NON_BSP_NODE_FACETS_TO_DRAW_OFFSET_MM8 = 50; // 2 bytes
    private static final int DRAWING_ORDER_OFFSET_OFFSET_MM8 = 52; // 4 bytes

    private static final int NUMBER_OF_CYLINDER_FACETS_OFFSET_MM8 = 56; // 2 bytes
    private static final int PADDING6_OFFSET_MM8 = 58; // 2 bytes
    private static final int CYLINDER_FACETS_OFFSET_OFFSET_MM8 = 60; // 4 bytes
    
    private static final int NUMBER_OF_COGS_OFFSET_MM8 = 64; // 2 bytes
    private static final int PADDING7_OFFSET_MM8 = 66; // 2 bytes
    private static final int COGS_OFFSET_OFFSET_MM8 = 68; // 4 bytes
    
    private static final int NUMBER_OF_DECORATIONS_OFFSET_MM8 = 72; // 2 bytes
    private static final int PADDING8_OFFSET_MM8 = 74; // 2 bytes
    private static final int DECORATIONS_OFFSET_OFFSET_MM8 = 76; // 4 bytes
    
    private static final int NUMBER_OF_SPECIAL_MARKERS_OFFSET_MM8 = 80; // 2 bytes
    private static final int PADDING9_OFFSET_MM8 = 82; // 2 bytes
    private static final int SPECIAL_MARKERS_OFFSET_OFFSET_MM8 = 84; // 4 bytes
    
    private static final int NUMBER_OF_PERMANENT_LIGHTS_OFFSET_MM8 = 88; // 2 bytes
    private static final int PADDING10_OFFSET_MM8 = 90; // 2 bytes
    private static final int PERMANENT_LIGHTS_OFFSET_OFFSET_MM8 = 92; // 4 bytes
    
    private static final int WATER_LEVEL_OFFSET_MM8 = 96; // 2 bytes
    private static final int MIST_LEVEL_OFFSET_MM8 = 98; // 2 bytes
    private static final int LIGHT_DISTANCE_MULTIPLIER_OFFSET_MM8 = 100; // 2 bytes
    private static final int MINIMUM_AMBIENT_LIGHT_LEVEL_OFFSET_MM8 = 102; // 2 bytes
    private static final int FIRST_BSP_NODE_INDEX_OFFSET_MM8 = 104; // 2 bytes
    
    private static final int LEVEL_ROOM_EXITS_TO_OFFSET_MM8 = 106; // 1 byte
    private static final int EXIT_TAG_OFFSET_MM8 = 107; // 1 byte

    private static final int BOUNDING_BOX_MIN_X_OFFSET_MM8 = 108; // two bytes
    private static final int BOUNDING_BOX_MAX_X_OFFSET_MM8 = 110; // two bytes
    private static final int BOUNDING_BOX_MIN_Y_OFFSET_MM8 = 112; // two bytes
    private static final int BOUNDING_BOX_MAX_Y_OFFSET_MM8 = 114; // two bytes
    private static final int BOUNDING_BOX_MIN_Z_OFFSET_MM8 = 116; // two bytes
    private static final int BOUNDING_BOX_MAX_Z_OFFSET_MM8 = 118; // two bytes
    
    private int gameVersion = 0;
    private byte roomData[] = null;
    private short floorArray[] = null;
    private short wallArray[] = null;
    private short ceilingArray[] = null;
    private short fluidArray[] = null;
    private short portalArray[] = null;
    private short drawingArray[] = null;
    private short cogArray[] = null;
    private short decorationArray[] = null;
    private short specialMarkerArray[] = null;
    private short permanentLightArray[] = null;
    
    public Room(int gameVersion)
    {
        super();
        this.gameVersion = gameVersion;
        
        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
        	this.roomData = new byte[ROOM_RECORD_LENGTH_MM6];
        else this.roomData = new byte[ROOM_RECORD_LENGTH_MM8];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        System.arraycopy(dataSrc, offset, this.roomData, 0, this.roomData.length);
        offset += this.roomData.length;
        
        return offset;
    }

    private int initializeList(byte data[], int offset, short[] array)
    {
        for (int index = 0; index < array.length; ++index)
        {
            array[index] = ByteConversions.getShortInByteArrayAtPosition(data, offset);
            offset += 2;
        }

        return offset;
    }

    public int initializeVariableRoomData(byte variableRoomData[], int offset)
    {
        floorArray = new short[getFloorCount()];
        offset = initializeList(variableRoomData, offset, floorArray);
        wallArray = new short[getWallCount()];
        offset = initializeList(variableRoomData, offset, wallArray);
        ceilingArray = new short[getCeilingCount()];
        offset = initializeList(variableRoomData, offset, ceilingArray);
        fluidArray = new short[getFluidCount()];
        offset = initializeList(variableRoomData, offset, fluidArray);
        portalArray = new short[getPortalCount()];
        offset = initializeList(variableRoomData, offset, portalArray);
        drawingArray = new short[getDrawingCount()];
        offset = initializeList(variableRoomData, offset, drawingArray);
        cogArray = new short[getCogCount()];
        offset = initializeList(variableRoomData, offset, cogArray);
        decorationArray = new short[getDecorationCount()];
        offset = initializeList(variableRoomData, offset, decorationArray);
        specialMarkerArray = new short[getSpecialMarkerCount()];
        offset = initializeList(variableRoomData, offset, specialMarkerArray);

        return offset;
    }

    public int initializeVariableRoomLightData(byte variableRoomLightData[], int offset)
    {
        permanentLightArray = new short[getPermanentLightCount()];
        offset = initializeList(variableRoomLightData, offset, permanentLightArray);

        return offset;
    }

    public static int populateObjects(int gameVersion, byte[] data, int offset, List roomList, int variableRoomDataSize, int variableRoomLightDataSize)
    {
        int unknownCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int roomIndex = 0; roomIndex < unknownCount; ++roomIndex)
        {
            Room room = new Room(gameVersion);
            roomList.add(room);
            offset = room.initialize(data, offset);
        }
        
        byte variableRoomData[] = new byte[variableRoomDataSize];
        System.arraycopy(data, offset, variableRoomData, 0, variableRoomData.length);
        offset += variableRoomData.length;
        
        byte variableRoomLightData[] = new byte[variableRoomLightDataSize];
        System.arraycopy(data, offset, variableRoomLightData, 0, variableRoomLightData.length);
        offset += variableRoomLightData.length;

        int variableRoomDataOffset = 0;
        for (int roomIndex = 0; roomIndex < roomList.size(); ++roomIndex)
        {
            Room room = (Room)roomList.get(roomIndex);
            variableRoomDataOffset = room.initializeVariableRoomData(variableRoomData, variableRoomDataOffset);
        }
        
        int variableRoomLightDataOffset = 0;
        for (int roomIndex = 0; roomIndex < roomList.size(); ++roomIndex)
        {
            Room room = (Room)roomList.get(roomIndex);
            variableRoomLightDataOffset = room.initializeVariableRoomLightData(variableRoomLightData, variableRoomLightDataOffset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List roomList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(roomList.size(), newData, offset);
        offset += 4;

        int variableRoomDataSize = 0;
        int variableRoomLightDataSize = 0;
        for (int roomIndex = 0; roomIndex < roomList.size(); ++roomIndex)
        {
            Room room = (Room)roomList.get(roomIndex);
            System.arraycopy(room.getRoomData(), 0, newData, offset, room.getRoomData().length);
            offset += room.getRoomData().length;
            variableRoomDataSize += room.getVariableRoomDataSize();
            variableRoomLightDataSize += room.getVariableRoomLightDataSize();
        }
        
        byte variableRoomData[] = new byte[variableRoomDataSize];
        byte variableRoomLightData[] = new byte[variableRoomLightDataSize];

        int variableRoomDataOffset = 0;
        int variableRoomLightDataOffset = 0;
        for (int roomIndex = 0; roomIndex < roomList.size(); ++roomIndex)
        {
            Room room = (Room)roomList.get(roomIndex);
            room.updateVariableRoomData(variableRoomData, variableRoomDataOffset);
            room.updateVariableRoomLightData(variableRoomLightData, variableRoomLightDataOffset);
        }
        
        System.arraycopy(variableRoomData, 0, newData, offset, variableRoomData.length);
        offset += variableRoomData.length;
        
        System.arraycopy(variableRoomLightData, 0, newData, offset, variableRoomLightData.length);
        offset += variableRoomLightData.length;

        return offset;
    }

    private int updateDataFromList(byte variableRoomData[], int variableRoomDataOffset, short[] array)
    {
        for (int index = 0; index < array.length; ++index)
        {
            ByteConversions.setShortInByteArrayAtPosition(array[index], variableRoomData, variableRoomDataOffset);
            variableRoomDataOffset += 2;
        }

        return variableRoomDataOffset;
    }


    private int updateVariableRoomData(byte[] variableRoomData, int variableRoomDataOffset)
    {
        variableRoomDataOffset = updateDataFromList(variableRoomData, variableRoomDataOffset, floorArray);
        variableRoomDataOffset = updateDataFromList(variableRoomData, variableRoomDataOffset, wallArray);
        variableRoomDataOffset = updateDataFromList(variableRoomData, variableRoomDataOffset, ceilingArray);
        variableRoomDataOffset = updateDataFromList(variableRoomData, variableRoomDataOffset, fluidArray);
        variableRoomDataOffset = updateDataFromList(variableRoomData, variableRoomDataOffset, portalArray);
        variableRoomDataOffset = updateDataFromList(variableRoomData, variableRoomDataOffset, drawingArray);
        variableRoomDataOffset = updateDataFromList(variableRoomData, variableRoomDataOffset, cogArray);
        variableRoomDataOffset = updateDataFromList(variableRoomData, variableRoomDataOffset, decorationArray);
        variableRoomDataOffset = updateDataFromList(variableRoomData, variableRoomDataOffset, specialMarkerArray);
        
        return variableRoomDataOffset;
    }

    private int updateVariableRoomLightData(byte[] variableRoomLightData, int variableRoomLightDataOffset)
    {
        variableRoomLightDataOffset = updateDataFromList(variableRoomLightData, variableRoomLightDataOffset, permanentLightArray);
        
        return variableRoomLightDataOffset;
    }

    private int getVariableRoomDataSize()
    {
        int size = 0;
        size += 2 * floorArray.length;
        size += 2 * wallArray.length;
        size += 2 * ceilingArray.length;
        size += 2 * fluidArray.length;
        size += 2 * portalArray.length;
        size += 2 * drawingArray.length;
        size += 2 * cogArray.length;
        size += 2 * decorationArray.length;
        size += 2 * specialMarkerArray.length;

        return size;
    }

    private int getVariableRoomLightDataSize()
    {
        int size = 0;
        size += 2 * permanentLightArray.length;

        return size;
    }

    public byte[] getRoomData()
    {
        return this.roomData;
    }

    public int getFloorCount()
    {
        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
            return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_FLOORS_OFFSET_MM6);
        else return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_FLOORS_OFFSET_MM8);

    }

    public int getWallCount()
    {
        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
            return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_WALLS_OFFSET_MM6);
        else return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_WALLS_OFFSET_MM8);

    }

    public int getCeilingCount()
    {
        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
            return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_CEILINGS_OFFSET_MM6);
        else return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_CEILINGS_OFFSET_MM8);

    }

    public int getFluidCount()
    {
        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
            return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_FLUID_FACETS_OFFSET_MM6);
        else return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_FLUID_FACETS_OFFSET_MM8);

    }

    public int getCylinderCount()
    {
        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
            return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_CYLINDER_FACETS_OFFSET_MM6);
        else return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_CYLINDER_FACETS_OFFSET_MM8);

    }

    public int getPortalCount()
    {
        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
            return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_PORTALS_OFFSET_MM6);
        else return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_PORTALS_OFFSET_MM8);

    }

    public int getDrawingCount()
    {
        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
            return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_FACETS_TO_DRAW_OFFSET_MM6);
        else return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_FACETS_TO_DRAW_OFFSET_MM8);

    }

    public int getCogCount()
    {
        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
            return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_COGS_OFFSET_MM6);
        else return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_COGS_OFFSET_MM8);

    }

    public int getDecorationCount()
    {
        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
            return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_DECORATIONS_OFFSET_MM6);
        else return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_DECORATIONS_OFFSET_MM8);

    }

    public int getSpecialMarkerCount()
    {
        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
            return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_SPECIAL_MARKERS_OFFSET_MM6);
        else return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_SPECIAL_MARKERS_OFFSET_MM8);

    }

    public int getPermanentLightCount()
    {
        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
            return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_PERMANENT_LIGHTS_OFFSET_MM6);
        else return ByteConversions.getShortInByteArrayAtPosition(roomData, NUMBER_OF_PERMANENT_LIGHTS_OFFSET_MM8);

    }

    public short[] getCeilingArray()
    {
        return this.ceilingArray;
    }
    public short[] getCogArray()
    {
        return this.cogArray;
    }
    public short[] getDecorationArray()
    {
        return this.decorationArray;
    }
    public short[] getDrawingArray()
    {
        return this.drawingArray;
    }
    public short[] getFloorArray()
    {
        return this.floorArray;
    }
    public short[] getFluidArray()
    {
        return this.fluidArray;
    }
    public int getGameVersion()
    {
        return this.gameVersion;
    }
    public short[] getPermanentLightArray()
    {
        return this.permanentLightArray;
    }
    public short[] getPortalArray()
    {
        return this.portalArray;
    }
    public short[] getSpecialMarkerArray()
    {
        return this.specialMarkerArray;
    }
    public short[] getWallArray()
    {
        return this.wallArray;
    }

    private static int getRecordSize(int gameVersion)
    {
        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
            return ROOM_RECORD_LENGTH_MM6;
        else return ROOM_RECORD_LENGTH_MM8;
    }

    public static int computeDataSize(int gameVersion, byte[] data, int offset, int variableRoomDataSize, int variableRoomLightDataSize)
    {
        int roomCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;
        offset += roomCount * Room.getRecordSize(gameVersion);
        offset += variableRoomDataSize + variableRoomLightDataSize;
        
        return offset;
    }

    public static int computeDataSize(int gameVersion, List roomList)
    {
        int dataSize = 0;
        dataSize += 4 + roomList.size() * Room.getRecordSize(gameVersion);
        
        int variableRoomDataSize = 0;
        int variableRoomLightDataSize = 0;
        for (int roomIndex = 0; roomIndex < roomList.size(); ++roomIndex)
        {
            Room room = (Room)roomList.get(roomIndex);
            variableRoomDataSize += room.getVariableRoomDataSize();
            variableRoomLightDataSize += room.getVariableRoomLightDataSize();
        }

        dataSize += variableRoomDataSize + variableRoomLightDataSize;
        
        return dataSize;
    }

    // Unknown things to decode

    public static List getOffsetList(int gameVersion)
    {
        List offsetList = new ArrayList();

        offsetList.add(new ComparativeTableControl.OffsetData(ATTRIBUTE_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Attributes"));

        if ( (GameVersion.MM6 == gameVersion) || (GameVersion.MM7 == gameVersion) )
        {
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_FLOORS_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of floors"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_WALLS_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of walls"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_CEILINGS_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of ceilings"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_FLUID_FACETS_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of fluids"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_PORTALS_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of portals"));

            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_FACETS_TO_DRAW_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of facets to draw"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_NON_BSP_NODE_FACETS_TO_DRAW_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of non-BSP Node facets to draw"));

            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_CYLINDER_FACETS_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of cylinder facets"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_COGS_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of cogs"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_DECORATIONS_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of decorations"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_SPECIAL_MARKERS_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of special markers"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_PERMANENT_LIGHTS_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of permanent lights"));

            offsetList.add(new ComparativeTableControl.OffsetData(WATER_LEVEL_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Water Level"));
            offsetList.add(new ComparativeTableControl.OffsetData(MIST_LEVEL_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Mist Level"));
            offsetList.add(new ComparativeTableControl.OffsetData(LIGHT_DISTANCE_MULTIPLIER_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Light Distance Multiplier"));
            offsetList.add(new ComparativeTableControl.OffsetData(MINIMUM_AMBIENT_LIGHT_LEVEL_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Min Ambient Light Level"));
            offsetList.add(new ComparativeTableControl.OffsetData(FIRST_BSP_NODE_INDEX_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "First BSP Node Index"));

            offsetList.add(new ComparativeTableControl.OffsetData(LEVEL_ROOM_EXITS_TO_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "Level Room Exits To"));
            offsetList.add(new ComparativeTableControl.OffsetData(EXIT_TAG_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "Exit Tag"));

            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MIN_X_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Min X"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MAX_X_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Max X"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MIN_Y_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Min Y"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MAX_Y_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Max Y"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MIN_Z_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Min Z"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MAX_Z_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Max Z"));

            offsetList.add(new ComparativeTableControl.OffsetData(PADDING1_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING2_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING3_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING4_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING5_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING6_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING7_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING8_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING9_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING10_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));

            offsetList.add(new ComparativeTableControl.OffsetData(FLOORS_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "floor offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(WALLS_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "walls offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(CEILINGS_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ceilings offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(FLUID_FACETS_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "fluids offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(PORTALS_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "portals offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(DRAWING_ORDER_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "drawing order offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(CYLINDER_FACETS_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "cylinder facets offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(COGS_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "cogs offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(DECORATIONS_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "decorations offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_MARKERS_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "special markers offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(PERMANENT_LIGHTS_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "permanent lights offset"));
        }
        else
        {
            offsetList.add(new ComparativeTableControl.OffsetData(EAX_ENVIRONMENT_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "EAX Environment"));

            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_FLOORS_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of floors"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_WALLS_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of walls"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_CEILINGS_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of ceilings"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_FLUID_FACETS_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of fluids"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_PORTALS_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of portals"));

            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_FACETS_TO_DRAW_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of facets to draw"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_NON_BSP_NODE_FACETS_TO_DRAW_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of non-BSP Node facets to draw"));

            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_CYLINDER_FACETS_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of cylinder facets"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_COGS_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of cogs"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_DECORATIONS_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of decorations"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_SPECIAL_MARKERS_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of special markers"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_PERMANENT_LIGHTS_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of permanent lights"));

            offsetList.add(new ComparativeTableControl.OffsetData(WATER_LEVEL_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Water Level"));
            offsetList.add(new ComparativeTableControl.OffsetData(MIST_LEVEL_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Mist Level"));
            offsetList.add(new ComparativeTableControl.OffsetData(LIGHT_DISTANCE_MULTIPLIER_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Light Distance Multiplier"));
            offsetList.add(new ComparativeTableControl.OffsetData(MINIMUM_AMBIENT_LIGHT_LEVEL_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Min Ambient Light Level"));
            offsetList.add(new ComparativeTableControl.OffsetData(FIRST_BSP_NODE_INDEX_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "First BSP Node Index"));

            offsetList.add(new ComparativeTableControl.OffsetData(LEVEL_ROOM_EXITS_TO_OFFSET_MM8, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "Level Room Exits To"));
            offsetList.add(new ComparativeTableControl.OffsetData(EXIT_TAG_OFFSET_MM8, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "Exit Tag"));

            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MIN_X_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Min X"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MAX_X_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Max X"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MIN_Y_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Min Y"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MAX_Y_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Max Y"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MIN_Z_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Min Z"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MAX_Z_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Max Z"));

            offsetList.add(new ComparativeTableControl.OffsetData(PADDING1_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING2_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING3_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING4_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING5_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING6_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING7_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING8_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING9_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING10_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Padding"));

            offsetList.add(new ComparativeTableControl.OffsetData(FLOORS_OFFSET_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "floor offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(WALLS_OFFSET_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "walls offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(CEILINGS_OFFSET_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ceilings offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(FLUID_FACETS_OFFSET_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "fluids offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(PORTALS_OFFSET_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "portals offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(DRAWING_ORDER_OFFSET_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "drawing order offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(CYLINDER_FACETS_OFFSET_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "cylinder facets offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(COGS_OFFSET_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "cogs offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(DECORATIONS_OFFSET_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "decorations offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_MARKERS_OFFSET_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "special markers offsets"));
            offsetList.add(new ComparativeTableControl.OffsetData(PERMANENT_LIGHTS_OFFSET_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "permanent lights offset"));
        }
        
        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List unknownList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return unknownList.size();
            }

            public byte[] getData(int dataRow)
            {
                Room unknown = (Room)unknownList.get(dataRow);
                return unknown.getRoomData();
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
