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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.table.AbstractTableModel;

import org.gamenet.application.mm8leveleditor.control.D3ObjectControl;
import org.gamenet.application.mm8leveleditor.control.SpawnPointControl;
import org.gamenet.application.mm8leveleditor.control.SpriteControl;
import org.gamenet.application.mm8leveleditor.control.TileSetSelectorControl;
import org.gamenet.application.mm8leveleditor.control.VertexControl;
import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.application.mm8leveleditor.data.mm6.SpawnPoint;
import org.gamenet.application.mm8leveleditor.data.mm6.Sprite;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.D3Object;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.OutdoorDataMap;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.IntVertex;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.TerrainNormalMapData;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.TileSetSelector;
import org.gamenet.swing.controls.ByteTextField;
import org.gamenet.swing.controls.ByteValueHolder;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.DataSection;
import org.gamenet.swing.controls.DataSectionable;
import org.gamenet.swing.controls.IntTextField;
import org.gamenet.swing.controls.IntValueHolder;
import org.gamenet.swing.controls.PixelMapDataSource;
import org.gamenet.swing.controls.ResizingJTable;
import org.gamenet.swing.controls.SwatchPanel;
import org.gamenet.swing.controls.TextMapTableControl;
import org.gamenet.util.ByteConversions;
import org.gamenet.util.TaskObserver;

public class OutdoorDataMapDataSectionable extends BaseDataSectionable implements DataSectionable
{
    private OutdoorDataMap outdoorDataMap = null;
    
    public OutdoorDataMapDataSectionable(OutdoorDataMap srcOutdoorDataMap)
    {
        super();
        
        this.outdoorDataMap = srcOutdoorDataMap;
    }

