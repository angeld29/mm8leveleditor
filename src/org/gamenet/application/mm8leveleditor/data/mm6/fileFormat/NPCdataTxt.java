package org.gamenet.application.mm8leveleditor.data.mm6.fileFormat;


public class NPCdataTxt extends Txt {

	public class NPCdata
	{
		private String name = null;
		private String picture = null;
		private String btb_check__State = null;
		private String fame = null;
		private String reputation = null;
		private String current_2d_location = null;
		private String profession = null;
		private String willJoin = null;
		private String hasNews = null;
		private String eventNumberA = null;
		private String eventNumberB = null;
		private String eventNumberC = null;
		private String notes = null;
	}
	
	public String getNameAtIndex(int index)
	{
		return getStringForMatchingColumnValue(1, 0, String.valueOf(index));
	}
}
