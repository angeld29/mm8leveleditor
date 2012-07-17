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
package org.gamenet.application.mm8leveleditor.dataSectionable;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.gamenet.application.mm8leveleditor.control.DsftBinIndexControl;
import org.gamenet.application.mm8leveleditor.data.DsftBin;
import org.gamenet.application.mm8leveleditor.data.DsftBinSprite;
import org.gamenet.application.mm8leveleditor.data.DsftBinSpriteSet;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.DataSection;
import org.gamenet.swing.controls.DataSectionable;
import org.gamenet.swing.controls.ResizingJTable;
import org.gamenet.util.TaskObserver;

public class DsftBinDataSectionable extends BaseDataSectionable implements DataSectionable
{
    private DsftBin dsftBin = null;

    public DsftBinDataSectionable(DsftBin srcDsftBin)
    {
        super();
        
        this.dsftBin = srcDsftBin;
    }


    public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
    {
        List indexNumberList = listForIterator(indexIterator);
        Integer indexNumber = (Integer)indexNumberList.get(0);
        int index = indexNumber.intValue();

        if (dataSectionName == DATA_SECTION_DSFT_SPRITE_SETS)
        {
            return getDsftBinSpriteSetPanel(taskObserver, indexNumberList);
        }
        else if (dataSectionName == DATA_SECTION_INDEXES)
        {
            if (1 == indexNumberList.size())
            {
                return new DsftBinIndexControl(list, index);
            }
            else
            {
                return getIndexPanel(taskObserver, indexNumberList);
            }
        }
        else throw new IllegalStateException("DataSection " + dataSectionName);
    }

    public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
    {
        if (dataSectionName == DATA_SECTION_DSFT_SPRITE_SETS) { return getDsftBinSpriteSetPanel(taskObserver, null); }
        else if (dataSectionName == DATA_SECTION_INDEXES) { return getIndexPanel(taskObserver, null); }
        else throw new IllegalStateException("DataSection " + dataSectionName);
    }
        
