package org.vayion.skyfall.doubleJumpManagement;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class DJmpStatic {
	
	
	public static int executeDoubleJump(Player player) {
		ItemStack[] items = player.getInventory().getContents();
		if(items[8]==null) {
			player.setVelocity(player.getLocation().getDirection().multiply(2.0D).setY(0.5D));
			return 3;
		}else {
			if (items[8].getItemMeta().getDisplayName()==null) {
				player.setVelocity(player.getLocation().getDirection().multiply(2.0D).setY(0.5D));
				return 3;
			}
		}
		String playerClass = items[8].getItemMeta().getDisplayName();
		playerClass = playerClass.toLowerCase();
		playerClass = ChatColor.stripColor(playerClass);
		switch(playerClass) {
		
		case "archer":
			player.setVelocity(player.getLocation().getDirection().multiply(0.6D).setY(0.4D));
			return 0;
			
		case "assassin":
			player.setVelocity(player.getLocation().getDirection().multiply(3.0D));
			player.setVelocity(player.getVelocity().setY(player.getVelocity().getY()/2));
			return 5;
			
		case "tank":
			player.setVelocity(player.getLocation().getDirection().multiply(1.0D).setY(0.1D));
			return 10;
			
		default:
			player.setVelocity(player.getLocation().getDirection().multiply(1.5D).setY(0.5D));
			return 3;
			
		}
	}
	
	
}
