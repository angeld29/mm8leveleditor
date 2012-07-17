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
import org.gamenet.swing.controls.NumberLabelValueHolder;
import org.gamenet.util.ByteConversions;

public class ChestContents
{
    // 4204 total
    
    private static final int DCHEST_ID_OFFSET = 0; // 2 bytes
    private static final int OPENED_STATUS_OFFSET = 2; // 2 bytes
    
    private static final int NUMBER_OF_ITEMS = 140;
    
    // private static final int CONTENT_LOCATION_OFFSET = 4 + NUMBER_OF_ITEMS * ContainedItem.getRecordSize(gameVersion);
    private static final int CONTENT_LOCATION_WIDTH = 9; // short records
    private static final int CONTENT_LOCATION_HEIGHT = 9;

    // private static final int REMAINING_DATA_OFFSET = CONTENT_LOCATION_OFFSET + CONTENT_LOCATION_WIDTH * 2 * CONTENT_LOCATION_HEIGHT;
    private static final int REMAINING_DATA_LENGTH = 118;
    
    public static final int OPENED_STATUS_UNOPENED_UNTRAPPED_UNPLACED = 0;
    public static final int OPENED_STATUS_UNOPENED_TRAPPED_UNPLACED = 1;
    public static final int OPENED_STATUS_OPENED_UNPLACED = 2;
    // public static final int IDENTIFIED = 4;
    
    public static final int ATTRIBUTE_LOCKED = 1;
    public static final int ATTRIBUTE_INITIALIZED = 2;
    public static final int ATTRIBUTE_IDENTIFIED = 4;
    private static final int ATTRIBUTE_OFFSET = 2; // 2 bytes

    private int gameVersion = 0;
    
    private long chestContentsDataOffset = 0;

    private int openedStatus = 0;
    private int dChestIDNumber = 0;
    
    private ItemContainer itemContainer = null;

    private byte remainingData[] = null;
    
    public ChestContents(int gameVersion)
    {
        super();
        this.gameVersion = gameVersion;
    }

    public ChestContents(int gameVersion, int openedStatus)
    {
        this(gameVersion);
        
        this.dChestIDNumber = 0;
        this.openedStatus = openedStatus;
        
        this.itemContainer = new ItemContainer(gameVersion, NUMBER_OF_ITEMS, CONTENT_LOCATION_WIDTH, CONTENT_LOCATION_HEIGHT, 2);
        
        this.remainingData = new byte[REMAINING_DATA_LENGTH];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.chestContentsDataOffset = offset;
    
        this.dChestIDNumber = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        
        this.openedStatus = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        
        this.itemContainer = new ItemContainer(gameVersion);
        offset = itemContainer.initializeItems(NUMBER_OF_ITEMS, dataSrc, offset);
        offset = itemContainer.initializeMap(dataSrc, offset, CONTENT_LOCATION_WIDTH, CONTENT_LOCATION_HEIGHT, 2);
        
        this.remainingData = new byte[REMAINING_DATA_LENGTH];
        System.arraycopy(dataSrc, offset, this.remainingData, 0, REMAINING_DATA_LENGTH);
        offset += REMAINING_DATA_LENGTH;
        
        return offset;
    }

    public int updateData(byte[] newData, int offset)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)this.dChestIDNumber, newData, offset);
        offset += 2;
        
        ByteConversions.setShortInByteArrayAtPosition((short)this.openedStatus, newData, offset);
        offset += 2;
        
        offset = this.itemContainer.updateDataItems(newData, offset);
        offset = this.itemContainer.updateDataMap(newData, offset);
        
        System.arraycopy(this.remainingData, 0, newData, offset, this.remainingData.length);
        offset += this.remainingData.length;
        
        return offset;
    }

    public static int populateObjects(int gameVersion, byte[] data, int offset, List chestContentsList)
    {
        int chestContentsCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int chestContentsIndex = 0; chestContentsIndex < chestContentsCount; ++chestContentsIndex)
        {
            ChestContents chestContents = new ChestContents(gameVersion);
            chestContentsList.add(chestContents);
            offset = chestContents.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List chestContentsList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(chestContentsList.size(), newData, offset);
        offset += 4;

        for (int chestContentsIndex = 0; chestContentsIndex < chestContentsList.size(); ++chestContentsIndex)
        {
            ChestContents chestContents = (ChestContents)chestContentsList.get(chestContentsIndex);
            offset = chestContents.updateData(newData, offset);
        }

        return offset;
    }

    public long getChestContentsDataOffset()
    {
        return chestContentsDataOffset;
    }
    


    // Unknown things to decode
    public static List getRemainingDataOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(0, REMAINING_DATA_LENGTH, ComparativeTableControl.REPRESENTATION_INT_HEX));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getRemainingDataComparativeDataSource(final List chestContentsList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return chestContentsList.size();
            }

            public byte[] getData(int dataRow)
            {
                ChestContents chestContents = (ChestContents)chestContentsList.get(dataRow);
                return chestContents.getRemainingData();
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

    public ItemContainer getItemContainer()
    {
        return this.itemContainer;
    }
    
    public static NumberLabelValueHolder[] getOpenStatuses()
    {
        return new NumberLabelValueHolder[] {
            	new NumberLabelValueHolder(OPENED_STATUS_UNOPENED_UNTRAPPED_UNPLACED, "unopened, untrapped, items unplaced"),
            	new NumberLabelValueHolder(OPENED_STATUS_UNOPENED_TRAPPED_UNPLACED, "unopened, trapped, items unplaced"),
            	new NumberLabelValueHolder(OPENED_STATUS_OPENED_UNPLACED, "opened, items placed")
        };
    }
    
    public int getDChestIDNumber()
    {
        return this.dChestIDNumber;
    }
    public void setDChestIDNumber(int unknown1)
    {
        this.dChestIDNumber = unknown1;
    }

    public int getOpenedStatus()
    {
        return this.openedStatus;
    }
    public void setOpenedStatus(int openedStatus)
    {
        this.openedStatus = openedStatus;
    }

    public byte[] getRemainingData()
    {
        return this.remainingData;
    }

    public static int getRecordSize(int gameVersion)
    {
        return 2 + 2 + ItemContainer.getRecordSize(gameVersion, NUMBER_OF_ITEMS, CONTENT_LOCATION_WIDTH, CONTENT_LOCATION_HEIGHT, 2) + REMAINING_DATA_LENGTH;
    }
    public int getGameVersion()
    {
        return this.gameVersion;
    }
}
