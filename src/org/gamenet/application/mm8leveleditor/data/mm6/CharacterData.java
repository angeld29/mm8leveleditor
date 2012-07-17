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

import org.gamenet.application.mm8leveleditor.data.ByteData;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.NumberLabelValueHolder;
import org.gamenet.util.ByteConversions;


public class CharacterData
{
    private static int CHARACTER_LENGTH = 5660;
    
    private static int CHARACTER_PICTURE_OFFSET = 0;
    
    private static int CHARACTER_NAME_OFFSET = 1;
    private static int CHARACTER_NAME_MAXLENGTH = 16;

    private static int CHARACTER_SEX_OFFSET = 17;  // 0 - male, 1 - female
    
    private static int CHARACTER_CLASS_OFFSET = 18;

    private static int MIGHT_OFFSET = 20;
    private static int TEMP_MIGHT_ADJ_OFFSET = 22;

    private static int INTELLECT_OFFSET = 24;
    private static int TEMP_INTELLECT_ADJ_OFFSET = 26;

    private static int PERSONALITY_OFFSET = 28;
    private static int TEMP_PERSONALITY_ADJ_OFFSET = 30;

    private static int ENDURANCE_OFFSET = 32;
    private static int TEMP_ENDURANCE_ADJ_OFFSET = 34;

    private static int SPEED_OFFSET = 36;
    private static int TEMP_SPEED_ADJ_OFFSET = 38;

    private static int ACCURACY_OFFSET = 40;
    private static int TEMP_ACCURACY_ADJ_OFFSET = 42;

    private static int LUCK_OFFSET = 44;
    private static int TEMP_LUCK_ADJ_OFFSET = 46;

    private static int TEMP_ARMOR_CLASS_ADJ_OFFSET = 48;
    
    private static int LEVEL_OFFSET = 50;
    private static int TEMP_LEVEL_ADJ_OFFSET = 52;

    private static int TEMP_AGE_ADJ_OFFSET = 54;

    //// unknowns starting at 58 -- 38 bytes
    
    private static int NORMAL_RANKING_MASK = 0; // no other ranking set
    private static int EXPERT_RANKING_MASK = 64;
    private static int MASTER_RANKING_MASK = 128;
    
    private static int BASE_SKILL_OFFSET = 96;
    private static int NUMBER_OF_SKILLS = 31;
    
    private static int SKILL_STAFF_OFFSET = 96;
    private static int SKILL_SWORD_OFFSET = 97;
    private static int SKILL_DAGGER_OFFSET = 98;
    private static int SKILL_AXE_OFFSET = 99;
    private static int SKILL_SPEAR_OFFSET = 100;
    private static int SKILL_BOW_OFFSET = 101;
    private static int SKILL_MACE_OFFSET = 102;
    private static int SKILL_BLASTER_OFFSET = 103;
    
    private static int SKILL_SHIELD_OFFSET = 104;
    private static int SKILL_LEATHER_OFFSET = 105;
    private static int SKILL_CHAIN_OFFSET = 106;
    private static int SKILL_PLATE_OFFSET = 107;
    
    private static int SKILL_FIRE_MAGIC_OFFSET = 108;
    private static int SKILL_AIR_MAGIC_OFFSET = 109;
    private static int SKILL_WATER_MAGIC_OFFSET = 110;
    private static int SKILL_EARTH_MAGIC_OFFSET = 111;
    private static int SKILL_SPIRIT_MAGIC_OFFSET = 112;
    private static int SKILL_MIND_MAGIC_OFFSET = 113;
    private static int SKILL_BODY_MAGIC_OFFSET = 114;
    private static int SKILL_LIGHT_MAGIC_OFFSET = 115;
    private static int SKILL_DARK_MAGIC_OFFSET = 116;

    private static int SKILL_IDENTIFY_ITEM_OFFSET = 117;
    private static int SKILL_MERCHANT_OFFSET = 118;
    private static int SKILL_REPAIR_ITEM_OFFSET = 119;
    private static int SKILL_BODY_BUILDING_OFFSET = 120;
    private static int SKILL_MEDITATION_OFFSET = 121;
    private static int SKILL_PERCEPTION_OFFSET = 122;
    private static int SKILL_DIPLOMACY_OFFSET = 123;
    private static int SKILL_THIEVERY_OFFSET = 124;
    private static int SKILL_DISARM_TRAP_OFFSET = 125;
    private static int SKILL_LEARNING_OFFSET = 126;

    private static final int NUMBER_OF_AWARDS = 80;
    private static final int AWARD_BASE_OFFSET = 127;

    private static int AWARD1_OFFSET = 127;
    // bit 64 -- return prince quest
    // bit 32 -- kilborn shield
    // bit 16 -- hourglass of time
    // bit  8 -- devils outpost
    // bit  4 -- prince of thieves
    // bit  2 -- fixed stable prices
    // bit  1 -- ended winter
    
    // honoraries and non-honararies
    private static int AWARD2_OFFSET = 128;
    // bit 64 -- honorary crusader
    private static int AWARD3_OFFSET = 129;
    private static int AWARD4_OFFSET = 130;
    
    // other quests
    private static int AWARD5_OFFSET = 131;
    private static int AWARD6_OFFSET = 132;
    private static int AWARD7_OFFSET = 133;
    private static int AWARD8_OFFSET = 134;
    
