package org.vayion.skyfall;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.vayion.skyfall.commands.SetLobbySpawn;
import org.vayion.skyfall.commands.SetSpawn;
import org.vayion.skyfall.commands.StartCmd;
import org.vayion.skyfall.listeners.GameListeners;
import org.vayion.skyfall.listeners.LobbyListeners;

public class Main extends JavaPlugin {
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
	private Location lobbySpawn;
	
	private int scoreRed;
	private int scoreBlue;
	
	private boolean finished = false;
	

	private FileManager fileManager;
	private TeamManager teamManager;
	
	
	@Override
	public void onEnable() {
		
		
		if(!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
		
		this.getCommand("joinTeam").setExecutor(new JoinTeam(this));
		this.getCommand("setflag").setExecutor(new SetFlag(this));
		this.getCommand("start").setExecutor(new StartCmd(this));
		this.getCommand("saveInventory").setExecutor(new SaveInventory(this));
		this.getCommand("setSpawn").setExecutor(new SetSpawn(this));
		this.getCommand("setLobbySpawn").setExecutor(new SetLobbySpawn(this));
		
		
		
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
		teamManager = new TeamManager(this);
		
		
		scoreBlue = 0;
		scoreRed = 0;
		started = false;
		
		fileManager = new FileManager(this);
	}
	
	@Override
	public void onDisable() {
		System.out.println("[Skyfall] Saving Locations");
		
		fileManager.setSpawn1(spawn1);
		fileManager.setSpawn2(spawn2);

		fileManager.save();
		
	}
	
	public void addToBlue(Player player) {
		teamManager.addToBlue(player);
	}
	
	public void addToRed(Player player) {
		teamManager.addToRed(player);
	}
	public Scoreboard getScoreboard() {
		return scoreboard;
	}
	
	
	public boolean start() {
		if(started) {return false;}
		flagA.checkflags();
		flagB.checkflags();
		flagC.checkflags();
		
		doSpawnFlips();
		
		started = true;
		
		manageScoreboard();
		manageScore();
		HandlerList.unregisterAll(lobbyListeners);
		this.getServer().getPluginManager().registerEvents(gameListeners, this);
		

		lobbyListeners.disableEdit();
		
		teamManager.start();
		
		
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
		teamManager.getRed().forEach(player -> player.setScoreboard(scoreboard));
		teamManager.getBlue().forEach(player -> player.setScoreboard(scoreboard));
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
	
	public void setBlueInv(PlayerInventory inventory) {teamManager.setBlueInv(inventory.getContents());}
	public void setRedInv(PlayerInventory inventory) {teamManager.setRedInv(inventory.getContents());}

	public void setBlueInv(ItemStack[] items) {teamManager.setBlueInv(items);}
	public void setRedInv(ItemStack[] items) {teamManager.setRedInv(items);}
	
	public ItemStack[] getBlueInv() {return teamManager.getBlueInv();}
	public ItemStack[] getRedInv() {return teamManager.getRedInv();}
	
	public void setSpawn1(Location loc) {spawn1=loc;}
	public void setSpawn2(Location loc) {spawn2=loc;}
	
	public Location getSpawn1() {return spawn1;}
	public Location getSpawn2() {return spawn2;}
	
	public ArrayList<Player> getTeamRed(){return teamManager.getRed();}
	public ArrayList<Player> getTeamBlue(){return teamManager.getBlue();}
	
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
		
		
		
		objective.unregister();
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
	
	
	public void doSpawnFlips() {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				Location loc = spawn1;
				spawn1 = spawn2;
				spawn2 = loc;
				doSpawnFlips();
		     }
		}, (20*30));
	}
	
	public FileManager getFileManager() {return fileManager;}
	
	public void setLobby(Location loc) {
		lobbySpawn = loc;
	}
	
	public Location getLobbySpawn() {return lobbySpawn;}
	
	public TeamManager getTeamManager() {
		return teamManager;
	}
	
}
