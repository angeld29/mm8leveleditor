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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

public class StringComboBox extends JComboBox implements ActionListener
{
    private ObjectLabelValueHolder[] objectValueLabelArray;
    private ObjectValueHolder objectValueHolder;

    /** @deprecated */
    private NumberLabelValueHolder[] numberValueLabelArray;
    /** @deprecated */
    private IntValueHolder intValueHolder;
    
    public StringComboBox(Map labelValuesMap, ObjectValueHolder srcObjectValueHolder)
    {
        this(convertMapToLabelNameObjectArray(labelValuesMap), srcObjectValueHolder);
    }

    private static ObjectLabelValueHolder[] convertMapToLabelNameObjectArray(Map map)
    {
        ObjectLabelValueHolder array[] = new ObjectLabelValueHolder[map.size()];
        int arrayIndex = 0;
        Iterator labelValueEntryIterator = map.entrySet().iterator();
        while (labelValueEntryIterator.hasNext())
        {
            Map.Entry labelValueEntry = (Map.Entry) labelValueEntryIterator.next();
            String label = (String)labelValueEntry.getKey();
            Object value = labelValueEntry.getValue();
            array[arrayIndex++] = new ObjectLabelValueHolder(value, label);
        }
        return array;
    }

    public StringComboBox(final ObjectLabelValueHolder srcValueLabelArray[],
            final ObjectValueHolder srcObjectValueHolder)
    {
        this(convertObjectToNumberVH(srcValueLabelArray), createIntValueHolderFromObjectValueHolder(srcValueLabelArray, srcObjectValueHolder));
    }

    //// IMPLEMENT:  WHY DOES THE FOLLOWING NOT WORK!?!?!?!?!?!?!?!?!??!?!?!?!?!?!
    
//    public StringComboBox(ObjectLabelValueHolder srcValueLabelArray[], ObjectValueHolder srcObjectValueHolder)
//    {
//        this.objectValueLabelArray = srcValueLabelArray;
//        this.objectValueHolder = srcObjectValueHolder;
//        
//        Object selectedObject = objectValueHolder.getValue();
//        
//        ObjectLabelValueHolder defaultObjectLabelValueHolder
//    			= ObjectLabelValueHolder.getObjectLabelValueHolderForObjectInObjectLabelValueHolderArray(
//    			        selectedObject, objectValueLabelArray);
//
//        ListCellRenderer renderer = new AbstractStringListCellRenderer()
//        {
//            protected String getStringForValue(Object value)
//            {
//                if (null == value)
//                {
//                    return  "Error: empty value";	
//                }
//                
//                if (value instanceof ObjectLabelValueHolder)
//                {
//                    return ((ObjectLabelValueHolder)value).getLabel();
//                }
//                else
//                {
//                    return  "Error: wrong value class: " + value.getClass().getName();	
//                }
//            }
//        };
//
//        this.setRenderer(renderer);
//        this.setSelectedItem(defaultObjectLabelValueHolder);
//        this.addActionListener(this);
//    }

    static private IntValueHolder createIntValueHolderFromObjectValueHolder(final ObjectLabelValueHolder[] srcValueLabelArray, final ObjectValueHolder srcObjectValueHolder)
    {
        return new IntValueHolder()
        {
            public int getValue()
            {
                return convertObjectToNumber(srcValueLabelArray, srcObjectValueHolder.getValue());
            }

            public void setValue(int value)
            {
                srcObjectValueHolder.setValue(convertNumberToObject(srcValueLabelArray, value));
            }

            private int convertObjectToNumber(ObjectLabelValueHolder[] valueLabelArray, Object targetObject)
            {
                for (int valueLabelIndex = 0; valueLabelIndex < valueLabelArray.length; valueLabelIndex++)
                {
                    ObjectLabelValueHolder valueLabel = valueLabelArray[valueLabelIndex];
                    if (valueLabel.getObject().equals(targetObject))
                    {
                        return valueLabelIndex;
                    }
                }
                
                return -1;
            }

            private Object convertNumberToObject(ObjectLabelValueHolder[] valueLabelArray, int number)
            {
                return valueLabelArray[number].getObject();
            }
        };
    }

    static private NumberLabelValueHolder[] convertObjectToNumberVH(final ObjectLabelValueHolder[] srcValueLabelArray)
    {
        NumberLabelValueHolder numberLabelValueArray[] = new NumberLabelValueHolder[srcValueLabelArray.length];
        for (int objectIndex = 0; objectIndex < srcValueLabelArray.length; objectIndex++)
        {
            ObjectLabelValueHolder objectVH = srcValueLabelArray[objectIndex];
            numberLabelValueArray[objectIndex] = new NumberLabelValueHolder(objectIndex, objectVH.getLabel());
        }
        
        return numberLabelValueArray;
    }

    /** @deprecated */
    public StringComboBox(NumberLabelValueHolder srcValueLabelArray[], IntValueHolder srcIntValueHolder)
    {
        super(srcValueLabelArray);
        this.numberValueLabelArray = srcValueLabelArray;
        this.intValueHolder = srcIntValueHolder;
        
        NumberLabelValueHolder selectedNumberLabelValueHolder = NumberLabelValueHolder.getLabelForNumberInArray(
                intValueHolder.getValue(), numberValueLabelArray);
        
        ListCellRenderer renderer = new AbstractStringListCellRenderer()
        {
            protected String getStringForValue(Object value)
            {
                if (null == value)  return  "Error: empty value";	
                return ((NumberLabelValueHolder)value).getLabel();
            }
        };

        this.setRenderer(renderer);
        this.setSelectedItem(selectedNumberLabelValueHolder);
        this.addActionListener(this);
    }

	public void actionPerformed(ActionEvent e)
	{
        JComboBox cb = (JComboBox)e.getSource();
        
        if (null == intValueHolder)
        {
            ObjectLabelValueHolder item = (ObjectLabelValueHolder)cb.getSelectedItem();
            objectValueHolder.setValue(item.getObject());
        }
        else /** deprecated methods */
        {
            NumberLabelValueHolder item = (NumberLabelValueHolder)cb.getSelectedItem();
            intValueHolder.setValue(item.getNumber());
        }
    }

    public ObjectLabelValueHolder[] getObjectLabelValueHolder()
    {
        return objectValueLabelArray;
    }

    public ObjectValueHolder getObjectValueHolder()
    {
        return objectValueHolder;
    }

    /** @deprecated */
    public NumberLabelValueHolder[] getNumberLabelValueHolder()
    {
        return numberValueLabelArray;
    }

    /** @deprecated */
    public IntValueHolder getIntValueHolder()
    {
        return intValueHolder;
    }
}
