package me.brotherhong.fishinglife.MenuSystem;

import org.bukkit.entity.Player;

public class PlayerMenuUtility {
	
	private Player owner;
	private int targetSlots = 0;
	private String targetAreaName = null;
	
	public PlayerMenuUtility(Player owner) {
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getTargetSlots() {
		return targetSlots;
	}

	public void setTargetSlots(int targetSlots) {
		this.targetSlots = targetSlots;
	}

	public String getTargetAreaName() {
		return targetAreaName;
	}

	public void setTargetAreaName(String targetAreaName) {
		this.targetAreaName = targetAreaName;
	}
	
	
}
