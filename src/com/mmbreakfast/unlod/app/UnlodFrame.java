/*
 *  com/mmbreakfast/unlod/app/UnlodFrame.java
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

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import org.gamenet.application.mm8leveleditor.handler.DataHandler;
import org.gamenet.application.mm8leveleditor.handler.LodResourceManager;
import org.gamenet.application.mm8leveleditor.lod.ApplicationController;
import org.gamenet.application.mm8leveleditor.lod.FileBasedModifiedLodResource;
import org.gamenet.application.mm8leveleditor.lod.ImportManager;
import org.gamenet.application.mm8leveleditor.lod.LodEntry;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.LodResourceController;
import org.gamenet.application.mm8leveleditor.lod.ProgressDisplayer;

import com.mmbreakfast.unlod.lod.*;
import com.mmbreakfast.util.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;


public class UnlodFrame extends JFrame implements PreferencesObserver, ProgressDisplayer, ApplicationController
{
    private static final int MILLISECONDS_TO_POPUP = 0;
    private static final int MILLISECONDS_TO_DECIDE_TO_POPUP = 0;

    public static ApplicationController defaultApplicationController;

    protected LodFileList lodFileList = new LodFileList(new LodFileListListener());
	protected LodEntryDisplayPanel display = new LodEntryDisplayPanel();
	protected LodResourceManager manager;
	public static final String TITLE = "MM8LevelEditor 3.47"; //$NON-NLS-1$
	private LodFile lodFile;
	private ExtractionManager extractionManager;
	private LodFileManager lodFileManager;
    private Preferences preferences;
    private JMenu fileMenu;
    private JMenuItem openItem;
    private JMenuItem exitItem;

    // ApplicationController methods
    
    public void setDisplayedComponent(Component displayComponent)
    {
        display.setDisplayedComponent(displayComponent);
    }
	
	public LodResourceManager getLodResourceManager()
	{
	    return manager;
	}
	
	public LodFile getCurrentLodFile()
	{
	    return lodFile;
	}
    
    public ProgressDisplayer getProgressDisplayer()
    {
        return this;
    }
    

    public ApplicationController getApplicationController()
    {
        return this;
    }

    public static ApplicationController getDefaultApplicationController()
    {
        return defaultApplicationController;
    }
    

    public File getFileToCreate()
    {
	    File defaultNewLodFileToCreate = new File(getCurrentLodFile().getFile().getAbsolutePath() + Messages.getString("UnlodFrame.newFilePrefix")); //$NON-NLS-1$

	    JFileChooser chooser = new JFileChooser(defaultNewLodFileToCreate);
	    chooser.setSelectedFile(defaultNewLodFileToCreate);
	    
	    int state = chooser.showSaveDialog(this);
	    if (JFileChooser.APPROVE_OPTION != state)
	    {
	        return null;
	    }
	    
	    File selectedFile = chooser.getSelectedFile();
	    
	    if (selectedFile.equals(getCurrentLodFile().getFile()))
	    {
	        displayErrorPanel(selectedFile.getAbsolutePath() + Messages.getString("UnlodFrame.sourceFileWarning")); //$NON-NLS-1$
	        return null;
	    }
	    
	    return selectedFile;
    }
   
    public ProgressMonitor getProgressMonitor(Object message, String note, int progressMin, int progressMax)
    {
        ProgressMonitor monitor = new ProgressMonitor(this, message, note, progressMin, progressMax);
        monitor.setMillisToDecideToPopup(MILLISECONDS_TO_DECIDE_TO_POPUP);
        monitor.setMillisToPopup(MILLISECONDS_TO_POPUP);
        monitor.setProgress(progressMin);
        
        return monitor;
    }

    public void displayErrorPanel(String error)
    {
        JOptionPane.showMessageDialog(this, error, Messages.getString("UnlodFrame.error"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
    }
    
    public int showConfirmDialog (Object message, String title)
    {
        return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
    }
    
   private class LodEntryComparatorAction extends AbstractAction
   {
       protected LodFileList.LodEntryComparator comparator;

       public LodEntryComparatorAction(
           LodFileList.LodEntryComparator aComparator)
       {
           comparator = aComparator;
           putValue(Action.NAME, Messages.getString("UnlodFrame.sortBy") + comparator.getDisplayName()); //$NON-NLS-1$ //$NON-NLS-2$
       }

       public void actionPerformed(ActionEvent actionevent)
       {
           changeLodEntryComparator(comparator);
       }
   }

	public void updateRecentFiles(File[] files) {
	   fileMenu.removeAll();
	   this.createFileMenu(fileMenu, files);
	}

    protected void setLodEntry(LodEntry selectedEntry)
    {
        LodResourceController lodResourceController = LodResourceController.getLodResourceControllerFor(this, selectedEntry);
        lodResourceController.displayLodResource();
    }

	private void createFileMenu(JMenu fileMenu, File[] recentFiles) {
      fileMenu.add(openItem);
      fileMenu.addSeparator();

      JMenuItem currentItem;

      for (int i = recentFiles.length - 1; i >= 0; i--) {
         currentItem = new JMenuItem(new RecentFileAction(recentFiles[i], recentFiles.length - i - 1));
         currentItem.setMnemonic(Character.forDigit(recentFiles.length - i, 10));
         fileMenu.add(currentItem);
      }

      if (recentFiles.length != 0) {
         fileMenu.addSeparator();
      }

      fileMenu.add(exitItem);
	}

    private void createFileEntryComparatorMenu(JMenu jmenu)
    {
        LodFileList.LodEntryComparator lodEntryComparatorArray[] =
            lodFileList.lodEntryComparatorArray;

        for (int i = 0; i < lodEntryComparatorArray.length; ++i)
        {
            JMenuItem jmenuitem =
                new JMenuItem(
                    new LodEntryComparatorAction(lodEntryComparatorArray[i]));
            jmenu.add(jmenuitem);
        }
    }

	public void setLodFile(final LodFile lodFile) {
	   this.lodFile = lodFile;
	   extractionManager = new ExtractionManager(this);
	   display.setDisplayedComponent(new JPanel());
   	new Thread(new Runnable() {
   	   public void run() {
   	      lodFileList.setLodFile(lodFile);
   	   }
   	}).start();
	}

   protected void openFile() {
      try {
         LodFileInfo newLodFile = lodFileManager.openLodFile();

         if (newLodFile != null && newLodFile.lodFile != null) {
            this.setLodFile(newLodFile.lodFile);
            preferences.addRecentFile(newLodFile.file);
            this.setTitle(TITLE + Messages.getString("UnlodFrame.verticalSeparator") + newLodFile.file.getAbsolutePath()); //$NON-NLS-1$
         }

      } catch (InvalidLodFileException e) {
         JOptionPane.showMessageDialog(this, Messages.getString("UnlodFrame.invalidLodFile"), Messages.getString("UnlodFrame.error"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
      }
      this.repaint();
   }

   protected void openLodFile(File file)
   {
      try {
         LodFileInfo newLodFile = lodFileManager.openLodFile(file);

         if (newLodFile != null && newLodFile.lodFile != null) {
            this.setLodFile(newLodFile.lodFile);
            preferences.addRecentFile(newLodFile.file);
            this.setTitle(TITLE + Messages.getString("UnlodFrame.verticalSeparator") + newLodFile.file.getAbsolutePath()); //$NON-NLS-1$
         }

      }
      catch (InvalidLodFileException e)
      {
         JOptionPane.showMessageDialog(this, Messages.getString("UnlodFrame.error"), Messages.getString("UnlodFrame.invalidLodFile"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
      }
      catch (FileNotFoundException e)
      {
          JOptionPane.showMessageDialog(this, Messages.getString("UnlodFrame.error"), Messages.getString("UnlodFrame.LodFileNotFound"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
      }
      catch (IOException exception)
	  {
          JOptionPane.showMessageDialog(this, Messages.getString("UnlodFrame.error"), Messages.getString("UnlodFrame.IOException") + exception.getLocalizedMessage(), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
	      exception.printStackTrace();
	  }
   }

   protected void changeLodEntryComparator(
       LodFileList.LodEntryComparator aComparator)
   {
       lodFileList.changeLodEntryComparator(aComparator);
   }

   protected void exit() {
      preferences.save();
      this.dispose();
      System.exit(0);
   }

   protected void setExtractionDirectory() {
      lodFileManager.promptForNewExtractionDirectory();
   }

   protected void extract() {
       LodEntry entry = lodFileList.getSelectedEntry();
       if (entry != null && extractionManager != null) {
          extractionManager.extractLodEntries(new LodEntry[] {lodFileList.getSelectedEntry()}, lodFileManager.getExtractionDirectory(), true);
       }
    }

    protected void extractSelection() {
       if (extractionManager != null) {
          extractionManager.extractLodEntries(lodFileList.getSelectedEntries(), lodFileManager.getExtractionDirectory(), true);
       }
    }

    protected void extractAll() {
       if (lodFile != null && extractionManager != null) {
          extractionManager.extract(lodFile, lodFileManager.getExtractionDirectory(), true);
       }
    }

    protected void extractRaw() {
        LodEntry entry = lodFileList.getSelectedEntry();
        if (entry != null && extractionManager != null) {
           extractionManager.extractLodEntries(new LodEntry[] {lodFileList.getSelectedEntry()}, lodFileManager.getExtractionDirectory(), false);
        }
     }

     protected void extractRawSelection() {
        if (extractionManager != null) {
           extractionManager.extractLodEntries(lodFileList.getSelectedEntries(), lodFileManager.getExtractionDirectory(), false);
        }
     }

     protected void extractRawAll() {
        if (lodFile != null && extractionManager != null) {
           extractionManager.extract(lodFile, lodFileManager.getExtractionDirectory(), false);
        }
     }

 	protected File selectFile(File openDir, Component parent)
 	{
 	    File files[] = selectFiles(openDir, parent, false);
 	    
 	    if (null == files)  return null;
 	    
 	    return files[0];
    }

	protected File[] selectFiles(File openDir, Component parent)
	{
		return selectFiles(openDir, parent, true);
    }

	protected File[] selectFiles(File openDir, Component parent, boolean multiSelectionEnabled)
	{
      JFileChooser chooser = null;

      if (!openDir.exists() || !openDir.isDirectory()) {
         chooser = new JFileChooser();
      } else {
		   chooser = new JFileChooser(openDir);
		}

		chooser.setAcceptAllFileFilterUsed(true);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setMultiSelectionEnabled(multiSelectionEnabled);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
		    if (multiSelectionEnabled)
		    {
				File[] openedFiles = chooser.getSelectedFiles();

//				openDir = chooser.getCurrentDirectory();
//				preferences.setInitialOpenDirectory(openDir);
				
				return openedFiles;
		    }
		    else return new File[] { chooser.getSelectedFile() };
        }

        return null;
	}

    protected void setImportDirectory()
    {
        throw new RuntimeException(Messages.getString("UnlodFrame.Unimplemented")); //$NON-NLS-1$
    }
    
    protected String importFileset(File[] filesToImportArray)
    {
        if (null == filesToImportArray)
        {
            return Messages.getString("UnlodFrame.NoFilesSelected"); //$NON-NLS-1$
        }

	    File newLodFileToCreate = this.getFileToCreate();
	    if (null == newLodFileToCreate)  return null;
	    
        List filesToImportList = Arrays.asList(filesToImportArray);
        
        ImportManager importManager = new ImportManager(this);
        importManager.importAndMonitorResources(lodFile, newLodFileToCreate, filesToImportList);
        
        return null;
    }

    protected void importSelectedFiles()
    {
        File[] fileArray = this.selectFiles(lodFileManager.getExtractionDirectory(), this);
        String error = importFileset(fileArray);
        if (null != error)  displayErrorPanel(error);
    }
    
    protected void importAll()
    {
        File[] fileArray = lodFileManager.getExtractionDirectory().listFiles();
        String error = importFileset(fileArray);
        if (null != error)  displayErrorPanel(error);
    }

    protected void quickAppendSelectedFile()
    {
	    int result = this.showConfirmDialog(Messages.getString("UnlodFrame.WillModifyFileContinuePrompt"), Messages.getString("UnlodFrame.DangerUpdatingLODFile")); //$NON-NLS-1$ //$NON-NLS-2$
	    if (JOptionPane.YES_OPTION != result)
	    {
	        return;
	    }
	    
        File file = this.selectFile(lodFileManager.getExtractionDirectory(), this);
        if (null == file)
        {
            displayErrorPanel(Messages.getString("UnlodFrame.NoFileSelected")); //$NON-NLS-1$
            return;
        }

        final LodEntry lodEntry = this.getCurrentLodFile().findLodEntryForFile(file);
        if (null == lodEntry)
        {
            displayErrorPanel(Messages.getString("UnlodFrame.NoMatchingLodEntryFound")); //$NON-NLS-1$
            return;
        }
        
        LodResource externalFileLodResource = new FileBasedModifiedLodResource(lodEntry.getFormatConverter(), file)
        {
            public String getEntryName()
            {
                return lodEntry.getEntryName();
            }
        };

        ImportManager importManager = new ImportManager(this.getProgressDisplayer());
	    importManager.updateAndMonitorResource(this.getApplicationController().getCurrentLodFile(), externalFileLodResource);
    }

    protected void importCurrentResource()
    {
        LodResource lodResource = lodFileList.getSelectedEntry();
        if (null == lodResource)
        {
            displayErrorPanel(Messages.getString("UnlodFrame.NoResourceSelected")); //$NON-NLS-1$
            return;
        }
        
        LodResourceController lodResourceController = LodResourceController.getLodResourceControllerFor(this, lodResource);
        lodResourceController.importData();
    }

    protected void quickAppendCurrentResource()
    {
        LodResource lodResource = lodFileList.getSelectedEntry();
        if (null == lodResource)
        {
            displayErrorPanel(Messages.getString("UnlodFrame.NoResourceSelected")); //$NON-NLS-1$
            return;
        }
        
        LodResourceController lodResourceController = LodResourceController.getLodResourceControllerFor(this, lodResource);
        lodResourceController.updateByAppendingData();
    }

   protected void openPreferences() {
      new PreferencesDialog(this, preferences).show();
   }

   protected void about() {
      new AboutDialog(this).show();
   }

	public UnlodFrame(String propertyFileName) {
	   super(TITLE);

	   // make it globally available
	   defaultApplicationController = this;
	   
	   this.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	         UnlodFrame.this.exit();
	      }
	   });


	   preferences = new Preferences(new File(propertyFileName), this);

	   manager = new LodResourceManager();

	   JPanel flowLayoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	   flowLayoutPanel.add(display);
      JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, new JScrollPane(lodFileList), new JScrollPane(flowLayoutPanel));
      sp.setDividerLocation(150);


      GridBagLayout gb = new GridBagLayout();
      Container cp = this.getContentPane();
      cp.setLayout(gb);

      this.setSize(800, 600);

      ComponentDeployer.deploy(cp, sp, GridBagConstraints.CENTER, GridBagConstraints.REMAINDER, 1, GridBagConstraints.BOTH,     1.0, 1.0, new Insets(0, 0, 0, 0));

      JMenuBar mb = new JMenuBar();

      Action openAction = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            UnlodFrame.this.openFile();
         }
      };

      openAction.putValue(Action.NAME, Messages.getString("UnlodFrame.Open") + Messages.getString("UnlodFrame.willOpenPanelSymbol")); //$NON-NLS-1$ //$NON-NLS-2$
      openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
      openAction.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_O));

      Action exitAction = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            UnlodFrame.this.exit();
         }
      };

      exitAction.putValue(Action.NAME, Messages.getString("UnlodFrame.Exit")); //$NON-NLS-1$
      exitAction.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_X));

      Action setDirAction = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            UnlodFrame.this.setExtractionDirectory();
         }
      };

      setDirAction.putValue(Action.NAME, Messages.getString("UnlodFrame.SetExtractionDirectory") + Messages.getString("UnlodFrame.willOpenPanelSymbol")); //$NON-NLS-1$ //$NON-NLS-2$
      setDirAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
      setDirAction.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_D));

      Action extractAction = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            UnlodFrame.this.extract();
         }
      };

      extractAction.putValue(Action.NAME, Messages.getString("UnlodFrame.SelectedFile")); //$NON-NLS-1$
      extractAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
      extractAction.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_F));

      Action extractSelAction = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            UnlodFrame.this.extractSelection();
         }
      };

      extractSelAction.putValue(Action.NAME, Messages.getString("UnlodFrame.SelectedFiles")); //$NON-NLS-1$
      extractSelAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
      extractSelAction.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_S));

      Action extractAllAction = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            UnlodFrame.this.extractAll();
         }
      };

      extractAllAction.putValue(Action.NAME, Messages.getString("UnlodFrame.All")); //$NON-NLS-1$
      extractAllAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
      extractAllAction.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_A));

      Action extractRawAction = new AbstractAction() {
          public void actionPerformed(ActionEvent e) {
             UnlodFrame.this.extractRaw();
          }
       };

       extractRawAction.putValue(Action.NAME, Messages.getString("UnlodFrame.SelectedFileRaw")); //$NON-NLS-1$

       Action extractRawSelAction = new AbstractAction() {
          public void actionPerformed(ActionEvent e) {
             UnlodFrame.this.extractRawSelection();
          }
       };

       extractRawSelAction.putValue(Action.NAME, Messages.getString("UnlodFrame.SelectedFilesRaw")); //$NON-NLS-1$

       Action extractRawAllAction = new AbstractAction() {
          public void actionPerformed(ActionEvent e) {
             UnlodFrame.this.extractRawAll();
          }
       };

       extractRawAllAction.putValue(Action.NAME, Messages.getString("UnlodFrame.AllRaw")); //$NON-NLS-1$

      Action prefAction = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            UnlodFrame.this.openPreferences();
         }
      };

      prefAction.putValue(Action.NAME, Messages.getString("UnlodFrame.Preferences") + Messages.getString("UnlodFrame.willOpenPanelSymbol")); //$NON-NLS-1$ //$NON-NLS-2$
      prefAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
      prefAction.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_P));

      Action aboutAction = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            UnlodFrame.this.about();
         }
      };

      aboutAction.putValue(Action.NAME, Messages.getString("UnlodFrame.About") + Messages.getString("UnlodFrame.willOpenPanelSymbol")); //$NON-NLS-1$ //$NON-NLS-2$
      aboutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
      aboutAction.putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_A));

      fileMenu = new JMenu(Messages.getString("UnlodFrame.File")); //$NON-NLS-1$
      fileMenu.setMnemonic(KeyEvent.VK_F);

      openItem = new JMenuItem(openAction);
      exitItem = new JMenuItem(exitAction);

      /*fileMenu.add(new JMenuItem(openAction));
      fileMenu.add(new JMenuItem(exitAction));*/

      this.createFileMenu(fileMenu, preferences.getRecentFiles());

      mb.add(fileMenu);


        AbstractAction displayByEntryNameAction = new AbstractAction()
        {
            public void actionPerformed(ActionEvent actionevent)
            {
                // TODO: displayByEntryName();
            }
        };
        displayByEntryNameAction.putValue(Action.NAME, Messages.getString("UnlodFrame.DisplayEntryName")); //$NON-NLS-1$ //$NON-NLS-2$
    
        AbstractAction displayByDataNameAction = new AbstractAction()
        {
            public void actionPerformed(ActionEvent actionevent)
            {
                // TODO: displayByEntryName();
            }
        };
        displayByDataNameAction.putValue(Action.NAME, Messages.getString("UnlodFrame.DisplayDataName")); //$NON-NLS-1$ //$NON-NLS-2$
    
        AbstractAction displayByBothEntryAndDataNameAction = new AbstractAction()
        {
            public void actionPerformed(ActionEvent actionevent)
            {
                // TODO: displayByBothEntryAndDataName();
            }
        };
        displayByBothEntryAndDataNameAction.putValue(
                Action.NAME, //$NON-NLS-1$
            Messages.getString("UnlodFrame.DisplayBothEntryAndDataNames")); //$NON-NLS-1$
    
        JMenu viewMenu = new JMenu(Messages.getString("UnlodFrame.View")); //$NON-NLS-1$
        viewMenu.setMnemonic('V');
