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
package org.gamenet.swing.controls;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gamenet.util.SubTaskObserver;

public class ComponentArrayPanel extends JPanel
{
    // null task observer
    private SubTaskObserver taskObserver = new SubTaskObserver() {
        public void taskProgress(float percentageDone) { }
    };
    
    private List componentList = null;
    private ComponentDataSource componentDataSource = null;
    private List componentControllerList = null;
    private ComponentArrayPanel thisComponentArrayPanel = this;
    
    public ComponentArrayPanel(SubTaskObserver taskObserver, List componentList, ComponentDataSource componentDataSource) throws InterruptedException
    {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.taskObserver = taskObserver;
        this.componentList = componentList;
        this.componentDataSource = componentDataSource;
        
        createComponentTable();
    }

    public ComponentArrayPanel(List componentList, ComponentDataSource componentDataSource) throws InterruptedException
    {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.componentList = componentList;
        this.componentDataSource = componentDataSource;
        
        createComponentTable();
    }

    private void createComponentTable() throws InterruptedException
    {
        componentControllerList = new ArrayList();
        
        for (int componentIndex = 0; componentIndex < componentList.size(); componentIndex++)
        {
            Component component = (Component)componentList.get(componentIndex);
            addControlsForComponent(component, componentIndex);
            
            taskObserver.taskProgress((1f / componentList.size()) * componentIndex);
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("createComponentTable() was interrupted.");
        }

        // Add button at end
        int componentIndex = componentList.size();
        
        final ComponentController componentController = new ComponentController(componentIndex);
        componentControllerList.add(componentIndex, componentController);
        
        JPanel componentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.add(componentPanel, componentIndex);

        URL addImageURL = getClass().getResource("/toolbarButtonGraphics/general/Add16.gif");
        JButton addButton = new JButton(new ImageIcon(addImageURL));
        addButton.setMargin(new Insets(0,0,0,0));
        componentPanel.add(addButton);

        componentController.setControls(null, addButton, null, null, null);

        final Runnable addRunnable = new Runnable()
        {
            public void run()
            {
                int componentIndex = componentController.getComponentIndex();
                
                Component newComponent = componentDataSource.createComponent(componentIndex);
                if (null == newComponent)  return;
                
                componentList.add(componentIndex, newComponent);
                thisComponentArrayPanel.addControlsForComponent(newComponent, componentIndex);
                
                refreshComponentControllers();
                
                componentDataSource.fireComponentAdded(componentIndex, newComponent);
            }
        };
        
        addButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        addRunnable.run();
                    }
                });


    }

    private void addControlsForComponent(Component component, int componentIndex)
    {
        JPanel componentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.add(componentPanel, componentIndex);

        final ComponentController componentController = new ComponentController(componentIndex);
        componentControllerList.add(componentIndex, componentController);
        
        JLabel idLabel = null;
        
        JButton addButton = null;
        JButton upButton = null;
        JButton downButton = null;
        JButton deleteButton = null;

        URL addImageURL = getClass().getResource("/toolbarButtonGraphics/general/Add16.gif");
        addButton = new JButton(new ImageIcon(addImageURL));
        addButton.setMargin(new Insets(0,0,0,0));
        componentPanel.add(addButton);

        URL upImageURL = getClass().getResource("/toolbarButtonGraphics/navigation/Up16.gif");
        upButton = new JButton(new ImageIcon(upImageURL));
        upButton.setMargin(new Insets(0,0,0,0));
        componentPanel.add(upButton);
        
        URL downImageURL = getClass().getResource("/toolbarButtonGraphics/navigation/Down16.gif");
        downButton = new JButton(new ImageIcon(downImageURL));
        downButton.setMargin(new Insets(0,0,0,0));
        componentPanel.add(downButton);
        
        URL deleteImageURL = getClass().getResource("/toolbarButtonGraphics/general/Delete16.gif");
        deleteButton = new JButton(new ImageIcon(deleteImageURL));
        deleteButton.setMargin(new Insets(0,0,0,0));
        componentPanel.add(deleteButton);

        idLabel = new JLabel("#" + String.valueOf(componentIndex) + " - ");

        componentController.setControls(idLabel, addButton, upButton, downButton, deleteButton);

        componentController.configureControls();
        
        componentPanel.add(idLabel);

        final Runnable addRunnable = new Runnable()
        {
            public void run()
            {
                int componentIndex = componentController.getComponentIndex();
                
                Component newComponent = componentDataSource.createComponent(componentIndex);
                if (null == newComponent)  return;
                
                componentList.add(componentIndex, newComponent);
                thisComponentArrayPanel.addControlsForComponent(newComponent, componentIndex);
                
                refreshComponentControllers();
                
                componentDataSource.fireComponentAdded(componentIndex, newComponent);
            }
        };
        
        final Runnable deleteRunnable = new Runnable()
        {
            public void run()
            {
                int componentIndex = componentController.getComponentIndex();
                
                Component component = (Component)componentList.remove(componentIndex);
                 
                thisComponentArrayPanel.remove(componentIndex);
                componentControllerList.remove(componentIndex);
                
                refreshComponentControllers();
                
                componentDataSource.fireComponentDeleted(componentIndex, component);
            }
        };
        
        final Runnable upRunnable = new Runnable()
        {
            public void run()
            {
                int componentIndex = componentController.getComponentIndex();

                Component component = (Component)componentList.remove(componentIndex);
                componentList.add(componentIndex - 1, component);
                
                Component componentControl = thisComponentArrayPanel.getComponent(componentIndex);
                thisComponentArrayPanel.remove(componentIndex);
                thisComponentArrayPanel.add(componentControl, componentIndex - 1);

                componentControllerList.remove(componentIndex);
                componentControllerList.add(componentIndex - 1, componentController);

                refreshComponentControllers();
                
                componentDataSource.fireComponentMovedUp(componentIndex, component);
            }
        };
        
        final Runnable downRunnable = new Runnable()
        {
            public void run()
            {
                int componentIndex = componentController.getComponentIndex();

                Component component = (Component)componentList.remove(componentIndex);
                componentList.add(componentIndex + 1, component);
                
                Component componentControl = thisComponentArrayPanel.getComponent(componentIndex);
                thisComponentArrayPanel.remove(componentIndex);
                thisComponentArrayPanel.add(componentControl, componentIndex + 1);
                
                componentControllerList.remove(componentIndex);
                componentControllerList.add(componentIndex + 1, componentController);

                refreshComponentControllers();
                
                componentDataSource.fireComponentMovedDown(componentIndex, component);
            }
        };
        
        addButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        addRunnable.run();
                    }
                });

        upButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        upRunnable.run();
                    }
                });
        
        downButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        downRunnable.run();
                    }
                });
        
        deleteButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        deleteRunnable.run();
                    }
                });
        
        
        componentPanel.add(component);
    }

    private void refreshComponentControllers()
    {
        for (int componentIndex = 0; componentIndex < componentControllerList.size(); ++componentIndex)
        {
            ComponentController componentController = (ComponentController)componentControllerList.get(componentIndex);
            componentController.setComponentIndex(componentIndex);

            this.revalidate();
            this.repaint();
        }
    }

    public interface ComponentDataSource
    {
        public Component createComponent(int componentIndex);
        
        public void fireComponentAdded(int componentIndex, Component component);
        public void fireComponentDeleted(int componentIndex, Component component);
        public void fireComponentMovedUp(int componentIndex, Component component);
        public void fireComponentMovedDown(int componentIndex, Component component);
    }
    
    private class ComponentController
    {
        private JLabel idLabel = null;
        private JButton addButton = null;
        private JButton upButton = null;
        private JButton downButton = null;
        private JButton deleteButton = null;

        private int componentIndex = -1;
        
        public ComponentController(int componentIndex)
        {
            this.componentIndex = componentIndex;
        }
        
        public int getComponentIndex()
        {
            return this.componentIndex;
        }
        public void setComponentIndex(int componentIndex)
        {
            this.componentIndex = componentIndex;
            configureControls();
        }
        
        public void setControls(JLabel idLabel, JButton addButton, JButton upButton, JButton downButton, JButton deleteButton)
        {
            this.addButton = addButton;
            this.upButton = upButton;
            this.downButton = downButton;
            this.deleteButton = deleteButton;
            this.idLabel = idLabel;
        }

        private static final int FIRST_EVENT_POSITION = 0;
        private static final int OTHER_EVENT_POSITION = 1;
        private static final int LAST_EVENT_POSITION = 2;
        private static final int FIRST_AND_LAST_EVENT_POSITION = 3;
        
        public void configureControls()
        {
            int componentPosition = OTHER_EVENT_POSITION;
            if (componentIndex == 0)
                componentPosition = FIRST_EVENT_POSITION;
            if (componentIndex == (componentList.size() - 1))
                componentPosition = LAST_EVENT_POSITION;
            if ((componentIndex == 0) && (componentIndex == (componentList.size() - 1)))
                componentPosition = FIRST_AND_LAST_EVENT_POSITION;

            if (null != upButton)
            {
                if ((componentPosition != FIRST_EVENT_POSITION) && (componentPosition != FIRST_AND_LAST_EVENT_POSITION))
                {
                    upButton.setEnabled(true);
                }
                else
                {
                    upButton.setEnabled(false);
                }
            }
            
            if (null != downButton)
            {
                if ((componentPosition != LAST_EVENT_POSITION) && (componentPosition != FIRST_AND_LAST_EVENT_POSITION))
                {
                    downButton.setEnabled(true);
                }
                else
                {
                    downButton.setEnabled(false);
                }
            }
            
            if (null != idLabel)
            {
                idLabel.setText("#" + String.valueOf(componentIndex) + " - ");
            }
        }
    }
    
}