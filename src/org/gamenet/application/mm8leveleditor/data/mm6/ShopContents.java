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

import java.util.List;

public class ShopContents
{
    private static final int NUMBER_OF_ITEMS = 12;
    
    private long shopContentsDataOffset = 0;

    private int gameVersion = 0;
    private ItemContainer itemContainer = null;
    
    public ShopContents(int gameVersion)
    {
        super();
        this.gameVersion = gameVersion;
    }

    public ContainedItem[] getContainedItemArray()
    {
        return this.itemContainer.getContainedItemArray();
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.shopContentsDataOffset = offset;
    
        itemContainer = new ItemContainer(gameVersion);
        offset = itemContainer.initializeItems(NUMBER_OF_ITEMS, dataSrc, offset);
        
        return offset;
    }

    public int updateData(byte[] newData, int offset)
    {
        offset = this.itemContainer.updateDataItems(newData, offset);
        
        return offset;
    }

    public static boolean checkDataIntegrity(int gameVersion, byte[] data, int offset, int expectedNewOffset)
    {
        offset += ContainedItem.getRecordSize(gameVersion) * NUMBER_OF_ITEMS;
        
        return (offset == expectedNewOffset);
    }

    public static int populateObjects(int gameVersion, byte[] data, int offset, List shopContentsList, int shopContentsCount)
    {
        for (int shopContentsIndex = 0; shopContentsIndex < shopContentsCount; ++shopContentsIndex)
        {
            ShopContents shopContents = new ShopContents(gameVersion);
            shopContentsList.add(shopContents);
            offset = shopContents.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List shopContentsList)
    {
        for (int shopContentsIndex = 0; shopContentsIndex < shopContentsList.size(); ++shopContentsIndex)
        {
            ShopContents shopContents = (ShopContents)shopContentsList.get(shopContentsIndex);
            offset = shopContents.updateData(newData, offset);
        }

        return offset;
    }

    public long getShopContentsDataOffset()
    {
        return shopContentsDataOffset;
    }
    
    public static int getRecordSize(int gameVersion)
    {
        return ContainedItem.getRecordSize(gameVersion) * NUMBER_OF_ITEMS;
    }
    public int getGameVersion()
    {
        return this.gameVersion;
    }
}
