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

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

import org.gamenet.application.mm8leveleditor.converter.FormatConverter;
import org.gamenet.application.mm8leveleditor.lod.LodEntry;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.util.ByteConversions;

import com.mmbreakfast.unlod.lod.LodFile;

public class MM6VidEntry extends LodEntry
{
    // entry header
    protected static final int ENTRY_NAME__ENTRY_HEADER_OFFSET = 0x00;
    protected static final int ENTRY_NAME__ENTRY_HEADER_MAX_LENGTH = 0x28;
    protected static final int DATA_SEGMENT_OFFSET__ENTRY_HEADER_OFFSET = 0x28; // 4 bytes

    private static final int WIDTH__DATA_HEADER_OFFSET = 0x04; // 4 bytes
    private static final int HEIGHT__DATA_HEADER_OFFSET = 0x08; // 4 bytes
    private static final int FRAMES__DATA_HEADER_OFFSET = 0x0C; // 4 bytes
    private static final int MAYBE_TOTAL_TIME_IN_32BIT_FLOAT__DATA_HEADER_OFFSET = 0x10;  // 4 bytes
    private static final int SOUND_SAMPLE_RATE__DATA_HEADER_OFFSET = 0x4C; // 2 bytes
    private static final int LARGEST_FRAME_SIZE__DATA_HEADER_OFFSET = 0x68; // 4 bytes

    protected long dataLength = 0;
    
    public MM6VidEntry(LodFile lodFile, long headerOffset)
   		throws IOException
   	{
    	super(lodFile, headerOffset);
    }


    protected void computeDataLength(RandomAccessFile raf, long entryIndex, long entryCount) throws IOException
    {
    	long pastLastEntry = (getLodFile().getEntryHeaderLength() * entryCount) + getLodFile().getHeaderOffset(); 
		
        long dataOffset = ByteConversions.getIntegerInByteArrayAtPosition(entryHeader, getOffsetInEntryHeaderForDataSegmentOffset());
  		long nextDataOffsetInEntryHeaderForDataSegmentOffset =  + getLodFile().getFileHeader().length + (entryIndex + 1) * getLodFile().getEntryHeaderLength() + getOffsetInEntryHeaderForDataSegmentOffset();

		long nextDataOffset = 0;
		if (nextDataOffsetInEntryHeaderForDataSegmentOffset < pastLastEntry)
		{
			raf.seek(nextDataOffsetInEntryHeaderForDataSegmentOffset);
			for (int longIndex = 0; longIndex < 4; longIndex++)
			nextDataOffset += raf.read() << 8 * longIndex;
		}
		else
		{
			nextDataOffset = raf.length();
		}
		
		dataLength = nextDataOffset - dataOffset;
    }

    protected int getOffsetInDataHeaderForWidth()
    {
        return WIDTH__DATA_HEADER_OFFSET;
    }
    protected int getOffsetInDataHeaderForHeight()
    {
        return HEIGHT__DATA_HEADER_OFFSET;
    }
    protected int getOffsetInDataHeaderForFrameCount()
    {
        return FRAMES__DATA_HEADER_OFFSET;
    }
    protected int getOffsetInDataHeaderForTotalTime()
    {
        return MAYBE_TOTAL_TIME_IN_32BIT_FLOAT__DATA_HEADER_OFFSET;
    }
    protected int getOffsetInDataHeaderForSoundSampleRate()
    {
        return SOUND_SAMPLE_RATE__DATA_HEADER_OFFSET;
    }
    protected int getOffsetInDataHeaderForLargestFrameSize()
    {
        return LARGEST_FRAME_SIZE__DATA_HEADER_OFFSET;
    }

    protected long getWidth() throws IOException
    {
        int index = (int)getOffsetInDataHeaderForWidth();
        if (-1 == index)   return -1;
        return ByteConversions.getIntegerInByteArrayAtPosition(getData(), index);
    }
    
    protected long getHeight() throws IOException
    {
        int index = (int)getOffsetInDataHeaderForHeight();
        if (-1 == index)   return -1;
        return ByteConversions.getIntegerInByteArrayAtPosition(getData(), index);
    }
    
    protected long getFrameCount() throws IOException
    {
        int index = (int)getOffsetInDataHeaderForFrameCount();
        if (-1 == index)   return -1;
        return ByteConversions.getIntegerInByteArrayAtPosition(getData(), index);
    }
    
    protected float getTotalTime() throws IOException
    {
        int index = (int)getOffsetInDataHeaderForTotalTime();
        if (-1 == index)   return -1;
        return ByteConversions.getFloatInByteArrayAtPosition(getData(), index);
    }
    
