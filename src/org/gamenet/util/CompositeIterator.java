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


public class CompositeIterator implements Iterator
{
    private Iterator iterator1;
    private Iterator iterator2;
    
    public CompositeIterator(Iterator iterator1, Iterator iterator2)
    {
        super();
        
        if (null == iterator1) throw new IllegalStateException("iterator1 must not be null.");
        if (null == iterator2) throw new IllegalStateException("iterator2 must not be null.");
        
        this.iterator1 = iterator1;
        this.iterator2 = iterator2;
    }
    
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    public boolean hasNext()
    {
        return iterator1.hasNext() || iterator2.hasNext();
    }

    public Object next()
    {
        if (iterator1.hasNext())
        {
            return iterator1.next();
        }
        
        return iterator2.next();
    }
    
    public String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(hashCode());
        stringBuffer.append(":iterator1=" + iterator1);
        stringBuffer.append(":iterator2=" + iterator2);
        return stringBuffer.toString();
    }
}