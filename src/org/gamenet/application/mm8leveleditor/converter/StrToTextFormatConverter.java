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
 */
public class StrToTextFormatConverter extends FormatConverter
{
    public StrToTextFormatConverter()
    {
        super();
    }

    public class StrToTextConversionOutputStream extends OutputStream
    {
        OutputStream out = null;
        
        public StrToTextConversionOutputStream(OutputStream out) throws IOException
        {
            super();
            
            this.out = out;
        }
        
        public void close() throws IOException
        {
            out.flush();
            out.close();
            super.close();
        }

        public void write(int b) throws IOException
        {
            if (0 == b)
            {
                out.write('\r');
                out.write('\n');
            }
            else out.write(b);
        }
    }

    public void setDestinationOutputStreamForNewFormat(
        OutputStream stream,
        LodResource anEntry)
        throws IOException
    {
        sourceOutputStream = new StrToTextConversionOutputStream(stream);
    }

    public class TextToStrConversionInputStream extends InputStream
    {
        private InputStream in = null;
        private boolean hasBufferedValue = false;
        private int bufferedValue = 0;
        
        public TextToStrConversionInputStream(InputStream in) throws IOException
        {
            super();
            
            this.in = in;
        }
        
        public void close() throws IOException
        {
            in.close();
            super.close();
        }

        public int read() throws IOException
        {
            // translate /r/n into 0
            
            int value = 0;
            
            if (hasBufferedValue)
            {
                hasBufferedValue = false;
                value = bufferedValue;
            }
            else value = in.read();
            
            if (value == '\r')
            {
                bufferedValue = in.read();
                if (bufferedValue == '\n')  return 0;

                hasBufferedValue = true;
                
                return value;
            }
            
            return value;
        }
    }

    public void setSourceInputStreamForNewFormat(
        InputStream stream,
        LodResource anEntry)
        throws IOException
    {
        sourceInputStream = new TextToStrConversionInputStream(stream);
    }

}
