package org.vayion.skyfall.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vayion.skyfall.Main;

public class SetLobbySpawn implements CommandExecutor {
	Main main;
	
	public SetLobbySpawn(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(!(sender instanceof Player)) {
			Bukkit.getLogger().info("This command can't be used in console");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(player.hasPermission("skyfall.admin")) {
			main.setLobby(player.getLocation());
			main.getFileManager().saveSpawn(player.getLocation());
			player.sendMessage(ChatColor.GREEN+"Sucessfully set new Lobby Spawn.");
		}else {player.sendMessage(ChatColor.RED+"[Skyfall] You do not have the required permissions to do this.");}
		return false;
	}
	
	

}
