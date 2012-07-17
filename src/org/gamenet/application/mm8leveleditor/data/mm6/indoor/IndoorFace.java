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

import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class IndoorFace
{
    private static final int FACE_RECORD_LENGTH_MM6 = 80;
    private static final int FACE_RECORD_LENGTH_MM7 = 96;
    
    private static final int FACES_BITMAP_NAME_LENGTH = 10;

    private static final int FACET_NORMAL_X_FIXED_POINT_OFFSET_MM6 = 0; // 4-byte int
    private static final int FACET_NORMAL_Y_FIXED_POINT_OFFSET_MM6 = 4; // 4-byte int
    private static final int FACET_NORMAL_Z_FIXED_POINT_OFFSET_MM6 = 8; // 4-byte int
    private static final int FACET_NORMAL_DISTANCE_FIXED_POINT_OFFSET_MM6 = 12; // 4-byte int

    private static final int Z_CALC_1_OFFSET_MM6 = 16; // 4-byte
    private static final int Z_CALC_2_OFFSET_MM6 = 20; // 4-byte
    private static final int Z_CALC_3_OFFSET_MM6 = 24; // 4-byte

    private static final int ATTRIBUTES_OFFSET_MM6 = 28; // 4-byte

    private static final int VERTEX_INDEX_OFFSET_OFFSET_MM6 = 32; // 4-byte
    private static final int X_DISPLACEMENT_OFFSET_OFFSET_MM6 = 36; // 4-byte
    private static final int Y_DISPLACEMENT_OFFSET_OFFSET_MM6 = 40; // 4-byte
    private static final int Z_DISPLACEMENT_OFFSET_OFFSET_MM6 = 44; // 4-byte
    private static final int U_TEXTURE_OFFSET_OFFSET_MM6 = 48; // 4-byte
    private static final int V_TEXTURE_OFFSET_OFFSET_MM6 = 52; // 4-byte

    private static final int DATA_INDEX_OFFSET_MM6 = 56; // 2-byte
    private static final int BITMAP_INDEX_OFFSET_MM6 = 58; // 2-byte
    private static final int ROOM_NUMBER_OFFSET_MM6 = 60; // 2-byte
    private static final int ROOM_NUMBER_BEHIND_FACET_OFFSET_MM6 = 62; // 2-byte

    private static final int BOUNDING_BOX_MIN_X_OFFSET_MM6 = 64; // two bytes
    private static final int BOUNDING_BOX_MAX_X_OFFSET_MM6 = 66; // two bytes
    private static final int BOUNDING_BOX_MIN_Y_OFFSET_MM6 = 68; // two bytes
    private static final int BOUNDING_BOX_MAX_Y_OFFSET_MM6 = 70; // two bytes
    private static final int BOUNDING_BOX_MIN_Z_OFFSET_MM6 = 72; // two bytes
    private static final int BOUNDING_BOX_MAX_Z_OFFSET_MM6 = 74; // two bytes
    
    private static final int FACET_TYPE_OFFSET_MM6 = 76; // one byte
    private static final int NUMBER_OF_VERTEXES_OFFSET_MM6 = 77; // one byte
    private static final int PADDING_OFFSET_MM6 = 78; // 2 bytes

    
    private static final int FACET_NORMAL_X_FLOAT_OFFSET_MM7 = 0; // 4-byte float
    private static final int FACET_NORMAL_Y_FLOAT_OFFSET_MM7 = 4; // 4-byte float
    private static final int FACET_NORMAL_Z_FLOAT_OFFSET_MM7 = 8; // 4-byte float
    private static final int FACET_NORMAL_DISTANCE_FLOAT_OFFSET_MM7 = 12; // 4-byte float

    private static final int FACET_NORMAL_X_FIXED_POINT_OFFSET_MM7 = 16; // 4-byte int
    private static final int FACET_NORMAL_Y_FIXED_POINT_OFFSET_MM7 = 20; // 4-byte int
    private static final int FACET_NORMAL_Z_FIXED_POINT_OFFSET_MM7 = 24; // 4-byte int
    private static final int FACET_NORMAL_DISTANCE_FIXED_POINT_OFFSET_MM7 = 28; // 4-byte int

    private static final int Z_CALC_1_OFFSET_MM7 = 32; // 4-byte
    private static final int Z_CALC_2_OFFSET_MM7 = 36; // 4-byte
    private static final int Z_CALC_3_OFFSET_MM7 = 40; // 4-byte

    private static final int ATTRIBUTES_OFFSET_MM7 = 44; // 4-byte

    private static final int VERTEX_INDEX_OFFSET_OFFSET_MM7 = 48; // 4-byte
    private static final int X_DISPLACEMENT_OFFSET_OFFSET_MM7 = 52; // 4-byte
    private static final int Y_DISPLACEMENT_OFFSET_OFFSET_MM7 = 56; // 4-byte
    private static final int Z_DISPLACEMENT_OFFSET_OFFSET_MM7 = 60; // 4-byte
    private static final int U_TEXTURE_OFFSET_OFFSET_MM7 = 64; // 4-byte
    private static final int V_TEXTURE_OFFSET_OFFSET_MM7 = 68; // 4-byte

    private static final int DATA_INDEX_OFFSET_MM7 = 72; // 2-byte
    private static final int BITMAP_INDEX_OFFSET_MM7 = 74; // 2-byte
    private static final int ROOM_NUMBER_OFFSET_MM7 = 76; // 2-byte
    private static final int ROOM_NUMBER_BEHIND_FACET_OFFSET_MM7 = 78; // 2-byte

    private static final int BOUNDING_BOX_MIN_X_OFFSET_MM7 = 80; // two bytes
    private static final int BOUNDING_BOX_MAX_X_OFFSET_MM7 = 82; // two bytes
    private static final int BOUNDING_BOX_MIN_Y_OFFSET_MM7 = 84; // two bytes
    private static final int BOUNDING_BOX_MAX_Y_OFFSET_MM7 = 86; // two bytes
    private static final int BOUNDING_BOX_MIN_Z_OFFSET_MM7 = 88; // two bytes
    private static final int BOUNDING_BOX_MAX_Z_OFFSET_MM7 = 90; // two bytes
    
    private static final int FACET_TYPE_OFFSET_MM7 = 92; // one byte
    
    private static final int NUMBER_OF_VERTEXES_OFFSET_MM7 = 93; // one byte

    private static final int PADDING_OFFSET_MM7 = 94; // 2 bytes
    
    // face2
    
    private int gameVersion = 0;
    private byte facetData[] = null;
    
    // variable length data
    private int vertexIndexArray[];
    private short xDisplacementArray[];
    private short yDisplacementArray[];
    private short zDisplacementArray[];
    private short uTextureArray[];
    private short vTextureArray[];
    
    private String faceBitmapName = null;

    public IndoorFace(int gameVersion)
    {
        super();
        this.gameVersion = gameVersion;
        
        if (GameVersion.MM6 == gameVersion)
            this.facetData = new byte[FACE_RECORD_LENGTH_MM6];
        else this.facetData = new byte[FACE_RECORD_LENGTH_MM7];
     }

    public int initialize(byte dataSrc[], int offset)
    {
        System.arraycopy(dataSrc, offset, this.facetData, 0, this.facetData.length);
        offset += this.facetData.length;
        
        return offset;
    }

    public int getVertexCount()
    {
        if (GameVersion.MM6 == gameVersion)
            return ByteConversions.convertByteToInt(this.facetData[NUMBER_OF_VERTEXES_OFFSET_MM6]);
        else return ByteConversions.convertByteToInt(this.facetData[NUMBER_OF_VERTEXES_OFFSET_MM7]);
    }
    
    public int initialize2(byte dataSrc[], int offset)
    {
        int vertexCount = getVertexCount();
        
        vertexIndexArray = new int[vertexCount];
        for (int index = 0; index < vertexIndexArray.length; ++index)
        {
            vertexIndexArray[index] = ByteConversions.getUnsignedShortInByteArrayAtPosition(dataSrc, offset);
            offset += 2;
        }

        xDisplacementArray = new short[vertexCount];
        for (int index = 0; index < xDisplacementArray.length; ++index)
        {
            xDisplacementArray[index] = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
            offset += 2;
        }

        yDisplacementArray = new short[vertexCount];
        for (int index = 0; index < yDisplacementArray.length; ++index)
        {
            yDisplacementArray[index] = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
            offset += 2;
        }

        zDisplacementArray = new short[vertexCount];
        for (int index = 0; index < zDisplacementArray.length; ++index)
        {
            zDisplacementArray[index] = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
            offset += 2;
        }

        uTextureArray = new short[vertexCount];
        for (int index = 0; index < uTextureArray.length; ++index)
        {
            uTextureArray[index] = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
            offset += 2;
        }

        vTextureArray = new short[vertexCount];
        for (int index = 0; index < vTextureArray.length; ++index)
        {
            vTextureArray[index] = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
            offset += 2;
        }
        
        return offset;
    }

    public int initializeWithBitmapName(byte dataSrc[], int offset)
    {
        this.faceBitmapName = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(dataSrc, offset, FACES_BITMAP_NAME_LENGTH);
        offset += FACES_BITMAP_NAME_LENGTH;
        
        return offset;
    }

    public static int populateObjects(int gameVersion, byte[] data, int offset, List faceList, int variableFacetDataSize)
    {
        int lastOffset = offset;
        int faceCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int faceIndex = 0; faceIndex < faceCount; ++faceIndex)
        {
            IndoorFace face = new IndoorFace(gameVersion);
            faceList.add(face);
            offset = face.initialize(data, offset);
        }
        
        byte variableFacetData[] = new byte[variableFacetDataSize];
        offset += variableFacetDataSize;
        
        int variableFacetDataOffset = 0;
        for (int faceIndex = 0; faceIndex < faceList.size(); ++faceIndex)
        {
            IndoorFace face = (IndoorFace)faceList.get(faceIndex);
            variableFacetDataOffset = face.initialize2(variableFacetData, variableFacetDataOffset);
        }
        // IMPLEMENT: why doesn't this check pass?
//        if (variableFacetDataOffset != variableFacetData.length)
//            throw new RuntimeException("variableFacetDataOffset<" + variableFacetDataOffset + "> != variableFacetData.length<" + variableFacetData.length + ">");
        

        for (int faceIndex = 0; faceIndex < faceList.size(); ++faceIndex)
        {
            IndoorFace face = (IndoorFace)faceList.get(faceIndex);
            offset = face.initializeWithBitmapName(data, offset);
        }
        
        return offset;
    }
    
    public int updateVariableData(byte[] newData, int offset)
    {
        int vertexCount = getVertexCount();
        
        vertexIndexArray = new int[vertexCount];
        for (int index = 0; index < vertexIndexArray.length; ++index)
        {
            ByteConversions.setUnsignedShortInByteArrayAtPosition(vertexIndexArray[index], newData, offset);
            offset += 2;
        }

        xDisplacementArray = new short[vertexCount];
        for (int index = 0; index < xDisplacementArray.length; ++index)
        {
            ByteConversions.setShortInByteArrayAtPosition(xDisplacementArray[index], newData, offset);
            offset += 2;
        }

        yDisplacementArray = new short[vertexCount];
        for (int index = 0; index < yDisplacementArray.length; ++index)
        {
            ByteConversions.setShortInByteArrayAtPosition(yDisplacementArray[index], newData, offset);
            offset += 2;
        }

        zDisplacementArray = new short[vertexCount];
        for (int index = 0; index < zDisplacementArray.length; ++index)
        {
            ByteConversions.setShortInByteArrayAtPosition(zDisplacementArray[index], newData, offset);
            offset += 2;
        }

        uTextureArray = new short[vertexCount];
        for (int index = 0; index < uTextureArray.length; ++index)
        {
            ByteConversions.setShortInByteArrayAtPosition(uTextureArray[index], newData, offset);
            offset += 2;
        }

        vTextureArray = new short[vertexCount];
        for (int index = 0; index < vTextureArray.length; ++index)
        {
            ByteConversions.setShortInByteArrayAtPosition(vTextureArray[index], newData, offset);
            offset += 2;
        }

        return offset;
    }

    public static int updateData(byte[] newData, int offset, List faceList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(faceList.size(), newData, offset);
        offset += 4;

        for (int faceIndex = 0; faceIndex < faceList.size(); ++faceIndex)
        {
            IndoorFace face = (IndoorFace)faceList.get(faceIndex);
            System.arraycopy(face.getFacetData(), 0, newData, offset, face.getFacetData().length);
            offset += face.getFacetData().length;
        }
        
        for (int faceIndex = 0; faceIndex < faceList.size(); ++faceIndex)
        {
            IndoorFace face = (IndoorFace)faceList.get(faceIndex);
            offset = face.updateVariableData(newData, offset);
        }
        
        for (int faceIndex = 0; faceIndex < faceList.size(); ++faceIndex)
        {
            IndoorFace face = (IndoorFace)faceList.get(faceIndex);
            ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(face.getBitmapName(), newData, offset, FACES_BITMAP_NAME_LENGTH);
            offset += FACES_BITMAP_NAME_LENGTH;
        }
        
        return offset;
    }

    public String getBitmapName()
    {
        return this.faceBitmapName;
    }
    public void setBitmapName(String faceBitmapName)
    {
        this.faceBitmapName = faceBitmapName;
    }
    public int getBitmapNameMaxLength()
    {
        return FACES_BITMAP_NAME_LENGTH;
    }

    public byte[] getFacetData()
    {
        return this.facetData;
    }

    public static int computeDataSize(int gameVersion, byte[] data, int offset, int variableFacetDataSize)
    {
        int faceCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        if (GameVersion.MM6 == gameVersion)
            offset += faceCount * FACE_RECORD_LENGTH_MM6;
        else offset += faceCount * FACE_RECORD_LENGTH_MM7;
        offset += variableFacetDataSize;
        offset += faceCount * FACES_BITMAP_NAME_LENGTH;

        return offset;
    }
    
    public static int getVariableFacetDataSize(List faceList)
    {
        int faceData2Length = 0;
        for (int faceIndex = 0; faceIndex < faceList.size(); ++faceIndex)
        {
            IndoorFace face = (IndoorFace)faceList.get(faceIndex);
            faceData2Length += face.vertexIndexArray.length;
            faceData2Length += face.xDisplacementArray.length;
            faceData2Length += face.yDisplacementArray.length;
            faceData2Length += face.zDisplacementArray.length;
            faceData2Length += face.uTextureArray.length;
            faceData2Length += face.vTextureArray.length;
        }
        
        return faceData2Length;
    }
    
    public static int getRecordSize(List faceList)
    {
        int faceListRecordSize = getVariableFacetDataSize(faceList);
        for (int faceIndex = 0; faceIndex < faceList.size(); ++faceIndex)
        {
            IndoorFace face = (IndoorFace)faceList.get(faceIndex);
            faceListRecordSize += face.getRecordSize();
        }
        
        return faceListRecordSize;
    }
    
    public int getRecordSize()
    {
        return facetData.length
	    	+ vertexIndexArray.length
	    	+ xDisplacementArray.length
	    	+ yDisplacementArray.length
	    	+ zDisplacementArray.length
	    	+ uTextureArray.length
	    	+ vTextureArray.length
        	+ FACES_BITMAP_NAME_LENGTH;
    }

    // Face things to decode

    public static List getOffsetList(int gameVersion)
    {
        List offsetList = new ArrayList();


        if (GameVersion.MM6 == gameVersion)
        {
            offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_X_FIXED_POINT_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "normal X fixed pt"));
            offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_Y_FIXED_POINT_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "normal Y fixed pt"));
            offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_Z_FIXED_POINT_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "normal Z fixed pt"));
            offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_DISTANCE_FIXED_POINT_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "normal distance fixed pt"));

            offsetList.add(new ComparativeTableControl.OffsetData(Z_CALC_1_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "z calc 1"));
            offsetList.add(new ComparativeTableControl.OffsetData(Z_CALC_2_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "z calc 2"));
            offsetList.add(new ComparativeTableControl.OffsetData(Z_CALC_3_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "z calc 3"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTRIBUTES_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "attributes"));

            offsetList.add(new ComparativeTableControl.OffsetData(DATA_INDEX_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Data Index"));
            offsetList.add(new ComparativeTableControl.OffsetData(BITMAP_INDEX_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bitmap Index"));
            offsetList.add(new ComparativeTableControl.OffsetData(ROOM_NUMBER_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Room #"));
            offsetList.add(new ComparativeTableControl.OffsetData(ROOM_NUMBER_BEHIND_FACET_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Room # Behind facet"));

            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MIN_X_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Min X"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MAX_X_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Max X"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MIN_Y_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Min Y"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MAX_Y_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Max Y"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MIN_Z_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Min Z"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MAX_Z_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Max Z"));

            offsetList.add(new ComparativeTableControl.OffsetData(FACET_TYPE_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "facet type"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_VERTEXES_OFFSET_MM6, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "# of vertexes"));

            offsetList.add(new ComparativeTableControl.OffsetData(PADDING_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "padding"));

            offsetList.add(new ComparativeTableControl.OffsetData(VERTEX_INDEX_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset offset"));
            offsetList.add(new ComparativeTableControl.OffsetData(X_DISPLACEMENT_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset offset"));
            offsetList.add(new ComparativeTableControl.OffsetData(Y_DISPLACEMENT_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset offset"));
            offsetList.add(new ComparativeTableControl.OffsetData(Z_DISPLACEMENT_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset offset"));
            offsetList.add(new ComparativeTableControl.OffsetData(U_TEXTURE_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset offset"));
            offsetList.add(new ComparativeTableControl.OffsetData(V_TEXTURE_OFFSET_OFFSET_MM6, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset offset"));
        }
        else
        {
            offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_X_FLOAT_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_FLOAT_DEC, "normal X float"));
            offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_Y_FLOAT_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_FLOAT_DEC, "normal Y float"));
            offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_Z_FLOAT_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_FLOAT_DEC, "normal Z float"));
            offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_DISTANCE_FLOAT_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_FLOAT_DEC, "normal distance fixed pt"));

            offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_X_FIXED_POINT_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "normal X fixed pt"));
            offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_Y_FIXED_POINT_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "normal Y fixed pt"));
            offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_Z_FIXED_POINT_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "normal Z fixed pt"));
            offsetList.add(new ComparativeTableControl.OffsetData(FACET_NORMAL_DISTANCE_FIXED_POINT_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "normal distance fixed pt"));

            offsetList.add(new ComparativeTableControl.OffsetData(Z_CALC_1_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "z calc 1"));
            offsetList.add(new ComparativeTableControl.OffsetData(Z_CALC_2_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "z calc 2"));
            offsetList.add(new ComparativeTableControl.OffsetData(Z_CALC_3_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "z calc 3"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTRIBUTES_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "attributes"));

            offsetList.add(new ComparativeTableControl.OffsetData(DATA_INDEX_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Data Index"));
            offsetList.add(new ComparativeTableControl.OffsetData(BITMAP_INDEX_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bitmap Index"));
            offsetList.add(new ComparativeTableControl.OffsetData(ROOM_NUMBER_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Room #"));
            offsetList.add(new ComparativeTableControl.OffsetData(ROOM_NUMBER_BEHIND_FACET_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Room # Behind facet"));

            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MIN_X_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Min X"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MAX_X_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Max X"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MIN_Y_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Min Y"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MAX_Y_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Max Y"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MIN_Z_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Min Z"));
            offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_BOX_MAX_Z_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Bounding Box Max Z"));

            offsetList.add(new ComparativeTableControl.OffsetData(FACET_TYPE_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "facet type"));
            offsetList.add(new ComparativeTableControl.OffsetData(NUMBER_OF_VERTEXES_OFFSET_MM7, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "# of vertexes"));
            
            offsetList.add(new ComparativeTableControl.OffsetData(PADDING_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "padding"));

            offsetList.add(new ComparativeTableControl.OffsetData(VERTEX_INDEX_OFFSET_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset offset"));
            offsetList.add(new ComparativeTableControl.OffsetData(X_DISPLACEMENT_OFFSET_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset offset"));
            offsetList.add(new ComparativeTableControl.OffsetData(Y_DISPLACEMENT_OFFSET_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset offset"));
            offsetList.add(new ComparativeTableControl.OffsetData(Z_DISPLACEMENT_OFFSET_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset offset"));
            offsetList.add(new ComparativeTableControl.OffsetData(U_TEXTURE_OFFSET_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset offset"));
            offsetList.add(new ComparativeTableControl.OffsetData(V_TEXTURE_OFFSET_OFFSET_MM7, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "offset offset"));
        }



        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List faceList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return faceList.size();
            }

            public byte[] getData(int dataRow)
            {
                IndoorFace face = (IndoorFace)faceList.get(dataRow);
                return face.getFacetData();
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

    public short[] getUTextureArray()
    {
        return this.uTextureArray;
    }
    public int[] getVertexIndexArray()
    {
        return this.vertexIndexArray;
    }
    public short[] getVTextureArray()
    {
        return this.vTextureArray;
    }
    public short[] getXDisplacementArray()
    {
        return this.xDisplacementArray;
    }
    public short[] getYDisplacementArray()
    {
        return this.yDisplacementArray;
    }
    public short[] getZDisplacementArray()
    {
        return this.zDisplacementArray;
    }
}
