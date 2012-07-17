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

public class ActiveSpell
{
    private static final int ACTIVE_SPELL_RECORD_SIZE = 16;
    
    private static int SPELL_DURATION_OFFSET = 0; // 8 bytes
    private static int SPELL_POWER_OFFSET = 8; // 2 bytes
    
    private static int SPELL_SKILL_RANK_TYPE_NORMAL = 1;
    private static int SPELL_SKILL_RANK_TYPE_EXPERT = 2;
    private static int SPELL_SKILL_RANK_TYPE_MASTER = 3;
    
    private static int SPELL_SKILL_RANK_OFFSET = 10; // 2 bytes

    private static int SPELL_OVERLAY_ID_OFFSET = 12; // 2 bytes
    private static int SPELL_CASTER_OFFSET = 14; // 1 byte
    private static int SPELL_ATTRIBUTES_OFFSET = 15; // 1 byte

    private int activeSpellOffset = 0;
    private byte activeSpellData[] = null;

    public ActiveSpell()
    {
        super();
    }

    public ActiveSpell(String fileName)
    {
        super();
        this.activeSpellData = new byte[ACTIVE_SPELL_RECORD_SIZE];
    }

    public long getEndDateTime()
    {
        return ByteConversions.getLongInByteArrayAtPosition(activeSpellData, SPELL_DURATION_OFFSET);
    }
    
    public void setEndDateTime(long value)
    {
        ByteConversions.setLongInByteArrayAtPosition(value, activeSpellData, SPELL_DURATION_OFFSET);
    }
    
    public short getPower()
    {
        return ByteConversions.getShortInByteArrayAtPosition(activeSpellData, SPELL_POWER_OFFSET);
    }
    
    public void setPower(short value)
    {
        ByteConversions.setShortInByteArrayAtPosition(value, activeSpellData, SPELL_POWER_OFFSET);
    }
    
    public short getRank()
    {
        return ByteConversions.getShortInByteArrayAtPosition(activeSpellData, SPELL_SKILL_RANK_OFFSET);
    }
    
    public void setRank(short value)
    {
        ByteConversions.setShortInByteArrayAtPosition(value, activeSpellData, SPELL_SKILL_RANK_OFFSET);
    }
    
    public int initialize(byte dataSrc[], int offset)
    {
        this.activeSpellOffset = offset;
        this.activeSpellData = new byte[ACTIVE_SPELL_RECORD_SIZE];
        System.arraycopy(dataSrc, offset, this.activeSpellData, 0, this.activeSpellData.length);
        offset += this.activeSpellData.length;
        
        return offset;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
        offset += ACTIVE_SPELL_RECORD_SIZE;
        
        return (offset == expectedNewOffset);
    }
    
    public static int populateObjects(byte[] data, int offset, List activeSpellList, int activeSpellCount)
    {
        for (int activeSpellIndex = 0; activeSpellIndex < activeSpellCount; ++activeSpellIndex)
        {
            ActiveSpell activeSpell = new ActiveSpell();
            activeSpellList.add(activeSpell);
            offset = activeSpell.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List activeSpellList)
    {
        for (int activeSpellIndex = 0; activeSpellIndex < activeSpellList.size(); ++activeSpellIndex)
        {
            ActiveSpell activeSpell = (ActiveSpell)activeSpellList.get(activeSpellIndex);
            System.arraycopy(activeSpell.getActiveSpellData(), 0, newData, offset, activeSpell.getActiveSpellData().length);
            offset += activeSpell.getActiveSpellData().length;
        }
        
        return offset;
    }

    public byte[] getActiveSpellData()
    {
        return this.activeSpellData;
    }
    public int getActiveSpellOffset()
    {
        return this.activeSpellOffset;
    }

    public static int getRecordSize()
    {
        return ACTIVE_SPELL_RECORD_SIZE;
    }

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        
        addOffsets(offsetList, 0, -1);

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List activeSpellList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return activeSpellList.size();
            }

            public byte[] getData(int dataRow)
            {
                ActiveSpell activeSpell = (ActiveSpell)activeSpellList.get(dataRow);
                return activeSpell.getActiveSpellData();
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

    public static void addOffsets(List offsetList, int startingOffset, int index)
    {
        String prefix = "";
        if (index != -1)  prefix = "ActiveSpell#" + String.valueOf(index) + ":";
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + SPELL_DURATION_OFFSET, 8, ComparativeTableControl.REPRESENTATION_TIME, prefix + "Duration"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + SPELL_POWER_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, prefix + "Power"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + SPELL_SKILL_RANK_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, prefix + "Skill"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + SPELL_OVERLAY_ID_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, prefix + "Overlay ID #"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + SPELL_CASTER_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, prefix + "Caster"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + SPELL_ATTRIBUTES_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, prefix + "Attributes"));
    }
}
