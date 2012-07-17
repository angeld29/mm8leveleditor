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

import javax.swing.JTabbedPane;

import org.gamenet.application.mm8leveleditor.control.VertexControl;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.IntVertex;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.TerrainNormalMapData;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.DataSection;
import org.gamenet.swing.controls.DataSectionable;
import org.gamenet.swing.controls.PixelMapDataSource;
import org.gamenet.swing.controls.SwatchPanel;
import org.gamenet.swing.controls.TextMapTableControl;
import org.gamenet.util.TaskObserver;

public class TerrainNormalMapDataDataSectionable extends BaseDataSectionable implements DataSectionable
{
    private TerrainNormalMapData terrainNormalMapData = null;
    
    public TerrainNormalMapDataDataSectionable(TerrainNormalMapData srcTerrainNormalMapData)
    {
        super();
        
        this.terrainNormalMapData = srcTerrainNormalMapData;
    }

    public Object getData()
    {
        return terrainNormalMapData;
    }

    
    /**
     * @param taskObserver
     * @return
     */
    protected Component getTextMapsPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Text Maps", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getTextMapsPanel() was interrupted.");

        JTabbedPane textMapsTabbedPane = new JTabbedPane();
        textMapsTabbedPane.addTab("Distance map[0]", makeNonStretchedPanelFor(new TextMapTableControl(terrainNormalMapData.getTerranNormalDistanceMap0(), 0)));
        textMapsTabbedPane.addTab("Distance map[1]", makeNonStretchedPanelFor(new TextMapTableControl(terrainNormalMapData.getTerranNormalDistanceMap1(), 0)));
        textMapsTabbedPane.addTab("Handle map[0]", makeNonStretchedPanelFor(new TextMapTableControl(terrainNormalMapData.getTerranNormalHandleMap0(), 0)));
        textMapsTabbedPane.addTab("Handle map[1]", makeNonStretchedPanelFor(new TextMapTableControl(terrainNormalMapData.getTerranNormalHandleMap1(), 0)));
        
        return textMapsTabbedPane;
    }

    /**
     * @param taskObserver
     * @return
     */
    protected Component getPixelMapsPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Pixel Maps", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getPixelMapsPanel() was interrupted.");

        PixelMapDataSource terranNormalDMap0DataSource = new PixelMapDataSource()
        {
            public int getValueAt(int row, int column)
            {
                return (int)terrainNormalMapData.getTerranNormalDistanceMap0()[row][column];
            }

            public int getRowCount() { return 128; }
            public int getColumnCount() { return 128; }
        };

        PixelMapDataSource terranNormalDMap1DataSource = new PixelMapDataSource()
        {
            public int getValueAt(int row, int column)
            {
                return (int)terrainNormalMapData.getTerranNormalDistanceMap1()[row][column];
            }

            public int getRowCount() { return 128; }
            public int getColumnCount() { return 128; }
        };

        PixelMapDataSource terranNormalHandleMap0DataSource = new PixelMapDataSource()
        {
            public int getValueAt(int row, int column)
            {
                return terrainNormalMapData.getTerranNormalHandleMap0()[row][column];
            }

            public int getRowCount() { return 128; }
            public int getColumnCount() { return 128; }
        };

        PixelMapDataSource terranNormalHandleMap1DataSource = new PixelMapDataSource()
        {
            public int getValueAt(int row, int column)
            {
                return terrainNormalMapData.getTerranNormalHandleMap1()[row][column];
            }

            public int getRowCount() { return 128; }
            public int getColumnCount() { return 128; }
        };

        JTabbedPane pixelMapsTabbedPane = new JTabbedPane();
        pixelMapsTabbedPane.addTab("Distance map[0]", makeNonStretchedPanelFor(new SwatchPanel(terranNormalDMap0DataSource, 0, 4, true)));
        pixelMapsTabbedPane.addTab("Distance map[1]", makeNonStretchedPanelFor(new SwatchPanel(terranNormalDMap1DataSource, 0, 4, true)));
        pixelMapsTabbedPane.addTab("Handle map[0]", makeNonStretchedPanelFor(new SwatchPanel(terranNormalHandleMap0DataSource, 0, 4, true)));
        pixelMapsTabbedPane.addTab("Handle map[1]", makeNonStretchedPanelFor(new SwatchPanel(terranNormalHandleMap1DataSource, 0, 4, true)));

        return pixelMapsTabbedPane;
    }

    public static final String DATA_SECTION_PIXEL_MAPS = "Pixel Maps";
    public static final String DATA_SECTION_TEXT_MAPS = "Text Maps";
    public static final String DATA_SECTION_VERTEXES = "Vertexes";

    public static DataSection[] getDataSections()
    {
        return new DataSection[] {
                new DataSection(DATA_SECTION_PIXEL_MAPS),
                new DataSection(DATA_SECTION_TEXT_MAPS),
                new DataSection(DATA_SECTION_VERTEXES)
        };
    }

    public DataSection[] getStaticDataSections() { return getDataSections(); }

    public Object getDataForDataSection(DataSection dataSection)
    {
        if (DATA_SECTION_PIXEL_MAPS == dataSection.getDataSectionName())
        {
            if (null == terrainNormalMapData)  return null;
            
            return null;
            // IMPLEMENT: return terrainNormalMapData.getPixelMaps();
        }
        else if (DATA_SECTION_TEXT_MAPS == dataSection.getDataSectionName())
        {
            if (null == terrainNormalMapData)  return null;
            
            return null;
            // IMPLEMENT: return terrainNormalMapData.getTextMaps();
        }
        else if (DATA_SECTION_VERTEXES == dataSection.getDataSectionName())
        {
            if (null == terrainNormalMapData)  return null;
            
            return terrainNormalMapData.getTerrainNormalVertexList();
        }
        else throw new IllegalStateException("DataSection " + dataSection.getDataSectionName());

    }

    public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
    {
        if (null == terrainNormalMapData)  return getNonApplicablePanel(taskObserver);
        
        if (dataSectionName == DATA_SECTION_TEXT_MAPS) { return getTextMapsPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_PIXEL_MAPS) { return getPixelMapsPanel(taskObserver); }
        else return super.getListComponent(dataSectionName, taskObserver, list, indexIterator);
    }

    public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
    {
        if (null == terrainNormalMapData)  return getNonApplicablePanel(taskObserver);
        
        if (dataSectionName == DATA_SECTION_TEXT_MAPS) { return getTextMapsPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_PIXEL_MAPS) { return getPixelMapsPanel(taskObserver); }
        else return super.getComponent(dataSectionName, taskObserver);
    }

    public DataTypeInfo getDataTypeInfo(String dataSectionName)
    {
        if (dataSectionName == DATA_SECTION_VERTEXES) { return vertexDataTypeInfo; }
        else throw new IllegalStateException("DataSection " + dataSectionName);
    }
    
    public IntVertex createNewTerrainNormalVertex()
    {
        int x = 0;
        int y = 0;
        int z = 0;
        return new IntVertex(x, y, z);
    }
    
    private DataTypeInfo vertexDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Vertex"; }
        public String getDataTypeNamePlural() 	{ return "Vertexes"; }
        public List getDataList() 				{ return terrainNormalMapData.getTerrainNormalVertexList(); }
        public List getOffsetList() 			{ return null; }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return null; }
        public Component getDataControl(int dataIndex) {
            									return new VertexControl((IntVertex)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((VertexControl)component).getVertex()); }
        public Component createNewDataControl() {
            									return new VertexControl(createNewTerrainNormalVertex()); }
    };
}
