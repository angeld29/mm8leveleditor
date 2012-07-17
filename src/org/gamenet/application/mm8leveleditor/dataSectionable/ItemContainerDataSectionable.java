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
import java.util.Iterator;
import java.util.List;

import org.gamenet.application.mm8leveleditor.data.mm6.ItemContainer;
import org.gamenet.swing.controls.DataSection;
import org.gamenet.swing.controls.DataSectionable;
import org.gamenet.util.TaskObserver;

public class ItemContainerDataSectionable extends BaseDataSectionable implements DataSectionable
{
    private ItemContainer itemContainer = null;
    
    public ItemContainerDataSectionable(ItemContainer srcItemContainer)
    {
        super();
        
        this.itemContainer = srcItemContainer;
    }

    public DataTypeInfo getDataTypeInfo(String dataSectionName)
    {
        // if (dataSectionName == DATA_SECTION_VERTEXES) { return vertexDataTypeInfo; }
        // else if (dataSectionName == DATA_SECTION_FACETS) { return facetDataTypeInfo; }
        throw new IllegalStateException("DataSection " + dataSectionName);
    }
    
    public Object getData()
    {
        return itemContainer;
    }
    
    public static final String DATA_SECTION_CONTAINER_ITEMS = "Contained Items";
    public static final String DATA_SECTION_CONTAINER_MAP = "Container Map";

    public static DataSection[] getDataSections()
    {
        return new DataSection[] {
//                new DataSection(DATA_SECTION_CONTAINER_ITEMS, false, ContainedItem.class, ContainedItem.getDataSections()),
//                new DataSection(DATA_SECTION_CONTAINER_MAP, false, ContainerMap.class, ContainerMap.getDataSections())
        };
    }
        
    public DataSection[] getStaticDataSections() { return getDataSections(); }

    public Object getDataForDataSection(DataSection dataSection)
    {
        if (DATA_SECTION_CONTAINER_ITEMS == dataSection.getDataSectionName())
        {
            return itemContainer.getContainedItemArray();
        }
        else if (DATA_SECTION_CONTAINER_MAP == dataSection.getDataSectionName())
        {
            return itemContainer.getItemLocationMap();
        }
        else throw new IllegalStateException("DataSection " + dataSection.getDataSectionName());
    }

    public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
    {
        
        // IMPLEMENT: item container stuff
        
//        if (DATA_SECTION_CONTAINER_ITEMS == dataSection.getDataSectionName())
//        {
//            return new ContainedItemControl(item);
//        }
//        else if (DATA_SECTION_CONTAINER_MAP == dataSection.getDataSectionName())
//        {
//            return new ContainerMapControl(itemLocationMap);
//        }
//        else
            throw new IllegalStateException("DataSection " + dataSectionName);
    }

    public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
    {
        throw new IllegalStateException("No data sections: " + dataSectionName);
    }
}
