/*
 *  com/mmbreakfast/unlod/app/AboutDialog.java
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

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mmbreakfast.util.ComponentDeployer;
import com.mmbreakfast.util.WindowUtilities;

public class AboutDialog extends JDialog {
	protected static final String TITLE = "About " + UnlodFrame.TITLE;
   private JLabel title = new JLabel(UnlodFrame.TITLE);
   private JLabel by    = new JLabel("by");
   private JLabel me    = new JLabel("Mike Kienenberger (mkienenb@gmail.com)");
   private JLabel webSite    = new JLabel("http://mm8leveleditor.sourceforge.net");
   private JLabel originalMe    = new JLabel("*Sil � 2000");
   private JButton okButton = new JButton("OK");
   private int height  = 250;
   private int width   = 250;

   public AboutDialog(Frame parent) {
      super(parent, TITLE, true);
      this.setResizable(false);
      title.setFont(new Font("Serif", Font.BOLD, 20));
      me.setFont(new Font("SansSerif", Font.PLAIN, 18));
      by.setFont(new Font("SansSerif", Font.ITALIC, 12));

      JPanel cp = (JPanel) this.getContentPane();

      cp.setLayout(new GridBagLayout());
      ComponentDeployer.deploy(cp,originalMe,GridBagConstraints.CENTER, GridBagConstraints.REMAINDER, 1, GridBagConstraints.NONE, 0.0, 0.0, new Insets( 0, 50,  0, 50));
      ComponentDeployer.deploy(cp, title,    GridBagConstraints.NORTH,  GridBagConstraints.REMAINDER, 1, GridBagConstraints.NONE, 0.0, 0.0, new Insets(20, 50, 50, 50));
      ComponentDeployer.deploy(cp, by,       GridBagConstraints.CENTER, GridBagConstraints.REMAINDER, 1, GridBagConstraints.NONE, 0.0, 0.0, new Insets( 0, 50,  0, 50));
      ComponentDeployer.deploy(cp, me,       GridBagConstraints.CENTER, GridBagConstraints.REMAINDER, 1, GridBagConstraints.NONE, 0.0, 0.0, new Insets( 0, 50,  0, 50));
      ComponentDeployer.deploy(cp, webSite,  GridBagConstraints.CENTER, GridBagConstraints.REMAINDER, 1, GridBagConstraints.NONE, 0.0, 0.0, new Insets( 0, 50,  0, 50));
      ComponentDeployer.deploy(cp, okButton, GridBagConstraints.SOUTH,  GridBagConstraints.REMAINDER, 1, GridBagConstraints.NONE, 0.0, 0.0, new Insets(20, 50, 10, 50));

      this.getRootPane().setDefaultButton(okButton);

      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            AboutDialog.this.dispose();
         }
      });

      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            AboutDialog.this.dispose();
         }
      });

      this.pack();
      WindowUtilities.centerOnScreen(this);
   }
}