package org.YodaSoda_HD.YSpigotSkyfall.ArenaManager;

import org.YodaSoda_HD.YSpigotSkyfall.Main;
import org.YodaSoda_HD.YSpigotSkyfall.ArenaManager.teams.Team;
import org.YodaSoda_HD.YSpigotSkyfall.PlayerManager.PlayerManagerMain;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Arena {
	String name;
	
	CapturePoint[] CapturePoints;
	int maxCapturePoints;
	
	Team[] Teams;
	
	Main main;
	
	PlayerManagerMain PMMain;
	
	World ArenaWorld;
	
	int maxPlayers;
	
	enum team{
		team1,
		team2
	}
	
	public Arena(Main _main, String _name) {
		name= _name;
		main = _main;
		maxCapturePoints = 5;
		CapturePoints = new CapturePoint[maxCapturePoints];
		Teams = new Team[2];
		
		maxPlayers = 20;
		
		PMMain = new PlayerManagerMain(this);
		
		System.out.println("[YSpigotSkyfall] Loading Arena "+ name+ ".");
	}
	
	
	public String getName() {
		return name;
	}
	
	public void checkForEnter(Player player) {
		if(!PMMain.checkForPlayer(player)) {
			return;
		}
		int ID = searchOnCapturePoints(player);
		if(ID!=maxCapturePoints) {
			if(!CapturePoints[ID].findPlayer(player)) {
				CapturePoints[ID].enterArea(player);
				return;
			}
		}
		else {
			if(CapturePoints[ID].findPlayer(player)) {
				CapturePoints[ID].leaveArea(player);
				return;
			}
		}
	}
	
	public int searchOnCapturePoints(Player player) {
		for (int i= 0; i< maxCapturePoints; i++) {
			if (CapturePoints[i].isInCapturePoint(player.getLocation(), player.getWorld())){
				return i;
			}
		}
		
		return maxCapturePoints;
	}
	
	public boolean addTeam(String color) {
		if(Teams[0]==null) {
			Teams[0]=new Team(team.team1.toString(), color, maxPlayers/2);
			}
		else if(Teams[1]==null) {
			Teams[1]=new Team(team.team2.toString(), color, maxPlayers/2);
			}
		return false;
	}
	
	public int getPlayerWeight(Player player) {
		if(Teams[1].getPlayerTeam(player).equals(team.team1.toString())) {
			return 1;
		}
		return -1;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
}
