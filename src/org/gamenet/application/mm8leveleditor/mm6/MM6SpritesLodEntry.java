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
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Map;

import org.gamenet.application.mm8leveleditor.converter.FormatConverter;
import org.gamenet.application.mm8leveleditor.converter.TGAToBMPFormatConverter;
import org.gamenet.application.mm8leveleditor.lod.LodEntry;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.SpriteTGADataProducer;
import org.gamenet.application.mm8leveleditor.lod.TGADataProducer;
import org.gamenet.util.ByteConversions;

import com.mmbreakfast.unlod.lod.InvalidLodFileException;
import com.mmbreakfast.unlod.lod.LodFile;
import com.mmbreakfast.unlod.lod.Extractor;
import com.mmbreakfast.unlod.lod.NoSuchEntryException;
import com.mmbreakfast.unlod.lod.PassThroughLodFileExtractor;
import com.mmbreakfast.unlod.lod.RandomAccessFileInputStream;

public class MM6SpritesLodEntry extends LodEntry implements TGADataProducer, SpriteTGADataProducer
{
    protected Extractor thePassThroughLodFileExtractor = new PassThroughLodFileExtractor();
    protected Extractor theLodFileExtractor = new Extractor();

    // entry header
    protected static final int ENTRY_NAME__ENTRY_HEADER_OFFSET = 0x00;
    protected static final int ENTRY_NAME__ENTRY_HEADER_MAX_LENGTH = 0x0a;
    protected static final int DATA_SEGMENT_OFFSET__ENTRY_HEADER_OFFSET = 0x10; // add file header length to get full offset
    protected static final int DATA_SEGMENT_LENGTH__ENTRY_HEADER_OFFSET = 0x14; // data_header + data_context length
    
	protected static final int DATA_HEADER_ATTRIBUTE__H_FLIP =      0x0001;     // Flip sprite horizontally
	protected static final int DATA_HEADER_ATTRIBUTE__V_FLIP =      0x0004;     // Flip sprite vertically
	protected static final int DATA_HEADER_ATTRIBUTE__Sp_NoClip =   0x0008;     // Don't clip sprite to view window
	protected static final int DATA_HEADER_ATTRIBUTE__Mask_Glass =  0x0010;     // use darkness mask
	protected static final int DATA_HEADER_ATTRIBUTE__Mask_Frost =  0x0020;     // use mist mask
	protected static final int DATA_HEADER_ATTRIBUTE__Mask_Blue =   0x0040;     // use blue mask
	protected static final int DATA_HEADER_ATTRIBUTE__Mask_Green =  0x0080;     // use green mask
	protected static final int DATA_HEADER_ATTRIBUTE__Sp_LodMem =   0x0400;     // don't attempt to delete map and palette pointers
	protected static final int DATA_HEADER_ATTRIBUTE__Sp_Center =   0x0800;     // center sprite

    // data header
    protected static final int DATA_NAME__DATA_HEADER_OFFSET = 0x00;
    protected static final int DATA_NAME__DATA_HEADER_MAX_LENGTH = 0x0C;
    protected static final int PIXEL_NUMBER__DATA_HEADER_OFFSET = 0x0C; // 4 bytes // number of pixels in sprite
    protected static final int TGA_BYTE_WIDTH__DATA_HEADER_OFFSET = 0x10; // 2 bytes
    protected static final int TGA_BYTE_HEIGHT__DATA_HEADER_OFFSET = 0x12; // 2 bytes
    protected static final int PALETTE_NUMBER__DATA_HEADER_OFFSET = 0x14; // 2 bytes
    protected static final int RESOLUTION__DATA_HEADER_OFFSET = 0x16; // 2 bytes  // unused?
    protected static final int YSKIP__DATA_HEADER_OFFSET = 0x18; // 2 bytes // number of clear lines at bottom
    protected static final int ATTRIBUTE_MASK__DATA_HEADER_OFFSET = 0x1a; // 2 bytes
    protected static final int DATA_CONTENT_UNCOMPRESSED_SIZE__DATA_HEADER_OFFSET = 0x1c; // 4 bytes

    protected static final int DATA_HEADER_LENGTH = 0x20;

    private static final int ZERO_COMPRESSION_TABLE__DATA_HEADER_OFFSET = DATA_HEADER_LENGTH; // 8 bytes * width

