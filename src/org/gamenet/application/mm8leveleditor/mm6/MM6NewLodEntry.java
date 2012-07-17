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
import java.io.RandomAccessFile;
import java.util.Arrays;

import org.gamenet.application.mm8leveleditor.converter.FormatConverter;
import org.gamenet.application.mm8leveleditor.converter.StrToTextFormatConverter;
import org.gamenet.application.mm8leveleditor.lod.LodEntry;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.util.ByteConversions;

import com.mmbreakfast.unlod.lod.LodFile;
import com.mmbreakfast.unlod.lod.Extractor;
import com.mmbreakfast.unlod.lod.PassThroughLodFileExtractor;

public class MM6NewLodEntry extends LodEntry
{
    protected Extractor thePassThroughLodFileExtractor = new PassThroughLodFileExtractor();
    protected Extractor theLodFileExtractor = new Extractor();

    
    // entry header
    protected static final int ENTRY_NAME__ENTRY_HEADER_OFFSET = 0x00;
    protected static final int ENTRY_NAME__ENTRY_HEADER_MAX_LENGTH = 0x0c;
    protected static final int DATA_SEGMENT_OFFSET__ENTRY_HEADER_OFFSET = 0x10;
    protected static final int DATA_SEGMENT_LENGTH__ENTRY_HEADER_OFFSET = 0x14; // really data_header + data_context length, includes palette for tga
    
    // data header
	protected static final int COMPRESSION_HEADER[] = { 120, 156 };
	
    protected static final int MM6_DATA_HEADER_LENGTH_UNCOMPRESSED = 0x00;  // if not compressed

    protected static final int MM6_DATA_HEADER_LENGTH_COMPRESSED = 0x08; // if compressed
    protected static final int MM6_DATA_CONTENT_COMPRESSED_SIZE__DATA_HEADER_OFFSET = 0x00;
    protected static final int MM6_DATA_CONTENT_UNCOMPRESSED_SIZE__DATA_HEADER_OFFSET = 0x04;

    protected Extractor theFileExtractor = new Extractor();

    protected Boolean isCompressed;
    
    public MM6NewLodEntry(LodFile lodFile, long headerOffset)
   		throws IOException
   	{
    	super(lodFile, headerOffset);
    }

    public String getTextDescription()
    {
        String fileType = getFileType();
        
        return "Name: "
            + getName()
            + "\n"
            + "EntryName: "
            + getEntryName()
            + "\n"
            + "DataName: "
            + getDataName()
            + "\n"
            + "FileType: "
            + fileType
            + "\n"
            + "Data Length: "
            + getDataLength()
            + "\n"
            + "Decompressed Size: "
            + getDecompressedSize()
            + "\n"
            + "Data Header Offset: "
            + getDataHeaderOffset()
            + "\n"
            + "Data Offset: "
            + getDataOffset()
            ;
    }

    protected boolean isCompressed()
    {
        return isCompressed.booleanValue();
    }
    
    protected void computeEntryBasedOffsets()
    {
        RandomAccessFile raf = lodFile.getRandomAccessFileInputStream().getFile();

        long possibleCompressionHeaderOffset = this.getDataHeaderOffset() + MM6_DATA_HEADER_LENGTH_COMPRESSED;

        try
        {
            raf.seek(possibleCompressionHeaderOffset);

            boolean matchedSignature = true;
            for (int i = 0; i < COMPRESSION_HEADER.length; i++)
            {
                int aByte = raf.read();
                if (aByte != COMPRESSION_HEADER[i])
                {
                    isCompressed = Boolean.FALSE;
                }
            }
            if (null == isCompressed)  isCompressed = Boolean.TRUE;
        }
        catch (IOException exception)
        {
            isCompressed = Boolean.FALSE;
        }

        dataHeader = new byte[getDataHeaderLength()];
    }

    protected void computeDataBasedOffsets()
    {
    }

    public long getDataOffset()
    {
        return getDataHeaderOffset() + getDataHeaderLength();
    }

    protected int getDataLength()
    {
        int length = ByteConversions.getIntegerInByteArrayAtPosition(entryHeader, DATA_SEGMENT_LENGTH__ENTRY_HEADER_OFFSET) - getDataHeaderLength();
        return length;
    }

    protected long getDataHeaderOffset()
    {
        return getLodFile().getHeaderOffset() + ByteConversions.getIntegerInByteArrayAtPosition(entryHeader, getOffsetInEntryHeaderForDataSegmentOffset());
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
        return DATA_SEGMENT_LENGTH__ENTRY_HEADER_OFFSET;
    }

