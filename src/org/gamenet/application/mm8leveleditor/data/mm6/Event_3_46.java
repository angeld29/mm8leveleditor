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
import java.util.Iterator;
import java.util.List;

import org.gamenet.util.ByteConversions;

public class Event_3_46
{
    public static int EVENT_NUMBER_OFFSET = 0; // two bytes
    private static int EVENT_SEQUENCE_OFFSET = 2; // one byte
    private static int EVENT_COMMAND_OFFSET = 3; // one byte
    public static int EVENT_COMMAND_ARGS_OFFSET = 4; // one byte
    
    // commands
    public static final int EVENT_COMMAND__STOP_PROCESSING = 1;
    public static final int EVENT_COMMAND__IDENTITY = 2;
    //
    public static final int EVENT_COMMAND__MOUSEOVER = 4;
    //
    public static final int EVENT_COMMAND__TELEPORT = 6;
    public static final int EVENT_COMMAND__OPEN_CHEST = 7;
    public static final int EVENT_COMMAND__SHOW_FACIAL_EXPRESSION = 8;
    //
    public static final int EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP = 12;
    public static final int EVENT_COMMAND__CHANGE_SPRITE = 13;
    public static final int EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE = 14;
    //
    public static final int EVENT_COMMAND__ADD_TARGET = 16;
    public static final int EVENT_COMMAND__DELETE_TARGET = 17;
    public static final int EVENT_COMMAND__SET_TARGET = 18;
    public static final int EVENT_COMMAND__CREATE_LOCAL_MONSTER = 19;
    //
    public static final int EVENT_COMMAND__CAST_SPELL_FROM_LOCATION = 21;
    //
    public static final int EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING = 29;
    public static final int EVENT_COMMAND__SHOW_NPCTEXT_STRING = 30;
    //
    public static final int EVENT_COMMAND__UNKNOWN_VOLCANO_EFFECT = 34; // something to do with the volcano buoy effect at New Sorpigal
    public static final int EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER = 35;
    public static final int EVENT_COMMAND__GOTO = 36;
    public static final int EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE = 37; // Stop processing / On level load level?
    public static final int EVENT_COMMAND__UNKNOWN_ON_X2_EXECUTE = 38;
    public static final int EVENT_COMMAND__CHANGE_DIALOG_EVENT = 39; // npctext/npctopic

    public static final int STOP_PROCESSING_ARGS_UNKNOWN1_OFFSET = 0;
    
    public static final int IDENTITY_ARGS_2DEVENT_ID_OFFSET = 0;
    
    public static final int MOUSEOVER_ARGS_EVENT_STR_ID_OFFSET = 0;
    
    public static final int TELEPORT_ARGS_DESTINATION_X_COORD_OFFSET = 0; // 4 bytes
    public static final int TELEPORT_ARGS_DESTINATION_Y_COORD_OFFSET = 4; // 4 bytes
    public static final int TELEPORT_ARGS_DESTINATION_Z_COORD_OFFSET = 8; // 4 bytes
    public static final int TELEPORT_ARGS_DESTINATION_HORIZONTAL_ORIENTATION_OFFSET = 12; // 2 bytes
    private static final int TELEPORT_ARGS_UNKNOWN1_OFFSET = 14; // 2 bytes
    public static final int TELEPORT_ARGS_DESTINATION_VERTICAL_ORIENTATION_OFFSET = 16; // 2 bytes
    private static final int TELEPORT_ARGS_UNKNOWN2_OFFSET = 18; // 2 bytes
    private static final int TELEPORT_ARGS_UNKNOWN3_OFFSET = 20; // 4 bytes
    public static final int TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET = 24; // 1 byte
    public static final int TELEPORT_ARGS_MINIICON_OFFSET = 25; // 1 byte
    public static final int TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET = 26; // 8 bytes
    public static final int TELEPORT_ARGS_DESTINATION_FILENAME_MAXLENGTH = 13;

    public static final int OPEN_CHEST_ARGS_CHEST_NUMBER_OFFSET = 0;
    
    public static final int SHOW_FACIAL_EXPRESSION_ARGS_AFFECTED_OFFSET = 0;
    public static final int SHOW_FACIAL_EXPRESSION_ARGS_IMAGE_ID_OFFSET = 1;
    
    public static final int CHANGE_3DOBJECT_FACE_BITMAP_ARGS_3D_OBJECT_NUMBER_OFFSET = 0; // object #
    public static final int CHANGE_3DOBJECT_FACE_BITMAP_ARGS_FACE_OFFSET = 4; // face #
    public static final int CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET = 8;
    public static final int CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_MAXLENGTH = 12;
    
    public static final int CHANGE_SPRITE_ARGS_SPRITE_NUMBER = 0;
    private static final int CHANGE_SPRITE_ARGS_UNKNOWN1_OFFSET = 2;
    private static final int CHANGE_SPRITE_ARGS_UNKNOWN2_OFFSET = 4;
    public static final int CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET = 5;
    public static final int CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_MAXLENGTH = 12;

    public static final int BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_TYPE_OFFSET = 0;
    public static final int BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_NUMBER_OFFSET = 1;
    private static final int BRANCH_ON_CONDITION_TRUE_ARGS_UNKNOWN1_OFFSET = 3;
    public static final int BRANCH_ON_CONDITION_TRUE_ARGS_GOTO_SEQUENCE_NUMBER_OFFSET = 5;
    
    public static final int ADD_TARGET_ARGS_TARGET_TYPE_OFFSET = 0;
    public static final int ADD_TARGET_ARGS_TARGET_NUMBER_OFFSET = 1;

    public static final int DELETE_TARGET_ARGS_TARGET_TYPE_OFFSET = 0;
    public static final int DELETE_TARGET_ARGS_TARGET_NUMBER_OFFSET = 1;
    private static final int DELETE_TARGET_ARGS_UNKNOWN1_OFFSET = 3;

    public static final int SET_TARGET_ARGS_TARGET_TYPE_OFFSET = 0;
    public static final int SET_TARGET_ARGS_TARGET_NUMBER_OFFSET = 1;
    private static final int SET_TARGET_ARGS_UNKNOWN1_OFFSET = 3;

    public static final int CREATE_LOCAL_MONSTER_ARGS_MONSTER_SPECIES_OFFSET = 0; // mod 3, remainder ups subspecies
    public static final int CREATE_LOCAL_MONSTER_ARGS_MONSTER_SUBSPECIES__OFFSET = 1; // 0 or 1, 2, 3
    public static final int CREATE_LOCAL_MONSTER_ARGS_COUNT_OFFSET = 2;
    public static final int CREATE_LOCAL_MONSTER_ARGS_X_OFFSET = 3;
    public static final int CREATE_LOCAL_MONSTER_ARGS_Y_OFFSET = 7;
    public static final int CREATE_LOCAL_MONSTER_ARGS_Z_OFFSET = 11;

    public static final int CAST_SPELL_FROM_LOCATION_ARGS_SPELL_NUMBER_OFFSET = 0; // 1 byte
    public static final int CAST_SPELL_FROM_LOCATION_ARGS_RANKING_OFFSET = 1; // 1 byte
    public static final int CAST_SPELL_FROM_LOCATION_ARGS_LEVEL_OFFSET = 2; // 1 byte
    public static final int CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_X_OFFSET = 3; // 4 byte
    public static final int CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Y_OFFSET = 7; // 4 byte
    public static final int CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Z_OFFSET = 11; // 4 byte
    public static final int CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN2_OFFSET = 15; // 4 byte - zeros
    public static final int CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN3_OFFSET = 19; // 4 byte - zeros
    public static final int CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN4_OFFSET = 23; // 4 byte - zeros

