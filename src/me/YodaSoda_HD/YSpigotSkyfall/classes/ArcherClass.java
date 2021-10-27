package me.YodaSoda_HD.YSpigotSkyfall.classes;

import org.bukkit.entity.Player;

public class ArcherClass extends standartClass {
	Player player;
	public ArcherClass(Player _player) {
		super(_player);
	}
	

	public void doubleJump() {
		player.setVelocity(player.getLocation().getDirection().multiply(1.0D).setY(0.5D));
	}
}
