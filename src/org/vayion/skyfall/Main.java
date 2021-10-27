package org.vayion.skyfall;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.vayion.skyfall.arenas.Flag;
import org.vayion.skyfall.arenas.Flag.Status;
import org.vayion.skyfall.commands.JoinTeam;
import org.vayion.skyfall.commands.SaveInventory;
import org.vayion.skyfall.commands.SetFlag;
import org.vayion.skyfall.commands.SetSpawn;
import org.vayion.skyfall.commands.StartCmd;
import org.vayion.skyfall.listeners.GameListeners;
import org.vayion.skyfall.listeners.LobbyListeners;

public class Main extends JavaPlugin {
	private ArrayList<Player> red;
	private ArrayList<Player> blue;
	private boolean started;
	private Flag flagA;
	private Flag flagB;
	private Flag flagC;
	
	private Scoreboard scoreboard;
	private ScoreboardManager sManager;
	private Objective objective;
	
	private LobbyListeners lobbyListeners;
	private GameListeners gameListeners;
	
	private Location spawn1;
	private Location spawn2;
	
	private int scoreRed;
	private int scoreBlue;
	
	private boolean finished = false;
	

	ItemStack[] blueInv;
	ItemStack[] redInv;
	
	@Override
	public void onEnable() {
		this.getCommand("joinTeam").setExecutor(new JoinTeam(this));
		this.getCommand("setflag").setExecutor(new SetFlag(this));
		this.getCommand("start").setExecutor(new StartCmd(this));
		this.getCommand("saveInventory").setExecutor(new SaveInventory(this));
		this.getCommand("setSpawn").setExecutor(new SetSpawn(this));
		
		
		red = new ArrayList<Player>();
		blue = new ArrayList<Player>();
		
		flagA = new Flag(this);
		flagB = new Flag(this);
		flagC = new Flag(this);
		
		gameListeners = new GameListeners(this);
		lobbyListeners = new LobbyListeners(this);
		
		this.getServer().getPluginManager().registerEvents(lobbyListeners, this);
		
		sManager = this.getServer().getScoreboardManager();
		scoreboard = sManager.getNewScoreboard();
		objective = scoreboard.registerNewObjective(ChatColor.GREEN+"Skyfall", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		Score score = objective.getScore("Test");
		score.setScore(2);
		
		scoreBlue = 0;
		scoreRed = 0;
		started = false;
	}
	
	public void addToBlue(Player player) {
		if (red.contains(player)) {red.remove(player);}
		if (blue.contains(player)) {blue.remove(player);}
		blue.add(player);
		player.sendMessage(ChatColor.BLUE+"Joined Team Blue.");
	}
	
	public void addToRed(Player player) {
		if (blue.contains(player)) {blue.remove(player);}
		if (red.contains(player)) {red.remove(player);}
		red.add(player);
		player.sendMessage(ChatColor.RED+"Joined Team Red.");
	}
	
	public ArrayList<Player> getTeamRed(){return red;}
	public ArrayList<Player> getTeamBlue(){return blue;}
	
	public boolean start() {
		if(started) {return false;}
		flagA.checkflags();
		flagB.checkflags();
		flagC.checkflags();
		
		started = true;
		
		manageScoreboard();
		manageScore();
		this.getServer().getPluginManager().registerEvents(gameListeners, this);
		
		for (int r = 0; r < red.size(); r++) {
			sendToSpawn(red.get(r), true);
		}
   		 
   	 	for (int b = 0; b < blue.size(); b++) {
   	 		sendToSpawn(blue.get(b), true);
   	 	}

		
		
		
		return true;
	}
	
	public Flag getFlagA() {return flagA;}
	public Flag getFlagB() {return flagB;}
	public Flag getFlagC() {return flagC;}
	
	public boolean getFinished() {return finished;}
	public boolean getStarted() {return started;}
	
	private void finish() {
		finished = true;
		HandlerList.unregisterAll(gameListeners);
	}
	
	public void addToScoreBoard() {
		red.forEach(player -> player.setScoreboard(scoreboard));
		blue.forEach(player -> player.setScoreboard(scoreboard));
	}
	
	public void manageScore() {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
		     public void run() {
		    	 if(flagA.getStatus()== Status.blue ) {scoreBlue++;}else if(flagA.getStatus()== Status.red ) {scoreRed++;}
		    	 if(flagB.getStatus()== Status.blue ) {scoreBlue++;}else if(flagB.getStatus()== Status.red ) {scoreRed++;}
		    	 if(flagC.getStatus()== Status.blue ) {scoreBlue++;}else if(flagC.getStatus()== Status.red ) {scoreRed++;}
		    	 
		    	 
		    	 if((scoreBlue>=100)&&(scoreRed>=100)){
		    		 Bukkit.broadcastMessage("Its a Tie."); 
		    		 finish();
		    		 return;
		    	 }
		    	 if(scoreBlue>= 100) {
		    		 Bukkit.broadcastMessage("Blue Wins!");
		    		 finish();
		    		 return;
	    		 }
		    	 if(scoreRed>= 100) {
		    		 Bukkit.broadcastMessage("Red Wins!");
		    		 finish();
		    		 return;
	    		 }
		    	 
		    	 manageScore();
		     }
		}, (100));
	}
	
	public void manageScoreboard() {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				generateScoreboard(flagA.getStatus(), flagB.getStatus(), flagC.getStatus());
		    	addToScoreBoard();
		    	manageScoreboard();
		     }
		}, (20));
	}
	
	public void setBlueInv(PlayerInventory inventory) {blueInv = inventory.getContents();}
	public void setRedInv(PlayerInventory inventory) {redInv = inventory.getContents();}
	
	public ItemStack[] getBlueInv() {return blueInv;}
	public ItemStack[] getRedInv() {return redInv;}
	
	public void setSpawn1(Location loc) {spawn1=loc;}
	public void setSpawn2(Location loc) {spawn2=loc;}
	
	public Location getSpawn1() {return spawn1;}
	public Location getSpawn2() {return spawn2;}
	
	public void generateScoreboard(Status a, Status b, Status c) {
		
		String ac="";
		String bc="";
		String cc="";
		
		switch (a){
		case neutral:
			ac = (ChatColor.GREEN+"A: Neutral Flag.");
			break;
		case red:
			ac = (ChatColor.RED+"A: Red Flag.");
			break;
		case blue:
			ac = (ChatColor.BLUE+"A: Blue Flag");
			break;
		}

		switch (b){
		case neutral:
			bc = (ChatColor.GREEN+"B: Neutral Flag.");
			break;
		case red:
			bc = (ChatColor.RED+"B: Red Flag.");
			break;
		case blue:
			bc = (ChatColor.BLUE+"B: Blue Flag");
			break;
		}

		switch (c){
		case neutral:
			cc = (ChatColor.GREEN+"C: Neutral Flag.");
			break;
		case red:
			cc = (ChatColor.RED+"C: Red Flag.");
			break;
		case blue:
			cc = (ChatColor.BLUE+"C: Blue Flag");
			break;
		}
		
		

		scoreboard = sManager.getNewScoreboard();
		objective = scoreboard.registerNewObjective(ChatColor.GREEN+"Skyfall", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		Score score1 = objective.getScore(ChatColor.BLUE+"Team Blue: "+scoreBlue);
		Score score2 = objective.getScore(ChatColor.RED+"Team Red: "+scoreRed);
		Score score3 = objective.getScore(ChatColor.GOLD+"----- ----");
		Score score4 = objective.getScore(cc);
		Score score5 = objective.getScore(bc);
		Score score6 = objective.getScore(ac);
		Score score7 = objective.getScore(ChatColor.GOLD+"----------");
		score1.setScore(1);
		score2.setScore(2);
		score3.setScore(3);
		score4.setScore(4);
		score5.setScore(5);
		score6.setScore(6);
		score7.setScore(7);
	}
	
	
	/*
	 * true = red, false = blue
	 */
	public void sendToSpawn(Player player, boolean red) {
		player.setGameMode(GameMode.SURVIVAL);
		if(red) {
			player.getInventory().setContents(redInv);
			player.updateInventory();
			player.teleport(spawn1);
		}
		else {
			player.getInventory().setContents(blueInv);
			player.updateInventory();
			player.teleport(spawn2);
		}
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setFireTicks(0);
	}
	
	
}
