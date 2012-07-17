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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.gamenet.application.mm8leveleditor.converter.FormatConverter;
import org.gamenet.application.mm8leveleditor.lod.LodEntry;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.util.ByteConversions;

import com.mmbreakfast.unlod.lod.LodFile;
import com.mmbreakfast.unlod.lod.Extractor;

public class MM6SndEntry extends LodEntry
{
    protected Extractor theLodFileExtractor = new Extractor();

    // entry header
    protected static final int ENTRY_NAME__ENTRY_HEADER_OFFSET = 0x00;
    protected static final int ENTRY_NAME__ENTRY_HEADER_MAX_LENGTH = 0x28;
    protected static final int DATA_SEGMENT_OFFSET__ENTRY_HEADER_OFFSET = 0x28;
    protected static final int DATA_CONTENT_COMPRESSED_SIZE__ENTRY_HEADER_OFFSET = 0x2C;
    protected static final int DATA_CONTENT_UNCOMPRESSED_SIZE__ENTRY_HEADER_OFFSET = 0x30;

    // data header
    protected static final int DATA_HEADER_LENGTH = 0x0;
    
    protected Extractor theFileExtractor = new Extractor();

    public MM6SndEntry(LodFile lodFile, long headerOffset)
   		throws IOException
   	{
    	super(lodFile, headerOffset);
    }

   public String getTextDescription()
    {
        return "Name: "
            + getName()
            + "\n"
            + "EntryName: "
            + getEntryName()
            + "\n"
            + "FileType: "
            + getFileType()
            + "\n"
            + "Data Length: "
            + getDataLength()
            + "\n"
            + "Decompressed Size: "
            + getDecompressedSize()
            + "\n"
            + "Data Offset: "
            + getDataOffset()
            ;
    }

    protected void computeEntryBasedOffsets()
    {
    }

    protected void computeDataBasedOffsets()
    {
    }

