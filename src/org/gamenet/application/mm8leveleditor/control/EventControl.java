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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.gamenet.application.mm8leveleditor.data.CoordinateSet;
import org.gamenet.application.mm8leveleditor.data.Event;
import org.gamenet.application.mm8leveleditor.data.EventFormat;
import org.gamenet.application.mm8leveleditor.data.EventFormat.IndirectValue;
import org.gamenet.swing.controls.ByteDataTableControl;
import org.gamenet.swing.controls.IntTextField;
import org.gamenet.swing.controls.IntValueHolder;
import org.gamenet.swing.controls.ObjectValueHolder;
import org.gamenet.swing.controls.StringComboBox;
import org.gamenet.swing.controls.StringTextField;
import org.gamenet.swing.controls.StringValueHolder;
import org.gamenet.swing.controls.Vertex3DTextFieldPanel;
import org.gamenet.swing.controls.Vertex3DValueHolder;

public class EventControl extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Event event = null;
    
    public EventControl(Event anEvent)
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
        JFormattedTextField eventSequenceNumberTextField = new JFormattedTextField(new Integer(this.event.getSequenceNumber()));
        eventSequenceNumberTextField.setColumns(3);
        eventSequenceNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        event.setSequenceNumber(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        flowLayoutPanel.add(eventSequenceNumberTextField);

        flowLayoutPanel.add(new JLabel(event.getEventFormat().getCommandTypeName(event.getCommandType())));
        
        if (event.getEventFormat().isDebugging(event))
        {
            JPanel flowLayoutPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            this.add(flowLayoutPanel2);
            flowLayoutPanel2.add(new JLabel(event.getEventFormat().eventDescription(event)));
            
            JPanel flowLayoutPanelUnderstood = new JPanel(new FlowLayout(FlowLayout.LEFT));
            this.add(flowLayoutPanelUnderstood);
            addEventControls(flowLayoutPanelUnderstood);

    	    ByteDataTableControl eventBDTC = new ByteDataTableControl(event.getRawData(), event.getRawData().length, 0);
    	    this.add(eventBDTC);
    	    
    	    this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        }
        else if (event.getEventFormat().isUnderstood(event))
        {
            addEventControls(flowLayoutPanel);
        }
        else
        {
            JPanel flowLayoutPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            this.add(flowLayoutPanel2);
            flowLayoutPanel2.add(new JLabel(event.getEventFormat().eventDescription(event)));

    	    ByteDataTableControl eventBDTC = new ByteDataTableControl(event.getRawData(), event.getRawData().length, 0);
    	    this.add(eventBDTC);
    	    
    	    this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        }
        
    }
    
