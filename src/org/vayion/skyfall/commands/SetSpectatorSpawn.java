package org.vayion.skyfall.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vayion.skyfall.Main;

public class SetSpectatorSpawn implements CommandExecutor {
	Main main;

	public SetSpectatorSpawn(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			if(!player.hasPermission("skyfall.admin")) {player.sendMessage(ChatColor.RED+"You don't have the required permissions to do this.");}

			Location loc = player.getLocation();
			
			
			main.setSpecSpawn(loc);
			main.getFileManager().setSpecSpawn(loc);
			player.sendMessage(ChatColor.GREEN+"Sucessfully set Spectator Spawn.");
			
			return true;
		}
		
		System.out.println("You can't do this in the console");
		return false;
	}
}