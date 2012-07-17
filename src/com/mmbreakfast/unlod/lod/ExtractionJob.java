/*
 *  com/mmbreakfast/unlod/lod/ExtractionJob.java
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
import java.util.*;

import org.gamenet.application.mm8leveleditor.converter.FormatConverter;
import org.gamenet.application.mm8leveleditor.converter.NullFormatConverter;
import org.gamenet.application.mm8leveleditor.lod.LodEntry;


public class ExtractionJob {
   protected Iterator entriesToExtractIterator;
   protected Extractor extractor;
   protected float total = 0;
   protected float counter = 0;

   protected boolean cancelSignalled = false;

	public ExtractionJob(Iterator entriesToExtractIterator, int count) {
	   this.entriesToExtractIterator = entriesToExtractIterator;
       total = count;
	}

	public void extractToFiles(File outDir,
	                           ExtractionObserver obs,
	                           boolean allowCancel,
	                           boolean convertData) throws IOException
    {
        cancelSignalled = false;

        if (outDir.exists() && !outDir.isDirectory())
        {
            throw new IllegalArgumentException(outDir.getAbsolutePath() + " is not a directory");
        }

        LodEntry currentEntry;

        if (!outDir.exists())
        {
           outDir.mkdirs();
           obs.directoryCreated(outDir);
        }

        while (entriesToExtractIterator.hasNext() && !(allowCancel && cancelSignalled))
        {
            currentEntry = (LodEntry) entriesToExtractIterator.next();
            String filename = currentEntry.getFileName();
            
            FormatConverter converter = null;
            if (convertData)  converter = currentEntry.getFormatConverter();
            else
            {
                converter = new NullFormatConverter();
                filename = filename + ".raw";
            }
            
            String identifier = currentEntry.getName();
            
            if (converter.requiresMultipleStreams())
            {
                // IMPLEMENT: replace basefilename
                String baseFilename = filename;
                String[] filenames = converter.getSuggestedFilenames(baseFilename);
                OutputStream[] outputStreamArray = new OutputStream[filenames.length];
                for (int i = 0; i < outputStreamArray.length; ++i)
                {
                    outputStreamArray[i] = new BufferedOutputStream(new FileOutputStream(new File(outDir, filenames[i])));
                }
                converter.setDestinationOutputStreamsForNewFormat(outputStreamArray, currentEntry);
            }
            else
            {
                converter.setDestinationOutputStreamForNewFormat(new BufferedOutputStream(new FileOutputStream(new File(outDir, filename))), currentEntry);
            }
            
            Extractor extractor = new Extractor();

            extractor.convert(identifier,
                              currentEntry.getData(),
                              converter.getSourceOutputStreamForOldFormat(),
                              (null != obs) ? new EntryObserver(obs, total) : null,
                              allowCancel);
            
            if (null != obs)
            {               
                obs.extractionProgress(identifier, counter++ / total);
            }
        }

        if (obs != null)
        {
           obs.extractionFinished("Done");
        }
	}

    public class EntryObserver implements ExtractionObserver
    {
        private ExtractionObserver parent = null;
        private int range = 0;
        private int lastProgressUpdate = -1;
        
        public int getRange()
        {
            return range;
        }

        public EntryObserver(ExtractionObserver parent, float divisions)
        {
            this.parent = parent;
            this.range = Math.round(((float)parent.getRange()) / divisions);
        }
        
        public void extractionProgress(String identifier, float percentageDone)
        {
            int trackableProgress = Math.round(percentageDone * range);
            if (trackableProgress > lastProgressUpdate)
            {
                parent.extractionProgress(identifier, trackableProgress);
                lastProgressUpdate = trackableProgress;
            }
        }

        public void extractionStarted(String identifier)
        {
        }

        public void extractionFinished(String identifier)
        {
        }

        public void extractionCancelled(String identifier)
        {
        }

        public void exceptionCaught(Exception e)
        {
        }

        public void directoryCreated(File dir)
        {
        }
    }

	public boolean cancelJob() {
	   cancelSignalled = true;
      return extractor.cancelExtraction();
	}
}