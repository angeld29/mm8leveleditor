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

public class Creature implements Vertex3DValueHolder
{
    private static final int CREATURE_RECORD_LENGTH_MM6 = 548;
    private static final int CREATURE_RECORD_LENGTH_MM7 = 836;
    private static final int CREATURE_RECORD_LENGTH_MM8 = 972;
    
    private static final int CREATURE_NAME_OFFSET = 0;
    private static final int CREATURE_NAME_MAX_LENGTH = 32;
    
    private static final int ATTRIBUTE_TYPE_AFRAID = 0x8000;

    private static final int NPC_ID_OFFSET_MM6 = 32; // 2 bytes
    private static final int PADDING1_OFFSET_MM6 = 34; // 2 bytes

    private static final int ATTRIBUTE_TYPE_OFFSET_MM6 = 36; // 4 bytes
    
    private static final int CURRENT_HP_OFFSET_MM6 = 40; // 2 bytes

	/////////////////////////////////////
    private static final int NAME_OFFSET_OFFSET_MM6 = 44; // 4 bytes
    private static final int PICNAME_OFFSET_OFFSET_MM6 = 48; // 4 bytes

    private static final int MONSTER_ID_OFFSET_MM6 = 52; // 1 byte

    private static final int LEVEL_OFFSET_MM6 = 53; // 1 byte
    
    private static final int TREASURE_ITEM_PERCENT_OFFSET_MM6 = 54; // 1 byte
	private static final int TREASURE_DICE_COUNT_OFFSET_MM6 = 55; // 1 byte
	private static final int TREASURE_DICE_TYPE_OFFSET_MM6 = 56; // 1 byte
	private static final int TREASURE_LEVEL_OFFSET_MM6 = 57; // 1 byte
	private static final int TREASURE_ITEM_TYPE_OFFSET_MM6 = 58; // 1 byte
	private static final int FLY_OFFSET_MM6 = 59; // 1 byte, 0 or 1?
	
	private static final int MOVE_TYPE_OFFSET_MM6 = 60; // 1 byte
	private static final int AI_TYPE_OFFSET_MM6 = 61; // 1 byte
	private static final int HOSTILE_TYPE_OFFSET_MM6 = 62; // 1 byte
	
	private static final int PREFERRED_TARGET_OFFSET_MM6 = 63; // 1 byte
	// 8 is D

	private static final int SPECIAL_ABILITY_OFFSET_MM6 = 64; // 1 byte, guess
	private static final int SPECIAL_ABILITY_BONUS_OFFSET_MM6 = 65; // 1 byte

	private static final int ATTACK1_TYPE_OFFSET_MM6 = 66; //  1 byte
	private static final int ATTACK1_DAMAGE_DICE_COUNT_OFFSET_MM6 = 67; // 1 byte
	private static final int ATTACK1_DAMAGE_DICE_TYPE_OFFSET_MM6 = 68; // 1 byte
	private static final int ATTACK1_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM6 = 69; // 1 byte
	
	private static final int ATTACK1_MISSILE_TYPE_OFFSET_MM6 = 70; // 1 byte

	private static final int ATTACK2_PERCENTAGE_OFFSET_MM6 = 71; //  1 byte
	private static final int ATTACK2_TYPE_OFFSET_MM6 = 72; //  1 byte
	private static final int ATTACK2_DAMAGE_DICE_COUNT_OFFSET_MM6 = 73; // 1 byte
	private static final int ATTACK2_DAMAGE_DICE_TYPE_OFFSET_MM6 = 74; // 1 byte
	private static final int ATTACK2_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM6 = 75; // 1 byte
	private static final int ATTACK2_MISSILE_TYPE_OFFSET_MM6 = 76; // 1 byte

	private static final int SPELL_ATTACK_USE_PERCENTAGE_OFFSET_MM6 = 77; // 1 byte
	private static final int SPELL_ID_OFFSET_MM6 = 78; // 1 byte
	private static final int SPELL_RANK_AND_SKILL_LEVEL_OFFSET_MM6 = 79; // 1 byte
	
	private static final int FIRE_RESISTANCE_OFFSET_MM6 = 80; // 1 byte
	private static final int ELECTRICAL_RESISTANCE_OFFSET_MM6 = 81; // 1 byte
	private static final int COLD_RESISTANCE_OFFSET_MM6 = 82; // 1 byte
	private static final int POISON_RESISTANCE_OFFSET_MM6 = 83; // 1 byte
	private static final int PHYSICAL_RESISTANCE_OFFSET_MM6 = 84; // 1 byte
	private static final int MAGICAL_RESISTANCE_OFFSET_MM6 = 85; // 1 byte

	private static final int SPECIAL_ATTACK_TYPE_OFFSET_MM6 = 86; // 1 byte
	private static final int SPECIAL_ATTACK_DAMAGE_DICE_COUNT_OFFSET_MM6 = 87; // 1 byte
	private static final int SPECIAL_ATTACK_DAMAGE_DICE_TYPE_OFFSET_MM6 = 88; // 1 byte
	private static final int SPECIAL_ATTACK_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM6 = 89; // 1 byte

	private static final int UNKNOWN1_OFFSET_MM6 = 90; // 2 bytes

	private static final int HIT_POINTS_OFFSET_MM6 = 92; // 4 bytes
	private static final int ARMOR_CLASS_OFFSET_MM6 = 96; // 4 bytes
	private static final int EXPERIENCE_OFFSET_MM6 = 100; // 4 bytes
	private static final int ATTACK_SPEED_OFFSET_MM6 = 104; // 4 bytes
	private static final int ATTACK_RECOVERY_OFFSET_MM6 = 108; // 4 bytes

	private static final int UNKNOWN2_OFFSET_MM6 = 112; // 4 bytes
	
	private static final int RANGE_ATTACK_OFFSET_MM6 = 116; // 2 bytes, 0 is melee
	private static final int MONSTER_ID_TYPE_OFFSET_MM6 = 118; // 2 bytes
	private static final int PHYSICAL_RADIUS_OFFSET_MM6 = 120; // 2 bytes
	private static final int PHYSICAL_HEIGHT_OFFSET_MM6 = 122; // 2 bytes
	private static final int VELOCITY_OFFSET_MM6 = 124; // 2 bytes
	
	private static final int X_COORD_OFFSET_MM6 = 126; // 2 bytes
    private static final int Y_COORD_OFFSET_MM6 = 128; // 2 bytes
    private static final int Z_COORD_OFFSET_MM6 = 130; // 2 bytes
	private static final int VELOCITY_X_OFFSET_MM6 = 132; // 2 bytes
	private static final int VELOCITY_Y_OFFSET_MM6 = 134; // 2 bytes
	private static final int VELOCITY_Z_OFFSET_MM6 = 136; // 2 bytes
	private static final int FACING_OFFSET_MM6 = 138; // 2 bytes
	private static final int TILT_OFFSET_MM6 = 140; // 2 bytes
	private static final int ROOM_OFFSET_MM6 = 142; // 2 bytes
	private static final int CURRENT_ACTION_LENGTH_OFFSET_MM6 = 144; // 2 bytes
	private static final int STARTING_X_OFFSET_MM6 = 146; // 2 bytes
	private static final int STARTING_Y_OFFSET_MM6 = 148; // 2 bytes
	private static final int STARTING_Z_OFFSET_MM6 = 150; // 2 bytes
	private static final int GUARDING_X_OFFSET_MM6 = 152; // 2 bytes
	private static final int GUARDING_Y_OFFSET_MM6 = 154; // 2 bytes
	private static final int GUARDING_Z_OFFSET_MM6 = 156; // 2 bytes
	private static final int TETHER_DISTANCE_OFFSET_MM6 = 158; // 2 bytes, in pixels

	private static final int AI_STATE_OFFSET_MM6 = 160; // 2 bytes, in pixels
	private static final int GRAPHIC_STATE_OFFSET_MM6 = 162; // 2 bytes, in pixels
	private static final int ITEM_CARRIED_OFFSET_MM6 = 164; // 2 bytes, 00 means use table
	private static final int PADDING4_OFFSET_MM6 = 166; // 2 bytes
	private static final int CURRENT_ACTION_TIME_SO_FAR_OFFSET_MM6 = 168; // 4 bytes

	private static final int FRAME_TABLE_STANDING_OFFSET_MM6 = 172; // 2 bytes
	private static final int FRAME_TABLE_WALKING_OFFSET_MM6 = 174; // 2 bytes
	private static final int FRAME_TABLE_NEAR_ATTACK_OFFSET_MM6 = 176; // 2 bytes
	private static final int FRAME_TABLE_FAR_ATTACK_OFFSET_MM6 = 178; // 2 bytes
	private static final int FRAME_TABLE_STUN_OFFSET_MM6 = 180; // 2 bytes
	private static final int FRAME_TABLE_DYING_OFFSET_MM6 = 182; // 2 bytes
	private static final int FRAME_TABLE_DEAD_OFFSET_MM6 = 184; // 2 bytes
	private static final int FRAME_TABLE_FIDGETING_OFFSET_MM6 = 186; // 2 bytes

	private static final int SOUND_EFFECT_1_OFFSET_MM6 = 188; // 2 bytes
	private static final int SOUND_EFFECT_2_OFFSET_MM6 = 190; // 2 bytes
	private static final int SOUND_EFFECT_3_OFFSET_MM6 = 192; // 2 bytes
	private static final int SOUND_EFFECT_4_OFFSET_MM6 = 194; // 2 bytes

	private static final int ACTIVE_SPELL_NULL_MM6 = 0;
	private static final int ACTIVE_SPELL_CHARMED_MM6 = 1;
	private static final int ACTIVE_SPELL_CURSED_MM6 = 2;
	private static final int ACTIVE_SPELL_SHRUNK_MM6 = 3;
	private static final int ACTIVE_SPELL_AFRAID_MM6 = 4;
	private static final int ACTIVE_SPELL_STONED_MM6 = 5;
	private static final int ACTIVE_SPELL_PARALYZED_MM6 = 6;
	private static final int ACTIVE_SPELL_SLOWED_MM6 = 7;
	private static final int ACTIVE_SPELL_FEEBLEMINDED_MM6 = 8;

