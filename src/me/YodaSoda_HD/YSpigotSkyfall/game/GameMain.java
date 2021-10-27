package me.YodaSoda_HD.YSpigotSkyfall.game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.YodaSoda_HD.YSpigotSkyfall.Main;
import me.YodaSoda_HD.YSpigotSkyfall.classes.ClassManager;

public class GameMain {
	GameTeam[] Teams;
	ClassManager CM;
	int status;
	Main main;
	boolean Team;
	int teams;
	
	public GameMain(Main _main) {
		teams = 2;
		Teams = new GameTeam[teams];
		Teams[0]= new GameTeam(8, "Red");
		Teams[1]= new GameTeam(8, "Blue");
		CM = new ClassManager(this, 16, main);
		status = 0;
		main = _main;
		System.out.println(ChatColor.RED + "[YSpigotSkyfall]"+ChatColor.WHITE+" Loaded GameMain");
	}
	
	public void removePlayer(Player _player) {
		Player player = _player;
		CM.removePlayer(player);
		boolean removed = Teams[0].removeMember(player);
		if(removed) {
			Teams[1].removeMember(player);
		}
	}
	
	public void addPlayer(Player _player) {
		Player player = _player;
		System.out.println("Trying to add "+ player.getName() +" to game.");
		/*if(Team) {
			Team = false;
			Teams[0].addMember(player);
		}
		else {
			Team = true;
			Teams[1].addMember(player);
		}
		CM.addPlayer(player);*/
	}
	
	public void listTeams(Player player) {
		for(int i= 0; i < teams; i++) {
			player.sendMessage("Team: "+ Teams[i].name + " with a maximum of " +Teams[i].maxMembers+ " members.");
		}
	}
	
	public void listTeamMembers(Player _player, String arg) {
		Player player = _player;
		for(int i= 0; i < teams; i++) {
			if(Teams[i].name.equals(arg)) {
				Teams[i].listMembers(player);
			}
		}
	}

	public void debug(Player player) {
		player.sendMessage("Debug from class GameMain");
	}
	
	public ClassManager getCM() {return CM;}
}
