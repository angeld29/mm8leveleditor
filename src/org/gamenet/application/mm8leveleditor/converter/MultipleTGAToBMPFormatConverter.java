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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.TGADataConsumer;
import org.gamenet.application.mm8leveleditor.lod.TGADataProducer;

import com.mmbreakfast.unlod.lod.Extractor;

/**
 * @author mlk
 */
public class MultipleTGAToBMPFormatConverter extends FormatConverter
{
    public MultipleTGAToBMPFormatConverter()
    {
        super();
    }
    
    public boolean isFileNameComponentForLodEntryFileName(String fileName, String LodEntryFileName)
    {
        String[] filenames = getSuggestedFilenames(LodEntryFileName);
        for (int i = 0; i < filenames.length; ++i)
        {
            if (filenames[i].equals(fileName))  return true;
        }
        
        return false;
    }
    
    public boolean requiresMultipleStreams()
    {
        return true;
    }
    
    public int getRequiredStreamCount()
    {
        return 4;
    }
    
    public String[] getSuggestedFilenames(String baseFilename)
    {
        String[] filenames = new String[getRequiredStreamCount()];
        for (int i = 0; i < getRequiredStreamCount(); ++i)
        {
            filenames[i] = baseFilename + "." + String.valueOf(i) + ".bmp";
        }
        
        return filenames;
    }
    
    public void setDestinationOutputStreamsForNewFormat(OutputStream[] outputStreamArray, LodResource lodResource) throws IOException
    {
        sourceOutputStream = new MultipleTGAToBMPConversionOutputStream(outputStreamArray, lodResource);
    }
    
    public class MultipleTGAToBMPConversionOutputStream extends OutputStream
    {
        private OutputStream[] outputStreamArray = null;
        private ByteArrayOutputStream memoryOutputStream = null;
        private LodResource sourceLodResource = null;

        public class InternalLodResource implements TGADataProducer, LodResource
        {
            private LodResource sourceLodResource = null;
            private TGADataProducer sourceTGADataProducer = null;
            private int width = -1;
            private int height = -1;
            private byte[] data = null;
            
            public InternalLodResource(LodResource sourceLodResource, int width, int height, byte[] data)
            {
                this.sourceLodResource = sourceLodResource;
                this.sourceTGADataProducer = (TGADataProducer)sourceLodResource;
                this.width = width;
                this.height = height;
                this.data = data;
            }
            
            public int getByteWidth()
            {
                return width;
            }

            public int getByteHeight()
            {
                return height;
            }

            public int[] getPalette() throws IOException
            {
                return sourceTGADataProducer.getPalette();
            }

            public byte[] getDataHeader()
            {
                return sourceLodResource.getDataHeader();
            }

            public byte[] getData() throws IOException
            {
                return data;
            }

            public String getTextDescription()
            {
                return sourceLodResource.getTextDescription();
            }

            public String getResourceType()
            {
                return sourceLodResource.getResourceType();
            }

            public String getName()
            {
                return sourceLodResource.getName();
            }
        }
        
        public MultipleTGAToBMPConversionOutputStream(OutputStream[] outputStreamArray, LodResource sourceLodResource) throws IOException
        {
            super();
            
            // Force immediate error if not correct type
            TGADataProducer tgaDataProducer = (TGADataProducer)sourceLodResource;
            this.sourceLodResource = sourceLodResource;
            this.outputStreamArray = outputStreamArray;

            memoryOutputStream = new ByteArrayOutputStream();
        }
        
        public void close() throws IOException
        {
            TGADataProducer tgaDataProducer = (TGADataProducer)sourceLodResource;
            int width = tgaDataProducer.getByteWidth();
            int height = tgaDataProducer.getByteHeight();

            byte byteArray[] = memoryOutputStream.toByteArray();

			byte byteArray1[] = new byte[width * height];
			System.arraycopy(byteArray, 0, byteArray1, 0, byteArray1.length);
			InternalLodResource internalLodResource1
				= new InternalLodResource(sourceLodResource, width, height, byteArray1);
			
			byte byteArray2[] = new byte[byteArray1.length / 4];
			System.arraycopy(byteArray, byteArray1.length, byteArray2, 0, byteArray2.length);
			InternalLodResource internalLodResource2
			= new InternalLodResource(sourceLodResource, width / 2, height / 2, byteArray2);
			
			byte byteArray3[] = new byte[byteArray2.length / 4];
			System.arraycopy(byteArray, byteArray1.length + byteArray2.length, byteArray3, 0, byteArray3.length);
			InternalLodResource internalLodResource3
			= new InternalLodResource(sourceLodResource, width / 4, height / 4, byteArray3);
			
			byte byteArray4[] = new byte[byteArray3.length / 4];
			System.arraycopy(byteArray, byteArray1.length + byteArray2.length + byteArray3.length, byteArray4, 0, byteArray4.length);
			InternalLodResource internalLodResource4
			= new InternalLodResource(sourceLodResource, width / 8, height / 8, byteArray4);

			String identifier = null;
            
            identifier = "1";
            TGAToBMPFormatConverter converter = new TGAToBMPFormatConverter();
            converter.setDestinationOutputStreamForNewFormat(outputStreamArray[0], internalLodResource1);
            Extractor extractor = new Extractor();
            extractor.convert(identifier, byteArray1,
                              converter.getSourceOutputStreamForOldFormat(),
                              null, false);

            identifier = "2";
            converter = new TGAToBMPFormatConverter();
            converter.setDestinationOutputStreamForNewFormat(outputStreamArray[1], internalLodResource2);
            extractor = new Extractor();
            extractor.convert(identifier, byteArray2,
                              converter.getSourceOutputStreamForOldFormat(),
                              null, false);
            
            identifier = "3";
            converter = new TGAToBMPFormatConverter();
            converter.setDestinationOutputStreamForNewFormat(outputStreamArray[2], internalLodResource3);
            extractor = new Extractor();
            extractor.convert(identifier, byteArray3,
                              converter.getSourceOutputStreamForOldFormat(),
                              null, false);
            
            identifier = "4";
            converter = new TGAToBMPFormatConverter();
            converter.setDestinationOutputStreamForNewFormat(outputStreamArray[3], internalLodResource4);
            extractor = new Extractor();
            extractor.convert(identifier, byteArray4,
                              converter.getSourceOutputStreamForOldFormat(),
                              null, false);
        }

