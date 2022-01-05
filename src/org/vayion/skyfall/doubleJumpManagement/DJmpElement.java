package org.vayion.skyfall.doubleJumpManagement;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public abstract class DJmpElement {
	
	public abstract DJmpElement addPlayer(Player player);
	public abstract DJmpElement removePlayer(Player player);
	
	//boolean return value to only cancel PlayerToggleFlyEvent when player is actually a player.
	public abstract boolean handleDoubleJump(Player player);

	public abstract  ArrayList<Player> getPlayers();
	public abstract  ArrayList<String> getPlayerNames();
}
