package org.vayion.skyfall.listeners;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
	public void onPlayerHit(EntityDamageByEntityEvent event) {
		if((event.getEntity()instanceof Player)&&(event.getDamager() instanceof Player)) {

    		ArrayList<Player> red = main.getTeamRed();
    		ArrayList<Player> blue = main.getTeamBlue();
    		
			Player player1 = (Player) event.getDamager();
			Player player2 = (Player) event.getEntity();
			
			if(((blue.contains(player1))&&(blue.contains(player2)))||((red.contains(player1))&&(red.contains(player2)))) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onOpenInventory(InventoryOpenEvent event) {
		if(!event.getInventory().getType().equals(InventoryType.PLAYER)) {event.setCancelled(true);}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.getPlayer().setGameMode(GameMode.SPECTATOR);

		ArrayList<Player> red = main.getTeamRed();
		ArrayList<Player> blue = main.getTeamBlue();
		

		if (red.contains(player)) {red.remove(player);}
		if (blue.contains(player)) {blue.remove(player);}
	}
	
	@EventHandler
	public void onPlayerDeath(EntityDamageEvent event) {
		if(!(event.getEntity()instanceof Player)) {return;}
		Player player = (Player) event.getEntity();
		
		if((player.getHealth()-event.getFinalDamage())<=0) {
			event.setCancelled(true);

			ArrayList<Player> red = main.getTeamRed();
			ArrayList<Player> blue = main.getTeamBlue();
			

			if (red.contains(player)) {
				main.sendToSpawn(player, true);
			}
			else if (blue.contains(player)) {
				main.sendToSpawn(player, false);
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if((player.getLocation().getBlock().getType().equals(Material.WATER))||(player.getLocation().getBlock().getType().equals(Material.STATIONARY_WATER))) {
			if(main.getTeamRed().contains(player)) {
				main.sendToSpawn(player, true);
			}
			else {
				main.sendToSpawn(player, false);
			}
		}
	}
	
	
}
