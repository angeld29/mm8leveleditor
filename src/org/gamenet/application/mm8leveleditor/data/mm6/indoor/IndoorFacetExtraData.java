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

package org.gamenet.application.mm8leveleditor.data.mm6.indoor;

import java.util.ArrayList;
import java.util.List;

import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class IndoorFacetExtraData
{
    private static final int FACET_EXTRA_DATA_RECORD_LENGTH = 36;
    private static final int FACES_BITMAP_NAME_LENGTH = 10;

    private static final int Z_FADE_OFFSET = 0; // 4 bytes
    private static final int X_FADE_OFFSET = 4; // 4 bytes
    private static final int Y_FADE_OFFSET = 8; // 4 bytes

    private static final int FACET_INDEX_OFFSET = 12; // 2 bytes
    private static final int BITMAP_INDEX_OFFSET = 14; // 2 bytes
    private static final int TEXTURE_FRAME_TABLE_INDEX_OFFSET = 16; // 2 bytes
    private static final int TEXTURE_FRAME_TABLE_COG_INDEX_OFFSET = 18; // 2 bytes
    private static final int BITMAP_OFFSET_X_OFFSET = 20; // 2 bytes
    private static final int BITMAP_OFFSET_Y_OFFSET = 22; // 2 bytes
    private static final int COG_NUMBER_OFFSET = 24; // 2 bytes
    private static final int COG_TRIGGERED_OFFSET = 26; // 2 bytes
    private static final int COG_TRIGGER_TYPE_OFFSET = 28; // 2 bytes
    private static final int FADE_BASE_X_OFFSET = 30; // 2 bytes
    private static final int FADE_BASE_Y_OFFSET = 32; // 2 bytes
    private static final int LIGHT_LEVEL_OFFSET = 34; // 2 bytes

    private byte indoorFacetExtraData[] = null;
    private String faceBitmapName = null;

    public IndoorFacetExtraData()
    {
        super();
        this.indoorFacetExtraData = new byte[FACET_EXTRA_DATA_RECORD_LENGTH];
    }

    public int initialize(byte dataSrc[], int offset)
    {
        System.arraycopy(dataSrc, offset, this.indoorFacetExtraData, 0, this.indoorFacetExtraData.length);
        offset += this.indoorFacetExtraData.length;
        
        return offset;
    }

    public static int populateObjects(byte[] data, int offset, List unknownList)
    {
        int unknownCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        return populateObjects(data, offset, unknownList, unknownCount);
    }

    public int initializeWithBitmapName(byte dataSrc[], int offset)
    {
        this.faceBitmapName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(dataSrc, offset, FACES_BITMAP_NAME_LENGTH);
        offset += FACES_BITMAP_NAME_LENGTH;
        
        return offset;
    }

    public static int populateObjects(byte[] data, int offset, List unknownList, int unknownCount)
    {
        for (int unknownIndex = 0; unknownIndex < unknownCount; ++unknownIndex)
        {
            IndoorFacetExtraData unknown = new IndoorFacetExtraData();
            unknownList.add(unknown);
            offset = unknown.initialize(data, offset);
        }
        
        for (int faceIndex = 0; faceIndex < unknownList.size(); ++faceIndex)
        {
            IndoorFacetExtraData face = (IndoorFacetExtraData)unknownList.get(faceIndex);
            offset = face.initializeWithBitmapName(data, offset);
        }

        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List facetExtraDataList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(facetExtraDataList.size(), newData, offset);
        offset += 4;

        for (int facetExtraDataIndex = 0; facetExtraDataIndex < facetExtraDataList.size(); ++facetExtraDataIndex)
        {
            IndoorFacetExtraData facetExtraData = (IndoorFacetExtraData)facetExtraDataList.get(facetExtraDataIndex);
            System.arraycopy(facetExtraData.getIndoorFacetExtraData(), 0, newData, offset, facetExtraData.getIndoorFacetExtraData().length);
            offset += facetExtraData.getIndoorFacetExtraData().length;
        }
        
        for (int facetExtraDataIndex = 0; facetExtraDataIndex < facetExtraDataList.size(); ++facetExtraDataIndex)
        {
            IndoorFacetExtraData facetExtraData = (IndoorFacetExtraData)facetExtraDataList.get(facetExtraDataIndex);
            ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(facetExtraData.getBitmapName(), newData, offset, FACES_BITMAP_NAME_LENGTH);
            offset += FACES_BITMAP_NAME_LENGTH;
        }
        
        return offset;
    }

    public byte[] getIndoorFacetExtraData()
    {
        return this.indoorFacetExtraData;
    }

    public String getBitmapName()
    {
        return this.faceBitmapName;
    }
    public void setBitmapName(String faceBitmapName)
    {
        this.faceBitmapName = faceBitmapName;
    }

    public static int getRecordSize()
    {
        return FACET_EXTRA_DATA_RECORD_LENGTH + FACES_BITMAP_NAME_LENGTH;
    }


    // Unknown things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(Z_FADE_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Z Fade"));
        offsetList.add(new ComparativeTableControl.OffsetData(X_FADE_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "X Fade"));
        offsetList.add(new ComparativeTableControl.OffsetData(Y_FADE_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Y Fade"));

        offsetList.add(new ComparativeTableControl.OffsetData(FACET_INDEX_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Facet Index"));
        offsetList.add(new ComparativeTableControl.OffsetData(BITMAP_INDEX_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bitmap Index"));
        offsetList.add(new ComparativeTableControl.OffsetData(TEXTURE_FRAME_TABLE_INDEX_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Texture FrameTable Index"));
        offsetList.add(new ComparativeTableControl.OffsetData(TEXTURE_FRAME_TABLE_COG_INDEX_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Texture FrameTable Cog Index"));
        offsetList.add(new ComparativeTableControl.OffsetData(BITMAP_OFFSET_X_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bitmap X Offset"));
        offsetList.add(new ComparativeTableControl.OffsetData(BITMAP_OFFSET_Y_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bitmap Y Offset"));
        offsetList.add(new ComparativeTableControl.OffsetData(COG_NUMBER_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Cog Number"));
        offsetList.add(new ComparativeTableControl.OffsetData(COG_TRIGGERED_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Cog Triggered"));
        offsetList.add(new ComparativeTableControl.OffsetData(COG_TRIGGER_TYPE_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Cog Trigger Type"));
        offsetList.add(new ComparativeTableControl.OffsetData(FADE_BASE_X_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Fade Base X Index"));
        offsetList.add(new ComparativeTableControl.OffsetData(FADE_BASE_Y_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Fade Base Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(LIGHT_LEVEL_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Light Level"));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List unknownList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return unknownList.size();
            }

            public byte[] getData(int dataRow)
            {
                IndoorFacetExtraData unknown = (IndoorFacetExtraData)unknownList.get(dataRow);
                return unknown.getIndoorFacetExtraData();
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
}
