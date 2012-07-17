/*
 * Copyright (c) 2009 (Mike) Maurice Kienenberger (mkienenb@gmail.com)
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

package org.gamenet.application.mm8leveleditor.data.mm6.fileFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Txt
{
	private String fileName = null;
    // text string lookup Table
    protected List<List<String>> lineList = new ArrayList<List<String>>();

	public Txt()
    {
        super();
    }

    public Txt(String fileName)
    {
        super();
        
        this.fileName = fileName;
    }

    public int initialize(byte dataSrc[], int offset)
    {
    	this.lineList = new ArrayList<List<String>>();
    	return populateObjects(dataSrc, offset, this.lineList);
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
    	// TODO: Probably could just verify that the last byte in the overall string was zero
    	
    	List<List<String>> temporaryLineList = new ArrayList<List<String>>();
    	
    	// Read in strings, zero terminated, one after another
    	int dataLeft = data.length - offset;
        String textFileContents = new String(data, offset, dataLeft);
		int textFileIndex = 0;
		List<String> columnList = new ArrayList<String>();
		temporaryLineList = new ArrayList<List<String>>();
		StringBuffer textBuffer = new StringBuffer();
		while (textFileIndex < textFileContents.length())
		{
			char textChar = textFileContents.charAt(textFileIndex++);
			
			if (9 == textChar)
			{
				columnList.add(textBuffer.toString());
				textBuffer = new StringBuffer();
			}
			else if (13 == textChar)
			{
				columnList.add(textBuffer.toString());
				textBuffer = new StringBuffer();
				
				temporaryLineList.add(columnList);
				columnList = new ArrayList<String>();
				
				if (textFileIndex < textFileContents.length())
				{
					char nextTextChar = textFileContents.charAt(textFileIndex);
					if (10 == nextTextChar)
					{
						textFileIndex++;
					}
				}
			}
			else
			{
				textBuffer.append(textChar);
			}
		}

        
        return textBuffer.length() == 0;
    }
    
    public static int populateObjects(byte[] data, int offset, List<List<String>> lineList)
    {
    	// Read in strings, zero terminated, one after another
    	int dataLeft = data.length - offset;
        String textFileContents = new String(data, offset, dataLeft);
		int textFileIndex = 0;
		List<String> columnList = new ArrayList<String>();
		StringBuffer textBuffer = new StringBuffer();
		while (textFileIndex < textFileContents.length())
		{
			char textChar = textFileContents.charAt(textFileIndex++);
			
			if (9 == textChar)
			{
				columnList.add(textBuffer.toString());
				textBuffer = new StringBuffer();
			}
			else if (13 == textChar)
			{
				columnList.add(textBuffer.toString());
				textBuffer = new StringBuffer();
				
				lineList.add(columnList);
				columnList = new ArrayList<String>();
				
				if (textFileIndex < textFileContents.length())
				{
					char nextTextChar = textFileContents.charAt(textFileIndex);
					if (10 == nextTextChar)
					{
						textFileIndex++;
					}
				}
			}
			else
			{
				textBuffer.append(textChar);
			}
		}
        
        return offset + dataLeft;
    }
    
    public static byte[] updateData(List<List<String>> lineList)
    {
    	// Write out strings, zero terminated, one after another
		StringBuffer textBuffer = new StringBuffer();

		Iterator<List<String>> lineIterator = lineList.iterator();
        while (lineIterator.hasNext())
        {
            List<String> columnList = lineIterator.next();
            
            Iterator<String> columnIterator = columnList.iterator();
            while (columnIterator.hasNext())
            {
                String value = columnIterator.next();
                if (null != value)  textBuffer.append(value);
                
                if (columnIterator.hasNext())  textBuffer.append((char)9);
            }
            textBuffer.append((char)13);
            textBuffer.append((char)10);
        }
        
        return textBuffer.toString().getBytes();
    }
    
    public static int updateData(byte[] newData, int offset, List<List<String>> stringsList)
    {
    	byte[] stringBytes = updateData(stringsList);
    	
        System.arraycopy(stringBytes, 0, newData, offset, stringBytes.length);
        
        return offset + stringBytes.length;
    }

	public List<List<String>> getLineList() {
		return this.lineList;
	}

	public void setLineList(List<List<String>> lineList) {
		this.lineList = lineList;
	}

	public String getStringForMatchingColumnValue(int targetStringColumn, int matchColumn, String matchValue) {
		Iterator<List<String>> lineIterator = lineList.iterator();
        while (lineIterator.hasNext())
        {
            List<String> columnList = lineIterator.next();
            if (matchValue.equals(columnList.get(matchColumn)))
    		{
            	return columnList.get(targetStringColumn);
    		}
        }
        
        return null;
	}
}
