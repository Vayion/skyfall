package org.YodaSoda_HD.YSpigotSkyfall.ArenaManager.teams;

import org.bukkit.entity.Player;

public class Team {
	
	Player[] members;
	int maxMembers;
	String team;
	String color;
	
	public Team(String _team, String _color, int _maxMembers) {
		color = _color;
		team = _team;
		maxMembers = _maxMembers;
	}
	
	public String getPlayerTeam(Player player){
		for(int i = 0; i< maxMembers; i++) {
			if(members[i].equals(player)) {
				return team;
			}
		}
		return null;
	}
	
}
