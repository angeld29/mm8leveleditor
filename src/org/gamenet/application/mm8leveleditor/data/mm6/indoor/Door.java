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

import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;
import org.gamenet.util.UnimplementedMethodException;

public class Door
{
    private static final int DOOR_RECORD_LENGTH = 80;

    private static final int DOOR_STATE_OPEN = 0;
    private static final int DOOR_STATE_CLOSING = 1;
    private static final int DOOR_STATE_CLOSED = 2;
    private static final int DOOR_STATE_OPENING = 3;

    private static final int DOOR_TRIGGER_OPEN = 0;
    private static final int DOOR_TRIGGER_CLOSE = 1;
    private static final int DOOR_TRIGGER_TOGGLE = 2;

    private static final int DOOR_ATTRIBUTE_START_CLOSED = 0x0001;
    private static final int DOOR_ATTRIBUTE_FORCE_MOVE = 0x0002;
    private static final int DOOR_ATTRIBUTE_NO_SOUND = 0x0004;

    private static final int ATTRIBUTES_OFFSET = 0; // 4 bytes
    private static final int DOOR_ID_OFFSET = 4; // 4 bytes
    private static final int TIME_SINCE_TRIGGERED_OFFSET = 8; // 4 bytes
    private static final int DIRECTION_X_OFFSET = 12; // 4 bytes
    private static final int DIRECTION_Y_OFFSET = 16; // 4 bytes
    private static final int DIRECTION_Z_OFFSET = 20; // 4 bytes
    private static final int DIRECTION_MAGNITUDE_OFFSET = 24; // 4 bytes, pixels
    private static final int OPENING_VELOCITY_OFFSET = 28; // 4 bytes, pixels/sec
    private static final int CLOSING_VELOCITY_OFFSET = 32; // 4 bytes, pixels/sec

    private static final int VERTEX_OFFSET_OFFSET = 36; // 4 bytes
    private static final int FACET_OFFSET_OFFSET = 40; // 4 bytes
    private static final int ROOM_OFFSET_OFFSET = 44; // 4 bytes
    private static final int BITMAP_DX_OFFSET_OFFSET = 48; // 4 bytes
    private static final int BITMAP_DY_OFFSET_OFFSET = 52; // 4 bytes
    private static final int VERTEX_X_OFFSET_OFFSET = 56; // 4 bytes
    private static final int VERTEX_Y_OFFSET_OFFSET = 60; // 4 bytes
    private static final int VERTEX_Z_OFFSET_OFFSET = 64; // 4 bytes

    private static final int NUMBER_OF_TARGETS_1_OFFSET = 68; // 2 bytes
    private static final int NUMBER_OF_TARGETS_2_OFFSET = 70; // 2 bytes
    private static final int NUMBER_OF_TARGETS_3_OFFSET = 72; // 2 bytes
    private static final int NUMBER_OF_X_OFFSET = 74; // 2 bytes
    private static final int DOOR_STATE_OFFSET = 76; // 2 bytes
    private static final int PADDING_OFFSET = 78; // 2 bytes

    private int doorOffset = 0;
    private byte doorData[] = null;
    
    private short vertexArray[] = null;
    private short facetArray[] = null;
    private short roomArray[] = null;
    private short bitmapDxArray[] = null;
    private short bitmapDyArray[] = null;
    private short targetX1Array[] = null;
    private short targetX2Array[] = null;
    private short targetX3Array[] = null;
    

    public Door()
    {
        super();
    }

