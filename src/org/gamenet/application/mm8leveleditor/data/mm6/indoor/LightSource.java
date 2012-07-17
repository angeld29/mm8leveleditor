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

public class LightSource
{
    private static final int LIGHT_SOURCE_RECORD_LENGTH_MM6 = 12;
    private static final int LIGHT_SOURCE_RECORD_LENGTH_MM7 = 16;
    private static final int LIGHT_SOURCE_RECORD_LENGTH_MM8 = 20;

    private static final int X_OFFSET = 0; // 2 bytes
    private static final int Y_OFFSET = 2; // 2 bytes
    private static final int Z_OFFSET = 4; // 2 bytes
    private static final int RADIUS_OFFSET = 6; // 2 bytes
    
    private static final int ATTRIBUTES_OFFSET_MM6 = 8; // 2 bytes
    private static final int AMBIENT_OFFSET_MM6 = 10; // 2 bytes
    
    
    // I think the ordering for these is wrong....
    
    private static final int RED_OFFSET = 8; // 1 byte
    private static final int GREEN_OFFSET = 9; // 1 byte
    private static final int BLUE_OFFSET = 10; // 1 byte
    
    private static final int LIGHT_TYPE_MODULATE = 1; // color multiply - natural effect, mutex ADD
    private static final int LIGHT_TYPE_ADDITIVE = 2; // color add -- specular effect, mutex MOD
    private static final int LIGHT_TYPE_POINT = 4; // uses facing, mutex OMNI
    private static final int LIGHT_TYPE_OMNI = 8; // no facing, mutex POINT
    private static final int TYPE_OFFSET = 11; // 1 byte
    
    private static final int ATTRIBUTES_OFFSET_MM7 = 12; // 2 bytes
    private static final int AMBIENT_OFFSET_MM7 = 14; // 2 bytes
    
    private static final int EVENT_ID_OFFSET = 16; // 4 bytes // mm8

    // 2 byte coords, then light source strengh at last byte (two bytes?)
    // sprites can have independent light sources

    private int gameVersion = 0;
    private byte lightSourceData[] = null;

    public LightSource(int gameVersion)
    {
        super();
        this.gameVersion = gameVersion;
        
        if (GameVersion.MM6 == gameVersion)
            this.lightSourceData = new byte[LIGHT_SOURCE_RECORD_LENGTH_MM6];
        else if (GameVersion.MM7 == gameVersion)
            this.lightSourceData = new byte[LIGHT_SOURCE_RECORD_LENGTH_MM7];
        else this.lightSourceData = new byte[LIGHT_SOURCE_RECORD_LENGTH_MM8];
    }

    public LightSource(int gameVersion, int x, int y, int z, int ambientLevel)
    {
        this(gameVersion);
        
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setAmbientLevel(ambientLevel);
    }

    public int initialize(byte dataSrc[], int offset)
    {
        System.arraycopy(dataSrc, offset, this.lightSourceData, 0, this.lightSourceData.length);
        offset += this.lightSourceData.length;
        
        return offset;
    }

