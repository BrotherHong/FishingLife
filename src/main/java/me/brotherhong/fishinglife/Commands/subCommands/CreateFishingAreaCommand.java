package me.brotherhong.fishinglife.Commands.subCommands;

import me.brotherhong.fishinglife.MyObject.FishingArea;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Commands.SubCommand;
import me.brotherhong.fishinglife.MyObject.Selection;

public class CreateFishingAreaCommand extends SubCommand {

	@Override
	public String getName() {
		return "create";
	}

	@Override
	public String getDescription() {
		return "Create a fishing area";
	}

	@Override
	public String getSyntax() {
		return "/fl create <name>";
	}

	@Override
	public void perform(Player player, String[] args) {
		
		if (args.length == 1) {
			sendUsage(player);
			return;
		}
		
		String areaName = args[1];
		FishingArea fishingArea = new FishingArea();
		fishingArea.setSelection(FishingLife.getPlayerSelection(player));
		
		if (fishingArea.isSelectionAccepted()) {
			player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("no-select")));
			return;
		}

		FishingArea checkedArea = new FishingArea();
		for (String name : area.getConfig().getConfigurationSection("selected-area").getKeys(false)) {

			Selection checkedSelection = new Selection();
			checkedSelection.setBlockOne(area.getConfig().getVector("selected-area." + name + ".starting-point").toBlockVector());
			checkedSelection.setBlockTwo(area.getConfig().getVector("selected-area." + name + ".ending-point").toBlockVector());

			checkedArea.setSelection(checkedSelection);

			if (FishingArea.isConflict(fishingArea, checkedArea)) {
				player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("area-conflict")));
				return;
			}

		}
		
		if (isNameExist(areaName)) {
			player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("same-name")));
			return;
		}

		fishingArea.write(areaName);

		// successful message
		player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("successful-create").replaceAll("%area_name%", areaName)));
	}

}












