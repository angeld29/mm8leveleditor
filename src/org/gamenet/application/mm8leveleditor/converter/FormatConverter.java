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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.gamenet.application.mm8leveleditor.lod.LodResource;

/**
 * @author mlk
 * 
 * Default implementation does nothing
 */
public abstract class FormatConverter
{
    public class UnsupportedFileFormatException extends IOException {
        public UnsupportedFileFormatException(String msg) { super(msg); }
        public UnsupportedFileFormatException(String msg, Throwable cause) { super(msg); initCause(cause); }
    };

    protected OutputStream sourceOutputStream = null;
    protected InputStream sourceInputStream = null;
    
    public FormatConverter()
    {
        super();
    }

    public boolean isFileNameComponentForLodEntryFileName(String fileName, String LodEntryFileName)
    {
        return LodEntryFileName.equals(fileName);
    }
    
    public boolean requiresMultipleStreams()
    {
        return false;
    }
    
    public int getRequiredStreamCount()
    {
        return 1;
    }
    
    public String[] getSuggestedFilenames(String baseFilename)
    {
        return new String[] { baseFilename } ;
    }
    
    public void setDestinationOutputStreamsForNewFormat(OutputStream[] outputStreamArray, LodResource lodResource) throws IOException
    {
        this.setDestinationOutputStreamForNewFormat(outputStreamArray[0], lodResource);
    }
    
    /**
     * Set output Stream where the new converted format will be written
     * 
     * @param stream where the new converted format will be written
     */
    public abstract void setDestinationOutputStreamForNewFormat(OutputStream stream, LodResource lodResource) throws IOException;

    /**
     * Set output Stream where the original format will be written
     * 
     * @param stream where the original format will be written
     */
    public OutputStream getSourceOutputStreamForOldFormat()
    {
        return sourceOutputStream;
    }


    public void setSourceInputStreamsForNewFormat(InputStream inputStreamArray[], LodResource lodResource) throws IOException
    {
        setSourceInputStreamForNewFormat(inputStreamArray[0], lodResource);
    }

    /**
     * Set input stream from which the new converted format will be read
     * 
     * @param stream from which the new converted format will be read
     */
    public abstract void setSourceInputStreamForNewFormat(InputStream stream, LodResource lodResource) throws IOException;

    /**
     * Set input Stream from which the original format can be read
     * 
     * @param stream from which the original format can be read
     */
    public InputStream getDestinationInputStreamForOldFormat()
    {
        return sourceInputStream;
    }
}

