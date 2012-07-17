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

package org.gamenet.application.mm8leveleditor.mm6;

import java.io.File;
import java.io.IOException;

import com.mmbreakfast.unlod.lod.InvalidLodFileException;
import com.mmbreakfast.unlod.lod.LodFile;
import com.mmbreakfast.unlod.lod.RandomAccessFileInputStream;

public class MM6SndFile extends LodFile
{
    static protected final long HEADER_OFFSET = 4L; // yes
    static protected final long ENTRY_HEADER_LENGTH = 52L; // yes
    static protected final long ENTRIES_NUMBER_OFFSET = 0L; // yes

    public MM6SndFile(File file, RandomAccessFileInputStream randomaccessfileinputstream)
        throws IOException, InvalidLodFileException
    {
        super(file, randomaccessfileinputstream);
    }

    public long getHeaderOffset()
    {
        return HEADER_OFFSET;
    }

    public long getEntryHeaderLength()
    {
        return ENTRY_HEADER_LENGTH;
    }

    public Class getEntryClass()
    {
        return MM6SndEntry.class;
    }

    public long getEntriesNumberOffset()
    {
        return ENTRIES_NUMBER_OFFSET;
    }

    protected long getFileHeaderSizeOffset()
    {
        return -1;
    }
    
    protected long getFileSizeMinusFileHeaderSizeOffset()
    {
        return -1;
    }
    
}