	private static final int NUMBER_OF_ACTIVE_SPELLS_MM6 = 14; // possibly wrong
	private static final int ACTIVE_SPELL_SIZE_MM6 = ActiveSpell.getRecordSize(); // 16 bytes
	private static final int SPECIAL_EFFECTS_START_OFFSET_MM6 = 196; // 480 total
	
	private static final int GROUP_OFFSET_MM6 = 420; // 4 bytes
	private static final int ALLY_OFFSET_MM6 = 424; // 4 bytes
	// 232 bytes in here
	
	private static final int SCHEDULE_SIZE_MM6 = Schedule.getRecordSize(); // 12 bytes
	private static final int SCHEDULE_1_OFFSET_MM6 = 428; // 12 bytes
	private static final int SCHEDULE_2_OFFSET_MM6 = 440; // 12 bytes
	private static final int SCHEDULE_3_OFFSET_MM6 = 452; // 12 bytes
	private static final int SCHEDULE_4_OFFSET_MM6 = 464; // 12 bytes
	private static final int SCHEDULE_5_OFFSET_MM6 = 476; // 12 bytes
	private static final int SCHEDULE_6_OFFSET_MM6 = 488; // 12 bytes
	private static final int SCHEDULE_7_OFFSET_MM6 = 500; // 12 bytes
	private static final int SCHEDULE_8_OFFSET_MM6 = 512; // 12 bytes

	private static final int SUMMONER_ID_OFFSET_MM6 = 524; // 4 bytes
	private static final int LAST_CHARACTER_ID_TO_HIT_OFFSET_MM6 = 528; // 4 bytes
	private static final int NAME_ID_OFFSET_MM6 = 532; // 4 bytes, 0 means use monster type name
	private static final int RESERVED_1_OFFSET_MM6 = 536; // 4 bytes
	private static final int RESERVED_2_OFFSET_MM6 = 540; // 4 bytes
	private static final int RESERVED_3_OFFSET_MM6 = 544; // 4 bytes

	////////////////////////////
	
    private static final int NPC_ID_OFFSET_MM7 = 32; // 2 bytes
    private static final int PADDING1_OFFSET_MM7 = 34; // 2 bytes

    private static final int ATTRIBUTE_TYPE_OFFSET_MM7 = 36; // 4 bytes
    
    private static final int CURRENT_HP_OFFSET_MM7 = 40; // 2 bytes

    ///////////////////////
    private static final int NAME_OFFSET_OFFSET_MM7 = 44; // 4 bytes
    private static final int PICNAME_OFFSET_OFFSET_MM7 = 48; // 4 bytes

    private static final int LEVEL_OFFSET_MM7 = 52; // 1 byte
    
    private static final int TREASURE_ITEM_TYPE_WEAPON = 20;
    private static final int TREASURE_ITEM_TYPE_ARMOR = 21;
    private static final int TREASURE_ITEM_TYPE_MISC = 22;
    private static final int TREASURE_ITEM_TYPE_SWORD = 23;
    private static final int TREASURE_ITEM_TYPE_DAGGER = 24;
    private static final int TREASURE_ITEM_TYPE_AXE = 25;
    private static final int TREASURE_ITEM_TYPE_SPEAR = 26;
    private static final int TREASURE_ITEM_TYPE_BOW = 27;
    private static final int TREASURE_ITEM_TYPE_MACE = 28;
    private static final int TREASURE_ITEM_TYPE_CLUB = 29;
    private static final int TREASURE_ITEM_TYPE_STAFF = 30;
    private static final int TREASURE_ITEM_TYPE_LEATHER = 31;
    private static final int TREASURE_ITEM_TYPE_CHAIN = 32;
    private static final int TREASURE_ITEM_TYPE_PLATE = 33;
    private static final int TREASURE_ITEM_TYPE_SHIELD = 34;
    private static final int TREASURE_ITEM_TYPE_HELM = 35;
    private static final int TREASURE_ITEM_TYPE_BELT = 36;
    private static final int TREASURE_ITEM_TYPE_CLOAK = 37;
    private static final int TREASURE_ITEM_TYPE_GAUNTLETS = 38;
    private static final int TREASURE_ITEM_TYPE_BOOTS = 39;
    private static final int TREASURE_ITEM_TYPE_RING = 40;
    private static final int TREASURE_ITEM_TYPE_AMULET = 41;
    private static final int TREASURE_ITEM_TYPE_WAND = 42;
    private static final int TREASURE_ITEM_TYPE_SCROLL = 43;
    private static final int TREASURE_ITEM_TYPE_POTION = 44;
    private static final int TREASURE_ITEM_TYPE_REAGENT = 45;
    private static final int TREASURE_ITEM_TYPE_GEM = 46;
    private static final int TREASURE_ITEM_TYPE_ORE = 47;   // MM8 only
    private static final int TREASURE_ITEM_TYPE_GOLD = 50;

    
    private static final int TREASURE_ITEM_PERCENT_OFFSET_MM7 = 53; // 1 byte
	private static final int TREASURE_DICE_COUNT_OFFSET_MM7 = 54; // 1 byte
	private static final int TREASURE_DICE_TYPE_OFFSET_MM7 = 55; // 1 byte
	private static final int TREASURE_LEVEL_OFFSET_MM7 = 56; // 1 byte
	private static final int TREASURE_ITEM_TYPE_OFFSET_MM7 = 57; // 1 byte
	private static final int FLY_OFFSET_MM7 = 58; // 1 byte, 0 or 1?
	
	private static final int MOVE_TYPE_SHORT = 0;
	private static final int MOVE_TYPE_MEDIUM = 1;
	private static final int MOVE_TYPE_LONG = 2;
	private static final int MOVE_TYPE_GUARD = 3;
	private static final int MOVE_TYPE_FREE = 4;
	private static final int MOVE_TYPE_STATIONARY = 5;

	private static final int MOVE_TYPE_OFFSET_MM7 = 59; // 1 byte
	
	private static final int AI_TYPE_SUICIDAL = 0;
	private static final int AI_TYPE_WIMP = 1;
	private static final int AI_TYPE_NORMAL = 2;
	private static final int AI_TYPE_AGRESSIVE = 3;

	private static final int AI_TYPE_OFFSET_MM7 = 60; // 1 byte
	
	private static final int HOSTILE_TYPE_FRIENDLY = 0;
	private static final int HOSTILE_TYPE_CLOSE = 1;
	private static final int HOSTILE_TYPE_SHORT = 2;
	private static final int HOSTILE_TYPE_MEDIUM = 3;
	private static final int HOSTILE_TYPE_LONG = 4;

	private static final int HOSTILE_TYPE_OFFSET_MM7 = 61; // 1 byte
	
	private static final int RESERVED_0_OFFSET_MM7 = 62; // 1 byte
	
	private static final int SPECIAL_ABILITY_OFFSET_MM7 = 63; // 1 byte
	
	private static final int SPECIAL_ABILITY_BONUS_CURSE = 1;
	private static final int SPECIAL_ABILITY_BONUS_WEAK = 2;
	private static final int SPECIAL_ABILITY_BONUS_ASLEEP = 3;
	private static final int SPECIAL_ABILITY_BONUS_DRUNK = 4;
	private static final int SPECIAL_ABILITY_BONUS_INSANE = 5;
	private static final int SPECIAL_ABILITY_BONUS_POISON1 = 6;
	private static final int SPECIAL_ABILITY_BONUS_POISON2 = 7;
	private static final int SPECIAL_ABILITY_BONUS_POISON3 = 8;
	private static final int SPECIAL_ABILITY_BONUS_DISEASE1 = 9;
	private static final int SPECIAL_ABILITY_BONUS_DISEASE2 = 10;
	private static final int SPECIAL_ABILITY_BONUS_DISEASE3 = 11;
	private static final int SPECIAL_ABILITY_BONUS_PARALYZE = 12;
	private static final int SPECIAL_ABILITY_BONUS_UNCONSCIOUS = 13;
	private static final int SPECIAL_ABILITY_BONUS_DEAD = 14;
	private static final int SPECIAL_ABILITY_BONUS_STONE = 15;
	private static final int SPECIAL_ABILITY_BONUS_ERADICATE = 16;
	private static final int SPECIAL_ABILITY_BONUS_BREAK_ITEM = 17;
	private static final int SPECIAL_ABILITY_BONUS_BREAK_ARMOR = 18;
	private static final int SPECIAL_ABILITY_BONUS_BREAK_WEAPON = 19;
	private static final int SPECIAL_ABILITY_BONUS_STEAL = 20;
	private static final int SPECIAL_ABILITY_BONUS_AGE = 21;
	private static final int SPECIAL_ABILITY_BONUS_DRAIN_SPELL_POINTS = 22;
	private static final int SPECIAL_ABILITY_BONUS_AFRAID = 23;

	private static final int SPECIAL_ABILITY_BONUS_OFFSET_MM7 = 64; // 1 byte
	
	private static final int ATTACK_TYPE_PHYSICAL = 0x01;
	private static final int ATTACK_TYPE_FIRE = 0x02;
	private static final int ATTACK_TYPE_ELECTRICITY = 0x04;
	private static final int ATTACK_TYPE_COLD = 0x08;
	private static final int ATTACK_TYPE_POISON = 0x10;
	private static final int ATTACK_TYPE_ENERGY = 0x20;
	private static final int ATTACK_TYPE_MAGICAL = 0x40;

	private static final int ATTACK1_TYPE_OFFSET_MM7 = 65; //  1 byte
	private static final int ATTACK1_DAMAGE_DICE_COUNT_OFFSET_MM7 = 66; // 1 byte
	private static final int ATTACK1_DAMAGE_DICE_TYPE_OFFSET_MM7 = 67; // 1 byte
	private static final int ATTACK1_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM7 = 68; // 1 byte
	