/************
    
    if (EventFormatMM6.TARGET_TYPE__ITEM == targetTypeValueSource.getValue())
    {
        final IndirectValue<Integer,String> itemTxtNameIndirectValue = new IndirectValue<Integer,String>() {
    		public int getDirectArgumentDataType() {
    			return EventFormat.ARGUMENT_DATA_TYPE__STRING;
    		}
			public Integer getIndirectValueForDirectValue(String directValue) {
				throw new RuntimeException("Unsupported: indirect modification of items.txt.");
			}
			public String getDirectValueForIndirectValue(Integer indirectValue) {
				if (null == ResourceServer.getInstance().getItemsTxt())
				{
					return "WARNING: **No items.txt resource found**";
				}
				try {
					return ResourceServer.getInstance().getItemsTxt().getItemNameAtIndex(indirectValue.intValue());
				} catch (IndexOutOfBoundsException e) {
					return "WARNING: **No item value found at index " + indirectValue + " in items.txt resource file**";
				}
				}
		};

        StringTextField indirectTargetNumberTextField = new StringTextField(new StringValueHolder()
        {
            public int getMaxLength() {
                return itemTxtNameIndirectValue.getDirectValueForIndirectValue(targetTypeValueSource.getValue()).length(); 
            }
            public String getValue() { return itemTxtNameIndirectValue.getDirectValueForIndirectValue(targetTypeValueSource.getValue()); }
            public void setValue(String value) {
                throw new RuntimeException("Unsupported modification -- need to create reference in remote lookup table instead");
            	// currentEvent.setArgumentAtIndex(argumentIndex, ((IndirectValue<Integer,String>)indirectValue).getIndirectValueForDirectValue(value));
        	}
        });
        indirectTargetNumberTextField.setEnabled(false);

        panel.add(indirectTargetNumberTextField);
    }
    
**********/
    
    private void createIndirectControl(JPanel eventControlsPanel,
            final Event currentEvent,
            final String prefixLabel,
            final String suffixLabel,
            final int dataFieldSize,
            final int argumentIndex,
            final IndirectValue<Integer,String> indirectValue)
    {
        JComponent inputControl;
        int directArgumentDataType = indirectValue.getDirectArgumentDataType();
        switch (directArgumentDataType)
        {
            case EventFormat.ARGUMENT_DATA_TYPE__STRING:
                inputControl = new StringTextField(new StringValueHolder()
                {
                    public int getMaxLength() {
                		int argument = ((Number)currentEvent.getArgumentAtIndex(argumentIndex)).intValue();
                        String directValueForIndirectValue = indirectValue.getDirectValueForIndirectValue(argument);
						return directValueForIndirectValue.length(); 
                    }
                    public String getValue() { return ((IndirectValue<Integer,String>)indirectValue).getDirectValueForIndirectValue(((Number)currentEvent.getArgumentAtIndex(argumentIndex)).intValue()); }
                    public void setValue(String value) {
                        throw new RuntimeException("Unsupported modification -- need to create reference in remote lookup table instead");
                    	// currentEvent.setArgumentAtIndex(argumentIndex, ((IndirectValue<Integer,String>)indirectValue).getIndirectValueForDirectValue(value));
                	}
                });
                inputControl.setEnabled(false);
            	break;
            default:
                throw new RuntimeException("Unsupported indirectArgumentDataType: " + directArgumentDataType);
        }

        eventControlsPanel.add(inputControl);
    }

    private void createControl(JPanel eventControlsPanel,
            final Event currentEvent,
            final String prefixLabel,
            final String suffixLabel,
            final int dataFieldSize,
            final int argumentIndex)
    {
        if (null != prefixLabel)
        {
            eventControlsPanel.add(new JLabel(prefixLabel));
        }
        
        JComponent inputControl;
        int argumentDataType = currentEvent.getArgumentDataTypeAtIndex(argumentIndex);
        switch (argumentDataType)
        {
            case EventFormat.ARGUMENT_DATA_TYPE__UNSIGNED_BYTE:
                inputControl = new IntTextField(3, new IntValueHolder() {
                    public int getValue() { return ((Number)currentEvent.getArgumentAtIndex(argumentIndex)).intValue(); }
                    public void setValue(int value) { currentEvent.setArgumentAtIndex(argumentIndex, new Integer(value)); }
                });
                break;
            case EventFormat.ARGUMENT_DATA_TYPE__UNSIGNED_SHORT:
                inputControl = new IntTextField(5, new IntValueHolder() {
                    public int getValue() { return ((Number)currentEvent.getArgumentAtIndex(argumentIndex)).intValue(); }
                    public void setValue(int value) { currentEvent.setArgumentAtIndex(argumentIndex, new Integer(value)); }
                });
                break;
            case EventFormat.ARGUMENT_DATA_TYPE__SIGNED_INT:
                inputControl = new IntTextField(5, new IntValueHolder() {
                    public int getValue() { return ((Number)currentEvent.getArgumentAtIndex(argumentIndex)).intValue(); }
                    public void setValue(int value) { currentEvent.setArgumentAtIndex(argumentIndex, new Integer(value)); }
                });
                break;
            case EventFormat.ARGUMENT_DATA_TYPE__STRING:
                inputControl = new StringTextField(new StringValueHolder()
                {
                    public int getMaxLength() {
                        return currentEvent.getEventFormat().getArgumentTypeDataSize(
                                currentEvent.getEventFormat().getArgumentTypeAtIndexForCommandType(
                                        argumentIndex, currentEvent.getCommandType()))
                                - 1; 
                    }
                    public String getValue() { return (String)currentEvent.getArgumentAtIndex(argumentIndex); }
                    public void setValue(String value) { currentEvent.setArgumentAtIndex(argumentIndex, value); }
                });
            	break;
            case EventFormat.ARGUMENT_DATA_TYPE__COORDINATE_SET:
                inputControl = new Vertex3DTextFieldPanel(new Vertex3DValueHolder()
                {
                    public int getX() {
                        CoordinateSet coordinateSet = (CoordinateSet)currentEvent.getArgumentAtIndex(argumentIndex);
                        return coordinateSet.getX(); }
                    public void setX(int x) { 
                        CoordinateSet coordinateSet = (CoordinateSet)currentEvent.getArgumentAtIndex(argumentIndex);
                        coordinateSet.setX(x); }
                    public int getY() { 
                        CoordinateSet coordinateSet = (CoordinateSet)currentEvent.getArgumentAtIndex(argumentIndex);
                        return coordinateSet.getY(); }
                    public void setY(int y) { 
                        CoordinateSet coordinateSet = (CoordinateSet)currentEvent.getArgumentAtIndex(argumentIndex);
                        coordinateSet.setY(y); }
                    public int getZ() { 
                        CoordinateSet coordinateSet = (CoordinateSet)currentEvent.getArgumentAtIndex(argumentIndex);
                        return coordinateSet.getZ(); }
                    public void setZ(int z) { 
                        CoordinateSet coordinateSet = (CoordinateSet)currentEvent.getArgumentAtIndex(argumentIndex);
                        coordinateSet.setZ(z); }
                });
            	break;
            case EventFormat.ARGUMENT_DATA_TYPE__CHOICE_PULLDOWN:
                Map argumentTypeValuesMap = currentEvent.getEventFormat().getArgumentTypeChoicesArray(
                        currentEvent.getEventFormat().getArgumentTypeAtIndexForCommandType(
                                argumentIndex, currentEvent.getCommandType()));
            	
                inputControl = new StringComboBox(argumentTypeValuesMap, new ObjectValueHolder() {
                    public Object getValue() { return currentEvent.getArgumentAtIndex(argumentIndex); }
                    public void setValue(Object value) { currentEvent.setArgumentAtIndex(argumentIndex, value); }
                });
                break;
            case EventFormat.ARGUMENT_DATA_TYPE__PLACEHOLDER:
                inputControl = new JLabel(".");
                break;
            default:
                throw new RuntimeException("Unsupported argumentDataType: " + argumentDataType);
        }

        eventControlsPanel.add(inputControl);

        if (null != suffixLabel)
        {
            eventControlsPanel.add(new JLabel(suffixLabel));
        }
        
    }


    private void addEventControls(JPanel eventControlsPanel)
    {
        final Event currentEvent = this.event;
        int[] argumentTypeArray = currentEvent.getEventFormat().getArgumentTypeArrayForCommandType(currentEvent.getCommandType());
        String[] prefixLabelArray = currentEvent.getEventFormat().getArgumentTypePrefixLabelArrayForCommandType(currentEvent.getCommandType());
        String[] suffixLabelArray = currentEvent.getEventFormat().getArgumentTypeSuffixLabelArrayForCommandType(currentEvent.getCommandType());
        // IMPLEMENT: sort arguments by ordering for display
        int[] sortOrderingArray = currentEvent.getEventFormat().getArgumentTypeSortOrderingArrayForCommandType(currentEvent.getCommandType());
        
        int targetTypeHowManyAgo = 0;
        for (int argumentTypeIndex = 0; argumentTypeIndex < argumentTypeArray.length; argumentTypeIndex++)
        {
            String label = null;
            final int finalArgumentIndex = argumentTypeIndex;
            final int argumentType = argumentTypeArray[argumentTypeIndex];
            boolean isIndirect = currentEvent.getEventFormat().getArgumentTypeIsIndirectFor(currentEvent, argumentTypeIndex);
            final int dataFieldSize = currentEvent.getEventFormat().getArgumentTypeDataSize(argumentType);
            
            targetTypeHowManyAgo--;
            
            boolean needsControlCreated = true;
            
            switch (argumentType)
            {
                case EventFormat.ARGUMENT_TYPE__ZERO_BYTE_PLACEHOLDER:
                    // No need to create a control or display anything for this type
                    needsControlCreated = false;
                    break;
//                case EventFormat.ARGUMENT_TYPE__TARGET_TYPE:
//                	needsControlCreated = false;
//             		addTargetControlsToPanel(
//             	        eventControlsPanel, 
//             	        new IntValueHolder() {
//             	            // IMPLEMENT: ARGUMENT_TYPE__TARGET_TYPE works for mm8 but should be a ByteValueHolder for mm6/7
//	                        public int getValue() { return ((Number)currentEvent.getArgumentAtIndex(finalArgumentIndex)).intValue(); }
//	                        public void setValue(int value) { currentEvent.setArgumentAtIndex(finalArgumentIndex, new Integer(value)); }
//	                    },
//             	        new IntValueHolder() {
//	                        public int getValue() { return ((Number)currentEvent.getArgumentAtIndex(finalArgumentIndex+1)).intValue(); }
//	                        public void setValue(int value) { currentEvent.setArgumentAtIndex(finalArgumentIndex+1, new Integer(value)); }
//	                    });
//                		targetTypeHowManyAgo = 2;
//                    break;
                // IMPLEMENT:  replace ARGUMENT_TYPE__FACE_IMAGE_NUMBER with a pulldown combobox
                case EventFormat.ARGUMENT_TYPE__FACE_IMAGE_NUMBER:  label = "FACE_IMAGE_NUMBER"; break;
                case EventFormat.ARGUMENT_TYPE__UNKNOWN_BYTE:  label = "UNKNOWN_BYTE"; break;
                case EventFormat.ARGUMENT_TYPE__UNKNOWN_SHORT:  label = "UNKNOWN_SHORT"; break;
                case EventFormat.ARGUMENT_TYPE__UNKNOWN_INTEGER:  label = "UNKNOWN_INTEGER"; break;
                case EventFormat.ARGUMENT_TYPE__UNKNOWN_ZERO_BYTE:  label = "UNKNOWN_ZERO_BYTE"; break;
                case EventFormat.ARGUMENT_TYPE__UNKNOWN_ZERO_SHORT:  label = "UNKNOWN_ZERO_SHORT"; break;
                case EventFormat.ARGUMENT_TYPE__UNKNOWN_ZERO_INTEGER:  label = "UNKNOWN_ZERO_INTEGER"; break;
                case EventFormat.ARGUMENT_TYPE__SEQUENCE:  label = "SEQUENCE"; break;
                case EventFormat.ARGUMENT_TYPE__2DEVENT_NUMBER:  label = "2dEvent#"; break;
                case EventFormat.ARGUMENT_TYPE__LOCAL_EVENT_STRING_NUMBER:  label = "LOCAL_EVENT_STRING_NUMBER"; break;
                case EventFormat.ARGUMENT_TYPE__DIALOG_NUMBER:  label = "DIALOG_NUMBER"; break;
                case EventFormat.ARGUMENT_TYPE__MINI_ICON_NUMBER:  label = "MINI_ICON_NUMBER"; break;
                case EventFormat.ARGUMENT_TYPE__CHEST_NUMBER:  label = "CHEST_NUMBER"; break;
                case EventFormat.ARGUMENT_TYPE__PARTY_MEMBER:  label = "PARTY_MEMBER"; break;
                case EventFormat.ARGUMENT_TYPE__3DOBJECT_NUMBER:  label = "3DOBJECT_NUMBER"; break;
                case EventFormat.ARGUMENT_TYPE__FACET_NUMBER:  label = "FACET_NUMBER"; break;
                case EventFormat.ARGUMENT_TYPE__SPRITE_NUMBER:  label = "SPRITE_NUMBER"; break;
                case EventFormat.ARGUMENT_TYPE__BOOLEAN:  label = "BOOLEAN"; break;
                case EventFormat.ARGUMENT_TYPE__SPECIES_TYPE:  label = "SPECIES_TYPE"; break;
                case EventFormat.ARGUMENT_TYPE__SUBSPECIES_TYPE:  label = "SUBSPECIES_TYPE"; break;
                case EventFormat.ARGUMENT_TYPE__MONSTER_CREATION_COUNT:  label = "MONSTER_CREATION_COUNT"; break;
                case EventFormat.ARGUMENT_TYPE__SPELL_NUMBER:  label = "SPELL_NUMBER"; break;
                case EventFormat.ARGUMENT_TYPE__SPELL_SKILL_EXPERTISE:  label = "SPELL_SKILL_EXPERTISE"; break;
                case EventFormat.ARGUMENT_TYPE__SPELL_SKILL_LEVEL:  label = "SPELL_SKILL_LEVEL"; break;
                case EventFormat.ARGUMENT_TYPE__NPCTEXT_NUMBER:  label = "NPCTEXT_NUMBER"; break;
                case EventFormat.ARGUMENT_TYPE__NPCDATA_NUMBER:  label = "NPCDATA_NUMBER"; break;
                case EventFormat.ARGUMENT_TYPE__NPC_MENU_INDEX:  label = "NPC_MENU_INDEX"; break;
                case EventFormat.ARGUMENT_TYPE__GLOBAL_EVENT_NUMBER:  label = "GLOBAL_EVENT_NUMBER"; break;
                case EventFormat.ARGUMENT_TYPE__COORDINATE_SET:  label = "COORDINATE"; break;
                case EventFormat.ARGUMENT_TYPE__FILENAME_12: label = "file"; break;
                case EventFormat.ARGUMENT_TYPE__FILENAME_13: label = "file"; break;
                case EventFormat.ARGUMENT_TYPE__TARGET_TYPE:  label = "TARGET_TYPE"; break;
                case EventFormat.ARGUMENT_TYPE__TARGET_NUMBER:  label = "TARGET_NUMBER"; break;
                case EventFormat.ARGUMENT_TYPE__FACET_ATTRIBUTE_TYPE: label = "FACET_ATTRIBUTE_TYPE"; break;
                case EventFormat.ARGUMENT_TYPE__SOUND_NUMBER: label = "SOUND_NUMBER"; break;
                
                case EventFormat.ARGUMENT_TYPE__UNKNOWN:
                default:
                    throw new RuntimeException("Unsupported argument type: " + argumentType);
            }
            
            if (needsControlCreated)
            {
                if (null == label)
                {
                    throw new RuntimeException("null label for argument type: " + argumentType);
                }
                
                createControl(eventControlsPanel, currentEvent, prefixLabelArray[finalArgumentIndex], suffixLabelArray[finalArgumentIndex], dataFieldSize, finalArgumentIndex);

                if (isIndirect)
                {
                	IndirectValue indirectValue = currentEvent.getEventFormat().getArgumentTypeIndirectValueFor(currentEvent, argumentTypeIndex);
                    createIndirectControl(eventControlsPanel, currentEvent, prefixLabelArray[finalArgumentIndex], suffixLabelArray[finalArgumentIndex], dataFieldSize, finalArgumentIndex, indirectValue);
                }
            }

//            case EventFormatMM6.EVENT_COMMAND__TELEPORT:
//            int dialogValue = ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET]);
//            int iconValue = ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_MINIICON_OFFSET]);
//            
//            JCheckBox immediateCheckBox = new JCheckBox("Immediately");
//            eventControlsPanel.add(immediateCheckBox);
//            
//            final JLabel dialogLabel = new JLabel("or via Dialog#");
//            eventControlsPanel.add(dialogLabel);
//            final JFormattedTextField dialogTextField = new JFormattedTextField(new Integer(dialogValue));
//            dialogTextField.setColumns(3);
//            dialogTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                    {
//                        public void propertyChange(PropertyChangeEvent e)
//                        {
//                            event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//                        }
//                    });
//            eventControlsPanel.add(dialogTextField);
//
//            final JLabel iconLabel = new JLabel("icon#");
//            eventControlsPanel.add(iconLabel);
//            final JComboBox miniIconList = new JComboBox(EventFormatMM6.getMiniIconNames());
//            miniIconList.setSelectedIndex(iconValue);
//            miniIconList.addActionListener(new ActionListener()
//                    {
//                        public void actionPerformed(ActionEvent e)
//                        {
//                            event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(((JComboBox)e.getSource()).getSelectedIndex());
//                        }
//                    }
//            );
//            eventControlsPanel.add(miniIconList);
//
//            boolean immediately = ((0 == dialogValue) && (0 == iconValue));
//            if (immediately)
//            {
//                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(0);
//                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(0);
//                dialogLabel.setEnabled(false);
//                dialogTextField.setEnabled(false);
//                iconLabel.setEnabled(false);
//                miniIconList.setEnabled(false);
//            }
//            else
//            {
//                dialogLabel.setEnabled(true);
//                dialogTextField.setEnabled(true);
//                iconLabel.setEnabled(true);
//                miniIconList.setEnabled(true);
//                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)dialogTextField.getValue()).intValue());
//                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(miniIconList.getSelectedIndex());
//            }
//
//            immediateCheckBox.setSelected(immediately);
//            immediateCheckBox.addItemListener(new ItemListener()
//                    {
//                        public void itemStateChanged(ItemEvent e)
//                        {
//                            if (e.getStateChange() == ItemEvent.DESELECTED)
//                            {
//                                dialogLabel.setEnabled(true);
//                                dialogTextField.setEnabled(true);
//                                iconLabel.setEnabled(true);
//                                miniIconList.setEnabled(true);
//                                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)dialogTextField.getValue()).intValue());
//                                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(miniIconList.getSelectedIndex());
//                            }
//                            else if (e.getStateChange() == ItemEvent.SELECTED)
//                            {
//                                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(0);
//                                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(0);
//                                dialogLabel.setEnabled(false);
//                                dialogTextField.setEnabled(false);
//                                iconLabel.setEnabled(false);
//                                miniIconList.setEnabled(false);
//                            }
//                        }
//                    }
//            );
        }
    }
    
    
    
    
    
