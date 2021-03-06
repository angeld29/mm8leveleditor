/*
 *  com/mmbreakfast/util/ComponentDeployer.java
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

package com.mmbreakfast.util;

import java.awt.*;


public class ComponentDeployer {

   private ComponentDeployer() {}  //Defeat instantiation

   public static final void deploy(Container container,
                                   Component component,
                                   int anchor,
                                   int gridwidth,
                                   int gridheight,
                                   int fill,
                                   double weightx,
                                   double weighty,
                                   Insets insets) {
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.anchor    = anchor;
      gbc.gridwidth = gridwidth;
      gbc.fill      = fill;
      gbc.weightx   = weightx;
      gbc.weighty   = weighty;
      gbc.insets    = insets;
      container.add(component, gbc);
   }

   public static final void deploy(Container container,
                                   Component component,
                                   int anchor,
                                   int gridwidth,
                                   int gridheight,
                                   int gridx,
                                   int gridy,
                                   int fill,
                                   double weightx,
                                   double weighty,
                                   Insets insets) {
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.anchor    = anchor;
      gbc.gridwidth = gridwidth;
      gbc.gridx     = gridx;
      gbc.gridy     = gridy;
      gbc.fill      = fill;
      gbc.weightx   = weightx;
      gbc.weighty   = weighty;
      gbc.insets    = insets;
      container.add(component, gbc);
   }

}