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
import javax.swing.TransferHandler;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import org.gamenet.util.ByteConversions;

public class TextMapTableControl extends JPanel
{
	private final ArrayDataTableModel dataTableModel;
	private long displayedOffset = 0;

    public TextMapTableControl(float[][] floatData, int displayedOffset)
    {
        super(new BorderLayout());

        this.displayedOffset = displayedOffset;
        this.dataTableModel = new FloatArrayDataTableModel(floatData);
        
        initialize(floatData[0].length);
    }

    public TextMapTableControl(int[][] intData, int displayedOffset)
    {
        super(new BorderLayout());

        this.displayedOffset = displayedOffset;
        this.dataTableModel = new IntArrayDataTableModel(intData);
        
        initialize(intData[0].length);
    }

    public TextMapTableControl(short[][] shortData, int displayedOffset)
    {
        super(new BorderLayout());

        this.displayedOffset = displayedOffset;
        this.dataTableModel = new ShortArrayDataTableModel(shortData);
        
        initialize(shortData[0].length);
    }

    public TextMapTableControl(byte[][] byteData, long displayedOffset)
    {
        super(new BorderLayout());

        int byteDataColumns = byteData[0].length;
        
        this.displayedOffset = displayedOffset;
        this.dataTableModel = new ByteArrayDataTableModel(byteData);
        
        initialize(byteData[0].length);
    }