//        viewMenu.add(new JMenuItem(displayByEntryNameAction));
//        viewMenu.add(new JMenuItem(displayByDataNameAction));
//        viewMenu.add(new JMenuItem(displayByBothEntryAndDataNameAction));
//        viewMenu.addSeparator();
        createFileEntryComparatorMenu(viewMenu);
        mb.add(viewMenu);


        AbstractAction displayWithDefaultHandlerAction = new AbstractAction()
        {
            public void actionPerformed(ActionEvent actionevent)
            {
        	    int result = showConfirmDialog(Messages.getString("UnlodFrame.WillPossiblyLoseChangesContinuePrompt"), Messages.getString("UnlodFrame.ChangeDisplayModeHandler")); //$NON-NLS-1$ //$NON-NLS-2$
        	    if (JOptionPane.YES_OPTION != result)  return;

        	    LodResource selectedEntry = lodFileList.getSelectedEntry();
        	    if (null == selectedEntry)
        	    {
        	        getApplicationController().getProgressDisplayer().displayErrorPanel(Messages.getString("UnlodFrame.NoSelection")); //$NON-NLS-1$
            	    return;
        	    }

        	    LodResourceController lodResourceController = LodResourceController.getLodResourceControllerFor(getApplicationController(), selectedEntry);
                lodResourceController.changeToDefaultHandler();
            }
        };
        displayWithDefaultHandlerAction.putValue(
                Action.NAME, //$NON-NLS-1$
            Messages.getString("UnlodFrame.Default")); //$NON-NLS-1$

        AbstractAction displayWithDataHandlerAction = new AbstractAction()
        {
            public void actionPerformed(ActionEvent actionevent)
            {
        	    int result = showConfirmDialog(Messages.getString("UnlodFrame.WillPossiblyLoseChangesContinuePrompt"), Messages.getString("UnlodFrame.ChangeDisplayModeHandler")); //$NON-NLS-1$ //$NON-NLS-2$
        	    if (JOptionPane.YES_OPTION != result)  return;

        	    LodResource selectedEntry = lodFileList.getSelectedEntry();
        	    if (null == selectedEntry)
        	    {
        	        getApplicationController().getProgressDisplayer().displayErrorPanel(Messages.getString("UnlodFrame.NoSelection")); //$NON-NLS-1$
            	    return;
        	    }
        	    
        	    LodResourceController lodResourceController = LodResourceController.getLodResourceControllerFor(getApplicationController(), selectedEntry);
                lodResourceController.changeToHandler(new DataHandler());
            }
        };
        displayWithDataHandlerAction.putValue(
                Action.NAME, //$NON-NLS-1$
            Messages.getString("UnlodFrame.Data")); //$NON-NLS-1$

        
        JMenu displayModeMenu = new JMenu(Messages.getString("UnlodFrame.DisplayMode")); //$NON-NLS-1$
        ButtonGroup displayModeGroup = new ButtonGroup();
        
        JRadioButtonMenuItem displayWithDefaultHandlerMenuItem = new JRadioButtonMenuItem(displayWithDefaultHandlerAction);
        JRadioButtonMenuItem displayWithDataHandlerMenuItem = new JRadioButtonMenuItem(displayWithDataHandlerAction);
        
        displayWithDefaultHandlerMenuItem.setSelected(true);
        
        displayModeMenu.add(displayWithDefaultHandlerMenuItem);
        displayModeGroup.add(displayWithDefaultHandlerMenuItem);
        
        displayModeMenu.add(displayWithDataHandlerMenuItem);
        displayModeGroup.add(displayWithDataHandlerMenuItem);
        
        mb.add(displayModeMenu);

        JMenu extractionMenu = new JMenu(Messages.getString("UnlodFrame.Extract")); //$NON-NLS-1$
        extractionMenu.setMnemonic(KeyEvent.VK_E);

        extractionMenu.add(new JMenuItem(setDirAction));
        extractionMenu.addSeparator();
        extractionMenu.add(new JMenuItem(extractAction));
        extractionMenu.add(new JMenuItem(extractSelAction));
        extractionMenu.add(new JMenuItem(extractAllAction));
        extractionMenu.addSeparator();
        extractionMenu.add(new JMenuItem(extractRawAction));
        extractionMenu.add(new JMenuItem(extractRawSelAction));
        extractionMenu.add(new JMenuItem(extractRawAllAction));

        mb.add(extractionMenu);

        JMenu importMenu = new JMenu(Messages.getString("UnlodFrame.Import")); //$NON-NLS-1$

        Action setImportDirAction = new AbstractAction() {
           public void actionPerformed(ActionEvent e) {
              UnlodFrame.this.setImportDirectory();
           }
        };
        setImportDirAction.putValue(Action.NAME, Messages.getString("UnlodFrame.SetImportDirectory") + Messages.getString("UnlodFrame.willOpenPanelSymbol")); //$NON-NLS-1$ //$NON-NLS-2$

        Action importCurrentResourceAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
               UnlodFrame.this.importCurrentResource();
            }
        };
        importCurrentResourceAction.putValue(Action.NAME, Messages.getString("UnlodFrame.CurrentlyDisplayedResource")); //$NON-NLS-1$

        Action quickAppendCurrentResourceAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
               UnlodFrame.this.quickAppendCurrentResource();
            }
        };
        quickAppendCurrentResourceAction.putValue(Action.NAME, Messages.getString("UnlodFrame.QuickAppendCurrentlyDisplayedResource")); //$NON-NLS-1$

        Action quickAppendSelectedFileAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
               UnlodFrame.this.quickAppendSelectedFile();
            }
         };
         quickAppendSelectedFileAction.putValue(Action.NAME, Messages.getString("UnlodFrame.QuickAppendSelectedFile")); //$NON-NLS-1$
       
        Action importSelectionAction = new AbstractAction() {
           public void actionPerformed(ActionEvent e) {
              UnlodFrame.this.importSelectedFiles();
           }
        };
        importSelectionAction.putValue(Action.NAME, Messages.getString("UnlodFrame.SelectedFiles")); //$NON-NLS-1$

        Action importAllAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
               UnlodFrame.this.importAll();
            }
         };
         importAllAction.putValue(Action.NAME, Messages.getString("UnlodFrame.All")); //$NON-NLS-1$
         
        importMenu.add(new JMenuItem(quickAppendCurrentResourceAction));
        importMenu.addSeparator();
        importMenu.add(new JMenuItem(importCurrentResourceAction));
        importMenu.addSeparator();
        importMenu.add(new JMenuItem(quickAppendSelectedFileAction));
        importMenu.addSeparator();
        importMenu.add(new JMenuItem(importSelectionAction));
        importMenu.add(new JMenuItem(importAllAction));

        mb.add(importMenu);

      JMenu prefMenu = new JMenu(Messages.getString("UnlodFrame.Preferences")); //$NON-NLS-1$
      prefMenu.setMnemonic(KeyEvent.VK_P);

      prefMenu.add(new JMenuItem(prefAction));

      mb.add(prefMenu);

      JMenu aboutMenu = new JMenu(Messages.getString("UnlodFrame.About")); //$NON-NLS-1$
      aboutMenu.setMnemonic(KeyEvent.VK_A);

      aboutMenu.add(new JMenuItem(aboutAction));

      mb.add(aboutMenu);

      this.setJMenuBar(mb);

      lodFileManager = new LodFileManager(preferences, this);

      WindowUtilities.centerOnScreen(this);
	   //this.pack();
	}

	private class LodFileListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
		   if (!e.getValueIsAdjusting())
			      UnlodFrame.this.setLodEntry(lodFileList.getSelectedEntry());
		}
	}

	private class RecentFileAction extends AbstractAction {
	   protected File file;

	   public RecentFileAction(File file, int index) {
	      this.file = file;
	      this.putValue(Action.NAME, (index + 1) + Messages.getString("UnlodFrame.horizontalSeparator") + file.getAbsolutePath()); //$NON-NLS-1$
	   }

	   public void actionPerformed(ActionEvent e) {
	      UnlodFrame.this.openLodFile(file);
	   }
	}

}