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
	
	private final ConfigManager config = new ConfigManager(this, "config.yml");
	private final ConfigManager lang = new ConfigManager(this, "lang.yml");
	private final ConfigManager area = new ConfigManager(this, "area.yml");
	private static final HashMap<Player, Selection> playerSelectionMap = new HashMap<>();
	private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
	private static String prefix;
	private static int maxLine;
	
	static {
		ConfigurationSerialization.registerClass(FishingDrop.class);
	}
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new FishCaughtListener(this), this);
		getServer().getPluginManager().registerEvents(new ToolClickListener(this), this);
		getServer().getPluginManager().registerEvents(new MenuListener(this), this);
		getServer().getPluginManager().registerEvents(new ModifyWeightListener(this), this);
        getCommand("fishinglife").setExecutor(new CommandManager(this));
		
		prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(lang.getConfig().getString("prefix")));
		maxLine = config.getConfig().getInt("size");
	}
	
	public static String getPrefix() {
		return prefix;
	}
	
	public static int getMaxSize() {
		return maxLine * 9;
	}

	@Override
	public void onDisable() {
		
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
}








