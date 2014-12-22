package de.tkprog.tkBATtray.main;

public class Config_defaults {
	public static final String updateThreadSleepTime = "updateThreadSleepTime";
	public static final String batDirFolderLocation = "batDirFolderLocation";

	public static void set(Config g){
		g.setDefaultValue(updateThreadSleepTime, "5000");
		g.setDefaultValue(batDirFolderLocation, "/sys/class/power_supply/BAT/");
	}
}
