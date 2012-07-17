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

package org.gamenet.application.mm8leveleditor.data.mm6.outdoor;

import java.util.ArrayList;
import java.util.List;

import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class OutdoorFace
{
    private static final int ORDER_SIZE = 2;
    private static final int FACET_RECORD_LENGTH = 308;
    private static final int FACES_BITMAP_NAME_LENGTH = 10;
    
    private static final int FACET_NORMAL_X_OFFSET = 0; // four bytes
    private static final int FACET_NORMAL_Y_OFFSET = 4; // four bytes
    private static final int FACET_NORMAL_Z_OFFSET = 8; // four bytes
    private static final int FACET_NORMAL_DISTANCE_OFFSET = 12; // four bytes

    private static final int FACET_Z_CALC1_OFFSET = 16; // four bytes
    private static final int FACET_Z_CALC2_OFFSET = 20; // four bytes
    private static final int FACET_Z_CALC3_OFFSET = 24; // four bytes
    
    private static final int FACET_ATTRIBUTES_OFFSET = 28; // four bytes

    private static final int FACET_VERTEX_LIST_OFFSET = 32;  // list of 20 shorts
    private static final int NUMBER_OF_VERTEXES_PER_FACET = 20;

    private static final int FACET_U_LIST_OFFSET = 72;  // list of 20 shorts
    private static final int NUMBER_OF_U_PER_FACET = 20;

    private static final int FACET_V_LIST_OFFSET = 112;  // list of 20 shorts
    private static final int NUMBER_OF_V_PER_FACET = 20;

    private static final int FACET_X_INTERCEPT_DISPLACEMENT_LIST_OFFSET = 152;  // list of 20 shorts
    private static final int NUMBER_OF_X_INTERCEPT_DISPLACEMENTS_PER_FACET = 20;

    private static final int FACET_Y_INTERCEPT_DISPLACEMENT_LIST_OFFSET = 192;  // list of 20 shorts
    private static final int NUMBER_OF_Y_INTERCEPT_DISPLACEMENTS_PER_FACET = 20;

    private static final int FACET_Z_INTERCEPT_DISPLACEMENT_LIST_OFFSET = 232;  // list of 20 shorts
    private static final int NUMBER_OF_Z_INTERCEPT_DISPLACEMENTS_PER_FACET = 20;

    private static final int FACET_BITMAP_INDEX_OFFSET = 272; // two bytes
    private static final int FACET_BITMAP_DELTA_U_TEXTURE_OFFSET = 274; // two bytes
    private static final int FACET_BITMAP_DELTA_V_TEXTURE_OFFSET = 276; // two bytes

    private static final int FACET_BOUNDING_BOX_MIN_X_OFFSET = 278; // two bytes
    private static final int FACET_BOUNDING_BOX_MAX_X_OFFSET = 280; // two bytes
    private static final int FACET_BOUNDING_BOX_MIN_Y_OFFSET = 282; // two bytes
    private static final int FACET_BOUNDING_BOX_MAX_Y_OFFSET = 284; // two bytes
    private static final int FACET_BOUNDING_BOX_MIN_Z_OFFSET = 286; // two bytes
    private static final int FACET_BOUNDING_BOX_MAX_Z_OFFSET = 288; // two bytes

    private static final int FACET_COG_NUMBER_OFFSET = 290; // two bytes
    private static final int FACET_COG_TRIGGERED_NUMBER_OFFSET = 292; // two bytes
    private static final int FACET_COG_TRIGGER_TYPE_OFFSET = 294; // two bytes
    private static final int FACET_RESERVED_OFFSET = 296; // two bytes

    private static final int FACET_GRADIENT_VERTEXES_LIST_OFFSET = 298; // list of 4 bytes
    private static final int NUMBER_OF_GRADIENT_VERTEXES_PER_FACET = 4;
    
    private static final int FACET_VERTEX_COUNT_OFFSET = 302; // list of 4 bytes
    private static final int FACET_POLYGON_TYPE_OFFSET = 303; // list of 4 bytes
    
    //mm7 & 8 for the rest?
    private static final int FACET_SHADE_OFFSET = 304; // list of 4 bytes
    private static final int FACET_VISIBILITY_OFFSET = 305; // list of 4 bytes

    private static final int FACET_PADDING_OFFSET = 306; // list of 2 bytes -- unused?

    private byte facetArray[] = null;
    private short ordering;
    private String bitmapName = null;
    
    private int offset = 0;
    
    public OutdoorFace(byte threeHundredEightByteRecordArray[], short ordering, String bitmapName, long offset)
    {
        super();
        
        this.facetArray = threeHundredEightByteRecordArray;
        this.ordering = ordering;
        this.bitmapName = bitmapName;
    }

    public OutdoorFace(short ordering, String bitmapName)
    {
        super();
        
        this.facetArray = new byte[FACET_RECORD_LENGTH];
        this.ordering = ordering;
        this.setBitmapName(bitmapName);
    }

    public static int populateObjects(byte[] dataSrc, int offset, List facesList, int facesCount)
    {
        long offsetArray[] = new long[facesCount];
        byte threeHundredEightByteRecordArrayArray[][] = new byte[facesCount][];
        short orderingArray[] = new short[facesCount];
        String facesBitmapNameArray[] = new String[facesCount];
        
        for (int facesIndex = 0; facesIndex < facesCount; ++facesIndex)
        {
            threeHundredEightByteRecordArrayArray[facesIndex] = new byte[FACET_RECORD_LENGTH];
            System.arraycopy(dataSrc, offset, threeHundredEightByteRecordArrayArray[facesIndex], 0, FACET_RECORD_LENGTH);
            offset += FACET_RECORD_LENGTH;
        }
        
        for (int facesIndex = 0; facesIndex < facesCount; ++facesIndex)
        {
            orderingArray[facesIndex] = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
            offset += 2;
        }
        
        for (int facesIndex = 0; facesIndex < facesCount; ++facesIndex)
        {
            facesBitmapNameArray[facesIndex] = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(dataSrc, offset, FACES_BITMAP_NAME_LENGTH);
            offset += FACES_BITMAP_NAME_LENGTH;
        }
        
        for (int facesIndex = 0; facesIndex < facesCount; ++facesIndex)
        {
            OutdoorFace face = new OutdoorFace(threeHundredEightByteRecordArrayArray[facesIndex], orderingArray[facesIndex], facesBitmapNameArray[facesIndex], offsetArray[facesIndex]);
            facesList.add(face);
        }
        
        return offset;
    }

    public static int updateData(byte[] newData, int offset, List facesList)
    {
        int facesCount = facesList.size();
        
        for (int facesIndex = 0; facesIndex < facesCount; ++facesIndex)
        {
            OutdoorFace face = (OutdoorFace)facesList.get(facesIndex);
            System.arraycopy(face.getFacetArray(), 0, newData, offset, FACET_RECORD_LENGTH);
            offset += FACET_RECORD_LENGTH;
        }
        
        for (int facesIndex = 0; facesIndex < facesCount; ++facesIndex)
        {
            OutdoorFace face = (OutdoorFace)facesList.get(facesIndex);
            ByteConversions.setShortInByteArrayAtPosition(face.getOrdering(), newData, offset);
            offset += 2;
        }
        
        for (int facesIndex = 0; facesIndex < facesCount; ++facesIndex)
        {
            OutdoorFace face = (OutdoorFace)facesList.get(facesIndex);
            ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(face.getBitmapName(), newData, offset, FACES_BITMAP_NAME_LENGTH);
            offset += FACES_BITMAP_NAME_LENGTH;
        }
        
        return offset;
    }

    public short getOrdering()
    {
        return this.ordering;
    }
    public void setOrdering(short ordering)
    {
        this.ordering = ordering;
    }

    public String getBitmapName()
    {
        return this.bitmapName;
    }
    public void setBitmapName(String bitmapName)
    {
        this.bitmapName = bitmapName.substring(0, FACES_BITMAP_NAME_LENGTH);
    }

    public int getOffset()
    {
        return this.offset;
    }
    public byte[] getFacetArray()
    {
        return this.facetArray;
    }
    
    public int[] getVertexNumberList()
    {
        int vertexCount = ByteConversions.convertByteToInt(getFacetArray()[FACET_VERTEX_COUNT_OFFSET]);

        int vertexNumberArray[] = new int[vertexCount];
        
        for (int index = 0; index < vertexCount; ++index)
        {
            vertexNumberArray[index] = (int)ByteConversions.getShortInByteArrayAtPosition(facetArray, FACET_VERTEX_LIST_OFFSET + (index * 2));
        }
        
        int lastVertexNumber = (int)ByteConversions.getShortInByteArrayAtPosition(facetArray, FACET_VERTEX_LIST_OFFSET + (NUMBER_OF_VERTEXES_PER_FACET * 2));
        if (lastVertexNumber != vertexNumberArray[0])
        {
            System.out.println("Unexpected vertex number mismatch between first <" + vertexNumberArray[0] + ">offset= " + String.valueOf(getOffset() + FACET_VERTEX_LIST_OFFSET) + " and last <" + lastVertexNumber + ">offset=" + String.valueOf(getOffset() + FACET_VERTEX_LIST_OFFSET + (NUMBER_OF_VERTEXES_PER_FACET * 2)));
        }
        
        return vertexNumberArray;
    }
    
    public static int getRecordSize()
    {
        return FACET_RECORD_LENGTH + ORDER_SIZE + FACES_BITMAP_NAME_LENGTH;
    }

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_X_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "NORMAL_X"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_Y_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "NORMAL_Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_Z_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "NORMAL_Z"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_DISTANCE_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "NORMAL_Distance"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_Z_CALC1_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Z_CALC1"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_Z_CALC2_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Z_CALC2"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_Z_CALC3_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Z_CALC3"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_ATTRIBUTES_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ATTRIBUTES"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_VERTEX_LIST_OFFSET, 2 * NUMBER_OF_VERTEXES_PER_FACET, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "VERTEX_LIST"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_U_LIST_OFFSET, 2 * NUMBER_OF_VERTEXES_PER_FACET, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "U_LIST"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_V_LIST_OFFSET, 2 * NUMBER_OF_VERTEXES_PER_FACET, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "V_LIST"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_X_INTERCEPT_DISPLACEMENT_LIST_OFFSET, 2 * NUMBER_OF_VERTEXES_PER_FACET, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "X_INTERCEPT_DISPLACEMENT_LIST"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_Y_INTERCEPT_DISPLACEMENT_LIST_OFFSET, 2 * NUMBER_OF_VERTEXES_PER_FACET, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Y_INTERCEPT_DISPLACEMENT_LIST"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_Z_INTERCEPT_DISPLACEMENT_LIST_OFFSET, 2 * NUMBER_OF_VERTEXES_PER_FACET, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Z_INTERCEPT_DISPLACEMENT_LIST"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_BITMAP_INDEX_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "BITMAP_INDEX"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_BITMAP_DELTA_U_TEXTURE_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "BITMAP_DELTA_U_TEXTURE"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_BITMAP_DELTA_V_TEXTURE_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "BITMAP_DELTA_V_TEXTURE"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_BOUNDING_BOX_MIN_X_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "BOUNDING_BOX_MIN_X"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_BOUNDING_BOX_MAX_X_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "BOUNDING_BOX_MAX_X"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_BOUNDING_BOX_MIN_Y_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "BOUNDING_BOX_MIN_Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_BOUNDING_BOX_MAX_Y_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "BOUNDING_BOX_MAX_Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_BOUNDING_BOX_MIN_Z_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "BOUNDING_BOX_MIN_Z"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_BOUNDING_BOX_MAX_Z_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "BOUNDING_BOX_MAX_Z"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_COG_NUMBER_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "COG_NUMBER"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_COG_TRIGGERED_NUMBER_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "COG_TRIGGERED_NUMBER"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_COG_TRIGGER_TYPE_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "COG_TRIGGER_TYPE"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_RESERVED_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "RESERVED"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_GRADIENT_VERTEXES_LIST_OFFSET, 1 * NUMBER_OF_GRADIENT_VERTEXES_PER_FACET, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "GRADIENT_VERTEXES_LIST"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_VERTEX_COUNT_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "VERTEX_COUNT"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_POLYGON_TYPE_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "POLYGON_TYPE"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_SHADE_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "SHADE"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_VISIBILITY_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "VISIBILITY"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACET_PADDING_OFFSET, 2, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "PADDING"));

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List outdoorFacesList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return outdoorFacesList.size();
            }

            public byte[] getData(int dataRow)
            {
                OutdoorFace outdoorFace = (OutdoorFace)outdoorFacesList.get(dataRow);
                return outdoorFace.getFacetArray();
            }

            public int getAdjustedDataRowIndex(int dataRow)
            {
                return dataRow;
            }
            
            public String getIdentifier(int dataRow)
            {
                OutdoorFace outdoorFace = (OutdoorFace)outdoorFacesList.get(dataRow);
                return outdoorFace.getBitmapName();
            }

            public int getOffset(int dataRow)
            {
                OutdoorFace outdoorFace = (OutdoorFace)outdoorFacesList.get(dataRow);
                return outdoorFace.getOffset();
            }
        };
    }
}
