package org.vayion.skyfall.arenas;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.vayion.skyfall.Main;

public class Flag {
	
	private Main main;
	
	private BossBar barBlue; private BossBar barRed; 
	private Location loc;
	private int flagCounter;

	public enum Status {
		neutral, blue, red
	}
	
	Status flagStatus;
	
	public Flag(Main main) {
		this.main = main;
		barRed = Bukkit.getServer().createBossBar("Flag", BarColor.WHITE, BarStyle.SEGMENTED_10, BarFlag.CREATE_FOG);
		barBlue = Bukkit.getServer().createBossBar("Flag", BarColor.WHITE, BarStyle.SEGMENTED_10, BarFlag.CREATE_FOG);
		flagCounter = 0;
		
		flagStatus = Status.neutral;
		
	}
	
	public void setFlag(Location loc) {
		this.loc = new Location(loc.getWorld(), (double)loc.getBlockX()+0.5D , (double)loc.getBlockY() , (double)loc.getBlockZ()+0.5D );
		new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ()).getBlock().setType(Material.STAINED_GLASS);
		new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ()).getBlock().setData((byte)5);
	}
	
	public void checkflags() {
		//Bukkit.broadcastMessage("Checking for Coordinates");
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
		     public void run() {
		    	 

	    		ArrayList<Player> red = main.getTeamRed();
	    		ArrayList<Player> blue = main.getTeamBlue();
	    	 
		    	 int redC = 0;
		    	 int blueC = 0;
		    	 
		    	 barBlue.removeAll();
		    	 barRed.removeAll();
		    	 
		    	 for (int r = 0; r < red.size(); r++) {
		    		 if (red.get(r).getLocation().distance(loc)<5) {
		    			 redC ++;
		    			 barRed.addPlayer(red.get(r));
		    		 }
		    	 }
		    		 
		    	 for (int b = 0; b < blue.size(); b++) {
		    		 if (blue.get(b).getLocation().distance(loc)<5) {
		    			 blueC --;
		    			 barBlue.addPlayer(blue.get(b));
		    		 }
		    	 }
		    	 
		    	 
		    	 
		    	 switch (flagStatus) {
		    	 
			    	case neutral:
			    		if((redC!=0)&&(blueC!=0)) {
				    		 barRed.setTitle(ChatColor.YELLOW+"Flag is Contested.");
				    		 barBlue.setTitle(ChatColor.YELLOW+"Flag is Contested.");
			    		}
			    		else if((redC==0)&&(blueC==0)) {
				    		 barRed.setTitle(ChatColor.GREEN + "Neutral Flag.");
				    		 barBlue.setTitle(ChatColor.GREEN + "Neutral Flag.");
				    		 if (flagCounter>0) {
				    			 flagCounter--;
				    		 }else if (flagCounter<0) {flagCounter++;}
			    		}
			    		else if (redC > 0) {
				    		 barRed.setTitle(ChatColor.GREEN + "Capturing.");
				    		 barBlue.setTitle(ChatColor.GREEN + "Flag is getting Captured by other Team.");
				    		 flagCounter = flagCounter +redC;
			    		}
			    		else {
				    		 barRed.setTitle(ChatColor.GREEN + "Flag is getting Captured by other Team.");
				    		 barBlue.setTitle(ChatColor.GREEN + "Capturing.");
				    		 flagCounter = flagCounter + blueC;
			    		}

			    		 if(flagCounter > 20) {
			    			 flagCounter = 20; flagStatus = Status.red;
			    				new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ()).getBlock().setType(Material.STAINED_GLASS);
			    				new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ()).getBlock().setData((byte)14);
		    			 }
			    		 else if(flagCounter < -20) {
			    			 flagCounter = -20; flagStatus = Status.blue;
			    				new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ()).getBlock().setType(Material.STAINED_GLASS);
			    				new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ()).getBlock().setData((byte)11);
		    			 }
			    		
			    		break;
			    		
			    	case red:
			    		if((redC!=0)&&(blueC!=0)) {
				    		 barRed.setTitle(ChatColor.YELLOW+"Flag is Contested.");
				    		 barBlue.setTitle(ChatColor.YELLOW+"Flag is Contested.");
			    		}
			    		else if((redC==0)&&(blueC==0)) {
				    		 barRed.setTitle(ChatColor.RED + "You already captured this flag.");
				    		 barBlue.setTitle(ChatColor.RED + "Enemy Flag.");
			    		}
			    		else if (redC > 0) {
				    		 barRed.setTitle(ChatColor.RED + "You already captured this flag.");
				    		 barBlue.setTitle(ChatColor.RED + "Taking down enemy flag.");
				    		 flagCounter = flagCounter +redC;
				    		 if(flagCounter > 20) {flagCounter = 20;}
			    		}
			    		else {
				    		 barRed.setTitle(ChatColor.RED + "Losing Flag.");
				    		 barBlue.setTitle(ChatColor.RED + "Taking down enemy Flag.");
				    		 flagCounter = flagCounter + blueC;
				    		 if (flagCounter<=0) {
				    			 flagStatus = Status.neutral;
				    			 new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ()).getBlock().setType(Material.STAINED_GLASS);
				    			 new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ()).getBlock().setData((byte)5);
				    		 }
			    		}
			    		break;
			    		
			    	case blue:
			    		if((redC!=0)&&(blueC!=0)) {
				    		 barRed.setTitle(ChatColor.YELLOW+"Flag is Contested.");
				    		 barBlue.setTitle(ChatColor.YELLOW+"Flag is Contested.");
			    		}
			    		else if((redC==0)&&(blueC==0)) {
				    		 barRed.setTitle(ChatColor.BLUE + "Enemy Flag");
				    		 barBlue.setTitle(ChatColor.BLUE + "You already captured this Flag.");
			    		}
			    		else if (blueC < 0) {
				    		 barBlue.setTitle(ChatColor.BLUE + "You already captured this Flag.");
				    		 barRed.setTitle(ChatColor.BLUE + "Taking down enemy Flag.");
				    		 flagCounter = flagCounter +blueC;
				    		 if(flagCounter < -20) {flagCounter = -20;}
			    		}
			    		else {
				    		 barBlue.setTitle(ChatColor.BLUE + "Losing Flag.");
				    		 barRed.setTitle(ChatColor.BLUE + "Taking down Enemy Flag.");
				    		 flagCounter = flagCounter + redC;
				    		 if (flagCounter>=0) {
				    			 flagStatus = Status.neutral;
				    			 new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ()).getBlock().setType(Material.STAINED_GLASS);
				    			 new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ()).getBlock().setData((byte)5);
				    		 }
			    		}
			    		
			    		break;
			    	}
		    		barRed.setProgress(((double)Math.abs(flagCounter))/20D);
		    		barBlue.setProgress(((double)Math.abs(flagCounter))/20D);
		    		
		    		if(!main.getFinished()) {checkflags();}
		    	 
		    	 
		     }
		}, (10));
	}
	
	public Status getStatus() {return flagStatus;}
	

}
