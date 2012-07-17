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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gamenet.application.mm8leveleditor.control.BSPNodeControl;
import org.gamenet.application.mm8leveleditor.control.IndoorFaceControl;
import org.gamenet.application.mm8leveleditor.control.IndoorFacetExtraDataControl;
import org.gamenet.application.mm8leveleditor.control.LightSourceControl;
import org.gamenet.application.mm8leveleditor.control.MapOutlineLineControl;
import org.gamenet.application.mm8leveleditor.control.RoomControl;
import org.gamenet.application.mm8leveleditor.control.SpawnPointControl;
import org.gamenet.application.mm8leveleditor.control.SpriteControl;
import org.gamenet.application.mm8leveleditor.control.VertexControl;
import org.gamenet.application.mm8leveleditor.data.ByteData;
import org.gamenet.application.mm8leveleditor.data.mm6.BSPNode;
import org.gamenet.application.mm8leveleditor.data.mm6.SpawnPoint;
import org.gamenet.application.mm8leveleditor.data.mm6.Sprite;
import org.gamenet.application.mm8leveleditor.data.mm6.indoor.IndoorDataMap;
import org.gamenet.application.mm8leveleditor.data.mm6.indoor.IndoorFace;
import org.gamenet.application.mm8leveleditor.data.mm6.indoor.IndoorFacetExtraData;
import org.gamenet.application.mm8leveleditor.data.mm6.indoor.LightSource;
import org.gamenet.application.mm8leveleditor.data.mm6.indoor.MapOutlineLine;
import org.gamenet.application.mm8leveleditor.data.mm6.indoor.Room;
import org.gamenet.application.mm8leveleditor.data.mm6.indoor.ShortVertex;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.DataSection;
import org.gamenet.swing.controls.DataSectionable;
import org.gamenet.swing.controls.IntTextField;
import org.gamenet.swing.controls.IntValueHolder;
import org.gamenet.swing.controls.StringTextField;
import org.gamenet.swing.controls.StringValueHolder;
import org.gamenet.util.TaskObserver;

public class IndoorDataMapDataSectionable extends BaseDataSectionable implements DataSectionable
{
    private IndoorDataMap indoorDataMap = null;
    
    public IndoorDataMapDataSectionable(IndoorDataMap srcIndoorDataMap)
    {
        super();
        
        this.indoorDataMap = srcIndoorDataMap;
    }
    
