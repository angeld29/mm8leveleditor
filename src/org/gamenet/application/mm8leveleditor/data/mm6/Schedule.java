/*
 * Created on Jul 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.gamenet.application.mm8leveleditor.data.mm6;

import java.util.List;

import org.gamenet.swing.controls.ComparativeTableControl;


class Schedule
{
    private static final int SCHEDULE_RECORD_SIZE = 12;
    
    private static final int X_OFFSET = 0; // 2 bytes
    private static final int Y_OFFSET = 2; // 2 bytes
    private static final int Z_OFFSET = 4; // 2 bytes
    private static final int ATTRIBUTES_OFFSET = 6; // 2 bytes
    
    // these might be in reverse order
    private static final int ACTION_OFFSET = 8; // 1 byte
    private static final int HOUR_OFFSET = 9; // 1 byte
    private static final int DAY_OFFSET = 10; // 1 byte
    private static final int MONTH_OFFSET = 11; // 1 byte

    public static int getRecordSize()
    {
        return SCHEDULE_RECORD_SIZE;
    }

    public static void addOffsets(int gameVersion, List offsetList, int startingOffset, int index)
    {
        String prefix = "";
        if (index != -1)  prefix = "Schedule#" + String.valueOf(index) + ":";
        
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + X_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, prefix + "x"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + Y_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, prefix + "y"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + Z_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, prefix + "z"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + ATTRIBUTES_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, prefix + "attributes"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + ACTION_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, prefix + "action"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + HOUR_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, prefix + "hour"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + DAY_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, prefix + "day"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + MONTH_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, prefix + "month"));
    }	
}