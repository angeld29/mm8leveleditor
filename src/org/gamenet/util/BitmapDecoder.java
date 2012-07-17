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


package org.gamenet.util;

import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BitmapDecoder
{
    public ImageProducer decode(int palette[], int aWidth, int aHeight, ByteArrayInputStream bytearrayinputstream)
        throws IOException, Exception
    {
    	int dataArray[] = new int[aWidth * aHeight];
		int dataIndex = 0;
		
		int anIntValue = -1;
		
		for (int h = 0; h < aHeight; ++h)
		{
			for (int w = 0; w < aWidth; ++w)
			{
				anIntValue = bytearrayinputstream.read();
				if (anIntValue != -1)
				{
					int red = palette[(anIntValue * 3) + 0];
					int green = palette[(anIntValue * 3) + 1];
					int blue = palette[(anIntValue * 3) + 2];
					
                    dataArray[dataIndex++] = (red * 256 * 256) | (green * 256) | blue;
				}
				else throw new Exception("Not enough data found, dataIndex=" + dataIndex + ", w=" + w + ", h=" + h);
			}
		}

 		ColorModel cm = new DirectColorModel(32, 0xFF0000, 0x00FF00, 0x0000FF);
		
		MemoryImageSource newMemoryImageSource = new MemoryImageSource(aWidth, aHeight, cm, dataArray, 0, aWidth);
		return newMemoryImageSource;
    }
}
