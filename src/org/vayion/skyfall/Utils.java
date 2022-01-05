package org.vayion.skyfall;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Utils {

	public static ItemStack[] dyeShit(ItemStack[] items, boolean red) {
		for(int i = 0; i<41; i++) {
			if(items[i]==null) {
			}
			else {
				switch (items[i].getType()) {
				case LEATHER_BOOTS:
					items[i] = dyeItem(items[i], red);
					break;
					
				case LEATHER_HELMET:
					items[i] = dyeItem(items[i], red);
					break;
					
				case LEATHER_CHESTPLATE:
					items[i] = dyeItem(items[i], red);
					break;
					
				case LEATHER_LEGGINGS:
					items[i] = dyeItem(items[i], red);
					break;
				
				default:
					break;
				}
			}
		}
		return items;
	}
	
	public static ItemStack dyeItem(ItemStack c, boolean red) {
		ItemStack item = new ItemStack(c.getType());
		item.setDurability(c.getDurability());
		item.addEnchantments(c.getEnchantments());
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		Color color = null;
		if(red) {
			color = Color.fromRGB(153, 0, 0);
		}else {
			color = Color.fromRGB(0, 73, 160);
		}
		meta.setColor(color);
		if((c.getItemMeta()==null)||(c.getItemMeta().getDisplayName()==null)) {}else {
			meta.setDisplayName(c.getItemMeta().getDisplayName());
		}
		if((c.getItemMeta()==null)||(c.getItemMeta().getLore()==null)) {}else {
			meta.setLore(c.getItemMeta().getLore());
		}
		item.setItemMeta(meta);
		return item;
	}
	
}