	private static final int ATTACK_MISSILE_TYPE_NONE = 0;
	private static final int ATTACK_MISSILE_TYPE_ARROW = 1;
	private static final int ATTACK_MISSILE_TYPE_ARROW_FIRE = 2;
	private static final int ATTACK_MISSILE_TYPE_FIRE = 3;
	private static final int ATTACK_MISSILE_TYPE_AIR = 4;
	private static final int ATTACK_MISSILE_TYPE_WATER = 5;
	private static final int ATTACK_MISSILE_TYPE_EARTH = 6;
	private static final int ATTACK_MISSILE_TYPE_SPIRIT = 7;
	private static final int ATTACK_MISSILE_TYPE_MIND = 8;
	private static final int ATTACK_MISSILE_TYPE_BODY = 9;
	private static final int ATTACK_MISSILE_TYPE_LIGHT = 10;
	private static final int ATTACK_MISSILE_TYPE_DARK = 11;
	private static final int ATTACK_MISSILE_TYPE_MAGIC = 12;
	private static final int ATTACK_MISSILE_TYPE_ENERGY = 13;

	private static final int ATTACK1_MISSILE_TYPE_OFFSET_MM7 = 69; // 1 byte

	private static final int ATTACK2_PERCENTAGE_OFFSET_MM7 = 70; //  1 byte
	private static final int ATTACK2_TYPE_OFFSET_MM7 = 71; //  1 byte
	private static final int ATTACK2_DAMAGE_DICE_COUNT_OFFSET_MM7 = 72; // 1 byte
	private static final int ATTACK2_DAMAGE_DICE_TYPE_OFFSET_MM7 = 73; // 1 byte
	private static final int ATTACK2_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM7 = 74; // 1 byte
	private static final int ATTACK2_MISSILE_TYPE_OFFSET_MM7 = 75; // 1 byte

	private static final int SPELL1_ATTACK_USE_PERCENTAGE_OFFSET_MM7 = 76; // 1 byte
	private static final int SPELL1_ID_OFFSET_MM7 = 77; // 1 byte
	private static final int SPELL2_ATTACK_USE_PERCENTAGE_OFFSET_MM7 = 80; // 1 byte
	private static final int SPELL2_ID_OFFSET_MM7 = 79; // 1 byte

	private static final int MONSTER_RESISTANCE_IMMUNE_MM6 = 200;

	private static final int FIRE_RESISTANCE_OFFSET_MM7 = 80; // 1 byte
	private static final int AIR_RESISTANCE_OFFSET_MM7 = 81; // 1 byte
	private static final int WATER_RESISTANCE_OFFSET_MM7 = 82; // 1 byte
	private static final int EARTH_RESISTANCE_OFFSET_MM7 = 83; // 1 byte
	private static final int MIND_RESISTANCE_OFFSET_MM7 = 84; // 1 byte
	private static final int SPIRIT_RESISTANCE_OFFSET_MM7 = 85; // 1 byte
	private static final int BODY_RESISTANCE_OFFSET_MM7 = 86; // 1 byte
	private static final int LIGHT_RESISTANCE_OFFSET_MM7 = 87; // 1 byte
	private static final int DARK_RESISTANCE_OFFSET_MM7 = 88; // 1 byte
	private static final int PHYSICAL_RESISTANCE_OFFSET_MM7 = 89; // 1 byte

	private static final int SPECIAL_ATTACK_TYPE_NONE = 0;
	private static final int SPECIAL_ATTACK_TYPE_SHOT = 1;
	private static final int SPECIAL_ATTACK_TYPE_SUMMON = 2;
	private static final int SPECIAL_ATTACK_TYPE_EXPLODE = 3;

	private static final int SPECIAL_ATTACK_TYPE_OFFSET_MM7 = 90; // 1 byte
	private static final int SPECIAL_ATTACK_DAMAGE_DICE_COUNT_OFFSET_MM7 = 91; // 1 byte
	private static final int SPECIAL_ATTACK_DAMAGE_DICE_TYPE_OFFSET_MM7 = 92; // 1 byte
	private static final int SPECIAL_ATTACK_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM7 = 93; // 1 byte

	private static final int NUMBER_OF_CHARACTERS_ATTACKED_PER_ATTACK_OFFSET_MM7 = 94; // 1 byte

	private static final int PADDING2_OFFSET_MM7 = 95; // 1 byte
	private static final int MONSTER_TYPE_OFFSET_MM7 = 96; // 2 bytes, PICTURE_LOOKING_OFFSET_MM7?
	private static final int MONSTER_ATTRIBUTES_OFFSET_MM7 = 98; // 2 bytes
	private static final int SPELL_SKILL_AND_MASTERY_1_OFFSET_MM7 = 100; // 2 bytes
	private static final int SPELL_SKILL_AND_MASTERY_2_OFFSET_MM7 = 102; // 2 bytes

	private static final int SPECIAL_ATTACK_TYPE_SUMMON_TYPE_STANDARD = 0;
	private static final int SPECIAL_ATTACK_TYPE_SUMMON_TYPE_GROUND = 1;
    
	private static final int SPECIAL_ATTACK_TYPE_SUMMON_TYPE_OFFSET_MM7 = 104; // 2 bytes
	private static final int PADDING3_OFFSET_MM7 = 106; // 2 bytes
	private static final int HIT_POINTS_OFFSET_MM7 = 108; // 4 bytes
	private static final int ARMOR_CLASS_OFFSET_MM7 = 112; // 4 bytes
	private static final int EXPERIENCE_OFFSET_MM7 = 116; // 4 bytes
	private static final int ATTACK_SPEED_OFFSET_MM7 = 120; // 4 bytes
	private static final int ATTACK_RECOVERY_OFFSET_MM7 = 124; // 4 bytes

	private static final int ATTACK_PREFERENCE_TYPE_NONE_MM7 = 0x0000;
	private static final int ATTACK_PREFERENCE_TYPE_KNIGHT_MM7 = 0x0001;
	private static final int ATTACK_PREFERENCE_TYPE_PALADIN_MM7 = 0x0002;
	private static final int ATTACK_PREFERENCE_TYPE_ARCHER_MM7 = 0x0004;
	private static final int ATTACK_PREFERENCE_TYPE_DRUID_MM7 = 0x0008;
	private static final int ATTACK_PREFERENCE_TYPE_CLERIC_MM7 = 0x0010;
	private static final int ATTACK_PREFERENCE_TYPE_SORCERER_MM7 = 0x0020;
	private static final int ATTACK_PREFERENCE_TYPE_RANGER_MM7 = 0x0040;
	private static final int ATTACK_PREFERENCE_TYPE_THIEF_MM7 = 0x0080;
	private static final int ATTACK_PREFERENCE_TYPE_MONK_MM7 = 0x0100;
	private static final int ATTACK_PREFERENCE_TYPE_MALE_MM7 = 0x0200;
	private static final int ATTACK_PREFERENCE_TYPE_FEMALE_MM7 = 0x0400;
	private static final int ATTACK_PREFERENCE_TYPE_HUMAN_MM7 = 0x0800;
	private static final int ATTACK_PREFERENCE_TYPE_ELF_MM7 = 0x1000;
	private static final int ATTACK_PREFERENCE_TYPE_DWARF_MM7 = 0x2000;
	private static final int ATTACK_PREFERENCE_TYPE_GOBLIN_MM7 = 0x4000;
	
	private static final int ATTACK_PREFERENCE_TYPE_OFFSET_MM7 = 128; // 4 bytes

	private static final int RANGE_ATTACK_OFFSET_MM7 = 132; // 2 bytes, 0 is melee
	private static final int MONSTER_ID_TYPE_OFFSET_MM7 = 134; // 2 bytes
	private static final int PHYSICAL_RADIUS_OFFSET_MM7 = 136; // 2 bytes
	private static final int PHYSICAL_HEIGHT_OFFSET_MM7 = 138; // 2 bytes
	private static final int VELOCITY_OFFSET_MM7 = 140; // 2 bytes

	private static final int X_OFFSET_MM7 = 142; // 2 bytes
	private static final int Y_OFFSET_MM7 = 144; // 2 bytes
	private static final int Z_OFFSET_MM7 = 146; // 2 bytes
	private static final int VELOCITY_X_OFFSET_MM7 = 148; // 2 bytes
	private static final int VELOCITY_Y_OFFSET_MM7 = 150; // 2 bytes
	private static final int VELOCITY_Z_OFFSET_MM7 = 152; // 2 bytes
	private static final int FACING_OFFSET_MM7 = 154; // 2 bytes
	private static final int TILT_OFFSET_MM7 = 156; // 2 bytes
	private static final int ROOM_OFFSET_MM7 = 158; // 2 bytes
	private static final int CURRENT_ACTION_LENGTH_OFFSET_MM7 = 160; // 2 bytes
	private static final int STARTING_X_OFFSET_MM7 = 162; // 2 bytes
	private static final int STARTING_Y_OFFSET_MM7 = 164; // 2 bytes
	private static final int STARTING_Z_OFFSET_MM7 = 166; // 2 bytes
	private static final int GUARDING_X_OFFSET_MM7 = 168; // 2 bytes
	private static final int GUARDING_Y_OFFSET_MM7 = 170; // 2 bytes
	private static final int GUARDING_Z_OFFSET_MM7 = 172; // 2 bytes
	private static final int TETHER_DISTANCE_OFFSET_MM7 = 174; // 2 bytes, in pixels
	
	private static final int AI_STATE_TYPE_STANDING = 0;
	private static final int AI_STATE_TYPE_TETHERED = 1;
	private static final int AI_STATE_TYPE_NEAR_ATTACK = 2;
	private static final int AI_STATE_TYPE_FAR_ATTACK_1 = 3;
	private static final int AI_STATE_TYPE_DYING = 4;
	private static final int AI_STATE_TYPE_DEAD = 5;
	private static final int AI_STATE_TYPE_PURSUING = 6;
	private static final int AI_STATE_TYPE_FLEEING = 7;
	private static final int AI_STATE_TYPE_STUNNED = 8;
	private static final int AI_STATE_TYPE_FIDGETING = 9;
	private static final int AI_STATE_TYPE_INTERACTING = 10;
	private static final int AI_STATE_TYPE_REMOVED = 11;
	private static final int AI_STATE_TYPE_FAR_ATTACK_2 = 12;
	private static final int AI_STATE_TYPE_FAR_ATTACK_3 = 13;
	private static final int AI_STATE_TYPE_STONED = 14;
	private static final int AI_STATE_TYPE_PARALYSED = 15;
	private static final int AI_STATE_TYPE_RESURRECTED = 16;
	private static final int AI_STATE_TYPE_SUMMONED = 17;
	private static final int AI_STATE_TYPE_FAR_ATTACK_4 = 18;
	private static final int AI_STATE_TYPE_DISABLED = 19;

