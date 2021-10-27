package org.vayion.skyfall.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vayion.skyfall.Main;

public class StartCmd implements CommandExecutor {
	Main main;

	public StartCmd(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getLogger().info("This command can't be used in console");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(player.hasPermission("skyfall.start")) {
			if(main.start()) {
				Bukkit.broadcastMessage(ChatColor.GREEN+"Started the game.");
			}
			else {
				sender.sendMessage(ChatColor.RED+"The game already started");
			}
		}else {player.sendMessage(ChatColor.RED+"You don't have the required permissions to do this.");}
		return false;
	}
}