package me.brotherhong.fishinglife.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Commands.subCommands.AddFishingDropsCommand;
import me.brotherhong.fishinglife.Commands.subCommands.CreateFishingAreaCommand;
import me.brotherhong.fishinglife.Commands.subCommands.DeleteFishingAreaCommand;
import me.brotherhong.fishinglife.Commands.subCommands.EditFishingDropsCommand;
import me.brotherhong.fishinglife.Commands.subCommands.HelpCommand;
import me.brotherhong.fishinglife.Commands.subCommands.ListFishingAreaCommand;
import me.brotherhong.fishinglife.Commands.subCommands.ReloadCommand;
import me.brotherhong.fishinglife.Commands.subCommands.ShowDropsCommand;
import me.brotherhong.fishinglife.Commands.subCommands.ToolCommand;
import me.brotherhong.fishinglife.Manager.ConfigManager;

public class CommandManager implements TabExecutor {
	
	private static ArrayList<SubCommand> subCommands = new ArrayList<>();
	private FishingLife plugin;
	private ConfigManager lang;
	private ConfigManager area;
	private List<String> cmdNames;
	private List<String> areaNames;
	
	public CommandManager(FishingLife plugin) {
		this.plugin = plugin;
		lang = plugin.getLangConfig();
		area = plugin.getAreaConfig();
		subCommands.add(new ToolCommand(this.plugin));
		subCommands.add(new CreateFishingAreaCommand(plugin));
		subCommands.add(new DeleteFishingAreaCommand(plugin));
		subCommands.add(new ListFishingAreaCommand(plugin));
		subCommands.add(new AddFishingDropsCommand(plugin));
		subCommands.add(new EditFishingDropsCommand(plugin));
		subCommands.add(new ShowDropsCommand(plugin));
		subCommands.add(new HelpCommand(plugin));
		subCommands.add(new ReloadCommand(plugin));
		
		subCommands.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			boolean found = false;
			
			if (!player.hasPermission("fishinglife.show")) {
				player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("no-permission")));
				return true;
			}
			
			if (args.length > 0) {
				for (int i = 0;i < getSubCommands().size();i++) {
					if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
						if (!args[0].equalsIgnoreCase("show") && !player.hasPermission("fishinglife.op")) {
							player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("no-permission")));
							return true;
						}
						getSubCommands().get(i).perform(player, args);
						found = true;
						break;
					}
				}
				if (!found) {
					player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("unknown-command")));
				}
			} else { // only /fl
				// help command
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&a>----------<%s&a>----------<", FishingLife.getPrefix().trim())));
				
				for (SubCommand scmd : CommandManager.getSubCommands()) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&b%s &f- %s", scmd.getSyntax(), scmd.getDescription())));
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public static ArrayList<SubCommand> getSubCommands() {
		return subCommands;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		
		cmdNames = ((List<SubCommand>)subCommands.clone()).stream().map(sc -> sc.getName()).collect(Collectors.toList());
		
		List<String> result = new ArrayList<>();
		if (args.length == 1) {
			
			for (String cname : cmdNames) {
				if (cname.toLowerCase().startsWith(args[0])) {
					result.add(cname);
				}
			}
			
			return result;
		} else if (args.length == 2) {
			
			areaNames = area.getConfig().getConfigurationSection("selected-area")
										.getKeys(false)
										.stream()
										.collect(Collectors.toList());
			
			if (args[0].equalsIgnoreCase("additem")) {
				result = getSimilar(args[1]);
			} else if (args[0].equalsIgnoreCase("create")) {
				result.add("<name>");
			} else if (args[0].equalsIgnoreCase("delete")) {
				result = getSimilar(args[1]);
			} else if (args[0].equalsIgnoreCase("edit")) {
				result = getSimilar(args[1]);
			} else if (args[0].equalsIgnoreCase("show")) {
				result = getSimilar(args[1]);
			}
			return result;
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("additem")) {
				result.add("<weight>");
			}
			return result;
		}
		
		return null;
	}
	
	private List<String> getSimilar(String str) {
		List<String> result = new ArrayList<>();
		
		for (String an : areaNames) {
			if (an.toLowerCase().startsWith(str)) {
				result.add(an);
			}
		}
		return result;
	}
	
}






