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

import org.gamenet.util.ByteConversions;

public class ContainerMap
{
    private int itemLocationMapDataOffset;
    private int itemLocationMapWidth;
    private int itemLocationMapHeight;
    private int itemLocationMapDataSize;

    private int itemLocationMap[][] = null;
    
    public ContainerMap()
    {
        super();
    }

    public ContainerMap(int itemLocationMapWidth, int itemLocationMapHeight, int itemLocationMapDataSize)
    {
        super();
        
        this.itemLocationMapWidth = itemLocationMapWidth;
        this.itemLocationMapHeight = itemLocationMapHeight;
        this.itemLocationMapDataSize = itemLocationMapDataSize;
        itemLocationMap = new int[itemLocationMapHeight][itemLocationMapWidth];
    }

    public int getItemLocationMapHeight()
    {
        return this.itemLocationMapHeight;
    }
    public int getItemLocationMapWidth()
    {
        return this.itemLocationMapWidth;
    }

    public int initialize(byte itemLocationMapData[], int itemLocationMapDataOffset, int itemLocationMapWidth, int itemLocationMapHeight, int itemLocationMapDataSize)
    {
        this.itemLocationMapDataOffset = itemLocationMapDataOffset;
        this.itemLocationMapWidth = itemLocationMapWidth;
        this.itemLocationMapHeight = itemLocationMapHeight;
        this.itemLocationMapDataSize = itemLocationMapDataSize;
        
        itemLocationMap = new int[itemLocationMapHeight][itemLocationMapWidth];
        for (int locationRowIndex = 0; locationRowIndex < itemLocationMapHeight; ++locationRowIndex)
        {
            for (int locationColumnIndex = 0; locationColumnIndex < itemLocationMapWidth; ++locationColumnIndex)
            {
                if (2 == itemLocationMapDataSize)
                    itemLocationMap[locationRowIndex][locationColumnIndex] = ByteConversions.getShortInByteArrayAtPosition(itemLocationMapData, itemLocationMapDataOffset);
                else if (4 == itemLocationMapDataSize)
                    itemLocationMap[locationRowIndex][locationColumnIndex] = ByteConversions.getIntegerInByteArrayAtPosition(itemLocationMapData, itemLocationMapDataOffset);
                itemLocationMapDataOffset += itemLocationMapDataSize;
            }
        }
        
        return itemLocationMapDataOffset;
    }

    public int updateData(byte[] newData, int offset)
    {
        // quick hacky update
        for (int locationRowIndex = 0; locationRowIndex < itemLocationMapHeight; ++locationRowIndex)
        {
            for (int locationColumnIndex = 0; locationColumnIndex < itemLocationMapWidth; ++locationColumnIndex)
            {
                if (2 == itemLocationMapDataSize)
                    ByteConversions.setShortInByteArrayAtPosition((short)itemLocationMap[locationRowIndex][locationColumnIndex], newData, offset);
                else if (4 == itemLocationMapDataSize)
                    ByteConversions.setIntegerInByteArrayAtPosition(itemLocationMap[locationRowIndex][locationColumnIndex], newData, offset);
                
                offset += itemLocationMapDataSize;
            }
        }

        return offset;
    }

    public int[][] getItemLocationMap()
    {
        return this.itemLocationMap;
    }

    public static int getRecordSize(int contentLocationWidth, int contentLocationHeight, int contentLocationDataSize)
    {
        return contentLocationDataSize * contentLocationWidth * contentLocationHeight;
    }
}
