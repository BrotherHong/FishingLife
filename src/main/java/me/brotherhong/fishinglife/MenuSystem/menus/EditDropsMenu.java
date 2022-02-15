package me.brotherhong.fishinglife.MenuSystem.menus;

import java.util.ArrayList;
import java.util.List;

import me.brotherhong.fishinglife.Listener.ModifyWeightListener;
import me.brotherhong.fishinglife.MenuSystem.ChestMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.MenuSystem.PlayerMenuUtility;
import me.brotherhong.fishinglife.MyObject.FishingDrop;

public class EditDropsMenu extends ChestMenu {

	private String areaName;

	public EditDropsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		this.areaName = playerMenuUtility.getTargetAreaName();
	}

	@Override
	public int getSlots() {
		return FishingLife.getMaxSize();
	}

	@Override
	public String getMenuName() {
		return "The drops of " + ChatColor.AQUA + areaName;
	}

	@Override
	public void handleMenu(InventoryClickEvent event) {

		if (event.getCurrentItem() == null)
			return;

		Player player = (Player) event.getWhoClicked();
		int targetSlot = event.getSlot();
		ClickType clickType = event.getClick();


		if (clickType == ClickType.RIGHT) { // delete

			// open confirm menu
			playerMenuUtility.setTargetAreaName(areaName);
			playerMenuUtility.setTargetSlots(targetSlot);
			new ConfirmDeleteDropsMenu(FishingLife.getPlayerMenuUtility(player)).open();

		} else if (clickType == ClickType.LEFT) { // modify weight

			player.closeInventory();

			FishingLife.getPlayerMenuUtility(player).setTargetSlots(event.getSlot());
			FishingLife.getPlayerMenuUtility(player).setTargetAreaName(areaName);

			// modify
			ModifyWeightListener.waiting.add(player);

			player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("new-weight-request")));

		}


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

		int totalWeight = 0;

		for (FishingDrop fd : dropItems) {
			totalWeight += fd.getWeight();
		}

		for (FishingDrop fd : dropItems) {

			ItemStack drop = fd.getItem().clone();
			ItemMeta meta;

			if (!drop.hasItemMeta()) {
				meta = Bukkit.getItemFactory().getItemMeta(drop.getType());
			} else {
				meta = drop.getItemMeta();
			}

			List<String> lore = meta.getLore();
			int weight = fd.getWeight();

			if (lore == null) {
				lore = new ArrayList<String>();
			}

			lore.add(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("weight-display").replaceAll("%weight%", Integer.toString(weight))));
			lore.add(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("chance-display").replaceAll("%chance%", String.format("%.2f", (double) weight / totalWeight * 100.0))));
			lore.add(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("change-weight-hint")));
			lore.add(ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("delete-hint")));
			meta.setLore(lore);

			drop.setItemMeta(meta);

			inventory.addItem(drop);
		}
	}

}






