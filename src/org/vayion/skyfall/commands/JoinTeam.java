package org.vayion.skyfall.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vayion.skyfall.Main;

public class JoinTeam implements CommandExecutor {
	
	Main main;
	
	public JoinTeam(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args.length==0) {return false;}
		if(sender instanceof Player) {
			Player player = (Player) sender;
			String argument = args[0].toLowerCase();
			
			if(argument.equals("red")) {
				main.addToRed(player);
			}
			else if(argument.equals("blue")) {
				main.addToBlue(player);
			}
		}
		return false;
	}
	
	

}