    // Joined various guilds
    private static int AWARD9_OFFSET = 135;
    private static int AWARD10_OFFSET = 136;
    // bit 00 - light guild
    
    // various numeric values
    private static int AWARD11_OFFSET = 137;
    // bit 128 -- dark guild
    // bit 64 -- collected X bounties
    // bit 32 -- X deaths
    // bit 16 -- X prison terms
    // bit 08 -- X page victories
    // bit 04 -- X squire victories
    // bit 02 -- X knight victories
    // bit 01 -- X lord victories
    
    //// unknowns starting at 138 -- 53 bytes
    
    // 0 - no, 1 - yes
    private static int NUMBER_OF_SPELL_SCHOOLS = 9;
    private static int NUMBER_OF_SPELLS_PER_SCHOOL = 11;
    private static int BASE_KNOWN_SPELL_OFFSET = 191;
    
    private static int TORCH_LIGHT_OFFSET = 191;
    private static int FLAME_ARROW_OFFSET = 192;
    private static int FIRE_SPELL_3_OFFSET = 193;
    private static int FIRE_SPELL_4_OFFSET = 194;
    private static int FIRE_SPELL_5_OFFSET = 195;
    private static int FIRE_SPELL_6_OFFSET = 196;
    private static int FIRE_SPELL_7_OFFSET = 197;
    private static int FIRE_SPELL_8_OFFSET = 198;
    private static int FIRE_SPELL_9_OFFSET = 199;
    private static int FIRE_SPELL_10_OFFSET = 200;
    private static int FIRE_SPELL_11_OFFSET = 201;
    // and so on
    private static int LAST_SPELL_OFFSET = 289;

    //// unknowns starting at 290 -- 34 bytes
    
    private static int BASE_ITEM_OFFSET = 324;
    // 138 available item lines, 126 possible in inventory, 16 on-hand
    private static final int NUMBER_OF_ITEMS = 138;

    // private static final int CONTENT_LOCATION_OFFSET = NUMBER_OF_ITEMS * ContainedItem.getRecordSize(gameVersion);
    private static final int CONTENT_LOCATION_WIDTH = 14; // short records
    private static final int CONTENT_LOCATION_HEIGHT = 9;
    
    private static int FIRST_ITEM_PLACEMENT_OFFSET = 4188; // 4 bytes
    // 504 bytes - 14 x 9 -  126 items
    
    private static int FIRE_RESISTANCE_OFFSET = 4692;
    private static int TEMP_FIRE_RESISTANCE_ADJ_OFFSET = 4694;

    private static int COLD_RESISTANCE_OFFSET = 4696;
    private static int TEMP_COLD_RESISTANCE_ADJ_OFFSET = 4698;

    private static int ELECTRICAL_RESISTANCE_OFFSET = 4700;
    private static int TEMP_ELECTRICAL_RESISTANCE_ADJ_OFFSET = 4702;

    private static int POISON_RESISTANCE_OFFSET = 4704;
    private static int TEMP_POISON_RESISTANCE_ADJ_OFFSET = 4706;

    private static int MAGIC_RESISTANCE_OFFSET = 4708;
    private static int TEMP_MAGIC_RESISTANCE_ADJ_OFFSET = 4710;
    
    // active individual spells
    private static int NUMBER_OF_ACTIVE_SPELLS = 12;
    private static int BASE_ACTIVE_SPELLS_OFFSET = 4712; // 16 bytes

    private static int SPELL_BLESS_OFFSET = 4712; // 16 bytes
    private static int SPELL_HEROISM_OFFSET = 4728; // 16 bytes
    private static int SPELL_HASTE_OFFSET = 4744; // 16 bytes
    private static int SPELL_SHIELD_OFFSET = 4760; // 16 bytes
    private static int SPELL_STONESKIN_OFFSET = 4776; // 16 bytes
    private static int SPELL_LUCKYDAY_OFFSET = 4792; // 16 bytes
    private static int SPELL_MEDITATION_INTELLECT_OFFSET = 4808; // 16 bytes
    private static int SPELL_MEDITATION_PERSONALITY_OFFSET = 4824; // 16 bytes
    private static int SPELL_PRECISION_OFFSET = 4840; // 16 bytes
    private static int SPELL_SPEED_OFFSET = 4856; // 16 bytes
    private static int SPELL_POWER_MIGHT_OFFSET = 4872; // 16 bytes
    private static int SPELL_POWER_ENDURANCE_OFFSET = 4888; // 16 bytes
    
    private static int BASE_CHARACTER_DATA3_OFFSET = 4904;
    //// unknowns starting at 4904 -- 84 bytes
    
    private static int TICKS_BEFORE_READY = 4988; // 2 or 4 bytes?
    
