package me.brotherhong.fishinglife.MenuSystem;

import me.brotherhong.fishinglife.FishingLife;
import org.bukkit.Bukkit;

public abstract class ChestMenu extends Menu {

    public ChestMenu(FishingLife plugin, PlayerMenuUtility playerMenuUtility) {
        super(plugin, playerMenuUtility);
    }

    public void open() {

        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

        this.setMenuItems();

        playerMenuUtility.getOwner().openInventory(inventory);
    }

}