    public Door(String value)
    {
        throw new UnimplementedMethodException("Door String constructor");
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.doorOffset = offset;
        this.doorData = new byte[DOOR_RECORD_LENGTH];
        System.arraycopy(dataSrc, offset, this.doorData, 0, this.doorData.length);
        offset += this.doorData.length;
        
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

    public int initializeVariableDoorData(byte variableDoorData[], int offset)
    {
        vertexArray = new short[getVertexCount()];
        offset = initializeList(variableDoorData, offset, vertexArray);
        facetArray = new short[getFacetCount()];
        offset = initializeList(variableDoorData, offset, facetArray);
        roomArray = new short[getRoomCount()];
        offset = initializeList(variableDoorData, offset, roomArray);
        bitmapDxArray = new short[getBitmapDxCount()];
        offset = initializeList(variableDoorData, offset, bitmapDxArray);
        bitmapDyArray = new short[getBitmapDyCount()];
        offset = initializeList(variableDoorData, offset, bitmapDyArray);
        targetX1Array = new short[getTargetX1Count()];
        offset = initializeList(variableDoorData, offset, targetX1Array);
        targetX2Array = new short[getTargetX2Count()];
        offset = initializeList(variableDoorData, offset, targetX2Array);
        targetX3Array = new short[getTargetX3Count()];
        offset = initializeList(variableDoorData, offset, targetX3Array);

        return offset;
    }

    private int getVertexCount()
    {
        return ByteConversions.getShortInByteArrayAtPosition(doorData, NUMBER_OF_TARGETS_1_OFFSET);
    }

    private int getFacetCount()
    {
        return ByteConversions.getShortInByteArrayAtPosition(doorData, NUMBER_OF_TARGETS_2_OFFSET);
    }

    private int getRoomCount()
    {
        return ByteConversions.getShortInByteArrayAtPosition(doorData, NUMBER_OF_TARGETS_3_OFFSET);
    }

    private int getBitmapDxCount()
    {
        return ByteConversions.getShortInByteArrayAtPosition(doorData, NUMBER_OF_TARGETS_2_OFFSET);
    }

    private int getBitmapDyCount()
    {
        return ByteConversions.getShortInByteArrayAtPosition(doorData, NUMBER_OF_TARGETS_2_OFFSET);
    }

    private int getTargetX1Count()
    {
        return ByteConversions.getShortInByteArrayAtPosition(doorData, NUMBER_OF_X_OFFSET);
    }

    private int getTargetX2Count()
    {
        return ByteConversions.getShortInByteArrayAtPosition(doorData, NUMBER_OF_X_OFFSET);
    }

    private int getTargetX3Count()
    {
        return ByteConversions.getShortInByteArrayAtPosition(doorData, NUMBER_OF_X_OFFSET);
    }

    public static int populateObjects(byte[] data, int offset, List doorList, int numberOfDoors, int variableDoorDataSize)
    {
        for (int doorIndex = 0; doorIndex < numberOfDoors; ++doorIndex)
        {
            Door door = new Door();
            doorList.add(door);
            offset = door.initialize(data, offset);
        }
        
        byte variableDoorData[] = new byte[variableDoorDataSize];
        System.arraycopy(data, offset, variableDoorData, 0, variableDoorData.length);
        offset += variableDoorData.length;
        
        int variableDoorDataOffset = 0;
        for (int doorIndex = 0; doorIndex < doorList.size(); ++doorIndex)
        {
            Door door = (Door)doorList.get(doorIndex);
            variableDoorDataOffset = door.initializeVariableDoorData(variableDoorData, variableDoorDataOffset);
        }
        
        return offset;
    }
    
    private int updateDataFromList(byte variableDoorData[], int variableDoorDataOffset, short[] array)
    {
        for (int index = 0; index < array.length; ++index)
        {
            ByteConversions.setShortInByteArrayAtPosition(array[index], variableDoorData, variableDoorDataOffset);
            variableDoorDataOffset += 2;
        }

        return variableDoorDataOffset;
    }


    private int updateVariableDoorData(byte[] variableDoorData, int variableDoorDataOffset)
    {
        variableDoorDataOffset = updateDataFromList(variableDoorData, variableDoorDataOffset, vertexArray);
        variableDoorDataOffset = updateDataFromList(variableDoorData, variableDoorDataOffset, facetArray);
        variableDoorDataOffset = updateDataFromList(variableDoorData, variableDoorDataOffset, roomArray);
        variableDoorDataOffset = updateDataFromList(variableDoorData, variableDoorDataOffset, bitmapDxArray);
        variableDoorDataOffset = updateDataFromList(variableDoorData, variableDoorDataOffset, bitmapDyArray);
        variableDoorDataOffset = updateDataFromList(variableDoorData, variableDoorDataOffset, targetX1Array);
        variableDoorDataOffset = updateDataFromList(variableDoorData, variableDoorDataOffset, targetX2Array);
        variableDoorDataOffset = updateDataFromList(variableDoorData, variableDoorDataOffset, targetX3Array);
        
        return variableDoorDataOffset;
    }

    public static int updateData(byte[] newData, int offset, List doorList)
    {
        int variableDoorDataSize = 0;
        for (int doorIndex = 0; doorIndex < doorList.size(); ++doorIndex)
        {
            Door door = (Door)doorList.get(doorIndex);
            System.arraycopy(door.getDoorData(), 0, newData, offset, door.getDoorData().length);
            offset += door.getDoorData().length;
            variableDoorDataSize += door.getVariableDoorDataSize();
        }
        
        byte variableDoorData[] = new byte[variableDoorDataSize];

        int variableDoorDataOffset = 0;
        for (int doorIndex = 0; doorIndex < doorList.size(); ++doorIndex)
        {
            Door door = (Door)doorList.get(doorIndex);
            door.updateVariableDoorData(variableDoorData, variableDoorDataOffset);
        }
        
        System.arraycopy(variableDoorData, 0, newData, offset, variableDoorData.length);
        offset += variableDoorData.length;
        
        return offset;
    }

    public static int computeDataSize(byte[] data, int offset, int numberOfDoors, int variableDoorDataSize)
    {
        offset += numberOfDoors * DOOR_RECORD_LENGTH;
        offset += variableDoorDataSize;
        return offset;
    }
    
    private int getVariableDoorDataSize()
    {
        int size = 0;
        size += 2 * vertexArray.length;
        size += 2 * facetArray.length;
        size += 2 * roomArray.length;
        size += 2 * bitmapDxArray.length;
        size += 2 * bitmapDyArray.length;
        size += 2 * targetX1Array.length;
        size += 2 * targetX2Array.length;
        size += 2 * targetX3Array.length;

        return size;
    }

    public static int computeDataSize(int gameVersion, List doorList)
    {
        int dataSize = 0;
        dataSize += doorList.size() * DOOR_RECORD_LENGTH;
        
        int variableDoorDataSize = 0;
        for (int doorIndex = 0; doorIndex < doorList.size(); ++doorIndex)
        {
            Door door = (Door)doorList.get(doorIndex);
            variableDoorDataSize += door.getVariableDoorDataSize();
        }

        dataSize += variableDoorDataSize;
        
        return dataSize;
    }

    public byte[] getDoorData()
    {
        return this.doorData;
    }
    public int getDoorOffset()
    {
        return this.doorOffset;
    }

    public int getRecordSize()
    {
        return getDoorData().length + getVariableDoorDataSize();
    }

    // Door things to decode

    public static List getConstantOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(ATTRIBUTES_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "attributes"));
        offsetList.add(new ComparativeTableControl.OffsetData(DOOR_ID_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "door id"));
        offsetList.add(new ComparativeTableControl.OffsetData(TIME_SINCE_TRIGGERED_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "time since triggered"));
        offsetList.add(new ComparativeTableControl.OffsetData(DIRECTION_X_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "direction x"));
        offsetList.add(new ComparativeTableControl.OffsetData(DIRECTION_Y_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "direction y"));
        offsetList.add(new ComparativeTableControl.OffsetData(DIRECTION_Z_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "direction z"));
        offsetList.add(new ComparativeTableControl.OffsetData(DIRECTION_MAGNITUDE_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "direction magnitude"));
        offsetList.add(new ComparativeTableControl.OffsetData(OPENING_VELOCITY_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "opening velocity"));
        offsetList.add(new ComparativeTableControl.OffsetData(CLOSING_VELOCITY_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "closing velocity"));
        offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_TARGETS_1_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of targets 1"));
        offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_TARGETS_2_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of targets 2"));
        offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_TARGETS_3_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of targets 3"));
        offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_X_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of x"));
        offsetList.add(new ComparativeTableControl.OffsetData(DOOR_STATE_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "state"));

        offsetList.add(new ComparativeTableControl.OffsetData(PADDING_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "padding"));
        offsetList.add(new ComparativeTableControl.OffsetData(VERTEX_OFFSET_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_OFFSET_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset"));
        offsetList.add(new ComparativeTableControl.OffsetData(ROOM_OFFSET_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset"));
        offsetList.add(new ComparativeTableControl.OffsetData(BITMAP_DX_OFFSET_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset"));
        offsetList.add(new ComparativeTableControl.OffsetData(BITMAP_DY_OFFSET_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset"));
        offsetList.add(new ComparativeTableControl.OffsetData(VERTEX_X_OFFSET_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset"));
        offsetList.add(new ComparativeTableControl.OffsetData(VERTEX_Y_OFFSET_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset"));
        offsetList.add(new ComparativeTableControl.OffsetData(VERTEX_Z_OFFSET_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset"));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getConstantComparativeDataSource(final List doorList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return doorList.size();
            }

            public byte[] getData(int dataRow)
            {
                Door unknown = (Door)doorList.get(dataRow);
                return unknown.getDoorData();
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
