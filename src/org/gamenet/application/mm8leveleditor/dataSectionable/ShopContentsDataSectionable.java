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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.gamenet.application.mm8leveleditor.control.ContainedItemControl;
import org.gamenet.application.mm8leveleditor.data.mm6.ContainedItem;
import org.gamenet.application.mm8leveleditor.data.mm6.ShopContents;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.DataSection;
import org.gamenet.swing.controls.DataSectionable;
import org.gamenet.util.TaskObserver;

public class ShopContentsDataSectionable extends BaseDataSectionable implements DataSectionable
{
    private ShopContents shopContents = null;
    
    public ShopContentsDataSectionable(ShopContents srcShopContents)
    {
        super();
        
        this.shopContents = srcShopContents;
    }

    public Object getData()
    {
        return shopContents;
    }

    public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
    {
        return super.getComponent(dataSectionName, taskObserver);
    }

    public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
    {
        return super.getListComponent(dataSectionName, taskObserver, list, indexIterator);
    }
    
    public static final String DATA_SECTION_CONTAINEDITEMS = "Contained Items";

    public static DataSection[] getDataSections()
    {
        return new DataSection[] {
                new DataSection(DATA_SECTION_CONTAINEDITEMS, ContainedItem.class, ContainedItemDataSectionable.class)
        };
    }
    
    public DataSection[] getStaticDataSections() { return getDataSections(); }

    public Object getDataForDataSection(DataSection dataSection)
    {
        if (DATA_SECTION_CONTAINEDITEMS == dataSection.getDataSectionName())
        {
            return Arrays.asList(shopContents.getContainedItemArray());
        }
        else throw new IllegalStateException("No data sections: " + dataSection);
    }

    public DataTypeInfo getDataTypeInfo(String dataSectionName)
    {
        if (dataSectionName == DATA_SECTION_CONTAINEDITEMS) { return containedItemDataTypeInfo; }
        else throw new IllegalStateException("No data sections: " + dataSectionName);
    }
    
    private ContainedItem createNewContainedItem()
    {
        ContainedItem newContainedItem = new ContainedItem(shopContents.getGameVersion());
        
        return newContainedItem;
    }

    private DataTypeInfo containedItemDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "ContainedItem"; }
        public String getDataTypeNamePlural() 	{ return "ContainedItems"; }
        public List getDataList() 				{ return Arrays.asList(shopContents.getContainedItemArray()); }
        public List getOffsetList() 			{ return null; }
        public ComparativeTableControl.DataSource getComparativeDataSource() { return null; }
        public Component getDataControl(int dataIndex) {
            									return new ContainedItemControl((ContainedItem)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((ContainedItemControl)component).getContainedItem()); }
        public Component createNewDataControl() {
            									return new ContainedItemControl(createNewContainedItem()); }
    };
}
