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

package org.gamenet.application.mm8leveleditor.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.SpriteTGADataConsumer;
import org.gamenet.application.mm8leveleditor.lod.TGADataConsumer;
import org.gamenet.application.mm8leveleditor.lod.TGADataProducer;
import org.gamenet.util.ByteConversions;

import com.mmbreakfast.unlod.lod.NoSuchEntryException;

/**
 * @author mlk
 */
public class TGAToBMPFormatConverter extends FormatConverter
{

    public TGAToBMPFormatConverter()
    {
        super();
    }
    
    public class TGAToBMPConversionOutputStream extends OutputStream
    {
        private OutputStream out = null;
        private int width = -1;
        private int height = -1;
        private ByteArrayOutputStream tgaData = null;
        
        private byte bitmapFileHeader[] = {'B', 'M', // id
                                            '?', '?', '?', '?', // file size
                                            0, 0, 0, 0, // always zero
                                            0x36, 0x04, 0x00, 0x00}; // offset to bitmap data
        private byte bitmapInfoHeader[] = { 40, 0, 0, 0, // size of bitmap info header
                                            '?', '?', '?', '?', // width of image in pixels
                                            '?', '?', '?', '?', // height of image in pixels
                                            1, 0,   // number of planes of the target device, must be set to zero.
                                            8, 0,   // biBitCount, number of bits per pixel
                                            0, 0, 0, 0, // compression type
                                            0, 0, 0, 0, // size of image data, may be zero with no compression
                                            0, 0, 0, 0, // horizontal pixels per meter on the designated targer device, usually set to zero.
                                            0, 0, 0, 0, // vertical pixels per meter on the designated targer device, usually set to zero.
                                            0, 0, 0, 0, // number of colors used in the bitmap, if set to zero the number of colors is calculated using the biBitCount member.
                                            0, 0, 0, 0 // number of color that are 'important' for the bitmap, if set to zero, all colors are important.
                                   };
        private byte rgbquadColors[] = null;  //  256 * 4 bytes in 8-bit mode // {B, G, R, 0}
        private byte bitmapBits[] = null;

        public TGAToBMPConversionOutputStream(OutputStream out, LodResource bitmapLodEntry) throws IOException
        {
            super();
            
            this.out = out;
            
            tgaData = new ByteArrayOutputStream();
            TGADataProducer tgaDataProducer = (TGADataProducer)bitmapLodEntry;
            width = tgaDataProducer.getByteWidth();
            height = tgaDataProducer.getByteHeight();
            int palette[];
			try {
				palette = tgaDataProducer.getPalette();
			} catch (IOException ioException) {
				if (ioException.getCause() instanceof NoSuchEntryException)
				{
					ioException.printStackTrace();
					
					// Meaningless image, but we can continue past it -- generate unique value for every palette point.
					palette = new int[768];
					for (int i = 0; i < palette.length; ++i)
					{
						palette[i] = i;
					}
				}
				else
				{
					throw ioException;
				}
			}
            
            rgbquadColors = new byte[256 * 4];
            
            for (int index = 0; index < 256; ++index)
            {
                rgbquadColors[(index*4) + 2] = (byte)palette[(index * 3) + 0];
                rgbquadColors[(index*4) + 1] = (byte)palette[(index * 3) + 1];
                rgbquadColors[(index*4) + 0] = (byte)palette[(index * 3) + 2];
                rgbquadColors[(index*4) + 3] = 0;
            }
             
            bitmapInfoHeader[4] = (byte)(width % 256);
            bitmapInfoHeader[5] = (byte)((width / 256) % 256);
            bitmapInfoHeader[6] = (byte)(((width / 256) / 256) % 256);
            bitmapInfoHeader[7] = (byte)((((width / 256) / 256) / 256) % 256);
            
            bitmapInfoHeader[8] = (byte)(height % 256);
            bitmapInfoHeader[9] = (byte)((height / 256) % 256);
            bitmapInfoHeader[10] = (byte)(((height / 256) / 256) % 256);
            bitmapInfoHeader[11] = (byte)((((height / 256) / 256) / 256) % 256);
        }
        
