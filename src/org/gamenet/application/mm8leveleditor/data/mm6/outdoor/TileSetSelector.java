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

package org.gamenet.application.mm8leveleditor.data.mm6.outdoor;

public class TileSetSelector
{
    // 0 = grass
    // 1 = sand
    // 2 = volcanic
    // 4 = x?
    // 5 = water
    
    private short group;
    private short dtileIdNumber;
    
    public TileSetSelector(short group, short offset)
    {
        super();
        
        this.group = group;
        this.dtileIdNumber = offset;
    }
    
    public short getDtileIdNumber()
    {
        return this.dtileIdNumber;
    }
    public void setDtileIdNumber(short offset)
    {
        this.dtileIdNumber = offset;
    }
    public short getGroup()
    {
        return this.group;
    }
    public void setGroup(short group)
    {
        this.group = group;
    }

    public static int getRecordSize()
    {
        return 2 + 2;
    }
}