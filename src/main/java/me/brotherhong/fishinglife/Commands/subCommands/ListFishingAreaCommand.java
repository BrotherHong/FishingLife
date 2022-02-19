package me.brotherhong.fishinglife.Commands.subCommands;

import java.util.Set;

import me.brotherhong.fishinglife.Msgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Commands.SubCommand;

public class ListFishingAreaCommand extends SubCommand {

	@Override
	public String getName() {
		return "list";
	}

	@Override
	public String getDescription() {
		return "List all fishing area";
	}

	@Override
	public String getSyntax() {
		return "/fl list";
	}

	@Override
	public void perform(Player player, String[] args) {
		
		if (area.getConfig().getConfigurationSection("selected-area") == null)
			return;
		
		Set<String> names = area.getConfig().getConfigurationSection("selected-area").getKeys(false);

		player.sendMessage(Msgs.LIST_AREA.replaceAll("%area_amount%", Integer.toString(names.size())));
		
		for (String areaName : names) {
			
			player.sendMessage(" - " + ChatColor.AQUA + areaName);
			
		}
		
	}
	
}
