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

import org.gamenet.application.mm8leveleditor.data.mm6.Sprite;
import org.gamenet.swing.controls.DataSection;
import org.gamenet.swing.controls.DataSectionable;
import org.gamenet.util.TaskObserver;

public class SpriteDataSectionable extends BaseDataSectionable implements DataSectionable
{
    private Sprite sprite = null;
    
    public SpriteDataSectionable(Sprite srcSprite)
    {
        super();
        
        this.sprite = srcSprite;
    }

    public DataTypeInfo getDataTypeInfo(String dataSectionName)
    {
        // if (dataSectionName == DATA_SECTION_VERTEXES) { return vertexDataTypeInfo; }
        // else if (dataSectionName == DATA_SECTION_FACETS) { return facetDataTypeInfo; }
        throw new IllegalStateException("DataSection " + dataSectionName);
    }
    
    public Object getData()
    {
        return sprite;
    }

    public static DataSection[] getDataSections()
    {
        return new DataSection[] {
//                new DataSection("Info")
         };
    }
    
    public DataSection[] getStaticDataSections() { return getDataSections(); }

    public Object getDataForDataSection(DataSection dataSection)
    {
        throw new IllegalStateException("No data sections: " + dataSection);
    }

    public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
    {
        throw new IllegalStateException("No data sections: " + dataSectionName);
    }

    public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
    {
        throw new IllegalStateException("No data sections: " + dataSectionName);
    }
}