    public static final int SHOW_LOCAL_EVENT_STRING_ARGS_STR_ID_OFFSET = 0;
    private static final int SHOW_LOCAL_EVENT_STRING_ARGS_UNKNOWN1_OFFSET = 2;
    
    public static final int SHOW_NPCTEXT_STRING_ARGS_STR_ID_OFFSET = 0;
    private static final int SHOW_NPCTEXT_STRING_ARGS_UNKNOWN1_OFFSET = 2;
    
    public static final int MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER_ARGS_AFFECTED_OFFSET = 0;
    
    public static final int GOTO_ARGS_SEQUENCE_NUMBER_OFFSET = 0;

    public static final int CHANGE_DIALOG_EVENT_ARGS_NPCDATA_OFFSET = 0; // 4 byte
    public static final int CHANGE_DIALOG_EVENT_ARGS_NPC_MENU_INDEX_OFFSET = 4; // 1 byte
    public static final int CHANGE_DIALOG_EVENT_ARGS_NEW_GLOBAL_EVENT_NUMBER_OFFSET = 5; // 5 byte

    //     2: add 305 HP / zeros SP
    public static final int TARGET_TYPE__TEMP_HP = 3;
    public static final int TARGET_TYPE__TEMP_HP_EQ_MAX_HP = 4;
    public static final int TARGET_TYPE__TEMP_SP = 5; // TARGET_TYPE__TEMP_SP
    public static final int TARGET_TYPE__TEMP_SP_EQ_MAX_SP = 6; // conditional, maybe?
    
    public static final int TARGET_TYPE__TEMP_AC = 8;
    public static final int TARGET_TYPE__LEVEL = 9;
    public static final int TARGET_TYPE__TEMP_LEVEL = 10;
    public static final int TARGET_TYPE__TEMP_AGE = 11;
    public static final int TARGET_TYPE__AWARD = 12;
    public static final int TARGET_TYPE__EXPERIENCE = 13;

    public static final int TARGET_TYPE__QUEST_BIT = 16;
    public static final int TARGET_TYPE__ITEM = 17;
    
    public static final int TARGET_TYPE__GOLD = 21;
    public static final int TARGET_TYPE__GOLD_2 = 22; // conditional, maybe?
    public static final int TARGET_TYPE__FOOD = 23;
    public static final int TARGET_TYPE__FOOD_2 = 24; // conditional, maybe?

    public static final int TARGET_TYPE__TEMP_MIGHT = 25;
    public static final int TARGET_TYPE__TEMP_INTELLECT = 26;
    public static final int TARGET_TYPE__TEMP_PERSONALITY = 27;
    public static final int TARGET_TYPE__TEMP_ENDURANCE = 28;
    public static final int TARGET_TYPE__TEMP_SPEED = 29;
    public static final int TARGET_TYPE__TEMP_ACCURACY = 30;
    public static final int TARGET_TYPE__TEMP_LUCK = 31;

    public static final int TARGET_TYPE__PERM_MIGHT = 32;
    public static final int TARGET_TYPE__PERM_INTELLECT = 33;
    public static final int TARGET_TYPE__PERM_PERSONALITY = 34;
    public static final int TARGET_TYPE__PERM_ENDURANCE = 35;
    public static final int TARGET_TYPE__PERM_SPEED = 36;
    public static final int TARGET_TYPE__PERM_ACCURACY = 37;
    public static final int TARGET_TYPE__PERM_LUCK = 38;

    public static final int TARGET_TYPE__MIN_MIGHT = 39; // also temp for add
    public static final int TARGET_TYPE__MIN_INTELLECT = 40; // also temp for add
    public static final int TARGET_TYPE__MIN_PERSONALITY = 41; // also temp for add
    public static final int TARGET_TYPE__MIN_ENDURANCE = 42; // also temp for add
    public static final int TARGET_TYPE__MIN_SPEED = 43; // also temp for add
    public static final int TARGET_TYPE__MIN_ACCURACY = 44; // also temp for add
    public static final int TARGET_TYPE__MIN_LUCK = 45; // also temp for add

    public static final int TARGET_TYPE__PERM_FIRE_RESISTANCE = 46;
    public static final int TARGET_TYPE__PERM_ELECTRICITY_RESISTANCE = 47;
    public static final int TARGET_TYPE__PERM_COLD_RESISTANCE = 48;
    public static final int TARGET_TYPE__PERM_POISON_RESISTANCE = 49;
    public static final int TARGET_TYPE__PERM_MAGIC_RESISTANCE = 50;

    public static final int TARGET_TYPE__TEMP_FIRE_RESISTANCE = 51;
    public static final int TARGET_TYPE__TEMP_ELECTRICITY_RESISTANCE = 52;
    public static final int TARGET_TYPE__TEMP_COLD_RESISTANCE = 53;
    public static final int TARGET_TYPE__TEMP_POISON_RESISTANCE = 54;
    public static final int TARGET_TYPE__TEMP_MAGIC_RESISTANCE = 55;

    public static final int TARGET_TYPE__STAFF_SKILL = 56;
    public static final int TARGET_TYPE__SWORD_SKILL = 57;
    public static final int TARGET_TYPE__DAGGER_SKILL = 58;
	// [...] fill in
    public static final int TARGET_TYPE__BLASTER_SKILL = 63;
    
    public static final int TARGET_TYPE__SHIELD_SKILL = 64;
	// [...] fill in
    public static final int TARGET_TYPE__PLATE_SKILL = 67;
    
    public static final int TARGET_TYPE__FIRE_MAGIC_SKILL = 68;
	// [...] fill in
    public static final int TARGET_TYPE__BODY_MAGIC_SKILL = 74;

    // x4b(75): ?
	// x4c(76): ?
	
    public static final int TARGET_TYPE__IDENTIFY_SKILL = 77;
	// [...] fill in
    public static final int TARGET_TYPE__LEARNING_SKILL = 86;
    
    public static final int TARGET_TYPE__CURSED_CONDITION = 87;
    public static final int TARGET_TYPE__WEAK_CONDITION = 88;
    public static final int TARGET_TYPE__ASLEEP_CONDITION = 89;
    public static final int TARGET_TYPE__AFRAID_CONDITION = 90;
    public static final int TARGET_TYPE__DRUNK_CONDITION = 91;
    public static final int TARGET_TYPE__INSANE_CONDITION = 92;
    public static final int TARGET_TYPE__POISON_GREEN_CONDITION = 93;
    public static final int TARGET_TYPE__DISEASE_GREEN_CONDITION = 94;
    public static final int TARGET_TYPE__POISON_YELLOW_CONDITION = 95;
    public static final int TARGET_TYPE__DISEASE_YELLOW_CONDITION = 96;
    public static final int TARGET_TYPE__POISON_RED_CONDITION = 97;
    public static final int TARGET_TYPE__DISEASE_RED_CONDITION = 98;
    public static final int TARGET_TYPE__PARALYSED_CONDITION = 99;
    public static final int TARGET_TYPE__UNCONSIOUS_CONDITION = 100;
    public static final int TARGET_TYPE__DEAD_CONDITION = 101;
    public static final int TARGET_TYPE__STONED_CONDITION = 102;
    public static final int TARGET_TYPE__ERADICATED_CONDITION = 103;

    public static final int TARGET_TYPE__REPUTATION = 215;
    
