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
import org.vayion.skyfall.commands.ToggleEditCmd;

public class LobbyListeners implements Listener {
	
	private Main main;
	private ArrayList<Player> editors;
	private boolean toggleable;
	
	public LobbyListeners(Main main) {
		toggleable = true;
		this.main = main;
		editors = new ArrayList<Player>();
		main.getCommand("toggleEdit").setExecutor(new ToggleEditCmd(this));
	}
	
	public void disableEdit() {
		editors.forEach(editor-> editor.setGameMode(GameMode.SURVIVAL));
		toggleable = false;
		
	}
	
	public boolean isToggleable() {
		return toggleable;
	}
	
	public ArrayList<Player> getEditors() {return editors;}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(editors.contains(event.getPlayer())) {
			return;
		}
		event.getPlayer().sendMessage(ChatColor.RED+"You can't do this here.");
		event.setCancelled(true);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(editors.contains(event.getPlayer())) {
			return;
		}
		event.getPlayer().sendMessage(ChatColor.RED+"You can't do this here.");
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent event) {
		if(editors.contains(event.getPlayer())) {
			return;
		}
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
		if(editors.contains(event.getPlayer())) {
			return;
		}
		if(!event.getInventory().getType().equals(InventoryType.PLAYER)) {event.setCancelled(true);}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.getPlayer().setGameMode(GameMode.SURVIVAL);
		
		if(main.getLobbySpawn()!= null) {
			player.teleport(main.getLobbySpawn());
		}
	}
	
	@EventHandler
	public void onPlayerDeath(EntityDamageEvent event) {
		if(!(event.getEntity()instanceof Player)) {return;}
		Player player = (Player) event.getEntity();
		
		if((player.getHealth()-event.getFinalDamage())<=0) {
			event.setCancelled(true);


			main.getTeamManager().sendToSpawn(player, true);
			player.teleport(main.getLobbySpawn());
		}
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