	private static final int AI_STATE_OFFSET_MM7 = 176; // 2 bytes, in pixels

	private static final int GRAPHICS_STATE_TYPE_STANDING = 0;
	private static final int GRAPHICS_STATE_TYPE_WALKING = 1;
	private static final int GRAPHICS_STATE_TYPE_NEAR_ATTACK = 2;
	private static final int GRAPHICS_STATE_TYPE_FAR_ATTACK = 3;
	private static final int GRAPHICS_STATE_TYPE_STUN = 4;
	private static final int GRAPHICS_STATE_TYPE_DYING = 5;
	private static final int GRAPHICS_STATE_TYPE_DEAD = 6;
	private static final int GRAPHICS_STATE_TYPE_FIDGETING = 7;
	
	private static final int GRAPHIC_STATE_OFFSET_MM7 = 178; // 2 bytes, in pixels
	private static final int ITEM_CARRIED_OFFSET_MM7 = 180; // 2 bytes, 00 means use table
	private static final int PADDING4_OFFSET_MM7 = 182; // 2 bytes
	private static final int CURRENT_ACTION_TIME_SO_FAR_OFFSET_MM7 = 184; // 4 bytes

	private static final int FRAME_TABLE_STANDING_OFFSET_MM7 = 188; // 2 bytes
	private static final int FRAME_TABLE_WALKING_OFFSET_MM7 = 190; // 2 bytes
	private static final int FRAME_TABLE_NEAR_ATTACK_OFFSET_MM7 = 192; // 2 bytes
	private static final int FRAME_TABLE_FAR_ATTACK_OFFSET_MM7 = 194; // 2 bytes
	private static final int FRAME_TABLE_STUN_OFFSET_MM7 = 196; // 2 bytes
	private static final int FRAME_TABLE_DYING_OFFSET_MM7 = 198; // 2 bytes
	private static final int FRAME_TABLE_DEAD_OFFSET_MM7 = 200; // 2 bytes
	private static final int FRAME_TABLE_FIDGETING_OFFSET_MM7 = 202; // 2 bytes

	private static final int SOUND_EFFECT_1_OFFSET_MM7 = 204; // 2 bytes
	private static final int SOUND_EFFECT_2_OFFSET_MM7 = 206; // 2 bytes
	private static final int SOUND_EFFECT_3_OFFSET_MM7 = 208; // 2 bytes
	private static final int SOUND_EFFECT_4_OFFSET_MM7 = 210; // 2 bytes

	private static final int NUMBER_OF_ACTIVE_SPELLS_MM7 = 22;
	private static final int ACTIVE_SPELL_SIZE_MM7 = ActiveSpell.getRecordSize(); // 16 bytes
	private static final int SPECIAL_EFFECTS_START_OFFSET_MM7 = 212; // 352 total
	
	private static final int CONTAINED_ITEM_SIZE_MM7 = ContainedItem.getRecordSize(GameVersion.MM7); // 36 bytes
	private static final int CONTAINED_ITEM_1_OFFSET_MM7 = 564; // 36 bytes
	private static final int CONTAINED_ITEM_2_OFFSET_MM7 = 600; // 36 bytes
	private static final int CONTAINED_ITEM_3_OFFSET_MM7 = 636; // 36 bytes
	private static final int CONTAINED_ITEM_4_OFFSET_MM7 = 672; // 36 bytes

	private static final int GROUP_OFFSET_MM7 = 708; // 4 bytes
	private static final int ALLY_OFFSET_MM7 = 712; // 4 bytes
	
	private static final int SCHEDULE_SIZE_MM7 = Schedule.getRecordSize(); // 12 bytes
	private static final int SCHEDULE_1_OFFSET_MM7 = 716; // 12 bytes
	private static final int SCHEDULE_2_OFFSET_MM7 = 728; // 12 bytes
	private static final int SCHEDULE_3_OFFSET_MM7 = 740; // 12 bytes
	private static final int SCHEDULE_4_OFFSET_MM7 = 752; // 12 bytes
	private static final int SCHEDULE_5_OFFSET_MM7 = 764; // 12 bytes
	private static final int SCHEDULE_6_OFFSET_MM7 = 776; // 12 bytes
	private static final int SCHEDULE_7_OFFSET_MM7 = 788; // 12 bytes
	private static final int SCHEDULE_8_OFFSET_MM7 = 800; // 12 bytes

	private static final int SUMMONER_ID_OFFSET_MM7 = 812; // 4 bytes
	private static final int LAST_CHARACTER_ID_TO_HIT_OFFSET_MM7 = 816; // 4 bytes
	private static final int NAME_ID_OFFSET_MM7 = 820; // 4 bytes, 0 means use monster type name
	private static final int RESERVED_1_OFFSET_MM7 = 824; // 4 bytes
	private static final int RESERVED_2_OFFSET_MM7 = 828; // 4 bytes
	private static final int RESERVED_3_OFFSET_MM7 = 832; // 4 bytes
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	
	private static final int MONSTER_RESISTANCE_IMMUNE_MM8 = 65000;

	private static final int FIRE_RESISTANCE_OFFSET_MM8 = 80; // 2 bytes
	private static final int AIR_RESISTANCE_OFFSET_MM8 = 82; // 2 bytes
	private static final int WATER_RESISTANCE_OFFSET_MM8 = 84; // 2 bytes
	private static final int EARTH_RESISTANCE_OFFSET_MM8 = 86; // 2 bytes
	private static final int MIND_RESISTANCE_OFFSET_MM8 = 88; // 2 bytes
	private static final int SPIRIT_RESISTANCE_OFFSET_MM8 = 90; // 2 bytes
	private static final int BODY_RESISTANCE_OFFSET_MM8 = 92; // 2 bytes
	private static final int LIGHT_RESISTANCE_OFFSET_MM8 = 94; // 2 bytes
	private static final int DARK_RESISTANCE_OFFSET_MM8 = 96; // 2 bytes
	private static final int PHYSICAL_RESISTANCE_OFFSET_MM8 = 98; // 2 bytes

	private static final int SPECIAL_ATTACK_TYPE_OFFSET_MM8 = 100; // 1 byte
	private static final int SPECIAL_ATTACK_DAMAGE_DICE_COUNT_OFFSET_MM8 = 101; // 1 byte
	private static final int SPECIAL_ATTACK_DAMAGE_DICE_TYPE_OFFSET_MM8 = 102; // 1 byte
	private static final int SPECIAL_ATTACK_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM8 = 103; // unverified

	private static final int NUMBER_OF_CHARACTERS_ATTACKED_PER_ATTACK_OFFSET_MM8 = 104; // 1 byte

	private static final int PADDING1_OFFSET_MM8 = 105; // 1 byte? ????
	private static final int MONSTER_TYPE_OFFSET_MM8 = 106; // 2 bytes
	private static final int ATTRIBUTES_OFFSET_MM8 = 108; // 2 bytes
	private static final int SPELL_SKILL_AND_MASTERY_1_OFFSET_MM8 = 110; // 2 bytes
	private static final int SPELL_SKILL_AND_MASTERY_2_OFFSET_MM8 = 112; // 2 bytes

	private static final int SPECIAL_ATTACK_TYPE_SUMMON_TYPE_OFFSET_MM8 = 114; // 2 bytes
	private static final int HIT_POINTS_OFFSET_MM8 = 116; // 4 bytes
	private static final int ARMOR_CLASS_OFFSET_MM8 = 120; // 4 bytes
	private static final int EXPERIENCE_OFFSET_MM8 = 124; // 4 bytes
	private static final int ATTACK_SPEED_OFFSET_MM8 = 128; // 4 bytes
	private static final int ATTACK_RECOVERY_OFFSET_MM8 = 132; // 4 bytes

	private static final int ATTACK_PREFERENCE_TYPE_NONE_MM8 = 0x0000;
	private static final int ATTACK_PREFERENCE_TYPE_NECROMANCER_MM8 = 0x0001;
	private static final int ATTACK_PREFERENCE_TYPE_CLERIC_MM8 = 0x0002;
	private static final int ATTACK_PREFERENCE_TYPE_KNIGHT_MM8 = 0x0004;
	private static final int ATTACK_PREFERENCE_TYPE_TROLL_MM8 = 0x0008;
	private static final int ATTACK_PREFERENCE_TYPE_MINOTAUR_MM8 = 0x0010;
	private static final int ATTACK_PREFERENCE_TYPE_DARKELF_MM8 = 0x0020;
	private static final int ATTACK_PREFERENCE_TYPE_VAMPIRE_MM8 = 0x0040;
	private static final int ATTACK_PREFERENCE_TYPE_DRAGON_MM8 = 0x0080;
	private static final int ATTACK_PREFERENCE_TYPE_MALE_MM8 = 0x0100;
	private static final int ATTACK_PREFERENCE_TYPE_FEMALE_MM8 = 0x0200;
	
	private static final int ATTACK_PREFERENCE_TYPE_OFFSET_MM8 = 136; // 4 bytes

