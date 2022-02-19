package me.brotherhong.fishinglife.Commands.subCommands;

import me.brotherhong.fishinglife.Msgs;
import me.brotherhong.fishinglife.MyObject.FishingArea;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Commands.SubCommand;
import me.brotherhong.fishinglife.MyObject.Selection;

public class CreateFishingAreaCommand extends SubCommand {

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
		FishingArea fishingArea = new FishingArea();
		fishingArea.setSelection(FishingLife.getPlayerSelection(player));
		
		if (!fishingArea.getSelection().isReady()) {
			player.sendMessage(Msgs.NO_SELECT);
			return;
		}

		if (FishingArea.willConflict(fishingArea)) {
			player.sendMessage(Msgs.AREA_CONFLICT);
			return;
		}
		
		if (isNameExist(areaName)) {
			player.sendMessage(Msgs.SAME_NAME);
			return;
		}

		fishingArea.write(areaName);

		// successful message
		player.sendMessage(Msgs.SUCCESS_CREATE.replaceAll("%area_name%", areaName));
	}

}












