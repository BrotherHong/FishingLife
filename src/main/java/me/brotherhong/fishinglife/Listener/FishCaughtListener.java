package me.brotherhong.fishinglife.Listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import me.brotherhong.fishinglife.Msgs;
import me.brotherhong.fishinglife.MyObject.FishingArea;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockVector;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Manager.ConfigManager;
import me.brotherhong.fishinglife.MyObject.FishingDrop;

public class FishCaughtListener implements Listener {
	
	private FishingLife plugin;

	private ConfigManager area;
	
	public FishCaughtListener() {
		this.plugin = FishingLife.getPlugin();
		area = plugin.getAreaConfig();
	}
	
	@EventHandler
	public void onFishCaught(PlayerFishEvent event) {
		if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
		
		if (event.getCaught() == null || !(event.getCaught() instanceof Item)) {
			return;
		}
		
		Player player = event.getPlayer();
		String areaName = inWhichFishingArea(event.getHook().getLocation());
		
		if (areaName == null) 
			return;
		
		// player.sendMessage("You successfully fish up a fish in area " + areaName);
		
		Item item = (Item) event.getCaught();
		item.setItemStack(getPrize(areaName));
		
		String name = item.getItemStack().getItemMeta().getDisplayName();
		
		if (name.equals("")) {
			name = item.getName();
		}
		
		player.sendMessage(Msgs.FISH_CAUGHT.replaceAll("%fish%", name).replaceAll("%amount%", Integer.toString(item.getItemStack().getAmount())));
		
	}
	
	private ItemStack getPrize(String areaName) {
		
		List<FishingDrop> drops = FishingArea.getFishingArea(areaName).getDrops();
		
		if (drops == null || drops.isEmpty()) {
			return new ItemStack(Material.COD, 1);
		}

		Collections.shuffle(drops);
		
		// chance random from stack overflow
		double totalChance = 0.0;
		
		for (FishingDrop fd : drops) {
			totalChance += fd.getChance();
		}
		
		Random rand = new Random();
		double rnd = rand.nextDouble(totalChance);
		
		for (int i = 0;i < drops.size();i++) {
			if (rnd < drops.get(i).getChance()) {
				return drops.get(i).getItem();
			}
			rnd -= drops.get(i).getChance();
		}
		
		return new ItemStack(Material.COD, 1);
	}
	
	private String inWhichFishingArea(Location loc) {
		
		if (area.getConfig().getConfigurationSection("selected-area") == null)
			return null;
		
		for (String areaName : area.getConfig().getConfigurationSection("selected-area").getKeys(false)) {
			
			String path = "selected-area." + areaName + ".";
			BlockVector one = area.getConfig().getVector(path + "starting-point").toBlockVector();
			BlockVector two = area.getConfig().getVector(path + "ending-point").toBlockVector();
			
			if (one.getBlockX() <= loc.getBlockX() && loc.getBlockX() <= two.getBlockX()
					&& one.getBlockY() <= loc.getBlockY() && loc.getBlockY() <= two.getBlockY()
					&& one.getBlockZ() <= loc.getBlockZ() && loc.getBlockZ() <= two.getBlockZ()) {
				return areaName;
			}
		}
		return null;
	}
	
}









