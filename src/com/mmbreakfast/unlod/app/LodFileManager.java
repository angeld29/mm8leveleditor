/*
 *  com/mmbreakfast/unlod/app/LodFileManager.java
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

package com.mmbreakfast.unlod.app;

import com.mmbreakfast.unlod.lod.*;
import java.io.*;
import java.awt.Component;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.gamenet.application.mm8leveleditor.lod.LodFileLoader;

public class LodFileManager {
	protected static final File DEFAULT_OPEN_DIR = new File(".");
	protected static final File DEFAULT_EXTRACTION_DIR = new File("extracted");
	protected File openDir;
	protected File extractionDir;
	protected Component parent;
	protected Preferences preferences;

	protected static final FileFilter LOD_FILE_FILTER = new FileFilter() {
		public boolean accept(File file) {
            return (
                file.getName().toLowerCase().endsWith(".lod")
                    || file.getName().toLowerCase().endsWith(".vid")
                    || file.getName().toLowerCase().endsWith(".snd")
                    || file.getName().toLowerCase().endsWith(".hwl")
                    || file.getName().toLowerCase().endsWith(".mm6")
                    || file.getName().toLowerCase().endsWith(".mm7")
                    || file.getName().toLowerCase().endsWith(".dod")
                    || file.isDirectory());
		}

		public String getDescription() {
            return "LOD Files (*.lod), VID Files (*.vid), SND Files (*.snd), HWL Files (*.hwl), Save Games (*.mm6,*.mm7,*.dod)";
		}
	};

	public LodFileManager(Preferences preferences, Component parent) {
	   this.preferences = preferences;
		this.parent = parent;

		File initialOpenDir = preferences.getInitialOpenDirectory();
		File initialExtractionDir = preferences.getInitialExtractionDirectory();

		if (!initialOpenDir.exists() || !initialOpenDir.isDirectory()) {
			openDir = DEFAULT_OPEN_DIR;
		} else {
			openDir = initialOpenDir;
		}

		if (!initialExtractionDir.exists() || !initialExtractionDir.isDirectory()) {
			extractionDir = DEFAULT_EXTRACTION_DIR;
		} else {
			extractionDir = initialExtractionDir;
		}

		preferences.setInitialOpenDirectory(openDir);
		preferences.setInitialExtractionDirectory(extractionDir);
	}

	public LodFileInfo openLodFile(File file) throws InvalidLodFileException, IOException {
	   LodFile lodFile = null;
		try {
            lodFile = LodFileLoader.tryKnownFileNames(file);
			preferences.addRecentFile(file);
		}
        catch (IOException e)
        {
            // Seems like this should do something
            e.printStackTrace();
            throw e;
        }

        return new LodFileInfo(lodFile, file);
	}

	public LodFileInfo openLodFile() throws InvalidLodFileException {
		if (!openDir.exists() || !openDir.isDirectory()) {
			openDir = DEFAULT_OPEN_DIR;
		}

      JFileChooser chooser = null;

      if (!openDir.exists() || !openDir.isDirectory()) {
         chooser = new JFileChooser();
      } else {
		   chooser = new JFileChooser(openDir);
		}

		chooser.setAcceptAllFileFilterUsed(true);
		chooser.addChoosableFileFilter(LOD_FILE_FILTER);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);

		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			File openedFile = chooser.getSelectedFile();
			LodFile lodFile = null;

			try {
                lodFile = LodFileLoader.tryKnownFileNames(openedFile);
				openDir = chooser.getCurrentDirectory();
				preferences.setInitialOpenDirectory(openDir);
            }
            catch (IOException ioexception)
            {
                /* empty */
            }

            return new LodFileInfo(lodFile, openedFile);
        }

        return null;
	}

	public File getExtractionDirectory() {
	   return extractionDir;
	}

	public void promptForNewExtractionDirectory() {
      JFileChooser chooser = null;

      if (!extractionDir.exists() || !extractionDir.isDirectory()) {
         chooser = new JFileChooser();
      } else {
		   chooser = new JFileChooser(extractionDir);
		}

      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);

		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			File openedFile = chooser.getSelectedFile();
		   if (openedFile.exists() && openedFile.isDirectory()) {
		      extractionDir = openedFile;
		      preferences.setInitialExtractionDirectory(extractionDir);
		   }
		}

	}

}