package me.brotherhong.fishinglife.MyObject;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.Manager.ConfigManager;
import org.bukkit.entity.Fish;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockVector;

import java.util.ArrayList;
import java.util.List;

public class FishingArea {

    private FishingLife plugin;
    private static ConfigManager area;

    private String name = null;
    private Selection selection = null;
    private List<FishingDrop> drops = null;

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

    public static FishingArea getFishingArea(String name) {
        FishingArea result = new FishingArea();
        result.setName(name);
        String path = "selected-area." + name;
        for (String n : area.getConfig().getConfigurationSection("selected-area").getKeys(false)) {
            if (n.equalsIgnoreCase(name)) {
                Selection selection = new Selection();
                selection.setBlockOne(area.getConfig().getVector(path + ".starting-point").toBlockVector());
                selection.setBlockTwo(area.getConfig().getVector(path + ".ending-point").toBlockVector());

                result.setSelection(selection);
                result.setDrops((List<FishingDrop>) area.getConfig().get(path + ".drops"));
                return result;
            }
        }
        return null;
    }

    public void save() {
        String path = "selected-area." + name;
        area.getConfig().set(path + ".starting-point", selection.getBlockOne());
        area.getConfig().set(path + ".ending-point", selection.getBlockTwo());
        area.getConfig().set(path + ".drops", drops);
        area.saveConfig();
    }

    public static boolean willConflict(FishingArea a) {
        return willConflict(a, "");
    }

    public static boolean willConflict(FishingArea a, String ban) {
        if (a == null) return false;

        BlockVector one = a.getSelection().getBlockOne();
        BlockVector two = a.getSelection().getBlockTwo();

        for (String name : area.getConfig().getConfigurationSection("selected-area").getKeys(false)) {
            if (name.equals(ban)) continue;

            BlockVector ckOne = area.getConfig().getVector("selected-area." + name + ".starting-point").toBlockVector();
            BlockVector ckTwo = area.getConfig().getVector("selected-area." + name + ".ending-point").toBlockVector();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FishingDrop> getDrops() {
        return drops;
    }

    public void setDrops(List<FishingDrop> drops) {
        this.drops = drops;
    }

    public Selection getSelection() {
        return selection;
    }

    public void setSelection(Selection selection) {
        selection.sort();
        this.selection = selection;
    }
}
