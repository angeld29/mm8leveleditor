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

package org.gamenet.application.mm8leveleditor.lod;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.gamenet.application.mm8leveleditor.mm6.BitmapsLodFile;
import org.gamenet.application.mm8leveleditor.mm6.MM6LodFile;
import org.gamenet.application.mm8leveleditor.mm6.MM6NewLodFile;
import org.gamenet.application.mm8leveleditor.mm6.MM6SndFile;
import org.gamenet.application.mm8leveleditor.mm6.MM6SpritesLodFile;
import org.gamenet.application.mm8leveleditor.mm6.MM6VidFile;
import org.gamenet.application.mm8leveleditor.mm7.MM7NewLodFile;
import org.gamenet.application.mm8leveleditor.mm8.EnglishTLodFile;

//import org.gamenet.application.mm8leveleditor.mm6.GameMMVILodFile;
//import org.gamenet.application.mm8leveleditor.mm6.MM6NewLodFile;
//import org.gamenet.application.mm8leveleditor.mm6.MM6SndFile;
//import org.gamenet.application.mm8leveleditor.mm6.MM6VidFile;
//import org.gamenet.application.mm8leveleditor.mm6.MMVILodFile;
//import org.gamenet.application.mm8leveleditor.mm6.MMVISpritesLodFile;
//import org.gamenet.application.mm8leveleditor.mm7.MM7HwlFile;
//import org.gamenet.application.mm8leveleditor.mm7.MM7NewLodFile;
//import org.gamenet.application.mm8leveleditor.mm7.MMVIILodFile;

import com.mmbreakfast.unlod.lod.InvalidLodFileException;
import com.mmbreakfast.unlod.lod.LodFile;
import com.mmbreakfast.unlod.lod.RandomAccessFileInputStream;
import com.mmbreakfast.unlod.lod.mm8.EnglishDLodFile;