	private static final int RANGE_ATTACK_OFFSET_MM8 = 140; // 2 bytes, 0 is melee
	private static final int MONSTER_ID_TYPE_OFFSET_MM8 = 142; // 2 bytes
	private static final int PHYSICAL_RADIUS_OFFSET_MM8 = 144; // 2 bytes
	private static final int PHYSICAL_HEIGHT_OFFSET_MM8 = 146; // 2 bytes
	private static final int VELOCITY_OFFSET_MM8 = 148; // 2 bytes
	private static final int X_OFFSET_MM8 = 150; // 2 bytes
	private static final int Y_OFFSET_MM8 = 152; // 2 bytes
	private static final int Z_OFFSET_MM8 = 154; // 2 bytes
	private static final int VELOCITY_X_OFFSET_MM8 = 156; // 2 bytes
	private static final int VELOCITY_Y_OFFSET_MM8 = 158; // 2 bytes
	private static final int VELOCITY_Z_OFFSET_MM8 = 160; // 2 bytes
	private static final int FACING_OFFSET_MM8 = 162; // 2 bytes
	private static final int TILT_OFFSET_MM8 = 164; // 2 bytes
	private static final int ROOM_OFFSET_MM8 = 166; // 2 bytes
	private static final int CURRENT_ACTION_LENGTH_OFFSET_MM8 = 168; // 2 bytes
	private static final int STARTING_X_OFFSET_MM8 = 170; // 2 bytes
	private static final int STARTING_Y_OFFSET_MM8 = 172; // 2 bytes
	private static final int STARTING_Z_OFFSET_MM8 = 174; // 2 bytes
	private static final int GUARDING_X_OFFSET_MM8 = 176; // 2 bytes
	private static final int GUARDING_Y_OFFSET_MM8 = 178; // 2 bytes
	private static final int GUARDING_Z_OFFSET_MM8 = 180; // 2 bytes
	private static final int TETHER_DISTANCE_OFFSET_MM8 = 182; // 2 bytes, in pixels
	
	private static final int AI_STATE_OFFSET_MM8 = 184; // 2 bytes, in pixels

	private static final int GRAPHIC_STATE_OFFSET_MM8 = 186; // 2 bytes, in pixels
	private static final int ITEM_CARRIED_OFFSET_MM8 = 188; // 2 bytes, 00 means use table
	private static final int PADDING4_OFFSET_MM8 = 190; // 2 bytes
	private static final int CURRENT_ACTION_TIME_SO_FAR_OFFSET_MM8 = 192; // 4 bytes

	private static final int FRAME_TABLE_STANDING_OFFSET_MM8 = 196; // 2 bytes
	private static final int FRAME_TABLE_WALKING_OFFSET_MM8 = 198; // 2 bytes
	private static final int FRAME_TABLE_NEAR_ATTACK_OFFSET_MM8 = 200; // 2 bytes
	private static final int FRAME_TABLE_FAR_ATTACK_OFFSET_MM8 = 202; // 2 bytes
	private static final int FRAME_TABLE_STUN_OFFSET_MM8 = 204; // 2 bytes
	private static final int FRAME_TABLE_DYING_OFFSET_MM8 = 206; // 2 bytes
	private static final int FRAME_TABLE_DEAD_OFFSET_MM8 = 208; // 2 bytes
	private static final int FRAME_TABLE_FIDGETING_OFFSET_MM8 = 210; // 2 bytes

	private static final int SOUND_EFFECT_1_OFFSET_MM8 = 212; // 2 bytes
	private static final int SOUND_EFFECT_2_OFFSET_MM8 = 214; // 2 bytes
	private static final int SOUND_EFFECT_3_OFFSET_MM8 = 216; // 2 bytes
	private static final int SOUND_EFFECT_4_OFFSET_MM8 = 218; // 2 bytes
	///////////////////////

	private static final int NUMBER_OF_ACTIVE_SPELLS_MM8 = 30;
	private static final int ACTIVE_SPELL_SIZE_MM8 = ActiveSpell.getRecordSize(); // 16 bytes
	private static final int SPECIAL_EFFECTS_START_OFFSET_MM8 = 220; // 480 total
	
	private static final int CONTAINED_ITEM_SIZE_MM8 = ContainedItem.getRecordSize(GameVersion.MM8); // 36 bytes
	private static final int CONTAINED_ITEM_1_OFFSET_MM8 = 700; // 36 bytes
	private static final int CONTAINED_ITEM_2_OFFSET_MM8 = 736; // 36 bytes
	private static final int CONTAINED_ITEM_3_OFFSET_MM8 = 772; // 36 bytes
	private static final int CONTAINED_ITEM_4_OFFSET_MM8 = 808; // 36 bytes

	private static final int GROUP_OFFSET_MM8 = 844; // 4 bytes
	private static final int ALLY_OFFSET_MM8 = 848; // 4 bytes
	
	private static final int SCHEDULE_SIZE_MM8 = Schedule.getRecordSize(); // 12 bytes
	private static final int SCHEDULE_1_OFFSET_MM8 = 852; // 12 bytes
	private static final int SCHEDULE_2_OFFSET_MM8 = 864; // 12 bytes
	private static final int SCHEDULE_3_OFFSET_MM8 = 876; // 12 bytes
	private static final int SCHEDULE_4_OFFSET_MM8 = 888; // 12 bytes
	private static final int SCHEDULE_5_OFFSET_MM8 = 900; // 12 bytes
	private static final int SCHEDULE_6_OFFSET_MM8 = 912; // 12 bytes
	private static final int SCHEDULE_7_OFFSET_MM8 = 924; // 12 bytes
	private static final int SCHEDULE_8_OFFSET_MM8 = 936; // 12 bytes

	private static final int SUMMONER_ID_OFFSET_MM8 = 948; // 4 bytes
	private static final int LAST_CHARACTER_ID_TO_HIT_OFFSET_MM8 = 952; // 4 bytes
	private static final int NAME_ID_OFFSET_MM8 = 956; // 4 bytes, 0 means use monster type name
	private static final int RESERVED_1_OFFSET_MM8 = 960; // 4 bytes
	private static final int RESERVED_2_OFFSET_MM8 = 964; // 4 bytes
	private static final int RESERVED_3_OFFSET_MM8 = 968; // 4 bytes

	private int gameVersion = 0;
    private byte creatureData[] = null;

    public Creature(int gameVersion)
    {
        super();
        this.gameVersion = gameVersion;
        
        if (GameVersion.MM6 == gameVersion)
            this.creatureData = new byte[CREATURE_RECORD_LENGTH_MM6];
        else if (GameVersion.MM7 == gameVersion)
            this.creatureData = new byte[CREATURE_RECORD_LENGTH_MM7];
        else this.creatureData = new byte[CREATURE_RECORD_LENGTH_MM8];
    }

    public Creature(int gameVersion, String creatureName)
    {
        this(gameVersion);
        
        this.setCreatureMouseOverName(creatureName);
    }

    public int initialize(byte dataSrc[], int offset)
    {
        System.arraycopy(dataSrc, offset, this.creatureData, 0, this.creatureData.length);
        offset += this.creatureData.length;
        
        return offset;
    }

    public static int populateObjects(int gameVersion, byte[] data, int offset, List creatureList)
    {
        int creatureCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int creatureIndex = 0; creatureIndex < creatureCount; ++creatureIndex)
        {
            Creature creature = new Creature(gameVersion);
            creatureList.add(creature);
            offset = creature.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List creatureList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(creatureList.size(), newData, offset);
        offset += 4;

        for (int creatureIndex = 0; creatureIndex < creatureList.size(); ++creatureIndex)
        {
            Creature creature = (Creature)creatureList.get(creatureIndex);
            System.arraycopy(creature.getCreatureData(), 0, newData, offset, creature.getCreatureData().length);
            offset += creature.getCreatureData().length;
        }
        
        return offset;
    }

    public String getCreatureMouseOverName()
    {
        return ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(this.creatureData, CREATURE_NAME_OFFSET, CREATURE_NAME_MAX_LENGTH);
    }

    public void setCreatureMouseOverName(String creatureName)
    {
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(creatureName, this.creatureData, CREATURE_NAME_OFFSET, CREATURE_NAME_MAX_LENGTH);
    }

    public int getCurrentHitPoints()
    {
        return ByteConversions.getShortInByteArrayAtPosition(this.creatureData, CURRENT_HP_OFFSET_MM6);
    }
    public void setCurrentHitPoints(int hitPoints)
    {
        ByteConversions.setIntegerInByteArrayAtPosition((short)hitPoints, this.creatureData, CURRENT_HP_OFFSET_MM6);
    }
    
