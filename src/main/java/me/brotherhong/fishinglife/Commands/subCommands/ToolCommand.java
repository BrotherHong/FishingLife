package me.brotherhong.fishinglife.Commands.subCommands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Commands.SubCommand;

public class ToolCommand extends SubCommand {

	@Override
	public String getName() {
		return "tool";
	}

	@Override
	public String getDescription() {
		return "To get a selection tool!";
	}

	@Override
	public String getSyntax() {
		return "/fl tool";
	}

	@Override
	public void perform(Player player, String[] args) {
		
		ItemStack tool = new ItemStack(Material.getMaterial(config.getConfig().getString("selection-tool")));
		player.getInventory().addItem(tool);
		player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("selection-tool")));
		
	}
	
}
