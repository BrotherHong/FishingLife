package me.brotherhong.fishinglife.Manager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.brotherhong.fishinglife.FishingLife;

public class ConfigManager {
	
	private FishingLife plugin;
	private FileConfiguration dataConfig = null;
	private File configFile = null;
	private String fileName = null;
	
	public ConfigManager(String fileName) {
		this.plugin = FishingLife.getPlugin();
		this.fileName = fileName;
		// save / initializing the config
		saveDefaultConfig();
	}
	
	public void reloadConfig() {
		if (this.configFile == null) {
			this.configFile = new File(this.plugin.getDataFolder(), fileName);
		}
		
		this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
		
		InputStream defaultStream = this.plugin.getResource(fileName);
		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			this.dataConfig.setDefaults(defaultConfig);
		}
	}
	
	public FileConfiguration getConfig() {
		if (this.dataConfig == null) {
			reloadConfig();
		}
		return this.dataConfig;
	}
	
	public void saveConfig() {
		if (this.dataConfig == null || this.configFile == null) return;
		
		try {
			this.getConfig().save(this.configFile);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
		}
	}
	
	public void saveDefaultConfig() {
		if (this.configFile == null) {
			this.configFile = new File(this.plugin.getDataFolder(), fileName);
		}
		if (!this.configFile.exists()) {
			this.plugin.saveResource(fileName, false);
		}
	}
	
}







