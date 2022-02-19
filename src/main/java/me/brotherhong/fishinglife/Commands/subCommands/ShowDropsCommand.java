package me.brotherhong.fishinglife.Commands.subCommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Commands.SubCommand;
import me.brotherhong.fishinglife.MenuSystem.menus.ShowDropsMenu;

public class ShowDropsCommand extends SubCommand {

	@Override
	public String getName() {
		return "show";
	}

	@Override
	public String getDescription() {
		return "Show all drops of certain area";
	}

	@Override
	public String getSyntax() {
		return "/fl show <areaName>";
	}

	@Override
	public void perform(Player player, String[] args) {
		
		if (args.length == 1) {
			sendUsage(player);
			return;
		}
		
		String areaName = args[1];
		
		// check if name exist
		if (!isNameExist(areaName)) {
			sendAreaNotFound(player);
			return;
		}

		// open menu
		FishingLife.getPlayerMenuUtility(player).setTargetAreaName(areaName);
		new ShowDropsMenu(FishingLife.getPlayerMenuUtility(player)).open();
	}
	
}








