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

import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.NumberLabelValueHolder;
import org.gamenet.util.ByteConversions;

public class ContainedItem
{
    private static final int ITEM_RECORD_LENGTH_MM6 = 28;
    private static final int ITEM_RECORD_LENGTH_MM7 = 36;
    
    private static final int ATTRIBUTE_TYPE_IDENTIFIED = 0x0001;
    private static final int ATTRIBUTE_TYPE_BROKEN = 0x0002;
    private static final int ATTRIBUTE_TYPE_CURSED = 0x0004;
    private static final int ATTRIBUTE_TYPE_TEMPORARY_POWER = 0x0008;
    private static final int ATTRIBUTE_TYPE_SPECIAL_EFFECT_1 = 0x0010;
    private static final int ATTRIBUTE_TYPE_SPECIAL_EFFECT_2 = 0x0020;
    private static final int ATTRIBUTE_TYPE_SPECIAL_EFFECT_3 = 0x0040;
    private static final int ATTRIBUTE_TYPE_SPECIAL_EFFECT_4 = 0x0080;
    private static final int ATTRIBUTE_TYPE_STOLEN = 0x0100;
    private static final int ATTRIBUTE_TYPE_HARDENED = 0x0200;

    private static final int ITEM_NUMBER_OFFSET = 0; // four bytes
    private static final int STANDARD_MAGIC_CLASS_OFFSET = 4; // four bytes, also potion power
    private static final int VALUE_MODIFIER_OFFSET = 8; // four bytes
    
    private static final int SPECIAL_MAGIC_CLASS_OFFSET = 12; // four bytes
    private static final int AMOUNT_OF_GOLD_OFFSET = 12; // 4 bytes
    
    private static final int CHARGES_OFFSET = 16; // four bytes
    private static final int ATTRIBUTE_OFFSET = 20; // four bytes

    private static final int BODY_LOCATION_OFFSET = 24; // 1 byte
    private static final int MAXIMUM_CHARGES_OFFSET = 25; // 1 byte
    private static final int OWNER_OFFSET = 26; // 1 byte
    private static final int PADDING_OFFSET = 27; // 1 byte
    
    private static final int TIME_OFFSET = 28; // 8 bytes // mm7,mm8

    private int gameVersion = 0;
    private byte containedItemData[] = null;

    public ContainedItem(int gameVersion)
    {
        super();
        this.gameVersion = gameVersion;
        
        if (GameVersion.MM6 == gameVersion)
            this.containedItemData = new byte[ITEM_RECORD_LENGTH_MM6];
        else this.containedItemData = new byte[ITEM_RECORD_LENGTH_MM7];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        System.arraycopy(dataSrc, offset, this.containedItemData, 0, this.containedItemData.length);
        offset += this.containedItemData.length;
        
        return offset;
    }

    public static int populateObjects(int gameVersion, byte[] data, int offset, List containedItemList)
    {
        int containedItemCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int containedItemIndex = 0; containedItemIndex < containedItemCount; ++containedItemIndex)
        {
            ContainedItem containedItem = new ContainedItem(gameVersion);
            containedItemList.add(containedItem);
            offset = containedItem.initialize(data, offset);
        }
        
