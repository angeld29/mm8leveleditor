package org.gamenet.application.mm8leveleditor;

import org.gamenet.application.mm8leveleditor.data.GameVersion;

public class AdditionalPreferences {
	
	// TODO: this needs to be merged in with the regular Preferences class?
	
	static private AdditionalPreferences instance = new AdditionalPreferences();
	static public AdditionalPreferences getInstance()
	{
		return instance;
	}
	
	private int gameVersion = GameVersion.UNKNOWN;
	
	public int getGameVersion() {
		return gameVersion;
	}

	public void setGameVersion(int gameVersion) {
		this.gameVersion = gameVersion;
	}
}
