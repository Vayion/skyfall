package org.vayion.skyfall.doubleJumpManagement;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.vayion.skyfall.Main;

public class DJmpReference extends DJmpElement {
	
	private DJmpElement next;
	private Player player;
	private int timer = 0;
	private Main main;
	
	public DJmpReference(Player player, Main main) {
		super();
		this.player = player;
		this.main = main;
		next = new DJmpEnd(main);
		player.setAllowFlight(true);
	}

	public DJmpElement addPlayer(Player player) {
		next = next.addPlayer(player);
		return this;
	}

	public DJmpElement removePlayer(Player player) {
		if(player.equals(this.player)) {
			return next;
		}
		else {
			next = next.removePlayer(player);
			return this;
		}
	}

	public boolean handleDoubleJump(Player player) {
		if(!player.equals(this.player)) {return next.handleDoubleJump(player);}
		else{
			if(timer == 0) {
				timer = DJmpStatic.executeDoubleJump(player);
				startTimer();
			}
			return true;
		}
	}
	
	public void startTimer() {
		if(timer == 0) {return;}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
		     public void run() {
		    	 //player.sendMessage("Cooldown: "+timer);
		    	 timer --;
		    	 startTimer();
		     }
		}, (20));
	}
	
	public ArrayList<Player> getPlayers() {
		ArrayList<Player> list = next.getPlayers();
		list.add(player);
		return list;
	}

	public ArrayList<String> getPlayerNames() {
		ArrayList<String> list = next.getPlayerNames();
		list.add(player.getName());
		return list;
	}

}
