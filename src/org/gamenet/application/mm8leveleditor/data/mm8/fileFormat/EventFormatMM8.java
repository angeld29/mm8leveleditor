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

package org.gamenet.application.mm8leveleditor.data.mm8.fileFormat;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gamenet.application.mm8leveleditor.data.Event;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.EventFormatMM6;
import org.gamenet.application.mm8leveleditor.data.mm7.fileFormat.EventFormatMM7;

public class EventFormatMM8 extends EventFormatMM7
{
    public static final int EVENT_COMMAND__ScheduleEvent = MM7_EVENT_LAST_COMMAND_TYPE_VALUE + 1; // 61
    public static final int EVENT_COMMAND__ScheduleEventSet = MM7_EVENT_LAST_COMMAND_TYPE_VALUE + 2; // 62
    public static final int EVENT_COMMAND__EV_StopDoor = MM7_EVENT_LAST_COMMAND_TYPE_VALUE + 3; // 63
    public static final int EVENT_COMMAND__IfHasItemRangeQty = MM7_EVENT_LAST_COMMAND_TYPE_VALUE + 4; // 64
    public static final int EVENT_COMMAND__TakeItemRangeQty = MM7_EVENT_LAST_COMMAND_TYPE_VALUE + 5; // 65
    public static final int EVENT_COMMAND__LaunchParty = MM7_EVENT_LAST_COMMAND_TYPE_VALUE + 6; // 66
    public static final int EVENT_COMMAND__IfHasBountyRange = MM7_EVENT_LAST_COMMAND_TYPE_VALUE + 7; // 67
    public static final int EVENT_COMMAND__IfPCCanAct = MM7_EVENT_LAST_COMMAND_TYPE_VALUE + 8; // 68

    public static final int MM8_EVENT_LAST_COMMAND_TYPE_VALUE = EVENT_COMMAND__IfPCCanAct;
    
    
    public static final int TARGET_TYPE__SKILL_REGENERATION = EVENTMM7_LAST_TARGET_TYPE_VALUE + 1;
    public static final int TARGET_TYPE__SKILL_DISARM_TRAP = EVENTMM7_LAST_TARGET_TYPE_VALUE + 2;
    public static final int TARGET_TYPE__SKILL_DARKELF_ABILITY = EVENTMM7_LAST_TARGET_TYPE_VALUE + 3;
    public static final int TARGET_TYPE__SKILL_VAMPIRE_ABILITY = EVENTMM7_LAST_TARGET_TYPE_VALUE + 4;
    public static final int TARGET_TYPE__SKILL_DRAGON_ABILITY = EVENTMM7_LAST_TARGET_TYPE_VALUE + 5;
    public static final int TARGET_TYPE__ROSTER_CHARACTER = EVENTMM7_LAST_TARGET_TYPE_VALUE + 6;
    