        public void close() throws IOException
        {
            int paddedWidth = width;
            // TODO: replace with real computation
            while (0 != (paddedWidth % 4)) ++paddedWidth;
            
            byte rawData[] = tgaData.toByteArray();
            int rawDataIndex = 0;
            bitmapBits = new byte[paddedWidth * height];
            int bitmapBitsIndex = 0;
            for (int h = 0; h < height; ++h)
            {
                for (int w = 0; w < paddedWidth; ++w)
                {
                    if (w >= width)
                    {
                        // System.out.println("calc=" + ((h * width) + w) + ", inc=" +  bitmapBitsIndex++);
                        bitmapBits[(((height - 1) - h) * paddedWidth) + w] = 0;
                    }
                    else
                    {
                        // System.out.println("calc=" + ((h * width) + w) + ", inc=" +  bitmapBitsIndex++);
                        bitmapBits[(((height - 1) - h) * paddedWidth) + w] = rawData[rawDataIndex++];
                    }
                }
            }
            
            int fileSize = bitmapFileHeader.length + bitmapInfoHeader.length
                + rgbquadColors.length + bitmapBits.length;
            bitmapFileHeader[2] = (byte)(fileSize % 256);
            bitmapFileHeader[3] = (byte)((fileSize / 256) % 256);
            bitmapFileHeader[4] = (byte)(((fileSize / 256) / 256) % 256);
            bitmapFileHeader[5] = (byte)((((fileSize / 256) / 256) / 256) % 256);
            
            out.write(bitmapFileHeader);
            out.write(bitmapInfoHeader);
            out.write(rgbquadColors);
            out.write(bitmapBits);
            
            out.flush();
            out.close();
        }

        public void write(int b) throws IOException
        {
            tgaData.write(b);
        }
    }
    
    /**
     * Set output Stream where the new BMP format will be written
     * 
     * @param stream where the new converted format will be written
     */
    public void setDestinationOutputStreamForNewFormat(OutputStream stream, LodResource bitmapLodEntry) throws IOException
    {
        sourceOutputStream = new TGAToBMPConversionOutputStream(stream, bitmapLodEntry);
    }
    
    public class BMPToTGAConversionInputStream extends InputStream
    {
        private InputStream in = null;
        private int width = -1;
        private int height = -1;
        private int[] palette = null;
        private byte[] tgaData = null;
        
        private int attributeMask;
        private int pixelNumber;
        private int resolution;
        private int ySkip;

        private boolean didReadTGAData = true;
        private int readingTGAIndex = 0;
        
        private int FILE_HEADER__ID_OFFSET = 0;
        private byte[] FILE_HEADER__ID_VALUE = {'B', 'M'};
        private int FILE_HEADER__FILE_SIZE_OFFSET = 2;
        private int FILE_HEADER__UNUSED1_OFFSET = 6;
        private int FILE_HEADER__UNUSED1_VALUE = 0;
        private int FILE_HEADER__BITMAP_BITS_OFFSET = 10;
        private byte bitmapFileHeader[] = {'B', 'M', // id
                                            '?', '?', '?', '?', // file size
                                            0, 0, 0, 0, // always zero
                                            0x36, 0x04, 0x00, 0x00}; // offset to bitmap data
        
