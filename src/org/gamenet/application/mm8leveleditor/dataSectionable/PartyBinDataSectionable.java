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

import org.gamenet.application.mm8leveleditor.control.ActiveSpellControl;
import org.gamenet.application.mm8leveleditor.control.CharacterDataControl;
import org.gamenet.application.mm8leveleditor.control.ContainedItemControl;
import org.gamenet.application.mm8leveleditor.control.DateTimeControl;
import org.gamenet.application.mm8leveleditor.control.FollowerControl;
import org.gamenet.application.mm8leveleditor.control.ShopContentsControl;
import org.gamenet.application.mm8leveleditor.data.ByteData;
import org.gamenet.application.mm8leveleditor.data.mm6.ActiveSpell;
import org.gamenet.application.mm8leveleditor.data.mm6.CharacterData;
import org.gamenet.application.mm8leveleditor.data.mm6.ContainedItem;
import org.gamenet.application.mm8leveleditor.data.mm6.Follower;
import org.gamenet.application.mm8leveleditor.data.mm6.ShopContents;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.PartyBin;
import org.gamenet.swing.controls.ByteDataTableControl;
import org.gamenet.swing.controls.CheckBoxArrayControl;
import org.gamenet.swing.controls.ComparativeTableControl;
import org.gamenet.swing.controls.DataSection;
import org.gamenet.swing.controls.DataSectionable;
import org.gamenet.swing.controls.LongValueHolder;
import org.gamenet.swing.controls.StateArrayValueHolder;
import org.gamenet.swing.controls.Vertex3DTextFieldPanel;
import org.gamenet.swing.controls.Vertex3DValueHolder;
import org.gamenet.util.TaskObserver;

public class PartyBinDataSectionable extends BaseDataSectionable implements DataSectionable
{
    private PartyBin partyBin = null;
    
    public PartyBinDataSectionable(PartyBin srcPartyBin)
    {
        super();
        
        this.partyBin = srcPartyBin;
    }

    
    public Component getListComponentForDataSection(TaskObserver taskObserver, String dataSectionName, List list, Iterator indexIterator) throws InterruptedException
    {
        List indexNumberList = listForIterator(indexIterator);
        Integer indexNumber = (Integer)indexNumberList.get(0);
        int index = indexNumber.intValue();
        
        if (dataSectionName == DATA_SECTION_ACTIVE_PARTY_SPELLS)
        {
            if (1 == indexNumberList.size())
            {
        	    return new ActiveSpellControl((ActiveSpell)list.get(index), partyBin.getActiveSpellNameArray()[index]);
            }
            else
            {
                return getActivePartySpellsPanel(taskObserver, indexNumberList);
            }
        }
        else if (dataSectionName == DATA_SECTION_CHARACTERS)
        {
            if (1 == indexNumberList.size())
            {
        	    return new CharacterDataControl(taskObserver, (CharacterData)list.get(index));
            }
            else
            {
                return getCharactersPanel(taskObserver, indexNumberList);
            }
        }
        else if (dataSectionName == DATA_SECTION_FOLLOWERS)
        {
            if (1 == indexNumberList.size())
            {
        	    return new FollowerControl((Follower)list.get(index));
            }
            else
            {
                return getFollowersPanel(taskObserver, indexNumberList);
            }
        }
        else if (dataSectionName == DATA_SECTION_NORMAL_SHOP_ITEMS)
        {
            if (1 == indexNumberList.size())
            {
                final int finalizedShopIndex = index;
    	        JPanel shopPanel = new JPanel(new BorderLayout());
    		    ShopContents shopContents = (ShopContents)list.get(index);
    		    shopPanel.add(new JLabel("Shop #" + String.valueOf(index + 1)), BorderLayout.PAGE_START);
    		    JPanel resetDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    		    resetDatePanel.add(new JLabel("Reset Date:"));
    	        DateTimeControl resetDateTimeControl = new DateTimeControl(new LongValueHolder()
    	                {
    	                    public long getValue() { return partyBin.getShopTimestampReset(finalizedShopIndex); }
    	                    public void setValue(long value) { partyBin.setShopTimestampResetList(value, finalizedShopIndex); }
    	                });
    	        resetDatePanel.add(resetDateTimeControl);
    		    shopPanel.add(resetDatePanel, BorderLayout.CENTER);
    		    shopPanel.add(new ShopContentsControl(shopContents), BorderLayout.PAGE_END);
    		    
    		    return shopPanel;
            }
            else
            {
                return getNormalShopItemsPanel(taskObserver, indexNumberList);
            }
        }
        else if (dataSectionName == DATA_SECTION_SPECIAL_SHOP_ITEMS)
        {
            if (1 == indexNumberList.size())
            {
                final int finalizedShopIndex = index;
    	        JPanel shopPanel = new JPanel(new BorderLayout());
    		    ShopContents shopContents = (ShopContents)list.get(index);
    		    shopPanel.add(new JLabel("Shop #" + String.valueOf(index + 1)), BorderLayout.PAGE_START);
    		    JPanel resetDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    		    resetDatePanel.add(new JLabel("Reset Date:"));
    	        DateTimeControl resetDateTimeControl = new DateTimeControl(new LongValueHolder()
    	                {
    	                    public long getValue() { return partyBin.getShopTimestampReset(finalizedShopIndex); }
    	                    public void setValue(long value) { partyBin.setShopTimestampResetList(value, finalizedShopIndex); }
    	                });
    	        resetDatePanel.add(resetDateTimeControl);
    		    shopPanel.add(resetDatePanel, BorderLayout.CENTER);
    		    shopPanel.add(new ShopContentsControl(shopContents), BorderLayout.PAGE_END);
    		    
    		    return shopPanel;
            }
            else
            {
                return getSpecialShopItemsPanel(taskObserver, indexNumberList);
            }
        }
        else if (dataSectionName == DATA_SECTION_GUILD_SHOP_ITEMS)
        {
            if (1 == indexNumberList.size())
            {
                final int finalizedShopIndex = index;
    	        JPanel shopPanel = new JPanel(new BorderLayout());
    		    ShopContents shopContents = (ShopContents)list.get(index);
    		    shopPanel.add(new JLabel("Shop #" + String.valueOf(index + 119)), BorderLayout.PAGE_START);
    		    JPanel resetDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    		    resetDatePanel.add(new JLabel("Reset Date:"));
    	        DateTimeControl resetDateTimeControl = new DateTimeControl(new LongValueHolder()
    	                {
    	                    public long getValue() { return partyBin.getGuildTimestampReset(finalizedShopIndex); }
    	                    public void setValue(long value) { partyBin.setGuildTimestampReset(value, finalizedShopIndex); }
    	                });
    	        resetDatePanel.add(resetDateTimeControl);
    		    shopPanel.add(resetDatePanel, BorderLayout.CENTER);
    		    shopPanel.add(new ShopContentsControl(shopContents), BorderLayout.PAGE_END);
    		    
    		    return shopPanel;
            }
            else
            {
                return getGuildShopItemsPanel(taskObserver, indexNumberList);
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
                return getUnknownsPanel(taskObserver, indexNumberList);
            }
        }
        else throw new IllegalStateException("DataSection " + dataSectionName);
    }

