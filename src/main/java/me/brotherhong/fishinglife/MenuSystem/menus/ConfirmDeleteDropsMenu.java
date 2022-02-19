package me.brotherhong.fishinglife.MenuSystem.menus;

import java.util.ArrayList;
import java.util.List;

import me.brotherhong.fishinglife.Msgs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.MenuSystem.Menu;
import me.brotherhong.fishinglife.MenuSystem.PlayerMenuUtility;
import me.brotherhong.fishinglife.MyObject.FishingDrop;

public class ConfirmDeleteDropsMenu extends Menu {
	
	private String areaName;
	private int targetSlot;

	public ConfirmDeleteDropsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		this.areaName = playerMenuUtility.getTargetAreaName();
		this.targetSlot = playerMenuUtility.getTargetSlots();
	}

	@Override
	public String getMenuName() {
		return "Confirm: Are You Sure?";
	}

	@Override
	public int getSlots() {
		return 9;
	}

	@Override
	public void handleMenu(InventoryClickEvent event) {
		
		String path = "selected-area." + areaName + ".drops";
		List<FishingDrop> dropItems = (ArrayList<FishingDrop>) area.getConfig().getList(path);
		Player player = playerMenuUtility.getOwner();
		
		switch (event.getCurrentItem().getType()) {
			case GREEN_WOOL:
				dropItems.remove(targetSlot);
				
				area.getConfig().set(path, dropItems);
				area.saveConfig();
				player.sendMessage(Msgs.SUCCESS_DELETE);
				break;
			case RED_WOOL:
				player.sendMessage(Msgs.CANCEL_DELETE);
				break;
		}

		FishingLife.getPlayerMenuUtility(player).setTargetAreaName(areaName);
		new EditDropsMenu(playerMenuUtility).open();
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
