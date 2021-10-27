package org.vayion.skyfall.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vayion.skyfall.Main;

public class SaveInventory implements CommandExecutor{

	Main main;
	
	public SaveInventory(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args.length==0) {return false;}
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(main.getStarted()) {player.sendMessage(ChatColor.RED + "The game already started, you can't do this.");}
			String argument = args[0].toLowerCase();
			
			if(argument.equals("red")) {
				main.setRedInv(player.getInventory());
			}
			else if(argument.equals("blue")) {
				main.setBlueInv(player.getInventory());
			}
		}
		return false;
	}
}
	
