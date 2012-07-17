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

import java.util.List;

import org.gamenet.application.mm8leveleditor.data.mm6.Vertex;
import org.gamenet.util.ByteConversions;

public class IntVertex implements Vertex
{
    private static final int OUTDOOR_VERTEX_RECORD_SIZE = 12;

    // 0, 0, 0 is center
    // short coords from -0x58 (-88) to 0x58 (88) are reachable, 
    private static final int EAST_WEST_OFFSET = 0; // four signed bytes, neg is west
    private static final int NORTH_SOUTH_OFFSET = 4; // four signed bytes, neg is south
    private static final int HEIGHT_OFFSET = 8; // four signed bytes

    private int x = 0; // east-west
    private int y = 0; // north-south
    private int z = 0; // height
    private long offset = 0;
    
    public IntVertex(int x, int y, int z, long offset)
    {
        super();
        
        this.x = x;
        this.y = y;
        this.z = z;
        this.offset = offset;
    }

    public IntVertex(int x, int y, int z)
    {
        super();
        
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }

    public static int populateObjects(byte[] dataSrc, int offset, List vertexList, int vertexCount)
    {
        for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
        {
            int vertexOffset = offset;

            int x = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset);
            offset += 4;
            int y = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset);
            offset += 4;
            int z = ByteConversions.getIntegerInByteArrayAtPosition(dataSrc, offset);
            offset += 4;
            
            IntVertex vertex = new IntVertex(x, y, z, vertexOffset);
            vertexList.add(vertex);
        }
        
        return offset;
    }

    public static int updateData(byte[] newData, int offset, List vertexList)
    {
        for (int vertexIndex = 0; vertexIndex < vertexList.size(); ++vertexIndex)
        {
            IntVertex vertex = (IntVertex)vertexList.get(vertexIndex);

            ByteConversions.setIntegerInByteArrayAtPosition(vertex.getX(), newData, offset);
            offset += 4;
            ByteConversions.setIntegerInByteArrayAtPosition(vertex.getY(), newData, offset);
            offset += 4;
            ByteConversions.setIntegerInByteArrayAtPosition(vertex.getZ(), newData, offset);
            offset += 4;
        }
        
        return offset;
    }
    
    public int getX()
    {
        return this.x;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    
    public int getY()
    {
        return this.y;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getZ()
    {
        return this.z;
    }
    public void setZ(int z)
    {
        this.z = z;
    }
    
    public long getOffset()
    {
        return this.offset;
    }
    
    public static int getRecordSize()
    {
        return OUTDOOR_VERTEX_RECORD_SIZE;
    }
}