    public static int populateObjects(int gameVersion, byte[] data, int offset, List lightSourceList)
    {
        int lightSourceCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        for (int lightSourceIndex = 0; lightSourceIndex < lightSourceCount; ++lightSourceIndex)
        {
            LightSource lightSource = new LightSource(gameVersion);
            lightSourceList.add(lightSource);
            offset = lightSource.initialize(data, offset);
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List lightSourceList)
    {
        ByteConversions.setIntegerInByteArrayAtPosition(lightSourceList.size(), newData, offset);
        offset += 4;

        for (int lightSourceIndex = 0; lightSourceIndex < lightSourceList.size(); ++lightSourceIndex)
        {
            LightSource lightSource = (LightSource)lightSourceList.get(lightSourceIndex);
            System.arraycopy(lightSource.getLightSourceData(), 0, newData, offset, lightSource.getLightSourceData().length);
            offset += lightSource.getLightSourceData().length;
        }
        
        return offset;
    }

    public int getX()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getLightSourceData(), X_OFFSET);
    }
    
    public void setX(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getLightSourceData(), X_OFFSET);
    }
    
    public int getY()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getLightSourceData(), Y_OFFSET);
    }
    
    public void setY(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getLightSourceData(), Y_OFFSET);
    }
    
    public int getZ()
    {
        return ByteConversions.getShortInByteArrayAtPosition(getLightSourceData(), Z_OFFSET);
    }
    
    public void setZ(int value)
    {
        ByteConversions.setShortInByteArrayAtPosition((short)value, getLightSourceData(), Z_OFFSET);
    }
    
    public int getAmbientLevel()
    {
        if (GameVersion.MM6 == gameVersion)
            return ByteConversions.getShortInByteArrayAtPosition(getLightSourceData(), AMBIENT_OFFSET_MM6);
        else return ByteConversions.getShortInByteArrayAtPosition(getLightSourceData(), AMBIENT_OFFSET_MM7);
    }
    
    public void setAmbientLevel(int value)
    {
        if (GameVersion.MM6 == gameVersion)
            ByteConversions.setShortInByteArrayAtPosition((short)value, getLightSourceData(), AMBIENT_OFFSET_MM6);
        else ByteConversions.setShortInByteArrayAtPosition((short)value, getLightSourceData(), AMBIENT_OFFSET_MM7);
        
    }
    
    public byte[] getLightSourceData()
    {
        return this.lightSourceData;
    }

    public static int getRecordSize(int gameVersion)
    {
        if (GameVersion.MM6 == gameVersion)
            return LIGHT_SOURCE_RECORD_LENGTH_MM6;
        else if (GameVersion.MM7 == gameVersion)
            return LIGHT_SOURCE_RECORD_LENGTH_MM7;
        else return LIGHT_SOURCE_RECORD_LENGTH_MM8;
    }

    // Unknown things to decode

    public static List getOffsetList(int gameVersion)
    {
        List offsetList = new ArrayList();
        offsetList.add(new ComparativeTableControl.OffsetData(X_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "X"));
        offsetList.add(new ComparativeTableControl.OffsetData(Y_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Y"));
        offsetList.add(new ComparativeTableControl.OffsetData(Z_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Z"));
        offsetList.add(new ComparativeTableControl.OffsetData(RADIUS_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Radius"));
        if (GameVersion.MM6 == gameVersion)
        {
            offsetList.add(new ComparativeTableControl.OffsetData(ATTRIBUTES_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Attributes"));
            offsetList.add(new ComparativeTableControl.OffsetData(AMBIENT_OFFSET_MM6, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Brightness"));
        }
        else
        {
            offsetList.add(new ComparativeTableControl.OffsetData(RED_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "Red"));
            offsetList.add(new ComparativeTableControl.OffsetData(GREEN_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "Green"));
            offsetList.add(new ComparativeTableControl.OffsetData(BLUE_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "Blue"));
            offsetList.add(new ComparativeTableControl.OffsetData(TYPE_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, "Type"));
            offsetList.add(new ComparativeTableControl.OffsetData(ATTRIBUTES_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Attributes"));
            offsetList.add(new ComparativeTableControl.OffsetData(AMBIENT_OFFSET_MM7, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "Brightness"));
        }

        if ((GameVersion.MM6 != gameVersion) && (GameVersion.MM7 != gameVersion))
        {
            offsetList.add(new ComparativeTableControl.OffsetData(EVENT_ID_OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "Event ID #"));
        }

        return offsetList;
    }
    
    public static ComparativeTableControl.DataSource getComparativeDataSource(final List lightSourceList)
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return lightSourceList.size();
            }

            public byte[] getData(int dataRow)
            {
                LightSource lightSource = (LightSource)lightSourceList.get(dataRow);
                return lightSource.getLightSourceData();
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
