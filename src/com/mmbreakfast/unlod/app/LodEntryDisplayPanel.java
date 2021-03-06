/*
 *  com/mmbreakfast/unlod/app/LodEntryDisplayPanel.java
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

import javax.swing.*;
import java.awt.*;


public class LodEntryDisplayPanel extends JPanel {
	protected Component comp = new JPanel();

   public LodEntryDisplayPanel() {
   	this.add(comp, BorderLayout.CENTER);
   }

   public void resetDisplayedComponent() {
   	this.setDisplayedComponent(new JPanel());
   }

   protected void setComp(Component comp) {
      this.remove(this.comp);
      this.comp = comp;
      this.add(this.comp, BorderLayout.CENTER);
   	this.revalidate();
   	this.repaint();
   }

   private Component getComp() {
      return comp;
   }

   private void removeComp() {
      this.remove(comp);
   }

	public void setDisplayedComponent(final Component comp) {
		EventQueue.invokeLater(new Runnable() {
		   public void run() {
         	LodEntryDisplayPanel.this.setComp(comp);
         }
      });
	}

}