public class LodFileLoader
{
    static public LodFile tryKnownFileNames(File file)
        throws InvalidLodFileException, IOException
    {
        LodFile lodFile = null;
        RandomAccessFile lodRandomAccessFile = null;
        RandomAccessFileInputStream lodInputStream = null;

        boolean hasTriedAllFileTypes = false;
        try
        {
            // Known files
            lodRandomAccessFile = new RandomAccessFile(file, "r");
            lodInputStream =
                new RandomAccessFileInputStream(lodRandomAccessFile);

            String lowercaseFileName = file.getName().toLowerCase();
            if (-1 != lowercaseFileName.indexOf("icons".toLowerCase()))
                lodFile = new MM6LodFile(file, lodInputStream);
            else if (-1 != lowercaseFileName.indexOf("bitmaps".toLowerCase()))
                lodFile = new BitmapsLodFile(file, lodInputStream);
            else if (-1 != lowercaseFileName.indexOf("events".toLowerCase()))
                lodFile = new MM6LodFile(file, lodInputStream);
            else if (-1 != lowercaseFileName.indexOf("sprites".toLowerCase()))
                lodFile = new MM6SpritesLodFile(file, lodInputStream);
            else if (-1 != lowercaseFileName.indexOf("Audio".toLowerCase()))
                lodFile = new MM6SndFile(file, lodInputStream);
            else if (-1 != lowercaseFileName.indexOf("EnglishD".toLowerCase()))
                lodFile = new EnglishDLodFile(file, lodInputStream);
            else if (-1 != lowercaseFileName.indexOf("EnglishT".toLowerCase()))
                lodFile = new EnglishTLodFile(file, lodInputStream);

//            else if ("d3dbitmap.hwl".equalsIgnoreCase(file.getName()))
//                lodFile = new MM7HwlFile(file, lodInputStream);
//            else if ("d3dsprite.hwl".equalsIgnoreCase(file.getName()))
//                lodFile = new MM7HwlFile(file, lodInputStream);
//
            else if (-1 != lowercaseFileName.indexOf("Anims".toLowerCase()))
                lodFile = new MM6VidFile(file, lodInputStream);
            else if (-1 != lowercaseFileName.indexOf("Might7".toLowerCase()))
                lodFile = new MM6VidFile(file, lodInputStream);
            else if (-1 != lowercaseFileName.indexOf("mightdod".toLowerCase()))
                lodFile = new MM6VidFile(file, lodInputStream);
            else if (-1 != lowercaseFileName.indexOf("magicdod".toLowerCase()))
                lodFile = new MM6VidFile(file, lodInputStream);
            else if (-1 != lowercaseFileName.indexOf("games".toLowerCase()))
            {
                try
                {
                    lodFile = new MM6NewLodFile(file, lodInputStream);
                }
                catch (InvalidLodFileException exception)
                {
                    lodFile = new MM7NewLodFile(file, lodInputStream);
                }
            }
            else if (-1 != lowercaseFileName.indexOf("new".toLowerCase()))
            {
                try
                {
                    lodFile = new MM6NewLodFile(file, lodInputStream);
                }
                catch (InvalidLodFileException exception)
                {
                    lodFile = new MM7NewLodFile(file, lodInputStream);
                }
            }
            else if (-1 != lowercaseFileName.indexOf(".mm6".toLowerCase()))
            {
                try
                {
                    lodFile = new MM6NewLodFile(file, lodInputStream);
                }
                catch (InvalidLodFileException exception)
                {
                    lodFile = new MM7NewLodFile(file, lodInputStream);
                }
            }
            else if (-1 != lowercaseFileName.indexOf(".mm7".toLowerCase()))
            {
                try
                {
                    lodFile = new MM6NewLodFile(file, lodInputStream);
                }
                catch (InvalidLodFileException exception)
                {
                    lodFile = new MM7NewLodFile(file, lodInputStream);
                }
            }
            else if (-1 != lowercaseFileName.indexOf(".dod".toLowerCase()))
            {
                try
                {
                    lodFile = new MM6NewLodFile(file, lodInputStream);
                }
                catch (InvalidLodFileException exception)
                {
                    lodFile = new MM7NewLodFile(file, lodInputStream);
                }
            }
            else
            {
                hasTriedAllFileTypes = true;
                lodFile = tryAllFileTypes(file, lodInputStream);
            }
        }
        catch (InvalidLodFileException invalidlodfileexception)
        {
            if (false == hasTriedAllFileTypes)
            {
                try
                {
                    lodFile = tryAllFileTypes(file, lodInputStream);
                }
                catch (InvalidLodFileException invalidlodfileexception2)
                {
                    throw invalidlodfileexception;
                }
            }
            else
            {
                throw invalidlodfileexception;
            }
        }

        return lodFile;
    }

    static public LodFile tryAllFileTypes(
        File file,
        RandomAccessFileInputStream lodInputStream)
        throws InvalidLodFileException, IOException
    {
        LodFile lodFile = null;
        lodFile = new EnglishDLodFile(file, lodInputStream);
//        try
//        {
//            lodFile = new EnglishDLodFile(file, lodInputStream);
//        }
//        catch (InvalidLodFileException invalidlodfileexception1)
//        {
//            try
//            {
//                lodFile = new MMVIILodFile(file, lodInputStream);
//            }
//            catch (InvalidLodFileException invalidlodfileexception2)
//            {
//                try
//                {
//                    lodFile =
//                        new MM6LodFile(
//                            file,
//                            new RandomAccessFileInputStream(
//                                new RandomAccessFile(file, "r")));
//                }
//                catch (InvalidLodFileException invalidlodfileexception3)
//                {
//                    lodFile =
//                        new GameMMVILodFile(
//                            file,
//                            new RandomAccessFileInputStream(
//                                new RandomAccessFile(file, "r")));
//                }
//            }
//        }

        return lodFile;
    }

}
