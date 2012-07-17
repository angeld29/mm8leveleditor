/*
 *  com/mmbreakfast/unlod/lod/mm8/EnglishDLodFile.java
 *
 *  Copyright (C) 2000 Sil Veritas (sil_the_follower_of_dark@hotmail.com)
 */

/*  This file is part of Unlod.
 *
 *  Unlod is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Unlod is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Unlod; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/*  Unlod
 *
 *  Copyright (C) 2000 Sil Veritas. All Rights Reserved. This work is
 *  distributed under the W3C(R) Software License [1] in the hope that it
 *  will be useful, but WITHOUT ANY WARRANTY; without even the implied
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 */

package com.mmbreakfast.unlod.lod.mm8;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.gamenet.application.mm8leveleditor.mm8.EnglishDLodEntry;

import com.mmbreakfast.unlod.lod.InvalidLodFileException;
import com.mmbreakfast.unlod.lod.LodFile;
import com.mmbreakfast.unlod.lod.RandomAccessFileInputStream;

public class EnglishDLodFile extends LodFile
{
    public static final long HEADER_OFFSET = 0x120L;
    public static final long ENTRY_HEADER_LENGTH = 0x04CL;
    public static final long ENTRIES_NUMBER_OFFSET = 0x11CL;
    protected static final int[] SIGNATURE = {'L', 'O', 'D', '\0', 'M', 'M', 'V', 'I', 'I', 'I', '\0'};

    public EnglishDLodFile(File file, RandomAccessFileInputStream in)
        throws IOException, InvalidLodFileException
    {
        super(file, in);
    }

    protected void verifySignature(RandomAccessFile randomAccessFile)
        throws IOException, InvalidLodFileException
    {
        randomAccessFile.seek(0L);
        for (int i = 0; i < SIGNATURE.length; i++)
        {
            int aByte = randomAccessFile.read();
            if (aByte != SIGNATURE[i])
                throw new InvalidLodFileException("Invalid Signature");
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
        return EnglishDLodEntry.class;
    }

    public long getEntriesNumberOffset()
    {
        return ENTRIES_NUMBER_OFFSET;
    }
}
