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


public class DsftBinSpriteSet
{
    private Class dsftBinSpriteClass;
    private DsftBinSprite dsftBinSpriteArray[] = null;
    private int setNumber = 0;
    private int offset = 0;
    
    public DsftBinSpriteSet(Class dsftBinSpriteClass)
    {
        super();
        
        this.dsftBinSpriteClass = dsftBinSpriteClass;
    }

    public int initialize(byte[] data, int offset)
    {
        this.offset = offset;

        List dsftBinSpriteList = new ArrayList();
        
        boolean moreRecords = true;
        boolean readSetHeader = false;
        while(moreRecords)
        {
            DsftBinSprite dbs;
            try
            {
                dbs = (DsftBinSprite)dsftBinSpriteClass.newInstance();
            }
            catch (InstantiationException exception)
            {
                throw new RuntimeException(exception);
            }
            catch (IllegalAccessException exception)
            {
                throw new RuntimeException(exception);
            }
            offset = dbs.initialize(data, offset);
            
            if ((readSetHeader) && (dbs.getSetName().length() != 0))
            {
                offset -= dbs.getRecordSize();
                moreRecords = false;
                break;
            }
            dsftBinSpriteList.add(dbs);
            moreRecords = dbs.hasMoreRecords();
            readSetHeader = true;
        }
        
        dsftBinSpriteArray = new DsftBinSprite[dsftBinSpriteList.size()];
        for (int index = 0; index < dsftBinSpriteArray.length; index++)
        {
            dsftBinSpriteArray[index] = (DsftBinSprite)dsftBinSpriteList.get(index);
        }
        
        return offset;
    }
    
    public int updateData(byte[] newData, int offset)
    {
        for (int index = 0; index < dsftBinSpriteArray.length; index++)
        {
            offset = dsftBinSpriteArray[index].updateData(newData, offset);
        }
        
        return offset;
    }
    
    public static int populateObjects(byte[] data, int offset, List dsftBinSpriteSetList, int entryCount, Class dsftBinSpriteClass)
    {
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

        int dsftEntryCount = 0;
        int endOfData = (8 + (entryCount * recordSizeNumber.intValue()));
        while(offset < endOfData)
        {
            DsftBinSpriteSet dsftBinSpriteSet = new DsftBinSpriteSet(dsftBinSpriteClass);
            
            offset = dsftBinSpriteSet.initialize(data, offset);
            
            dsftBinSpriteSetList.add(dsftBinSpriteSet);
            
            dsftEntryCount += dsftBinSpriteSet.getDsftBinSpriteArray().length;
        }
        
        return offset;
    }
    
    public static int updateData(byte[] newData, int offset, List dsftBinSpriteSet)
    {
        for (int dsftBinListIndex = 0; dsftBinListIndex < dsftBinSpriteSet.size(); ++dsftBinListIndex)
        {
            DsftBinSpriteSet dsftBin = (DsftBinSpriteSet)dsftBinSpriteSet.get(dsftBinListIndex);
           
            offset = dsftBin.updateData(newData, offset);
        }
        
        return offset;
    }

    public String getSetName()
    {
        return this.dsftBinSpriteArray[0].getSetName();
    }
    public void setSetName(String setName)
    {
        this.dsftBinSpriteArray[0].setSetName(setName);
    }
    public int getSetNumber()
    {
        return this.setNumber;
    }
    public void setSetNumber(int setNumber)
    {
        this.setNumber = setNumber;
    }
    public DsftBinSprite[] getDsftBinSpriteArray()
    {
        return this.dsftBinSpriteArray;
    }
    public int getOffset()
    {
        return this.offset;
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
                return getDsftBinSpriteArray().length;
            }

            public byte[] getData(int dataRow)
            {
                int currentRow = 0;
                
                DsftBinSprite dsftBinSpriteArray[] = getDsftBinSpriteArray();

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
                
                throw new RuntimeException("Should never reach here");
            }

            public int getAdjustedDataRowIndex(int dataRow)
            {
                return dataRow;
            }
            
            public String getIdentifier(int dataRow)
            {
                int currentRow = 0;
                
                DsftBinSprite dsftBinSpriteArray[] = getDsftBinSpriteArray();

                for (int dsftBinSpriteIndex = 0; dsftBinSpriteIndex < dsftBinSpriteArray.length;  ++dsftBinSpriteIndex)
                {
                    if (dataRow == currentRow)
                    {
                        return String.valueOf(dsftBinSpriteIndex) + ": " + ((dsftBinSpriteArray[dsftBinSpriteIndex].getSetName().length() == 0) ? "     " : dsftBinSpriteArray[dsftBinSpriteIndex].getSetName()) + "." + dsftBinSpriteArray[dsftBinSpriteIndex].getSpriteName();
                    }
                    else
                    {
                        ++currentRow;
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
