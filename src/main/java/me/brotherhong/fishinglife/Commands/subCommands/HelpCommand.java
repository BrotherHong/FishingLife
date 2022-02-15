package me.brotherhong.fishinglife.Commands.subCommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Commands.CommandManager;
import me.brotherhong.fishinglife.Commands.SubCommand;

public class HelpCommand extends SubCommand {

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "Show all the commands of this plugin";
	}

	@Override
	public String getSyntax() {
		return "/fl help";
	}

	@Override
	public void perform(Player player, String[] args) {
		
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&a>----------<%s&a>----------<", FishingLife.getPrefix().trim())));
		
		for (SubCommand cmd : CommandManager.getSubCommands()) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&b%s &f- %s", cmd.getSyntax(), cmd.getDescription())));
		}
		
	}
	
}
