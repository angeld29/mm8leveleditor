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

import org.gamenet.application.mm8leveleditor.converter.FormatConverter;
import org.gamenet.application.mm8leveleditor.converter.MultipleTGAToBMPFormatConverter;
import org.gamenet.util.ByteConversions;

import com.mmbreakfast.unlod.lod.LodFile;

public class BitmapsLodEntry extends MM6LodEntry
{
    public BitmapsLodEntry(LodFile lodFile, long headerOffset)
   		throws IOException
   	{
    	super(lodFile, headerOffset);
    }

   public FormatConverter getFormatConverter()
    {
        if (getFileType().equals("4tga"))
            return new MultipleTGAToBMPFormatConverter();
        else return super.getFormatConverter();
    }

    public String getFormatConverterFileType()
    {
        if (getFileType().equals("tga"))
            return "bmp";
        else return super.getFormatConverterFileType();
    }

    protected String getFileType()
    {
        // TGA files inconsistently named
        // Tga images (and only tga images) have a non-zero image size
        int imageSize = ByteConversions.getIntegerInByteArrayAtPosition(dataHeader, getOffsetInDataHeaderForTGAImageSize());
        if (0 != imageSize)  return "4tga";
        
      String dataType = this.getDataType();
      if (null == dataType)  dataType = "";
      else dataType = dataType.toLowerCase();
      
        String fileType = getEntryName().toLowerCase();

        int dotPos = fileType.lastIndexOf('.');
        if (-1 == dotPos)  return dataType;
        if ((fileType.length() - 1) == dotPos)  return dataType;
        
        return fileType.substring(dotPos + 1).toLowerCase(); 
    }

}

