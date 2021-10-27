package me.YodaSoda_HD.YSpigotSkyfall.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import me.YodaSoda_HD.YSpigotSkyfall.game.GameMain;

public class ConnectionEvent implements Listener {
	GameMain game;
	public ConnectionEvent(GameMain _game) {
		game = _game;
		System.out.println(ChatColor.RED + "[YSpigotSkyfall]"+ChatColor.WHITE+" Loaded Connection Event Listener.");
		}
	@EventHandler
	public void onKick(PlayerKickEvent event) {
		//game.removePlayer(event.getPlayer());
	}
	@EventHandler
	public void onDisconnect(PlayerQuitEvent event) {
		//game.removePlayer(event.getPlayer());
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.setVelocity(new Vector(0, 4, 0));
		game.debug(player);
		//game.addPlayer(player);
	}
}
