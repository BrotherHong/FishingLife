package me.brotherhong.fishinglife.Commands.subCommands;

import me.brotherhong.fishinglife.Commands.SubCommand;
import me.brotherhong.fishinglife.MyObject.FishingArea;
import org.bukkit.entity.Player;

public class BoardDisplayCommand extends SubCommand {

    @Override
    public String getName() {
        return "board";
    }

    @Override
    public String getDescription() {
        return "Show the board of fishing area.";
    }

    @Override
    public String getSyntax() {
        return "/fl board <name>";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length < 2) {
            sendUsage(player);
            return;
        }

        if (!isNameExist(args[1])) {
            sendAreaNotFound(player);
            return;
        }

        FishingArea.getFishingArea(args[1]).getSelection().playParticles(player.getWorld());

    }
}
