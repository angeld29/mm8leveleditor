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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;

public class BinaryFileCompare
{
    public static void compare(PrintStream out, byte data1[], byte data2[])
	{
		ByteArrayInputStream inputStream1 = new ByteArrayInputStream(data1);
		ByteArrayInputStream inputStream2 = new ByteArrayInputStream(data1);
		int inputLength1 = data1.length;
		int inputLength2 = data2.length;
		int minSize = (inputLength1 > inputLength2) ? inputLength2 : inputLength1;
		
		for (long i = 0; i < minSize; ++i)
		{
			int firstData = inputStream1.read();
			int secondData = inputStream2.read();
			if (firstData != secondData)
			{
			    out.println(String.valueOf(i) + ": " + toDisplayString(inputStream1, i) + " - " + "data1");
			    out.println(String.valueOf(i) + ": " + toDisplayString(inputStream2, i) + " - " + "data2");
			    out.println();
			}
		}

		if (inputLength1 >= minSize)
		{
			for (long i = minSize; i < inputLength1; ++i)
			{
				int firstData = inputStream1.read();
				out.println(String.valueOf(i) + ": " + toDisplayString(inputStream1, i) + " - " + "data1");
				out.println(String.valueOf(i) + ": null" + " - " + "data2");
				out.println();
			}
		}

		if (inputLength2 >= minSize)
		{
			for (long i = minSize; i < inputLength2; ++i)
			{
				int secondData = inputStream2.read();
				out.println(String.valueOf(i) + ": null" + " - " + "data1");
				out.println(String.valueOf(i) + ": " + toDisplayString(inputStream2, i) + " - " + "data2");
				out.println();
			}
		}
    }
    
    public static void main(String[] strings) throws IOException
	{
		File firstFile = new File(strings[0]);
		File secondFile = new File(strings[1]);
		
		System.out.println("Comparing " + firstFile + " and " + secondFile);
		System.out.println();
		
		RandomAccessFile firstRandomAccessFile = null;
		try
		{
			firstRandomAccessFile = new RandomAccessFile(firstFile, "r");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.err.println("File Not Found: " + firstFile.getName());
		}
        
		RandomAccessFile secondRandomAccessFile = null;
		try
		{
			secondRandomAccessFile = new RandomAccessFile(secondFile, "r");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.err.println("File Not Found: " + secondFile.getName());
		}
        
		long firstRandomAccessFileLength = firstRandomAccessFile.length();
		long secondRandomAccessFileLength = secondRandomAccessFile.length();
		long minSize = (firstRandomAccessFileLength > secondRandomAccessFileLength) ? secondRandomAccessFileLength : firstRandomAccessFileLength;
		
		for (long i = 0; i < minSize; ++i)
		{
			int firstData = firstRandomAccessFile.read();
			int secondData = secondRandomAccessFile.read();
			if (firstData != secondData)
			{
				System.err.println(String.valueOf(i) + ": " + toDisplayString(firstRandomAccessFile, i) + " - " + firstFile.getName());
				System.err.println(String.valueOf(i) + ": " + toDisplayString(secondRandomAccessFile, i) + " - " + secondFile.getName());
				System.err.println();
			}
		}

		if (firstRandomAccessFileLength >= minSize)
		{
			for (long i = minSize; i < firstRandomAccessFileLength; ++i)
			{
				int firstData = firstRandomAccessFile.read();
				System.err.println(String.valueOf(i) + ": " + toDisplayString(firstRandomAccessFile, i) + " - " + firstFile.getName());
				System.err.println(String.valueOf(i) + ": null" + " - " + secondFile.getName());
				System.err.println();
			}
		}

		if (secondRandomAccessFileLength >= minSize)
		{
			for (long i = minSize; i < secondRandomAccessFileLength; ++i)
			{
				int secondData = secondRandomAccessFile.read();
				System.err.println(String.valueOf(i) + ": null" + " - " + firstFile.getName());
				System.err.println(String.valueOf(i) + ": " + toDisplayString(secondRandomAccessFile, i) + " - " + secondFile.getName());
				System.err.println();
			}
		}
    }
    
