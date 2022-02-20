package me.brotherhong.fishinglife.Commands.subCommands;

import me.brotherhong.fishinglife.Commands.SubCommand;
import me.brotherhong.fishinglife.Msgs;
import me.brotherhong.fishinglife.MyObject.FishingArea;
import me.brotherhong.fishinglife.MyObject.Selection;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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

        if (args.length <= 2) {
            sendUsage(player);
            return;
        }

        String areaName = args[1];
        int value = Integer.parseInt(args[2]);

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
        Selection selection = fishingArea.getSelection();

        switch (face) {
            case NORTH: // -z
                selection.getBlockOne().add(new Vector(0, 0, -value));
                break;
            case SOUTH: // +z
                selection.getBlockTwo().add(new Vector(0, 0, value));
                break;
            case WEST: // -x
                selection.getBlockOne().add(new Vector(-value, 0, 0));
                break;
            case EAST: // +x
                selection.getBlockTwo().add(new Vector(value, 0, 0));
                break;
            case UP: // +y
                selection.getBlockTwo().add(new Vector(0, value, 0));
                break;
            case DOWN: // -y
                selection.getBlockOne().add(new Vector(0, -value, 0));
                break;
        }

        fishingArea.setSelection(selection);
        if (FishingArea.willConflict(fishingArea, areaName)) {
            player.sendMessage(Msgs.AREA_CONFLICT);
            return;
        }

        fishingArea.save();

        player.sendMessage(Msgs.SUCCESS_EXPAND);
    }
}