    protected int getDataHeaderLength()
    {
        if (isCompressed())
            return MM6_DATA_HEADER_LENGTH_COMPRESSED;
        else return MM6_DATA_HEADER_LENGTH_UNCOMPRESSED;
    }

    protected long getOffsetInDataHeaderFortDataContentCompressedLength()
    {
        if (isCompressed())
            return MM6_DATA_CONTENT_COMPRESSED_SIZE__DATA_HEADER_OFFSET;
        else return -1;
    }
    
    protected long getOffsetInDataHeaderFortDataContentUncompressedLength()
    {
        if (isCompressed())
            return MM6_DATA_CONTENT_UNCOMPRESSED_SIZE__DATA_HEADER_OFFSET;
        else return -1;
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

    protected String getFileTypex()
	{
		String fileType = getEntryName().toLowerCase();

		int dotPos = fileType.lastIndexOf('.');
		if (-1 == dotPos)  return "";
		if ((fileType.length() - 1) == dotPos)  return "";
		
		return fileType.substring(dotPos + 1).toLowerCase(); 
	}

    protected String getFileType()
    {
        String fileType = getEntryName().toLowerCase();

        int dotPos = fileType.lastIndexOf('.');
        if (-1 == dotPos)  return "";
        if ((fileType.length() - 1) == dotPos)  return "";
        
        return fileType.substring(dotPos + 1).toLowerCase(); 
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

    public String getDataName()
    {
        return null;
    }

    protected long getDecompressedSize()
    {
        int index = (int)getOffsetInDataHeaderFortDataContentUncompressedLength();
        if (-1 == index)   return 0;
        return ByteConversions.getIntegerInByteArrayAtPosition(dataHeader, index);
    }

    protected Extractor getExtractor()
    {
        if (0 == getDecompressedSize())
            return thePassThroughLodFileExtractor;
        else
            return theLodFileExtractor;
    }

    public FormatConverter getFormatConverter()
    {
        if (getFileType().equals("str"))
            return new StrToTextFormatConverter();
        else return super.getFormatConverter();
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

    protected int getNewWrittenDataContentLength(LodResource lodResourceDataSource)
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

    protected int getUpdatedWrittenDataContentLength(LodResource lodResourceDataSource)
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

    private long writeEntry(String name, long writtenDataLength, OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
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
        ByteConversions.setIntegerInByteArrayAtPosition((dataOffset - entryListOffset), newEntryHeader, getOffsetInEntryHeaderForDataSegmentOffset());
//      System.out.println(getName() + "- storing " + String.valueOf(dataOffset - entryListOffset) + " in entry.dataoffset");

//      System.out.println(getName() + "- datacontentlength: " + writtenDataLength);

        ByteConversions.setIntegerInByteArrayAtPosition(writtenDataLength, newEntryHeader, (int)this.getOffsetInEntryHeaderForDataSegmentLength());
//      System.out.println(getName() + "- storing " + String.valueOf(writtenDataLength) + " in entry.datalength");

        outputStream.write(newEntryHeader);
       
        return dataOffset + writtenDataLength;
    }

    public long rewriteEntry(OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
    {
        byte[] rawData = readRawData();
        long dataLength = getDataHeaderLength() + rawData.length;

        long newDataOffset = writeEntry(null, dataLength, outputStream, entryListOffset, entryOffset, dataOffset);
        
        return newDataOffset;
    }

    public long writeNewEntry(LodResource lodResourceDataSource, OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
    {
        return writeEntry(this.getEntryName(), getNewWrittenDataContentLength(lodResourceDataSource), outputStream, entryListOffset, entryOffset, dataOffset);
    }

    public long updateEntry(LodResource lodResourceDataSource, OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
    {
        return writeEntry(null, getUpdatedWrittenDataContentLength(lodResourceDataSource), outputStream, entryListOffset, entryOffset, dataOffset);
    }

    /**
    * @param outputStream
    * @param dataOffset
    * @return
    */
    protected long writeNewDataHeader(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset, long compressedDataLength, long uncompressedDataLength)
        throws IOException
    {
        if (false == isCompressed())  return dataOffset;
        
        byte originalDataHeader[] = this.getDataHeader();

        byte newDataHeader[] = new byte[originalDataHeader.length];
        System.arraycopy(
            originalDataHeader,
            0,
            newDataHeader,
            0,
            originalDataHeader.length);

        if (-1 != getOffsetInDataHeaderFortDataContentCompressedLength())
            ByteConversions.setIntegerInByteArrayAtPosition(
                 compressedDataLength,
                newDataHeader,
                (int)this.getOffsetInDataHeaderFortDataContentCompressedLength());
        
        // For uncompressed data, write 0 for uncompressed data length
        if (-1 != getOffsetInDataHeaderFortDataContentUncompressedLength())
            ByteConversions.setIntegerInByteArrayAtPosition(
                 uncompressedDataLength,
                newDataHeader,
                (int)this.getOffsetInDataHeaderFortDataContentUncompressedLength());

        long newDataOffset = dataOffset + newDataHeader.length;
        
        outputStream.write(newDataHeader);

        return newDataOffset;
    }
 
    protected long updateDataHeader(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset, long compressedDataLength, long uncompressedDataLength)
    throws IOException
    {
        if (false == isCompressed())  return dataOffset;
        
        byte originalDataHeader[] = this.getDataHeader();

        byte newDataHeader[] = new byte[originalDataHeader.length];
        System.arraycopy(
            originalDataHeader,
            0,
            newDataHeader,
            0,
            originalDataHeader.length);

        if (-1 != getOffsetInDataHeaderFortDataContentCompressedLength())
            ByteConversions.setIntegerInByteArrayAtPosition(
                 compressedDataLength,
                newDataHeader,
                (int)this.getOffsetInDataHeaderFortDataContentCompressedLength());
        
        // For uncompressed data, write 0 for uncompressed data length
        if (-1 != getOffsetInDataHeaderFortDataContentUncompressedLength())
            ByteConversions.setIntegerInByteArrayAtPosition(
                 uncompressedDataLength,
                newDataHeader,
                (int)this.getOffsetInDataHeaderFortDataContentUncompressedLength());

        long newDataOffset = dataOffset + newDataHeader.length;
        
        outputStream.write(newDataHeader);

        return newDataOffset;
    }

    public long writeNewData(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset)
    throws IOException
    {
        byte uncompressedData[] = lodResourceDataSource.getData();
//      System.out.println(getName() + "- DS data size: " + uncompressedData.length);
        
        byte compressedData[] = theLodFileExtractor.compress(uncompressedData);

        byte smallestData[] = null;
        int uncompressedDataLengthSpecifier;
       
        if (isCompressed())
        {
            smallestData = compressedData;
            uncompressedDataLengthSpecifier = uncompressedData.length;
        }
        else
        {
            smallestData = uncompressedData;
            uncompressedDataLengthSpecifier = 0;
        }

//      System.out.println(getName() + "- rawdataLength: " + smallestData.length);
        
        long newDataOffset = writeNewDataHeader(lodResourceDataSource, outputStream, dataOffset, smallestData.length, uncompressedDataLengthSpecifier);
       
        outputStream.write(smallestData);

        long newOffset = newDataOffset + smallestData.length;

//      System.out.println(getName() + " - writeData newOffset: " + newOffset);

        return newOffset;
    }

     public long updateData(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset)
         throws IOException
     {
         byte uncompressedData[] = lodResourceDataSource.getData();
//       System.out.println(getName() + "- DS data size: " + uncompressedData.length);
         
         byte compressedData[] = theLodFileExtractor.compress(uncompressedData);

         byte smallestData[] = null;
         int uncompressedDataLengthSpecifier;
        
         if (isCompressed())
         {
             smallestData = compressedData;
             uncompressedDataLengthSpecifier = uncompressedData.length;
         }
         else
         {
             smallestData = uncompressedData;
             uncompressedDataLengthSpecifier = 0;
         }

//       System.out.println(getName() + "- rawdataLength: " + smallestData.length);
         
         long newDataOffset = updateDataHeader(lodResourceDataSource, outputStream, dataOffset, smallestData.length, uncompressedDataLengthSpecifier);
        
         outputStream.write(smallestData);

         long newOffset = newDataOffset + smallestData.length;

//       System.out.println(getName() + " - writeData newOffset: " + newOffset);

         return newOffset;
     }

     public long rewriteData(OutputStream outputStream, long dataOffset)
            throws IOException
    {
         outputStream.write(getDataHeader());

         byte[] rawData = readRawData();
         outputStream.write(rawData);

         long newOffset = dataOffset + getDataHeaderLength() + rawData.length;

//       System.out.println(getName() + " - rewriteData newOffset: " + newOffset);

         return newOffset;
    }
}