        public void write(int b) throws IOException
        {
            memoryOutputStream.write(b);
        }
    }
    
    /**
     * Set output Stream where the new BMP format will be written
     * 
     * @param stream where the new converted format will be written
     */
    public void setDestinationOutputStreamForNewFormat(OutputStream stream, LodResource bitmapLodEntry) throws IOException
    {
        throw new RuntimeException("Unsupported operation.");
    }
    
    public class BMPToMultipleTGAConversionInputStream extends InputStream
    {
        private ByteArrayInputStream conversionInputStream = null;

        public class NullLodResource implements TGADataConsumer, LodResource
        {
            public NullLodResource()
            {
            }
            
            public byte[] getDataHeader()
            {
                return null;
            }

            public byte[] getData() throws IOException
            {
                return null;
            }

            public String getTextDescription()
            {
                return null;
            }

            public String getResourceType()
            {
                return null;
            }

            public String getName()
            {
                return null;
            }

            public void setImageWidth(int width)
            {
            }

            public void setImageHeight(int height)
            {
            }

            public void setImagePalette(int[] palette)
            {
            }
        }

        public BMPToMultipleTGAConversionInputStream(InputStream[] inputStreamArray, LodResource lodResource) throws IOException
        {
            super();

            NullLodResource nullLodResource = new NullLodResource();
            TGAToBMPFormatConverter converter = new TGAToBMPFormatConverter();
            
            byte data[] = readData(converter, inputStreamArray[0], lodResource);

            for (int i = 1; i < inputStreamArray.length; ++i)
            {
                converter = new TGAToBMPFormatConverter();
                byte moreData[] = readData(converter, inputStreamArray[i], nullLodResource);
                byte[] newData = new byte[data.length + moreData.length];
                System.arraycopy(data, 0, newData, 0, data.length);
                System.arraycopy(moreData, 0, newData, data.length, moreData.length);
                data = newData;
                
                inputStreamArray[i].close();
            }
            
            conversionInputStream = new ByteArrayInputStream(data);
        }
        
        private byte[] readData(FormatConverter converter, InputStream inputStream, LodResource lodResource) throws IOException {
            int length = 64;
            InputStream convertedInputStream = null;
            byte[] data = null;
            if (null == converter)
            {
                convertedInputStream = inputStream;
                
              data = new byte[length];
              int total = 0;
              while (total < length)
              {
                  int count = convertedInputStream.read(data, total, (length - total));
                  if (-1 == count)  break;
                    
                  total += count;
              }
            
            }
            else
            {
                converter.setSourceInputStreamForNewFormat(inputStream, lodResource);
                convertedInputStream = converter.getDestinationInputStreamForOldFormat();

                // TODO: recombine the data reading?
                data = new byte[length];
                int total = 0;
                int count = 0;
                while (-1 != count)
                {
                    if (total >= data.length)
                    {
                        byte[] newData = new byte[data.length * 2];
                        System.arraycopy(data, 0, newData, 0, data.length);
                        data = newData;
                    }
                
                    count = convertedInputStream.read(data, total, (data.length - total));
                    if (-1 == count)  break;
                    
                    total += count;
                }

                if (total != data.length)
                {
                    byte[] newData = new byte[total];
                    System.arraycopy(data, 0, newData, 0, total);
                    data = newData;
                }

            }


            return data;
        }
       
        public int read() throws IOException
        {
            return conversionInputStream.read();
        }

        public void close() throws IOException
        {
        }
    }
    
    public void setSourceInputStreamsForNewFormat(InputStream inputStreamArray[], LodResource lodResource) throws IOException
    {
        sourceInputStream = new BMPToMultipleTGAConversionInputStream(inputStreamArray, lodResource);
    }

    /**
     * Set input Stream where the new BMP format will be written
     * 
     * @param stream where the new converted format will be written
     */
    public void setSourceInputStreamForNewFormat(InputStream stream, LodResource tgaAcceptingLodEntry) throws IOException
    {
        throw new RuntimeException("Unsupported Operation.");
    }
}
