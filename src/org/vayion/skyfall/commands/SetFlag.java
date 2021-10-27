package org.vayion.skyfall.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vayion.skyfall.Main;

public class SetFlag implements CommandExecutor {
	Main main;

	public SetFlag(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;

			if(args.length==0) {player.sendMessage(ChatColor.RED+ "[Skyfall] Missing Args.");return false;}
			Location loc = player.getLocation();
			
			String arg = args[0];
			
			arg.toLowerCase();
			
			switch (arg) {
			case "a":
				main.getFlagA().setFlag(loc);
				main.getFileManager().setFlagA(loc);
				player.sendMessage(ChatColor.GREEN+"[Skyfall] Sucessfully set Flag A.");
				break;
			case "b":
				main.getFlagB().setFlag(loc);
				main.getFileManager().setFlagB(loc);
				player.sendMessage(ChatColor.GREEN+"[Skyfall] Sucessfully set Flag B.");
				break;
			case "c":
				main.getFlagC().setFlag(loc);
				main.getFileManager().setFlagC(loc);
				player.sendMessage(ChatColor.GREEN+"[Skyfall] Sucessfully set Flag C.");
				break;

			default:
				player.sendMessage(ChatColor.RED+ "[Skyfall] Arguments are invalid.");return false;
			}
			
			return true;
		}
		
		System.out.println("You cant do this in the console");
		return false;
	}
}
