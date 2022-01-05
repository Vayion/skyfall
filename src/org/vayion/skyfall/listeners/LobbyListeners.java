package org.vayion.skyfall.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
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
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.vayion.skyfall.Main;
import org.vayion.skyfall.commands.ToggleEditCmd;

public class LobbyListeners implements Listener {
	
	private Main main;
	private ArrayList<Player> editors;
	private boolean toggleable;
	private int countdown;
	private boolean initiated;
	
	public LobbyListeners(Main main) {
		toggleable = true;
		this.main = main;
		editors = new ArrayList<Player>();
		main.getCommand("toggleEdit").setExecutor(new ToggleEditCmd(this));
		initiated = false;
		countdown = 30;
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
    public void serverListPing(ServerListPingEvent event){
		if(main.getTeamManager().getAmountPlayers()<12) {
			event.setMotd(ChatColor.GREEN+"Waiting.");	
		}
		else {
			event.setMotd(ChatColor.RED+"Game is already full.");	
		}
    }
	

	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent event) {
		if(editors.contains(event.getPlayer())) {
			return;
		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(editors.contains(event.getPlayer())) {return;}
		if(event.getItem()== null) {return;}
		if(event.getItem().getType().equals(Material.EYE_OF_ENDER)) {event.getPlayer().openInventory(getNewCompassInterface(event.getPlayer()));}
	}
	
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onOpenInventory(InventoryOpenEvent event) {
		if(editors.contains(event.getPlayer())) {
			return;
		}
		if(event.getInventory().getName().equals("Choose a team.")) {return;}
		if(!event.getInventory().getType().equals(InventoryType.PLAYER)) {event.setCancelled(true);}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.setGameMode(GameMode.SURVIVAL);
		if(main.getTeamManager().getAmountPlayers()>=12) {
			main.getTeamManager().addToSpectator(player);
			player.teleport(main.getSpectatorSpawn());
			return;
		}
		setWaitingInventory(player);
		main.getTeamManager().addToTeamless(player);
		 player.setScoreboard(main.getScoreboard());
		
		if(main.getLobbySpawn()!= null) {
			player.teleport(main.getLobbySpawn());
		}
		checkForStart();
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
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(editors.contains(event.getWhoClicked())) {
			return;
		}
		event.setCancelled(true);
		if(event.getInventory().getName().equals("Choose a team.")) {
			int i = event.getSlot() % 9;
			if(i<3) {
				main.addToRed((Player)event.getWhoClicked());
			}
			else if (i<6) {
				main.getTeamManager().addToTeamless((Player)event.getWhoClicked());
				event.getWhoClicked().sendMessage(ChatColor.YELLOW+"You will now be set in a random Team.");
			}
			else {
				main.addToBlue((Player)event.getWhoClicked());
			}
			checkForStart();
			
		}
		
		
	}
	
	
	public Inventory getNewCompassInterface(Player player) {
		
		Inventory inv = Bukkit.createInventory(player, 3*9,"Choose a team.");

		ItemStack redGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 14);
		ItemMeta redM = redGlass.getItemMeta();
		redM.setDisplayName(ChatColor.RED+"Join Red Team.");
		redGlass.setItemMeta(redM);
		
		
		ItemStack blueGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 11);
		ItemMeta blueM = blueGlass.getItemMeta();
		blueM.setDisplayName(ChatColor.BLUE+"Join Blue Team.");
		blueGlass.setItemMeta(blueM);


		ItemStack grayGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 7);
		ItemMeta grayM = blueGlass.getItemMeta();
		grayM.setDisplayName(ChatColor.WHITE+"Random Team.");
		grayGlass.setItemMeta(grayM);

		int i = 0;
		
		for(int a = 0; a < 3; a++) {
			for(int b = 0; b < 3; b++) {inv.setItem(i, redGlass);i++;}
			for(int c = 0; c < 3; c++) {inv.setItem(i, grayGlass);i++;}
			for(int d = 0; d < 3; d++) {inv.setItem(i, blueGlass);i++;}
		}
		

		return inv;
	}

	public static void setWaitingInventory(Player player) {
		ItemStack[] inv = new ItemStack[41];
		ItemStack eye = new ItemStack(Material.EYE_OF_ENDER);
		ItemMeta meta = eye.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN+"Choose a team.");
		eye.setItemMeta(meta);
		inv[4]= eye;
		player.getInventory().setContents(inv);
		player.updateInventory();
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setFireTicks(0);
		player.getPlayer().setGameMode(GameMode.SURVIVAL);
	}
	
	public void countdown() {
		if(!checkIfAllowed()) {
			countdown = 30;
			initiated = false;
			Bukkit.getOnlinePlayers().forEach(player->player.sendMessage(ChatColor.DARK_RED+"Countdown got cancelled.")); 
			return;}
		if(countdown <= 0) {main.start();return;}
		switch (countdown) {
		case 1:
			sendCountdownMessage();break;
		case 2:
			sendCountdownMessage();break;
		case 3:
			sendCountdownMessage();break;
		case 4:
			sendCountdownMessage();break;
		case 5:
			sendCountdownMessage();break;
		case 10:
			sendCountdownMessage();break;
		case 20:
			sendCountdownMessage();break;
		case 30:
			sendCountdownMessage();break;
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			public void run() {
				countdown--;
				countdown();
		    }
		}, (20));
	}
	
	public void cooldownUpdate() {
		if(initiated) {return;}
		if(checkIfAllowed()) {
			countdown();
			initiated = true;
		}
	}
	
	public void sendCountdownMessage() {
		String seconds;
		if(countdown ==1) {
			seconds = "second.";
		}else {
			seconds = "seconds.";
		}
		Bukkit.getOnlinePlayers().forEach(player->player.sendMessage(ChatColor.DARK_GREEN+"Game starts in "+countdown+ " "+seconds));
	}
	
	public boolean checkIfAllowed() {
		if(main.getTeamManager().getAmountPlayers()>3) {
			if((main.getTeamManager().getAmountPlayers()==main.getTeamBlue().size())||(main.getTeamManager().getAmountPlayers()==main.getTeamRed().size())) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	public void checkForStart() {
		if(!initiated) {
			if (checkIfAllowed()) {countdown(); initiated = true;}
		}
	}

}
