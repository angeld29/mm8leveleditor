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

package org.gamenet.application.mm8leveleditor.data;

import java.util.List;
import java.util.Map;

import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.Str;

public interface EventFormat
{
    public interface IndirectValue<E,F>
    {
    	public int getDirectArgumentDataType();
		public F getDirectValueForIndirectValue(E indirectValue);
    	public E getIndirectValueForDirectValue(F directValue);
    }
    
    public static final int ARGUMENT_TYPE__UNKNOWN = 0;
    public static final int ARGUMENT_TYPE__UNKNOWN_BYTE = -1;
    public static final int ARGUMENT_TYPE__UNKNOWN_SHORT = -2;
    public static final int ARGUMENT_TYPE__UNKNOWN_INTEGER = -4;
    
    public static final int ARGUMENT_TYPE__UNKNOWN_ZERO_BYTE = -11;
    public static final int ARGUMENT_TYPE__UNKNOWN_ZERO_SHORT = -12;
    public static final int ARGUMENT_TYPE__UNKNOWN_ZERO_INTEGER = -14;
    
    public static final int ARGUMENT_TYPE__TARGET_TYPE = 1;
    public static final int ARGUMENT_TYPE__TARGET_NUMBER = 2;
    public static final int ARGUMENT_TYPE__SEQUENCE = 3; 
    public static final int ARGUMENT_TYPE__2DEVENT_NUMBER = 4; // ByteConversions.getIntegerInByteArrayAtPosition
    public static final int ARGUMENT_TYPE__LOCAL_EVENT_STRING_NUMBER = 5; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__COORDINATE_SET = 6; // 3 x ByteConversions.getIntegerInByteArrayAtPosition
    public static final int ARGUMENT_TYPE__DIALOG_NUMBER = 7; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__MINI_ICON_NUMBER = 8; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__FILENAME_13 = 9; // 8 to 13?
    public static final int ARGUMENT_TYPE__CHEST_NUMBER = 10; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__PARTY_MEMBER = 11; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__FACE_IMAGE_NUMBER = 12; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__3DOBJECT_NUMBER = 13; // ByteConversions.getIntegerInByteArrayAtPosition
    public static final int ARGUMENT_TYPE__FACET_NUMBER = 14; // ByteConversions.getIntegerInByteArrayAtPosition
    public static final int ARGUMENT_TYPE__SPRITE_NUMBER = 15; // ByteConversions.getIntegerInByteArrayAtPosition
    public static final int ARGUMENT_TYPE__BOOLEAN = 16; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__SPECIES_TYPE = 17; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__SUBSPECIES_TYPE = 18; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__MONSTER_CREATION_COUNT = 19; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__SPELL_NUMBER = 20; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__SPELL_SKILL_EXPERTISE = 21; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__SPELL_SKILL_LEVEL = 22; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__NPCTEXT_NUMBER = 23; // ByteConversions.getIntegerInByteArrayAtPosition
    public static final int ARGUMENT_TYPE__NPCDATA_NUMBER = 24; // ByteConversions.getIntegerInByteArrayAtPosition
    public static final int ARGUMENT_TYPE__NPC_MENU_INDEX = 25; // ByteConversions.convertByteToInt
    public static final int ARGUMENT_TYPE__GLOBAL_EVENT_NUMBER = 26; // ByteConversions.getIntegerInByteArrayAtPosition
    public static final int ARGUMENT_TYPE__FILENAME_12 = 27; // 8 to 13?
    public static final int ARGUMENT_TYPE__FACET_ATTRIBUTE_TYPE = 28;
    public static final int ARGUMENT_TYPE__ZERO_BYTE_PLACEHOLDER = 29;
    public static final int ARGUMENT_TYPE__SOUND_NUMBER = 30;

    public static final int ARGUMENT_DATA_TYPE__UNSIGNED_BYTE = 1;
    public static final int ARGUMENT_DATA_TYPE__UNSIGNED_SHORT = 2;
    public static final int ARGUMENT_DATA_TYPE__SIGNED_INT = 3;
    public static final int ARGUMENT_DATA_TYPE__STRING = 4;
    public static final int ARGUMENT_DATA_TYPE__COORDINATE_SET = 5;
    public static final int ARGUMENT_DATA_TYPE__CHOICE_PULLDOWN = 6;
    public static final int ARGUMENT_DATA_TYPE__BYTE_ARRAY = 7;
    public static final int ARGUMENT_DATA_TYPE__PLACEHOLDER = 8;
    
    // Event management
    public Event createEventInstance();
    public int initialize(Event event, byte[] dataSrc, int offset, byte eventLength);
    public void initializeWithValues(Event event, int eventNumber, int sequence,
            int commandType);
    public List<Event> readEvents(byte data[]);
    public byte[] writeEvents(List<Event> eventList);
    public int updateData(Event event, byte[] newData, int offset);

    public boolean isDebugging(Event event);
    public boolean isUnderstood(Event event);
    public String eventDescription(Event event);

    public Integer[] getCommandTypes();
    public String getCommandTypeName(int commandType);
    public int[] getArgumentTypeArrayForCommandType(int commandType);
    public String[] getArgumentTypePrefixLabelArrayForCommandType(int commandType);
    public String[] getArgumentTypeSuffixLabelArrayForCommandType(int commandType);
    public int[] getArgumentTypeSortOrderingArrayForCommandType(int commandType);
	public boolean getArgumentTypeIsIndirectFor(Event currentEvent, int argumentTypeIndex);
	public IndirectValue getArgumentTypeIndirectValueFor(Event currentEvent, int argumentTypeIndex);

    public int getArgumentTypeAtIndexForCommandType(int index, int commandType);

    public int getArgumentDataType(int argumentType);
    public int getArgumentTypeDataSize(int argumentType);

    public Integer[] getTargetTypes();
    public String getTargetTypeName(int targetType);

    public Map getArgumentTypeChoicesArray(int argumentType);
    
    // TODO: This doesn't seem appropriate, yet it's the only place for it right now
	public void setStrResource(Str eventStr);
	public Str getStrResource();
}