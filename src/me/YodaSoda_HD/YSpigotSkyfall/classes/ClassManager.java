package me.YodaSoda_HD.YSpigotSkyfall.classes;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.YodaSoda_HD.YSpigotSkyfall.Main;
import me.YodaSoda_HD.YSpigotSkyfall.game.GameMain;

public class ClassManager {
	Player[] players;
	standartClass[] classes;
	GameMain game;
	int maxPlayers;
	Main main;
	public ClassManager(GameMain _game, int _maxPlayers, Main _main) {
		System.out.println(ChatColor.RED + "[YSpigotSkyfall]"+ChatColor.WHITE+" Loaded Kit Manager");
		game = _game;
		maxPlayers= _maxPlayers;
		players = new Player[maxPlayers];
		classes = new standartClass[maxPlayers];
		main = _main;
	}
	public boolean registerNewStandartClass(Player _player) {
		Player player = _player;
		int PlayerNumber = searchPlayer(player);
		if(PlayerNumber!=maxPlayers) {
			classes[PlayerNumber] = new standartClass(player);
			player.sendMessage("You now use Standard Class");
			return true;
		}
		return false;
	}
	
	public boolean registerNewArchertClass(Player _player) {
		Player player = _player;
		int PlayerNumber = searchPlayer(player);
		if(PlayerNumber!=maxPlayers) {
			classes[PlayerNumber] = new ArcherClass(player);
			player.sendMessage("You now use Archer Class");
			return true;
		}
		return false;
	}
	
	public boolean addPlayer(Player _player) {
		Player player = _player;
		for(int i = 0; i < maxPlayers; i++) {
			if(players[i]==null) {
				players[i]=player;
				registerNewStandartClass(player);
				return true;
			}
		}
		return false;
	}
	
	public boolean removePlayer(Player _player) {
		Player player = _player;
		int id = searchPlayer(player);
		if(id == maxPlayers) {
			return false;
		}
		classes[id]=null;
		players[id]=null;
		return true;
	}
	
	public int searchPlayer(Player wanted) {
		for(int i = 0; i < maxPlayers; i++) {
			if(wanted.equals(players[i])) {
				return i;
			}
		}
		return maxPlayers;
	}
	
	public void doubleJump(Player player) {
		classes[searchPlayer(player)].doubleJump();
	}
	public void listPlayers(Player _player){
		Player player = _player;
		for(int i = 0; i < maxPlayers; i++) {
			if(players[i]!=null) {
				player.sendMessage(player.getName());
			}
		}
	}
	
	
}