    public void initialize(int dataColumns)
    {
		final ResizingJTable dataTable = new ResizingJTable(dataTableModel)
		{
		    public String getToolTipText(MouseEvent e) {
		        String tip = null;
		        java.awt.Point p = e.getPoint();
		        int rowIndex = rowAtPoint(p);
		        int colIndex = columnAtPoint(p);
		        int realColumnIndex = convertColumnIndexToModel(colIndex);
		        
		        if ((0 == realColumnIndex) || (0 == rowIndex))  return null;
		        
		        return "(" + getValueAt(0, realColumnIndex) + "," + getValueAt(rowIndex, 0) + ")";
		    }		    

		    // Doesn't seem to work
//		    public TableCellRenderer getCellRenderer(int row, int column) {
//		        if ((row == 0) || (column == 0)) {
//		            TableCellRenderer renderer = super.getCellRenderer(row, column);
//		            DefaultTableCellRenderer dr = (DefaultTableCellRenderer)renderer;
//		            dr.setFont(dr.getFont().deriveFont(Font.BOLD));
//		            return dr;
//		        }
//		        // else...
//		        return super.getCellRenderer(row, column);
//		    }
		};
		
		JComboBox displayModeComboBox = new JComboBox(dataTableModel.displayModeNameArray);
		displayModeComboBox.setSelectedIndex(this.dataTableModel.getDisplayMode());
		displayModeComboBox.addActionListener(new ActionListener()
			{
                public void actionPerformed(ActionEvent anActionEvent)
                {
					JComboBox cb = (JComboBox)anActionEvent.getSource();
					dataTableModel.setDisplayMode(cb.getSelectedIndex());
					dataTable.recalcColumnWidths();
                }
			}
		);
		
		final JButton copyToClipboardTextFieldButton = new JButton("Copy to clipboard");
	    TransferHandler handler = new TransferHandler("text")
	    {
	        protected Transferable createTransferable(JComponent c) {
	            return new StringSelection(dataTableModel.exportDataAsCSV());
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
		controlPanel.add(new JLabel("Offset: " + displayedOffset));

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
                    dataTableModel.changeOffsetDisplay();
                }
            }
        });
    }

    /**
     * @param message
     * @param title
     */
    private void displayError(String message, String title)
    {
        JOptionPane.showMessageDialog(this.getRootPane(), message, title, JOptionPane.ERROR_MESSAGE);
    }

    abstract class ArrayDataTableModel extends AbstractTableModel
    {
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
        
        private int offsetDisplayMode = HEX_MODE;
        protected int displayMode = HEX_MODE;
        
        final int dataRowCount;
        final int dataColumnCount;
        
        public ArrayDataTableModel(int dataRowCount, int dataColumnCount)
        {
            super();
                
            this.dataRowCount = dataRowCount;
            this.dataColumnCount = dataColumnCount;
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
            
        public abstract Object getDataValueAt(int row, int column);
        public abstract void setDataValueAt(Object value, int row, int column);
            
        public String getColumnName(int col)
        {
            if (0 == col)  return null;
            
            return String.valueOf(512 * ((col - 1) - (this.dataColumnCount/2))) + ":";
        }
        public int getRowCount()
        {
            return 1 + this.dataRowCount;
        }
        public int getColumnCount()
        {
            return 1 + this.dataColumnCount;
        }

        public String exportDataAsCSV()
        {
            String dataString = "";
            
            for (int row = 1; row < getRowCount(); ++row)
            {
                for (int col = 1; col < getColumnCount(); ++col)
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
            if (0 == realRow)  return getColumnName(realColumn);

            if (0 == realColumn)
            {
                return String.valueOf(-512 * ((realRow - 1) - (this.dataRowCount/2))) + ":";
            }
            
            return getDataValueAt(realRow - 1, realColumn -1);
        }
        
        public boolean isCellEditable(int row, int realColumn)
        {
            if (0 == realColumn)  return false;

            return true;
        }
        
        public void setValueAt(Object value, int realRow, int realColumn)
        {
            if (0 == realRow)
            {
                displayError("Illegal row,column pair [" + realRow + "," + realColumn + "]", "Error");
                return;
            }
            
            if (0 == realColumn)
            {
                displayError("Illegal row,column pair [" + realRow + "," + realColumn + "]", "Error");
                return;
            }
            
            Object oldValue = getDataValueAt(realRow - 1, realColumn - 1);
            setDataValueAt(value, realRow - 1, realColumn - 1);
            Object newValue = getDataValueAt(realRow - 1, realColumn - 1);
            
            if ((null == oldValue) && (null != newValue))
            {
                fireTableCellUpdated(realRow - 1, realColumn - 1);
            }
            else if (false == oldValue.equals(newValue))
            {
                fireTableCellUpdated(realRow - 1, realColumn - 1);
            }
        }
        
        protected void changeOffsetDisplay()
        {
            if (DECIMAL_MODE == offsetDisplayMode)
                offsetDisplayMode = HEX_MODE;
            else offsetDisplayMode = DECIMAL_MODE;
            fireTableDataChanged();
        }
        
        protected int convertValueFromString(int displayMode, String value)
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

    class ByteArrayDataTableModel extends ArrayDataTableModel
    {
        byte byteData[][] = null;
        
        public ByteArrayDataTableModel(byte byteDataArray[][])
        {
            super(byteDataArray.length, byteDataArray[0].length);
                
            this.byteData = byteDataArray;
        }
        
        public Object getDataValueAt(int row, int column)
        {
            byte aByte = byteData[row][column];
            
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

        public void setDataValueAt(Object value, int row, int column)
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
                
            byteData[row][column] = newByteValue;
        }
    }

    class ShortArrayDataTableModel extends ArrayDataTableModel
    {
        short shortData[][] = null;
        
        public ShortArrayDataTableModel(short shortDataArray[][])
        {
            super(shortDataArray.length, shortDataArray[0].length);
                
            this.shortData = shortDataArray;
        }
        
        public Object getDataValueAt(int row, int column)
        {
            short aByte = shortData[row][column];
            
            if (BINARY_MODE == displayMode)
            {
                StringBuffer binaryOutput = new StringBuffer(16);
        
                for (int i = 15; i >= 0; --i)
                    binaryOutput.append((aByte & 1 << i) != 0 ? '1' : '0');
                return binaryOutput.toString();
            }
            else if (SIGNED_BYTE_MODE == displayMode)
            {
                return new Short(aByte);
            }
            else if (DECIMAL_MODE == displayMode)
            {
                return new Integer(aByte);
            }
            else if (OCTAL_MODE == displayMode)
            {
                return Integer.toOctalString(aByte);
            }
            else if (HEX_MODE == displayMode)
            {
                return Integer.toHexString(aByte);
            }
            else
            {
                displayError("Unsupported Display Mode: " + displayMode, "Error");
                return "";
            }
        }

        public boolean isCellEditable(int row, int realColumn)
        {
            return false;
        }
        
        public void setDataValueAt(Object value, int row, int column)
        {
            displayError("Editing disallowed.", "Error");
            return;
            
//            short newShortValue = 0;
//                
//            if (value instanceof Number)
//            {
//                newShortValue = ((Number)value).shortValue();
//            }
//            else if (value instanceof String)
//            {
//                String stringValue = (String)value;
//                int intValue = convertValueFromString(displayMode, stringValue);
//                
//                // Error already reported
//                if (-1 == intValue)  return;
//
//                newShortValue = ByteConversions.convertIntToShort(intValue);
//            }
//            else
//            {
//                displayError("Illegal value class: " + value.getClass().getName(), "Error");
//                return;
//            }
//                
//            shortData[row][column] = newShortValue;
        }
    }

    class IntArrayDataTableModel extends ArrayDataTableModel
    {
        int intData[][] = null;
        
        public IntArrayDataTableModel(int intDataArray[][])
        {
            super(intDataArray.length, intDataArray[0].length);
                
            this.intData = intDataArray;
        }
        
        public Object getDataValueAt(int row, int column)
        {
            int aByte = intData[row][column];
            
            if (BINARY_MODE == displayMode)
            {
                StringBuffer binaryOutput = new StringBuffer(16);
        
                for (int i = 15; i >= 0; --i)
                    binaryOutput.append((aByte & 1 << i) != 0 ? '1' : '0');
                return binaryOutput.toString();
            }
            else if (SIGNED_BYTE_MODE == displayMode)
            {
                return new Integer(aByte);
            }
            else if (DECIMAL_MODE == displayMode)
            {
                return new Integer(aByte);
            }
            else if (OCTAL_MODE == displayMode)
            {
                return Integer.toOctalString(aByte);
            }
            else if (HEX_MODE == displayMode)
            {
                return Integer.toHexString(aByte);
            }
            else
            {
                displayError("Unsupported Display Mode: " + displayMode, "Error");
                return "";
            }
        }

        public boolean isCellEditable(int row, int realColumn)
        {
            return false;
        }
        
        public void setDataValueAt(Object value, int row, int column)
        {
            displayError("Editing disallowed.", "Error");
            return;
            
//            int newShortValue = 0;
//                
//            if (value instanceof Number)
//            {
//                newShortValue = ((Number)value).intValue();
//            }
//            else if (value instanceof String)
//            {
//                String stringValue = (String)value;
//                int intValue = convertValueFromString(displayMode, stringValue);
//                
//                // Error already reported
//                if (-1 == intValue)  return;
//
//                newShortValue = ByteConversions.convertIntToShort(intValue);
//            }
//            else
//            {
//                displayError("Illegal value class: " + value.getClass().getName(), "Error");
//                return;
//            }
//                
//            intData[row][column] = newShortValue;
        }
    }

    class FloatArrayDataTableModel extends ArrayDataTableModel
    {
        float floatData[][] = null;
        
        public FloatArrayDataTableModel(float floatDataArray[][])
        {
            super(floatDataArray.length, floatDataArray[0].length);
                
            this.floatData = floatDataArray;
        }
        
        public Object getDataValueAt(int row, int column)
        {
            float value = floatData[row][column];
            
            return new Float(value);
        }

        public boolean isCellEditable(int row, int realColumn)
        {
            return false;
        }
        
        public void setDataValueAt(Object value, int row, int column)
        {
            displayError("Editing disallowed.", "Error");
            return;
            
//            int newShortValue = 0;
//                
//            if (value instanceof Number)
//            {
//                newShortValue = ((Number)value).intValue();
//            }
//            else if (value instanceof String)
//            {
//                String stringValue = (String)value;
//                int intValue = convertValueFromString(displayMode, stringValue);
//                
//                // Error already reported
//                if (-1 == intValue)  return;
//
//                newShortValue = ByteConversions.convertIntToShort(intValue);
//            }
//            else
//            {
//                displayError("Illegal value class: " + value.getClass().getName(), "Error");
//                return;
//            }
//                
//            intData[row][column] = newShortValue;
        }
    }

}
