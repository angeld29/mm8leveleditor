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

import org.gamenet.util.ByteConversions;

public class UnknownMapData
{
    // following that is
    // x10557c - 1070460
    // A = 4 bytes record count?  22372?  2 x 2 x 7 x 17 x 47? 
    
    // following that is
    // x105580 - 1070464
    // 22372 * 2 bytes -- unanalysed
    //  first non-zero, offset 12120 -- convergent somewhere between 260-290?
    // x1084d8 - 1082584

    // x110448 - 1115208
    // 22372 records going from 0 to 22371
    // 0
    // 1
    // [...]
    // 22370
    // 22371
    
    private int idDataBaseOffset = 0;
    private int incrementingDataBaseOffset = 0;

    private short idData[] = null;
    private int offsetMap[][] = null;

    public UnknownMapData()
    {
        super();
    }

    public int initialize(byte[] data, int offset, int MAP_WIDTH, int MAP_HEIGHT)
    {
        int idDataCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;
        
        idDataBaseOffset = offset;
        
        idData = new short[idDataCount];
        for (int idIndex = 0; idIndex < idDataCount; idIndex++)
        {
            idData[idIndex] = ByteConversions.getShortInByteArrayAtPosition(data, offset);
            offset += 2;
        }
        
        incrementingDataBaseOffset = offset;
        offsetMap = new int[MAP_HEIGHT][MAP_WIDTH];
        for (int row = 0; row < MAP_HEIGHT; ++row)
        {
            for (int col = 0; col < MAP_WIDTH; ++col)
            {
                offsetMap[row][col] = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
                offset += 4;
            }
        }

        return offset;
    }

    public int updateData(byte[] newData, int offset)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(idData.length / 2, newData, offset);
        offset += 4;
        
        System.arraycopy(idData, 0, newData, offset, idData.length);
        offset += idData.length;

        for (int row = 0; row < offsetMap.length; ++row)
        {
            for (int col = 0; col < offsetMap[row].length; ++col)
            {
                ByteConversions.setIntegerInByteArrayAtPosition(offsetMap[row][col], newData, offset);
                offset += 4;
            }
        }

        return offset;
    }

    public short[] getIdData()
    {
        return this.idData;
    }
    public int getIdDataBaseOffset()
    {
        return this.idDataBaseOffset;
    }

    public int[][] getOffsetMap()
    {
        return this.offsetMap;
    }
    public int getIncrementingDataBaseOffset()
    {
        return this.incrementingDataBaseOffset;
    }







    public short getUnknownShortDataAt(int row, int column)
    {
        return getIdData()[getOffsetMap()[row][column]];
    }

    public short[][] getMapDataAsShort()
    {
        int rows = getOffsetMap().length;
        int columns = getOffsetMap()[0].length;
        
        short map[][] = new short[rows][columns];
        for (int row = 0; row < rows; row++)
        {
            for (int column = 0; column < columns; column++)
            {
                map[row][column] = getUnknownShortDataAt(row, column);
            }
        }
        return map;
    }

}
