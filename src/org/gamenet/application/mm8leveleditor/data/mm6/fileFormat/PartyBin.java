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

import org.gamenet.application.mm8leveleditor.data.ByteData;
import org.gamenet.application.mm8leveleditor.data.mm6.ActiveSpell;
import org.gamenet.application.mm8leveleditor.data.mm6.CharacterData;
import org.gamenet.application.mm8leveleditor.data.mm6.ContainedItem;
import org.gamenet.application.mm8leveleditor.data.mm6.Follower;
import org.gamenet.application.mm8leveleditor.data.mm6.ShopContents;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;
import org.gamenet.util.UnsupportedFileFormatException;

public class PartyBin
{
    // flush system in hive? 00-flush-hive.mm6
    // hive defeated
    // varn radiation damage
    // quest followers
    // fame --  calculated as total experience of all four characters / 1000
    // last time rested stored somewhere
    // weak after hasted datetime?
    // how to determine when a bounty is fulfilled for a month.
    // Determining when a bounty goes into effect as well
    // rest of bounty detail bits (other halls)  // janice npcdata 291, global evt 399, npctext 368/369
    
    //// unknowns starting at 0 -- 40 bytes
    
    // coordinates of party
    private static final int NUMBER_OF_CHARACTERS = 4;
    private static int X_OFFSET = 40; // 4 bytes
    private static int Y_OFFSET = 44; // 4 bytes
    private static int Z_OFFSET = 48; // 4 bytes
    private static int FACING_OFFSET = 52; // 2? 4? bytes, e is 0, n is 512, w is 1024, s is 1536
    private static int TILT_OFFSET = 56; // 2 bytes  - 0 is level, neg is down, pos is up
    // dup of coordinates -- unknown use
    private static int X2_OFFSET = 60; // 4 bytes
    private static int Y2_OFFSET = 64; // 4 bytes
    private static int Z2_OFFSET = 68; // 4 bytes
    private static int FACING2_OFFSET = 72; // 2? 4? bytes
    private static int TILT2_OFFSET = 76; // 2? bytes
    
    //// unknowns starting at 80 -- 72 bytes
    
    // 8-byte values for time
    // 256 ticks in a minute, 60 minutes in an hour, 24 hours in a day,
    // 28 days in a month, 12 months in a year, 0 is year 1165
    private static int GAME_TIME_OFFSET = 152; // 4 bytes, probably 8 bytes
    
    //// unknowns starting at 160 -- 28 bytes
    
    private static int FOOD_OFFSET = 188; // 4 bytes
    
    //// unknowns starting at 192 -- 24 bytes
    
    private static int REPUTATION_OFFSET = 216; // 4 bytes
    
    //// unknowns starting at 220 -- 4 bytes (0 to 1 on bribe + hire scout)
    
    private static int MONEY_ON_HAND_OFFSET = 224; // 4 bytes
    private static int MONEY_IN_BANK_OFFSET = 228; // 4 bytes
    private static int DEATHS_OFFSET = 232; // 4 bytes
    
    private static int UNKNOWN2_OFFSET = 236; // 4 bytes
    //// unknowns starting at 236 -- 4 bytes
    
    private static int PRISON_TERMS_OFFSET = 240; // 4 bytes
    private static int BOUNTIES_COLLECTED_OFFSET = 244; // 4 bytes

    /////// unknowns starting at 248 -- 5 bytes
    
    private static int NUMBER_OF_QUESTS = 512;
    private static int QUEST_BASE_OFFSET = 253; // 8 bits per byte, starting at quest 1 to quest 512
    private static int QUEST10_OFFSET = 263;
    // sulmans letter to potbello, letter to humphrey, goblinwatch combination, chime of harmony, kilborn's shield
    // bit 128 -- 
    // bit 64 -- quest 81
    // bit 32 -- quest 82
    // bit 16 -- 
    // bit 08 -- 
    // bit 04 -- 
    // bit 02 -- 
    // bit 01 -- 
    private static int QUEST11_OFFSET = 264;
    // damsel in distress, slay dragon, entertain nicolai, find nicolai
    private static int QUEST12_OFFSET = 265;
    // third eye, prince of thieves
    private static int QUEST13_OFFSET = 266;
    // repair temple, get chalice, hourglass of time, fountain of magic
    private static int QUEST14_OFFSET = 267;
    // crystal of terrax, devil's outpost, chadwick knight nomination, defeat warlard, fix prices, altar of sun, altar of moon
    private static int QUEST15_OFFSET = 268;
    // end winter, get dragon tower key, reset dragon towers, kill snergle, silver helm outpost, potbello baa candelabra
    // bit 128 -- 
    // bit 64 --
    // bit 32 --
    // bit 16 -- 
    // bit 08 -- 
    // bit 04 -- 
    // bit 02 -- quest 83
    // bit 01 -- 
    private static int QUEST16_OFFSET = 269;
    // dragoon caverns harp, ethric's skull, queen of spiders heart, deface monolith altar, destroy wicked crystal    
    private static int QUEST17_OFFSET = 270;
    // emmanuel, zoltan's artifact, shadow guild sharry, abandoned temple angela, cannibal sherell, destroy werewolf's altar, find pearl of putrescence
    private static int QUEST63_OFFSET = 316;  // quest 512 is illegal and shouldn't be used
    //
    private static int NUMBER_OF_AUTONOTES = 128;
    private static int AUTONOTE_BASE_OFFSET = 317;
    private static int AUTONOTE1_OFFSET = 317; // autonotes 1-8
    private static int AUTONOTE15_OFFSET = 328;
    // red, etc
    private static int AUTONOTE16_OFFSET = 332; // 121 to 128 autonotes
    // bit 128 -- yellow liquid accuracy (note 121)
    // bit 64 --
    // bit 32 --
    // bit 16 -- 
    // bit 08 -- 
    // bit 04 -- 
    // bit 02 --
    // bit 01 -- 

