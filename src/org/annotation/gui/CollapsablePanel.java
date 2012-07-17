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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;

// http://sourceforge.net/projects/wordfreak/

/**
 * A JPanel with a title bar that can be double clicked to collapse it, similar
 * to WindowShade on Macintosh and Gnome. There is also a colapse triangle to
 * expand colapse, and the panel can be dragged out of its parent container and
 * form a new window, which snaps back to its parent when closed.
 */

public class CollapsablePanel extends JPanel
{
    IndirectDataSource componentDataSource = null;

    Component cachedComponent = null;

    boolean collapsed = false;

    CollapsablePanelTitle title;

    JPanel panel;

    int startx = 0, starty = 0;

    int shiftx = 0, shifty = 0;

    List listeners = new ArrayList();

    public Component getComponentToDisplay()
    {
        if (null == cachedComponent)
        {
            cachedComponent = componentDataSource.getComponent();
        }

        return cachedComponent;
    }

    public CollapsablePanel(String t, IndirectDataSource componentDataSource)
    {
        this(t, componentDataSource, true);
    }

    public CollapsablePanel(String t, IndirectDataSource componentDataSource,
            boolean col)
    {
        super(new BorderLayout(0, 0));
        panel = new JPanel(new BorderLayout(0, 0));
        this.componentDataSource = componentDataSource;

        if (col)
        {
            collapsed = true;
        }
        else
        {
            collapsed = true;
            panel.add(getComponentToDisplay(), BorderLayout.CENTER);
        }
        title = new CollapsablePanelTitle(t, this);
        panel.add(title, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);

        title.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                title.b.mouseClicked(e);
                toggle();
            }
        });
    }

    public void addCollapseListener(CollapseListener l)
    {
        listeners.add(l);
    }

    public void removeCollapseListener(CollapseListener l)
    {
        listeners.remove(l);
    }

    public static final void main(String[] args)
    {
        JFrame frame = new JFrame("Test Frame");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(new CollapsablePanel("Tree", new IndirectDataSource()
        {
            public Component getComponent()
            {
                 return new JTree();
            }
        }),
                BorderLayout.CENTER);
        frame.getContentPane().add(
                new CollapsablePanel("Roles", new IndirectDataSource()
                {
                    public Component getComponent()
                    {
                        return new JTextArea("blah\nblah");
                    }
                }),
                BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    public Window getFrame()
    {
        if (panel.getTopLevelAncestor() instanceof Window)
            return (Window) panel.getTopLevelAncestor();
        else
            return null;
    }

    public void setTitle(String t)
    {
        title.setTitle(t);
    }

    public void toggle()
    {
        if (!collapsed)
            collapse();
        else
            expand();
    }

    public void collapse()
    {
        for (Iterator i = listeners.iterator(); i.hasNext();)
        {
            ((CollapseListener) i.next()).panelCollapsed();
        }
        collapsed = true;
        panel.remove(getComponentToDisplay());
        if (getFrame() != null)
        {
            getFrame().validate();
        }

    }

    public void expand()
    {
        for (Iterator i = listeners.iterator(); i.hasNext();)
        {
            ((CollapseListener) i.next()).panelExpanded();
        }
        collapsed = false;
        panel.add(getComponentToDisplay(), BorderLayout.CENTER);
        if (getFrame() != null)
        {
            getFrame().validate();
        }
    }

    public Dimension getPreferredSize()
    {
        if (collapsed)
            return title.getPreferredSize();
        else
            return super.getPreferredSize();
    }

    public Dimension getMinimumSize()
    {
        if (collapsed)
            return title.getMinimumSize();
        else
            return super.getMinimumSize();
    }

    public Dimension getMaximumSize()
    {
        if (collapsed)
            return title.getMaximumSize();
        else
            return super.getMaximumSize();
    }

    public boolean isCollapsed()
    {
        return collapsed;
    }

    class CollapsableWindowAdapter extends WindowAdapter
    {
        Component component;

        JPanel parent;

        public CollapsableWindowAdapter(JPanel p, Component c)
        {
            parent = p;
            component = c;
        }

        public void windowClosing(WindowEvent e)
        {
            windowClosed(e);
        }

        public void windowIconified(WindowEvent e)
        {
            e.getWindow().dispose();
        }

        public void windowClosed(WindowEvent e)
        {
            parent.add(component, BorderLayout.CENTER);
            Rectangle r = getFrame().getBounds();
            getFrame().setSize(r.width, r.height + component.getHeight());
            getFrame().validate();
        }

    }

    public interface CollapseListener
    {
        public void panelCollapsed();

        public void panelExpanded();
    }

    public interface IndirectDataSource
    {
        public Component getComponent();
    }
}



class CollapsablePanelTitle extends JPanel implements MouseListener
{

    CollapsablePanel p;

    CollapseButton b;

    CloseButton x;

    JLabel l;

    public static final Color bgColor = javax.swing.UIManager
            .getColor("ToggleButton.select"); //javax.swing.UIManager.getColor("Menu.selectionBackground");

    public static final Color fgColor = javax.swing.UIManager
            .getColor("InternalFrame.activeTitleForeground");

    //  public static final Border border =
    // javax.swing.UIManager.getBorder("ToggleButton.border");

    public CollapsablePanelTitle(String t, CollapsablePanel p)
    {
        this(t, p, bgColor, fgColor);
    }

    public CollapsablePanelTitle(String t, CollapsablePanel p, Color bg,
            Color fg)
    {
        super(new BorderLayout());
        setBackground(bg);
        l = new JLabel(t);
        l.setForeground(fg);
        //setBorder(border);

        b = new CollapseButton(p.isCollapsed());
        b.setBackground(null);
        b.setForeground(fg);
        b.addMouseListener(this);

        x = new CloseButton();
        x.setBackground(null);
        x.setForeground(fg);
        x.addMouseListener(this);

        add(b, BorderLayout.WEST);
        add(l, BorderLayout.CENTER);
        add(x, BorderLayout.EAST);
        this.p = p;
    }

    public void setTitle(String t)
    {
        l.setText(t);
    }

    public void setCloseEnabled(boolean b)
    {
        x.setActive(b);
    }

    public Dimension getMaximumSize()
    {
        Dimension d = super.getMaximumSize();
        d.height = 20;
        return d;
    }

    public Dimension getPreferredSize()
    {
        Dimension d = super.getPreferredSize();
        d.height = 20;
        return d;
    }

    public Dimension getMinimumSize()
    {
        Dimension d = super.getMinimumSize();
        d.height = 20;
        return d;
    }

    public void mousePressed(MouseEvent e)
    {
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void mouseClicked(MouseEvent e)
    {
        p.toggle();
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

}

