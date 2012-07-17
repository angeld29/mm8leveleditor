/*
 * Copyright (c) 2007 (Mike) Maurice Kienenberger (mkienenb@gmail.com)
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gamenet.application.mm8leveleditor.data.CoordinateSet;
import org.gamenet.application.mm8leveleditor.data.Event;
import org.gamenet.application.mm8leveleditor.data.EventFormat;
import org.gamenet.application.mm8leveleditor.handler.ResourceServer;
import org.gamenet.util.ByteConversions;
import org.gamenet.util.UnimplementedMethodException;

public class EventFormatMM6 implements EventFormat
{
    // private static final String VERIFICATION_LOGGER_NAME = "verification";
    public static int EVENT_NUMBER_OFFSET = 0; // two bytes
    private static int EVENT_SEQUENCE_OFFSET = 2; // one byte
    private static int EVENT_COMMAND_OFFSET = 3; // one byte
    public static int EVENT_COMMAND_ARGS_OFFSET = 4; // any number of bytes
    
    // commands


    public static final int EVENT_COMMAND__None = 0;
    public static final int EVENT_COMMAND__EndEvent = 1;
    public static final int EVENT_COMMAND__TownEvent = 2;
    public static final int EVENT_COMMAND__EV_PlaySound = 3;
    public static final int EVENT_COMMAND__EventVerb = 4;
    public static final int EVENT_COMMAND__EventMazeInfo = 5;
    public static final int EVENT_COMMAND__ChangeXYZ = 6;
    public static final int EVENT_COMMAND__GiveChest = 7;
    public static final int EVENT_COMMAND__PlayFace = 8;
    public static final int EVENT_COMMAND__CharDamage = 9;
    public static final int EVENT_COMMAND__Weather = 10;
    public static final int EVENT_COMMAND__ModifyTexture = 11;
    public static final int EVENT_COMMAND__EV_PlaySmacker = 12;
    public static final int EVENT_COMMAND__ModifyDecoration = 13;
    public static final int EVENT_COMMAND__IfHas = 14;
    public static final int EVENT_COMMAND__EV_Door = 15;
    public static final int EVENT_COMMAND__Give = 16;
    public static final int EVENT_COMMAND__Take = 17;
    public static final int EVENT_COMMAND__Set = 18;
    public static final int EVENT_COMMAND__CreateMonster = 19;
    public static final int EVENT_COMMAND__ModifyItem = 20;
    public static final int EVENT_COMMAND__EV_CastSpell = 21;
    public static final int EVENT_COMMAND__DoNPC = 22;
    public static final int EVENT_COMMAND__FacetAttrib = 23;
    public static final int EVENT_COMMAND__ModifyMonster = 24;
    public static final int EVENT_COMMAND__RandomGoto = 25;
    public static final int EVENT_COMMAND__GetInput = 26;
    public static final int EVENT_COMMAND__RndPassword = 27;
    public static final int EVENT_COMMAND__RndAnswer = 28;
    public static final int EVENT_COMMAND__EV_PrintStatus = 29;
    public static final int EVENT_COMMAND__PrintScroll = 30;
    public static final int EVENT_COMMAND__TimeEvent = 31;
    public static final int EVENT_COMMAND__ModifyLight = 32;
    public static final int EVENT_COMMAND__AnyKey = 33;
    public static final int EVENT_COMMAND__SpoutObject = 34;
    public static final int EVENT_COMMAND__SetActiveChar = 35;
    public static final int EVENT_COMMAND__EV_Goto = 36;
    public static final int EVENT_COMMAND__OnLoad = 37;
    public static final int EVENT_COMMAND__TimeEvent2 = 38;
    public static final int EVENT_COMMAND__ChangeNPCEvent = 39;
    public static final int EVENT_COMMAND__ChangeNPC2DLoc = 40;
    public static final int EVENT_COMMAND__CreateItem = 41;
    public static final int EVENT_COMMAND__ChangeEvent = 42;
    public static final int EVENT_COMMAND__CheckSkill = 43;
    public static final int EVENT_COMMAND__IfHasPrecondition = 44;
    public static final int EVENT_COMMAND__EndPrecondition = 45;
    public static final int EVENT_COMMAND__Precondition = 46;
    public static final int EVENT_COMMAND__ChangeGroupNews = 47;
    public static final int EVENT_COMMAND__ChangeMonsterGroup = 48;
    public static final int EVENT_COMMAND__ChangeNPCInv = 49;
    public static final int EVENT_COMMAND__ChangeNPCGreeting = 50;
    public static final int EVENT_COMMAND__IfMonsterDead = 51;
    public static final int EVENT_COMMAND__IfMonsterDeadPrecondition = 52;
    public static final int EVENT_COMMAND__OnExit = 53;
    public static final int EVENT_COMMAND__ChangeGroupToGroup = 54;
    public static final int EVENT_COMMAND__ChangeGroupAlly = 55;
    public static final int EVENT_COMMAND__IfSeason = 56;
    public static final int EVENT_COMMAND__ModifyMonsterGroup = 57;
    public static final int EVENT_COMMAND__ModifyChest = 58;
    public static final int EVENT_COMMAND__PlayAction = 59;
    public static final int EVENT_COMMAND__ChangeMonsterInv = 60;
    
    public static final int MM6_EVENT_LAST_COMMAND_TYPE_VALUE = EVENT_COMMAND__ChangeMonsterInv;

    
    public static final int TARGET_TYPE__NONE = 0;
    public static final int TARGET_TYPE__SEX = 1;
    public static final int TARGET_TYPE__CLASS = 2;
    public static final int TARGET_TYPE__HIT_POINTS = 3;
    public static final int TARGET_TYPE__HIT_POINTS_MAX = 4;
    public static final int TARGET_TYPE__SPELL_POINTS = 5;
    public static final int TARGET_TYPE__SPELL_POINTS_MAX = 6;
    public static final int TARGET_TYPE__ARMOR_CLASS = 7;
    public static final int TARGET_TYPE__ARMOR_CLASS_TEMP = 8;
    public static final int TARGET_TYPE__LEVEL_PERM = 9;
    public static final int TARGET_TYPE__LEVEL_TEMP = 10;
    public static final int TARGET_TYPE__AGE = 11;
    public static final int TARGET_TYPE__AWARD = 12;
    public static final int TARGET_TYPE__EXPERIENCE = 13;
    public static final int TARGET_TYPE__RACE = 14;
    public static final int TARGET_TYPE__EVENT_SPELL = 15;
    public static final int TARGET_TYPE__QUEST_BIT = 16;
    public static final int TARGET_TYPE__ITEM = 17;
    public static final int TARGET_TYPE__TIME_OF_DAY = 18;
    public static final int TARGET_TYPE__DAY_OF_YEAR = 19;
    public static final int TARGET_TYPE__DAY_OF_WEEK = 20;
    public static final int TARGET_TYPE__GOLD = 21;
    public static final int TARGET_TYPE__GOLD_RANDOM = 22;
    public static final int TARGET_TYPE__FOOD = 23;
    public static final int TARGET_TYPE__FOOD_RANDOM = 24;
    public static final int TARGET_TYPE__MIGHT_TEMP = 25;
    public static final int TARGET_TYPE__INTELLECT_TEMP = 26;
    public static final int TARGET_TYPE__PERSONALITY_TEMP = 27;
    public static final int TARGET_TYPE__ENDURANCE_TEMP = 28;
    public static final int TARGET_TYPE__SPEED_TEMP = 29;
    public static final int TARGET_TYPE__ACCURACY_TEMP = 30;
    public static final int TARGET_TYPE__LUCK_TEMP = 31;
    public static final int TARGET_TYPE__MIGHT_PERM = 32;
    public static final int TARGET_TYPE__INTELLECT_PERM = 33;
    public static final int TARGET_TYPE__PERSONALITY_PERM = 34;
    public static final int TARGET_TYPE__ENDURANCE_PERM = 35;
    public static final int TARGET_TYPE__SPEED_PERM = 36;
    public static final int TARGET_TYPE__ACCURACY_PERM = 37;
    public static final int TARGET_TYPE__LUCK_PERM = 38;
    public static final int TARGET_TYPE__MIGHT_CURRENT = 39;
    public static final int TARGET_TYPE__INTELLECT_CURRENT = 40;
    public static final int TARGET_TYPE__PERSONALITY_CURRENT = 41;
    public static final int TARGET_TYPE__ENDURANCE_CURRENT = 42;
    public static final int TARGET_TYPE__SPEED_CURRENT = 43;
    public static final int TARGET_TYPE__ACCURACY_CURRENT = 44;
    public static final int TARGET_TYPE__LUCK_CURRENT = 45;
    public static final int TARGET_TYPE__FIRE_RESISTANCE_PERM = 46;
    public static final int TARGET_TYPE__ELECTRICITY_RESISTANCE_PERM = 47;
    public static final int TARGET_TYPE__COLD_RESISTANCE_PERM = 48;
    public static final int TARGET_TYPE__POISON_RESISTANCE_PERM = 49;
    public static final int TARGET_TYPE__MAGIC_RESISTANCE_PERM = 50;
    public static final int TARGET_TYPE__FIRE_RESISTANCE_TEMP = 51;
    public static final int TARGET_TYPE__ELECTRICITY_RESISTANCE_TEMP = 52;
    public static final int TARGET_TYPE__COLD_RESISTANCE_TEMP = 53;
    public static final int TARGET_TYPE__POISON_RESISTANCE_TEMP = 54;
    public static final int TARGET_TYPE__MAGIC_RESISTANCE_TEMP = 55;
    public static final int TARGET_TYPE__SKILL_STAFF = 56;
    public static final int TARGET_TYPE__SKILL_SWORD = 57;
    public static final int TARGET_TYPE__SKILL_DAGGER = 58;
    public static final int TARGET_TYPE__SKILL_AXE = 59;
    public static final int TARGET_TYPE__SKILL_SPEAR = 60;
    public static final int TARGET_TYPE__SKILL_BOW = 61;
    public static final int TARGET_TYPE__SKILL_MACE = 62;
    public static final int TARGET_TYPE__SKILL_BLASTER = 63;
    public static final int TARGET_TYPE__SKILL_SHIELD = 64;
    public static final int TARGET_TYPE__SKILL_LEATHER = 65;
    public static final int TARGET_TYPE__SKILL_CHAIN = 66;
    public static final int TARGET_TYPE__SKILL_PLATE = 67;
    public static final int TARGET_TYPE__SKILL_FIRE_MAGIC = 68;
    public static final int TARGET_TYPE__SKILL_AIR_MAGIC = 69;
    public static final int TARGET_TYPE__SKILL_WATER_MAGIC = 70;
    public static final int TARGET_TYPE__SKILL_EARTH_MAGIC = 71;
    public static final int TARGET_TYPE__SKILL_SPIRIT_MAGIC = 72;
    public static final int TARGET_TYPE__SKILL_MIND_MAGIC = 73;
    public static final int TARGET_TYPE__SKILL_BODY_MAGIC = 74;
    public static final int TARGET_TYPE__SKILL_LIGHT_MAGIC = 75;
    public static final int TARGET_TYPE__SKILL_DARK_MAGIC = 76;
    public static final int TARGET_TYPE__SKILL_IDENTIFY_ITEM = 77;
    public static final int TARGET_TYPE__SKILL_MERCHANT = 78;
    public static final int TARGET_TYPE__SKILL_REPAIR_ITEM = 79;
    public static final int TARGET_TYPE__SKILL_BODY_BUILDING = 80;
    public static final int TARGET_TYPE__SKILL_MEDITATION = 81;
    public static final int TARGET_TYPE__SKILL_PERCEPTION = 82;
    public static final int TARGET_TYPE__SKILL_DIPLOMACY = 83;
    public static final int TARGET_TYPE__SKILL_THIEVERY = 84;
    public static final int TARGET_TYPE__SKILL_LOCK_PICK = 85;
    public static final int TARGET_TYPE__SKILL_LEARNING = 86;
    public static final int TARGET_TYPE__CONDITION_CURSED = 87;
    public static final int TARGET_TYPE__CONDITION_WEAK = 88;
    public static final int TARGET_TYPE__CONDITION_ASLEEP = 89;
    public static final int TARGET_TYPE__CONDITION_AFRAID = 90;
    public static final int TARGET_TYPE__CONDITION_DRUNK = 91;
    public static final int TARGET_TYPE__CONDITION_INSANE = 92;
    public static final int TARGET_TYPE__CONDITION_POISON1 = 93; // green
    public static final int TARGET_TYPE__CONDITION_DISEASE1 = 94; // green
    public static final int TARGET_TYPE__CONDITION_POISON2 = 95; // yellow
    public static final int TARGET_TYPE__CONDITION_DISEASE2 = 96; // yellow
    public static final int TARGET_TYPE__CONDITION_POISON3 = 97; // red
    public static final int TARGET_TYPE__CONDITION_DISEASE3 = 98; // red
    public static final int TARGET_TYPE__CONDITION_PARALYZED = 99;
    public static final int TARGET_TYPE__CONDITION_UNCONSCIOUS = 100;
    public static final int TARGET_TYPE__CONDITION_DEAD = 101;
    public static final int TARGET_TYPE__CONDITION_STONE = 102;
    public static final int TARGET_TYPE__CONDITION_ERADICATED = 103;
    public static final int TARGET_TYPE__CONDITION_GOOD = 104;
    public static final int TARGET_TYPE__DUNGEON_BYTE_1 = 105;
    public static final int TARGET_TYPE__DUNGEON_BYTE_2 = 106;
    public static final int TARGET_TYPE__DUNGEON_BYTE_3 = 107;
    public static final int TARGET_TYPE__DUNGEON_BYTE_4 = 108;
    public static final int TARGET_TYPE__DUNGEON_BYTE_5 = 109;
    public static final int TARGET_TYPE__DUNGEON_BYTE_6 = 110;
    public static final int TARGET_TYPE__DUNGEON_BYTE_7 = 111;
    public static final int TARGET_TYPE__DUNGEON_BYTE_8 = 112;
    public static final int TARGET_TYPE__DUNGEON_BYTE_9 = 113;
    public static final int TARGET_TYPE__DUNGEON_BYTE_10 = 114;
    public static final int TARGET_TYPE__DUNGEON_BYTE_11 = 115;
    public static final int TARGET_TYPE__DUNGEON_BYTE_12 = 116;
    public static final int TARGET_TYPE__DUNGEON_BYTE_13 = 117;
    public static final int TARGET_TYPE__DUNGEON_BYTE_14 = 118;
    public static final int TARGET_TYPE__DUNGEON_BYTE_15 = 119;
    public static final int TARGET_TYPE__DUNGEON_BYTE_16 = 120;
    public static final int TARGET_TYPE__DUNGEON_BYTE_17 = 121;
    public static final int TARGET_TYPE__DUNGEON_BYTE_18 = 122;
    public static final int TARGET_TYPE__DUNGEON_BYTE_19 = 123;
    public static final int TARGET_TYPE__DUNGEON_BYTE_20 = 124;
    public static final int TARGET_TYPE__DUNGEON_BYTE_21 = 125;
    public static final int TARGET_TYPE__DUNGEON_BYTE_22 = 126;
    public static final int TARGET_TYPE__DUNGEON_BYTE_23 = 127;
    public static final int TARGET_TYPE__DUNGEON_BYTE_24 = 128;
    public static final int TARGET_TYPE__DUNGEON_BYTE_25 = 129;
    public static final int TARGET_TYPE__DUNGEON_BYTE_26 = 130;
    public static final int TARGET_TYPE__DUNGEON_BYTE_27 = 131;
    public static final int TARGET_TYPE__DUNGEON_BYTE_28 = 132;
    public static final int TARGET_TYPE__DUNGEON_BYTE_29 = 133;
    public static final int TARGET_TYPE__DUNGEON_BYTE_30 = 134;
    public static final int TARGET_TYPE__DUNGEON_BYTE_31 = 135;
    public static final int TARGET_TYPE__DUNGEON_BYTE_32 = 136;
    public static final int TARGET_TYPE__DUNGEON_BYTE_33 = 137;
    public static final int TARGET_TYPE__DUNGEON_BYTE_34 = 138;
    public static final int TARGET_TYPE__DUNGEON_BYTE_35 = 139;
    public static final int TARGET_TYPE__DUNGEON_BYTE_36 = 140;
    public static final int TARGET_TYPE__DUNGEON_BYTE_37 = 141;
    public static final int TARGET_TYPE__DUNGEON_BYTE_38 = 142;
    public static final int TARGET_TYPE__DUNGEON_BYTE_39 = 143;
    public static final int TARGET_TYPE__DUNGEON_BYTE_40 = 144;
    public static final int TARGET_TYPE__DUNGEON_BYTE_41 = 145;
    public static final int TARGET_TYPE__DUNGEON_BYTE_42 = 146;
    public static final int TARGET_TYPE__DUNGEON_BYTE_43 = 147;
    public static final int TARGET_TYPE__DUNGEON_BYTE_44 = 148;
    public static final int TARGET_TYPE__DUNGEON_BYTE_45 = 149;
    public static final int TARGET_TYPE__DUNGEON_BYTE_46 = 150;
    public static final int TARGET_TYPE__DUNGEON_BYTE_47 = 151;
    public static final int TARGET_TYPE__DUNGEON_BYTE_48 = 152;
    public static final int TARGET_TYPE__DUNGEON_BYTE_49 = 153;
    public static final int TARGET_TYPE__DUNGEON_BYTE_50 = 154;
    public static final int TARGET_TYPE__DUNGEON_BYTE_51 = 155;
    public static final int TARGET_TYPE__DUNGEON_BYTE_52 = 156;
    public static final int TARGET_TYPE__DUNGEON_BYTE_53 = 157;
    public static final int TARGET_TYPE__DUNGEON_BYTE_54 = 158;
    public static final int TARGET_TYPE__DUNGEON_BYTE_55 = 159;
    public static final int TARGET_TYPE__DUNGEON_BYTE_56 = 160;
    public static final int TARGET_TYPE__DUNGEON_BYTE_57 = 161;
    public static final int TARGET_TYPE__DUNGEON_BYTE_58 = 162;
    public static final int TARGET_TYPE__DUNGEON_BYTE_59 = 163;
    public static final int TARGET_TYPE__DUNGEON_BYTE_60 = 164;
    public static final int TARGET_TYPE__DUNGEON_BYTE_61 = 165;
    public static final int TARGET_TYPE__DUNGEON_BYTE_62 = 166;
    public static final int TARGET_TYPE__DUNGEON_BYTE_63 = 167;
    public static final int TARGET_TYPE__DUNGEON_BYTE_64 = 168;
    public static final int TARGET_TYPE__DUNGEON_BYTE_65 = 169;
    public static final int TARGET_TYPE__DUNGEON_BYTE_66 = 170;
    public static final int TARGET_TYPE__DUNGEON_BYTE_67 = 171;
    public static final int TARGET_TYPE__DUNGEON_BYTE_68 = 172;
    public static final int TARGET_TYPE__DUNGEON_BYTE_69 = 173;
    public static final int TARGET_TYPE__DUNGEON_BYTE_70 = 174;
    public static final int TARGET_TYPE__DUNGEON_BYTE_71 = 175;
    public static final int TARGET_TYPE__DUNGEON_BYTE_72 = 176;
    public static final int TARGET_TYPE__DUNGEON_BYTE_73 = 177;
    public static final int TARGET_TYPE__DUNGEON_BYTE_74 = 178;
    public static final int TARGET_TYPE__DUNGEON_BYTE_75 = 179;
    public static final int TARGET_TYPE__DUNGEON_BYTE_76 = 180;
    public static final int TARGET_TYPE__DUNGEON_BYTE_77 = 181;
    public static final int TARGET_TYPE__DUNGEON_BYTE_78 = 182;
    public static final int TARGET_TYPE__DUNGEON_BYTE_79 = 183;
    public static final int TARGET_TYPE__DUNGEON_BYTE_80 = 184;
    public static final int TARGET_TYPE__DUNGEON_BYTE_81 = 185;
    public static final int TARGET_TYPE__DUNGEON_BYTE_82 = 186;
    public static final int TARGET_TYPE__DUNGEON_BYTE_83 = 187;
    public static final int TARGET_TYPE__DUNGEON_BYTE_84 = 188;
    public static final int TARGET_TYPE__DUNGEON_BYTE_85 = 189;
    public static final int TARGET_TYPE__DUNGEON_BYTE_86 = 190;
    public static final int TARGET_TYPE__DUNGEON_BYTE_87 = 191;
    public static final int TARGET_TYPE__DUNGEON_BYTE_88 = 192;
    public static final int TARGET_TYPE__DUNGEON_BYTE_89 = 193;
    public static final int TARGET_TYPE__DUNGEON_BYTE_90 = 194;
    public static final int TARGET_TYPE__DUNGEON_BYTE_91 = 195;
    public static final int TARGET_TYPE__DUNGEON_BYTE_92 = 196;
    public static final int TARGET_TYPE__DUNGEON_BYTE_93 = 197;
    public static final int TARGET_TYPE__DUNGEON_BYTE_94 = 198;
    public static final int TARGET_TYPE__DUNGEON_BYTE_95 = 199;
    public static final int TARGET_TYPE__DUNGEON_BYTE_96 = 200;
    public static final int TARGET_TYPE__DUNGEON_BYTE_97 = 201;
    public static final int TARGET_TYPE__DUNGEON_BYTE_98 = 202;
    public static final int TARGET_TYPE__DUNGEON_BYTE_99 = 203;
    public static final int TARGET_TYPE__DUNGEON_BYTE_100 = 204;
    public static final int TARGET_TYPE__EVENT_AUTONOTE = 205;
    public static final int TARGET_TYPE__MIGHT_MAX = 206;
    public static final int TARGET_TYPE__INTELLECT_MAX = 207;
    public static final int TARGET_TYPE__PERSONALITY_MAX = 208;
    public static final int TARGET_TYPE__ENDURANCE_MAX = 209;
    public static final int TARGET_TYPE__SPEED_MAX = 210;
    public static final int TARGET_TYPE__ACCURACY_MAX = 211;
    public static final int TARGET_TYPE__LUCK_MAX = 212;
    public static final int TARGET_TYPE__CHARACTER_BIT = 213;
    public static final int TARGET_TYPE__NPC_IN_PARTY = 214;
    public static final int TARGET_TYPE__REPUTATION = 215;
    public static final int TARGET_TYPE__DAYS_PAST_1 = 216;
    public static final int TARGET_TYPE__DAYS_PAST_2 = 217;
    public static final int TARGET_TYPE__DAYS_PAST_3 = 218;
    public static final int TARGET_TYPE__DAYS_PAST_4 = 219;
    public static final int TARGET_TYPE__DAYS_PAST_5 = 220;
    public static final int TARGET_TYPE__DAYS_PAST_6 = 221;
    public static final int TARGET_TYPE__PARTY_FLYING = 222;
    public static final int TARGET_TYPE__NPC_PROFESSION_IN_PARTY = 223;
    public static final int TARGET_TYPE__CIRCUS_TOTAL = 224;
    public static final int TARGET_TYPE__SKILL_POINTS = 225;
    public static final int TARGET_TYPE__MONTH_OF_YEAR = 226;
    public static final int TARGET_TYPE__TIMER1 = 227;
    public static final int TARGET_TYPE__TIMER2 = 228;
    public static final int TARGET_TYPE__TIMER3 = 229;
    public static final int TARGET_TYPE__TIMER4 = 230;
    public static final int TARGET_TYPE__TIMER5 = 231;
    public static final int TARGET_TYPE__TIMER6 = 232;
    public static final int TARGET_TYPE__TIMER7 = 233;
    public static final int TARGET_TYPE__TIMER8 = 234;
    public static final int TARGET_TYPE__TIMER9 = 235;
    public static final int TARGET_TYPE__TIMER10 = 236;
    public static final int TARGET_TYPE__MAP_STEAL_DIFFICULTY_TEMP = 244;
    public static final int TARGET_TYPE__MAP_ON_ALERT = 245;
    public static final int TARGET_TYPE__BANK_GOLD = 246;
    public static final int TARGET_TYPE__DEATH = 247;
    public static final int TARGET_TYPE__BOUNTY = 248;
    public static final int TARGET_TYPE__PRISON_TERM = 249;
    public static final int TARGET_TYPE__ARENA_PAGE = 250;
    public static final int TARGET_TYPE__ARENA_SQUIRE = 251;
    public static final int TARGET_TYPE__ARENA_KNIGHT = 252;
    public static final int TARGET_TYPE__ARENA_LORD = 253;
    public static final int TARGET_TYPE__INVISIBILITY = 254;
    public static final int TARGET_TYPE__EQUIP = 255;

    public static final int MM6_EVENT_LAST_TARGET_TYPE_VALUE = TARGET_TYPE__EQUIP;
    
//    private byte data[] = null;
//    private int offset = 0;
//    
//    private int eventNumber = 0;
//    private int eventSequenceNumber = 0;
//    private int eventCommandNumber = 0;

    public EventFormatMM6()
    {
        super();
    }

    public Event createEventInstance()
    {
        return new Event(this);
    }


    protected boolean initializedCommandTypeInfo = false;
    protected Integer commandTypes[] = null;
    protected Map<Integer,int[]> argumentTypeArrayByCommandTypeMap = null;
    protected Map<Integer, ArgumentTypeDisplayInfo[]> argumentTypeDisplayInfoArrayByCommandTypeMap = null;
    
    /** readability method */
    protected Integer _I_(int value)
    {
        return new Integer(value);
    }

    protected void initializeCommandTypeInfo()
    {
        commandTypes = new Integer[] {
                new Integer(EVENT_COMMAND__None), // 0;
                new Integer(EVENT_COMMAND__EndEvent), // 1 - EVENT_COMMAND__STOP_PROCESSING
                new Integer(EVENT_COMMAND__TownEvent), // 2 - EVENT_COMMAND__IDENTITY
                new Integer(EVENT_COMMAND__EV_PlaySound), // 3 - unverified
                new Integer(EVENT_COMMAND__EventVerb), // 4 - EVENT_COMMAND__MOUSEOVER
                new Integer(EVENT_COMMAND__EventMazeInfo), // 5 - unverified
                new Integer(EVENT_COMMAND__ChangeXYZ), // 6 - EVENT_COMMAND__TELEPORT
                new Integer(EVENT_COMMAND__GiveChest), // 7 - EVENT_COMMAND__OPEN_CHEST
                new Integer(EVENT_COMMAND__PlayFace), // 8 - EVENT_COMMAND__SHOW_FACIAL_EXPRESSION
                new Integer(EVENT_COMMAND__CharDamage), // 9 - unverified
                new Integer(EVENT_COMMAND__Weather), // 10 - unverified
                
                new Integer(EVENT_COMMAND__EV_PlaySmacker), // 11 - unverified
                // changing an object texture is 12, maybe playsmacker is 11 instead (swapped for mm7/8?)
                new Integer(EVENT_COMMAND__ModifyTexture), // 12 - EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP
                
                new Integer(EVENT_COMMAND__ModifyDecoration), // 13 - EVENT_COMMAND__CHANGE_SPRITE
                new Integer(EVENT_COMMAND__IfHas), // 14 - EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE
                new Integer(EVENT_COMMAND__EV_Door), // 15 - unverified
                new Integer(EVENT_COMMAND__Give), // 16 - EVENT_COMMAND__ADD_TARGET
                new Integer(EVENT_COMMAND__Take), // 17 - EVENT_COMMAND__DELETE_TARGET
                new Integer(EVENT_COMMAND__Set), // 18 - EVENT_COMMAND__SET_TARGET
                new Integer(EVENT_COMMAND__CreateMonster), // 19 - EVENT_COMMAND__CREATE_LOCAL_MONSTER
                new Integer(EVENT_COMMAND__ModifyItem), // 20 - unverified
                new Integer(EVENT_COMMAND__EV_CastSpell), // 21 - EVENT_COMMAND__CAST_SPELL_FROM_LOCATION
                new Integer(EVENT_COMMAND__DoNPC), // 22 - unverified
                new Integer(EVENT_COMMAND__FacetAttrib), // 23 - unverified
                new Integer(EVENT_COMMAND__ModifyMonster), // 24 - unverified
                new Integer(EVENT_COMMAND__RandomGoto), // 25 - unverified
                new Integer(EVENT_COMMAND__GetInput), // 26 - unverified
                new Integer(EVENT_COMMAND__RndPassword), // 27 - unverified
                new Integer(EVENT_COMMAND__RndAnswer), // 28 - unverified
                new Integer(EVENT_COMMAND__EV_PrintStatus), // 29 - EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING
                new Integer(EVENT_COMMAND__PrintScroll), // 30 - EVENT_COMMAND__SHOW_NPCTEXT_STRING
                new Integer(EVENT_COMMAND__TimeEvent), // 31 - unverified
                new Integer(EVENT_COMMAND__ModifyLight), // 32 - unverified
                new Integer(EVENT_COMMAND__AnyKey), // 33 - unverified
                new Integer(EVENT_COMMAND__SpoutObject), // 34 - EVENT_COMMAND__UNKNOWN_VOLCANO_EFFECT, something to do with the volcano buoy effect at New Sorpigal
                new Integer(EVENT_COMMAND__SetActiveChar), // 35 - EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER
                new Integer(EVENT_COMMAND__EV_Goto), // 36 - EVENT_COMMAND__GOTO
                new Integer(EVENT_COMMAND__OnLoad), // 37 - EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE, Stop processing / On level load level?
                new Integer(EVENT_COMMAND__TimeEvent2), // 38 - EVENT_COMMAND__UNKNOWN_ON_X2_EXECUTE
                new Integer(EVENT_COMMAND__ChangeNPCEvent), // 39 - EVENT_COMMAND__CHANGE_DIALOG_EVENT, npctext/npctopic
                new Integer(EVENT_COMMAND__ChangeNPC2DLoc), // 40 - unverified
                new Integer(EVENT_COMMAND__CreateItem), // 41 - unverified
                new Integer(EVENT_COMMAND__ChangeEvent), // 42 - unverified
                new Integer(EVENT_COMMAND__CheckSkill), // 43 - unverified
                new Integer(EVENT_COMMAND__IfHasPrecondition), // 44 - unverified
                new Integer(EVENT_COMMAND__EndPrecondition), // 45 - unverified
                new Integer(EVENT_COMMAND__Precondition), // 46 - unverified
                new Integer(EVENT_COMMAND__ChangeGroupNews), // 47 - unverified
                new Integer(EVENT_COMMAND__ChangeMonsterGroup), // 48 - unverified
                new Integer(EVENT_COMMAND__ChangeNPCInv), // 49 - unverified
                new Integer(EVENT_COMMAND__ChangeNPCGreeting), // 50 - unverified
                new Integer(EVENT_COMMAND__IfMonsterDead), // 51 - unverified
                new Integer(EVENT_COMMAND__IfMonsterDeadPrecondition), // 52 - unverified
                new Integer(EVENT_COMMAND__OnExit), // 53 - unverified
                new Integer(EVENT_COMMAND__ChangeGroupToGroup), // 54 - unverified
                new Integer(EVENT_COMMAND__ChangeGroupAlly), // 55 - unverified
                new Integer(EVENT_COMMAND__IfSeason), // 56 - unverified
                new Integer(EVENT_COMMAND__ModifyMonsterGroup), // 57 - unverified
                new Integer(EVENT_COMMAND__ModifyChest), // 58 - unverified
                new Integer(EVENT_COMMAND__PlayAction), // 59 - unverified
                new Integer(EVENT_COMMAND__ChangeMonsterInv) // 60 - unverified
        };

        Object argumentTypesForCommandTypeArray[] = new Object[] {
                _I_(EVENT_COMMAND__None),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                _I_(EVENT_COMMAND__EndEvent),  new Object[] {
                        _I_(ARGUMENT_TYPE__ZERO_BYTE_PLACEHOLDER), null, null, _I_(0)
                	},
                _I_(EVENT_COMMAND__TownEvent),  new Object[] { 
                        _I_(ARGUMENT_TYPE__2DEVENT_NUMBER), "Execute 2DEvent#", null, _I_(0)
                	},
                _I_(EVENT_COMMAND__EV_PlaySound),  new Object[] {
        				_I_(ARGUMENT_TYPE__SOUND_NUMBER), "#", null, _I_(0),
            			_I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "unknown int", null, _I_(1), // followed by 8 zero bytes?
            			_I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "unknown int", null, _I_(2)
                	},
                _I_(EVENT_COMMAND__EventVerb),  new Object[] { 
                        _I_(ARGUMENT_TYPE__LOCAL_EVENT_STRING_NUMBER), "local event string #", null, _I_(0)
                    },
                _I_(EVENT_COMMAND__EventMazeInfo),  new Object[] { // sequence is never incremented "free item", ignored in actual game?
                		_I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "?", null, _I_(0)
                	},
                _I_(EVENT_COMMAND__ChangeXYZ),  new Object[] { 
                        _I_(ARGUMENT_TYPE__COORDINATE_SET), "destination coordinates", null, _I_(1), // dest x, y, z
                        _I_(ARGUMENT_TYPE__COORDINATE_SET), "facing", null, _I_(2), // dest HORIZONTAL p, phi_angle; VERTICAL t, theta_angle; ? v, vz - not really sure if this is an orientation
                        _I_(ARGUMENT_TYPE__DIALOG_NUMBER), "dialog#", null, _I_(3), // smacker number - 1 byte mm6/7, 2 bytes mm8
                        _I_(ARGUMENT_TYPE__MINI_ICON_NUMBER), "icon#", null, _I_(4), // picture
                        _I_(ARGUMENT_TYPE__FILENAME_13), "to level", null, _I_(0) // level name
                	},
                _I_(EVENT_COMMAND__GiveChest),  new Object[] {
                        _I_(ARGUMENT_TYPE__CHEST_NUMBER), "#", null, _I_(0)
                    },
                _I_(EVENT_COMMAND__PlayFace),  new Object[] {
                        _I_(ARGUMENT_TYPE__PARTY_MEMBER), "for", null, _I_(1),
                        _I_(ARGUMENT_TYPE__FACE_IMAGE_NUMBER), "image #", null, _I_(0)
                },
                _I_(EVENT_COMMAND__CharDamage),  new Object[] {
            			_I_(ARGUMENT_TYPE__PARTY_MEMBER),  null, null, _I_(0),
            			_I_(ARGUMENT_TYPE__UNKNOWN_BYTE),  "damage type", null, _I_(1),
            			_I_(ARGUMENT_TYPE__UNKNOWN_INTEGER),  "damage amount", null, _I_(2)
                },
                _I_(EVENT_COMMAND__Weather),  new Object[] {
                    	_I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "should change state?", null, _I_(0), // only change the weather if non-zero?
                    	_I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "mode?", null, _I_(1), // 0 is normal, 1 is snow
                	},
                _I_(EVENT_COMMAND__ModifyTexture),  new Object[] { 
                        _I_(ARGUMENT_TYPE__3DOBJECT_NUMBER), "object #", null, _I_(0), // looks like this may be gone in mm7/8
                        _I_(ARGUMENT_TYPE__FACET_NUMBER), "face #", null, _I_(0), // 
                        _I_(ARGUMENT_TYPE__FILENAME_12), "to texture", null, _I_(0) // bitmap name, maybe only 7 characters?
                	},
                _I_(EVENT_COMMAND__EV_PlaySmacker),  new Object[] {
						_I_(ARGUMENT_TYPE__UNKNOWN_BYTE),  "?", null, _I_(0),
						_I_(ARGUMENT_TYPE__UNKNOWN_BYTE),  "?", null, _I_(0),
						_I_(ARGUMENT_TYPE__UNKNOWN_BYTE),  "stretch?", null, _I_(0),
    					_I_(ARGUMENT_TYPE__UNKNOWN_BYTE),  "exit now?", null, _I_(1),
                		_I_(ARGUMENT_TYPE__FILENAME_12), null, null, _I_(2) // movie name, seems like it might only be 9 characters
                	},
                _I_(EVENT_COMMAND__ModifyDecoration),  new Object[] { 
                        _I_(ARGUMENT_TYPE__SPRITE_NUMBER), "#", null, _I_(0), // 
                        _I_(ARGUMENT_TYPE__BOOLEAN), null, "remove if set", _I_(2), // remove decoration if 1, restore if 0
                        _I_(ARGUMENT_TYPE__FILENAME_12), "to", null, _I_(1) // sprite name
                	},
                _I_(EVENT_COMMAND__IfHas),  new Object[] { 
                        _I_(ARGUMENT_TYPE__TARGET_TYPE), "(", null, _I_(0),
                        _I_(ARGUMENT_TYPE__TARGET_NUMBER), null, ")", _I_(1), //  ">= for most target types, unless obviously '=='
                        _I_(ARGUMENT_TYPE__SEQUENCE), "goto seq#", null, _I_(2)
                    },
                _I_(EVENT_COMMAND__EV_Door),  new Object[] {
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "ID", null, _I_(0), // Door ID
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), null, null, _I_(1) // IMPLEMENT: _I_(ARGUMENT_TYPE__DOOR_TRIGGER_TYPE), null, null, _I_(0): 0-open,1-close,2-toggle
                    },
                _I_(EVENT_COMMAND__Give),  new Object[] {
                        _I_(ARGUMENT_TYPE__TARGET_TYPE), null, null, _I_(0),
                        _I_(ARGUMENT_TYPE__TARGET_NUMBER), null, null, _I_(1)
                    },
                _I_(EVENT_COMMAND__Take),  new Object[] {
                        _I_(ARGUMENT_TYPE__TARGET_TYPE), null, null, _I_(0),
                        _I_(ARGUMENT_TYPE__TARGET_NUMBER), null, null, _I_(1)
                    },
                _I_(EVENT_COMMAND__Set),  new Object[] {
                        _I_(ARGUMENT_TYPE__TARGET_TYPE), null, null, _I_(0),
                        _I_(ARGUMENT_TYPE__TARGET_NUMBER), null, null, _I_(1)
                    },
                _I_(EVENT_COMMAND__CreateMonster),  new Object[] {
                        _I_(ARGUMENT_TYPE__SPECIES_TYPE), "Map species #", null, _I_(1),
                        _I_(ARGUMENT_TYPE__SUBSPECIES_TYPE), "subspecies", null, _I_(2),
                        _I_(ARGUMENT_TYPE__MONSTER_CREATION_COUNT), "count", null, _I_(0),
                        _I_(ARGUMENT_TYPE__COORDINATE_SET), "at", null, _I_(3)
                    },
                _I_(EVENT_COMMAND__ModifyItem),  new Object[] {
                		_I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) // 5 bytes, no longer supported in MM7 & MM8? // probably a 4-byte int & a 1 byte
                	},
                _I_(EVENT_COMMAND__EV_CastSpell),  new Object[] {
                        _I_(ARGUMENT_TYPE__SPELL_NUMBER), "Spell #", null, _I_(1),
                        _I_(ARGUMENT_TYPE__SPELL_SKILL_EXPERTISE), "at expertise", null, _I_(2), // TODO: ARGUMENT_TYPE__SPELL_SKILL_EXPERTISE pulldown
                        _I_(ARGUMENT_TYPE__SPELL_SKILL_LEVEL), "level", null, _I_(0), // 
                        _I_(ARGUMENT_TYPE__COORDINATE_SET), "starting at coordinate", null, _I_(3),
                        _I_(ARGUMENT_TYPE__COORDINATE_SET), "ending at coordinate", null, _I_(4)
                    },
                _I_(EVENT_COMMAND__DoNPC),  new Object[] {
                        _I_(ARGUMENT_TYPE__NPCDATA_NUMBER), "npcDataNumber", null, _I_(1) // TODO: npcDataNumber
                    },
                _I_(EVENT_COMMAND__FacetAttrib),  new Object[] { 
                        _I_(ARGUMENT_TYPE__FACET_NUMBER), "facet #", null, _I_(1),
                        _I_(ARGUMENT_TYPE__FACET_ATTRIBUTE_TYPE), "attribute", null, _I_(2),
                        _I_(ARGUMENT_TYPE__BOOLEAN), "Set?", null, _I_(0), // set or unset
                	},
                _I_(EVENT_COMMAND__ModifyMonster),  new Object[] {
                    _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "monsterId?", null, _I_(1),
                    _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "bit?", null, _I_(1),
                    _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "bit?", null, _I_(2), // see MONSTER_ATTRIBUTE_ values
                    _I_(ARGUMENT_TYPE__BOOLEAN), "Set?", null, _I_(0), // set or unset
                	},
                // tested for mm7,mm8
                _I_(EVENT_COMMAND__RandomGoto),  new Object[] {
                        _I_(ARGUMENT_TYPE__SEQUENCE), "non-zero seq# in list: ", ",", _I_(0),
                        _I_(ARGUMENT_TYPE__SEQUENCE), null, ",", _I_(1),
                        _I_(ARGUMENT_TYPE__SEQUENCE), null, ",", _I_(2),
                        _I_(ARGUMENT_TYPE__SEQUENCE), null, ",", _I_(3),
                        _I_(ARGUMENT_TYPE__SEQUENCE), null, ",", _I_(4),
                        _I_(ARGUMENT_TYPE__SEQUENCE), null, null, _I_(5)
                	},
                	
                _I_(EVENT_COMMAND__GetInput),  new Object[] {
                    _I_(ARGUMENT_TYPE__NPCTEXT_NUMBER), ", on failure print status #", ",", _I_(4), // print this if no match
                    _I_(ARGUMENT_TYPE__NPCTEXT_NUMBER), "match #", null, _I_(1), // match either first value
                    _I_(ARGUMENT_TYPE__NPCTEXT_NUMBER), "or #", null, _I_(2), // or match second value
                    _I_(ARGUMENT_TYPE__SEQUENCE), ", on success goto seq#", null, _I_(3) // goto here if matched one
                	},
                _I_(EVENT_COMMAND__RndPassword),  new Object[] { // only in MM6? D09.evt
            			_I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "?", null, _I_(0),
            			_I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "?", null, _I_(0)
                	},
                _I_(EVENT_COMMAND__RndAnswer),  new Object[] { // only in MM6? D09.evt
                		_I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "?", null, _I_(0)
                	},
                _I_(EVENT_COMMAND__EV_PrintStatus),  new Object[] {
                        _I_(ARGUMENT_TYPE__NPCTEXT_NUMBER), "#", null, _I_(0)
                	},
                _I_(EVENT_COMMAND__PrintScroll),  new Object[] {
                        _I_(ARGUMENT_TYPE__NPCTEXT_NUMBER), "#", null, _I_(0)
                   	},
                _I_(EVENT_COMMAND__TimeEvent),  new Object[] {
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "month", null, _I_(0), // TODO: month
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "week", null, _I_(1), // TODO: week
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "day", null, _I_(2), // TODO: day
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "hour", null, _I_(3), // TODO: hour
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "minute", null, _I_(4), // TODO: minute
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "seconds", null, _I_(5), // TODO: seconds
                        _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "interval", null, _I_(6)  // TODO: interval
                	},
                _I_(EVENT_COMMAND__ModifyLight),  new Object[] {
	            		_I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "#?", null, _I_(0),
	            		_I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "mode?", null, _I_(0)
                	},
                _I_(EVENT_COMMAND__AnyKey),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN_ZERO_BYTE), null, null, _I_(0) },
                _I_(EVENT_COMMAND__SpoutObject),  new Object[] {
                        _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "item number/power", null, _I_(1), // TODO: type (combo item number and power?) num % 1000, power / 1000
                        _I_(ARGUMENT_TYPE__COORDINATE_SET), "at", null, _I_(2), // x, y, z
                        _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "velocity", null, _I_(4), // TODO: velocity
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), null, null, _I_(0), // TODO: num
                        _I_(ARGUMENT_TYPE__BOOLEAN), "random?", null, _I_(3) // TODO: randomize direction of spout
                	},
                _I_(EVENT_COMMAND__SetActiveChar),  new Object[] {
                        _I_(ARGUMENT_TYPE__PARTY_MEMBER),  "#", null, _I_(0)
                	},
                _I_(EVENT_COMMAND__EV_Goto),  new Object[] {
                        _I_(ARGUMENT_TYPE__SEQUENCE), "seq#", null, _I_(0)
                    },
                _I_(EVENT_COMMAND__OnLoad),  new Object[] {
                        _I_(ARGUMENT_TYPE__ZERO_BYTE_PLACEHOLDER), null, null, _I_(0)
                    },
                _I_(EVENT_COMMAND__TimeEvent2),  new Object[] {
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "month", null, _I_(0), // TODO: month
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "week", null, _I_(1), // TODO: week
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "day", null, _I_(2), // TODO: day
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "hour", null, _I_(3), // TODO: hour
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "minute", null, _I_(4), // TODO: minute
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "seconds", null, _I_(5), // TODO: seconds
                        _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "interval", null, _I_(6)  // TODO: interval
                	},
                _I_(EVENT_COMMAND__ChangeNPCEvent),  new Object[] {
                        _I_(ARGUMENT_TYPE__NPCDATA_NUMBER), "for NPC#", ",", _I_(0),
                        _I_(ARGUMENT_TYPE__NPC_MENU_INDEX), "index", null, _I_(1),
                        _I_(ARGUMENT_TYPE__GLOBAL_EVENT_NUMBER), "to global event #", null, _I_(2)
                    },
                _I_(EVENT_COMMAND__ChangeNPC2DLoc),  new Object[] {
            			_I_(ARGUMENT_TYPE__NPCDATA_NUMBER), "Move npc #", null, _I_(0), // npc #
            			_I_(ARGUMENT_TYPE__2DEVENT_NUMBER), "to 2DEvent Location", null, _I_(1) // 2DEvent.txt location
                	},
                _I_(EVENT_COMMAND__CreateItem),  new Object[] {
            			_I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "generated level?", null, _I_(0), // must be 1 to 6
            			_I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "generated item type?", null, _I_(1), // unclear what this means, but probably see TREASURE_TYPE_* values defined elsewhere
            			_I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "new item type if non-zero?", null, _I_(2) // same as above
                	},
                	
                	
                _I_(EVENT_COMMAND__ChangeEvent),  new Object[] { // Global.evt
                		_I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "event to change?", null, _I_(0) // no idea, but it has to do with decorations
                	},
