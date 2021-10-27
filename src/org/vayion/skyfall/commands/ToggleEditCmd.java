package org.vayion.skyfall.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.vayion.skyfall.listeners.LobbyListeners;

public class ToggleEditCmd implements CommandExecutor {
	LobbyListeners lobby;
	public ToggleEditCmd(LobbyListeners lobby) {
		this.lobby = lobby;
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(!(sender instanceof Player)) {
			Bukkit.getLogger().info("This command can't be used in console");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(player.hasPermission("skyfall.edit")) {
			if(!lobby.isToggleable()) {player.sendMessage(ChatColor.RED+"[Skyfall] Game already started.");return false;}
			ArrayList<Player> editors = lobby.getEditors();
			if(editors.contains(player)) {
				editors.remove(player);
				player.sendMessage(ChatColor.GREEN+"Disabled editing.");
				LobbyListeners.setWaitingInventory(player);
			}
			else {
				editors.add(player); 
				player.sendMessage(ChatColor.GREEN+"Enabled editing.");
				player.setGameMode(GameMode.CREATIVE);
				ItemStack[] inv = new ItemStack[22];
				player.getInventory().setContents(inv);
				player.updateInventory();
			}
		}else {player.sendMessage(ChatColor.RED+"[Skyfall] You do not have the required permissions to do this.");}
		return false;
	}

	
}
