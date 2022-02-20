package me.brotherhong.fishinglife.MyObject;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class FishingDrop implements ConfigurationSerializable {
	
	private ItemStack item;
	private double chance;
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> serialized = new HashMap<>();
		
		serialized.put("chance", chance);
		serialized.put("item", item);
		
		return serialized;
	}
	
	public static FishingDrop deserialize(Map<String, Object> serialized) {
		FishingDrop drop = new FishingDrop();
		
		drop.setChance((Double) serialized.get("chance"));
		drop.setItem((ItemStack) serialized.get("item"));
		
		return drop;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}
}






