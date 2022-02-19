package me.brotherhong.fishinglife.Commands.subCommands;

import java.util.ArrayList;
import java.util.List;

import me.brotherhong.fishinglife.Msgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Commands.SubCommand;
import me.brotherhong.fishinglife.MyObject.FishingDrop;

public class AddFishingDropsCommand extends SubCommand {

	@Override
	public String getName() {
		return "addItem";
	}

	@Override
	public String getDescription() {
		return "Add custom drop to your fishing area";
	}

	@Override
	public String getSyntax() {
		return "/fl addItem <areaName> <weight>";
	}

	@Override
	public void perform(Player player, String[] args) {
		
		if (args.length < 3) {
			sendUsage(player);
			return;
		}
		
		String areaName = args[1];
		String path = "selected-area." + areaName + ".drops";
		
		// check if name exist
		if (!isNameExist(areaName)) {
			sendAreaNotFound(player);
			return;
		}
		
		// check if input weight available
		if (!args[2].matches("^[1-9]+[0-9]*$")) {
			player.sendMessage(Msgs.ONLY_INTEGER);
			return;
		}
		int weight = Integer.parseInt(args[2]);
		
		List<FishingDrop> dropItems = (ArrayList<FishingDrop>) area.getConfig().getList(path);
		if (dropItems == null) {
			dropItems = new ArrayList<>();
		}
		
		// check if have item on hand
		ItemStack target = player.getInventory().getItemInMainHand().clone();
		if (target.getType().isAir()) {
			player.sendMessage(Msgs.NO_ITEM_IN_HAND);
			return;
		}
		
		// has same drop
		if (hasSameDrop(areaName, target)) {
			player.sendMessage(Msgs.SAME_DROPS);
			return;
		}
		
		FishingDrop newDrop = new FishingDrop();
		newDrop.setItem(target);
		newDrop.setWeight(weight);
		
		dropItems.add(newDrop);
		
		area.getConfig().set(path, dropItems);
		area.saveConfig();

		player.sendMessage(Msgs.SUCCESS_ADD);
	}
	
	private boolean hasSameDrop(String areaName, ItemStack target) {
		
		if (area.getConfig().getConfigurationSection("selected-area") == null)
			return false;
		
		String path = "selected-area." + areaName + ".drops";
		List<FishingDrop> drops = (ArrayList<FishingDrop>) area.getConfig().getList(path);
		if (drops == null) {
			return false;
		}
		
		for (FishingDrop fd : drops) {
			if (fd.getItem().equals(target)) {
				return true;
			}
		}
		
		return false;
	}


}