    public long getDataOffset()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(entryHeader, getOffsetInEntryHeaderForDataSegmentOffset());
    }

    protected int getDataLength()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(entryHeader, DATA_CONTENT_COMPRESSED_SIZE__ENTRY_HEADER_OFFSET);
    }

    protected void readDataHeader() throws IOException
    {
    }

    public byte[] getData() throws IOException
    {
        byte[] rawData = readRawData();

        if (0 == getDecompressedSize())
            return rawData;
        else        
        {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            theLodFileExtractor.decompress(null, rawData, byteStream, null, true);
            return byteStream.toByteArray();
        }
    }

    public String getResourceType()
    {
        return getFileType();
    }

    protected int getOffsetInEntryHeaderForDataSegmentOffset()
    {
        return DATA_SEGMENT_OFFSET__ENTRY_HEADER_OFFSET;
    }

    protected int getOffsetInEntryHeaderForDataSegmentLength()
    {
        return DATA_CONTENT_COMPRESSED_SIZE__ENTRY_HEADER_OFFSET;
    }

    protected long getOffsetInEntryHeaderFortDataContentCompressedLength()
    {
        return DATA_CONTENT_COMPRESSED_SIZE__ENTRY_HEADER_OFFSET;
    }
    
    protected long getOffsetInEntryHeaderFortDataContentUncompressedLength()
    {
        return DATA_CONTENT_UNCOMPRESSED_SIZE__ENTRY_HEADER_OFFSET;
    }

    public String getFileName()
    {
        String baseName = getEntryName();
        String fileName = null;
        if (baseName.toLowerCase().endsWith("." + getFileType().toLowerCase()))
        {
            fileName = baseName;
        }
        else
        {
            fileName = baseName + "." + getFileType();
        }

        String converterFileType = getFormatConverterFileType();
        if (null != converterFileType)
        {
            if (false == fileName.toLowerCase().endsWith("." + converterFileType.toLowerCase()))
            {
                fileName = fileName + "." + converterFileType;
            }
        }
        
        return fileName;
    }

    protected String getFileType()
    {
        return "wav"; 
    }

    public String getEntryName()
    {
        int offset = ENTRY_NAME__ENTRY_HEADER_OFFSET;
        int maxLength = ENTRY_NAME__ENTRY_HEADER_MAX_LENGTH;
        int length = 0;
        
        while ( (0 != entryHeader[offset + length]) && (length < maxLength) )
            length++;
                
        return new String(entryHeader, offset, length);
    }

    protected long getDecompressedSize()
    {
        int index = (int)getOffsetInEntryHeaderFortDataContentUncompressedLength();
        if (-1 == index)   return -1;
        return ByteConversions.getIntegerInByteArrayAtPosition(entryHeader, index);
    }

    public FormatConverter getFormatConverter()
    {
        return super.getFormatConverter();
    }

    public String getFormatConverterFileType()
    {
        return super.getFormatConverterFileType();
    }


    protected class NullOutputStream extends OutputStream
    {
        public void write(int b) throws IOException
        {
        }
    }

    protected int getNewCompressedDataLength(LodResource lodResourceDataSource)
    {
        try
        {
            return (int)writeNewData(lodResourceDataSource, new NullOutputStream(), 0);
        }
        catch (IOException exception)
        {
            // Doesn't throw exceptions
            exception.printStackTrace();
            return -1;
        }
    }

    protected int getUpdatedCompressedDataLength(LodResource lodResourceDataSource)
    {
        try
        {
            return (int)updateData(lodResourceDataSource, new NullOutputStream(), 0);
        }
        catch (IOException exception)
        {
            // Doesn't throw exceptions
            exception.printStackTrace();
            return -1;
        }
    }

    protected int getNewUncompressedDataLength(LodResource lodResourceDataSource) throws IOException
    {
        try
        {
            return lodResourceDataSource.getData().length;
        }
        catch (IOException exception)
        {
            // Doesn't throw exceptions
            exception.printStackTrace();
            return -1;
        }
    }

    protected int getUpdatedUncompressedDataLength(LodResource lodResourceDataSource) throws IOException
    {
        try
        {
            return lodResourceDataSource.getData().length;
        }
        catch (IOException exception)
        {
            // Doesn't throw exceptions
            exception.printStackTrace();
            return -1;
        }
    }

    private long writeEntry(String name, long compressedDataLength, long uncompressedDataLength, OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
    {
        byte originalEntryHeader[] = getEntryHeader();

        byte newEntryHeader[] = new byte[originalEntryHeader.length];
        System.arraycopy(originalEntryHeader, 0, newEntryHeader, 0, originalEntryHeader.length);

        if (null != name)
        {
            // update name
            Arrays.fill(newEntryHeader, 0, ENTRY_NAME__ENTRY_HEADER_MAX_LENGTH, (byte)0);
            byte[] entryNameBytes = name.getBytes();
            System.arraycopy(entryNameBytes, 0, newEntryHeader, 0, Math.min(ENTRY_NAME__ENTRY_HEADER_MAX_LENGTH, entryNameBytes.length));
            if (entryNameBytes.length < ENTRY_NAME__ENTRY_HEADER_MAX_LENGTH) newEntryHeader[entryNameBytes.length] = 0;
        }
       
        // update data offset
        ByteConversions.setIntegerInByteArrayAtPosition(dataOffset, newEntryHeader, getOffsetInEntryHeaderForDataSegmentOffset());
//      System.out.println(getName() + "- storing " + String.valueOf(dataOffset) + " in entry.dataoffset");

//      System.out.println(getName() + "- datacontentlength: " + compressedDataLength);
        // update data compressed length
        ByteConversions.setIntegerInByteArrayAtPosition(compressedDataLength, newEntryHeader, (int)this.getOffsetInEntryHeaderFortDataContentCompressedLength());
//      System.out.println(getName() + "- storing " + String.valueOf(compressedDataLength) + " in entry.compressedDataLength");

//      System.out.println(getName() + "- datacontentlength: " + uncompressedDataLength);
        // update data decompressed length
        ByteConversions.setIntegerInByteArrayAtPosition(uncompressedDataLength, newEntryHeader, (int)this.getOffsetInEntryHeaderFortDataContentUncompressedLength());
//      System.out.println(getName() + "- storing " + String.valueOf(uncompressedDataLength) + " in entry.uncompressedDataLength");

        outputStream.write(newEntryHeader);
       
        return dataOffset + compressedDataLength;
    }

    public long rewriteEntry(OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
    {
        byte[] rawData = readRawData();
        return writeEntry(null, rawData.length, getDecompressedSize(), outputStream, entryListOffset, entryOffset, dataOffset);
    }

    public long writeNewEntry(LodResource lodResourceDataSource, OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
    {
        return writeEntry(this.getEntryName(), getNewCompressedDataLength(lodResourceDataSource), getNewUncompressedDataLength(lodResourceDataSource), outputStream, entryListOffset, entryOffset, dataOffset);
    }

    public long updateEntry(LodResource lodResourceDataSource, OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
    {
        return writeEntry(null, getUpdatedCompressedDataLength(lodResourceDataSource), getNewUncompressedDataLength(lodResourceDataSource), outputStream, entryListOffset, entryOffset, dataOffset);
    }

    public long writeNewData(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset)
     throws IOException
    {
        byte uncompressedData[] = lodResourceDataSource.getData();
//      System.out.println(getName() + "- DS data size: " + uncompressedData.length);
        
        byte compressedData[] = theLodFileExtractor.compress(uncompressedData);

        int uncompressedDataLengthSpecifier;
       
//      System.out.println(getName() + "- rawdataLength: " + compressedData.length);
        
        outputStream.write(compressedData);

        long newOffset = dataOffset + compressedData.length;

//      System.out.println(getName() + " - nextoffset: " + newOffset);

        return newOffset;
    }

     public long updateData(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset)
         throws IOException
     {
         byte uncompressedData[] = lodResourceDataSource.getData();
//       System.out.println(getName() + "- DS data size: " + uncompressedData.length);
         
         byte compressedData[] = theLodFileExtractor.compress(uncompressedData);

//       System.out.println(getName() + "- rawdataLength: " + compressedData.length);
         
         outputStream.write(compressedData);

         long newOffset = dataOffset + compressedData.length;

//       System.out.println(getName() + " - nextoffset: " + newOffset);

         return newOffset;
     }

     public long rewriteData(OutputStream outputStream, long dataOffset)
            throws IOException
    {
         byte[] rawData = readRawData();
         outputStream.write(rawData);

        long newOffset = dataOffset + rawData.length;

//      System.out.println(getName() + " - nextoffset: " + newOffset);

        return newOffset;
    }

    protected long getDataHeaderOffset()
    {
        return 0;
    }

    public String getDataName()
    {
        return null;
    }
}
