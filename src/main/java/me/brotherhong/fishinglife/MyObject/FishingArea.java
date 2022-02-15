package me.brotherhong.fishinglife.MyObject;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Manager.ConfigManager;
import org.bukkit.entity.Fish;
import org.bukkit.util.BlockVector;

public class FishingArea {

    private FishingLife plugin;
    private ConfigManager area;

    private Selection selection = null;

    public FishingArea() {
        this.plugin = FishingLife.getPlugin();
        area = plugin.getAreaConfig();
    }

    // return true if successful
    public boolean write(String areaName) {
        String path = "selected-area." + areaName;
        area.getConfig().set(path + ".starting-point", selection.getBlockOne());
        area.getConfig().set(path + ".ending-point", selection.getBlockTwo());
        area.saveConfig();
        return true;
    }

    public static boolean isConflict(FishingArea area1, FishingArea area2) {
        if (area1 == null || area2 == null) return false;

        BlockVector one = area1.getSelection().getBlockOne();
        BlockVector two = area1.getSelection().getBlockTwo();

        BlockVector ckOne = area2.getSelection().getBlockOne();
        BlockVector ckTwo = area2.getSelection().getBlockTwo();

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
            return false;
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
            return false;
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
            return false;
        }

        return true;
    }

    public Selection getSelection() {
        return selection;
    }

    public void setSelection(Selection selection) {
        selection.sort();
        this.selection = selection;
    }
}
