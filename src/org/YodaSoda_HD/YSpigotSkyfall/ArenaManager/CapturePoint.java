package org.YodaSoda_HD.YSpigotSkyfall.ArenaManager;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CapturePoint {
	
	Arena arena;
	Location loc;
	Location corner1;
	Location corner2;
	
	Player[] playersOnPoint;
	int[] playerWeight;

	int corner1X;
	int corner1Y;
	int corner1Z;

	int corner2X;
	int corner2Y;
	int corner2Z;
	
	World world;
	int status;
	
	int maxPlayers;
	
	public CapturePoint(Arena _arena, Location _loc, World _world) {
		loc = _loc;
		corner1 = loc.subtract(new Vector(-2,-1,-2));
		corner2 = loc.subtract(new Vector(2,5,2));
		arena = _arena;
		world = _world;
		maxPlayers = arena.getMaxPlayers();

		corner1X = corner1.getBlockX();
		corner1Y = corner1.getBlockY();
		corner1Z = corner1.getBlockZ();

		corner2X = corner2.getBlockX();
		corner2Y = corner2.getBlockY();
		corner2Z = corner2.getBlockZ();
		
		playersOnPoint = new Player[maxPlayers];
		playerWeight = new int[maxPlayers];
	}
	
	public void update() {
		for(int i= 0; i< maxPlayers; i++) {
			status = status + playerWeight[i];
			
		}
	}

	public void enterArea(Player player){
		for(int i = 0; i < maxPlayers; i++) {
			if(playersOnPoint[i]==null) {
				playersOnPoint[i] = player;
				playerWeight[i] = arena.getPlayerWeight(player);
				return;
			}
		}
	}

	public void leaveArea(Player player){
		for(int i = 0; i < maxPlayers; i++) {
			if(playersOnPoint[i].equals(player)) {
				playersOnPoint[i] = null;
				playerWeight[i] = 0;
				return;
			}
		}
	}
	
	public boolean findPlayer(Player player) {
		for(int i = 0; i < maxPlayers; i++) {
			if(playersOnPoint[i].equals(player)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInCapturePoint(Location loc, World checkWorld) {
		if(checkWorld.equals(world)) {
			int X = loc.getBlockX();
			int Y = loc.getBlockY();
			int Z = loc.getBlockZ();
			if(((corner1X)<=X&&(corner2X>=X))&&((corner1Y)<=Y&&(corner2Y>=Y))&&((corner1Z)<=Z&&(corner2Z>=Z))) {
				return true;
			}
		}
		return false;
	}
	public void buildFlagpole() {
		
	}
}