    private static int NUMBER_OF_CONDITIONS = 17;
    private static int BASE_CONDITION_OFFSET = 4992;
    // 8-byte values
    private static int CURSED_DATETIME_OFFSET = 4992;
    private static int WEAK_DATETIME_OFFSET = 5000;
    private static int ASLEEP_DATETIME_OFFSET = 5008;
    private static int AFRAID_DATETIME_OFFSET = 5016;
    private static int DRUNK_DATETIME_OFFSET = 5024;
    private static int INSANE_DATETIME_OFFSET = 5032;
    private static int POISON1_DATETIME_OFFSET = 5040;
    private static int DISEASED1_DATETIME_OFFSET = 5048;
    private static int POISON2_DATETIME_OFFSET = 5056;
    private static int DISEASED2_DATETIME_OFFSET = 5064;
    private static int POISON3_DATETIME_OFFSET = 5072;
    private static int DISEASED3_DATETIME_OFFSET = 5080;
    private static int PARALYZED_DATETIME_OFFSET = 5088;
    private static int UNCONSCIOUS_DATETIME_OFFSET = 5096;
    private static int DEAD_DATETIME_OFFSET = 5104;
    private static int STONED_DATETIME_OFFSET = 5112;
    private static int ERADICATED_DATETIME_OFFSET = 5120;

    //// unknowns starting at 5128 -- 8 bytes
    
    // current values
    private static int SKILL_POINTS_OFFSET = 5136;
    private static int HIT_POINTS_OFFSET = 5140;
    private static int SPELL_POINTS_OFFSET = 5144;
    private static int YEAR_OF_BIRTH_OFFSET = 5148;
    private static int EXPERIENCE_POINTS_OFFSET = 5152;

    //// unknowns starting at 5156 -- 267 bytes
    
    private static int QUICK_SPELL_OFFSET = 5423; // 4? bytes
    
    //// unknowns starting at 5427 -- 89 bytes

    private static int NUMBER_OF_LLOYDS_BEACON_HOLDERS = 5;
    private static int BASE_LLOYDS_BEACON_SPELL_OFFSET = 5516;
    private static int LLOYDS_BEACON_SPELL_LENGTH = 28;
    //
    private static int LLOYDS_BEACON_SPELL_END_TIMESTAMP_OFFSET = 0;
    private static int LLOYDS_BEACON_SPELL_X_OFFSET = 8;
    private static int LLOYDS_BEACON_SPELL_Y_OFFSET = 12;
    private static int LLOYDS_BEACON_SPELL_Z_OFFSET = 16;
    private static int LLOYDS_BEACON_SPELL_FACING_OFFSET = 20;
    private static int LLOYDS_BEACON_SPELL_TILT_OFFSET = 22;
    private static int LLOYDS_BEACON_SPELL_UNK1_OFFSET = 24;
    private static int LLOYDS_BEACON_SPELL_DESTINATION_OFFSET = 26;
    
    private static int BASE_CHARACTER_DATA4_OFFSET = 5656;
    //// unknowns starting at 5656 -- 4 bytes
    
    private int gameVersion = 0;
    
    private int characterDataOffset = 0;
    private byte characterDataData[] = null;

    private ItemContainer itemContainer = null;

    private short fireResistance;
    private short fireResistanceAdjustment;
    private short coldResistance;
    private short coldResistanceAdjustment;
    private short electricalResistance;
    private short electricalResistanceAdjustment;
    private short poisonResistance;
    private short poisonResistanceAdjustment;
    private short magicResistance;
    private short magicResistanceAdjustment;

    public List activeSpellList;

    private int characterData3Offset = 0;
    private byte characterData3Data[] = null;

    public List lloydsBeaconList;

    private int characterData4Offset = 0;
    private byte characterData4Data[] = null;

    public CharacterData(int gameVersion)
    {
        super();
        this.gameVersion = gameVersion;
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.characterDataOffset = offset;
        this.characterDataData = new byte[BASE_ITEM_OFFSET];
        System.arraycopy(dataSrc, offset, this.characterDataData, 0, this.characterDataData.length);
        offset += this.characterDataData.length;
        
        this.itemContainer = new ItemContainer(gameVersion);
        offset = itemContainer.initializeItems(NUMBER_OF_ITEMS, dataSrc, offset);
        offset = itemContainer.initializeMap(dataSrc, offset, CONTENT_LOCATION_WIDTH, CONTENT_LOCATION_HEIGHT, 4);

        fireResistance = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        fireResistanceAdjustment = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        coldResistance = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        coldResistanceAdjustment = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        electricalResistance = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        electricalResistanceAdjustment = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        poisonResistance = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        poisonResistanceAdjustment = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        magicResistance = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        magicResistanceAdjustment = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;

        // Active Individual Spells
        activeSpellList = new ArrayList();
        offset = ActiveSpell.populateObjects(dataSrc, offset, activeSpellList, NUMBER_OF_ACTIVE_SPELLS);

        this.characterData3Offset = offset;
        this.characterData3Data = new byte[BASE_LLOYDS_BEACON_SPELL_OFFSET - BASE_CHARACTER_DATA3_OFFSET];
        System.arraycopy(dataSrc, offset, this.characterData3Data, 0, this.characterData3Data.length);
        offset += this.characterData3Data.length;
        
        // Lloyd's Beacon
        lloydsBeaconList = new ArrayList();
        offset = LloydsBeacon.populateObjects(dataSrc, offset, lloydsBeaconList, NUMBER_OF_LLOYDS_BEACON_HOLDERS);

        this.characterData4Offset = offset;
        this.characterData4Data = new byte[CHARACTER_LENGTH - BASE_CHARACTER_DATA4_OFFSET];
        System.arraycopy(dataSrc, offset, this.characterData4Data, 0, this.characterData4Data.length);
        offset += this.characterData4Data.length;
        
        return offset;
    }

