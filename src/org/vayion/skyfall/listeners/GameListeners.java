package org.vayion.skyfall.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.vayion.skyfall.Main;

public class GameListeners implements Listener {
	
	private Main main;
	
	
	
	public GameListeners (Main main) {
		this.main = main;
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
	public void onOpenInventory(InventoryOpenEvent event) {
		if(!event.getInventory().getType().equals(InventoryType.PLAYER)) {event.setCancelled(true);}
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
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		main.getTeamManager().addToSpectator(player);
		player.teleport(main.getSpectatorSpawn());

		ArrayList<Player> red = main.getTeamRed();
		ArrayList<Player> blue = main.getTeamBlue();
		

		if (red.contains(player)) {red.remove(player);}
		if (blue.contains(player)) {blue.remove(player);}
	}
	
	@EventHandler
	public void onPlayerDeath(EntityDamageEvent event) {
		if(event.getCause().equals(DamageCause.ENTITY_ATTACK)) {return;}
		if(!(event.getEntity()instanceof Player)) {return;}
		Player player = (Player) event.getEntity();
		
		if((player.getHealth()-event.getFinalDamage())<=0) {
			event.setCancelled(true);
			Bukkit.broadcastMessage(getColor(player)+" died to semi-natural reasons.");
			player.playSound(player.getLocation(),Sound.ENTITY_ENDERDRAGON_FLAP,  1, 1);
			

			ArrayList<Player> red = main.getTeamRed();
			ArrayList<Player> blue = main.getTeamBlue();
			

			if (red.contains(player)) {
				main.getTeamManager().sendToSpawn(player, true);
			}
			else if (blue.contains(player)) {
				main.getTeamManager().sendToSpawn(player, false);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeathEntity(EntityDamageByEntityEvent event) {
		if(!(event.getEntity()instanceof Player)) {return;}
		Player player = (Player) event.getEntity();
		
		if((player.getHealth()-event.getFinalDamage())<=0) {
			event.setCancelled(true);
			
			if(event.getDamager()instanceof Player) {
				Player damager = (Player) event.getDamager();
				Bukkit.broadcastMessage(getColor(player)+" got killed by "+getColor(damager)+".");
				damager.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
			}
			else {
				if (event.getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getDamager();
					if(arrow.getShooter() instanceof Player) {
						Player shooter = (Player) arrow.getShooter();
						Bukkit.broadcastMessage(getColor(player)+" got shot by "+getColor(shooter)+".");
					}else {
						Bukkit.broadcastMessage(getColor(player)+" died to semi-natural reasons.");
					}
				}
				else {
					Bukkit.broadcastMessage(getColor(player)+" died to semi-natural reasons.");
				}
			}
			player.playSound(player.getLocation(),Sound.ENTITY_ENDERDRAGON_FLAP,  1, 1);
			

			ArrayList<Player> red = main.getTeamRed();
			ArrayList<Player> blue = main.getTeamBlue();
			

			if (red.contains(player)) {
				main.getTeamManager().sendToSpawn(player, true);
			}
			else if (blue.contains(player)) {
				main.getTeamManager().sendToSpawn(player, false);
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if((player.getLocation().getBlock().getType().equals(Material.WATER))||(player.getLocation().getBlock().getType().equals(Material.STATIONARY_WATER))) {
			if(main.getTeamRed().contains(player)) {
				main.getTeamManager().sendToSpawn(player, true);
			}
			else if (main.getTeamBlue().contains(player)) {
				main.getTeamManager().sendToSpawn(player, false);
			}
		}
	}
	
	private String getColor(Player player) {
		String name;
		if(main.getTeamBlue().contains(player)) {
			name = ChatColor.BLUE+player.getName()+ChatColor.GREEN;
		}else {
			name = ChatColor.RED  + player.getName()+ChatColor.GREEN;
		}
		
		return name;
	}
	
	
}
