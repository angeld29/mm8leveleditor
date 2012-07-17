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
import java.io.RandomAccessFile;

import org.gamenet.application.mm8leveleditor.lod.LodEntry;

import com.mmbreakfast.unlod.lod.InvalidLodFileException;
import com.mmbreakfast.unlod.lod.LodFile;
import com.mmbreakfast.unlod.lod.RandomAccessFileInputStream;

public class MM6VidFile extends LodFile
{
    public static final long HEADER_OFFSET = 4L; // yes
    public static final long ENTRY_HEADER_LENGTH = 44L; // yes
    public static final long ENTRIES_NUMBER_OFFSET = 0L; // yes

    public MM6VidFile(File file, RandomAccessFileInputStream in)
        throws IOException, InvalidLodFileException
    {
        super(file, in);
    }

    protected void readEntries(RandomAccessFile raf, long entryCount) throws IOException
    {
        raf.seek(getHeaderOffset());
        
        LodEntry currentEntry;
        for (int entryIndex = 0;
            ((long)entryIndex < entryCount
                && (currentEntry = getNextEntry(entryIndex, raf))
                    != null);
            entryIndex++)
        {
            entries.put(currentEntry.getFileName(), currentEntry);
            orderedEntries.add(currentEntry);
            
            MM6VidEntry mm6VidEntry = (MM6VidEntry)currentEntry;
            mm6VidEntry.computeDataLength(raf, entryIndex, entryCount);
        }
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
        return MM6VidEntry.class;
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
