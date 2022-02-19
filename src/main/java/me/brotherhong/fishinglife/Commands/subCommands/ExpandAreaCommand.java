package me.brotherhong.fishinglife.Commands.subCommands;

import me.brotherhong.fishinglife.Commands.SubCommand;
import me.brotherhong.fishinglife.Msgs;
import me.brotherhong.fishinglife.MyObject.FishingArea;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
// not use
public class ExpandAreaCommand extends SubCommand {

    @Override
    public String getName() {
        return "expand";
    }

    @Override
    public String getDescription() {
        return "Expand your fishing area you facing.";
    }

    @Override
    public String getSyntax() {
        return "/fl expand <name>";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length == 1) {
            sendUsage(player);
            return;
        }

        String areaName = args[1];

        if (!isNameExist(areaName)) {
            sendAreaNotFound(player);
            return;
        }

        BlockFace face = player.getFacing();
        if (!face.isCartesian()) {
            player.sendMessage(Msgs.ONE_DIRECTION);
            return;
        }

        FishingArea fishingArea = FishingArea.getFishingArea(areaName);

        switch (face) {
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
            case UP:
            case DOWN:
        }

        player.sendMessage(Msgs.SUCCESS_EXPAND);
    }
}
