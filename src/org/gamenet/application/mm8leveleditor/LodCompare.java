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
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import org.gamenet.application.mm8leveleditor.lod.LodEntry;
import org.gamenet.application.mm8leveleditor.lod.LodFileLoader;

import com.mmbreakfast.unlod.lod.InvalidLodFileException;
import com.mmbreakfast.unlod.lod.LodFile;

public class LodCompare
{
    public static void main(String[] strings)
	{
		File firstFile = new File(strings[0]);
		File secondFile = new File(strings[1]);
		
		LodFile firstLodFile = null;
		try
        {
            firstLodFile = LodFileLoader.tryKnownFileNames(firstFile);
        }
        catch (InvalidLodFileException e)
        {
            e.printStackTrace();
            System.err.println("Invalid LOD file " + firstFile.getName());
        }
        catch (IOException e)
        {
			System.err.println("Unable to load " + firstFile.getName());
            e.printStackTrace();
        }
        
		LodFile secondLodFile = null;
		try
        {
            secondLodFile = LodFileLoader.tryKnownFileNames(secondFile);
        }
		catch (InvalidLodFileException e)
		{
			e.printStackTrace();
			System.err.println("Invalid LOD file " + firstFile.getName());
		}
		catch (IOException e)
		{
			System.err.println("Unable to load " + firstFile.getName());
			e.printStackTrace();
		}
		
		byte firstFileHeader[] = firstLodFile.getFileHeader();
		byte secondFileHeader[] = secondLodFile.getFileHeader();
		
		int minSize = (firstFileHeader.length > secondFileHeader.length) ? secondFileHeader.length : firstFileHeader.length;
		
		for (int i = 0; i < minSize; ++i)
		{
			if (firstFileHeader[i] != secondFileHeader[i])
			{
				System.err.println(String.valueOf(i) + ": " + toDisplayString(firstFileHeader, i) + " - " + firstFile.getName());
				System.err.println(String.valueOf(i) + ": " + toDisplayString(secondFileHeader, i) + " - " + secondFile.getName());
				System.err.println();
			}
		}

		if (firstFileHeader.length >= minSize)
		{
			for (int i = minSize; i < firstFileHeader.length; ++i)
			{
				if (firstFileHeader[i] != secondFileHeader[i])
				{
					System.err.println(String.valueOf(i) + ": " + toDisplayString(firstFileHeader, i) + " - " + firstFile.getName());
					System.err.println(String.valueOf(i) + ": null" + " - " + secondFile.getName());
					System.err.println();
				}
			}
		}

		if (secondFileHeader.length >= minSize)
		{
			for (int i = minSize; i < secondFileHeader.length; ++i)
			{
				if (secondFileHeader[i] != secondFileHeader[i])
				{
					System.err.println(String.valueOf(i) + ": null" + " - " + firstFile.getName());
					System.err.println(String.valueOf(i) + ": " + toDisplayString(secondFileHeader, i) + " - " + secondFile.getName());
					System.err.println();
				}
			}
		}
		
		Map firstEntryMap = firstLodFile.getLodEntries();
		Map secondEntryMap = secondLodFile.getLodEntries();
		
		Comparator entryNameComparator = new Comparator()
		{
			public int compare(Object o1, Object o2)
			{
				LodEntry le1 = (LodEntry)o1;
				LodEntry le2 = (LodEntry)o2;
				return le1.getEntryName().toLowerCase().compareTo(le2.getEntryName().toLowerCase());
			}
		};

		org.gamenet.application.mm8leveleditor.lod.LodEntry[] firstEntriesArray = (LodEntry[])firstEntryMap.values().toArray(new LodEntry[0]);
		Arrays.sort(firstEntriesArray, entryNameComparator);

		LodEntry[] secondEntriesArray = (LodEntry[])secondEntryMap.values().toArray(new LodEntry[0]);
		Arrays.sort(secondEntriesArray, entryNameComparator);

		int firstEntriesArrayIndex = 0;
		int secondEntriesArrayIndex = 0;
		while ( (firstEntriesArrayIndex < firstEntriesArray.length)
		 	 && (secondEntriesArrayIndex < secondEntriesArray.length) )
		{
			LodEntry firstLodEntry = (LodEntry)firstEntriesArray[firstEntriesArrayIndex];
			LodEntry secondLodEntry = (LodEntry)secondEntriesArray[secondEntriesArrayIndex];

			int result = firstLodEntry.getEntryName().toLowerCase().compareTo(secondLodEntry.getEntryName().toLowerCase());
			
			if (result < 0)
			{
				++firstEntriesArrayIndex;

				System.err.println(firstLodEntry.getEntryName() + " only exists in " + firstFile.getName());
				System.err.println();
			}
			else if (result > 0)
			{
				++secondEntriesArrayIndex;

				System.err.println(secondLodEntry.getEntryName() + " only exists in " + secondFile.getName());
				System.err.println();
			}
			else
			{
				++firstEntriesArrayIndex;
				++secondEntriesArrayIndex;

				byte firstEntryHeader[] = firstLodEntry.getEntryHeader();
				byte secondEntryHeader[] = secondLodEntry.getEntryHeader();
		
				int entryHeaderMinSize = (firstEntryHeader.length > secondEntryHeader.length) ? secondEntryHeader.length : firstEntryHeader.length;
		
				for (int i = 0; i < entryHeaderMinSize; ++i)
				{
					if (firstEntryHeader[i] != secondEntryHeader[i])
					{
						System.err.println(String.valueOf(i) + ": " + toDisplayString(firstEntryHeader, i) + " - " + firstFile.getName() + ":" + firstLodEntry.getEntryName());
						System.err.println(String.valueOf(i) + ": " + toDisplayString(secondEntryHeader, i) + " - " + secondFile.getName() + ":" + secondLodEntry.getEntryName());
						System.err.println();
					}
				}

				if (firstEntryHeader.length >= entryHeaderMinSize)
				{
					for (int i = entryHeaderMinSize; i < firstEntryHeader.length; ++i)
					{
						if (firstEntryHeader[i] != secondEntryHeader[i])
						{
							System.err.println(String.valueOf(i) + ": " + toDisplayString(firstEntryHeader, i) + " - " + firstFile.getName() + ":" + firstLodEntry.getEntryName());
							System.err.println(String.valueOf(i) + ": null" + " - " + secondFile.getName() + ":" + secondLodEntry.getEntryName());
							System.err.println();
						}
					}
				}

				if (secondEntryHeader.length >= entryHeaderMinSize)
				{
					for (int i = entryHeaderMinSize; i < secondEntryHeader.length; ++i)
					{
						if (secondEntryHeader[i] != secondEntryHeader[i])
						{
							System.err.println(String.valueOf(i) + ": null" + " - " + firstFile.getName() + ":" + firstLodEntry.getEntryName());
							System.err.println(String.valueOf(i) + ": " + toDisplayString(secondEntryHeader, i) + " - " + secondFile.getName() + ":" + secondLodEntry.getEntryName());
							System.err.println();
						}
					}
				}
								
				// todo: compare data header
				byte firstDataHeader[] = firstLodEntry.getDataHeader();
				byte secondDataHeader[] = secondLodEntry.getDataHeader();
		
				if ( (null != firstDataHeader) && (null != secondDataHeader) )
				{
					int dataHeaderMinSize = (firstDataHeader.length > secondDataHeader.length) ? secondDataHeader.length : firstDataHeader.length;
		
					for (int i = 0; i < dataHeaderMinSize; ++i)
					{
						if (firstDataHeader[i] != secondDataHeader[i])
						{
							System.err.println(String.valueOf(i) + ": " + toDisplayString(firstDataHeader, i) + " - " + firstFile.getName() + ":" + firstLodEntry.getEntryName() + ":" + firstLodEntry.getDataName());
							System.err.println(String.valueOf(i) + ": " + toDisplayString(secondDataHeader, i) + " - " + secondFile.getName() + ":" + secondLodEntry.getEntryName() + ":" + secondLodEntry.getDataName());
							System.err.println();
						}
					}

					if (firstDataHeader.length >= dataHeaderMinSize)
					{
						for (int i = dataHeaderMinSize; i < firstDataHeader.length; ++i)
						{
							if (firstDataHeader[i] != secondDataHeader[i])
							{
								System.err.println(String.valueOf(i) + ": " + toDisplayString(firstDataHeader, i) + " - " + firstFile.getName() + ":" + firstLodEntry.getEntryName() + ":" + firstLodEntry.getDataName());
								System.err.println(String.valueOf(i) + ": null" + " - " + secondFile.getName() + ":" + secondLodEntry.getEntryName() + ":" + secondLodEntry.getDataName());
								System.err.println();
							}
						}
					}

					if (secondDataHeader.length >= dataHeaderMinSize)
					{
						for (int i = dataHeaderMinSize; i < secondDataHeader.length; ++i)
						{
							if (secondDataHeader[i] != secondDataHeader[i])
							{
								System.err.println(String.valueOf(i) + ": null" + " - " + firstFile.getName() + ":" + firstLodEntry.getEntryName() + ":" + firstLodEntry.getDataName());
								System.err.println(String.valueOf(i) + ": " + toDisplayString(secondDataHeader, i) + " - " + secondFile.getName() + ":" + secondLodEntry.getEntryName() + ":" + secondLodEntry.getDataName());
								System.err.println();
							}
						}
					}
				}
								
				
				// todo: compare data content
			}
		}

		if (firstEntriesArrayIndex < firstEntriesArray.length)
		{
			for (int i = firstEntriesArrayIndex; i < firstEntriesArray.length; ++i)
			{
				LodEntry firstLodEntry = (LodEntry)firstEntriesArray[i];
				System.err.println(firstLodEntry.getEntryName() + " only exists in " + firstFile.getName());
				System.err.println();
			}
			
		}

		if (secondEntriesArrayIndex < secondEntriesArray.length)
		{
			for (int i = secondEntriesArrayIndex; i < secondEntriesArray.length; ++i)
			{
				LodEntry secondLodEntry = (LodEntry)secondEntriesArray[i];
				System.err.println(secondLodEntry.getEntryName() + " only exists in " + secondFile.getName());
				System.err.println();
			}
			
		}
    }
    
