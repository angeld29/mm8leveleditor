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
package org.gamenet.application.mm8leveleditor.control;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.gamenet.application.mm8leveleditor.data.mm6.CharacterData;
import org.gamenet.application.mm8leveleditor.dataSectionable.CharacterDataDataSectionable;
import org.gamenet.util.SubTaskObserverImpl;
import org.gamenet.util.TaskObserver;

public class CharacterDataControl extends JTabbedPane
{
    private CharacterData characterData = null;
    
    public CharacterDataControl(TaskObserver parentTaskObserver, CharacterData srcCharacterData) throws InterruptedException
    {
        super();
        
        this.characterData = srcCharacterData;
        
        CharacterDataDataSectionable provider = new CharacterDataDataSectionable(getCharacterData());
    
		this.addTab("General", makeNonStretchedPanelFor(provider.getGeneralPanel(new SubTaskObserverImpl(parentTaskObserver, "CharacterData", 0.0f, .09f))));
        this.addTab("Skills", makeNonStretchedPanelFor(provider.getSkillsPanel(new SubTaskObserverImpl(parentTaskObserver, "CharacterData", 0.1f, .19f))));
        this.addTab("Known Spells", provider.getKnownSpellsPanel(new SubTaskObserverImpl(parentTaskObserver, "CharacterData", 0.2f, .29f)));
        this.addTab("Awards", makeNonStretchedPanelFor(provider.getAwardsPanel(new SubTaskObserverImpl(parentTaskObserver, "CharacterData", 0.3f, .39f))));
        this.addTab("Conditions", makeNonStretchedPanelFor(provider.getConditionsPanel(new SubTaskObserverImpl(parentTaskObserver, "CharacterData", 0.4f, .49f))));
        this.addTab("Active Spells", makeNonStretchedPanelFor(provider.getActiveSpellsPanel(new SubTaskObserverImpl(parentTaskObserver, "CharacterData", 0.5f, .59f), null)));
        this.addTab("Items", makeNonStretchedPanelFor(new ItemContainerControl(characterData.getItemContainer())));
        this.addTab("Lloyd's Beacon", makeNonStretchedPanelFor(provider.getLloydsBeaconPanel(new SubTaskObserverImpl(parentTaskObserver, "CharacterData", 0.7f, .79f), null)));
        this.addTab("Unknowns", makeNonStretchedPanelFor(provider.getUnknownsPanel(new SubTaskObserverImpl(parentTaskObserver, "CharacterData", 0.8f, .99f), null, characterData.getUnknownByteDataList())));
    }

    public CharacterData getCharacterData()
    {
        return characterData;
    }
    
    protected JPanel makeNonStretchedPanelFor(Component component)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        if (null != component)  panel.add(component);
        return panel;
    }
}
