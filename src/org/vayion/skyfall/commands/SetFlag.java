package org.vayion.skyfall.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vayion.skyfall.Main;

public class SetFlag implements CommandExecutor {
	Main main;

	public SetFlag(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Location loc = ((Player)sender).getLocation();
			main.setFlag(loc);
			new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()).getBlock().setType(Material.FENCE);
			new Location(loc.getWorld(), loc.getX(), loc.getY()+1, loc.getZ()).getBlock().setType(Material.FENCE);
			new Location(loc.getWorld(), loc.getX(), loc.getY()+2, loc.getZ()).getBlock().setType(Material.FENCE);
			new Location(loc.getWorld(), loc.getX(), loc.getY()+3, loc.getZ()).getBlock().setType(Material.FENCE);
		}
		return false;
	}
}
