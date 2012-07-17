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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.gamenet.application.mm8leveleditor.control.ActiveSpellControl;
import org.gamenet.application.mm8leveleditor.control.DateTimeControl;
import org.gamenet.application.mm8leveleditor.control.ItemContainerControl;
import org.gamenet.application.mm8leveleditor.control.LloydsBeaconControl;
import org.gamenet.application.mm8leveleditor.data.ByteData;
import org.gamenet.application.mm8leveleditor.data.mm6.ActiveSpell;
import org.gamenet.application.mm8leveleditor.data.mm6.CharacterData;
import org.gamenet.application.mm8leveleditor.data.mm6.ItemContainer;
import org.gamenet.application.mm8leveleditor.data.mm6.LloydsBeacon;
import org.gamenet.swing.controls.ByteDataTableControl;
import org.gamenet.swing.controls.CheckBoxArrayControl;
import org.gamenet.swing.controls.DataSection;
import org.gamenet.swing.controls.DataSectionable;
import org.gamenet.swing.controls.IntTextField;
import org.gamenet.swing.controls.IntValueHolder;
import org.gamenet.swing.controls.LongValueHolder;
import org.gamenet.swing.controls.ShortTextField;
import org.gamenet.swing.controls.ShortValueHolder;
import org.gamenet.swing.controls.StateArrayValueHolder;
import org.gamenet.swing.controls.StringComboBox;
import org.gamenet.swing.controls.StringTextField;
import org.gamenet.swing.controls.StringValueHolder;
import org.gamenet.util.TaskObserver;

public class CharacterDataDataSectionable extends BaseDataSectionable implements DataSectionable
{
    private CharacterData characterData = null;
    
    public CharacterDataDataSectionable(CharacterData srcCharacterData)
    {
        super();
        
        this.characterData = srcCharacterData;
    }
    

    public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
    {
        List indexNumberList = listForIterator(indexIterator);
        Integer indexNumber = (Integer)indexNumberList.get(0);
        int index = indexNumber.intValue();
        
        if (dataSectionName == DATA_SECTION_ACTIVE_SPELLS)
        {
            if (1 == indexNumberList.size())
            {
                return new ActiveSpellControl((ActiveSpell)list.get(index), characterData.getActiveSpellNameArray()[index]);
            }
            else
            {
                return getActiveSpellsPanel(taskObserver, indexNumberList);
            }
        }
        else if (dataSectionName == DATA_SECTION_LLOYDS_BEACON)
        {
            if (1 == indexNumberList.size())
            {
                return new LloydsBeaconControl((LloydsBeacon)list.get(index));
            }
            else
            {
                return getLloydsBeaconPanel(taskObserver, indexNumberList);
            }
        }
        else if (dataSectionName == DATA_SECTION_UNKNOWNS)
        {
            if (1 == indexNumberList.size())
            {
                ByteData byteData = (ByteData)list.get(index);
                return new ByteDataTableControl(byteData.getData(), 32, byteData.getExternalOffset(), byteData.getStartOffset(), byteData.getEndOffset());
            }
            else
            {
                return getUnknownsPanel(taskObserver, indexNumberList, characterData.getUnknownByteDataList());
            }
        }
        else throw new IllegalStateException("DataSection " + dataSectionName);
    }

    public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
    {
        if (dataSectionName == DATA_SECTION_GENERAL) { return getGeneralPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_SKILLS) { return getSkillsPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_KNOWN_SPELLS) { return getKnownSpellsPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_AWARDS) { return getAwardsPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_CONDITIONS) { return getConditionsPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_ACTIVE_SPELLS) { return getActiveSpellsPanel(taskObserver, null); }
        else if (dataSectionName == DATA_SECTION_ITEMS) { return new ItemContainerControl(characterData.getItemContainer()); }
        else if (dataSectionName == DATA_SECTION_LLOYDS_BEACON) { return getLloydsBeaconPanel(taskObserver, null); }
        else if (dataSectionName == DATA_SECTION_UNKNOWNS) { return getUnknownsPanel(taskObserver, null, characterData.getUnknownByteDataList()); }
        else throw new IllegalStateException("DataSection " + dataSectionName);
    }
  
