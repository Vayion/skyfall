package org.vayion.skyfall;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class TeamManager {
	
	private Team redTeam;
	private Team blueTeam;
	
	private ArrayList<Player> red;
	private ArrayList<Player> blue;
	

	private ItemStack[] blueInv;
	private ItemStack[] redInv;
	
	Main main;
	
	public TeamManager(Main main) {
		this.main = main;
		red = new ArrayList<Player>();
		blue = new ArrayList<Player>();
		
		try {
			main.getScoreboard().getTeam("red").unregister();
		}catch(Exception e) {}
			
		redTeam = main.getScoreboard().registerNewTeam("red");
		redTeam.setAllowFriendlyFire(false);
		
		try {
			main.getScoreboard().getTeam("blue").unregister();
		}catch(Exception e) {}
		
		blueTeam = main.getScoreboard().registerNewTeam("blue");
		blueTeam.setAllowFriendlyFire(false);
		

		
		redTeam.setPrefix(ChatColor.RED+"");
		blueTeam.setPrefix(ChatColor.BLUE+"");
		
		
	}
	
	public void addToBlue(Player player) {
		if (blue.size()>=6) {player.sendMessage(ChatColor.YELLOW+"This Team is already full.");return;}
		if (blue.contains(player)) {player.sendMessage(ChatColor.BLUE+"You already joined this team."); return;}
		if (red.contains(player)) {red.remove(player);redTeam.removePlayer(player);}
		blue.add(player);
		blueTeam.addPlayer(player);
		player.sendMessage(ChatColor.BLUE+"Joined Team Blue.");
	}
	
	public void addToRed(Player player) {
		if (red.size()>=6) {player.sendMessage(ChatColor.YELLOW+"This Team is already full.");return;}
		if (red.contains(player)) {player.sendMessage(ChatColor.RED+"You already joined this team."); return;}
		if (blue.contains(player)) {blue.remove(player);blueTeam.removePlayer(player);}
		red.add(player);
		redTeam.addPlayer(player);
		player.sendMessage(ChatColor.RED+"Joined Team Red.");
	}
	
	public void start() {
		for (int r = 0; r < red.size(); r++) {
			sendToSpawn(red.get(r), true);
		}
   		 
   	 	for (int b = 0; b < blue.size(); b++) {
   	 		sendToSpawn(blue.get(b), false);
   	 	}
	}
	
	public void setBlueInv(ItemStack[] items) {blueInv = items;}
	public void setRedInv(ItemStack[] items) {redInv = items;}
	
	public ItemStack[] getBlueInv() {return blueInv;}
	public ItemStack[] getRedInv() {return redInv;}
	
	
	
	/*
	 * true = red, false = blue
	 */
	public void sendToSpawn(Player player, boolean red) {
		player.setGameMode(GameMode.SURVIVAL);
		if(red) {
			player.getInventory().setContents(redInv);
			player.updateInventory();
			player.teleport(main.getSpawn1());
		}
		else {
			player.getInventory().setContents(blueInv);
			player.updateInventory();
			player.teleport(main.getSpawn2());
		}
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setFireTicks(0);
	}
	
	public ArrayList<Player> getBlue() {
		return blue;
	}
	public ArrayList<Player> getRed() {
		return red;
	}
	
	
	
	
	
}
