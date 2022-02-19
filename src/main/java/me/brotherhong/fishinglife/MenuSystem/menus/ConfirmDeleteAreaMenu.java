package me.brotherhong.fishinglife.MenuSystem.menus;

import me.brotherhong.fishinglife.Msgs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.brotherhong.fishinglife.MenuSystem.Menu;
import me.brotherhong.fishinglife.MenuSystem.PlayerMenuUtility;

public class ConfirmDeleteAreaMenu extends Menu {
	
	String areaName;

	public ConfirmDeleteAreaMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		this.areaName = playerMenuUtility.getTargetAreaName();
	}
	
	@Override
	public int getSlots() {
		return 9;
	}

	@Override
	public String getMenuName() {
		return "Confirm: Are You Sure?";
	}

	@Override
	public void handleMenu(InventoryClickEvent event) {
		
		String path = "selected-area." + areaName;
		Player player = (Player) event.getWhoClicked();
		
		switch (event.getCurrentItem().getType()) {
			case GREEN_WOOL:
				area.getConfig().set(path, null);
				area.saveConfig();
				player.sendMessage(Msgs.SUCCESS_DELETE);
				break;
			case RED_WOOL:
				player.sendMessage(Msgs.CANCEL_DELETE);
				break;
		}
		player.closeInventory();
	}

	@Override
	public void setMenuItems() {
		
		ItemStack yes = new ItemStack(Material.GREEN_WOOL, 1);
		ItemMeta yes_meta = yes.getItemMeta();
		yes_meta.setDisplayName(ChatColor.GREEN + "Yes");
		yes.setItemMeta(yes_meta);
		
		ItemStack no = new ItemStack(Material.RED_WOOL, 1);
		ItemMeta no_meta = no.getItemMeta();
		no_meta.setDisplayName(ChatColor.RED + "No");
		no.setItemMeta(no_meta);
		
		inventory.setItem(3, yes);
		inventory.setItem(5, no);
	}
	
}










