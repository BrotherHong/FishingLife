package me.brotherhong.fishinglife.MenuSystem.menus;

import java.util.ArrayList;
import java.util.List;

import me.brotherhong.fishinglife.MenuSystem.ChestMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.MenuSystem.Menu;
import me.brotherhong.fishinglife.MenuSystem.PlayerMenuUtility;
import me.brotherhong.fishinglife.MyObject.FishingDrop;

public class ShowDropsMenu extends ChestMenu {
	
	private String areaName;
	
	public ShowDropsMenu(FishingLife plugin, PlayerMenuUtility playerMenuUtility, String areaName) {
		super(plugin, playerMenuUtility);
		this.areaName = areaName;
	}

	@Override
	public String getMenuName() {
		return "The drops of " + areaName;
	}

	@Override
	public int getSlots() {
		return FishingLife.getMaxSize();
	}

	@Override
	public void handleMenu(InventoryClickEvent event) {
		// null
	}

	@Override
	public void setMenuItems() {
		
		String path = "selected-area." + areaName + ".drops";
		List<FishingDrop> dropItems = (ArrayList<FishingDrop>) area.getConfig().getList(path);
		Player player = playerMenuUtility.getOwner();
		
		// check null
		if (dropItems == null || dropItems.size() == 0) {
			player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("no-drops")));
			return;
		}
		
		// Bukkit.getServer().getLogger().log(Level.WARNING, Integer.toString(dropItems.size()));
		
		int totalWeight = 0;
		
		for (FishingDrop fd : dropItems) {
			totalWeight += fd.getWeight();
		}
		
		for (FishingDrop fd : dropItems) {
			
			ItemStack drop = fd.getItem().clone();
			ItemMeta meta = drop.getItemMeta();
			
			// Bukkit.getServer().getLogger().log(Level.WARNING, drop.getType().toString());
			
			if (meta == null) {
				// Bukkit.getServer().getLogger().log(Level.WARNING, "SHOW");
				return;
			}
			
			List<String> lore = meta.getLore();
			int weight = fd.getWeight();
			
			if (lore == null) {
				lore = new ArrayList<String>();
			}
			
			lore.add(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("weight-display").replaceAll("%weight%", Integer.toString(weight))));
			lore.add(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("chance-display").replaceAll("%chance%", String.format("%.2f", (double)weight/totalWeight*100.0))));
			meta.setLore(lore);
			
			drop.setItemMeta(meta);
			
			inventory.addItem(drop);
		}
		
	}
	
	
	
}
