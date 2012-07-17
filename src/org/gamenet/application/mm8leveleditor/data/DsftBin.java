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


package org.gamenet.application.mm8leveleditor.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.util.ByteConversions;

public class DsftBin
{
    // Sprite Frame Table
    private int offset;

    private List dsftBinSpriteSetList = null;
    private List dsftBinIndexList = null;
    private Class dsftBinSpriteClass;

    public DsftBin(Class dsftBinSpriteClass)
    {
        super();
        
        this.dsftBinSpriteClass = dsftBinSpriteClass;
    }

    public int initialize(byte[] data, int offset)
    {
        this.offset = offset;
        
        dsftBinSpriteSetList = new ArrayList();
        dsftBinIndexList = new ArrayList();

        int entryCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        int indexCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        offset = DsftBinSpriteSet.populateObjects(data, offset, dsftBinSpriteSetList, entryCount, dsftBinSpriteClass);

        for (int indexPos = 0; indexPos < indexCount; ++indexPos)
        {
            int index = ByteConversions.getShortInByteArrayAtPosition(data, offset);
            offset += 2;
            
            dsftBinIndexList.add(new Integer(index));
        }
        
        return offset;
    }
    
    public int updateData(byte[] newData, int offset)
    {
        int dsftBinSpriteCount = 0;
        for (int dsftBinIndex = 0; dsftBinIndex < dsftBinSpriteSetList.size();  ++dsftBinIndex)
        {
            DsftBinSpriteSet dsftBinSpriteSet = (DsftBinSpriteSet)dsftBinSpriteSetList.get(dsftBinIndex);
            DsftBinSprite dsftBinSpriteArray[] = dsftBinSpriteSet.getDsftBinSpriteArray();
            dsftBinSpriteCount += dsftBinSpriteArray.length;
        }
        
        ByteConversions.setIntegerInByteArrayAtPosition(dsftBinSpriteCount, newData, offset);
        offset += 4;

        ByteConversions.setIntegerInByteArrayAtPosition(dsftBinIndexList.size(), newData, offset);
        offset += 4;

        offset = DsftBinSpriteSet.updateData(newData, offset, dsftBinSpriteSetList);
        
        for (int indexPos = 0; indexPos < dsftBinIndexList.size(); ++indexPos)
        {
            Number indexNumber = (Number)dsftBinIndexList.get(indexPos);
            ByteConversions.setShortInByteArrayAtPosition(indexNumber.shortValue(), newData, offset);
            offset += 2;
        }
        
        return offset;
    }
    
    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset, Class dsftBinSpriteClass)
    {
        int entryCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        int indexCount = ByteConversions.getIntegerInByteArrayAtPosition(data, offset);
        offset += 4;

        Number recordSizeNumber = null;
        try
        {
            Method getStaticRecordSizeMethod = dsftBinSpriteClass.getMethod("getStaticRecordSize", null);
            recordSizeNumber = (Number)getStaticRecordSizeMethod.invoke(dsftBinSpriteClass, null);
        }
        catch (SecurityException exception)
        {
            throw new RuntimeException(exception);
        }
        catch (NoSuchMethodException exception)
        {
            throw new RuntimeException(exception);
        }
        catch (IllegalArgumentException exception)
        {
            throw new RuntimeException(exception);
        }
        catch (IllegalAccessException exception)
        {
            throw new RuntimeException(exception);
        }
        catch (InvocationTargetException exception)
        {
            throw new RuntimeException(exception);
        }
        
        offset += entryCount * recordSizeNumber.intValue();
        offset += indexCount * 2;
        
        return (offset == expectedNewOffset);
    }
    
    public int getOffset()
    {
        return this.offset;
    }		
    
    public List getDsftBinSpriteSetList()
    {
        return this.dsftBinSpriteSetList;
    }
    public List getDsftBinIndexList()
    {
        return this.dsftBinIndexList;
    }
    
    public int getRecordSize()
    {
        int dsftBinSpriteCount = 0;
        for (int dsftBinIndex = 0; dsftBinIndex < dsftBinSpriteSetList.size();  ++dsftBinIndex)
        {
            DsftBinSpriteSet dsftBinSpriteSet = (DsftBinSpriteSet)dsftBinSpriteSetList.get(dsftBinIndex);
            DsftBinSprite dsftBinSpriteArray[] = dsftBinSpriteSet.getDsftBinSpriteArray();
            dsftBinSpriteCount += dsftBinSpriteArray.length;
        }
        
        int dsftBinSpriteRecordSize = 0;
        if (false == dsftBinSpriteSetList.isEmpty())
        {
            DsftBinSpriteSet dsftBinSpriteSet = (DsftBinSpriteSet)dsftBinSpriteSetList.get(0);
            DsftBinSprite dsftBinSpriteArray[] = dsftBinSpriteSet.getDsftBinSpriteArray();
            dsftBinSpriteRecordSize = dsftBinSpriteArray[0].getRecordSize();
        }
        
        int length = 4 + 4;
        length += dsftBinSpriteCount * dsftBinSpriteRecordSize;
        length += dsftBinIndexList.size() * 2;
        return length;
    }


    // Unknown things to decode

    public List getOffsetList()
    {
        try
        {
            Method getOffsetListMethod = dsftBinSpriteClass.getMethod("getOffsetList", null);
            return (List)getOffsetListMethod.invoke(dsftBinSpriteClass, null);
        }
        catch (SecurityException exception)
        {
            throw new RuntimeException(exception);
        }
        catch (NoSuchMethodException exception)
        {
            throw new RuntimeException(exception);
        }
        catch (IllegalArgumentException exception)
        {
            throw new RuntimeException(exception);
        }
        catch (IllegalAccessException exception)
        {
            throw new RuntimeException(exception);
        }
        catch (InvocationTargetException exception)
        {
            throw new RuntimeException(exception);
        }
    }
    public ComparativeTableControl.DataSource getComparativeDataSource()
    {
        return new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                int rowCount = 0;
                
                for (int dsftBinIndex = 0; dsftBinIndex < dsftBinSpriteSetList.size();  ++dsftBinIndex)
                {
                    DsftBinSpriteSet dsftBin = (DsftBinSpriteSet)dsftBinSpriteSetList.get(dsftBinIndex);
                    DsftBinSprite dsftBinSpriteArray[] = dsftBin.getDsftBinSpriteArray();
                    rowCount += dsftBinSpriteArray.length;
                }
                return rowCount;
            }

            public byte[] getData(int dataRow)
            {
                int currentRow = 0;
                
                for (int dsftBinIndex = 0; dsftBinIndex < dsftBinSpriteSetList.size();  ++dsftBinIndex)
                {
                    DsftBinSpriteSet dsftBin = (DsftBinSpriteSet)dsftBinSpriteSetList.get(dsftBinIndex);
                    DsftBinSprite dsftBinSpriteArray[] = dsftBin.getDsftBinSpriteArray();

                    for (int dsftBinSpriteIndex = 0; dsftBinSpriteIndex < dsftBinSpriteArray.length;  ++dsftBinSpriteIndex)
                    {
                        if (dataRow == currentRow)
                        {
                            return dsftBinSpriteArray[dsftBinSpriteIndex].getData();
                        }
                        else
                        {
                            ++currentRow;
                        }
                    }
                }
                
                throw new RuntimeException("Should never reach here");
            }

            public int getAdjustedDataRowIndex(int dataRow)
            {
                return dataRow;
            }
            
            public String getIdentifier(int dataRow)
            {
                int currentRow = 0;
                
                for (int dsftBinIndex = 0; dsftBinIndex < dsftBinSpriteSetList.size();  ++dsftBinIndex)
                {
                    DsftBinSpriteSet dsftBin = (DsftBinSpriteSet)dsftBinSpriteSetList.get(dsftBinIndex);
                    DsftBinSprite dsftBinSpriteArray[] = dsftBin.getDsftBinSpriteArray();

                    for (int dsftBinSpriteIndex = 0; dsftBinSpriteIndex < dsftBinSpriteArray.length;  ++dsftBinSpriteIndex)
                    {
                        if (dataRow == currentRow)
                        {
                            return String.valueOf(dsftBinIndex) + "." + String.valueOf(dsftBinSpriteIndex) + ": " + ((dsftBinSpriteArray[dsftBinSpriteIndex].getSetName().length() == 0) ? "     " : dsftBinSpriteArray[dsftBinSpriteIndex].getSetName()) + "." + dsftBinSpriteArray[dsftBinSpriteIndex].getSpriteName();
                        }
                        else
                        {
                            ++currentRow;
                        }
                    }
                }
                
                throw new RuntimeException("Should never reach here");
            }

            public int getOffset(int dataRow)
            {
                return 0;
            }
        };
    }
}