    public Object getData()
    {
        return outdoorDataMap;
    }

    
    /**
     * @param taskObserver
     * @return
     */
    protected Component getUnknownMappedDataPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Unknown Mapped Data", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getUnknownMappedDataPanel() was interrupted.");

	    abstract class Function
	    {
	        abstract public Object getValue(int row, int col);
	    }

        class SubstitutingIncrementingTableModel extends AbstractTableModel
        {
            private Function function = null;
            
            public SubstitutingIncrementingTableModel(Function f)
            {
                super();
                this.function = f;
            }
            
            public int getColumnCount()
            {
                return 1 + 128;
            }

            public int getRowCount()
            {
                return 1 + 128;
            }

            public String getColumnName(int realColumnIndex)
            {
                int dataColumnIndex = realColumnIndex - 1;
                
                if (0 == realColumnIndex)  return null;
                
                return String.valueOf(dataColumnIndex);
            }
            
            public Object getValueAt(int realRowIndex, int realColumnIndex)
            {
                if (realRowIndex == 0)  return getColumnName(realColumnIndex);
                int dataRowIndex = realRowIndex - 1;
                
                int dataColumnIndex = realColumnIndex - 1;
                
                if (0 == realColumnIndex)  return String.valueOf(dataRowIndex);

                
                return getDataValueAt(dataRowIndex, dataColumnIndex);
            }

            public Object getDataValueAt(int dataRowIndex, int dataColumnIndex)
            {
                return function.getValue(dataRowIndex, dataColumnIndex);
            }
        }

        ResizingJTable shortValueMapTable = new ResizingJTable(new SubstitutingIncrementingTableModel(new Function()
                {
		            public Object getValue(int dataRowIndex, int dataColumnIndex)
		            {
		                short value = outdoorDataMap.getUnknownMapData().getUnknownShortDataAt(dataRowIndex, dataColumnIndex);
		                if (0 == value)  return null;
		                String v = String.valueOf(value);
		                switch (v.length())
		                {
		                    case 1:  return "000" + v;
		                    case 2:  return "00" + v;
		                    case 3:  return "0" + v;
		                    case 4:  return v;
		                    
		                    default: throw new RuntimeException("v[" + v + "].length() = " + v.length());
		                }
		            }
                }));
        
        JPanel unknownDataPanel = new JPanel();
        unknownDataPanel.setLayout(new BoxLayout(unknownDataPanel, BoxLayout.Y_AXIS));
        unknownDataPanel.add(makeNonStretchedPanelFor(new JLabel("See unknown data map tabs for visual representations.")));
        unknownDataPanel.add(makeNonStretchedPanelFor(new JLabel("# of Unknown Objects (after map table): 2? size = " + String.valueOf(outdoorDataMap.getUnknownMapData().getIdData().length / 2))));
        unknownDataPanel.add(makeNonStretchedPanelFor(shortValueMapTable));

        return unknownDataPanel;
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
        textMapsTabbedPane.addTab("Height map", makeNonStretchedPanelFor(new TextMapTableControl(outdoorDataMap.getHeightMap(), outdoorDataMap.getHeightMapOffset())));
        textMapsTabbedPane.addTab("Tile map", makeNonStretchedPanelFor(new TextMapTableControl(outdoorDataMap.getTileMap(), outdoorDataMap.getTileMapOffset())));
        textMapsTabbedPane.addTab("Attribute map", makeNonStretchedPanelFor(new TextMapTableControl(outdoorDataMap.getAttributeMap(), outdoorDataMap.getAttributeMapOffset())));
        textMapsTabbedPane.addTab("ID Offset map", makeNonStretchedPanelFor(new TextMapTableControl(outdoorDataMap.getUnknownMapData().getMapDataAsShort(), outdoorDataMap.getUnknownMapData().getIdDataBaseOffset())));
        
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

        PixelMapDataSource heightPixelMapDataSource = new PixelMapDataSource()
        {
            public int getValueAt(int row, int column)
            {
                return ByteConversions.convertByteToInt(outdoorDataMap.getHeightMap()[row][column]);
            }

            public int getRowCount() { return 128; }
            public int getColumnCount() { return 128; }
        };

        PixelMapDataSource tilePixelMapDataSource = new PixelMapDataSource()
        {
            public int getValueAt(int row, int column)
            {
                return ByteConversions.convertByteToInt(outdoorDataMap.getTileMap()[row][column]);
            }

            public int getRowCount() { return 128; }
            public int getColumnCount() { return 128; }
        };
        
        PixelMapDataSource attributePixelMapDataSource = new PixelMapDataSource()
        {
            public int getValueAt(int row, int column)
            {
                return ByteConversions.convertByteToInt(outdoorDataMap.getAttributeMap()[row][column]);
            }

            public int getRowCount() { return 128; }
            public int getColumnCount() { return 128; }
        };
        
        PixelMapDataSource monstersPixelMapDataSource = new PixelMapDataSource()
        {
            public int getValueAt(int row, int column)
            {
                List monsterList = outdoorDataMap.getSpawnPointList();

                int convertedX = (column * 512) - (64 * 512);
                int convertedY = (row * 512) - (64 * 512);

                for (int index = 0; index < monsterList.size(); ++index)
                {
                    SpawnPoint monster = (SpawnPoint)monsterList.get(index);
                    int monsterX = ByteConversions.getIntegerInByteArrayAtPosition(monster.getMonsterData(), 0);
                    int monsterY = ByteConversions.getIntegerInByteArrayAtPosition(monster.getMonsterData(), 4);
                
                    if ( (Math.abs(monsterX - convertedX) < 256)
                      && (Math.abs(monsterY - convertedY) < 256) )
                    {
                        return 255;
                    }
                }
                
                return 0;
            }

            public int getRowCount() { return 128; }
            public int getColumnCount() { return 128; }
        };

        PixelMapDataSource spriteLocPixelMapDataSource = new PixelMapDataSource()
        {
            public int getValueAt(int row, int column)
            {
                List spriteList = outdoorDataMap.getSpriteList();

                int convertedX = 512 * (column - 64);
                int convertedY = -512 * (row - 64);

                for (int index = 0; index < spriteList.size(); ++index)
                {
                    Sprite sprite = (Sprite)spriteList.get(index);
                    int spriteX = sprite.getX();
                    int spriteY = sprite.getY();
                
                    if ( (Math.abs(spriteX - convertedX) < 256)
                      && (Math.abs(spriteY - convertedY) < 256) )
                    {
                        return 255;
                    }
                }
                
                return 0;
            }

            public int getRowCount() { return 128; }
            public int getColumnCount() { return 128; }
        };

        JTabbedPane pixelMapsTabbedPane = new JTabbedPane();
        pixelMapsTabbedPane.addTab("Height map", makeNonStretchedPanelFor(new SwatchPanel(heightPixelMapDataSource, 0, 4, true)));
        pixelMapsTabbedPane.addTab("Tile map", makeNonStretchedPanelFor(new SwatchPanel(tilePixelMapDataSource, 0, 4, true)));
        pixelMapsTabbedPane.addTab("Attribute map", makeNonStretchedPanelFor(new SwatchPanel(attributePixelMapDataSource, 0, 4, true)));
        pixelMapsTabbedPane.addTab("ID Offset map", new JLabel("unimplemented"));
