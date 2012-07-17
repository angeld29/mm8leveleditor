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
import org.gamenet.swing.controls.Vertex3DValueHolder;
import org.gamenet.util.ByteConversions;

public class SpawnPoint implements Vertex3DValueHolder
{
    private static final int X_OFFSET = 0; // 4 bytes
    private static final int Y_OFFSET = 4; // 4 bytes
    private static final int Z_OFFSET = 8; // 4 bytes
    private static final int RADIUS_OFFSET = 12; // 2 bytes
    private static final int TYPE_ID_OFFSET = 14; // 2 bytes
    
    private static final int INDEX_OFFSET = 16; // 2 bytes
    private static final int ATTRIBUTE_OFFSET = 18; // 2 bytes
    
    private static final int GROUP_OFFSET = 20; // 4 bytes
//    1 - bunch of goblins
//    2 - bunch of mages
//    3 - bunch of archers
//    4 - one goblin
//    5 - one apprentice mage
//    6 - one archer
//    7 - one goblin shaman
//    8 - one journeyman mage
//    9 - one archer
//    10 - one goblin king
//    11 - one mage

    private static final int MONSTER_RECORD_LENGTH_MM6 = 20;
    private static final int MONSTER_RECORD_LENGTH_MM7 = 24;
    
    // end of file:
    // A = 4 byte record cound
    // A * 20 byte records

    private long monsterOffset = 0;

//    int x = 0;
//    int y = 0;
//    int z = 0;
//    int monsterClass = 0;
    private int gameVersion = GameVersion.MM6;
    private byte monsterData[] = null;

    public SpawnPoint(int gameVersion)
    {
        super();

        this.gameVersion = gameVersion;
    }

    public SpawnPoint(int gameVersion, int monsterClass, int x, int y, int z, int radius)
    {
        this(gameVersion);
        
        if (GameVersion.MM6 == gameVersion)
            this.monsterData = new byte[MONSTER_RECORD_LENGTH_MM6];
        else this.monsterData = new byte[MONSTER_RECORD_LENGTH_MM7];

        this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setRadius(radius);
		this.setMonsterClass(monsterClass);
	}

    public int initialize(byte dataSrc[], int offset)
    {
        this.monsterOffset = offset;
        
        if (GameVersion.MM6 == gameVersion)
            this.monsterData = new byte[MONSTER_RECORD_LENGTH_MM6];
        else this.monsterData = new byte[MONSTER_RECORD_LENGTH_MM7];

        System.arraycopy(dataSrc, offset, this.monsterData, 0, this.monsterData.length);
        offset += this.monsterData.length;
        
        return offset;
    }

    public static int populateObjects(int gameVersion, byte[] data, int offset, List monsterList)
    {
        int monsterCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int monsterIndex = 0; monsterIndex < monsterCount; ++monsterIndex)
        {
            SpawnPoint monster = new SpawnPoint(gameVersion);
            offset = monster.initialize(data, offset);
            monsterList.add(monster);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List monsterList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(monsterList.size(), newData, offset);
        offset += 4;

        for (int monsterIndex = 0; monsterIndex < monsterList.size(); ++monsterIndex)
        {
            SpawnPoint monster = (SpawnPoint)monsterList.get(monsterIndex);
            System.arraycopy(monster.getMonsterData(), 0, newData, offset, monster.getRecordSize());
            offset += monster.getRecordSize();
        }
        
        return offset;
    }

