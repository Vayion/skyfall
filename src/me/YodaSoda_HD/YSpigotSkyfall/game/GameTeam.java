package me.YodaSoda_HD.YSpigotSkyfall.game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameTeam {
	int maxMembers;
	String name;
	Player[] members;
	
	public GameTeam(int _maxMembers, String _name) {
		name = _name;
		maxMembers = _maxMembers;
		members = new Player[maxMembers];
		System.out.println(ChatColor.RED + "[YSpigotSkyfall]"+ChatColor.WHITE+" Loaded Team: "+ (String)name);
	}
	
	public void addMember(Player _player) {
		for(int i = 0; i < maxMembers; i ++) {
			if(members[i].equals(null)) {
				members[i]=_player;
				_player.sendMessage("Welcome to Team "+ name);
				return;
				}
		}
	}
	
	public boolean checkforMember(Player wanted) {
		for(int i = 0; i < maxMembers; i ++) {
			if(members[i].equals(wanted)) {return true;}
		}
		return false;
	}
	
	public boolean removeMember(Player wanted) {
		for(int i = 0; i < maxMembers; i ++) {
			if(members[i].equals(wanted)) {
				members[i]=null;
				return true;
				}
		}
		return false;
	}
	
	public void listMembers(Player asks) {
		asks.sendMessage("Players in Team "+name);
		for(int i= 0; i< maxMembers; i++) {
			if (members[i]!= null) {
				asks.sendMessage(members[i].getName());
			}
		}
	}
}