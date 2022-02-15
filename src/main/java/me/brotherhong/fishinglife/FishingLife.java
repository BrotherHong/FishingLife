package me.brotherhong.fishinglife;

import java.util.HashMap;
import java.util.Objects;

import me.brotherhong.fishinglife.Listener.ModifyWeightListener;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.brotherhong.fishinglife.Commands.CommandManager;
import me.brotherhong.fishinglife.Listener.FishCaughtListener;
import me.brotherhong.fishinglife.Listener.MenuListener;
import me.brotherhong.fishinglife.Listener.ToolClickListener;
import me.brotherhong.fishinglife.Manager.ConfigManager;
import me.brotherhong.fishinglife.MenuSystem.PlayerMenuUtility;
import me.brotherhong.fishinglife.MyObject.FishingDrop;
import me.brotherhong.fishinglife.MyObject.Selection;

public class FishingLife extends JavaPlugin {
	
	private ConfigManager config;
	private ConfigManager lang;
	private ConfigManager area;
	private static final HashMap<Player, Selection> playerSelectionMap = new HashMap<>();
	private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
	private static String prefix;
	private static int maxLine;
	private static FishingLife plugin;

	static {
		ConfigurationSerialization.registerClass(FishingDrop.class);
	}
	
	@Override
	public void onEnable() {
		plugin = this;

		config = new ConfigManager("config.yml");
		lang = new ConfigManager("lang.yml");
		area = new ConfigManager("area.yml");

		getServer().getPluginManager().registerEvents(new FishCaughtListener(), this);
		getServer().getPluginManager().registerEvents(new ToolClickListener(), this);
		getServer().getPluginManager().registerEvents(new MenuListener(), this);
		getServer().getPluginManager().registerEvents(new ModifyWeightListener(), this);
        getCommand("fishinglife").setExecutor(new CommandManager());
		
		prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(lang.getConfig().getString("prefix")));
		maxLine = config.getConfig().getInt("size");
	}

	@Override
	public void onDisable() {
		
	}

	public static String getPrefix() {
		return prefix;
	}

	public static int getMaxSize() {
		return maxLine * 9;
	}
	
	public static Selection getPlayerSelection(Player player) {
		if (!playerSelectionMap.containsKey(player)) {
			Selection sel = new Selection();
			playerSelectionMap.put(player, sel);
		}
		return playerSelectionMap.get(player);
	}
	
	public static PlayerMenuUtility getPlayerMenuUtility(Player player) {
		if (!playerMenuUtilityMap.containsKey(player)) {
			PlayerMenuUtility utility = new PlayerMenuUtility(player);
			playerMenuUtilityMap.put(player, utility);
		}
		return playerMenuUtilityMap.get(player);
	}
	
	public ConfigManager getConfigConfig() {
		return this.config;
	}
	
	public ConfigManager getLangConfig() {
		return this.lang;
	}
	
	public ConfigManager getAreaConfig() {
		return this.area;
	}

	public static FishingLife getPlugin() {
		return plugin;
	}
}