    static public String toDisplayString(byte byteArray[], int index)
    {
    	// Byte values
		byte aByte = byteArray[index];

		int anInt1 = aByte >= 0 ? aByte : (256 + ((int)aByte));
    	
		String byteString = Integer.toHexString(anInt1);
		while (byteString.length() < 2)  byteString = "0" + byteString;
		
		String intString = String.valueOf(anInt1);
		while (intString.length() < 3)  intString = "0" + intString;
		
		String charString = (new Character((char)anInt1)).toString();
		
		if ((index + 1) < byteArray.length)
		{
			// short values
			byte aByte2 = byteArray[index + 1];
			int anInt2 = aByte2 >= 0 ? aByte2 : (256 + ((int)aByte2));
			int shortInt = anInt1 + (anInt2 << 8);
   	
			String shortByteString = Integer.toHexString(shortInt);
			while (shortByteString.length() < 4)  shortByteString = "0" + shortByteString;
		
			String shortIntString = String.valueOf(shortInt);
			while (shortIntString.length() < 5)  shortIntString = "0" + shortIntString;

			if ((index + 4) < byteArray.length)
			{
				// short values
				byte aByte3 = byteArray[index + 2];
				byte aByte4 = byteArray[index + 3];
				int anInt3 = aByte3 >= 0 ? aByte3 : (256 + ((int)aByte3));
				int anInt4 = aByte4 >= 0 ? aByte4 : (256 + ((int)aByte4));
				long longInt = shortInt + (anInt3 << 16) + (anInt4 << 24);
   	
				String longByteString = Long.toHexString(longInt);
				while (longByteString.length() < 8)  longByteString = "0" + longByteString;
		
				String longIntString = String.valueOf(longInt);
				while (longIntString.length() < 10)  longIntString = "0" + longIntString;

				return byteString + "," + intString + "," + charString + "  " + shortByteString + "," + shortIntString + "  " + longByteString + "," + longIntString;
			}

			return byteString + "," + intString + "," + charString + "  " + shortByteString + "," + shortIntString;
		}

		return byteString + "," + intString + "," + charString;
    }
}
