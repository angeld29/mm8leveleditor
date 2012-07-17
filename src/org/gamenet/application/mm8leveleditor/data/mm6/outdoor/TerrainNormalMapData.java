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

import org.gamenet.util.ByteConversions;

public class TerrainNormalMapData
{
    private float terranNormalDistanceMap0[][] = null; // mm7/mm8
    private float terranNormalDistanceMap1[][] = null; // mm7/mm8
    
    private int terranNormalHandleMap0[][] = null; // unsigned shorts, mm7/mm8
    private int terranNormalHandleMap1[][] = null; // unsigned shorts, mm7/mm8
    private List terrainNormalVertexList = null; // mm7/mm8
    
    public TerrainNormalMapData()
    {
        super();
    }

    public int initialize(byte[] data, int offset, int MAP_WIDTH, int MAP_HEIGHT)
    {
        int normalsCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        // IMPLEMENT: probably should be a float type
        terranNormalDistanceMap0 = new float[MAP_HEIGHT][MAP_WIDTH];
        terranNormalDistanceMap1 = new float[MAP_HEIGHT][MAP_WIDTH];
        for (int heightIndex = 0; heightIndex < terranNormalDistanceMap0.length; heightIndex++)
        {
            for (int widthIndex = 0; widthIndex < terranNormalDistanceMap0[heightIndex].length; widthIndex++)
            {
                terranNormalDistanceMap0[heightIndex][widthIndex] = ByteConversions.getFloatInByteArrayAtPosition(data, offset);
                offset += 4;
                terranNormalDistanceMap1[heightIndex][widthIndex] = ByteConversions.getFloatInByteArrayAtPosition(data, offset);
                offset += 4;
            }
        }
        
        terranNormalHandleMap0 = new int[MAP_HEIGHT][MAP_WIDTH];
        terranNormalHandleMap1 = new int[MAP_HEIGHT][MAP_WIDTH];
        for (int heightIndex = 0; heightIndex < terranNormalHandleMap0.length; heightIndex++)
        {
            for (int widthIndex = 0; widthIndex < terranNormalHandleMap0[heightIndex].length; widthIndex++)
            {
                terranNormalHandleMap0[heightIndex][widthIndex] = ByteConversions.getUnsignedShortInByteArrayAtPosition(data, offset);
                offset += 2;
                terranNormalHandleMap1[heightIndex][widthIndex] = ByteConversions.getUnsignedShortInByteArrayAtPosition(data, offset);
                offset += 2;
            }
        }
        
        terrainNormalVertexList = new ArrayList();
        offset = IntVertex.populateObjects(data, offset, terrainNormalVertexList, normalsCount);
        
        return offset;
    }

    public int updateData(byte[] newData, int offset)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(terrainNormalVertexList.size(), newData, offset);
        offset += 4;

        // IMPLEMENT: probably should be a float type
        for (int heightIndex = 0; heightIndex < terranNormalDistanceMap0.length; heightIndex++)
        {
            for (int widthIndex = 0; widthIndex < terranNormalDistanceMap0[heightIndex].length; widthIndex++)
            {
                ByteConversions.setFloatInByteArrayAtPosition(terranNormalDistanceMap0[heightIndex][widthIndex], newData, offset);
                offset += 4;
                ByteConversions.setFloatInByteArrayAtPosition(terranNormalDistanceMap1[heightIndex][widthIndex], newData, offset);
                offset += 4;
            }
        }
        
        for (int heightIndex = 0; heightIndex < terranNormalHandleMap0.length; heightIndex++)
        {
            for (int widthIndex = 0; widthIndex < terranNormalHandleMap0[heightIndex].length; widthIndex++)
            {
                ByteConversions.setShortInByteArrayAtPosition((short)terranNormalHandleMap0[heightIndex][widthIndex], newData, offset);
                offset += 2;
                ByteConversions.setShortInByteArrayAtPosition((short)terranNormalHandleMap1[heightIndex][widthIndex], newData, offset);
                offset += 2;
            }
        }
        
        return offset;
    }

    public int getRecordSize()
    {
        int newDataSize = 0;
        newDataSize += 4;

        newDataSize += terranNormalDistanceMap0.length * terranNormalDistanceMap0[0].length * 4;
        newDataSize += terranNormalDistanceMap1.length * terranNormalDistanceMap1[0].length * 4;
        newDataSize += terranNormalHandleMap0.length * terranNormalHandleMap0[0].length * 2;
        newDataSize += terranNormalHandleMap1.length * terranNormalHandleMap1[0].length * 2;
        
        newDataSize += terrainNormalVertexList.size() * IntVertex.getRecordSize();
        
        return newDataSize;
    }

    public List getTerrainNormalVertexList()
    {
        return this.terrainNormalVertexList;
    }
    public float[][] getTerranNormalDistanceMap0()
    {
        return this.terranNormalDistanceMap0;
    }
    public float[][] getTerranNormalDistanceMap1()
    {
        return this.terranNormalDistanceMap1;
    }
    public int[][] getTerranNormalHandleMap0()
    {
        return this.terranNormalHandleMap0;
    }
    public int[][] getTerranNormalHandleMap1()
    {
        return this.terranNormalHandleMap1;
    }
}
