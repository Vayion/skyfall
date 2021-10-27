package me.YodaSoda_HD.YSpigotSkyfall.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.YodaSoda_HD.YSpigotSkyfall.game.GameMain;

public class listplayers implements CommandExecutor {
	GameMain game;
	public listplayers(GameMain _game) {
		game = _game;
		System.out.println(ChatColor.RED + "[YSpigotSkyfall]"+ChatColor.WHITE+" Loaded listPlayers Command");
	}
	public boolean onCommand(CommandSender _s, Command _c ,String _label, String[] _args) {
		if(_s instanceof Player) {
			Player player = (Player) _s;
			player.sendMessage("debug");
			game.getCM().listPlayers(player);
		}
		
		return false;
	}
}

