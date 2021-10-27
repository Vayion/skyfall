package me.YodaSoda_HD.YSpigotSkyfall;

import org.bukkit.plugin.java.JavaPlugin;

import me.YodaSoda_HD.YSpigotSkyfall.commands.ClassCommands;
import me.YodaSoda_HD.YSpigotSkyfall.commands.listTeams;
import me.YodaSoda_HD.YSpigotSkyfall.commands.listplayers;
import me.YodaSoda_HD.YSpigotSkyfall.commands.teammembers;
import me.YodaSoda_HD.YSpigotSkyfall.events.ConnectionEvent;
import me.YodaSoda_HD.YSpigotSkyfall.events.DoubleJumpEvent;
import me.YodaSoda_HD.YSpigotSkyfall.game.GameMain;

public class Main extends JavaPlugin{
	ConnectionEvent CE;
	DoubleJumpEvent DJE;
	ClassCommands CC;
	GameMain game;
	listplayers lP;
	listTeams lT;
	teammembers tm;
	public Main() {
		CE = new ConnectionEvent(game);
	}
	@Override
	public void onEnable() {
		game = new GameMain(this);
		CC = new ClassCommands(game);
		DJE = new DoubleJumpEvent(game);
		lP = new listplayers(game);
		lT = new listTeams(game);
		tm = new teammembers(game);
		this.getCommand("class").setExecutor(CC);
		this.getCommand("listPlayers").setExecutor(lP);
		this.getCommand("listTeams").setExecutor(lT);
		this.getCommand("teamMembers").setExecutor(tm);
		
		getServer().getPluginManager().registerEvents(CE, this);
		getServer().getPluginManager().registerEvents(DJE, this);
	}
	
}
