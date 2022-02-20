package me.brotherhong.fishinglife.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.brotherhong.fishinglife.Commands.subCommands.*;
import me.brotherhong.fishinglife.Msgs;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Manager.ConfigManager;

public class CommandManager implements TabExecutor {
	
	private static ArrayList<SubCommand> subCommands = new ArrayList<>();
	private FishingLife plugin;
	private ConfigManager area;
	private List<String> cmdNames;
	private List<String> areaNames;
	
	public CommandManager() {
		this.plugin = FishingLife.getPlugin();
		area = plugin.getAreaConfig();
		subCommands.add(new ToolCommand());
		subCommands.add(new CreateFishingAreaCommand());
		subCommands.add(new DeleteFishingAreaCommand());
		subCommands.add(new ListFishingAreaCommand());
		subCommands.add(new AddFishingDropsCommand());
		subCommands.add(new EditFishingDropsCommand());
		subCommands.add(new ShowDropsCommand());
		subCommands.add(new HelpCommand());
		subCommands.add(new ReloadCommand());
		subCommands.add(new ExpandAreaCommand());
		
		subCommands.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			boolean found = false;
			
			if (!player.hasPermission("fishinglife.show")) {
				player.sendMessage(Msgs.NO_PERMISSION);
				return true;
			}
			
			if (args.length > 0) {
				for (int i = 0;i < getSubCommands().size();i++) {
					if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
						if (!args[0].equalsIgnoreCase("show") && !player.hasPermission("fishinglife.op")) {
							player.sendMessage(Msgs.NO_PERMISSION);
							return true;
						}
						getSubCommands().get(i).perform(player, args);
						found = true;
						break;
					}
				}
				if (!found) {
					player.sendMessage(Msgs.UNKNOWN_COMMAND);
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
		
		cmdNames = ((List<SubCommand>)subCommands.clone()).stream().map(SubCommand::getName).collect(Collectors.toList());
		
		List<String> result = new ArrayList<>();
		if (args.length == 1) {
			
			for (String cname : cmdNames) {
				if (cname.toLowerCase().startsWith(args[0])) {
					result.add(cname);
				}
			}
			
			return result;
		} else if (args.length == 2) {
			
			areaNames = new ArrayList<>(area.getConfig().getConfigurationSection("selected-area")
					.getKeys(false));
			
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
			} else if (args[0].equalsIgnoreCase("expand")) {
				result = getSimilar(args[1]);
			}
			return result;
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("additem")) {
				result.add("<chance>");
			} else if (args[0].equalsIgnoreCase("expand")) {
				result.add("<number>");
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