    /////// unknown starting at 333 -- 1 byte
    
    private static int PAGE_VICTORIES = 334;
    private static int SQUIRE_VICTORIES = 335;
    private static int KNIGHT_VICTORIES = 336;
    private static int LORD_VICTORIES = 337;
    
    // 448 goblin bounty in jan solved?
    /// 338 to 346 is probably bounty data
    /////// unknown starting at 338 -- 3 bytes
    private static int NEW_SORPIGAL_BOUNTY_CREATURE = 341;
    private static int KILLED_NEW_SORPIGAL_BOUNTY_CREATURE = 344;
    
    private static int NUMBER_OF_ARTIFACTS_FOUND = 30;
    private static int BASE_ARTIFACTS_FOUND_OFFSET = 347;
    
    private static int UNKNOWN377_OFFSET = 377;
    /////// unknown starting at 377 -- 75 bytes
    
    private static int NUMBER_OF_ACTIVE_SPELLS = 12; // actually, only 11 with a dead area at 596
    private static int BASE_ACTIVE_PARTY_SPELLS_OFFSET = 452; // 16 bytes
    //
    // active party spells
    private static int PROTECTION_FROM_FIRE_OFFSET = 452; // 16 bytes
    private static int PROTECTION_FROM_COLD_OFFSET = 468; // 16 bytes
    private static int PROTECTION_FROM_ELECTRICITY_OFFSET = 484; // 16 bytes
    private static int PROTECTION_FROM_MAGIC_OFFSET = 500; // 16 bytes
    private static int PROTECTION_FROM_POISON_OFFSET = 516; // 16 bytes
    private static int FEATURE_FALL_OFFSET = 532; // 16 bytes
    private static int WATER_WALK_OFFSET = 548; // 16 bytes
    private static int FLY_OFFSET = 564; // 16 bytes
    private static int GUARDIAN_OFFSET = 580; // 16 bytes
    
    /////// 16 bytes
    private static int UNKNOWN1_OFFSET = 596; // 16 bytes
    
    private static int WIZARD_EYE_OFFSET = 612; // 16 bytes
    private static int TORCH_LIGHT_OFFSET = 628; // 16 bytes

    ///// unknown starting at 644 -- 64 bytes left
    
    private static int FIRST_CHARACTER_OFFSET = 708;
    private static int POST_DATA_OFFSET = 23348;
    
    private static int BASE_FOLLOWER_OFFSET = 23348;
    private static int NUMBER_OF_FOLLOWERS = 2;
    private static int FOLLOWER_PICTURE_NUMBER_OFFSET = 4; // 4 bytes
    private static int FOLLOWER_PROFESSION_NUMBER_OFFSET = 24; // 4 bytes
    // 60 bytes total for follower?
    private static int HIRE_FOLLOWER1a_OFFSET = 23348; // 4x1? bytes  104,104,91,1
    private static int FOLLOWER1_PICTURE_NUMBER_OFFSET = 23352; // 4 bytes
    private static int HIRE_FOLLOWER1b_OFFSET = 23356; // 4? bytes  131 or -125
    private static int HIRE_FOLLOWER1c_OFFSET = 23364; // 4? bytes  -44?
    private static int FOLLOWER1_PROFESSION_NUMBER_OFFSET = 23372; // 4 bytes
    private static int HIRE_FOLLOWER1d_OFFSET = 23376; // 4? bytes  1
    private static int HIRE_FOLLOWER1e_OFFSET = 23380; // 4? bytes  1
    private static int HIRE_FOLLOWER1g_OFFSET = 23404; // 4? bytes  14
    
