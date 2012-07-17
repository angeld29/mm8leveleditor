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

public class MapNote
{
    private static final int MAP_NOTE_RECORD_SIZE = 306; // 1 byte
    
    private static final int ACTIVE_OFFSET = 0; // 1 byte
    private static final int PADDING_OFFSET = 1; // 1 byte
    private static final int X_OFFSET = 2; // 2 bytes
    private static final int Y_OFFSET = 4; // 2 bytes
    private static final int TEXT_OFFSET = 6; // 2 bytes
    private static final int TEXT_MAX_LENGTH = 298;
    private static final int ID_OFFSET = 304; // 2 bytes
    
    private byte mapNoteData[] = null;
    
    public MapNote()
    {
        this.mapNoteData = new byte[MAP_NOTE_RECORD_SIZE];
    }

    public MapNote(short id, boolean active, short x, short y, String text)
    {
        setId(id);
        setActive(active);
        setX(x);
        setY(y);
        setText(text);
    }

    public int initialize(byte dataSrc[], int offset)
    {
        System.arraycopy(dataSrc, offset, this.mapNoteData, 0, this.mapNoteData.length);
        offset += this.mapNoteData.length;

        return offset;
    }

    public static int populateObjects(byte[] data, int offset, List mapNoteList, int numberOfMapNodes)
    {
        for (int mapNoteIndex = 0; mapNoteIndex < numberOfMapNodes; ++mapNoteIndex)
        {
            MapNote mapNote = new MapNote();
            mapNoteList.add(mapNote);
            offset = mapNote.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List mapNoteList)
    {
        for (int mapNoteIndex = 0; mapNoteIndex < mapNoteList.size(); ++mapNoteIndex)
        {
            MapNote mapNote = (MapNote)mapNoteList.get(mapNoteIndex);
            System.arraycopy(mapNote.getMapNoteData(), 0, newData, offset, mapNote.getMapNoteData().length);
            offset += mapNote.getMapNoteData().length;
        }
        
        return offset;
    }
    

    public static int getRecordSize()
    {
        return MAP_NOTE_RECORD_SIZE;
    }
    
    public boolean isActive()
    {
        return getMapNoteData()[ACTIVE_OFFSET] != 0;
    }
    public void setActive(boolean active)
    {
        getMapNoteData()[ACTIVE_OFFSET] = (byte)(active ? 1 : 0);
    }
    public short getId()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getMapNoteData(), ID_OFFSET);
    }
    public void setId(short id)
    {
        ByteConversions.setShortInByteArrayAtPosition(id, getMapNoteData(), ID_OFFSET);
    }
    public String getText()
    {
        return ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(getMapNoteData(), TEXT_OFFSET, TEXT_MAX_LENGTH);
    }
    public void setText(String text)
    {
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(text, getMapNoteData(), TEXT_OFFSET, TEXT_MAX_LENGTH);
    }
    public short getX()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getMapNoteData(), X_OFFSET);
    }
    public void setX(short x)
    {
        ByteConversions.setShortInByteArrayAtPosition(x, getMapNoteData(), X_OFFSET);
    }
    public short getY()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getMapNoteData(), Y_OFFSET);
    }
    public void setY(short y)
    {
        ByteConversions.setShortInByteArrayAtPosition(y, getMapNoteData(), Y_OFFSET);
    }
    public byte[] getMapNoteData()
    {
        return this.mapNoteData;
    }

    public int getTextMaxLength()
    {
        return TEXT_MAX_LENGTH;
    }

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();

        offsetList.add(new ComparativeTableControl.OffsetData(ACTIVE_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "Active?"));
        offsetList.add(new ComparativeTableControl.OffsetData(X_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "X"));
        offsetList.add(new ComparativeTableControl.OffsetData(Y_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(TEXT_OFFSET, TEXT_MAX_LENGTH, ComparativeTableControl.REPRESENTATION_STRING, "Text"));
        offsetList.add(new ComparativeTableControl.OffsetData(ID_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "ID"));
        offsetList.add(new ComparativeTableControl.OffsetData(PADDING_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "Padding"));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List monsterList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return monsterList.size();
            }

            public byte[] getData(int dataRow)
            {
                MapNote monster = (MapNote)monsterList.get(dataRow);
                return monster.getMapNoteData();
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