    protected Component getGeneralPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("General", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getGeneralPanel() was interrupted.");

        IntTextField levelNumberStringTextField = new IntTextField(new IntValueHolder() {
            public int getValue() { return indoorDataMap.getLevelNumber(); }
            public void setValue(int value) { indoorDataMap.setLevelNumber(value); }
        });

        StringTextField levelNameStringTextField = new StringTextField(new StringValueHolder() {
            public String getValue() { return indoorDataMap.getLevelName(); }
            public void setValue(String value) { indoorDataMap.setLevelName(value); }
            public int getMaxLength() { return indoorDataMap.getLevelNameMaxLength(); }
        });

        // IMPLEMENT: palettes
        
        StringTextField songNameStringTextField = new StringTextField(new StringValueHolder() {
            public String getValue() { return indoorDataMap.getSongName(); }
            public void setValue(String value) { indoorDataMap.setSongName(value); }
            public int getMaxLength() { return indoorDataMap.getSongNameMaxLength(); }
        });

        StringTextField skyBitmapStringTextField = new StringTextField(new StringValueHolder() {
            public String getValue() { return indoorDataMap.getSkyBitmapName(); }
            public void setValue(String value) { indoorDataMap.setSkyBitmapName(value); }
            public int getMaxLength() { return indoorDataMap.getSkyBitmapNameMaxLength(); }
        });

        IntTextField skyIndexTextField = new IntTextField(new IntValueHolder() {
            public int getValue() { return indoorDataMap.getSkyIndex(); }
            public void setValue(int value) { indoorDataMap.setSkyIndex(value); }
        });

        IntTextField variableDoorDataSizeTextField = new IntTextField(new IntValueHolder() {
            public int getValue() { return indoorDataMap.getVariableDoorDataSize(); }
            public void setValue(int value) { indoorDataMap.setVariableDoorDataSize(value); }
        });

        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(new JLabel("Level Number: "));
        labelPane.add(new JLabel("Level Name: "));
        labelPane.add(new JLabel("Song Name: "));
        labelPane.add(new JLabel("Sky Bitmap Name: "));
        labelPane.add(new JLabel("Sky Index: "));
        labelPane.add(new JLabel("Variable Door Data Size: "));
        
        //Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(levelNumberStringTextField);
        fieldPane.add(levelNameStringTextField);
        fieldPane.add(songNameStringTextField);
        fieldPane.add(skyBitmapStringTextField);
        fieldPane.add(skyIndexTextField);
        fieldPane.add(variableDoorDataSizeTextField);

        int paletteArray[] = indoorDataMap.getPaletteArray();
        for (int index = 0; index < paletteArray.length; index++)
        {
            final int finalizedIndex = index;
            
            labelPane.add(new JLabel("Palette[" + String.valueOf(index) + "]: "));

            IntTextField paletteTextField = new IntTextField(new IntValueHolder() {
                public int getValue() { return indoorDataMap.getPaletteArray()[finalizedIndex]; }
                public void setValue(int value) { indoorDataMap.getPaletteArray()[finalizedIndex] = value; }
            });
            fieldPane.add(paletteTextField);
        }

        //Put the panels in this panel, labels on left,
        //text fields on right.
        JPanel labelAndFieldPanel = new JPanel(new BorderLayout());
        labelAndFieldPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        labelAndFieldPanel.add(labelPane, BorderLayout.CENTER);
        labelAndFieldPanel.add(fieldPane, BorderLayout.LINE_END);
		
        return labelAndFieldPanel;
    }

    public ShortVertex createNewVertex()
    {
        int x = 0;
        int y = 0;
        int z = 0;
        return new ShortVertex(x, y, z);
    }
    
    private IndoorFace createNewFace()
    {
        return new IndoorFace(indoorDataMap.getGameVersion());
    }

    private IndoorFacetExtraData createNewIndoorFacetExtraData()
    {
        return new IndoorFacetExtraData();
    }

    private Room createNewRoom()
    {
        return new Room(indoorDataMap.getGameVersion());
    }
    
    private Sprite createNewSprite()
    {
        String spriteName = "";
        int x = 0;
        int y = 0;
        int z = 0;
        int eventNumber = 0;

        Sprite newSprite = new Sprite(indoorDataMap.getGameVersion(), spriteName, x, y, z, eventNumber);
        
        return newSprite;
    }

    private LightSource createNewLightSource()
    {
        int x = 0;
        int y = 0;
        int z = 0;
        int ambientLevel = 0;

        LightSource newLightSource = new LightSource(indoorDataMap.getGameVersion(), x, y, z, ambientLevel);
        
        return newLightSource;
    }

    private MapOutlineLine createNewMapOutlineLine()
    {
        int vertex1 = 1;
        int vertex2 = 0;
        int vertex3 = 0;
        int vertex4 = 0;
        int z = 0;

        MapOutlineLine newMapOutlineLine = new MapOutlineLine(vertex1, vertex2, vertex3, vertex4, z);
        
        return newMapOutlineLine;
    }

    private BSPNode createNewBSPNode()
    {
        short frontNode = 0;
        short backNode = 0;
        short coplanarOffset = 0;
        short coplanarSize = 0;
        return new BSPNode(frontNode, backNode, coplanarOffset, coplanarSize);
    }
    
    private SpawnPoint createNewMonster() {
        int monsterClass = 1;
        int x = 0;
        int y = 0;
        int z = 0;
        int radius = 0;
        return new SpawnPoint(indoorDataMap.getGameVersion(), monsterClass, x, y, z, radius);
    }


