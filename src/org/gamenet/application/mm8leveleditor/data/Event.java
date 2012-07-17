/*
 * Copyright (c) 2007 (Mike) Maurice Kienenberger (mkienenb@sourceforge.net)
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

package org.gamenet.application.mm8leveleditor.data;

import java.util.ArrayList;
import java.util.List;

public class Event
{
    private EventFormat eventFormat = null;
    
    // IMPLEMENT: remove after there are no more unknowns.
    private byte rawData[];
    
    private int eventNumber = 0;
    private int sequenceNumber = 0;
    private int commandType = 0;
    private List argumentList = new ArrayList();

    public Event(EventFormat eventFormat)
    {
        super();

        this.eventFormat = eventFormat;
    }
    
    public EventFormat getEventFormat()
    {
        return this.eventFormat;
    }

    public int getEventNumber()
    {
        return this.eventNumber;
    }
    public void setEventNumber(int eventNumber)
    {
        this.eventNumber = eventNumber;
    }

    public int getCommandType()
    {
        return this.commandType;
    }
    public void setCommandType(int commandType)
    {
        this.commandType = commandType;
    }

    public int getSequenceNumber()
    {
        return this.sequenceNumber;
    }
    public void setSequenceNumber(int sequenceNumber)
    {
        this.sequenceNumber = sequenceNumber;
    }
    
    /**
     * Appends the specified argument to the end of the argument list.
     *
     * @param argument argument to be appended to this list.
     */
    public void addArgument(Object argument)
    {
        argumentList.add(argument);
    }

    /**
     * Replaces the argument at the specified position in the argument list with the
     * specified argument.
     *
     * @param index index of argument to replace.
     * @param argument argument to be stored at the specified position.
     * 
     * @throws    IndexOutOfBoundsException if the index is out of range
     *		  (index &lt; 0 || index &gt;= size()).
     */
    public void setArgumentAtIndex(int index, Object argument)
    {
        argumentList.set(index, argument);
    }
    
    /**
     * Returns the argument at the specified position in the argument list.
     *
     * @param index index of argument to return.
     * @return the element at the specified position in the argument list.
     * 
     * @throws IndexOutOfBoundsException if the index is out of range (index
     * 		  &lt; 0 || index &gt;= size()).
     */
    public Object getArgumentAtIndex(int index)
    {
        return argumentList.get(index);
    }
    
    public int getArgumentDataTypeAtIndex(int index)
    {
        int argumentType = getEventFormat().getArgumentTypeAtIndexForCommandType(index, getCommandType());
        return getEventFormat().getArgumentDataType(argumentType);
    }
    
    
    public byte[] getRawData()
    {
        return this.rawData;
    }
    public void setRawData(byte[] rawData)
    {
        this.rawData = rawData;
    }
}
