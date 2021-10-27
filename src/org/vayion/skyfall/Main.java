package org.vayion.skyfall;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.vayion.skyfall.commands.JoinTeam;
import org.vayion.skyfall.commands.SetFlag;
import org.vayion.skyfall.commands.StartCmd;

public class Main extends JavaPlugin implements Listener {
	private Location flag;
	private int flagCounter;
	private ArrayList<Player> red;
	private ArrayList<Player> blue;
	private boolean started;
	private BossBar bar; 
	
	public enum Status {
		neutral, blue, red
	}
	
	Status flagStatus;
	
	@Override
	public void onEnable() {
		this.getCommand("joinTeam").setExecutor(new JoinTeam(this));
		this.getCommand("setflag").setExecutor(new SetFlag(this));
		this.getCommand("start").setExecutor(new StartCmd(this));
		
		red = new ArrayList<Player>();
		blue = new ArrayList<Player>();
		
		flagStatus = Status.neutral;
		
		bar = Bukkit.getServer().createBossBar("Flag", BarColor.WHITE, BarStyle.SEGMENTED_10, BarFlag.CREATE_FOG);
		
		bar.setVisible(true);
		
		
		flagCounter = 0;
		//flag = -100 -> blue / flag = 100 -> red
		
		started = false;
	}
	
	public void addToBlue(Player player) {
		bar.removePlayer(player);
		if (red.contains(player)) {
			red.remove(player);
		}
		blue.add(player);
		player.sendMessage(ChatColor.BLUE+"Joined Team Blue.");
	}
	
	public void addToRed(Player player) {
		bar.removePlayer(player);
		if (blue.contains(player)) {
			blue.remove(player);
		}
		red.add(player);
		player.sendMessage(ChatColor.RED+"Joined Team Red.");
	}

	public void setFlag(Location loc) {
		flag = loc;
		Bukkit.broadcastMessage("Set the Flag at "+loc.toString());
	}
	
	public void checkflags() {
		//Bukkit.broadcastMessage("Checking for Coordinates");
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
		     public void run() {
		    	 int redC = 0;
		    	 int blueC = 0;
		    	 
		    	 bar.removeAll();
		    	 
		    	 for (int r = 0; r < red.size(); r++) {
		    		 if (red.get(r).getLocation().distance(flag)<5) {
		    			 redC ++;
		    			 bar.addPlayer(red.get(r));
		    		 }
		    	 }
		    		 
		    	 for (int b = 0; b < blue.size(); b++) {
		    		 if (blue.get(b).getLocation().distance(flag)<5) {
		    			 blueC --;
		    			 bar.addPlayer(blue.get(b));
		    		 }
		    	 }
		    	 
		    	 if((redC!=0)&&(blueC!=0)) {
		    		 bar.setTitle(ChatColor.getLastColors(bar.getTitle())+"Flag is Contested.");
		    		 
		    	 }
		    	 else {
		    		 bar.setTitle(ChatColor.getLastColors(bar.getTitle())+"Capturing...");
		    		 flagCounter = flagCounter+redC;
		    		 flagCounter = flagCounter+blueC;
		    		 if(flagCounter > 100) {flagCounter = 100; flagStatus = Status.red;}
		    		 if(flagCounter < -100) {flagCounter = -100; flagStatus = Status.blue;}
		    	 }
		    	 Bukkit.broadcastMessage("flagCounter = "+flagCounter);
		    	 bar.getPlayers().forEach(player -> Bukkit.broadcastMessage(player.getName()));
		    	 checkflags();
		    	 
		     }
		}, (5));
	}
		     
	public void giveStatus() {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
		     public void run() {
		    	switch (flagStatus) {
		    	case neutral:
		    		bar.setTitle(ChatColor.WHITE+ChatColor.stripColor(bar.getTitle()));
		    		bar.setColor(BarColor.WHITE);
		    		bar.setProgress(((double)Math.abs(flagCounter))/100D);
		    		break;
		    	case red:
		    		bar.setTitle(ChatColor.RED+ChatColor.stripColor(bar.getTitle()));
		    		bar.setColor(BarColor.RED);
		    		bar.setProgress(((double)Math.abs(flagCounter))/100D);
		    		break;
		    	case blue:
		    		bar.setTitle(ChatColor.BLUE+ChatColor.stripColor(bar.getTitle()));
		    		bar.setColor(BarColor.BLUE);
		    		bar.setProgress(((double)Math.abs(flagCounter))/100D);
		    		break;
		    	}
		    	giveStatus();
		     }
		}, (20));
	}
	public boolean start() {
		if (started) {return false;}
		checkflags();
		giveStatus();
		return true;
	}
}
