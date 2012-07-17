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
import org.gamenet.application.mm8leveleditor.converter.TGAToBMPFormatConverter;
import org.gamenet.application.mm8leveleditor.lod.LodEntry;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.TGADataProducer;
import org.gamenet.util.ByteConversions;

import com.mmbreakfast.unlod.lod.LodFile;
import com.mmbreakfast.unlod.lod.Extractor;
import com.mmbreakfast.unlod.lod.PassThroughLodFileExtractor;

public class MM6LodEntry extends LodEntry implements TGADataProducer
{
    protected Extractor thePassThroughLodFileExtractor = new PassThroughLodFileExtractor();
    protected Extractor theLodFileExtractor = new Extractor();

    
//  private static final int UNKNOWN_DECOMPRESSED_DATA_LENGTH_OFFSET = 16; // 4 bytes
//  private static final int UNKNOWN1_OFFSET = 20; // ? bytes
//  private static final int UNKNOWN2_OFFSET = 44; // 4 bytes

    // entry header
    protected static final int ENTRY_NAME__ENTRY_HEADER_OFFSET = 0x00;
    protected static final int ENTRY_NAME__ENTRY_HEADER_MAX_LENGTH = 0x10;
    protected static final int DATA_SEGMENT_OFFSET__ENTRY_HEADER_OFFSET = 0x10;
    protected static final int DATA_SEGMENT_LENGTH__ENTRY_HEADER_OFFSET = 0x14; // really data_header + data_context length, includes palette for tga
    
    // data header
    protected static final int DATA_HEADER_LENGTH = 0x30;
    protected static final int DATA_NAME__DATA_HEADER_OFFSET = 0x00;
    protected static final int DATA_NAME__DATA_HEADER_MAX_LENGTH = 0x10;
    protected static final int TGA_IMAGE_SIZE__DATA_HEADER_OFFSET = 0x10; // 4 bytes
    protected static final int DATA_CONTENT_COMPRESSED_SIZE__DATA_HEADER_OFFSET = 0x14;
    protected static final int TGA_BYTE_WIDTH__DATA_HEADER_OFFSET = 0x18; // 2 bytes
    protected static final int TGA_BYTE_HEIGHT__DATA_HEADER_OFFSET = 0x1a; // 2 bytes
    protected static final int DATA_CONTENT_UNCOMPRESSED_SIZE__DATA_HEADER_OFFSET = 0x28;

    protected Extractor theFileExtractor = new Extractor();

    public MM6LodEntry(LodFile lodFile, long headerOffset)
   		throws IOException
   	{
    	super(lodFile, headerOffset);
    }