    public static final int TARGET_TYPE__MONTH_EQUALS = 226;
    
    
    private byte data[] = null;
    private int offset = 0;
    
    private int eventNumber = 0;
    private int eventSequenceNumber = 0;
    private int eventCommandNumber = 0;

    public Event_3_46()
    {
        super();
    }

    public int initialize(byte[] dataSrc, int offset, byte eventLength)
    {
        this.offset = offset;
        
        this.data = new byte[eventLength];
        System.arraycopy(dataSrc, offset, this.data, 0, eventLength);
        offset += eventLength;

        this.eventNumber = ByteConversions.getUnsignedShortInByteArrayAtPosition(this.data, EVENT_NUMBER_OFFSET);
        this.eventSequenceNumber = ByteConversions.convertByteToInt(this.data[EVENT_SEQUENCE_OFFSET]);
        this.eventCommandNumber = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_OFFSET]);

        return offset;
    }

    public void initializeWithValues(int eventNumber, int eventSequence, int commandValue)
    {
        this.eventNumber = eventNumber;
        this.eventSequenceNumber = eventSequence;
        this.eventCommandNumber = commandValue;

        this.offset = 0; // not sure what to do about this.
        
        int minExpectedArgCount = Event_3_46.minExpectedArgCount(commandValue);
        byte eventLength = (byte)(Event_3_46.EVENT_COMMAND_ARGS_OFFSET + minExpectedArgCount);
        
        this.data = new byte[eventLength];
        ByteConversions.setShortInByteArrayAtPosition((short)eventNumber, this.data, Event_3_46.EVENT_NUMBER_OFFSET);
        this.data[Event_3_46.EVENT_SEQUENCE_OFFSET] = ByteConversions.convertIntToByte(eventSequence);
        this.data[Event_3_46.EVENT_COMMAND_OFFSET] = ByteConversions.convertIntToByte(commandValue);
    }
    
    public int updateData(byte[] newData, int offset)
    {
        // IMPLEMENT: deal with this as unsigned short!
        ByteConversions.setShortInByteArrayAtPosition((short)this.eventNumber, this.data, EVENT_NUMBER_OFFSET);
        this.data[EVENT_SEQUENCE_OFFSET] = ByteConversions.convertIntToByte(this.eventSequenceNumber);
        this.data[EVENT_COMMAND_OFFSET] = ByteConversions.convertIntToByte(this.eventCommandNumber);

        System.arraycopy(this.data, 0, newData, offset, this.data.length);
        offset += this.data.length;

        return offset;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
        while (expectedNewOffset > offset)
        {
            byte eventLength = data[offset];
            offset += 1 + eventLength;
        }
            
        return (offset == expectedNewOffset);
    }
    
    public static List<Event_3_46> readEvents(byte data[])
    {
        int offset = 0;
        List<Event_3_46> eventList = new ArrayList<Event_3_46>();
        
        while (data.length != offset)
        {
            byte eventLength = data[offset];
            offset += 1;
            
            Event_3_46 event = new Event_3_46();
            offset = event.initialize(data, offset, eventLength);
            eventList.add(event);
        }
        
        return eventList;
    }

    public static byte[] writeEvents(List<Event_3_46> eventList)
    {
        int offset = 0;
        byte data[] = null;
        
        int dataLength = 0;
        Iterator eventIterator = eventList.iterator();
        while (eventIterator.hasNext())
        {
            Event_3_46 event = (Event_3_46) eventIterator.next();
            dataLength += 1 + event.getEventLength();
        }
        
        data = new byte[dataLength];
        
        eventIterator = eventList.iterator();
        while (eventIterator.hasNext())
        {
            Event_3_46 event = (Event_3_46) eventIterator.next();
            data[offset] = event.getEventLength();
            offset += 1;
            
            offset = event.updateData(data, offset);
        }
        
        return data;
    }

    public byte[] getData()
    {
        return this.data;
    }
    
    public int getOffset()
    {
        return this.offset;
    }

    public byte getEventLength()
    {
        return (byte)this.data.length;
    }

    public int getEventNumber()
    {
        return this.eventNumber;
    }
    public void setEventNumber(int eventNumber)
    {
        this.eventNumber = eventNumber;
    }

    public int getEventSequenceNumber()
    {
        return this.eventSequenceNumber;
    }
    public void setEventSequenceNumber(int eventSequenceNumber)
    {
        this.eventSequenceNumber = eventSequenceNumber;
    }

    public int getEventCommandNumber()
    {
        return this.eventCommandNumber;
    }
    public void setEventCommandNumber(int eventCommandNumber)
    {
        this.eventCommandNumber = eventCommandNumber;
    }

    public String getEventCommandName()
    {
        return Event_3_46.getEventCommandName(this.eventCommandNumber);
    }

    public String getCommandTeleportArgumentLevelName()
    {
        int nameOffset = EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET;
        return ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, nameOffset, TELEPORT_ARGS_DESTINATION_FILENAME_MAXLENGTH);
    }

    public void setCommandTeleportArgumentLevelName(String levelName)
    {
        int nameOffset = EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET;
        byte newData[] = new byte[nameOffset + levelName.length() + 1];
        System.arraycopy(newData, 0, newData, 0, nameOffset);
        
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(levelName, newData, nameOffset, levelName.length() + 1);
        
        this.data = newData;
    }
    
    public String getCommandChangeSpriteArgumentNewSpriteName()
    {
        int nameOffset = EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET;
        return ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, nameOffset, CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_MAXLENGTH);
    }

    public void setCommandChangeSpriteArgumentNewSpriteName(String newSpriteName)
    {
        int nameOffset = EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET;
        byte newData[] = new byte[nameOffset + newSpriteName.length() + 1];
        System.arraycopy(data, 0, newData, 0, nameOffset);
        
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(newSpriteName, newData, nameOffset, newSpriteName.length() + 1);
        
        this.data = newData;
    }
    
    public String getCommandChangeFaceBitmapArgumentNewBitmapName()
    {
        int nameOffset = EVENT_COMMAND_ARGS_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET;
        return ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(data, nameOffset, CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_MAXLENGTH);
    }

    public void setCommandChangeFaceBitmapArgumentNewBitmapName(String newSpriteName)
    {
        int nameOffset = EVENT_COMMAND_ARGS_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET;
        byte newData[] = new byte[nameOffset + newSpriteName.length() + 1];
        System.arraycopy(data, 0, newData, 0, nameOffset);
        
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(newSpriteName, newData, nameOffset, newSpriteName.length() + 1);
        
        this.data = newData;
    }
    
    public static String[] getMiniIconNames()
    {
        return new String[] {
                "NO SYMBOL",
                "castle",
                "dungeon",
                "iDoor",
                "iSecDoor",
                "iStairDn",
                "iStairup",
                "iTrap",
                "outside"
        };
    }

    public static Integer[] getCommands()
    {
        return new Integer[] { 
                new Integer(EVENT_COMMAND__STOP_PROCESSING),
                new Integer(EVENT_COMMAND__IDENTITY),
                new Integer(EVENT_COMMAND__MOUSEOVER),
                new Integer(EVENT_COMMAND__TELEPORT),
                new Integer(EVENT_COMMAND__OPEN_CHEST),
                new Integer(EVENT_COMMAND__SHOW_FACIAL_EXPRESSION),
                new Integer(EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP),
                new Integer(EVENT_COMMAND__CHANGE_SPRITE),
                new Integer(EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE),
                new Integer(EVENT_COMMAND__ADD_TARGET),
                new Integer(EVENT_COMMAND__DELETE_TARGET),
                new Integer(EVENT_COMMAND__SET_TARGET),
                new Integer(EVENT_COMMAND__CREATE_LOCAL_MONSTER),
                new Integer(EVENT_COMMAND__CAST_SPELL_FROM_LOCATION),
                new Integer(EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING),
                new Integer(EVENT_COMMAND__SHOW_NPCTEXT_STRING),
                new Integer(EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER),
                new Integer(EVENT_COMMAND__GOTO),
                new Integer(EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE),
                new Integer(EVENT_COMMAND__CHANGE_DIALOG_EVENT)
        };
    }
    
    public static String getMiniIconName(int miniIconNumber)
    {
        // Hardcoded in MM6.exe file
        // These icon names are contained in icon.lod
        switch(miniIconNumber)
        {
            case 0:
                return "NO SYMBOL";
            case 1:
                return "castle";
            case 2:
                return "dungeon";
            case 3:
                return "idoor";
            case 4:
                return "isecdoor";
            case 5:
                return "istairdn";
            case 6:
                return "istairup";
            case 7:
                return "itrap";
            case 8:
                return "outside";
            default:
                return null;
        }
    }

    // guesswork so far
    
    public static String getEventCommandName(int command)
    {
        switch(command)
        {
            case EVENT_COMMAND__STOP_PROCESSING:
                return "Stop processing";
            case EVENT_COMMAND__IDENTITY:
                return "Identity";
            case EVENT_COMMAND__MOUSEOVER:
                return "Mouseover";
            case EVENT_COMMAND__TELEPORT:
                return "Teleport";
            case EVENT_COMMAND__OPEN_CHEST:
                return "Open Chest";
            case EVENT_COMMAND__SHOW_FACIAL_EXPRESSION:
                return "Show Facial expression";
            case EVENT_COMMAND__CHANGE_SPRITE:
                return "Change Sprite";
            case EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP:
                return "Change 3dObject Face Bitmap";
            case EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE:
                return "Branch on condition true";
            case EVENT_COMMAND__ADD_TARGET:
                return "Add Target";
            case EVENT_COMMAND__DELETE_TARGET:
                return "Delete Target";
            case EVENT_COMMAND__SET_TARGET:
                return "Set Target";
            case EVENT_COMMAND__CREATE_LOCAL_MONSTER:
                return "Create local monster";
            case EVENT_COMMAND__CAST_SPELL_FROM_LOCATION:
                return "Cast spell from location";
            case EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING:
                return "Show local event String";
            case EVENT_COMMAND__SHOW_NPCTEXT_STRING:
                return "Show NPCText string";
            case EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER:
                return "modify next command by party member";
            case EVENT_COMMAND__GOTO:
                return "Goto";
            case EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE:
                return "On Level Reload Event:";
            case EVENT_COMMAND__CHANGE_DIALOG_EVENT:
                return "Change dialog event";
            default:
                return "UNKNOWN COMMAND 0x" + Integer.toHexString(command);
        }
    }

    private static int minExpectedArgCount(int command)
    {
        switch(command)
        {
            case EVENT_COMMAND__STOP_PROCESSING:
                return 1;
            case EVENT_COMMAND__IDENTITY:
                return 4;
            case EVENT_COMMAND__MOUSEOVER:
                return 1;
            case EVENT_COMMAND__TELEPORT:
                return TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET + 2; // 1 char + zero-terminator for filename
            case EVENT_COMMAND__OPEN_CHEST:
                return 1;
            case EVENT_COMMAND__SHOW_FACIAL_EXPRESSION:
                return 2;
            case EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP:
                return CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET + 2;
            case EVENT_COMMAND__CHANGE_SPRITE:
                return CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET + 2;
            case EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE:
                return 6;
            case EVENT_COMMAND__DELETE_TARGET:
                return 5;
            case EVENT_COMMAND__ADD_TARGET:
                return 5;
            case EVENT_COMMAND__SET_TARGET:
                return 5;
            case EVENT_COMMAND__CREATE_LOCAL_MONSTER:
                return 15;
            case EVENT_COMMAND__CAST_SPELL_FROM_LOCATION:
                return 27;
            case EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING:
                return 4;
            case EVENT_COMMAND__SHOW_NPCTEXT_STRING:
                return 4;
            case EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER:
                return 1;
            case EVENT_COMMAND__GOTO:
                return 1;
            case EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE:
                return 1;
            case EVENT_COMMAND__CHANGE_DIALOG_EVENT:
                return 9;
            default:
                return -1;
        }
    }

    private static int maxExpectedArgCount(int command)
    {
        switch(command)
        {
            case EVENT_COMMAND__STOP_PROCESSING:
                return 1;
            case EVENT_COMMAND__IDENTITY:
                return 4;
            case EVENT_COMMAND__MOUSEOVER:
                return 1;
            case EVENT_COMMAND__TELEPORT:
                return TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET + TELEPORT_ARGS_DESTINATION_FILENAME_MAXLENGTH;
            case EVENT_COMMAND__OPEN_CHEST:
                return 1;
            case EVENT_COMMAND__SHOW_FACIAL_EXPRESSION:
                return 2;
            case EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP:
                return CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_MAXLENGTH;
            case EVENT_COMMAND__CHANGE_SPRITE:
                return CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET + CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_MAXLENGTH;
            case EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE:
                return 6;
            case EVENT_COMMAND__ADD_TARGET:
                return 5;
            case EVENT_COMMAND__DELETE_TARGET:
                return 5;
            case EVENT_COMMAND__SET_TARGET:
                return 5;
            case EVENT_COMMAND__CREATE_LOCAL_MONSTER:
                return 15;
            case EVENT_COMMAND__CAST_SPELL_FROM_LOCATION:
                return 27;
            case EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING:
                return 4;
            case EVENT_COMMAND__SHOW_NPCTEXT_STRING:
                return 4;
            case EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER:
                return 1;
            case EVENT_COMMAND__GOTO:
                return 1;
            case EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE:
                return 1;
            case EVENT_COMMAND__CHANGE_DIALOG_EVENT:
                return 9;
            default:
                return -1;
        }
    }

    private String getEventDescriptionForCommand()
    {
        int minExpectedArgCount =  minExpectedArgCount(this.eventCommandNumber);
        int maxExpectedArgCount =  maxExpectedArgCount(this.eventCommandNumber);
        if (minExpectedArgCount == maxExpectedArgCount)
        {
            if (-1 != minExpectedArgCount)
            {
                if (minExpectedArgCount != (this.data.length - EVENT_COMMAND_ARGS_OFFSET))
                    return ": Expected " + minExpectedArgCount + " arguments but had " + (this.data.length - EVENT_COMMAND_ARGS_OFFSET) + " arguments.";
            }
        }
        else
        {
            if (-1 != minExpectedArgCount)
            {
                int actualArgs = this.data.length - EVENT_COMMAND_ARGS_OFFSET;
                if ((actualArgs < minExpectedArgCount) || (actualArgs > maxExpectedArgCount))
                    return ": Expected between " + minExpectedArgCount + " and " + maxExpectedArgCount + " arguments but had " + (this.data.length - EVENT_COMMAND_ARGS_OFFSET) + " arguments.";
            }
        }
        
        int arg0;
        int arg1;
        int arg2;
        int arg3;
        int arg4;
        int arg5;
        int arg6;
        int arg7;
        
        int byteError;
        int shortError;
        int intError;
        
        switch(this.eventCommandNumber)
        {
            case EVENT_COMMAND__STOP_PROCESSING:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + STOP_PROCESSING_ARGS_UNKNOWN1_OFFSET]);
                if (0 != arg0)  return ": Unexpected arg0=" + arg0;
                return "";
            case EVENT_COMMAND__IDENTITY:
                arg0 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + IDENTITY_ARGS_2DEVENT_ID_OFFSET);
                return " 2dEvent#" + arg0;
            case EVENT_COMMAND__MOUSEOVER:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + MOUSEOVER_ARGS_EVENT_STR_ID_OFFSET]);
                return " Local Event Str#" + arg0;
            case EVENT_COMMAND__TELEPORT:
                // coordinates
                arg0 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_X_COORD_OFFSET);
                arg1 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_Y_COORD_OFFSET);
                arg2 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_Z_COORD_OFFSET);
                
                // horizontal orientation
                arg3 = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_HORIZONTAL_ORIENTATION_OFFSET);
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_UNKNOWN1_OFFSET);
                if (0 != shortError)
                {
                    return ": Unexpected nonzero value for offset " + TELEPORT_ARGS_UNKNOWN1_OFFSET + " - " + shortError;
                }
                
                // vertical orientation
                arg4 = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_VERTICAL_ORIENTATION_OFFSET);
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_UNKNOWN2_OFFSET);
                if (0 != shortError)
                {
                    return ": Unexpected nonzero value for offset " + TELEPORT_ARGS_UNKNOWN2_OFFSET + " - " + shortError;
                }

                // unknown
                intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_UNKNOWN3_OFFSET);
                if (0 != intError)
                {
                    return ": Unexpected nonzero value for offset " + TELEPORT_ARGS_UNKNOWN3_OFFSET + " - " + intError;
                }
                
                arg5 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET]);
                arg6 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_MINIICON_OFFSET]);
                String miniIconName = getMiniIconName(arg6);
                if (null == miniIconName)
                {
                    return ": Unexpected mini icon #" + arg6;
                }
                String levelName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET, this.data.length - (EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_DESTINATION_FILENAME_OFFSET));
                String viaString = " via dialog #" + arg5 + " and mini icon " + miniIconName;
                if ((0 == arg5) && (0 == arg6))  viaString = " immediately";
                return " to " + levelName + " at (" + arg0 + "," + arg1 + "," + arg2 + ") " + " facing (" + arg3 + "," + arg4 + ")" + viaString;
            case EVENT_COMMAND__OPEN_CHEST:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + OPEN_CHEST_ARGS_CHEST_NUMBER_OFFSET]);
                return " #" + arg0;
            case EVENT_COMMAND__SHOW_FACIAL_EXPRESSION:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + SHOW_FACIAL_EXPRESSION_ARGS_AFFECTED_OFFSET]);
                arg1 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + SHOW_FACIAL_EXPRESSION_ARGS_IMAGE_ID_OFFSET]);
                return " #" + arg1 + " for " + affectedFacialTarget(arg0);
            case EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP:
                arg0 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_3D_OBJECT_NUMBER_OFFSET);
                arg1 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_FACE_OFFSET);
                String newBitmapName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET, this.data.length - (EVENT_COMMAND_ARGS_OFFSET + CHANGE_3DOBJECT_FACE_BITMAP_ARGS_NEW_BITMAP_NAME_OFFSET));
                return " object #" + arg0 + ", face #" + arg1 + " to " + newBitmapName;
            case EVENT_COMMAND__CHANGE_SPRITE:
                arg0 = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_SPRITE_NUMBER);
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_UNKNOWN1_OFFSET);
                if (0 != shortError)
                {
                    return ": Unexpected nonzero value for offset " + CHANGE_SPRITE_ARGS_UNKNOWN1_OFFSET + " - " + shortError;
                }
                
                byteError = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_UNKNOWN2_OFFSET]);
                if (1 != byteError)
                {
                    return ": Unexpected non-1 value for offset " + CHANGE_SPRITE_ARGS_UNKNOWN2_OFFSET + " - " + byteError;
                }

                String newSpriteName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET, this.data.length - (EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_NEW_SPRITE_NAME_OFFSET));
                return " #" + arg0 + " to " + newSpriteName;
            case EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_TYPE_OFFSET]);
                arg1 = ByteConversions.getUnsignedShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_NUMBER_OFFSET);
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + BRANCH_ON_CONDITION_TRUE_ARGS_UNKNOWN1_OFFSET);
                if (0 != shortError)
                {
                    return ": Unexpected nonzero value for offset " + BRANCH_ON_CONDITION_TRUE_ARGS_UNKNOWN1_OFFSET + " - " + shortError;
                }
                arg2 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + BRANCH_ON_CONDITION_TRUE_ARGS_GOTO_SEQUENCE_NUMBER_OFFSET]);
                return " - if ( " + getTarget(arg0, arg1) + " ) goto event sequence #" + getEventNumber() + "-" + arg2;
            case EVENT_COMMAND__ADD_TARGET:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + ADD_TARGET_ARGS_TARGET_TYPE_OFFSET]);
                arg1 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + ADD_TARGET_ARGS_TARGET_NUMBER_OFFSET);
                return " - Add " + getTarget(arg0, arg1);
            case EVENT_COMMAND__DELETE_TARGET:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + DELETE_TARGET_ARGS_TARGET_TYPE_OFFSET]);
                arg1 = ByteConversions.getUnsignedShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + DELETE_TARGET_ARGS_TARGET_NUMBER_OFFSET);
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + DELETE_TARGET_ARGS_UNKNOWN1_OFFSET);
                if (0 != shortError)
                {
                    return ": Unexpected nonzero value for offset " + DELETE_TARGET_ARGS_UNKNOWN1_OFFSET + " - " + shortError;
                }
                if (TARGET_TYPE__ITEM != arg0)
                {
                    return ": Non-inventory delete #" + arg0;
                }
                return " - Delete " + getTarget(arg0, arg1) + " from inventory";
            case EVENT_COMMAND__SET_TARGET:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + SET_TARGET_ARGS_TARGET_TYPE_OFFSET]);
                arg1 = ByteConversions.getUnsignedShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SET_TARGET_ARGS_TARGET_NUMBER_OFFSET);
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SET_TARGET_ARGS_UNKNOWN1_OFFSET);
                if (0 != shortError)
                {
                    return ": Unexpected nonzero value for offset " + SET_TARGET_ARGS_UNKNOWN1_OFFSET + " - " + shortError;
                }
                return " " + getTarget(arg0, arg1);
                
            case EVENT_COMMAND__CREATE_LOCAL_MONSTER:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CREATE_LOCAL_MONSTER_ARGS_MONSTER_SPECIES_OFFSET]);
                arg1 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CREATE_LOCAL_MONSTER_ARGS_MONSTER_SUBSPECIES__OFFSET]);
                arg2 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CREATE_LOCAL_MONSTER_ARGS_COUNT_OFFSET]);
                arg3 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CREATE_LOCAL_MONSTER_ARGS_X_OFFSET);
                arg4 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CREATE_LOCAL_MONSTER_ARGS_Y_OFFSET);
                arg5 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CREATE_LOCAL_MONSTER_ARGS_Z_OFFSET);
                return arg2 + " species #" + arg0 + "-" + arg1 + ", at (" + arg3 + "," + arg4 + "," + arg5 + ")";
            case EVENT_COMMAND__CAST_SPELL_FROM_LOCATION:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_SPELL_NUMBER_OFFSET]);
                arg1 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_RANKING_OFFSET]);
                arg2 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_LEVEL_OFFSET]);
                arg3 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_X_OFFSET);
                arg4 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Y_OFFSET);
                arg5 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Z_OFFSET);
                // unknown
                intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN2_OFFSET);
                if (0 != intError)
                {
                    return ": Unexpected nonzero value for offset " + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN2_OFFSET + " - " + intError;
                }
                // unknown
                intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN3_OFFSET);
                if (0 != intError)
                {
                    return ": Unexpected nonzero value for offset " + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN3_OFFSET + " - " + intError;
                }
                // unknown
                intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN4_OFFSET);
                if (0 != intError)
                {
                    return ": Unexpected nonzero value for offset " + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN4_OFFSET + " - " + intError;
                }
                return ": spell #" + arg0 + ", ranking " + arg1 + ", level " + arg2 + ", at (" + arg3 + "," + arg4 + "," + arg5 + ")";
            case EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING:
                arg0 = ByteConversions.getUnsignedShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SHOW_LOCAL_EVENT_STRING_ARGS_STR_ID_OFFSET);
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SHOW_LOCAL_EVENT_STRING_ARGS_UNKNOWN1_OFFSET);
                if (0 != shortError)
                {
                    return ": Unexpected nonzero value for offset " + SHOW_LOCAL_EVENT_STRING_ARGS_UNKNOWN1_OFFSET  + " - " + shortError;
                }
                return " #" + arg0;
            case EVENT_COMMAND__SHOW_NPCTEXT_STRING:
                arg0 = ByteConversions.getUnsignedShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SHOW_NPCTEXT_STRING_ARGS_STR_ID_OFFSET);
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SHOW_NPCTEXT_STRING_ARGS_UNKNOWN1_OFFSET);
                if (0 != shortError)
                {
                    return ": Unexpected nonzero value for offset " + SHOW_NPCTEXT_STRING_ARGS_UNKNOWN1_OFFSET  + " - " + shortError;
                }
                return " #" + arg0;
            case EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER_ARGS_AFFECTED_OFFSET]);
                return " : " + affectedFacialTarget(arg0);
            case EVENT_COMMAND__GOTO:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + GOTO_ARGS_SEQUENCE_NUMBER_OFFSET]);
                return " sequence #" + getEventNumber() + "-" + arg0;
            case EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + STOP_PROCESSING_ARGS_UNKNOWN1_OFFSET]);
                if (0 != arg0)  return ": Unexpected arg0=" + arg0;
                return "";
            case EVENT_COMMAND__CHANGE_DIALOG_EVENT:
                arg0 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_DIALOG_EVENT_ARGS_NPCDATA_OFFSET);
                arg1 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CHANGE_DIALOG_EVENT_ARGS_NPC_MENU_INDEX_OFFSET]);
                arg2 = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_DIALOG_EVENT_ARGS_NEW_GLOBAL_EVENT_NUMBER_OFFSET);
                return " for NPC #" + arg0 + ", index " + arg1 + " to global event #" + arg2; 
            default:
                return " - UNKNOWN";
        }
    }

    public boolean isUnderstood()
    {
        int minExpectedArgCount =  minExpectedArgCount(this.eventCommandNumber);
        int maxExpectedArgCount =  maxExpectedArgCount(this.eventCommandNumber);
        if (minExpectedArgCount == maxExpectedArgCount)
        {
            if (-1 != minExpectedArgCount)
            {
                if (minExpectedArgCount != (this.data.length - EVENT_COMMAND_ARGS_OFFSET))
                    return false;
            }
        }
        else
        {
            if (-1 != minExpectedArgCount)
            {
                int actualArgs = this.data.length - EVENT_COMMAND_ARGS_OFFSET;
                if ((actualArgs < minExpectedArgCount) || (actualArgs > maxExpectedArgCount))
                    return false;
            }
        }
        
        int arg0;
        int byteError;
        int shortError;
        int intError;
        
        switch(this.eventCommandNumber)
        {
            case EVENT_COMMAND__STOP_PROCESSING:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + STOP_PROCESSING_ARGS_UNKNOWN1_OFFSET]);
                if (0 != arg0)  return false;
                return true;
            case EVENT_COMMAND__IDENTITY:
                return true;
            case EVENT_COMMAND__MOUSEOVER:
                return true;
            case EVENT_COMMAND__TELEPORT:
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_UNKNOWN1_OFFSET);
                if (0 != shortError)
                {
                    return false;
                }
                
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_UNKNOWN2_OFFSET);
                if (0 != shortError)
                {
                    return false;
                }

                intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_UNKNOWN3_OFFSET);
                if (0 != intError)
                {
                    return false;
                }
                
                int arg6 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + TELEPORT_ARGS_MINIICON_OFFSET]);
                String miniIconName = getMiniIconName(arg6);
                if (null == miniIconName)
                {
                    return false;
                }
                return true;
            case EVENT_COMMAND__OPEN_CHEST:
                return true;
            case EVENT_COMMAND__SHOW_FACIAL_EXPRESSION:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + STOP_PROCESSING_ARGS_UNKNOWN1_OFFSET]);
                if (arg0 > 5)  return false; // above this has been random
                return true;
            case EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP:
                return true;
            case EVENT_COMMAND__CHANGE_SPRITE:
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_UNKNOWN1_OFFSET);
                if (0 != shortError)
                {
                    return false;
                }
                
                byteError = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + CHANGE_SPRITE_ARGS_UNKNOWN2_OFFSET]);
                if (1 != byteError)
                {
                    return false;
                }
                return true;
            case EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_TYPE_OFFSET]);
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + BRANCH_ON_CONDITION_TRUE_ARGS_UNKNOWN1_OFFSET);
                if (0 != shortError)
                {
                    return false;
                }
                if (false == isTargetTypeUnderstood(this.eventCommandNumber, arg0))
                {
                    return false;
                }
                return true;
           case EVENT_COMMAND__ADD_TARGET:
               arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + ADD_TARGET_ARGS_TARGET_TYPE_OFFSET]);
               if (false == isTargetTypeUnderstood(this.eventCommandNumber, arg0))
               {
                   return false;
               }
               return true;
           case EVENT_COMMAND__DELETE_TARGET:
               arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + DELETE_TARGET_ARGS_TARGET_TYPE_OFFSET]);
               shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + DELETE_TARGET_ARGS_UNKNOWN1_OFFSET);
               if (0 != shortError)
               {
                   return false;
               }
               if (false == isTargetTypeUnderstood(this.eventCommandNumber, arg0))
               {
                   return false;
               }
               return true;
            case EVENT_COMMAND__SET_TARGET:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + SET_TARGET_ARGS_TARGET_TYPE_OFFSET]);
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SET_TARGET_ARGS_UNKNOWN1_OFFSET);
                if (0 != shortError)
                {
                    return false;
                }
                if (false == isTargetTypeUnderstood(this.eventCommandNumber, arg0))
                {
                    return false;
                }
                return true;
            case EVENT_COMMAND__CREATE_LOCAL_MONSTER:
                return true;
            case EVENT_COMMAND__CAST_SPELL_FROM_LOCATION:
                // unknown
                intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN2_OFFSET);
                if (0 != intError)
                {
                    return false;
                }
                // unknown
                intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN3_OFFSET);
                if (0 != intError)
                {
                    return false;
                }
                // unknown
                intError = ByteConversions.getIntegerInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + CAST_SPELL_FROM_LOCATION_ARGS_UNKNOWN4_OFFSET);
                if (0 != intError)
                {
                    return false;
                }
                return true;
            case EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING:
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SHOW_LOCAL_EVENT_STRING_ARGS_UNKNOWN1_OFFSET);
                if (0 != shortError)
                {
                    return false;
                }
                return true;
            case EVENT_COMMAND__SHOW_NPCTEXT_STRING:
                shortError = ByteConversions.getShortInByteArrayAtPosition(this.data, EVENT_COMMAND_ARGS_OFFSET + SHOW_NPCTEXT_STRING_ARGS_UNKNOWN1_OFFSET);
                if (0 != shortError)
                {
                    return false;
                }
                return true;
            case EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER_ARGS_AFFECTED_OFFSET]);
                if (arg0 > 5)  return false; // above this has been random
                return true;
            case EVENT_COMMAND__GOTO:
                return true;
            case EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE:
                arg0 = ByteConversions.convertByteToInt(this.data[EVENT_COMMAND_ARGS_OFFSET + STOP_PROCESSING_ARGS_UNKNOWN1_OFFSET]);
                if (0 != arg0)  return false;
                return true;
            case EVENT_COMMAND__CHANGE_DIALOG_EVENT:
                return true;
            default:
                return false;
        }
    }

    private String affectedFacialTarget(int affected)
    {
        switch(affected)
        {
            case 0:
                return "outer-left character";
            case 1:
                return "inner-left character";
            case 2:
                return "inner-right character";
            case 3:
                return "outer-right character";
            case 4:
                return "selected character";
            case 5:
                return "all characters";
            case 6:
                return "random character?";
            default:
                return "UNKNOWN #" + affected;
        }
    }

    public static String getTargetTypeName(int targetType)
    {
        switch(targetType)
        {
            case TARGET_TYPE__TEMP_HP:
                return "current hit points";
            case TARGET_TYPE__TEMP_HP_EQ_MAX_HP:
                return "current hit points equal maximum hit points";
            case TARGET_TYPE__TEMP_SP:
                return "current spell points";
            case TARGET_TYPE__TEMP_SP_EQ_MAX_SP:
                return "current spell points equal maximum spell points";
            case TARGET_TYPE__TEMP_AC:
                return "current armor class";
            case TARGET_TYPE__LEVEL:
                return "level";
            case TARGET_TYPE__TEMP_LEVEL:
                return "current level";
            case TARGET_TYPE__TEMP_AGE:
                return "current age";
            case TARGET_TYPE__AWARD:
                return "award";
            case TARGET_TYPE__EXPERIENCE:
                return "experience";
            case TARGET_TYPE__QUEST_BIT:
                return "quest bit";
            case TARGET_TYPE__ITEM:
                return "item";
            case TARGET_TYPE__GOLD:
                return "gold";
            case TARGET_TYPE__GOLD_2:
                return "gold2";
            case TARGET_TYPE__FOOD:
                return "food";
            case TARGET_TYPE__FOOD_2:
                return "food";
            case TARGET_TYPE__TEMP_MIGHT:
                return "current might";
            case TARGET_TYPE__TEMP_INTELLECT:
                return "current intellect";
            case TARGET_TYPE__TEMP_PERSONALITY:
                return "current personality";
            case TARGET_TYPE__TEMP_ENDURANCE:
                return "current endurance";
            case TARGET_TYPE__TEMP_SPEED:
                return "current speed";
            case TARGET_TYPE__TEMP_ACCURACY:
                return "current accuracy";
            case TARGET_TYPE__TEMP_LUCK:
                return "current luck";
            case TARGET_TYPE__PERM_MIGHT:
                return "might";
            case TARGET_TYPE__PERM_INTELLECT:
                return "intellect";
            case TARGET_TYPE__PERM_PERSONALITY:
                return "personality";
            case TARGET_TYPE__PERM_ENDURANCE:
                return "endurance";
            case TARGET_TYPE__PERM_SPEED:
                return "speed";
            case TARGET_TYPE__PERM_ACCURACY:
                return "accuracy";
            case TARGET_TYPE__PERM_LUCK:
                return "luck";
            case TARGET_TYPE__MIN_MIGHT:
                return "current might >= ";
            case TARGET_TYPE__MIN_INTELLECT:
                return "current intellect >= ";
            case TARGET_TYPE__MIN_PERSONALITY:
                return "current personality >= ";
            case TARGET_TYPE__MIN_ENDURANCE:
                return "current endurance >= ";
            case TARGET_TYPE__MIN_SPEED:
                return "current speed >= ";
            case TARGET_TYPE__MIN_ACCURACY:
                return "current accuracy >= ";
            case TARGET_TYPE__MIN_LUCK:
                return "current luck >= ";
            case TARGET_TYPE__PERM_FIRE_RESISTANCE:
                return "fire resistance";
            case TARGET_TYPE__PERM_ELECTRICITY_RESISTANCE:
                return "electricity resistance";
            case TARGET_TYPE__PERM_COLD_RESISTANCE:
                return "cold resistance";
            case TARGET_TYPE__PERM_POISON_RESISTANCE:
                return "poison resistance";
            case TARGET_TYPE__PERM_MAGIC_RESISTANCE:
                return "magic resistance";
            case TARGET_TYPE__TEMP_FIRE_RESISTANCE:
                return "current fire resistance";
            case TARGET_TYPE__TEMP_ELECTRICITY_RESISTANCE:
                return "current electricity resistance";
            case TARGET_TYPE__TEMP_COLD_RESISTANCE:
                return "current cold resistance";
            case TARGET_TYPE__TEMP_POISON_RESISTANCE:
                return "current poison resistance";
            case TARGET_TYPE__TEMP_MAGIC_RESISTANCE:
                return "current magic resistance";
            case TARGET_TYPE__STAFF_SKILL:
                return "staff skill";
            case TARGET_TYPE__SWORD_SKILL:
                return "sword skill";
            case TARGET_TYPE__DAGGER_SKILL:
                return "dagger skill";
            case TARGET_TYPE__BLASTER_SKILL:
                return "blaster skill";
            case TARGET_TYPE__SHIELD_SKILL:
                return "shield skill";
            case TARGET_TYPE__PLATE_SKILL:
                return "plate skill";
            case TARGET_TYPE__FIRE_MAGIC_SKILL:
                return "fire magic skill";
            case TARGET_TYPE__BODY_MAGIC_SKILL:
                return "body magic skill";
            case TARGET_TYPE__IDENTIFY_SKILL:
                return "identify skill";
            case TARGET_TYPE__LEARNING_SKILL:
                return "learning skill";
            case TARGET_TYPE__CURSED_CONDITION:
                return "cursed condition";
            case TARGET_TYPE__WEAK_CONDITION:
                return "weak condition";
            case TARGET_TYPE__ASLEEP_CONDITION:
                return "asleep condition";
            case TARGET_TYPE__AFRAID_CONDITION:
                return "afraid condition";
            case TARGET_TYPE__DRUNK_CONDITION:
                return "drunk condition";
            case TARGET_TYPE__INSANE_CONDITION:
                return "insane condition";
            case TARGET_TYPE__POISON_GREEN_CONDITION:
                return "poisoned green condition";
            case TARGET_TYPE__DISEASE_GREEN_CONDITION:
                return "diseased green condition";
            case TARGET_TYPE__POISON_YELLOW_CONDITION:
                return "poisoned yellow condition";
            case TARGET_TYPE__DISEASE_YELLOW_CONDITION:
                return "diseased yellow condition";
            case TARGET_TYPE__POISON_RED_CONDITION:
                return "poisoned red condition";
            case TARGET_TYPE__DISEASE_RED_CONDITION:
                return "diseased red condition";
            case TARGET_TYPE__PARALYSED_CONDITION:
                return "paralysed condition";
            case TARGET_TYPE__UNCONSIOUS_CONDITION:
                return "unconsious condition";
            case TARGET_TYPE__DEAD_CONDITION:
                return "dead condition";
            case TARGET_TYPE__STONED_CONDITION:
                return "stoned condition";
            case TARGET_TYPE__ERADICATED_CONDITION:
                return "eradicated condition";
            case TARGET_TYPE__REPUTATION:
                return "reputation";
            case TARGET_TYPE__MONTH_EQUALS:
                return "month = ";
            default:
                return "UNKNOWN TYPE 0x" + Integer.toHexString(targetType);
        }
    }

    public static Integer[] getTargetTypes()
    {
        return new Integer[] {
                new Integer(TARGET_TYPE__TEMP_HP),
                new Integer(TARGET_TYPE__TEMP_HP_EQ_MAX_HP),
                new Integer(TARGET_TYPE__TEMP_SP),
                new Integer(TARGET_TYPE__TEMP_SP_EQ_MAX_SP),
                new Integer(TARGET_TYPE__TEMP_AC),
                new Integer(TARGET_TYPE__LEVEL),
                new Integer(TARGET_TYPE__TEMP_LEVEL),
                new Integer(TARGET_TYPE__TEMP_AGE),
                new Integer(TARGET_TYPE__AWARD),
                new Integer(TARGET_TYPE__EXPERIENCE),
                new Integer(TARGET_TYPE__QUEST_BIT),
                new Integer(TARGET_TYPE__ITEM),
                new Integer(TARGET_TYPE__GOLD),
                new Integer(TARGET_TYPE__GOLD_2),
                new Integer(TARGET_TYPE__FOOD),
                new Integer(TARGET_TYPE__FOOD_2),
                new Integer(TARGET_TYPE__TEMP_MIGHT),
                new Integer(TARGET_TYPE__TEMP_INTELLECT),
                new Integer(TARGET_TYPE__TEMP_PERSONALITY),
                new Integer(TARGET_TYPE__TEMP_ENDURANCE),
                new Integer(TARGET_TYPE__TEMP_SPEED),
                new Integer(TARGET_TYPE__TEMP_ACCURACY),
                new Integer(TARGET_TYPE__TEMP_LUCK),
                new Integer(TARGET_TYPE__PERM_MIGHT),
                new Integer(TARGET_TYPE__PERM_INTELLECT),
                new Integer(TARGET_TYPE__PERM_PERSONALITY),
                new Integer(TARGET_TYPE__PERM_ENDURANCE),
                new Integer(TARGET_TYPE__PERM_SPEED),
                new Integer(TARGET_TYPE__PERM_ACCURACY),
                new Integer(TARGET_TYPE__PERM_LUCK),
                new Integer(TARGET_TYPE__MIN_MIGHT),
                new Integer(TARGET_TYPE__MIN_INTELLECT),
                new Integer(TARGET_TYPE__MIN_PERSONALITY),
                new Integer(TARGET_TYPE__MIN_ENDURANCE),
                new Integer(TARGET_TYPE__MIN_SPEED),
                new Integer(TARGET_TYPE__MIN_ACCURACY),
                new Integer(TARGET_TYPE__MIN_LUCK),
                new Integer(TARGET_TYPE__PERM_FIRE_RESISTANCE),
                new Integer(TARGET_TYPE__PERM_ELECTRICITY_RESISTANCE),
                new Integer(TARGET_TYPE__PERM_COLD_RESISTANCE),
                new Integer(TARGET_TYPE__PERM_POISON_RESISTANCE),
                new Integer(TARGET_TYPE__PERM_MAGIC_RESISTANCE),
                new Integer(TARGET_TYPE__TEMP_FIRE_RESISTANCE),
                new Integer(TARGET_TYPE__TEMP_ELECTRICITY_RESISTANCE),
                new Integer(TARGET_TYPE__TEMP_COLD_RESISTANCE),
                new Integer(TARGET_TYPE__TEMP_POISON_RESISTANCE),
                new Integer(TARGET_TYPE__TEMP_MAGIC_RESISTANCE),
                new Integer(TARGET_TYPE__STAFF_SKILL),
                new Integer(TARGET_TYPE__SWORD_SKILL),
                new Integer(TARGET_TYPE__DAGGER_SKILL),
                new Integer(TARGET_TYPE__BLASTER_SKILL),
                new Integer(TARGET_TYPE__SHIELD_SKILL),
                new Integer(TARGET_TYPE__PLATE_SKILL),
                new Integer(TARGET_TYPE__FIRE_MAGIC_SKILL),
                new Integer(TARGET_TYPE__BODY_MAGIC_SKILL),
                new Integer(TARGET_TYPE__IDENTIFY_SKILL),
                new Integer(TARGET_TYPE__LEARNING_SKILL),
                new Integer(TARGET_TYPE__CURSED_CONDITION),
                new Integer(TARGET_TYPE__WEAK_CONDITION),
                new Integer(TARGET_TYPE__ASLEEP_CONDITION),
                new Integer(TARGET_TYPE__AFRAID_CONDITION),
                new Integer(TARGET_TYPE__DRUNK_CONDITION),
                new Integer(TARGET_TYPE__INSANE_CONDITION),
                new Integer(TARGET_TYPE__POISON_GREEN_CONDITION),
                new Integer(TARGET_TYPE__DISEASE_GREEN_CONDITION),
                new Integer(TARGET_TYPE__POISON_YELLOW_CONDITION),
                new Integer(TARGET_TYPE__DISEASE_YELLOW_CONDITION),
                new Integer(TARGET_TYPE__POISON_RED_CONDITION),
                new Integer(TARGET_TYPE__DISEASE_RED_CONDITION),
                new Integer(TARGET_TYPE__PARALYSED_CONDITION),
                new Integer(TARGET_TYPE__UNCONSIOUS_CONDITION),
                new Integer(TARGET_TYPE__DEAD_CONDITION),
                new Integer(TARGET_TYPE__STONED_CONDITION),
                new Integer(TARGET_TYPE__ERADICATED_CONDITION),
                new Integer(TARGET_TYPE__REPUTATION),
        		new Integer(TARGET_TYPE__MONTH_EQUALS) };
    }

    private static String getTarget(int targetType, int number)
    {
        return getTargetTypeName(targetType) + " #" + String.valueOf(number);
    }
    
    public String eventDescription()
    {
        return getEventNumber() + "-" + getEventSequenceNumber() + ": " + getEventCommandName() + getEventDescriptionForCommand();
    }
    

    // Unknown things to decode

    public static List getOffsetList()
    {
        return null;
    }
    
    public static List getObjectList(List eventList)
    {
        return null;
    }

    private boolean isTargetTypeUnderstood(int command, int targetType)
    {
        Integer[] targetTypes = getTargetTypes();
        for (int index = 0; index < targetTypes.length; index++)
        {
            Integer targetTypeNumber = targetTypes[index];
            if (targetTypeNumber.intValue() == targetType)
            {
                return true;
            }
        }
        return false;
    }
}
