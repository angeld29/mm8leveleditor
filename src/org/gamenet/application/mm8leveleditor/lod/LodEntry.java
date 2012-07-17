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

package org.gamenet.application.mm8leveleditor.lod;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import org.gamenet.application.mm8leveleditor.converter.FormatConverter;
import org.gamenet.application.mm8leveleditor.converter.NullFormatConverter;

import com.mmbreakfast.unlod.lod.LodFile;

/**
 * @author mlk
 */
public abstract class LodEntry implements LodResource, Comparable
{
    protected LodFile lodFile = null;
    protected long entryOffset = -1;
    
    protected byte[] entryHeader = null;
    protected byte[] dataHeader = null;
    
    public LodEntry()
    {
        super();
    }

    public LodEntry(LodFile lodFile, long entryOffset) throws IOException
    {
        super();
        
        this.lodFile = lodFile;
        this.entryOffset = entryOffset;
        
        computeFileBasedOffsets();
        
        readEntryHeader();
        
        computeEntryBasedOffsets();
        
        readDataHeader();
        
        computeDataBasedOffsets();
    }

    // needed to retrieve palettes from Bitmap.lod for sprite.lod entries
    public LodFile getLodFile()
    {
        return lodFile;
    }
    
    protected void computeFileBasedOffsets()
    {
        entryHeader = new byte[(int)lodFile.getEntryHeaderLength()];
    }

    protected void readEntryHeader() throws IOException
    {
        RandomAccessFile raf = lodFile.getRandomAccessFileInputStream().getFile();
        
        raf.seek(getEntryOffset());
        int totalBytesRead = 0;
        while (totalBytesRead < entryHeader.length)
        {
            int bytesRead = raf.read(entryHeader, totalBytesRead, (entryHeader.length - totalBytesRead));
            if (-1 == bytesRead)
            {
                throw new EOFException("Read " + String.valueOf(totalBytesRead) + " bytes but expected " + String.valueOf(entryHeader.length) + " bytes for entryHeader.");
            }
                    
            totalBytesRead += bytesRead;
        }
    }

    protected void readDataHeader() throws IOException
    {
        RandomAccessFile raf = lodFile.getRandomAccessFileInputStream().getFile();
        
        raf.seek(getDataHeaderOffset());
        int totalBytesRead = 0;
        while (totalBytesRead < dataHeader.length)
        {
            int bytesRead = raf.read(dataHeader, totalBytesRead, (dataHeader.length - totalBytesRead));
            if (-1 == bytesRead)
            {
                throw new EOFException("Read " + String.valueOf(totalBytesRead) + " bytes but expected " + String.valueOf(dataHeader) + " bytes for dataHeader.");
            }
                    
            totalBytesRead += bytesRead;
        }
    }

    protected byte[] readRawData() throws IOException
    {
        int dataLength = this.getDataLength();
        byte[] data = new byte[dataLength];

        RandomAccessFile raf = lodFile.getRandomAccessFileInputStream().getFile();
        
        long dataOffset = this.getDataOffset();
        raf.seek(dataOffset);
        int totalBytesRead = 0;
        while (totalBytesRead < data.length)
        {
            int bytesRead = raf.read(data, totalBytesRead, (data.length - totalBytesRead));
            if (-1 == bytesRead)
            {
                throw new EOFException("Read only " + String.valueOf(totalBytesRead) + " bytes but expected " + String.valueOf(data) + " bytes for data.");
            }
                    
            totalBytesRead += bytesRead;
        }
        
        return data;
    }

    abstract protected void computeEntryBasedOffsets();
    abstract protected void computeDataBasedOffsets();
    abstract protected int getDataLength(); 
    abstract protected long getDataHeaderOffset();
   
    public FormatConverter getFormatConverter()
    {
        return new NullFormatConverter();
    }

    public String getFormatConverterFileType()
    {
        return null;
    }


    public int compareTo(Object obj) {
       return (getDataOffset() < ((LodEntry) obj).getDataOffset()) ? -1 : 1;
    }


    // for sorting
    abstract public String getEntryName();    
    abstract public String getDataName();
    abstract public long getDataOffset();

    // for sorting
    public long getEntryOffset()
    {
        return entryOffset;
    }
    
    // for sorting
    public String getName()
    {
        return getEntryName();
    }
    
    // used by LodCompare program
    public byte[] getEntryHeader()
    {
        return entryHeader;
    }

    // used by LodCompare program
    public byte[] getDataHeader()
    {
        return dataHeader;
    }

    abstract public String getFileName();
    
    /**
     * @param outputStream
     * @param entryOffset to start writing the entry
     * @param dataOffset to start writing the data for this entry
     * @return dataOffset for next entry
     */
    abstract public long writeNewEntry(LodResource lodResourceDataSource, OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException;
    abstract public long updateEntry(LodResource lodResourceDataSource, OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException;
    abstract public long rewriteEntry(OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException;

     /**
     * @param outputStream
     * @param dataOffset to start writing this datum
     * @return dataOffset for next datum
     */
    abstract public long writeNewData(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset) throws IOException;
    abstract public long updateData(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset) throws IOException;
    abstract public long rewriteData(OutputStream outputStream, long dataOffset) throws IOException;
}