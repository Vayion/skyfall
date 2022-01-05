package org.vayion.skyfall.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.vayion.skyfall.Main;

public class PostGameManager implements Listener {
	
	Main main;
	
	public PostGameManager(Main main, ArrayList<Player> winners) {
		this.main = main;
		chirp();
		Bukkit.getOnlinePlayers().forEach(player -> makeWin(player));
		Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 3, 1));
		Bukkit.getOnlinePlayers().forEach(player -> player.teleport(main.getLobbySpawn()));
		Bukkit.getOnlinePlayers().forEach(player -> player.setGameMode(GameMode.SURVIVAL));
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			public void run() {
				Bukkit.shutdown();
		     }
		}, (20*40));
	}
	
	public void makeWin(Player player) {
		player.spawnParticle(Particle.NOTE, player.getLocation(), 100, 1, 1, 1);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			public void run() {
				makeWin(player);
		     }
		}, (5));
	}
	
	public void chirp() {
		Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.RECORD_CHIRP, 10, 1));
	}
	

	@EventHandler
    public void serverListPing(ServerListPingEvent event){
		event.setMotd(ChatColor.YELLOW+"Just ended!");
    }
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		event.getPlayer().sendMessage(ChatColor.RED+"You can't do this here.");
		event.setCancelled(true);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		event.getPlayer().sendMessage(ChatColor.RED+"You can't do this here.");
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}
	
	
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onOpenInventory(InventoryOpenEvent event) {
		if(!event.getInventory().getType().equals(InventoryType.PLAYER)) {event.setCancelled(true);}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.setGameMode(GameMode.SURVIVAL);
		 player.setScoreboard(main.getScoreboard());
		
		if(main.getLobbySpawn()!= null) {
			player.teleport(main.getLobbySpawn());
		}
	}
	
	@EventHandler
	public void onPlayerKick (PlayerKickEvent event) {
		main.getTeamManager().removeFromAll(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerLeave (PlayerQuitEvent event) {
		main.getTeamManager().removeFromAll(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(player.getLocation().getBlock().getType().equals(Material.WATER)) {
			
			main.getTeamManager().sendToSpawn(player, false);
			player.teleport(main.getLobbySpawn());
		}
	}
	
	
	
}
