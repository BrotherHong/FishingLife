package me.brotherhong.fishinglife.MenuSystem;

import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class PaginatedMenu extends Menu {

    protected int page = 0;

    protected int maxItemPerPage = 45;

    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

}
