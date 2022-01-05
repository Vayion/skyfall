package org.vayion.skyfall.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vayion.skyfall.classes.ClassManager;

public class SelectClass implements CommandExecutor {
	
	ClassManager main;
	
	public SelectClass(ClassManager main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args.length==0) {return false;}
		if(sender instanceof Player) {
			Player player = (Player) sender;
			String argument = args[0].toLowerCase();
			
			if(argument.equals("default")) {main.selectDefault(player);
			}else if(argument.equals("archer")) {main.selectArcher(player);
			}else if(argument.equals("tank")) {main.selectTank(player);
			}else if(argument.equals("assassin")) {main.selectAssassin(player);
			}else if(argument.equals("berserk")) {main.selectDefault(player);
			} else {return false;}
			player.sendMessage(ChatColor.GREEN+"Sucessfully changed classes.");
		}
		return false;
	}
	
	

}
