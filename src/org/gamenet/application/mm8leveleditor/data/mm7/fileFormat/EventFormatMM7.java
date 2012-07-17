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

package org.gamenet.application.mm8leveleditor.data.mm7.fileFormat;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gamenet.application.mm8leveleditor.data.Event;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.EventFormatMM6;

public class EventFormatMM7 extends EventFormatMM6
{
    public static final int MM7_EVENT_LAST_COMMAND_TYPE_VALUE = MM6_EVENT_LAST_COMMAND_TYPE_VALUE;
    
    public static final int TARGET_TYPE__AIR_RESISTANCE_PERM = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 1;
    public static final int TARGET_TYPE__WATER_RESISTANCE_PERM = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 2;
    public static final int TARGET_TYPE__EARTH_RESISTANCE_PERM = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 3;
    public static final int TARGET_TYPE__SPIRIT_RESISTANCE_PERM = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 4;
    public static final int TARGET_TYPE__MIND_RESISTANCE_PERM = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 5;
    public static final int TARGET_TYPE__BODY_RESISTANCE_PERM = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 6;
    public static final int TARGET_TYPE__LIGHT_RESISTANCE_PERM = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 7;
    public static final int TARGET_TYPE__DARK_RESISTANCE_PERM = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 8;
    public static final int TARGET_TYPE__PHYSICAL_RESISTANCE_PERM = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 9;
    public static final int TARGET_TYPE__FIRE_RESISTANCE_TEMP = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 10;
    public static final int TARGET_TYPE__AIR_RESISTANCE_TEMP = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 11;
    public static final int TARGET_TYPE__WATER_RESISTANCE_TEMP = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 12;
    public static final int TARGET_TYPE__EARTH_RESISTANCE_TEMP = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 13;
    public static final int TARGET_TYPE__SPIRIT_RESISTANCE_TEMP = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 14;
    public static final int TARGET_TYPE__MIND_RESISTANCE_TEMP = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 15;
    public static final int TARGET_TYPE__BODY_RESISTANCE_TEMP = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 16;
    public static final int TARGET_TYPE__LIGHT_RESISTANCE_TEMP = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 17;
    public static final int TARGET_TYPE__DARK_RESISTANCE_TEMP = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 18;
    public static final int TARGET_TYPE__PHYSICAL_RESISTANCE_TEMP = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 19;

    public static final int TARGET_TYPE__SKILL_DODGING = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 20;
    public static final int TARGET_TYPE__SKILL_UNARMED = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 21;
    public static final int TARGET_TYPE__SKILL_IDENTIFY_MONSTER = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 22;
    public static final int TARGET_TYPE__SKILL_ARMSMASTER = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 23;
    public static final int TARGET_TYPE__SKILL_STEALING = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 24;
    public static final int TARGET_TYPE__SKILL_ALCHEMY = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 25;

    public static final int TARGET_TYPE__IMPORTANT1 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 26;
    public static final int TARGET_TYPE__IMPORTANT2 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 27;
    public static final int TARGET_TYPE__IMPORTANT3 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 28;
    public static final int TARGET_TYPE__IMPORTANT4 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 29;
    public static final int TARGET_TYPE__IMPORTANT5 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 30;
    public static final int TARGET_TYPE__IMPORTANT6 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 31;
    public static final int TARGET_TYPE__IMPORTANT7 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 32;
    public static final int TARGET_TYPE__IMPORTANT8 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 33;
    public static final int TARGET_TYPE__IMPORTANT9 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 34;
    public static final int TARGET_TYPE__IMPORTANT10 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 35;
    public static final int TARGET_TYPE__IMPORTANT11 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 36;
    public static final int TARGET_TYPE__IMPORTANT12 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 37;
    public static final int TARGET_TYPE__IMPORTANT13 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 38;
    public static final int TARGET_TYPE__IMPORTANT14 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 39;
    public static final int TARGET_TYPE__IMPORTANT15 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 40;
    public static final int TARGET_TYPE__IMPORTANT16 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 41;
    public static final int TARGET_TYPE__IMPORTANT17 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 42;
    public static final int TARGET_TYPE__IMPORTANT18 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 43;
    public static final int TARGET_TYPE__IMPORTANT19 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 44;
    public static final int TARGET_TYPE__IMPORTANT20 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 45;

    public static final int TARGET_TYPE__HISTORY1 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 46;
    public static final int TARGET_TYPE__HISTORY2 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 47;
    public static final int TARGET_TYPE__HISTORY3 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 48;
    public static final int TARGET_TYPE__HISTORY4 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 49;
    public static final int TARGET_TYPE__HISTORY5 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 50;
    public static final int TARGET_TYPE__HISTORY6 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 51;
    public static final int TARGET_TYPE__HISTORY7 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 52;
    public static final int TARGET_TYPE__HISTORY8 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 53;
    public static final int TARGET_TYPE__HISTORY9 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 54;
    public static final int TARGET_TYPE__HISTORY10 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 55;
    public static final int TARGET_TYPE__HISTORY11 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 56;
    public static final int TARGET_TYPE__HISTORY12 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 57;
    public static final int TARGET_TYPE__HISTORY13 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 58;
    public static final int TARGET_TYPE__HISTORY14 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 59;
    public static final int TARGET_TYPE__HISTORY15 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 60;
    public static final int TARGET_TYPE__HISTORY16 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 61;
    public static final int TARGET_TYPE__HISTORY17 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 62;
    public static final int TARGET_TYPE__HISTORY18 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 63;
    public static final int TARGET_TYPE__HISTORY19 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 64;
    public static final int TARGET_TYPE__HISTORY20 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 65;
    public static final int TARGET_TYPE__HISTORY21 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 66;
    public static final int TARGET_TYPE__HISTORY22 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 67;
    public static final int TARGET_TYPE__HISTORY23 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 68;
    public static final int TARGET_TYPE__HISTORY24 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 69;
    public static final int TARGET_TYPE__HISTORY25 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 70;
    public static final int TARGET_TYPE__HISTORY26 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 71;
    public static final int TARGET_TYPE__HISTORY27 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 72;
    public static final int TARGET_TYPE__HISTORY28 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 73;
    public static final int TARGET_TYPE__HISTORY29 = MM6_EVENT_LAST_TARGET_TYPE_VALUE + 74;