   public String getTextDescription()
    {
        String fileType = getFileType();
        if (fileType.equals("tga"))
        {
            fileType = fileType 
            + "\n"
            + "Width: "
            + getByteWidth()
            + "\n"
            + "Height: "
            + getByteHeight()
            + "\n"
            + "PaletteOffset: "
            + getPaletteOffset();
        }
        
        return "Name: "
            + getName()
            + "\n"
            + "EntryName: "
            + getEntryName()
            + "\n"
            + "DataName: "
            + getDataName()
            + "\n"
            + "DataType: "
            + getDataType()
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

    public int getByteWidth()
    {
        return ByteConversions.getShortInByteArrayAtPosition(dataHeader, getOffsetInDataHeaderForTGAByteWidth());
    }

    public int getByteHeight()
    {
        return ByteConversions.getShortInByteArrayAtPosition(dataHeader, getOffsetInDataHeaderForTGAByteHeight());
    }

    public int[] getPalette() throws IOException
    {
        int paletteArray[] = new int[768];

        RandomAccessFile raf =
            this
                .getLodFile()
                .getRandomAccessFileInputStream()
                .getFile();
        raf.seek(getPaletteOffset());
        for (int index = 0; index < 768; ++index)
        {
            paletteArray[index] = raf.read();
            if (-1 == paletteArray[index])  throw new RuntimeException("Unexpected end of file found at index=" + String.valueOf(index));
        }

        return paletteArray;
    }

    protected void computeEntryBasedOffsets()
    {
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
        if (getFileType().equals("tga"))
        {
            int sourcePalette[] = null;
            try
            {
                sourcePalette = getPalette();
            }
            catch (IOException exception)
            {
                // TODO Auto-generated catch block
                exception.printStackTrace();
            }
            if (null != sourcePalette)
            {
                length -= sourcePalette.length;
            }
        }

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

    protected long getPaletteOffset()
    {
        int compressedSize = ByteConversions.getIntegerInByteArrayAtPosition(dataHeader,
            (int)this.getOffsetInDataHeaderFortDataContentCompressedLength());
        
        return getDataOffset() + compressedSize;
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
        return DATA_HEADER_LENGTH;
    }

    protected int getOffsetInDataHeaderForDataName()
    {
        return DATA_NAME__DATA_HEADER_OFFSET;
    }
    
    protected int getDataNameMaxLength()
    {
        return DATA_NAME__DATA_HEADER_MAX_LENGTH;
    }

    protected int getOffsetInDataHeaderForTGAImageSize()
    {
        return TGA_IMAGE_SIZE__DATA_HEADER_OFFSET;
    }

    protected long getOffsetInDataHeaderFortDataContentCompressedLength()
    {
        return DATA_CONTENT_COMPRESSED_SIZE__DATA_HEADER_OFFSET;
    }
    
    protected long getOffsetInDataHeaderFortDataContentUncompressedLength()
    {
        return DATA_CONTENT_UNCOMPRESSED_SIZE__DATA_HEADER_OFFSET;
    }

    protected int getOffsetInDataHeaderForTGAByteWidth()
    {
        return TGA_BYTE_WIDTH__DATA_HEADER_OFFSET;
    }

    protected int getOffsetInDataHeaderForTGAByteHeight()
    {
        return TGA_BYTE_HEIGHT__DATA_HEADER_OFFSET;
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

		String alternateName = getDataName();
		if (null != alternateName)
		{
			fileType = alternateName.toLowerCase();
		}
		
		
		int dotPos = fileType.lastIndexOf('.');
		if (-1 == dotPos)  return "";
		if ((fileType.length() - 1) == dotPos)  return "";
		
		return fileType.substring(dotPos + 1).toLowerCase(); 
	}

    protected String getFileType()
    {
        // TGA files inconsistently named
        // Tga images (and only tga images) have a non-zero image size
        int imageSize = ByteConversions.getIntegerInByteArrayAtPosition(dataHeader, getOffsetInDataHeaderForTGAImageSize());
        if (0 != imageSize)  return "tga";
        
      String dataType = this.getDataType();
      if (null == dataType)  dataType = "";
      else dataType = dataType.toLowerCase();
      
        String fileType = getEntryName().toLowerCase();

        int dotPos = fileType.lastIndexOf('.');
        if (-1 == dotPos)  return dataType;
        if ((fileType.length() - 1) == dotPos)  return dataType;
        
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
        int offset = getOffsetInDataHeaderForDataName();
        int maxLength = getDataNameMaxLength();
        int length = 0;
        
        while ( (0 != dataHeader[offset + length]) && (length < maxLength) )
            length++;
                
        return new String(dataHeader, offset, length);
    }

    protected String getDataType()
    {
        int offset = getOffsetInDataHeaderForDataName();
        int maxLength = getDataNameMaxLength();
        int nameLength = 0;
        
        // skip first 0
        while ( (0 != dataHeader[offset + nameLength]) && (nameLength < maxLength) )
        nameLength++;
                
        if (nameLength >= maxLength)  return null;

        int typeLength = nameLength + 1;
                
        while ( (0 != dataHeader[offset + typeLength]) && (typeLength < maxLength) )
            typeLength++;
                
        if (typeLength > maxLength)  return null;

        if ((offset + nameLength + 1) == typeLength)  return null;
        
        return new String(dataHeader, offset + nameLength + 1, typeLength - (offset + nameLength + 1));
    }

    protected long getDecompressedSize()
    {
        int index = (int)getOffsetInDataHeaderFortDataContentUncompressedLength();
        if (-1 == index)   return -1;
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
        if (getFileType().equals("tga"))
            return new TGAToBMPFormatConverter();
            // return super.getFormatConverter();
        else if (getFileType().equals("str"))
            return new StrToTextFormatConverter();
        else return super.getFormatConverter();
    }

    public String getFormatConverterFileType()
    {
        if (getFileType().equals("tga"))
            return "bmp";
        else return super.getFormatConverterFileType();
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

        String fileType = getFileType();
        if (fileType.equals("tga"))
        {
            int sourcePalette[] = getPalette();

            dataLength += sourcePalette.length;
        }

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
        byte originalDataHeader[] = this.getDataHeader();

        byte newDataHeader[] = new byte[originalDataHeader.length];
        System.arraycopy(
            originalDataHeader,
            0,
            newDataHeader,
            0,
            originalDataHeader.length);

        Arrays.fill(newDataHeader, 0, getDataNameMaxLength(), (byte)0);
        byte nameBytes[] = this.getDataName().getBytes();
        System.arraycopy(nameBytes, 0, newDataHeader, 0, nameBytes.length);

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
        
        // zero image size
        ByteConversions.setIntegerInByteArrayAtPosition(0, newDataHeader, getOffsetInDataHeaderForTGAImageSize());

        if (lodResourceDataSource instanceof TGADataProducer)
        {
            TGADataProducer tgaDataProducer = (TGADataProducer)lodResourceDataSource;
            int sourcePalette[] = tgaDataProducer.getPalette();

            if (null != sourcePalette)
            {
                byte[] tgaBytes = "TGA".getBytes();
                System.arraycopy(tgaBytes, 0, newDataHeader, nameBytes.length + 1, tgaBytes.length);

                int sourceWidth = tgaDataProducer.getByteWidth();
                int sourceHeight = tgaDataProducer.getByteHeight();
                
                // add width
                ByteConversions.setShortInByteArrayAtPosition((short)sourceWidth, newDataHeader, getOffsetInDataHeaderForTGAByteWidth());
                
                // add height
                ByteConversions.setShortInByteArrayAtPosition((short)sourceHeight, newDataHeader, getOffsetInDataHeaderForTGAByteHeight());

                // add image size
                ByteConversions.setIntegerInByteArrayAtPosition(sourceWidth * sourceHeight, newDataHeader, getOffsetInDataHeaderForTGAImageSize());
            }
        }
        
        outputStream.write(newDataHeader);

        return newDataOffset;
    }
 
    protected long updateDataHeader(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset, long compressedDataLength, long uncompressedDataLength)
    throws IOException
{
    byte originalDataHeader[] = this.getDataHeader();

    byte newDataHeader[] = new byte[originalDataHeader.length];
    System.arraycopy(
        originalDataHeader,
        0,
        newDataHeader,
        0,
        originalDataHeader.length);

//    Arrays.fill(newDataHeader, 0, getDataNameMaxLength(), (byte)0);
//    byte nameBytes[] = this.getDataName().getBytes();
//    System.arraycopy(nameBytes, 0, newDataHeader, 0, nameBytes.length);

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
    
    // zero image size
    ByteConversions.setIntegerInByteArrayAtPosition(0, newDataHeader, getOffsetInDataHeaderForTGAImageSize());

    if (lodResourceDataSource instanceof TGADataProducer)
    {
        TGADataProducer tgaDataProducer = (TGADataProducer)lodResourceDataSource;
        int sourcePalette[] = tgaDataProducer.getPalette();

        if (null != sourcePalette)
        {
//            byte[] tgaBytes = "TGA".getBytes();
//            System.arraycopy(tgaBytes, 0, newDataHeader, nameBytes.length + 1, tgaBytes.length);

            int sourceWidth = tgaDataProducer.getByteWidth();
            int sourceHeight = tgaDataProducer.getByteHeight();
            
            // add width
            ByteConversions.setShortInByteArrayAtPosition((short)sourceWidth, newDataHeader, getOffsetInDataHeaderForTGAByteWidth());
            
            // add height
            ByteConversions.setShortInByteArrayAtPosition((short)sourceHeight, newDataHeader, getOffsetInDataHeaderForTGAByteHeight());

            // add image size
            ByteConversions.setIntegerInByteArrayAtPosition(sourceWidth * sourceHeight, newDataHeader, getOffsetInDataHeaderForTGAImageSize());
        }
    }
    
    outputStream.write(newDataHeader);

    return newDataOffset;
}

 public long writeNewData(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset)
     throws IOException
 {
     byte uncompressedData[] = lodResourceDataSource.getData();
//   System.out.println(getName() + "- DS data size: " + uncompressedData.length);
     
     byte compressedData[] = theLodFileExtractor.compress(uncompressedData);

     byte smallestData[] = null;
     int uncompressedDataLengthSpecifier;
    
     if (uncompressedData.length > compressedData.length)
     {
         smallestData = compressedData;
         uncompressedDataLengthSpecifier = uncompressedData.length;
     }
     else
     {
         smallestData = uncompressedData;
         uncompressedDataLengthSpecifier = 0;
     }

     if ("pal".equals(this.getDataType()))
     {
         smallestData = uncompressedData;
         uncompressedDataLengthSpecifier = 0;
     }
         
//   System.out.println(getName() + "- rawdataLength: " + smallestData.length);
     
     long newDataOffset = writeNewDataHeader(lodResourceDataSource, outputStream, dataOffset, smallestData.length, uncompressedDataLengthSpecifier);
    
     outputStream.write(smallestData);

     long newOffset = newDataOffset + smallestData.length;

     if (lodResourceDataSource instanceof TGADataProducer)
     {
         TGADataProducer tgaDataProducer = (TGADataProducer)lodResourceDataSource;
         int sourcePalette[] = tgaDataProducer.getPalette();

         if (null != sourcePalette)
         {
             for (int i = 0 ; i < sourcePalette.length ; i++) {
                 outputStream.write(sourcePalette[i]);
             }
       
             newOffset += sourcePalette.length;

//           System.out.println("palette: " + sourcePalette.length);
         }
     }

//   System.out.println(getName() + " - writeData newOffset: " + newOffset);

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
        
         if (uncompressedData.length > compressedData.length)
         {
             smallestData = compressedData;
             uncompressedDataLengthSpecifier = uncompressedData.length;
         }
         else
         {
             smallestData = uncompressedData;
             uncompressedDataLengthSpecifier = 0;
         }

         if ("pal".equals(this.getDataType()))
         {
             smallestData = uncompressedData;
             uncompressedDataLengthSpecifier = 0;
         }
             
//       System.out.println(getName() + "- rawdataLength: " + smallestData.length);
         
         long newDataOffset = updateDataHeader(lodResourceDataSource, outputStream, dataOffset, smallestData.length, uncompressedDataLengthSpecifier);
        
         outputStream.write(smallestData);

         long newOffset = newDataOffset + smallestData.length;

         if (lodResourceDataSource instanceof TGADataProducer)
         {
             TGADataProducer tgaDataProducer = (TGADataProducer)lodResourceDataSource;
             int sourcePalette[] = tgaDataProducer.getPalette();

             if (null != sourcePalette)
             {
                 for (int i = 0 ; i < sourcePalette.length ; i++) {
                     outputStream.write(sourcePalette[i]);
                 }
           
                 newOffset += sourcePalette.length;

//               System.out.println("palette: " + sourcePalette.length);
             }
         }

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

        String fileType = getFileType();
        if (fileType.equals("tga"))
        {
            int sourcePalette[] = getPalette();

            if (null != sourcePalette)
            {
                for (int i = 0; i < sourcePalette.length; i++)
                {
                    outputStream.write(sourcePalette[i]);
                }

                newOffset += sourcePalette.length;

//              System.out.println("palette: " + sourcePalette.length);
            }
        }

//      System.out.println(getName() + " - rewriteData newOffset: " + newOffset);

        return newOffset;
    }
}