    static public String toDisplayString(RandomAccessFile aFile, long position)
    {
		byte byteArray[] = new byte[4];
		int index = 0;

		int bytesRead = 0;
        try
        {
			long originalPos = aFile.getFilePointer();
			aFile.seek(position);
			bytesRead = aFile.read(byteArray, 0, 4);
			if (-1 == bytesRead)  throw new IOException("Unexpected end of file.");
			aFile.seek(originalPos);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return toDisplayString(byteArray, bytesRead);
    }
    
    static public String toDisplayString(InputStream inputStream, long position)
    {
		byte byteArray[] = new byte[4];

		int bytesRead = 0;
        try
        {
			inputStream.mark(4);
			bytesRead = inputStream.read(byteArray, 0, 4);
			if (4 != bytesRead)  throw new IOException("Unexpected end of stream.");
			inputStream.reset();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return toDisplayString(byteArray, bytesRead);
    }
    
    static public String toDisplayString(byte byteArray[], int bytesRead)
    {
		int index = 0;

		// Byte values
		byte aByte = byteArray[index];

		int anUnsignedInt1 = aByte >= 0 ? aByte : (256 + ((int)aByte));
    	
		String byteString = Integer.toHexString(anUnsignedInt1);
		while (byteString.length() < 2)  byteString = "0" + byteString;
		
		String unsignedIntString = String.valueOf(anUnsignedInt1);
		while (unsignedIntString.length() < 3)  unsignedIntString = " " + unsignedIntString;
		
		int aSignedInt1 = aByte;
		String signedIntString = String.valueOf(aSignedInt1);
		while (signedIntString.length() < 4)  signedIntString = " " + signedIntString;
	
		String charString = (new Character((char)anUnsignedInt1)).toString();
		if (0 == anUnsignedInt1)  charString = ""; // 0 messes up cut-and-paste
		
		if ((index + 1) < bytesRead)
		{
			// short values
			byte aByte2 = byteArray[index + 1];
			int anInt2 = aByte2 >= 0 ? aByte2 : (256 + ((int)aByte2));
			int shortInt = anUnsignedInt1 + (anInt2 << 8);
   	
			String shortByteString = Integer.toHexString(shortInt);
			while (shortByteString.length() < 4)  shortByteString = "0" + shortByteString;
		
			String unsignedShortIntString = String.valueOf(shortInt);
			while (unsignedShortIntString.length() < 5)  unsignedShortIntString = " " + unsignedShortIntString;

			int aSignedShortInt = shortInt <= Short.MAX_VALUE ? shortInt : shortInt - ((Short.MAX_VALUE * 2) + 2);
			String signedShortIntString = String.valueOf(aSignedShortInt);
			while (signedShortIntString.length() < 6)  signedShortIntString = " " + signedShortIntString;
		
			if ((index + 3) < bytesRead)
			{
				// short values
				byte aByte3 = byteArray[index + 2];
				byte aByte4 = byteArray[index + 3];
				int anInt3 = aByte3 >= 0 ? aByte3 : (256 + ((int)aByte3));
				int anInt4 = aByte4 >= 0 ? aByte4 : (256 + ((int)aByte4));
				long longInt = ((long)shortInt) + (((long)anInt3) << 16) + (((long)anInt4) << 24);
   	
				String longByteString = Long.toHexString(longInt);
				// truncate leading FFs until we only have 8 bytes in the case of a negative
				// if (longByteString.length() > 8)  longByteString = longByteString.substring(longByteString.length() - 8);
				while (longByteString.length() < 8)  longByteString = "0" + longByteString;
		
				String unsignedLongIntString = String.valueOf(longInt);
				while (unsignedLongIntString.length() < 11)  unsignedLongIntString = " " + unsignedLongIntString;

				long aSignedLongInt = longInt <= Integer.MAX_VALUE ? longInt : longInt - ((((long)Integer.MAX_VALUE) * 2L) + 2L);
				String signedLongIntString = String.valueOf(aSignedLongInt);
				while (signedLongIntString.length() < 11)  signedLongIntString = " " + signedLongIntString;
		
				return "0x" + byteString + "," + unsignedIntString + "," + signedIntString + "  " + "0x" + shortByteString + "," + unsignedShortIntString + "," + signedShortIntString + "  " + "0x" + longByteString + "," + unsignedLongIntString + "," + signedLongIntString + " [" + charString + "]";
			}

			return "0x" + byteString + "," + unsignedIntString + "," + signedIntString + "," + charString + "  " + "0x" + shortByteString + "," + unsignedShortIntString + "," + signedShortIntString + " [" + charString + "]";
		}

		return "0x" + byteString + "," + unsignedIntString + "," + signedIntString + " [" + charString + "]";
    }
}