    private static int HIRE_FOLLOWER2a_OFFSET = 23408; // (191 or -65), 93, 91, 1
    private static int FOLLOWER2_PICTURE_NUMBER_OFFSET = 23412; // 4 bytes
    private static int HIRE_FOLLOWER2b_OFFSET = 23416; // 129 or -127
    private static int FOLLOWER2_PROFESSION_NUMBER_OFFSET = 23432;
    private static int HIRE_FOLLOWER2d_OFFSET = 23436; // 1
    private static int HIRE_FOLLOWER2e_OFFSET = 23440; // 1
    private static int HIRE_FOLLOWER2f_OFFSET = 23456; // 1
    private static int HIRE_FOLLOWER2g_OFFSET = 23464; // 18
    
    //// unknown starting at 23468 -- 128 bytes
    private static int CURSOR_CARRIED_ITEM_OFFSET = 23468;
    
    private static int Unknown23496 = 23496; // bit 4 makes party take damage as if on water
    
    // 23500 is some kinda date closely following game time
    
    private static int NUMBER_OF_SHOPS = 47;
    private static int NUMBER_OF_GUILDS = 22; // unclear if this is too small
    // 23596 -- base timestamp of next shop reset (8 bytes x 47 entries)
    private static int BASE_SHOP_RESET_TIMESTAMP_OFFSET = 23596;
    private static int BASE_MAGIC_GUILD_RESET_TIMESTAMP_OFFSET = 23972; // (shop number - 119)  x 8-bytes
    private static int INITIATE_GUILD_OF_ELEMENTS_RESET_TIMESTAMP_OFFSET = 24108;
   
    //// unknown starting at 24148 -- 360 bytes
    
    // "buy" offset (12 rows of 28 bytes)
    private static int BASE_SHOP_BUY_ITEMS_OFFSET = 24508;
    // "special" offset (12 rows of 28 bytes)
    private static int BASE_SHOP_SPECIAL_ITEMS_OFFSET = 40300;
    //
    private static int BASE_MAGIC_GUILD_ITEMS_OFFSET = 56092;  // (shop number - 119) x (12 x 28-bytes)
    private static int INITIATE_GUILD_OF_ELEMENTS_ITEMS_OFFSET = 61804;
    
    ///// unknown starting at 63484 - 1026 bytes
    
    private static int FOLLOWER_NAME_MAXLENGTH = 100;
    private static int BASE_FOLLOWER_NAME_OFFSET = 64510;
    private static int FOLLOWER1_NAME_OFFSET = 64510; // assuming that these run up to 100
    private static int FOLLOWER2_NAME_OFFSET = 64610;
    
    ///// unknown starting at 64710 -- 10 bytes
    
    private static int PARTY_BIN_RECORD_SIZE = 64720;
    
    private int gameVersion = 0;
    
    private byte preCharacterData[] = null;
    private byte postCharacterData[] = null;
    private byte post1CharacterData[] = null;
    private byte post2CharacterData[] = null;
    private byte post3CharacterData[] = null;
    
    private int preCharacterDataOffset = 0;
    private int postCharacterDataOffset = 0;
    private int post1CharacterDataOffset = 0;
    private int post2CharacterDataOffset = 0;
    private int post3CharacterDataOffset = 0;

//    private byte unknown0[]; // 40 bytes
//    
//    public int x;
//    public int y;
//    public int z;
//    public int facing;
//    public int tilt;
//    
//    public int x2;
//    public int y2;
//    public int z2;
//    public int facing2;
//    public int tilt2;
//    
//    private byte unknown80[]; // 72 bytes
//
//    public long gameTime;
//
//    byte[] unknown160; // 28 bytes
//
//    public int food;
//
//    byte[] unknown194; // 4 bytes
//    
//    public int reputation;
//
//    byte[] unknown220; // 4 bytes
//    
//    public int moneyOnHand;
//    public int moneyInBank;
//    
//    public int deaths;
//    
//    byte[] unknown236; // 4 bytes
//
//    public int prisonTerms;
//    public int bountiesCollected;
//
//    private byte unknown248[]; // 5 bytes
//
//    public boolean quests[];
//
//    public boolean autonotes[];
//    
//    private byte unknown333[]; // 1 byte
//
//    public int pageVictories;
//    public int squireVictories;
//    public int knightVictories;
//    public int lordVictories;
//
//    private byte unknown338[]; // 3 bytes
//
//    byte bountyDetails[];
//
//    int newSorpigalBountyCreature;
//
//    private byte unknown342[]; // 2 bytes
//
//    int killedNewSorpigalBountyCreature;
//    
//    private byte unknown345[]; // 108 bytes

    public List activeSpellList;
    
    private static final int UNKNOWN_644_DATA_SIZE = 64;
    private byte unknown644Data[]; // 64 bytes

    public List characterList;
    public List followerList;

    public ContainedItem cursorItem;
    
//    public byte unknown23496[]; // 100 bytes
//
	public long shopTimestampReset[];
	public long guildTimestampReset[];
//    
//    public byte unknown24148[]; // 360 bytes
//    
    public List shopBuyNormalItemsList;
    public List shopBuySpecialItemsList;
    public List shopBuyGuildSpellBookList;
//    
//    public byte unknown63484[]; // 1026 bytes
//
//    public String followerName[];
//    
//    public byte unknown64710[]; // 4 bytes
    
