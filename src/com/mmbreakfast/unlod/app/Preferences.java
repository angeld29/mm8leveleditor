/*
 *  com/mmbreakfast/unlod/app/Preferences.java
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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Preferences
{
     protected static final String INITIAL_OPEN_DIR = "InitialOpenDir";
    protected static final String DEFAULT_INITIAL_OPEN_DIR = ".";
    protected static final String INITIAL_EXTRACT_DIR = "InitialExtractionDir";
    protected static final String DEFAULT_INITIAL_EXTRACT_DIR = "extracted";
    protected static final String RECENT_FILE = "RecentFile";
    protected static final String DEFAULT_RECENT_FILE = "";
    public static final int NUMBER_OF_RECENT_FILES = 8;
    protected Properties properties;
    protected ArrayList recentFiles;
    protected File preferenceFile;
    protected PreferencesObserver obs;
    protected File initialExtractionDirectory;
    protected File initialOpenDirectory;

    public class InvalidLodEntryHandlerManagerException extends Exception
    {
    }

    public static Properties getDefaultProperties()
    {
        Properties properties = new Properties();
        properties.setProperty("InitialOpenDir", ".");
        properties.setProperty("InitialExtractionDir", "extracted");
        for (int i = 0; i < 8; i++)
            properties.setProperty("RecentFile" + i, "");
        return properties;
    }

    public Preferences(File file, PreferencesObserver preferencesobserver)
    {
        preferenceFile = file;
        obs = preferencesobserver;
        properties = new Properties(getDefaultProperties());
        try
        {
            properties.load(new BufferedInputStream(new FileInputStream(file)));
        }
        catch (IOException ioexception)
        {
            /* empty */
        }
    }

    public File[] getRecentFiles()
    {
        if (recentFiles == null)
        {
            recentFiles = new ArrayList(8);
            Object object = null;
            Object object_1_ = null;
            for (int i = 0; i < 8; i++)
            {
                String string = properties.getProperty("RecentFile" + i);
                if (string != "" && !string.equals(""))
                {
                    File file = new File(string);
                    if (file.isFile())
                        recentFiles.add(file);
                }
            }
        }
        return (File[])recentFiles.toArray(new File[0]);
    }

    public void addRecentFile(File file)
    {
        if (!recentFiles.contains(file))
        {
            if (recentFiles.size() == 8)
                recentFiles.remove(0);
        }
        else
            recentFiles.remove(file);
        recentFiles.add(file);
        if (obs != null)
            obs.updateRecentFiles((File[])recentFiles.toArray(new File[0]));
    }

    public void removeRecentFile(File file)
    {
        if (recentFiles.remove(file))
            obs.updateRecentFiles((File[])recentFiles.toArray(new File[0]));
    }

    public File getInitialOpenDirectory()
    {
        return (
            initialOpenDirectory =
                new File(properties.getProperty("InitialOpenDir")));
    }

    public File getInitialExtractionDirectory()
    {
        return (
            initialExtractionDirectory =
                new File(properties.getProperty("InitialExtractionDir")));
    }

    public void setInitialExtractionDirectory(File file)
    {
        initialExtractionDirectory = file;
    }

    public void setInitialOpenDirectory(File file)
    {
        initialOpenDirectory = file;
    }

    public void save()
    {
        properties.setProperty(
            "InitialOpenDir",
            initialOpenDirectory.getAbsolutePath());
        properties.setProperty(
            "InitialExtractionDir",
            initialExtractionDirectory.getAbsolutePath());
        for (int i = 0; i < 8; i++)
            properties.setProperty(
                "RecentFile" + i,
                (i < recentFiles.size()
                    ? ((File)recentFiles.get(i)).getAbsolutePath()
                    : ""));
        try
        {
            BufferedOutputStream bufferedoutputstream =
                (
                    new BufferedOutputStream(
                        new FileOutputStream(preferenceFile)));
            properties.store(bufferedoutputstream, "Preferences");
            bufferedoutputstream.flush();
            bufferedoutputstream.close();
        }
        catch (IOException ioexception)
        {
            /* empty */
        }
    }
}