	private static final int ZERO_COMPRESSION_TABLE_DATA_SIZE = 0x08; // per image row
	// both START and END are -1 (FF FF FF FF) if all zeros and offset is 00 00 00 00 
	private static final int ZERO_COMPRESSION_LINE_DATA_START_BYTE_OFFSET = 0x00; // 2 bytes -- number of zero bytes at start of row 
	private static final int ZERO_COMPRESSION_LINE_DATA_END_BYTE_OFFSET = 0x02; // 2 bytes -- (width - 1) - (number of zero bytes at end of row)
	private static final int ZERO_COMPRESSION_LINE_DATA_OFFSET_OFFSET = 0x04; // 2 bytes -- 0-based index into compacted uncompressed data

    protected Extractor theFileExtractor = new Extractor();

    public MM6SpritesLodEntry(LodFile lodFile, long headerOffset)
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
            + "DataName: "
            + getDataName()
            + "\n"
            + "DataType: "
            + getDataType()
            + "\n"
            + "FileType: "
            + getFileType()
            + "\n"
            + "Width: "
            + getByteWidth()
            + "\n"
            + "Height: "
            + getByteHeight()
            + "\n"
            + "PaletteNumber: "
            + getPaletteNumber()
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

    public long getZeroCompressionTableOffset()
    {
        return ZERO_COMPRESSION_TABLE__DATA_HEADER_OFFSET;
    }

	public int[] getPalette() throws IOException
	{
	    BitmapsLodFile cachedBitmapsLodFile = ((MM6SpritesLodFile)getLodFile()).getCachedBitmapsLodFile();
	    if (null == cachedBitmapsLodFile)
	    {
			File thisFile = this.getLodFile().getFile();
			File paletteFile = new File(thisFile.getParent() + File.separator + "BITMAPS.LOD");
				
			RandomAccessFile paletteRandomAccessFile = new RandomAccessFile(paletteFile, "r");
			RandomAccessFileInputStream paletteInputStream = new RandomAccessFileInputStream(paletteRandomAccessFile);
			BitmapsLodFile paletteLodFile;
	        try
	        {
	            cachedBitmapsLodFile = new BitmapsLodFile(paletteFile,  paletteInputStream);
	            ((MM6SpritesLodFile)getLodFile()).setCachedBitmapsLodFile(cachedBitmapsLodFile);
	        }
	        catch (InvalidLodFileException exception)
	        {
	            IOException ioException = new IOException(exception.getMessage());
	            ioException.initCause(exception);
	            throw ioException;
	        }
	    }
	    
        long paletteNumber = getPaletteNumber();
        
	    Map cachedPaletteHashTable = ((MM6SpritesLodFile)getLodFile()).getCachedPaletteHashTable();

        int palette[] = (int [])cachedPaletteHashTable.get(new Long(paletteNumber));
        if (null != palette)  return palette;

        String paletteNumberString = String.valueOf(paletteNumber);
		while (paletteNumberString.length() < 3)  paletteNumberString = "0" + paletteNumberString;
			
		String paletteEntryName = "pal" + paletteNumberString + ".pal";
		BitmapsLodEntry paletteLodEntry = null;
        try
        {
            paletteLodEntry = (BitmapsLodEntry)cachedBitmapsLodFile.findLodEntryByFileName(paletteEntryName);
        }
        catch (NoSuchEntryException exception)
        {
            IOException ioException = new IOException(exception.getMessage());
            ioException.initCause(exception);
            throw ioException;
        }
        int paletteArray[] = new int[768];

		long startPosition = paletteLodEntry.getDataOffset();
		RandomAccessFile raf =
			paletteLodEntry
				.getLodFile()
				.getRandomAccessFileInputStream()
				.getFile();
		raf.seek(startPosition);
		for (int index = 0; index < 768; ++index)
		{
			paletteArray[index] = raf.read(); 
		}

		cachedPaletteHashTable.put(new Long(paletteNumber), paletteArray);
		
		return paletteArray;
	}

    protected void computeEntryBasedOffsets()
    {
        dataHeader = new byte[getDataHeaderLength()];
    }

    protected void computeDataBasedOffsets()
    {
    }

    protected long getDataHeaderOffset()
    {
        return getLodFile().getHeaderOffset() + ByteConversions.getIntegerInByteArrayAtPosition(entryHeader, getOffsetInEntryHeaderForDataSegmentOffset());
    }