    public PartyBin(int gameVersion)
    {
        super();
        this.gameVersion = gameVersion;
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.preCharacterDataOffset = offset;
        this.preCharacterData = new byte[BASE_ACTIVE_PARTY_SPELLS_OFFSET];
        System.arraycopy(dataSrc, offset, this.preCharacterData, 0, this.preCharacterData.length);
        offset += this.preCharacterData.length;
        
        // Active Party Spells
        activeSpellList = new ArrayList();
        offset = ActiveSpell.populateObjects(dataSrc, offset, activeSpellList, NUMBER_OF_ACTIVE_SPELLS);

        this.unknown644Data = new byte[FIRST_CHARACTER_OFFSET - offset];
        System.arraycopy(dataSrc, offset, this.unknown644Data, 0, this.unknown644Data.length);
        offset += this.unknown644Data.length;
        
        // Characters
        characterList = new ArrayList();
        offset = CharacterData.populateObjects(gameVersion, dataSrc, offset, characterList, NUMBER_OF_CHARACTERS);

        followerList = new ArrayList();
        offset = Follower.populateObjects(dataSrc, offset, followerList, NUMBER_OF_FOLLOWERS);
        
        cursorItem = new ContainedItem(gameVersion);
        offset = cursorItem.initialize(dataSrc, offset);

        this.postCharacterDataOffset = offset;
        this.postCharacterData = new byte[BASE_SHOP_RESET_TIMESTAMP_OFFSET - offset];
        System.arraycopy(dataSrc, offset, this.postCharacterData, 0, this.postCharacterData.length);
        offset += this.postCharacterData.length;
        
        this.shopTimestampReset = new long[NUMBER_OF_SHOPS];
        for (int shopTimestampResetIndex = 0; shopTimestampResetIndex < NUMBER_OF_SHOPS; shopTimestampResetIndex++)
        {
            this.shopTimestampReset[shopTimestampResetIndex] = ByteConversions.getLongInByteArrayAtPosition(dataSrc, offset);
            offset += 8;
        }
        
        this.guildTimestampReset = new long[NUMBER_OF_GUILDS];
        for (int guildTimestampResetIndex = 0; guildTimestampResetIndex < NUMBER_OF_GUILDS; guildTimestampResetIndex++)
        {
            this.guildTimestampReset[guildTimestampResetIndex] = ByteConversions.getLongInByteArrayAtPosition(dataSrc, offset);
            offset += 8;
        }
        
        this.post1CharacterDataOffset = offset;
        this.post1CharacterData = new byte[BASE_SHOP_BUY_ITEMS_OFFSET - offset];
        System.arraycopy(dataSrc, offset, this.post1CharacterData, 0, this.post1CharacterData.length);
        offset += this.post1CharacterData.length;
        
        // Shop normal contents
        shopBuyNormalItemsList = new ArrayList();
        offset = ShopContents.populateObjects(gameVersion, dataSrc, offset, shopBuyNormalItemsList, NUMBER_OF_SHOPS);

        // Shop special contents
        shopBuySpecialItemsList = new ArrayList();
        offset = ShopContents.populateObjects(gameVersion, dataSrc, offset, shopBuySpecialItemsList, NUMBER_OF_SHOPS);

        // Guild spellbook contents
        shopBuyGuildSpellBookList = new ArrayList();
        offset = ShopContents.populateObjects(gameVersion, dataSrc, offset, shopBuyGuildSpellBookList, NUMBER_OF_GUILDS);

        this.post2CharacterDataOffset = offset;
        this.post2CharacterData = new byte[BASE_FOLLOWER_NAME_OFFSET - offset];
        System.arraycopy(dataSrc, offset, this.post2CharacterData, 0, this.post2CharacterData.length);
        offset += this.post2CharacterData.length;
        
        offset = Follower.addNames(dataSrc, offset, followerList);

        this.post3CharacterDataOffset = offset;
        this.post3CharacterData = new byte[dataSrc.length - offset];
        System.arraycopy(dataSrc, offset, this.post3CharacterData, 0, this.post3CharacterData.length);
        offset += this.post3CharacterData.length;
        
        return offset;
    }

