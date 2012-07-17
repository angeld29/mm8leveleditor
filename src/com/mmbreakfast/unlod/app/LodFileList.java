/*
 *  com/mmbreakfast/unlod/app/LodFileList.java
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

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionListener;

import org.gamenet.application.mm8leveleditor.lod.LodEntry;
import org.gamenet.swing.controls.AbstractStringListCellRenderer;

import com.mmbreakfast.unlod.lod.LodFile;


public class LodFileList extends /*JPanel implements Scrollable*/ JList {
	protected LodEntry[] entries = new LodEntry[0];
	protected LodFileListModel listModel = new LodFileListModel();
	protected LodFile lodFile = null;

    public interface LodEntryComparator extends Comparator
    {
        public String getDisplayName();
    }

    LodEntryComparator nameLodEntryComparator = new LodEntryComparator()
        {// <, =, > 
        // -, 0, +
    public int compare(Object o1, Object o2)
        {
            LodEntry le1 = (LodEntry)o1;
            LodEntry le2 = (LodEntry)o2;
            return le1.getName().compareTo(le2.getName());
        }

        public String getDisplayName()
        {
            return "filename (case-sensitive)";
        }
    };

    LodEntryComparator fileTypeLodEntryComparator = new LodEntryComparator()
        {// <, =, > 
        // -, 0, +
    public int compare(Object o1, Object o2)
        {
            LodEntry le1 = (LodEntry)o1;
            LodEntry le2 = (LodEntry)o2;
            return le1.getResourceType().compareTo(le2.getResourceType());
        }

        public String getDisplayName()
        {
            return "file type";
        }
    };

    LodEntryComparator nameIgnoreCaseLodEntryComparator =
        new LodEntryComparator()
        {// <, =, > 
        // -, 0, +
    public int compare(Object o1, Object o2)
        {
            LodEntry le1 = (LodEntry)o1;
            LodEntry le2 = (LodEntry)o2;
            return le1.getName().compareToIgnoreCase(le2.getName());
        }

        public String getDisplayName()
        {
            return "filename";
        }
    };

    LodEntryComparator entryOffsetLodEntryComparator = new LodEntryComparator()
    {
        public int compare(Object o1, Object o2)
        {
            LodEntry le1 = (LodEntry)o1;
            LodEntry le2 = (LodEntry)o2;
            return le1.getEntryOffset() < le2.getEntryOffset() ? -1 : 1;
        }

        public String getDisplayName()
        {
            return "entry offset";
        }
    };

    LodEntryComparator dataOffsetLodEntryComparator = new LodEntryComparator()
    {
        public int compare(Object o1, Object o2)
        {
            LodEntry le1 = (LodEntry)o1;
            LodEntry le2 = (LodEntry)o2;
            return le1.getDataOffset() < le2.getDataOffset() ? -1 : 1;
        }

        public String getDisplayName()
        {
            return "data offset";
        }
    };

    public LodEntryComparator lodEntryComparatorArray[] =
        {
            nameLodEntryComparator,
            nameIgnoreCaseLodEntryComparator,
            fileTypeLodEntryComparator,
            entryOffsetLodEntryComparator,
            dataOffsetLodEntryComparator };

    public LodEntryComparator lastSelectedLodEntryComparator =
        dataOffsetLodEntryComparator;

   public LodFileList(ListSelectionListener listener) {
      this.addListSelectionListener(listener);
      this.setModel(listModel);
      ListCellRenderer itemLabelRenderer = new AbstractStringListCellRenderer()
      {
          protected String getStringForValue(Object value)
          {
              return ((LodEntry)value).getName();
          }
      };
      this.setCellRenderer(itemLabelRenderer);
   }

   public LodEntry[] getSelectedEntries() {
      Object[] sel = this.getSelectedValues();
      LodEntry[] lodEntries = new LodEntry[sel.length];
      System.arraycopy(sel, 0, lodEntries, 0, sel.length);
      return lodEntries;
   }

   public LodEntry getSelectedEntry() {
      return (LodEntry) this.getSelectedValue();
   }

	public void setLodFile(LodFile lodFile) {
	   this.lodFile = lodFile;
		listModel.setLodFileEntries(lodFile.getLodEntries());
	}

	public Map getAllEntries() {
	   return lodFile.getLodEntries();
	}

    public void changeLodEntryComparator(LodEntryComparator aComparator)
    {
        listModel.changeLodEntryComparator(aComparator);
    }

	protected class LodFileListModel extends AbstractListModel {
		public void setLodFileEntries(Map entries) {
		   LodFileList.this.entries = (LodEntry[]) entries.values().toArray(new LodEntry[0]);
           Arrays.sort(LodFileList.this.entries, lastSelectedLodEntryComparator);
		   this.fireContentsChanged(this, 0, LodFileList.this.entries.length - 1);
		}

        public void changeLodEntryComparator(LodEntryComparator newLodEntryComparator)
        {
            lastSelectedLodEntryComparator = newLodEntryComparator;
            Arrays.sort(entries, lastSelectedLodEntryComparator);
            fireContentsChanged(this, 0, entries.length - 1);
        }

		public Object getElementAt(int index) {
			return entries[index];
		}

		public int getSize() {
			return entries.length;
		}
	}
}