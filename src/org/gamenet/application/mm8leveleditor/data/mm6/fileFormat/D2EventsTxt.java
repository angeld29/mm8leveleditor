package org.gamenet.application.mm8leveleditor.data.mm6.fileFormat;


public class D2EventsTxt extends Txt {

	public class Quest
	{
		private String typeNumber = null;
		private String type = null;
		private String map = null;
		private String picture = null;
		private String name = null;
		private String proprieterName = null;
		private String title = null;
		private String otherNotes = null;
		private String state = null;
		private String rep = null;
		private String per = null;
		private String val = null;
		private String a = null;
		private String b = null;
		private String c = null;
		private String notes1 = null;
		private String notes2 = null;
		private String openSchedule = null;
		private String closedSchedule = null;
		private String otherExitsPicture = null;
		private String otherExitsMap = null;
		private String questBitRestrictions = null;
		private String enterText = null;
		private String lockedText = null;
	}
	
	public String getNameAtIndex(int index)
	{
		if ((0 == index) || (-1 == index))
		{
			return "**Special Location value " + index + "**";
		}
		
		String name = getStringForMatchingColumnValue(5, 0, String.valueOf(index));
		if (null == name)
		{
			return "WARNING: **No name value " + index + " found in 2DEvents.txt resource file**";
		}
		
		return name;
	}
}
