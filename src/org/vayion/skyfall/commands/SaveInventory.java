package org.vayion.skyfall.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.vayion.skyfall.Main;

public class SaveInventory implements CommandExecutor{

	Main main;
	
	public SaveInventory(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args.length==0) {return false;}
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(!player.hasPermission("skyfall.admin")) {player.sendMessage(ChatColor.RED+"You don't have the required permissions to do this.");}
			if(main.getStarted()) {player.sendMessage(ChatColor.RED + "The game already started, you can't do this.");}
			String argument = args[0].toLowerCase();
			
			if(argument.equals("default")) {
				ItemStack[] items = player.getInventory().getContents();
				if(items[8]==null) {
					items[8] = new ItemStack(Material.CLAY_BRICK);
				}
				ItemMeta meta = items[8].getItemMeta();
				meta.setDisplayName(ChatColor.GREEN+"Berserk");
				items[8].setItemMeta(meta);
				main.saveDefaultInv(items);
				player.sendMessage(ChatColor.GREEN+"[Skyfall] Successfully set Default Class inventory");
			}
			
			else if(argument.equals("archer")) {
				ItemStack[] items = player.getInventory().getContents();
				if(items[8]==null) {
					items[8] = new ItemStack(Material.BOW);
				}
				ItemMeta meta = items[8].getItemMeta();
				meta.setDisplayName(ChatColor.GREEN+"Archer");
				meta.setUnbreakable(true);
				items[8].setItemMeta(meta);
				main.saveArchInv(items);
				player.sendMessage(ChatColor.GREEN+"[Skyfall] Successfully set Arch Class inventory");
			}
			
			else if(argument.equals("tank")) {
				ItemStack[] items = player.getInventory().getContents();
				if(items[8]==null) {
					items[8] = new ItemStack(Material.IRON_INGOT);
				}
				ItemMeta meta = items[8].getItemMeta();
				meta.setDisplayName(ChatColor.AQUA+"Tank");
				items[8].setItemMeta(meta);
				main.saveTankInv(items);
				player.sendMessage(ChatColor.GREEN+"[Skyfall] Successfully set Tank Class inventory");
			}
			
			else if(argument.equals("assassin")) {
				ItemStack[] items = player.getInventory().getContents();
				if(items[8]==null) {
					items[8] = new ItemStack(Material.DIAMOND);
				}
				ItemMeta meta = items[8].getItemMeta();
				meta.setDisplayName(ChatColor.RED+"Assassin");
				items[8].setItemMeta(meta);
				main.saveAssInv(items);
				player.sendMessage(ChatColor.GREEN+"[Skyfall] Successfully set Assassin Class inventory");
			}
		}
		return false;
	}
}
	
