/*
 *  com/mmbreakfast/unlod/lod/LodFile.java
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

package com.mmbreakfast.unlod.lod;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gamenet.application.mm8leveleditor.lod.FileBasedModifiedLodResource;
import org.gamenet.application.mm8leveleditor.lod.LodEntry;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.util.ByteConversions;
import org.gamenet.util.TaskObserver;

import com.mmbreakfast.unlod.app.LodFileList.LodEntryComparator;
import com.mmbreakfast.util.RandomAccessFileOutputStream;

public abstract class LodFile
{
    protected static final Class[] ENTRY_CONSTRUCTOR = new Class[] {LodFile.class, Long.TYPE};

    protected File file;
    protected RandomAccessFileInputStream in;
    protected byte fileHeader[] = null;
    
    // IMPLEMENT: check up on use of this variable in subclasses
    protected HashMap entries = new HashMap();
    protected List orderedEntries = new ArrayList();
    
//    static protected final long LOD_SIGNATURE_OFFSET = 0L; // 3 bytes
//    static protected final String LOD_SIGNATURE = "LOD";
//    static protected final long LOD_SIGNATURE_END_OFFSET = 3L; // 0 bytes
//
//    static protected final long GAME_NAME_SIGNATURE_OFFSET = 4L; // 0x04 -- 3 bytes
//    static protected final String GAME_NAME_SIGNATURE = "MMVI";
//
//    static protected final long UNKNOWN1_OFFSET = 72L; // 0x48 -- 2 bytes = 0x0034
//    static protected final long UNKNOWN2_OFFSET = 74L; // 0x4A -- 2 bytes = 0x004c
//    static protected final long UNKNOWN3_OFFSET = 76L; // 0x4C -- 4 bytes = 0x00000002
//    static protected final long UNKNOWN4_OFFSET = 80L; // 0x50 -- 4 bytes = 0x00000057
//
//    static protected final long DESCRIPTION_OFFSET = 84L;
//    // 0x54 -- 17/18 bytes = "Bitmaps for MMVI" + zero
//
//    static protected final long UNKNOWN5_OFFSET = DESCRIPTION_OFFSET;
//    // + description length ;
//    // 0x66 -- 2 bytes = 0x0002
//    static protected final long UNKNOWN6_OFFSET = 104L; // 0x68 -- 4 bytes = 0x00000000
//    static protected final long UNKNOWN7_OFFSET = 108L;
//    // 0x6c -- 4 bytes = 0x77f121fb = 2012291579 = 8699, 30705
//    static protected final long UNKNOWN8_OFFSET = 112L; // 0x70 -- 4 bytes = 0xffffffff
//    static protected final long UNKNOWN9_OFFSET = 116L;
//    // 0x74 -- 4 bytes = 0x0012fe04 = 1244676
//    static protected final long UNKNOWN10_OFFSET = 120L; // 0x78 -- 4 bytes = 0x00000000
//    static protected final long UNKNOWN11_OFFSET = 124L;
//    // 0x7c -- 4 bytes = 0x0012fe08 = 1244680
//    static protected final long UNKNOWN12_OFFSET = 128L; // 0x80 -- 4 bytes = 0x00000000
//    static protected final long UNKNOWN13_OFFSET = 132L; // 0x84 -- 4 bytes = 0x00000040
//    static protected final long UNKNOWN14_OFFSET = 136L;
//    // 0x88 -- 4 bytes = 0x000186a0 = 100000
//    static protected final long UNKNOWN15_OFFSET = 140L;
//    // 0x8c -- 4 bytes = 0x0000ffd0 = 65488
//    static protected final long UNKNOWN16_OFFSET = 144L;
//    // 0x90 -- 4 bytes = 0x000186a0 = 100000
//    static protected final long UNKNOWN17_OFFSET = 148L;
//    // 0x94 -- 4 bytes = 0x0041b50c = 4306188
//    static protected final long UNKNOWN18_OFFSET = 152L; // 0x98 -- 4 bytes = 0x00000000
//    static protected final long UNKNOWN19_OFFSET = 156L;
//    // 0x9c -- 4 bytes = 0x0012fdcc = 1244620
//    static protected final long UNKNOWN20_OFFSET = 160L;
//    // 0xa0 -- 4 bytes = 0x0012fe08 = 1244680
//    static protected final long UNKNOWN21_OFFSET = 164L; // 0xa4 -- 4 bytes = 0x00000064 = 100
//    static protected final long UNKNOWN22_OFFSET = 168L; // 0xa8 -- 4 bytes = 0x00000000
//    static protected final long UNKNOWN23_OFFSET = 172L; // 0xac -- 4 bytes = 0x00000001
//    static protected final long UNKNOWN24_OFFSET = 176L; // 0xb0 -- 4 bytes = 0xffffffff
//    static protected final long UNKNOWN25_OFFSET = 180L;
//    // 0xb4 -- 4 bytes = 0x0012fe14 = 1244692
//    static protected final long UNKNOWN26_OFFSET = 184L;
//    // 0xb8 -- 4 bytes = 0x77f1217d = 2012291453 = 8573, 30705
//    static protected final long UNKNOWN27_OFFSET = 188L; // 0xbc -- 4 bytes = 0xffffffff
//    static protected final long UNKNOWN28_OFFSET = 192L;
//    // 0xc0 -- 4 bytes = 0x000186a0 = 100008
//    static protected final long UNKNOWN29_OFFSET = 196L; // 0xc4 -- 4 bytes = 0x00000001
//    static protected final long UNKNOWN30_OFFSET = 200L;
//    // 0xc8 -- 2 bytes = 0x0030 = 48 // data header? data content offset?
//    static protected final long UNKNOWN31_OFFSET = 202L; // 0xca -- 2 bytes = 0x00b4 = 180
//    static protected final long UNKNOWN32_OFFSET = 204L; // 0xcc -- 2 bytes = 0x0000 = 0
//    static protected final long UNKNOWN33_OFFSET = 206L; // 0xce -- 2 bytes = 0x00b4 = 180
//    static protected final long UNKNOWN34_OFFSET = 208L;
//    // 0xd0 -- 4 bytes = 0x004131da = 4272602 = 12762, 65
//    static protected final long UNKNOWN35_OFFSET = 212L;
//    // 0xd4 -- 4 bytes = 0x00019000 = 102400 = 36864, 1
//    static protected final long UNKNOWN36_OFFSET = 216L; // 0xd8 -- 2 bytes = 0x0000 = 0
//    static protected final long UNKNOWN37_OFFSET = 218L; // 0xda -- 2 bytes = 0x00b4 = 180
//    static protected final long UNKNOWN38_OFFSET = 220L;
//    // 0xdc -- 4 bytes = 0x0012ffac = 1245100 = 65452,18
//    static protected final long UNKNOWN39_OFFSET = 224L; // 0xe0 -- 2 bytes = 0x002c = 44
//    static protected final long UNKNOWN40_OFFSET = 226L; // 0xe2 -- 2 bytes = 0x00b4 = 180
//    static protected final long UNKNOWN41_OFFSET = 228L; // 0xe4 -- 4 bytes = 0x00000005
//    static protected final long UNKNOWN42_OFFSET = 232L;
//    // 0xe8 -- 4 bytes = 0x0041536c = 4281196 = 21356, 65
//    static protected final long UNKNOWN43_OFFSET = 236L;
//    // 0xec -- 4 bytes = 0x00000020 = 32 // entry header size?
//    static protected final long UNKNOWN44_OFFSET = 240L;
//    // 0xf0 -- 4 bytes = 0x00412aae = 4270766 = 10926, 65
//    static protected final long UNKNOWN45_OFFSET = 244L;
//    // 0xf4 -- 4 bytes = 0x0012fe54 = 1244756 = 65108, 18
//    static protected final long UNKNOWN46_OFFSET = 248L;
//    // 0xf8 -- 4 bytes = 0x0041e6c7 = 4318919 = 59079, 65
//    static protected final long UNKNOWN47_OFFSET = 252L;
//    // 0xfc -- 4 bytes = 0x004156e1 = 5282081 = 22241, 65
//    static protected final long UNKNOWN48_OFFSET = 256L;
//    // 0x0100 - 8 bytes = "bitmaps" + zero
//    static protected final String UNKNOWN48_STRING = "bitmaps";
//    // 0x0100 - 8 bytes = "bitmaps" + zero
//    static protected final long UNKNOWN49_OFFSET = 264L; // 0x0108 -- 2 bytes = 183
//    static protected final long UNKNOWN50_OFFSET = 266L; // 0x010a -- 2 bytes = 179
//    static protected final long UNKNOWN51_OFFSET = 268L;
//    // 0x010c -- 4 bytes = 0x00415346 = 4270918 = 11078, 65
    static protected final long FILE_HEADER_SIZE_OFFSET = 272L;
//    // 0x0110 -- 4 bytes = 0x00000120 = 288
    static protected final long FILE_SIZE_MINUS_FILE_HEADER_SIZE_OFFSET = 276L;
//    // 0x0114 -- 4 bytes = 0x02c82ef1 = 46673649
//    static protected final long UNKNOWN54_OFFSET = 280L; // 0x0118 -- 4 bytes = 0x00000000
//    static protected final long ENTRY_COUNT_OFFSET = 284L;
//    // 0x011c -- 4 bytes = 0x000007a6 = 1958, 0 :: 071a = 1818

    protected LodFile(File file, RandomAccessFileInputStream inputStream)
        throws IOException, InvalidLodFileException
    {
        this.file = file;
        this.in = inputStream;

        RandomAccessFile raf = in.getFile();

        verify(raf);
        
        fileHeader = readFileHeader(raf);
        
        long entryCount = readEntryCount(raf);
        
        readEntries(raf, entryCount);
    }

    public File getFile()
    {
        return file;
    }

    public String getFileName()
    {
        return file.getName();
    }

    protected void verifySignature(RandomAccessFile raf)
        throws IOException, InvalidLodFileException
    {
    }

    protected void verify(RandomAccessFile raf) throws IOException, InvalidLodFileException
    {
        verifySignature(raf);
    }

    protected byte[] readFileHeader(RandomAccessFile raf) throws IOException
    {
        int headerEndLocation = (int)getHeaderOffset();
        byte[] fileHeader = new byte[headerEndLocation];
        raf.seek(0L);
        raf.read(fileHeader, 0, headerEndLocation);
        
        return fileHeader;
    }
    
    protected long readEntryCount(RandomAccessFile raf) throws IOException, InvalidLodFileException
    {
        raf.seek(getEntriesNumberOffset());
        long entryCount = 0L;
        int aByte = raf.read();
        if (aByte != -1)
            entryCount = (long)aByte;
        for (int byteIndex = 1; byteIndex < 4 && aByte != -1; byteIndex++)
        {
            aByte = raf.read();
            entryCount += (long)aByte << 8 * byteIndex;
        }
        if (entryCount == 0L)
            throw new InvalidLodFileException("Number of entries is NUL");
        
        return entryCount;
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
            String entryNameToStore = currentEntry.getFileName().toLowerCase();
            
            if (null != entries.get(entryNameToStore))
            {
                if (entryNameToStore.equals("header.bin"))
                {
                    System.out.println("Unclear what to do with duplicate header.bin in new.lod files.");
                    entryNameToStore = "header.bin.duplicate";
                }
                else
                {
                    throw new RuntimeException("Duplicate entry " + entryIndex + " named '" + currentEntry.getFileName() + "'");
                }
            }
            entries.put(entryNameToStore, currentEntry);
            orderedEntries.add(currentEntry);
        }
    }
    
    private LodResource findFileBasedModifiedLodResource(List modifiedFileList, LodEntry lodEntry)
    {
        Iterator fileIterator = modifiedFileList.iterator();
        while (fileIterator.hasNext())
        {
            File file = (File)fileIterator.next();
            String fileName = file.getName();
            
            if (lodEntry.getFormatConverter().isFileNameComponentForLodEntryFileName(file.getName(), lodEntry.getFileName()))
                return new FileBasedModifiedLodResource(lodEntry.getFormatConverter(), file);
        }
        
        return null;
    }

    public LodEntry findLodEntryForFile(File file)
    {
        List lodEntries = new ArrayList(getLodEntries().values());
        Iterator lodEntryIterator = lodEntries.iterator();
        while (lodEntryIterator.hasNext())
        {
            LodEntry lodEntry = (LodEntry)lodEntryIterator.next();
            
            if (lodEntry.getFormatConverter().isFileNameComponentForLodEntryFileName(file.getName(), lodEntry.getFileName()))
                return lodEntry;
        }
        
        return null;
    }

    public byte[] getFileHeader()
    {
        return fileHeader;
    }

    public Map getLodEntries()
    {
        return entries;
    }

    // needed to retrieve palettes from Bitmap.lod for sprite.lod entries
    public LodEntry findLodEntryByFileName(String string)
        throws NoSuchEntryException
    {
        if (false == entries.containsKey(string.toLowerCase()))
            throw new NoSuchEntryException(string);
        return (LodEntry)entries.get(string.toLowerCase());
    }

    protected LodEntry getNextEntry(int entriesSoFar, RandomAccessFile raf)
        throws IOException
    {
        long offset = this.getHeaderOffset() + this.getEntryHeaderLength() * (long)entriesSoFar;

        raf.seek(offset);

        // The class we choose for instantiation is that of lodFile.getEntryClass()
        try {
           return (LodEntry) this.getEntryClass().getConstructor(ENTRY_CONSTRUCTOR).newInstance(new Object[] {this, new Long(offset)});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
           return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
           return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
           return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
           return null;
        }
    }

    public void write(TaskObserver taskObserver, RandomAccessFile randomAccessFile, List modifiedFileList, LodResource resourceToImport) throws IOException, InterruptedException
    {
        RandomAccessFileOutputStream randomAccessFileOutputStream = new RandomAccessFileOutputStream(randomAccessFile);
        
        // process data offets in order of current dataOffset ordering
        LodEntryComparator dataOffsetLodEntryComparator = new LodEntryComparator()
        {
            public int compare(Object o1, Object o2)
            {
                LodEntry le1 = (LodEntry)o1;
                LodEntry le2 = (LodEntry)o2;
                return le1.getDataOffset() < le2.getDataOffset() ? -1 : 1;
            }

            public String getDisplayName()
            {
                return "data offset";
            }
        };

        List lodEntriesSortedByDataOffset = new ArrayList(getLodEntries().values());
        Collections.sort(lodEntriesSortedByDataOffset, dataOffsetLodEntryComparator);

        // Compute offsets
        
        int oldEntriesCount = lodEntriesSortedByDataOffset.size();
        int newEntriesCount = 0;  // IMPLEMENT: append new entries at end
        int totalEntriesCount = oldEntriesCount + newEntriesCount;

        // start dataOffset after entries
        long computedDataOffset = this.getFileHeader().length + totalEntriesCount * this.getEntryHeaderLength();
        
        float counter = 0;
        Iterator lodEntryByDataOffsetIterator = lodEntriesSortedByDataOffset.iterator();
        while (lodEntryByDataOffsetIterator.hasNext())
        {
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("write thread was interrupted.");

            long newComputedDataOffset = 0;
            LodEntry lodEntry = (LodEntry)lodEntryByDataOffsetIterator.next();

            // write out data
            randomAccessFileOutputStream.getFile().seek(computedDataOffset);

            LodResource lodResource = null;
            if (null != modifiedFileList)
            {
                lodResource = findFileBasedModifiedLodResource(modifiedFileList, lodEntry);
            }

            if (null != resourceToImport)
            {
                if (resourceToImport.getName().equals(lodEntry.getEntryName()))
                {
                    lodResource = resourceToImport;
                }
            }
            
            if (null == lodResource)
            {
                taskObserver.taskProgress("Reusing data " + lodEntry.getFileName(), counter++ / totalEntriesCount);                
                System.out.println("Reusing data " + lodEntry.getFileName());
                newComputedDataOffset = lodEntry.rewriteData(randomAccessFileOutputStream, computedDataOffset);
            }
            else
            {
                taskObserver.taskProgress("Replacing data " + lodEntry.getFileName(), counter++ / totalEntriesCount);                
                System.out.println("Replacing data " + lodEntry.getFileName());
                newComputedDataOffset = lodEntry.updateData(lodResource, randomAccessFileOutputStream, computedDataOffset);
            }

            // System.out.println(lodEntry.getName() + ": new dataoffset " + computedDataOffset);


            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("write thread was interrupted.");

            
            
            // entryOffset for this entry
            long entryOffset = lodEntry.getEntryOffset();
            
            // dataOffset is set for next entry
//          System.out.println(lodEntry.getName() + " - computedOffset: " + String.valueOf(computedDataOffset));

            // write out entry
            randomAccessFileOutputStream.getFile().seek(entryOffset);
            if (null == lodResource)
            {
                System.out.println("Reusing entry " + lodEntry.getFileName());
                long newDataOffset = lodEntry.rewriteEntry(randomAccessFileOutputStream, this.getFileHeader().length, entryOffset, computedDataOffset);
                if (newDataOffset != newComputedDataOffset)
                {
                    throw new RuntimeException("newDataOffset:" + newDataOffset + " != newComputedDataOffset:" + newComputedDataOffset);
                }
            }
            else
            {
                System.out.println("Replacing entry " + lodEntry.getFileName());
                long newDataOffset = lodEntry.updateEntry(lodResource, randomAccessFileOutputStream, this.getFileHeader().length, entryOffset, computedDataOffset);
                if (newDataOffset != newComputedDataOffset)
                {
                    throw new RuntimeException("newDataOffset:" + newDataOffset + " != newComputedDataOffset:" + newComputedDataOffset);
                }
            }

            // System.out.println();
            
            computedDataOffset = newComputedDataOffset;
        }

//      System.out.println("Wrote entries.\n\n");

        // copy file header
        byte newFileHeader[] = new byte[this.fileHeader.length];
        System.arraycopy(this.fileHeader, 0, newFileHeader, 0, this.fileHeader.length);
        
        // update file header
        ByteConversions.setIntegerInByteArrayAtPosition(totalEntriesCount, newFileHeader, (int)this.getEntriesNumberOffset());
        if (-1 != getFileHeaderSizeOffset())
            ByteConversions.setIntegerInByteArrayAtPosition(this.getFileHeader().length, newFileHeader, (int)getFileHeaderSizeOffset());
        if (-1 != getFileSizeMinusFileHeaderSizeOffset())
            ByteConversions.setIntegerInByteArrayAtPosition(computedDataOffset - this.getFileHeader().length, newFileHeader, (int)getFileSizeMinusFileHeaderSizeOffset());
        
        // write out file header
        randomAccessFile.seek(0L);
        randomAccessFile.write(newFileHeader);
        
//      System.out.println("Wrote header.\n\n");
    }

    public void updateByAppendingData(TaskObserver taskObserver, RandomAccessFile randomAccessFile, LodResource resourceToImport) throws IOException, InterruptedException
    {
        taskObserver.taskProgress(resourceToImport.getName(), 0);

        LodEntry lodEntryToUpdate = null;
        float counter = 0;
        Iterator lodEntryByDataOffsetIterator = getLodEntries().values().iterator();
        while (lodEntryByDataOffsetIterator.hasNext())
        {
            if (Thread.currentThread().isInterrupted())
                throw new InterruptedException("append thread was interrupted.");

            LodEntry lodEntry = (LodEntry)lodEntryByDataOffsetIterator.next();

            if (resourceToImport.getName().equals(lodEntry.getEntryName()))
            {
                lodEntryToUpdate = lodEntry;
                break;
            }
            else if (resourceToImport.getName().equals(lodEntry.getFileName()))
            {
                lodEntryToUpdate = lodEntry;
                break;
            }
        }
        
        taskObserver.taskProgress(resourceToImport.getName(), 0.10f);

        if (null == lodEntryToUpdate)
        {
            throw new RuntimeException("Unable to find lod entry for '" + resourceToImport.getName() + "'.");
        }

        long oldFileSize = lodEntryToUpdate.getLodFile().getFile().length();
        RandomAccessFileOutputStream randomAccessFileOutputStream = new RandomAccessFileOutputStream(randomAccessFile);

        // append data content
        randomAccessFileOutputStream.getFile().seek(oldFileSize);
        long newFileSize = lodEntryToUpdate.updateData(resourceToImport, randomAccessFileOutputStream, oldFileSize);

        taskObserver.taskProgress(resourceToImport.getName(), 0.90f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("append thread was interrupted.");

        // update entry content
        long entryOffset = lodEntryToUpdate.getEntryOffset();
        randomAccessFileOutputStream.getFile().seek(entryOffset);
        long newFileSize2 = lodEntryToUpdate.updateEntry(resourceToImport, randomAccessFileOutputStream, this.getFileHeader().length, entryOffset, oldFileSize);

        if (newFileSize != newFileSize2)
            throw new RuntimeException("Data size was " + newFileSize + ", but entry computed size as " + newFileSize2);

        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("append thread was interrupted.");

        taskObserver.taskProgress(resourceToImport.getName(), 0.99f);
        // update file header
        long fileSizeMinusFileHeaderSize = newFileSize - this.getFileHeader().length;
        if (-1 != getFileSizeMinusFileHeaderSizeOffset())
        {
            byte newFileSizeMinusFileHeaderSize[] = new byte[4];
            ByteConversions.setIntegerInByteArrayAtPosition(fileSizeMinusFileHeaderSize, newFileSizeMinusFileHeaderSize, 0);
            randomAccessFile.seek(getFileSizeMinusFileHeaderSizeOffset());
            randomAccessFile.write(newFileSizeMinusFileHeaderSize);
        }
    }

    public RandomAccessFileInputStream getRandomAccessFileInputStream()
    {
        return in;
    }

    protected long getFileHeaderSizeOffset()
    {
        return FILE_HEADER_SIZE_OFFSET;
    }
    
    protected long getFileSizeMinusFileHeaderSizeOffset()
    {
        return FILE_SIZE_MINUS_FILE_HEADER_SIZE_OFFSET;
    }
    
    public abstract long getHeaderOffset();

    public abstract long getEntriesNumberOffset();

    public abstract long getEntryHeaderLength();

    public abstract Class getEntryClass();
}