    public Component getGeneralPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("General", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getGeneralPanel() was interrupted.");

        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.Y_AXIS));
        
        JPanel labelPanel = new JPanel(new GridLayout(0,1));
        JPanel controlPanel = new JPanel(new GridLayout(0,1));

        JPanel labelAndControlPanel = new JPanel(new BorderLayout());
        labelAndControlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        labelAndControlPanel.add(labelPanel, BorderLayout.LINE_START);
        labelAndControlPanel.add(controlPanel, BorderLayout.CENTER);
        generalPanel.add(labelAndControlPanel);
        
        labelPanel.add(new JLabel("Name:"));
        controlPanel.add(new StringTextField(new StringValueHolder() {
            public String getValue() { return characterData.getCharacterName(); }
            public void setValue(String value) { characterData.setCharacterName(value); }
            public int getMaxLength() { return CharacterData.getCharacterNameMaxlength(); }
        }));
        
        labelPanel.add(new JLabel("Picture#"));
        controlPanel.add(new IntTextField(3, new IntValueHolder() {
            public int getValue() { return characterData.getPictureNumber(); }
            public void setValue(int value) { characterData.setPictureNumber(value); }
        }));

        labelPanel.add(new JLabel("Sex"));
        controlPanel.add(new StringComboBox(characterData.getSexOptions(), new IntValueHolder() {
            public int getValue() { return characterData.getSex(); }
            public void setValue(int value) { characterData.setSex(value); }
        }));

        labelPanel.add(new JLabel("Class"));
        controlPanel.add(new IntTextField(3, new IntValueHolder() {
            public int getValue() { return characterData.getCharacterClass(); }
            public void setValue(int value) { characterData.setCharacterClass(value); }
        }));

        labelPanel.add(new JLabel("Might"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getMight(); }
            public void setValue(int value) { characterData.setMight(value); }
        }));

        labelPanel.add(new JLabel("Might Adjustment"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getMightAdjustment(); }
            public void setValue(int value) { characterData.setMightAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Intellect"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getIntellect(); }
            public void setValue(int value) { characterData.setIntellect(value); }
        }));

        labelPanel.add(new JLabel("Intellect Adjustment"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getIntellectAdjustment(); }
            public void setValue(int value) { characterData.setIntellectAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Personality"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getPersonality(); }
            public void setValue(int value) { characterData.setPersonality(value); }
        }));

        labelPanel.add(new JLabel("Personality Adjustment"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getPersonalityAdjustment(); }
            public void setValue(int value) { characterData.setPersonalityAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Accuracy"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getAccuracy(); }
            public void setValue(int value) { characterData.setAccuracy(value); }
        }));

        labelPanel.add(new JLabel("Accuracy Adjustment"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getAccuracyAdjustment(); }
            public void setValue(int value) { characterData.setAccuracyAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Speed"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getSpeed(); }
            public void setValue(int value) { characterData.setSpeed(value); }
        }));

        labelPanel.add(new JLabel("Speed Adjustment"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getSpeedAdjustment(); }
            public void setValue(int value) { characterData.setSpeedAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Endurance"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getEndurance(); }
            public void setValue(int value) { characterData.setEndurance(value); }
        }));

        labelPanel.add(new JLabel("Endurance Adjustment"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getEnduranceAdjustment(); }
            public void setValue(int value) { characterData.setEnduranceAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Luck"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getLuck(); }
            public void setValue(int value) { characterData.setLuck(value); }
        }));

        labelPanel.add(new JLabel("Luck Adjustment"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getLuckAdjustment(); }
            public void setValue(int value) { characterData.setLuckAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Armor Class Adjustment"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getArmorClassAdjustment(); }
            public void setValue(int value) { characterData.setArmorClassAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Age Adjustment"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getAgeAdjustment(); }
            public void setValue(int value) { characterData.setAgeAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Level"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getLevel(); }
            public void setValue(int value) { characterData.setLevel(value); }
        }));

        labelPanel.add(new JLabel("Level Adjustment"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getLevelAdjustment(); }
            public void setValue(int value) { characterData.setLevelAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Fire Resistance"));
        controlPanel.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return characterData.getFireResistance(); }
            public void setValue(short value) { characterData.setFireResistance(value); }
        }));

        labelPanel.add(new JLabel("Fire Resistance Adjustment"));
        controlPanel.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return characterData.getFireResistanceAdjustment(); }
            public void setValue(short value) { characterData.setFireResistanceAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Cold Resistance"));
        controlPanel.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return characterData.getColdResistance(); }
            public void setValue(short value) { characterData.setColdResistance(value); }
        }));

        labelPanel.add(new JLabel("Cold Resistance Adjustment"));
        controlPanel.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return characterData.getColdResistanceAdjustment(); }
            public void setValue(short value) { characterData.setColdResistanceAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Electrical Resistance"));
        controlPanel.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return characterData.getElectricalResistance(); }
            public void setValue(short value) { characterData.setElectricalResistance(value); }
        }));

        labelPanel.add(new JLabel("Electrical Resistance Adjustment"));
        controlPanel.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return characterData.getElectricalResistanceAdjustment(); }
            public void setValue(short value) { characterData.setElectricalResistanceAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Poison Resistance"));
        controlPanel.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return characterData.getPoisonResistance(); }
            public void setValue(short value) { characterData.setPoisonResistance(value); }
        }));

        labelPanel.add(new JLabel("Poison Resistance Adjustment"));
        controlPanel.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return characterData.getPoisonResistanceAdjustment(); }
            public void setValue(short value) { characterData.setPoisonResistanceAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Magic Resistance"));
        controlPanel.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return characterData.getMagicResistance(); }
            public void setValue(short value) { characterData.setMagicResistance(value); }
        }));

        labelPanel.add(new JLabel("Magic Resistance Adjustment"));
        controlPanel.add(new ShortTextField(new ShortValueHolder() {
            public short getValue() { return characterData.getMagicResistanceAdjustment(); }
            public void setValue(short value) { characterData.setMagicResistanceAdjustment(value); }
        }));

        labelPanel.add(new JLabel("Ticks Before Ready"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getTicksBeforeReady(); }
            public void setValue(int value) { characterData.setTicksBeforeReady(value); }
        }));

        labelPanel.add(new JLabel("Skill Points"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getSkillPoints(); }
            public void setValue(int value) { characterData.setSkillPoints(value); }
        }));

        labelPanel.add(new JLabel("Hit Points"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getHitPoints(); }
            public void setValue(int value) { characterData.setHitPoints(value); }
        }));

        labelPanel.add(new JLabel("Spell Points"));
        controlPanel.add(new IntTextField(5, new IntValueHolder() {
            public int getValue() { return characterData.getSpellPoints(); }
            public void setValue(int value) { characterData.setSpellPoints(value); }
        }));

        labelPanel.add(new JLabel("Year of Birth"));
        controlPanel.add(new IntTextField(4, new IntValueHolder() {
            public int getValue() { return characterData.getYearOfBirth(); }
            public void setValue(int value) { characterData.setYearOfBirth(value); }
        }));

        labelPanel.add(new JLabel("Quick Spell"));
        controlPanel.add(new IntTextField(4, new IntValueHolder() {
            public int getValue() { return characterData.getQuickSpell(); }
            public void setValue(int value) { characterData.setQuickSpell(value); }
        }));
        
        return generalPanel;
    }
    
    public Component getAwardsPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Awards", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getAwardsPanel() was interrupted.");

        // awards
        JPanel awardsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		CheckBoxArrayControl awardControl = new CheckBoxArrayControl(new StateArrayValueHolder()
        {
            public String getValueLabel(int index) { return "Award #" + String.valueOf(index + 1); }
            public boolean getValue(int index) { return characterData.getAward(index); }
            public void setValue(boolean value, int index) { characterData.setAward(value, index); }
            public int getStartIndex() { return 0; }
            public int getEndIndex() { return characterData.getAwardCount() - 1; }
        });
		awardsPanel.add(awardControl);
        
        return awardsPanel;
    }
    
    public Component getSkillsPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Skills", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getSkillsPanel() was interrupted.");

		// Skills
        JPanel skillsPanel = new JPanel();
        skillsPanel.setLayout(new BoxLayout(skillsPanel, BoxLayout.Y_AXIS));
		for (int skillIndex = 0; skillIndex < characterData.getSkillCount(); ++skillIndex)
		{
            final int finalizedIndex = skillIndex;
            JPanel skillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            skillPanel.add(new JLabel(characterData.getSkillName(skillIndex)));
            skillPanel.add(new StringComboBox(characterData.getSkillRankingOptions(), new IntValueHolder()
            {
                public int getValue() { return characterData.getSkillRanking(finalizedIndex); }
                public void setValue(int value) { characterData.setSkillRanking(value, finalizedIndex); }
            }));
            skillPanel.add(new IntTextField(new IntValueHolder()
	        {
                public int getValue() { return characterData.getSkillPoints(finalizedIndex); }
                public void setValue(int value) { characterData.setSkillPoints(value, finalizedIndex); }
            }));
            skillsPanel.add(skillPanel);
		}
        
        return skillsPanel;
    }
    
    public Component getKnownSpellsPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Known Spells", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getKnownSpellsPanel() was interrupted.");

		// Known Spells
        JTabbedPane knownSpellsPanel = new JTabbedPane();
        for (int schoolIndex = 0; schoolIndex < characterData.getSpellSchoolCount(); ++schoolIndex)
        {
            final int finalizedSchoolIndex = schoolIndex;
            JPanel knownSpellInSchoolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    		CheckBoxArrayControl knownSpellsControl = new CheckBoxArrayControl(new StateArrayValueHolder()
            {
                public String getValueLabel(int spellIndex) { return characterData.getSpellNameBySchool(finalizedSchoolIndex, spellIndex); }
                public boolean getValue(int spellIndex) { return characterData.getKnowsSpell(finalizedSchoolIndex, spellIndex); }
                public void setValue(boolean value, int spellIndex) { characterData.setKnowsSpell(value, finalizedSchoolIndex, spellIndex); }
                public int getStartIndex() { return 0; }
                public int getEndIndex() { return characterData.getSpellsPerSchoolCount() - 1; }
            });
    		knownSpellsPanel.add(characterData.getSpellSchoolNameArray()[schoolIndex], knownSpellsControl);
        }
        
        return knownSpellsPanel;
    }
    
    public Component getConditionsPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Conditions", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getConditionsPanel() was interrupted.");

		// Conditions
        JPanel conditionsPanel = new JPanel();
        conditionsPanel.setLayout(new BoxLayout(conditionsPanel, BoxLayout.Y_AXIS));
		for (int conditionIndex = 0; conditionIndex < characterData.getConditionsCount(); ++conditionIndex)
		{
            final int finalizedIndex = conditionIndex;
            JPanel conditionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            conditionPanel.add(new JLabel(characterData.getConditionName(conditionIndex)));
            conditionPanel.add(new DateTimeControl(new LongValueHolder()
	        {
                public long getValue() { return characterData.getConditionStartTime(finalizedIndex); }
                public void setValue(long value) { characterData.setConditionStartTime(value, finalizedIndex); }
            }));
            conditionsPanel.add(conditionPanel);
		}
        
        return conditionsPanel;
    }
    
    public Component getActiveSpellsPanel(TaskObserver taskObserver, List indexNumberList) throws InterruptedException
    {
        taskObserver.taskProgress("Active Spells", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getActiveSpellsPanel() was interrupted.");

		// Active Spells
        JPanel activeSpellsPanel = new JPanel();
        activeSpellsPanel.setLayout(new BoxLayout(activeSpellsPanel, BoxLayout.Y_AXIS));
		List activeSpellList = characterData.getActiveSpellList();
		if (null == indexNumberList)  indexNumberList = createListForSequence(activeSpellList.size());
		String activePartySpellName[] = characterData.getActiveSpellNameArray();
		Iterator indexIterator = indexNumberList.iterator();
        while (indexIterator.hasNext())
        {
            Integer indexInteger = (Integer) indexIterator.next();
            int activeSpellIndex = indexInteger.intValue();

		    ActiveSpell activeSpell = (ActiveSpell)activeSpellList.get(activeSpellIndex);
		    activeSpellsPanel.add(new ActiveSpellControl(activeSpell, activePartySpellName[activeSpellIndex]));
            
            taskObserver.taskProgress("Active Spells", 0.1f + (((0.7f - 0.1f) / indexNumberList.size()) * activeSpellIndex));
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("getActiveSpellsPanel() was interrupted.");
		}
        
        return activeSpellsPanel;
    }
    
    public Component getLloydsBeaconPanel(TaskObserver taskObserver, List indexNumberList) throws InterruptedException
    {
        taskObserver.taskProgress("Lloyds Beacon", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getLloydsBeaconPanel() was interrupted.");

		// Lloyd's Beacons
        JPanel lloydsBeaconPanel = new JPanel();
        lloydsBeaconPanel.setLayout(new BoxLayout(lloydsBeaconPanel, BoxLayout.Y_AXIS));
        List lloydsPartyBeaconList = characterData.getLloydsBeaconList();
		if (null == indexNumberList)  indexNumberList = createListForSequence(lloydsPartyBeaconList.size());
		Iterator indexIterator = indexNumberList.iterator();
        while (indexIterator.hasNext())
        {
            Integer indexInteger = (Integer) indexIterator.next();
            int lloydsBeaconIndex = indexInteger.intValue();

		    LloydsBeacon lloydsBeacon = (LloydsBeacon)lloydsPartyBeaconList.get(lloydsBeaconIndex);
		    lloydsBeaconPanel.add(new LloydsBeaconControl(lloydsBeacon));
            
            taskObserver.taskProgress("Lloyds Beacon", 0.1f + (((0.7f - 0.1f) / indexNumberList.size()) * lloydsBeaconIndex));
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("getLloydsBeaconPanel() was interrupted.");
		}
        
        return lloydsBeaconPanel;
    }
    
    public DataTypeInfo getDataTypeInfo(String dataSectionName)
    {
        // if (dataSectionName == DATA_SECTION_VERTEXES) { return vertexDataTypeInfo; }
        // else if (dataSectionName == DATA_SECTION_FACETS) { return facetDataTypeInfo; }
        throw new IllegalStateException("DataSection " + dataSectionName);
    }
    
    public Object getData()
    {
        return characterData;
    }

    public static final String DATA_SECTION_GENERAL = "General";
    public static final String DATA_SECTION_SKILLS = "Skills";
    public static final String DATA_SECTION_KNOWN_SPELLS = "Known Spells";
    public static final String DATA_SECTION_AWARDS = "Awards";
    public static final String DATA_SECTION_CONDITIONS = "Conditions";
    public static final String DATA_SECTION_ACTIVE_SPELLS = "Active Spells";
    public static final String DATA_SECTION_ITEMS = "Items";
    public static final String DATA_SECTION_LLOYDS_BEACON = "Lloyd's Beacon";
    public static final String DATA_SECTION_UNKNOWNS = "Unknowns";
	
    public static DataSection[] getDataSections()
    {
        return new DataSection[] {
                new DataSection(DATA_SECTION_GENERAL),
                new DataSection(DATA_SECTION_SKILLS),
                new DataSection(DATA_SECTION_KNOWN_SPELLS),
                new DataSection(DATA_SECTION_AWARDS),
                new DataSection(DATA_SECTION_CONDITIONS),
                new DataSection(DATA_SECTION_ACTIVE_SPELLS, ActiveSpell.class, ActiveSpellDataSectionable.class),
                new DataSection(DATA_SECTION_ITEMS, ItemContainer.class, ItemContainerDataSectionable.class),
                new DataSection(DATA_SECTION_LLOYDS_BEACON, LloydsBeacon.class, LloydsBeaconDataSectionable.class),
                new DataSection(DATA_SECTION_UNKNOWNS)
        };
    }
    
    public DataSection[] getStaticDataSections() { return getDataSections(); }

    public Object getDataForDataSection(DataSection dataSection)
    {
        if (DATA_SECTION_GENERAL == dataSection.getDataSectionName())
        {
            return characterData.getCharacterDataData();
        }
        else if (DATA_SECTION_SKILLS == dataSection.getDataSectionName())
        {
            return characterData.getCharacterDataData();
        }
        else if (DATA_SECTION_KNOWN_SPELLS == dataSection.getDataSectionName())
        {
            return characterData.getCharacterDataData();
        }
        else if (DATA_SECTION_AWARDS == dataSection.getDataSectionName())
        {
            return characterData.getCharacterDataData();
        }
        else if (DATA_SECTION_CONDITIONS == dataSection.getDataSectionName())
        {
            return characterData.getCharacterDataData();
        }
        else if (DATA_SECTION_ACTIVE_SPELLS == dataSection.getDataSectionName())
        {
            return characterData.getActiveSpellList();
        }
        else if (DATA_SECTION_ITEMS == dataSection.getDataSectionName())
        {
            return characterData.getItemContainer();
        }
        else if (DATA_SECTION_LLOYDS_BEACON == dataSection.getDataSectionName())
        {
            return characterData.getLloydsBeaconList();
        }
        else if (DATA_SECTION_UNKNOWNS == dataSection.getDataSectionName())
        {
            return characterData.getUnknownByteDataList();
        }
        else throw new IllegalStateException("DataSection " + dataSection.getDataSectionName());

    }
}
