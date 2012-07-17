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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;

import org.gamenet.application.mm8leveleditor.data.mm6.Event_3_46;
import org.gamenet.swing.controls.AbstractStringListCellRenderer;
import org.gamenet.swing.controls.ByteDataTableControl;
import org.gamenet.swing.controls.IntValueHolder;
import org.gamenet.swing.controls.Vertex2DValueHolder;
import org.gamenet.swing.controls.Vertex2DTextFieldPanel;
import org.gamenet.swing.controls.Vertex3DValueHolder;
import org.gamenet.swing.controls.Vertex3DTextFieldPanel;
import org.gamenet.util.ByteConversions;

public class Event_3_46_Control extends JPanel
{
    private Event_3_46 event = null;
    
    public Event_3_46_Control(Event_3_46 anEvent)
    {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.event = anEvent;
        

        JPanel flowLayoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.add(flowLayoutPanel);
        
        flowLayoutPanel.add(new JLabel("Event#"));
        JFormattedTextField eventNumberTextField = new JFormattedTextField(new Integer(this.event.getEventNumber()));
        eventNumberTextField.setColumns(5);
        eventNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        event.setEventNumber(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        flowLayoutPanel.add(eventNumberTextField);

        flowLayoutPanel.add(new JLabel("Seq#"));
        JFormattedTextField eventSequenceNumberTextField = new JFormattedTextField(new Integer(this.event.getEventSequenceNumber()));
        eventSequenceNumberTextField.setColumns(3);
        eventSequenceNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        event.setEventSequenceNumber(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        flowLayoutPanel.add(eventSequenceNumberTextField);

        flowLayoutPanel.add(new JLabel(event.getEventCommandName()));
        
        if (event.isUnderstood())
        {
            addEventControls(flowLayoutPanel);
        }
        else
        {
            JPanel flowLayoutPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            this.add(flowLayoutPanel2);
            flowLayoutPanel2.add(new JLabel(event.eventDescription()));

    	    ByteDataTableControl eventBDTC = new ByteDataTableControl(event.getData(), event.getData().length, event.getOffset());
    	    this.add(eventBDTC);
    	    
    	    this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        }
        
    }

    private void addEventControls(JPanel eventControlsPanel)
    {
        JFormattedTextField targetTypeTextField = null;
        JFormattedTextField targetNumberTextField = null;
        JFormattedTextField affectedTextField = null;
        JFormattedTextField stringIDTextField = null;
        
        switch(this.event.getEventCommandNumber())
        {
            case Event_3_46.EVENT_COMMAND__STOP_PROCESSING:
                return;
            case Event_3_46.EVENT_COMMAND__IDENTITY:
                eventControlsPanel.add(new JLabel("2dEvent#"));
                JFormattedTextField identityTextField = new JFormattedTextField(new Integer(ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.IDENTITY_ARGS_2DEVENT_ID_OFFSET)));
                identityTextField.setColumns(5);
                identityTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                        {
                            public void propertyChange(PropertyChangeEvent e)
                            {
                                ByteConversions.setIntegerInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue(), event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.IDENTITY_ARGS_2DEVENT_ID_OFFSET);
                            }
                        });
                eventControlsPanel.add(identityTextField);
                return;
            case Event_3_46.EVENT_COMMAND__MOUSEOVER:
                eventControlsPanel.add(new JLabel("2dEvent#"));
                JFormattedTextField eventStrTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.MOUSEOVER_ARGS_EVENT_STR_ID_OFFSET])));
                eventStrTextField.setColumns(3);
                eventStrTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                        {
                            public void propertyChange(PropertyChangeEvent e)
                            {
                                event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.MOUSEOVER_ARGS_EVENT_STR_ID_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                            }
                        });
                eventControlsPanel.add(eventStrTextField);
                return;
            case Event_3_46.EVENT_COMMAND__TELEPORT:
                eventControlsPanel.add(new JLabel("destination:"));
                JFormattedTextField levelNameTextField = new JFormattedTextField(event.getCommandTeleportArgumentLevelName());
                levelNameTextField.setColumns(10);
                levelNameTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                        {
                            public void propertyChange(PropertyChangeEvent e)
                            {
                                event.setCommandTeleportArgumentLevelName((String)((JFormattedTextField)e.getSource()).getValue());
                            }
                        });
                eventControlsPanel.add(levelNameTextField);
                
                // coordinates
                eventControlsPanel.add(new JLabel("coordinates:"));
                eventControlsPanel.add(new Vertex3DTextFieldPanel(new Vertex3DValueHolder()
                        {
                            public int getX()
                            {
                                return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_X_COORD_OFFSET);
                            }

                            public void setX(int x)
                            {
                                ByteConversions.setIntegerInByteArrayAtPosition(x, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_X_COORD_OFFSET);
                            }

                            public int getY()
                            {
                                return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_Y_COORD_OFFSET);
                            }

                            public void setY(int y)
                            {
                                ByteConversions.setIntegerInByteArrayAtPosition(y, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_Y_COORD_OFFSET);
                            }

                            public int getZ()
                            {
                                return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_Z_COORD_OFFSET);
                            }

                            public void setZ(int z)
                            {
                                ByteConversions.setIntegerInByteArrayAtPosition(z, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_Z_COORD_OFFSET);
                            }
                        }));
                
                eventControlsPanel.add(new JLabel("facing:"));
                eventControlsPanel.add(new Vertex2DTextFieldPanel(new Vertex2DValueHolder()
                        {
                            public int getX()
                            {
                                return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_HORIZONTAL_ORIENTATION_OFFSET);
                            }

                            public void setX(int x)
                            {
                                ByteConversions.setIntegerInByteArrayAtPosition(x, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_HORIZONTAL_ORIENTATION_OFFSET);
                            }

                            public int getY()
                            {
                                return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_VERTICAL_ORIENTATION_OFFSET);
                            }

                            public void setY(int y)
                            {
                                ByteConversions.setIntegerInByteArrayAtPosition(y, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_VERTICAL_ORIENTATION_OFFSET);
                            }
                        }));

                int dialogValue = ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET]);
                int iconValue = ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_MINIICON_OFFSET]);
                
                JCheckBox immediateCheckBox = new JCheckBox("Immediately");
                eventControlsPanel.add(immediateCheckBox);
                
                final JLabel dialogLabel = new JLabel("or via Dialog#");
                eventControlsPanel.add(dialogLabel);
                final JFormattedTextField dialogTextField = new JFormattedTextField(new Integer(dialogValue));
                dialogTextField.setColumns(3);
                dialogTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                        {
                            public void propertyChange(PropertyChangeEvent e)
                            {
                                event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                            }
                        });
                eventControlsPanel.add(dialogTextField);

                final JLabel iconLabel = new JLabel("icon#");
                eventControlsPanel.add(iconLabel);
                final JComboBox miniIconList = new JComboBox(Event_3_46.getMiniIconNames());
                miniIconList.setSelectedIndex(iconValue);
                miniIconList.addActionListener(new ActionListener()
                        {
                            public void actionPerformed(ActionEvent e)
                            {
                                event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(((JComboBox)e.getSource()).getSelectedIndex());
                            }
                        }
                );
                eventControlsPanel.add(miniIconList);

                boolean immediately = ((0 == dialogValue) && (0 == iconValue));
                if (immediately)
                {
                    event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(0);
                    event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(0);
                    dialogLabel.setEnabled(false);
                    dialogTextField.setEnabled(false);
                    iconLabel.setEnabled(false);
                    miniIconList.setEnabled(false);
                }
                else
                {
                    dialogLabel.setEnabled(true);
                    dialogTextField.setEnabled(true);
                    iconLabel.setEnabled(true);
                    miniIconList.setEnabled(true);
                    event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)dialogTextField.getValue()).intValue());
                    event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(miniIconList.getSelectedIndex());
                }

                immediateCheckBox.setSelected(immediately);
                immediateCheckBox.addItemListener(new ItemListener()
                        {
                            public void itemStateChanged(ItemEvent e)
                            {
                                if (e.getStateChange() == ItemEvent.DESELECTED)
                                {
                                    dialogLabel.setEnabled(true);
                                    dialogTextField.setEnabled(true);
                                    iconLabel.setEnabled(true);
                                    miniIconList.setEnabled(true);
                                    event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)dialogTextField.getValue()).intValue());
                                    event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(miniIconList.getSelectedIndex());
                                }
                                else if (e.getStateChange() == ItemEvent.SELECTED)
                                {
                                    event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(0);
                                    event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(0);
                                    dialogLabel.setEnabled(false);
                                    dialogTextField.setEnabled(false);
                                    iconLabel.setEnabled(false);
                                    miniIconList.setEnabled(false);
                                }
                            }
                        }
                );

                return;
            case Event_3_46.EVENT_COMMAND__OPEN_CHEST:
                eventControlsPanel.add(new JLabel("#"));
                JFormattedTextField chestIDTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.OPEN_CHEST_ARGS_CHEST_NUMBER_OFFSET])));
                chestIDTextField.setColumns(3);
                chestIDTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                        {
                            public void propertyChange(PropertyChangeEvent e)
                            {
                                event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.OPEN_CHEST_ARGS_CHEST_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                            }
                        });
                eventControlsPanel.add(chestIDTextField);
                return;
            case Event_3_46.EVENT_COMMAND__SHOW_FACIAL_EXPRESSION:
                eventControlsPanel.add(new JLabel("#"));
                JFormattedTextField imageIDTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.SHOW_FACIAL_EXPRESSION_ARGS_IMAGE_ID_OFFSET])));
                imageIDTextField.setColumns(3);
                imageIDTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                        {
                            public void propertyChange(PropertyChangeEvent e)
                            {
                                event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.SHOW_FACIAL_EXPRESSION_ARGS_IMAGE_ID_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                            }
                        });
                eventControlsPanel.add(imageIDTextField);
                eventControlsPanel.add(new JLabel("for"));
                // IMPLEMENT:  replace this with a pulldown combobox
                affectedTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.SHOW_FACIAL_EXPRESSION_ARGS_AFFECTED_OFFSET])));
                affectedTextField.setColumns(3);
                affectedTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                        {
                            public void propertyChange(PropertyChangeEvent e)
                            {
                                event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.SHOW_FACIAL_EXPRESSION_ARGS_AFFECTED_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                            }
                        });
                eventControlsPanel.add(affectedTextField);
                return;
            case Event_3_46.EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP:
                eventControlsPanel.add(new JLabel("object #"));
	            JFormattedTextField d3ObjectTextField = new JFormattedTextField(new Integer(ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CHANGE_3DOBJECT_FACE_BITMAP_ARGS_3D_OBJECT_NUMBER_OFFSET)));
	            d3ObjectTextField.setColumns(5);
	            d3ObjectTextField.addPropertyChangeListener("value", new PropertyChangeListener()
	                    {
	                        public void propertyChange(PropertyChangeEvent e)
	                        {
	                            ByteConversions.setIntegerInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue(), event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CHANGE_3DOBJECT_FACE_BITMAP_ARGS_3D_OBJECT_NUMBER_OFFSET);
	                        }
	                    });
	            eventControlsPanel.add(d3ObjectTextField);
                eventControlsPanel.add(new JLabel("face #"));
	            JFormattedTextField faceTextField = new JFormattedTextField(new Integer(ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CHANGE_3DOBJECT_FACE_BITMAP_ARGS_FACE_OFFSET)));
	            faceTextField.setColumns(5);
	            faceTextField.addPropertyChangeListener("value", new PropertyChangeListener()
	                    {
	                        public void propertyChange(PropertyChangeEvent e)
	                        {
	                            ByteConversions.setIntegerInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue(), event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CHANGE_3DOBJECT_FACE_BITMAP_ARGS_FACE_OFFSET);
	                        }
	                    });
	            eventControlsPanel.add(faceTextField);
                eventControlsPanel.add(new JLabel("to"));
                JFormattedTextField newBitmapNameTextField = new JFormattedTextField(event.getCommandChangeFaceBitmapArgumentNewBitmapName());
                newBitmapNameTextField.setColumns(10);
                newBitmapNameTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                        {
                            public void propertyChange(PropertyChangeEvent e)
                            {
                                event.setCommandChangeFaceBitmapArgumentNewBitmapName((String)((JFormattedTextField)e.getSource()).getValue());
                            }
                        });
                eventControlsPanel.add(newBitmapNameTextField);
                return;
            case Event_3_46.EVENT_COMMAND__CHANGE_SPRITE:
                eventControlsPanel.add(new JLabel("#"));
                JFormattedTextField spriteNumberTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CHANGE_SPRITE_ARGS_SPRITE_NUMBER])));
	            spriteNumberTextField.setColumns(3);
	            spriteNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
	                    {
	                        public void propertyChange(PropertyChangeEvent e)
	                        {
	                            event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CHANGE_SPRITE_ARGS_SPRITE_NUMBER] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
	                        }
	                    });
	            eventControlsPanel.add(spriteNumberTextField);
                eventControlsPanel.add(new JLabel("to"));
                JFormattedTextField newSpriteNameTextField = new JFormattedTextField(event.getCommandChangeSpriteArgumentNewSpriteName());
                newSpriteNameTextField.setColumns(10);
                newSpriteNameTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                        {
                            public void propertyChange(PropertyChangeEvent e)
                            {
                                event.setCommandChangeSpriteArgumentNewSpriteName((String)((JFormattedTextField)e.getSource()).getValue());
                            }
                        });
                eventControlsPanel.add(newSpriteNameTextField);
                return;
            case Event_3_46.EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE:
                eventControlsPanel.add(new JLabel("if ("));
                addTargetControlsToPanel(eventControlsPanel, new IntValueHolder()
                        {
                           public int getValue()
                           {
                               return ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_TYPE_OFFSET]);
                           }

                           public void setValue(int value)
                           {
	                            event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_TYPE_OFFSET] = ByteConversions.convertIntToByte(value);
                           }
                        },
                        new IntValueHolder()
                        {
                            public int getValue()
                            {
                                return ByteConversions.getUnsignedShortInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_NUMBER_OFFSET);
                            }

                            public void setValue(int value)
                            {
								 ByteConversions.setShortInByteArrayAtPosition((short)value, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.BRANCH_ON_CONDITION_TRUE_ARGS_TARGET_NUMBER_OFFSET);
                            }
                        });

                eventControlsPanel.add(new JLabel(") goto seq#"));
                JFormattedTextField targetSequenceNumberTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.BRANCH_ON_CONDITION_TRUE_ARGS_GOTO_SEQUENCE_NUMBER_OFFSET])));
                targetSequenceNumberTextField.setColumns(3);
                targetSequenceNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                        {
                            public void propertyChange(PropertyChangeEvent e)
                            {
                                event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.BRANCH_ON_CONDITION_TRUE_ARGS_GOTO_SEQUENCE_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                            }
                        });
                eventControlsPanel.add(targetSequenceNumberTextField);
                return;
            case Event_3_46.EVENT_COMMAND__ADD_TARGET:
                addTargetControlsToPanel(eventControlsPanel, new IntValueHolder()
                        {
                           public int getValue()
                           {
                               return ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.ADD_TARGET_ARGS_TARGET_TYPE_OFFSET]);
                           }

                           public void setValue(int value)
                           {
	                            event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.ADD_TARGET_ARGS_TARGET_TYPE_OFFSET] = ByteConversions.convertIntToByte(value);
                           }
                        },
                        new IntValueHolder()
                        {
                            public int getValue()
                            {
                                return ByteConversions.getUnsignedShortInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.ADD_TARGET_ARGS_TARGET_NUMBER_OFFSET);
                            }

                            public void setValue(int value)
                            {
								 ByteConversions.setShortInByteArrayAtPosition((short)value, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.ADD_TARGET_ARGS_TARGET_NUMBER_OFFSET);
                            }
                        });
                return;
            case Event_3_46.EVENT_COMMAND__DELETE_TARGET:
                addTargetControlsToPanel(eventControlsPanel, new IntValueHolder()
                        {
                           public int getValue()
                           {
                               return ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.DELETE_TARGET_ARGS_TARGET_TYPE_OFFSET]);
                           }

                           public void setValue(int value)
                           {
	                            event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.DELETE_TARGET_ARGS_TARGET_TYPE_OFFSET] = ByteConversions.convertIntToByte(value);
                           }
                        },
                        new IntValueHolder()
                        {
                            public int getValue()
                            {
                                return ByteConversions.getUnsignedShortInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.DELETE_TARGET_ARGS_TARGET_NUMBER_OFFSET);
                            }

                            public void setValue(int value)
                            {
								 ByteConversions.setShortInByteArrayAtPosition((short)value, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.DELETE_TARGET_ARGS_TARGET_NUMBER_OFFSET);
                            }
                        });
                return;
             case Event_3_46.EVENT_COMMAND__SET_TARGET:
                 addTargetControlsToPanel(eventControlsPanel, new IntValueHolder()
                         {
                            public int getValue()
                            {
                                return ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.SET_TARGET_ARGS_TARGET_TYPE_OFFSET]);
                            }

                            public void setValue(int value)
                            {
	                            event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.SET_TARGET_ARGS_TARGET_TYPE_OFFSET] = ByteConversions.convertIntToByte(value);
                            }
                         },
                         new IntValueHolder()
                         {
                             public int getValue()
                             {
                                 return ByteConversions.getUnsignedShortInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.SET_TARGET_ARGS_TARGET_NUMBER_OFFSET);
                             }

                             public void setValue(int value)
                             {
								 ByteConversions.setShortInByteArrayAtPosition((short)value, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.SET_TARGET_ARGS_TARGET_NUMBER_OFFSET);
                             }
                         });
                return;
             case Event_3_46.EVENT_COMMAND__CREATE_LOCAL_MONSTER:
                 eventControlsPanel.add(new JLabel("count"));
                 JFormattedTextField numberToCreateTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CREATE_LOCAL_MONSTER_ARGS_COUNT_OFFSET])));
	             numberToCreateTextField.setColumns(3);
	             numberToCreateTextField.addPropertyChangeListener("value", new PropertyChangeListener()
	                     {
	                         public void propertyChange(PropertyChangeEvent e)
	                         {
	                             event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CREATE_LOCAL_MONSTER_ARGS_COUNT_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
	                         }
	                     });
	             eventControlsPanel.add(numberToCreateTextField);
	
                 eventControlsPanel.add(new JLabel("species #"));
	             JFormattedTextField speciesTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CREATE_LOCAL_MONSTER_ARGS_MONSTER_SPECIES_OFFSET])));
	             speciesTextField.setColumns(3);
	             speciesTextField.addPropertyChangeListener("value", new PropertyChangeListener()
	                     {
	                         public void propertyChange(PropertyChangeEvent e)
	                         {
	                             event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CREATE_LOCAL_MONSTER_ARGS_MONSTER_SPECIES_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
	                         }
	                     });
	             eventControlsPanel.add(speciesTextField);
                 eventControlsPanel.add(new JLabel("-"));
	             JFormattedTextField subspeciesTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CREATE_LOCAL_MONSTER_ARGS_MONSTER_SUBSPECIES__OFFSET])));
	             subspeciesTextField.setColumns(3);
	             subspeciesTextField.addPropertyChangeListener("value", new PropertyChangeListener()
	                     {
	                         public void propertyChange(PropertyChangeEvent e)
	                         {
	                             event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CREATE_LOCAL_MONSTER_ARGS_MONSTER_SUBSPECIES__OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
	                         }
	                     });
	             eventControlsPanel.add(subspeciesTextField);

                 // coordinates
                 eventControlsPanel.add(new JLabel("at"));
                 eventControlsPanel.add(new Vertex3DTextFieldPanel(new Vertex3DValueHolder()
                         {
                             public int getX()
                             {
                                 return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CREATE_LOCAL_MONSTER_ARGS_X_OFFSET);
                             }

                             public void setX(int x)
                             {
                                 ByteConversions.setIntegerInByteArrayAtPosition(x, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CREATE_LOCAL_MONSTER_ARGS_X_OFFSET);
                             }

                             public int getY()
                             {
                                 return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CREATE_LOCAL_MONSTER_ARGS_Y_OFFSET);
                             }

                             public void setY(int y)
                             {
                                 ByteConversions.setIntegerInByteArrayAtPosition(y, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CREATE_LOCAL_MONSTER_ARGS_Y_OFFSET);
                             }

                             public int getZ()
                             {
                                 return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CREATE_LOCAL_MONSTER_ARGS_Z_OFFSET);
                             }

                             public void setZ(int z)
                             {
                                 ByteConversions.setIntegerInByteArrayAtPosition(z, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CREATE_LOCAL_MONSTER_ARGS_Z_OFFSET);
                             }
                         }));
                 return;
             case Event_3_46.EVENT_COMMAND__CAST_SPELL_FROM_LOCATION:
                 eventControlsPanel.add(new JLabel("Spell #"));
                 JFormattedTextField spellNumberTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CAST_SPELL_FROM_LOCATION_ARGS_SPELL_NUMBER_OFFSET])));
	             spellNumberTextField.setColumns(3);
	             spellNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
	                     {
	                         public void propertyChange(PropertyChangeEvent e)
	                         {
	                             event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CAST_SPELL_FROM_LOCATION_ARGS_SPELL_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
	                         }
	                     });
	             eventControlsPanel.add(spellNumberTextField);
                 eventControlsPanel.add(new JLabel("at ranking"));
	             JFormattedTextField spellRankingTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CAST_SPELL_FROM_LOCATION_ARGS_RANKING_OFFSET])));
	             spellRankingTextField.setColumns(3);
	             spellRankingTextField.addPropertyChangeListener("value", new PropertyChangeListener()
	                     {
	                         public void propertyChange(PropertyChangeEvent e)
	                         {
	                             event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CAST_SPELL_FROM_LOCATION_ARGS_RANKING_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
	                         }
	                     });
	             eventControlsPanel.add(spellRankingTextField);
                 eventControlsPanel.add(new JLabel("level"));
	             JFormattedTextField spellLevelTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CAST_SPELL_FROM_LOCATION_ARGS_LEVEL_OFFSET])));
	             spellRankingTextField.setColumns(3);
	             spellRankingTextField.addPropertyChangeListener("value", new PropertyChangeListener()
	                     {
	                         public void propertyChange(PropertyChangeEvent e)
	                         {
	                             event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CAST_SPELL_FROM_LOCATION_ARGS_LEVEL_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
	                         }
	                     });
	             eventControlsPanel.add(spellLevelTextField);
                 // coordinates
                 eventControlsPanel.add(new JLabel("from"));
                 eventControlsPanel.add(new Vertex3DTextFieldPanel(new Vertex3DValueHolder()
                         {
                             public int getX()
                             {
                                 return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_X_OFFSET);
                             }

                             public void setX(int x)
                             {
                                 ByteConversions.setIntegerInByteArrayAtPosition(x, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_X_OFFSET);
                             }

                             public int getY()
                             {
                                 return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Y_OFFSET);
                             }

                             public void setY(int y)
                             {
                                 ByteConversions.setIntegerInByteArrayAtPosition(y, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Y_OFFSET);
                             }

                             public int getZ()
                             {
                                 return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Z_OFFSET);
                             }

                             public void setZ(int z)
                             {
                                 ByteConversions.setIntegerInByteArrayAtPosition(z, event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Z_OFFSET);
                             }
                         }));
                 return;
             case Event_3_46.EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING:
                eventControlsPanel.add(new JLabel("#"));
				stringIDTextField = new JFormattedTextField(new Integer(ByteConversions.getUnsignedShortInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.SHOW_LOCAL_EVENT_STRING_ARGS_STR_ID_OFFSET)));
				stringIDTextField.setColumns(5);
				stringIDTextField.addPropertyChangeListener("value", new PropertyChangeListener()
				        {
				            public void propertyChange(PropertyChangeEvent e)
				            {
	                            ByteConversions.setShortInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).shortValue(), event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.SHOW_LOCAL_EVENT_STRING_ARGS_STR_ID_OFFSET);
				            }
				        });
				eventControlsPanel.add(stringIDTextField);
				return;
             case Event_3_46.EVENT_COMMAND__SHOW_NPCTEXT_STRING:
                eventControlsPanel.add(new JLabel("#"));
				stringIDTextField = new JFormattedTextField(new Integer(ByteConversions.getUnsignedShortInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.SHOW_NPCTEXT_STRING_ARGS_STR_ID_OFFSET)));
				stringIDTextField.setColumns(5);
				stringIDTextField.addPropertyChangeListener("value", new PropertyChangeListener()
				        {
				            public void propertyChange(PropertyChangeEvent e)
				            {
	                            ByteConversions.setShortInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).shortValue(), event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.SHOW_NPCTEXT_STRING_ARGS_STR_ID_OFFSET);
				            }
				        });
				eventControlsPanel.add(stringIDTextField);
				return;
             case Event_3_46.EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER:
                 eventControlsPanel.add(new JLabel("#"));
	             // IMPLEMENT:  replace this with a pulldown combobox
	             affectedTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER_ARGS_AFFECTED_OFFSET])));
	             affectedTextField.setColumns(3);
	             affectedTextField.addPropertyChangeListener("value", new PropertyChangeListener()
	                     {
	                         public void propertyChange(PropertyChangeEvent e)
	                         {
	                             event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER_ARGS_AFFECTED_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
	                         }
	                     });
	             eventControlsPanel.add(affectedTextField);
             return;
             case Event_3_46.EVENT_COMMAND__GOTO:
                 eventControlsPanel.add(new JLabel("seq#"));
                 JFormattedTextField sequenceNumberTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.GOTO_ARGS_SEQUENCE_NUMBER_OFFSET])));
                 sequenceNumberTextField.setColumns(3);
                 sequenceNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                         {
                             public void propertyChange(PropertyChangeEvent e)
                             {
                                 event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.GOTO_ARGS_SEQUENCE_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                             }
                         });
                 eventControlsPanel.add(sequenceNumberTextField);
                 return;
             case Event_3_46.EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE:
                 return;
             case Event_3_46.EVENT_COMMAND__CHANGE_DIALOG_EVENT:
	             eventControlsPanel.add(new JLabel("NPC#"));
	             JFormattedTextField npcTextField = new JFormattedTextField(new Integer(ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CHANGE_DIALOG_EVENT_ARGS_NPCDATA_OFFSET)));
	             npcTextField.setColumns(5);
	             npcTextField.addPropertyChangeListener("value", new PropertyChangeListener()
	                     {
	                         public void propertyChange(PropertyChangeEvent e)
	                         {
	                             ByteConversions.setIntegerInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue(), event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CHANGE_DIALOG_EVENT_ARGS_NPCDATA_OFFSET);
	                         }
	                     });
	             eventControlsPanel.add(npcTextField);
                 
	             eventControlsPanel.add(new JLabel("index"));
                 JFormattedTextField menuIndexTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CHANGE_DIALOG_EVENT_ARGS_NPC_MENU_INDEX_OFFSET])));
                 menuIndexTextField.setColumns(3);
                 menuIndexTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                         {
                             public void propertyChange(PropertyChangeEvent e)
                             {
                                 event.getData()[Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CHANGE_DIALOG_EVENT_ARGS_NPC_MENU_INDEX_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                             }
                         });
                 
                 eventControlsPanel.add(menuIndexTextField);
	             eventControlsPanel.add(new JLabel("global Event#"));
	             JFormattedTextField globalEventTextField = new JFormattedTextField(new Integer(ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CHANGE_DIALOG_EVENT_ARGS_NEW_GLOBAL_EVENT_NUMBER_OFFSET)));
	             globalEventTextField.setColumns(5);
	             globalEventTextField.addPropertyChangeListener("value", new PropertyChangeListener()
	                     {
	                         public void propertyChange(PropertyChangeEvent e)
	                         {
	                             ByteConversions.setIntegerInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue(), event.getData(), Event_3_46.EVENT_COMMAND_ARGS_OFFSET + Event_3_46.CHANGE_DIALOG_EVENT_ARGS_NEW_GLOBAL_EVENT_NUMBER_OFFSET);
	                         }
	                     });
	             eventControlsPanel.add(globalEventTextField);
	             return;
            default:
                throw new RuntimeException("Should never reach this.");
        }
    }

    private void addTargetControlsToPanel(JPanel panel, final IntValueHolder targetTypeValueSource, final IntValueHolder targetNumberValueSource)
    {
        Integer validTargetTypes[] = Event_3_46.getTargetTypes();
        Integer targetTypes[] = new Integer[validTargetTypes.length + 1];
        targetTypes[0] = new Integer(Integer.MIN_VALUE);
        for (int index = 0; index < validTargetTypes.length; ++index)
        {
            targetTypes[1 + index] = validTargetTypes[index];
        }

        final JComboBox targetTypeList = new JComboBox(targetTypes);
        targetTypeList.setSelectedItem(new Integer(targetTypeValueSource.getValue()));
        final JFormattedTextField targetTypeTextField = new JFormattedTextField(new Integer(targetTypeValueSource.getValue()));
        targetTypeTextField.setColumns(3);

        if (0 == targetTypeList.getSelectedIndex())
        {
            targetTypeTextField.setEnabled(true);
        }
        else
        {
            targetTypeTextField.setEnabled(false);
        }

		ListCellRenderer targetTypeRenderer = new AbstractStringListCellRenderer()
        {
            protected String getStringForValue(Object value)
            {
                Integer valueNumber = (Integer)value;
                if ((null == valueNumber) || (Integer.MIN_VALUE == valueNumber.intValue()))
                    return "UNKNOWN";
                return Event_3_46.getTargetTypeName(valueNumber.intValue());
            }
        };
        targetTypeList.setRenderer(targetTypeRenderer);

        targetTypeList.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        Object selectedItem = ((JComboBox)e.getSource()).getSelectedItem();
                        int value;
                        if (null == selectedItem)
                            value = Integer.MIN_VALUE;
                        else value = ((Number)selectedItem).intValue();

                        if (Integer.MIN_VALUE == value)
                        {
                            targetTypeTextField.setEnabled(true);
                            targetTypeValueSource.setValue(((Number)targetTypeTextField.getValue()).intValue());
                            return;
                        }
                        targetTypeTextField.setEnabled(false);
                        targetTypeValueSource.setValue(value);
                        targetTypeTextField.setValue(new Integer(targetTypeValueSource.getValue()));
                    }
                }
        );

        targetTypeTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        targetTypeValueSource.setValue(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                        targetTypeList.setSelectedItem(null);
                        targetTypeList.setSelectedItem(new Integer(targetTypeValueSource.getValue()));
                    }
                });

        JFormattedTextField targetNumberTextField = new JFormattedTextField(new Integer(targetNumberValueSource.getValue()));
        targetNumberTextField.setColumns(5);
        targetNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        targetNumberValueSource.setValue(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });

        panel.add(targetTypeList);
        panel.add(targetTypeTextField);
        panel.add(targetNumberTextField);
    }
}