    public Component getComponentForDataSection(TaskObserver taskObserver, String dataSectionName) throws InterruptedException
    {
        if (dataSectionName == DATA_SECTION_GENERAL) { return getGeneralPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_ARTIFACTS_FOUND) { return getArtifactsFoundPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_QUESTS) { return getQuestsPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_AUTONOTES) { return getAutonotesPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_ACTIVE_PARTY_SPELLS) { return getActivePartySpellsPanel(taskObserver, null ); }
        else if (dataSectionName == DATA_SECTION_CHARACTERS) { return getCharactersPanel(taskObserver, null); }
        else if (dataSectionName == DATA_SECTION_FOLLOWERS) { return getFollowersPanel(taskObserver, null); }
        else if (dataSectionName == DATA_SECTION_CURSOR_ITEM) { return getCursorItemPanel(taskObserver); }
        else if (dataSectionName == DATA_SECTION_NORMAL_SHOP_ITEMS) { return getNormalShopItemsPanel(taskObserver, null); }
        else if (dataSectionName == DATA_SECTION_SPECIAL_SHOP_ITEMS) { return getSpecialShopItemsPanel(taskObserver, null); }
        else if (dataSectionName == DATA_SECTION_GUILD_SHOP_ITEMS) { return getGuildShopItemsPanel(taskObserver, null); }
        else if (dataSectionName == DATA_SECTION_UNKNOWNS) { return getUnknownsPanel(taskObserver, null); }
        else throw new IllegalStateException("DataSection " + dataSectionName);
    }
        
