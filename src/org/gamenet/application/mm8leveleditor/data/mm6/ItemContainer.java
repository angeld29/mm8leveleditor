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

public class ItemContainer
{
    // IMPLEMENT: containedItemArray should be a list for easier updating
    private int gameVersion = 0;
    private ContainedItem containedItemArray[] = null;
    private ContainerMap itemLocationMap = null;
    
    public ItemContainer(int gameVersion)
    {
        super();
        this.gameVersion = gameVersion;
    }

    public ItemContainer(int gameVersion, int numberOfItems, int itemLocationMapWidth, int itemLocationMapHeight, int itemLocationDataSize)
    {
        this(gameVersion);

        this.containedItemArray = new ContainedItem[numberOfItems];
        for (int itemIndex = 0; itemIndex < containedItemArray.length; itemIndex++)
        {
            containedItemArray[itemIndex] = new ContainedItem(gameVersion);
        }
        itemLocationMap = new ContainerMap(itemLocationMapWidth, itemLocationMapHeight, itemLocationDataSize);
    }

    public int initializeItems(int numberOfItems, byte itemData[], int itemDataOffset)
    {
        this.containedItemArray = new ContainedItem[numberOfItems];
        
        for (int itemIndex = 0; itemIndex < containedItemArray.length; itemIndex++)
        {
            containedItemArray[itemIndex] = new ContainedItem(gameVersion);
            itemDataOffset = containedItemArray[itemIndex].initialize(itemData, itemDataOffset);
        }
        
        return itemDataOffset;
    }

    public int initializeMap(byte itemLocationMapData[], int itemLocationMapDataOffset, int itemLocationMapWidth, int itemLocationMapHeight, int itemLocationDataSize)
    {
        itemLocationMap = new ContainerMap();
        return itemLocationMap.initialize(itemLocationMapData, itemLocationMapDataOffset, itemLocationMapWidth, itemLocationMapHeight, itemLocationDataSize);
    }

    public int updateDataItems(byte[] newData, int offset)
    {
        for (int itemIndex = 0; itemIndex < getContainedItemArray().length; itemIndex++)
        {
            ContainedItem containerItem = getContainedItemArray()[itemIndex];
            offset = containerItem.updateData(newData, offset);
        }

        return offset;
    }

    public int updateDataMap(byte[] newData, int offset)
    {
        return itemLocationMap.updateData(newData, offset);
    }

    public ContainedItem[] getContainedItemArray()
    {
        return this.containedItemArray;
    }
    
    public ContainerMap getItemLocationMap()
    {
        return this.itemLocationMap;
    }

    public static int getRecordSize(int gameVersion, int numberOfItems, int contentLocationWidth, int contentLocationHeight, int contentLocationDataSize)
    {
        return numberOfItems * ContainedItem.getRecordSize(gameVersion) + ContainerMap.getRecordSize(contentLocationWidth, contentLocationHeight, contentLocationDataSize);
    }
    public int getGameVersion()
    {
        return this.gameVersion;
    }
}
