/*
 * The contents of this file are subject to the Mozilla Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 * 
 * The Original Code is the WordFreak annotation tool.
 *
 * The Initial Developer of the Original Code is Thomas S. Morton
 * Copyright (C) 2002.  All Rights Reserved.
 * 
 * Contributor(s):
 *   Thomas S. Morton <tsmorton@cis.upenn.edu> (Original Author)
 *   Jeremy LaCivita <lacivita@linc.cis.upenn.edu>
 */

package org.annotation.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

public class CloseButton extends Component {
  
  boolean active;

  public CloseButton() {
    this(false);
  }

  public CloseButton(boolean a) {
    active = a;
  }

  public void setActive(boolean a) {
    active = a;
    repaint();
  }

  public boolean getActive() {
    return active;
  }

  public Dimension getMaximumSize() {
    return new Dimension(20, 20);
  }

  public Dimension getPreferredSize() {
    return new Dimension(20, 20);
  }

  public Dimension getMinimumSize() {
    return new Dimension(20, 20);
  }  

  public void paint(Graphics g) {
    g.setColor(getForeground());
    if (active) {
      int[] xpoints = { 4, 5,10,15,16,12,16,15,10, 5, 4, 8};
      int[] ypoints = { 5, 4, 8, 4, 5,10,15,16,12,16,15,10};
      g.fillPolygon(xpoints, ypoints, 12);
    }
    else {
    }
  }
  
  /*
  public void mouseClicked (MouseEvent e) {}
  public void mouseEntered (MouseEvent e) {}
  public void mouseExited  (MouseEvent e) {}
  public void mousePressed (MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  */
}
