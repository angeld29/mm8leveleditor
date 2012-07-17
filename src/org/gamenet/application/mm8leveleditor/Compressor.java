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

package org.gamenet.application.mm8leveleditor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class Compressor
{
    public static void main(String[] argument)
	{
        try
        {
            if (argument.length == 4)
            {
                InputStream inputStream = new FileInputStream(argument[0]);
                
                OutputStream outputStream = new DeflaterOutputStream(new FileOutputStream(argument[1]), new Deflater(Integer.parseInt(argument[2]), Boolean.getBoolean(argument[3])));

                int value = inputStream.read();
                while (-1 != value)
                {
                    outputStream.write(value);
                    value = inputStream.read();
                }
            }
            else if (argument.length == 2)
            {
                for (int level = -1; level < 10; ++level)
                {
                    for (int nowrapInt = 0; nowrapInt < 2; ++nowrapInt)
                    {
                        boolean nowrap = (0 == nowrapInt);
                        InputStream inputStream = new FileInputStream(argument[0]);
                        
                        OutputStream outputStream = new DeflaterOutputStream(new FileOutputStream(argument[1] + "." + String.valueOf(nowrap) + "." + String.valueOf(level)), new Deflater(level, nowrap));

                        int value = inputStream.read();
                        while (-1 != value)
                        {
                            outputStream.write(value);
                            value = inputStream.read();
                        }
                        
                        outputStream.close();
                        inputStream.close();
                    }
                }
            }
            else
            {
                throw new RuntimeException("Usage: Compressor <uncompressed data file> <compressed data file> [<compression level> <nowrap>]");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