/* 
 * Change event values
// 268 Empty Barrel
// 269 Red Barrel
// 270 Yellow Barrel
// 271 Blue Barrel
// 272 Orange Barrel
// 273 Green Barrel
// 274 Purple Barrel
// 275 White Barrel
// 276 Empty Cauldron
// 277 Steaming Cauldron
// 278 Frosty Cauldron
// 279 Shocking Cauldron
// 280 Dirty Cauldron
// 281 Trash Heap
// 282 Trash Heap
// 283 Trash Heap
// 284 Trash Heap
// 285 Camp Fire
// 286 Camp Fire
// 287 Food Bowl
// 288 Empty Cask
// 289 Cask

// 531 Haste Pedestal
// 532 Earth Resistance Pedestal
// 533 Day of the Gods Pedestal
// 534 Shield Pedestal
// 535 Water Resistance Pedestal
// 536 Fire Resistance Pedestal
// 537 
// ...
// 570
 */
                _I_(EVENT_COMMAND__CheckSkill),  new Object[] {
	            		_I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "#?", null, _I_(0),  // skill #?  SKILLDES.TXT with 0-based entries?
	            		_I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "skill mastery?", null, _I_(1), // 0 - has skill, 1 - expert, 2 - master, 3 - grand master
	            		_I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "minimum required?", null, _I_(2), // minimum amount of skill needed
	            		_I_(ARGUMENT_TYPE__SEQUENCE), ", on success goto seq", null, _I_(3)
                	},
                _I_(EVENT_COMMAND__IfHasPrecondition),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                _I_(EVENT_COMMAND__EndPrecondition),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                _I_(EVENT_COMMAND__Precondition),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                _I_(EVENT_COMMAND__ChangeGroupNews),  new Object[] { 
                        _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "for NPC group index", null, _I_(0), // TODO: ARGUMENT_TYPE__NPC_GROUP_INDEX
                        _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "to news number", null, _I_(1)  // TODO: ARGUMENT_TYPE__NEWS_NUMBER
                	},
                _I_(EVENT_COMMAND__ChangeMonsterGroup),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                _I_(EVENT_COMMAND__ChangeNPCInv),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                _I_(EVENT_COMMAND__ChangeNPCGreeting),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                _I_(EVENT_COMMAND__IfMonsterDead),  new Object[] { 
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "type", null, _I_(2), // TODO: type
                        _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "val", null, _I_(3),  // TODO: val
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), null, "monster(s)", _I_(1), // TODO: count
                        _I_(ARGUMENT_TYPE__BOOLEAN), "include disabled?", null, _I_(0), // TODO: disabledIsDead
                        _I_(ARGUMENT_TYPE__SEQUENCE), "seq#", null, _I_(4)
                	},
                _I_(EVENT_COMMAND__IfMonsterDeadPrecondition),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                _I_(EVENT_COMMAND__OnExit),  new Object[] {
                        _I_(ARGUMENT_TYPE__ZERO_BYTE_PLACEHOLDER), null, null, _I_(0)
                    },
                _I_(EVENT_COMMAND__ChangeGroupToGroup),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                _I_(EVENT_COMMAND__ChangeGroupAlly),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                _I_(EVENT_COMMAND__IfSeason),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                _I_(EVENT_COMMAND__ModifyMonsterGroup),  new Object[] { 
                        _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "monster group", null, _I_(0), // TODO: ARGUMENT_TYPE__MONSTER_GROUP
                        _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "attribute", null, _I_(2), // TODO: ARGUMENT_TYPE__ATTRIBUTE (MG or M?)
                        _I_(ARGUMENT_TYPE__BOOLEAN), "Set?", null, _I_(1)  // set/unset
                	},
                _I_(EVENT_COMMAND__ModifyChest),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                _I_(EVENT_COMMAND__PlayAction),  new Object[] {
                        _I_(ARGUMENT_TYPE__PARTY_MEMBER), "for", null, _I_(1),
                        _I_(ARGUMENT_TYPE__UNKNOWN_BYTE), "action state", null, _I_(0)  // TODO: ARGUMENT_TYPE__ACTION_STATE
                    },
                _I_(EVENT_COMMAND__ChangeMonsterInv),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) }
            };

        int argumentTypesForCommandTypeArrayColumns = 2;
        if (argumentTypesForCommandTypeArray.length % argumentTypesForCommandTypeArrayColumns != 0)
        {
            throw new RuntimeException("argumentTypesForCommandTypeArray.length = " + argumentTypesForCommandTypeArray.length + " which is not a power of " + argumentTypesForCommandTypeArrayColumns + ".");
        }
        
        argumentTypeArrayByCommandTypeMap = new HashMap<Integer,int[]>();
        argumentTypeDisplayInfoArrayByCommandTypeMap = new HashMap<Integer,ArgumentTypeDisplayInfo[]>();
        for (int argumentTypesForCommandTypeArrayIndex = 0; argumentTypesForCommandTypeArrayIndex < argumentTypesForCommandTypeArray.length; argumentTypesForCommandTypeArrayIndex = argumentTypesForCommandTypeArrayIndex+argumentTypesForCommandTypeArrayColumns)
        {
            Integer commandTypeNumber = (Integer)argumentTypesForCommandTypeArray[argumentTypesForCommandTypeArrayIndex];
            Object argumentTypeDataArray[] = (Object [])argumentTypesForCommandTypeArray[argumentTypesForCommandTypeArrayIndex+1];
            
            Integer key = null;
            for (int index = 0; index < commandTypes.length; index++) {
    			if (commandTypes[index].equals(commandTypeNumber))
    			{
    				key = new Integer(index);
    			}
    		}
            if (null == key)
            {
                throw new RuntimeException("Unable to find command type value" + commandTypeNumber + " in commandTypes");
            }

            int[] argumentTypeArray = getArgumentTypeArrayFromArgumentTypeDataArray(argumentTypeDataArray);

            if (argumentTypeArrayByCommandTypeMap.containsKey(key))
            {
            	int[] value = argumentTypeArrayByCommandTypeMap.get(key);
                throw new RuntimeException("argumentTypeArrayByCommandTypeMap already contains key " + key + " with value of " + value);
            }

            argumentTypeArrayByCommandTypeMap.put(key, argumentTypeArray);
            
            
            ArgumentTypeDisplayInfo[] argumentTypeDisplayInfoArray = getArgumentTypeDisplayInfoArrayFromArgumentTypeDataArray(argumentTypeDataArray);

            if (argumentTypeDisplayInfoArrayByCommandTypeMap.containsKey(key))
            {
            	ArgumentTypeDisplayInfo[] value = argumentTypeDisplayInfoArrayByCommandTypeMap.get(key);
                throw new RuntimeException("argumentTypeDisplayInfoArrayByCommandTypeMap already contains key " + key + " with value of " + value);
            }

            argumentTypeDisplayInfoArrayByCommandTypeMap.put(key, argumentTypeDisplayInfoArray);
        }
        
        initializedCommandTypeInfo = true;
    }

    protected int[] getArgumentTypeArrayFromArgumentTypeDataArray(Object[] argumentTypeDataArray)
    {
        // 4 columns: argumentType, pre-label, post-label, ordering
        int argumentTypeDataColumns = 4;
        if (argumentTypeDataArray.length % argumentTypeDataColumns != 0)
        {
            throw new RuntimeException("argumentTypeDataArray.length = " + argumentTypeDataArray.length + " which is not a power of " + argumentTypeDataColumns + ".");
        }
        
        int argumentTypeArray[] = new int[argumentTypeDataArray.length / argumentTypeDataColumns];
        int argumentTypeArrayCounter = 0;
        for (int argumentTypeDataArrayIndex = 0; argumentTypeDataArrayIndex < argumentTypeDataArray.length; argumentTypeDataArrayIndex = argumentTypeDataArrayIndex+argumentTypeDataColumns)
        {
            Integer argumentType = (Integer)argumentTypeDataArray[argumentTypeDataArrayIndex];
            
            argumentTypeArray[argumentTypeArrayCounter++] = argumentType.intValue();
        }
        return argumentTypeArray;
    }

    protected class ArgumentTypeDisplayInfo
    {
    	private IndirectValue indirectValue;
		private String prefixLabel;
        private String suffixLabel;
        private int sortOrdering;
        /**
         * @param prefixLabel
         * @param suffixLabel
         * @param sortOrdering
         * @param isIndirect
         */
        public ArgumentTypeDisplayInfo(String prefixLabel, String suffixLabel,
                int sortOrdering, IndirectValue indirectValue)
        {
            super();
            this.prefixLabel = prefixLabel;
            this.suffixLabel = suffixLabel;
            this.sortOrdering = sortOrdering;
            this.indirectValue = indirectValue;
        }

        public String getPrefixLabel()
        {
            return this.prefixLabel;
        }
        public int getSortOrdering()
        {
            return this.sortOrdering;
        }
        public String getSuffixLabel()
        {
            return this.suffixLabel;
        }
        public IndirectValue getIndirectValue(Event event, int argumentIndex)
        {
           	// special case for target type
        	int argumentType = getArgumentTypeAtIndexForCommandType(argumentIndex, event.getCommandType());
            if (ARGUMENT_TYPE__TARGET_NUMBER == argumentType)
            {
            	int previousArgumentType = getArgumentTypeAtIndexForCommandType(argumentIndex-1, event.getCommandType());
                if (ARGUMENT_TYPE__TARGET_TYPE == previousArgumentType)
                {
                	int targetType = ((Number)event.getArgumentAtIndex(argumentIndex-1)).intValue();
                	return getIndirectValueForTargetType(targetType);
                }
            }

        	return this.indirectValue;
        }
        public boolean isIndirect(Event event, int argumentIndex) {
        	// special case for target type
        	int argumentType = getArgumentTypeAtIndexForCommandType(argumentIndex, event.getCommandType());
            if (ARGUMENT_TYPE__TARGET_NUMBER == argumentType)
            {
            	int previousArgumentType = getArgumentTypeAtIndexForCommandType(argumentIndex-1, event.getCommandType());
                if (ARGUMENT_TYPE__TARGET_TYPE == previousArgumentType)
                {
                	int targetType = ((Number)event.getArgumentAtIndex(argumentIndex-1)).intValue();
                	return hasIndirectValueForTargetType(targetType);
                }
            }

			return this.indirectValue != null;
		}
    }

    protected ArgumentTypeDisplayInfo[] getArgumentTypeDisplayInfoArrayFromArgumentTypeDataArray(Object[] argumentTypeDataArray)
    {
        // 4 columns: argumentType, pre-label, post-label, ordering
        int argumentTypeDataColumns = 4;
        if (argumentTypeDataArray.length % argumentTypeDataColumns != 0)
        {
            throw new RuntimeException("argumentTypeDataArray.length = " + argumentTypeDataArray.length + " which is not a power of " + argumentTypeDataColumns + ".");
        }
        
        ArgumentTypeDisplayInfo argumentTypeDisplayInfoArray[] = new ArgumentTypeDisplayInfo[argumentTypeDataArray.length / argumentTypeDataColumns];
        int argumentTypeArrayCounter = 0;
        for (int argumentTypeDataArrayIndex = 0; argumentTypeDataArrayIndex < argumentTypeDataArray.length; argumentTypeDataArrayIndex = argumentTypeDataArrayIndex+argumentTypeDataColumns)
        {
            Integer argumentType = (Integer)argumentTypeDataArray[argumentTypeDataArrayIndex];
            String prefixLabel = (String)argumentTypeDataArray[argumentTypeDataArrayIndex+1];
            String suffixLabel = (String)argumentTypeDataArray[argumentTypeDataArrayIndex+2];
            Integer sortOrder = (Integer)argumentTypeDataArray[argumentTypeDataArrayIndex+3];
            
            IndirectValue<Integer,String> indirectValue = null;
            
            // If this is Global.evt, use NPCTEXT.TXT for this instead
            if ((ARGUMENT_TYPE__NPCTEXT_NUMBER == argumentType.intValue())
                    || (ARGUMENT_TYPE__LOCAL_EVENT_STRING_NUMBER == argumentType.intValue()))
                    {
                    	indirectValue = new IndirectValue<Integer,String>() {
                    		public int getDirectArgumentDataType() {
                    			return EventFormat.ARGUMENT_DATA_TYPE__STRING;
                    		}
            				public Integer getIndirectValueForDirectValue(String directValue) {
            					return getStrResource().getStringsList().indexOf(directValue);
            				}
            				public String getDirectValueForIndirectValue(Integer indirectValue) {
            					if (null == getStrResource())
            					{
        							return "WARNING: **No str resource found**";
            					}
            					try {
        							return getStrResource().getStringsList().get(indirectValue.intValue());
        						} catch (IndexOutOfBoundsException e) {
        							return "WARNING: **No str value found in str resource file**";
        						}
             				}
            			};
                    }
            
            else if (ARGUMENT_TYPE__NPCDATA_NUMBER == argumentType.intValue())
            {
            	indirectValue = new IndirectValue<Integer,String>() {
            		public int getDirectArgumentDataType() {
            			return EventFormat.ARGUMENT_DATA_TYPE__STRING;
            		}
    				public Integer getIndirectValueForDirectValue(String directValue) {
    					throw new UnimplementedMethodException("NPCdata.txt resource getIndirectValueForDirectValue(String directValue)");
    				}
    				public String getDirectValueForIndirectValue(Integer indirectValue) {
    					NPCdataTxt npcDataTxt = ResourceServer.getInstance().getNPCDataTxt();
    					if (null == npcDataTxt)
    					{
    						return "WARNING: **No NPCdata.txt resource found**";
    					}
    					try {
    						return npcDataTxt.getNameAtIndex(indirectValue.intValue());
    					} catch (IndexOutOfBoundsException e) {
    						return "WARNING: **No item value " + indirectValue.intValue() + " found in NPCdata.txt resource file**";
    					}
    				}
    			};
            }
            
            else if (ARGUMENT_TYPE__2DEVENT_NUMBER == argumentType.intValue())
                    {
                    	indirectValue = new IndirectValue<Integer,String>() {
                    		public int getDirectArgumentDataType() {
                    			return EventFormat.ARGUMENT_DATA_TYPE__STRING;
                    		}
            				public Integer getIndirectValueForDirectValue(String directValue) {
            					throw new UnimplementedMethodException("2DEvents.txt resource getIndirectValueForDirectValue(String directValue)");
            				}
            				public String getDirectValueForIndirectValue(Integer indirectValue) {
            					D2EventsTxt d2EventsTxt = ResourceServer.getInstance().getD2EventsTxt();
            					if (null == d2EventsTxt)
            					{
            						return "WARNING: **No 2DEvents.txt resource found**";
            					}
            					try {
            						return d2EventsTxt.getNameAtIndex(indirectValue.intValue());
            					} catch (IndexOutOfBoundsException e) {
            						return "WARNING: **No name value " + indirectValue.intValue() + " found in 2DEvents.txt resource file**";
            					}
            				}
            			};
                    }

            ArgumentTypeDisplayInfo argumentTypeDisplayInfo = new ArgumentTypeDisplayInfo(
            		prefixLabel, suffixLabel, sortOrder.intValue(), indirectValue);
            argumentTypeDisplayInfoArray[argumentTypeArrayCounter++] = argumentTypeDisplayInfo;
        }
        return argumentTypeDisplayInfoArray;
    }

    // IMPLEMENT: Move into the command array since the same commandType value can be reused 
    public String getCommandTypeName(int commandType)
    {
        Integer commandTypeKey = getCommandTypeKey(commandType);
        if (null == commandTypeKey)
        {
            return "UNKNOWN COMMAND TYPE 0x" + Integer.toHexString(commandType);
        }

        switch(commandTypeKey.intValue())
        {
		    case EVENT_COMMAND__None:  return "None";
		    case EVENT_COMMAND__EndEvent:  return "End Event";
		    case EVENT_COMMAND__TownEvent:  return "TownEvent";
		    case EVENT_COMMAND__EV_PlaySound:  return "PlaySound";
		    case EVENT_COMMAND__EventVerb:  return "EventVerb";
		    case EVENT_COMMAND__EventMazeInfo:  return "EventMazeInfo";
		    case EVENT_COMMAND__ChangeXYZ:  return "Change Party Location";
		    case EVENT_COMMAND__GiveChest:  return "GiveChest";
		    case EVENT_COMMAND__PlayFace:  return "PlayFace";
		    case EVENT_COMMAND__CharDamage:  return "Damage Character";
		    case EVENT_COMMAND__Weather:  return "Weather";
		    case EVENT_COMMAND__ModifyTexture:  return "ModifyTexture";
		    case EVENT_COMMAND__EV_PlaySmacker:  return "PlaySmacker";
		    case EVENT_COMMAND__ModifyDecoration:  return "ModifyDecoration";
		    case EVENT_COMMAND__IfHas:  return "If Has";
		    case EVENT_COMMAND__EV_Door:  return "Door";
		    case EVENT_COMMAND__Give:  return "Give";
		    case EVENT_COMMAND__Take:  return "Take";
		    case EVENT_COMMAND__Set:  return "Set";
		    case EVENT_COMMAND__CreateMonster:  return "CreateMonster";
		    case EVENT_COMMAND__ModifyItem:  return "ModifyItem";
		    case EVENT_COMMAND__EV_CastSpell:  return "Cast Spell";
		    case EVENT_COMMAND__DoNPC:  return "DoNPC";
		    case EVENT_COMMAND__FacetAttrib:  return "Change Facet Attribute";
		    case EVENT_COMMAND__ModifyMonster:  return "ModifyMonster";
		    case EVENT_COMMAND__RandomGoto:  return "Goto Random";
		    case EVENT_COMMAND__GetInput:  return "Get Keyboard Input";
		    case EVENT_COMMAND__RndPassword:  return "RndPassword";
		    case EVENT_COMMAND__RndAnswer:  return "RndAnswer";
		    case EVENT_COMMAND__EV_PrintStatus:  return "PrintStatus";
		    case EVENT_COMMAND__PrintScroll:  return "PrintScroll";
		    case EVENT_COMMAND__TimeEvent:  return "TimeEvent";
		    case EVENT_COMMAND__ModifyLight:  return "ModifyLight";
		    case EVENT_COMMAND__AnyKey:  return "Wait for Key Press";
		    case EVENT_COMMAND__SpoutObject:  return "Spew Object";
		    case EVENT_COMMAND__SetActiveChar:  return "SetActiveChar";
		    case EVENT_COMMAND__EV_Goto:  return "Goto";
		    case EVENT_COMMAND__OnLoad:  return "Run Event OnLoad";
		    case EVENT_COMMAND__TimeEvent2:  return "TimeEvent2";
		    case EVENT_COMMAND__ChangeNPCEvent:  return "ChangeNPCEvent";
		    case EVENT_COMMAND__ChangeNPC2DLoc:  return "ChangeNPC2DLoc";
		    case EVENT_COMMAND__CreateItem:  return "CreateItem";
		    case EVENT_COMMAND__ChangeEvent:  return "ChangeEvent";
		    case EVENT_COMMAND__CheckSkill:  return "CheckSkill";
		    case EVENT_COMMAND__IfHasPrecondition:  return "IfHasPrecondition";
		    case EVENT_COMMAND__EndPrecondition:  return "EndPrecondition";
		    case EVENT_COMMAND__Precondition:  return "Precondition";
		    case EVENT_COMMAND__ChangeGroupNews:  return "ChangeGroupNews";
		    case EVENT_COMMAND__ChangeMonsterGroup:  return "ChangeMonsterGroup";
		    case EVENT_COMMAND__ChangeNPCInv:  return "ChangeNPCInv";
		    case EVENT_COMMAND__ChangeNPCGreeting:  return "ChangeNPCGreeting";
		    case EVENT_COMMAND__IfMonsterDead:  return "IfMonsterDead";
		    case EVENT_COMMAND__IfMonsterDeadPrecondition:  return "IfMonsterDeadPrecondition";
		    case EVENT_COMMAND__OnExit:  return "Run Event OnExit";
		    case EVENT_COMMAND__ChangeGroupToGroup:  return "ChangeGroupToGroup";
		    case EVENT_COMMAND__ChangeGroupAlly:  return "ChangeGroupAlly";
		    case EVENT_COMMAND__IfSeason:  return "IfSeason";
		    case EVENT_COMMAND__ModifyMonsterGroup:  return "ModifyMonsterGroup";
		    case EVENT_COMMAND__ModifyChest:  return "ModifyChest";
		    case EVENT_COMMAND__PlayAction:  return "PlayAction";
		    case EVENT_COMMAND__ChangeMonsterInv:  return "ChangeMonsterInv";
            default:
                return "UNKNOWN COMMAND TYPE 0x" + Integer.toHexString(commandType);
        }
    }
    
    protected boolean initializedArgumentTypeInfo = false;
    protected Map<Integer,Integer> argumentDataTypeByArgumentTypeMap = null;
    protected Map<Integer,Integer> sizeByArgumentTypeMap = null;

    protected void initializeArgumentTypeInfo()
    {
        // argument type, size of each argument type
        int argsAndSizeArray[] = new int[] {
                ARGUMENT_TYPE__UNKNOWN, ARGUMENT_DATA_TYPE__BYTE_ARRAY, -1,
                ARGUMENT_TYPE__UNKNOWN_BYTE, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                ARGUMENT_TYPE__UNKNOWN_SHORT, ARGUMENT_DATA_TYPE__UNSIGNED_SHORT, 2,
                ARGUMENT_TYPE__UNKNOWN_INTEGER, ARGUMENT_DATA_TYPE__SIGNED_INT, 4,
                ARGUMENT_TYPE__UNKNOWN_ZERO_BYTE, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                ARGUMENT_TYPE__UNKNOWN_ZERO_SHORT, ARGUMENT_DATA_TYPE__UNSIGNED_SHORT, 2,
                ARGUMENT_TYPE__UNKNOWN_ZERO_INTEGER, ARGUMENT_DATA_TYPE__SIGNED_INT, 4,
                ARGUMENT_TYPE__TARGET_TYPE, ARGUMENT_DATA_TYPE__CHOICE_PULLDOWN, 1, // 2 for mm7/8
                ARGUMENT_TYPE__TARGET_NUMBER, ARGUMENT_DATA_TYPE__SIGNED_INT, 4,
                ARGUMENT_TYPE__SEQUENCE, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                ARGUMENT_TYPE__2DEVENT_NUMBER, ARGUMENT_DATA_TYPE__SIGNED_INT, 4,
                ARGUMENT_TYPE__LOCAL_EVENT_STRING_NUMBER, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                ARGUMENT_TYPE__COORDINATE_SET, ARGUMENT_DATA_TYPE__COORDINATE_SET, 12,
                ARGUMENT_TYPE__DIALOG_NUMBER, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1, // 2 for MM8, smacker #
                ARGUMENT_TYPE__MINI_ICON_NUMBER, ARGUMENT_DATA_TYPE__CHOICE_PULLDOWN, 1,
                ARGUMENT_TYPE__FILENAME_12, ARGUMENT_DATA_TYPE__STRING, 12,
                ARGUMENT_TYPE__FILENAME_13, ARGUMENT_DATA_TYPE__STRING, 13,
                ARGUMENT_TYPE__CHEST_NUMBER, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                ARGUMENT_TYPE__PARTY_MEMBER, ARGUMENT_DATA_TYPE__CHOICE_PULLDOWN, 1,
                ARGUMENT_TYPE__FACE_IMAGE_NUMBER, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                ARGUMENT_TYPE__3DOBJECT_NUMBER, ARGUMENT_DATA_TYPE__SIGNED_INT, 4,
                ARGUMENT_TYPE__FACET_NUMBER, ARGUMENT_DATA_TYPE__SIGNED_INT, 4,
                ARGUMENT_TYPE__SPRITE_NUMBER, ARGUMENT_DATA_TYPE__SIGNED_INT, 4,
                ARGUMENT_TYPE__BOOLEAN, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                ARGUMENT_TYPE__SPECIES_TYPE, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                ARGUMENT_TYPE__SUBSPECIES_TYPE, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                ARGUMENT_TYPE__MONSTER_CREATION_COUNT, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                ARGUMENT_TYPE__SPELL_NUMBER, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                ARGUMENT_TYPE__SPELL_SKILL_EXPERTISE, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                ARGUMENT_TYPE__SPELL_SKILL_LEVEL, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                
// Looking like this is really another index into the appropriate .STR file, same as ARGUMENT_TYPE__LOCAL_EVENT_STRING_NUMBER,
// actually, print_scroll uses npctext.txt instead for global.evt, but not anywhere else
                ARGUMENT_TYPE__NPCTEXT_NUMBER, ARGUMENT_DATA_TYPE__SIGNED_INT, 4,
                
                ARGUMENT_TYPE__NPCDATA_NUMBER, ARGUMENT_DATA_TYPE__SIGNED_INT, 4,
                ARGUMENT_TYPE__NPC_MENU_INDEX, ARGUMENT_DATA_TYPE__UNSIGNED_BYTE, 1,
                ARGUMENT_TYPE__GLOBAL_EVENT_NUMBER, ARGUMENT_DATA_TYPE__SIGNED_INT, 4,
                ARGUMENT_TYPE__ZERO_BYTE_PLACEHOLDER, ARGUMENT_DATA_TYPE__PLACEHOLDER, 1,
                ARGUMENT_TYPE__FACET_ATTRIBUTE_TYPE, ARGUMENT_DATA_TYPE__CHOICE_PULLDOWN, 4,
                ARGUMENT_TYPE__SOUND_NUMBER, ARGUMENT_DATA_TYPE__SIGNED_INT, 4
         };
        
        int argsAndSizeArrayColumnCount = 3;
        if (argsAndSizeArray.length % argsAndSizeArrayColumnCount != 0)
        {
            throw new RuntimeException("argsAndSizeArray.length = " + argsAndSizeArray.length + " which is not a power of argsAndSizeArrayColumnCount.");
        }
        
        sizeByArgumentTypeMap = new HashMap<Integer,Integer>();
        argumentDataTypeByArgumentTypeMap = new HashMap<Integer,Integer>();
        for (int index = 0; index < argsAndSizeArray.length; index = index+argsAndSizeArrayColumnCount)
        {
            int argumentType = argsAndSizeArray[index];
            int argumentDataType = argsAndSizeArray[index+1];
            int size = argsAndSizeArray[index+2];
            
            Integer key = new Integer(argumentType);

            if (argumentDataTypeByArgumentTypeMap.containsKey(key))
            {
                Integer value = argumentDataTypeByArgumentTypeMap.get(key);
                throw new RuntimeException("argumentDataTypeByArgumentTypeMap already contains key " + argumentType + " with value of " + value);
            }

            Integer newArgumentDataTypeValue = new Integer(argumentDataType);
            argumentDataTypeByArgumentTypeMap.put(key, newArgumentDataTypeValue);


            if (sizeByArgumentTypeMap.containsKey(key))
            {
                Integer value = sizeByArgumentTypeMap.get(key);
                throw new RuntimeException("sizeByArgumentTypeMap already contains key " + argumentType + " with value of " + value);
            }

            Integer newSizeValue = new Integer(size);
            sizeByArgumentTypeMap.put(key, newSizeValue);
        }

        initializedArgumentTypeInfo = true;
    }

    protected boolean initializedTargetTypeInfo = false;
    protected Integer[] targetTypeArray = null;
    
    protected void initializeTargetTypeInfo()
    {
        targetTypeArray = new Integer[] {
                new Integer(TARGET_TYPE__NONE), // 0
                new Integer(TARGET_TYPE__SEX), // 1
                new Integer(TARGET_TYPE__CLASS), // 2
                new Integer(TARGET_TYPE__HIT_POINTS), // 3
                new Integer(TARGET_TYPE__HIT_POINTS_MAX), // 4
                new Integer(TARGET_TYPE__SPELL_POINTS), // 5
                new Integer(TARGET_TYPE__SPELL_POINTS_MAX), // 6
                new Integer(TARGET_TYPE__ARMOR_CLASS), // 7
                new Integer(TARGET_TYPE__ARMOR_CLASS_TEMP), // 8
                new Integer(TARGET_TYPE__LEVEL_PERM), // 9
                new Integer(TARGET_TYPE__LEVEL_TEMP), // 10
                new Integer(TARGET_TYPE__AGE), // 11
                new Integer(TARGET_TYPE__AWARD), // 12
                new Integer(TARGET_TYPE__EXPERIENCE), // 13
                new Integer(TARGET_TYPE__RACE), // 14
                new Integer(TARGET_TYPE__EVENT_SPELL), // 15
                new Integer(TARGET_TYPE__QUEST_BIT), // 16
                new Integer(TARGET_TYPE__ITEM), // 17
                new Integer(TARGET_TYPE__TIME_OF_DAY), // 18
                new Integer(TARGET_TYPE__DAY_OF_YEAR), // 19
                new Integer(TARGET_TYPE__DAY_OF_WEEK), // 20
                new Integer(TARGET_TYPE__GOLD), // 21
                new Integer(TARGET_TYPE__GOLD_RANDOM), // 22
                new Integer(TARGET_TYPE__FOOD), // 23
                new Integer(TARGET_TYPE__FOOD_RANDOM), // 24
                new Integer(TARGET_TYPE__MIGHT_TEMP), // 25
                new Integer(TARGET_TYPE__INTELLECT_TEMP), // 26
                new Integer(TARGET_TYPE__PERSONALITY_TEMP), // 27
                new Integer(TARGET_TYPE__ENDURANCE_TEMP), // 28
                
                new Integer(TARGET_TYPE__SPEED_TEMP), // 29
                new Integer(TARGET_TYPE__ACCURACY_TEMP), // 30
                new Integer(TARGET_TYPE__LUCK_TEMP), // 31
                new Integer(TARGET_TYPE__MIGHT_PERM), // 32
                new Integer(TARGET_TYPE__INTELLECT_PERM), // 33
                new Integer(TARGET_TYPE__PERSONALITY_PERM), // 34
                new Integer(TARGET_TYPE__ENDURANCE_PERM), // 35
                new Integer(TARGET_TYPE__SPEED_PERM), // 36
                new Integer(TARGET_TYPE__ACCURACY_PERM), // 37
                new Integer(TARGET_TYPE__LUCK_PERM), // 38
                new Integer(TARGET_TYPE__MIGHT_CURRENT), // 39
                new Integer(TARGET_TYPE__INTELLECT_CURRENT), // 40
                new Integer(TARGET_TYPE__PERSONALITY_CURRENT), // 41
                new Integer(TARGET_TYPE__ENDURANCE_CURRENT), // 42
                new Integer(TARGET_TYPE__SPEED_CURRENT), // 43
                new Integer(TARGET_TYPE__ACCURACY_CURRENT), // 44
                new Integer(TARGET_TYPE__LUCK_CURRENT), // 45
                new Integer(TARGET_TYPE__FIRE_RESISTANCE_PERM), // 46
                new Integer(TARGET_TYPE__ELECTRICITY_RESISTANCE_PERM), // 47
                new Integer(TARGET_TYPE__COLD_RESISTANCE_PERM), // 48
                new Integer(TARGET_TYPE__POISON_RESISTANCE_PERM), // 49
                new Integer(TARGET_TYPE__MAGIC_RESISTANCE_PERM), // 50

                new Integer(TARGET_TYPE__FIRE_RESISTANCE_TEMP), // 51
                new Integer(TARGET_TYPE__ELECTRICITY_RESISTANCE_TEMP), // 52
                new Integer(TARGET_TYPE__COLD_RESISTANCE_TEMP), // 53
                new Integer(TARGET_TYPE__POISON_RESISTANCE_TEMP), // 54
                new Integer(TARGET_TYPE__MAGIC_RESISTANCE_TEMP), // 55

                new Integer(TARGET_TYPE__SKILL_STAFF), // 56
                new Integer(TARGET_TYPE__SKILL_SWORD), // 57
                new Integer(TARGET_TYPE__SKILL_DAGGER), // 58
                
                // Guessing these ones: 59 to 62
                new Integer(TARGET_TYPE__SKILL_AXE), // 59
                new Integer(TARGET_TYPE__SKILL_SPEAR), // 60
                new Integer(TARGET_TYPE__SKILL_BOW), // 61
                new Integer(TARGET_TYPE__SKILL_MACE), // 62
                
                new Integer(TARGET_TYPE__SKILL_BLASTER), // 63
                
                new Integer(TARGET_TYPE__SKILL_SHIELD), // 64
                new Integer(TARGET_TYPE__SKILL_LEATHER), // 65
                new Integer(TARGET_TYPE__SKILL_CHAIN), // 66
                new Integer(TARGET_TYPE__SKILL_PLATE), // 67

                new Integer(TARGET_TYPE__SKILL_FIRE_MAGIC), // 68

                // Guessing these ones: 69 to 73
                new Integer(TARGET_TYPE__SKILL_AIR_MAGIC), // 69
                new Integer(TARGET_TYPE__SKILL_WATER_MAGIC), // 70
                new Integer(TARGET_TYPE__SKILL_EARTH_MAGIC), // 71
                new Integer(TARGET_TYPE__SKILL_SPIRIT_MAGIC), // 72
                new Integer(TARGET_TYPE__SKILL_MIND_MAGIC), // 73
                
                new Integer(TARGET_TYPE__SKILL_BODY_MAGIC), // 74

                
                new Integer(TARGET_TYPE__SKILL_LIGHT_MAGIC), // 75
                new Integer(TARGET_TYPE__SKILL_DARK_MAGIC), // 76

                new Integer(TARGET_TYPE__SKILL_IDENTIFY_ITEM), // 77
                
                // guessing these values
                new Integer(TARGET_TYPE__SKILL_MERCHANT), // 78
                new Integer(TARGET_TYPE__SKILL_REPAIR_ITEM), // 79
                new Integer(TARGET_TYPE__SKILL_BODY_BUILDING), // 80
                new Integer(TARGET_TYPE__SKILL_MEDITATION), // 81
                new Integer(TARGET_TYPE__SKILL_PERCEPTION), // 82
                new Integer(TARGET_TYPE__SKILL_DIPLOMACY), // 83
                
                //  Unused in MM6?
                new Integer(TARGET_TYPE__SKILL_THIEVERY), // 84

                new Integer(TARGET_TYPE__SKILL_LOCK_PICK), // 85
                new Integer(TARGET_TYPE__SKILL_LEARNING), // 86

                new Integer(TARGET_TYPE__CONDITION_CURSED), // 87
                new Integer(TARGET_TYPE__CONDITION_WEAK), // 88
                new Integer(TARGET_TYPE__CONDITION_ASLEEP), // 89
                new Integer(TARGET_TYPE__CONDITION_AFRAID), // 90
                new Integer(TARGET_TYPE__CONDITION_DRUNK), // 91
                new Integer(TARGET_TYPE__CONDITION_INSANE), // 92
                new Integer(TARGET_TYPE__CONDITION_POISON1), // 93 - green
                new Integer(TARGET_TYPE__CONDITION_DISEASE1), // 94 - green
                new Integer(TARGET_TYPE__CONDITION_POISON2), // 95 - yellow
                new Integer(TARGET_TYPE__CONDITION_DISEASE2), // 96 - yellow
                new Integer(TARGET_TYPE__CONDITION_POISON3), // 97 - red
                new Integer(TARGET_TYPE__CONDITION_DISEASE3), // 98 - red
                new Integer(TARGET_TYPE__CONDITION_PARALYZED), // 99
                new Integer(TARGET_TYPE__CONDITION_UNCONSCIOUS), // 100
                new Integer(TARGET_TYPE__CONDITION_DEAD), // 101
                new Integer(TARGET_TYPE__CONDITION_STONE), // 102
                new Integer(TARGET_TYPE__CONDITION_ERADICATED), // 103
                
                // guesswork
                new Integer(TARGET_TYPE__CONDITION_GOOD), // 104
     
                // pure speculation
                new Integer(TARGET_TYPE__DUNGEON_BYTE_1), // 105
                new Integer(TARGET_TYPE__DUNGEON_BYTE_2), // 106
                new Integer(TARGET_TYPE__DUNGEON_BYTE_3), // 107
                new Integer(TARGET_TYPE__DUNGEON_BYTE_4), // 108
                new Integer(TARGET_TYPE__DUNGEON_BYTE_5), // 109
                new Integer(TARGET_TYPE__DUNGEON_BYTE_6), // 110
                new Integer(TARGET_TYPE__DUNGEON_BYTE_7), // 111
                new Integer(TARGET_TYPE__DUNGEON_BYTE_8), // 112
                new Integer(TARGET_TYPE__DUNGEON_BYTE_9), // 113
                new Integer(TARGET_TYPE__DUNGEON_BYTE_10), // 114
                new Integer(TARGET_TYPE__DUNGEON_BYTE_11), // 115
                new Integer(TARGET_TYPE__DUNGEON_BYTE_12), // 116
                new Integer(TARGET_TYPE__DUNGEON_BYTE_13), // 117
                new Integer(TARGET_TYPE__DUNGEON_BYTE_14), // 118
                new Integer(TARGET_TYPE__DUNGEON_BYTE_15), // 119
                new Integer(TARGET_TYPE__DUNGEON_BYTE_16), // 120
                new Integer(TARGET_TYPE__DUNGEON_BYTE_17), // 121
                new Integer(TARGET_TYPE__DUNGEON_BYTE_18), // 122
                new Integer(TARGET_TYPE__DUNGEON_BYTE_19), // 123
                new Integer(TARGET_TYPE__DUNGEON_BYTE_20), // 124
                new Integer(TARGET_TYPE__DUNGEON_BYTE_21), // 125
                new Integer(TARGET_TYPE__DUNGEON_BYTE_22), // 126
                new Integer(TARGET_TYPE__DUNGEON_BYTE_23), // 127
                new Integer(TARGET_TYPE__DUNGEON_BYTE_24), // 128
                new Integer(TARGET_TYPE__DUNGEON_BYTE_25), // 129
                new Integer(TARGET_TYPE__DUNGEON_BYTE_26), // 130
                new Integer(TARGET_TYPE__DUNGEON_BYTE_27), // 131
                new Integer(TARGET_TYPE__DUNGEON_BYTE_28), // 132
                new Integer(TARGET_TYPE__DUNGEON_BYTE_29), // 133
                new Integer(TARGET_TYPE__DUNGEON_BYTE_30), // 134
                new Integer(TARGET_TYPE__DUNGEON_BYTE_31), // 135
                new Integer(TARGET_TYPE__DUNGEON_BYTE_32), // 136
                new Integer(TARGET_TYPE__DUNGEON_BYTE_33), // 137
                new Integer(TARGET_TYPE__DUNGEON_BYTE_34), // 138
                new Integer(TARGET_TYPE__DUNGEON_BYTE_35), // 139
                new Integer(TARGET_TYPE__DUNGEON_BYTE_36), // 140
                new Integer(TARGET_TYPE__DUNGEON_BYTE_37), // 141
                new Integer(TARGET_TYPE__DUNGEON_BYTE_38), // 142
                new Integer(TARGET_TYPE__DUNGEON_BYTE_39), // 143
                new Integer(TARGET_TYPE__DUNGEON_BYTE_40), // 144
                new Integer(TARGET_TYPE__DUNGEON_BYTE_41), // 145
                new Integer(TARGET_TYPE__DUNGEON_BYTE_42), // 146
                new Integer(TARGET_TYPE__DUNGEON_BYTE_43), // 147
                new Integer(TARGET_TYPE__DUNGEON_BYTE_44), // 148
                new Integer(TARGET_TYPE__DUNGEON_BYTE_45), // 149
                new Integer(TARGET_TYPE__DUNGEON_BYTE_46), // 150
                new Integer(TARGET_TYPE__DUNGEON_BYTE_47), // 151
                new Integer(TARGET_TYPE__DUNGEON_BYTE_48), // 152
                new Integer(TARGET_TYPE__DUNGEON_BYTE_49), // 153
                new Integer(TARGET_TYPE__DUNGEON_BYTE_50), // 154
                new Integer(TARGET_TYPE__DUNGEON_BYTE_51), // 155
                new Integer(TARGET_TYPE__DUNGEON_BYTE_52), // 156
                new Integer(TARGET_TYPE__DUNGEON_BYTE_53), // 157
                new Integer(TARGET_TYPE__DUNGEON_BYTE_54), // 158
                new Integer(TARGET_TYPE__DUNGEON_BYTE_55), // 159
                new Integer(TARGET_TYPE__DUNGEON_BYTE_56), // 160
                new Integer(TARGET_TYPE__DUNGEON_BYTE_57), // 161
                new Integer(TARGET_TYPE__DUNGEON_BYTE_58), // 162
                new Integer(TARGET_TYPE__DUNGEON_BYTE_59), // 163
                new Integer(TARGET_TYPE__DUNGEON_BYTE_60), // 164
                new Integer(TARGET_TYPE__DUNGEON_BYTE_61), // 165
                new Integer(TARGET_TYPE__DUNGEON_BYTE_62), // 166
                new Integer(TARGET_TYPE__DUNGEON_BYTE_63), // 167
                new Integer(TARGET_TYPE__DUNGEON_BYTE_64), // 168
                new Integer(TARGET_TYPE__DUNGEON_BYTE_65), // 169
                new Integer(TARGET_TYPE__DUNGEON_BYTE_66), // 170
                new Integer(TARGET_TYPE__DUNGEON_BYTE_67), // 171
                new Integer(TARGET_TYPE__DUNGEON_BYTE_68), // 172
                new Integer(TARGET_TYPE__DUNGEON_BYTE_69), // 173
                new Integer(TARGET_TYPE__DUNGEON_BYTE_70), // 174
                new Integer(TARGET_TYPE__DUNGEON_BYTE_71), // 175
                new Integer(TARGET_TYPE__DUNGEON_BYTE_72), // 176
                new Integer(TARGET_TYPE__DUNGEON_BYTE_73), // 177
                new Integer(TARGET_TYPE__DUNGEON_BYTE_74), // 178
                new Integer(TARGET_TYPE__DUNGEON_BYTE_75), // 179
                new Integer(TARGET_TYPE__DUNGEON_BYTE_76), // 180
                new Integer(TARGET_TYPE__DUNGEON_BYTE_77), // 181
                new Integer(TARGET_TYPE__DUNGEON_BYTE_78), // 182
                new Integer(TARGET_TYPE__DUNGEON_BYTE_79), // 183
                new Integer(TARGET_TYPE__DUNGEON_BYTE_80), // 184
                new Integer(TARGET_TYPE__DUNGEON_BYTE_81), // 185
                new Integer(TARGET_TYPE__DUNGEON_BYTE_82), // 186
                new Integer(TARGET_TYPE__DUNGEON_BYTE_83), // 187
                new Integer(TARGET_TYPE__DUNGEON_BYTE_84), // 188
                new Integer(TARGET_TYPE__DUNGEON_BYTE_85), // 189
                new Integer(TARGET_TYPE__DUNGEON_BYTE_86), // 190
                new Integer(TARGET_TYPE__DUNGEON_BYTE_87), // 191
                new Integer(TARGET_TYPE__DUNGEON_BYTE_88), // 192
                new Integer(TARGET_TYPE__DUNGEON_BYTE_89), // 193
                new Integer(TARGET_TYPE__DUNGEON_BYTE_90), // 194
                new Integer(TARGET_TYPE__DUNGEON_BYTE_91), // 195
                new Integer(TARGET_TYPE__DUNGEON_BYTE_92), // 196
                new Integer(TARGET_TYPE__DUNGEON_BYTE_93), // 197
                new Integer(TARGET_TYPE__DUNGEON_BYTE_94), // 198
                new Integer(TARGET_TYPE__DUNGEON_BYTE_95), // 199
                new Integer(TARGET_TYPE__DUNGEON_BYTE_96), // 200
                new Integer(TARGET_TYPE__DUNGEON_BYTE_97), // 201
                new Integer(TARGET_TYPE__DUNGEON_BYTE_98), // 202
                new Integer(TARGET_TYPE__DUNGEON_BYTE_99), // 203
                new Integer(TARGET_TYPE__DUNGEON_BYTE_100), // 204
                new Integer(TARGET_TYPE__EVENT_AUTONOTE), // 205
                new Integer(TARGET_TYPE__MIGHT_MAX), // 206
                new Integer(TARGET_TYPE__INTELLECT_MAX), // 207
                new Integer(TARGET_TYPE__PERSONALITY_MAX), // 208
                new Integer(TARGET_TYPE__ENDURANCE_MAX), // 209
                new Integer(TARGET_TYPE__SPEED_MAX), // 210
                new Integer(TARGET_TYPE__ACCURACY_MAX), // 211
                new Integer(TARGET_TYPE__LUCK_MAX), // 212
                new Integer(TARGET_TYPE__CHARACTER_BIT), // 213
                new Integer(TARGET_TYPE__NPC_IN_PARTY), // 214

                // known
                new Integer(TARGET_TYPE__REPUTATION), // 215
       
                new Integer(TARGET_TYPE__DAYS_PAST_1), // 216
                new Integer(TARGET_TYPE__DAYS_PAST_2), // 217
                new Integer(TARGET_TYPE__DAYS_PAST_3), // 218
                new Integer(TARGET_TYPE__DAYS_PAST_4), // 219
                new Integer(TARGET_TYPE__DAYS_PAST_5), // 220
                new Integer(TARGET_TYPE__DAYS_PAST_6), // 221
                new Integer(TARGET_TYPE__PARTY_FLYING), // 222
                new Integer(TARGET_TYPE__NPC_PROFESSION_IN_PARTY), // 223
                new Integer(TARGET_TYPE__CIRCUS_TOTAL), // 224
                new Integer(TARGET_TYPE__SKILL_POINTS), // 225
                
                // known
                new Integer(TARGET_TYPE__MONTH_OF_YEAR), // 226

                // more speculation
                new Integer(TARGET_TYPE__TIMER1), // 227
                new Integer(TARGET_TYPE__TIMER2), // 228
                new Integer(TARGET_TYPE__TIMER3), // 229
                new Integer(TARGET_TYPE__TIMER4), // 230
                new Integer(TARGET_TYPE__TIMER5), // 231
                new Integer(TARGET_TYPE__TIMER6), // 232
                new Integer(TARGET_TYPE__TIMER7), // 233
                new Integer(TARGET_TYPE__TIMER8), // 234
                new Integer(TARGET_TYPE__TIMER9), // 235
                new Integer(TARGET_TYPE__TIMER10), // 236
                null, // 237
                null, // 238
                null, // 239
                null, // 240
                null, // 241
                null, // 242
                null, // 243
                // not sure of any of these values or ordering
                new Integer(TARGET_TYPE__MAP_STEAL_DIFFICULTY_TEMP), // 244
                new Integer(TARGET_TYPE__MAP_ON_ALERT), // 245
                new Integer(TARGET_TYPE__BANK_GOLD), // 246
                new Integer(TARGET_TYPE__DEATH), // 247
                new Integer(TARGET_TYPE__BOUNTY), // 248
                new Integer(TARGET_TYPE__PRISON_TERM), // 249
                new Integer(TARGET_TYPE__ARENA_PAGE), // 250
                new Integer(TARGET_TYPE__ARENA_SQUIRE), // 251
                new Integer(TARGET_TYPE__ARENA_KNIGHT), // 252
                new Integer(TARGET_TYPE__ARENA_LORD), // 253
                new Integer(TARGET_TYPE__INVISIBILITY), // 254
                new Integer(TARGET_TYPE__EQUIP) // 255
        };
    }
    
    public String getTargetTypeName(int targetType)
    {
        Integer targetTypeKey = getTargetTypeKey(targetType);
        if (null == targetTypeKey)
        {
            return "UNKNOWN TARGET TYPE 0x" + Integer.toHexString(targetType);
        }

        switch(targetTypeKey.intValue())
        {
		    case TARGET_TYPE__NONE:  return "NONE";
		    case TARGET_TYPE__SEX:  return "SEX";
		    case TARGET_TYPE__CLASS:  return "CLASS";
		    case TARGET_TYPE__HIT_POINTS:  return "HIT_POINTS";
		    case TARGET_TYPE__HIT_POINTS_MAX:  return "HIT_POINTS_MAX";
		    case TARGET_TYPE__SPELL_POINTS:  return "SPELL_POINTS";
		    case TARGET_TYPE__SPELL_POINTS_MAX:  return "SPELL_POINTS_MAX";
		    case TARGET_TYPE__ARMOR_CLASS:  return "ARMOR_CLASS";
		    case TARGET_TYPE__ARMOR_CLASS_TEMP:  return "ARMOR_CLASS_TEMP";
		    case TARGET_TYPE__LEVEL_PERM:  return "LEVEL_PERM";
		    case TARGET_TYPE__LEVEL_TEMP:  return "LEVEL_TEMP";
		    case TARGET_TYPE__AGE:  return "AGE";
		    case TARGET_TYPE__AWARD:  return "AWARD";
		    case TARGET_TYPE__EXPERIENCE:  return "EXPERIENCE";
		    case TARGET_TYPE__RACE:  return "RACE";
		    case TARGET_TYPE__EVENT_SPELL:  return "EVENT_SPELL";
		    case TARGET_TYPE__QUEST_BIT:  return "QUEST_BIT";
		    case TARGET_TYPE__ITEM:  return "ITEM";
		    case TARGET_TYPE__TIME_OF_DAY:  return "TIME_OF_DAY";
		    case TARGET_TYPE__DAY_OF_YEAR:  return "DAY_OF_YEAR";
		    case TARGET_TYPE__DAY_OF_WEEK:  return "DAY_OF_WEEK";
		    case TARGET_TYPE__GOLD:  return "GOLD";
		    case TARGET_TYPE__GOLD_RANDOM:  return "GOLD_RANDOM";
		    case TARGET_TYPE__FOOD:  return "FOOD";
		    case TARGET_TYPE__FOOD_RANDOM:  return "FOOD_RANDOM";
		    case TARGET_TYPE__MIGHT_TEMP:  return "MIGHT_TEMP";
		    case TARGET_TYPE__INTELLECT_TEMP:  return "INTELLECT_TEMP";
		    case TARGET_TYPE__PERSONALITY_TEMP:  return "PERSONALITY_TEMP";
		    case TARGET_TYPE__ENDURANCE_TEMP:  return "ENDURANCE_TEMP";
		    case TARGET_TYPE__SPEED_TEMP:  return "SPEED_TEMP";
		    case TARGET_TYPE__ACCURACY_TEMP:  return "ACCURACY_TEMP";
		    case TARGET_TYPE__LUCK_TEMP:  return "LUCK_TEMP";
		    case TARGET_TYPE__MIGHT_PERM:  return "MIGHT_PERM";
		    case TARGET_TYPE__INTELLECT_PERM:  return "INTELLECT_PERM";
		    case TARGET_TYPE__PERSONALITY_PERM:  return "PERSONALITY_PERM";
		    case TARGET_TYPE__ENDURANCE_PERM:  return "ENDURANCE_PERM";
		    case TARGET_TYPE__SPEED_PERM:  return "SPEED_PERM";
		    case TARGET_TYPE__ACCURACY_PERM:  return "ACCURACY_PERM";
		    case TARGET_TYPE__LUCK_PERM:  return "LUCK_PERM";
		    case TARGET_TYPE__MIGHT_CURRENT:  return "MIGHT_CURRENT";
		    case TARGET_TYPE__INTELLECT_CURRENT:  return "INTELLECT_CURRENT";
		    case TARGET_TYPE__PERSONALITY_CURRENT:  return "PERSONALITY_CURRENT";
		    case TARGET_TYPE__ENDURANCE_CURRENT:  return "ENDURANCE_CURRENT";
		    case TARGET_TYPE__SPEED_CURRENT:  return "SPEED_CURRENT";
		    case TARGET_TYPE__ACCURACY_CURRENT:  return "ACCURACY_CURRENT";
		    case TARGET_TYPE__LUCK_CURRENT:  return "LUCK_CURRENT";
		    case TARGET_TYPE__FIRE_RESISTANCE_PERM:  return "FIRE_RESISTANCE_PERM";
		    case TARGET_TYPE__ELECTRICITY_RESISTANCE_PERM:  return "ELECTRICITY_RESISTANCE_PERM";
		    case TARGET_TYPE__COLD_RESISTANCE_PERM:  return "COLD_RESISTANCE_PERM";
		    case TARGET_TYPE__POISON_RESISTANCE_PERM:  return "POISON_RESISTANCE_PERM";
		    case TARGET_TYPE__MAGIC_RESISTANCE_PERM:  return "MAGIC_RESISTANCE_PERM";
		    case TARGET_TYPE__FIRE_RESISTANCE_TEMP:  return "FIRE_RESISTANCE_TEMP";
		    case TARGET_TYPE__ELECTRICITY_RESISTANCE_TEMP:  return "ELECTRICITY_RESISTANCE_TEMP";
		    case TARGET_TYPE__COLD_RESISTANCE_TEMP:  return "COLD_RESISTANCE_TEMP";
		    case TARGET_TYPE__POISON_RESISTANCE_TEMP:  return "POISON_RESISTANCE_TEMP";
		    case TARGET_TYPE__MAGIC_RESISTANCE_TEMP:  return "MAGIC_RESISTANCE_TEMP";
		    case TARGET_TYPE__SKILL_STAFF:  return "SKILL_STAFF";
		    case TARGET_TYPE__SKILL_SWORD:  return "SKILL_SWORD";
		    case TARGET_TYPE__SKILL_DAGGER:  return "SKILL_DAGGER";
		    case TARGET_TYPE__SKILL_AXE:  return "SKILL_AXE";
		    case TARGET_TYPE__SKILL_SPEAR:  return "SKILL_SPEAR";
		    case TARGET_TYPE__SKILL_BOW:  return "SKILL_BOW";
		    case TARGET_TYPE__SKILL_MACE:  return "SKILL_MACE";
		    case TARGET_TYPE__SKILL_BLASTER:  return "SKILL_BLASTER";
		    case TARGET_TYPE__SKILL_SHIELD:  return "SKILL_SHIELD";
		    case TARGET_TYPE__SKILL_LEATHER:  return "SKILL_LEATHER";
		    case TARGET_TYPE__SKILL_CHAIN:  return "SKILL_CHAIN";
		    case TARGET_TYPE__SKILL_PLATE:  return "SKILL_PLATE";
		    case TARGET_TYPE__SKILL_FIRE_MAGIC:  return "SKILL_FIRE_MAGIC";
		    case TARGET_TYPE__SKILL_AIR_MAGIC:  return "SKILL_AIR_MAGIC";
		    case TARGET_TYPE__SKILL_WATER_MAGIC:  return "SKILL_WATER_MAGIC";
		    case TARGET_TYPE__SKILL_EARTH_MAGIC:  return "SKILL_EARTH_MAGIC";
		    case TARGET_TYPE__SKILL_SPIRIT_MAGIC:  return "SKILL_SPIRIT_MAGIC";
		    case TARGET_TYPE__SKILL_MIND_MAGIC:  return "SKILL_MIND_MAGIC";
		    case TARGET_TYPE__SKILL_BODY_MAGIC:  return "SKILL_BODY_MAGIC";
		    case TARGET_TYPE__SKILL_LIGHT_MAGIC:  return "SKILL_LIGHT_MAGIC";
		    case TARGET_TYPE__SKILL_DARK_MAGIC:  return "SKILL_DARK_MAGIC";
		    case TARGET_TYPE__SKILL_IDENTIFY_ITEM:  return "SKILL_IDENTIFY_ITEM";
		    case TARGET_TYPE__SKILL_MERCHANT:  return "SKILL_MERCHANT";
		    case TARGET_TYPE__SKILL_REPAIR_ITEM:  return "SKILL_REPAIR_ITEM";
		    case TARGET_TYPE__SKILL_BODY_BUILDING:  return "SKILL_BODY_BUILDING";
		    case TARGET_TYPE__SKILL_MEDITATION:  return "SKILL_MEDITATION";
		    case TARGET_TYPE__SKILL_PERCEPTION:  return "SKILL_PERCEPTION";
		    case TARGET_TYPE__SKILL_DIPLOMACY:  return "SKILL_DIPLOMACY";
		    case TARGET_TYPE__SKILL_THIEVERY:  return "SKILL_THIEVERY";
		    case TARGET_TYPE__SKILL_LOCK_PICK:  return "SKILL_LOCK_PICK";
		    case TARGET_TYPE__SKILL_LEARNING:  return "SKILL_LEARNING";
		    case TARGET_TYPE__CONDITION_CURSED:  return "CONDITION_CURSED";
		    case TARGET_TYPE__CONDITION_WEAK:  return "CONDITION_WEAK";
		    case TARGET_TYPE__CONDITION_ASLEEP:  return "CONDITION_ASLEEP";
		    case TARGET_TYPE__CONDITION_AFRAID:  return "CONDITION_AFRAID";
		    case TARGET_TYPE__CONDITION_DRUNK:  return "CONDITION_DRUNK";
		    case TARGET_TYPE__CONDITION_INSANE:  return "CONDITION_INSANE";
		    case TARGET_TYPE__CONDITION_POISON1:  return "CONDITION_POISON1"; // green
		    case TARGET_TYPE__CONDITION_DISEASE1:  return "CONDITION_DISEASE1"; // green
		    case TARGET_TYPE__CONDITION_POISON2:  return "CONDITION_POISON2"; // yellow
		    case TARGET_TYPE__CONDITION_DISEASE2:  return "CONDITION_DISEASE2"; // yellow
		    case TARGET_TYPE__CONDITION_POISON3:  return "CONDITION_POISON3"; // red
		    case TARGET_TYPE__CONDITION_DISEASE3:  return "CONDITION_DISEASE3"; // red
		    case TARGET_TYPE__CONDITION_PARALYZED:  return "CONDITION_PARALYZED";
		    case TARGET_TYPE__CONDITION_UNCONSCIOUS:  return "CONDITION_UNCONSCIOUS";
		    case TARGET_TYPE__CONDITION_DEAD:  return "CONDITION_DEAD";
		    case TARGET_TYPE__CONDITION_STONE:  return "CONDITION_STONE";
		    case TARGET_TYPE__CONDITION_ERADICATED:  return "CONDITION_ERADICATED";
		    case TARGET_TYPE__CONDITION_GOOD:  return "CONDITION_GOOD";
		    case TARGET_TYPE__DUNGEON_BYTE_1:  return "DUNGEON_BYTE_1";
		    case TARGET_TYPE__DUNGEON_BYTE_2:  return "DUNGEON_BYTE_2";
		    case TARGET_TYPE__DUNGEON_BYTE_3:  return "DUNGEON_BYTE_3";
		    case TARGET_TYPE__DUNGEON_BYTE_4:  return "DUNGEON_BYTE_4";
		    case TARGET_TYPE__DUNGEON_BYTE_5:  return "DUNGEON_BYTE_5";
		    case TARGET_TYPE__DUNGEON_BYTE_6:  return "DUNGEON_BYTE_6";
		    case TARGET_TYPE__DUNGEON_BYTE_7:  return "DUNGEON_BYTE_7";
		    case TARGET_TYPE__DUNGEON_BYTE_8:  return "DUNGEON_BYTE_8";
		    case TARGET_TYPE__DUNGEON_BYTE_9:  return "DUNGEON_BYTE_9";
		    case TARGET_TYPE__DUNGEON_BYTE_10:  return "DUNGEON_BYTE_10";
		    case TARGET_TYPE__DUNGEON_BYTE_11:  return "DUNGEON_BYTE_11";
		    case TARGET_TYPE__DUNGEON_BYTE_12:  return "DUNGEON_BYTE_12";
		    case TARGET_TYPE__DUNGEON_BYTE_13:  return "DUNGEON_BYTE_13";
		    case TARGET_TYPE__DUNGEON_BYTE_14:  return "DUNGEON_BYTE_14";
		    case TARGET_TYPE__DUNGEON_BYTE_15:  return "DUNGEON_BYTE_15";
		    case TARGET_TYPE__DUNGEON_BYTE_16:  return "DUNGEON_BYTE_16";
		    case TARGET_TYPE__DUNGEON_BYTE_17:  return "DUNGEON_BYTE_17";
		    case TARGET_TYPE__DUNGEON_BYTE_18:  return "DUNGEON_BYTE_18";
		    case TARGET_TYPE__DUNGEON_BYTE_19:  return "DUNGEON_BYTE_19";
		    case TARGET_TYPE__DUNGEON_BYTE_20:  return "DUNGEON_BYTE_20";
		    case TARGET_TYPE__DUNGEON_BYTE_21:  return "DUNGEON_BYTE_21";
		    case TARGET_TYPE__DUNGEON_BYTE_22:  return "DUNGEON_BYTE_22";
		    case TARGET_TYPE__DUNGEON_BYTE_23:  return "DUNGEON_BYTE_23";
		    case TARGET_TYPE__DUNGEON_BYTE_24:  return "DUNGEON_BYTE_24";
		    case TARGET_TYPE__DUNGEON_BYTE_25:  return "DUNGEON_BYTE_25";
		    case TARGET_TYPE__DUNGEON_BYTE_26:  return "DUNGEON_BYTE_26";
		    case TARGET_TYPE__DUNGEON_BYTE_27:  return "DUNGEON_BYTE_27";
		    case TARGET_TYPE__DUNGEON_BYTE_28:  return "DUNGEON_BYTE_28";
		    case TARGET_TYPE__DUNGEON_BYTE_29:  return "DUNGEON_BYTE_29";
		    case TARGET_TYPE__DUNGEON_BYTE_30:  return "DUNGEON_BYTE_30";
		    case TARGET_TYPE__DUNGEON_BYTE_31:  return "DUNGEON_BYTE_31";
		    case TARGET_TYPE__DUNGEON_BYTE_32:  return "DUNGEON_BYTE_32";
		    case TARGET_TYPE__DUNGEON_BYTE_33:  return "DUNGEON_BYTE_33";
		    case TARGET_TYPE__DUNGEON_BYTE_34:  return "DUNGEON_BYTE_34";
		    case TARGET_TYPE__DUNGEON_BYTE_35:  return "DUNGEON_BYTE_35";
		    case TARGET_TYPE__DUNGEON_BYTE_36:  return "DUNGEON_BYTE_36";
		    case TARGET_TYPE__DUNGEON_BYTE_37:  return "DUNGEON_BYTE_37";
		    case TARGET_TYPE__DUNGEON_BYTE_38:  return "DUNGEON_BYTE_38";
		    case TARGET_TYPE__DUNGEON_BYTE_39:  return "DUNGEON_BYTE_39";
		    case TARGET_TYPE__DUNGEON_BYTE_40:  return "DUNGEON_BYTE_40";
		    case TARGET_TYPE__DUNGEON_BYTE_41:  return "DUNGEON_BYTE_41";
		    case TARGET_TYPE__DUNGEON_BYTE_42:  return "DUNGEON_BYTE_42";
		    case TARGET_TYPE__DUNGEON_BYTE_43:  return "DUNGEON_BYTE_43";
		    case TARGET_TYPE__DUNGEON_BYTE_44:  return "DUNGEON_BYTE_44";
		    case TARGET_TYPE__DUNGEON_BYTE_45:  return "DUNGEON_BYTE_45";
		    case TARGET_TYPE__DUNGEON_BYTE_46:  return "DUNGEON_BYTE_46";
		    case TARGET_TYPE__DUNGEON_BYTE_47:  return "DUNGEON_BYTE_47";
		    case TARGET_TYPE__DUNGEON_BYTE_48:  return "DUNGEON_BYTE_48";
		    case TARGET_TYPE__DUNGEON_BYTE_49:  return "DUNGEON_BYTE_49";
		    case TARGET_TYPE__DUNGEON_BYTE_50:  return "DUNGEON_BYTE_50";
		    case TARGET_TYPE__DUNGEON_BYTE_51:  return "DUNGEON_BYTE_51";
		    case TARGET_TYPE__DUNGEON_BYTE_52:  return "DUNGEON_BYTE_52";
		    case TARGET_TYPE__DUNGEON_BYTE_53:  return "DUNGEON_BYTE_53";
		    case TARGET_TYPE__DUNGEON_BYTE_54:  return "DUNGEON_BYTE_54";
		    case TARGET_TYPE__DUNGEON_BYTE_55:  return "DUNGEON_BYTE_55";
		    case TARGET_TYPE__DUNGEON_BYTE_56:  return "DUNGEON_BYTE_56";
		    case TARGET_TYPE__DUNGEON_BYTE_57:  return "DUNGEON_BYTE_57";
		    case TARGET_TYPE__DUNGEON_BYTE_58:  return "DUNGEON_BYTE_58";
		    case TARGET_TYPE__DUNGEON_BYTE_59:  return "DUNGEON_BYTE_59";
		    case TARGET_TYPE__DUNGEON_BYTE_60:  return "DUNGEON_BYTE_60";
		    case TARGET_TYPE__DUNGEON_BYTE_61:  return "DUNGEON_BYTE_61";
		    case TARGET_TYPE__DUNGEON_BYTE_62:  return "DUNGEON_BYTE_62";
		    case TARGET_TYPE__DUNGEON_BYTE_63:  return "DUNGEON_BYTE_63";
		    case TARGET_TYPE__DUNGEON_BYTE_64:  return "DUNGEON_BYTE_64";
		    case TARGET_TYPE__DUNGEON_BYTE_65:  return "DUNGEON_BYTE_65";
		    case TARGET_TYPE__DUNGEON_BYTE_66:  return "DUNGEON_BYTE_66";
		    case TARGET_TYPE__DUNGEON_BYTE_67:  return "DUNGEON_BYTE_67";
		    case TARGET_TYPE__DUNGEON_BYTE_68:  return "DUNGEON_BYTE_68";
		    case TARGET_TYPE__DUNGEON_BYTE_69:  return "DUNGEON_BYTE_69";
		    case TARGET_TYPE__DUNGEON_BYTE_70:  return "DUNGEON_BYTE_70";
		    case TARGET_TYPE__DUNGEON_BYTE_71:  return "DUNGEON_BYTE_71";
		    case TARGET_TYPE__DUNGEON_BYTE_72:  return "DUNGEON_BYTE_72";
		    case TARGET_TYPE__DUNGEON_BYTE_73:  return "DUNGEON_BYTE_73";
		    case TARGET_TYPE__DUNGEON_BYTE_74:  return "DUNGEON_BYTE_74";
		    case TARGET_TYPE__DUNGEON_BYTE_75:  return "DUNGEON_BYTE_75";
		    case TARGET_TYPE__DUNGEON_BYTE_76:  return "DUNGEON_BYTE_76";
		    case TARGET_TYPE__DUNGEON_BYTE_77:  return "DUNGEON_BYTE_77";
		    case TARGET_TYPE__DUNGEON_BYTE_78:  return "DUNGEON_BYTE_78";
		    case TARGET_TYPE__DUNGEON_BYTE_79:  return "DUNGEON_BYTE_79";
		    case TARGET_TYPE__DUNGEON_BYTE_80:  return "DUNGEON_BYTE_80";
		    case TARGET_TYPE__DUNGEON_BYTE_81:  return "DUNGEON_BYTE_81";
		    case TARGET_TYPE__DUNGEON_BYTE_82:  return "DUNGEON_BYTE_82";
		    case TARGET_TYPE__DUNGEON_BYTE_83:  return "DUNGEON_BYTE_83";
		    case TARGET_TYPE__DUNGEON_BYTE_84:  return "DUNGEON_BYTE_84";
		    case TARGET_TYPE__DUNGEON_BYTE_85:  return "DUNGEON_BYTE_85";
		    case TARGET_TYPE__DUNGEON_BYTE_86:  return "DUNGEON_BYTE_86";
		    case TARGET_TYPE__DUNGEON_BYTE_87:  return "DUNGEON_BYTE_87";
		    case TARGET_TYPE__DUNGEON_BYTE_88:  return "DUNGEON_BYTE_88";
		    case TARGET_TYPE__DUNGEON_BYTE_89:  return "DUNGEON_BYTE_89";
		    case TARGET_TYPE__DUNGEON_BYTE_90:  return "DUNGEON_BYTE_90";
		    case TARGET_TYPE__DUNGEON_BYTE_91:  return "DUNGEON_BYTE_91";
		    case TARGET_TYPE__DUNGEON_BYTE_92:  return "DUNGEON_BYTE_92";
		    case TARGET_TYPE__DUNGEON_BYTE_93:  return "DUNGEON_BYTE_93";
		    case TARGET_TYPE__DUNGEON_BYTE_94:  return "DUNGEON_BYTE_94";
		    case TARGET_TYPE__DUNGEON_BYTE_95:  return "DUNGEON_BYTE_95";
		    case TARGET_TYPE__DUNGEON_BYTE_96:  return "DUNGEON_BYTE_96";
		    case TARGET_TYPE__DUNGEON_BYTE_97:  return "DUNGEON_BYTE_97";
		    case TARGET_TYPE__DUNGEON_BYTE_98:  return "DUNGEON_BYTE_98";
		    case TARGET_TYPE__DUNGEON_BYTE_99:  return "DUNGEON_BYTE_99";
		    case TARGET_TYPE__DUNGEON_BYTE_100:  return "DUNGEON_BYTE_100";
		    case TARGET_TYPE__EVENT_AUTONOTE:  return "EVENT_AUTONOTE";
		    case TARGET_TYPE__MIGHT_MAX:  return "MIGHT_MAX";
		    case TARGET_TYPE__INTELLECT_MAX:  return "INTELLECT_MAX";
		    case TARGET_TYPE__PERSONALITY_MAX:  return "PERSONALITY_MAX";
		    case TARGET_TYPE__ENDURANCE_MAX:  return "ENDURANCE_MAX";
		    case TARGET_TYPE__SPEED_MAX:  return "SPEED_MAX";
		    case TARGET_TYPE__ACCURACY_MAX:  return "ACCURACY_MAX";
		    case TARGET_TYPE__LUCK_MAX:  return "LUCK_MAX";
		    case TARGET_TYPE__CHARACTER_BIT:  return "CHARACTER_BIT";
		    case TARGET_TYPE__NPC_IN_PARTY:  return "NPC_IN_PARTY";
		    case TARGET_TYPE__REPUTATION:  return "REPUTATION";
		    case TARGET_TYPE__DAYS_PAST_1:  return "DAYS_PAST_1";
		    case TARGET_TYPE__DAYS_PAST_2:  return "DAYS_PAST_2";
		    case TARGET_TYPE__DAYS_PAST_3:  return "DAYS_PAST_3";
		    case TARGET_TYPE__DAYS_PAST_4:  return "DAYS_PAST_4";
		    case TARGET_TYPE__DAYS_PAST_5:  return "DAYS_PAST_5";
		    case TARGET_TYPE__DAYS_PAST_6:  return "DAYS_PAST_6";
		    case TARGET_TYPE__PARTY_FLYING:  return "PARTY_FLYING";
		    case TARGET_TYPE__NPC_PROFESSION_IN_PARTY:  return "NPC_PROFESSION_IN_PARTY";
		    case TARGET_TYPE__CIRCUS_TOTAL:  return "CIRCUS_TOTAL";
		    case TARGET_TYPE__SKILL_POINTS:  return "SKILL_POINTS";
		    case TARGET_TYPE__MONTH_OF_YEAR:  return "MONTH_OF_YEAR";
		    case TARGET_TYPE__TIMER1:  return "TIMER1";
		    case TARGET_TYPE__TIMER2:  return "TIMER2";
		    case TARGET_TYPE__TIMER3:  return "TIMER3";
		    case TARGET_TYPE__TIMER4:  return "TIMER4";
		    case TARGET_TYPE__TIMER5:  return "TIMER5";
		    case TARGET_TYPE__TIMER6:  return "TIMER6";
		    case TARGET_TYPE__TIMER7:  return "TIMER7";
		    case TARGET_TYPE__TIMER8:  return "TIMER8";
		    case TARGET_TYPE__TIMER9:  return "TIMER9";
		    case TARGET_TYPE__TIMER10:  return "TIMER10";
		    case TARGET_TYPE__MAP_STEAL_DIFFICULTY_TEMP:  return "MAP_STEAL_DIFFICULTY_TEMP";
		    case TARGET_TYPE__MAP_ON_ALERT:  return "MAP_ON_ALERT";
		    case TARGET_TYPE__BANK_GOLD:  return "BANK_GOLD";
		    case TARGET_TYPE__DEATH:  return "DEATH";
		    case TARGET_TYPE__BOUNTY:  return "BOUNTY";
		    case TARGET_TYPE__PRISON_TERM:  return "PRISON_TERM";
		    case TARGET_TYPE__ARENA_PAGE:  return "ARENA_PAGE";
		    case TARGET_TYPE__ARENA_SQUIRE:  return "ARENA_SQUIRE";
		    case TARGET_TYPE__ARENA_KNIGHT:  return "ARENA_KNIGHT";
		    case TARGET_TYPE__ARENA_LORD:  return "ARENA_LORD";
		    case TARGET_TYPE__INVISIBILITY:  return "INVISIBILITY";
            default:
                return "UNKNOWN TARGET TYPE 0x" + Integer.toHexString(targetType);
        }
    }

    // Hardcoded in MM6.exe file
    // These icon names are contained in icon.lod
    protected boolean initializedMiniIconNameChoiceData = false;
    protected String miniIconNameChoiceArray[] = null;
    protected void initializeMiniIconNameChoiceData()
    {
        miniIconNameChoiceArray = new String[] {
                "NO SYMBOL", // 0
                "castle", // 1
                "dungeon", // 2
                "idoor", // 3
                "isecdoor", // 4
                "istairdn", // 5
                "istairup", // 6
                "itrap", // 7
                "outside" // 8
        };
        
        initializedMiniIconNameChoiceData = true;
    }

    protected boolean initializedPartyMemberChoiceData = false;
    protected String partyMemberChoiceArray[] = null;
    protected void initializePartyMemberChoiceData()
    {
        partyMemberChoiceArray = new String[] {
                "outer-left character", // 0
                "inner-left character", // 1
                "inner-right character", // 2
                "outer-right character", // 3
                "current character", // 4
                "any/all characters", // 5
                "random character" // 6
        };
        
        initializedPartyMemberChoiceData = true;
    }

    static public final int MONSTER_ATTRIBUTE_Visible				= 0x00000008;
    static public final int MONSTER_ATTRIBUTE_FireDamage			= 0x00000010;    // Monster has suffered fire damage
    static public final int MONSTER_ATTRIBUTE_ColdDamage			= 0x00000020;    // Monster has suffered cold damage
    static public final int MONSTER_ATTRIBUTE_PoisonDamage		= 0x00000040;    // Monster has suffered poison damage
    static public final int MONSTER_ATTRIBUTE_InTurnStack			= 0x00000080;    // Monster is currently in the turn stack
    static public final int MONSTER_ATTRIBUTE_BluntDamage			= 0x00000100;    // Monster has suffered blunt damage
    static public final int MONSTER_ATTRIBUTE_SlashDamage			= 0x00000200;    // Monster has suffered slash damage
    static public final int MONSTER_ATTRIBUTE_Active				= 0x00000400;    // Monster is active for all AI routines.
    static public final int MONSTER_ATTRIBUTE_PunctureDamage		= 0x00000800;    // Monster has suffered puncture damage
    static public final int MONSTER_ATTRIBUTE_EnergyDamage		= 0x00001000;    // Monster has suffered energy damage
    static public final int MONSTER_ATTRIBUTE_MagicDamage			= 0x00002000;    // Monster has suffered magic damage
    static public final int MONSTER_ATTRIBUTE_On					= 0x00004000;    // Monster is a candidate for 'Active State'
    static public final int MONSTER_ATTRIBUTE_LOS					= 0x00008000;    // Monster has LOS to player
    static public final int MONSTER_ATTRIBUTE_Disabled			= 0x00010000;    // Monster has been disabled
    static public final int MONSTER_ATTRIBUTE_Berserk				= 0x00020000;    // Monster will never flee, overrides normal AI conditions
    static public final int MONSTER_ATTRIBUTE_ForceMove			= 0x00040000;    // Monster will have to ignore attack next round
    static public final int MONSTER_ATTRIBUTE_HatesParty			= 0x00080000;	  // Party has angered this monster, overrides hostility chart!
    static public final int MONSTER_ATTRIBUTE_OnAlertMap			= 0x00100000;    // Monster is only on the alert map
    static public final int MONSTER_ATTRIBUTE_HasStopped			= 0x00200000;	  // Monster has come to a stop
    static public final int MONSTER_ATTRIBUTE_OnSchedule			= 0x00400000;	  // Monster has a schedule
    static public final int MONSTER_ATTRIBUTE_GeneratedTreasure	= 0x00800000;	  // Monster has generated his treasure and stored it in his Pack slots
    static public final int MONSTER_ATTRIBUTE_ShowAsHostile		= 0x01000000;    // Show Monster as hostile to party on map!

    public static final int TREASURE_TYPE_WEAPON = 20;
    public static final int TREASURE_TYPE_ARMOR = 21;
    public static final int TREASURE_TYPE_MISC = 22;
    public static final int TREASURE_TYPE_SWORD = 23;
    public static final int TREASURE_TYPE_DAGGER = 24;
    public static final int TREASURE_TYPE_AXE = 25;
    public static final int TREASURE_TYPE_SPEAR = 26;
    public static final int TREASURE_TYPE_BOW = 27;
    public static final int TREASURE_TYPE_MACE = 28;
    public static final int TREASURE_TYPE_CLUB = 29;
    public static final int TREASURE_TYPE_STAFF = 30;
    public static final int TREASURE_TYPE_LEATHER	 = 31;
    public static final int TREASURE_TYPE_CHAIN = 32;
    public static final int TREASURE_TYPE_PLATE = 33;
    public static final int TREASURE_TYPE_SHIELD = 34;
    public static final int TREASURE_TYPE_HELM = 35;
    public static final int TREASURE_TYPE_BELT = 36;
    public static final int TREASURE_TYPE_CAPE = 37;
    public static final int TREASURE_TYPE_GAUNTLETS = 38;
    public static final int TREASURE_TYPE_BOOTS = 39;
    public static final int TREASURE_TYPE_RING = 40;
    public static final int TREASURE_TYPE_AMULET = 41;
    public static final int TREASURE_TYPE_WAND = 42;
    public static final int TREASURE_TYPE_SCROLL = 43;
    public static final int TREASURE_TYPE_POTION = 44;
    public static final int TREASURE_TYPE_REAGENT	 = 45;
    public static final int TREASURE_TYPE_GEM	 = 46;
    
    public static final int TREASURE_TYPE_GOLD = 50;

    // mm8
    public static final int TREASURE_TYPE_ORE = 47;

    // mm6
    public static final int FACET_ATTRIBUTE_TYPE_Portal = 1;
    public static final int FACET_ATTRIBUTE_TYPE_UNKNOWN_2 = 2;
    public static final int FACET_ATTRIBUTE_TYPE_UNKNOWN_3 = 3;
    public static final int FACET_ATTRIBUTE_TYPE_AlignTop = 4;
    public static final int FACET_ATTRIBUTE_TYPE_water = 5;
    public static final int FACET_ATTRIBUTE_TYPE_UNKNOWN_6 = 6;
    public static final int FACET_ATTRIBUTE_TYPE_UNKNOWN_7 = 7;
    public static final int FACET_ATTRIBUTE_TYPE_drawn = 8;
    public static final int FACET_ATTRIBUTE_TYPE_intxy = 9;
    public static final int FACET_ATTRIBUTE_TYPE_intxz = 10;
    public static final int FACET_ATTRIBUTE_TYPE_intyz = 11;
    public static final int FACET_ATTRIBUTE_TYPE_UNKNOWN_12 = 12;
    public static final int FACET_ATTRIBUTE_TYPE_AlignLeft = 13;
    public static final int FACET_ATTRIBUTE_TYPE_Invisible = 14;
    public static final int FACET_ATTRIBUTE_TYPE_TFT = 15;
    public static final int FACET_ATTRIBUTE_TYPE_AlignRight = 16;
    public static final int FACET_ATTRIBUTE_TYPE_UNKNOWN_17 = 17;
    public static final int FACET_ATTRIBUTE_TYPE_AlignBottom = 18;
    public static final int FACET_ATTRIBUTE_TYPE_Peg = 19;
    public static final int FACET_ATTRIBUTE_TYPE_NoSeed = 20;
    public static final int FACET_ATTRIBUTE_TYPE_InfoEvent = 21;
    public static final int FACET_ATTRIBUTE_TYPE_BlockMonster = 22;
    public static final int FACET_ATTRIBUTE_TYPE_Sky = 23;
    public static final int FACET_ATTRIBUTE_TYPE_MirrorU = 24;
    public static final int FACET_ATTRIBUTE_TYPE_MirrorV = 25;
    public static final int FACET_ATTRIBUTE_TYPE_ClickTr = 26;
    public static final int FACET_ATTRIBUTE_TYPE_PlayTr = 27;
    public static final int FACET_ATTRIBUTE_TYPE_Trapped = 28;
    public static final int FACET_ATTRIBUTE_TYPE_Disarmed = 29;
    public static final int FACET_ATTRIBUTE_TYPE_NoBlock = 30;
    public static final int FACET_ATTRIBUTE_TYPE_Lava = 31;
    public static final int FACET_ATTRIBUTE_TYPE_Ex = 32;

    // mm7
    public static final int FACET_ATTRIBUTE_TYPE_Secret = 2;
    public static final int FACET_ATTRIBUTE_TYPE_ScrollDn = 3;
    public static final int FACET_ATTRIBUTE_TYPE_ScrollUp = 6;
    public static final int FACET_ATTRIBUTE_TYPE_ScrollLt = 7;
    public static final int FACET_ATTRIBUTE_TYPE_ScrollRt = 12;
    public static final int FACET_ATTRIBUTE_TYPE_Outline = 17;

    private boolean initializedFacetAttributeData = false;
    private Map<Integer,Integer> facetAttributeBitByFacetAttributeTypeMap = null;
    private Map<Integer,String> facetAttributeNameByFacetAttributeTypeMap = null;
    
    protected void initializeFacetAttributeData()
    {
        Object facetAttributeData[] = new Object[] {
    	    new Integer(FACET_ATTRIBUTE_TYPE_Portal), new Integer(1), "facet is a portal",
    	    new Integer(FACET_ATTRIBUTE_TYPE_UNKNOWN_2), new Integer(2), "unknown - mm7: facet is a 'secret', flagged by level designer",
    	    new Integer(FACET_ATTRIBUTE_TYPE_UNKNOWN_3), new Integer(3), "unknown - mm7: facet texture will scroll down",
    	    new Integer(FACET_ATTRIBUTE_TYPE_AlignTop), new Integer(4), "align facet texture to top of facet",
    	    new Integer(FACET_ATTRIBUTE_TYPE_water), new Integer(5), "facet is water",
    	    new Integer(FACET_ATTRIBUTE_TYPE_UNKNOWN_6), new Integer(6), "unknown - mm7: facet texture will scroll up",
    	    new Integer(FACET_ATTRIBUTE_TYPE_UNKNOWN_7), new Integer(7), "unknown - mm7: facet texture will scroll left",
    	    new Integer(FACET_ATTRIBUTE_TYPE_drawn), new Integer(8), "facet has been drawn",
    	    new Integer(FACET_ATTRIBUTE_TYPE_intxy), new Integer(9), "use x and y for facet interceptions",
    	    new Integer(FACET_ATTRIBUTE_TYPE_intxz), new Integer(10), "use x and z for facet interceptions",
    	    new Integer(FACET_ATTRIBUTE_TYPE_intyz), new Integer(11), "use y and z for facet interceptions",
    	    new Integer(FACET_ATTRIBUTE_TYPE_UNKNOWN_12), new Integer(12), "unknown - mm7: facet texture will scroll right",
    	    new Integer(FACET_ATTRIBUTE_TYPE_AlignLeft), new Integer(13), "align facet texture to left of facet",
    	    new Integer(FACET_ATTRIBUTE_TYPE_Invisible), new Integer(14), "facet is invisible",
    	    new Integer(FACET_ATTRIBUTE_TYPE_TFT), new Integer(15), "use texture frame table for bitmapA",
    	    new Integer(FACET_ATTRIBUTE_TYPE_AlignRight), new Integer(16), "align texture to right of facet",
    	    new Integer(FACET_ATTRIBUTE_TYPE_UNKNOWN_17), new Integer(17), "unknown - mm7: This facet wants to be outlined",
    	    new Integer(FACET_ATTRIBUTE_TYPE_AlignBottom), new Integer(18), "align texture to bottom of facet",
    	    new Integer(FACET_ATTRIBUTE_TYPE_Peg), new Integer(19), "bitmap is pegged to facet",
    	    new Integer(FACET_ATTRIBUTE_TYPE_NoSeed), new Integer(20), "do not recurse when building rooms",
    	    new Integer(FACET_ATTRIBUTE_TYPE_InfoEvent), new Integer(21), "facet has an 'Info' event attached to it",
    	    new Integer(FACET_ATTRIBUTE_TYPE_BlockMonster), new Integer(22), "block the monster, ONLY from passing this facet",
    	    new Integer(FACET_ATTRIBUTE_TYPE_Sky), new Integer(23), "sky",
    	    new Integer(FACET_ATTRIBUTE_TYPE_MirrorU), new Integer(24), "mirror u texture coordinate",
    	    new Integer(FACET_ATTRIBUTE_TYPE_MirrorV), new Integer(25), "mirror v tecture coordinate",
    	    new Integer(FACET_ATTRIBUTE_TYPE_ClickTr), new Integer(26), "player click trigger",
    	    new Integer(FACET_ATTRIBUTE_TYPE_PlayTr), new Integer(27), "player touch trigger",
    	    new Integer(FACET_ATTRIBUTE_TYPE_Trapped), new Integer(28), "trapped",
    	    new Integer(FACET_ATTRIBUTE_TYPE_Disarmed), new Integer(29), "disarmed",
    	    new Integer(FACET_ATTRIBUTE_TYPE_NoBlock), new Integer(30), "no block movement",
    	    new Integer(FACET_ATTRIBUTE_TYPE_Lava), new Integer(31), "on lava",
    	    new Integer(FACET_ATTRIBUTE_TYPE_Ex), new Integer(32), "facet has extra data"
        };

       int facetAttributeTypeArrayColumns = 3;
       if (facetAttributeData.length % facetAttributeTypeArrayColumns != 0)
       {
           throw new RuntimeException("facetAttributeData.length = " + facetAttributeData.length + " which is not a power of " + facetAttributeTypeArrayColumns + ".");
       }

       facetAttributeBitByFacetAttributeTypeMap = new HashMap<Integer,Integer>();
       facetAttributeNameByFacetAttributeTypeMap = new HashMap<Integer,String>();
       
       for (int index = 0; index < facetAttributeData.length; index = index+facetAttributeTypeArrayColumns)
       {
           Integer facetAttributeType = (Integer)facetAttributeData[index];
           Integer facetAttributeBit = (Integer)facetAttributeData[index+1];
           String facetAttributeName = (String)facetAttributeData[index+2];

           Integer key = facetAttributeType;
           
           Integer newFacetAttributeBitValue = facetAttributeBit;
           if (facetAttributeBitByFacetAttributeTypeMap.containsKey(key))
           {
        	   Integer value = facetAttributeBitByFacetAttributeTypeMap.get(key);
               throw new RuntimeException("facetAttributeBitByFacetAttributeTypeMap already contains key " + key + " with value of " + value);
           }
           facetAttributeBitByFacetAttributeTypeMap.put(key, newFacetAttributeBitValue);
           
           String newFacetAttributeNameValue = facetAttributeName;
           if (facetAttributeNameByFacetAttributeTypeMap.containsKey(key))
           {
               String value = facetAttributeNameByFacetAttributeTypeMap.get(key);
               throw new RuntimeException("facetAttributeNameByFacetAttributeTypeMap already contains key " + key + " with value of " + value);
           }
           facetAttributeNameByFacetAttributeTypeMap.put(key, newFacetAttributeNameValue);
       }
       
       initializedFacetAttributeData = true;
    }

    protected String[] getMiniIconNameChoiceArray()
    {
        if (false == initializedMiniIconNameChoiceData)
        {
            initializeMiniIconNameChoiceData();
        }
        
        return miniIconNameChoiceArray;
    }

    protected String[] getPartyMemberChoiceArray()
    {
        if (false == initializedPartyMemberChoiceData)
        {
            initializePartyMemberChoiceData();
        }
        
        return partyMemberChoiceArray;
    }

    public Integer[] getCommandTypes()
    {
        if (false == initializedCommandTypeInfo)
        {
            initializeCommandTypeInfo();
        }
        
        return commandTypes;
    }

    public Map<Integer,int[]> getArgumentTypeArrayByCommandTypeMap()
    {
        if (false == initializedCommandTypeInfo)
        {
            initializeCommandTypeInfo();
        }
        
        return argumentTypeArrayByCommandTypeMap;
    }

    public Map<Integer,ArgumentTypeDisplayInfo[]> getArgumentTypeDisplayInfoArrayByCommandTypeMap()
    {
        if (false == initializedCommandTypeInfo)
        {
            initializeCommandTypeInfo();
        }
        
        return argumentTypeDisplayInfoArrayByCommandTypeMap;
    }

    protected Map<Integer,Integer> getSizeByArgumentTypeMap()
    {
        if (false == initializedArgumentTypeInfo)
        {
            initializeArgumentTypeInfo();
        }
        
        return sizeByArgumentTypeMap;
    }

    protected Map<Integer,Integer> getArgumentDataTypeByArgumentTypeMap()
    {
        if (false == initializedArgumentTypeInfo)
        {
            initializeArgumentTypeInfo();
        }
        
        return argumentDataTypeByArgumentTypeMap;
    }

    public Integer[] getTargetTypes()
    {
        if (false == initializedTargetTypeInfo)
        {
            initializeTargetTypeInfo();
        }
        
        return targetTypeArray;
    }

    protected Map<Integer,Integer> getFacetAttributeBitByFacetAttributeTypeMap()
    {
        if (false == initializedFacetAttributeData)
        {
            initializeFacetAttributeData();
        }
        
        return facetAttributeBitByFacetAttributeTypeMap;
    }

    protected Map<Integer,String> getFacetAttributeNameByFacetAttributeTypeMap()
    {
        if (false == initializedFacetAttributeData)
        {
            initializeFacetAttributeData();
        }
        
        return facetAttributeNameByFacetAttributeTypeMap;
    }

    public int initialize(Event event, final byte[] dataSrc, final int offset, byte eventLength)
    {
        // IMPLEMENT: remove this after everything is understood.
        byte rawData[] = new byte[eventLength];
        System.arraycopy(dataSrc, offset, rawData, 0, rawData.length);
        event.setRawData(rawData);

        int eventNumber = ByteConversions.getUnsignedShortInByteArrayAtPosition(dataSrc, offset + EVENT_NUMBER_OFFSET);
        int sequenceNumber = ByteConversions.convertByteToInt(dataSrc[offset + EVENT_SEQUENCE_OFFSET]);
        int commandType = ByteConversions.convertByteToInt(dataSrc[offset + EVENT_COMMAND_OFFSET]);

        event.setEventNumber(eventNumber);
        event.setSequenceNumber(sequenceNumber);
        event.setCommandType(commandType);

        int eventOffset = offset + EVENT_COMMAND_ARGS_OFFSET;
        int argumentTypeArray[] = getArgumentTypeArrayForCommandType(commandType);
        for (int argumentTypeIndex = 0; argumentTypeIndex < argumentTypeArray.length; argumentTypeIndex++)
        {
            Object argument;
            int argumentType = argumentTypeArray[argumentTypeIndex];
            int dataSize = getArgumentTypeDataSize(argumentType);
            switch (argumentType)
            {
                case ARGUMENT_TYPE__UNKNOWN:
	                byte unknownData[] = new byte[eventLength - (eventOffset - offset)];
	                System.arraycopy(dataSrc, eventOffset, unknownData, 0, unknownData.length);
	                argument = unknownData;
	                eventOffset += unknownData.length;
	                break;
                case ARGUMENT_TYPE__COORDINATE_SET:
        	        int x = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, eventOffset);
        	        int y = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, eventOffset + 4);
        	        int z = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, eventOffset + 8);
        	        argument = new CoordinateSet(x, y, z);
	                eventOffset += dataSize;
                    break;
                case ARGUMENT_TYPE__FILENAME_12:
                case ARGUMENT_TYPE__FILENAME_13:
                    argument = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(
                            dataSrc, eventOffset, dataSize);
	                eventOffset += dataSize;
                    break;
                case ARGUMENT_TYPE__UNKNOWN_BYTE:
                case ARGUMENT_TYPE__UNKNOWN_ZERO_BYTE:
                case ARGUMENT_TYPE__UNKNOWN_SHORT:
                case ARGUMENT_TYPE__UNKNOWN_ZERO_SHORT:
                case ARGUMENT_TYPE__UNKNOWN_INTEGER:
                case ARGUMENT_TYPE__UNKNOWN_ZERO_INTEGER:
                case ARGUMENT_TYPE__TARGET_TYPE:
                case ARGUMENT_TYPE__TARGET_NUMBER:
                case ARGUMENT_TYPE__SEQUENCE:
                case ARGUMENT_TYPE__2DEVENT_NUMBER:
                case ARGUMENT_TYPE__LOCAL_EVENT_STRING_NUMBER:
                case ARGUMENT_TYPE__DIALOG_NUMBER:
                case ARGUMENT_TYPE__MINI_ICON_NUMBER:
                case ARGUMENT_TYPE__CHEST_NUMBER:
                case ARGUMENT_TYPE__PARTY_MEMBER:
                case ARGUMENT_TYPE__FACE_IMAGE_NUMBER:
                case ARGUMENT_TYPE__3DOBJECT_NUMBER:
                case ARGUMENT_TYPE__FACET_NUMBER:
                case ARGUMENT_TYPE__SPRITE_NUMBER:
                case ARGUMENT_TYPE__BOOLEAN:
                case ARGUMENT_TYPE__SPECIES_TYPE:
                case ARGUMENT_TYPE__SUBSPECIES_TYPE:
                case ARGUMENT_TYPE__MONSTER_CREATION_COUNT:
                case ARGUMENT_TYPE__SPELL_NUMBER:
                case ARGUMENT_TYPE__SPELL_SKILL_EXPERTISE:
                case ARGUMENT_TYPE__SPELL_SKILL_LEVEL:
                case ARGUMENT_TYPE__NPCTEXT_NUMBER:
                case ARGUMENT_TYPE__NPCDATA_NUMBER:
                case ARGUMENT_TYPE__NPC_MENU_INDEX:
                case ARGUMENT_TYPE__GLOBAL_EVENT_NUMBER:
                case ARGUMENT_TYPE__FACET_ATTRIBUTE_TYPE:
                case ARGUMENT_TYPE__ZERO_BYTE_PLACEHOLDER:
                case ARGUMENT_TYPE__SOUND_NUMBER:
                    int value;
                	switch (dataSize)
                	{
                	    case 1:
                	        value = ByteConversions.convertByteToInt(dataSrc[eventOffset]);
                 	        break;
                	    case 2:
                	        value = ByteConversions.getUnsignedShortInByteArrayAtPosition(dataSrc, eventOffset);
                   	        break;
                	    case 4:
                	        value = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, eventOffset);
                   	        break;
                   	    default:
                            throw new RuntimeException("Unsupported dataSize: " + dataSize
                                    + " for argument Type: " + argumentType
                                    + " found at argumentTypeIndex: " + argumentTypeIndex
                                    + " in commandType: " + commandType
                                    + " at offset: " + offset);
                	}
                	switch(argumentType)
                	{
                        case ARGUMENT_TYPE__UNKNOWN_ZERO_BYTE:
                        case ARGUMENT_TYPE__UNKNOWN_ZERO_SHORT:
                        case ARGUMENT_TYPE__UNKNOWN_ZERO_INTEGER:
                        case ARGUMENT_TYPE__ZERO_BYTE_PLACEHOLDER:
                            if (0 != value)
                            {
                                throw new RuntimeException("Found non-zero illegal value: " + value
                                        + " for argument Type: " + argumentType
                                        + " found at argumentTypeIndex: " + argumentTypeIndex
                                        + " in commandType: " + commandType
                                        + " at offset: " + offset);
                            }
                            break;
                        case ARGUMENT_TYPE__BOOLEAN:
                            if ((0 != value) && (1 != value))
                            {
                                throw new RuntimeException("Found non-zero, non-one illegal value: " + value
                                        + " for argument Type: " + argumentType
                                        + " found at argumentTypeIndex: " + argumentTypeIndex
                                        + " in commandType: " + commandType
                                        + " at offset: " + offset);
                            }
                            break;
                   	    default:
                   	        // no validation necessary
                   	        break;
                	}
        	        argument = new Integer(value);
        	        eventOffset += dataSize;
        	        break;
                default:
                    throw new RuntimeException("Unsupported Argument Type: " + argumentType
                            + " found at argumentTypeIndex: " + argumentTypeIndex
                            + " in commandType: " + commandType
                            + " at offset: " + offset);
            }
            
            event.addArgument(argument);
        }
        
        return offset + eventLength;
    }

    public void initializeWithValues(Event event, int eventNumber, int sequenceNumber, int commandType)
    {
        // IMPLEMENT: remove this after everything is understood.
        byte rawData[] = new byte[0];
        event.setRawData(rawData);

        event.setEventNumber(eventNumber);
        event.setSequenceNumber(sequenceNumber);
        event.setCommandType(commandType);

//        int minExpectedArgCount = minExpectedArgByteCount(commandType);
//        byte eventLength = (byte)(EVENT_COMMAND_ARGS_OFFSET + minExpectedArgCount);
//        
//        event.data = new byte[eventLength];
//        ByteConversions.setShortInByteArrayAtPosition((short)eventNumber, event.data, EVENT_NUMBER_OFFSET);
//        event.data[EVENT_SEQUENCE_OFFSET] = ByteConversions.convertIntToByte(sequenceNumber);
//        event.data[EVENT_COMMAND_OFFSET] = ByteConversions.convertIntToByte(commandType);
    }
    
    public int updateData(Event event, byte[] newData, final int offset)
    {
        int eventNumber = event.getEventNumber();
        int sequenceNumber = event.getSequenceNumber();
        int commandType = event.getCommandType();

        // IMPLEMENT: deal with this as unsigned short!
        ByteConversions.setShortInByteArrayAtPosition(
                (short)eventNumber, newData, offset + EVENT_NUMBER_OFFSET);
        newData[offset + EVENT_SEQUENCE_OFFSET] = ByteConversions.convertIntToByte(sequenceNumber);
        newData[offset + EVENT_COMMAND_OFFSET] = ByteConversions.convertIntToByte(commandType);

        int eventOffset = offset + EVENT_COMMAND_ARGS_OFFSET;
        int argumentTypeArray[] = getArgumentTypeArrayForCommandType(commandType);
        for (int argumentTypeIndex = 0; argumentTypeIndex < argumentTypeArray.length; argumentTypeIndex++)
        {
            Object argument = event.getArgumentAtIndex(argumentTypeIndex);
            int argumentType = argumentTypeArray[argumentTypeIndex];
            int dataSize = getArgumentTypeDataSize(argumentType);
            switch (argumentType)
            {
                case ARGUMENT_TYPE__UNKNOWN:
	                byte unknownData[] = (byte[])argument;
                	dataSize = unknownData.length;
	                System.arraycopy(unknownData, 0, newData, eventOffset, unknownData.length);
	                eventOffset += unknownData.length;
	                break;
                case ARGUMENT_TYPE__COORDINATE_SET:
        	        CoordinateSet coordinateSet = (CoordinateSet)argument;
                    ByteConversions.setIntegerInByteArrayAtPosition(coordinateSet.getX(), newData, eventOffset + 0);
                    ByteConversions.setIntegerInByteArrayAtPosition(coordinateSet.getY(), newData, eventOffset + 4);
                    ByteConversions.setIntegerInByteArrayAtPosition(coordinateSet.getZ(), newData, eventOffset + 8);
	                eventOffset += dataSize;
                    break;
                case ARGUMENT_TYPE__FILENAME_12:
                case ARGUMENT_TYPE__FILENAME_13:
                    String filename = (String)argument;
                    ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(
                            filename, newData, eventOffset, dataSize);
	                eventOffset += dataSize;
                    break;
                case ARGUMENT_TYPE__UNKNOWN_BYTE:
                case ARGUMENT_TYPE__UNKNOWN_ZERO_BYTE:
                case ARGUMENT_TYPE__UNKNOWN_SHORT:
                case ARGUMENT_TYPE__UNKNOWN_ZERO_SHORT:
                case ARGUMENT_TYPE__UNKNOWN_INTEGER:
                case ARGUMENT_TYPE__UNKNOWN_ZERO_INTEGER:
                case ARGUMENT_TYPE__TARGET_TYPE:
                case ARGUMENT_TYPE__TARGET_NUMBER:
                case ARGUMENT_TYPE__SEQUENCE:
                case ARGUMENT_TYPE__2DEVENT_NUMBER:
                case ARGUMENT_TYPE__LOCAL_EVENT_STRING_NUMBER:
                case ARGUMENT_TYPE__DIALOG_NUMBER:
                case ARGUMENT_TYPE__MINI_ICON_NUMBER:
                case ARGUMENT_TYPE__CHEST_NUMBER:
                case ARGUMENT_TYPE__PARTY_MEMBER:
                case ARGUMENT_TYPE__FACE_IMAGE_NUMBER:
                case ARGUMENT_TYPE__3DOBJECT_NUMBER:
                case ARGUMENT_TYPE__FACET_NUMBER:
                case ARGUMENT_TYPE__SPRITE_NUMBER:
                case ARGUMENT_TYPE__BOOLEAN:
                case ARGUMENT_TYPE__SPECIES_TYPE:
                case ARGUMENT_TYPE__SUBSPECIES_TYPE:
                case ARGUMENT_TYPE__MONSTER_CREATION_COUNT:
                case ARGUMENT_TYPE__SPELL_NUMBER:
                case ARGUMENT_TYPE__SPELL_SKILL_EXPERTISE:
                case ARGUMENT_TYPE__SPELL_SKILL_LEVEL:
                case ARGUMENT_TYPE__NPCTEXT_NUMBER:
                case ARGUMENT_TYPE__NPCDATA_NUMBER:
                case ARGUMENT_TYPE__NPC_MENU_INDEX:
                case ARGUMENT_TYPE__GLOBAL_EVENT_NUMBER:
                case ARGUMENT_TYPE__ZERO_BYTE_PLACEHOLDER:
                case ARGUMENT_TYPE__SOUND_NUMBER:
                case ARGUMENT_TYPE__FACET_ATTRIBUTE_TYPE:
                    Number number = (Number)argument;
                	switch (dataSize)
                	{
                	    case 1:
                	        newData[eventOffset] = ByteConversions.convertIntToByte(number.intValue());
                 	        break;
                	    case 2:
                	        ByteConversions.setUnsignedShortInByteArrayAtPosition(number.intValue(), newData, eventOffset);
                   	        break;
                	    case 4:
                            ByteConversions.setIntegerInByteArrayAtPosition(number.intValue(), newData, eventOffset);
                   	        break;
                   	    default:
                            throw new RuntimeException("Unsupported dataSize: " + dataSize
                                    + " for argument Type: " + argumentType
                                    + " found at argumentTypeIndex: " + argumentTypeIndex
                                    + " in commandType: " + commandType
                                    + " at offset: " + offset);
                	}
        	        eventOffset += dataSize;
        	        break;
                default:
                    throw new RuntimeException("Unsupported Argument Type: " + argumentType
                            + " found at argumentTypeIndex: " + argumentTypeIndex
                            + " in commandType: " + commandType
                            + " at offset: " + offset);
            }
            
        }

        return eventOffset;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
    	boolean result = checkDataIntegrityTotal(data, offset, expectedNewOffset);
        if  (!result)
        {
        	return false;
        }
        
        // TODO: need to be able to recognize whether this is an MM6, 7, or 8 file at this point.
        try {
			EventFormatMM6 eventFormatMM6 = new EventFormatMM6();
			List<Event> eventList = eventFormatMM6.readEvents(data);
			Iterator<Event> eventIterator = eventList.iterator();
			while (eventIterator.hasNext()) {
				Event event = eventIterator.next();
				if (!eventFormatMM6.isValid(event))
				{
					return false;
				}
			}
		} catch (RuntimeException e) {
			return false;
		}
		
		return true;
    }

    public static boolean checkDataIntegrityTotal(byte[] data, int offset, int expectedNewOffset)
    {
        while (expectedNewOffset > offset)
        {
            byte eventLength = data[offset];
            offset += 1 + eventLength;
        }

        return (offset == expectedNewOffset);
    }
    
    public List<Event> readEvents(byte data[])
    {
        int offset = 0;
        List<Event> eventList = new ArrayList<Event>();
        
        while (data.length != offset)
        {
            byte eventLength = data[offset];
            offset += 1;
            
            Event event = createEventInstance();
            offset = this.initialize(event, data, offset, eventLength);
            eventList.add(event);
        }
        
        return eventList;
    }

    public byte[] writeEvents(List<Event> eventList)
    {
        int offset = 0;
        byte data[] = null;
        
        int dataLength = 0;
        Iterator<Event> eventIterator = eventList.iterator();
        while (eventIterator.hasNext())
        {
            Event event = eventIterator.next();
            dataLength += 1 + this.getEventLength(event);
        }
        
        data = new byte[dataLength];
        
        eventIterator = eventList.iterator();
        while (eventIterator.hasNext())
        {
            Event event = eventIterator.next();
            data[offset] = this.getEventLength(event);
            offset += 1;

            offset = this.updateData(event, data, offset);
        }
        
        return data;
    }

    protected byte getEventLength(Event event)
    {
        int length = EVENT_COMMAND_ARGS_OFFSET;

        int argumentTypeArray[] = getArgumentTypeArrayForCommandType(event.getCommandType());
        for (int argumentTypeIndex = 0; argumentTypeIndex < argumentTypeArray.length; argumentTypeIndex++)
        {
            int argumentType = argumentTypeArray[argumentTypeIndex];
            int argumentTypeDataSize = getArgumentTypeDataSize(argumentType);
            if (0 > argumentTypeDataSize)
            {
            	if (ARGUMENT_TYPE__UNKNOWN == argumentType)
            	{
                    Object argument = event.getArgumentAtIndex(argumentTypeIndex);
	                byte unknownData[] = (byte[])argument;
	                argumentTypeDataSize = unknownData.length;
            	}
            	else
            	{
                	throw new UnimplementedMethodException("Event " + event + " has argument type " + argumentType + " with -1 size");
            	}
            }
			length += argumentTypeDataSize;
        }

        return (byte)length;
    }