        private int INFO_HEADER__SIZE_OF_BITMAP_INFO_HEADER_OFFSET = 0;
        private int INFO_HEADER__SIZE_OF_BITMAP_INFO_HEADER_VALUE = 40;
        private int INFO_HEADER__IMAGE_WIDTH_OFFSET = 4;
        private int INFO_HEADER__IMAGE_HEIGHT_OFFSET = 8;
        private int INFO_HEADER__PLANES_OFFSET = 12;
        private short INFO_HEADER__PLANES_VALUE = 1;
        private int INFO_HEADER__BITS_PER_PIXEL_OFFSET = 14;
        private short INFO_HEADER__BITS_PER_PIXEL_VALUE = 8;
        private int INFO_HEADER__COMPRESSION_OFFSET = 16;
        private int INFO_HEADER__COMPRESSION_VALUE = 0;
        private int INFO_HEADER__SIZE_OF_IMAGE_DATA_OFFSET = 20;
        private int INFO_HEADER__SIZE_OF_IMAGE_DATA_VALUE = 0;
        private int INFO_HEADER__HORIZONTAL_PIXELS_PER_METER_OFFSET = 24;
        private int INFO_HEADER__HORIZONTAL_PIXELS_PER_METER_VALUE = 0;
        private int INFO_HEADER__VERTICAL_PIXELS_PER_METER_OFFSET = 28;
        private int INFO_HEADER__VERTICAL_PIXELS_PER_METER_VALUE = 0;
        private int INFO_HEADER__NUMBER_OF_COLORS_OFFSET = 32;
        private int INFO_HEADER__NUMBER_OF_COLORS_VALUE = 0;
        private int INFO_HEADER__NUMBER_OF_IMPORTANT_COLORS_OFFSET = 36;
        private int INFO_HEADER__NUMBER_OF_IMPORTANT_COLORS_VALUE = 0;
        private byte bitmapInfoHeader[] = { 40, 0, 0, 0, // size of bitmap info header
                                            '?', '?', '?', '?', // width of image in pixels
                                            '?', '?', '?', '?', // height of image in pixels
                                            1, 0,   // number of planes of the target device, must be set to zero.
                                            8, 0,   // biBitCount, number of bits per pixel
                                            0, 0, 0, 0, // compression type
                                            0, 0, 0, 0, // size of image data, may be zero with no compression
                                            0, 0, 0, 0, // horizontal pixels per meter on the designated targer device, usually set to zero.
                                            0, 0, 0, 0, // vertical pixels per meter on the designated targer device, usually set to zero.
                                            0, 0, 0, 0, // number of colors used in the bitmap, if set to zero the number of colors is calculated using the biBitCount member.
                                            0, 0, 0, 0 // number of color that are 'important' for the bitmap, if set to zero, all colors are important.
                                   };
        private byte rgbquadColors[] = null;  //  256 * 4 bytes in 8-bit mode // {B, G, R, 0}
        private byte bitmapBits[] = null;



