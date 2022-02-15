package me.brotherhong.fishinglife.Commands.subCommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Commands.SubCommand;
import me.brotherhong.fishinglife.MyObject.Selection;

public class CreateFishingAreaCommand extends SubCommand {

	public CreateFishingAreaCommand(FishingLife plugin) {
		super(plugin);
	}

	@Override
	public String getName() {
		return "create";
	}

	@Override
	public String getDescription() {
		return "Create a fishing area";
	}

	@Override
	public String getSyntax() {
		return "/fl create <name>";
	}

	@Override
	public void perform(Player player, String[] args) {
		
		if (args.length == 1) {
			sendUsage(player);
			return;
		}
		
		String areaName = args[1];
		String path = "selected-area." + areaName;
		
		Selection pSel = FishingLife.getPlayerSelection(player);
		pSel.sort();
		BlockVector one = pSel.getBlockOne();
		BlockVector two = pSel.getBlockTwo();
		
//		System.out.println(one.toString());
//		System.out.println(two.toString());
		
		if (one == null || two == null) {
			player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("no-select")));
			return;
		}
		
		if (isAreaConflict(pSel)) {
			player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("area-conflict")));
			return;
		}
		
		if (isNameExist(areaName)) {
			player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("same-name")));
			return;
		}
		
		// establish the area
		area.getConfig().set(path + ".starting-point", one);
		area.getConfig().set(path + ".ending-point", two);
		area.saveConfig();
		player.sendMessage(FishingLife.getPrefix() + ChatColor.translateAlternateColorCodes('&', lang.getConfig().getString("successful-create").replaceAll("%area_name%", areaName)));
	}
	
	private boolean isAreaConflict(Selection sel) {
		BlockVector one = sel.getBlockOne();
		BlockVector two = sel.getBlockTwo();
		
		if (area.getConfig().getConfigurationSection("selected-area") == null)
			return false;
		
		for (String areaName : area.getConfig().getConfigurationSection("selected-area").getKeys(false)) {
			
			String path = "selected-area." + areaName + ".";
			BlockVector ckOne = area.getConfig().getVector(path + "starting-point").toBlockVector();
			BlockVector ckTwo = area.getConfig().getVector(path + "ending-point").toBlockVector();
			
			// check x
			if (one.getBlockX() > ckOne.getBlockX()) {
				BlockVector tmp = one;
				one = ckOne;
				ckOne = tmp;
				
				tmp = two;
				two = ckTwo;
				ckTwo = tmp;
			}
			if (two.getBlockX() < ckOne.getBlockX()) {
				continue;
			}
			
			// check y
			if (one.getBlockY() > ckOne.getBlockY()) {
				BlockVector tmp = one;
				one = ckOne;
				ckOne = tmp;
				
				tmp = two;
				two = ckTwo;
				ckTwo = tmp;
			}
			if (two.getBlockY() < ckOne.getBlockY()) {
				continue;
			}
			
			// check z
			if (one.getBlockZ() > ckOne.getBlockZ()) {
				BlockVector tmp = one;
				one = ckOne;
				ckOne = tmp;
				
				tmp = two;
				two = ckTwo;
				ckTwo = tmp;
			}
			if (two.getBlockZ() < ckOne.getBlockZ()) {
				continue;
			}
			
			return true;
		}
		
		return false;
	}

}