    public long getDataOffset()
    {
        return getDataHeaderOffset() + getDataHeaderLength();
    }

    protected int getDataLength()
    {
        return ByteConversions.getIntegerInByteArrayAtPosition(entryHeader, DATA_SEGMENT_LENGTH__ENTRY_HEADER_OFFSET) - getDataHeaderLength();
    }

    public byte[] getData() throws IOException
    {
        byte[] rawData = readRawData();
        
        int zeroCompressedTableLength = ZERO_COMPRESSION_TABLE_DATA_SIZE * getByteHeight();
        int compactedDataLength = rawData.length - zeroCompressedTableLength;
        
        byte[] compressedImageData = new byte[compactedDataLength];
        System.arraycopy(rawData, zeroCompressedTableLength, compressedImageData, 0, compactedDataLength);
        
        byte[] zeroCompressionTableByteArray = new byte[zeroCompressedTableLength];
        System.arraycopy(rawData, 0, zeroCompressionTableByteArray, 0, zeroCompressedTableLength);
        
        byte[] compactedData = null;
        if (0 == getDecompressedSize())
            compactedData = compressedImageData;
        else        
        {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            theLodFileExtractor.decompress(null, compressedImageData, byteStream, null, true);
            compactedData = byteStream.toByteArray();
        }
        
        return unZeroCompressDataUsingTable(compactedData, zeroCompressionTableByteArray);
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

    protected int getOffsetInDataHeaderForPaletteNumber()
    {
        return PALETTE_NUMBER__DATA_HEADER_OFFSET;
    }
    
    protected long getOffsetInDataHeaderForDataContentUncompressedLength()
    {
        return DATA_CONTENT_UNCOMPRESSED_SIZE__DATA_HEADER_OFFSET;
    }

    protected int getOffsetInDataHeaderForPixelNumber()
    {
        return PIXEL_NUMBER__DATA_HEADER_OFFSET;
    }

    protected int getOffsetInDataHeaderForResolution()
    {
        return RESOLUTION__DATA_HEADER_OFFSET;
    }

    protected int getOffsetInDataHeaderForYSkip()
    {
        return YSKIP__DATA_HEADER_OFFSET;
    }

    protected int getOffsetInDataHeaderForAttributeMask()
    {
        return ATTRIBUTE_MASK__DATA_HEADER_OFFSET;
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

    protected String getFileType()
    {
        return "sprite";
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
        return "sprite";
    }

    protected long getPaletteNumber()
    {
        int index = (int)getOffsetInDataHeaderForPaletteNumber();
        if (-1 == index)   return -1;
        return ByteConversions.getIntegerInByteArrayAtPosition(dataHeader, index);
    }


	public int getPixelNumber()
	{
        int index = (int)getOffsetInDataHeaderForPixelNumber();
        if (-1 == index)   return -1;
        return ByteConversions.getIntegerInByteArrayAtPosition(dataHeader, index);
	}


	public int getResolution()
	{
        int index = (int)getOffsetInDataHeaderForResolution();
        if (-1 == index)   return -1;
        return ByteConversions.getIntegerInByteArrayAtPosition(dataHeader, index);
	}


	public int getYSkip()
	{
        int index = (int)getOffsetInDataHeaderForYSkip();
        if (-1 == index)   return -1;
        return ByteConversions.getIntegerInByteArrayAtPosition(dataHeader, index);
	}


	public int getAttributeMask()
	{
        int index = (int)getOffsetInDataHeaderForAttributeMask();
        if (-1 == index)   return -1;
        return ByteConversions.getIntegerInByteArrayAtPosition(dataHeader, index);
	}

    protected long getDecompressedSize()
    {
        int index = (int)getOffsetInDataHeaderForDataContentUncompressedLength();
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
        return new TGAToBMPFormatConverter();
    }

    public String getFormatConverterFileType()
    {
        return "bmp";
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
        // update data length
        ByteConversions.setIntegerInByteArrayAtPosition(writtenDataLength, newEntryHeader, (int)this.getOffsetInEntryHeaderForDataSegmentLength());
//      System.out.println(getName() + "- storing " + String.valueOf(writtenDataLength) + " in entry.datalength");

        outputStream.write(newEntryHeader);
       
        return dataOffset + writtenDataLength;
    }

    public long rewriteEntry(OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
    {
        byte[] rawData = readRawData();
        return writeEntry(null, rawData.length + getDataHeaderLength(), outputStream, entryListOffset, entryOffset, dataOffset);
    }

    public long writeNewEntry(LodResource lodResourceDataSource, OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
    {
        return writeEntry(this.getEntryName(), getNewWrittenDataContentLength(lodResourceDataSource), outputStream, entryListOffset, entryOffset, dataOffset);
    }

    public long updateEntry(LodResource lodResourceDataSource, OutputStream outputStream, long entryListOffset, long entryOffset, long dataOffset) throws IOException
    {
        return writeEntry(null, getUpdatedWrittenDataContentLength(lodResourceDataSource), outputStream, entryListOffset, entryOffset, dataOffset);
    }

    protected long writeNewDataHeader(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset, long uncompressedDataLength, long paletteNumber)
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

    	// For uncompressed data, write 0 for uncompressed data length
        if (-1 != getOffsetInDataHeaderForDataContentUncompressedLength())
            ByteConversions.setIntegerInByteArrayAtPosition(
                 uncompressedDataLength,
                newDataHeader,
                (int)this.getOffsetInDataHeaderForDataContentUncompressedLength());

        long newDataOffset = dataOffset + newDataHeader.length;
        
        

        if (lodResourceDataSource instanceof SpriteTGADataProducer)
        {
        	SpriteTGADataProducer tgaDataProducer = (SpriteTGADataProducer)lodResourceDataSource;
            int sourcePalette[] = tgaDataProducer.getPalette();

            if (null != sourcePalette)
            {
                byte[] tgaBytes = "TGA".getBytes();
                System.arraycopy(tgaBytes, 0, newDataHeader, nameBytes.length + 1, tgaBytes.length);

                updateDataHeaderValues(newDataHeader, tgaDataProducer,
						paletteNumber);
            }
        }
        
        outputStream.write(newDataHeader);

        return newDataOffset;
    }
 
    protected long updateDataHeader(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset, long uncompressedDataLength, long paletteNumber)
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

//        Arrays.fill(newDataHeader, 0, getDataNameMaxLength(), (byte)0);
//        byte nameBytes[] = this.getDataName().getBytes();
//        System.arraycopy(nameBytes, 0, newDataHeader, 0, nameBytes.length);

        // For uncompressed data, write 0 for uncompressed data length
        if (-1 != getOffsetInDataHeaderForDataContentUncompressedLength())
            ByteConversions.setIntegerInByteArrayAtPosition(
                 uncompressedDataLength,
                newDataHeader,
                (int)this.getOffsetInDataHeaderForDataContentUncompressedLength());

        long newDataOffset = dataOffset + newDataHeader.length;
        
        if (lodResourceDataSource instanceof SpriteTGADataProducer)
        {
        	SpriteTGADataProducer tgaDataProducer = (SpriteTGADataProducer)lodResourceDataSource;
            int sourcePalette[] = tgaDataProducer.getPalette();

            if (null != sourcePalette)
            {
//                byte[] tgaBytes = "TGA".getBytes();
//                System.arraycopy(tgaBytes, 0, newDataHeader, nameBytes.length + 1, tgaBytes.length);

                updateDataHeaderValues(newDataHeader, tgaDataProducer,
						paletteNumber);
            }
        }
        
        outputStream.write(newDataHeader);

        return newDataOffset;
    }

	private void updateDataHeaderValues(byte[] newDataHeader,
			SpriteTGADataProducer tgaDataProducer, long paletteNumber) {
		int sourceWidth = tgaDataProducer.getByteWidth();
		int sourceHeight = tgaDataProducer.getByteHeight();
		
		
		// TODO: implement these methods so sprite headers 
		int pixelNumber = tgaDataProducer.getPixelNumber();
		int resolution = tgaDataProducer.getResolution();
		int yskip = tgaDataProducer.getYSkip();
		int attributeMask = tgaDataProducer.getAttributeMask();

		// I'm computing yskip while setting leftmost and rightmost coordinates of lines.
//		
//        protected static final int PIXEL_NUMBER__DATA_HEADER_OFFSET = 0x0C; // 4 bytes // number of pixels in sprite
//        protected static final int RESOLUTION__DATA_HEADER_OFFSET = 0x16; // 2 bytes  // unused?
//    	private static final int YSKIP__DATA_HEADER_OFFSET = 0x18; // 2 bytes // number of clear lines at bottom

		// add pixel number
		ByteConversions.setIntegerInByteArrayAtPosition(pixelNumber, newDataHeader, getOffsetInDataHeaderForPixelNumber());

		// add width
		ByteConversions.setShortInByteArrayAtPosition((short)sourceWidth, newDataHeader, getOffsetInDataHeaderForTGAByteWidth());
		
		// add height
		ByteConversions.setShortInByteArrayAtPosition((short)sourceHeight, newDataHeader, getOffsetInDataHeaderForTGAByteHeight());

		// add palette number
		ByteConversions.setShortInByteArrayAtPosition((short)paletteNumber, newDataHeader, getOffsetInDataHeaderForPaletteNumber());
		
		// add resolution
		ByteConversions.setShortInByteArrayAtPosition((short)resolution, newDataHeader, getOffsetInDataHeaderForResolution());

		// add yskip
		ByteConversions.setShortInByteArrayAtPosition((short)yskip, newDataHeader, getOffsetInDataHeaderForYSkip());

		// add attributeMask
		ByteConversions.setShortInByteArrayAtPosition((short)attributeMask, newDataHeader, getOffsetInDataHeaderForAttributeMask());
		
	}

    public long writeNewData(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset)
    throws IOException
    {
        TGADataProducer tgaDataProducer = (TGADataProducer)lodResourceDataSource;

        byte uncompactedData[] = lodResourceDataSource.getData();
//      System.out.println(getName() + "- DS uncompacted data size: " + uncompactedData.length);
        
        byte zeroCompressionTableData[] = new byte[ZERO_COMPRESSION_TABLE_DATA_SIZE * tgaDataProducer.getByteHeight()];
        byte uncompressedData[] = zeroCompressDataIntoTable(tgaDataProducer.getByteWidth(), tgaDataProducer.getByteHeight(), uncompactedData, zeroCompressionTableData);
        
//      System.out.println(getName() + "- DS uncompressed data size: " + uncompressedData.length);
//      System.out.println(getName() + "- DS zeroCompressionTabledata size: " + zeroCompressionTableData.length);
        
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

//      System.out.println(getName() + "- rawdataLength: " + smallestData.length);
        

        int sourcePalette[] = tgaDataProducer.getPalette();

        if (null == sourcePalette)
            throw new RuntimeException("No source palette");

        int originalPalette[] = getPalette();
        
        // IMPLEMENT: verify that palette matches known palette number or create new palette resource
        // for now, assume palette doesn't change
        long paletteNumber = getPaletteNumber();

        for (int i = 0 ; i < sourcePalette.length ; i++) {
            if (sourcePalette[i] != originalPalette[i])
                System.err.println(getName() + ": Warning: palette doesn't match original palette for sprite!");
            	// IMPLEMENT: throw harder error
        }

//      System.out.println("palette: " + sourcePalette.length);
        long newDataOffset = writeNewDataHeader(lodResourceDataSource, outputStream, dataOffset, uncompressedDataLengthSpecifier, paletteNumber);
       
        outputStream.write(zeroCompressionTableData);
        outputStream.write(smallestData);

        long newOffset = newDataOffset + zeroCompressionTableData.length + smallestData.length;

//      System.out.println(getName() + " - nextoffset: " + newOffset);

        return newOffset;
    }

    public long updateData(LodResource lodResourceDataSource, OutputStream outputStream, long dataOffset)
         throws IOException
    {
        TGADataProducer tgaDataProducer = (TGADataProducer)lodResourceDataSource;

        byte uncompactedData[] = lodResourceDataSource.getData();
//      System.out.println(getName() + "- DS uncompacted data size: " + uncompactedData.length);
        
        byte zeroCompressionTableData[] = new byte[ZERO_COMPRESSION_TABLE_DATA_SIZE * tgaDataProducer.getByteHeight()];
        byte uncompressedData[] = zeroCompressDataIntoTable(tgaDataProducer.getByteWidth(), tgaDataProducer.getByteHeight(), uncompactedData, zeroCompressionTableData);
        
//      System.out.println(getName() + "- DS uncompressed data size: " + uncompressedData.length);
//      System.out.println(getName() + "- DS zeroCompressionTabledata size: " + zeroCompressionTableData.length);
        
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

//       System.out.println(getName() + "- rawdataLength: " + smallestData.length);
         
         int sourcePalette[] = tgaDataProducer.getPalette();

         if (null == sourcePalette)
             throw new RuntimeException("No source palette");

         // IMPLEMENT: verify that palette matches known palette number or create new palette resource
         // for now, assume palette doesn't change
         long paletteNumber = getPaletteNumber();

         int originalPalette[];
		try {
			originalPalette = getPalette();
		} catch (IOException ioException) {
			if (ioException.getCause() instanceof NoSuchEntryException)
			{
				ioException.printStackTrace();
				
				// Meaningless image, but we can continue past it -- generate unique value for every palette point.
				originalPalette = new int[768];
				for (int i = 0; i < originalPalette.length; ++i)
				{
					originalPalette[i] = i;
				}
			}
			else
			{
				throw ioException;
			}
		}
         
         for (int i = 0 ; i < sourcePalette.length ; i++) {
             if (sourcePalette[i] != originalPalette[i])
                 System.err.println(getName() + ": Warning: palette doesn't match original palette for sprite!");
             	// IMPLEMENT: throw harder error
         }

//       System.out.println("palette: " + sourcePalette.length);

         long newDataOffset = updateDataHeader(lodResourceDataSource, outputStream, dataOffset, uncompressedDataLengthSpecifier, paletteNumber);
        
         outputStream.write(zeroCompressionTableData);
         outputStream.write(smallestData);

         long newOffset = newDataOffset + zeroCompressionTableData.length + smallestData.length;

//       System.out.println(getName() + " - nextoffset: " + newOffset);

         return newOffset;
     }

     public long rewriteData(OutputStream outputStream, long dataOffset)
            throws IOException
    {
         outputStream.write(getDataHeader());

         byte[] rawData = readRawData();
         outputStream.write(rawData);

        long newOffset = dataOffset + getDataHeaderLength() + rawData.length;

//      System.out.println(getName() + " - nextoffset: " + newOffset);

        return newOffset;
    }

     public class ZeroCompressionTableEntry
     {
     	short lineDataStart = -1;
     	short lineDataEnd = -1;
     	int lineDataOffset = -1;
     	
     	public ZeroCompressionTableEntry(short lineDataStart, short lineDataEnd, int lineDataOffset)
     	{
     		this.lineDataStart = lineDataStart;
     		this.lineDataEnd = lineDataEnd;
     		this.lineDataOffset = lineDataOffset;
     	}
     	
     	public String toString()
     	{
     		String output = super.toString();
     		
     		output += ": lineDataStart=" + lineDataStart;
     		output += ", lineDataEnd=" + lineDataEnd;
     		output += ", lineDataOffset=" + lineDataOffset;
     		
     		return output;
     	}
     }

     public ZeroCompressionTableEntry[] getZeroCompressionTable(byte zeroCompressionTableData[]) throws IOException
     {
         int imageHeight = getByteHeight();
         
     	ZeroCompressionTableEntry zeroCompressionTable[] = new ZeroCompressionTableEntry[imageHeight];

     	for (int row = 0; row < imageHeight; ++row)
     	{
     	    int rowOffset = row * ZERO_COMPRESSION_TABLE_DATA_SIZE;
     		short lineDataStart = ByteConversions.getShortInByteArrayAtPosition(zeroCompressionTableData, rowOffset + ZERO_COMPRESSION_LINE_DATA_START_BYTE_OFFSET);
     		short lineDataEnd = ByteConversions.getShortInByteArrayAtPosition(zeroCompressionTableData, rowOffset + ZERO_COMPRESSION_LINE_DATA_END_BYTE_OFFSET);
     		int lineDataOffset = ByteConversions.getIntegerInByteArrayAtPosition(zeroCompressionTableData, rowOffset + ZERO_COMPRESSION_LINE_DATA_OFFSET_OFFSET);
     			
     		zeroCompressionTable[row] = new ZeroCompressionTableEntry(lineDataStart, lineDataEnd, lineDataOffset);
     	}

     	return zeroCompressionTable;
     }

     /**
      * @param uncompactedData
      * @param zeroCompressionTableData
      * @return
      */
     private byte[] zeroCompressDataIntoTable(int imageWidth, int imageHeight, byte[] uncompactedData, byte[] zeroCompressionTableData)
     {
        byte pessimisticCompactedData[] = new byte[uncompactedData.length];
        
  		int lineDataOffset = 0;
		int uncompactedByteArrayIndex = 0;
		for (int row = 0; row < imageHeight; ++row)
		{
     	   int tableBase = row * ZERO_COMPRESSION_TABLE_DATA_SIZE;
     	   int dataBase = row * imageWidth;
 	   
     		short lineDataStart = 0;
     		short lineDataEnd = 0;

     		int col = 0;
     		while ((col < imageWidth) && (0 == uncompactedData[dataBase + col]))
         	{
				col++;
				lineDataStart++;
         	}
     		
     		short zerosAtEndOfRow = 0;
     		col = imageWidth - 1;
     		while ((col >= 0) && (0 == uncompactedData[dataBase + col]))
         	{
				col--;
				zerosAtEndOfRow++;
         	}
     		lineDataEnd = (short)((imageWidth - 1) - zerosAtEndOfRow);
     		
     		if (lineDataStart == imageWidth)
     		{
     		    // all zeros
         		// assign to table
         		ByteConversions.setShortInByteArrayAtPosition((short)-1, zeroCompressionTableData, tableBase + ZERO_COMPRESSION_LINE_DATA_START_BYTE_OFFSET);
         		ByteConversions.setShortInByteArrayAtPosition((short)-1, zeroCompressionTableData, tableBase + ZERO_COMPRESSION_LINE_DATA_END_BYTE_OFFSET);
         		ByteConversions.setIntegerInByteArrayAtPosition(0, zeroCompressionTableData, tableBase + ZERO_COMPRESSION_LINE_DATA_OFFSET_OFFSET);
     		}
     		else
     		{
         		// assign to table
         		ByteConversions.setShortInByteArrayAtPosition(lineDataStart, zeroCompressionTableData, tableBase + ZERO_COMPRESSION_LINE_DATA_START_BYTE_OFFSET);
         		ByteConversions.setShortInByteArrayAtPosition(lineDataEnd, zeroCompressionTableData, tableBase + ZERO_COMPRESSION_LINE_DATA_END_BYTE_OFFSET);
         		ByteConversions.setIntegerInByteArrayAtPosition(lineDataOffset, zeroCompressionTableData, tableBase + ZERO_COMPRESSION_LINE_DATA_OFFSET_OFFSET);
          		
         		for (col = lineDataStart; col <= lineDataEnd; ++col)
          		   pessimisticCompactedData[uncompactedByteArrayIndex++] = uncompactedData[dataBase + col];
     		}
     		
     		lineDataOffset = uncompactedByteArrayIndex;
     	}

		byte compactedData[] = new byte[uncompactedByteArrayIndex];
		System.arraycopy(pessimisticCompactedData, 0, compactedData, 0, uncompactedByteArrayIndex);
		
     	return compactedData;
     }

     public byte[] unZeroCompressDataUsingTable(byte[] compactedByteArray, byte[] zeroCompressionTableByteArray) throws IOException
     {
     	byte uncompactedByteArray[] = new byte[(getByteWidth() * getByteHeight())];

     	ZeroCompressionTableEntry zeroCompressionTable[] = getZeroCompressionTable(zeroCompressionTableByteArray);

     	int uncompactedByteArrayIndex = 0;
     	for (int index = 0; index < getByteHeight(); ++index)
     	{
     		short lineDataStart = zeroCompressionTable[index].lineDataStart;
     		short lineDataEnd = zeroCompressionTable[index].lineDataEnd;
     		int lineDataOffset = zeroCompressionTable[index].lineDataOffset;
     		
     		if ( (-1 != lineDataStart) && (-1 != lineDataEnd) )
     		{
     			for (int zeroIndex = 0; zeroIndex < lineDataStart; ++zeroIndex)
     				uncompactedByteArray[uncompactedByteArrayIndex++] = 0;
     		
     			int compactedIndex = lineDataOffset;
     			for (int lineDataIndex = lineDataStart; lineDataIndex <= lineDataEnd; ++lineDataIndex)
     				uncompactedByteArray[uncompactedByteArrayIndex++] = compactedByteArray[compactedIndex++];
     		}
     		
     		for (int zeroIndex = (lineDataEnd + 1); zeroIndex < getByteWidth(); ++zeroIndex)
     			uncompactedByteArray[uncompactedByteArrayIndex++] = 0;
     	}

     	return uncompactedByteArray;
     }
}
