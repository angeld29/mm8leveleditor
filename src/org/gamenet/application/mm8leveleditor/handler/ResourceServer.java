package org.gamenet.application.mm8leveleditor.handler;

import java.io.IOException;

import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.AutonoteTxt;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.AwardsTxt;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.ClassTxt;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.D2EventsTxt;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.ItemsTxt;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.NPCdataTxt;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.NpcProfTxt;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.QuestsTxt;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.Str;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.Txt;
import org.gamenet.application.mm8leveleditor.lod.LodEntry;

import com.mmbreakfast.unlod.lod.LodFile;
import com.mmbreakfast.unlod.lod.NoSuchEntryException;

public class ResourceServer {
	static private ResourceServer instance = null;
	
	static public ResourceServer getInstance()
	{
		if (null == instance)
		{
			instance = new ResourceServer();
		}
		return instance;
	}
	
	private LodFile lodFile;
	
	public LodFile getLodFile() {
		return lodFile;
	}

	public void setLodFile(LodFile lodFile) {
		this.lodFile = lodFile;
		
		classTxt = null;
		itemsTxt = null;
		awardsTxt = null;
		npcProfTxt = null;
		questsTxt = null;
		autonoteTxt = null;
		npcDataTxt = null;
		d2EventsTxt = null;
	}

	public ResourceServer()
	{
		super();
	}
	
	public String getBaseName(String resourceName) {
		if (resourceName.toUpperCase().endsWith(".EVT"))
		{
			resourceName = resourceName.substring(0, resourceName.toUpperCase().lastIndexOf(".EVT"));
		}
		return resourceName;
	}

	public Str getStr(String resourceName)
	{
		LodEntry lodEntry;
		try {
			lodEntry = lodFile.findLodEntryByFileName(resourceName);
		} catch (NoSuchEntryException e) {
			e.printStackTrace();
			return null;
		}

		Str str = new Str();
		try {
			str.initialize(lodEntry.getData(), 0);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return str;
	}

	public Txt getTxt(String resourceName)
	{
		LodEntry lodEntry;
		try {
			lodEntry = lodFile.findLodEntryByFileName(resourceName);
		} catch (NoSuchEntryException e) {
			e.printStackTrace();
			return null;
		}

		Txt txt = new Txt();
		try {
			txt.initialize(lodEntry.getData(), 0);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return txt;
	}

	// TODO: bad caching
	private ItemsTxt itemsTxt = null;
	public ItemsTxt getItemsTxt()
	{
		if (null == itemsTxt)
		{
			LodEntry lodEntry;
			try {
				lodEntry = lodFile.findLodEntryByFileName("ITEMS.TXT");
			} catch (NoSuchEntryException e) {
				e.printStackTrace();
				return null;
			}

			itemsTxt = new ItemsTxt();
			try {
				itemsTxt.initialize(lodEntry.getData(), 0);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return itemsTxt;
	}

	// TODO: bad caching
	private ClassTxt classTxt = null;
	public ClassTxt getClassTxt()
	{
		if (null == classTxt)
		{
			LodEntry lodEntry;
			try {
				lodEntry = lodFile.findLodEntryByFileName("Class.txt");
			} catch (NoSuchEntryException e) {
				e.printStackTrace();
				return null;
			}

			classTxt = new ClassTxt();
			try {
				classTxt.initialize(lodEntry.getData(), 0);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return classTxt;
	}

	// TODO: bad caching
	private AwardsTxt awardsTxt = null;
	public AwardsTxt getAwardsTxt()
	{
		if (null == awardsTxt)
		{
			LodEntry lodEntry;
			try {
				lodEntry = lodFile.findLodEntryByFileName("Awards.txt");
			} catch (NoSuchEntryException e) {
				e.printStackTrace();
				return null;
			}

			awardsTxt = new AwardsTxt();
			try {
				awardsTxt.initialize(lodEntry.getData(), 0);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return awardsTxt;
	}

	// TODO: bad caching
	private NpcProfTxt npcProfTxt = null;
	public NpcProfTxt getNpcProfTxt()
	{
		if (null == npcProfTxt)
		{
			LodEntry lodEntry;
			try {
				lodEntry = lodFile.findLodEntryByFileName("NpcProf.txt");
			} catch (NoSuchEntryException e) {
				e.printStackTrace();
				return null;
			}

			npcProfTxt = new NpcProfTxt();
			try {
				npcProfTxt.initialize(lodEntry.getData(), 0);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return npcProfTxt;
	}

	// TODO: bad caching
	private QuestsTxt questsTxt = null;
	public QuestsTxt getQuestsTxt()
	{
		if (null == questsTxt)
		{
			LodEntry lodEntry;
			try {
				lodEntry = lodFile.findLodEntryByFileName("Quests.txt");
			} catch (NoSuchEntryException e) {
				e.printStackTrace();
				return null;
			}

			questsTxt = new QuestsTxt();
			try {
				questsTxt.initialize(lodEntry.getData(), 0);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return questsTxt;
	}

	// TODO: bad caching
	private D2EventsTxt d2EventsTxt = null;
	public D2EventsTxt getD2EventsTxt()
	{
		if (null == d2EventsTxt)
		{
			LodEntry lodEntry;
			try {
				lodEntry = lodFile.findLodEntryByFileName("2DEvents.txt");
			} catch (NoSuchEntryException e) {
				e.printStackTrace();
				return null;
			}

			d2EventsTxt = new D2EventsTxt();
			try {
				d2EventsTxt.initialize(lodEntry.getData(), 0);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return d2EventsTxt;
	}

	// TODO: bad caching
	private AutonoteTxt autonoteTxt = null;
	public AutonoteTxt getAutonoteTxt()
	{
		if (null == autonoteTxt)
		{
			LodEntry lodEntry;
			try {
				lodEntry = lodFile.findLodEntryByFileName("Autonote.txt");
			} catch (NoSuchEntryException e) {
				e.printStackTrace();
				return null;
			}

			autonoteTxt = new AutonoteTxt();
			try {
				autonoteTxt.initialize(lodEntry.getData(), 0);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return autonoteTxt;
	}

	// TODO: bad caching
	private NPCdataTxt npcDataTxt = null;
	public NPCdataTxt getNPCDataTxt()
	{
		if (null == npcDataTxt)
		{
			LodEntry lodEntry;
			try {
				lodEntry = lodFile.findLodEntryByFileName("NPCdata.txt");
			} catch (NoSuchEntryException e) {
				e.printStackTrace();
				return null;
			}

			npcDataTxt = new NPCdataTxt();
			try {
				npcDataTxt.initialize(lodEntry.getData(), 0);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return npcDataTxt;
	}
}
