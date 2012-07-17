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


public class ObjectLabelValueHolder
{
    private Object object;
    private String label;
    
    public ObjectLabelValueHolder(Object object, String label)
    {
        this.object = object;
        this.label = label;
    }
    
    public String getLabel()
    {
        return this.label;
    }
    public void setLabel(String label)
    {
        this.label = label;
    }
    public Object getObject()
    {
        return this.object;
    }
    public void setObject(Object object)
    {
        this.object = object;
    }

    public static ObjectLabelValueHolder getObjectLabelValueHolderForObjectInObjectLabelValueHolderArray(Object targetObject, ObjectLabelValueHolder[] valueLabelArray)
    {
        for (int valueLabelIndex = 0; valueLabelIndex < valueLabelArray.length; valueLabelIndex++)
        {
            ObjectLabelValueHolder valueLabel = valueLabelArray[valueLabelIndex];
            if (valueLabel.getObject().equals(targetObject))
            {
                return valueLabel;
            }
        }
        
        return null;
    }

    public boolean equals(Object obj)
    {
        // if (super.equals(obj))  return true;

        ObjectLabelValueHolder ovh2 = (ObjectLabelValueHolder)obj;
        
        if (false == this.getLabel().equals(ovh2.getLabel())) 
            return false;
        
        boolean result = (this.getObject().equals(ovh2.getObject()));
        
        return result;
    }
}