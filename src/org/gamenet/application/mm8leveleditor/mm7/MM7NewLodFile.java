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

package org.gamenet.application.mm8leveleditor.mm7;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

import com.mmbreakfast.unlod.lod.InvalidLodFileException;
import com.mmbreakfast.unlod.lod.LodFile;
import com.mmbreakfast.unlod.lod.NoSuchEntryException;
import com.mmbreakfast.unlod.lod.RandomAccessFileInputStream;

import org.gamenet.application.mm8leveleditor.lod.LodEntry;

public class MM7NewLodFile extends LodFile
{
    protected static final long HEADER_OFFSET = 288L; // yes
    protected static final long ENTRY_HEADER_LENGTH = 32L; // yes
    protected static final long ENTRIES_NUMBER_OFFSET = 284L; // yes
    protected static final int[] SIGNATURE_NEW_MM7 =
    { 76, 79, 68, 0, 77, 77, 86, 73, 73, 0 };
    protected static final int[] SIGNATURE_GAME_MM7 =
        { 76, 79, 68, 0, 71, 97, 109, 101, 77, 77, 86, 73, 0, 135 };
    protected static final int[] SIGNATURE_GAME_MM8 =
        { 76, 79, 68, 0, 71, 97, 109, 101, 77, 77, 86, 73, 0, 134 };


    public MM7NewLodFile(File file, RandomAccessFileInputStream randomaccessfileinputstream)
        throws IOException, InvalidLodFileException
    {
        super(file, randomaccessfileinputstream);
    }

    protected void verifySignature(RandomAccessFile randomaccessfile) throws IOException, InvalidLodFileException
    {
        randomaccessfile.seek(0L);
        boolean matchedSignature = true;
        for (int i = 0; i < SIGNATURE_NEW_MM7.length; i++)
        {
            int aByte = randomaccessfile.read();
            if (aByte != SIGNATURE_NEW_MM7[i])
            {
                matchedSignature = false;
                break;
            }
        }
        
        if (false == matchedSignature)
        {
            randomaccessfile.seek(0L);
            matchedSignature = true;
            for (int i = 0; i < SIGNATURE_GAME_MM7.length; i++)
            {
                int aByte = randomaccessfile.read();
                if (aByte != SIGNATURE_GAME_MM7[i])
                {
                    matchedSignature = false;
                    break;
                }
            }
        }
        if (false == matchedSignature)
        {
            randomaccessfile.seek(0L);
            matchedSignature = true;
            for (int i = 0; i < SIGNATURE_GAME_MM8.length; i++)
            {
                int aByte = randomaccessfile.read();
                if (aByte != SIGNATURE_GAME_MM8[i])
                {
                    matchedSignature = false;
                    break;
                }
            }
        }

        if (false == matchedSignature)
        {
            throw new InvalidLodFileException("Invalid Signature");
        }
    }

	public LodEntry getLodEntryByName(String targetName)
		throws NoSuchEntryException
	{
		Iterator valuesIterator = entries.values().iterator();
		while (valuesIterator.hasNext())
		{
			MM7NewLodEntry aLodEntry = (MM7NewLodEntry)valuesIterator.next();
			if (aLodEntry.getEntryName().equalsIgnoreCase(targetName))  return aLodEntry;
		} 
		
		throw new NoSuchEntryException(targetName);
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
        return MM7NewLodEntry.class;
    }

    public long getEntriesNumberOffset()
    {
        return ENTRIES_NUMBER_OFFSET;
    }
}
