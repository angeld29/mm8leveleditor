package org.gamenet.application.mm8leveleditor.data.mm6.fileFormat;


public class NpcProfTxt extends Txt {

	public class Quest
	{
		private String npcProfession = null;
		private String randomChance = null;
		private String joinCost = null;
		private String personality = null;
		private String actionText = null;
		private String inPartyBenefit = null;
		private String joinText = null;
	}
	
	public String getNameAtIndex(int index)
	{
		return getStringForMatchingColumnValue(1, 0, String.valueOf(index));
	}
}
