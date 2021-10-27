package me.YodaSoda_HD.YSpigotSkyfall.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.YodaSoda_HD.YSpigotSkyfall.game.GameMain;

public class ClassCommands implements CommandExecutor {
	GameMain game;
	public ClassCommands(GameMain _game) {
		game = _game;
		System.out.println(ChatColor.RED + "[YSpigotSkyfall]"+ChatColor.WHITE+" Loaded Kit Command");
	}
	public boolean onCommand(CommandSender _s, Command _c ,String _label, String[] _args) {
		if(_s instanceof Player) {
			Player player = (Player) _s;
			game.getCM().registerNewArchertClass(player);
		}
		
		return false;
	}
}