    public int updateData(byte[] newData, int offset)
    {
        System.arraycopy(this.characterDataData, 0, newData, offset, this.characterDataData.length);
        offset += this.characterDataData.length;
       
        offset = this.itemContainer.updateDataItems(newData, offset);
        offset = this.itemContainer.updateDataMap(newData, offset);
        
        ByteConversions.setShortInByteArrayAtPosition(fireResistance, newData, offset);
        offset += 2;
        ByteConversions.setShortInByteArrayAtPosition(fireResistanceAdjustment, newData, offset);
        offset += 2;
        ByteConversions.setShortInByteArrayAtPosition(coldResistance, newData, offset);
        offset += 2;
        ByteConversions.setShortInByteArrayAtPosition(coldResistanceAdjustment, newData, offset);
        offset += 2;
        ByteConversions.setShortInByteArrayAtPosition(electricalResistance, newData, offset);
        offset += 2;
        ByteConversions.setShortInByteArrayAtPosition(electricalResistanceAdjustment, newData, offset);
        offset += 2;
        ByteConversions.setShortInByteArrayAtPosition(poisonResistance, newData, offset);
        offset += 2;
        ByteConversions.setShortInByteArrayAtPosition(poisonResistanceAdjustment, newData, offset);
        offset += 2;
        ByteConversions.setShortInByteArrayAtPosition(magicResistance, newData, offset);
        offset += 2;
        ByteConversions.setShortInByteArrayAtPosition(magicResistanceAdjustment, newData, offset);
        offset += 2;

        // Active Individual Spells
        offset = ActiveSpell.updateData(newData, offset, activeSpellList);

        System.arraycopy(this.characterData3Data, 0, newData, offset, this.characterData3Data.length);
        offset += this.characterData3Data.length;

        // Lloyd's Beacon
        offset = LloydsBeacon.updateData(newData, offset, lloydsBeaconList);

        System.arraycopy(this.characterData4Data, 0, newData, offset, this.characterData4Data.length);
        offset += this.characterData4Data.length;

        return offset;
    }

    public int getPictureNumber()
    {
        return ByteConversions.convertByteToInt(getCharacterDataData()[CHARACTER_PICTURE_OFFSET]);
    }
    public void setPictureNumber(int value)
    {
        getCharacterDataData()[CHARACTER_PICTURE_OFFSET] = ByteConversions.convertIntToByte(value);
    }
    
    public String getCharacterName()
    {
        return ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(getCharacterDataData(), CHARACTER_NAME_OFFSET, CHARACTER_NAME_MAXLENGTH);
    }
    public void setCharacterName(String creatureName)
    {
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(creatureName, getCharacterDataData(), CHARACTER_NAME_OFFSET, CHARACTER_NAME_MAXLENGTH);
    }
    public static int getCharacterNameMaxlength()
    {
        return CHARACTER_NAME_MAXLENGTH;
    }

    public NumberLabelValueHolder[] getSexOptions()
    {
        return new NumberLabelValueHolder[] {
            	new NumberLabelValueHolder(0, "Male"),
            	new NumberLabelValueHolder(1, "Female")
        };
    }
    
    public int getSex()
    {
        return ByteConversions.convertByteToInt(getCharacterDataData()[CHARACTER_SEX_OFFSET]);
    }
    public void setSex(int value)
    {
        getCharacterDataData()[CHARACTER_SEX_OFFSET] = ByteConversions.convertIntToByte(value);
    }
    