    public int getX()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getMonsterData(), X_OFFSET);
    }
    
    public void setX(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getMonsterData(), X_OFFSET);
    }
    
    public int getY()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getMonsterData(), Y_OFFSET);
    }
    
    public void setY(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getMonsterData(), Y_OFFSET);
    }
    
    public int getZ()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getMonsterData(), Z_OFFSET);
    }
    
    public void setZ(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getMonsterData(), Z_OFFSET);
    }
    
    public int getRadius()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getMonsterData(), RADIUS_OFFSET);
    }
    
    public void setRadius(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getMonsterData(), RADIUS_OFFSET);
    }
    
    public int getMonsterClass()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getMonsterData(), INDEX_OFFSET);
    }
    
    public void setMonsterClass(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getMonsterData(), INDEX_OFFSET);
    }
    
    public byte[] getMonsterData()
    {
        return this.monsterData;
    }
    
    public long getMonsterOffset()
    {
        return this.monsterOffset;
    }

    public static int getRecordSize(int gameVersion)
    {
        if (GameVersion.MM6 == gameVersion)
            return MONSTER_RECORD_LENGTH_MM6;
        else return MONSTER_RECORD_LENGTH_MM7;
    }

    public int getRecordSize()
    {
        return getRecordSize(gameVersion);
    }

    // Unknown things to decode

    public static List getOffsetList(int gameVersion)
    {
        List offsetList = new ArrayList();

        offsetList.add(new ComparativeTableControl.OffsetData(X_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "X"));
        offsetList.add(new ComparativeTableControl.OffsetData(Y_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(Z_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Z"));
        offsetList.add(new ComparativeTableControl.OffsetData(RADIUS_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Radius"));
        offsetList.add(new ComparativeTableControl.OffsetData(TYPE_ID_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Type"));
        offsetList.add(new ComparativeTableControl.OffsetData(INDEX_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Index"));
        offsetList.add(new ComparativeTableControl.OffsetData(ATTRIBUTE_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Attributes"));
        
        if (GameVersion.MM6 != gameVersion)
        {
            offsetList.add(new ComparativeTableControl.OffsetData(GROUP_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Group"));
        }

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
                SpawnPoint monster = (SpawnPoint)monsterList.get(dataRow);
                return monster.getMonsterData();
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

//  1 - bunch of goblins
//  2 - bunch of mages
//  3 - bunch of archers
//  4 - one goblin
//  5 - one apprentice mage
//  6 - one archer
//  7 - one goblin shaman
//  8 - one journeyman mage
//  9 - one archer
//  10 - one goblin king
//  11 - one mage

    public static class MonsterClassLabelValue
    {
        private int index;
        private int value;
        private String label;
        
        public MonsterClassLabelValue(int index, int value, String label)
        {
            this.index = index;
            this.value = value;
            this.label = label;
        }
        public int getIndex()
        {
            return this.index;
        }
        public String getLabel()
        {
            return this.label;
        }
        public int getValue()
        {
            return this.value;
        }
    }
    
    private static MonsterClassLabelValue monsterClassArray[] = new MonsterClassLabelValue[] {
            new SpawnPoint.MonsterClassLabelValue(0, 1, "group of monster 1"),
            new SpawnPoint.MonsterClassLabelValue(1, 2, "group of monster 2"),
            new SpawnPoint.MonsterClassLabelValue(2, 3, "group of monster 3"),
            new SpawnPoint.MonsterClassLabelValue(3, 4, "small monster 1"),
            new SpawnPoint.MonsterClassLabelValue(4, 5, "small monster 2"),
            new SpawnPoint.MonsterClassLabelValue(5, 6, "small monster 3"),
            new SpawnPoint.MonsterClassLabelValue(6, 7, "medium monster 1"),
            new SpawnPoint.MonsterClassLabelValue(7, 8, "medium monster 2"),
            new SpawnPoint.MonsterClassLabelValue(8, 9, "medium monster 3"),
            new SpawnPoint.MonsterClassLabelValue(9, 10, "big monster 1"),
            new SpawnPoint.MonsterClassLabelValue(10, 11, "big monster 2"),
            new SpawnPoint.MonsterClassLabelValue(11, 12, "big monster 3"),
            };

    public static MonsterClassLabelValue[] getMonsterClassValues()
    {
        return monsterClassArray;
    }

    public static int getMonsterClassIndexForValue(int value)
    {
        for (int index = 0; index < monsterClassArray.length; index++)
        {
            MonsterClassLabelValue monsterClasslabelValue = monsterClassArray[index];
            if (monsterClasslabelValue.getValue() == value)
                return monsterClasslabelValue.getIndex();
        }
        
        return -1;
    }

    public static String getMonsterClassLabelForObject(Object object)
    {
        MonsterClassLabelValue monsterClassLabelValue = (MonsterClassLabelValue)object;
        return monsterClassLabelValue.getLabel();
    }

    public void setMonsterClassForItem(Object object)
    {
        MonsterClassLabelValue monsterClassLabelValue = (MonsterClassLabelValue)object;
        this.setMonsterClass(monsterClassLabelValue.getValue());
    }

}
