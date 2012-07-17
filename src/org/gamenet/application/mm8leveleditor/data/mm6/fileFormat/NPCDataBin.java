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

import java.util.List;

import org.gamenet.util.ByteConversions;

public class NPCDataBin
{
    private static final int UNKNOWN1_OFFSET = 0x00;
    private static final int PICTURE_OFFSET = 0x04;
    private static final int BTB_CHECK_STATE_OFFSET = 0x08;
    private static final int FAME_OFFSET = 0x0C;
    private static final int REPUTATION_OFFSET = 0x10;
    private static final int CURRENT_2D_LOCATION_OFFSET = 0x14;
    private static final int PROFESSION_OFFSET = 0x18;
    private static final int JOIN_OFFSET = 0x1C;
    private static final int NEWS_OFFSET = 0x20;
    private static final int EVENT_A_OFFSET = 0x24;
    private static final int EVENT_B_OFFSET = 0x28;
    private static final int EVENT_C_OFFSET = 0x2C;
    private static final int UNKNOWN2_OFFSET = 0x30;
    private static final int UNKNOWN3_OFFSET = 0x34;
    private static final int UNKNOWN4_OFFSET = 0x34;
    
    private static final int NPC_RECORD_LENGTH = 60;

    private int unknown1 = 0;
    private int picture = 0;
    private int btbCheckState = 0;
    private int fame = 0;
    private int reputation = 0;
    private int current2DLocation = 0;
    private int profession = 0;
    private int join = 0;
    private int news = 0;
    private int eventA = 0;
    private int eventB = 0;
    private int eventC = 0;    
    private int unknown2 = 0;
    private int unknown3 = 0;
    private int unknown4 = 0;
    
    public NPCDataBin(byte data[], int offset)
    {
        unknown1 = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + UNKNOWN1_OFFSET);
        picture = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + PICTURE_OFFSET);
        btbCheckState = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + BTB_CHECK_STATE_OFFSET);
        fame = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + FAME_OFFSET);
        reputation = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + REPUTATION_OFFSET);
        current2DLocation = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + CURRENT_2D_LOCATION_OFFSET);
        profession = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + PROFESSION_OFFSET);
        join = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + JOIN_OFFSET);
        news = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + NEWS_OFFSET);
        eventA = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + EVENT_A_OFFSET);
        eventB = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + EVENT_B_OFFSET);
        eventC = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + EVENT_C_OFFSET);
        unknown2 = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + UNKNOWN2_OFFSET);
        unknown3 = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + UNKNOWN3_OFFSET);
        unknown4 = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + UNKNOWN4_OFFSET);
    }
    
    public void updateData(byte newData[], int offset)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(unknown1, newData, offset + UNKNOWN1_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(picture, newData, offset + PICTURE_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(btbCheckState, newData, offset + BTB_CHECK_STATE_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(fame, newData, offset + FAME_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(reputation, newData, offset + REPUTATION_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(current2DLocation, newData, offset + CURRENT_2D_LOCATION_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(profession, newData, offset + PROFESSION_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(join, newData, offset + JOIN_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(news, newData, offset + NEWS_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(eventA, newData, offset + EVENT_A_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(eventB, newData, offset + EVENT_B_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(eventC, newData, offset + EVENT_C_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(unknown2, newData, offset + UNKNOWN2_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(unknown3, newData, offset + UNKNOWN3_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(unknown4, newData, offset + UNKNOWN4_OFFSET);
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset, int count)
    {
        offset += count * NPC_RECORD_LENGTH;
        
        return (offset == expectedNewOffset);
    }
    
    public static int populateObjects(byte[] data, int offset, List npcDataList, int npcDataCount)
    {
        for (int npcDataIndex = 0; npcDataIndex < npcDataCount; ++npcDataIndex)
        {
            NPCDataBin npcData = new NPCDataBin(data, offset);
            npcDataList.add(npcData);
            offset += NPC_RECORD_LENGTH;
        }
        
        return offset;
    }

    public static int updateData(byte[] newData, int offset, List npcDataList)
    {
        for (int npcDataIndex = 0; npcDataIndex < npcDataList.size(); ++npcDataIndex)
        {
            NPCDataBin npcData = (NPCDataBin)npcDataList.get(npcDataIndex);
            npcData.updateData(newData, offset);
            offset += NPC_RECORD_LENGTH;
        }
        
        return offset;
    }

    public int getBtbCheckState()
    {
        return this.btbCheckState;
    }
    public void setBtbCheckState(int btbCheckState)
    {
        this.btbCheckState = btbCheckState;
    }
    public int getCurrent2DLocation()
    {
        return this.current2DLocation;
    }
    public void setCurrent2DLocation(int current2DLocation)
    {
        this.current2DLocation = current2DLocation;
    }
    public int getEventA()
    {
        return this.eventA;
    }
    public void setEventA(int eventA)
    {
        this.eventA = eventA;
    }
    public int getEventB()
    {
        return this.eventB;
    }
    public void setEventB(int eventB)
    {
        this.eventB = eventB;
    }
    public int getEventC()
    {
        return this.eventC;
    }
    public void setEventC(int eventC)
    {
        this.eventC = eventC;
    }
    public int getFame()
    {
        return this.fame;
    }
    public void setFame(int fame)
    {
        this.fame = fame;
    }
    public int getJoin()
    {
        return this.join;
    }
    public void setJoin(int join)
    {
        this.join = join;
    }
    public int getNews()
    {
        return this.news;
    }
    public void setNews(int news)
    {
        this.news = news;
    }
    public int getPicture()
    {
        return this.picture;
    }
    public void setPicture(int picture)
    {
        this.picture = picture;
    }
    public int getProfession()
    {
        return this.profession;
    }
    public void setProfession(int profession)
    {
        this.profession = profession;
    }
    public int getReputation()
    {
        return this.reputation;
    }
    public void setReputation(int reputation)
    {
        this.reputation = reputation;
    }
    public int getUnknown1()
    {
        return this.unknown1;
    }
    public void setUnknown1(int unknown1)
    {
        this.unknown1 = unknown1;
    }
    public int getUnknown2()
    {
        return this.unknown2;
    }
    public void setUnknown2(int unknown2)
    {
        this.unknown2 = unknown2;
    }
    public int getUnknown3()
    {
        return this.unknown3;
    }
    public void setUnknown3(int unknown3)
    {
        this.unknown3 = unknown3;
    }
    public int getUnknown4()
    {
        return this.unknown4;
    }
    public void setUnknown4(int unknown4)
    {
        this.unknown4 = unknown4;
    }
}
