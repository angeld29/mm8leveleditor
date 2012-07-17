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

import java.io.IOException;
import java.io.InputStream;

import javax.media.Time;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.FileTypeDescriptor;
import javax.media.protocol.InputSourceStream;
import javax.media.protocol.PullDataSource;
import javax.media.protocol.PullSourceStream;

public class WavInputStreamDataSource extends PullDataSource
{
	static private final ContentDescriptor waveContentDescriptor = new FileTypeDescriptor(FileTypeDescriptor.WAVE);

	private InputStream inputStream = null;
						
	public WavInputStreamDataSource()
	{
		super();
	}

	public WavInputStreamDataSource(InputStream anInputStream)
	{
		this();
        
		setInputStream(anInputStream);
	}

	public void setInputStream(InputStream anInputStream)
	{
		inputStream = anInputStream;
	}
					
	public PullSourceStream[] getStreams()
	{
		PullSourceStream aPullSourceStream[] = new PullSourceStream[1];
		aPullSourceStream[0] = new InputSourceStream(inputStream, waveContentDescriptor);
		return aPullSourceStream;
	}

    public void connect() throws IOException
    {
		// do nothing
    }

    public void disconnect()
    {
		// do nothing
    }

    public String getContentType()
    {
    	return FileTypeDescriptor.WAVE;
    }

    public Object getControl(String aControlName)
    {
    	return null;
    }

    public Object[] getControls()
    {
    	return null;
    }

    public Time getDuration()
    {
    	return DURATION_UNKNOWN;
    }

    public void start() throws IOException
    {
		// do nothing
    }

    public void stop() throws IOException
    {
    	// do nothing
    }
}
