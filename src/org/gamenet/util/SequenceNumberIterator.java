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

package org.gamenet.util;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class SequenceNumberIterator implements Iterator
{
    int start;
    int current;
    int end;
    int step;
    
    public SequenceNumberIterator(int start, int end, int step)
    {
        super();
        
        if (0 == step) throw new IllegalStateException("Step value must not be 0.");
        
        this.start = start;
        this.current = start;
        this.end = end;
        this.step = step;
    }
    
    public SequenceNumberIterator(int start, int end)
    {
        this(start, end, 1);
    }
    
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    public boolean hasNext()
    {
        if (step > 0)
        {
            if (current <= end)  return true;
        }
        else
        {
            if (current >= end)  return true;
        }
        return false;
    }

    public Object next()
    {
        if (step > 0)
        {
            if (current <= end)
            {
                int oldCurrent = current;
                current += step;
                return new Integer(oldCurrent);
            }
        }
        else
        {
            if (current >= end)
            {
                int oldCurrent = current;
                current += step;
                return new Integer(oldCurrent);
            }
        }
           

        throw new NoSuchElementException();
    }
    
    public String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(hashCode());
        stringBuffer.append(":start=" + start);
        stringBuffer.append(":current=" + current);
        stringBuffer.append(",end=" + String.valueOf(end));
        stringBuffer.append(",step=" + String.valueOf(step));
        return stringBuffer.toString();
    }
}