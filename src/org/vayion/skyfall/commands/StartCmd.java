package org.vayion.skyfall.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.vayion.skyfall.Main;

public class StartCmd implements CommandExecutor {
	Main main;

	public StartCmd(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(main.start()) {
			Bukkit.broadcastMessage(ChatColor.GREEN+"Started the game.");
		}
		else {
			sender.sendMessage(ChatColor.RED+"The game already started");
		}
		return false;
	}
}