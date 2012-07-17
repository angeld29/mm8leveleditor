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

public class Str
{
	private String fileName = null;
    // text string lookup Table
    private List<String> stringsList = new ArrayList<String>();

	public Str()
    {
        super();
    }

    public Str(String fileName)
    {
        super();
        
        this.fileName = fileName;
    }

    public int initialize(byte dataSrc[], int offset)
    {
    	stringsList = new ArrayList<String>();
    	return populateObjects(dataSrc, offset, stringsList);
    }

    public static boolean checkDataIntegrity(byte[] data, int offset, int expectedNewOffset)
    {
    	// TODO: Probably could just verify that the last byte in the overall string was zero
    	
    	List<String> temporaryStringsList = new ArrayList<String>();
    	
    	// Read in strings, zero terminated, one after another
    	int dataLeft = data.length - offset;
        String textFileContents = new String(data, offset, dataLeft);
		int textFileIndex = 0;
		StringBuffer textBuffer = new StringBuffer();
		while (textFileIndex < textFileContents.length())
		{
			char textChar = textFileContents.charAt(textFileIndex++);
			
			if (0 == textChar)
			{
				temporaryStringsList.add(textBuffer.toString());
				textBuffer = new StringBuffer();
			}
			else
			{
				textBuffer.append(textChar);
			}
		}
        
        return textBuffer.length() == 0;
    }
    
    public static int populateObjects(byte[] data, int offset, List<String> stringsList)
    {
    	// Read in strings, zero terminated, one after another
    	int dataLeft = data.length - offset;
        String textFileContents = new String(data, offset, dataLeft);
		int textFileIndex = 0;
		StringBuffer textBuffer = new StringBuffer();
		while (textFileIndex < textFileContents.length())
		{
			char textChar = textFileContents.charAt(textFileIndex++);
			
			if (0 == textChar)
			{
			    stringsList.add(textBuffer.toString());
				textBuffer = new StringBuffer();
			}
			else
			{
				textBuffer.append(textChar);
			}
		}
        
        return offset + dataLeft;
    }
    
    public static byte[] updateData(List<String> stringsList)
    {
    	// Write out strings, zero terminated, one after another
        StringBuffer textBuffer = new StringBuffer();
        Iterator<String> stringIterator = stringsList.iterator();
        while (stringIterator.hasNext()) {
			String string = stringIterator.next();
            textBuffer.append(string);
            textBuffer.append((char)0);
        }

		return textBuffer.toString().getBytes();
    }
    
    public static int updateData(byte[] newData, int offset, List<String> stringsList)
    {
    	byte[] stringBytes = updateData(stringsList);
    	
        System.arraycopy(stringBytes, 0, newData, offset, stringBytes.length);
        
        return offset + stringBytes.length;
    }

    public List<String> getStringsList() {
		return stringsList;
	}

	public void setStringsList(List<String> stringsList) {
		this.stringsList = stringsList;
	}
}
