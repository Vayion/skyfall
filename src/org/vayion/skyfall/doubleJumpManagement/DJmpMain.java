package org.vayion.skyfall.doubleJumpManagement;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.vayion.skyfall.Main;

public class DJmpMain {
	
	
	Main main;
	DJmpElement first;
	
	public DJmpMain(Main main) {
		this.main = main;
		first = new DJmpEnd(main);
	}
	
	public void start() {
		ArrayList<Player> players = new ArrayList<Player>();
		main.getTeamBlue().forEach(player->players.add(player));
		main.getTeamRed().forEach(player->players.add(player));
		for (int i = 0; i<players.size();i++) {
			first = first.addPlayer(players.get(i));
		}
		first.getPlayerNames().forEach(names->Bukkit.broadcastMessage(names));
	}
	
	public void removePlayer(Player player) {
		first = first.removePlayer(player);
	}
	
	public boolean handleDoubleJump(Player player) {
		return first.handleDoubleJump(player);
	}

}
