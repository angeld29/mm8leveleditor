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
import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gamenet.application.mm8leveleditor.control.ChestContentsControl;
import org.gamenet.application.mm8leveleditor.control.CreatureControl;
import org.gamenet.application.mm8leveleditor.control.DateTimeControl;
import org.gamenet.application.mm8leveleditor.control.ItemControl;
import org.gamenet.application.mm8leveleditor.control.MapNoteControl;
import org.gamenet.application.mm8leveleditor.data.mm6.ChestContents;
import org.gamenet.application.mm8leveleditor.data.mm6.Creature;
import org.gamenet.application.mm8leveleditor.data.mm6.Item;
import org.gamenet.application.mm8leveleditor.data.mm6.MapNote;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.DeltaOutdoorDataMap;
import org.gamenet.swing.controls.ByteDataTableControl;
import org.gamenet.swing.controls.ByteTextFieldArrayControl;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.DataSection;
import org.gamenet.swing.controls.DataSectionable;
import org.gamenet.swing.controls.IntTextField;
import org.gamenet.swing.controls.IntTextFieldArrayControl;
import org.gamenet.swing.controls.IntValueHolder;
import org.gamenet.swing.controls.LongValueHolder;
import org.gamenet.swing.controls.PixelMapDataSource;
import org.gamenet.swing.controls.ShortTextFieldArrayControl;
import org.gamenet.swing.controls.StringTextField;
import org.gamenet.swing.controls.StringValueHolder;
import org.gamenet.swing.controls.SwatchPanel;
import org.gamenet.util.TaskObserver;

public class DeltaOutdoorDataMapDataSectionable extends BaseDataSectionable implements DataSectionable
{
    private DeltaOutdoorDataMap deltaOutdoorDataMap = null;
    
    public DeltaOutdoorDataMapDataSectionable(DeltaOutdoorDataMap srcDeltaOutdoorDataMap)
    {
        super();
        
        this.deltaOutdoorDataMap = srcDeltaOutdoorDataMap;
    }

    protected Component getVisibleMapPanel1(TaskObserver taskObserver) throws InterruptedException
    {
        return getVisibleMapPanel(taskObserver, deltaOutdoorDataMap.getVisibleMapData1());
    }

    protected Component getVisibleMapPanel2(TaskObserver taskObserver) throws InterruptedException
    {
        return getVisibleMapPanel(taskObserver, deltaOutdoorDataMap.getVisibleMapData2());
    }

