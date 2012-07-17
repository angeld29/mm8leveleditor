/*
 *  com/mmbreakfast/unlod/lod/PassThroughLodFileExtractor.java
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
import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import org.gamenet.application.mm8leveleditor.lod.LodEntry;

public class PassThroughLodFileExtractor extends Extractor
{
    protected LodFile lodFile;
    private boolean allowCancel = false;
    private boolean cancelSignalled = false;

    public void decompress(
        LodEntry lodentry,
        byte[] data,
        OutputStream outputstream,
        ExtractionObserver extractionobserver,
        boolean bool)
    {
        allowCancel = bool;
        cancelSignalled = false;

        // IMPLEMENT: dump the bytestream
        ByteArrayInputStream in = new ByteArrayInputStream(data);

        try
        {
            if (extractionobserver != null)
                extractionobserver.extractionStarted(lodentry.getName());
			int dataLeftToRead = data.length;
            if (dataLeftToRead > 0)
            {
				while (dataLeftToRead > 0)
				{
					int i = in.read();
					outputstream.write(i);
					--dataLeftToRead;
					
					if (bool)
					{
						if (cancelSignalled && extractionobserver != null)
							extractionobserver.extractionCancelled(lodentry.getName());
					}
				}
            }
            outputstream.flush();
            outputstream.close();
        }
        catch (Exception exception)
        {
            if (extractionobserver != null)
                extractionobserver.exceptionCaught(exception);
        }
        if (extractionobserver != null)
            extractionobserver.extractionFinished(lodentry.getName());
    }

    public boolean cancelExtraction()
    {
        if (allowCancel)
        {
            cancelSignalled = true;
            return true;
        }
        return false;
    }
}