    public int getX()
    {
        return ByteConversions.getShortInByteArrayAtPosition(this.creatureData, X_COORD_OFFSET_MM6);
    }
    public void setX(int x)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)x, this.creatureData, X_COORD_OFFSET_MM6);
    }
    
    public int getY()
    {
        return ByteConversions.getShortInByteArrayAtPosition(this.creatureData, Y_COORD_OFFSET_MM6);
    }
    public void setY(int y)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)y, this.creatureData, Y_COORD_OFFSET_MM6);
    }
    
    public int getZ()
    {
        return ByteConversions.getShortInByteArrayAtPosition(this.creatureData, Z_COORD_OFFSET_MM6);
    }
    public void setZ(int z)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)z, this.creatureData, Z_COORD_OFFSET_MM6);
    }
    
    public byte[] getCreatureData()
    {
        return this.creatureData;
    }

    // Unknown things to decode

    public static List getOffsetList(int gameVersion)
    {
        List offsetList = new ArrayList();

        offsetList.add(new ComparativeTableControl.OffsetData(CREATURE_NAME_OFFSET, CREATURE_NAME_MAX_LENGTH, ComparativeTableControl.REPRESENTATION_STRING, "Name"));

        if (GameVersion.MM6 == gameVersion)
        {
            offsetList.add(new ComparativeTableControl.OffsetData(NPC_ID_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "NPC_ID"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING1_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "PADDING1"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTRIBUTE_TYPE_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ATTRIBUTE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(CURRENT_HP_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "CURRENT_HP"));
            offsetList.add(new ComparativeTableControl.OffsetData(NAME_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "NAME_OFFSET"));
            offsetList.add(new ComparativeTableControl.OffsetData(PICNAME_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "PICNAME_OFFSET"));
            offsetList.add(new ComparativeTableControl.OffsetData(MONSTER_ID_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "MONSTER_ID"));
            offsetList.add(new ComparativeTableControl.OffsetData(LEVEL_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "LEVEL"));
            offsetList.add(new ComparativeTableControl.OffsetData(TREASURE_ITEM_PERCENT_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "TREASURE_ITEM_PERCENT"));
            offsetList.add(new ComparativeTableControl.OffsetData(TREASURE_DICE_COUNT_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "TREASURE_DICE_COUNT"));
            offsetList.add(new ComparativeTableControl.OffsetData(TREASURE_DICE_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "TREASURE_DICE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(TREASURE_LEVEL_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "TREASURE_LEVEL"));
            offsetList.add(new ComparativeTableControl.OffsetData(TREASURE_ITEM_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "TREASURE_ITEM_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(FLY_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "FLY"));
            offsetList.add(new ComparativeTableControl.OffsetData(MOVE_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "MOVE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(AI_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "AI_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(HOSTILE_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "HOSTILE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(PREFERRED_TARGET_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "PREFERRED_TARGET?"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ABILITY_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPECIAL_ABILITY"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ABILITY_BONUS_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPECIAL_ABILITY_BONUS"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK1_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK1_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK1_DAMAGE_DICE_COUNT_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK1_DAMAGE_DICE_COUNT"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK1_DAMAGE_DICE_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK1_DAMAGE_DICE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK1_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK1_DAMAGE_DICE_ADJUSTMENT"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK1_MISSILE_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK1_MISSILE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK2_PERCENTAGE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK2_PERCENTAGE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK2_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK2_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK2_DAMAGE_DICE_COUNT_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK2_DAMAGE_DICE_COUNT"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK2_DAMAGE_DICE_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK2_DAMAGE_DICE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK2_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK2_DAMAGE_DICE_ADJUSTMENT"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK2_MISSILE_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK2_MISSILE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPELL_ATTACK_USE_PERCENTAGE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPELL_ATTACK_USE_PERCENTAGE"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPELL_ID_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPELL_ID"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPELL_RANK_AND_SKILL_LEVEL_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPELL_RANK_AND_SKILL_LEVEL"));
            offsetList.add(new ComparativeTableControl.OffsetData(FIRE_RESISTANCE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "FIRE_RESISTANCE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ELECTRICAL_RESISTANCE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ELECTRICAL_RESISTANCE"));
            offsetList.add(new ComparativeTableControl.OffsetData(COLD_RESISTANCE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "COLD_RESISTANCE"));
            offsetList.add(new ComparativeTableControl.OffsetData(POISON_RESISTANCE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "POISON_RESISTANCE"));
            offsetList.add(new ComparativeTableControl.OffsetData(PHYSICAL_RESISTANCE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "PHYSICAL_RESISTANCE"));
            offsetList.add(new ComparativeTableControl.OffsetData(MAGICAL_RESISTANCE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "MAGICAL_RESISTANCE"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "PREFERRED_TARGET?"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_DAMAGE_DICE_COUNT_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "Padding?"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_DAMAGE_DICE_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "Padding?"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "Padding?"));
            offsetList.add(new ComparativeTableControl.OffsetData(UNKNOWN1_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "UNKNOWN1?"));
            offsetList.add(new ComparativeTableControl.OffsetData(HIT_POINTS_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "HIT_POINTS"));
            offsetList.add(new ComparativeTableControl.OffsetData(ARMOR_CLASS_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ARMOR_CLASS"));
            offsetList.add(new ComparativeTableControl.OffsetData(EXPERIENCE_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "EXPERIENCE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK_SPEED_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ATTACK_SPEED"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK_RECOVERY_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ATTACK_RECOVERY"));
            offsetList.add(new ComparativeTableControl.OffsetData(UNKNOWN2_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "UNKNOWN2?"));
            offsetList.add(new ComparativeTableControl.OffsetData(RANGE_ATTACK_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "RANGE_ATTACK"));
            offsetList.add(new ComparativeTableControl.OffsetData(MONSTER_ID_TYPE_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "MONSTER_ID_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(PHYSICAL_RADIUS_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "PHYSICAL_RADIUS"));
            offsetList.add(new ComparativeTableControl.OffsetData(PHYSICAL_HEIGHT_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "PHYSICAL_HEIGHT"));
            offsetList.add(new ComparativeTableControl.OffsetData(VELOCITY_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "VELOCITY"));
            offsetList.add(new ComparativeTableControl.OffsetData(X_COORD_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "X_COORD"));
            offsetList.add(new ComparativeTableControl.OffsetData(Y_COORD_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Y_COORD"));
            offsetList.add(new ComparativeTableControl.OffsetData(Z_COORD_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Z_COORD"));
            offsetList.add(new ComparativeTableControl.OffsetData(VELOCITY_X_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "VELOCITY_X"));
            offsetList.add(new ComparativeTableControl.OffsetData(VELOCITY_Y_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "VELOCITY_Y"));
            offsetList.add(new ComparativeTableControl.OffsetData(VELOCITY_Z_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "VELOCITY_Z"));
            offsetList.add(new ComparativeTableControl.OffsetData(FACING_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FACING"));
            offsetList.add(new ComparativeTableControl.OffsetData(TILT_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "TILT"));
            offsetList.add(new ComparativeTableControl.OffsetData(ROOM_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "ROOM"));
            offsetList.add(new ComparativeTableControl.OffsetData(CURRENT_ACTION_LENGTH_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "CURRENT_ACTION_LENGTH"));
            offsetList.add(new ComparativeTableControl.OffsetData(STARTING_X_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "STARTING_X"));
            offsetList.add(new ComparativeTableControl.OffsetData(STARTING_Y_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "STARTING_Y"));
            offsetList.add(new ComparativeTableControl.OffsetData(STARTING_Z_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "STARTING_Z"));
            offsetList.add(new ComparativeTableControl.OffsetData(GUARDING_X_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "GUARDING_X"));
            offsetList.add(new ComparativeTableControl.OffsetData(GUARDING_Y_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "GUARDING_Y"));
            offsetList.add(new ComparativeTableControl.OffsetData(GUARDING_Z_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "GUARDING_Z"));
            offsetList.add(new ComparativeTableControl.OffsetData(TETHER_DISTANCE_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "TETHER_DISTANCE"));
            offsetList.add(new ComparativeTableControl.OffsetData(AI_STATE_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "AI_STATE"));
            offsetList.add(new ComparativeTableControl.OffsetData(GRAPHIC_STATE_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "GRAPHIC_STATE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ITEM_CARRIED_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "ITEM_CARRIED"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING4_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "PADDING4?"));
            offsetList.add(new ComparativeTableControl.OffsetData(CURRENT_ACTION_TIME_SO_FAR_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "CURRENT_ACTION_TIME_SO_FAR"));
            offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_STANDING_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_STANDING"));
            offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_WALKING_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_WALKING"));
            offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_NEAR_ATTACK_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_NEAR_ATTACK"));
            offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_FAR_ATTACK_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_FAR_ATTACK"));
            offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_STUN_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_STUN"));
            offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_DYING_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_DYING"));
            offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_DEAD_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_DEAD"));
            offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_FIDGETING_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_FIDGETING"));
            offsetList.add(new ComparativeTableControl.OffsetData(SOUND_EFFECT_1_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SOUND_EFFECT_1"));
            offsetList.add(new ComparativeTableControl.OffsetData(SOUND_EFFECT_2_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SOUND_EFFECT_2"));
            offsetList.add(new ComparativeTableControl.OffsetData(SOUND_EFFECT_3_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SOUND_EFFECT_3"));
            offsetList.add(new ComparativeTableControl.OffsetData(SOUND_EFFECT_4_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SOUND_EFFECT_4"));

            int activeSpellOffset = SPECIAL_EFFECTS_START_OFFSET_MM6;
            for (int index = 0; index < NUMBER_OF_ACTIVE_SPELLS_MM6; index++)
            {
                ActiveSpell.addOffsets(offsetList, activeSpellOffset, index);
            }


            offsetList.add(new ComparativeTableControl.OffsetData(GROUP_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "GROUP?"));
            offsetList.add(new ComparativeTableControl.OffsetData(ALLY_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ALLY?"));

            Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_1_OFFSET_MM6, 1);
            Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_2_OFFSET_MM6, 2);
            Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_3_OFFSET_MM6, 3);
            Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_4_OFFSET_MM6, 4);
            Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_5_OFFSET_MM6, 5);
            Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_6_OFFSET_MM6, 6);
            Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_7_OFFSET_MM6, 7);
            Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_8_OFFSET_MM6, 8);

            offsetList.add(new ComparativeTableControl.OffsetData(SUMMONER_ID_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "SUMMONER_ID?"));

            offsetList.add(new ComparativeTableControl.OffsetData(LAST_CHARACTER_ID_TO_HIT_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "LAST_CHARACTER_ID_TO_HIT?"));
            offsetList.add(new ComparativeTableControl.OffsetData(NAME_ID_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "NAME_ID?"));
            offsetList.add(new ComparativeTableControl.OffsetData(RESERVED_1_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "RESERVED_1"));
            offsetList.add(new ComparativeTableControl.OffsetData(RESERVED_2_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "RESERVED_2"));
            offsetList.add(new ComparativeTableControl.OffsetData(RESERVED_3_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "RESERVED_3"));
        }
        else
        {
            offsetList.add(new ComparativeTableControl.OffsetData(NPC_ID_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "NPC_ID"));
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING1_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "PADDING1"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTRIBUTE_TYPE_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ATTRIBUTE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(CURRENT_HP_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "CURRENT_HP"));
            offsetList.add(new ComparativeTableControl.OffsetData(NAME_OFFSET_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "NAME_OFFSET"));
            offsetList.add(new ComparativeTableControl.OffsetData(PICNAME_OFFSET_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "PICNAME_OFFSET"));
            offsetList.add(new ComparativeTableControl.OffsetData(LEVEL_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "LEVEL"));
            offsetList.add(new ComparativeTableControl.OffsetData(TREASURE_ITEM_PERCENT_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "TREASURE_ITEM_PERCENT"));
            offsetList.add(new ComparativeTableControl.OffsetData(TREASURE_DICE_COUNT_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "TREASURE_DICE_COUNT"));
            offsetList.add(new ComparativeTableControl.OffsetData(TREASURE_DICE_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "TREASURE_DICE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(TREASURE_LEVEL_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "TREASURE_LEVEL"));
            offsetList.add(new ComparativeTableControl.OffsetData(TREASURE_ITEM_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "TREASURE_ITEM_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(FLY_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "FLY"));
            offsetList.add(new ComparativeTableControl.OffsetData(MOVE_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "MOVE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(AI_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "AI_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(HOSTILE_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "HOSTILE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(RESERVED_0_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "RESERVED_0"));	
            offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ABILITY_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPECIAL_ABILITY"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ABILITY_BONUS_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPECIAL_ABILITY_BONUS"));	
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK1_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK1_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK1_DAMAGE_DICE_COUNT_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK1_DAMAGE_DICE_COUNT"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK1_DAMAGE_DICE_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK1_DAMAGE_DICE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK1_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK1_DAMAGE_DICE_ADJUSTMENT"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK1_MISSILE_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK1_MISSILE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK2_PERCENTAGE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK2_PERCENTAGE_OFFSET_MM7"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK2_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK2_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK2_DAMAGE_DICE_COUNT_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK2_DAMAGE_DICE_COUNT"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK2_DAMAGE_DICE_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK2_DAMAGE_DICE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK2_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK2_DAMAGE_DICE_ADJUSTMENT"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTACK2_MISSILE_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "ATTACK2_MISSILE_TYPE"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPELL1_ATTACK_USE_PERCENTAGE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPELL1_ATTACK_USE_PERCENTAGE"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPELL1_ID_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPELL1_ID"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPELL2_ATTACK_USE_PERCENTAGE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPELL2_ATTACK_USE_PERCENTAGE"));
            offsetList.add(new ComparativeTableControl.OffsetData(SPELL2_ID_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPELL2_ID"));
            
            if (GameVersion.MM7 == gameVersion)
            {
                
                offsetList.add(new ComparativeTableControl.OffsetData(FIRE_RESISTANCE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "FIRE_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(AIR_RESISTANCE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "AIR_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(WATER_RESISTANCE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "WATER_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(EARTH_RESISTANCE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "EARTH_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(MIND_RESISTANCE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "MIND_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPIRIT_RESISTANCE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPIRIT_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(BODY_RESISTANCE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "BODY_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(LIGHT_RESISTANCE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "LIGHT_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(DARK_RESISTANCE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "DARK_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(PHYSICAL_RESISTANCE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "PHYSICAL_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPECIAL_ATTACK_TYPE"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_DAMAGE_DICE_COUNT_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPECIAL_ATTACK_DAMAGE_DICE_COUNT"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_DAMAGE_DICE_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPECIAL_ATTACK_DAMAGE_DICE_TYPE"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPECIAL_ATTACK_DAMAGE_DICE_ADJUSTMENT"));
                offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_CHARACTERS_ATTACKED_PER_ATTACK_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "NUMBER_OF_CHARACTERS_ATTACKED_PER_ATTACK"));
                offsetList.add(new ComparativeTableControl.OffsetData(PADDING2_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "PADDING2"));
                offsetList.add(new ComparativeTableControl.OffsetData(MONSTER_TYPE_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "MONSTER_TYPE"));
                offsetList.add(new ComparativeTableControl.OffsetData(MONSTER_ATTRIBUTES_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "MONSTER_ATTRIBUTES"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPELL_SKILL_AND_MASTERY_1_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SPELL_SKILL_AND_MASTERY_1"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPELL_SKILL_AND_MASTERY_2_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SPELL_SKILL_AND_MASTERY_2"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_TYPE_SUMMON_TYPE_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SPECIAL_ATTACK_TYPE_SUMMON_TYPE"));
                offsetList.add(new ComparativeTableControl.OffsetData(PADDING3_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "PADDING3"));
                offsetList.add(new ComparativeTableControl.OffsetData(HIT_POINTS_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "HIT_POINTS"));
                offsetList.add(new ComparativeTableControl.OffsetData(ARMOR_CLASS_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ARMOR_CLASS"));
                offsetList.add(new ComparativeTableControl.OffsetData(EXPERIENCE_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "EXPERIENCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(ATTACK_SPEED_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ATTACK_SPEED"));
                offsetList.add(new ComparativeTableControl.OffsetData(ATTACK_RECOVERY_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ATTACK_RECOVERY"));
                offsetList.add(new ComparativeTableControl.OffsetData(ATTACK_PREFERENCE_TYPE_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ATTACK_PREFERENCE_TYPE"));
                offsetList.add(new ComparativeTableControl.OffsetData(RANGE_ATTACK_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "RANGE_ATTACK"));
                offsetList.add(new ComparativeTableControl.OffsetData(MONSTER_ID_TYPE_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "MONSTER_ID_TYPE"));
                offsetList.add(new ComparativeTableControl.OffsetData(PHYSICAL_RADIUS_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "PHYSICAL_RADIUS"));
                offsetList.add(new ComparativeTableControl.OffsetData(PHYSICAL_HEIGHT_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "PHYSICAL_HEIGHT"));
                offsetList.add(new ComparativeTableControl.OffsetData(VELOCITY_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "VELOCITY"));
                offsetList.add(new ComparativeTableControl.OffsetData(X_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "X"));
                offsetList.add(new ComparativeTableControl.OffsetData(Y_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Y"));
                offsetList.add(new ComparativeTableControl.OffsetData(Z_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Z"));
                offsetList.add(new ComparativeTableControl.OffsetData(VELOCITY_X_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "VELOCITY_X"));
                offsetList.add(new ComparativeTableControl.OffsetData(VELOCITY_Y_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "VELOCITY_Y"));
                offsetList.add(new ComparativeTableControl.OffsetData(VELOCITY_Z_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "VELOCITY_Z"));
                offsetList.add(new ComparativeTableControl.OffsetData(FACING_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FACING"));
                offsetList.add(new ComparativeTableControl.OffsetData(TILT_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "TILT"));
                offsetList.add(new ComparativeTableControl.OffsetData(ROOM_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "ROOM"));
                offsetList.add(new ComparativeTableControl.OffsetData(CURRENT_ACTION_LENGTH_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "CURRENT_ACTION_LENGTH"));
                offsetList.add(new ComparativeTableControl.OffsetData(STARTING_X_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "STARTING_X"));
                offsetList.add(new ComparativeTableControl.OffsetData(STARTING_Y_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "STARTING_Y"));
                offsetList.add(new ComparativeTableControl.OffsetData(STARTING_Z_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "STARTING_Z"));
                offsetList.add(new ComparativeTableControl.OffsetData(GUARDING_X_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "GUARDING_X"));
                offsetList.add(new ComparativeTableControl.OffsetData(GUARDING_Y_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "GUARDING_Y"));
                offsetList.add(new ComparativeTableControl.OffsetData(GUARDING_Z_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "GUARDING_Z"));
                offsetList.add(new ComparativeTableControl.OffsetData(TETHER_DISTANCE_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "TETHER_DISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(AI_STATE_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "AI_STATE"));
                offsetList.add(new ComparativeTableControl.OffsetData(GRAPHIC_STATE_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "GRAPHIC_STATE"));
                offsetList.add(new ComparativeTableControl.OffsetData(ITEM_CARRIED_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "ITEM_CARRIED"));
                offsetList.add(new ComparativeTableControl.OffsetData(PADDING4_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "PADDING4"));
                offsetList.add(new ComparativeTableControl.OffsetData(CURRENT_ACTION_TIME_SO_FAR_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "CURRENT_ACTION_TIME_SO_FAR"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_STANDING_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_STANDING"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_WALKING_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_WALKING"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_NEAR_ATTACK_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_NEAR_ATTACK"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_FAR_ATTACK_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_FAR_ATTACK"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_STUN_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_STUN"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_DYING_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_DYING"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_DEAD_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_DEAD"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_FIDGETING_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_FIDGETING"));
                offsetList.add(new ComparativeTableControl.OffsetData(SOUND_EFFECT_1_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SOUND_EFFECT_1"));
                offsetList.add(new ComparativeTableControl.OffsetData(SOUND_EFFECT_2_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SOUND_EFFECT_2"));
                offsetList.add(new ComparativeTableControl.OffsetData(SOUND_EFFECT_3_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SOUND_EFFECT_3"));
                offsetList.add(new ComparativeTableControl.OffsetData(SOUND_EFFECT_4_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SOUND_EFFECT_4"));

                int activeSpellOffset = SPECIAL_EFFECTS_START_OFFSET_MM7;
                for (int index = 0; index < NUMBER_OF_ACTIVE_SPELLS_MM7; index++)
                {
                    ActiveSpell.addOffsets(offsetList, activeSpellOffset, index);
                }

                ContainedItem.addOffsets(gameVersion, offsetList, CONTAINED_ITEM_1_OFFSET_MM7, 1);
                ContainedItem.addOffsets(gameVersion, offsetList, CONTAINED_ITEM_2_OFFSET_MM7, 2);
                ContainedItem.addOffsets(gameVersion, offsetList, CONTAINED_ITEM_3_OFFSET_MM7, 3);
                ContainedItem.addOffsets(gameVersion, offsetList, CONTAINED_ITEM_4_OFFSET_MM7, 4);

                offsetList.add(new ComparativeTableControl.OffsetData(GROUP_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "GROUP"));
                offsetList.add(new ComparativeTableControl.OffsetData(ALLY_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "SUMMONER_ID"));

                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_1_OFFSET_MM7, 1);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_2_OFFSET_MM7, 2);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_3_OFFSET_MM7, 3);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_4_OFFSET_MM7, 4);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_5_OFFSET_MM7, 5);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_6_OFFSET_MM7, 6);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_7_OFFSET_MM7, 7);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_8_OFFSET_MM7, 8);

                offsetList.add(new ComparativeTableControl.OffsetData(SUMMONER_ID_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "SUMMONER_ID"));
                offsetList.add(new ComparativeTableControl.OffsetData(LAST_CHARACTER_ID_TO_HIT_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "LAST_CHARACTER_ID_TO_HIT"));
                offsetList.add(new ComparativeTableControl.OffsetData(NAME_ID_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "NAME_ID"));
                offsetList.add(new ComparativeTableControl.OffsetData(RESERVED_1_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "RESERVED_1"));
                offsetList.add(new ComparativeTableControl.OffsetData(RESERVED_2_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "RESERVED_2"));
                offsetList.add(new ComparativeTableControl.OffsetData(RESERVED_3_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "RESERVED_3"));            
            }
            else
            {
                offsetList.add(new ComparativeTableControl.OffsetData(FIRE_RESISTANCE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FIRE_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(AIR_RESISTANCE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "WATER_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(WATER_RESISTANCE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "WATER_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(EARTH_RESISTANCE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "EARTH_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(MIND_RESISTANCE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "MIND_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPIRIT_RESISTANCE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SPIRIT_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(BODY_RESISTANCE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "BODY_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(LIGHT_RESISTANCE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "LIGHT_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(DARK_RESISTANCE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "DARK_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(PHYSICAL_RESISTANCE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "PHYSICAL_RESISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_TYPE_OFFSET_MM8, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPECIAL_ATTACK_TYPE"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_DAMAGE_DICE_COUNT_OFFSET_MM8, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPECIAL_ATTACK_DAMAGE_DICE_TYPE"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_DAMAGE_DICE_TYPE_OFFSET_MM8, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPECIAL_ATTACK_DAMAGE_DICE_TYPE"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_DAMAGE_DICE_ADJUSTMENT_OFFSET_MM8, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SPECIAL_ATTACK_DAMAGE_DICE_ADJUSTMENT"));
                offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_CHARACTERS_ATTACKED_PER_ATTACK_OFFSET_MM8, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "NUMBER_OF_CHARACTERS_ATTACKED_PER_ATTACK"));
                offsetList.add(new ComparativeTableControl.OffsetData(PADDING1_OFFSET_MM8, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "PADDING1"));
                offsetList.add(new ComparativeTableControl.OffsetData(MONSTER_TYPE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "MONSTER_TYPE"));
                offsetList.add(new ComparativeTableControl.OffsetData(ATTRIBUTES_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "ATTRIBUTES"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPELL_SKILL_AND_MASTERY_1_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SPELL_SKILL_AND_MASTERY_1"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPELL_SKILL_AND_MASTERY_2_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SPELL_SKILL_AND_MASTERY_2"));
                offsetList.add(new ComparativeTableControl.OffsetData(SPECIAL_ATTACK_TYPE_SUMMON_TYPE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SPECIAL_ATTACK_TYPE_SUMMON_TYPE"));
                offsetList.add(new ComparativeTableControl.OffsetData(HIT_POINTS_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "HIT_POINTS"));
                offsetList.add(new ComparativeTableControl.OffsetData(ARMOR_CLASS_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ARMOR_CLASS"));
                offsetList.add(new ComparativeTableControl.OffsetData(EXPERIENCE_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "EXPERIENCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(ATTACK_SPEED_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ATTACK_RECOVERY"));
                offsetList.add(new ComparativeTableControl.OffsetData(ATTACK_RECOVERY_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ATTACK_RECOVERY"));
                offsetList.add(new ComparativeTableControl.OffsetData(ATTACK_PREFERENCE_TYPE_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ATTACK_PREFERENCE_TYPE"));
                offsetList.add(new ComparativeTableControl.OffsetData(RANGE_ATTACK_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "RANGE_ATTACK"));
                offsetList.add(new ComparativeTableControl.OffsetData(MONSTER_ID_TYPE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "MONSTER_ID_TYPE"));
                offsetList.add(new ComparativeTableControl.OffsetData(PHYSICAL_RADIUS_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "PHYSICAL_RADIUS"));
                offsetList.add(new ComparativeTableControl.OffsetData(PHYSICAL_HEIGHT_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "PHYSICAL_HEIGHT"));
                offsetList.add(new ComparativeTableControl.OffsetData(VELOCITY_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "VELOCITY"));
                offsetList.add(new ComparativeTableControl.OffsetData(X_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "X"));
                offsetList.add(new ComparativeTableControl.OffsetData(Y_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Y"));
                offsetList.add(new ComparativeTableControl.OffsetData(Z_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Z"));
                offsetList.add(new ComparativeTableControl.OffsetData(VELOCITY_X_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "VELOCITY_X"));
                offsetList.add(new ComparativeTableControl.OffsetData(VELOCITY_Y_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "VELOCITY_Z"));
                offsetList.add(new ComparativeTableControl.OffsetData(VELOCITY_Z_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "VELOCITY_Z"));
                offsetList.add(new ComparativeTableControl.OffsetData(FACING_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FACING"));
                offsetList.add(new ComparativeTableControl.OffsetData(TILT_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "TILT"));
                offsetList.add(new ComparativeTableControl.OffsetData(ROOM_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "ROOM"));
                offsetList.add(new ComparativeTableControl.OffsetData(CURRENT_ACTION_LENGTH_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "CURRENT_ACTION_LENGTH"));
                offsetList.add(new ComparativeTableControl.OffsetData(STARTING_X_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "STARTING_X"));
                offsetList.add(new ComparativeTableControl.OffsetData(STARTING_Y_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "STARTING_Y"));
                offsetList.add(new ComparativeTableControl.OffsetData(STARTING_Z_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "STARTING_Z"));
                offsetList.add(new ComparativeTableControl.OffsetData(GUARDING_X_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "GUARDING_X"));
                offsetList.add(new ComparativeTableControl.OffsetData(GUARDING_Y_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "GUARDING_Z"));
                offsetList.add(new ComparativeTableControl.OffsetData(GUARDING_Z_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "GUARDING_Z"));
                offsetList.add(new ComparativeTableControl.OffsetData(TETHER_DISTANCE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "TETHER_DISTANCE"));
                offsetList.add(new ComparativeTableControl.OffsetData(AI_STATE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "AI_STATE"));
                offsetList.add(new ComparativeTableControl.OffsetData(GRAPHIC_STATE_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "ITEM_CARRIED"));
                offsetList.add(new ComparativeTableControl.OffsetData(ITEM_CARRIED_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "ITEM_CARRIED"));
                offsetList.add(new ComparativeTableControl.OffsetData(PADDING4_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "PADDING4"));
                offsetList.add(new ComparativeTableControl.OffsetData(CURRENT_ACTION_TIME_SO_FAR_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "CURRENT_ACTION_TIME_SO_FAR"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_STANDING_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_STANDING"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_WALKING_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_WALKING"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_NEAR_ATTACK_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_NEAR_ATTACK"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_FAR_ATTACK_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_STUN"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_STUN_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_STUN"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_DYING_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_DYING"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_DEAD_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_DEAD"));
                offsetList.add(new ComparativeTableControl.OffsetData(FRAME_TABLE_FIDGETING_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "FRAME_TABLE_FIDGETING"));
                offsetList.add(new ComparativeTableControl.OffsetData(SOUND_EFFECT_1_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SOUND_EFFECT_1"));
                offsetList.add(new ComparativeTableControl.OffsetData(SOUND_EFFECT_2_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SOUND_EFFECT_2"));
                offsetList.add(new ComparativeTableControl.OffsetData(SOUND_EFFECT_3_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SOUND_EFFECT_3"));
                offsetList.add(new ComparativeTableControl.OffsetData(SOUND_EFFECT_4_OFFSET_MM8, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "SOUND_EFFECT_4"));

                int activeSpellOffset = SPECIAL_EFFECTS_START_OFFSET_MM8;
                for (int index = 0; index < NUMBER_OF_ACTIVE_SPELLS_MM8; index++)
                {
                    ActiveSpell.addOffsets(offsetList, activeSpellOffset, index);
                }

                ContainedItem.addOffsets(gameVersion, offsetList, CONTAINED_ITEM_1_OFFSET_MM8, 1);
                ContainedItem.addOffsets(gameVersion, offsetList, CONTAINED_ITEM_2_OFFSET_MM8, 2);
                ContainedItem.addOffsets(gameVersion, offsetList, CONTAINED_ITEM_3_OFFSET_MM8, 3);
                ContainedItem.addOffsets(gameVersion, offsetList, CONTAINED_ITEM_4_OFFSET_MM8, 4);

                offsetList.add(new ComparativeTableControl.OffsetData(GROUP_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "GROUP"));
                offsetList.add(new ComparativeTableControl.OffsetData(ALLY_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ALLY"));

                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_1_OFFSET_MM8, 1);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_2_OFFSET_MM8, 2);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_3_OFFSET_MM8, 3);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_4_OFFSET_MM8, 4);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_5_OFFSET_MM8, 5);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_6_OFFSET_MM8, 6);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_7_OFFSET_MM8, 7);
                Schedule.addOffsets(gameVersion, offsetList, SCHEDULE_8_OFFSET_MM8, 8);

                offsetList.add(new ComparativeTableControl.OffsetData(SUMMONER_ID_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "SUMMONER_ID"));
                offsetList.add(new ComparativeTableControl.OffsetData(LAST_CHARACTER_ID_TO_HIT_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "LAST_CHARACTER_ID_TO_HIT"));
                offsetList.add(new ComparativeTableControl.OffsetData(NAME_ID_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "RESERVED_1"));
                offsetList.add(new ComparativeTableControl.OffsetData(RESERVED_1_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "RESERVED_1"));
                offsetList.add(new ComparativeTableControl.OffsetData(RESERVED_2_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "RESERVED_2"));
                offsetList.add(new ComparativeTableControl.OffsetData(RESERVED_3_OFFSET_MM8, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "RESERVED_3"));            }
        }

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List creatureList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return creatureList.size();
            }

            public byte[] getData(int dataRow)
            {
                Creature creature = (Creature)creatureList.get(dataRow);
                return creature.getCreatureData();
            }

            public int getAdjustedDataRowIndex(int dataRow)
            {
                return dataRow;
            }
            
            public String getIdentifier(int dataRow)
            {
                Creature creature = (Creature)creatureList.get(dataRow);
                return creature.getCreatureMouseOverName();
            }

            public int getOffset(int dataRow)
            {
                return 0;
            }
        };
    }

    public static int getRecordSize(int gameVersion)
    {
        if (GameVersion.MM6 == gameVersion)
            return CREATURE_RECORD_LENGTH_MM6;
        else if (GameVersion.MM7 == gameVersion)
            return CREATURE_RECORD_LENGTH_MM7;
        else return CREATURE_RECORD_LENGTH_MM8;
    }
    public int getGameVersion()
    {
        return this.gameVersion;
    }
}
