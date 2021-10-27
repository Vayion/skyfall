package org.YodaSoda_HD.YSpigotSkyfall.commands;

import org.YodaSoda_HD.YSpigotSkyfall.PlayerManager.PlayerManagerMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class listPlayers implements CommandExecutor{
	PlayerManagerMain PMMain;
	
	public listPlayers(PlayerManagerMain _PMMain) {
		PMMain = _PMMain;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			PMMain.listPlayers(player);
			return true;
		}
		System.out.println("Hi Console!");
		return false;
	}

}
