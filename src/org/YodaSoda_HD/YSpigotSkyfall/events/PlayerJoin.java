package org.YodaSoda_HD.YSpigotSkyfall.events;

import org.YodaSoda_HD.YSpigotSkyfall.PlayerManager.PlayerManagerMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
	PlayerManagerMain PMMain;
	
	public PlayerJoin(PlayerManagerMain _PMMain) {
		PMMain = _PMMain;
		
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
	}
}