//    public byte[] getData()
//    {
//        return this.data;
//    }
//    
//    public int getOffset()
//    {
//        return this.offset;
//    }
//
//    public byte getEventLength()
//    {
//        return (byte)this.data.length;
//    }
//
//    public int getEventNumber()
//    {
//        return this.eventNumber;
//    }
//    public void setEventNumber(int eventNumber)
//    {
//        this.eventNumber = eventNumber;
//    }
//
//    public int getEventSequenceNumber()
//    {
//        return this.eventSequenceNumber;
//    }
//    public void setEventSequenceNumber(int eventSequenceNumber)
//    {
//        this.eventSequenceNumber = eventSequenceNumber;
//    }
//
//    public int getEventCommandNumber()
//    {
//        return this.eventCommandNumber;
//    }
//    public void setEventCommandNumber(int eventCommandNumber)
//    {
//        this.eventCommandNumber = eventCommandNumber;
//    }

    private int minExpectedArgByteCount(int commandType)
    {
        int byteCount = 0;
        
        int argumentTypeArray[] = getArgumentTypeArrayForCommandType(commandType);
        for (int argumentTypeIndex = 0; argumentTypeIndex < argumentTypeArray.length; argumentTypeIndex++)
        {
            int argumentType = argumentTypeArray[argumentTypeIndex];
            if ((argumentType == ARGUMENT_TYPE__FILENAME_12) || (argumentType == ARGUMENT_TYPE__FILENAME_13))
            {
                // 1 char + zero-terminator for filename
                byteCount += 2;
            }
            else
            {
                byteCount += getArgumentTypeDataSize(argumentType);
            }
        }
        
//        int oldByteCount = minExpectedArgCount_OLD(commandType);
//        if (byteCount != oldByteCount)
//        {
//            Logger log = Logger.getLogger(VERIFICATION_LOGGER_NAME);
//            log.warning("New minExpectedArgByteCount: " + byteCount + " doesn't equal minExpectedArgByteCount_OLD: " + oldByteCount + " for commandType: " + commandType);
//        }
        
        return byteCount;
    }

    private int maxExpectedArgByteCount(int commandType)
    {
        int byteCount = 0;
        
        int argumentTypeArray[] = getArgumentTypeArrayForCommandType(commandType);
        for (int argumentTypeIndex = 0; argumentTypeIndex < argumentTypeArray.length; argumentTypeIndex++)
        {
            int argumentType = argumentTypeArray[argumentTypeIndex];
            byteCount += getArgumentTypeDataSize(argumentType);
        }
        
//        int oldByteCount = maxExpectedArgCount_OLD(commandType);
//        if (byteCount != oldByteCount)
//        {
//            Logger log = Logger.getLogger(VERIFICATION_LOGGER_NAME);
//            log.warning("New maxExpectedArgByteCount: " + byteCount + " doesn't equal maxExpectedArgCount_OLD: " + oldByteCount + " for commandType: " + commandType);
//        }
        
        return byteCount;
    }

    private String getEventArgumentDescriptionForEvent(Event event)
    {
        int commandType = event.getCommandType();
        int minExpectedArgCount =  minExpectedArgByteCount(commandType);
        int maxExpectedArgCount =  maxExpectedArgByteCount(commandType);
        if (minExpectedArgCount == maxExpectedArgCount)
        {
            if (-1 != minExpectedArgCount)
            {
                if (minExpectedArgCount != (event.getRawData().length - EVENT_COMMAND_ARGS_OFFSET))
                    return ": Expected " + minExpectedArgCount + " arguments but had " + (event.getRawData().length - EVENT_COMMAND_ARGS_OFFSET) + " arguments.";
            }
        }
        else
        {
            if (-1 != minExpectedArgCount)
            {
                int actualArgs = event.getRawData().length - EVENT_COMMAND_ARGS_OFFSET;
                if ((actualArgs < minExpectedArgCount) || (actualArgs > maxExpectedArgCount))
                    return ": Expected between " + minExpectedArgCount + " and " + maxExpectedArgCount + " arguments but had " + (event.getRawData().length - EVENT_COMMAND_ARGS_OFFSET) + " arguments.";
            }
        }
        
        StringBuffer buffer = new StringBuffer();
        int[] argumentTypeArray = this.getArgumentTypeArrayForCommandType(event.getCommandType());

        int targetTypeHowManyAgo = 0;
        for (int argumentIndex = 0; argumentIndex < argumentTypeArray.length; argumentIndex++)
        {
            Object argument = event.getArgumentAtIndex(argumentIndex);
            
            buffer.append(' ');
            
            String label = null;
	        final int argumentType = argumentTypeArray[argumentIndex];
	        final int dataFieldSize = this.getArgumentTypeDataSize(argumentType);
	        
	        targetTypeHowManyAgo--;
	        
	        boolean needsOutputHandled = true;
	        
	        switch (argumentType)
	        {
	            case ARGUMENT_TYPE__ZERO_BYTE_PLACEHOLDER:
	                // Don't need to output this
	                needsOutputHandled = false;
	                break;
	            case EventFormat.ARGUMENT_TYPE__TARGET_TYPE:
	                needsOutputHandled = false;
            		targetTypeHowManyAgo = 2;
	                break;
	            case EventFormat.ARGUMENT_TYPE__FILENAME_12:
	            case EventFormat.ARGUMENT_TYPE__FILENAME_13:
	                needsOutputHandled = false;
	                label = "file";
	            	buffer.append(label);
	                buffer.append(": ");
	            	buffer.append((String)argument);
	            	break;
	            case EventFormat.ARGUMENT_TYPE__COORDINATE_SET:
	                needsOutputHandled = false;
	                label = "COORDINATE";
	            	buffer.append(label);
	                buffer.append(": ");
	            	buffer.append("(");
	            	CoordinateSet coordinateSet = (CoordinateSet)argument;
	            	buffer.append(coordinateSet.getX());
	            	buffer.append(",");
	            	buffer.append(coordinateSet.getY());
	            	buffer.append(",");
	            	buffer.append(coordinateSet.getZ());
	            	buffer.append(")");
	            	break;
	            
	            case EventFormat.ARGUMENT_TYPE__TARGET_NUMBER:
	                if (1 == targetTypeHowManyAgo)
	                {
		                needsOutputHandled = false;
	            		targetTypeHowManyAgo = 0;
	            		Number targetTypeArgument = (Number)event.getArgumentAtIndex(argumentIndex-1);
	            		Number targetNumberArgument = (Number)argument;
		            	buffer.append(getTarget(targetTypeArgument.intValue(), targetNumberArgument.intValue()));
	                }
	                break;
	            case EventFormat.ARGUMENT_TYPE__UNKNOWN:  label = "UNKNOWN";
	                needsOutputHandled = false;
	                label = "UNKNOWN";
	            	buffer.append(label);
	            	break;
	            	
	            case EventFormat.ARGUMENT_TYPE__FACE_IMAGE_NUMBER:  label = "FACE_IMAGE_NUMBER"; break;
	            case EventFormat.ARGUMENT_TYPE__UNKNOWN_BYTE:  label = "UNKNOWN_BYTE"; break;
	            case EventFormat.ARGUMENT_TYPE__UNKNOWN_SHORT:  label = "UNKNOWN_SHORT"; break;
	            case EventFormat.ARGUMENT_TYPE__UNKNOWN_INTEGER:  label = "UNKNOWN_INTEGER"; break;
	            case EventFormat.ARGUMENT_TYPE__UNKNOWN_ZERO_BYTE:  label = "UNKNOWN_ZERO_BYTE"; break;
	            case EventFormat.ARGUMENT_TYPE__UNKNOWN_ZERO_SHORT:  label = "UNKNOWN_ZERO_SHORT"; break;
	            case EventFormat.ARGUMENT_TYPE__UNKNOWN_ZERO_INTEGER:  label = "UNKNOWN_ZERO_INTEGER"; break;
	            case EventFormat.ARGUMENT_TYPE__SEQUENCE:  label = "SEQUENCE"; break;
	            case EventFormat.ARGUMENT_TYPE__2DEVENT_NUMBER:  label = "2dEvent#"; break;
	            case EventFormat.ARGUMENT_TYPE__LOCAL_EVENT_STRING_NUMBER:  label = "LOCAL_EVENT_STRING_NUMBER"; break;
	            case EventFormat.ARGUMENT_TYPE__DIALOG_NUMBER:  label = "DIALOG_NUMBER"; break;
	            case EventFormat.ARGUMENT_TYPE__MINI_ICON_NUMBER:  label = "MINI_ICON_NUMBER"; break;
	            case EventFormat.ARGUMENT_TYPE__CHEST_NUMBER:  label = "CHEST_NUMBER"; break;
	            case EventFormat.ARGUMENT_TYPE__PARTY_MEMBER:  label = "PARTY_MEMBER"; break;
	            case EventFormat.ARGUMENT_TYPE__3DOBJECT_NUMBER:  label = "3DOBJECT_NUMBER"; break;
	            case EventFormat.ARGUMENT_TYPE__FACET_NUMBER:  label = "FACET_NUMBER"; break;
	            case EventFormat.ARGUMENT_TYPE__SPRITE_NUMBER:  label = "SPRITE_NUMBER"; break;
	            case EventFormat.ARGUMENT_TYPE__BOOLEAN:  label = "BOOLEAN"; break;
	            case EventFormat.ARGUMENT_TYPE__SPECIES_TYPE:  label = "SPECIES_TYPE"; break;
	            case EventFormat.ARGUMENT_TYPE__SUBSPECIES_TYPE:  label = "SUBSPECIES_TYPE"; break;
	            case EventFormat.ARGUMENT_TYPE__MONSTER_CREATION_COUNT:  label = "MONSTER_CREATION_COUNT"; break;
	            case EventFormat.ARGUMENT_TYPE__SPELL_NUMBER:  label = "SPELL_NUMBER"; break;
	            case EventFormat.ARGUMENT_TYPE__SPELL_SKILL_EXPERTISE:  label = "SPELL_SKILL_EXPERTISE"; break;
	            case EventFormat.ARGUMENT_TYPE__SPELL_SKILL_LEVEL:  label = "SPELL_SKILL_LEVEL"; break;
	            case EventFormat.ARGUMENT_TYPE__NPCTEXT_NUMBER:  label = "NPCTEXT_NUMBER"; break;
	            case EventFormat.ARGUMENT_TYPE__NPCDATA_NUMBER:  label = "NPCDATA_NUMBER"; break;
	            case EventFormat.ARGUMENT_TYPE__NPC_MENU_INDEX:  label = "NPC_MENU_INDEX"; break;
	            case EventFormat.ARGUMENT_TYPE__GLOBAL_EVENT_NUMBER:  label = "GLOBAL_EVENT_NUMBER"; break;
	            case EventFormat.ARGUMENT_TYPE__FACET_ATTRIBUTE_TYPE:  label = "FACET_ATTRIBUTE_TYPE"; break;
	            case EventFormat.ARGUMENT_TYPE__SOUND_NUMBER:  label = "SOUND_NUMBER"; break;
	            default:
	                throw new RuntimeException("Unsupported argument type: " + argumentType);
	        }

           if (needsOutputHandled)
            {
                if (null == label)
                {
                    throw new RuntimeException("null label for argument type: " + argumentType);
                }
                
                buffer.append(label);
                buffer.append(": ");
                switch (dataFieldSize)
                {
                    case 1:
                        buffer.append((Number)argument);
                        break;
                    case 2:
                        buffer.append((Number)argument);
                        break;
                    case 4:
                        buffer.append((Number)argument);
                        break;
                    default:
                        throw new RuntimeException("Unsupported dataFieldSize: " + dataFieldSize
                                + " for argumentIndex: " + argumentIndex + " for commandType:" + commandType);
                }
            }

	        if (1 == targetTypeHowManyAgo)
	        {
                throw new RuntimeException("target type wasn't followed by target number argument: " + argumentType);
	        }
       }
        
        return buffer.toString();
    }

    public boolean isDebugging(Event event)
    {
        int commandType = event.getCommandType();
        
        // IMPLEMENT: remove after debugged
        // if (EVENT_COMMAND__PlayAction == commandType)  return true;
        
        return false;
    }

    public boolean isUnderstood(Event event)
    {
        int commandType = event.getCommandType();
        int minExpectedArgCount =  minExpectedArgByteCount(commandType);
        int maxExpectedArgCount =  maxExpectedArgByteCount(commandType);
        if (minExpectedArgCount == maxExpectedArgCount)
        {
            if (-1 != minExpectedArgCount)
            {
                if (minExpectedArgCount != (event.getRawData().length - EVENT_COMMAND_ARGS_OFFSET))
                    return false;
            }
        }
        else
        {
            if (-1 != minExpectedArgCount)
            {
                int actualArgs = event.getRawData().length - EVENT_COMMAND_ARGS_OFFSET;
                if ((actualArgs < minExpectedArgCount) || (actualArgs > maxExpectedArgCount))
                    return false;
            }
        }
        
        int argumentTypeArray[] = getArgumentTypeArrayForCommandType(commandType);
        if (ARGUMENT_TYPE__UNKNOWN == argumentTypeArray[0])
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean isValid(Event event)
    {
        int commandType = event.getCommandType();
        int minExpectedArgCount =  minExpectedArgByteCount(commandType);
        int maxExpectedArgCount =  maxExpectedArgByteCount(commandType);
        if (minExpectedArgCount == maxExpectedArgCount)
        {
            if (-1 != minExpectedArgCount)
            {
                if (minExpectedArgCount != (event.getRawData().length - EVENT_COMMAND_ARGS_OFFSET))
                    return false;
            }
        }
        else
        {
            if (-1 != minExpectedArgCount)
            {
                int actualArgs = event.getRawData().length - EVENT_COMMAND_ARGS_OFFSET;
                if ((actualArgs < minExpectedArgCount) || (actualArgs > maxExpectedArgCount))
                    return false;
            }
        }
        
        return true;
    }

    protected Integer getCommandTypeKey(int commandType)
    {
        Integer commandTypes[] = getCommandTypes();
        if (commandType >= commandTypes.length)
        {
            return null;
        }
        
        Integer commandTypeKey = (Integer)commandTypes[commandType];
        if (null == commandTypeKey)
        {
            return null;
        }
        
        return commandTypeKey;
    }
    
    protected Integer getTargetTypeKey(int targetType)
    {
        Integer targetTypes[] = getTargetTypes();
        if (targetType >= targetTypes.length)
        {
            return null;
        }
        
        Integer targetTypeKey = (Integer)targetTypes[targetType];
        if (null == targetTypeKey)
        {
            return null;
        }
        
        return targetTypeKey;
    }
    
    private String getTarget(int targetType, int number)
    {
        return getTargetTypeName(targetType) + " #" + String.valueOf(number);
    }
    
    public String eventDescription(Event event)
    {
        return event.getEventNumber() + "-" + event.getSequenceNumber() + ": " + getCommandTypeName(event.getCommandType()) + getEventArgumentDescriptionForEvent(event);
    }
    

    // Unknown things to decode

    public static List getOffsetList()
    {
        return null;
    }
    
    public static List getObjectList(List<Event> eventList)
    {
        return null;
    }

    private boolean isTargetTypeUnderstood(int command, int targetType)
    {
        Integer targetTypeNumber = getTargetTypeKey(targetType);
        if (null != targetTypeNumber)
        {
            return true;
        }
        return false;
    }
    

    
//    public static final int EVENT_COMMAND__STOP_PROCESSING = 1;
//    public static final int EVENT_COMMAND__IDENTITY = 2;
//    //
//    public static final int EVENT_COMMAND__MOUSEOVER = 4;
//    //
//    public static final int EVENT_COMMAND__TELEPORT = 6;
//    public static final int EVENT_COMMAND__OPEN_CHEST = 7;
//    public static final int EVENT_COMMAND__SHOW_FACIAL_EXPRESSION = 8;
//    //
//    public static final int EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP = 12;
//    public static final int EVENT_COMMAND__CHANGE_SPRITE = 13;
//    public static final int EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE = 14;
//    //
//    public static final int EVENT_COMMAND__ADD_TARGET = 16;
//    public static final int EVENT_COMMAND__DELETE_TARGET = 17;
//    public static final int EVENT_COMMAND__SET_TARGET = 18;
//    public static final int EVENT_COMMAND__CREATE_LOCAL_MONSTER = 19;
//    //
//    public static final int EVENT_COMMAND__CAST_SPELL_FROM_LOCATION = 21;
//    //
//    public static final int EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING = 29;
//    public static final int EVENT_COMMAND__SHOW_NPCTEXT_STRING = 30;
//    //
//    public static final int EVENT_COMMAND__UNKNOWN_VOLCANO_EFFECT = 34; // something to do with the volcano buoy effect at New Sorpigal
//    public static final int EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER = 35;
//    public static final int EVENT_COMMAND__GOTO = 36;
//    public static final int EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE = 37; // Stop processing / On level load level?
//    public static final int EVENT_COMMAND__UNKNOWN_ON_X2_EXECUTE = 38;
//    public static final int EVENT_COMMAND__CHANGE_DIALOG_EVENT = 39; // npctext/npctopic
//
//    public static final int STOP_PROCESSING_ARGS_UNKNOWN1_OFFSET = 0;
//    
//    public static final int IDENTITY_ARGS_2DEVENT_ID_OFFSET = 0;
//    
//    public static final int MOUSEOVER_ARGS_EVENT_STR_ID_OFFSET = 0;
//    
//    public static final int TELEPORT_ARGS_DESTINATION_X_COORD_OFFSET = 0; // 4 bytes
//    public static final int TELEPORT_ARGS_DESTINATION_Y_COORD_OFFSET = 4; // 4 bytes
//    public static final int TELEPORT_ARGS_DESTINATION_Z_COORD_OFFSET = 8; // 4 bytes
//    public static final int TELEPORT_ARGS_DESTINATION_HORIZONTAL_ORIENTATION_OFFSET = 12; // 2 bytes
//    private static final int TELEPORT_ARGS_UNKNOWN1_OFFSET = 14; // 2 bytes
//    public static final int TELEPORT_ARGS_DESTINATION_VERTICAL_ORIENTATION_OFFSET = 16; // 2 bytes
//    private static final int TELEPORT_ARGS_UNKNOWN2_OFFSET = 18; // 2 bytes
//    private static final int TELEPORT_ARGS_UNKNOWN3_OFFSET = 20; // 4 bytes
//    public static final int TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET = 24; // 1 byte
//    public static final int TELEPORT_ARGS_MINIICON_OFFSET = 25; // 1 byte
//    public static final int TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET = 26; // 8 bytes
//    public static final int TELEPORT_ARGS_DESTINATION_FILENAME = 13;
//
//    public static final int OPEN_CHEST_ARGS_CHEST_NUMBER_OFFSET = 0;
//    
//    public static final int SHOW_FACIAL_EXPRESSION_ARGS_AFFECTED_OFFSET = 0;
//    public static final int SHOW_FACIAL_EXPRESSION_ARGS_IMAGE_ID_OFFSET = 1;
//    
//    public static final int CHANGE_3DOBJECT_FACE_BITMAP_ARGS_3D_OBJECT_NUMBER_OFFSET = 0; // object #
//    public static final int CHANGE_3DOBJECT_FACE_BITMAP_ARGS_FACE_OFFSET = 4; // face #
//    public static final int CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET = 8;
//    public static final int CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME = 12;
//    
//    public static final int CHANGE_SPRITE_ARGS_SPRITE_NUMBER = 0;
//    private static final int CHANGE_SPRITE_ARGS_UNKNOWN1_OFFSET = 2;
//    private static final int CHANGE_SPRITE_ARGS_UNKNOWN2_OFFSET = 4;
//    public static final int CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET = 5;
//    public static final int CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME = 12;
//
//    public static final int MM6_BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_TYPE_OFFSET = 0;
//    public static final int MM6_BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_TYPE_LENGTH = 1;
//    public static final int MM6_BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_NUMBER_OFFSET = 1;
//    public static final int MM6_BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_NUMBER_LENGTH = 4;
//    public static final int MM6_BRANCH_ON_CONDITION_TRUE_ARGS_GOTO_SEQUENCE_NUMBER_OFFSET = 5;
//    public static final int MM6_BRANCH_ON_CONDITION_TRUE_ARGS_GOTO_SEQUENCE_NUMBER_LENGTH = 1;
//    
//    public static final int ADD_TARGET_ARGS_TARGET_TYPE_OFFSET = 0;
//    public static final int ADD_TARGET_ARGS_TARGET_NUMBER_OFFSET = 1;
//
//    public static final int DELETE_TARGET_ARGS_TARGET_TYPE_OFFSET = 0;
//    public static final int DELETE_TARGET_ARGS_TARGET_NUMBER_OFFSET = 1;
//    private static final int DELETE_TARGET_ARGS_UNKNOWN1_OFFSET = 3;
//
//    public static final int SET_TARGET_ARGS_TARGET_TYPE_OFFSET = 0;
//    public static final int SET_TARGET_ARGS_TARGET_NUMBER_OFFSET = 1;
//    private static final int SET_TARGET_ARGS_UNKNOWN1_OFFSET = 3;
//
//    public static final int CREATE_LOCAL_MONSTER_ARGS_MONSTER_SPECIES_OFFSET = 0; // mod 3, remainder ups subspecies
//    public static final int CREATE_LOCAL_MONSTER_ARGS_MONSTER_SUBSPECIES__OFFSET = 1; // 0 or 1, 2, 3
//    public static final int CREATE_LOCAL_MONSTER_ARGS_COUNT_OFFSET = 2;
//    public static final int CREATE_LOCAL_MONSTER_ARGS_X_OFFSET = 3;
//    public static final int CREATE_LOCAL_MONSTER_ARGS_Y_OFFSET = 7;
//    public static final int CREATE_LOCAL_MONSTER_ARGS_Z_OFFSET = 11;
//
//    public static final int CAST_SPELL_FROM_LOCATION_ARGS_SPELL_NUMBER_OFFSET = 0; // 1 byte
//    public static final int CAST_SPELL_FROM_LOCATION_ARGS_RANKING_OFFSET = 1; // 1 byte
//    public static final int CAST_SPELL_FROM_LOCATION_ARGS_LEVEL_OFFSET = 2; // 1 byte
//    public static final int CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_X_OFFSET = 3; // 4 byte
//    public static final int CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Y_OFFSET = 7; // 4 byte
//    public static final int CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Z_OFFSET = 11; // 4 byte
//    public static final int CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN2_OFFSET = 15; // 4 byte - zeros
//    public static final int CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN3_OFFSET = 19; // 4 byte - zeros
//    public static final int CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN4_OFFSET = 23; // 4 byte - zeros
//
//    public static final int SHOW_LOCAL_EVENT_STRING_ARGS_STR_ID_OFFSET = 0;
//    private static final int SHOW_LOCAL_EVENT_STRING_ARGS_UNKNOWN1_OFFSET = 2;
//    
//    public static final int SHOW_NPCTEXT_STRING_ARGS_STR_ID_OFFSET = 0;
//    private static final int SHOW_NPCTEXT_STRING_ARGS_UNKNOWN1_OFFSET = 2;
//    
//    public static final int MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER_ARGS_AFFECTED_OFFSET = 0;
//    
//    public static final int GOTO_ARGS_SEQUENCE_NUMBER_OFFSET = 0;
//
//    public static final int CHANGE_DIALOG_EVENT_ARGS_NPCDATA_OFFSET = 0; // 4 byte
//    public static final int CHANGE_DIALOG_EVENT_ARGS_NPC_MENU_INDEX_OFFSET = 4; // 1 byte
//    public static final int CHANGE_DIALOG_EVENT_ARGS_NEW_GLOBAL_EVENT_NUMBER_OFFSET = 5; // 5 byte

//    public String getCommandTeleportArgumentLevelName()
//    {
//        int nameOffset = EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET;
//        return ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, nameOffset, TELEPORT_ARGS_DESTINATION_FILENAME);
//    }
//
//    public void setCommandTeleportArgumentLevelName(String levelName)
//    {
//        int nameOffset = EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET;
//        byte newData[] = new byte[nameOffset + levelName.length() + 1];
//        System.arraycopy(newData, 0, newData, 0, nameOffset);
//        
//        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(levelName, newData, nameOffset, levelName.length() + 1);
//        
//        this.data = newData;
//    }
//    
//    public String getCommandChangeSpriteArgumentNewSpriteName()
//    {
//        int nameOffset = EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET;
//        return ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, nameOffset, CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME);
//    }
//
//    public void setCommandChangeSpriteArgumentNewSpriteName(String newSpriteName)
//    {
//        int nameOffset = EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET;
//        byte newData[] = new byte[nameOffset + newSpriteName.length() + 1];
//        System.arraycopy(data, 0, newData, 0, nameOffset);
//        
//        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(newSpriteName, newData, nameOffset, newSpriteName.length() + 1);
//        
//        this.data = newData;
//    }
//    
//    public String getCommandChangeFaceBitmapArgumentNewBitmapName()
//    {
//        int nameOffset = EVENT_COMMAND_ARGS_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET;
//        return ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, nameOffset, CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME);
//    }
//
//    public void setCommandChangeFaceBitmapArgumentNewBitmapName(String newSpriteName)
//    {
//        int nameOffset = EVENT_COMMAND_ARGS_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET;
//        byte newData[] = new byte[nameOffset + newSpriteName.length() + 1];
//        System.arraycopy(data, 0, newData, 0, nameOffset);
//        
//        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(newSpriteName, newData, nameOffset, newSpriteName.length() + 1);
//        
//        this.data = newData;
//    }
    
//    // guesswork so far
//    
//    public String getEventCommandName_OLD(int command)
//    {
//        switch(command)
//        {
//            case EVENT_COMMAND__STOP_PROCESSING:
//                return "Stop processing";
//            case EVENT_COMMAND__IDENTITY:
//                return "Identity";
//            case EVENT_COMMAND__MOUSEOVER:
//                return "Mouseover";
//            case EVENT_COMMAND__TELEPORT:
//                return "Teleport";
//            case EVENT_COMMAND__OPEN_CHEST:
//                return "Open Chest";
//            case EVENT_COMMAND__SHOW_FACIAL_EXPRESSION:
//                return "Show Facial expression";
//            case EVENT_COMMAND__CHANGE_SPRITE:
//                return "Change Sprite";
//            case EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP:
//                return "Change 3dObject Face Bitmap";
//            case EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE:
//                return "Branch on condition true";
//            case EVENT_COMMAND__ADD_TARGET:
//                return "Add Target";
//            case EVENT_COMMAND__DELETE_TARGET:
//                return "Delete Target";
//            case EVENT_COMMAND__SET_TARGET:
//                return "Set Target";
//            case EVENT_COMMAND__CREATE_LOCAL_MONSTER:
//                return "Create local monster";
//            case EVENT_COMMAND__CAST_SPELL_FROM_LOCATION:
//                return "Cast spell from location";
//            case EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING:
//                return "Show local event String";
//            case EVENT_COMMAND__SHOW_NPCTEXT_STRING:
//                return "Show NPCText string";
//            case EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER:
//                return "modify next command by party member";
//            case EVENT_COMMAND__GOTO:
//                return "Goto";
//            case EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE:
//                return "On Level Reload Event:";
//            case EVENT_COMMAND__CHANGE_DIALOG_EVENT:
//                return "Change dialog event";
//            default:
//                return "UNKNOWN COMMAND 0x" + Integer.toHexString(command);
//        }
//    }

//    private int minExpectedArgCount_OLD(int command)
//    {
//        switch(command)
//        {
//            case EVENT_COMMAND__STOP_PROCESSING:
//                return 1;
//            case EVENT_COMMAND__IDENTITY:
//                return 4;
//            case EVENT_COMMAND__MOUSEOVER:
//                return 1;
//            case EVENT_COMMAND__TELEPORT:
//                return TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET + 2; // 1 char + zero-terminator for filename
//            case EVENT_COMMAND__OPEN_CHEST:
//                return 1;
//            case EVENT_COMMAND__SHOW_FACIAL_EXPRESSION:
//                return 2;
//            case EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP:
//                return CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET + 2;
//            case EVENT_COMMAND__CHANGE_SPRITE:
//                return CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET + 2;
//            case EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE:
//                return 1 + 4 + 1;
////                return getBranchOnConditionTrueArgsTargetTypeLength()
////	                + getBranchOnConditionTrueArgsTargetNumberLength()
////	                + getBranchOnConditionTrueArgsGotoSequenceNumberLength();
//            case EVENT_COMMAND__DELETE_TARGET:
//                return 5;
//            case EVENT_COMMAND__ADD_TARGET:
//                return 5;
//            case EVENT_COMMAND__SET_TARGET:
//                return 5;
//            case EVENT_COMMAND__CREATE_LOCAL_MONSTER:
//                return 15;
//            case EVENT_COMMAND__CAST_SPELL_FROM_LOCATION:
//                return 27;
//            case EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING:
//                return 4;
//            case EVENT_COMMAND__SHOW_NPCTEXT_STRING:
//                return 4;
//            case EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER:
//                return 1;
//            case EVENT_COMMAND__GOTO:
//                return 1;
//            case EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE:
//                return 1;
//            case EVENT_COMMAND__CHANGE_DIALOG_EVENT:
//                return 9;
//            default:
//                return -1;
//        }
//    }
//
//    private int maxExpectedArgCount_OLD(int command)
//    {
//        switch(command)
//        {
//            case EVENT_COMMAND__STOP_PROCESSING:
//                return 1;
//            case EVENT_COMMAND__IDENTITY:
//                return 4;
//            case EVENT_COMMAND__MOUSEOVER:
//                return 1;
//            case EVENT_COMMAND__TELEPORT:
//                return TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET + TELEPORT_ARGS_DESTINATION_FILENAME;
//            case EVENT_COMMAND__OPEN_CHEST:
//                return 1;
//            case EVENT_COMMAND__SHOW_FACIAL_EXPRESSION:
//                return 2;
//            case EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP:
//                return CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME;
//            case EVENT_COMMAND__CHANGE_SPRITE:
//                return CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET + CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME;
//            case EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE:
//                return 1 + 4 + 1;
////                return getBranchOnConditionTrueArgsTargetTypeLength()
////                + getBranchOnConditionTrueArgsTargetNumberLength()
////                + getBranchOnConditionTrueArgsGotoSequenceNumberLength();
//            case EVENT_COMMAND__ADD_TARGET:
//                return 5;
//            case EVENT_COMMAND__DELETE_TARGET:
//                return 5;
//            case EVENT_COMMAND__SET_TARGET:
//                return 5;
//            case EVENT_COMMAND__CREATE_LOCAL_MONSTER:
//                return 15;
//            case EVENT_COMMAND__CAST_SPELL_FROM_LOCATION:
//                return 27;
//            case EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING:
//                return 4;
//            case EVENT_COMMAND__SHOW_NPCTEXT_STRING:
//                return 4;
//            case EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER:
//                return 1;
//            case EVENT_COMMAND__GOTO:
//                return 1;
//            case EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE:
//                return 1;
//            case EVENT_COMMAND__CHANGE_DIALOG_EVENT:
//                return 9;
//            default:
//                return -1;
//        }
//    }

//    private String getEventDescriptionForCommand_OLD()
//    {
//        int minExpectedArgCount =  minExpectedArgByteCount(this.eventCommandNumber);
//        int maxExpectedArgCount =  maxExpectedArgByteCount(this.eventCommandNumber);
//        if (minExpectedArgCount == maxExpectedArgCount)
//        {
//            if (-1 != minExpectedArgCount)
//            {
//                if (minExpectedArgCount != (this.data.length - EVENT_COMMAND_ARGS_OFFSET))
//                    return ": Expected " + minExpectedArgCount + " arguments but had " + (this.data.length - EVENT_COMMAND_ARGS_OFFSET) + " arguments.";
//            }
//        }
//        else
//        {
//            if (-1 != minExpectedArgCount)
//            {
//                int actualArgs = this.data.length - EVENT_COMMAND_ARGS_OFFSET;
//                if ((actualArgs < minExpectedArgCount) || (actualArgs > maxExpectedArgCount))
//                    return ": Expected between " + minExpectedArgCount + " and " + maxExpectedArgCount + " arguments but had " + (this.data.length - EVENT_COMMAND_ARGS_OFFSET) + " arguments.";
//            }
//        }
//        
//        int arg0;
//        int arg1;
//        int arg2;
//        int arg3;
//        int arg4;
//        int arg5;
//        int arg6;
//        int arg7;
//        
//        int byteError;
//        int shortError;
//        int intError;
//        
//        switch(this.eventCommandNumber)
//        {
//            case EVENT_COMMAND__STOP_PROCESSING:
//                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + STOP_PROCESSING_ARGS_UNKNOWN1_OFFSET]);
//                if (0 != arg0)  return ": Unexpected arg0=" + arg0;
//                return "";
//            case EVENT_COMMAND__IDENTITY:
//                arg0 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + IDENTITY_ARGS_2DEVENT_ID_OFFSET);
//                return " 2dEvent#" + arg0;
//            case EVENT_COMMAND__MOUSEOVER:
//                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + MOUSEOVER_ARGS_EVENT_STR_ID_OFFSET]);
//                return " Local Event Str#" + arg0;
//            case EVENT_COMMAND__TELEPORT:
//                // coordinates
//                arg0 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_X_COORD_OFFSET);
//                arg1 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_Y_COORD_OFFSET);
//                arg2 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_Z_COORD_OFFSET);
//                
//                // horizontal orientation
//                arg3 = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_HORIZONTAL_ORIENTATION_OFFSET);
//                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_UNKNOWN1_OFFSET);
//                if (0 != shortError)
//                {
//                    return ": Unexpected nonzero value for offset " + TELEPORT_ARGS_UNKNOWN1_OFFSET + " - " + shortError;
//                }
//                
//                // vertical orientation
//                arg4 = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_VERTICAL_ORIENTATION_OFFSET);
//                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_UNKNOWN2_OFFSET);
//                if (0 != shortError)
//                {
//                    return ": Unexpected nonzero value for offset " + TELEPORT_ARGS_UNKNOWN2_OFFSET + " - " + shortError;
//                }
//
//                // unknown
//                intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_UNKNOWN3_OFFSET);
//                if (0 != intError)
//                {
//                    return ": Unexpected nonzero value for offset " + TELEPORT_ARGS_UNKNOWN3_OFFSET + " - " + intError;
//                }
//                
//                arg5 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET]);
//                arg6 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_MINIICON_OFFSET]);
//                String miniIconName = getMiniIconName(arg6);
//                if (null == miniIconName)
//                {
//                    return ": Unexpected mini icon #" + arg6;
//                }
//                String levelName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET, this.data.length - (EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET));
//                String viaString = " via dialog #" + arg5 + " and mini icon " + miniIconName;
//                if ((0 == arg5) && (0 == arg6))  viaString = " immediately";
//                return " to " + levelName + " at (" + arg0 + "," + arg1 + "," + arg2 + ") " + " facing (" + arg3 + "," + arg4 + ")" + viaString;
//            case EVENT_COMMAND__OPEN_CHEST:
//                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + OPEN_CHEST_ARGS_CHEST_NUMBER_OFFSET]);
//                return " #" + arg0;
//            case EVENT_COMMAND__SHOW_FACIAL_EXPRESSION:
//                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + SHOW_FACIAL_EXPRESSION_ARGS_AFFECTED_OFFSET]);
//                arg1 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + SHOW_FACIAL_EXPRESSION_ARGS_IMAGE_ID_OFFSET]);
//                return " #" + arg1 + " for " + affectedFacialTarget(arg0);
//            case EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP:
//                arg0 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_3D_OBJECT_NUMBER_OFFSET);
//                arg1 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_FACE_OFFSET);
//                String newBitmapName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET, this.data.length - (EVENT_COMMAND_ARGS_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET));
//                return " object #" + arg0 + ", face #" + arg1 + " to " + newBitmapName;
//            case EVENT_COMMAND__CHANGE_SPRITE:
//                arg0 = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_SPRITE_NUMBER);
//                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_UNKNOWN1_OFFSET);
//                if (0 != shortError)
//                {
//                    return ": Unexpected nonzero value for offset " + CHANGE_SPRITE_ARGS_UNKNOWN1_OFFSET + " - " + shortError;
//                }
//                
//                byteError = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_UNKNOWN2_OFFSET]);
//                if (1 != byteError)
//                {
//                    return ": Unexpected non-1 value for offset " + CHANGE_SPRITE_ARGS_UNKNOWN2_OFFSET + " - " + byteError;
//                }
//
//                String newSpriteName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET, this.data.length - (EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET));
//                return " #" + arg0 + " to " + newSpriteName;
//            case EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE:
//                arg0 = ByteConversions.getIntegerFromDataInByteArrayAtPositionForLength(
//                        this.data,
//                        EVENT_COMMAND_ARGS_OFFSET + 0,
//                        1);
//                arg1 = ByteConversions.getIntegerFromDataInByteArrayAtPositionForLength(
//                        this.data,
//                        EVENT_COMMAND_ARGS_OFFSET + 1,
//                        4);
//                arg2 = ByteConversions.getIntegerFromDataInByteArrayAtPositionForLength(
//                        this.data,
//                        EVENT_COMMAND_ARGS_OFFSET + 5,
//                        1);
////                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_TYPE_OFFSET]);
////                arg1 = ByteConversions.getUnsignedShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_NUMBER_OFFSET);
////                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + BRANCH_ON_CONDITION_TRUE_ARGS_UNKNOWN1_OFFSET);
////                if (0 != shortError)
////                {
////                    return ": Unexpected nonzero value for offset " + BRANCH_ON_CONDITION_TRUE_ARGS_UNKNOWN1_OFFSET + " - " + shortError;
////                }
////                arg2 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + BRANCH_ON_CONDITION_TRUE_ARGS_GOTO_SEQUENCE_NUMBER_OFFSET]);
//                return " - if ( " + getTarget(arg0, arg1) + " ) goto event sequence #" + getEventNumber() + "-" + arg2;
//            case EVENT_COMMAND__ADD_TARGET:
//                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + ADD_TARGET_ARGS_TARGET_TYPE_OFFSET]);
//                arg1 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + ADD_TARGET_ARGS_TARGET_NUMBER_OFFSET);
//                return " - Add " + getTarget(arg0, arg1);
//            case EVENT_COMMAND__DELETE_TARGET:
//                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + DELETE_TARGET_ARGS_TARGET_TYPE_OFFSET]);
//                arg1 = ByteConversions.getUnsignedShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + DELETE_TARGET_ARGS_TARGET_NUMBER_OFFSET);
//                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + DELETE_TARGET_ARGS_UNKNOWN1_OFFSET);
//                if (0 != shortError)
//                {
//                    return ": Unexpected nonzero value for offset " + DELETE_TARGET_ARGS_UNKNOWN1_OFFSET + " - " + shortError;
//                }
//                if (TARGET_TYPE__ITEM != arg0)
//                {
//                    return ": Non-inventory delete #" + arg0;
//                }
//                return " - Delete " + getTarget(arg0, arg1) + " from inventory";
//            case EVENT_COMMAND__SET_TARGET:
//                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + SET_TARGET_ARGS_TARGET_TYPE_OFFSET]);
//                arg1 = ByteConversions.getUnsignedShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SET_TARGET_ARGS_TARGET_NUMBER_OFFSET);
//                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SET_TARGET_ARGS_UNKNOWN1_OFFSET);
//                if (0 != shortError)
//                {
//                    return ": Unexpected nonzero value for offset " + SET_TARGET_ARGS_UNKNOWN1_OFFSET + " - " + shortError;
//                }
//                return " " + getTarget(arg0, arg1);
//                
//            case EVENT_COMMAND__CREATE_LOCAL_MONSTER:
//                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CREATE_LOCAL_MONSTER_ARGS_MONSTER_SPECIES_OFFSET]);
//                arg1 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CREATE_LOCAL_MONSTER_ARGS_MONSTER_SUBSPECIES__OFFSET]);
//                arg2 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CREATE_LOCAL_MONSTER_ARGS_COUNT_OFFSET]);
//                arg3 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CREATE_LOCAL_MONSTER_ARGS_X_OFFSET);
//                arg4 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CREATE_LOCAL_MONSTER_ARGS_Y_OFFSET);
//                arg5 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CREATE_LOCAL_MONSTER_ARGS_Z_OFFSET);
//                return arg2 + " species #" + arg0 + "-" + arg1 + ", at (" + arg3 + "," + arg4 + "," + arg5 + ")";
//            case EVENT_COMMAND__CAST_SPELL_FROM_LOCATION:
//                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_SPELL_NUMBER_OFFSET]);
//                arg1 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_RANKING_OFFSET]);
//                arg2 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_LEVEL_OFFSET]);
//                arg3 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_X_OFFSET);
//                arg4 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Y_OFFSET);
//                arg5 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Z_OFFSET);
//                // unknown
//                intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN2_OFFSET);
//                if (0 != intError)
//                {
//                    return ": Unexpected nonzero value for offset " + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN2_OFFSET + " - " + intError;
//                }
//                // unknown
//                intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN3_OFFSET);
//                if (0 != intError)
//                {
//                    return ": Unexpected nonzero value for offset " + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN3_OFFSET + " - " + intError;
//                }
//                // unknown
//                intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN4_OFFSET);
//                if (0 != intError)
//                {
//                    return ": Unexpected nonzero value for offset " + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN4_OFFSET + " - " + intError;
//                }
//                return ": spell #" + arg0 + ", ranking " + arg1 + ", level " + arg2 + ", at (" + arg3 + "," + arg4 + "," + arg5 + ")";
//            case EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING:
//                arg0 = ByteConversions.getUnsignedShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SHOW_LOCAL_EVENT_STRING_ARGS_STR_ID_OFFSET);
//                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SHOW_LOCAL_EVENT_STRING_ARGS_UNKNOWN1_OFFSET);
//                if (0 != shortError)
//                {
//                    return ": Unexpected nonzero value for offset " + SHOW_LOCAL_EVENT_STRING_ARGS_UNKNOWN1_OFFSET  + " - " + shortError;
//                }
//                return " #" + arg0;
//            case EVENT_COMMAND__SHOW_NPCTEXT_STRING:
//                arg0 = ByteConversions.getUnsignedShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SHOW_NPCTEXT_STRING_ARGS_STR_ID_OFFSET);
//                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SHOW_NPCTEXT_STRING_ARGS_UNKNOWN1_OFFSET);
//                if (0 != shortError)
//                {
//                    return ": Unexpected nonzero value for offset " + SHOW_NPCTEXT_STRING_ARGS_UNKNOWN1_OFFSET  + " - " + shortError;
//                }
//                return " #" + arg0;
//            case EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER:
//                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER_ARGS_AFFECTED_OFFSET]);
//                return " : " + affectedFacialTarget(arg0);
//            case EVENT_COMMAND__GOTO:
//                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + GOTO_ARGS_SEQUENCE_NUMBER_OFFSET]);
//                return " sequence #" + getEventNumber() + "-" + arg0;
//            case EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE:
//                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + STOP_PROCESSING_ARGS_UNKNOWN1_OFFSET]);
//                if (0 != arg0)  return ": Unexpected arg0=" + arg0;
//                return "";
//            case EVENT_COMMAND__CHANGE_DIALOG_EVENT:
//                arg0 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_DIALOG_EVENT_ARGS_NPCDATA_OFFSET);
//                arg1 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CHANGE_DIALOG_EVENT_ARGS_NPC_MENU_INDEX_OFFSET]);
//                arg2 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_DIALOG_EVENT_ARGS_NEW_GLOBAL_EVENT_NUMBER_OFFSET);
//                return " for NPC #" + arg0 + ", index " + arg1 + " to global event #" + arg2; 
//            default:
//                return " - UNKNOWN";
//        }
//    }

//  public boolean isUnderstood_OLD()
//  {
//      int minExpectedArgCount =  minExpectedArgByteCount(this.eventCommandNumber);
//      int maxExpectedArgCount =  maxExpectedArgByteCount(this.eventCommandNumber);
//      if (minExpectedArgCount == maxExpectedArgCount)
//      {
//          if (-1 != minExpectedArgCount)
//          {
//              if (minExpectedArgCount != (this.data.length - EVENT_COMMAND_ARGS_OFFSET))
//                  return false;
//          }
//      }
//      else
//      {
//          if (-1 != minExpectedArgCount)
//          {
//              int actualArgs = this.data.length - EVENT_COMMAND_ARGS_OFFSET;
//              if ((actualArgs < minExpectedArgCount) || (actualArgs > maxExpectedArgCount))
//                  return false;
//          }
//      }
//      
//      int arg0;
//      int byteError;
//      int shortError;
//      int intError;
//      
//      switch(this.eventCommandNumber)
//      {
//          case EVENT_COMMAND__STOP_PROCESSING:
//              arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + STOP_PROCESSING_ARGS_UNKNOWN1_OFFSET]);
//              if (0 != arg0)  return false;
//              return true;
//          case EVENT_COMMAND__IDENTITY:
//              return true;
//          case EVENT_COMMAND__MOUSEOVER:
//              return true;
//          case EVENT_COMMAND__TELEPORT:
//              shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_UNKNOWN1_OFFSET);
//              if (0 != shortError)
//              {
//                  return false;
//              }
//              
//              shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_UNKNOWN2_OFFSET);
//              if (0 != shortError)
//              {
//                  return false;
//              }
//
//              intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_UNKNOWN3_OFFSET);
//              if (0 != intError)
//              {
//                  return false;
//              }
//              
//              int arg6 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_MINIICON_OFFSET]);
//              String miniIconName = getMiniIconName(arg6);
//              if (null == miniIconName)
//              {
//                  return false;
//              }
//              return true;
//          case EVENT_COMMAND__OPEN_CHEST:
//              return true;
//          case EVENT_COMMAND__SHOW_FACIAL_EXPRESSION:
//              arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + STOP_PROCESSING_ARGS_UNKNOWN1_OFFSET]);
//              if (arg0 > 5)  return false; // above this has been random
//              return true;
//          case EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP:
//              return true;
//          case EVENT_COMMAND__CHANGE_SPRITE:
//              shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_UNKNOWN1_OFFSET);
//              if (0 != shortError)
//              {
//                  return false;
//              }
//              
//              byteError = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_UNKNOWN2_OFFSET]);
//              if (1 != byteError)
//              {
//                  return false;
//              }
//              return true;
//          case EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE:
//              arg0 = ByteConversions.getIntegerFromDataInByteArrayAtPositionForLength(
//                      this.data,
//                      EVENT_COMMAND_ARGS_OFFSET + 0,
//                      1);
//              if (false == isTargetTypeUnderstood(this.eventCommandNumber, arg0))
//              {
//                  return false;
//              }
//              return true;
//         case EVENT_COMMAND__ADD_TARGET:
//             arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + ADD_TARGET_ARGS_TARGET_TYPE_OFFSET]);
//             if (false == isTargetTypeUnderstood(this.eventCommandNumber, arg0))
//             {
//                 return false;
//             }
//             return true;
//         case EVENT_COMMAND__DELETE_TARGET:
//             arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + DELETE_TARGET_ARGS_TARGET_TYPE_OFFSET]);
//             shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + DELETE_TARGET_ARGS_UNKNOWN1_OFFSET);
//             if (0 != shortError)
//             {
//                 return false;
//             }
//             if (false == isTargetTypeUnderstood(this.eventCommandNumber, arg0))
//             {
//                 return false;
//             }
//             return true;
//          case EVENT_COMMAND__SET_TARGET:
//              arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + SET_TARGET_ARGS_TARGET_TYPE_OFFSET]);
//              shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SET_TARGET_ARGS_UNKNOWN1_OFFSET);
//              if (0 != shortError)
//              {
//                  return false;
//              }
//              if (false == isTargetTypeUnderstood(this.eventCommandNumber, arg0))
//              {
//                  return false;
//              }
//              return true;
//          case EVENT_COMMAND__CREATE_LOCAL_MONSTER:
//              return true;
//          case EVENT_COMMAND__CAST_SPELL_FROM_LOCATION:
//              // unknown
//              intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN2_OFFSET);
//              if (0 != intError)
//              {
//                  return false;
//              }
//              // unknown
//              intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN3_OFFSET);
//              if (0 != intError)
//              {
//                  return false;
//              }
//              // unknown
//              intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN4_OFFSET);
//              if (0 != intError)
//              {
//                  return false;
//              }
//              return true;
//          case EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING:
//              shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SHOW_LOCAL_EVENT_STRING_ARGS_UNKNOWN1_OFFSET);
//              if (0 != shortError)
//              {
//                  return false;
//              }
//              return true;
//          case EVENT_COMMAND__SHOW_NPCTEXT_STRING:
//              shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SHOW_NPCTEXT_STRING_ARGS_UNKNOWN1_OFFSET);
//              if (0 != shortError)
//              {
//                  return false;
//              }
//              return true;
//          case EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER:
//              arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER_ARGS_AFFECTED_OFFSET]);
//              if (arg0 > 5)  return false; // above this has been random
//              return true;
//          case EVENT_COMMAND__GOTO:
//              return true;
//          case EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE:
//              arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + STOP_PROCESSING_ARGS_UNKNOWN1_OFFSET]);
//              if (0 != arg0)  return false;
//              return true;
//          case EVENT_COMMAND__CHANGE_DIALOG_EVENT:
//              return true;
//          default:
//              return false;
//      }
//  }

    public int[] getArgumentTypeArrayForCommandType(int commandType)
    {
        Map argumentTypeArrayByCommandTypeMap = this.getArgumentTypeArrayByCommandTypeMap();
        Integer key = new Integer(commandType);
        int argumentTypeArray[] = (int [])argumentTypeArrayByCommandTypeMap.get(key);
        return argumentTypeArray;
    }

    protected ArgumentTypeDisplayInfo[] getArgumentTypeDisplayInfoArrayForCommandType(int commandType)
    {
        Map argumentTypeDisplayInfoArrayByCommandTypeMap = this.getArgumentTypeDisplayInfoArrayByCommandTypeMap();
        Integer key = new Integer(commandType);
        Object value = argumentTypeDisplayInfoArrayByCommandTypeMap.get(key);
        ArgumentTypeDisplayInfo argumentTypeDisplayInfoArray[] = (ArgumentTypeDisplayInfo [])value;
        return argumentTypeDisplayInfoArray;
    }

    public String[] getArgumentTypePrefixLabelArrayForCommandType(int commandType)
    {
        ArgumentTypeDisplayInfo argumentTypeDisplayInfoArray[] = getArgumentTypeDisplayInfoArrayForCommandType(commandType);
        String prefixLabelArray[] = new String[argumentTypeDisplayInfoArray.length];
        for (int index = 0; index < argumentTypeDisplayInfoArray.length; index++)
        {
            ArgumentTypeDisplayInfo info = argumentTypeDisplayInfoArray[index];
            prefixLabelArray[index] = info.getPrefixLabel();
        }
        
        return prefixLabelArray;
    }

    public String[] getArgumentTypeSuffixLabelArrayForCommandType(int commandType)
    {
        ArgumentTypeDisplayInfo argumentTypeDisplayInfoArray[] = getArgumentTypeDisplayInfoArrayForCommandType(commandType);
        String suffixLabelArray[] = new String[argumentTypeDisplayInfoArray.length];
        for (int index = 0; index < argumentTypeDisplayInfoArray.length; index++)
        {
            ArgumentTypeDisplayInfo info = argumentTypeDisplayInfoArray[index];
            suffixLabelArray[index] = info.getSuffixLabel();
        }
        
        return suffixLabelArray;
    }

    public int[] getArgumentTypeSortOrderingArrayForCommandType(int commandType)
    {
        ArgumentTypeDisplayInfo argumentTypeDisplayInfoArray[] = getArgumentTypeDisplayInfoArrayForCommandType(commandType);
        int sortOrderingArray[] = new int[argumentTypeDisplayInfoArray.length];
        for (int index = 0; index < argumentTypeDisplayInfoArray.length; index++)
        {
            ArgumentTypeDisplayInfo info = argumentTypeDisplayInfoArray[index];
            sortOrderingArray[index] = info.getSortOrdering();
        }
        
        return sortOrderingArray;
    }

	public boolean getArgumentTypeIsIndirectFor(Event event, int argumentIndex)
	{
        ArgumentTypeDisplayInfo argumentTypeDisplayInfoArray[] = getArgumentTypeDisplayInfoArrayForCommandType(event.getCommandType());
        ArgumentTypeDisplayInfo info = argumentTypeDisplayInfoArray[argumentIndex];
        return info.isIndirect(event, argumentIndex);
	}
	
	public IndirectValue getArgumentTypeIndirectValueFor(Event event, int argumentIndex)
	{
        ArgumentTypeDisplayInfo argumentTypeDisplayInfoArray[] = getArgumentTypeDisplayInfoArrayForCommandType(event.getCommandType());
        ArgumentTypeDisplayInfo info = argumentTypeDisplayInfoArray[argumentIndex];
        return info.getIndirectValue(event, argumentIndex);
	}

	public int getArgumentTypeDataSize(int argumentType)
    {
        Map<Integer,Integer> sizeByArgumentTypeMap = this.getSizeByArgumentTypeMap();
        Integer dataFieldSizeNumber = sizeByArgumentTypeMap.get(new Integer(argumentType));
        if (null == dataFieldSizeNumber)
        {
            throw new RuntimeException("Unable to determine data field size for argumentType: " + argumentType);
        }
        final int dataFieldSize = dataFieldSizeNumber.intValue();
        return dataFieldSize;
    }

    public int getArgumentTypeAtIndexForCommandType(int index, int commandType)
    {
        int argumentTypeArray[] = getArgumentTypeArrayForCommandType(commandType);
        int argumentType = argumentTypeArray[index];
        return argumentType;
    }

    public Map getArgumentTypeChoicesArray(int argumentType)
    {
        Map map = new HashMap();
        String[] labels = null;
        switch (argumentType)
        {
            case ARGUMENT_TYPE__TARGET_TYPE:
                Integer targetTypes[] = getTargetTypes();
                for (int targetTypeIndex = 0; targetTypeIndex < targetTypes.length; targetTypeIndex++)
                {
                    Number targetTypeNumber = targetTypes[targetTypeIndex];
                    if (null != targetTypeNumber)
                    {
                        int targetType = targetTypeNumber.intValue();
                        String label = getTargetTypeName(targetType);
                        Object value = targetTypeNumber;
                        map.put(label, value);
                    }
                    else
                    {
                        String label = "Unknown TargetType #" + targetTypeIndex;
                        Object value = "Unknown TargetType #" + targetTypeIndex;
                        map.put(label, value);
                    }
                }
                break;
            case ARGUMENT_TYPE__FACET_ATTRIBUTE_TYPE:
                Map<Integer,String> facetAttributeNameByFacetAttributeTypeMap = getFacetAttributeNameByFacetAttributeTypeMap();
                Iterator<Map.Entry<Integer,String>> entryIterator = facetAttributeNameByFacetAttributeTypeMap.entrySet().iterator();
                while (entryIterator.hasNext())
                {
                    Map.Entry<Integer,String> entry = entryIterator.next();
                    String label = entry.getValue();
                    Integer bitPositionNumber = entry.getKey();
                    int bitPosition = bitPositionNumber.intValue();
                    Integer value = new Integer(1 << bitPosition);
                    // IMPLEMENT: check that label wasn't already in the map with another value?
                    map.put(label, value);
                }
                break;
            case ARGUMENT_TYPE__PARTY_MEMBER:
                labels = getPartyMemberChoiceArray();
            case ARGUMENT_TYPE__MINI_ICON_NUMBER:
                if (labels == null)
                {
                    labels = getMiniIconNameChoiceArray();
                }
                
                // code common to all of the above cases
                for (int labelIndex = 0; labelIndex < labels.length; labelIndex++)
                {
                    String label = labels[labelIndex];
                    Object value = new Integer(labelIndex);
                    map.put(label, value);
                }
                break;
            default:
                throw new RuntimeException("No choices list for argumentType: " + argumentType);
        }
        
        return map;
    }
 
    public int getArgumentDataType(int argumentType)
    {
        Map<Integer,Integer> argumentDataTypeByArgumentTypeMap = this.getArgumentDataTypeByArgumentTypeMap();
        Integer argumentDataTypeNumber = argumentDataTypeByArgumentTypeMap.get(new Integer(argumentType));
        if (null == argumentDataTypeNumber)
        {
            throw new RuntimeException("Unable to determine argumentDataType for argumentType: " + argumentType);
        }
        final int argumentDataType = argumentDataTypeNumber.intValue();
        return argumentDataType;
    }


    // TODO: This doesn't seem appropriate, yet it's the only place for it right now
	private Str strResource = null;
	public void setStrResource(Str strResource)
	{
		this.strResource = strResource;
	}
	public Str getStrResource()
	{
		return this.strResource;
	}

	protected boolean hasIndirectValueForTargetType(int targetType) {
		switch (targetType)
		{
			case TARGET_TYPE__AWARD: 
			case TARGET_TYPE__ITEM: 
			case TARGET_TYPE__NPC_IN_PARTY:
			case TARGET_TYPE__NPC_PROFESSION_IN_PARTY:
			case TARGET_TYPE__EVENT_AUTONOTE:
			case TARGET_TYPE__CLASS:
			case TARGET_TYPE__QUEST_BIT:
				return true;
			default:
				return false;
		}
	}
	protected IndirectValue<Integer,String> getIndirectValueForTargetType(int targetType) {
		switch (targetType)
		{
		case TARGET_TYPE__NPC_PROFESSION_IN_PARTY:
			return new IndirectValue<Integer,String>() {
				public int getDirectArgumentDataType() {
					return EventFormat.ARGUMENT_DATA_TYPE__STRING;
				}
				public Integer getIndirectValueForDirectValue(String directValue) {
					throw new UnimplementedMethodException("npcprof.txt resource getIndirectValueForDirectValue(String directValue)");
				}
				public String getDirectValueForIndirectValue(Integer indirectValue) {
					NpcProfTxt npcProfTxt = ResourceServer.getInstance().getNpcProfTxt();
					if (null == npcProfTxt)
					{
						return "WARNING: **No npcprof.txt resource found**";
					}
					try {
						return npcProfTxt.getNameAtIndex(indirectValue.intValue());
					} catch (IndexOutOfBoundsException e) {
						return "WARNING: **No profession value " + indirectValue.intValue() + " found in npcprof.txt resource file**";
					}
				}
			};
		case TARGET_TYPE__EVENT_AUTONOTE:
			return new IndirectValue<Integer,String>() {
				public int getDirectArgumentDataType() {
					return EventFormat.ARGUMENT_DATA_TYPE__STRING;
				}
				public Integer getIndirectValueForDirectValue(String directValue) {
					throw new UnimplementedMethodException("Autonote.txt resource getIndirectValueForDirectValue(String directValue)");
				}
				public String getDirectValueForIndirectValue(Integer indirectValue) {
					AutonoteTxt autonoteTxt = ResourceServer.getInstance().getAutonoteTxt();
					if (null == autonoteTxt)
					{
						return "WARNING: **No Autonote.txt resource found**";
					}
					try {
						return autonoteTxt.getStringsList().get(indirectValue.intValue());
					} catch (IndexOutOfBoundsException e) {
						return "WARNING: **No autonote value " + indirectValue.intValue() + " found in Autonote.txt resource file**";
					}
				}
			};
		case TARGET_TYPE__AWARD: 
			return new IndirectValue<Integer,String>() {
				public int getDirectArgumentDataType() {
					return EventFormat.ARGUMENT_DATA_TYPE__STRING;
				}
				public Integer getIndirectValueForDirectValue(String directValue) {
					throw new UnimplementedMethodException("awards.Txt resource getIndirectValueForDirectValue(String directValue)");
				}
				public String getDirectValueForIndirectValue(Integer indirectValue) {
					AwardsTxt awardsTxt = ResourceServer.getInstance().getAwardsTxt();
					if (null == awardsTxt)
					{
						return "WARNING: **No awards.Txt resource found**";
					}
					try {
						return awardsTxt.getStringsList().get(indirectValue.intValue());
					} catch (IndexOutOfBoundsException e) {
						return "WARNING: **No award value " + indirectValue.intValue() + " found in awards.Txt resource file**";
					}
				}
			};
		case TARGET_TYPE__NPC_IN_PARTY: 
			return new IndirectValue<Integer,String>() {
				public int getDirectArgumentDataType() {
					return EventFormat.ARGUMENT_DATA_TYPE__STRING;
				}
				public Integer getIndirectValueForDirectValue(String directValue) {
					throw new UnimplementedMethodException("NPCdata.txt resource getIndirectValueForDirectValue(String directValue)");
				}
				public String getDirectValueForIndirectValue(Integer indirectValue) {
					NPCdataTxt npcDataTxt = ResourceServer.getInstance().getNPCDataTxt();
					if (null == npcDataTxt)
					{
						return "WARNING: **No NPCdata.txt resource found**";
					}
					try {
						return npcDataTxt.getNameAtIndex(indirectValue.intValue());
					} catch (IndexOutOfBoundsException e) {
						return "WARNING: **No item value " + indirectValue.intValue() + " found in NPCdata.txt resource file**";
					}
				}
			};
		case TARGET_TYPE__ITEM: 
			return new IndirectValue<Integer,String>() {
				public int getDirectArgumentDataType() {
					return EventFormat.ARGUMENT_DATA_TYPE__STRING;
				}
				public Integer getIndirectValueForDirectValue(String directValue) {
					throw new UnimplementedMethodException("items.Txt resource getIndirectValueForDirectValue(String directValue)");
				}
				public String getDirectValueForIndirectValue(Integer indirectValue) {
					ItemsTxt itemsTxt = ResourceServer.getInstance().getItemsTxt();
					if (null == itemsTxt)
					{
						return "WARNING: **No items.Txt resource found**";
					}
					try {
						return itemsTxt.getItemNameAtIndex(indirectValue.intValue());
					} catch (IndexOutOfBoundsException e) {
						return "WARNING: **No item value " + indirectValue.intValue() + " found in items.Txt resource file**";
					}
				}
			};
		case TARGET_TYPE__QUEST_BIT: 
			return new IndirectValue<Integer,String>() {
				public int getDirectArgumentDataType() {
					return EventFormat.ARGUMENT_DATA_TYPE__STRING;
				}
				public Integer getIndirectValueForDirectValue(String directValue) {
					throw new UnimplementedMethodException("Quests.txt resource getIndirectValueForDirectValue(String directValue)");
				}
				public String getDirectValueForIndirectValue(Integer indirectValue) {
					QuestsTxt questsTxt = ResourceServer.getInstance().getQuestsTxt();
					if (null == questsTxt)
					{
						return "WARNING: **No Quests.txt resource found**";
					}
					try {
						return questsTxt.getNameAtIndex(indirectValue.intValue());
					} catch (IndexOutOfBoundsException e) {
						return "WARNING: **No name value " + indirectValue.intValue() + " found in Quests.txt resource file**";
					}
				}
			};
		case TARGET_TYPE__CLASS: 
			return new IndirectValue<Integer,String>() {
				public int getDirectArgumentDataType() {
					return EventFormat.ARGUMENT_DATA_TYPE__STRING;
				}
				public Integer getIndirectValueForDirectValue(String directValue) {
					throw new UnimplementedMethodException("class.txt resource getIndirectValueForDirectValue(String directValue)");
				}
				public String getDirectValueForIndirectValue(Integer indirectValue) {
					ClassTxt classTxt = ResourceServer.getInstance().getClassTxt();
					if (null == classTxt)
					{
						return "WARNING: **No class.txt resource found**";
					}
					try {
						return classTxt.getNameAtIndex(indirectValue.intValue());
					} catch (IndexOutOfBoundsException e) {
						return "WARNING: **No value " + indirectValue.intValue() + " found in class.txt resource file**";
					}
				}
			};
		default:
			throw new UnimplementedMethodException("no indirect value for targetType " + targetType);
		}
	}
}
