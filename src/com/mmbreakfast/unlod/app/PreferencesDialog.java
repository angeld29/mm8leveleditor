/*
 *  com/mmbreakfast/unlod/app/PreferencesDialog.java
 *
 *  Copyright (C) 2000 Sil Veritas (sil_the_follower_of_dark@hotmail.com)
 */

/*  This file is part of Unlod.
 *
 *  Unlod is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Unlod is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Unlod; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/*  Unlod
 *
 *  Copyright (C) 2000 Sil Veritas. All Rights Reserved. This work is
 *  distributed under the W3C(R) Software License [1] in the hope that it
 *  will be useful, but WITHOUT ANY WARRANTY; without even the implied
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 */

package com.mmbreakfast.unlod.app;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.gamenet.application.mm8leveleditor.AdditionalPreferences;
import org.gamenet.application.mm8leveleditor.data.GameVersion;

import com.mmbreakfast.util.*;


public class PreferencesDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	protected static final String TITLE = "Preferences";
   private JButton okButton            = new JButton("OK");
   private JButton cancelButton        = new JButton("Cancel");
   private JPanel buttonPanel          = new JPanel(new GridLayout(1, 2, 4, 4));
   protected Frame parent;
   protected Preferences preferences;

   public PreferencesDialog(Frame parent, Preferences preferences) {
   	super(parent, TITLE, true);
      this.parent = parent;
      this.preferences = preferences;

      this.setResizable(false);

   	JPanel cp = (JPanel) this.getContentPane();
   	cp.setLayout(new GridBagLayout());

   	buttonPanel.add(okButton);
   	buttonPanel.add(cancelButton);

   	JLabel engineVersionLabel = new JLabel("Preferred Engine format for editing resources: ");
   	JRadioButton mm6EngineVersionButton = new JRadioButton("MM6 engine");
   	JRadioButton mm7EngineVersionButton = new JRadioButton("MM7 engine");
   	JRadioButton mm8EngineVersionButton = new JRadioButton("MM8 engine");
   	JRadioButton unknownEngineVersionButton = new JRadioButton("unknown engine");
   	
   	switch (AdditionalPreferences.getInstance().getGameVersion())
   	{
		case GameVersion.MM6:
			mm6EngineVersionButton.setSelected(true);
   		   	break;
   		case GameVersion.MM7:
   			mm7EngineVersionButton.setSelected(true);
   		   	break;
   		case GameVersion.MM8:
   			mm8EngineVersionButton.setSelected(true);
   		   	break;
   		case GameVersion.UNKNOWN:
   		default:
   		   	unknownEngineVersionButton.setSelected(true);
   		   	break;
   	}

   	JPanel optionsPanel = new JPanel(new GridLayout(5, 1, 4, 4));
    optionsPanel.add(engineVersionLabel);
    optionsPanel.add(mm6EngineVersionButton);
    optionsPanel.add(mm7EngineVersionButton);
    optionsPanel.add(mm8EngineVersionButton);
    optionsPanel.add(unknownEngineVersionButton);

    //Group the radio buttons.
    ButtonGroup engineVersionGroup = new ButtonGroup();
    engineVersionGroup.add(mm6EngineVersionButton);
    engineVersionGroup.add(mm7EngineVersionButton);
    engineVersionGroup.add(mm8EngineVersionButton);
    engineVersionGroup.add(unknownEngineVersionButton);

    mm6EngineVersionButton.addActionListener(new ActionListener() {
    	
		public void actionPerformed(ActionEvent e) {
			if (((JRadioButton)e.getSource()).isSelected())
			{
				AdditionalPreferences.getInstance().setGameVersion(GameVersion.MM6);
			}
		}
	});
    mm7EngineVersionButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (((JRadioButton)e.getSource()).isSelected())
			{
				AdditionalPreferences.getInstance().setGameVersion(GameVersion.MM7);
			}
		}
	});
    mm8EngineVersionButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (((JRadioButton)e.getSource()).isSelected())
			{
				AdditionalPreferences.getInstance().setGameVersion(GameVersion.MM8);
			}
		}
	});
    unknownEngineVersionButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (((JRadioButton)e.getSource()).isSelected())
			{
				AdditionalPreferences.getInstance().setGameVersion(GameVersion.UNKNOWN);
			}
		}
	});

   	ComponentDeployer.deploy(cp, optionsPanel,  GridBagConstraints.CENTER,     GridBagConstraints.REMAINDER, 1, GridBagConstraints.BOTH, 1.0, 1.0, new Insets(15, 15, 15, 15));
   	ComponentDeployer.deploy(cp, buttonPanel,  GridBagConstraints.SOUTH,     GridBagConstraints.REMAINDER, 1, GridBagConstraints.NONE, 0.0, 0.0, new Insets(15, 15, 15, 15));

   	this.pack();
      WindowUtilities.centerOnScreen(this);

   	this.addWindowListener(new WindowAdapter() {
   	   public void windowClosing(WindowEvent e) {
   	      PreferencesDialog.this.dispose();
   	   }
   	});

   	cancelButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
   	      PreferencesDialog.this.dispose();
   	   }
   	});

   	okButton.addActionListener(new ActionListener() {
   	   public void actionPerformed(ActionEvent e) {
   	   }
   	});

   	this.getRootPane().setDefaultButton(okButton);
   }
}