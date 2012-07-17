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


public class DataSection
{
    private String dataSectionName;
    private Class dataSectionClass;
    private Class dataSectionableClass;
    
    public DataSection(String dataSectionName)
    {
        this(dataSectionName, null, null);
    }
    
    public DataSection(String dataSectionName, Class dataSectionClass, Class dataSectionableClass)
    {
        if ( (null != dataSectionableClass)
          && (false == DataSectionable.class.isAssignableFrom(dataSectionableClass)) )
            throw new RuntimeException("dataSectionable <" + dataSectionableClass.getName() + "> must be an instance of DataSectionable");
        
        this.dataSectionName = dataSectionName;
        this.dataSectionClass = dataSectionClass;
        this.dataSectionableClass = dataSectionableClass;
    }
    
    public String getDataSectionName()
    {
        return this.dataSectionName;
    }
    public Class getDataSectionClass()
    {
        return this.dataSectionClass;
    }
    public Class getDataSectionableClass()
    {
        return this.dataSectionableClass;
    }
    
    public String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(hashCode());
        stringBuffer.append(":dataSectionName=" + dataSectionName);
        return stringBuffer.toString();
    }
}
