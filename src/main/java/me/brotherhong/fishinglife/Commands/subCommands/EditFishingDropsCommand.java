package me.brotherhong.fishinglife.Commands.subCommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Commands.SubCommand;
import me.brotherhong.fishinglife.MenuSystem.menus.EditDropsMenu;

public class EditFishingDropsCommand extends SubCommand {

	public EditFishingDropsCommand(FishingLife plugin) {
		super(plugin);
	}

	@Override
	public String getName() {
		return "edit";
	}

	@Override
	public String getDescription() {
		return "Edit the drops of certain fishing area";
	}

	@Override
	public String getSyntax() {
		return "/fl edit <areaName>";
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
			player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("not-found")));
			return;
		}

		FishingLife.getPlayerMenuUtility(player).setTargetAreaName(areaName);
		new EditDropsMenu(plugin, FishingLife.getPlayerMenuUtility(player)).open();
	}

}







