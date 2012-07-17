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

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.Graphics;

public class CollapseButton extends Component implements MouseListener{
  
  boolean collapsed;

  public CollapseButton() {
    this(false);
  }

  public CollapseButton(boolean c) {
    collapsed = c;
    this.addMouseListener(this);
  }

  public boolean getCollapsed() {
    return collapsed;
  }

  public void setCollapsed(boolean c) {
    collapsed = c;
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
    if (collapsed) {
      int[] xpoints = {7, 12, 7};
      int[] ypoints = {6, 10, 15};
      g.fillPolygon(xpoints, ypoints, 3);
    }
    else {
      int[] xpoints = {6, 10, 15};
      int[] ypoints = {7, 12, 7};
      g.fillPolygon(xpoints, ypoints, 3);
    }
  }
  
  public void mouseClicked (MouseEvent e) {
      collapsed = !collapsed;
      repaint();
  }
  public void mouseEntered (MouseEvent e) {}
  public void mouseExited  (MouseEvent e) {}
  public void mousePressed (MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {
  }
}