    public byte[] updateData(byte oldData[])
    {
        int newDataLength = preCharacterData.length;
        newDataLength += activeSpellList.size() * ActiveSpell.getRecordSize();
        newDataLength += unknown644Data.length;
        newDataLength += characterList.size() * CharacterData.getRecordSize();
        newDataLength += followerList.size() * Follower.getRecordSize();
        newDataLength += ContainedItem.getRecordSize(gameVersion);
        newDataLength += postCharacterData.length;
        newDataLength += shopTimestampReset.length * 8;
        newDataLength += guildTimestampReset.length * 8;
        newDataLength += post1CharacterData.length;
        newDataLength += shopBuyNormalItemsList.size() * ShopContents.getRecordSize(gameVersion);
        newDataLength += shopBuySpecialItemsList.size() * ShopContents.getRecordSize(gameVersion);
        newDataLength += shopBuyGuildSpellBookList.size() * ShopContents.getRecordSize(gameVersion);
        newDataLength += post2CharacterData.length;
        newDataLength += followerList.size() * Follower.getRecordSizeName();
        newDataLength += post3CharacterData.length;
        
        byte newData[] = new byte[newDataLength];
        
        int offset = 0;
        
        System.arraycopy(preCharacterData, 0, newData, offset, preCharacterData.length);
        offset += preCharacterData.length;
        
        // Active Party Spells
        offset = ActiveSpell.updateData(newData, offset, activeSpellList);

        System.arraycopy(unknown644Data, 0, newData, offset, unknown644Data.length);
        offset += unknown644Data.length;
        
        offset = CharacterData.updateData(newData, offset, characterList);
        
        offset = Follower.updateData(newData, offset, followerList);
        
        offset = cursorItem.updateData(newData, offset);
         
        System.arraycopy(postCharacterData, 0, newData, offset, postCharacterData.length);
        offset += postCharacterData.length;
        
        for (int shopTimestampResetIndex = 0; shopTimestampResetIndex < NUMBER_OF_SHOPS; shopTimestampResetIndex++)
        {
            ByteConversions.setLongInByteArrayAtPosition(this.shopTimestampReset[shopTimestampResetIndex], newData, offset);
            offset += 8;
        }
        
        for (int guildTimestampResetIndex = 0; guildTimestampResetIndex < NUMBER_OF_GUILDS; guildTimestampResetIndex++)
        {
            ByteConversions.setLongInByteArrayAtPosition(this.guildTimestampReset[guildTimestampResetIndex], newData, offset);
            offset += 8;
        }
        
        System.arraycopy(post1CharacterData, 0, newData, offset, post1CharacterData.length);
        offset += post1CharacterData.length;
        
        offset = ShopContents.updateData(newData, offset, shopBuyNormalItemsList);
        
        offset = ShopContents.updateData(newData, offset, shopBuySpecialItemsList);
        
        offset = ShopContents.updateData(newData, offset, shopBuyGuildSpellBookList);
        
        System.arraycopy(post2CharacterData, 0, newData, offset, post2CharacterData.length);
        offset += post2CharacterData.length;
        
        offset = Follower.updateDataNames(newData, offset, followerList);
        
        System.arraycopy(post3CharacterData, 0, newData, offset, post3CharacterData.length);
        offset += post3CharacterData.length;
        
        return newData;
    }

    public static boolean checkDataIntegrity(int gameVersion, byte[] data, int offset, int expectedNewOffset)
    {
        if (PARTY_BIN_RECORD_SIZE != (expectedNewOffset - offset))  return false;
        
        offset += BASE_ACTIVE_PARTY_SPELLS_OFFSET;
        
        int activeSpellRecordSize = ActiveSpell.getRecordSize();
        for (int index = 0; index < NUMBER_OF_ACTIVE_SPELLS; ++index)
        {
            if (false == ActiveSpell.checkDataIntegrity(data, offset, offset + activeSpellRecordSize))
            {
                throw new UnsupportedFileFormatException("Not an expected mm6 Party.bin ActiveSpell format for index " + index);
            }
            offset += activeSpellRecordSize;
        }
        
        offset += UNKNOWN_644_DATA_SIZE;

        int characterDataRecordSize = CharacterData.getRecordSize();
        for (int index = 0; index < NUMBER_OF_CHARACTERS; ++index)
        {
            if (false == CharacterData.checkDataIntegrity(data, offset, offset + characterDataRecordSize))
            {
                throw new UnsupportedFileFormatException("Not an expected mm6 Party.bin CharacterData format for index " + index);
            }
            offset += characterDataRecordSize;
        }
        
        int followerRecordSize = Follower.getRecordSize();
        for (int index = 0; index < NUMBER_OF_FOLLOWERS; ++index)
        {
            if (false == Follower.checkDataIntegrity(data, offset, offset + followerRecordSize))
            {
                throw new UnsupportedFileFormatException("Not an expected mm6 Party.bin Follower format for index " + index);
            }
            offset += followerRecordSize;
        }

        offset += ContainedItem.getRecordSize(gameVersion);

        offset += (BASE_SHOP_BUY_ITEMS_OFFSET - (CURSOR_CARRIED_ITEM_OFFSET + ContainedItem.getRecordSize(gameVersion)));

        int shopContentsRecordSize = ShopContents.getRecordSize(gameVersion);
        for (int index = 0; index < NUMBER_OF_SHOPS; ++index)
        {
            if (false == ShopContents.checkDataIntegrity(gameVersion, data, offset, offset + shopContentsRecordSize))
            {
                throw new UnsupportedFileFormatException("Not an expected mm6 Party.bin ShopContents format for normal index " + index);
            }
            offset += shopContentsRecordSize;
        }
        for (int index = 0; index < NUMBER_OF_SHOPS; ++index)
        {
            if (false == ShopContents.checkDataIntegrity(gameVersion, data, offset, offset + shopContentsRecordSize))
            {
                throw new UnsupportedFileFormatException("Not an expected mm6 Party.bin ShopContents format for special index " + index);
            }
            offset += shopContentsRecordSize;
        }
        for (int index = 0; index < NUMBER_OF_GUILDS; ++index)
        {
            if (false == ShopContents.checkDataIntegrity(gameVersion, data, offset, offset + shopContentsRecordSize))
            {
                throw new UnsupportedFileFormatException("Not an expected mm6 Party.bin ShopContents format for guild index " + index);
            }
            offset += shopContentsRecordSize;
        }
        
        offset += (BASE_FOLLOWER_NAME_OFFSET - (BASE_MAGIC_GUILD_ITEMS_OFFSET + (NUMBER_OF_GUILDS * ShopContents.getRecordSize(gameVersion))));

        int followerNameRecordSize = Follower.getRecordSizeName();
        for (int index = 0; index < NUMBER_OF_FOLLOWERS; ++index)
        {
            if (false == Follower.checkDataIntegrityNames(data, offset, offset + followerNameRecordSize))
            {
                throw new UnsupportedFileFormatException("Not an expected mm6 Party.bin Follower name format for index " + index);
            }
            offset += followerNameRecordSize;
        }
        
        offset += (PARTY_BIN_RECORD_SIZE - (BASE_FOLLOWER_NAME_OFFSET + (NUMBER_OF_FOLLOWERS * Follower.getRecordSizeName())));

        return (offset == expectedNewOffset);
    }
    
