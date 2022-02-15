package me.brotherhong.fishinglife.Listener;

import me.brotherhong.fishinglife.FishingLife;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import me.brotherhong.fishinglife.MenuSystem.Menu;

public class MenuListener implements Listener {

	FishingLife plugin;

	public MenuListener() {
		this.plugin = FishingLife.getPlugin();
	}

	@EventHandler
	public void onMenuClick(InventoryClickEvent event) {
		
		if (event.getClickedInventory() == null || event.getInventory() != event.getClickedInventory())
			return;
		
		InventoryHolder holder = event.getClickedInventory().getHolder();

		if (holder instanceof Menu) {

			event.setCancelled(true);

			if (event.getCurrentItem() == null)
				return;

			Menu menu = (Menu) holder;
			
			menu.handleMenu(event);
		}
		
	}
	
}






