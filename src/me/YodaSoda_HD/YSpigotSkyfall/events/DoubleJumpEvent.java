package me.YodaSoda_HD.YSpigotSkyfall.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import me.YodaSoda_HD.YSpigotSkyfall.game.GameMain;

public class DoubleJumpEvent implements Listener{
	GameMain game;
	public DoubleJumpEvent(GameMain _game) {
		System.out.println(ChatColor.RED + "[YSpigotSkyfall]"+ChatColor.WHITE+" Loaded DoubleJump Event Listener.");
		game= _game;
		}
	@EventHandler
	public void onToggleFlight(PlayerToggleFlightEvent event) {
		event.setCancelled(true);
		doubleJump(event.getPlayer());
	}
	
	@EventHandler
	public void onToggleSneak(PlayerToggleSneakEvent event) {
		doubleJump(event.getPlayer());
	}
	public void doubleJump(Player player) {
		game.getCM().doubleJump(player);
	}
}
