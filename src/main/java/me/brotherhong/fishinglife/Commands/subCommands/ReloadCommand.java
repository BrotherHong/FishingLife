package me.brotherhong.fishinglife.Commands.subCommands;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Commands.SubCommand;

public class ReloadCommand extends SubCommand {

	public ReloadCommand(FishingLife plugin) {
		super(plugin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "reload";
	}

	@Override
	public String getDescription() {
		return "Reload this plugin.";
	}

	@Override
	public String getSyntax() {
		return "/fl reload";
	}

	@Override
	public void perform(Player player, String[] args) {
		Bukkit.getServer().getLogger().log(Level.INFO, "[FishingLife] Reloading config.yml...");
		config.reloadConfig();
		
		Bukkit.getServer().getLogger().log(Level.INFO, "[FishingLife] Reloading lang.yml...");
		lang.reloadConfig();
		
		Bukkit.getServer().getLogger().log(Level.INFO, "[FishingLife] Reloading area.yml...");
		area.reloadConfig();
		
		Bukkit.getServer().getLogger().log(Level.INFO, "[FishingLife] Reload complete!");
		player.sendMessage(FishingLife.getPrefix() + ChatColor.GREEN + "Reload Complete!");
	}
	
	
	
}
