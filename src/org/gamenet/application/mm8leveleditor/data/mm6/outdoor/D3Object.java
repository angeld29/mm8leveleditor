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

import org.gamenet.application.mm8leveleditor.data.DataSizeException;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class D3Object
{
    private static final int D3OBJECT_LENGTH = 188; // 188 bytes
    
    private static final int NAME1_OFFSET = 0; // 32 bytes
    private static final int NAME1_MAX_LENGTH = 32;
    
    private static final int NAME2_OFFSET = 32; // 32 bytes
    private static final int NAME2_MAX_LENGTH = 32;
    
    private static final int ATTRIBUTE_OFFSET = 64; // 4 bytes
    
    private static final int REMAINING_DATA_OFFSET = ATTRIBUTE_OFFSET; // 4 bytes

    private static final int VERTEXES_COUNT_OFFSET = 68; // 4 bytes
    private static final int VERTEX_OFFSET_OFFSET = 72; // 4 bytes
    
    private static final int FACES_COUNT_OFFSET = 76; // 4 bytes
    
    private static final int CONVEX_FACETS_COUNT_OFFSET = 80; // 2 bytes
    // 2 bytes for C struct padding?
    
    private static final int FACES_OFFSET_OFFSET = 84; // 4 bytes
    
    private static final int ORDERING_OFFSET_OFFSET = 88; // 4 bytes
    
    private static final int BSPNODE_COUNT_OFFSET = 92; // 4 bytes
    private static final int BSPNODE_OFFSET_OFFSET = 96; // 4 bytes

    private static final int DECORATIONS_COUNT_OFFSET = 100; // 4 bytes
    
    private static final int CENTER_X_OFFSET = 104; // 4 bytes
    private static final int CENTER_Y_OFFSET = 108; // 4 bytes

    private static final int X_OFFSET = 112; // 4 bytes
    private static final int Y_OFFSET = 116; // 4 bytes
    private static final int Z_OFFSET = 120; // 4 bytes

    private static final int MIN_X_OFFSET = 124; // 4 bytes
    private static final int MIN_Y_OFFSET = 128; // 4 bytes
    private static final int MIN_Z_OFFSET = 132; // 4 bytes

    private static final int MAX_X_OFFSET = 136; // 4 bytes
    private static final int MAX_Y_OFFSET = 140; // 4 bytes
    private static final int MAX_Z_OFFSET = 144; // 4 bytes

    private static final int BF_MIN_X_OFFSET = 148; // 4 bytes
    private static final int BF_MIN_Y_OFFSET = 152; // 4 bytes
    private static final int BF_MIN_Z_OFFSET = 156; // 4 bytes

    private static final int BF_MAX_X_OFFSET = 160; // 4 bytes
    private static final int BF_MAX_Y_OFFSET = 164; // 4 bytes
    private static final int BF_MAX_Z_OFFSET = 168; // 4 bytes

    private static final int BOUNDING_CENTER_X_OFFSET = 172; // 4 bytes
    private static final int BOUNDING_CENTER_Y_OFFSET = 176; // 4 bytes
    private static final int BOUNDING_CENTER_Z_OFFSET = 180; // 4 bytes

    private static final int BOUNDING_RADIUS_OFFSET = 184; // 4 bytes
    
    
    private byte remainingData[] = null;
    
    private long offset = 0;
    private long vertexesOffset = 0;
    private long facesOffset = 0;
    private long bspNodeOffset = 0;
    
    
    private String name1 = null;
    private String name2 = null;
    
    private List vertexList = null;
    private List facetList = null;
    private List bspNodeList = null;

    public D3Object()
    {
    }
    
    public D3Object(String name1, String name2)
    {
        super();
        this.setName1(name1);
        this.setName2(name2);
        this.setVertexList(new ArrayList());
        this.setFacetList(new ArrayList());
    }
    
    public int initialize(byte[] dataSrc, int offset)
    {
        this.offset = offset;
        
        int remainingDataLength = D3OBJECT_LENGTH - REMAINING_DATA_OFFSET;
        this.remainingData = new byte[remainingDataLength];

        this.name1 = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(dataSrc, offset + NAME1_OFFSET, NAME1_MAX_LENGTH);
        this.name2 = ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(dataSrc, offset + NAME2_OFFSET, NAME2_MAX_LENGTH);

        System.arraycopy(dataSrc, offset + REMAINING_DATA_OFFSET, this.remainingData, 0, remainingDataLength);
        
        offset += D3OBJECT_LENGTH;

        return offset;
    }
    
    public static int populateObjects(int gameVersion, byte[] dataSrc, int offset, List d3ObjectList)
    {
        int d3ObjectCount = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset);
        offset += 4;

        int vertexCountArray[] = new int[d3ObjectCount];
        int faceCountArray[] = new int[d3ObjectCount];
        int bspNodeCountArray[] = new int[d3ObjectCount];
        for (int d3ObjectIndex = 0; d3ObjectIndex < d3ObjectCount; ++d3ObjectIndex)
        {
            vertexCountArray[d3ObjectIndex] = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset + VERTEXES_COUNT_OFFSET);
            faceCountArray[d3ObjectIndex] = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset + FACES_COUNT_OFFSET);
            bspNodeCountArray[d3ObjectIndex] = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset + BSPNODE_COUNT_OFFSET);
            
            D3Object d3Object = new D3Object();
            offset = d3Object.initialize(dataSrc, offset);
            d3ObjectList.add(d3Object);
        }

        for (int index = 0; index < d3ObjectList.size(); ++index)
        {
            D3Object d3Object = (D3Object)d3ObjectList.get(index);
            
            int vertexCount = vertexCountArray[index];
            d3Object.vertexesOffset = offset;
            List vertexList = new ArrayList();
            offset = IntVertex.populateObjects(dataSrc, offset, vertexList, vertexCount);
            
            int faceCount = faceCountArray[index];
            d3Object.facesOffset = offset;
            List faceList = new ArrayList();
            offset = OutdoorFace.populateObjects(dataSrc, offset, faceList, faceCount);
            
            // IMPLEMENT: THIS IS BROKEN -- need to break up function above and move it between ordering and textures
//            if (7 == GAME_VERSION)
//            {
//	            int bspNodeCount = bspNodeCountArray[index];
//	            d3Object.bspNodeOffset = offset;
//	            List bspNodeList = new ArrayList();
//	            offset = BSPNode.populateObjects(dataSrc, offset, bspNodeList, bspNodeCount);
//            }
            
            d3Object.setVertexList(vertexList);
            d3Object.setFacetList(faceList);
            // IMPLEMENT: d3Object.setBSPNodeList(bspNodeList);
        }
        
        return offset;
    }
    
    /**
     * @param newData
     * @param offset
     * @param d3ObjectArray
     * @return
     */
    public static int updateData(byte[] newData, int offset, List d3ObjectList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(d3ObjectList.size(), newData, offset);
        offset += 4;

        for (int d3ObjectIndex = 0; d3ObjectIndex < d3ObjectList.size(); ++d3ObjectIndex)
        {
            D3Object d3Object = (D3Object)d3ObjectList.get(d3ObjectIndex);

            ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(d3Object.getName1(), newData, offset, NAME1_MAX_LENGTH);
            offset += NAME1_MAX_LENGTH;
            ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(d3Object.getName2(), newData, offset, NAME2_MAX_LENGTH);
            offset += NAME2_MAX_LENGTH;

            // stuff we don't understand yet
            System.arraycopy(d3Object.getRemainingData(), 0, newData, offset, d3Object.getRemainingData().length);
            offset += d3Object.getRemainingData().length;

            // these will be overwritten by remainingData above otherwise
//            ByteConversions.setIntegerInByteArrayAtPosition(d3Object.getFaceArray().length, newData, FACES_COUNT_OFFSET);
//            ByteConversions.setIntegerInByteArrayAtPosition(d3Object.getVertexArray().length, newData, VERTEXES_COUNT_OFFSET);
        }

        for (int index = 0; index < d3ObjectList.size(); ++index)
        {
            D3Object d3Object = (D3Object)d3ObjectList.get(index);
            
            offset = IntVertex.updateData(newData, offset, d3Object.getVertexList());
            offset = OutdoorFace.updateData(newData, offset, d3Object.getFacetList());
        }
        
        return offset;
    }
    
    public String getName1()
    {
        return name1;
    }
    public void setName1(String name1)
    {
        this.name1 = name1;
    }

    public String getName2()
    {
        return name2;
    }
    public void setName2(String name2)
    {
        this.name2 = name2;
    }
    
    public byte[] getRemainingData()
    {
        return remainingData;
    }
    
    public int getRemainingDataOffset()
    {
        return REMAINING_DATA_OFFSET;
    }
    
    public List getVertexList()
    {
        return this.vertexList;
    }
    public void setVertexList(List vertexList)
    {
        this.vertexList = vertexList;
    }
    

    public List getFacetList()
    {
        return this.facetList;
    }
    
    public void setFacetList(List facesList)
    {
        this.facetList = facesList;
    }
    
    public long getOffset()
    {
        return this.offset;
    }
    public long getVertexesOffset()
    {
        return this.vertexesOffset;
    }
    public long getFacesOffset()
    {
        return this.facesOffset;
    }

    public int getXMin()
    {
        int min = Integer.MAX_VALUE;
        for (int vertexIndex = 0; vertexIndex < vertexList.size(); ++vertexIndex)
        {
            IntVertex vertex = (IntVertex)vertexList.get(vertexIndex);
            int value = vertex.getX();
    		if (min > value)  min = value;
        }
        
        return min;
    }
    

    public void setXMin(int newMin)
    {
        int adjustment = newMin - getXMin();
        for (int vertexIndex = 0; vertexIndex < vertexList.size(); ++vertexIndex)
        {
            IntVertex vertex = (IntVertex)vertexList.get(vertexIndex);
            vertex.setX(vertex.getX() + adjustment);
        }
//        ByteConversions.setIntegerInByteArrayAtPosition(ByteConversions.getIntegerInByteArrayAtPosition(remainingData, UNK_X_OFFSET - REMAINING_DATA_OFFSET) + adjustment, remainingData, UNK_X_OFFSET - REMAINING_DATA_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(ByteConversions.getIntegerInByteArrayAtPosition(remainingData, MIN_X_OFFSET - REMAINING_DATA_OFFSET) + adjustment, remainingData, MIN_X_OFFSET - REMAINING_DATA_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(ByteConversions.getIntegerInByteArrayAtPosition(remainingData, MAX_X_OFFSET - REMAINING_DATA_OFFSET) + adjustment, remainingData, MAX_X_OFFSET - REMAINING_DATA_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(ByteConversions.getIntegerInByteArrayAtPosition(remainingData, CENTER_X_OFFSET - REMAINING_DATA_OFFSET) + adjustment, remainingData, CENTER_X_OFFSET - REMAINING_DATA_OFFSET);
    }
    
    public int getXMax()
    {
        int max = Integer.MIN_VALUE;
        for (int vertexIndex = 0; vertexIndex < vertexList.size(); ++vertexIndex)
        {
            IntVertex vertex = (IntVertex)vertexList.get(vertexIndex);
            int value = vertex.getX();
    		if (max < value)  max = value;
        }
        
        return max;
    }
    
    public int getYMin()
    {
        int min = Integer.MAX_VALUE;
        for (int vertexIndex = 0; vertexIndex < vertexList.size(); ++vertexIndex)
        {
            IntVertex vertex = (IntVertex)vertexList.get(vertexIndex);
            int value = vertex.getY();
    		if (min > value)  min = value;
        }
        
        return min;
    }
    
    public void setYMin(int newMin)
    {
        int adjustment = newMin - getYMin();
        for (int vertexIndex = 0; vertexIndex < vertexList.size(); ++vertexIndex)
        {
            IntVertex vertex = (IntVertex)vertexList.get(vertexIndex);
            vertex.setY(vertex.getY() + adjustment);
        }
//        ByteConversions.setIntegerInByteArrayAtPosition(ByteConversions.getIntegerInByteArrayAtPosition(remainingData, UNK_Y_OFFSET - REMAINING_DATA_OFFSET) + adjustment, remainingData, UNK_Y_OFFSET - REMAINING_DATA_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(ByteConversions.getIntegerInByteArrayAtPosition(remainingData, MIN_Y_OFFSET - REMAINING_DATA_OFFSET) + adjustment, remainingData, MIN_Y_OFFSET - REMAINING_DATA_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(ByteConversions.getIntegerInByteArrayAtPosition(remainingData, MAX_Y_OFFSET - REMAINING_DATA_OFFSET) + adjustment, remainingData, MAX_Y_OFFSET - REMAINING_DATA_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(ByteConversions.getIntegerInByteArrayAtPosition(remainingData, CENTER_Y_OFFSET - REMAINING_DATA_OFFSET) + adjustment, remainingData, CENTER_Y_OFFSET - REMAINING_DATA_OFFSET);
    }
    
    public int getYMax()
    {
        int max = Integer.MIN_VALUE;
        for (int vertexIndex = 0; vertexIndex < vertexList.size(); ++vertexIndex)
        {
            IntVertex vertex = (IntVertex)vertexList.get(vertexIndex);
            int value = vertex.getY();
    		if (max < value)  max = value;
        }
        
        return max;
    }
    
    public int getZMin()
    {
        int min = Integer.MAX_VALUE;
        for (int vertexIndex = 0; vertexIndex < vertexList.size(); ++vertexIndex)
        {
            IntVertex vertex = (IntVertex)vertexList.get(vertexIndex);
            int value = vertex.getZ();
    		if (min > value)  min = value;
        }
        
        return min;
    }
    
    public void setZMin(int newMin)
    {
        int adjustment = newMin - getZMin();
        for (int vertexIndex = 0; vertexIndex < vertexList.size(); ++vertexIndex)
        {
            IntVertex vertex = (IntVertex)vertexList.get(vertexIndex);
            vertex.setZ(vertex.getZ() + adjustment);
        }
//        ByteConversions.setIntegerInByteArrayAtPosition(ByteConversions.getIntegerInByteArrayAtPosition(remainingData, UNK_Z_OFFSET - REMAINING_DATA_OFFSET) + adjustment, remainingData, UNK_Z_OFFSET - REMAINING_DATA_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(ByteConversions.getIntegerInByteArrayAtPosition(remainingData, MIN_Z_OFFSET - REMAINING_DATA_OFFSET) + adjustment, remainingData, MIN_Z_OFFSET - REMAINING_DATA_OFFSET);
        ByteConversions.setIntegerInByteArrayAtPosition(ByteConversions.getIntegerInByteArrayAtPosition(remainingData, MAX_Z_OFFSET - REMAINING_DATA_OFFSET) + adjustment, remainingData, MAX_Z_OFFSET - REMAINING_DATA_OFFSET);
//        ByteConversions.setIntegerInByteArrayAtPosition(ByteConversions.getIntegerInByteArrayAtPosition(remainingData, CENTER_Z_OFFSET - REMAINING_DATA_OFFSET) + adjustment, remainingData, CENTER_Z_OFFSET - REMAINING_DATA_OFFSET);
    }
    
    public int getZMax()
    {
        int max = Integer.MIN_VALUE;
        for (int vertexIndex = 0; vertexIndex < vertexList.size(); ++vertexIndex)
        {
            IntVertex vertex = (IntVertex)vertexList.get(vertexIndex);
            int value = vertex.getZ();
    		if (max < value)  max = value;
        }
        
        return max;
    }
    
    public static int computeDataSize(int gameVersion, byte[] data, int offset)
    {
        int d3ObjectCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        if (d3ObjectCount > data.length)
            throw new DataSizeException("d3ObjectCount=" + String.valueOf(d3ObjectCount) + " at offset=" + String.valueOf(offset));
        
        int vertexCountArray[] = new int[d3ObjectCount];
        int faceCountArray[] = new int[d3ObjectCount];
        for (int d3ObjectIndex = 0; d3ObjectIndex < d3ObjectCount; ++d3ObjectIndex)
        {
            vertexCountArray[d3ObjectIndex] = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + VERTEXES_COUNT_OFFSET);
            faceCountArray[d3ObjectIndex] = ByteConversions.getIntegerInByteArrayAtPosition(data, offset + FACES_COUNT_OFFSET);
            
            offset += D3OBJECT_LENGTH;
        }

        for (int index = 0; index < d3ObjectCount; ++index)
        {
            offset += vertexCountArray[index] * IntVertex.getRecordSize();
        }

        for (int index = 0; index < d3ObjectCount; ++index)
        {
            offset += faceCountArray[index] * OutdoorFace.getRecordSize();
        }
        
        return offset;
    }
    
    public int getRecordSize(int gameVersion)
    {
        return D3OBJECT_LENGTH + (vertexList.size() * IntVertex.getRecordSize()) + (facetList.size() * OutdoorFace.getRecordSize());
    }

    // Unknown things to decode

    public static List getOffsetList()
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(ATTRIBUTE_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Attributes"));
        
        offsetList.add(new ComparativeTableControl.OffsetData(VERTEXES_COUNT_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "# of vertexes"));

        offsetList.add(new ComparativeTableControl.OffsetData(FACES_COUNT_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "# of Facets"));
        
        offsetList.add(new ComparativeTableControl.OffsetData(CENTER_X_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "center X"));
        offsetList.add(new ComparativeTableControl.OffsetData(CENTER_Y_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "center Y"));

        offsetList.add(new ComparativeTableControl.OffsetData(X_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "X"));
        offsetList.add(new ComparativeTableControl.OffsetData(Y_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(Z_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Z"));

        offsetList.add(new ComparativeTableControl.OffsetData(MIN_X_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Bounding MIN X"));
        offsetList.add(new ComparativeTableControl.OffsetData(MIN_Y_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Bounding MIN Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(MIN_Z_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Bounding MIN Z"));

        offsetList.add(new ComparativeTableControl.OffsetData(MAX_X_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Bounding MAX X"));
        offsetList.add(new ComparativeTableControl.OffsetData(MAX_Y_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Bounding MAX Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(MAX_Z_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Bounding MAX Z"));

        offsetList.add(new ComparativeTableControl.OffsetData(BF_MIN_X_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "BF_MIN_X"));
        offsetList.add(new ComparativeTableControl.OffsetData(BF_MIN_Y_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "BF_MIN_Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(BF_MIN_Z_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "BF_MIN_Z"));

        offsetList.add(new ComparativeTableControl.OffsetData(BF_MAX_X_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "BF_MAX_X"));
        offsetList.add(new ComparativeTableControl.OffsetData(BF_MAX_Y_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "BF_MAX_Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(BF_MAX_Z_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "BF_MAX_Z"));

        offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_CENTER_X_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Bounding center X"));
        offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_CENTER_Y_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Bounding center Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_CENTER_Z_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Bounding center Z"));

        offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_RADIUS_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Bounding radius"));
        
        // Suspected as unused
        offsetList.add(new ComparativeTableControl.OffsetData(CONVEX_FACETS_COUNT_OFFSET+2, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Unused"));
        offsetList.add(new ComparativeTableControl.OffsetData(VERTEX_OFFSET_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "VERTEX_OFFSET"));
        offsetList.add(new ComparativeTableControl.OffsetData(FACES_OFFSET_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "FACES_OFFSET"));
        offsetList.add(new ComparativeTableControl.OffsetData(ORDERING_OFFSET_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "ORDERING_OFFSET"));
        offsetList.add(new ComparativeTableControl.OffsetData(BSPNODE_OFFSET_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "BSPNODE_OFFSET"));
        offsetList.add(new ComparativeTableControl.OffsetData(BSPNODE_COUNT_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "# of BSP Nodes"));
        offsetList.add(new ComparativeTableControl.OffsetData(DECORATIONS_COUNT_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "# of Decorators"));
        offsetList.add(new ComparativeTableControl.OffsetData(CONVEX_FACETS_COUNT_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of Convex Facets"));
        
        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List d3ObjectsList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return d3ObjectsList.size();
            }

            public byte[] getData(int dataRow)
            {
                D3Object d3Object = (D3Object)d3ObjectsList.get(dataRow);
                return d3Object.getRemainingData();
            }

            public int getAdjustedDataRowIndex(int dataRow)
            {
                return dataRow;
            }
            
            public String getIdentifier(int dataRow)
            {
                D3Object d3Object = (D3Object)d3ObjectsList.get(dataRow);
                return d3Object.getName1();
            }

            public int getOffset(int dataRow)
            {
                D3Object d3Object = (D3Object)d3ObjectsList.get(dataRow);
                return d3Object.getRemainingDataOffset();
            }
        };
    }
}
