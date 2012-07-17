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

package org.gamenet.application.mm8leveleditor.data.mm6.fileFormat;

import java.util.ArrayList;
import java.util.List;

import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class DPftBin
{
    // Player Frame Table
    private static final int DPFT_BIN_RECORD_SIZE = 10;
    
    // TODO: this is really a player frame set (or a character animation set)
    
	public final static int ATTRIBUTE__NOT_LAST_FRAME = 	0x00000001; 
	public final static int ATTRIBUTE__NEW_FRAME = 		0x00000004; 

	public final static int PLAYER_TYPE__NORMAL = 1;
	public final static int PLAYER_TYPE__PAIN = 2;
	public final static int PLAYER_TYPE__WEAK = 3;
	public final static int PLAYER_TYPE__ASLEEP = 4;
	public final static int PLAYER_TYPE__STARTLED = 5;
	public final static int PLAYER_TYPE__DRUNK = 6;
	public final static int PLAYER_TYPE__INSANE = 7;
	public final static int PLAYER_TYPE__POISONED = 8;
	public final static int PLAYER_TYPE__DISEASED = 9;
	public final static int PLAYER_TYPE__PARALYZED = 10;
	public final static int PLAYER_TYPE__UNCONSCIOUS = 11;
	public final static int PLAYER_TYPE__STONE = 12;
	public final static int PLAYER_TYPE__BLINK = 13;
	public final static int PLAYER_TYPE__FIDGET_EYE = 14;
	public final static int PLAYER_TYPE__FIDGET_MOUTH1 = 15;
	public final static int PLAYER_TYPE__FIDGET_MOUTH2 = 16;
	public final static int PLAYER_TYPE__EYES_UP = 17;
	public final static int PLAYER_TYPE__EYES_LEFT = 18;
	public final static int PLAYER_TYPE__EYES_RIGHT = 19;
	public final static int PLAYER_TYPE__EYES_DOWN = 20;
	public final static int PLAYER_TYPE__TALK = 21;
	public final static int PLAYER_TYPE__TALK_MMMM = 21;
	public final static int PLAYER_TYPE__TALK_AHHH = 22;
	public final static int PLAYER_TYPE__TALK_EH = 23;
	public final static int PLAYER_TYPE__TALK_OHHH = 24;
	public final static int PLAYER_TYPE__NO = 25;
	public final static int PLAYER_TYPE__YES = 27;
	public final static int PLAYER_TYPE__LOOK_LEFT_EYES_LEFT = 29;
	public final static int PLAYER_TYPE__LOOK_RIGHT_EYES_RIGHT = 30;
	public final static int PLAYER_TYPE__LOOK_UP_EYES_UP = 31;
	public final static int PLAYER_TYPE__LOOK_DOWN_EYES_DOWN = 32;
	public final static int PLAYER_TYPE__DUCK = 33;
	public final static int PLAYER_TYPE__HIT_LIGHT = 34;
	public final static int PLAYER_TYPE__HIT_MED = 35;
	public final static int PLAYER_TYPE__HIT_HEAVY = 36;
	public final static int PLAYER_TYPE__SMILE = 37;
	public final static int PLAYER_TYPE__CHEER = 38;
	public final static int PLAYER_TYPE__FROWN = 39;
	public final static int PLAYER_TYPE__CAST = 40;
	public final static int PLAYER_TYPE__DOH1 = 41;
	public final static int PLAYER_TYPE__DOH2 = 42;
	public final static int PLAYER_TYPE__I_DONT_KNOW = 43;
	public final static int PLAYER_TYPE__CRY = 44;
	public final static int PLAYER_TYPE__SMIRK = 45;
	public final static int PLAYER_TYPE__YELL = 46;
	public final static int PLAYER_TYPE__EUREKA = 47;
	public final static int PLAYER_TYPE__ANNOYED = 48;
	public final static int PLAYER_TYPE__SKEPTICAL = 49;
	public final static int PLAYER_TYPE__ANGER = 50;
	public final static int PLAYER_TYPE__STRAINING = 51;
	public final static int PLAYER_TYPE__LAUGH = 52;
	public final static int PLAYER_TYPE__YELL_HEAD_BACK = 53;
	public final static int PLAYER_TYPE__FIDGET_MMM = 54;
	public final static int PLAYER_TYPE__FIDGET_AHH = 55;
	public final static int PLAYER_TYPE__FIDGET_EH = 56;
	public final static int PLAYER_TYPE__FIDGET_OHH = 57;
	public final static int PLAYER_TYPE__WAKEUP = 58;

	private int dPftBinOffset = 0;
    private byte dPftBinData[] = null;

    public DPftBin()
    {
        super();
    }

    public DPftBin(String fileName)
    {
        super();
        this.dPftBinData = new byte[DPFT_BIN_RECORD_SIZE];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.dPftBinOffset = offset;
        this.dPftBinData = new byte[DPFT_BIN_RECORD_SIZE];
        System.arraycopy(dataSrc, offset, this.dPftBinData, 0, this.dPftBinData.length);
        offset += this.dPftBinData.length;
        
        return offset;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
        int count = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        offset += count * DPFT_BIN_RECORD_SIZE;
        
        return (offset == expectedNewOffset);
    }
    
    public static int populateObjects(byte[] data, int offset, List dPftBinList)
    {
        int dPftBinCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int dPftBinIndex = 0; dPftBinIndex < dPftBinCount; ++dPftBinIndex)
        {
            DPftBin dPftBin = new DPftBin();
            dPftBinList.add(dPftBin);
            offset = dPftBin.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List dPftBinList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(dPftBinList.size(), newData, offset);
        offset += 4;

        for (int dPftBinIndex = 0; dPftBinIndex < dPftBinList.size(); ++dPftBinIndex)
        {
            DPftBin dPftBin = (DPftBin)dPftBinList.get(dPftBinIndex);
            System.arraycopy(dPftBin.getDPftBinData(), 0, newData, offset, dPftBin.getDPftBinData().length);
            offset += dPftBin.getDPftBinData().length;
        }
        
        return offset;
    }

    public byte[] getDPftBinData()
    {
        return this.dPftBinData;
    }
    public int getDPftBinOffset()
    {
        return this.dPftBinOffset;
    }

    public static int getRecordSize()
    {
        return DPFT_BIN_RECORD_SIZE;
    }


    // DPftBin things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();

        offsetList.add(new ComparativeTableControl.OffsetData(0, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "type"));
        offsetList.add(new ComparativeTableControl.OffsetData(2, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "index"));
        offsetList.add(new ComparativeTableControl.OffsetData(4, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "frame display time")); // 1/32 of a second
        offsetList.add(new ComparativeTableControl.OffsetData(6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "total animation time"));
        offsetList.add(new ComparativeTableControl.OffsetData(8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "attributes"));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List dPftBinList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return dPftBinList.size();
            }

            public byte[] getData(int dataRow)
            {
                DPftBin dPftBin = (DPftBin)dPftBinList.get(dataRow);
                return dPftBin.getDPftBinData();
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
