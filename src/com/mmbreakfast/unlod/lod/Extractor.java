/*
 *  com/mmbreakfast/unlod/lod/Extractor.java
 *
 *  Copyright (C) 2000 Sil Veritas (sil_the_follower_of_dark@hotmail.com)
 */

/*  This file is part of Unlod.
 *
 *  Unlod is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Unlod is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Unlod; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/*  Unlod
 *
 *  Copyright (C) 2000 Sil Veritas. All Rights Reserved. This work is
 *  distributed under the W3C(R) Software License [1] in the hope that it
 *  will be useful, but WITHOUT ANY WARRANTY; without even the implied
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 */

package com.mmbreakfast.unlod.lod;

import java.io.*;
import java.util.zip.*;


public class Extractor {
	private boolean allowCancel = false;
	private boolean cancelSignalled = false;

    public Extractor() {}

    // TODO: rewrite using observer format
    public byte[] compress(byte[] uncompressedData)
    {
        Deflater compresser = new Deflater(Deflater.DEFAULT_COMPRESSION, false);
        
        byte[] workArea = new byte[uncompressedData.length];

        compresser.setInput(uncompressedData);
        compresser.finish();
        int newSize = compresser.deflate(workArea);

        byte[] compressedData = new byte[newSize];
        System.arraycopy(workArea, 0, compressedData, 0, newSize);
        
        return compressedData;
    }

    public void decompress(Object entry, byte[] compressedData, OutputStream out, ExtractionObserver obs, boolean allowCancel) throws IOException
    {
        this.allowCancel = allowCancel;
        int r;

        cancelSignalled = false;

        ByteArrayInputStream in = new ByteArrayInputStream(compressedData);
        

        try {
            // Create new ZLIB inflater
            InflaterInputStream iis = new InflaterInputStream(in, new Inflater(false));
            if (obs != null) {
               obs.extractionStarted(entry.toString());
            }

            // Don't write last available byte to out. It's always 0xFF and must be ignored.
            if (iis.available() > 0) {
               r = iis.read();


                   if (allowCancel) {
                       while (iis.available() > 0 && !cancelSignalled)  {
                      out.write(r);
                      r = iis.read();
                   }
                   if (cancelSignalled && obs != null) {
                      obs.extractionCancelled(entry.toString());
                   }
               } else {
                   while (iis.available() > 0)  {
                      out.write(r);
                      r = iis.read();
                   }
               }
            }

            out.flush();
            out.close();
        } catch (IOException e) {
           if (obs != null) {
              obs.exceptionCaught(e);
              throw e;
           }
        }

        if (obs != null) {
           obs.extractionFinished(entry.toString());
        }
    }

    public void convert(String identifier, byte[] data, OutputStream out, ExtractionObserver obs, boolean allowCancel)
        throws IOException
    {
        this.allowCancel = allowCancel;
        int r;

        cancelSignalled = false;

        ByteArrayInputStream in = new ByteArrayInputStream(data);
        float total = data.length;

        try
        {
            if (obs != null)
            {
                obs.extractionStarted(identifier);
            }

            if (allowCancel)
            {
                float counter = 0;
                while (in.available() > 0 && !cancelSignalled)
                {
                    ++counter;
                    r = in.read();
                    out.write(r);
                    if (obs != null) {
                       obs.extractionProgress(identifier, counter / total);
                    }
                }
                if (cancelSignalled && obs != null)
                {
                   obs.extractionCancelled(identifier);
                }
            }
            else
            {
                while (in.available() > 0)
                {
                   r = in.read();
                   out.write(r);
                }
            }
        }
        catch (IOException e)
        {
           if (obs != null)
           {
              obs.exceptionCaught(e);
              throw e;
           }
        }
        finally
        {
            out.flush();
            out.close();
        }

        if (obs != null)
        {
           obs.extractionFinished(identifier);
        }
    }

     public boolean cancelExtraction() {
       if (allowCancel) {
          cancelSignalled = true;
          return true;
       } else {
          return false;
       }
     }
}