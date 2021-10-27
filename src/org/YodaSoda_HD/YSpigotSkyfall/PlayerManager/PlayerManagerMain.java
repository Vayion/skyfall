package org.YodaSoda_HD.YSpigotSkyfall.PlayerManager;
import org.YodaSoda_HD.YSpigotSkyfall.ArenaManager.Arena;
import org.YodaSoda_HD.YSpigotSkyfall.commands.listPlayers;
import org.YodaSoda_HD.YSpigotSkyfall.events.PlayerJoin;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerManagerMain {
	Arena arena;
	PlayerJoin PJ;
	listPlayers lPC;

	Player[] players;
	int maxPlayers;
	
	public PlayerManagerMain(Arena _arena) {

		
		arena = _arena;
		players = new Player[maxPlayers];
		
		maxPlayers = arena.getMaxPlayers();
	}
	
	public void listPlayers(Player player) {
		for(int i = 0; i < maxPlayers; i++) {
			if(players[i]!=null) {
				player.sendMessage(players[i].getName());
			}
		}
	
	}
	
	public void addPlayer(Player player) {
		for(int i = 0; i < maxPlayers; i++) {
			if(players[i]==null) {
				players[i] = player;
				return;
			}
		}
		player.setGameMode(GameMode.SPECTATOR);
	
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public boolean checkForPlayer(Player player) {
		for(int i = 0; i < maxPlayers; i++) {
			if(players[i].equals(player)) {
				return true;
			}
		}
		return false;
	}
}