    protected short getSoundSampleRate() throws IOException
    {
        int index = (int)getOffsetInDataHeaderForSoundSampleRate();
        if (-1 == index)   return -1;
        return ByteConversions.getShortInByteArrayAtPosition(getData(), index);
    }
    
    protected long getLargestFrameSize() throws IOException
    {
        int index = (int)getOffsetInDataHeaderForLargestFrameSize();
        if (-1 == index)   return -1;
        return ByteConversions.getIntegerInByteArrayAtPosition(getData(), index);
    }
    

    public String getTextDescription()
    {
        String dataDescription;
		try {
			dataDescription = "Width: "
			    + getWidth()
			    + "\n"
			    + "Height: "
			    + getHeight()
			    + "\n"
			    + "Frame Count: "
			    + getFrameCount()
			    + "\n"
			    + "Total Time: "
			    + getTotalTime()
			    + "\n"
			    + "Sound Sample Rate: "
			    + getSoundSampleRate()
			    + "\n"
			    + "Largest Frame Size: "
			    + getLargestFrameSize();
		} catch (IOException e) {
			dataDescription = "error reading data: " + e.getMessage();
		}

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
            + "Data Offset: "
            + getDataOffset()
            
            + "\n"
            
            + dataDescription
            ; 

//            Human Temple0.smk from MM7 Might.vid
//w 460
//h 344
//frames: 86
//color depth: 8
//alpha plane: no
//fps: 12.00
//ms p f: 83.33
//total time: 0 min, 7 secs
//total size: 766624
//av data rate: 106975
//av frame size: 8914
//largest frame size: 111856
//highest one second data rate: 87492
//highest one second data rate start frame: 2
//ring frame: y
//y-interaced: no
//y-doubled: no
//track 2: 22050 KHz, 16 bit, Mono sound
//
//
//4 bytes
//SMK2
//
//4 bytes
//width
//
//4 bytes
//height
//
//4 bytes: 
//frames
//
//4 bytes signed 32
//negative ms per frame?
//
//4 bytes boolean?  1
//
//4 bytes boolean? 0
//
//4 byte total time in 32 bit float format?
//
//5x4 zeros (4 32-bit zeros?)
//
//unknown 4 byte value?
//
//...
//
//offset x4c
//
//2 bytes khz sound
//
//offset x68
//largest frame size
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
		return (int)dataLength;
    }

    protected void readDataHeader() throws IOException
    {
    }

    public byte[] getData() throws IOException
    {
        return readRawData();
    }

    public String getResourceType()
    {
        return getFileType();
    }

    protected int getOffsetInEntryHeaderForDataSegmentOffset()
    {
        return DATA_SEGMENT_OFFSET__ENTRY_HEADER_OFFSET;
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
        if (getEntryName().toLowerCase().endsWith(".bik"))
            return "bik";
            
        if (getEntryName().toLowerCase().endsWith(".smk"))
            return "smk";
            
      	return "smk";
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

    protected int getNewDataLength(LodResource lodResourceDataSource) throws IOException
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

    protected int getUpdatedDataLength(LodResource lodResourceDataSource) throws IOException
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

    private long writeEntry(String name, long uncompressedDataLength, OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
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

        outputStream.write(newEntryHeader);
       
        return dataOffset + uncompressedDataLength;
    }

    public long rewriteEntry(OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
    {
        byte[] rawData = readRawData();
        return writeEntry(null, rawData.length, outputStream, entryListOffset, entryOffset, dataOffset);
    }

    public long writeNewEntry(LodResource lodResourceDataSource, OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
    {
        return writeEntry(this.getEntryName(), getNewDataLength(lodResourceDataSource), outputStream, entryListOffset, entryOffset, dataOffset);
    }

    public long updateEntry(LodResource lodResourceDataSource, OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
    {
        return writeEntry(null, getUpdatedDataLength(lodResourceDataSource), outputStream, entryListOffset, entryOffset, dataOffset);
    }

    public long writeNewData(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset)
     throws IOException
    {
        byte uncompressedData[] = lodResourceDataSource.getData();
//      System.out.println(getName() + "- DS data size: " + uncompressedData.length);
        
        outputStream.write(uncompressedData);

        long newOffset = dataOffset + uncompressedData.length;

//      System.out.println(getName() + " - nextoffset: " + newOffset);

        return newOffset;
    }

     public long updateData(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset)
         throws IOException
     {
         byte uncompressedData[] = lodResourceDataSource.getData();
//       System.out.println(getName() + "- DS data size: " + uncompressedData.length);
         
         outputStream.write(uncompressedData);

         long newOffset = dataOffset + uncompressedData.length;

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