    public Object getData()
    {
        return indoorDataMap;
    }
    public static final String DATA_SECTION_GENERAL = "General";
    public static final String DATA_SECTION_VERTEXES = "Vertexes";
    public static final String DATA_SECTION_FACETS = "Facets";
    public static final String DATA_SECTION_FACET_DATA_EXTRA = "More Facets Data";
    public static final String DATA_SECTION_ROOMS = "Rooms";
    public static final String DATA_SECTION_VARIABLE_ROOM_DATA = "Variable Room Data";
    public static final String DATA_SECTION_VARIABLE_ROOM_LIGHT_DATA = "Variable Room Light Data";
    public static final String DATA_SECTION_SPRITES = "Sprites";
    public static final String DATA_SECTION_LIGHT_SOURCES = "Light Sources";
    public static final String DATA_SECTION_BSP_NODES = "BSP Node List";
    public static final String DATA_SECTION_SPAWN_POINTS = "Spawn Points";
    public static final String DATA_SECTION_MAP_OUTLINE_LINES = "Map Outline Lines";

    public static DataSection[] getDataSections()
    {
        return new DataSection[] {
                new DataSection(DATA_SECTION_GENERAL),
                new DataSection(DATA_SECTION_VERTEXES, ShortVertex.class, IndoorVertexDataSectionable.class),
                new DataSection(DATA_SECTION_FACETS, IndoorFace.class, IndoorFaceDataSectionable.class),
                new DataSection(DATA_SECTION_FACET_DATA_EXTRA, null, null),
//              new DataSection(DATA_SECTION_FACET_DATA_EXTRA, IndoorFacetExtraData.class, IndoorFacetDataExtraSectionable.class),
                new DataSection(DATA_SECTION_ROOMS, null, null),
//              new DataSection(DATA_SECTION_ROOMS, Room.class, RoomDataSectionable.class),
                new DataSection(DATA_SECTION_VARIABLE_ROOM_DATA),
                new DataSection(DATA_SECTION_VARIABLE_ROOM_LIGHT_DATA),
                new DataSection(DATA_SECTION_SPRITES, Sprite.class, SpriteDataSectionable.class),
                new DataSection(DATA_SECTION_LIGHT_SOURCES, LightSource.class, LightSourceDataSectionable.class),
                new DataSection(DATA_SECTION_BSP_NODES, null, null),
//              new DataSection(DATA_SECTION_BSP_NODES, BSPNode.class, BSPNodeDataSectionable.class),
                new DataSection(DATA_SECTION_SPAWN_POINTS, SpawnPoint.class, MonsterDataSectionable.class),
                new DataSection(DATA_SECTION_MAP_OUTLINE_LINES, MapOutlineLine.class, MapOutlineLineDataSectionable.class),
        };
    }
    
    public DataSection[] getStaticDataSections() { return getDataSections(); }

    public Object getDataForDataSection(DataSection dataSection)
    {
        if (DATA_SECTION_GENERAL == dataSection.getDataSectionName())
        {
            return null;
            // IMPLEMENT: return indoorDataMap.getGeneral();
        }
        else if (DATA_SECTION_VERTEXES == dataSection.getDataSectionName())
        {
            return indoorDataMap.getVertexList();
        }
        else if (DATA_SECTION_FACETS == dataSection.getDataSectionName())
        {
            return indoorDataMap.getFaceList();
        }
        else if (DATA_SECTION_FACET_DATA_EXTRA == dataSection.getDataSectionName())
        {
            return indoorDataMap.getIndoorFacetExtraDataList();
        }
        else if (DATA_SECTION_ROOMS == dataSection.getDataSectionName())
        {
            return indoorDataMap.getRoomList();
        }
        else if (DATA_SECTION_VARIABLE_ROOM_DATA == dataSection.getDataSectionName())
        {
            return indoorDataMap.getVariableRoomData();
        }
        else if (DATA_SECTION_VARIABLE_ROOM_LIGHT_DATA == dataSection.getDataSectionName())
        {
            return indoorDataMap.getVariableRoomLightData();
        }
        else if (DATA_SECTION_SPRITES == dataSection.getDataSectionName())
        {
            return indoorDataMap.getSpriteList();
        }
        else if (DATA_SECTION_LIGHT_SOURCES == dataSection.getDataSectionName())
        {
            return indoorDataMap.getLightSourcesList();
        }
        else if (DATA_SECTION_BSP_NODES == dataSection.getDataSectionName())
        {
            return indoorDataMap.getBspNodeList();
        }
        else if (DATA_SECTION_SPAWN_POINTS == dataSection.getDataSectionName())
        {
            return indoorDataMap.getSpawnPointList();
        }
        else if (DATA_SECTION_MAP_OUTLINE_LINES == dataSection.getDataSectionName())
        {
            return indoorDataMap.getMapOutlineList();
        }
        else throw new IllegalStateException("DataSection " + dataSection.getDataSectionName());

    }