    public static final int EVENTMM7_LAST_TARGET_TYPE_VALUE = TARGET_TYPE__HISTORY29;
    
    public EventFormatMM7()
    {
        super();
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
			EventFormatMM7 eventFormatMM7 = new EventFormatMM7();
			List<Event> eventList = eventFormatMM7.readEvents(data);
			Iterator<Event> eventIterator = eventList.iterator();
			while (eventIterator.hasNext()) {
				Event event = eventIterator.next();
				if (!eventFormatMM7.isValid(event))
				{
					return false;
				}
			}
		} catch (RuntimeException e) {
			return false;
		}
		
		return true;
    }

    public Integer[] getCommandTypes()
    {
        return new Integer[] {
                new Integer(EVENT_COMMAND__None), // 0;
                new Integer(EVENT_COMMAND__EndEvent), // 1;
                new Integer(EVENT_COMMAND__TownEvent), // 2;
                new Integer(EVENT_COMMAND__EV_PlaySound), // 3;
                new Integer(EVENT_COMMAND__EventVerb), // 4;
                new Integer(EVENT_COMMAND__EventMazeInfo), // 5;
                new Integer(EVENT_COMMAND__ChangeXYZ), // 6;
                new Integer(EVENT_COMMAND__GiveChest), // 7;
                new Integer(EVENT_COMMAND__PlayFace), // 8;
                new Integer(EVENT_COMMAND__CharDamage), // 9;
                new Integer(EVENT_COMMAND__Weather), // 10;
                new Integer(EVENT_COMMAND__ModifyTexture), // 11;
                new Integer(EVENT_COMMAND__EV_PlaySmacker), // 12;
                new Integer(EVENT_COMMAND__ModifyDecoration), // 13;
                new Integer(EVENT_COMMAND__IfHas), // 14;
                new Integer(EVENT_COMMAND__EV_Door), // 15;
                new Integer(EVENT_COMMAND__Give), // 16;
                new Integer(EVENT_COMMAND__Take), // 17;
                new Integer(EVENT_COMMAND__Set), // 18;
                new Integer(EVENT_COMMAND__CreateMonster), // 19;
                new Integer(EVENT_COMMAND__ModifyItem), // 20;
                new Integer(EVENT_COMMAND__EV_CastSpell), // 21;
                new Integer(EVENT_COMMAND__DoNPC), // 22;
                new Integer(EVENT_COMMAND__FacetAttrib), // 23;
                new Integer(EVENT_COMMAND__ModifyMonster), // 24;
                new Integer(EVENT_COMMAND__RandomGoto), // 25;
                new Integer(EVENT_COMMAND__GetInput), // 26;
                new Integer(EVENT_COMMAND__RndPassword), // 27;
                new Integer(EVENT_COMMAND__RndAnswer), // 28;
                new Integer(EVENT_COMMAND__EV_PrintStatus), // 29;
                new Integer(EVENT_COMMAND__PrintScroll), // 30;
                new Integer(EVENT_COMMAND__TimeEvent), // 31;
                new Integer(EVENT_COMMAND__ModifyLight), // 32;
                new Integer(EVENT_COMMAND__AnyKey), // 33;
                new Integer(EVENT_COMMAND__SpoutObject), // 34;
                new Integer(EVENT_COMMAND__SetActiveChar), // 35;
                new Integer(EVENT_COMMAND__EV_Goto), // 36;
                new Integer(EVENT_COMMAND__OnLoad), // 37;
                new Integer(EVENT_COMMAND__TimeEvent2), // 38;
                new Integer(EVENT_COMMAND__ChangeNPCEvent), // 39;
                new Integer(EVENT_COMMAND__ChangeNPC2DLoc), // 40;
                new Integer(EVENT_COMMAND__CreateItem), // 41;
                new Integer(EVENT_COMMAND__ChangeEvent), // 42;
                new Integer(EVENT_COMMAND__CheckSkill), // 43;
                new Integer(EVENT_COMMAND__IfHasPrecondition), // 44;
                new Integer(EVENT_COMMAND__EndPrecondition), // 45;
                new Integer(EVENT_COMMAND__Precondition), // 46;
                new Integer(EVENT_COMMAND__ChangeGroupNews), // 47;
                new Integer(EVENT_COMMAND__ChangeMonsterGroup), // 48;
                new Integer(EVENT_COMMAND__ChangeNPCInv), // 49;
                new Integer(EVENT_COMMAND__ChangeNPCGreeting), // 50;
                new Integer(EVENT_COMMAND__IfMonsterDead), // 51;
                new Integer(EVENT_COMMAND__IfMonsterDeadPrecondition), // 52;
                new Integer(EVENT_COMMAND__OnExit), // 53;
                new Integer(EVENT_COMMAND__ChangeGroupToGroup), // 54;
                new Integer(EVENT_COMMAND__ChangeGroupAlly), // 55;
                new Integer(EVENT_COMMAND__IfSeason), // 56;
                new Integer(EVENT_COMMAND__ModifyMonsterGroup), // 57;
                new Integer(EVENT_COMMAND__ModifyChest), // 58;
                new Integer(EVENT_COMMAND__PlayAction), // 59;
                new Integer(EVENT_COMMAND__ChangeMonsterInv) // 60;
        };
    }
    
    protected void initializeCommandTypeInfo()
    {
        super.initializeCommandTypeInfo();
        
        Map mm6ArgumentTypeArrayByCommandTypeMap = super.getArgumentTypeArrayByCommandTypeMap();
        Map mm6ArgumentTypeDisplayInfoArrayByCommandTypeMap = super.getArgumentTypeDisplayInfoArrayByCommandTypeMap();

        Object replacementArgumentsByCommandType[] =  new Object[] {
                new Integer(EVENT_COMMAND__CreateMonster),  new Object[] {
                        _I_(ARGUMENT_TYPE__SPECIES_TYPE), "of species #", ",", _I_(1),
                        _I_(ARGUMENT_TYPE__SUBSPECIES_TYPE), "subspecies", null, _I_(2),
                        _I_(ARGUMENT_TYPE__MONSTER_CREATION_COUNT), null, "monster(s)", _I_(0),
                        _I_(ARGUMENT_TYPE__COORDINATE_SET), "at", null, _I_(3),
                        _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "with spawn group number", null, _I_(4), // TODO: ARGUMENT_TYPE__SPAWN_GROUP_NUMBER
                        _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "and monster name id", null, _I_(5) // TODO: ARGUMENT_TYPE__MONSTER_NAME_ID
                    },
                new Integer(EVENT_COMMAND__ModifyMonster),  new Object[] {
	                    _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "monsterId?", null, _I_(1),
	                    _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "bit?", null, _I_(2),
	                    _I_(ARGUMENT_TYPE__BOOLEAN), "Set?", null, _I_(0), // set or unset
                	},
                	
            	new Integer(EVENT_COMMAND__EV_PlaySmacker),  new Object[] {
					_I_(ARGUMENT_TYPE__UNKNOWN_BYTE),  "stretch?", null, _I_(0),
					_I_(ARGUMENT_TYPE__UNKNOWN_BYTE),  "exit now?", null, _I_(1),
            		_I_(ARGUMENT_TYPE__FILENAME_12), null, null, _I_(2) // movie name, seems like it might only be 9 characters
            	}
        };

        int argumentTypesForCommandTypeArrayColumns = 2;
        if (replacementArgumentsByCommandType.length % argumentTypesForCommandTypeArrayColumns != 0)
        {
            throw new RuntimeException("argsAndSizeArray.length = " + replacementArgumentsByCommandType.length + " which is not a power of " + argumentTypesForCommandTypeArrayColumns + ".");
        }

        for (int index = 0; index < replacementArgumentsByCommandType.length; index = index+argumentTypesForCommandTypeArrayColumns)
        {
            Integer commandTypeNumber = (Integer)replacementArgumentsByCommandType[index];
            Object argumentTypeDataArray[] = (Object [])replacementArgumentsByCommandType[index+1];

            Integer key = null;
            for (int commandTypesIndex = 0; commandTypesIndex < commandTypes.length; commandTypesIndex++) {
    			if (commandTypes[commandTypesIndex].equals(commandTypeNumber))
    			{
    				key = new Integer(commandTypesIndex);
    			}
    		}
            if (null == key)
            {
                throw new RuntimeException("Unable to find command type value" + commandTypeNumber + " in commandTypes");
            }
            
            int[] argumentTypeArray = getArgumentTypeArrayFromArgumentTypeDataArray(argumentTypeDataArray);
            Object newArgumentTypeArrayValue = argumentTypeArray;

            if (false == mm6ArgumentTypeArrayByCommandTypeMap.containsKey(key))
            {
                throw new RuntimeException("mm6ArgumentTypeArrayByCommandTypeMap does not contain key " + key + " for replacement value of " + newArgumentTypeArrayValue);
            }

            mm6ArgumentTypeArrayByCommandTypeMap.put(key, newArgumentTypeArrayValue);

            
            ArgumentTypeDisplayInfo[] argumentTypeDisplayInfoArray = getArgumentTypeDisplayInfoArrayFromArgumentTypeDataArray(argumentTypeDataArray);
            Object newArgumentTypeDisplayInfoArrayValue = argumentTypeDisplayInfoArray;

            if (false == mm6ArgumentTypeDisplayInfoArrayByCommandTypeMap.containsKey(key))
            {
                throw new RuntimeException("mm6ArgumentTypeDisplayInfoArrayByCommandTypeMap does not contain key " + key + " for replacement value of " + newArgumentTypeArrayValue);
            }

            mm6ArgumentTypeDisplayInfoArrayByCommandTypeMap.put(key, newArgumentTypeDisplayInfoArrayValue);
        }
    }

    protected void initializeArgumentTypeInfo()
    {
        super.initializeArgumentTypeInfo();
        
        Map sizeByArgumentTypeMap = super.getSizeByArgumentTypeMap();
        sizeByArgumentTypeMap.put(new Integer(ARGUMENT_TYPE__TARGET_TYPE), new Integer(2));

        Map argumentDataTypeByArgumentTypeMap = super.getArgumentDataTypeByArgumentTypeMap();
        argumentDataTypeByArgumentTypeMap.put(new Integer(ARGUMENT_TYPE__TARGET_TYPE), new Integer(ARGUMENT_DATA_TYPE__CHOICE_PULLDOWN));
    }

    public Integer[] getTargetTypes()
    {
        return new Integer[] {
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
                new Integer(TARGET_TYPE__AIR_RESISTANCE_PERM), // 47
                new Integer(TARGET_TYPE__WATER_RESISTANCE_PERM), // 48
                new Integer(TARGET_TYPE__EARTH_RESISTANCE_PERM), // 49
                new Integer(TARGET_TYPE__SPIRIT_RESISTANCE_PERM), // 50
                new Integer(TARGET_TYPE__MIND_RESISTANCE_PERM), // 51
                new Integer(TARGET_TYPE__BODY_RESISTANCE_PERM), // 52
                new Integer(TARGET_TYPE__LIGHT_RESISTANCE_PERM), // 53
                new Integer(TARGET_TYPE__DARK_RESISTANCE_PERM), // 54
                new Integer(TARGET_TYPE__PHYSICAL_RESISTANCE_PERM), // 55
                new Integer(TARGET_TYPE__MAGIC_RESISTANCE_PERM), // 56
                new Integer(TARGET_TYPE__FIRE_RESISTANCE_TEMP), // 57
                new Integer(TARGET_TYPE__AIR_RESISTANCE_TEMP), // 58
                new Integer(TARGET_TYPE__WATER_RESISTANCE_TEMP), // 59
                new Integer(TARGET_TYPE__EARTH_RESISTANCE_TEMP), // 60
                new Integer(TARGET_TYPE__SPIRIT_RESISTANCE_TEMP), // 61
                new Integer(TARGET_TYPE__MIND_RESISTANCE_TEMP), // 62
                new Integer(TARGET_TYPE__BODY_RESISTANCE_TEMP), // 63
                new Integer(TARGET_TYPE__LIGHT_RESISTANCE_TEMP), // 64
                new Integer(TARGET_TYPE__DARK_RESISTANCE_TEMP), // 65
                new Integer(TARGET_TYPE__PHYSICAL_RESISTANCE_TEMP), // 66
                new Integer(TARGET_TYPE__MAGIC_RESISTANCE_TEMP), // 67
                new Integer(TARGET_TYPE__SKILL_STAFF), // 68
                new Integer(TARGET_TYPE__SKILL_SWORD), // 69
                new Integer(TARGET_TYPE__SKILL_DAGGER), // 70
                new Integer(TARGET_TYPE__SKILL_AXE), // 71
                new Integer(TARGET_TYPE__SKILL_SPEAR), // 72
                new Integer(TARGET_TYPE__SKILL_BOW), // 73
                new Integer(TARGET_TYPE__SKILL_MACE), // 74
                new Integer(TARGET_TYPE__SKILL_BLASTER), // 75
                new Integer(TARGET_TYPE__SKILL_SHIELD), // 76
                new Integer(TARGET_TYPE__SKILL_LEATHER), // 77
                new Integer(TARGET_TYPE__SKILL_CHAIN), // 78
                new Integer(TARGET_TYPE__SKILL_PLATE), // 79
                new Integer(TARGET_TYPE__SKILL_FIRE_MAGIC), // 80
                new Integer(TARGET_TYPE__SKILL_AIR_MAGIC), // 81
                new Integer(TARGET_TYPE__SKILL_WATER_MAGIC), // 82
                new Integer(TARGET_TYPE__SKILL_EARTH_MAGIC), // 83
                new Integer(TARGET_TYPE__SKILL_SPIRIT_MAGIC), // 84
                new Integer(TARGET_TYPE__SKILL_MIND_MAGIC), // 85
                new Integer(TARGET_TYPE__SKILL_BODY_MAGIC), // 86
                new Integer(TARGET_TYPE__SKILL_LIGHT_MAGIC), // 87
                new Integer(TARGET_TYPE__SKILL_DARK_MAGIC), // 88
                new Integer(TARGET_TYPE__SKILL_IDENTIFY_ITEM), // 89
                new Integer(TARGET_TYPE__SKILL_MERCHANT), // 90
                new Integer(TARGET_TYPE__SKILL_REPAIR_ITEM), // 91
                new Integer(TARGET_TYPE__SKILL_BODY_BUILDING), // 92
                new Integer(TARGET_TYPE__SKILL_MEDITATION), // 93
                new Integer(TARGET_TYPE__SKILL_PERCEPTION), // 94
                new Integer(TARGET_TYPE__SKILL_DIPLOMACY), // 95
                new Integer(TARGET_TYPE__SKILL_THIEVERY), // 96
                new Integer(TARGET_TYPE__SKILL_LOCK_PICK), // 97
                new Integer(TARGET_TYPE__SKILL_DODGING), // 98
                new Integer(TARGET_TYPE__SKILL_UNARMED), // 99
                new Integer(TARGET_TYPE__SKILL_IDENTIFY_MONSTER), // 100
                new Integer(TARGET_TYPE__SKILL_ARMSMASTER), // 101
                new Integer(TARGET_TYPE__SKILL_STEALING), // 102
                new Integer(TARGET_TYPE__SKILL_ALCHEMY), // 103
                new Integer(TARGET_TYPE__SKILL_LEARNING), // 104
                new Integer(TARGET_TYPE__CONDITION_CURSED), // 105
                new Integer(TARGET_TYPE__CONDITION_WEAK), // 106
                new Integer(TARGET_TYPE__CONDITION_ASLEEP), // 107
                new Integer(TARGET_TYPE__CONDITION_AFRAID), // 108
                new Integer(TARGET_TYPE__CONDITION_DRUNK), // 109
                new Integer(TARGET_TYPE__CONDITION_INSANE), // 110
                new Integer(TARGET_TYPE__CONDITION_POISON1), // 111
                new Integer(TARGET_TYPE__CONDITION_DISEASE1), // 112
                new Integer(TARGET_TYPE__CONDITION_POISON2), // 113
                new Integer(TARGET_TYPE__CONDITION_DISEASE2), // 114
                new Integer(TARGET_TYPE__CONDITION_POISON3), // 115
                new Integer(TARGET_TYPE__CONDITION_DISEASE3), // 116
                new Integer(TARGET_TYPE__CONDITION_PARALYZED), // 117
                new Integer(TARGET_TYPE__CONDITION_UNCONSCIOUS), // 118
                new Integer(TARGET_TYPE__CONDITION_DEAD), // 119
                new Integer(TARGET_TYPE__CONDITION_STONE), // 120
                new Integer(TARGET_TYPE__CONDITION_ERADICATED), // 121
                new Integer(TARGET_TYPE__CONDITION_GOOD), // 122
                new Integer(TARGET_TYPE__DUNGEON_BYTE_1), // 123
                new Integer(TARGET_TYPE__DUNGEON_BYTE_2), // 124
                new Integer(TARGET_TYPE__DUNGEON_BYTE_3), // 125
                new Integer(TARGET_TYPE__DUNGEON_BYTE_4), // 126
                new Integer(TARGET_TYPE__DUNGEON_BYTE_5), // 127
                new Integer(TARGET_TYPE__DUNGEON_BYTE_6), // 128
                new Integer(TARGET_TYPE__DUNGEON_BYTE_7), // 129
                new Integer(TARGET_TYPE__DUNGEON_BYTE_8), // 130
                new Integer(TARGET_TYPE__DUNGEON_BYTE_9), // 131
                new Integer(TARGET_TYPE__DUNGEON_BYTE_10), // 132
                new Integer(TARGET_TYPE__DUNGEON_BYTE_11), // 133
                new Integer(TARGET_TYPE__DUNGEON_BYTE_12), // 134
                new Integer(TARGET_TYPE__DUNGEON_BYTE_13), // 135
                new Integer(TARGET_TYPE__DUNGEON_BYTE_14), // 136
                new Integer(TARGET_TYPE__DUNGEON_BYTE_15), // 137
                new Integer(TARGET_TYPE__DUNGEON_BYTE_16), // 138
                new Integer(TARGET_TYPE__DUNGEON_BYTE_17), // 139
                new Integer(TARGET_TYPE__DUNGEON_BYTE_18), // 140
                new Integer(TARGET_TYPE__DUNGEON_BYTE_19), // 141
                new Integer(TARGET_TYPE__DUNGEON_BYTE_20), // 142
                new Integer(TARGET_TYPE__DUNGEON_BYTE_21), // 143
                new Integer(TARGET_TYPE__DUNGEON_BYTE_22), // 144
                new Integer(TARGET_TYPE__DUNGEON_BYTE_23), // 145
                new Integer(TARGET_TYPE__DUNGEON_BYTE_24), // 146
                new Integer(TARGET_TYPE__DUNGEON_BYTE_25), // 147
                new Integer(TARGET_TYPE__DUNGEON_BYTE_26), // 148
                new Integer(TARGET_TYPE__DUNGEON_BYTE_27), // 149
                new Integer(TARGET_TYPE__DUNGEON_BYTE_28), // 150
                new Integer(TARGET_TYPE__DUNGEON_BYTE_29), // 151
                new Integer(TARGET_TYPE__DUNGEON_BYTE_30), // 152
                new Integer(TARGET_TYPE__DUNGEON_BYTE_31), // 153
                new Integer(TARGET_TYPE__DUNGEON_BYTE_32), // 154
                new Integer(TARGET_TYPE__DUNGEON_BYTE_33), // 155
                new Integer(TARGET_TYPE__DUNGEON_BYTE_34), // 156
                new Integer(TARGET_TYPE__DUNGEON_BYTE_35), // 157
                new Integer(TARGET_TYPE__DUNGEON_BYTE_36), // 158
                new Integer(TARGET_TYPE__DUNGEON_BYTE_37), // 159
                new Integer(TARGET_TYPE__DUNGEON_BYTE_38), // 160
                new Integer(TARGET_TYPE__DUNGEON_BYTE_39), // 161
                new Integer(TARGET_TYPE__DUNGEON_BYTE_40), // 162
                new Integer(TARGET_TYPE__DUNGEON_BYTE_41), // 163
                new Integer(TARGET_TYPE__DUNGEON_BYTE_42), // 164
                new Integer(TARGET_TYPE__DUNGEON_BYTE_43), // 165
                new Integer(TARGET_TYPE__DUNGEON_BYTE_44), // 166
                new Integer(TARGET_TYPE__DUNGEON_BYTE_45), // 167
                new Integer(TARGET_TYPE__DUNGEON_BYTE_46), // 168
                new Integer(TARGET_TYPE__DUNGEON_BYTE_47), // 169
                new Integer(TARGET_TYPE__DUNGEON_BYTE_48), // 170
                new Integer(TARGET_TYPE__DUNGEON_BYTE_49), // 171
                new Integer(TARGET_TYPE__DUNGEON_BYTE_50), // 172
                new Integer(TARGET_TYPE__DUNGEON_BYTE_51), // 173
                new Integer(TARGET_TYPE__DUNGEON_BYTE_52), // 174
                new Integer(TARGET_TYPE__DUNGEON_BYTE_53), // 175
                new Integer(TARGET_TYPE__DUNGEON_BYTE_54), // 176
                new Integer(TARGET_TYPE__DUNGEON_BYTE_55), // 177
                new Integer(TARGET_TYPE__DUNGEON_BYTE_56), // 178
                new Integer(TARGET_TYPE__DUNGEON_BYTE_57), // 179
                new Integer(TARGET_TYPE__DUNGEON_BYTE_58), // 180
                new Integer(TARGET_TYPE__DUNGEON_BYTE_59), // 181
                new Integer(TARGET_TYPE__DUNGEON_BYTE_60), // 182
                new Integer(TARGET_TYPE__DUNGEON_BYTE_61), // 183
                new Integer(TARGET_TYPE__DUNGEON_BYTE_62), // 184
                new Integer(TARGET_TYPE__DUNGEON_BYTE_63), // 185
                new Integer(TARGET_TYPE__DUNGEON_BYTE_64), // 186
                new Integer(TARGET_TYPE__DUNGEON_BYTE_65), // 187
                new Integer(TARGET_TYPE__DUNGEON_BYTE_66), // 188
                new Integer(TARGET_TYPE__DUNGEON_BYTE_67), // 189
                new Integer(TARGET_TYPE__DUNGEON_BYTE_68), // 190
                new Integer(TARGET_TYPE__DUNGEON_BYTE_69), // 191
                new Integer(TARGET_TYPE__DUNGEON_BYTE_70), // 192
                new Integer(TARGET_TYPE__DUNGEON_BYTE_71), // 193
                new Integer(TARGET_TYPE__DUNGEON_BYTE_72), // 194
                new Integer(TARGET_TYPE__DUNGEON_BYTE_73), // 195
                new Integer(TARGET_TYPE__DUNGEON_BYTE_74), // 196
                new Integer(TARGET_TYPE__DUNGEON_BYTE_75), // 197
                new Integer(TARGET_TYPE__DUNGEON_BYTE_76), // 198
                new Integer(TARGET_TYPE__DUNGEON_BYTE_77), // 199
                new Integer(TARGET_TYPE__DUNGEON_BYTE_78), // 200
                new Integer(TARGET_TYPE__DUNGEON_BYTE_79), // 201
                new Integer(TARGET_TYPE__DUNGEON_BYTE_80), // 202
                new Integer(TARGET_TYPE__DUNGEON_BYTE_81), // 203
                new Integer(TARGET_TYPE__DUNGEON_BYTE_82), // 204
                new Integer(TARGET_TYPE__DUNGEON_BYTE_83), // 205
                new Integer(TARGET_TYPE__DUNGEON_BYTE_84), // 206
                new Integer(TARGET_TYPE__DUNGEON_BYTE_85), // 207
                new Integer(TARGET_TYPE__DUNGEON_BYTE_86), // 208
                new Integer(TARGET_TYPE__DUNGEON_BYTE_87), // 209
                new Integer(TARGET_TYPE__DUNGEON_BYTE_88), // 210
                new Integer(TARGET_TYPE__DUNGEON_BYTE_89), // 211
                new Integer(TARGET_TYPE__DUNGEON_BYTE_90), // 212
                new Integer(TARGET_TYPE__DUNGEON_BYTE_91), // 213
                new Integer(TARGET_TYPE__DUNGEON_BYTE_92), // 214
                new Integer(TARGET_TYPE__DUNGEON_BYTE_93), // 215
                new Integer(TARGET_TYPE__DUNGEON_BYTE_94), // 216
                new Integer(TARGET_TYPE__DUNGEON_BYTE_95), // 217
                new Integer(TARGET_TYPE__DUNGEON_BYTE_96), // 218
                new Integer(TARGET_TYPE__DUNGEON_BYTE_97), // 219
                new Integer(TARGET_TYPE__DUNGEON_BYTE_98), // 220
                new Integer(TARGET_TYPE__DUNGEON_BYTE_99), // 221
                new Integer(TARGET_TYPE__DUNGEON_BYTE_100), // 222
                new Integer(TARGET_TYPE__EVENT_AUTONOTE), // 223
                new Integer(TARGET_TYPE__MIGHT_MAX), // 224
                new Integer(TARGET_TYPE__INTELLECT_MAX), // 225
                new Integer(TARGET_TYPE__PERSONALITY_MAX), // 226
                new Integer(TARGET_TYPE__ENDURANCE_MAX), // 227
                new Integer(TARGET_TYPE__SPEED_MAX), // 228
                new Integer(TARGET_TYPE__ACCURACY_MAX), // 229
                new Integer(TARGET_TYPE__LUCK_MAX), // 230
                new Integer(TARGET_TYPE__CHARACTER_BIT), // 231
                new Integer(TARGET_TYPE__NPC_IN_PARTY), // 232
                new Integer(TARGET_TYPE__REPUTATION), // 233
                new Integer(TARGET_TYPE__DAYS_PAST_1), // 234
                new Integer(TARGET_TYPE__DAYS_PAST_2), // 235
                new Integer(TARGET_TYPE__DAYS_PAST_3), // 236
                new Integer(TARGET_TYPE__DAYS_PAST_4), // 237
                new Integer(TARGET_TYPE__DAYS_PAST_5), // 238
                new Integer(TARGET_TYPE__DAYS_PAST_6), // 239
                new Integer(TARGET_TYPE__PARTY_FLYING), // 240
                new Integer(TARGET_TYPE__NPC_PROFESSION_IN_PARTY), // 241
                new Integer(TARGET_TYPE__CIRCUS_TOTAL), // 242
                new Integer(TARGET_TYPE__SKILL_POINTS), // 243
                new Integer(TARGET_TYPE__MONTH_OF_YEAR), // 244
                new Integer(TARGET_TYPE__TIMER1), // 245
                new Integer(TARGET_TYPE__TIMER2), // 246
                new Integer(TARGET_TYPE__TIMER3), // 247
                new Integer(TARGET_TYPE__TIMER4), // 248
                new Integer(TARGET_TYPE__TIMER5), // 249
                new Integer(TARGET_TYPE__TIMER6), // 250
                new Integer(TARGET_TYPE__TIMER7), // 251
                new Integer(TARGET_TYPE__TIMER8), // 252
                new Integer(TARGET_TYPE__TIMER9), // 253
                new Integer(TARGET_TYPE__TIMER10), // 254
                new Integer(TARGET_TYPE__IMPORTANT1), // 255
                new Integer(TARGET_TYPE__IMPORTANT2), // 256
                new Integer(TARGET_TYPE__IMPORTANT3), // 257
                new Integer(TARGET_TYPE__IMPORTANT4), // 258
                new Integer(TARGET_TYPE__IMPORTANT5), // 259
                new Integer(TARGET_TYPE__IMPORTANT6), // 260
                new Integer(TARGET_TYPE__IMPORTANT7), // 261
                new Integer(TARGET_TYPE__IMPORTANT8), // 262
                new Integer(TARGET_TYPE__IMPORTANT9), // 263
                new Integer(TARGET_TYPE__IMPORTANT10), // 264
                new Integer(TARGET_TYPE__IMPORTANT11), // 265
                new Integer(TARGET_TYPE__IMPORTANT12), // 266
                new Integer(TARGET_TYPE__IMPORTANT13), // 267
                new Integer(TARGET_TYPE__IMPORTANT14), // 268
                new Integer(TARGET_TYPE__IMPORTANT15), // 269
                new Integer(TARGET_TYPE__IMPORTANT16), // 270
                new Integer(TARGET_TYPE__IMPORTANT17), // 271
                new Integer(TARGET_TYPE__IMPORTANT18), // 272
                new Integer(TARGET_TYPE__IMPORTANT19), // 273
                new Integer(TARGET_TYPE__IMPORTANT20), // 274
                new Integer(TARGET_TYPE__MAP_STEAL_DIFFICULTY_TEMP), // 275
                new Integer(TARGET_TYPE__HISTORY1), // 276
                new Integer(TARGET_TYPE__HISTORY2), // 277
                new Integer(TARGET_TYPE__HISTORY3), // 278
                new Integer(TARGET_TYPE__HISTORY4), // 279
                new Integer(TARGET_TYPE__HISTORY5), // 280
                new Integer(TARGET_TYPE__HISTORY6), // 281
                new Integer(TARGET_TYPE__HISTORY7), // 282
                new Integer(TARGET_TYPE__HISTORY8), // 283
                new Integer(TARGET_TYPE__HISTORY9), // 284
                new Integer(TARGET_TYPE__HISTORY10), // 285
                new Integer(TARGET_TYPE__HISTORY11), // 286
                new Integer(TARGET_TYPE__HISTORY12), // 287
                new Integer(TARGET_TYPE__HISTORY13), // 288
                new Integer(TARGET_TYPE__HISTORY14), // 289
                new Integer(TARGET_TYPE__HISTORY15), // 290
                new Integer(TARGET_TYPE__HISTORY16), // 291
                new Integer(TARGET_TYPE__HISTORY17), // 292
                new Integer(TARGET_TYPE__HISTORY18), // 293
                new Integer(TARGET_TYPE__HISTORY19), // 294
                new Integer(TARGET_TYPE__HISTORY20), // 295
                new Integer(TARGET_TYPE__HISTORY21), // 296
                new Integer(TARGET_TYPE__HISTORY22), // 297
                new Integer(TARGET_TYPE__HISTORY23), // 298
                new Integer(TARGET_TYPE__HISTORY24), // 299
                new Integer(TARGET_TYPE__HISTORY25), // 300
                new Integer(TARGET_TYPE__HISTORY26), // 301
                new Integer(TARGET_TYPE__HISTORY27), // 302
                new Integer(TARGET_TYPE__HISTORY28), // 303
                new Integer(TARGET_TYPE__HISTORY29), // 304
                new Integer(TARGET_TYPE__MAP_ON_ALERT), // 305
                new Integer(TARGET_TYPE__BANK_GOLD), // 306
                new Integer(TARGET_TYPE__DEATH), // 307
                new Integer(TARGET_TYPE__BOUNTY), // 308
                new Integer(TARGET_TYPE__PRISON_TERM), // 309
                new Integer(TARGET_TYPE__ARENA_PAGE), // 310
                new Integer(TARGET_TYPE__ARENA_SQUIRE), // 311
                new Integer(TARGET_TYPE__ARENA_KNIGHT), // 312
                new Integer(TARGET_TYPE__ARENA_LORD), // 313
                new Integer(TARGET_TYPE__INVISIBILITY), // 314
                new Integer(TARGET_TYPE__EQUIP) // 315
        };
    }

    public String getTargetTypeName(int targetType)
    {
        Integer targetTypeKey = getTargetTypeKey(targetType);
        if (null == targetTypeKey)
        {
            return "UNKNOWN TYPE 0x" + Integer.toHexString(targetType);
        }

        switch(targetTypeKey.intValue())
        {
            case TARGET_TYPE__AIR_RESISTANCE_PERM:  return "AIR_RESISTANCE_PERM";
            case TARGET_TYPE__WATER_RESISTANCE_PERM:  return "WATER_RESISTANCE_PERM";
            case TARGET_TYPE__EARTH_RESISTANCE_PERM:  return "EARTH_RESISTANCE_PERM";
            case TARGET_TYPE__SPIRIT_RESISTANCE_PERM:  return "SPIRIT_RESISTANCE_PERM";
            case TARGET_TYPE__MIND_RESISTANCE_PERM:  return "MIND_RESISTANCE_PERM";
            case TARGET_TYPE__BODY_RESISTANCE_PERM:  return "BODY_RESISTANCE_PERM";
            case TARGET_TYPE__LIGHT_RESISTANCE_PERM:  return "LIGHT_RESISTANCE_PERM";
            case TARGET_TYPE__DARK_RESISTANCE_PERM:  return "DARK_RESISTANCE_PERM";
            case TARGET_TYPE__PHYSICAL_RESISTANCE_PERM:  return "PHYSICAL_RESISTANCE_PERM";
            case TARGET_TYPE__FIRE_RESISTANCE_TEMP:  return "FIRE_RESISTANCE_TEMP";
            case TARGET_TYPE__AIR_RESISTANCE_TEMP:  return "AIR_RESISTANCE_TEMP";
            case TARGET_TYPE__WATER_RESISTANCE_TEMP:  return "WATER_RESISTANCE_TEMP";
            case TARGET_TYPE__EARTH_RESISTANCE_TEMP:  return "EARTH_RESISTANCE_TEMP";
            case TARGET_TYPE__SPIRIT_RESISTANCE_TEMP:  return "SPIRIT_RESISTANCE_TEMP";
            case TARGET_TYPE__MIND_RESISTANCE_TEMP:  return "MIND_RESISTANCE_TEMP";
            case TARGET_TYPE__BODY_RESISTANCE_TEMP:  return "BODY_RESISTANCE_TEMP";
            case TARGET_TYPE__LIGHT_RESISTANCE_TEMP:  return "LIGHT_RESISTANCE_TEMP";
            case TARGET_TYPE__DARK_RESISTANCE_TEMP:  return "DARK_RESISTANCE_TEMP";
            case TARGET_TYPE__PHYSICAL_RESISTANCE_TEMP:  return "PHYSICAL_RESISTANCE_TEMP";

            case TARGET_TYPE__SKILL_DODGING:  return "SKILL_DODGING";
            case TARGET_TYPE__SKILL_UNARMED:  return "SKILL_UNARMED";
            case TARGET_TYPE__SKILL_IDENTIFY_MONSTER:  return "SKILL_IDENTIFY_MONSTER";
            case TARGET_TYPE__SKILL_ARMSMASTER:  return "SKILL_ARMSMASTER";
            case TARGET_TYPE__SKILL_STEALING:  return "SKILL_STEALING";
            case TARGET_TYPE__SKILL_ALCHEMY:  return "SKILL_ALCHEMY";

            case TARGET_TYPE__IMPORTANT1:  return "IMPORTANT1";
            case TARGET_TYPE__IMPORTANT2:  return "IMPORTANT2";
            case TARGET_TYPE__IMPORTANT3:  return "IMPORTANT3";
            case TARGET_TYPE__IMPORTANT4:  return "IMPORTANT4";
            case TARGET_TYPE__IMPORTANT5:  return "IMPORTANT5";
            case TARGET_TYPE__IMPORTANT6:  return "IMPORTANT6";
            case TARGET_TYPE__IMPORTANT7:  return "IMPORTANT7";
            case TARGET_TYPE__IMPORTANT8:  return "IMPORTANT8";
            case TARGET_TYPE__IMPORTANT9:  return "IMPORTANT9";
            case TARGET_TYPE__IMPORTANT10:  return "IMPORTANT10";
            case TARGET_TYPE__IMPORTANT11:  return "IMPORTANT11";
            case TARGET_TYPE__IMPORTANT12:  return "IMPORTANT12";
            case TARGET_TYPE__IMPORTANT13:  return "IMPORTANT13";
            case TARGET_TYPE__IMPORTANT14:  return "IMPORTANT14";
            case TARGET_TYPE__IMPORTANT15:  return "IMPORTANT15";
            case TARGET_TYPE__IMPORTANT16:  return "IMPORTANT16";
            case TARGET_TYPE__IMPORTANT17:  return "IMPORTANT17";
            case TARGET_TYPE__IMPORTANT18:  return "IMPORTANT18";
            case TARGET_TYPE__IMPORTANT19:  return "IMPORTANT19";
            case TARGET_TYPE__IMPORTANT20:  return "IMPORTANT20";

            case TARGET_TYPE__HISTORY1:  return "HISTORY1";
            case TARGET_TYPE__HISTORY2:  return "HISTORY2";
            case TARGET_TYPE__HISTORY3:  return "HISTORY3";
            case TARGET_TYPE__HISTORY4:  return "HISTORY4";
            case TARGET_TYPE__HISTORY5:  return "HISTORY5";
            case TARGET_TYPE__HISTORY6:  return "HISTORY6";
            case TARGET_TYPE__HISTORY7:  return "HISTORY7";
            case TARGET_TYPE__HISTORY8:  return "HISTORY8";
            case TARGET_TYPE__HISTORY9:  return "HISTORY9";
            case TARGET_TYPE__HISTORY10:  return "HISTORY10";
            case TARGET_TYPE__HISTORY11:  return "HISTORY11";
            case TARGET_TYPE__HISTORY12:  return "HISTORY12";
            case TARGET_TYPE__HISTORY13:  return "HISTORY13";
            case TARGET_TYPE__HISTORY14:  return "HISTORY14";
            case TARGET_TYPE__HISTORY15:  return "HISTORY15";
            case TARGET_TYPE__HISTORY16:  return "HISTORY16";
            case TARGET_TYPE__HISTORY17:  return "HISTORY17";
            case TARGET_TYPE__HISTORY18:  return "HISTORY18";
            case TARGET_TYPE__HISTORY19:  return "HISTORY19";
            case TARGET_TYPE__HISTORY20:  return "HISTORY20";
            case TARGET_TYPE__HISTORY21:  return "HISTORY21";
            case TARGET_TYPE__HISTORY22:  return "HISTORY22";
            case TARGET_TYPE__HISTORY23:  return "HISTORY23";
            case TARGET_TYPE__HISTORY24:  return "HISTORY24";
            case TARGET_TYPE__HISTORY25:  return "HISTORY25";
            case TARGET_TYPE__HISTORY26:  return "HISTORY26";
            case TARGET_TYPE__HISTORY27:  return "HISTORY27";
            case TARGET_TYPE__HISTORY28:  return "HISTORY28";
            case TARGET_TYPE__HISTORY29:  return "HISTORY29";

            default:
                return super.getTargetTypeName(targetType);
        }
    }

    protected void initializeFacetAttributeData()
    {
        super.initializeFacetAttributeData();
        
        Map mm6FacetAttributeBitByFacetAttributeTypeMap = super.getFacetAttributeBitByFacetAttributeTypeMap();
        Map mm6FacetAttributeNameByFacetAttributeTypeMap = super.getFacetAttributeNameByFacetAttributeTypeMap();
        
        Object replacementFacetAttributeData[] = new Object[] {
            	new Integer(FACET_ATTRIBUTE_TYPE_Secret), new Integer(2), "facet is a 'secret', flagged by level designer",
        	    new Integer(FACET_ATTRIBUTE_TYPE_ScrollDn), new Integer(3), "facet texture will scroll down",
        	    new Integer(FACET_ATTRIBUTE_TYPE_ScrollUp), new Integer(6), "facet texture will scroll up",
        	    new Integer(FACET_ATTRIBUTE_TYPE_ScrollLt), new Integer(7), "facet texture will scroll left",
        	    new Integer(FACET_ATTRIBUTE_TYPE_ScrollRt), new Integer(12), "facet texture will scroll right",
        	    new Integer(FACET_ATTRIBUTE_TYPE_Outline), new Integer(17), "This facet wants to be outlined"
            };

       int facetAttributeTypeArrayColumns = 3;
       if (replacementFacetAttributeData.length % facetAttributeTypeArrayColumns != 0)
       {
           throw new RuntimeException("replacementFacetAttributeData.length = " + replacementFacetAttributeData.length + " which is not a power of " + facetAttributeTypeArrayColumns + ".");
       }

       for (int index = 0; index < replacementFacetAttributeData.length; index = index+facetAttributeTypeArrayColumns)
       {
           Integer facetAttributeType = (Integer)replacementFacetAttributeData[index];
           Integer facetAttributeBit = (Integer)replacementFacetAttributeData[index+1];
           String facetAttributeName = (String)replacementFacetAttributeData[index+2];

           Integer key = facetAttributeType;
           
           Integer newFacetAttributeBitValue = facetAttributeBit;
           if (false == mm6FacetAttributeBitByFacetAttributeTypeMap.containsKey(key))
           {
               throw new RuntimeException("mm6FacetAttributeBitByFacetAttributeTypeMap does not contain key " + key + " for replacement value of " + newFacetAttributeBitValue);
           }
           mm6FacetAttributeBitByFacetAttributeTypeMap.put(key, newFacetAttributeBitValue);
           
           String newFacetAttributeNameValue = facetAttributeName;
           if (false == mm6FacetAttributeNameByFacetAttributeTypeMap.containsKey(key))
           {
               throw new RuntimeException("mm6FacetAttributeNameByFacetAttributeTypeMap does not contain key " + key + " for replacement value of " + newFacetAttributeNameValue);
           }
           mm6FacetAttributeNameByFacetAttributeTypeMap.put(key, newFacetAttributeNameValue);
       }
    }
    
    protected void initializeMiniIconNameChoiceData()
    {
        miniIconNameChoiceArray = new String[] {
                "NO SYMBOL", // 0
                "ticon01",		//1
                "ticon02",		//2
                "ticon03",		//3
                "ticon04",		//4
                "ticon05",		//5
                "istairup",		//6
                "itrap",		//7
                "outside",		//8
                "idoor",		//9
                "isecdoor"		//10
        };
        
        initializedPartyMemberChoiceData = true;
    }
}
