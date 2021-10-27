package org.vayion.skyfall.commands;

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
			if(!player.hasPermission("skyfall.admin")) {player.sendMessage(ChatColor.RED+"You don't have the required permissions to do this.");}
			if(main.getStarted()) {player.sendMessage(ChatColor.RED + "The game already started, you can't do this.");}
			String argument = args[0].toLowerCase();
			
			if(argument.equals("red")) {
				main.setRedInv(player.getInventory());
				main.getFileManager().setRedInventory(player.getInventory());
				player.sendMessage(ChatColor.GREEN+"[Skyfall] Successfully set Team Red's inventory");
			}
			else if(argument.equals("blue")) {
				main.setBlueInv(player.getInventory());
				main.getFileManager().setBlueInventory(player.getInventory());
				player.sendMessage(ChatColor.GREEN+"[Skyfall] Successfully set Team Blue's inventory");
			}
		}
		return false;
	}
}
	