        return offset;
    }

    public int updateData(byte[] newData, int offset)
    {
        System.arraycopy(getContainedItemData(), 0, newData, offset, getContainedItemData().length);
        offset += getContainedItemData().length;
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List containedItemList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(containedItemList.size(), newData, offset);
        offset += 4;

        for (int containedItemIndex = 0; containedItemIndex < containedItemList.size(); ++containedItemIndex)
        {
            ContainedItem containedItem = (ContainedItem)containedItemList.get(containedItemIndex);
            System.arraycopy(containedItem.getContainedItemData(), 0, newData, offset, containedItem.getContainedItemData().length);
            offset += containedItem.getContainedItemData().length;
        }
        
        return offset;
    }

    public byte[] getContainedItemData()
    {
        return this.containedItemData;
    }


    public int getItemNumber()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(containedItemData, ITEM_NUMBER_OFFSET);
    }
    
    public void setItemNumber(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, containedItemData, ITEM_NUMBER_OFFSET);
    }
    
    public int getStandardMagicClass()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(containedItemData, STANDARD_MAGIC_CLASS_OFFSET);
    }
    
    public void setStandardMagicClass(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, containedItemData, STANDARD_MAGIC_CLASS_OFFSET);
    }
    
    public int getValueModifier()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(containedItemData, VALUE_MODIFIER_OFFSET);
    }
    
    public void setValueModifier(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, containedItemData, VALUE_MODIFIER_OFFSET);
    }

    public int getSpecialMagicClass()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(containedItemData, SPECIAL_MAGIC_CLASS_OFFSET);
    }
    
    public void setSpecialMagicClass(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, containedItemData, SPECIAL_MAGIC_CLASS_OFFSET);
    }
    
    public int getGoldAmount()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(containedItemData, AMOUNT_OF_GOLD_OFFSET);
    }
    public void setGoldAmount(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, containedItemData, AMOUNT_OF_GOLD_OFFSET);
    }

    public int getCharges()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(containedItemData, CHARGES_OFFSET);
    }
    
    public void setCharges(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, containedItemData, CHARGES_OFFSET);
    }

    public int getAttributes()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(containedItemData, ATTRIBUTE_OFFSET);
    }
    
    public void setAttributes(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, containedItemData, ATTRIBUTE_OFFSET);
    }
    
    public int getBodyLocation()
    {
        return ByteConversions.convertByteToInt(containedItemData[BODY_LOCATION_OFFSET]);
    }
    public void setBodyLocation(int value)
    {
        containedItemData[BODY_LOCATION_OFFSET] = ByteConversions.convertIntToByte(value);
    }

    public int getMaximumCharges()
    {
        return ByteConversions.convertByteToInt(containedItemData[MAXIMUM_CHARGES_OFFSET]);
    }
    public void setMaximumCharges(int value)
    {
        containedItemData[MAXIMUM_CHARGES_OFFSET] = ByteConversions.convertIntToByte(value);
    }

    public int getOwner()
    {
        return ByteConversions.convertByteToInt(containedItemData[OWNER_OFFSET]);
    }
    public void setOwner(int value)
    {
        containedItemData[OWNER_OFFSET] = ByteConversions.convertIntToByte(value);
    }

    public long getTime()
    {
        return ByteConversions.getLongInByteArrayAtPosition(containedItemData, TIME_OFFSET);
    }
    public void setTime(long value)
    {
        ByteConversions.setLongInByteArrayAtPosition(value, containedItemData, TIME_OFFSET);
    }

    public static int getRecordSize(int gameVersion)
    {
        if (GameVersion.MM6 == gameVersion)
            return ITEM_RECORD_LENGTH_MM6;
        else return ITEM_RECORD_LENGTH_MM7;
    }

    public static List getOffsetList(int gameVersion)
    {
        List offsetList = new ArrayList();
        
        addOffsets(gameVersion, offsetList, 0, -1);

        return offsetList;
    }


    public static void addOffsets(int gameVersion, List offsetList, int startingOffset, int index)
    {
        String prefix = "";
        if (index != -1)  prefix = "ContainedItem#" + String.valueOf(index) + ":";
        
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + ITEM_NUMBER_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, prefix + "item #"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + STANDARD_MAGIC_CLASS_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, prefix + "std magic class"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + VALUE_MODIFIER_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, prefix + "value modifier"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + SPECIAL_MAGIC_CLASS_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, prefix + "spc magic class/gold"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + CHARGES_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, prefix + "charges"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + ATTRIBUTE_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, prefix + "attributes"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + BODY_LOCATION_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, prefix + "body location"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + MAXIMUM_CHARGES_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, prefix + "max charges"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + OWNER_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, prefix + "owner"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + PADDING_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, prefix + "padding"));

        if (GameVersion.MM6 != gameVersion)
        {
            offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + TIME_OFFSET, 8, ComparativeTableControl.REPRESENTATION_TIME, prefix + "time"));
        }
    }

    public static ComparativeTableControl.DataSource getComparativeDataSource(final List containedItemList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return containedItemList.size();
            }

            public byte[] getData(int dataRow)
            {
                ContainedItem containedItem = (ContainedItem)containedItemList.get(dataRow);
                return containedItem.getContainedItemData();
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

    public static ComparativeTableControl.DataSource getComparativeDataSource(final ContainedItem containedItemArray[])
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return containedItemArray.length;
            }

            public byte[] getData(int dataRow)
            {
                ContainedItem containedItem = containedItemArray[dataRow];
                return containedItem.getContainedItemData();
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

    public NumberLabelValueHolder[] getBodyLocationOptions()
    {
        return new NumberLabelValueHolder[] {
            	new NumberLabelValueHolder(0, "Inventory"),
            	new NumberLabelValueHolder(1, "Right Hand"),
            	new NumberLabelValueHolder(2, "Left Hand"),
            	new NumberLabelValueHolder(3, "Bow"),
            	new NumberLabelValueHolder(4, "Armor"),
            	new NumberLabelValueHolder(5, "Helm"),
            	new NumberLabelValueHolder(6, "Belt"),
            	new NumberLabelValueHolder(7, "Cloak"),
            	new NumberLabelValueHolder(8, "Guantlets"),
            	new NumberLabelValueHolder(9, "Boots"),
            	new NumberLabelValueHolder(10, "Amulet"),
            	new NumberLabelValueHolder(11, "Ring (1,1)"),
            	new NumberLabelValueHolder(12, "Ring (1,2)"),
            	new NumberLabelValueHolder(13, "Ring (1,3)"),
            	new NumberLabelValueHolder(14, "Ring (2,1)"),
            	new NumberLabelValueHolder(15, "Ring (2,2)"),
            	new NumberLabelValueHolder(16, "Ring (2,3)")
        };
    }

    public boolean isEmptyItem()
    {
        // TODO: add rest of fields
        if (getItemNumber() != 0)  return false;
        if (getStandardMagicClass () != 0)  return false;
        if (getValueModifier() != 0)  return false;
        if (getSpecialMagicClass() != 0)  return false;
        if (getGoldAmount() != 0)  return false;
        if (getCharges() != 0)  return false;
        if (getAttributes() != 0)  return false;
        if (getBodyLocation() != 0)  return false;
        if (getMaximumCharges() != 0)  return false;
        if (getOwner() != 0)  return false;
        if (GameVersion.MM6 != gameVersion)
        {
            if (getTime() != 0)  return false;
        }

        return true;
    }
    public int getGameVersion()
    {
        return this.gameVersion;
    }
}
