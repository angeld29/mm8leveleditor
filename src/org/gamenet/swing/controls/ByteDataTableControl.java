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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import org.gamenet.util.ByteConversions;

public class ByteDataTableControl extends JPanel
{
	private final ByteDataTableModel byteDataTableModel;

    public ByteDataTableControl(byte[] byteData, int byteDataColumns, long baseOffsetForDisplay)
    {
        this(byteData, byteDataColumns, baseOffsetForDisplay, 0, byteData.length);
    }
    
    public ByteDataTableControl(byte[] byteData, int byteDataColumns, long baseOffsetForDisplay, int inclusiveStartOffset, int exclusiveEndOffset)
    {
        super(new BorderLayout());

        this.byteDataTableModel = new ByteDataTableModel(byteDataColumns, baseOffsetForDisplay, byteData, inclusiveStartOffset, exclusiveEndOffset);
        
        if ((exclusiveEndOffset - inclusiveStartOffset) == 0)
        {
    		this.add(new JLabel("No data."), BorderLayout.CENTER);
    		return;
        }
        
		final ResizingJTable dataTable = new ResizingJTable(byteDataTableModel)
		{
		    public String getToolTipText(MouseEvent e) {
		        String tip = null;
		        java.awt.Point p = e.getPoint();
		        int rowIndex = rowAtPoint(p);
		        int colIndex = columnAtPoint(p);
		        int realColumnIndex = convertColumnIndexToModel(colIndex);
		        
		        if (0 == realColumnIndex)  return null;

                return byteDataTableModel.getOffsetDisplayString(rowIndex, realColumnIndex);
		    }		    
		};

		JComboBox displayModeComboBox = new JComboBox(byteDataTableModel.displayModeNameArray);
		displayModeComboBox.setSelectedIndex(this.byteDataTableModel.getDisplayMode());
		displayModeComboBox.addActionListener(new ActionListener()
			{
                public void actionPerformed(ActionEvent anActionEvent)
                {
					JComboBox cb = (JComboBox)anActionEvent.getSource();
					byteDataTableModel.setDisplayMode(cb.getSelectedIndex());
					dataTable.recalcColumnWidths();
                }
			}
		);
		
		JLabel columnCountTextFieldLabel = new JLabel("Bytes per line: ");
		final JTextField columnCountTextField = new JTextField(4);
		columnCountTextField.setText(String.valueOf(byteDataColumns));

		JButton columnCountTextFieldButton = new JButton("Change bytes per line");
		columnCountTextFieldButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent anActionEvent)
				{
                    try
                    {
						int newColumnCount = Integer.parseInt(columnCountTextField.getText());
						byteDataTableModel.setColumnCount(newColumnCount + 1);
						dataTable.recalcColumnWidths();
                    }
                    catch (NumberFormatException e)
                    {
                        e.printStackTrace();
                    }
				}
			}
		);

		JLabel paddingTextFieldLabel = new JLabel("bytes of padding: ");
		final JTextField paddingTextField = new JTextField(4);
		paddingTextField.setText(String.valueOf(0));

		JButton paddingTextFieldButton = new JButton("Change initial padding bytes");
		paddingTextFieldButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent anActionEvent)
				{
                    try
                    {
						int newPadding = Integer.parseInt(paddingTextField.getText());
						byteDataTableModel.setPadding(newPadding);
                    }
                    catch (NumberFormatException e)
                    {
                        e.printStackTrace();
                    }
				}
			}
		);

		final JButton copyToClipboardTextFieldButton = new JButton("Copy to clipboard");
	    TransferHandler handler = new TransferHandler("text")
	    {
	        protected Transferable createTransferable(JComponent c) {
	            return new StringSelection(byteDataTableModel.exportDataAsCSV());
	        }
	    };
	    copyToClipboardTextFieldButton.setTransferHandler(handler);
		copyToClipboardTextFieldButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent anActionEvent)
				{
				    Action copyAction = TransferHandler.getCopyAction();
				    copyAction.actionPerformed(new ActionEvent(copyToClipboardTextFieldButton,
		                                              ActionEvent.ACTION_PERFORMED,
		                                              null));
				}
			}
		);
		
		JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		controlPanel.add(displayModeComboBox);
		controlPanel.add(copyToClipboardTextFieldButton);
		controlPanel.add(columnCountTextFieldLabel);
		controlPanel.add(columnCountTextField);
		controlPanel.add(columnCountTextFieldButton);
		controlPanel.add(paddingTextFieldLabel);
		controlPanel.add(paddingTextField);
		controlPanel.add(paddingTextFieldButton);

		JPanel dataTablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dataTablePanel.add(dataTable);
		
		this.add(controlPanel, BorderLayout.PAGE_START);
		this.add(dataTablePanel, BorderLayout.CENTER);

		dataTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                JTable table = (JTable) e.getSource();
                TableColumnModel columnModel = table.getColumnModel();
                int viewColumn = columnModel.getColumnIndexAtX(e.getX());
                int column = columnModel.getColumn(viewColumn).getModelIndex();
                if (0 == column)
                {
                    byteDataTableModel.changeOffsetDisplay();
                }
            }
        });
    }

    public byte[] getDataCopy()
    {
        byte oldData[] = byteDataTableModel.getData();
        byte newData[] = new byte[oldData.length];
        System.arraycopy(oldData, 0, newData, 0, oldData.length);
        
        return newData;
    }

    /**
     * @param message
     * @param title
     */
    private void displayError(String message, String title)
    {
        JOptionPane.showMessageDialog(this.getRootPane(), message, title, JOptionPane.ERROR_MESSAGE);
    }

    class ByteDataTableModel extends AbstractTableModel
    {
        static public final int ABS_HEX_MODE = 0;
        static public final int ABS_DECIMAL_MODE = 1;
        static public final int REL_BASE_HEX_MODE = 2;
        static public final int REL_BASE_DECIMAL_MODE = 3;
        static public final int REL_HEX_MODE = 4;
        static public final int REL_DECIMAL_MODE = 5;

        private int offsetDisplayMode = ABS_HEX_MODE;
        
        static public final int BINARY_MODE = 0;
        static public final int SIGNED_BYTE_MODE = 1;
        static public final int CHARACTER_MODE = 2;
        static public final int OCTAL_MODE = 3;
        static public final int DECIMAL_MODE = 4;
        static public final int HEX_MODE = 5;
        
        /**
         * Object array of String, Integer pairs
         */ 
        public final Object displayModeArray[] = new Object[] {
            new Object[] { "Binary", new Integer(BINARY_MODE) } ,
            new Object[] { "Signed Byte", new Integer(SIGNED_BYTE_MODE) } ,
            new Object[] { "Character", new Integer(CHARACTER_MODE) } ,
            new Object[] { "Octal", new Integer(OCTAL_MODE) } ,
            new Object[] { "Decimal", new Integer(DECIMAL_MODE) } ,
            new Object[] { "Hex", new Integer(HEX_MODE) }
        };
        
        public final String displayModeNameArray[] = new String[] {
            "Binary",
            "Signed Byte",
            "Character",
            "Octal",
            "Decimal",
            "Hex"
        };
        
        private int displayMode = HEX_MODE;
        
        private int padding = 0;
        private int columnCount = -1;
        private byte byteData[] = null;
        private int inclusiveStartOffset;
        private int exclusiveEndOffset;
        private int intervalLength;
    	private long displayedOffset = 0;

        public ByteDataTableModel(int columnCount, long baseOffsetForDisplay, byte byteDataArray[], int inclusiveStartOffset, int exclusiveEndOffset)
        {
            super();

            this.displayedOffset = baseOffsetForDisplay + inclusiveStartOffset;
            this.inclusiveStartOffset = inclusiveStartOffset;
            this.exclusiveEndOffset = exclusiveEndOffset;
            this.intervalLength = exclusiveEndOffset - inclusiveStartOffset;
            
            if (intervalLength < columnCount)
                columnCount = intervalLength;
            
            setColumnCount(columnCount + 1);
            setData(byteDataArray);
        }
        
        public void setPadding(int padding)
        {
            this.padding = padding;
            this.fireTableDataChanged();
        }

        public int getDisplayMode()
        {
            return this.displayMode;
        }
        
        public void setDisplayMode(int displayMode)
        {
            this.displayMode = displayMode;
            this.fireTableDataChanged();
        }
            
        public void setColumnCount(int columnCount)
        {
            this.columnCount = columnCount;
            this.fireTableStructureChanged();
        }
            
        protected byte[] getData()
        {
            return byteData;
        }
            
        public void setData(byte byteDataArray[])
        {
            byteData = byteDataArray;
        }
            
        public String getColumnName(int col)
        {
            return String.valueOf(col);
        }
        public int getRowCount()
        {
            int dataColumnCount = this.columnCount - 1;
            int effectivePadding = this.padding;
            if (effectivePadding >= dataColumnCount)  effectivePadding = 0;
            
            // Often added an extra row
            // return ((shortData.length + effectivePadding) / (this.columnCount - 1)) + 1;
            
            int fullRowsNeeded = (intervalLength + effectivePadding) / (this.columnCount - 1);
            if ((fullRowsNeeded * (this.columnCount - 1)) < (intervalLength + effectivePadding))
                return fullRowsNeeded + 1;
            else return fullRowsNeeded;
        }
        public int getColumnCount()
        {
            return this.columnCount;
        }

        public String exportDataAsCSV()
        {
            String dataString = "";
            
            for (int row = 0; row < getRowCount(); ++row)
            {
                for (int col = 0; col < getColumnCount(); ++col)
                {
                    Object value = getValueAt(row, col);
                    if (value != null) dataString += String.valueOf(value);
                    dataString += "\t";
                }
                dataString += "\n";
            }
            return dataString;
        }
        
        public Object getValueAt(int realRow, int realColumn)
        {
            int dataColumn = realColumn - 1;
            int dataRow = realRow;
            int numberOfDataColumns = this.columnCount - 1;
            
            int effectivePadding = this.padding;
            if (effectivePadding >= numberOfDataColumns)  effectivePadding = 0;
            
            if (0 == realColumn)
            {
                return getOffsetDisplayString(realRow, realColumn);
            }
            
            if ((effectivePadding > dataColumn) && (0 == realRow))
                return null;

            int index =  this.inclusiveStartOffset + (((realRow * numberOfDataColumns) + dataColumn) - effectivePadding);
            if (index >= this.exclusiveEndOffset)  return null;
            
            return getDataValueAt(index);
        }
        
        public Object getDataValueAt(int index)
        {
            byte aByte = byteData[index];
            if (BINARY_MODE == displayMode)
            {
                StringBuffer binaryOutput = new StringBuffer(8);
        
                for (int i = 7; i >= 0; --i)
                    binaryOutput.append((aByte & 1 << i) != 0 ? '1' : '0');
                return binaryOutput.toString();
            }
            else if (SIGNED_BYTE_MODE == displayMode)
            {
                return new Byte(aByte);
            }
            else if (DECIMAL_MODE == displayMode)
            {
                return new Integer(aByte >= 0 ? aByte : (256 + ((int)aByte)));
            }
            else if (CHARACTER_MODE == displayMode)
            {
                Character ch = new Character((char)(aByte >= 0 ? aByte : (256 + ((int)aByte))));
                if (false == Character.isDefined(ch.charValue()))  return "..";
                if (Character.isISOControl(ch.charValue()))  return "..";
                return ch;
            }
            else if (OCTAL_MODE == displayMode)
            {
                return Integer.toOctalString(aByte >= 0 ? aByte : (256 + ((int)aByte)));
            }
            else if (HEX_MODE == displayMode)
            {
                return Integer.toHexString(aByte >= 0 ? aByte : (256 + ((int)aByte)));
            }
            else
            {
                displayError("Unsupported Display Mode: " + displayMode, "Error");
                return "";
            }
        }

        public String getOffsetDisplayString(int row, int realColumn)
        {
            int dataColumn = realColumn - 1;
            int dataColumnCount = this.columnCount - 1;
            
            int dataColumnOffset = dataColumn;
            if (dataColumnOffset < 0)  dataColumnOffset = 0;
            
            int effectivePadding = this.padding;
            if (effectivePadding >= dataColumnCount)  effectivePadding = 0;
            
            long offsetValue = dataColumnOffset + (row * dataColumnCount) - effectivePadding;
            if ((ABS_DECIMAL_MODE == offsetDisplayMode) || (ABS_HEX_MODE == offsetDisplayMode))
            {
                offsetValue += displayedOffset;
            }
            if ((REL_BASE_DECIMAL_MODE == offsetDisplayMode) || (REL_BASE_HEX_MODE == offsetDisplayMode))
            {
                offsetValue += this.inclusiveStartOffset;
            }

            if (offsetValue < 0)  return null;
            
            String offsetString = null;
            
            if ((ABS_DECIMAL_MODE == offsetDisplayMode) || (REL_BASE_DECIMAL_MODE == offsetDisplayMode) || (REL_DECIMAL_MODE == offsetDisplayMode))
            {
                offsetString = String.valueOf(offsetValue);
            }
            else if ((ABS_HEX_MODE == offsetDisplayMode) || (REL_BASE_HEX_MODE == offsetDisplayMode) || (REL_HEX_MODE == offsetDisplayMode))
            {
                offsetString = "0x" + Long.toHexString(offsetValue);
            }
            
            if ((REL_HEX_MODE == offsetDisplayMode) || (REL_DECIMAL_MODE == offsetDisplayMode))
            {
                offsetString += "(r)";
            }
            
            if ((REL_BASE_HEX_MODE == offsetDisplayMode) || (REL_BASE_DECIMAL_MODE == offsetDisplayMode))
            {
                offsetString += "(b)";
            }
            
            return offsetString + ":";
        }

        public boolean isCellEditable(int row, int realColumn)
        {
            if (0 == realColumn)  return false;

            int dataColumn = realColumn - 1;
            int dataColumnCount = this.columnCount - 1;
            
            int effectivePadding = this.padding;
            if (effectivePadding >= dataColumnCount)  effectivePadding = 0;
            
            if ((effectivePadding > dataColumn) && (0 == row))
                return false;

            int index = this.inclusiveStartOffset + (((row * dataColumnCount) + dataColumn) - effectivePadding);
            if (index >= this.exclusiveEndOffset)  return false;
            
            return true;
        }
        public void setValueAt(Object value, int realRow, int realColumn)
        {
            if (0 == realColumn)
            {
                displayError("Illegal row,column pair [" + realRow + "," + realColumn + "]", "Error");
                return;
            }

            int dataColumn = realColumn - 1;
            int dataColumnCount = this.columnCount - 1;

            int effectivePadding = this.padding;
            if (effectivePadding >= dataColumnCount)  effectivePadding = 0;
            
            if ((effectivePadding > dataColumn) && (0 == realRow))
                return;
                
            int index = this.inclusiveStartOffset + (((realRow * dataColumnCount) + dataColumn) - effectivePadding);
            if (index >= this.exclusiveEndOffset)
            {
                displayError("Illegal row,column pair [" + realRow + "," + realColumn + "]", "Error");
                return;
            }
            
            setDataValueAt(value, index);
            fireTableCellUpdated(realRow, realColumn);
        }
        public void setDataValueAt(Object value, int index)
        {
            byte newByteValue = 0;
                
            if (value instanceof Number)
            {
                newByteValue = ((Number)value).byteValue();
            }
            else if (value instanceof Character)
            {
                newByteValue = (byte)(((Character)value).charValue());
            }
            else if (value instanceof String)
            {
                String stringValue = (String)value;
                int intValue = convertValueFromString(displayMode, stringValue);
                
                // Error already reported
                if (-1 == intValue)  return;

                newByteValue = ByteConversions.convertIntToByte(intValue);
            }
            else
            {
                displayError("Illegal value class: " + value.getClass().getName(), "Error");
                return;
            }
                
            byteData[index] = newByteValue;
        }

        protected void changeOffsetDisplay()
        {
            if (ABS_HEX_MODE == offsetDisplayMode)
                offsetDisplayMode = ABS_DECIMAL_MODE;
            else if (ABS_DECIMAL_MODE == offsetDisplayMode)
                offsetDisplayMode = REL_BASE_HEX_MODE;
            else if (REL_BASE_HEX_MODE == offsetDisplayMode)
                offsetDisplayMode = REL_BASE_DECIMAL_MODE;
            else if (REL_BASE_DECIMAL_MODE == offsetDisplayMode)
                offsetDisplayMode = REL_HEX_MODE;
            else if (REL_HEX_MODE == offsetDisplayMode)
                offsetDisplayMode = REL_DECIMAL_MODE;
            else if (REL_DECIMAL_MODE == offsetDisplayMode)
                offsetDisplayMode = ABS_HEX_MODE;
            else
            {
                displayError("Illegal offsetDisplayMode: " + offsetDisplayMode, "Error");
                return;
            }
            fireTableDataChanged();
        }
        
        private int convertValueFromString(int displayMode, String value)
        {
            switch(displayMode)
            {
                case BINARY_MODE:
                    int binaryValue = -1;
                    try
                    {
                        binaryValue = Integer.parseInt(value, 2);
                    }
                    catch (NumberFormatException exception)
                    {
                        displayError("Illegal value: '" + value + "' is not a binary number", "Error");
                        return -1;
                    }
                    if ((binaryValue > 255) || (binaryValue < 0))
                    {
                        displayError("Illegal value: binary number must be between 0 and 11111111", "Error");
                        return -1;
                    }
                    return binaryValue;
                    
                case SIGNED_BYTE_MODE:
                    int signedIntValue = -129;
                    try
                    {
                        signedIntValue = Integer.parseInt(value);
                    }
                    catch (NumberFormatException exception)
                    {
                        displayError("Illegal value: '" + value + "' is not a signed byte number", "Error");
                        return -1;
                    }
                    if ((signedIntValue > 127) || (signedIntValue < -128))
                    {
                        displayError("Illegal value: signed byte number must be between 0 and 255", "Error");
                        return -1;
                    }
                    return ByteConversions.convertByteToInt((byte)signedIntValue);
                    
                case DECIMAL_MODE:
                    int intValue = -1;
                    try
                    {
                        intValue = Integer.parseInt(value);
                    }
                    catch (NumberFormatException exception)
                    {
                        displayError("Illegal value: '" + value + "' is not a decimal number", "Error");
                        return -1;
                    }
                    if ((intValue > 255) || (intValue < 0))
                    {
                        displayError("Illegal value: decimal number must be between 0 and 255", "Error");
                        return -1;
                    }
                    return intValue;
                    
                case CHARACTER_MODE:
                    if (value.length() != 1)
                    {
                        displayError("Illegal value: only one character allowed", "Error");
                        return -1;
                    }
                    return (byte)(value.charAt(0));
                    
                case OCTAL_MODE:
                    int octalValue = -1;
                    try
                    {
                        octalValue = Integer.parseInt(value, 8);
                    }
                    catch (NumberFormatException exception)
                    {
                        displayError("Illegal value: '" + value + "' is not a octal number", "Error");
                        return -1;
                    }
                    if ((octalValue > 255) || (octalValue < 0))
                    {
                        displayError("Illegal value: octal number must be between 0 and 377", "Error");
                        return -1;
                    }
                    return octalValue;
                    
                    
                case HEX_MODE:
                    int hexValue = -1;
                    try
                    {
                        hexValue = Integer.parseInt(value, 16);
                    }
                    catch (NumberFormatException exception)
                    {
                        displayError("Illegal value: '" + value + "' is not a hex number", "Error");
                        return -1;
                    }
                    if ((hexValue > 255) || (hexValue < 0))
                    {
                        displayError("Illegal value: hex number must be between 0 and ff", "Error");
                        return -1;
                    }
                    return hexValue;
                    
                default:
                    displayError("Unsupported Display Mode: " + displayMode, "Error");
                	return -1;
            }
        }
    }

}