    public byte[] getPreCharacterData()
    {
        return this.preCharacterData;
    }
    public int getPreCharacterDataOffset()
    {
        return this.preCharacterDataOffset;
    }

    public byte[] getPost1CharacterData()
    {
        return this.post1CharacterData;
    }
    public int getPost1CharacterDataOffset()
    {
        return this.post1CharacterDataOffset;
    }
    
    public byte[] getPostCharacterData()
    {
        return this.postCharacterData;
    }
    public int getPostCharacterDataOffset()
    {
        return this.postCharacterDataOffset;
    }
    
    public byte[] getPost2CharacterData()
    {
        return this.post2CharacterData;
    }
    public int getPost2CharacterDataOffset()
    {
        return this.post2CharacterDataOffset;
    }
    
    public byte[] getPost3CharacterData()
    {
        return this.post3CharacterData;
    }
    public int getPost3CharacterDataOffset()
    {
        return this.post3CharacterDataOffset;
    }
    
    public List getCharacterList()
    {
        return this.characterList;
    }

    public int getX()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), X_OFFSET);
    }
    public void setX(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), X_OFFSET);
    }
    public int getY()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), Y_OFFSET);
    }
    public void setY(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), Y_OFFSET);
    }
    public int getZ()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), Z_OFFSET);
    }
    public void setZ(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), Z_OFFSET);
    }
    public int getFacing()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), FACING_OFFSET);
    }
    public void setFacing(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), FACING_OFFSET);
    }
    public int getTilt()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), TILT_OFFSET);
    }
    public void setTilt(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), TILT_OFFSET);
    }

    public int getX2()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), X2_OFFSET);
    }
    public void setX2(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), X2_OFFSET);
    }
    public int getY2()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), Y2_OFFSET);
    }
    public void setY2(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), Y2_OFFSET);
    }
    public int getZ2()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), Z2_OFFSET);
    }
    public void setZ2(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), Z2_OFFSET);
    }
    public int getFacing2()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), FACING2_OFFSET);
    }
    public void setFacing2(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), FACING2_OFFSET);
    }
    public int getTilt2()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), TILT2_OFFSET);
    }
    public void setTilt2(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), TILT2_OFFSET);
    }

    public long getGameTime()
    {
        return ByteConversions.getLongInByteArrayAtPosition(getPreCharacterData(), GAME_TIME_OFFSET);
    }
    
    public void setGameTime(long value)
    {
        ByteConversions.setLongInByteArrayAtPosition(value, getPreCharacterData(), GAME_TIME_OFFSET);
    }
    
    public int getFood()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), FOOD_OFFSET);
    }
    
    public void setFood(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), FOOD_OFFSET);
    }
    
    public int getReputation()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), REPUTATION_OFFSET);
    }
    
    public void setReputation(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), REPUTATION_OFFSET);
    }
    
    public int getMoneyOnHand()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), MONEY_ON_HAND_OFFSET);
    }
    
    public void setMoneyOnHand(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), MONEY_ON_HAND_OFFSET);
    }
    
    public int getMoneyInBank()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), MONEY_IN_BANK_OFFSET);
    }
    
    public void setMoneyInBank(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), MONEY_IN_BANK_OFFSET);
    }
    
    public int getBountiesCollected()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), BOUNTIES_COLLECTED_OFFSET);
    }
    
    public void setBountiesCollected(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), BOUNTIES_COLLECTED_OFFSET);
    }
    
    public int getDeaths()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), DEATHS_OFFSET);
    }
    public void setDeaths(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), DEATHS_OFFSET);
    }
    
    public int getPrisonTerms()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), PRISON_TERMS_OFFSET);
    }
    public void setPrisonTerms(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), PRISON_TERMS_OFFSET);
    }

    public int getPageVictories()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), PAGE_VICTORIES);
    }
    public void setPageVictories(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), PAGE_VICTORIES);
    }
    public int getSquireVictories()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), SQUIRE_VICTORIES);
    }
    public void setSquireVictories(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), SQUIRE_VICTORIES);
    }
    public int getKnightVictories()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), KNIGHT_VICTORIES);
    }
    public void setKnightVictories(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), KNIGHT_VICTORIES);
    }
    public int getLordVictories()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), LORD_VICTORIES);
    }
    public void setLordVictories(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), LORD_VICTORIES);
    }

    public int getNewSorpigalBountyCreature()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), NEW_SORPIGAL_BOUNTY_CREATURE);
    }
    public void setNewSorpigalBountyCreature(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), NEW_SORPIGAL_BOUNTY_CREATURE);
    }
    public int getKilledNewSorpigalBountyCreature()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getPreCharacterData(), KILLED_NEW_SORPIGAL_BOUNTY_CREATURE);
    }
    public void setKilledNewSorpigalBountyCreature(
            int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getPreCharacterData(), KILLED_NEW_SORPIGAL_BOUNTY_CREATURE);
    }

