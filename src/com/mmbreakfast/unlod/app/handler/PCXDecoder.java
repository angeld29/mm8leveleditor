/*
 *  com/mmbreakfast/unlod/app/handler/PCXDecoder.java
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

//import com.mmbreakfast.unlod.lod.RandomAccessFileInputStream;
package com.mmbreakfast.unlod.app.handler;

import java.io.*;
import java.awt.image.*;


public class PCXDecoder {
	protected static final int Xmin_OFFSET         = 4;
	protected static final int Ymin_OFFSET         = 6;
	protected static final int Xmax_OFFSET         = 8;
	protected static final int Ymax_OFFSET         = 10;
	protected static final int NPlanes_OFFSET      = 65;
	protected static final int BytesPerLine_OFFSET = 66;
	protected static final int Data_OFFSET         = 128;
   protected static final int TOP_TWO_BITS        = 0xC0;
   protected static final int LO_SIX_BITS         = ~TOP_TWO_BITS;

   public ImageProducer decode(ByteArrayInputStream in) throws IOException, InvalidPCXFileException {
	   int pos = 0;

	   int xMin         = this.readNibble(in, Xmin_OFFSET);
	   pos = Xmin_OFFSET + 2;
	   int yMin         = this.readNibble(in, Ymin_OFFSET - pos);
	   pos = Ymin_OFFSET + 2;
	   int xMax         = this.readNibble(in, Xmax_OFFSET - pos);
	   pos = Xmax_OFFSET + 2;
	   int yMax         = this.readNibble(in, Ymax_OFFSET - pos);
	   pos = Ymax_OFFSET + 2;
	   int nPlanes      = this.readByte(in, NPlanes_OFFSET - pos);
	   pos =  NPlanes_OFFSET + 1;
	   int bytesPerLine = this.readNibble(in, BytesPerLine_OFFSET - pos);
	   pos = BytesPerLine_OFFSET + 2;

	   int xSize = xMax - xMin + 1;
	   int ySize = yMax - yMin + 1;
	   int totalBytesPerLine = nPlanes * bytesPerLine;
	   int totalBytes = xSize * ySize;

	   in.skip(Data_OFFSET - pos);

      int[] scanLine = new int[totalBytesPerLine];
      int[] image    = new int[totalBytes];

	   int r = 0;
	   int bytesWritten = 0;
	   int repeats = 0;
	   int totalRepeats = 0;


      int imageBytesWritten = 0;
      int totalImageBytesWritten = 0;
      int plane = 0;
      int srgb = 0;

      for (int y = 0; y < ySize; y++) {
         // scan a line

         bytesWritten = 0;
   	   while ((bytesWritten < totalBytesPerLine) && ((r = in.read()) != -1) ) {
   	   	if ((r & TOP_TWO_BITS) == TOP_TWO_BITS) {
   	   		for (repeats = 0, totalRepeats = (r & LO_SIX_BITS), r = in.read(); repeats < totalRepeats; repeats++) {
   	   			if (r == -1) {
   	   				throw new InvalidPCXFileException();
   	   			} else {
   	   				scanLine[bytesWritten++] = r;
   	   				//System.out.println(r);
   	   			}
   	   		}
   	   	} else {
   	   	   scanLine[bytesWritten++] = r;
   	   	   //System.out.println(r);
   	   	}
   	   }

   	   if (bytesWritten < totalBytesPerLine) {
   	      throw new InvalidPCXFileException();
   	   }


   	   // write the line

   	   if (nPlanes == 4) {
      	   for (imageBytesWritten = 0; imageBytesWritten < xSize; imageBytesWritten++, totalImageBytesWritten++) {
      	      srgb =   (scanLine[imageBytesWritten + 3 * bytesPerLine] << 24)
      	             | (scanLine[imageBytesWritten]                        << 16)
      	             | (scanLine[imageBytesWritten +     bytesPerLine] << 8)
      	             | (scanLine[imageBytesWritten + 2 * bytesPerLine]);
      	      image[totalImageBytesWritten] = srgb;
      	   }
   	   } else if (nPlanes == 3) {
   	      for (imageBytesWritten = 0; imageBytesWritten < xSize; imageBytesWritten++, totalImageBytesWritten++) {
      	      srgb =   (0xFF                                               << 24)
      	             | (scanLine[imageBytesWritten]                        << 16)
      	             | (scanLine[imageBytesWritten +     bytesPerLine] << 8)
      	             | (scanLine[imageBytesWritten + 2 * bytesPerLine]);
      	      image[totalImageBytesWritten] = srgb;
      	   }
   	   }

	   }

      return new MemoryImageSource(xSize, ySize, image, 0, xSize);

	}

	public ImageProducer decode(RandomAccessFile in) throws IOException, InvalidPCXFileException {
	   int xMin        = this.readNibble(in, Xmin_OFFSET);
	   int yMin         = this.readNibble(in, Ymin_OFFSET);
	   int xMax         = this.readNibble(in, Xmax_OFFSET);
	   int yMax         = this.readNibble(in, Ymax_OFFSET);
	   int nPlanes      = this.readByte(in, NPlanes_OFFSET);
	   int bytesPerLine = this.readNibble(in, BytesPerLine_OFFSET);

	   int xSize = xMax - xMin + 1;
	   int ySize = yMax - yMin + 1;
	   int totalBytesPerLine = nPlanes * bytesPerLine;
	   //int totalBytes = nPlanes * bytesPerLine * ySize;
	   int totalBytes = xSize * ySize;

	   in.seek(Data_OFFSET);

      int[] scanLine = new int[totalBytesPerLine];
      int[] image    = new int[totalBytes];

	   int r = 0;
	   int bytesWritten = 0;
	   int repeats = 0;
	   int totalRepeats = 0;


      int imageBytesWritten = 0;
      int totalImageBytesWritten = 0;
      int plane = 0;
      int srgb = 0;

      for (int y = 0; y < ySize; y++) {
         // scan a line

         bytesWritten = 0;
   	   while ((bytesWritten < totalBytesPerLine) && ((r = in.read()) != -1) ) {
   	   	if ((r & TOP_TWO_BITS) == TOP_TWO_BITS) {
   	   		for (repeats = 0, totalRepeats = (r & LO_SIX_BITS), r = in.read(); repeats < totalRepeats; repeats++) {
   	   			if (r == -1) {
   	   				throw new InvalidPCXFileException();
   	   			} else {
   	   				scanLine[bytesWritten++] = r;
   	   				//System.out.println(r);
   	   			}
   	   		}
   	   	} else {
   	   	   scanLine[bytesWritten++] = r;
   	   	   //System.out.println(r);
   	   	}
   	   }

   	   if (bytesWritten < totalBytesPerLine) {
   	      throw new InvalidPCXFileException();
   	   }

   	   if (nPlanes == 4) {
      	   for (imageBytesWritten = 0; imageBytesWritten < xSize; imageBytesWritten++, totalImageBytesWritten++) {
      	      srgb =   (scanLine[imageBytesWritten + 3 * bytesPerLine] << 24)
      	             | (scanLine[imageBytesWritten]                        << 16)
      	             | (scanLine[imageBytesWritten +     bytesPerLine] << 8)
      	             | (scanLine[imageBytesWritten + 2 * bytesPerLine]);
      	      image[totalImageBytesWritten] = srgb;
      	   }
   	   } else if (nPlanes == 3) {
   	      for (imageBytesWritten = 0; imageBytesWritten < xSize; imageBytesWritten++, totalImageBytesWritten++) {
      	      srgb =   (0xFF                                               << 24)
      	             | (scanLine[imageBytesWritten]                        << 16)
      	             | (scanLine[imageBytesWritten +     bytesPerLine] << 8)
      	             | (scanLine[imageBytesWritten + 2 * bytesPerLine]);
      	      image[totalImageBytesWritten] = srgb;
      	   }
   	   }

	   }

      return new MemoryImageSource(xSize, ySize, image, 0, xSize);

	}


   private int readNibble(RandomAccessFile in, long offset) throws IOException, InvalidPCXFileException {
   	// Fixme: Since the readNibble() methods now read 2 bytes they really should be called readWord()
   	in.seek(offset);
   	int r = 0;
   	int nibble = 0;

   	for (int i = 0; (i < 2) && ((r = in.read()) != -1); i++) {
			nibble += (r << (8 * i));
   	}
   	if (r == -1) {
   		throw new InvalidPCXFileException();
   	}

   	return nibble;
   }

   private int readByte(RandomAccessFile in, long offset) throws IOException, InvalidPCXFileException {
   	in.seek(offset);
   	int r = in.read();
   	if (r == -1) {
   	   throw new InvalidPCXFileException();
   	}

   	return r;
   }

   private int readNibble(ByteArrayInputStream in, int skip) throws InvalidPCXFileException {
   	in.skip(skip);
   	int r = 0;
   	int nibble = 0;

   	for (int i = 0; (i < 2) && ((r = in.read()) != -1); i++) {
			nibble += (r << (8 * i));
   	}
   	if (r == -1) {
   		throw new InvalidPCXFileException();
   	}

   	return nibble;
   }

   private int readByte(ByteArrayInputStream in, int skip) throws InvalidPCXFileException {
   	in.skip(skip);
   	int r = in.read();
   	if (r == -1) {
   	   throw new InvalidPCXFileException();
   	}

   	return r;
   }

   public class InvalidPCXFileException extends Exception {}
}
