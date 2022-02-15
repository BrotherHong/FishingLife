package me.brotherhong.fishinglife.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Manager.ConfigManager;

public abstract class SubCommand {
	
	protected FishingLife plugin;
	
	protected ConfigManager config;
	protected ConfigManager lang;
	protected ConfigManager area;
	
	public SubCommand(FishingLife plugin) {
		this.plugin = plugin;
		config = plugin.getConfigConfig();
		lang = plugin.getLangConfig();
		area = plugin.getAreaConfig();
	}
	
	public abstract String getName();
	
	public abstract String getDescription();
	
	public abstract String getSyntax();
	
	public abstract void perform(Player player, String[] args);
	
	protected void sendUsage(Player target) {
		target.sendMessage(FishingLife.getPrefix() + ChatColor.RED + "Usage: " + getSyntax());
	}
	
	protected boolean isNameExist(String name) {
		
		if (area.getConfig().getConfigurationSection("selected-area") == null)
			return false;
		
		for (String areaName : area.getConfig().getConfigurationSection("selected-area").getKeys(false)) {
			if (areaName.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
}