//        pixelMapsTabbedPane.addTab("sprite pts map", makeNonStretchedPanelFor(new SwatchPanel(spriteLocPixelMapDataSource, 0, 4, true)));
//        pixelMapsTabbedPane.addTab("monster pts map", makeNonStretchedPanelFor(new SwatchPanel(monstersPixelMapDataSource, 0, 4, true)));

        return pixelMapsTabbedPane;
    }

    /**
     * @param taskObserver
     * @return
     */
    protected Component getTilesetPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Tileset", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getTilesetPanel() was interrupted.");

        JPanel tilesetPanel = new JPanel();
	    tilesetPanel.setLayout(new BoxLayout(tilesetPanel, BoxLayout.Y_AXIS));
	    tilesetPanel.add(makeNonStretchedPanelFor(new JLabel("Tilesets: ")));

	    TileSetSelector tileSetSelectorData[] = outdoorDataMap.getTileSetSelectorArray();
        for (int index = 0; index < tileSetSelectorData.length; index++)
        {
            tilesetPanel.add(new TileSetSelectorControl(tileSetSelectorData[index]));
        }

        return tilesetPanel;
    }

    /**
     * @param taskObserver
     * @return
     */
    protected Component getGeneralPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("General", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getGeneralPanel() was interrupted.");
    
        JFormattedTextField blankTextField = new JFormattedTextField(outdoorDataMap.getBlankString());
        blankTextField.setColumns(32);
        JFormattedTextField defaultTextField = new JFormattedTextField(outdoorDataMap.getDefaultOdmString());
        defaultTextField.setColumns(32);
        JFormattedTextField editorTextField = new JFormattedTextField(outdoorDataMap.getEditorString());
        editorTextField.setColumns(32);
        JFormattedTextField skyTextField = new JFormattedTextField(outdoorDataMap.getSkyBitmapName());
        skyTextField.setColumns(32);
        JFormattedTextField groundTextField = new JFormattedTextField(outdoorDataMap.getGroundBitmapName());
        groundTextField.setColumns(32);
      
        blankTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        outdoorDataMap.setBlankString((String)((JFormattedTextField)e.getSource()).getValue());
                    }
                });
                
        defaultTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        outdoorDataMap.setDefaultOdmString((String)((JFormattedTextField)e.getSource()).getValue());
                    }
                });
                
        editorTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        outdoorDataMap.setEditorString((String)((JFormattedTextField)e.getSource()).getValue());
                    }
                });
                
        skyTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        outdoorDataMap.setSkyBitmapName((String)((JFormattedTextField)e.getSource()).getValue());
                    }
                });
                
        groundTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        outdoorDataMap.setGroundBitmapName((String)((JFormattedTextField)e.getSource()).getValue());
                    }
                });
                
        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(new JLabel("BlankString: "));
        labelPane.add(new JLabel("DefaultOdmString: "));
        labelPane.add(new JLabel("EditorString: "));
        labelPane.add(new JLabel("Sky Bitmap Name (design note?): "));
        labelPane.add(new JLabel("Ground Bitmap Name (design note?): "));

        //Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(blankTextField);
        fieldPane.add(defaultTextField);
        fieldPane.add(editorTextField);
        fieldPane.add(skyTextField);
        fieldPane.add(groundTextField);

        if ((GameVersion.MM6 != outdoorDataMap.getGameVersion()) && (GameVersion.MM7 != outdoorDataMap.getGameVersion()))
        {
            IntTextField attributesTextField = new IntTextField(new IntValueHolder() {
                public int getValue() { return outdoorDataMap.getAttributes(); }
                public void setValue(int value) { outdoorDataMap.setAttributes(value); }
            });

            labelPane.add(new JLabel("Attributes: "));
            fieldPane.add(attributesTextField);
            
            ByteTextField masterTileTextField = new ByteTextField(new ByteValueHolder() {
                public byte getValue() { return outdoorDataMap.getMasterTile(); }
                public void setValue(byte value) { outdoorDataMap.setMasterTile(value); }
            });

            labelPane.add(new JLabel("Master Tile: "));
            fieldPane.add(masterTileTextField);
        }
        
        //Put the panels in this panel, labels on left,
        //text fields on right.
        JPanel labelAndFieldPanel = new JPanel(new BorderLayout());
        labelAndFieldPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        labelAndFieldPanel.add(labelPane, BorderLayout.CENTER);
        labelAndFieldPanel.add(fieldPane, BorderLayout.LINE_END);
        
        return labelAndFieldPanel;
    }

    private D3Object createNewD3Object()
    {
        String name1 = "";
        String name2 = "";
        
        D3Object newD3Object = new D3Object(name1, name2);
        
        return newD3Object;
    }

    private SpawnPoint createNewMonster()
    {
        int monsterClass = 1;
        int x = 0;
        int y = 0;
        int z = 0;
        int radius = 0;
        
        SpawnPoint newMonster = new SpawnPoint(outdoorDataMap.getGameVersion(), monsterClass, x, y, z, radius);
        
        return newMonster;
    }

    private Sprite createNewSprite()
    {
        String spriteName = "";
        int x = 0;
        int y = 0;
        int z = 0;
        int eventNumber = 0;

        Sprite newSprite = new Sprite(outdoorDataMap.getGameVersion(), spriteName, x, y, z, eventNumber);
        
        return newSprite;
    }

    public static final String DATA_SECTION_GENERAL = "General";
    public static final String DATA_SECTION_TILESET = "Tileset";
    public static final String DATA_SECTION_PIXEL_MAPS = "Pixel Maps";
    public static final String DATA_SECTION_TEXT_MAPS = "Text Maps";
    public static final String DATA_SECTION_TERRAIN_NORMAL_DATA = "Terrain Normal";
    public static final String DATA_SECTION_3D_OBJECTS = "3D Objects";
    public static final String DATA_SECTION_SPRITES = "Sprites";
    public static final String DATA_SECTION_UNKNOWN_MAPPED_DATA = "Unknown Mapped Data";
    public static final String DATA_SECTION_SPAWN_POINTS = "Spawn Points";

    public static DataSection[] getDataSections()
    {
        return new DataSection[] {
                new DataSection(DATA_SECTION_GENERAL),
                new DataSection(DATA_SECTION_TILESET),
                new DataSection(DATA_SECTION_PIXEL_MAPS),
                new DataSection(DATA_SECTION_TEXT_MAPS),
                new DataSection(DATA_SECTION_TERRAIN_NORMAL_DATA, TerrainNormalMapData.class, TerrainNormalMapDataDataSectionable.class),
                new DataSection(DATA_SECTION_3D_OBJECTS, D3Object.class, D3ObjectDataSectionable.class),
                new DataSection(DATA_SECTION_SPRITES, Sprite.class, SpriteDataSectionable.class),
                new DataSection(DATA_SECTION_UNKNOWN_MAPPED_DATA),
                new DataSection(DATA_SECTION_SPAWN_POINTS, SpawnPoint.class, MonsterDataSectionable.class)
        };
    }

    public DataSection[] getStaticDataSections() { return getDataSections(); }

    public Object getDataForDataSection(DataSection dataSection)
    {
        if (DATA_SECTION_GENERAL == dataSection.getDataSectionName())
        {
            return null;
            // IMPLEMENT: return outdoorDataMap.getGeneral();
        }
        else if (DATA_SECTION_TILESET == dataSection.getDataSectionName())
        {
            return outdoorDataMap.getTileSetSelectorArray();
        }
        else if (DATA_SECTION_PIXEL_MAPS == dataSection.getDataSectionName())
        {
            return null;
            // IMPLEMENT: return outdoorDataMap.getPixelMaps();
        }
        else if (DATA_SECTION_TEXT_MAPS == dataSection.getDataSectionName())
        {
            return null;
            // IMPLEMENT: return outdoorDataMap.getTextMaps();
        }
        else if (DATA_SECTION_TERRAIN_NORMAL_DATA == dataSection.getDataSectionName())
        {
            if (GameVersion.MM6 == outdoorDataMap.getGameVersion())  return null;
            
            return outdoorDataMap.getTerrainNormalData();
        }
        else if (DATA_SECTION_3D_OBJECTS == dataSection.getDataSectionName())
        {
            return outdoorDataMap.getD3ObjectList();
        }
        else if (DATA_SECTION_SPRITES == dataSection.getDataSectionName())
        {
            return outdoorDataMap.getSpriteList();
        }
        else if (DATA_SECTION_UNKNOWN_MAPPED_DATA == dataSection.getDataSectionName())
        {
            return outdoorDataMap.getUnknownMapData();
        }
        else if (DATA_SECTION_SPAWN_POINTS == dataSection.getDataSectionName())
        {
            return outdoorDataMap.getSpawnPointList();
        }
        else throw new IllegalStateException("DataSection " + dataSection.getDataSectionName());

    }

    public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
    {
        if ( (GameVersion.MM6 == outdoorDataMap.getGameVersion())
          && (dataSectionName == DATA_SECTION_TERRAIN_NORMAL_DATA) )  return getNonApplicablePanel(taskObserver);

        if (dataSectionName == DATA_SECTION_GENERAL) { return getGeneralPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_TILESET) { return getTilesetPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_TEXT_MAPS) { return getTextMapsPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_PIXEL_MAPS) { return getPixelMapsPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_UNKNOWN_MAPPED_DATA) { return getUnknownMappedDataPanel(taskObserver); }
        else return super.getListComponent(dataSectionName, taskObserver, list, indexIterator);
    }

    public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
    {
        if ( (GameVersion.MM6 == outdoorDataMap.getGameVersion())
                && (dataSectionName == DATA_SECTION_TERRAIN_NORMAL_DATA) )  return getNonApplicablePanel(taskObserver);

        if (dataSectionName == DATA_SECTION_GENERAL) { return getGeneralPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_TILESET) { return getTilesetPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_TEXT_MAPS) { return getTextMapsPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_PIXEL_MAPS) { return getPixelMapsPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_UNKNOWN_MAPPED_DATA) { return getUnknownMappedDataPanel(taskObserver); }
        else return super.getComponent(dataSectionName, taskObserver);
    }

    public DataTypeInfo getDataTypeInfo(String dataSectionName)
    {
        if (dataSectionName == DATA_SECTION_3D_OBJECTS) { return d3ObjectDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_TERRAIN_NORMAL_DATA) { return vertexDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_SPRITES) { return spriteDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_SPAWN_POINTS) { return monsterDataTypeInfo; }
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
        public List getDataList() 				{ return outdoorDataMap.getTerrainNormalData().getTerrainNormalVertexList(); }
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
    
    private DataTypeInfo d3ObjectDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "3d Object"; }
        public String getDataTypeNamePlural() 	{ return "3d Objects"; }
        public List getDataList() 				{ return outdoorDataMap.getD3ObjectList(); }
        public List getOffsetList() 			{ return D3Object.getOffsetList(); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return D3Object.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new D3ObjectControl((D3Object)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((D3ObjectControl)component).getD3Object()); }
        public Component createNewDataControl() {
            									return new D3ObjectControl(createNewD3Object()); }
    };
    
    private DataTypeInfo spriteDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Sprite"; }
        public String getDataTypeNamePlural() 	{ return "Sprites"; }
        public List getDataList() 				{ return outdoorDataMap.getSpriteList(); }
        public List getOffsetList() 			{ return Sprite.getOffsetList(outdoorDataMap.getGameVersion()); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return Sprite.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new SpriteControl((Sprite)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((SpriteControl)component).getSprite()); }
        public Component createNewDataControl() {
            									return new SpriteControl(createNewSprite()); }
    };
    
    private DataTypeInfo monsterDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Spawn Point"; }
        public String getDataTypeNamePlural() 	{ return "Spawn Points"; }
        public List getDataList() 				{ return outdoorDataMap.getSpawnPointList(); }
        public List getOffsetList() 			{ return SpawnPoint.getOffsetList(outdoorDataMap.getGameVersion()); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return SpawnPoint.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new SpawnPointControl((SpawnPoint)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((SpawnPointControl)component).getMonster()); }
        public Component createNewDataControl() {
            									return new SpawnPointControl(createNewMonster()); }
    };
}