    public Component getIndexPanel(TaskObserver taskObserver, List indexNumberList) throws InterruptedException
    {
        taskObserver.taskProgress("Indexes", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getIndexPanel() was interrupted.");

        final List dsftBinIndexList = dsftBin.getDsftBinIndexList();
		if (null == indexNumberList)  indexNumberList = createListForSequence(dsftBinIndexList.size());

		class DsftBinIndexHolder
		{
		    private int realIndex;
            public DsftBinIndexHolder(int realIndex)
            {
                this.realIndex = realIndex;
            }
            
            public int getRealIndex()
            {
                return realIndex;
            }
            
            public Integer getDsftBinIndex()
            {
                return (Integer)dsftBinIndexList.get(realIndex);
            }
            public void setDsftBinIndex(Integer dsftBinIndex)
            {
                dsftBinIndexList.remove(realIndex);
                dsftBinIndexList.add(realIndex, dsftBinIndex);
            }
		}
		
        final List finalizedPartialDsftBinIndexList = new ArrayList();
		
		Iterator indexIterator = indexNumberList.iterator();
        while (indexIterator.hasNext())
        {
            Integer indexInteger = (Integer) indexIterator.next();
            int dsftBinIndexIndex = indexInteger.intValue();

            DsftBinIndexHolder dsftBinIndexHolder = new DsftBinIndexHolder(dsftBinIndexIndex);
            finalizedPartialDsftBinIndexList.add(dsftBinIndexHolder);
        }

		ResizingJTable indexTable = new ResizingJTable(new AbstractTableModel()
        {
            public int getColumnCount()
            {
                return 2;
            }

            public int getRowCount()
            {
                return finalizedPartialDsftBinIndexList.size();
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return (1 == columnIndex);
            }

            public Object getValueAt(int rowIndex, int columnIndex)
            {
                if (0 == columnIndex)  return new Integer(rowIndex);
                
                DsftBinIndexHolder dsftBinIndexHolder = (DsftBinIndexHolder)finalizedPartialDsftBinIndexList.get(rowIndex);
                return dsftBinIndexHolder.getDsftBinIndex();
            }

            public void setValueAt(Object aValue, int rowIndex, int columnIndex)
            {
                DsftBinIndexHolder dsftBinIndexHolder = (DsftBinIndexHolder)finalizedPartialDsftBinIndexList.get(rowIndex);
                dsftBinIndexHolder.setDsftBinIndex(new Integer((String)aValue));
            }
        });
        
        return indexTable;
    }

    public Component getDsftBinSpriteSetPanel(TaskObserver taskObserver, List indexNumberList) throws InterruptedException
    {
        taskObserver.taskProgress("DsftBinSpriteSets", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getDsftBinSpriteSetPanel() was interrupted.");

		if (null == indexNumberList)  indexNumberList = createListForSequence(dsftBin.getComparativeDataSource().getRowCount());

        final Map realSpriteBaseIndexHashMap = new HashMap();
        
        List dsftBinSpriteSetList = dsftBin.getDsftBinSpriteSetList();

        int spriteIndex = 0;
        for (int spriteSetIndex = 0; spriteSetIndex < dsftBinSpriteSetList.size(); spriteSetIndex++)
        {
            DsftBinSpriteSet dsftBinSpriteSet = (DsftBinSpriteSet)dsftBinSpriteSetList.get(spriteSetIndex);
            
            realSpriteBaseIndexHashMap.put(new Integer(spriteSetIndex), new Integer(spriteIndex));

            DsftBinSprite dsftBinSpriteArray[] = dsftBinSpriteSet.getDsftBinSpriteArray();
            spriteIndex += dsftBinSpriteArray.length;
        }

        final Map realSpriteSetHashMap = new HashMap();
        final Map realSpriteSetIndexHashMap = new HashMap();
        final Map realSpriteIndexHashMap = new HashMap();
        final Map adjustedDataRowHashMap = new HashMap();

        int dataRow = 0;
		Iterator indexIterator = indexNumberList.iterator();
        while (indexIterator.hasNext())
        {
            Integer indexInteger = (Integer) indexIterator.next();
            int dsftBinSpriteSetListIndex = indexInteger.intValue();

            DsftBinSpriteSet dsftBinSpriteSet = (DsftBinSpriteSet)dsftBinSpriteSetList.get(dsftBinSpriteSetListIndex);
            
            DsftBinSprite dsftBinSpriteArray[] = dsftBinSpriteSet.getDsftBinSpriteArray();
            for (int i = 0; i < dsftBinSpriteArray.length; i++)
            {
                realSpriteSetHashMap.put(new Integer(dataRow), dsftBinSpriteSet);
                realSpriteSetIndexHashMap.put(new Integer(dataRow), new Integer(dsftBinSpriteSetListIndex));
                realSpriteIndexHashMap.put(new Integer(dataRow), new Integer(((Integer)realSpriteBaseIndexHashMap.get(new Integer(dsftBinSpriteSetListIndex))).intValue() + i));
                adjustedDataRowHashMap.put(new Integer(dataRow), new Integer(i));
                dataRow++;
            }
        }

	    ComparativeTableControl.DataSource partialComparativeDataSource = new ComparativeTableControl.DataSource()
        {
            public int getRowCount()
            {
                return adjustedDataRowHashMap.size();
            }

            public byte[] getData(int dataRow)
            {
                Integer dataRowNumber = new Integer(dataRow);
                Integer adjustedDataRowNumber = (Integer)adjustedDataRowHashMap.get(dataRowNumber);
                int adjustedDataRow = adjustedDataRowNumber.intValue();
                DsftBinSpriteSet dsftBinSpriteSet = (DsftBinSpriteSet)realSpriteSetHashMap.get(dataRowNumber);
                return dsftBinSpriteSet.getComparativeDataSource().getData(adjustedDataRow);
            }

            public int getAdjustedDataRowIndex(int dataRow)
            {
                Integer dataRowNumber = new Integer(dataRow);
                return ((Integer)realSpriteIndexHashMap.get(dataRowNumber)).intValue();
            }
            
            public String getIdentifier(int dataRow)
            {
                Integer dataRowNumber = new Integer(dataRow);
                Integer adjustedDataRowNumber = (Integer)adjustedDataRowHashMap.get(dataRowNumber);
                Integer realSpriteSetIndexNumber = (Integer)realSpriteSetIndexHashMap.get(dataRowNumber);
                
                int adjustedDataRow = adjustedDataRowNumber.intValue();
                DsftBinSpriteSet dsftBinSpriteSet = (DsftBinSpriteSet)realSpriteSetHashMap.get(dataRowNumber);
                return "Set #" + realSpriteSetIndexNumber + "." + dsftBinSpriteSet.getComparativeDataSource().getIdentifier(adjustedDataRow);
                
            }

            public int getOffset(int dataRow)
            {
                Integer dataRowNumber = new Integer(dataRow);
                Integer adjustedDataRowNumber = (Integer)adjustedDataRowHashMap.get(dataRowNumber);
                int adjustedDataRow = adjustedDataRowNumber.intValue();
                DsftBinSpriteSet dsftBinSpriteSet = (DsftBinSpriteSet)realSpriteSetHashMap.get(dataRowNumber);
                return dsftBinSpriteSet.getComparativeDataSource().getOffset(adjustedDataRow);
            }
        };

        ComparativeTableControl dsftCBDTC = new ComparativeTableControl(dsftBin.getOffsetList(), partialComparativeDataSource);
        
        return dsftCBDTC;
    }
    
    public DataTypeInfo getDataTypeInfo(String dataSectionName)
    {
        // if (dataSectionName == DATA_SECTION_VERTEXES) { return vertexDataTypeInfo; }
        // else if (dataSectionName == DATA_SECTION_FACETS) { return facetDataTypeInfo; }
        throw new IllegalStateException("DataSection " + dataSectionName);
    }
    
    public Object getData()
    {
        return dsftBin;
    }

    public static final String DATA_SECTION_DSFT_SPRITE_SETS = "Sprite Sets";
    public static final String DATA_SECTION_INDEXES = "Index";

    public static DataSection[] getDataSections()
    {
        return new DataSection[] {
                new DataSection(DATA_SECTION_DSFT_SPRITE_SETS, DsftBinSpriteSet.class, DsftBinSpriteSetDataSectionable.class),
                new DataSection(DATA_SECTION_INDEXES)
        };
    }

    public DataSection[] getStaticDataSections() { return getDataSections(); }

    public Object getDataForDataSection(DataSection dataSection)
    {
        if (DATA_SECTION_DSFT_SPRITE_SETS == dataSection.getDataSectionName())
        {
            return dsftBin.getDsftBinSpriteSetList();
        }
        else if (DATA_SECTION_INDEXES == dataSection.getDataSectionName())
        {
            return dsftBin.getDsftBinIndexList();
        }
        else throw new IllegalStateException("DataSection " + dataSection.getDataSectionName());
    }
}
