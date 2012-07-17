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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.gamenet.application.mm8leveleditor.converter.FormatConverter;
import org.gamenet.application.mm8leveleditor.converter.NullFormatConverter;

public class FileBasedModifiedLodResource implements LodResource, TGADataProducer, SpriteTGADataProducer, TGADataConsumer, SpriteTGADataConsumer
{
    private File file = null;
    private FormatConverter converter = null;

    private int byteWidth = -1;
    private int byteHeight = -1;
    private int[] palette = null;
    
    private int attributeMask = -1;
    private int pixelNumber = -1;
    private int resolution = -1;
    private int ySkip = -1;

    public FileBasedModifiedLodResource(FormatConverter converter, File file)
    {
        super();

        this.file = file;
        this.converter = converter;
    }


    public File getFile()
    {
        return this.file;
    }

    private byte[] readData(FormatConverter converter, File file) {
        FileInputStream inputStream = null;
        try
        {
            if (null == converter)
            {
                converter = new NullFormatConverter();
            }

            InputStream convertedInputStream = null;
            if (converter.requiresMultipleStreams())
            {
                File outDir = file.getParentFile();
                // IMPLEMENT: replace basefilename
                String baseFilename = file.getName();
                String FILE_SUFFIX_0 = ".0.bmp";
                String FILE_SUFFIX_1 = ".1.bmp";
                String FILE_SUFFIX_2 = ".2.bmp";
                String FILE_SUFFIX_3 = ".3.bmp";
                if ( (false == baseFilename.endsWith(FILE_SUFFIX_0))
				  && (false == baseFilename.endsWith(FILE_SUFFIX_1))
				  && (false == baseFilename.endsWith(FILE_SUFFIX_2))
				  && (false == baseFilename.endsWith(FILE_SUFFIX_3)) )
                    throw converter.new UnsupportedFileFormatException("Unable to load " + file.getAbsolutePath());

                baseFilename = baseFilename.substring(0, baseFilename.length() - FILE_SUFFIX_0.length());
                
                String[] filenames = converter.getSuggestedFilenames(baseFilename);
                InputStream[] inputStreamArray = new InputStream[filenames.length];
                for (int i = 0; i < inputStreamArray.length; ++i)
                {
                    File aFile = new File(outDir, filenames[i]);
                    if ((false == aFile.exists()) || (false == aFile.isFile()) )
                        throw converter.new UnsupportedFileFormatException("Unable to load " + aFile.getAbsolutePath());
                    
                    inputStreamArray[i] = new FileInputStream(aFile);
                }
                
                try
                {
                    converter.setSourceInputStreamsForNewFormat(inputStreamArray, this);
                }
                catch (FormatConverter.UnsupportedFileFormatException e1)
                {
                    for (int i = 0; i < inputStreamArray.length; ++i)
                    {
                        inputStreamArray[i].close();
                    }
                    throw converter.new UnsupportedFileFormatException("Unable to load " + file.getAbsolutePath(), e1);
                }
                convertedInputStream = converter.getDestinationInputStreamForOldFormat();
            }
            else
            {
                inputStream = new FileInputStream(file);
                try
                {
                    converter.setSourceInputStreamForNewFormat(inputStream, this);
                }
                catch (FormatConverter.UnsupportedFileFormatException e1)
                {
                    inputStream.close();
                    throw converter.new UnsupportedFileFormatException("Unable to load " + file.getAbsolutePath(), e1);
                }
                convertedInputStream = converter.getDestinationInputStreamForOldFormat();
            }



            byte[] data = null;
 
            int length = 64;
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


            return data;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                if (null != inputStream)
                    inputStream.close();
            }
            catch (IOException exception)
            {
                exception.printStackTrace();
            }
        }
    }
   
    private byte[] readDataOld(FormatConverter converter, File file) {
        int length = (int)file.length();
        FileInputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(file);

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
                try
                {
                    converter.setSourceInputStreamForNewFormat(inputStream, this);
                }
                catch (FormatConverter.UnsupportedFileFormatException e1)
                {
                    inputStream.close();
                    throw converter.new UnsupportedFileFormatException("Unable to load " + file.getAbsolutePath(), e1);
                }

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
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                if (null != inputStream)
                    inputStream.close();
            }
            catch (IOException exception)
            {
                exception.printStackTrace();
            }
        }
    }
   
    public byte[] getData() {
        return readData(converter, file);
    }

    public int getByteWidth()
    {
        return byteWidth;
    }

    public int getByteHeight()
    {
        return byteHeight;
    }

    public int[] getPalette() throws IOException
    {
        return palette;
    }


    public byte[] getDataHeader()
    {
        throw new RuntimeException("unimplemented");
    }

	public int getAttributeMask() {
		return attributeMask;
	}

	public int getPixelNumber() {
		return pixelNumber;
	}

	public int getResolution() {
		return resolution;
	}

	public int getYSkip() {
		return ySkip;
	}



    public void setImageWidth(int width)
    {
        this.byteWidth = width;
    }

    public void setImageHeight(int height)
    {
        this.byteHeight = height;
    }

    public void setImagePalette(int[] palette)
    {
        this.palette = palette;
    }


	public void setAttributeMask(int attributeMask) {
        this.attributeMask = attributeMask;
	}

	public void setPixelNumber(int pixelNumber) {
        this.pixelNumber = pixelNumber;
	}

	public void setResolution(int resolution) {
        this.resolution = resolution;
	}

	public void setYSkip(int ySkip) {
        this.ySkip = ySkip;
	}



    public String getTextDescription()
    {
        throw new RuntimeException("unimplemented");
    }

    public String getResourceType()
    {
        throw new RuntimeException("unimplemented");
    }

    public String getName()
    {
        return getFile().getName();
    }
}
