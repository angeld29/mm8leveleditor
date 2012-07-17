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

public class MapOutlineLine
{
    private static final int MAP_OUTLINE_RECORD_LENGTH = 12;

    private static final int ATTRIBUTE_SHOULD_DRAW_LINE = 0x00000001;
    
    private static final int VERTEX1_OFFSET = 0; // 2 bytes
    private static final int VERTEX2_OFFSET = 2; // 2 bytes
    private static final int FACET1_OFFSET = 4; // 2 bytes
    private static final int FACET2_OFFSET = 6; // 2 bytes
    private static final int Z_OFFSET = 8; // 2 bytes
    private static final int ATTRIBUTE_OFFSET = 10; // 2 bytes

    private int mapOutlineLineOffset = 0;
    private byte mapOutlineLineData[] = null;

    public MapOutlineLine()
    {
        super();
    }

    public MapOutlineLine(int vertex1, int vertex2, int vertex3, int vertex4, int z)
    {
        super();
        this.mapOutlineLineData = new byte[MAP_OUTLINE_RECORD_LENGTH];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.mapOutlineLineOffset = offset;
        this.mapOutlineLineData = new byte[MAP_OUTLINE_RECORD_LENGTH];
        System.arraycopy(dataSrc, offset, this.mapOutlineLineData, 0, this.mapOutlineLineData.length);
        offset += this.mapOutlineLineData.length;
        
        return offset;
    }

    public static int populateObjects(byte[] data, int offset, List mapOutlineLineList)
    {
        int mapOutlineLineCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int mapOutlineLineIndex = 0; mapOutlineLineIndex < mapOutlineLineCount; ++mapOutlineLineIndex)
        {
            MapOutlineLine mapOutlineLine = new MapOutlineLine();
            mapOutlineLineList.add(mapOutlineLine);
            offset = mapOutlineLine.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List mapOutlineLineList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(mapOutlineLineList.size(), newData, offset);
        offset += 4;

        for (int mapOutlineLineIndex = 0; mapOutlineLineIndex < mapOutlineLineList.size(); ++mapOutlineLineIndex)
        {
            MapOutlineLine mapOutlineLine = (MapOutlineLine)mapOutlineLineList.get(mapOutlineLineIndex);
            System.arraycopy(mapOutlineLine.getMapOutlineLineData(), 0, newData, offset, mapOutlineLine.getMapOutlineLineData().length);
            offset += mapOutlineLine.getMapOutlineLineData().length;
        }
        
        return offset;
    }

    public byte[] getMapOutlineLineData()
    {
        return this.mapOutlineLineData;
    }
    public int getMapOutlineLineOffset()
    {
        return this.mapOutlineLineOffset;
    }

    public static int getRecordSize()
    {
        return MAP_OUTLINE_RECORD_LENGTH;
    }


    // Unknown things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(VERTEX1_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Vertex 1"));
        offsetList.add(new ComparativeTableControl.OffsetData(VERTEX2_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Vertex 2"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET1_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Facet 1"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET2_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Facet 2"));
        offsetList.add(new ComparativeTableControl.OffsetData(Z_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Z"));
        offsetList.add(new ComparativeTableControl.OffsetData(ATTRIBUTE_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Attributes"));

    	return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List mapOutlineLineList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return mapOutlineLineList.size();
            }

            public byte[] getData(int dataRow)
            {
                MapOutlineLine mapOutlineLine = (MapOutlineLine)mapOutlineLineList.get(dataRow);
                return mapOutlineLine.getMapOutlineLineData();
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
