package me.brotherhong.fishinglife.Commands.subCommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Commands.SubCommand;
import me.brotherhong.fishinglife.MenuSystem.menus.ConfirmDeleteAreaMenu;

public class DeleteFishingAreaCommand extends SubCommand {

	@Override
	public String getName() {
		return "delete";
	}

	@Override
	public String getDescription() {
		return "Delete the area";
	}

	@Override
	public String getSyntax() {
		return "/fl delete <areaName>";
	}

	@Override
	public void perform(Player player, String[] args) {
		
		if (args.length == 1) {
			sendUsage(player);
			return;
		}
		
		String areaName = args[1];
		
		if (!isNameExist(areaName)) {
			sendAreaNotFound(player);
			return;
		}
		
		// open confirm menu
		FishingLife.getPlayerMenuUtility(player).setTargetAreaName(areaName);
		new ConfirmDeleteAreaMenu(FishingLife.getPlayerMenuUtility(player)).open();
	}
	
}








