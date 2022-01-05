package org.vayion.skyfall.doubleJumpManagement;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.vayion.skyfall.Main;

public class DJmpEnd extends DJmpElement {
	
	Main main;
	
	public DJmpEnd(Main main) {
		this.main = main;
	}

	public DJmpElement addPlayer(Player player) {
		return new DJmpReference(player, main);
	}

	public DJmpElement removePlayer(Player player) {
		return this;
	}

	public boolean handleDoubleJump(Player player) {
		//player is a spectator or not in game, so he's not handled
		return false;
	}

	public  ArrayList<Player> getPlayers() {
		return new ArrayList<Player>();
	}

	public  ArrayList<String> getPlayerNames() {
		return new  ArrayList<String>();
	}

}
