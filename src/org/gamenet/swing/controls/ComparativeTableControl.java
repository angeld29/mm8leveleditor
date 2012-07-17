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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import org.gamenet.application.mm8leveleditor.data.DateTime;
import org.gamenet.util.ByteConversions;

public class ComparativeTableControl extends JPanel
{
	private final ComparativeByteDataTableModel byteDataTableModel;
	private long displayedOffset = 0;
    private JPopupMenu popup = null;
    private JMenuItem splitMenuItem = null;
    private JMenuItem mergeMenuItem = null;
	private JMenuItem nameAndLockOffsetDataMenuItem = null;
	private JMenuItem unlockOffsetDataMenuItem = null;
	
    public ComparativeTableControl(List offsetList, DataSource dataSource)
    {
        super(new BorderLayout());

        this.byteDataTableModel = new ComparativeByteDataTableModel(offsetList, dataSource);
        
		final ResizingJTable dataTable = new ResizingJTable(byteDataTableModel)
		{
		    public String getToolTipText(MouseEvent e) {
		        String tip = null;
		        java.awt.Point p = e.getPoint();
		        int rowIndex = rowAtPoint(p);
		        int colIndex = columnAtPoint(p);
		        int realColumnIndex = convertColumnIndexToModel(colIndex);
		        
		        return byteDataTableModel.getCellTooltip(rowIndex, realColumnIndex);
		    }		    
		};

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
		controlPanel.add(copyToClipboardTextFieldButton);
		controlPanel.add(new JLabel("   Right-click to merge or split data."));

		JPanel dataTablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dataTablePanel.add(dataTable);
		
		this.add(controlPanel, BorderLayout.PAGE_START);
		this.add(dataTablePanel, BorderLayout.CENTER);

		popup = new JPopupMenu();
	    
		splitMenuItem = new JMenuItem("Split before column");
	    splitMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                byteDataTableModel.splitOffsetData();
            }
        });
	    popup.add(splitMenuItem);
	    
	    mergeMenuItem = new JMenuItem("Merge columns");
	    mergeMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                byteDataTableModel.mergeOffsetData();
            }
        });
	    popup.add(mergeMenuItem);
	    
	    nameAndLockOffsetDataMenuItem = new JMenuItem("Name and lock column");
	    nameAndLockOffsetDataMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                byteDataTableModel.nameAndLockOffsetData();
            }
        });
	    popup.add(nameAndLockOffsetDataMenuItem);
	    
	    unlockOffsetDataMenuItem = new JMenuItem("Unlock column");
	    unlockOffsetDataMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                byteDataTableModel.unlockOffsetData();
            }
        });
	    popup.add(unlockOffsetDataMenuItem);
	    
		dataTable.addMouseListener(new MouseAdapter()
        {
	        public void mousePressed(MouseEvent e) {
	            maybeShowPopup(e);
	        }

	        public void mouseReleased(MouseEvent e) {
                if (false == maybeShowPopup(e)) {
                    JTable table = (JTable) e.getSource();
                    TableColumnModel columnModel = table.getColumnModel();
                    int viewColumn = columnModel.getColumnIndexAtX(e.getX());
                    int column = columnModel.getColumn(viewColumn).getModelIndex();
                    int row = table.rowAtPoint(e.getPoint());
                    byteDataTableModel.mouseClickAt(row, column);
	            }
	        }

	        private boolean maybeShowPopup(MouseEvent e)
	        {
                 if (e.isPopupTrigger())
                 {
                    JTable table = (JTable) e.getSource();
                    TableColumnModel columnModel = table.getColumnModel();
                    int viewColumn = columnModel.getColumnIndexAtX(e.getX());
                    int column = columnModel.getColumn(viewColumn).getModelIndex();
                    int row = table.rowAtPoint(e.getPoint());
                    byteDataTableModel.setRowAndColumnOnPopup(row, column);
                    
                    byteDataTableModel.hasValidPopupMenuItems(row, column);
                    
	                popup.show(e.getComponent(),
	                           e.getX(), e.getY());
	                return true;
	            }
                else return false;
	        }
        });
    }

    public AbstractTableModel getTableModel()
    {
        return this.byteDataTableModel;
    }

    /**
     * @param message
     * @param title
     */
    private void displayError(String message, String title)
    {
        JOptionPane.showMessageDialog(this.getRootPane(), message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @param message
     * @param title
     */
    private String displayInputPanel(String message, String title)
    {
        return (String)JOptionPane.showInputDialog(
                this.getRootPane(),
                message,
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);
    }

    public interface DataSource
    {
        public int getRowCount();
        public byte[] getData(int dataRow);
        public int getAdjustedDataRowIndex(int dataRow);
        public String getIdentifier(int dataRow);
        public int getOffset(int dataRow);
    }
    
    public static class PartialComparativeDataSource implements DataSource
    {
        private Map adjustedDataRowHashMap = new HashMap();
        private DataSource dataSource = null;

        public PartialComparativeDataSource(DataSource dataSource, List indexList)
        {
            super();
            
            this.dataSource = dataSource;
            int dataRow = 0;
            Iterator indexIterator = indexList.iterator();
            while (indexIterator.hasNext())
            {
                Integer indexInteger = (Integer) indexIterator.next();
                int index = indexInteger.intValue();

                adjustedDataRowHashMap.put(new Integer(dataRow), new Integer(index));
                dataRow++;
            }
        }
        
        public int getRowCount()
        {
            return adjustedDataRowHashMap.size();
        }

        public byte[] getData(int dataRow)
        {
            Integer dataRowNumber = new Integer(dataRow);
            Integer adjustedDataRowNumber = (Integer)adjustedDataRowHashMap.get(dataRowNumber);
            int adjustedDataRow = adjustedDataRowNumber.intValue();
            return dataSource.getData(adjustedDataRow);
        }

        public int getAdjustedDataRowIndex(int dataRow)
        {
            Integer dataRowNumber = new Integer(dataRow);
            Integer adjustedDataRowNumber = (Integer)adjustedDataRowHashMap.get(dataRowNumber);
            return adjustedDataRowNumber.intValue();
        }
        
        public String getIdentifier(int dataRow)
        {
            Integer dataRowNumber = new Integer(dataRow);
            Integer adjustedDataRowNumber = (Integer)adjustedDataRowHashMap.get(dataRowNumber);
            int adjustedDataRow = adjustedDataRowNumber.intValue();
            return dataSource.getIdentifier(adjustedDataRow);
            
        }

        public int getOffset(int dataRow)
        {
            Integer dataRowNumber = new Integer(dataRow);
            Integer adjustedDataRowNumber = (Integer)adjustedDataRowHashMap.get(dataRowNumber);
            int adjustedDataRow = adjustedDataRowNumber.intValue();
            return dataSource.getOffset(adjustedDataRow);
        }
    };

    private class InternalModelDataSource
    {
        private DataSource dataObjectDataSource = null;
        
        public InternalModelDataSource(DataSource dataObjectDataSource)
        {
            this.dataObjectDataSource = dataObjectDataSource;
        }
        
        public int getRowCount()
        {
            return this.dataObjectDataSource.getRowCount();
        }
        

        public int getAdjustedDataRowIndex(int dataRow)
        {
            return this.dataObjectDataSource.getAdjustedDataRowIndex(dataRow);
        }

        public String getRowIdentifier(int dataRow)
        {
            return this.dataObjectDataSource.getIdentifier(dataRow);
        }
        
        public long getRowDataAsLongAtPosition(int dataRow, int index)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if ((offset + 8) > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            return ByteConversions.getLongInByteArrayAtPosition(dataObjectDataSource.getData(dataRow), offset);
        }

        public float getRowDataAsFloatAtPosition(int dataRow, int index)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if ((offset + 4) > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            return ByteConversions.getFloatInByteArrayAtPosition(dataObjectDataSource.getData(dataRow), offset);
        }

        public long getRowDataAsUnsignedIntegerAtPosition(int dataRow, int index)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if ((offset + 4) > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            return ByteConversions.getIntegerInByteArrayAtPosition(dataObjectDataSource.getData(dataRow), offset);
        }

        public int getRowDataAsUnsignedShortAtPosition(int dataRow, int index)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if ((offset + 2) > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            return ByteConversions.getUnsignedShortInByteArrayAtPosition(dataObjectDataSource.getData(dataRow), offset);
        }


        public int getRowDataAsSignedShortAtPosition(int dataRow, int index)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if ((offset + 2) > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            return ByteConversions.getUnsignedShortInByteArrayAtPosition(dataObjectDataSource.getData(dataRow), offset);
        }
        
        public int getRowDataAsUnsignedByteAtPosition(int dataRow, int index)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if (offset > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            return ByteConversions.convertByteToInt(dataObjectDataSource.getData(dataRow)[offset]);
        }


        public int getRowDataAsSignedByteAtPosition(int dataRow, int index)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if (offset > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            return dataObjectDataSource.getData(dataRow)[offset];
        }
        
        public String getRowDataAsZeroTerminatedStringAtPosition(int dataRow, int index, int maxLength)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if ((offset + maxLength) > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds with maxLength <" + maxLength + ">.");
            
            return ByteConversions.getZeroTerminatedStringInByteArrayAtPositionMaxLength(dataObjectDataSource.getData(dataRow), offset, maxLength);
        }
        
        public void setRowDataAsLongAtPosition(int dataRow, long value, int index)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if ((offset + 8) > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            ByteConversions.setLongInByteArrayAtPosition(value, dataObjectDataSource.getData(dataRow), offset);
        }

        public void setRowDataAsFloatAtPosition(int dataRow, long value, int index)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if ((offset + 4) > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            ByteConversions.setFloatInByteArrayAtPosition(value, dataObjectDataSource.getData(dataRow), offset);
        }

        public void setRowDataAsUnsignedIntegerAtPosition(int dataRow, long value, int index)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if ((offset + 4) > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            ByteConversions.setIntegerInByteArrayAtPosition(value, dataObjectDataSource.getData(dataRow), offset);
        }

        public void setRowDataAsSignedShortAtPosition(int dataRow, int value, int index)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if ((offset + 2) > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            ByteConversions.setShortInByteArrayAtPosition((short)value, dataObjectDataSource.getData(dataRow), offset);
        }

        public void setRowDataAsUnsignedShortAtPosition(int dataRow, int value, int index)
        {
            // IMPLEMENT: setDataAsUnsignedShortAtPosition
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if ((offset + 2) > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            ByteConversions.setShortInByteArrayAtPosition((short)value, dataObjectDataSource.getData(dataRow), offset);
        }

        public void setRowDataAsUnsignedByteAtPosition(int dataRow, int value, int index)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if (offset > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            dataObjectDataSource.getData(dataRow)[offset] = ByteConversions.convertIntToByte(value);
        }

        public void setRowDataAsSignedByteAtPosition(int dataRow, int value, int index)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if (offset > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds.");
            
            dataObjectDataSource.getData(dataRow)[offset] = (byte)value;
        }
        
        public void setRowDataAsZeroTerminatedStringAtPosition(int dataRow, String value, int index, int maxLength)
        {
            int offset = index - dataObjectDataSource.getOffset(dataRow);
            if ((offset + maxLength) > dataObjectDataSource.getData(dataRow).length)
                throw new RuntimeException("index <" + index + "> out of bounds with maxLength <" + maxLength + ">.");
            
            ByteConversions.setZeroTerminatedStringInByteArrayAtPositionMaxLength(value, dataObjectDataSource.getData(dataRow), offset, maxLength);
        }
    }

    public static class OffsetData
    {
        private int offset;
        private int representation;
        private int byteLength;
        private String offsetDataName;
        
        public OffsetData(int offset, int byteLength)
        {
            this(offset, byteLength, REPRESENTATION_INT_HEX, null);
        }
        
        public OffsetData(int offset, int byteLength, int representation)
        {
            this(offset, byteLength, representation, null);
        }
        
        public OffsetData(int offset, int byteLength, int representation, String offsetDataName)
        {
            super();
            
            this.offset = offset;
            this.representation = bestRepresentationForByteLength(representation, byteLength);
            this.byteLength = byteLength;
            this.offsetDataName = offsetDataName;
            
            switch (this.representation)
            {
                // 8-byte reps
                case REPRESENTATION_TIME:
                    if (0 != (byteLength % 8))  throw new RuntimeException("Invalid representation <" + this.representation + "> for byteLength <" + byteLength + ">");
                    break;
                    
                    // 4-byte reps
                case REPRESENTATION_INT_HEX:
                case REPRESENTATION_INT_DEC:
                case REPRESENTATION_FLOAT_DEC:
                    if (0 != (byteLength % 4))  throw new RuntimeException("Invalid representation <" + this.representation + "> for byteLength <" + byteLength + ">");
                    break;
                    
                // 2-byte reps
                case REPRESENTATION_SHORT_HEX:
                case REPRESENTATION_SHORT_DEC:
                    if (0 != (byteLength % 2))  throw new RuntimeException("Invalid representation <" + this.representation + "> for byteLength <" + byteLength + ">");
                    break;
                    
                // 1-byte reps
                case REPRESENTATION_BYTE_HEX:
                case REPRESENTATION_BYTE_DEC:
                case REPRESENTATION_STRING:
                    if (byteLength < 1)  throw new RuntimeException("Invalid representation <" + this.representation + "> for byteLength <" + byteLength + ">");
                    break;
                    
                default:
                    throw new RuntimeException("Illegal representation <" + this.representation + ">");
            }
        }
        
        public int getByteLength()
        {
            return this.byteLength;
        }
        public void setByteLength(int byteLength)
        {
            this.byteLength = byteLength;
        }
        public int getRepresentation()
        {
            return this.representation;
        }
        public void setRepresentation(int representation)
        {
            this.representation = representation;
        }
        public int getOffset()
        {
            return this.offset;
        }
        public void setOffset(int offset)
        {
            this.offset = offset;
        }

        public String getOffsetDataName()
        {
            return this.offsetDataName;
        }

        private static int bestRepresentationForByteLength(int oldRepresentation, int byteLength)
        {
            int bestRepresentation = oldRepresentation;
            switch (bestRepresentation)
            {
                // 8-byte reps
                case REPRESENTATION_TIME:
                    if (0 == (byteLength % 8))  return bestRepresentation;
                    bestRepresentation = REPRESENTATION_INT_HEX;
                    break;
            }
            
            switch (bestRepresentation)
            {
                // 4-byte reps
                case REPRESENTATION_INT_HEX:
                    if (0 == (byteLength % 4))  return bestRepresentation;
                    bestRepresentation = REPRESENTATION_SHORT_HEX;
                    break;
                case REPRESENTATION_INT_DEC:
                    if (0 == (byteLength % 4))  return bestRepresentation;
                    bestRepresentation = REPRESENTATION_SHORT_DEC;
                    break;
                case REPRESENTATION_FLOAT_DEC:
                    if (0 == (byteLength % 4))  return bestRepresentation;
                    bestRepresentation = REPRESENTATION_FLOAT_DEC;
                    break;
            }
            
            switch (bestRepresentation)
            {
                // 2-byte reps
                case REPRESENTATION_SHORT_HEX:
                    if (0 == (byteLength % 2))  return bestRepresentation;
                    bestRepresentation = REPRESENTATION_BYTE_HEX;
                    break;
                case REPRESENTATION_SHORT_DEC:
                    if (0 == (byteLength % 2))  return bestRepresentation;
                    bestRepresentation = REPRESENTATION_BYTE_DEC;
                    break;
            }
            
            return bestRepresentation;
        }

        public boolean canSplit(int internalOffset)
        {
            if (null != this.getOffsetDataName())  return false;
            if ((internalOffset < 1) || (internalOffset >= this.byteLength))  return false;
            return true;
        }
        
        /**
         * Truncates existing OffsetData at internal offset and returns rest of data in new OffsetData object
         * @param internalOffset
         * @return new OffsetData containing data after internalOffset
         */
        public OffsetData split(int internalOffset)
        {
            if (false == canSplit(internalOffset))  return null;
            
            int newOffset = this.offset + internalOffset;
            int newByteLength = this.byteLength - internalOffset;
            OffsetData newOffsetData = new OffsetData(newOffset, newByteLength, bestRepresentationForByteLength(this.representation, newByteLength));
        
            int modifiedByteLength = this.byteLength - newByteLength;
            int modifiedRepresentation = bestRepresentationForByteLength(this.representation, modifiedByteLength);
            
            this.byteLength = modifiedByteLength;
            this.representation = modifiedRepresentation;
            
            return newOffsetData;
        }
        
        public boolean canMerge(OffsetData nextOffsetData)
        {
            if (null == nextOffsetData)  return false;
            if (null != this.getOffsetDataName())  return false;
            if (null != nextOffsetData.getOffsetDataName())  return false;
            
            if ((this.offset + byteLength) != nextOffsetData.getOffset())  return false;
            return true;
        }
        
        /**
         * Merges nextOffsetData with existing OffsetData if data is consecutive.
         * nextOffsetData is left unchanged.
         * @param nextOffsetData to merge
         * @return whether merge was successful
         */
        public boolean merge(OffsetData nextOffsetData)
        {
            if (false == canMerge(nextOffsetData))  return false;
            
            int modifiedByteLength = this.byteLength + nextOffsetData.getByteLength();
            int modifiedRepresentation = bestRepresentationForByteLength(this.representation, modifiedByteLength);
            
            this.byteLength = modifiedByteLength;
            this.representation = modifiedRepresentation;

            return true;
        }

        public boolean canNameAndLock()
        {
            return true;
        }

        public void nameAndLock(String newOffsetDataName)
        {
            if (false == canNameAndLock())  return;
            
            this.offsetDataName = newOffsetDataName;
        }

        public boolean canUnlock()
        {
            return (null != this.offsetDataName);
        }

        public void unlock()
        {
            if (false == canUnlock())  return;
            
            this.offsetDataName = null;
        }
    }
    
    public static final int REPRESENTATION_INT_HEX = 0;
    public static final int REPRESENTATION_INT_DEC = 1;
    public static final int REPRESENTATION_SHORT_HEX = 2;
    public static final int REPRESENTATION_SHORT_DEC = 3;
    public static final int REPRESENTATION_BYTE_HEX = 4;
    public static final int REPRESENTATION_BYTE_DEC = 5;
    public static final int REPRESENTATION_STRING = 6;
    public static final int REPRESENTATION_FLOAT_DEC = 7;
    public static final int REPRESENTATION_TIME = 8;

    class ComparativeByteDataTableModel extends AbstractTableModel implements ResizingJTable.ResizingJTableAdvice
    {
        static private final int OFFSET_DISPLAY_MODE_ABS_HEX = 0;
        static private final int OFFSET_DISPLAY_MODE_ABS_DECIMAL = 1;
        static private final int OFFSET_DISPLAY_MODE_REL_HEX = 2;
        static private final int OFFSET_DISPLAY_MODE_REL_DECIMAL = 3;

        private int offsetDisplayMode = OFFSET_DISPLAY_MODE_ABS_HEX;

        private boolean shouldRecalcColumnWidths = true;

        private List offsetList = null;
        private InternalModelDataSource internalModelDataSource = null;
        
        private int lastRowOnPopup = -1;
        private int lastColumnOnPopup = -1;
        
        public ComparativeByteDataTableModel(List offsetList, DataSource dataSource)
        {
            super();
            this.offsetList = offsetList;
            this.internalModelDataSource = new InternalModelDataSource(dataSource);
        }
        
        private String getOffsetDisplayString(int offsetValue)
        {
            String offsetString = null;
            
            if ((OFFSET_DISPLAY_MODE_ABS_DECIMAL == offsetDisplayMode) || (OFFSET_DISPLAY_MODE_REL_DECIMAL == offsetDisplayMode))
            {
                offsetString = String.valueOf(offsetValue);
            }
            else if ((OFFSET_DISPLAY_MODE_ABS_HEX == offsetDisplayMode) || (OFFSET_DISPLAY_MODE_REL_HEX == offsetDisplayMode))
            {
                offsetString = "0x" + Long.toHexString(offsetValue);
            }
            
            if ((OFFSET_DISPLAY_MODE_REL_HEX == offsetDisplayMode) || (OFFSET_DISPLAY_MODE_REL_DECIMAL == offsetDisplayMode))
            {
                offsetString += "(r)";
            }
            
            return offsetString;
        }

        private void changeOffsetDisplay()
        {
            if (OFFSET_DISPLAY_MODE_ABS_HEX == offsetDisplayMode)
                offsetDisplayMode = OFFSET_DISPLAY_MODE_ABS_DECIMAL;
            else if (OFFSET_DISPLAY_MODE_ABS_DECIMAL == offsetDisplayMode)
                offsetDisplayMode = OFFSET_DISPLAY_MODE_REL_HEX;
            else if (OFFSET_DISPLAY_MODE_REL_HEX == offsetDisplayMode)
                offsetDisplayMode = OFFSET_DISPLAY_MODE_REL_DECIMAL;
            else if (OFFSET_DISPLAY_MODE_REL_DECIMAL == offsetDisplayMode)
                offsetDisplayMode = OFFSET_DISPLAY_MODE_ABS_HEX;
            else
            {
                displayError("Illegal offsetDisplayMode: " + offsetDisplayMode, "Error");
                return;
            }
            fireTableDataChanged();
        }
        
        private int representationColumnCount(int representation, int byteLength)
        {
            switch (representation)
            {
                case REPRESENTATION_TIME:
                    if (0 == (byteLength % 8)) return byteLength / 8;
                    displayError("Illegal length <" + byteLength + "> for representation <" + representation + ">", "Error");
            		return -1;
                case REPRESENTATION_INT_HEX:
                case REPRESENTATION_INT_DEC:
                case REPRESENTATION_FLOAT_DEC:
                    if (0 == (byteLength % 4)) return byteLength / 4;
                    displayError("Illegal length <" + byteLength + "> for representation <" + representation + ">", "Error");
            		return -1;
                case REPRESENTATION_SHORT_HEX:
                case REPRESENTATION_SHORT_DEC:
                    if (0 == (byteLength % 2)) return byteLength / 2;
                    displayError("Illegal length <" + byteLength + "> for representation <" + representation + ">", "Error");
            		return -1;
                case REPRESENTATION_BYTE_HEX:
                case REPRESENTATION_BYTE_DEC:
                    return byteLength;
                case REPRESENTATION_STRING:
                    return 1;
                default:
                    displayError("Illegal representation <" + representation + ">", "Error");
            		return -1;
            }
        }
        
        private int getRepresentationByteCountPerColumn(int representation, int byteLength)
        {
            switch (representation)
            {
                case REPRESENTATION_TIME:
            		return 8;
                case REPRESENTATION_INT_HEX:
                case REPRESENTATION_INT_DEC:
                case REPRESENTATION_FLOAT_DEC:
            		return 4;
                case REPRESENTATION_SHORT_HEX:
                case REPRESENTATION_SHORT_DEC:
            		return 2;
                case REPRESENTATION_BYTE_HEX:
                case REPRESENTATION_BYTE_DEC:
                    return 1;
                case REPRESENTATION_STRING:
                    return byteLength;
                default:
                    displayError("Illegal representation <" + representation + ">", "Error");
            		return -1;
            }
        }
        
        public int getColumnCount()
        {
            int columnCount = 1; // row header/identity
            
            for (int offsetIndex = 0; offsetIndex < offsetList.size(); offsetIndex++)
            {
                OffsetData offsetData = (OffsetData)offsetList.get(offsetIndex);
                columnCount += representationColumnCount(offsetData.getRepresentation(), offsetData.getByteLength());
                columnCount += 1; // EMPTY_COLUMN
            }
            
            return columnCount;
        }

        public int getRowCount()
        {
            return 2 + this.internalModelDataSource.getRowCount();
        }

        // TODO: optimize by precomputing these values
        private OffsetData getOffsetDataForColumn(int dataColumn)
        {
            int currentColumn = 0;
            
            for (int offsetIndex = 0; offsetIndex < offsetList.size(); offsetIndex++)
            {
                int startColumn = currentColumn;
                OffsetData offsetData = (OffsetData)offsetList.get(offsetIndex);
                int columnsOfData = representationColumnCount(offsetData.getRepresentation(), offsetData.getByteLength());
                int endColumn = startColumn + columnsOfData; // includes EMPTY_COLUMN at end
            
                if ((dataColumn >= startColumn) && (dataColumn <= endColumn))
                    return offsetData;
                
                currentColumn = endColumn + 1;
            }
            
            displayError("Illegal data column index <" + currentColumn + "> ", "Error");
            return null;
        }

        private int getOffsetInOffsetDataForColumn(int dataColumn)
        {
            int currentColumn = 0;
            
            for (int offsetIndex = 0; offsetIndex < offsetList.size(); offsetIndex++)
            {
                int startColumn = currentColumn;
                OffsetData offsetData = (OffsetData)offsetList.get(offsetIndex);
                int columnsOfData = representationColumnCount(offsetData.getRepresentation(), offsetData.getByteLength());
                int endColumn = startColumn + columnsOfData; // includes EMPTY_COLUMN at end
            
                if ((dataColumn >= startColumn) && (dataColumn <= endColumn))
                    return (dataColumn - startColumn) * getRepresentationByteCountPerColumn(offsetData.getRepresentation(), offsetData.getByteLength());
                
                currentColumn = endColumn + 1;
            }
            
            displayError("Illegal data column index <" + currentColumn + "> ", "Error");
            return -1;
        }

        public String getColumnHeader1(int realColumnIndex)
        {
            if (0 == realColumnIndex)  return null;
            
            int dataColumnIndex = realColumnIndex - 1;
            
            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);
            int offsetInOffsetData = getOffsetInOffsetDataForColumn(dataColumnIndex);
            
            if (offsetData.getByteLength() == offsetInOffsetData)  return null; // EMPTY_COLUMN
            
            int offset = offsetData.getOffset() + offsetInOffsetData;
            
            return getOffsetDisplayString(offset);
        }

        public String getColumnHeader2(int realColumnIndex)
        {
            if (0 == realColumnIndex)  return null;
            
            int dataColumnIndex = realColumnIndex - 1;
            
            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);
            
            int offsetInOffsetData = getOffsetInOffsetDataForColumn(dataColumnIndex);
            int representationByteCountPerColumn = getRepresentationByteCountPerColumn(offsetData.getRepresentation(), offsetData.getByteLength());
            
            if (offsetData.getByteLength() == offsetInOffsetData)  return null; // EMPTY_COLUMN
            
            if (null != offsetData.getOffsetDataName())  return offsetData.getOffsetDataName();
            
            switch (offsetData.getRepresentation())
            {
                case REPRESENTATION_TIME:
                    return "Date" + String.valueOf(offsetInOffsetData / representationByteCountPerColumn);
                case REPRESENTATION_FLOAT_DEC:
                    return "Flt" + String.valueOf(offsetInOffsetData / representationByteCountPerColumn) + "(h)";
                case REPRESENTATION_INT_HEX:
                    return "Lg" + String.valueOf(offsetInOffsetData / representationByteCountPerColumn) + "(h)";
                case REPRESENTATION_INT_DEC:
                    return "Lg" + String.valueOf(offsetInOffsetData / representationByteCountPerColumn) + "(d)";
                case REPRESENTATION_SHORT_HEX:
                    return "Sh" + String.valueOf(offsetInOffsetData / representationByteCountPerColumn) + "(h)";
                case REPRESENTATION_SHORT_DEC:
                    return "Sh" + String.valueOf(offsetInOffsetData / representationByteCountPerColumn) + "(d)";
                case REPRESENTATION_BYTE_HEX:
                    return "By" + String.valueOf(offsetInOffsetData / representationByteCountPerColumn) + "(h)";
                case REPRESENTATION_BYTE_DEC:
                    return "By" + String.valueOf(offsetInOffsetData / representationByteCountPerColumn) + "(d)";
                case REPRESENTATION_STRING:
                    return "str[" + String.valueOf(offsetData.getByteLength()) + "]";
                default:
                    displayError("Illegal representation <" + offsetData.getRepresentation() + ">", "Error");
            		return null;
            }
        }
        
        public String getDataRowName(int dataRowIndex)
        {
            return String.valueOf(internalModelDataSource.getAdjustedDataRowIndex(dataRowIndex)) + ": " + internalModelDataSource.getRowIdentifier(dataRowIndex);
        }
        
        public void mouseClickAt(int realRowIndex, int realColumnIndex)
        {
            if ((0 != realRowIndex) && (1 != realRowIndex))  return;
            
            if (0 == realColumnIndex)  return;
            
            if (0 == realRowIndex)
            {
                changeOffsetDisplay();
                return;
            }

            int dataColumnIndex = realColumnIndex - 1;
            
            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);
            int offsetInOffsetData = getOffsetInOffsetDataForColumn(dataColumnIndex);

            int newRepresentation;
            switch (offsetData.getRepresentation())
            {
                // 8-byte reps
                case REPRESENTATION_TIME:
                    newRepresentation = REPRESENTATION_FLOAT_DEC;
                    break;
                    
                // 4-byte reps
                case REPRESENTATION_FLOAT_DEC:
                    newRepresentation = REPRESENTATION_INT_HEX;
                    break;
                case REPRESENTATION_INT_HEX:
                    newRepresentation = REPRESENTATION_INT_DEC;
                    break;
                case REPRESENTATION_INT_DEC:
                    newRepresentation = REPRESENTATION_SHORT_HEX;
                    break;
                    
                // 2-byte reps
                case REPRESENTATION_SHORT_HEX:
                    newRepresentation = REPRESENTATION_SHORT_DEC;
                    break;
                case REPRESENTATION_SHORT_DEC:
                    newRepresentation = REPRESENTATION_BYTE_HEX;
                    break;
                    
                // 1-byte reps
                case REPRESENTATION_BYTE_HEX:
                    newRepresentation = REPRESENTATION_BYTE_DEC;
                    break;
                case REPRESENTATION_BYTE_DEC:
                    newRepresentation = REPRESENTATION_STRING;
                    break;
                case REPRESENTATION_STRING:
                    // NOTE: valid reps should really be determined programmatically by rep size, but it's not worth handling that way yet
                    if (0 == (offsetData.getByteLength() % 8))
                        newRepresentation = REPRESENTATION_TIME;
                    else if (0 == (offsetData.getByteLength() % 4))
                        newRepresentation = REPRESENTATION_FLOAT_DEC;
                    else if (0 == (offsetData.getByteLength() % 2))
                        newRepresentation = REPRESENTATION_SHORT_HEX;
                    else newRepresentation = REPRESENTATION_BYTE_HEX;
                    break;
                    
                default:
                    displayError("Illegal representation <" + offsetData.getRepresentation() + ">", "Error");
            		return;
            }
            
            offsetData.setRepresentation(newRepresentation);

            shouldRecalcColumnWidths = false;
            try
            {
                fireTableStructureChanged();
            }
            finally
            {
                shouldRecalcColumnWidths = true;
            }
            fireTableDataChanged();
        }
        
        /**
         * @param row
         * @param column
         */
        public void setRowAndColumnOnPopup(int row, int column)
        {
            this.lastRowOnPopup = row;
            this.lastColumnOnPopup = column;
        }
        
        /**
         * @param row
         * @param column
         */
        public boolean hasValidPopupMenuItems(int row, int column)
        {
            boolean canSplitOffsetData = canSplitOffsetData(row, column);
            boolean canMergeOffsetData = canMergeOffsetData(row, column);
            boolean canNameAndLockOffsetData = canNameAndLockOffsetData(row, column);
            boolean canUnlockOffsetData = canUnlockOffsetData(row, column);
            splitMenuItem.setEnabled(canSplitOffsetData);
            mergeMenuItem.setEnabled(canMergeOffsetData);
            nameAndLockOffsetDataMenuItem.setEnabled(canNameAndLockOffsetData);
            unlockOffsetDataMenuItem.setEnabled(canUnlockOffsetData);
            
            return canSplitOffsetData | canMergeOffsetData | canNameAndLockOffsetData | canUnlockOffsetData;
        }

        private boolean canNameAndLockOffsetData(int row, int column)
        {
            int dataRowIndex = row - 2;
            int dataColumnIndex = column - 1;

            // UI Check
            if (dataColumnIndex < 0)  return false;
            
            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);
            int offsetInOffsetData = getOffsetInOffsetDataForColumn(dataColumnIndex);

            // UI Check
            if (offsetData.getByteLength() == offsetInOffsetData)  return false; // EMPTY_COLUMN

            return offsetData.canNameAndLock();
        }

        public void nameAndLockOffsetData()
        {
            int dataRowIndex = lastRowOnPopup - 2;
            int dataColumnIndex = lastColumnOnPopup - 1;

            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);

            if (false == offsetData.canNameAndLock())  return;

            String newOffsetDataName = displayInputPanel("Enter Column Name:", "Name Offset Data");

			if ((newOffsetDataName == null) || (newOffsetDataName.length() == 0))
			    return;

            offsetData.nameAndLock(newOffsetDataName);
            
            fireTableDataChanged();
        }

        private boolean canUnlockOffsetData(int row, int column)
        {
            int dataRowIndex = row - 2;
            int dataColumnIndex = column - 1;

            // UI Check
            if (dataColumnIndex < 0)  return false;
            
            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);
            int offsetInOffsetData = getOffsetInOffsetDataForColumn(dataColumnIndex);

            // UI Check
            if (offsetData.getByteLength() == offsetInOffsetData)  return false; // EMPTY_COLUMN

            return offsetData.canUnlock();
        }

        public void unlockOffsetData()
        {
            int dataRowIndex = lastRowOnPopup - 2;
            int dataColumnIndex = lastColumnOnPopup - 1;

            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);

            if (false == offsetData.canUnlock())  return;

            offsetData.unlock();
            
            fireTableDataChanged();
        }

        private boolean canSplitOffsetData(int row, int column)
        {
            int dataRowIndex = row - 2;
            int dataColumnIndex = column - 1;

            // UI Check
            if (dataColumnIndex < 0)  return false;
            
            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);
            int offsetInOffsetData = getOffsetInOffsetDataForColumn(dataColumnIndex);

            // UI Check
            if (offsetData.getByteLength() == offsetInOffsetData)  return false; // EMPTY_COLUMN

            return offsetData.canSplit(offsetInOffsetData);
        }

        public void splitOffsetData()
        {
            int dataRowIndex = lastRowOnPopup - 2;
            int dataColumnIndex = lastColumnOnPopup - 1;

            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);
            int offsetInOffsetData = getOffsetInOffsetDataForColumn(dataColumnIndex);

            if (false == offsetData.canSplit(offsetInOffsetData))  return;

            int offsetIndex = offsetList.indexOf(offsetData);
            OffsetData newOffsetData = offsetData.split(offsetInOffsetData);
            offsetList.add(offsetIndex + 1, newOffsetData);
            
            shouldRecalcColumnWidths = false;
            try
            {
                fireTableStructureChanged();
            }
            finally
            {
                shouldRecalcColumnWidths = true;
            }
            fireTableDataChanged();
        }

        private boolean canMergeOffsetData(int row, int column)
        {
            int dataRowIndex = lastRowOnPopup - 2;
            int dataColumnIndex = lastColumnOnPopup - 1;

            // UI Check
            if (dataColumnIndex < 0)  return false;
            
            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);
            int offsetInOffsetData = getOffsetInOffsetDataForColumn(dataColumnIndex);

            // UI Check
            if (offsetData.getByteLength() != offsetInOffsetData)  return false; // not EMPTY_COLUMN

            int offsetIndex = offsetList.indexOf(offsetData);
            if ((offsetIndex + 1) == offsetList.size())  return false; // no offsets after this one
            
            OffsetData nextOffsetData = (OffsetData)offsetList.get(offsetIndex + 1);

            return offsetData.canMerge(nextOffsetData);
        }

        public void mergeOffsetData()
        {
            int dataRowIndex = lastRowOnPopup - 2;
            int dataColumnIndex = lastColumnOnPopup - 1;

            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);
            int offsetInOffsetData = getOffsetInOffsetDataForColumn(dataColumnIndex);

            int offsetIndex = offsetList.indexOf(offsetData);
            if ((offsetIndex + 1) == offsetList.size())  return; // no offsets after this one

            OffsetData nextOffsetData = (OffsetData)offsetList.get(offsetIndex + 1);

            if (false == offsetData.canMerge(nextOffsetData))  return;

            if (offsetData.merge(nextOffsetData))
            {
                offsetList.remove(offsetIndex + 1);
            }
            
            shouldRecalcColumnWidths = false;
            try
            {
                fireTableStructureChanged();
            }
            finally
            {
                shouldRecalcColumnWidths = true;
            }
            fireTableDataChanged();
        }

        public String getCellTooltip(int realRowIndex, int realColumnIndex)
        {
            if (0 == realColumnIndex)  return null;
            if (0 == realRowIndex)  return "Click to change offset representation";
            if (1 == realRowIndex)  return "Click to change data representation";
            
            int dataRowIndex = realRowIndex - 2;
            int dataColumnIndex = realColumnIndex - 1;

            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);
            int offsetInOffsetData = getOffsetInOffsetDataForColumn(dataColumnIndex);

            if (offsetData.getByteLength() == offsetInOffsetData)  return null; // EMPTY_COLUMN
            
            return byteDataTableModel.getColumnHeader1(realColumnIndex) + " " + byteDataTableModel.getColumnHeader2(realColumnIndex) + ", " + byteDataTableModel.getDataRowName(dataRowIndex);
        }

        public Object getValueAt(int realRowIndex, int realColumnIndex)
        {
            if (0 == realRowIndex)  return getColumnHeader1(realColumnIndex);
            if (1 == realRowIndex)  return getColumnHeader2(realColumnIndex);

            int dataRowIndex = realRowIndex - 2;
            int dataColumnIndex = realColumnIndex - 1;

            if (0 == realColumnIndex)  return getDataRowName(dataRowIndex);

            return getDataValueAt(dataRowIndex, dataColumnIndex);
        }

        public Object getDataValueAt(int dataRowIndex, int dataColumnIndex)
        {
            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);
            int offsetInOffsetData = getOffsetInOffsetDataForColumn(dataColumnIndex);

            if (offsetData.getByteLength() == offsetInOffsetData)  return null; // EMPTY_COLUMN
                        
            // System.out.println("dataRowIndex=" + dataRowIndex + ", dataColumnIndex=" + dataColumnIndex + ", datumColumns=" + datumColumns + ", dataIndex=" + dataIndex + ", dataType=" + dataType);
            
            int offset = offsetData.getOffset() + offsetInOffsetData;
            
            switch (offsetData.getRepresentation())
            {
                case REPRESENTATION_TIME:
                    return DateTime.toString(internalModelDataSource.getRowDataAsLongAtPosition(dataRowIndex, offset));
                case REPRESENTATION_FLOAT_DEC:
                    return Float.toString(internalModelDataSource.getRowDataAsFloatAtPosition(dataRowIndex, offset));
                case REPRESENTATION_INT_HEX:
                    return Long.toHexString(internalModelDataSource.getRowDataAsUnsignedIntegerAtPosition(dataRowIndex, offset));
                case REPRESENTATION_INT_DEC:
                    return String.valueOf(internalModelDataSource.getRowDataAsUnsignedIntegerAtPosition(dataRowIndex, offset));
                case REPRESENTATION_SHORT_HEX:
                    return Integer.toHexString(internalModelDataSource.getRowDataAsUnsignedShortAtPosition(dataRowIndex, offset));
                case REPRESENTATION_SHORT_DEC:
                    return String.valueOf(internalModelDataSource.getRowDataAsSignedShortAtPosition(dataRowIndex, offset));
                case REPRESENTATION_BYTE_HEX:
                    return Integer.toHexString(internalModelDataSource.getRowDataAsUnsignedByteAtPosition(dataRowIndex, offset));
                case REPRESENTATION_BYTE_DEC:
                    return String.valueOf(internalModelDataSource.getRowDataAsUnsignedByteAtPosition(dataRowIndex, offset));
                case REPRESENTATION_STRING:
                    return internalModelDataSource.getRowDataAsZeroTerminatedStringAtPosition(dataRowIndex, offset, offsetData.getByteLength());
                default:
                    displayError("Illegal representation <" + offsetData.getRepresentation() + ">", "Error");
            		return null;
            }
        }
        
        public boolean isCellEditable(int realRow, int realColumn)
        {
            if (0 == realColumn)  return false;
            if ((0 == realRow) || (1 == realRow))  return false;
            
            int dataRowIndex = realRow - 2;
            int dataColumnIndex = realColumn - 1;

            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);
            int offsetInOffsetData = getOffsetInOffsetDataForColumn(dataColumnIndex);

            if (offsetData.getByteLength() == offsetInOffsetData)  return false; // EMPTY_COLUMN
            
            return true;
        }

        public void setValueAt(Object value, int realRow, int realColumn)
        {
            if ((0 == realRow) || (1 == realRow))
            {
                displayError("Illegal row,column pair [" + realRow + "," + realColumn + "]", "Error");
                return;
            }
            
            if (0 == realColumn)
            {
                displayError("Illegal row,column pair [" + realRow + "," + realColumn + "]", "Error");
                return;
            }
            
            int dataRow = realRow - 2;
            int dataColumn = realColumn - 1;
            
            Object oldValue = getDataValueAt(dataRow, dataColumn);
            setDataValueAt(value, dataRow, dataColumn);
            Object newValue = getDataValueAt(dataRow, dataColumn);
            
            if ((null == oldValue) && (null != newValue))
            {
                fireTableCellUpdated(realRow, realColumn);
            }
            else if (false == oldValue.equals(newValue))
            {
                fireTableCellUpdated(realRow, realColumn);
            }
        }
            
        public void setDataValueAt(Object value, int dataRowIndex, int dataColumnIndex)
        {
            OffsetData offsetData = getOffsetDataForColumn(dataColumnIndex);
            int offsetInOffsetData = getOffsetInOffsetDataForColumn(dataColumnIndex);

            if (offsetData.getByteLength() == offsetInOffsetData) // EMPTY_COLUMN
            {
                displayError("Illegal row,column pair [" + dataRowIndex + "," + dataColumnIndex + "]", "Error");
                return;
            }
            
            int offset = offsetData.getOffset() + offsetInOffsetData;

            switch (offsetData.getRepresentation())
            {
                case REPRESENTATION_TIME:
                    Long result = DateTime.parseLong((String)value);
                    if (null == result)
                    {
                        displayError("Unable to convert <" + value + "> to game time.", "Error");
                		return;
                    }
                    internalModelDataSource.setRowDataAsLongAtPosition(dataRowIndex, result.longValue(), offset);
                    break;
                case REPRESENTATION_FLOAT_DEC:
                    internalModelDataSource.setRowDataAsFloatAtPosition(dataRowIndex, Long.parseLong((String)value, 16), offset);
                    break;
                case REPRESENTATION_INT_HEX:
                    internalModelDataSource.setRowDataAsUnsignedIntegerAtPosition(dataRowIndex, Long.parseLong((String)value, 16), offset);
                    break;
                case REPRESENTATION_INT_DEC:
                    internalModelDataSource.setRowDataAsUnsignedIntegerAtPosition(dataRowIndex, Long.parseLong((String)value), offset);
                    break;
                case REPRESENTATION_SHORT_HEX:
                    internalModelDataSource.setRowDataAsSignedShortAtPosition(dataRowIndex, Short.parseShort((String)value, 16), offset);
                    break;
                case REPRESENTATION_SHORT_DEC:
                    internalModelDataSource.setRowDataAsSignedShortAtPosition(dataRowIndex, Short.parseShort((String)value), offset);
                    break;
                case REPRESENTATION_BYTE_HEX:
                    internalModelDataSource.setRowDataAsUnsignedByteAtPosition(dataRowIndex, Short.parseShort((String)value, 16), offset);
                    break;
                case REPRESENTATION_BYTE_DEC:
                    internalModelDataSource.setRowDataAsUnsignedByteAtPosition(dataRowIndex, Short.parseShort((String)value), offset);
                    break;
                case REPRESENTATION_STRING:
                    internalModelDataSource.setRowDataAsZeroTerminatedStringAtPosition(dataRowIndex, (String)value, offset, offsetData.getByteLength());
                    break;
                default:
                    displayError("Illegal representation <" + offsetData.getRepresentation() + ">", "Error");
            		return;
            }
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
        
        public boolean shouldRecalcColumnWidths()
        {
            return shouldRecalcColumnWidths;
        }
        
    }
}
