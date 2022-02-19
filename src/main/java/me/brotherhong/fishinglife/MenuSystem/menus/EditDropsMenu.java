package me.brotherhong.fishinglife.MenuSystem.menus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import me.brotherhong.fishinglife.Listener.ModifyWeightListener;
import me.brotherhong.fishinglife.MenuSystem.PaginatedMenu;
import me.brotherhong.fishinglife.Msgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.MenuSystem.PlayerMenuUtility;
import me.brotherhong.fishinglife.MyObject.FishingDrop;

public class EditDropsMenu extends PaginatedMenu {

	private String areaName;
	private List<FishingDrop> dropItems = null;

	public EditDropsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		this.areaName = playerMenuUtility.getTargetAreaName();
	}

	@Override
	public int getSlots() {
		return 54;
	}

	@Override
	public String getMenuName() {
		return "The drops of " + areaName;
	}

	@Override
	public void handleMenu(InventoryClickEvent event) {

		ItemStack item = event.getCurrentItem();

		if (item == null)
			return;

		Player player = (Player) event.getWhoClicked();
		int targetSlot = event.getSlot();
		ClickType clickType = event.getClick();

		if (event.getSlot() == 45) {
			if (page == 0) {
				player.sendMessage(Msgs.FIRST_PAGE);
			} else {
				page = page - 1;
				super.open();
			}
		} else if (event.getSlot() == 53) {
			if ((index + 1) < dropItems.size()) {
				page = page + 1;
				super.open();
			} else {
				player.sendMessage(Msgs.LAST_PAGE);
			}
		} else if (event.getSlot() == 49) {
			// nothing
		} else {
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

				player.sendMessage(Msgs.NEW_WEIGHT_REQUEST);

			}
		}
	}

	@Override
	public void setMenuItems() {

		String path = "selected-area." + areaName + ".drops";
		dropItems = (ArrayList<FishingDrop>) area.getConfig().getList(path);
		Player player = playerMenuUtility.getOwner();

		// check null
		if (dropItems == null || dropItems.size() == 0) {
			player.sendMessage(Msgs.NO_DROPS);
			return;
		}

		int totalWeight = 0;

		for (FishingDrop fd : dropItems) {
			totalWeight += fd.getWeight();
		}

		dropItems.sort(Comparator.comparingInt(FishingDrop::getWeight));

		for (int i = 0;i < super.maxItemPerPage;i++) {
			index = super.maxItemPerPage * page + i;
			if (index >= dropItems.size()) break;

			FishingDrop fd = dropItems.get(index);

			ItemStack drop = fd.getItem().clone();
			ItemMeta meta = drop.getItemMeta();

			List<String> lore = meta.getLore();
			int weight = fd.getWeight();

			if (lore == null) {
				lore = new ArrayList<String>();
			}

			lore.add(ChatColor.translateAlternateColorCodes('&', Msgs.WEIGHT_DISPLAY.replaceAll("%weight%", Integer.toString(weight))));
			lore.add(ChatColor.translateAlternateColorCodes('&', Msgs.CHANCE_DISPLAY.replaceAll("%chance%", String.format("%.2f", (double) weight / totalWeight * 100.0))));
			lore.add(ChatColor.translateAlternateColorCodes('&', Msgs.CHANGE_WEIGHT_HINT));
			lore.add(ChatColor.translateAlternateColorCodes('&', Msgs.DELETE_HINT));
			meta.setLore(lore);

			drop.setItemMeta(meta);

			inventory.addItem(drop);
		}

		// current page
		ItemStack cur = new ItemStack(Material.COMPASS, 1);
		ItemMeta cur_meta = cur.getItemMeta();
		cur_meta.setDisplayName(ChatColor.GREEN + "目前頁數: " + Integer.toString(page+1));
		cur.setItemMeta(cur_meta);
		inventory.setItem(49, cur);

		// arrow
		ItemStack back = new ItemStack(Material.ARROW, 1);
		ItemMeta back_meta = back.getItemMeta();
		back_meta.setDisplayName(ChatColor.GREEN + "上一頁");
		back.setItemMeta(back_meta);
		inventory.setItem(45, back);

		ItemStack next = new ItemStack(Material.ARROW, 1);
		ItemMeta next_meta = back.getItemMeta();
		back_meta.setDisplayName(ChatColor.GREEN + "下一頁");
		next.setItemMeta(next_meta);
		inventory.setItem(53, next);

	}

}






