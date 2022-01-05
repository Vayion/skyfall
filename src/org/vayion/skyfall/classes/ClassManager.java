package org.vayion.skyfall.classes;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.vayion.skyfall.Main;
import org.vayion.skyfall.commands.SelectClass;

public class ClassManager {
	
	private ArrayList<Player> archers;
	private ArrayList<Player> assassins;
	private ArrayList<Player> tanks;
	private ArrayList<Player> defaults;
	
	private Main main;

	public ClassManager(Main main) {
		this.main = main;
		archers = new ArrayList<Player>();
		assassins = new ArrayList<Player>();
		tanks = new ArrayList<Player>();
		defaults = new ArrayList<Player>();
		main.getCommand("selectClass").setExecutor(new SelectClass(this));
	}

	public void selectDefault(Player player) {
		defaults.add(player);
		archers.remove(player);
		tanks.remove(player);
		assassins.remove(player);
	}
	
	public void selectArcher(Player player) {
		archers.add(player);
		tanks.remove(player);
		assassins.remove(player);
		defaults.remove(player);
	}
	
	public void selectTank(Player player) {
		tanks.add(player);
		archers.remove(player);
		assassins.remove(player);
		defaults.remove(player);
	}
	
	public void selectAssassin(Player player) {
		assassins.add(player);
		archers.remove(player);
		tanks.remove(player);
		defaults.remove(player);
	}
	
	public ArrayList<Player> getArchers() {return archers;}
	public ArrayList<Player> getAssassins() {return assassins;}
	public ArrayList<Player> getDefaults() {return defaults;}
	public ArrayList<Player> getTanks() {return tanks;}
	
}