    protected Component getVisibleMapPanel(TaskObserver taskObserver, final byte visibleMapData1[][]) throws InterruptedException
    {
        taskObserver.taskProgress("Visible Map", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getVisibleMapPanel() was interrupted.");

        PixelMapDataSource visiblePixelMapDataSource = new PixelMapDataSource()
        {
            public int getValueAt(int row, int column)
            {
                int byteColumn = column / 8;
                int byteValue = visibleMapData1[row][byteColumn];
                
                int bitColumn = column % 8;
                int value = byteValue & (1 << (7 - bitColumn));
                if (0 != value)  value = 255;

                return value;
            }

            public int getRowCount()
            {
                return DeltaOutdoorDataMap.VISIBLE_MAP_HEIGHT;
            }

            public int getColumnCount()
            {
                return DeltaOutdoorDataMap.VISIBLE_MAP_WIDTH * DeltaOutdoorDataMap.VISIBLE_MAP_PIXELS_PER_BYTE;
            }
        };
        JPanel visibleMapDataPanel = makeNonStretchedPanelFor(new SwatchPanel(visiblePixelMapDataSource, 0, 4, false));
        
        return visibleMapDataPanel;
    }

    protected Component getGeneralPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("General", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getGeneralPanel() was interrupted.");

        JPanel generalDataPanel = new JPanel();
        generalDataPanel.setLayout(new BoxLayout(generalDataPanel, BoxLayout.Y_AXIS));

        JPanel mapResetCountPanel = makeNonStretchedPanelFor(new JLabel("Map Reset Count"));
        generalDataPanel.add(mapResetCountPanel);
        mapResetCountPanel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getMapResetCount(); }
            public void setValue(int value) { deltaOutdoorDataMap.setMapResetCount(value); }
        }));

        JPanel lastResetDayPanel = makeNonStretchedPanelFor(new JLabel("Last Reset Day"));
        generalDataPanel.add(lastResetDayPanel);
        lastResetDayPanel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getLastResetDay(); }
            public void setValue(int value) { deltaOutdoorDataMap.setLastResetDay(value); }
        }));


        JPanel stealingDifficultyAdjustmentPanel = makeNonStretchedPanelFor(new JLabel("Stealing Difficulty Adjustment"));
        generalDataPanel.add(stealingDifficultyAdjustmentPanel);
        stealingDifficultyAdjustmentPanel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getStealingDifficultyAdjustment(); }
            public void setValue(int value) { deltaOutdoorDataMap.setStealingDifficultyAdjustment(value); }
        }));

        JPanel mapAlertStatusPanel = makeNonStretchedPanelFor(new JLabel("Map Alert Status"));
        generalDataPanel.add(mapAlertStatusPanel);
        mapAlertStatusPanel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getMapAlertStatus(); }
            public void setValue(int value) { deltaOutdoorDataMap.setMapAlertStatus(value); }
        }));

        JPanel facetsInD3ObjectsCountPanel = makeNonStretchedPanelFor(new JLabel("Total Number Of Facets In D3Objects"));
        generalDataPanel.add(facetsInD3ObjectsCountPanel);
        facetsInD3ObjectsCountPanel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getTotalNumberOfFacetsInD3Objects(); }
            public void setValue(int value) { deltaOutdoorDataMap.setTotalNumberOfFacetsInD3Objects(value); }
        }));

        JPanel spriteCountPanel = makeNonStretchedPanelFor(new JLabel("Total Number Of Sprites"));
        generalDataPanel.add(spriteCountPanel);
        spriteCountPanel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getTotalNumberOfSprites(); }
            public void setValue(int value) { deltaOutdoorDataMap.setTotalNumberOfSprites(value); }
        }));

        JPanel d3ObjectCountPanel = makeNonStretchedPanelFor(new JLabel("Total Number Of D3Objects"));
        generalDataPanel.add(d3ObjectCountPanel);
        d3ObjectCountPanel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getTotalNumberOfD3Objects(); }
            public void setValue(int value) { deltaOutdoorDataMap.setTotalNumberOfD3Objects(value); }
        }));

        JPanel mapNotesPanel = makeNonStretchedPanelFor(new JLabel("Map Notes Offset"));
        generalDataPanel.add(mapNotesPanel);
        mapNotesPanel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getMapNotesOffset(); }
            public void setValue(int value) { deltaOutdoorDataMap.setMapNotesOffset(value); }
        }));

        JPanel reserved1Panel = makeNonStretchedPanelFor(new JLabel("Reserved1"));
        generalDataPanel.add(reserved1Panel);
        reserved1Panel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getReserved1(); }
            public void setValue(int value) { deltaOutdoorDataMap.setReserved1(value); }
        }));

        JPanel reserved2Panel = makeNonStretchedPanelFor(new JLabel("Reserved2"));
        generalDataPanel.add(reserved2Panel);
        reserved2Panel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getReserved2(); }
            public void setValue(int value) { deltaOutdoorDataMap.setReserved2(value); }
        }));
        
        JPanel dayAttributePanel = makeNonStretchedPanelFor(new JLabel("Day Attribute"));
        generalDataPanel.add(dayAttributePanel);
        dayAttributePanel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getDayAttribute(); }
            public void setValue(int value) { deltaOutdoorDataMap.setDayAttribute(value); }
        }));

        JPanel fogRange1Panel = makeNonStretchedPanelFor(new JLabel("Fog Range 1"));
        generalDataPanel.add(fogRange1Panel);
        fogRange1Panel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getFogRange1(); }
            public void setValue(int value) { deltaOutdoorDataMap.setFogRange1(value); }
        }));

        JPanel fogRange2Panel = makeNonStretchedPanelFor(new JLabel("Fog Range 2"));
        generalDataPanel.add(fogRange2Panel);
        fogRange2Panel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getFogRange2(); }
            public void setValue(int value) { deltaOutdoorDataMap.setFogRange2(value); }
        }));

        JPanel attributesPanel = makeNonStretchedPanelFor(new JLabel("Attributes"));
        generalDataPanel.add(attributesPanel);
        attributesPanel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getAttributes(); }
            public void setValue(int value) { deltaOutdoorDataMap.setAttributes(value); }
        }));

        JPanel ceilingPanel = makeNonStretchedPanelFor(new JLabel("Ceiling"));
        generalDataPanel.add(ceilingPanel);
        ceilingPanel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getCeiling(); }
            public void setValue(int value) { deltaOutdoorDataMap.setCeiling(value); }
        }));

        JPanel lastTimeVisitedPanel = makeNonStretchedPanelFor(new JLabel("Last Visited Time"));
        generalDataPanel.add(lastTimeVisitedPanel);
        lastTimeVisitedPanel.add(new DateTimeControl(new LongValueHolder() {
            public long getValue() { return deltaOutdoorDataMap.getLastVisitedTime(); }
            public void setValue(long value) { deltaOutdoorDataMap.setLastVisitedTime(value); }
        }));

        JPanel skyBitmapNamePanel = makeNonStretchedPanelFor(new JLabel("Sky Bitmap Name"));
        generalDataPanel.add(skyBitmapNamePanel);
        skyBitmapNamePanel.add(new StringTextField(new StringValueHolder() {
            public String getValue() { return deltaOutdoorDataMap.getSkyBitmapName(); }
            public void setValue(String value) { deltaOutdoorDataMap.setSkyBitmapName(value); }
            public int getMaxLength() { return deltaOutdoorDataMap.getSkyBitmapNameMaxLength(); }
        }));

        JPanel activeMapNoteCountPanel = makeNonStretchedPanelFor(new JLabel("active MapNote Count"));
        generalDataPanel.add(activeMapNoteCountPanel);
        activeMapNoteCountPanel.add(new IntTextField(new IntValueHolder() {
            public int getValue() { return deltaOutdoorDataMap.getActiveMapNoteCount(); }
            public void setValue(int value) { deltaOutdoorDataMap.setActiveMapNoteCount(value); }
        }));

        return generalDataPanel;
    }

    public Component getMapBitsPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Map bits", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getMapBitsPanel() was interrupted.");

        JPanel mapBitsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ByteTextFieldArrayControl mapBitControl = new ByteTextFieldArrayControl(deltaOutdoorDataMap.getMapBits(), "MapBit");
		mapBitsPanel.add(mapBitControl);
		
        return mapBitsPanel;
    }

    public Component getFacetAttributesPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Facet Attributes", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getFacetAttributesPanel() was interrupted.");

        JPanel facetAttributesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        IntTextFieldArrayControl facetAttributesControl = new IntTextFieldArrayControl(deltaOutdoorDataMap.getFacetAttributeArray(), "Facet Attributes");
		facetAttributesPanel.add(facetAttributesControl);
		
        return facetAttributesPanel;
    }

    public Component getSpriteAttributesPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Sprite Attributes", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getSpriteAttributesPanel() was interrupted.");

        JPanel spriteAttributesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ShortTextFieldArrayControl spriteAttributesControl = new ShortTextFieldArrayControl(deltaOutdoorDataMap.getSpriteAttributeArray(), "Sprite Attributes");
		spriteAttributesPanel.add(spriteAttributesControl);
		
        return spriteAttributesPanel;
    }

    protected Component getRemainingDataPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Remaining Data", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getRemainingDataPanel() was interrupted.");

        JPanel remainingDataPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        byte remainingData[] = deltaOutdoorDataMap.getRemainingData();
		ByteDataTableControl remainingDataBDTC = new ByteDataTableControl(remainingData, 32, deltaOutdoorDataMap.getRemainingDataOffset());
		remainingDataPanel.add(makeNonStretchedPanelFor(remainingDataBDTC));

        return remainingDataPanel;
    }

    public Object getData()
    {
        return deltaOutdoorDataMap;
    }

    private Creature createNewCreature()
    {
        String creatureName = "";
        Creature newCreature = new Creature(deltaOutdoorDataMap.getGameVersion(), creatureName);
        
        return newCreature;
    }

    private Item createNewItem()
    {
        int itemId = 0;
        int itemNumber = 0;
        int pictureNumber = 0;
        int x = 0;
        int y = 0;
        int z = 0;
        int stdMagicClass = 0;
        int stdMagicBonus = 0;
        int amountOfGold = 0;
        int charges = 0;
        
        Item newItem = new Item(deltaOutdoorDataMap.getGameVersion(), itemNumber, pictureNumber, x, y, z, stdMagicClass, stdMagicBonus, amountOfGold, charges);
        
        return newItem;
    }

    private ChestContents createNewChestContents()
    {
        int openedStatus = ChestContents.OPENED_STATUS_UNOPENED_UNTRAPPED_UNPLACED;
        ChestContents newChestContents = new ChestContents(deltaOutdoorDataMap.getGameVersion(), openedStatus);
        
        return newChestContents;
    }

    private MapNote createNewMapNote()
    {
        short id = 0;
        boolean active = false;
        short x = 0;
        short y = 0;
        String text = "";
        MapNote newMapNote = new MapNote(id, active, x, y, text);
        
        return newMapNote;
    }

    public static final String DATA_SECTION_UNKNOWN0 = "General";
    public static final String DATA_SECTION_VISIBLE_MAP_1 = "Visible map 1";
    public static final String DATA_SECTION_VISIBLE_MAP_2 = "Visible map 2";
    public static final String DATA_SECTION_FACET_ATTRIBUTES = "Facet Attributes";
    public static final String DATA_SECTION_SPRITE_ATTRIBUTES = "Sprite Attributes";
    public static final String DATA_SECTION_CREATURES = "Creatures";
    public static final String DATA_SECTION_ITEMS = "Items";
    public static final String DATA_SECTION_CHEST_CONTENTS = "Chest Contents";
    public static final String DATA_SECTION_MAP_BITS = "Map bits";
    public static final String DATA_SECTION_REMAINING_DATA = "Remaining Data";
    public static final String DATA_SECTION_MAP_NOTES = "Map Notes";

    public static DataSection[] getDataSections()
    {
        return new DataSection[] {
                new DataSection(DATA_SECTION_UNKNOWN0),
                new DataSection(DATA_SECTION_VISIBLE_MAP_1),
                new DataSection(DATA_SECTION_VISIBLE_MAP_2),
                new DataSection(DATA_SECTION_FACET_ATTRIBUTES),
                new DataSection(DATA_SECTION_SPRITE_ATTRIBUTES),
                new DataSection(DATA_SECTION_CREATURES, Creature.class, CreatureDataSectionable.class),
                new DataSection(DATA_SECTION_ITEMS, Item.class, ItemDataSectionable.class),
                new DataSection(DATA_SECTION_CHEST_CONTENTS, ChestContents.class, ChestContentsDataSectionable.class),
                new DataSection(DATA_SECTION_MAP_BITS),
                new DataSection(DATA_SECTION_REMAINING_DATA),
                new DataSection(DATA_SECTION_MAP_NOTES, MapNote.class, MapNoteDataSectionable.class)
        };
    }
    
    public DataSection[] getStaticDataSections() { return getDataSections(); }

    public Object getDataForDataSection(DataSection dataSection)
    {
        String dataSectionName = dataSection.getDataSectionName();
        if (dataSectionName == DATA_SECTION_UNKNOWN0)
        {
            return null;
            // IMPLEMENT: return deltaOutdoorDataMap.getGeneral();
        }
        else if (dataSectionName == DATA_SECTION_VISIBLE_MAP_1) { return deltaOutdoorDataMap.getVisibleMapData1(); }
        else if (dataSectionName == DATA_SECTION_VISIBLE_MAP_2) { return deltaOutdoorDataMap.getVisibleMapData2(); }
        else if (dataSectionName == DATA_SECTION_FACET_ATTRIBUTES) { return deltaOutdoorDataMap.getFacetAttributeArray(); }
        else if (dataSectionName == DATA_SECTION_SPRITE_ATTRIBUTES) { return deltaOutdoorDataMap.getSpriteAttributeArray(); }
        else if (dataSectionName == DATA_SECTION_CREATURES) { return deltaOutdoorDataMap.getCreatureList(); }
        else if (dataSectionName == DATA_SECTION_ITEMS) { return deltaOutdoorDataMap.getItemList(); }
        else if (dataSectionName == DATA_SECTION_CHEST_CONTENTS) { return deltaOutdoorDataMap.getChestContentsList(); }
        else if (dataSectionName == DATA_SECTION_MAP_BITS) { return deltaOutdoorDataMap.getMapBits(); }
        else if (dataSectionName == DATA_SECTION_REMAINING_DATA) { return deltaOutdoorDataMap.getRemainingData(); }
        else if (dataSectionName == DATA_SECTION_MAP_NOTES) { return deltaOutdoorDataMap.getMapNoteList(); }
        else throw new IllegalStateException("DataSection " + dataSectionName);
    }

    public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
    {
        if (dataSectionName == DATA_SECTION_UNKNOWN0) { return getGeneralPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_VISIBLE_MAP_1) { return getVisibleMapPanel1(taskObserver); }
        else if (dataSectionName == DATA_SECTION_VISIBLE_MAP_2) { return getVisibleMapPanel2(taskObserver); }
        else if (dataSectionName == DATA_SECTION_FACET_ATTRIBUTES) { return getFacetAttributesPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_SPRITE_ATTRIBUTES) { return getSpriteAttributesPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_MAP_BITS) { return getMapBitsPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_REMAINING_DATA) { return getRemainingDataPanel(taskObserver); }
        else return super.getComponent(dataSectionName, taskObserver);
    }

    public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
    {
        if (dataSectionName == DATA_SECTION_UNKNOWN0) { return getGeneralPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_VISIBLE_MAP_1) { return getVisibleMapPanel1(taskObserver); }
        else if (dataSectionName == DATA_SECTION_VISIBLE_MAP_2) { return getVisibleMapPanel2(taskObserver); }
        else if (dataSectionName == DATA_SECTION_FACET_ATTRIBUTES) { return getFacetAttributesPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_SPRITE_ATTRIBUTES) { return getSpriteAttributesPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_MAP_BITS) { return getMapBitsPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_REMAINING_DATA) { return getRemainingDataPanel(taskObserver); }
        else return super.getListComponent(dataSectionName, taskObserver, list, indexIterator);
    }
    
    public DataTypeInfo getDataTypeInfo(String dataSectionName)
    {
        if (dataSectionName == DATA_SECTION_CREATURES) { return creatureDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_ITEMS) { return itemDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_CHEST_CONTENTS) { return chestContentsDataTypeInfo; }
        else if (dataSectionName == DATA_SECTION_MAP_NOTES) { return mapNoteDataTypeInfo; }
        else throw new IllegalStateException("DataSection " + dataSectionName);
    }
    
    private DataTypeInfo creatureDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Creature"; }
        public String getDataTypeNamePlural() 	{ return "Creatures"; }
        public List getDataList() 				{ return deltaOutdoorDataMap.getCreatureList(); }
        public List getOffsetList() 			{ return Creature.getOffsetList(deltaOutdoorDataMap.getGameVersion()); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return Creature.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new CreatureControl((Creature)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((CreatureControl)component).getCreature()); }
        public Component createNewDataControl() {
            									return new CreatureControl(createNewCreature()); }
    };
    
    private DataTypeInfo itemDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Item"; }
        public String getDataTypeNamePlural() 	{ return "Items"; }
        public List getDataList() 				{ return deltaOutdoorDataMap.getItemList(); }
        public List getOffsetList() 			{ return Item.getOffsetList(deltaOutdoorDataMap.getGameVersion()); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return Item.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new ItemControl((Item)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((ItemControl)component).getItem()); }
        public Component createNewDataControl() {
            									return new ItemControl(createNewItem()); }
    };
    
    private DataTypeInfo chestContentsDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "ChestContents"; }
        public String getDataTypeNamePlural() 	{ return "ChestContents"; }
        public List getDataList() 				{ return deltaOutdoorDataMap.getChestContentsList(); }
        public List getOffsetList() 			{ return ChestContents.getRemainingDataOffsetList(); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return ChestContents.getRemainingDataComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new ChestContentsControl((ChestContents)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((ChestContentsControl)component).getChestContents()); }
        public Component createNewDataControl() {
            									return new ChestContentsControl(createNewChestContents()); }
    };
    
    private DataTypeInfo mapNoteDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "Map Note"; }
        public String getDataTypeNamePlural() 	{ return "Map Notes"; }
        public List getDataList() 				{ return deltaOutdoorDataMap.getMapNoteList(); }
        public List getOffsetList() 			{ return MapNote.getOffsetList(); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
            									return MapNote.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int dataIndex) {
            									return new MapNoteControl((MapNote)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((MapNoteControl)component).getMapNote()); }
        public Component createNewDataControl() {
            									return new MapNoteControl(createNewMapNote()); }
    };
}
