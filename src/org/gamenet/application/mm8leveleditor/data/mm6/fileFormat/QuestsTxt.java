package org.gamenet.application.mm8leveleditor.data.mm6.fileFormat;


public class QuestsTxt extends Txt {

	public class Quest
	{
		private String questBitNumber = null;
		private String actualQuestNoteText = null;
		private String notes = null;
		private String oldQuestNoteText = null;
	}
	
	public String getNameAtIndex(int index)
	{
		return getStringForMatchingColumnValue(1, 0, String.valueOf(index));
	}
}