    public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
    {
        if (dataSectionName == DATA_SECTION_GENERAL) { return getGeneralPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_VARIABLE_ROOM_DATA)
        {
            return getUnknownsPanel(taskObserver, Collections.singletonList(new Integer(0)),
                    Collections.singletonList(new ByteData(indoorDataMap.getVariableRoomData(), 0)));
        }
        else if (dataSectionName == DATA_SECTION_VARIABLE_ROOM_LIGHT_DATA)
        {
            return getUnknownsPanel(taskObserver, Collections.singletonList(new Integer(0)),
                    Collections.singletonList(new ByteData(indoorDataMap.getVariableRoomLightData(), 0)));
        }
        else return super.getListComponent(dataSectionName, taskObserver, list, indexIterator);
    }

    public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
    {
        if (dataSectionName == DATA_SECTION_GENERAL) { return getGeneralPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_VARIABLE_ROOM_DATA)
        {
            return getUnknownsPanel(taskObserver, Collections.singletonList(new Integer(0)),
                    Collections.singletonList(new ByteData(indoorDataMap.getVariableRoomData(), 0)));
        }
        else if (dataSectionName == DATA_SECTION_VARIABLE_ROOM_LIGHT_DATA)
        {
            return getUnknownsPanel(taskObserver, Collections.singletonList(new Integer(0)),
                    Collections.singletonList(new ByteData(indoorDataMap.getVariableRoomLightData(), 0)));
        }
        else return super.getComponent(dataSectionName, taskObserver);
    }

    public DataTypeInfo getDataTypeInfo(String dataSectionName)
    {
        if (dataSectionName == DATA_SECTION_VERTEXES) { return vertexDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_FACETS) { return facetDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_FACET_DATA_EXTRA) { return facetDataExtraDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_ROOMS) { return roomDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_SPRITES) { return spriteDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_LIGHT_SOURCES) { return lightSourceDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_BSP_NODES) { return bspNodeDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_SPAWN_POINTS) { return monsterDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_MAP_OUTLINE_LINES) { return mapOutlineLinesDataTypeInfo; }
        else throw new IllegalStateException("DataSection " + dataSectionName);
    }
    