//    public byte[] getBountyDetails()
//    {
//        return this.bountyDetails;
//    }

    public int getArtifactsFoundCount()
    {
        return NUMBER_OF_ARTIFACTS_FOUND;
    }
    
    public boolean getArtifactsFound(int artifactNumber)
    {
        int offset = BASE_ARTIFACTS_FOUND_OFFSET + (artifactNumber - 400);
        return (0 != ByteConversions.convertByteToInt(getPreCharacterData()[offset]));
    }
    
    public void setArtifactsFound(boolean value, int artifactNumber)
    {
        int offset = BASE_ARTIFACTS_FOUND_OFFSET + (artifactNumber - 400);
        int intValue = value ? 1 : 0;
        getPreCharacterData()[offset] = ByteConversions.convertIntToByte(intValue);
    }
    
    public int getQuestCount()
    {
        return NUMBER_OF_QUESTS;
    }
    
    // 1-based quests, but stored 0-based
    public boolean getQuest(int questNumber)
    {
        int bitIndex = 7 - (questNumber % 8);
        int byteIndex = QUEST_BASE_OFFSET + questNumber / 8;
        byte questByte = getPreCharacterData()[byteIndex];
        
        return ByteConversions.getValueForByteAtBit(questByte, bitIndex);
    }
    // 1-based quests, but stored 0-based
    public void setQuest(boolean value, int questNumber)
    {
        int bitIndex = 7 - (questNumber % 8);
        int byteIndex = QUEST_BASE_OFFSET + questNumber / 8;
        
        byte oldValue = getPreCharacterData()[byteIndex];
        byte newValue = ByteConversions.setValueForByteAtBit(value, oldValue, bitIndex);
        getPreCharacterData()[byteIndex] = newValue;
    }
    
    public int getAutonoteCount()
    {
        return NUMBER_OF_AUTONOTES;
    }
    
    // 0-based autonotes
    public boolean getAutonote(int autonoteNumber)
    {
        int bitIndex = 7 - (autonoteNumber % 8);
        int byteIndex = AUTONOTE_BASE_OFFSET + autonoteNumber / 8;
        byte autonoteByte = getPreCharacterData()[byteIndex];
        
        return ByteConversions.getValueForByteAtBit(autonoteByte, bitIndex);
    }
    // 0-based autonotes, but autonotes.txt wrongly has 1-based
    public void setAutonote(boolean value, int autonoteNumber)
    {
        int bitIndex = 7 - (autonoteNumber % 8);
        int byteIndex = AUTONOTE_BASE_OFFSET + autonoteNumber / 8;
        
        getPreCharacterData()[byteIndex] = ByteConversions.setValueForByteAtBit(value, getPreCharacterData()[byteIndex], bitIndex);
    }
    

    private String activeSpellNameArray[] = new String[] {
            "Protection from Fire",
            "Protection from Cold",
            "Protection from Electricity",
            "Protection from Magic",
            "Protection from Poison",
            "Feather Fall",
            "Water Walk",
            "Fly",
            "Guardian Angel",
            "Unknown/Unused",
            "Wizard Eye",
            "Torch Light"
    };
    
    public String[] getActiveSpellNameArray()
    {
        return activeSpellNameArray;
    }

    public List getActiveSpellList()
    {
        return activeSpellList;
    }
    
    public byte[] getUnknown644Data()
    {
        return this.unknown644Data;
    }

    public List getFollowerList()
    {
        return followerList;
    }

    public String getFollowerName(int follower)
    {
        int followerOffset = BASE_FOLLOWER_NAME_OFFSET * (follower * FOLLOWER_NAME_MAXLENGTH);

        return ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(getPostCharacterData(), followerOffset, FOLLOWER_NAME_MAXLENGTH);
    }
    
    public void setFollowerName(String followerName, int follower)
    {
        int followerOffset = BASE_FOLLOWER_NAME_OFFSET * (follower * FOLLOWER_NAME_MAXLENGTH);

        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(followerName, getPostCharacterData(), followerOffset, FOLLOWER_NAME_MAXLENGTH);
    }
    
    public long getShopTimestampReset(int shopNumber)
    {
        return shopTimestampReset[shopNumber];
    }
    public void setShopTimestampResetList(long value, int shopNumber)
    {
        shopTimestampReset[shopNumber] = value;        
    }
    public long getGuildTimestampReset(int guildNumber)
    {
        return guildTimestampReset[guildNumber];
    }
    public void setGuildTimestampReset(long value, int guildNumber)
    {
        guildTimestampReset[guildNumber] = value;        
    }

    public List getShopBuyNormalItemsList()
    {
        return this.shopBuyNormalItemsList;
    }
    public void setShopBuyNormalItemsList(List shopBuyNormalItemsList)
    {
        this.shopBuyNormalItemsList = shopBuyNormalItemsList;
    }
    public List getShopBuySpecialItemsList()
    {
        return this.shopBuySpecialItemsList;
    }
    public void setShopBuySpecialItemsList(List shopBuySpecialItemsList)
    {
        this.shopBuySpecialItemsList = shopBuySpecialItemsList;
    }
    public List getShopBuyGuildSpellBookList()
    {
        return this.shopBuyGuildSpellBookList;
    }
    public void setShopBuyGuildSpellBookList(List shopBuyGuildSpellBookList)
    {
        this.shopBuyGuildSpellBookList = shopBuyGuildSpellBookList;
    }

    // Unknown things to decode

    public static List getOffsetList(int size)
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(0, size, ComparativeTableControl.REPRESENTATION_INT_DEC));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List unknownList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return unknownList.size();
            }

            public byte[] getData(int dataRow)
            {
                PartyBin unknown = (PartyBin)unknownList.get(dataRow);
                return unknown.getPreCharacterData();
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
    public ContainedItem getCursorItem()
    {
        return this.cursorItem;
    }

    public List getUnknownByteDataList()
    {
		List unknownList = new ArrayList();
        unknownList.add(new ByteData(this.getPreCharacterData(), this.getPreCharacterDataOffset(), 338, 347));
		unknownList.add(new ByteData(this.getPreCharacterData(), this.getPreCharacterDataOffset(), 0, 0 + 40));
		unknownList.add(new ByteData(this.getPreCharacterData(), this.getPreCharacterDataOffset(), 80, 80 + 72));
		unknownList.add(new ByteData(this.getPreCharacterData(), this.getPreCharacterDataOffset(), 160, 160 + 28));
		unknownList.add(new ByteData(this.getPreCharacterData(), this.getPreCharacterDataOffset(), 192, 192 + 24));
		unknownList.add(new ByteData(this.getPreCharacterData(), this.getPreCharacterDataOffset(), 220, 220 + 4));
		unknownList.add(new ByteData(this.getPreCharacterData(), this.getPreCharacterDataOffset(), 236, 236 + 4));
		unknownList.add(new ByteData(this.getPreCharacterData(), this.getPreCharacterDataOffset(), 248, 248 + 5));
		unknownList.add(new ByteData(this.getPreCharacterData(), this.getPreCharacterDataOffset(), 333, 333 + 1));
		unknownList.add(new ByteData(this.getPreCharacterData(), this.getPreCharacterDataOffset(), 377, 377 + 75));
		unknownList.add(new ByteData(this.getUnknown644Data(), 644));
		unknownList.add(new ByteData(this.getPostCharacterData(), this.getPostCharacterDataOffset()));
		unknownList.add(new ByteData(this.getPost1CharacterData(), this.getPost1CharacterDataOffset()));
		unknownList.add(new ByteData(this.getPost2CharacterData(), this.getPost2CharacterDataOffset()));
		unknownList.add(new ByteData(this.getPost3CharacterData(), this.getPost3CharacterDataOffset()));
        return unknownList;
    }
}
