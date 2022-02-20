package me.brotherhong.fishinglife.Commands.subCommands;

import java.util.Set;

import me.brotherhong.fishinglife.Msgs;
import me.brotherhong.fishinglife.MyObject.FishingArea;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;

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
			FishingArea fishingArea = FishingArea.getFishingArea(areaName);

			TextComponent text = new TextComponent(" - " + areaName);
			text.setColor(ChatColor.GRAY);
			text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.AQUA + "(" + fishingArea.getSelection().getBlockOne().toString() + ")")));
			player.spigot().sendMessage(text);
		}
		
	}
	
}
