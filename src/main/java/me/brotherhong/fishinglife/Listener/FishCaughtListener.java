package me.brotherhong.fishinglife.Listener;

import java.util.ArrayList;
import java.util.Random;

import me.brotherhong.fishinglife.Msgs;
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
	
	private ConfigManager config;
	private ConfigManager area;
	private ConfigManager lang;
	
	public FishCaughtListener() {
		this.plugin = FishingLife.getPlugin();
		config = plugin.getConfigConfig();
		area = plugin.getAreaConfig();
		lang = plugin.getLangConfig();
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
		
		ArrayList<FishingDrop> drops = (ArrayList<FishingDrop>)area.getConfig().getList("selected-area." + areaName + ".drops");
		
		if (drops == null || drops.isEmpty()) {
			return new ItemStack(Material.COD, 1);
		}
		
		// weighted random from stack overflow
		int totalWeight = 0;
		
		for (FishingDrop fd : drops) {
			totalWeight += fd.getWeight();
		}
		
		Random rand = new Random();
		int rnd = rand.nextInt(totalWeight);
		
		// System.out.println(rnd);
		
		for (int i = 0;i < drops.size();i++) {
			if (rnd < drops.get(i).getWeight()) {
				return drops.get(i).getItem();
			}
			rnd -= drops.get(i).getWeight();
		}
		
		return null;
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









