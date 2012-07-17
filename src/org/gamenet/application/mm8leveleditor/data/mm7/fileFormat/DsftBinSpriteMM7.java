/*
 * Created on Apr 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.gamenet.application.mm8leveleditor.data.mm7.fileFormat;

import java.util.ArrayList;
import java.util.List;

import org.gamenet.application.mm8leveleditor.data.DsftBinSprite;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;


public class DsftBinSpriteMM7 implements DsftBinSprite
{
    // Label	Filename	(NEW)	Palette	Scale	Light	Ticks	Facing	X	Y	Alpha	Glow	Center	Flag4	//	Comments

    public static final int DSFT_SPRITE_RECORD_LENGTH = 60;

    
	// FOR MM7
	public final static int ATTRIBUTE__NOT_LAST_FRAME = 	0x00000001; 
	public final static int ATTRIBUTE__SELF_LUMINOUS = 	0x00000002; 
	public final static int ATTRIBUTE__NEW_FRAME = 		0x00000004; 
	
	public final static int ATTRIBUTE__ONLY_ONE_IMAGE = 	0x00000010; 
	public final static int ATTRIBUTE__CENTER = 			0x00000020; 
	
	// Special case For mirroring Monster fidget Sequence
	public final static int ATTRIBUTE__FIDGET = 			0x00000040; 
	
	// frame table has been enabled
	public final static int ATTRIBUTE__ENABLED =			0x00000080; 

	public final static int ATTRIBUTE__MIRROR0 = 0x00000100; // mirror image  0
	public final static int ATTRIBUTE__MIRROR1 = 0x00000200; // mirror image  1
	public final static int ATTRIBUTE__MIRROR2 = 0x00000400; // mirror image  2
	public final static int ATTRIBUTE__MIRROR3 = 0x00000800; // mirror image  3
	
	public final static int ATTRIBUTE__MIRROR4 = 0x00001000; // mirror image  4
	public final static int ATTRIBUTE__MIRROR5 = 0x00002000; // mirror image  5
	public final static int ATTRIBUTE__MIRROR6 = 0x00004000; // mirror image  6
	public final static int ATTRIBUTE__MIRROR7 = 0x00008000; // mirror image  7

	// mirror last three images 5-7
	public final static int ATTRIBUTE__MIRROR =			0x0000e000; 
	
	// only views 0,2,4 exist (mirrored)
	public final static int ATTRIBUTE__REDUCED_VIEW_SET =	0x00010000; 
	
	// certain palette entries are not shaded
	public final static int ATTRIBUTE__GLOW =				0x00020000; 
	
	// transparent image
	public final static int ATTRIBUTE__TRANSPARENT = 		0x00040000;


    protected static final int SET_NAME_OFFSET = 0x00;
    protected static final int SET_NAME_MAXLENGTH = 12;
    
    protected static final int SPRITE_NAME_OFFSET = 12;
    protected static final int SPRITE_NAME_MAXLENGTH = 28;
    

    private static final int SPRITE_FRAME_PTR_TABLE_OFFSET = 24; // unknown if this exists -- probably used only at runtime

    private static final int SCALE_OFFSET = 40; // 4 bytes -- the values are goofy, but that's also true for MM7
    
    private static final int ATTRIBUTE_MASK_OFFSET = 44; // 4 bytes
    private static final int LIGHT_RADIUS_OFFSET = 48; // 2 bytes

    private static final int PALETTE_ID_OFFSET = 50; // 2 bytes
    private static final int PALETTE_INDEX_OFFSET = 52; // 2 bytes -- probably used only at runtime
    
    private static final int FRAME_DISPLAY_TIME_OFFSET = 54; // 2 bytes 
    private static final int TOTAL_ANIMATION_TIME_OFFSET = 56; // 2 bytes
    private static final int UNUSED_OFFSET = 58; // 2 bytes
    
    protected static final int CONTINUATION_OFFSET = 56; // two bytes - 00 if continuation, # of items in this set?
    
    private String spriteName = null;
    private byte data[] = null;
    private long offset = 0;
    
    public DsftBinSpriteMM7()
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


        offsetList.add(new ComparativeTableControl.OffsetData(SET_NAME_OFFSET, SET_NAME_MAXLENGTH, ComparativeTableControl.REPRESENTATION_STRING, "name"));
        offsetList.add(new ComparativeTableControl.OffsetData(SPRITE_NAME_OFFSET, SPRITE_NAME_MAXLENGTH, ComparativeTableControl.REPRESENTATION_STRING, "name"));

        offsetList.add(new ComparativeTableControl.OffsetData(SPRITE_FRAME_PTR_TABLE_OFFSET, 16, ComparativeTableControl.REPRESENTATION_STRING, "sprite frame ptr table -- unused"));

        offsetList.add(new ComparativeTableControl.OffsetData(SCALE_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "scale"));
        offsetList.add(new ComparativeTableControl.OffsetData(ATTRIBUTE_MASK_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_HEX, "attribute mask"));
        offsetList.add(new ComparativeTableControl.OffsetData(LIGHT_RADIUS_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "light radius"));
        offsetList.add(new ComparativeTableControl.OffsetData(PALETTE_ID_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "palette id"));
        offsetList.add(new ComparativeTableControl.OffsetData(PALETTE_INDEX_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "palette index"));
        offsetList.add(new ComparativeTableControl.OffsetData(FRAME_DISPLAY_TIME_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "frame display time"));
        offsetList.add(new ComparativeTableControl.OffsetData(TOTAL_ANIMATION_TIME_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "total animation time"));
            
        return offsetList;
    }
    
}