    private DataTypeInfo vertexDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Vertex"; }
        public String getDataTypeNamePlural() 	{ return "Vertexes"; }
        public List getDataList() 				{ return indoorDataMap.getVertexList(); }
        public List getOffsetList() 			{ return null; }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return null; }
        public Component getDataControl(int dataIndex) {
            									return new VertexControl((ShortVertex)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((VertexControl)component).getVertex()); }
        public Component createNewDataControl() {
            									return new VertexControl(createNewVertex()); }
    };
    
    private DataTypeInfo facetDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Facet"; }
        public String getDataTypeNamePlural() 	{ return "Facets"; }
        public List getDataList() 				{ return indoorDataMap.getFaceList(); }
        public List getOffsetList() 			{ return IndoorFace.getOffsetList(indoorDataMap.getGameVersion()); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return IndoorFace.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new IndoorFaceControl((IndoorFace)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((IndoorFaceControl)component).getIndoorFace()); }
        public Component createNewDataControl() {
            									return new IndoorFaceControl(createNewFace()); }
    };
    
    private DataTypeInfo facetDataExtraDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Extra Facet Data"; }
        public String getDataTypeNamePlural() 	{ return "Extra Facet Data"; }
        public List getDataList() 				{ return indoorDataMap.getIndoorFacetExtraDataList(); }
        public List getOffsetList() 			{ return IndoorFacetExtraData.getOffsetList(); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return IndoorFacetExtraData.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new IndoorFacetExtraDataControl((IndoorFacetExtraData)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((IndoorFacetExtraDataControl)component).getIndoorFacetExtraData()); }
        public Component createNewDataControl() {
            									return new IndoorFacetExtraDataControl(createNewIndoorFacetExtraData()); }
    };

    private DataTypeInfo roomDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Room"; }
        public String getDataTypeNamePlural() 	{ return "Rooms"; }
        public List getDataList() 				{ return indoorDataMap.getRoomList(); }
        public List getOffsetList() 			{ return Room.getOffsetList(indoorDataMap.getGameVersion()); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return Room.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new RoomControl((Room)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((RoomControl)component).getRoom()); }
        public Component createNewDataControl() {
            									return new RoomControl(createNewRoom()); }
    };
    
    private DataTypeInfo spriteDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Sprite"; }
        public String getDataTypeNamePlural() 	{ return "Sprites"; }
        public List getDataList() 				{ return indoorDataMap.getSpriteList(); }
        public List getOffsetList() 			{ return Sprite.getOffsetList(indoorDataMap.getGameVersion()); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return Sprite.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new SpriteControl((Sprite)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((SpriteControl)component).getSprite()); }
        public Component createNewDataControl() {
            									return new SpriteControl(createNewSprite()); }
    };
    
    private DataTypeInfo lightSourceDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Light Source"; }
        public String getDataTypeNamePlural() 	{ return "Light Sources"; }
        public List getDataList() 				{ return indoorDataMap.getLightSourcesList(); }
        public List getOffsetList() 			{ return LightSource.getOffsetList(indoorDataMap.getGameVersion()); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return LightSource.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new LightSourceControl((LightSource)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((LightSourceControl)component).getLightSource()); }
        public Component createNewDataControl() {
            									return new LightSourceControl(createNewLightSource()); }
    };
    
    private DataTypeInfo bspNodeDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "BSPNode"; }
        public String getDataTypeNamePlural() 	{ return "BSPNodes"; }
        public List getDataList() 				{ return indoorDataMap.getBspNodeList(); }
        public List getOffsetList() 			{ return null; }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return null; }
        public Component getDataControl(int dataIndex) {
            									return new BSPNodeControl((BSPNode)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((BSPNodeControl)component).getBSPNode()); }
        public Component createNewDataControl() {
            									return new BSPNodeControl(createNewBSPNode()); }
    };
    
    private DataTypeInfo monsterDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Spawn Point"; }
        public String getDataTypeNamePlural() 	{ return "Spawn Points"; }
        public List getDataList() 				{ return indoorDataMap.getSpawnPointList(); }
        public List getOffsetList() 			{ return SpawnPoint.getOffsetList(indoorDataMap.getGameVersion()); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return SpawnPoint.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new SpawnPointControl((SpawnPoint)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((SpawnPointControl)component).getMonster()); }
        public Component createNewDataControl() {
            									return new SpawnPointControl(createNewMonster()); }
    };
    
    private DataTypeInfo mapOutlineLinesDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Map Outline Line"; }
        public String getDataTypeNamePlural() 	{ return "Map Outline Lines"; }
        public List getDataList() 				{ return indoorDataMap.getMapOutlineList(); }
        public List getOffsetList() 			{ return MapOutlineLine.getOffsetList(); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return MapOutlineLine.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new MapOutlineLineControl((MapOutlineLine)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((MapOutlineLineControl)component).getMapOutlineLine()); }
        public Component createNewDataControl() {
            									return new MapOutlineLineControl(createNewMapOutlineLine()); }
    };
}
