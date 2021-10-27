package org.YodaSoda_HD.YSpigotSkyfall.commands;

import org.YodaSoda_HD.YSpigotSkyfall.ArenaManager.Arena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class skyfalladmin implements CommandExecutor{
	Arena arena;
	
	public skyfalladmin(Arena _arena) {
		arena = _arena;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			return true;
		}
		System.out.println("Hi Console!");
		return false;
	}

}
