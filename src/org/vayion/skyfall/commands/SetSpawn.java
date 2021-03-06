package org.vayion.skyfall.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vayion.skyfall.Main;

public class SetSpawn  implements CommandExecutor {
	Main main;

	public SetSpawn(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			if(!player.hasPermission("skyfall.admin")) {player.sendMessage(ChatColor.RED+"You don't have the required permissions to do this.");}

			if(args.length==0) {player.sendMessage(ChatColor.RED+ "[Skyfall] Missing Args.");return false;}
			Location loc = player.getLocation();
			
			String arg = args[0];
			
			arg.toLowerCase();
			
			switch (arg) {
			case "1":
				main.setSpawn1(loc);
				main.getFileManager().setSpawn1(loc);
				player.sendMessage(ChatColor.GREEN+"Sucessfully set Spawn 1.");
				break;
			case "2":
				main.setSpawn2(loc);
				main.getFileManager().setSpawn2(loc);
				player.sendMessage(ChatColor.GREEN+"Sucessfully set Spawn 2.");
				break;

			default:
				player.sendMessage(ChatColor.RED+ "[Skyfall] Arguments are invalid.");return false;
			}
			
			return true;
		}
		
		System.out.println("You can't do this in the console");
		return false;
	}
}