        public BMPToTGAConversionInputStream(InputStream in, TGADataConsumer tgaAcceptingLodEntry) throws IOException
        {
            super();
            
            SpriteTGADataConsumer spriteTGADataConsumer = null;
            if (tgaAcceptingLodEntry instanceof SpriteTGADataConsumer)
            {
            	spriteTGADataConsumer = (SpriteTGADataConsumer)tgaAcceptingLodEntry;
            }
            
            this.in = in;
            
            int totalBitmapFileHeader = 0;
            while (totalBitmapFileHeader < bitmapFileHeader.length)
            {
                int count = in.read(bitmapFileHeader, totalBitmapFileHeader, (bitmapFileHeader.length - totalBitmapFileHeader));
                if (-1 == count)  throw new RuntimeException("unexpected end of file: " + String.valueOf(bitmapFileHeader.length - totalBitmapFileHeader) + " bytes left in bitmapFileHeader.");
                totalBitmapFileHeader += count;
            }
            
            for (int index = FILE_HEADER__ID_OFFSET; index < FILE_HEADER__ID_VALUE.length; ++index)
                if (FILE_HEADER__ID_VALUE[index] != bitmapFileHeader[index])
                    throw new UnsupportedFileFormatException("File header id value <" + bitmapFileHeader[index] + "> does not match expected value <" + FILE_HEADER__ID_VALUE[index] + ">.");

            int fileSize = ByteConversions.getIntegerInByteArrayAtPosition(bitmapFileHeader, FILE_HEADER__FILE_SIZE_OFFSET);

            int unused1 = ByteConversions.getIntegerInByteArrayAtPosition(bitmapFileHeader, FILE_HEADER__UNUSED1_OFFSET);
            if (unused1 != FILE_HEADER__UNUSED1_VALUE)
                throw new UnsupportedFileFormatException("File header reserved bytes value <" + unused1 + "> does not match expected value <" + FILE_HEADER__UNUSED1_VALUE + ">.");

            int bitmapBitsOffset = ByteConversions.getIntegerInByteArrayAtPosition(bitmapFileHeader, FILE_HEADER__BITMAP_BITS_OFFSET);

            int totalBitmapInfoHeader = 0;
            while (totalBitmapInfoHeader < 4)
            {
                int count = in.read(bitmapInfoHeader, totalBitmapInfoHeader, (4 - totalBitmapInfoHeader));
                if (-1 == count)  throw new RuntimeException("unexpected end of file: " + String.valueOf(4 - totalBitmapInfoHeader) + " bytes left in bitmapInfoHeader pre-header.");
                totalBitmapInfoHeader += count;
            }
            
            int bitmapInfoHeaderSize = ByteConversions.getIntegerInByteArrayAtPosition(bitmapInfoHeader, INFO_HEADER__SIZE_OF_BITMAP_INFO_HEADER_OFFSET);
            if (bitmapInfoHeaderSize != INFO_HEADER__SIZE_OF_BITMAP_INFO_HEADER_VALUE)
                throw new UnsupportedFileFormatException("File header bitmap info header size value <" + bitmapInfoHeaderSize + "> does not match expected value <" + INFO_HEADER__SIZE_OF_BITMAP_INFO_HEADER_VALUE + ">.");

            while (totalBitmapInfoHeader < bitmapInfoHeader.length)
            {
                int count = in.read(bitmapInfoHeader, totalBitmapInfoHeader, (bitmapInfoHeader.length - totalBitmapInfoHeader));
                if (-1 == count)  throw new RuntimeException("unexpected end of file: " + String.valueOf(bitmapInfoHeader.length - totalBitmapInfoHeader) + " bytes left in bitmapInfoHeader.");
                totalBitmapInfoHeader += count;
            }
            
            width = ByteConversions.getIntegerInByteArrayAtPosition(bitmapInfoHeader, INFO_HEADER__IMAGE_WIDTH_OFFSET);
            height = ByteConversions.getIntegerInByteArrayAtPosition(bitmapInfoHeader, INFO_HEADER__IMAGE_HEIGHT_OFFSET);
            
            short planes = ByteConversions.getShortInByteArrayAtPosition(bitmapInfoHeader, INFO_HEADER__PLANES_OFFSET);
            if (planes != INFO_HEADER__PLANES_VALUE)
                throw new UnsupportedFileFormatException("Info header planes value <" + planes + "> does not match expected value <" + INFO_HEADER__PLANES_VALUE + ">.");
            
            short bitsPerPixel = ByteConversions.getShortInByteArrayAtPosition(bitmapInfoHeader, INFO_HEADER__BITS_PER_PIXEL_OFFSET);
            if (bitsPerPixel != INFO_HEADER__BITS_PER_PIXEL_VALUE)
                throw new UnsupportedFileFormatException("Info header bitsPerPixel value <" + bitsPerPixel + "> does not match expected value <" + INFO_HEADER__BITS_PER_PIXEL_VALUE + ">.");

            int compression = ByteConversions.getIntegerInByteArrayAtPosition(bitmapInfoHeader, INFO_HEADER__COMPRESSION_OFFSET);
            if (compression != INFO_HEADER__COMPRESSION_VALUE)
                throw new UnsupportedFileFormatException("Info header compression value <" + compression + "> does not match expected value <" + INFO_HEADER__COMPRESSION_VALUE + ">.");

            int sizeOfImageData = ByteConversions.getIntegerInByteArrayAtPosition(bitmapInfoHeader, INFO_HEADER__SIZE_OF_IMAGE_DATA_OFFSET);
//            if (sizeOfImageData != INFO_HEADER__SIZE_OF_IMAGE_DATA_VALUE)
//                throw new UnsupportedFileFormatException("Info header size of image data value <" + sizeOfImageData + "> does not match expected value <" + INFO_HEADER__SIZE_OF_IMAGE_DATA_VALUE + ">.");
            sizeOfImageData = 0; // IMPLEMENT:  This should still be compared against expected value if not zero
                
            int horizontalPixelsPerMeter = ByteConversions.getIntegerInByteArrayAtPosition(bitmapInfoHeader, INFO_HEADER__HORIZONTAL_PIXELS_PER_METER_OFFSET);
//            if (horizontalPixelsPerMeter != INFO_HEADER__HORIZONTAL_PIXELS_PER_METER_VALUE)
//                throw new UnsupportedFileFormatException("Info header horizontal Pixels Per Meter value <" + horizontalPixelsPerMeter + "> does not match expected value <" + INFO_HEADER__HORIZONTAL_PIXELS_PER_METER_VALUE + ">.");
            horizontalPixelsPerMeter = 0;

            int verticalPixelsPerMeter = ByteConversions.getIntegerInByteArrayAtPosition(bitmapInfoHeader, INFO_HEADER__VERTICAL_PIXELS_PER_METER_OFFSET);
//            if (verticalPixelsPerMeter != INFO_HEADER__VERTICAL_PIXELS_PER_METER_VALUE)
//                throw new UnsupportedFileFormatException("Info header vertical Pixels Per Meter value <" + verticalPixelsPerMeter + "> does not match expected value <" + INFO_HEADER__VERTICAL_PIXELS_PER_METER_VALUE + ">.");
            verticalPixelsPerMeter = 0;
            
            int numberOfColors = ByteConversions.getIntegerInByteArrayAtPosition(bitmapInfoHeader, INFO_HEADER__NUMBER_OF_COLORS_OFFSET);
//            if (numberOfColors != INFO_HEADER__NUMBER_OF_COLORS_VALUE)
//                throw new UnsupportedFileFormatException("Info header number of colors value <" + numberOfColors + "> does not match expected value <" + INFO_HEADER__NUMBER_OF_IMPORTANT_COLORS_OFFSET + ">.");
            numberOfColors = 0;
            
            int numberOfImportantColors = ByteConversions.getIntegerInByteArrayAtPosition(bitmapInfoHeader, INFO_HEADER__NUMBER_OF_COLORS_OFFSET);
//            if (numberOfImportantColors != INFO_HEADER__NUMBER_OF_IMPORTANT_COLORS_VALUE)
//                throw new UnsupportedFileFormatException("Info header number of important colors value <" + numberOfImportantColors + "> does not match expected value <" + INFO_HEADER__NUMBER_OF_IMPORTANT_COLORS_VALUE + ">.");
            numberOfImportantColors = 0;
            
            switch (bitsPerPixel)
            {
                case 8:
                    rgbquadColors = new byte[256 * 4];  //  256 * 4 bytes in 8-bit mode // {B, G, R, 0}
                    break;
                default:
                    throw new UnsupportedFileFormatException("Unsupported bitsPerPixel value <" + bitsPerPixel + ">.");
            }

            int totalRgbquadColors = 0;
            while (totalRgbquadColors < rgbquadColors.length)
            {
                int count = in.read(rgbquadColors, totalRgbquadColors, (rgbquadColors.length - totalRgbquadColors));
                if (-1 == count)  throw new RuntimeException("unexpected end of file: " + String.valueOf(rgbquadColors.length - totalRgbquadColors) + " bytes left in rgbquadColors.");
                totalRgbquadColors += count;
            }
            
            if (bitmapBitsOffset != (bitmapFileHeader.length + bitmapInfoHeader.length + rgbquadColors.length))
                throw new UnsupportedFileFormatException("File header bitmap bits offset  <" + bitmapBitsOffset + "> does not match expected value <" + (bitmapFileHeader.length + bitmapInfoHeader.length + rgbquadColors.length) + ">.");

            int bitmapBitsSize = fileSize - (bitmapFileHeader.length + bitmapInfoHeader.length + rgbquadColors.length);

            int paddedWidth = width;
            // TODO: replace with real computation
            while (0 != (paddedWidth % 4)) ++paddedWidth;

            if (bitmapBitsSize != (height * paddedWidth))
                throw new UnsupportedFileFormatException("Bitmap bits size  <" + bitmapBitsSize + "> does not match expected value <" + (height * paddedWidth) + ">.");

            bitmapBits = new byte[bitmapBitsSize];
            
            int totalBitmapBits = 0;
            while (totalBitmapBits < bitmapBits.length)
            {
                int count = in.read(bitmapBits, totalBitmapBits, (bitmapBits.length - totalBitmapBits));
                if (-1 == count)  throw new RuntimeException("unexpected end of file: " + String.valueOf(bitmapBits.length - totalBitmapBits) + " bytes left in bitmapBits.");
                totalBitmapBits += count;
            }
            
            palette = new int[256 * 3];
            for (int index = 0; index < 256; ++index)
            {
                palette[(index * 3) + 0] = ByteConversions.convertByteToInt(rgbquadColors[(index*4) + 2]);
                palette[(index * 3) + 1] = ByteConversions.convertByteToInt(rgbquadColors[(index*4) + 1]);
                palette[(index * 3) + 2] = ByteConversions.convertByteToInt(rgbquadColors[(index*4) + 0]);
            }

            int tgaDataIndex = 0;
            int blankLineCount = 0;
            tgaData = new byte[width * height];
            for (int h = 0; h < height; ++h)
            {
            	boolean isBlankLine = true;
                for (int w = 0; w < paddedWidth; ++w)
                {
                    if (w < width)
                    {
                        // System.out.println("tgaData[" + String.valueOf(tgaDataIndex) + "] = bitmapBits[(((" + String.valueOf(height) + " - 1) - " + String.valueOf(h) + ") * " + String.valueOf(paddedWidth) + ") + " + String.valueOf(w) + "]");
                        byte tgaDataValue = bitmapBits[(((height - 1) - h) * paddedWidth) + w];
                        if (tgaDataValue != 0)
                        {
                        	isBlankLine = false;
                        }
						tgaData[tgaDataIndex++] = tgaDataValue;
                    }
                }
                
                if (isBlankLine)
                {
                	blankLineCount++;
                }
                else
                {
                	blankLineCount = 0;
                }
            }
            
            tgaAcceptingLodEntry.setImageWidth(width);
            tgaAcceptingLodEntry.setImageHeight(height);
            tgaAcceptingLodEntry.setImagePalette(palette);
            
            // I don't know if these values are actually correct or acceptable
            attributeMask = 0;  // told that this value is set to zero on load anyway
            pixelNumber = width * height;
            resolution = 0; // told that this isn't used
            ySkip = blankLineCount;

            if (null != spriteTGADataConsumer)
            {
            	spriteTGADataConsumer.setAttributeMask(attributeMask);
            	spriteTGADataConsumer.setPixelNumber(pixelNumber);
            	spriteTGADataConsumer.setResolution(resolution);
            	spriteTGADataConsumer.setYSkip(ySkip);
            }
            
            didReadTGAData = false;
        }
        
        public void close() throws IOException
        {
            super.close();
            in.close();
        }

        public int read() throws IOException
        {
            if (false == didReadTGAData)
            {
                int value = ByteConversions.convertByteToInt(tgaData[readingTGAIndex++]);
                if (readingTGAIndex >= tgaData.length)
                {
                    didReadTGAData = true;
                }
                
                return value;
            }
            else
            {
                this.close();
                return -1;
            }
        }
    }
    
    /**
     * Set input Stream where the new BMP format will be written
     * 
     * @param stream where the new converted format will be written
     */
    public void setSourceInputStreamForNewFormat(InputStream stream, LodResource tgaAcceptingLodEntry) throws IOException
    {
        sourceInputStream = new BMPToTGAConversionInputStream(stream, (TGADataConsumer)tgaAcceptingLodEntry);
    }
}
