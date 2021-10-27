package org.YodaSoda_HD.YSpigotSkyfall;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	arenaManager AM;
	
	@Override
	public void onEnable(){
		AM = new arenaManager(this);
	}

}
