/*
 * Created on Apr 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.gamenet.application.mm8leveleditor.data.mm6.fileFormat;

import java.util.ArrayList;
import java.util.List;

import org.gamenet.application.mm8leveleditor.data.DsftBinSprite;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;


public class DsftBinSpriteMM6 implements DsftBinSprite
{
    // Label	Filename	(NEW)	Palette	Scale	Light	Ticks	Facing	X	Y	Alpha	Glow	Center	Flag4	//	Comments

    public static final int DSFT_SPRITE_RECORD_LENGTH = 56;

    protected static final int SET_NAME_OFFSET = 0x00;
    protected static final int SET_NAME_MAXLENGTH = 12;
    
    protected static final int SPRITE_NAME_OFFSET = 12;
    protected static final int SPRITE_NAME_MAXLENGTH = 28;
    
    private static final int SPRITE_FRAME_PTR_TABLE_OFFSET = 24; // unknown if this exists -- probably used only at runtime

    private static final int SCALE_OFFSET = 40; // 4 bytes -- the values are goofy, but that's also true for MM7
    
     private static final int ATTRIBUTE_MASK_OFFSET = 44; // 2 bytes
     private static final int LIGHT_RADIUS_OFFSET = 46; // 2 bytes

    private static final int PALETTE_ID_OFFSET = 48; // 2 bytes -- this is confirmed
    private static final int PALETTE_INDEX_OFFSET = 50; // 2 bytes -- probably used only at runtime
    
    private static final int FRAME_DISPLAY_TIME_OFFSET = 52; // 2 bytes  // these seem correct
    private static final int TOTAL_ANIMATION_TIME_OFFSET = 54; // 2 bytes // these seem correct

    protected static final int CONTINUATION_OFFSET = 54; // two bytes - 00 if continuation, # of items in this set?
    
    private String spriteName = null;
    private byte data[] = null;
    private long offset = 0;
    
    public DsftBinSpriteMM6()
    {
        super();
    }

    public int initialize(byte[] data, int offset)
    {
        this.offset = offset;
        this.data = new byte[DSFT_SPRITE_RECORD_LENGTH];
        System.arraycopy(data, offset, this.data, 0, DSFT_SPRITE_RECORD_LENGTH);
        offset += DSFT_SPRITE_RECORD_LENGTH;
        
        return offset;
    }

    public int updateData(byte[] newData, int offset)
    {
        System.arraycopy(this.data, 0, newData, offset, DSFT_SPRITE_RECORD_LENGTH);
        offset += DSFT_SPRITE_RECORD_LENGTH;
        
        return offset;
    }

    public boolean hasMoreRecords()
    {
        int continuation = ByteConversions.getShortInByteArrayAtPosition(data, CONTINUATION_OFFSET);

        if ((this.getSetName().length() != 0) && (continuation > 1))  return true;

        return (0 == continuation);
    }
    
    public int getContinuation()
    {
        return ByteConversions.getShortInByteArrayAtPosition(data, CONTINUATION_OFFSET);
    }
    
    public String getSpriteName()
    {
        return ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(this.data, SPRITE_NAME_OFFSET, SPRITE_NAME_MAXLENGTH);
    }
    public void setSpriteName(String spriteName)
    {
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(spriteName, this.data, SPRITE_NAME_OFFSET, SPRITE_NAME_MAXLENGTH);
    }
    
    public String getSetName()
    {
        return ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(this.data, SET_NAME_OFFSET, SET_NAME_MAXLENGTH);
    }
    public void setSetName(String setName)
    {
        ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(setName, this.data, SET_NAME_OFFSET, SET_NAME_MAXLENGTH);
    }
    
    public byte[] getData()
    {
        return this.data;
    }
    public long getOffset()
    {
        return this.offset;
    }

    public int getRecordSize()
    {
        return getStaticRecordSize();
    }
    
    public static int getStaticRecordSize()
    {
        return DSFT_SPRITE_RECORD_LENGTH;
    }

    // Unknown things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        
        offsetList.add(new ComparativeTableControl.OffsetData(0, 12, ComparativeTableControl.REPRESENTATION_STRING, "set name"));
        offsetList.add(new ComparativeTableControl.OffsetData(12, 12, ComparativeTableControl.REPRESENTATION_STRING, "file name"));
        
        offsetList.add(new ComparativeTableControl.OffsetData(24, 16, ComparativeTableControl.REPRESENTATION_STRING));
        
        offsetList.add(new ComparativeTableControl.OffsetData(40, 4, ComparativeTableControl.REPRESENTATION_INT_DEC));
        
        offsetList.add(new ComparativeTableControl.OffsetData(44, 4, ComparativeTableControl.REPRESENTATION_INT_HEX, "attributes"));
        
        offsetList.add(new ComparativeTableControl.OffsetData(48, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "palette index"));
        offsetList.add(new ComparativeTableControl.OffsetData(50, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC));
       
        offsetList.add(new ComparativeTableControl.OffsetData(52, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "frame time"));
        offsetList.add(new ComparativeTableControl.OffsetData(54, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "total time"));

//        offsetList.add(new ComparativeTableControl.OffsetData(SCALE_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "scale"));
//        offsetList.add(new ComparativeTableControl.OffsetData(AMBIENCE_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "ambience"));
//        offsetList.add(new ComparativeTableControl.OffsetData(PALETTE_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "palette"));
//        offsetList.add(new ComparativeTableControl.OffsetData(TICKS_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "ticks"));
//        
//        offsetList.add(new ComparativeTableControl.OffsetData(UNKNOWN1_OFFSET, 2, ComparativeTableControl.REPRESENTATION_BYTE_DEC));
//        offsetList.add(new ComparativeTableControl.OffsetData(UNKNOWN2_OFFSET, 3, ComparativeTableControl.REPRESENTATION_BYTE_DEC));
//        offsetList.add(new ComparativeTableControl.OffsetData(UNKNOWN4_OFFSET, 2, ComparativeTableControl.REPRESENTATION_BYTE_DEC));
         
        return offsetList;
    }
    
}