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

import java.util.List;

import org.gamenet.util.ByteConversions;

public class BSPNode
{
    private short frontNode;
    private short backNode;
    private short coplanarOffset;
    private short coplanarSize;
    
    public BSPNode()
    {
    }

    public BSPNode(short frontNode, short backNode, short coplanarOffset, short coplanarSize)
    {
        this.frontNode = frontNode;
        this.backNode = backNode;
        this.coplanarOffset = coplanarOffset;
        this.coplanarSize = coplanarSize;
    }

    public int initialize(byte dataSrc[], int offset)
    {
        this.frontNode = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        this.backNode = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        this.coplanarOffset = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;
        this.coplanarSize = ByteConversions.getShortInByteArrayAtPosition(dataSrc, offset);
        offset += 2;

        return offset;
    }

    public static int populateObjects(byte[] data, int offset, List bspNodeList)
    {
        int bspNodeCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int bspNodeIndex = 0; bspNodeIndex < bspNodeCount; ++bspNodeIndex)
        {
            BSPNode bspNode = new BSPNode();
            bspNodeList.add(bspNode);
            offset = bspNode.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List bspNodeList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(bspNodeList.size(), newData, offset);
        offset += 4;

        for (int bspNodeIndex = 0; bspNodeIndex < bspNodeList.size(); ++bspNodeIndex)
        {
            BSPNode bspNode = (BSPNode)bspNodeList.get(bspNodeIndex);
            ByteConversions.setShortInByteArrayAtPosition(bspNode.getFrontNode(), newData, offset);
            offset += 2;
            ByteConversions.setShortInByteArrayAtPosition(bspNode.getBackNode(), newData, offset);
            offset += 2;
            ByteConversions.setShortInByteArrayAtPosition(bspNode.getCoplanarOffset(), newData, offset);
            offset += 2;
            ByteConversions.setShortInByteArrayAtPosition(bspNode.getCoplanarSize(), newData, offset);
            offset += 2;
        }
        
        return offset;
    }
    
    public short getBackNode()
    {
        return this.backNode;
    }
    public void setBackNode(short backNode)
    {
        this.backNode = backNode;
    }
    public short getCoplanarOffset()
    {
        return this.coplanarOffset;
    }
    public void setCoplanarOffset(short coplanarOffset)
    {
        this.coplanarOffset = coplanarOffset;
    }
    public short getCoplanarSize()
    {
        return this.coplanarSize;
    }
    public void setCoplanarSize(short coplanarSize)
    {
        this.coplanarSize = coplanarSize;
    }
    public short getFrontNode()
    {
        return this.frontNode;
    }
    public void setFrontNode(short frontNode)
    {
        this.frontNode = frontNode;
    }

    public static int getRecordSize()
    {
        return 2 + 2 + 2 + 2;
    }
}
