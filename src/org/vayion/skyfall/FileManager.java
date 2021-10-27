package org.vayion.skyfall;


import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class FileManager {
	Main main;
	
	private File file;
	private FileConfiguration config;
	
	public FileManager(Main _main) {
		main = _main;
		
		file = new File(main.getDataFolder(), "arena.yml");
		config = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
		if(file.exists()) {
			loadArena();
		}
		else {
			try {
				file.createNewFile();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.config = YamlConfiguration.loadConfiguration(file);
	}
	
	
	public void setSpawn1(Location loc) {config.set("spawn1", loc);}
	public void setSpawn2(Location loc) {config.set("spawn2", loc);}
	public void setFlagA(Location loc) {config.set("FlagA", loc);}
	public void setFlagB(Location loc) {config.set("FlagB", loc);}
	public void setFlagC(Location loc) {config.set("FlagC", loc);}
	public void setSpecSpawn(Location loc) {config.set("SpecSpawn", loc);}
	
	public void setRedInventory(PlayerInventory inv) {
		ItemStack[] items = inv.getContents();
		for (int i = 0; i < 41; i++) {
			config.set("redItem"+i, items[i]);
		}
	}
	public void setBlueInventory(PlayerInventory inv) {
		ItemStack[] items = inv.getContents();
		for (int i = 0; i < 41; i++) {
			config.set("blueItem"+i, items[i]);
		}
	}
	
	public void save() {
		try {
			config.save(file);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveSpawn(Location _loc) {
		config.set("spawnLobby", _loc);
	}
	
	public void loadArena() {
		if(config.get("spawn1")!=null){main.setSpawn1((Location)config.get("spawn1"));Bukkit.getLogger().info("Loaded Spawn 1");}
		if(config.get("spawn2")!=null){main.setSpawn2((Location)config.get("spawn2"));Bukkit.getLogger().info("Loaded Spawn 2");}
		if(config.get("spawnLobby")!=null){main.setLobby((Location)config.get("spawnLobby"));Bukkit.getLogger().info("Loaded Lobby Spawn");}

		try {
			ItemStack[] items = new ItemStack[41];
			for (int i = 0; i < 41; i++) {
				items[i]=(ItemStack)config.get("redItem"+i);
			}
			main.setRedInv(items);
			Bukkit.getLogger().info("Loaded Red Inventory");
		}catch(Exception e) {Bukkit.getLogger().info("Red Inventory wasn't saved correctly. Please delete the save files associated with it.");}
		try {
			ItemStack[] items = new ItemStack[41];
			for (int i = 0; i < 41; i++) {
				items[i]=(ItemStack)config.get("blueItem"+i);
			}
			main.setBlueInv(items);
			Bukkit.getLogger().info("Loaded Blue Inventory");
		}catch(Exception e) {Bukkit.getLogger().info("Blue Inventory wasn't saved correctly. Please delete the save files associated with it.");}

		if(config.get("FlagA")!=null){main.getFlagA().setFlag((Location)config.get("FlagA"));Bukkit.getLogger().info("Loaded Flag A");}
		if(config.get("FlagB")!=null){main.getFlagB().setFlag((Location)config.get("FlagB"));Bukkit.getLogger().info("Loaded Flag B");}
		if(config.get("FlagC")!=null){main.getFlagC().setFlag((Location)config.get("FlagC"));Bukkit.getLogger().info("Loaded Flag C");}
		if(config.get("SpecSpawn")!=null){main.setSpecSpawn((Location)config.get("SpecSpawn"));Bukkit.getLogger().info("Loaded Spectator Spawn");}
	}
	
	
}