    public Component getUnknownsPanel(TaskObserver taskObserver, List indexNumberList) throws InterruptedException
    {
        taskObserver.taskProgress("Unknowns", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getUnknownsPanel() was interrupted.");

        JPanel unknownsDataPanel = new JPanel();
        unknownsDataPanel.setLayout(new BoxLayout(unknownsDataPanel, BoxLayout.Y_AXIS));

        List byteDataList = partyBin.getUnknownByteDataList();
		if (null == indexNumberList)  indexNumberList = createListForSequence(byteDataList.size());

		Iterator indexIterator = indexNumberList.iterator();
        while (indexIterator.hasNext())
        {
            Integer indexInteger = (Integer) indexIterator.next();
            int byteDataIndex = indexInteger.intValue();

            ByteData byteData = (ByteData)byteDataList.get(byteDataIndex);

    		ByteDataTableControl byteDataTableControl = new ByteDataTableControl(byteData.getData(), 32, byteData.getExternalOffset(), byteData.getStartOffset(), byteData.getEndOffset());
    		unknownsDataPanel.add(makeNonStretchedPanelFor(byteDataTableControl));
            
            taskObserver.taskProgress("Unknowns", 0.1f + (((0.7f - 0.1f) / indexNumberList.size()) * byteDataIndex));
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("getUnknownsPanel() was interrupted.");
		}
		
        return unknownsDataPanel;
    }

    public Component getNormalShopItemsPanel(TaskObserver taskObserver, List indexNumberList) throws InterruptedException
    {
        taskObserver.taskProgress("Normal Shop Items", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getNormalShopItemsPanel() was interrupted.");

        JTabbedPane shopBuyNormalItemsPanel = new JTabbedPane();
		List shopBuyNormalItemsList = partyBin.getShopBuyNormalItemsList();
		if (null == indexNumberList)  indexNumberList = createListForSequence(shopBuyNormalItemsList.size());
		Iterator indexIterator = indexNumberList.iterator();
        while (indexIterator.hasNext())
        {
            Integer indexInteger = (Integer) indexIterator.next();
            int shopIndex = indexInteger.intValue();

		    final int finalizedShopIndex = shopIndex;
	        JPanel shopPanel = new JPanel(new BorderLayout());
		    ShopContents shopContents = (ShopContents)shopBuyNormalItemsList.get(shopIndex);
		    shopPanel.add(new JLabel("Shop #" + String.valueOf(shopIndex + 1)), BorderLayout.PAGE_START);
		    JPanel resetDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		    resetDatePanel.add(new JLabel("Reset Date:"));
	        DateTimeControl resetDateTimeControl = new DateTimeControl(new LongValueHolder()
	                {
	                    public long getValue() { return partyBin.getShopTimestampReset(finalizedShopIndex); }
	                    public void setValue(long value) { partyBin.setShopTimestampResetList(value, finalizedShopIndex); }
	                });
	        resetDatePanel.add(resetDateTimeControl);
		    shopPanel.add(resetDatePanel, BorderLayout.CENTER);
		    shopPanel.add(new ShopContentsControl(shopContents), BorderLayout.PAGE_END);
		    shopBuyNormalItemsPanel.add(String.valueOf(shopIndex + 1), shopPanel);
            
            taskObserver.taskProgress("Normal Shop Items",
                    0.1f + (((0.7f - 0.1f) / indexNumberList.size()) * shopIndex));
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("getNormalShopItemsPanel() was interrupted.");
		}

        return shopBuyNormalItemsPanel;
    }

    public Component getSpecialShopItemsPanel(TaskObserver taskObserver, List indexNumberList) throws InterruptedException
    {
        taskObserver.taskProgress("Special Shop Items", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getSpecialShopItemsPanel() was interrupted.");

		List shopBuySpecialItemsList = partyBin.getShopBuySpecialItemsList();
		if (null == indexNumberList)  indexNumberList = createListForSequence(shopBuySpecialItemsList.size());
		JTabbedPane shopBuySpecialItemsPanel = new JTabbedPane();
		Iterator indexIterator = indexNumberList.iterator();
        while (indexIterator.hasNext())
        {
            Integer indexInteger = (Integer) indexIterator.next();
            int shopIndex = indexInteger.intValue();

		    final int finalizedShopIndex = shopIndex;
	        JPanel shopPanel = new JPanel(new BorderLayout());
		    ShopContents shopContents = (ShopContents)shopBuySpecialItemsList.get(shopIndex);
		    shopPanel.add(new JLabel("Shop #" + String.valueOf(shopIndex + 1)), BorderLayout.PAGE_START);
		    JPanel resetDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		    resetDatePanel.add(new JLabel("Reset Date:"));
	        DateTimeControl resetDateTimeControl = new DateTimeControl(new LongValueHolder()
	                {
	                    public long getValue() { return partyBin.getShopTimestampReset(finalizedShopIndex); }
	                    public void setValue(long value) { partyBin.setShopTimestampResetList(value, finalizedShopIndex); }
	                });
	        resetDatePanel.add(resetDateTimeControl);
		    shopPanel.add(resetDatePanel, BorderLayout.CENTER);
		    shopPanel.add(new ShopContentsControl(shopContents), BorderLayout.PAGE_END);
		    shopBuySpecialItemsPanel.add(String.valueOf(shopIndex + 1), shopPanel);
            
            taskObserver.taskProgress("Special Shop Items",
                    0.1f + (((0.7f - 0.1f) / indexNumberList.size()) * shopIndex));
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("getSpecialShopItemsPanel() was interrupted.");
		}
        
        return shopBuySpecialItemsPanel;
    }

    public Component getGuildShopItemsPanel(TaskObserver taskObserver, List indexNumberList) throws InterruptedException
    {
        taskObserver.taskProgress("Guild Shop Items", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getGuildShopItemsPanel() was interrupted.");

		List guildSpellBookItemsList = partyBin.getShopBuyGuildSpellBookList();
		if (null == indexNumberList)  indexNumberList = createListForSequence(guildSpellBookItemsList.size());
		JTabbedPane guildSpellBookItemsPanel = new JTabbedPane();
		Iterator indexIterator = indexNumberList.iterator();
        while (indexIterator.hasNext())
        {
            Integer indexInteger = (Integer) indexIterator.next();
            int shopIndex = indexInteger.intValue();

            final int finalizedShopIndex = shopIndex;
	        JPanel shopPanel = new JPanel(new BorderLayout());
		    ShopContents shopContents = (ShopContents)guildSpellBookItemsList.get(shopIndex);
		    shopPanel.add(new JLabel("Shop #" + String.valueOf(shopIndex + 119)), BorderLayout.PAGE_START);
		    JPanel resetDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		    resetDatePanel.add(new JLabel("Reset Date:"));
	        DateTimeControl resetDateTimeControl = new DateTimeControl(new LongValueHolder()
	                {
	                    public long getValue() { return partyBin.getGuildTimestampReset(finalizedShopIndex); }
	                    public void setValue(long value) { partyBin.setGuildTimestampReset(value, finalizedShopIndex); }
	                });
	        resetDatePanel.add(resetDateTimeControl);
		    shopPanel.add(resetDatePanel, BorderLayout.CENTER);
		    shopPanel.add(new ShopContentsControl(shopContents), BorderLayout.PAGE_END);
		    guildSpellBookItemsPanel.add(String.valueOf(shopIndex + 119), shopPanel);
            
            taskObserver.taskProgress("Guild Spell Books",
                    0.1f + (((0.7f - 0.1f) / indexNumberList.size()) * shopIndex));
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("getGuildShopItemsPanel() was interrupted.");
		}

        return guildSpellBookItemsPanel;
    }

    public Component getCursorItemPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Cursor Item", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getCursorItemPanel() was interrupted.");

        JPanel cursorItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cursorItemPanel.add(new ContainedItemControl(partyBin.getCursorItem()));
        
        return cursorItemPanel;
    }

    public Component getFollowersPanel(TaskObserver taskObserver, List indexNumberList) throws InterruptedException
    {
        taskObserver.taskProgress("Followers", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getFollowersPanel() was interrupted.");

		List followerList = partyBin.getFollowerList();
		if (null == indexNumberList)  indexNumberList = createListForSequence(followerList.size());
        JPanel followerPanel = new JPanel();
        followerPanel.setLayout(new BoxLayout(followerPanel, BoxLayout.Y_AXIS));
		Iterator indexIterator = indexNumberList.iterator();
        while (indexIterator.hasNext())
        {
            Integer indexInteger = (Integer) indexIterator.next();
            int followerIndex = indexInteger.intValue();

		    Follower follower = (Follower)followerList.get(followerIndex);
			followerPanel.add(new FollowerControl(follower));
            
            taskObserver.taskProgress("Followers", 0.1f + (((0.7f - 0.1f) / indexNumberList.size()) * followerIndex));
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("getFollowersPanel() was interrupted.");
		}
        ComparativeTableControl followerComparativeByteDataTableControl = new ComparativeTableControl(Follower.getOffsetList(), Follower.getComparativeDataSource(followerList));
        followerPanel.add(makeNonStretchedPanelFor(new JLabel("Follower Unknowns: ")));
        followerPanel.add(followerComparativeByteDataTableControl);

        return followerPanel;
    }

    public Component getCharactersPanel(TaskObserver taskObserver, List indexNumberList) throws InterruptedException
    {
        taskObserver.taskProgress("Characters", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getCharactersPanel() was interrupted.");

		List characterList = partyBin.getCharacterList();
		if (null == indexNumberList)  indexNumberList = createListForSequence(characterList.size());
        JPanel characterDataPanel[] = new JPanel[4];
		Iterator indexIterator = indexNumberList.iterator();
        while (indexIterator.hasNext())
        {
            Integer indexInteger = (Integer) indexIterator.next();
            int characterIndex = indexInteger.intValue();

		    CharacterData characterData = (CharacterData)characterList.get(characterIndex);
	        characterDataPanel[characterIndex] = new JPanel();
	        characterDataPanel[characterIndex].setLayout(new BoxLayout(characterDataPanel[characterIndex], BoxLayout.Y_AXIS));
			characterDataPanel[characterIndex].add(new CharacterDataControl(taskObserver, characterData));
            
            taskObserver.taskProgress("Character #" + String.valueOf(characterIndex + 1), 0.1f + (((0.7f - 0.1f) / indexNumberList.size()) * characterIndex));
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("getCharactersPanel() was interrupted.");
		}

        JTabbedPane characterTabbedPane = new JTabbedPane();
        characterTabbedPane.addTab("Character 0", makeNonStretchedPanelFor(characterDataPanel[0]));
        characterTabbedPane.addTab("Character 1", makeNonStretchedPanelFor(characterDataPanel[1]));
        characterTabbedPane.addTab("Character 2", makeNonStretchedPanelFor(characterDataPanel[2]));
        characterTabbedPane.addTab("Character 3", makeNonStretchedPanelFor(characterDataPanel[3]));
        
        return characterTabbedPane;
    }

    public Component getActivePartySpellsPanel(TaskObserver taskObserver, List indexNumberList) throws InterruptedException
    {
        taskObserver.taskProgress("Active Party Spells", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getActivePartySpellsPanel() was interrupted.");

		List activePartySpellList = partyBin.getActiveSpellList();
		if (null == indexNumberList)  indexNumberList = createListForSequence(activePartySpellList.size());
		String activePartySpellName[] = partyBin.getActiveSpellNameArray();
        JPanel activePartySpellsPanel = new JPanel();
        activePartySpellsPanel.setLayout(new BoxLayout(activePartySpellsPanel, BoxLayout.Y_AXIS));
		Iterator indexIterator = indexNumberList.iterator();
        while (indexIterator.hasNext())
        {
            Integer indexInteger = (Integer) indexIterator.next();
            int activeSpellIndex = indexInteger.intValue();

		    ActiveSpell activeSpell = (ActiveSpell)activePartySpellList.get(activeSpellIndex);
			activePartySpellsPanel.add(new ActiveSpellControl(activeSpell, activePartySpellName[activeSpellIndex]));
            
            taskObserver.taskProgress("Active Party Spells", 0.1f + (((0.7f - 0.1f) / indexNumberList.size()) * activeSpellIndex));
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("getActivePartySpellsPanel() was interrupted.");
		}

        return activePartySpellsPanel;
    }

    public Component getAutonotesPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Autonotes", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getAutonotesPanel() was interrupted.");

        JPanel autonotesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		CheckBoxArrayControl autonoteControl = new CheckBoxArrayControl(new StateArrayValueHolder()
        {
            public String getValueLabel(int index) { return "Autonote #" + String.valueOf(index + 1); }
            public boolean getValue(int index) { return partyBin.getAutonote(index); }
            public void setValue(boolean value, int index) { partyBin.setAutonote(value, index); }
            public int getStartIndex() { return 0; }
            public int getEndIndex() { return partyBin.getAutonoteCount() - 1; }
        });
		autonotesPanel.add(autonoteControl);
		
        return autonotesPanel;
    }

    public Component getQuestsPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Quests", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getQuestsPanel() was interrupted.");

        JPanel questsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		CheckBoxArrayControl questsControl = new CheckBoxArrayControl(new StateArrayValueHolder()
        {
            public String getValueLabel(int index) { return "Quest #" + String.valueOf(index); }
            public boolean getValue(int index) { return partyBin.getQuest(index); }
            public void setValue(boolean value, int index) { partyBin.setQuest(value, index); }
            public int getStartIndex() { return 0; }
            public int getEndIndex() { return partyBin.getQuestCount() - 1; }
        });
		questsPanel.add(questsControl);
		
        return questsPanel;
    }

    public Component getArtifactsFoundPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("Artifacts Found", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getArtifactsFoundPanel() was interrupted.");

        JPanel artifactsFoundPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		CheckBoxArrayControl artifactsFoundControl = new CheckBoxArrayControl(new StateArrayValueHolder()
        {
            public String getValueLabel(int index) { return "Artifact Item #" + String.valueOf(index); }
            public boolean getValue(int index) { return partyBin.getArtifactsFound(index); }
            public void setValue(boolean value, int index) { partyBin.setArtifactsFound(value, index); }
            public int getStartIndex() { return 400; }
            public int getEndIndex() { return 400 + (partyBin.getArtifactsFoundCount() - 1); }
        });
		artifactsFoundPanel.add(artifactsFoundControl);
		
        return artifactsFoundPanel;
    }

    public Component getGeneralPanel(TaskObserver taskObserver) throws InterruptedException
    {
        taskObserver.taskProgress("General", 0.1f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("getGeneralPanel() was interrupted.");

        JPanel generalDataPanel = new JPanel();
        generalDataPanel.setLayout(new BoxLayout(generalDataPanel, BoxLayout.Y_AXIS));
        
        JPanel coordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        coordPanel.add(new JLabel("Coords:"));
        coordPanel.add(new Vertex3DTextFieldPanel(new Vertex3DValueHolder()
                {
                    public int getX() { return partyBin.getX(); }
                    public void setX(int x) { partyBin.setX(x); }
                    public int getY() { return partyBin.getY(); }
                    public void setY(int y) { partyBin.setY(y); }
                    public int getZ() { return partyBin.getZ(); }
                    public void setZ(int z) { partyBin.setZ(z); }
                }));
        JFormattedTextField facingTextField = new JFormattedTextField(new Integer(partyBin.getFacing()));
        facingTextField.setColumns(5);
        facingTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setFacing(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        coordPanel.add(new JLabel("Facing:"));
        coordPanel.add(facingTextField);
        JFormattedTextField tiltTextField = new JFormattedTextField(new Integer(partyBin.getTilt()));
        tiltTextField.setColumns(5);
        tiltTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setTilt(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        coordPanel.add(new JLabel("Tilt:"));
        coordPanel.add(tiltTextField);
        generalDataPanel.add(coordPanel);
        
        JPanel coord2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        coord2Panel.add(new JLabel("Unknown Coords:"));
        coord2Panel.add(new Vertex3DTextFieldPanel(new Vertex3DValueHolder()
                {
                    public int getX() { return partyBin.getX2(); }
                    public void setX(int x) { partyBin.setX2(x); }
                    public int getY() { return partyBin.getY2(); }
                    public void setY(int y) { partyBin.setY2(y); }
                    public int getZ() { return partyBin.getZ2(); }
                    public void setZ(int z) { partyBin.setZ2(z); }
                }));
        JFormattedTextField facing2TextField = new JFormattedTextField(new Integer(partyBin.getFacing2()));
        facing2TextField.setColumns(5);
        facing2TextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setFacing2(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        coord2Panel.add(new JLabel("Facing:"));
        coord2Panel.add(facing2TextField);
        JFormattedTextField tilt2TextField = new JFormattedTextField(new Integer(partyBin.getTilt2()));
        tilt2TextField.setColumns(5);
        tilt2TextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setTilt2(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        coord2Panel.add(new JLabel("Tilt:"));
        coord2Panel.add(tilt2TextField);
        generalDataPanel.add(coord2Panel);

        DateTimeControl gameTimeControl = new DateTimeControl(new LongValueHolder()
                {
                    public long getValue() { return partyBin.getGameTime(); }
                    public void setValue(long value) { partyBin.setGameTime(value); }
                });
                
        JFormattedTextField foodTextField = new JFormattedTextField(new Integer(partyBin.getFood()));
        foodTextField.setColumns(10);
        foodTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setFood(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        JFormattedTextField reputationTextField = new JFormattedTextField(new Integer(partyBin.getReputation()));
        reputationTextField.setColumns(10);
        reputationTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setReputation(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        JFormattedTextField moneyOnHandTextField = new JFormattedTextField(new Integer(partyBin.getMoneyOnHand()));
        moneyOnHandTextField.setColumns(10);
        moneyOnHandTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setMoneyOnHand(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        JFormattedTextField moneyInBankTextField = new JFormattedTextField(new Integer(partyBin.getMoneyInBank()));
        moneyInBankTextField.setColumns(10);
        moneyInBankTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setMoneyInBank(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        JFormattedTextField bountiesCollectedTextField = new JFormattedTextField(new Integer(partyBin.getBountiesCollected()));
        bountiesCollectedTextField.setColumns(10);
        bountiesCollectedTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setBountiesCollected(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        JFormattedTextField deathsTextField = new JFormattedTextField(new Integer(partyBin.getDeaths()));
        deathsTextField.setColumns(10);
        deathsTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setDeaths(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        JFormattedTextField prisonTermsTextField = new JFormattedTextField(new Integer(partyBin.getPrisonTerms()));
        prisonTermsTextField.setColumns(10);
        prisonTermsTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setPrisonTerms(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        JFormattedTextField pageVictoriesTextField = new JFormattedTextField(new Integer(partyBin.getPageVictories()));
        pageVictoriesTextField.setColumns(3);
        pageVictoriesTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setPageVictories(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        JFormattedTextField squireVictoriesTextField = new JFormattedTextField(new Integer(partyBin.getSquireVictories()));
        squireVictoriesTextField.setColumns(3);
        squireVictoriesTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setSquireVictories(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        JFormattedTextField knightVictoriesTextField = new JFormattedTextField(new Integer(partyBin.getKnightVictories()));
        knightVictoriesTextField.setColumns(3);
        knightVictoriesTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setKnightVictories(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        JFormattedTextField lordVictoriesTextField = new JFormattedTextField(new Integer(partyBin.getLordVictories()));
        lordVictoriesTextField.setColumns(3);
        lordVictoriesTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setLordVictories(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        JFormattedTextField newSorpigalBountyCreatureTextField = new JFormattedTextField(new Integer(partyBin.getNewSorpigalBountyCreature()));
        newSorpigalBountyCreatureTextField.setColumns(3);
        newSorpigalBountyCreatureTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setNewSorpigalBountyCreature(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        JFormattedTextField killedNewSorpigalBountyCreatureTextField = new JFormattedTextField(new Integer(partyBin.getKilledNewSorpigalBountyCreature()));
        killedNewSorpigalBountyCreatureTextField.setColumns(3);
        killedNewSorpigalBountyCreatureTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        partyBin.setKilledNewSorpigalBountyCreature(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        
        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(new JLabel("Game Time: "));
        labelPane.add(new JLabel("Food: "));
        labelPane.add(new JLabel("Reputation: "));
        labelPane.add(new JLabel("Money On Hand: "));
        labelPane.add(new JLabel("Money In Bank: "));
        labelPane.add(new JLabel("Bounties Collected: "));
        labelPane.add(new JLabel("Deaths: "));
        labelPane.add(new JLabel("Prison Terms: "));
        labelPane.add(new JLabel("Page Victories: "));
        labelPane.add(new JLabel("Squire Victories: "));
        labelPane.add(new JLabel("Knight Victories: "));
        labelPane.add(new JLabel("Lord Victories: "));
        labelPane.add(new JLabel("New Sorpigal Bounty Creature: "));
        labelPane.add(new JLabel("Killed New Sorpigal Bounty Creature: "));

        //Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(gameTimeControl);
        fieldPane.add(foodTextField);
        fieldPane.add(reputationTextField);
        fieldPane.add(moneyOnHandTextField);
        fieldPane.add(moneyInBankTextField);
        fieldPane.add(bountiesCollectedTextField);
        fieldPane.add(deathsTextField);
        fieldPane.add(prisonTermsTextField);
        fieldPane.add(pageVictoriesTextField);
        fieldPane.add(squireVictoriesTextField);
        fieldPane.add(knightVictoriesTextField);
        fieldPane.add(lordVictoriesTextField);
        fieldPane.add(newSorpigalBountyCreatureTextField);
        fieldPane.add(killedNewSorpigalBountyCreatureTextField);

        //Put the panels in this panel, labels on left,
        //text fields on right.
        JPanel labelAndFieldPanel = new JPanel(new BorderLayout());
        labelAndFieldPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        labelAndFieldPanel.add(labelPane, BorderLayout.CENTER);
        labelAndFieldPanel.add(fieldPane, BorderLayout.LINE_END);

		generalDataPanel.add(makeNonStretchedPanelFor(labelAndFieldPanel));

        return generalDataPanel;
    }

    public DataTypeInfo getDataTypeInfo(String dataSectionName)
    {
        // if (dataSectionName == DATA_SECTION_VERTEXES) { return vertexDataTypeInfo; }
        // else if (dataSectionName == DATA_SECTION_FACETS) { return facetDataTypeInfo; }
        throw new IllegalStateException("DataSection " + dataSectionName);
    }
    
    public Object getData()
    {
        return partyBin;
    }

    public static final String DATA_SECTION_GENERAL = "General";
    public static final String DATA_SECTION_ARTIFACTS_FOUND = "Artifacts Found";
    public static final String DATA_SECTION_QUESTS = "Quests";
    public static final String DATA_SECTION_AUTONOTES = "Autonotes";
    public static final String DATA_SECTION_ACTIVE_PARTY_SPELLS = "Active Party Spells";
    public static final String DATA_SECTION_CHARACTERS = "Characters";
    public static final String DATA_SECTION_FOLLOWERS = "Followers";
    public static final String DATA_SECTION_CURSOR_ITEM = "Cursor Item";
    public static final String DATA_SECTION_NORMAL_SHOP_ITEMS = "Normal Shop Items";
    public static final String DATA_SECTION_SPECIAL_SHOP_ITEMS = "Special Shop Items";
    public static final String DATA_SECTION_GUILD_SHOP_ITEMS = "Guild Shop Items";
    public static final String DATA_SECTION_UNKNOWNS = "Unknowns";

    public static DataSection[] getDataSections()
    {
        return new DataSection[] {
                new DataSection(DATA_SECTION_GENERAL),
                new DataSection(DATA_SECTION_ARTIFACTS_FOUND),
                new DataSection(DATA_SECTION_QUESTS),
                new DataSection(DATA_SECTION_AUTONOTES),
                new DataSection(DATA_SECTION_ACTIVE_PARTY_SPELLS, ActiveSpell.class, ActiveSpellDataSectionable.class),
                new DataSection(DATA_SECTION_CHARACTERS, CharacterData.class, CharacterDataDataSectionable.class),
                new DataSection(DATA_SECTION_FOLLOWERS, Follower.class, FollowerDataSectionable.class),
                new DataSection(DATA_SECTION_CURSOR_ITEM, ContainedItem.class, ContainedItemDataSectionable.class),
                new DataSection(DATA_SECTION_NORMAL_SHOP_ITEMS, ShopContents.class, ShopContentsDataSectionable.class),
                new DataSection(DATA_SECTION_SPECIAL_SHOP_ITEMS, ShopContents.class, ShopContentsDataSectionable.class),
                new DataSection(DATA_SECTION_GUILD_SHOP_ITEMS, ShopContents.class, ShopContentsDataSectionable.class),
                new DataSection(DATA_SECTION_UNKNOWNS)
        };
    }

    public DataSection[] getStaticDataSections() { return getDataSections(); }

    public Object getDataForDataSection(DataSection dataSection)
    {
        if (DATA_SECTION_GENERAL == dataSection.getDataSectionName())
        {
            return partyBin.getPreCharacterData();
        }
        else if (DATA_SECTION_ARTIFACTS_FOUND == dataSection.getDataSectionName())
        {
            return partyBin.getPreCharacterData();
        }
        else if (DATA_SECTION_QUESTS == dataSection.getDataSectionName())
        {
            return partyBin.getPreCharacterData();
        }
        else if (DATA_SECTION_AUTONOTES == dataSection.getDataSectionName())
        {
            return partyBin.getPreCharacterData();
        }
        else if (DATA_SECTION_ACTIVE_PARTY_SPELLS == dataSection.getDataSectionName())
        {
            return partyBin.getActiveSpellList();
        }
        else if (DATA_SECTION_CHARACTERS == dataSection.getDataSectionName())
        {
            return partyBin.getCharacterList();
        }
        else if (DATA_SECTION_FOLLOWERS == dataSection.getDataSectionName())
        {
            return partyBin.getFollowerList();
        }
        else if (DATA_SECTION_CURSOR_ITEM == dataSection.getDataSectionName())
        {
            return partyBin.getCursorItem();
        }
        else if (DATA_SECTION_NORMAL_SHOP_ITEMS == dataSection.getDataSectionName())
        {
            return partyBin.getShopBuyNormalItemsList();
        }
        else if (DATA_SECTION_SPECIAL_SHOP_ITEMS == dataSection.getDataSectionName())
        {
            return partyBin.getShopBuySpecialItemsList();
        }
        else if (DATA_SECTION_GUILD_SHOP_ITEMS == dataSection.getDataSectionName())
        {
            return partyBin.getShopBuyGuildSpellBookList();
        }
        else if (DATA_SECTION_UNKNOWNS == dataSection.getDataSectionName())
        {
            return partyBin.getUnknownByteDataList();
        }
        else throw new IllegalStateException("DataSection " + dataSection.getDataSectionName());
    }
}