    public int getCharacterClass()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), CHARACTER_CLASS_OFFSET);
    }
    public void setCharacterClass(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), CHARACTER_CLASS_OFFSET);
    }

    public int getMight()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), MIGHT_OFFSET);
    }
    public void setMight(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), MIGHT_OFFSET);
    }

    public int getMightAdjustment()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), TEMP_MIGHT_ADJ_OFFSET);
    }
    public void setMightAdjustment(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), TEMP_MIGHT_ADJ_OFFSET);
    }

    public int getIntellect()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), INTELLECT_OFFSET);
    }
    public void setIntellect(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), INTELLECT_OFFSET);
    }

    public int getIntellectAdjustment()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), TEMP_INTELLECT_ADJ_OFFSET);
    }
    public void setIntellectAdjustment(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), TEMP_INTELLECT_ADJ_OFFSET);
    }

    public int getPersonality()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), PERSONALITY_OFFSET);
    }
    public void setPersonality(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), PERSONALITY_OFFSET);
    }

    public int getPersonalityAdjustment()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), TEMP_PERSONALITY_ADJ_OFFSET);
    }
    public void setPersonalityAdjustment(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), TEMP_PERSONALITY_ADJ_OFFSET);
    }

    public int getAccuracy()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), ACCURACY_OFFSET);
    }
    public void setAccuracy(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), ACCURACY_OFFSET);
    }

    public int getAccuracyAdjustment()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), TEMP_ACCURACY_ADJ_OFFSET);
    }
    public void setAccuracyAdjustment(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), TEMP_ACCURACY_ADJ_OFFSET);
    }

    public int getEndurance()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), ENDURANCE_OFFSET);
    }
    public void setEndurance(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), ENDURANCE_OFFSET);
    }

    public int getEnduranceAdjustment()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), TEMP_ENDURANCE_ADJ_OFFSET);
    }
    public void setEnduranceAdjustment(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), TEMP_ENDURANCE_ADJ_OFFSET);
    }

    public int getSpeed()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), SPEED_OFFSET);
    }
    public void setSpeed(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), SPEED_OFFSET);
    }

    public int getSpeedAdjustment()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), TEMP_SPEED_ADJ_OFFSET);
    }
    public void setSpeedAdjustment(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), TEMP_SPEED_ADJ_OFFSET);
    }

    public int getLuck()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), LUCK_OFFSET);
    }
    public void setLuck(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), LUCK_OFFSET);
    }

    public int getLuckAdjustment()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), TEMP_LUCK_ADJ_OFFSET);
    }
    public void setLuckAdjustment(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), TEMP_LUCK_ADJ_OFFSET);
    }

    public int getArmorClassAdjustment()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), TEMP_ARMOR_CLASS_ADJ_OFFSET);
    }
    public void setArmorClassAdjustment(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), TEMP_ARMOR_CLASS_ADJ_OFFSET);
    }

    public int getLevel()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), LEVEL_OFFSET);
    }
    public void setLevel(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), LEVEL_OFFSET);
    }

    public int getLevelAdjustment()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), TEMP_LEVEL_ADJ_OFFSET);
    }
    public void setLevelAdjustment(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), TEMP_LEVEL_ADJ_OFFSET);
    }

    public int getAgeAdjustment()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getCharacterDataData(), TEMP_AGE_ADJ_OFFSET);
    }
    public void setAgeAdjustment(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getCharacterDataData(), TEMP_AGE_ADJ_OFFSET);
    }

    private int getSkillPointsAtOffset(int skillOffset)
    {
        int rankingMask = MASTER_RANKING_MASK | EXPERT_RANKING_MASK | NORMAL_RANKING_MASK;
        int skillLevelMask = ~rankingMask;
        return skillLevelMask & ByteConversions.convertByteToInt(getCharacterDataData()[skillOffset]);
    }
    private void setSkillPointsAtOffset(int value, int skillOffset)
    {
        int rankingMask = MASTER_RANKING_MASK | EXPERT_RANKING_MASK | NORMAL_RANKING_MASK;
        int skillLevelMask = ~rankingMask;
        int oldValue = ByteConversions.convertByteToInt(getCharacterDataData()[skillOffset]);
        int newValue = ByteConversions.convertIntToByte(value);
        int oldRanking = rankingMask & oldValue;
        int oldSkillLevel = skillLevelMask & oldValue;
        getCharacterDataData()[skillOffset] = ByteConversions.convertIntToByte((rankingMask & oldValue) | (skillLevelMask & newValue));
    }

    private int getSkillRankingAtOffset(int skillOffset)
    {
        int rankingMask = MASTER_RANKING_MASK | EXPERT_RANKING_MASK | NORMAL_RANKING_MASK;
        int oldValue = ByteConversions.convertByteToInt(getCharacterDataData()[skillOffset]);
        return rankingMask & oldValue;
    }
    private void setSkillRankingAtOffset(int value, int skillOffset)
    {
        int rankingMask = MASTER_RANKING_MASK | EXPERT_RANKING_MASK | NORMAL_RANKING_MASK;
        int skillLevelMask = ~rankingMask;
        int oldValue = ByteConversions.convertByteToInt(getCharacterDataData()[skillOffset]);
        getCharacterDataData()[skillOffset] = ByteConversions.convertIntToByte((oldValue & skillLevelMask) | (rankingMask & value));
    }
    
    public NumberLabelValueHolder[] getSkillRankingOptions()
    {
        return new NumberLabelValueHolder[] {
            	new NumberLabelValueHolder(NORMAL_RANKING_MASK, "Normal"),
            	new NumberLabelValueHolder(EXPERT_RANKING_MASK, "Expert"),
            	new NumberLabelValueHolder(MASTER_RANKING_MASK, "Master")
        };
    }
    
    public int getSkillCount()
    {
        return NUMBER_OF_SKILLS;
    }
    private String skillNameArray[] = new String[] {
            "Staff",
            "Sword",
            "Dagger",
            "Axe",
            "Spear",
            "Bow",
            "Mace",
            "Blaster",
            "Shield",
            "Leather",
            "Chain",
            "Plate",
            "Fire Magic",
            "Air Magic",
            "Water Magic",
            "Earth Magic",
            "Spirit Magic",
            "Mind Magic",
            "Body Magic",
            "Light Magic",
            "Dark Magic",
            "Identify Item",
            "Merchant",
            "Repair Item",
            "Body Building",
            "Meditation",
            "Perception",
            "Diplomacy",
            "Thievery",
            "Disarm Trap",
            "Learning"
    };
    public String getSkillName(int skillNumber)
    {
        return skillNameArray[skillNumber];
    }
    public int getSkillPoints(int skillNumber)
    {
        return getSkillPointsAtOffset(BASE_SKILL_OFFSET + skillNumber);
    }
    public void setSkillPoints(int value, int skillNumber)
    {
        setSkillPointsAtOffset(value, BASE_SKILL_OFFSET + skillNumber);
    }
    public int getSkillRanking(int skillNumber)
    {
        return getSkillRankingAtOffset(BASE_SKILL_OFFSET + skillNumber);
    }
    public void setSkillRanking(int value, int skillNumber)
    {
        setSkillRankingAtOffset(value, BASE_SKILL_OFFSET + skillNumber);
    }

    public int getAwardCount()
    {
        return NUMBER_OF_AWARDS;
    }
    
    // 0-based awards
    public boolean getAward(int awardNumber)
    {
        int bitIndex = 7 - (awardNumber % 8);
        int byteIndex = AWARD_BASE_OFFSET + awardNumber / 8;
        byte awardByte = getCharacterDataData()[byteIndex];
        
        return ByteConversions.getValueForByteAtBit(awardByte, bitIndex);
    }
    // 0-based awards, but awards.txt wrongly has 1-based
    public void setAward(boolean value, int awardNumber)
    {
        int bitIndex = 7 - (awardNumber % 8);
        int byteIndex = AWARD_BASE_OFFSET + awardNumber / 8;
        
        getCharacterDataData()[byteIndex] = ByteConversions.setValueForByteAtBit(value, getCharacterDataData()[byteIndex], bitIndex);
    }

    private String fireSchoolSpellNameArray[] = new String[] {
            "Torch Light",
            "Flame Arrow",
            "Protection from Fire",
            "Fire Bolt",
            "Haste",
            "Fireball",
            "Ring of Fire",
            "Fire Blast",
            "Meteor Shower",
            "Inferno",
            "Incinerate"
    };
                                                           
    private String airSchoolSpellNameArray[] = new String[] {
            "Wizard Eye",
            "Static Charge",
            "Protection from Electricity",
            "Sparks",
            "Feather Fall",
            "Shield",
            "Lightning Bolt",
            "Jump",
            "Implosion",
            "Fly",
            "Starburst"
    };
                                                           
    private String waterSchoolSpellNameArray[] = new String[] {
            "Awaken",
            "Cold Beam",
            "Protection from Cold",
            "Poison Spray",
            "Water Walk",
            "Ice Bolt",
            "Enchant Item",
            "Acid Burst",
            "Town Portal",
            "Ice Blast",
            "Lloyd's Beacon"
    };
                                                           
    private String earthSchoolSpellNameArray[] = new String[] {
            "Stun",
            "Magic Arrow",
            "Protection from Magic",
            "Deadly Swarm",
            "Stone Skin",
            "Blades",
            "Stone to Flesh",
            "Rock Blast",
            "Turn to Stone",
            "Death Blossom",
            "Mass Distortion"
    };
                                                           
    private String spiritSchoolSpellNameArray[] = new String[] {
            "Spirit Arrow",
            "Bless",
            "Healing Touch",
            "Lucky Day",
            "Remove Curse",
            "Guardian Angel",
            "Heroism",
            "Turn Undead",
            "Raise Dead",
            "Shared Life",
            "Resurrection"
    };
                                                           
    private String mindSchoolSpellNameArray[] = new String[] {
            "Meditation",
            "Remove Fear",
            "Mind Blast",
            "Precision",
            "Cure Paralysis",
            "Charm",
            "Mass Fear",
            "Feeblemind",
            "Cure Insanity",
            "Psychic Shock",
            "Telekinesis"
    };
                                                           
    private String bodySchoolSpellNameArray[] = new String[] {
            "Cure Weakness",
            "First Aid",
			"Protection from Poison",
			"Harm",
			"Cure Wounds",
			"Cure Poison",
			"Speed",
			"Cure Disease",
			"Power",
			"Flying Fist",
			"Power Cure"
    };
                                                           
    private String lightSchoolSpellNameArray[] = new String[] {
            "Create Food",
            "Golden Touch",
			"Dispel Magic",
			"Slow",
			"Destory Undead",
			"Day of the Gods",
			"Prismatic Light",
			"Hour of Power",
			"Paralyze",
			"Sunray",
			"Divine Intervention"
    };
                                                           
    private String darkSchoolSpellNameArray[] = new String[] {
            "Reanimate",
            "Toxic Cloud",
			"Mass Curse",
			"Shrapmetal",
			"Shrinking Ray",
			"Day of Protection",
			"Finger of Death",
			"Moon Ray",
			"Dragon Breath",
			"Armageddon",
			"Dark Containment"
    };
                                                           
    private String spellNamesBySchoolArray[][] = new String[][] {
            fireSchoolSpellNameArray,
            airSchoolSpellNameArray,
            waterSchoolSpellNameArray,
            earthSchoolSpellNameArray,
            spiritSchoolSpellNameArray,
            mindSchoolSpellNameArray,
            bodySchoolSpellNameArray,
            lightSchoolSpellNameArray,
            darkSchoolSpellNameArray
    };
    
    private String spellSchoolNameArray[] = new String[] {
            "Fire",
            "Air",
            "Water",
            "Earth",
            "Spirit",
            "Mind",
            "Body",
            "Light",
            "Dark"
    };
    
    public String getSpellNameBySchool(int schoolNumber, int spellNumber)
    {
        return spellNamesBySchoolArray[schoolNumber][spellNumber];
    }

    public String[] getSpellSchoolNameArray()
    {
        return spellSchoolNameArray;
    }

    public int getSpellSchoolCount()
    {
        return NUMBER_OF_SPELL_SCHOOLS;
    }
    
    public int getSpellsPerSchoolCount()
    {
        return NUMBER_OF_SPELLS_PER_SCHOOL;
    }
    
    public boolean getKnowsSpell(int schoolOfMagic, int spellNumber)
    {
        int offset = BASE_KNOWN_SPELL_OFFSET + ((schoolOfMagic * NUMBER_OF_SPELLS_PER_SCHOOL) + spellNumber);
        return (0 != ByteConversions.convertByteToInt(getCharacterDataData()[offset]));
    }
    
    public void setKnowsSpell(boolean value, int schoolOfMagic, int spellNumber)
    {
        int offset = BASE_KNOWN_SPELL_OFFSET + ((schoolOfMagic * NUMBER_OF_SPELLS_PER_SCHOOL) + spellNumber);
        int intValue = value ? 1 : 0;
        getCharacterDataData()[offset] = ByteConversions.convertIntToByte(intValue);
    }
    
    public ItemContainer getItemContainer()
    {
        return this.itemContainer;
    }

    public short getFireResistance()
    {
        return this.fireResistance;
    }
    public void setFireResistance(short fireResistance)
    {
        this.fireResistance = fireResistance;
    }
    public short getFireResistanceAdjustment()
    {
        return this.fireResistanceAdjustment;
    }
    public void setFireResistanceAdjustment(short fireResistanceAdjustment)
    {
        this.fireResistanceAdjustment = fireResistanceAdjustment;
    }

    public short getColdResistance()
    {
        return this.coldResistance;
    }
    public void setColdResistance(short coldResistance)
    {
        this.coldResistance = coldResistance;
    }
    public short getColdResistanceAdjustment()
    {
        return this.coldResistanceAdjustment;
    }
    public void setColdResistanceAdjustment(short coldResistanceAdjustment)
    {
        this.coldResistanceAdjustment = coldResistanceAdjustment;
    }
    
    public short getElectricalResistance()
    {
        return this.electricalResistance;
    }
    public void setElectricalResistance(short electricalResistance)
    {
        this.electricalResistance = electricalResistance;
    }
    public short getElectricalResistanceAdjustment()
    {
        return this.electricalResistanceAdjustment;
    }
    public void setElectricalResistanceAdjustment(
            short electricalResistanceAdjustment)
    {
        this.electricalResistanceAdjustment = electricalResistanceAdjustment;
    }
    
    public short getPoisonResistance()
    {
        return this.poisonResistance;
    }
    public void setPoisonResistance(short poisonResistance)
    {
        this.poisonResistance = poisonResistance;
    }
    public short getPoisonResistanceAdjustment()
    {
        return this.poisonResistanceAdjustment;
    }
    public void setPoisonResistanceAdjustment(short poisonResistanceAdjustment)
    {
        this.poisonResistanceAdjustment = poisonResistanceAdjustment;
    }

    public short getMagicResistance()
    {
        return this.magicResistance;
    }
    public void setMagicResistance(short magicResistance)
    {
        this.magicResistance = magicResistance;
    }
    public short getMagicResistanceAdjustment()
    {
        return this.magicResistanceAdjustment;
    }
    public void setMagicResistanceAdjustment(short magicResistanceAdjustment)
    {
        this.magicResistanceAdjustment = magicResistanceAdjustment;
    }
    
    private String activeSpellNameArray[] = new String[] {
            "Bless",
            "Heroism",
            "Haste",
            "Shield",
            "Stoneskin",
            "Lucky Day (Luck)",
            "Meditation (Intellect)",
            "Meditation (Personality)",
            "Precision (Accuracy)",
            "Speed (Speed)",
            "Power (Might)",
            "Power (Endurance)"
    };
    
    public String[] getActiveSpellNameArray()
    {
        return activeSpellNameArray;
    }

    public List getActiveSpellList()
    {
        return activeSpellList;
    }
    
    public int getTicksBeforeReady()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getCharacterData3Data(), TICKS_BEFORE_READY - BASE_CHARACTER_DATA3_OFFSET);
    }
    public void setTicksBeforeReady(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition((short)value, getCharacterData3Data(), TICKS_BEFORE_READY - BASE_CHARACTER_DATA3_OFFSET);
    }

    public int getConditionsCount()
    {
        return NUMBER_OF_CONDITIONS;
    }
    private String conditionNameArray[] = new String[] {
            "Cursed",
            "Weak",
            "Asleep",
            "Afraid",
            "Drunk",
            "Insane",
            "Poison1",
            "Disease1",
            "Poison2",
            "Disease2",
            "Poison3",
            "Disease3",
            "Paralyzed",
            "Unconscious",
            "Dead",
            "Stoned",
            "Eradicated"
    };
    public String getConditionName(int index)
    {
        return conditionNameArray[index];
    }
    
    public long getConditionStartTime(int condition)
    {
        int offset = BASE_CONDITION_OFFSET + (condition * 8) - BASE_CHARACTER_DATA3_OFFSET;
        return ByteConversions.getLongInByteArrayAtPosition(getCharacterData3Data(),  offset);
    }
    public void setConditionStartTime(long startDateTime, int condition)
    {
        int offset = BASE_CONDITION_OFFSET + (condition * 8) - BASE_CHARACTER_DATA3_OFFSET;
        ByteConversions.setLongInByteArrayAtPosition(startDateTime, getCharacterData3Data(), offset);
    }

    public int getSkillPoints()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getCharacterData3Data(), SKILL_POINTS_OFFSET - BASE_CHARACTER_DATA3_OFFSET);
    }
    public void setSkillPoints(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getCharacterData3Data(), SKILL_POINTS_OFFSET - BASE_CHARACTER_DATA3_OFFSET);
    }

    public int getHitPoints()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getCharacterData3Data(), HIT_POINTS_OFFSET - BASE_CHARACTER_DATA3_OFFSET);
    }
    public void setHitPoints(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getCharacterData3Data(), HIT_POINTS_OFFSET - BASE_CHARACTER_DATA3_OFFSET);
    }

    public int getSpellPoints()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getCharacterData3Data(), SPELL_POINTS_OFFSET - BASE_CHARACTER_DATA3_OFFSET);
    }
    public void setSpellPoints(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getCharacterData3Data(), SPELL_POINTS_OFFSET - BASE_CHARACTER_DATA3_OFFSET);
    }

    public int getYearOfBirth()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getCharacterData3Data(), YEAR_OF_BIRTH_OFFSET - BASE_CHARACTER_DATA3_OFFSET);
    }
    public void setYearOfBirth(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getCharacterData3Data(), YEAR_OF_BIRTH_OFFSET - BASE_CHARACTER_DATA3_OFFSET);
    }
    
    public int getQuickSpell()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(getCharacterData3Data(), QUICK_SPELL_OFFSET - BASE_CHARACTER_DATA3_OFFSET);
    }
    public void setQuickSpell(int value)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(value, getCharacterData3Data(), QUICK_SPELL_OFFSET - BASE_CHARACTER_DATA3_OFFSET);
    }

    public List getLloydsBeaconList()
    {
        return this.lloydsBeaconList;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
        offset += CHARACTER_LENGTH;
        
        return (offset == expectedNewOffset);
    }
    
    public static int populateObjects(int gameVersion, byte[] data, int offset, List characterDataList, int characterDataCount)
    {
        for (int characterDataIndex = 0; characterDataIndex < characterDataCount; ++characterDataIndex)
        {
            CharacterData characterData = new CharacterData(gameVersion);
            characterDataList.add(characterData);
            offset = characterData.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List characterDataList)
    {
        for (int characterDataIndex = 0; characterDataIndex < characterDataList.size(); ++characterDataIndex)
        {
            CharacterData characterData = (CharacterData)characterDataList.get(characterDataIndex);
            offset = characterData.updateData(newData, offset);
        }
        
        return offset;
    }

    public byte[] getCharacterDataData()
    {
        return this.characterDataData;
    }
    public int getCharacterDataOffset()
    {
        return this.characterDataOffset;
    }
    public byte[] getCharacterData3Data()
    {
        return this.characterData3Data;
    }
    public int getCharacterData3Offset()
    {
        return this.characterData3Offset;
    }
    public byte[] getCharacterData4Data()
    {
        return this.characterData4Data;
    }
    public int getCharacterData4Offset()
    {
        return this.characterData4Offset;
    }

    public static int getRecordSize()
    {
        return CHARACTER_LENGTH;
    }


    // CharacterData things to decode

    public static List getOffsetList(int size)
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(0, size, ComparativeTableControl.REPRESENTATION_INT_DEC));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List characterDataList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return characterDataList.size();
            }

            public byte[] getData(int dataRow)
            {
                CharacterData characterData = (CharacterData)characterDataList.get(dataRow);
                return characterData.getCharacterDataData();
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

    public List getUnknownByteDataList()
    {
		List unknownList = new ArrayList();
		
		unknownList.add(new ByteData(this.getCharacterDataData(), this.getCharacterDataOffset(), 58, 58 + 38));
		unknownList.add(new ByteData(this.getCharacterDataData(), this.getCharacterDataOffset(), 138, 138 + 53));
		unknownList.add(new ByteData(this.getCharacterDataData(), this.getCharacterDataOffset(), 290, 290 + 34));
		unknownList.add(new ByteData(this.getCharacterData3Data(), this.getCharacterData3Offset(), 4904 - 4904, (4904 - 4904) + 84));
		unknownList.add(new ByteData(this.getCharacterData3Data(), this.getCharacterData3Offset(), 5128 - 4904, (5128 - 4904) + 8));
		unknownList.add(new ByteData(this.getCharacterData3Data(), this.getCharacterData3Offset(), 5156 - 4904, (5156 - 4904) + 267));
		unknownList.add(new ByteData(this.getCharacterData3Data(), this.getCharacterData3Offset(), 5427 - 4904, (5427 - 4904) + 89));
		unknownList.add(new ByteData(this.getCharacterData4Data(), this.getCharacterData4Offset()));

        return unknownList;
    }

}