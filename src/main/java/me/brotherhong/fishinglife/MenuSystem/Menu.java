package me.brotherhong.fishinglife.MenuSystem;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Manager.ConfigManager;

public abstract class Menu implements InventoryHolder {
	
	protected FishingLife plugin;
	protected Inventory inventory;
	protected PlayerMenuUtility playerMenuUtility;
	
	protected ConfigManager config;
	protected ConfigManager lang;
	protected ConfigManager area;
	
	public Menu(FishingLife plugin, PlayerMenuUtility playerMenuUtility) {
		this.plugin = plugin;
		this.playerMenuUtility = playerMenuUtility;
		
		config = plugin.getConfigConfig();
		lang = plugin.getLangConfig();
		area = plugin.getAreaConfig();
	}
	
	public abstract String getMenuName();
	
	public abstract int getSlots();
	
	public abstract void handleMenu(InventoryClickEvent event);
	
	public abstract void setMenuItems();
	
	public abstract void open();
	
	@Override
	public Inventory getInventory() {
		return inventory;
	}
	
}