    public EventFormatMM8()
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
			EventFormatMM8 eventFormatMM8 = new EventFormatMM8();
			List<Event> eventList = eventFormatMM8.readEvents(data);
			Iterator<Event> eventIterator = eventList.iterator();
			while (eventIterator.hasNext()) {
				Event event = eventIterator.next();
				if (!eventFormatMM8.isValid(event))
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
                new Integer(EVENT_COMMAND__ChangeMonsterInv), // 60;
                
                // mm8-only
                new Integer(EVENT_COMMAND__ScheduleEvent), // 61
                new Integer(EVENT_COMMAND__ScheduleEventSet), // 62
                new Integer(EVENT_COMMAND__EV_StopDoor), // 63
                new Integer(EVENT_COMMAND__IfHasItemRangeQty), // 64
                new Integer(EVENT_COMMAND__TakeItemRangeQty), // 65
                new Integer(EVENT_COMMAND__LaunchParty), // 66
                new Integer(EVENT_COMMAND__IfHasBountyRange), // 67
                new Integer(EVENT_COMMAND__IfPCCanAct) // 68
        };
    }
    
    public String getCommandTypeName(int commandType)
    {
        Integer commandTypeKey = getCommandTypeKey(commandType);
        if (null == commandTypeKey)
        {
            return "UNKNOWN COMMAND TYPE 0x" + Integer.toHexString(commandType);
        }

        switch(commandTypeKey.intValue())
        {
            case EVENT_COMMAND__ScheduleEvent:  return "ScheduleEvent";
            case EVENT_COMMAND__ScheduleEventSet:  return "ScheduleEventSet";
            case EVENT_COMMAND__EV_StopDoor:  return "EV_StopDoor";
            case EVENT_COMMAND__IfHasItemRangeQty:  return "IfHasItemRangeQty";
            case EVENT_COMMAND__TakeItemRangeQty:  return "TakeItemRangeQty";
            case EVENT_COMMAND__LaunchParty:  return "LaunchParty";
            case EVENT_COMMAND__IfHasBountyRange:  return "IfHasBountyRange";
            case EVENT_COMMAND__IfPCCanAct:  return "IfPCCanAct";
            default:
                return super.getCommandTypeName(commandType);
        }
    }

    protected void initializeCommandTypeInfo()
    {
        super.initializeCommandTypeInfo();
        
        Map mm7ArgumentTypeArrayByCommandTypeMap = super.getArgumentTypeArrayByCommandTypeMap();
        Map mm7ArgumentTypeDisplayInfoArrayByCommandTypeMap = super.getArgumentTypeDisplayInfoArrayByCommandTypeMap();
        
        Object newArgumentsByCommandType[] =  new Object[] {
                new Integer(EVENT_COMMAND__ScheduleEvent),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                new Integer(EVENT_COMMAND__ScheduleEventSet),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                new Integer(EVENT_COMMAND__EV_StopDoor),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                new Integer(EVENT_COMMAND__IfHasItemRangeQty),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                new Integer(EVENT_COMMAND__TakeItemRangeQty),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                new Integer(EVENT_COMMAND__LaunchParty),  new Object[] { 
                        _I_(ARGUMENT_TYPE__UNKNOWN_SHORT), "phi", null, _I_(0),
                        _I_(ARGUMENT_TYPE__UNKNOWN_SHORT), "theta", null, _I_(1),
                        _I_(ARGUMENT_TYPE__UNKNOWN_INTEGER), "velocity", null, _I_(2)
                },
                new Integer(EVENT_COMMAND__IfHasBountyRange),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) },
                new Integer(EVENT_COMMAND__IfPCCanAct),  new Object[] { _I_(ARGUMENT_TYPE__UNKNOWN), null, null, _I_(0) }
        };
        
        int argumentTypesForCommandTypeArrayColumns = 2;
        if (newArgumentsByCommandType.length % argumentTypesForCommandTypeArrayColumns != 0)
        {
            throw new RuntimeException("argsAndSizeArray.length = " + newArgumentsByCommandType.length + " which is not a power of " + argumentTypesForCommandTypeArrayColumns + ".");
        }

        for (int index = 0; index < newArgumentsByCommandType.length; index = index+argumentTypesForCommandTypeArrayColumns)
        {
            Integer commandTypeNumber = (Integer)newArgumentsByCommandType[index];
            Object argumentTypeDataArray[] = (Object [])newArgumentsByCommandType[index+1];

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

            if (argumentTypeArrayByCommandTypeMap.containsKey(key))
            {
                Object value = mm7ArgumentTypeArrayByCommandTypeMap.get(key);
                throw new RuntimeException("mm7ArgumentTypeArrayByCommandTypeMap already contains key " + key + " with value of " + value);
            }

            mm7ArgumentTypeArrayByCommandTypeMap.put(key, newArgumentTypeArrayValue);

        
            ArgumentTypeDisplayInfo[] argumentTypeDisplayInfoArray = getArgumentTypeDisplayInfoArrayFromArgumentTypeDataArray(argumentTypeDataArray);
            Object newArgumentTypeDisplayInfoArrayValue = argumentTypeDisplayInfoArray;

            if (mm7ArgumentTypeDisplayInfoArrayByCommandTypeMap.containsKey(key))
            {
                Object value = mm7ArgumentTypeDisplayInfoArrayByCommandTypeMap.get(key);
                throw new RuntimeException("mm7ArgumentTypeDisplayInfoArrayByCommandTypeMap already contains key " + key + " with value of " + value);
            }

            mm7ArgumentTypeDisplayInfoArrayByCommandTypeMap.put(key, newArgumentTypeDisplayInfoArrayValue);
        }
    }

    protected void initializeArgumentTypeInfo()
    {
        super.initializeArgumentTypeInfo();
        
        Map sizeByArgumentTypeMap = super.getSizeByArgumentTypeMap();
        sizeByArgumentTypeMap.put(new Integer(ARGUMENT_TYPE__DIALOG_NUMBER), new Integer(2));

        Map argumentDataTypeByArgumentTypeMap = super.getArgumentDataTypeByArgumentTypeMap();
        argumentDataTypeByArgumentTypeMap.put(new Integer(ARGUMENT_TYPE__DIALOG_NUMBER), new Integer(ARGUMENT_DATA_TYPE__UNSIGNED_SHORT));
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
                new Integer(TARGET_TYPE__SKILL_REGENERATION), // 95
                new Integer(TARGET_TYPE__SKILL_DISARM_TRAP), // 96
                new Integer(TARGET_TYPE__SKILL_DODGING), // 97
                new Integer(TARGET_TYPE__SKILL_UNARMED), // 98
                new Integer(TARGET_TYPE__SKILL_IDENTIFY_MONSTER), // 99
                new Integer(TARGET_TYPE__SKILL_ARMSMASTER), // 100
                new Integer(TARGET_TYPE__SKILL_STEALING), // 101
                new Integer(TARGET_TYPE__SKILL_ALCHEMY), // 102
                new Integer(TARGET_TYPE__SKILL_LEARNING), // 103
                new Integer(TARGET_TYPE__SKILL_DARKELF_ABILITY), // 104
                new Integer(TARGET_TYPE__SKILL_VAMPIRE_ABILITY), // 105
                new Integer(TARGET_TYPE__SKILL_DRAGON_ABILITY), // 106
                new Integer(TARGET_TYPE__CONDITION_CURSED), // 107
                new Integer(TARGET_TYPE__CONDITION_WEAK), // 108
                new Integer(TARGET_TYPE__CONDITION_ASLEEP), // 109
                new Integer(TARGET_TYPE__CONDITION_AFRAID), // 110
                new Integer(TARGET_TYPE__CONDITION_DRUNK), // 111
                new Integer(TARGET_TYPE__CONDITION_INSANE), // 112
                new Integer(TARGET_TYPE__CONDITION_POISON1), // 113
                new Integer(TARGET_TYPE__CONDITION_DISEASE1), // 114
                new Integer(TARGET_TYPE__CONDITION_POISON2), // 115
                new Integer(TARGET_TYPE__CONDITION_DISEASE2), // 116
                new Integer(TARGET_TYPE__CONDITION_POISON3), // 117
                new Integer(TARGET_TYPE__CONDITION_DISEASE3), // 118
                new Integer(TARGET_TYPE__CONDITION_PARALYZED), // 119
                new Integer(TARGET_TYPE__CONDITION_UNCONSCIOUS), // 120
                new Integer(TARGET_TYPE__CONDITION_DEAD), // 121
                new Integer(TARGET_TYPE__CONDITION_STONE), // 122
                new Integer(TARGET_TYPE__CONDITION_ERADICATED), // 123
                new Integer(TARGET_TYPE__CONDITION_GOOD), // 124
                new Integer(TARGET_TYPE__DUNGEON_BYTE_1), // 125
                new Integer(TARGET_TYPE__DUNGEON_BYTE_2), // 126
                new Integer(TARGET_TYPE__DUNGEON_BYTE_3), // 127
                new Integer(TARGET_TYPE__DUNGEON_BYTE_4), // 128
                new Integer(TARGET_TYPE__DUNGEON_BYTE_5), // 129
                new Integer(TARGET_TYPE__DUNGEON_BYTE_6), // 130
                new Integer(TARGET_TYPE__DUNGEON_BYTE_7), // 131
                new Integer(TARGET_TYPE__DUNGEON_BYTE_8), // 132
                new Integer(TARGET_TYPE__DUNGEON_BYTE_9), // 133
                new Integer(TARGET_TYPE__DUNGEON_BYTE_10), // 134
                new Integer(TARGET_TYPE__DUNGEON_BYTE_11), // 135
                new Integer(TARGET_TYPE__DUNGEON_BYTE_12), // 136
                new Integer(TARGET_TYPE__DUNGEON_BYTE_13), // 137
                new Integer(TARGET_TYPE__DUNGEON_BYTE_14), // 138
                new Integer(TARGET_TYPE__DUNGEON_BYTE_15), // 139
                new Integer(TARGET_TYPE__DUNGEON_BYTE_16), // 140
                new Integer(TARGET_TYPE__DUNGEON_BYTE_17), // 141
                new Integer(TARGET_TYPE__DUNGEON_BYTE_18), // 142
                new Integer(TARGET_TYPE__DUNGEON_BYTE_19), // 143
                new Integer(TARGET_TYPE__DUNGEON_BYTE_20), // 144
                new Integer(TARGET_TYPE__DUNGEON_BYTE_21), // 145
                new Integer(TARGET_TYPE__DUNGEON_BYTE_22), // 146
                new Integer(TARGET_TYPE__DUNGEON_BYTE_23), // 147
                new Integer(TARGET_TYPE__DUNGEON_BYTE_24), // 148
                new Integer(TARGET_TYPE__DUNGEON_BYTE_25), // 149
                new Integer(TARGET_TYPE__DUNGEON_BYTE_26), // 150
                new Integer(TARGET_TYPE__DUNGEON_BYTE_27), // 151
                new Integer(TARGET_TYPE__DUNGEON_BYTE_28), // 152
                new Integer(TARGET_TYPE__DUNGEON_BYTE_29), // 153
                new Integer(TARGET_TYPE__DUNGEON_BYTE_30), // 154
                new Integer(TARGET_TYPE__DUNGEON_BYTE_31), // 155
                new Integer(TARGET_TYPE__DUNGEON_BYTE_32), // 156
                new Integer(TARGET_TYPE__DUNGEON_BYTE_33), // 157
                new Integer(TARGET_TYPE__DUNGEON_BYTE_34), // 158
                new Integer(TARGET_TYPE__DUNGEON_BYTE_35), // 159
                new Integer(TARGET_TYPE__DUNGEON_BYTE_36), // 160
                new Integer(TARGET_TYPE__DUNGEON_BYTE_37), // 161
                new Integer(TARGET_TYPE__DUNGEON_BYTE_38), // 162
                new Integer(TARGET_TYPE__DUNGEON_BYTE_39), // 163
                new Integer(TARGET_TYPE__DUNGEON_BYTE_40), // 164
                new Integer(TARGET_TYPE__DUNGEON_BYTE_41), // 165
                new Integer(TARGET_TYPE__DUNGEON_BYTE_42), // 166
                new Integer(TARGET_TYPE__DUNGEON_BYTE_43), // 167
                new Integer(TARGET_TYPE__DUNGEON_BYTE_44), // 168
                new Integer(TARGET_TYPE__DUNGEON_BYTE_45), // 169
                new Integer(TARGET_TYPE__DUNGEON_BYTE_46), // 170
                new Integer(TARGET_TYPE__DUNGEON_BYTE_47), // 171
                new Integer(TARGET_TYPE__DUNGEON_BYTE_48), // 172
                new Integer(TARGET_TYPE__DUNGEON_BYTE_49), // 173
                new Integer(TARGET_TYPE__DUNGEON_BYTE_50), // 174
                new Integer(TARGET_TYPE__DUNGEON_BYTE_51), // 175
                new Integer(TARGET_TYPE__DUNGEON_BYTE_52), // 176
                new Integer(TARGET_TYPE__DUNGEON_BYTE_53), // 177
                new Integer(TARGET_TYPE__DUNGEON_BYTE_54), // 178
                new Integer(TARGET_TYPE__DUNGEON_BYTE_55), // 179
                new Integer(TARGET_TYPE__DUNGEON_BYTE_56), // 180
                new Integer(TARGET_TYPE__DUNGEON_BYTE_57), // 181
                new Integer(TARGET_TYPE__DUNGEON_BYTE_58), // 182
                new Integer(TARGET_TYPE__DUNGEON_BYTE_59), // 183
                new Integer(TARGET_TYPE__DUNGEON_BYTE_60), // 184
                new Integer(TARGET_TYPE__DUNGEON_BYTE_61), // 185
                new Integer(TARGET_TYPE__DUNGEON_BYTE_62), // 186
                new Integer(TARGET_TYPE__DUNGEON_BYTE_63), // 187
                new Integer(TARGET_TYPE__DUNGEON_BYTE_64), // 188
                new Integer(TARGET_TYPE__DUNGEON_BYTE_65), // 189
                new Integer(TARGET_TYPE__DUNGEON_BYTE_66), // 190
                new Integer(TARGET_TYPE__DUNGEON_BYTE_67), // 191
                new Integer(TARGET_TYPE__DUNGEON_BYTE_68), // 192
                new Integer(TARGET_TYPE__DUNGEON_BYTE_69), // 193
                new Integer(TARGET_TYPE__DUNGEON_BYTE_70), // 194
                new Integer(TARGET_TYPE__DUNGEON_BYTE_71), // 195
                new Integer(TARGET_TYPE__DUNGEON_BYTE_72), // 196
                new Integer(TARGET_TYPE__DUNGEON_BYTE_73), // 197
                new Integer(TARGET_TYPE__DUNGEON_BYTE_74), // 198
                new Integer(TARGET_TYPE__DUNGEON_BYTE_75), // 199
                new Integer(TARGET_TYPE__DUNGEON_BYTE_76), // 200
                new Integer(TARGET_TYPE__DUNGEON_BYTE_77), // 201
                new Integer(TARGET_TYPE__DUNGEON_BYTE_78), // 202
                new Integer(TARGET_TYPE__DUNGEON_BYTE_79), // 203
                new Integer(TARGET_TYPE__DUNGEON_BYTE_80), // 204
                new Integer(TARGET_TYPE__DUNGEON_BYTE_81), // 205
                new Integer(TARGET_TYPE__DUNGEON_BYTE_82), // 206
                new Integer(TARGET_TYPE__DUNGEON_BYTE_83), // 207
                new Integer(TARGET_TYPE__DUNGEON_BYTE_84), // 208
                new Integer(TARGET_TYPE__DUNGEON_BYTE_85), // 209
                new Integer(TARGET_TYPE__DUNGEON_BYTE_86), // 210
                new Integer(TARGET_TYPE__DUNGEON_BYTE_87), // 211
                new Integer(TARGET_TYPE__DUNGEON_BYTE_88), // 212
                new Integer(TARGET_TYPE__DUNGEON_BYTE_89), // 213
                new Integer(TARGET_TYPE__DUNGEON_BYTE_90), // 214
                new Integer(TARGET_TYPE__DUNGEON_BYTE_91), // 215
                new Integer(TARGET_TYPE__DUNGEON_BYTE_92), // 216
                new Integer(TARGET_TYPE__DUNGEON_BYTE_93), // 217
                new Integer(TARGET_TYPE__DUNGEON_BYTE_94), // 218
                new Integer(TARGET_TYPE__DUNGEON_BYTE_95), // 219
                new Integer(TARGET_TYPE__DUNGEON_BYTE_96), // 220
                new Integer(TARGET_TYPE__DUNGEON_BYTE_97), // 221
                new Integer(TARGET_TYPE__DUNGEON_BYTE_98), // 222
                new Integer(TARGET_TYPE__DUNGEON_BYTE_99), // 223
                new Integer(TARGET_TYPE__DUNGEON_BYTE_100), // 224
                new Integer(TARGET_TYPE__EVENT_AUTONOTE), // 225
                new Integer(TARGET_TYPE__MIGHT_MAX), // 226
                new Integer(TARGET_TYPE__INTELLECT_MAX), // 227
                new Integer(TARGET_TYPE__PERSONALITY_MAX), // 228
                new Integer(TARGET_TYPE__ENDURANCE_MAX), // 229
                new Integer(TARGET_TYPE__SPEED_MAX), // 230
                new Integer(TARGET_TYPE__ACCURACY_MAX), // 231
                new Integer(TARGET_TYPE__LUCK_MAX), // 232
                new Integer(TARGET_TYPE__CHARACTER_BIT), // 233
                new Integer(TARGET_TYPE__NPC_IN_PARTY), // 234
                new Integer(TARGET_TYPE__REPUTATION), // 235
                new Integer(TARGET_TYPE__DAYS_PAST_1), // 236
                new Integer(TARGET_TYPE__DAYS_PAST_2), // 237
                new Integer(TARGET_TYPE__DAYS_PAST_3), // 238
                new Integer(TARGET_TYPE__DAYS_PAST_4), // 239
                new Integer(TARGET_TYPE__DAYS_PAST_5), // 240
                new Integer(TARGET_TYPE__DAYS_PAST_6), // 241
                new Integer(TARGET_TYPE__PARTY_FLYING), // 242
                new Integer(TARGET_TYPE__NPC_PROFESSION_IN_PARTY), // 243
                new Integer(TARGET_TYPE__CIRCUS_TOTAL), // 244
                new Integer(TARGET_TYPE__SKILL_POINTS), // 245
                new Integer(TARGET_TYPE__MONTH_OF_YEAR), // 246
                new Integer(TARGET_TYPE__TIMER1), // 247
                new Integer(TARGET_TYPE__TIMER2), // 248
                new Integer(TARGET_TYPE__TIMER3), // 249
                new Integer(TARGET_TYPE__TIMER4), // 250
                new Integer(TARGET_TYPE__TIMER5), // 251
                new Integer(TARGET_TYPE__TIMER6), // 252
                new Integer(TARGET_TYPE__TIMER7), // 253
                new Integer(TARGET_TYPE__TIMER8), // 254
                new Integer(TARGET_TYPE__TIMER9), // 255
                new Integer(TARGET_TYPE__TIMER10), // 256
                new Integer(TARGET_TYPE__IMPORTANT1), // 257
                new Integer(TARGET_TYPE__IMPORTANT2), // 258
                new Integer(TARGET_TYPE__IMPORTANT3), // 259
                new Integer(TARGET_TYPE__IMPORTANT4), // 260
                new Integer(TARGET_TYPE__IMPORTANT5), // 261
                new Integer(TARGET_TYPE__IMPORTANT6), // 262
                new Integer(TARGET_TYPE__IMPORTANT7), // 263
                new Integer(TARGET_TYPE__IMPORTANT8), // 264
                new Integer(TARGET_TYPE__IMPORTANT9), // 265
                new Integer(TARGET_TYPE__IMPORTANT10), // 266
                new Integer(TARGET_TYPE__IMPORTANT11), // 267
                new Integer(TARGET_TYPE__IMPORTANT12), // 268
                new Integer(TARGET_TYPE__IMPORTANT13), // 269
                new Integer(TARGET_TYPE__IMPORTANT14), // 270
                new Integer(TARGET_TYPE__IMPORTANT15), // 271
                new Integer(TARGET_TYPE__IMPORTANT16), // 272
                new Integer(TARGET_TYPE__IMPORTANT17), // 273
                new Integer(TARGET_TYPE__IMPORTANT18), // 274
                new Integer(TARGET_TYPE__IMPORTANT19), // 275
                new Integer(TARGET_TYPE__IMPORTANT20), // 276
                new Integer(TARGET_TYPE__MAP_STEAL_DIFFICULTY_TEMP), // 277
                new Integer(TARGET_TYPE__HISTORY1), // 278
                new Integer(TARGET_TYPE__HISTORY2), // 279
                new Integer(TARGET_TYPE__HISTORY3), // 280
                new Integer(TARGET_TYPE__HISTORY4), // 281
                new Integer(TARGET_TYPE__HISTORY5), // 282
                new Integer(TARGET_TYPE__HISTORY6), // 283
                new Integer(TARGET_TYPE__HISTORY7), // 284
                new Integer(TARGET_TYPE__HISTORY8), // 285
                new Integer(TARGET_TYPE__HISTORY9), // 286
                new Integer(TARGET_TYPE__HISTORY10), // 287
                new Integer(TARGET_TYPE__HISTORY11), // 288
                new Integer(TARGET_TYPE__HISTORY12), // 289
                new Integer(TARGET_TYPE__HISTORY13), // 290
                new Integer(TARGET_TYPE__HISTORY14), // 291
                new Integer(TARGET_TYPE__HISTORY15), // 292
                new Integer(TARGET_TYPE__HISTORY16), // 293
                new Integer(TARGET_TYPE__HISTORY17), // 294
                new Integer(TARGET_TYPE__HISTORY18), // 295
                new Integer(TARGET_TYPE__HISTORY19), // 296
                new Integer(TARGET_TYPE__HISTORY20), // 297
                new Integer(TARGET_TYPE__HISTORY21), // 298
                new Integer(TARGET_TYPE__HISTORY22), // 299
                new Integer(TARGET_TYPE__HISTORY23), // 300
                new Integer(TARGET_TYPE__HISTORY24), // 301
                new Integer(TARGET_TYPE__HISTORY25), // 302
                new Integer(TARGET_TYPE__HISTORY26), // 303
                new Integer(TARGET_TYPE__HISTORY27), // 304
                new Integer(TARGET_TYPE__HISTORY28), // 305
                new Integer(TARGET_TYPE__HISTORY29), // 306
                new Integer(TARGET_TYPE__MAP_ON_ALERT), // 307
                new Integer(TARGET_TYPE__BANK_GOLD), // 308
                new Integer(TARGET_TYPE__DEATH), // 309
                new Integer(TARGET_TYPE__BOUNTY), // 310
                new Integer(TARGET_TYPE__PRISON_TERM), // 311
                new Integer(TARGET_TYPE__ARENA_PAGE), // 312
                new Integer(TARGET_TYPE__ARENA_SQUIRE), // 313
                new Integer(TARGET_TYPE__ARENA_KNIGHT), // 314
                new Integer(TARGET_TYPE__ARENA_LORD), // 315
                new Integer(TARGET_TYPE__INVISIBILITY), // 316
                new Integer(TARGET_TYPE__EQUIP), // 317
                new Integer(TARGET_TYPE__ROSTER_CHARACTER) // 318
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
            case TARGET_TYPE__SKILL_REGENERATION:  return "SKILL_REGENERATION";
            case TARGET_TYPE__SKILL_DISARM_TRAP:  return "SKILL_DISARM_TRAP";
            case TARGET_TYPE__SKILL_DARKELF_ABILITY:  return "SKILL_DARKELF_ABILITY";
            case TARGET_TYPE__SKILL_VAMPIRE_ABILITY:  return "SKILL_VAMPIRE_ABILITY";
            case TARGET_TYPE__SKILL_DRAGON_ABILITY:  return "SKILL_DRAGON_ABILITY";
            case TARGET_TYPE__ROSTER_CHARACTER:  return "ROSTER_CHARACTER";

            default:
                return super.getTargetTypeName(targetType);
        }
    }

    protected void initializePartyMemberChoiceData()
    {
        partyMemberChoiceArray = new String[] {
                "outer-left character", // 0
                "inner-left character", // 1
                "middle character", // 2
                "inner-right character", // 3
                "outer-right character", // 4
                "any/all characters", // 5
                "random character", // 6
                "current character" // 7
        };
        
        initializedPartyMemberChoiceData = true;
    }
}
