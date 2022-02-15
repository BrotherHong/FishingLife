package me.brotherhong.fishinglife.MyObject;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class FishingDrop implements ConfigurationSerializable {
	
	private ItemStack item;
	private int weight;
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> serialized = new HashMap<>();
		
		serialized.put("weight", weight);
		serialized.put("item", item);
		
		return serialized;
	}
	
	public static FishingDrop deserialize(Map<String, Object> serialized) {
		FishingDrop drop = new FishingDrop();
		
		drop.setWeight((Integer) serialized.get("weight"));
		drop.setItem((ItemStack) serialized.get("item"));
		
		return drop;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}