//    private void addEventControls(JPanel eventControlsPanel)
//    {
//        JFormattedTextField targetTypeTextField = null;
//        JFormattedTextField targetNumberTextField = null;
//        JFormattedTextField affectedTextField = null;
//        JFormattedTextField stringIDTextField = null;
//        
//        switch(this.event.getEventCommandNumber())
//        {
//            case EventFormatMM6.EVENT_COMMAND__STOP_PROCESSING:
//                return;
//            case EventFormatMM6.EVENT_COMMAND__IDENTITY:
//                eventControlsPanel.add(new JLabel("2dEvent#"));
//                JFormattedTextField identityTextField = new JFormattedTextField(new Integer(ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.IDENTITY_ARGS_2DEVENT_ID_OFFSET)));
//                identityTextField.setColumns(5);
//                identityTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                        {
//                            public void propertyChange(PropertyChangeEvent e)
//                            {
//                                ByteConversions.setIntegerInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue(), event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.IDENTITY_ARGS_2DEVENT_ID_OFFSET);
//                            }
//                        });
//                eventControlsPanel.add(identityTextField);
//                return;
//            case EventFormatMM6.EVENT_COMMAND__MOUSEOVER:
//                eventControlsPanel.add(new JLabel("2dEvent#"));
//                JFormattedTextField eventStrTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.MOUSEOVER_ARGS_EVENT_STR_ID_OFFSET])));
//                eventStrTextField.setColumns(3);
//                eventStrTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                        {
//                            public void propertyChange(PropertyChangeEvent e)
//                            {
//                                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.MOUSEOVER_ARGS_EVENT_STR_ID_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//                            }
//                        });
//                eventControlsPanel.add(eventStrTextField);
//                return;
//            case EventFormatMM6.EVENT_COMMAND__TELEPORT:
//                eventControlsPanel.add(new JLabel("destination:"));
//                JFormattedTextField levelNameTextField = new JFormattedTextField(event.getCommandTeleportArgumentLevelName());
//                levelNameTextField.setColumns(10);
//                levelNameTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                        {
//                            public void propertyChange(PropertyChangeEvent e)
//                            {
//                                event.setCommandTeleportArgumentLevelName((String)((JFormattedTextField)e.getSource()).getValue());
//                            }
//                        });
//                eventControlsPanel.add(levelNameTextField);
//                
//                // coordinates
//                eventControlsPanel.add(new JLabel("coordinates:"));
//                eventControlsPanel.add(new Vertex3DTextFieldPanel(new Vertex3DValueHolder()
//                        {
//                            public int getX()
//                            {
//                                return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_X_COORD_OFFSET);
//                            }
//
//                            public void setX(int x)
//                            {
//                                ByteConversions.setIntegerInByteArrayAtPosition(x, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_X_COORD_OFFSET);
//                            }
//
//                            public int getY()
//                            {
//                                return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_Y_COORD_OFFSET);
//                            }
//
//                            public void setY(int y)
//                            {
//                                ByteConversions.setIntegerInByteArrayAtPosition(y, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_Y_COORD_OFFSET);
//                            }
//
//                            public int getZ()
//                            {
//                                return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_Z_COORD_OFFSET);
//                            }
//
//                            public void setZ(int z)
//                            {
//                                ByteConversions.setIntegerInByteArrayAtPosition(z, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_Z_COORD_OFFSET);
//                            }
//                        }));
//                
//                eventControlsPanel.add(new JLabel("facing:"));
//                eventControlsPanel.add(new Vertex2DTextFieldPanel(new Vertex2DValueHolder()
//                        {
//                            public int getX()
//                            {
//                                return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_HORIZONTAL_ORIENTATION_OFFSET);
//                            }
//
//                            public void setX(int x)
//                            {
//                                ByteConversions.setIntegerInByteArrayAtPosition(x, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_HORIZONTAL_ORIENTATION_OFFSET);
//                            }
//
//                            public int getY()
//                            {
//                                return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_VERTICAL_ORIENTATION_OFFSET);
//                            }
//
//                            public void setY(int y)
//                            {
//                                ByteConversions.setIntegerInByteArrayAtPosition(y, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_VERTICAL_ORIENTATION_OFFSET);
//                            }
//                        }));
//
//                int dialogValue = ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET]);
//                int iconValue = ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_MINIICON_OFFSET]);
//                
//                JCheckBox immediateCheckBox = new JCheckBox("Immediately");
//                eventControlsPanel.add(immediateCheckBox);
//                
//                final JLabel dialogLabel = new JLabel("or via Dialog#");
//                eventControlsPanel.add(dialogLabel);
//                final JFormattedTextField dialogTextField = new JFormattedTextField(new Integer(dialogValue));
//                dialogTextField.setColumns(3);
//                dialogTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                        {
//                            public void propertyChange(PropertyChangeEvent e)
//                            {
//                                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//                            }
//                        });
//                eventControlsPanel.add(dialogTextField);
//
//                final JLabel iconLabel = new JLabel("icon#");
//                eventControlsPanel.add(iconLabel);
//                final JComboBox miniIconList = new JComboBox(EventFormatMM6.getMiniIconNames());
//                miniIconList.setSelectedIndex(iconValue);
//                miniIconList.addActionListener(new ActionListener()
//                        {
//                            public void actionPerformed(ActionEvent e)
//                            {
//                                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(((JComboBox)e.getSource()).getSelectedIndex());
//                            }
//                        }
//                );
//                eventControlsPanel.add(miniIconList);
//
//                boolean immediately = ((0 == dialogValue) && (0 == iconValue));
//                if (immediately)
//                {
//                    event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(0);
//                    event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(0);
//                    dialogLabel.setEnabled(false);
//                    dialogTextField.setEnabled(false);
//                    iconLabel.setEnabled(false);
//                    miniIconList.setEnabled(false);
//                }
//                else
//                {
//                    dialogLabel.setEnabled(true);
//                    dialogTextField.setEnabled(true);
//                    iconLabel.setEnabled(true);
//                    miniIconList.setEnabled(true);
//                    event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)dialogTextField.getValue()).intValue());
//                    event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(miniIconList.getSelectedIndex());
//                }
//
//                immediateCheckBox.setSelected(immediately);
//                immediateCheckBox.addItemListener(new ItemListener()
//                        {
//                            public void itemStateChanged(ItemEvent e)
//                            {
//                                if (e.getStateChange() == ItemEvent.DESELECTED)
//                                {
//                                    dialogLabel.setEnabled(true);
//                                    dialogTextField.setEnabled(true);
//                                    iconLabel.setEnabled(true);
//                                    miniIconList.setEnabled(true);
//                                    event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)dialogTextField.getValue()).intValue());
//                                    event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(miniIconList.getSelectedIndex());
//                                }
//                                else if (e.getStateChange() == ItemEvent.SELECTED)
//                                {
//                                    event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_DESTINATION_DIALOG_NUMBER_OFFSET] = ByteConversions.convertIntToByte(0);
//                                    event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.TELEPORT_ARGS_MINIICON_OFFSET] = ByteConversions.convertIntToByte(0);
//                                    dialogLabel.setEnabled(false);
//                                    dialogTextField.setEnabled(false);
//                                    iconLabel.setEnabled(false);
//                                    miniIconList.setEnabled(false);
//                                }
//                            }
//                        }
//                );
//
//                return;
//            case EventFormatMM6.EVENT_COMMAND__OPEN_CHEST:
//                eventControlsPanel.add(new JLabel("#"));
//                JFormattedTextField chestIDTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.OPEN_CHEST_ARGS_CHEST_NUMBER_OFFSET])));
//                chestIDTextField.setColumns(3);
//                chestIDTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                        {
//                            public void propertyChange(PropertyChangeEvent e)
//                            {
//                                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.OPEN_CHEST_ARGS_CHEST_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//                            }
//                        });
//                eventControlsPanel.add(chestIDTextField);
//                return;
//            case EventFormatMM6.EVENT_COMMAND__SHOW_FACIAL_EXPRESSION:
//                eventControlsPanel.add(new JLabel("#"));
//                JFormattedTextField imageIDTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.SHOW_FACIAL_EXPRESSION_ARGS_IMAGE_ID_OFFSET])));
//                imageIDTextField.setColumns(3);
//                imageIDTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                        {
//                            public void propertyChange(PropertyChangeEvent e)
//                            {
//                                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.SHOW_FACIAL_EXPRESSION_ARGS_IMAGE_ID_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//                            }
//                        });
//                eventControlsPanel.add(imageIDTextField);
//                eventControlsPanel.add(new JLabel("for"));
//                // IMPLEMENT:  replace this with a pulldown combobox
//                affectedTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.SHOW_FACIAL_EXPRESSION_ARGS_AFFECTED_OFFSET])));
//                affectedTextField.setColumns(3);
//                affectedTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                        {
//                            public void propertyChange(PropertyChangeEvent e)
//                            {
//                                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.SHOW_FACIAL_EXPRESSION_ARGS_AFFECTED_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//                            }
//                        });
//                eventControlsPanel.add(affectedTextField);
//                return;
//            case EventFormatMM6.EVENT_COMMAND__CHANGE_3DOBJECT_FACE_BITMAP:
//                eventControlsPanel.add(new JLabel("object #"));
//	            JFormattedTextField d3ObjectTextField = new JFormattedTextField(new Integer(ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CHANGE_3DOBJECT_FACE_BITMAP_ARGS_3D_OBJECT_NUMBER_OFFSET)));
//	            d3ObjectTextField.setColumns(5);
//	            d3ObjectTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//	                    {
//	                        public void propertyChange(PropertyChangeEvent e)
//	                        {
//	                            ByteConversions.setIntegerInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue(), event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CHANGE_3DOBJECT_FACE_BITMAP_ARGS_3D_OBJECT_NUMBER_OFFSET);
//	                        }
//	                    });
//	            eventControlsPanel.add(d3ObjectTextField);
//                eventControlsPanel.add(new JLabel("face #"));
//	            JFormattedTextField faceTextField = new JFormattedTextField(new Integer(ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CHANGE_3DOBJECT_FACE_BITMAP_ARGS_FACE_OFFSET)));
//	            faceTextField.setColumns(5);
//	            faceTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//	                    {
//	                        public void propertyChange(PropertyChangeEvent e)
//	                        {
//	                            ByteConversions.setIntegerInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue(), event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CHANGE_3DOBJECT_FACE_BITMAP_ARGS_FACE_OFFSET);
//	                        }
//	                    });
//	            eventControlsPanel.add(faceTextField);
//                eventControlsPanel.add(new JLabel("to"));
//                JFormattedTextField newBitmapNameTextField = new JFormattedTextField(event.getCommandChangeFaceBitmapArgumentNewBitmapName());
//                newBitmapNameTextField.setColumns(10);
//                newBitmapNameTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                        {
//                            public void propertyChange(PropertyChangeEvent e)
//                            {
//                                event.setCommandChangeFaceBitmapArgumentNewBitmapName((String)((JFormattedTextField)e.getSource()).getValue());
//                            }
//                        });
//                eventControlsPanel.add(newBitmapNameTextField);
//                return;
//            case EventFormatMM6.EVENT_COMMAND__CHANGE_SPRITE:
//                eventControlsPanel.add(new JLabel("#"));
//                JFormattedTextField spriteNumberTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CHANGE_SPRITE_ARGS_SPRITE_NUMBER])));
//	            spriteNumberTextField.setColumns(3);
//	            spriteNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//	                    {
//	                        public void propertyChange(PropertyChangeEvent e)
//	                        {
//	                            event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CHANGE_SPRITE_ARGS_SPRITE_NUMBER] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//	                        }
//	                    });
//	            eventControlsPanel.add(spriteNumberTextField);
//                eventControlsPanel.add(new JLabel("to"));
//                JFormattedTextField newSpriteNameTextField = new JFormattedTextField(event.getCommandChangeSpriteArgumentNewSpriteName());
//                newSpriteNameTextField.setColumns(10);
//                newSpriteNameTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                        {
//                            public void propertyChange(PropertyChangeEvent e)
//                            {
//                                event.setCommandChangeSpriteArgumentNewSpriteName((String)((JFormattedTextField)e.getSource()).getValue());
//                            }
//                        });
//                eventControlsPanel.add(newSpriteNameTextField);
//                return;
//            case EventFormatMM6.EVENT_COMMAND__BRANCH_ON_CONDITION_TRUE:
//                eventControlsPanel.add(new JLabel("if ("));
//                addTargetControlsToPanel(eventControlsPanel, new IntValueHolder()
//                        {
//                           public int getValue()
//                           {
//                               return ByteConversions.convertByteToInt(event.getData()[
//                                   EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET
//                                   + event.getBranchOnConditionTrueArgsTargetTypeOffset()]);
//                           }
//
//                           public void setValue(int value)
//                           {
//	                            event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET
//	                                            + event.getBranchOnConditionTrueArgsTargetTypeOffset()]
//	                                            = ByteConversions.convertIntToByte(value);
//                           }
//                        },
//                        new IntValueHolder()
//                        {
//                            public int getValue()
//                            {
//                                return ByteConversions.getUnsignedShortInByteArrayAtPosition(event.getData(),
//                                        EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET
//                                        + event.getBranchOnConditionTrueArgsTargetNumberOffset());
//                            }
//
//                            public void setValue(int value)
//                            {
//								 ByteConversions.setShortInByteArrayAtPosition((short)value, event.getData(),
//								         EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET
//								         + event.getBranchOnConditionTrueArgsTargetNumberOffset());
//                            }
//                        });
//
//                eventControlsPanel.add(new JLabel(") goto seq#"));
//                JFormattedTextField targetSequenceNumberTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET
//                     + event.getBranchOnConditionTrueArgsGotoSequenceNumberOffset()])));
//                targetSequenceNumberTextField.setColumns(3);
//                targetSequenceNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                        {
//                            public void propertyChange(PropertyChangeEvent e)
//                            {
//                                event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET
//                                    + event.getBranchOnConditionTrueArgsGotoSequenceNumberOffset()]
//                                    = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//                            }
//                        });
//                eventControlsPanel.add(targetSequenceNumberTextField);
//                return;
//            case EventFormatMM6.EVENT_COMMAND__ADD_TARGET:
//                addTargetControlsToPanel(eventControlsPanel, new IntValueHolder()
//                        {
//                           public int getValue()
//                           {
//                               return ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.ADD_TARGET_ARGS_TARGET_TYPE_OFFSET]);
//                           }
//
//                           public void setValue(int value)
//                           {
//	                            event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.ADD_TARGET_ARGS_TARGET_TYPE_OFFSET] = ByteConversions.convertIntToByte(value);
//                           }
//                        },
//                        new IntValueHolder()
//                        {
//                            public int getValue()
//                            {
//                                return ByteConversions.getUnsignedShortInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.ADD_TARGET_ARGS_TARGET_NUMBER_OFFSET);
//                            }
//
//                            public void setValue(int value)
//                            {
//								 ByteConversions.setShortInByteArrayAtPosition((short)value, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.ADD_TARGET_ARGS_TARGET_NUMBER_OFFSET);
//                            }
//                        });
//                return;
//            case EventFormatMM6.EVENT_COMMAND__DELETE_TARGET:
//                addTargetControlsToPanel(eventControlsPanel, new IntValueHolder()
//                        {
//                           public int getValue()
//                           {
//                               return ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.DELETE_TARGET_ARGS_TARGET_TYPE_OFFSET]);
//                           }
//
//                           public void setValue(int value)
//                           {
//	                            event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.DELETE_TARGET_ARGS_TARGET_TYPE_OFFSET] = ByteConversions.convertIntToByte(value);
//                           }
//                        },
//                        new IntValueHolder()
//                        {
//                            public int getValue()
//                            {
//                                return ByteConversions.getUnsignedShortInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.DELETE_TARGET_ARGS_TARGET_NUMBER_OFFSET);
//                            }
//
//                            public void setValue(int value)
//                            {
//								 ByteConversions.setShortInByteArrayAtPosition((short)value, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.DELETE_TARGET_ARGS_TARGET_NUMBER_OFFSET);
//                            }
//                        });
//                return;
//             case EventFormatMM6.EVENT_COMMAND__SET_TARGET:
//                 addTargetControlsToPanel(eventControlsPanel, new IntValueHolder()
//                         {
//                            public int getValue()
//                            {
//                                return ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.SET_TARGET_ARGS_TARGET_TYPE_OFFSET]);
//                            }
//
//                            public void setValue(int value)
//                            {
//	                            event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.SET_TARGET_ARGS_TARGET_TYPE_OFFSET] = ByteConversions.convertIntToByte(value);
//                            }
//                         },
//                         new IntValueHolder()
//                         {
//                             public int getValue()
//                             {
//                                 return ByteConversions.getUnsignedShortInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.SET_TARGET_ARGS_TARGET_NUMBER_OFFSET);
//                             }
//
//                             public void setValue(int value)
//                             {
//								 ByteConversions.setShortInByteArrayAtPosition((short)value, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.SET_TARGET_ARGS_TARGET_NUMBER_OFFSET);
//                             }
//                         });
//                return;
//             case EventFormatMM6.EVENT_COMMAND__CREATE_LOCAL_MONSTER:
//                 eventControlsPanel.add(new JLabel("count"));
//                 JFormattedTextField numberToCreateTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CREATE_LOCAL_MONSTER_ARGS_COUNT_OFFSET])));
//	             numberToCreateTextField.setColumns(3);
//	             numberToCreateTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//	                     {
//	                         public void propertyChange(PropertyChangeEvent e)
//	                         {
//	                             event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CREATE_LOCAL_MONSTER_ARGS_COUNT_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//	                         }
//	                     });
//	             eventControlsPanel.add(numberToCreateTextField);
//	
//                 eventControlsPanel.add(new JLabel("species #"));
//	             JFormattedTextField speciesTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CREATE_LOCAL_MONSTER_ARGS_MONSTER_SPECIES_OFFSET])));
//	             speciesTextField.setColumns(3);
//	             speciesTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//	                     {
//	                         public void propertyChange(PropertyChangeEvent e)
//	                         {
//	                             event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CREATE_LOCAL_MONSTER_ARGS_MONSTER_SPECIES_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//	                         }
//	                     });
//	             eventControlsPanel.add(speciesTextField);
//                 eventControlsPanel.add(new JLabel("-"));
//	             JFormattedTextField subspeciesTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CREATE_LOCAL_MONSTER_ARGS_MONSTER_SUBSPECIES__OFFSET])));
//	             subspeciesTextField.setColumns(3);
//	             subspeciesTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//	                     {
//	                         public void propertyChange(PropertyChangeEvent e)
//	                         {
//	                             event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CREATE_LOCAL_MONSTER_ARGS_MONSTER_SUBSPECIES__OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//	                         }
//	                     });
//	             eventControlsPanel.add(subspeciesTextField);
//
//                 // coordinates
//                 eventControlsPanel.add(new JLabel("at"));
//                 eventControlsPanel.add(new Vertex3DTextFieldPanel(new Vertex3DValueHolder()
//                         {
//                             public int getX()
//                             {
//                                 return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CREATE_LOCAL_MONSTER_ARGS_X_OFFSET);
//                             }
//
//                             public void setX(int x)
//                             {
//                                 ByteConversions.setIntegerInByteArrayAtPosition(x, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CREATE_LOCAL_MONSTER_ARGS_X_OFFSET);
//                             }
//
//                             public int getY()
//                             {
//                                 return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CREATE_LOCAL_MONSTER_ARGS_Y_OFFSET);
//                             }
//
//                             public void setY(int y)
//                             {
//                                 ByteConversions.setIntegerInByteArrayAtPosition(y, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CREATE_LOCAL_MONSTER_ARGS_Y_OFFSET);
//                             }
//
//                             public int getZ()
//                             {
//                                 return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CREATE_LOCAL_MONSTER_ARGS_Z_OFFSET);
//                             }
//
//                             public void setZ(int z)
//                             {
//                                 ByteConversions.setIntegerInByteArrayAtPosition(z, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CREATE_LOCAL_MONSTER_ARGS_Z_OFFSET);
//                             }
//                         }));
//                 return;
//             case EventFormatMM6.EVENT_COMMAND__CAST_SPELL_FROM_LOCATION:
//                 eventControlsPanel.add(new JLabel("Spell #"));
//                 JFormattedTextField spellNumberTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CAST_SPELL_FROM_LOCATION_ARGS_SPELL_NUMBER_OFFSET])));
//	             spellNumberTextField.setColumns(3);
//	             spellNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//	                     {
//	                         public void propertyChange(PropertyChangeEvent e)
//	                         {
//	                             event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CAST_SPELL_FROM_LOCATION_ARGS_SPELL_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//	                         }
//	                     });
//	             eventControlsPanel.add(spellNumberTextField);
//                 eventControlsPanel.add(new JLabel("at ranking"));
//	             JFormattedTextField spellRankingTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CAST_SPELL_FROM_LOCATION_ARGS_RANKING_OFFSET])));
//	             spellRankingTextField.setColumns(3);
//	             spellRankingTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//	                     {
//	                         public void propertyChange(PropertyChangeEvent e)
//	                         {
//	                             event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CAST_SPELL_FROM_LOCATION_ARGS_RANKING_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//	                         }
//	                     });
//	             eventControlsPanel.add(spellRankingTextField);
//                 eventControlsPanel.add(new JLabel("level"));
//	             JFormattedTextField spellLevelTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CAST_SPELL_FROM_LOCATION_ARGS_LEVEL_OFFSET])));
//	             spellRankingTextField.setColumns(3);
//	             spellRankingTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//	                     {
//	                         public void propertyChange(PropertyChangeEvent e)
//	                         {
//	                             event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CAST_SPELL_FROM_LOCATION_ARGS_LEVEL_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//	                         }
//	                     });
//	             eventControlsPanel.add(spellLevelTextField);
//                 // coordinates
//                 eventControlsPanel.add(new JLabel("from"));
//                 eventControlsPanel.add(new Vertex3DTextFieldPanel(new Vertex3DValueHolder()
//                         {
//                             public int getX()
//                             {
//                                 return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_X_OFFSET);
//                             }
//
//                             public void setX(int x)
//                             {
//                                 ByteConversions.setIntegerInByteArrayAtPosition(x, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_X_OFFSET);
//                             }
//
//                             public int getY()
//                             {
//                                 return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Y_OFFSET);
//                             }
//
//                             public void setY(int y)
//                             {
//                                 ByteConversions.setIntegerInByteArrayAtPosition(y, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Y_OFFSET);
//                             }
//
//                             public int getZ()
//                             {
//                                 return ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Z_OFFSET);
//                             }
//
//                             public void setZ(int z)
//                             {
//                                 ByteConversions.setIntegerInByteArrayAtPosition(z, event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CAST_SPELL_FROM_LOCATION_ARGS_SOURCE_Z_OFFSET);
//                             }
//                         }));
//                 return;
//             case EventFormatMM6.EVENT_COMMAND__SHOW_LOCAL_EVENT_STRING:
//                eventControlsPanel.add(new JLabel("#"));
//				stringIDTextField = new JFormattedTextField(new Integer(ByteConversions.getUnsignedShortInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.SHOW_LOCAL_EVENT_STRING_ARGS_STR_ID_OFFSET)));
//				stringIDTextField.setColumns(5);
//				stringIDTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//				        {
//				            public void propertyChange(PropertyChangeEvent e)
//				            {
//	                            ByteConversions.setShortInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).shortValue(), event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.SHOW_LOCAL_EVENT_STRING_ARGS_STR_ID_OFFSET);
//				            }
//				        });
//				eventControlsPanel.add(stringIDTextField);
//				return;
//             case EventFormatMM6.EVENT_COMMAND__SHOW_NPCTEXT_STRING:
//                eventControlsPanel.add(new JLabel("#"));
//				stringIDTextField = new JFormattedTextField(new Integer(ByteConversions.getUnsignedShortInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.SHOW_NPCTEXT_STRING_ARGS_STR_ID_OFFSET)));
//				stringIDTextField.setColumns(5);
//				stringIDTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//				        {
//				            public void propertyChange(PropertyChangeEvent e)
//				            {
//	                            ByteConversions.setShortInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).shortValue(), event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.SHOW_NPCTEXT_STRING_ARGS_STR_ID_OFFSET);
//				            }
//				        });
//				eventControlsPanel.add(stringIDTextField);
//				return;
//             case EventFormatMM6.EVENT_COMMAND__MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER:
//                 eventControlsPanel.add(new JLabel("#"));
//	             // IMPLEMENT:  replace this with a pulldown combobox
//	             affectedTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER_ARGS_AFFECTED_OFFSET])));
//	             affectedTextField.setColumns(3);
//	             affectedTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//	                     {
//	                         public void propertyChange(PropertyChangeEvent e)
//	                         {
//	                             event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.MODIFY_NEXT_COMMAND_BY_PARTY_MEMBER_ARGS_AFFECTED_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//	                         }
//	                     });
//	             eventControlsPanel.add(affectedTextField);
//             return;
//             case EventFormatMM6.EVENT_COMMAND__GOTO:
//                 eventControlsPanel.add(new JLabel("seq#"));
//                 JFormattedTextField sequenceNumberTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.GOTO_ARGS_SEQUENCE_NUMBER_OFFSET])));
//                 sequenceNumberTextField.setColumns(3);
//                 sequenceNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                         {
//                             public void propertyChange(PropertyChangeEvent e)
//                             {
//                                 event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.GOTO_ARGS_SEQUENCE_NUMBER_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//                             }
//                         });
//                 eventControlsPanel.add(sequenceNumberTextField);
//                 return;
//             case EventFormatMM6.EVENT_COMMAND__ON_LEVEL_RELOAD_EXECUTE:
//                 return;
//             case EventFormatMM6.EVENT_COMMAND__CHANGE_DIALOG_EVENT:
//	             eventControlsPanel.add(new JLabel("NPC#"));
//	             JFormattedTextField npcTextField = new JFormattedTextField(new Integer(ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CHANGE_DIALOG_EVENT_ARGS_NPCDATA_OFFSET)));
//	             npcTextField.setColumns(5);
//	             npcTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//	                     {
//	                         public void propertyChange(PropertyChangeEvent e)
//	                         {
//	                             ByteConversions.setIntegerInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue(), event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CHANGE_DIALOG_EVENT_ARGS_NPCDATA_OFFSET);
//	                         }
//	                     });
//	             eventControlsPanel.add(npcTextField);
//                 
//	             eventControlsPanel.add(new JLabel("index"));
//                 JFormattedTextField menuIndexTextField = new JFormattedTextField(new Integer(ByteConversions.convertByteToInt(event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CHANGE_DIALOG_EVENT_ARGS_NPC_MENU_INDEX_OFFSET])));
//                 menuIndexTextField.setColumns(3);
//                 menuIndexTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                         {
//                             public void propertyChange(PropertyChangeEvent e)
//                             {
//                                 event.getData()[EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CHANGE_DIALOG_EVENT_ARGS_NPC_MENU_INDEX_OFFSET] = ByteConversions.convertIntToByte(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//                             }
//                         });
//                 
//                 eventControlsPanel.add(menuIndexTextField);
//	             eventControlsPanel.add(new JLabel("global Event#"));
//	             JFormattedTextField globalEventTextField = new JFormattedTextField(new Integer(ByteConversions.getIntegerInByteArrayAtPosition(event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CHANGE_DIALOG_EVENT_ARGS_NEW_GLOBAL_EVENT_NUMBER_OFFSET)));
//	             globalEventTextField.setColumns(5);
//	             globalEventTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//	                     {
//	                         public void propertyChange(PropertyChangeEvent e)
//	                         {
//	                             ByteConversions.setIntegerInByteArrayAtPosition(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue(), event.getData(), EventFormatMM6.EVENT_COMMAND_ARGS_OFFSET + EventFormatMM6.CHANGE_DIALOG_EVENT_ARGS_NEW_GLOBAL_EVENT_NUMBER_OFFSET);
//	                         }
//	                     });
//	             eventControlsPanel.add(globalEventTextField);
//	             return;
//            default:
//                throw new RuntimeException("Should never reach this.");
//        }
//    }
//
//    private void addTargetControlsToPanel(JPanel panel, final IntValueHolder targetTypeValueSource, final IntValueHolder targetNumberValueSource)
//    {
//        Integer validTargetTypes[] = this.event.getEventFormat().getTargetTypes();
//        Integer targetTypes[] = new Integer[validTargetTypes.length + 1];
//        targetTypes[0] = new Integer(Integer.MIN_VALUE);
//        for (int index = 0; index < validTargetTypes.length; ++index)
//        {
//            targetTypes[1 + index] = validTargetTypes[index];
//        }
//
//        final JComboBox targetTypeList = new JComboBox(targetTypes);
//        targetTypeList.setSelectedItem(new Integer(targetTypeValueSource.getValue()));
//        final JFormattedTextField targetTypeTextField = new JFormattedTextField(new Integer(targetTypeValueSource.getValue()));
//        targetTypeTextField.setColumns(3);
//
//        if (0 == targetTypeList.getSelectedIndex())
//        {
//            targetTypeTextField.setEnabled(true);
//        }
//        else
//        {
//            targetTypeTextField.setEnabled(false);
//        }
//
//		ListCellRenderer targetTypeRenderer = new AbstractStringListCellRenderer()
//        {
//            protected String getStringForValue(Object value)
//            {
//                Integer valueNumber = (Integer)value;
//                if ((null == valueNumber) || (Integer.MIN_VALUE == valueNumber.intValue()))
//                    return "UNKNOWN";
//                return event.getEventFormat().getTargetTypeName(valueNumber.intValue());
//            }
//        };
//        targetTypeList.setRenderer(targetTypeRenderer);
//
//        targetTypeList.addActionListener(new ActionListener()
//                {
//                    public void actionPerformed(ActionEvent e)
//                    {
//                        Object selectedItem = ((JComboBox)e.getSource()).getSelectedItem();
//                        int value;
//                        if (null == selectedItem)
//                            value = Integer.MIN_VALUE;
//                        else value = ((Number)selectedItem).intValue();
//
//                        if (Integer.MIN_VALUE == value)
//                        {
//                            targetTypeTextField.setEnabled(true);
//                            targetTypeValueSource.setValue(((Number)targetTypeTextField.getValue()).intValue());
//                            return;
//                        }
//                        targetTypeTextField.setEnabled(false);
//                        targetTypeValueSource.setValue(value);
//                        targetTypeTextField.setValue(new Integer(targetTypeValueSource.getValue()));
//                    }
//                }
//        );
//
//        targetTypeTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                {
//                    public void propertyChange(PropertyChangeEvent e)
//                    {
//                        targetTypeValueSource.setValue(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//                        targetTypeList.setSelectedItem(null);
//                        targetTypeList.setSelectedItem(new Integer(targetTypeValueSource.getValue()));
//                    }
//                });
//
//        JFormattedTextField targetNumberTextField = new JFormattedTextField(new Integer(targetNumberValueSource.getValue()));
//        targetNumberTextField.setColumns(5);
//        targetNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
//                {
//                    public void propertyChange(PropertyChangeEvent e)
//                    {
//                        targetNumberValueSource.setValue(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
//                    }
//                });
//
//        panel.add(targetTypeList);
//        panel.add(targetTypeTextField);
//        panel.add(targetNumberTextField);
//    }
}
