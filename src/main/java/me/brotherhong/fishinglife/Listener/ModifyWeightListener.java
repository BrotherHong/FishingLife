package me.brotherhong.fishinglife.Listener;

import java.util.ArrayList;
import java.util.List;

import me.brotherhong.fishinglife.MenuSystem.Menu;
import me.brotherhong.fishinglife.MenuSystem.menus.EditDropsMenu;
import me.brotherhong.fishinglife.Msgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.MyObject.FishingDrop;

public class ModifyWeightListener implements Listener {

    private FishingLife plugin;

    public static List<Player> waiting = new ArrayList<>();

    public ModifyWeightListener() {
        this.plugin = FishingLife.getPlugin();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (waiting.contains(player)) {

            event.setCancelled(true);
            waiting.remove(player);

            String input = event.getMessage();

            if (input.matches("^[1-9]+[0-9]*$")) {

                int oldWeight = 0;
                int newWeight = Integer.parseInt(input);
                int slot = FishingLife.getPlayerMenuUtility(player).getTargetSlots();
                String areaName = FishingLife.getPlayerMenuUtility(player).getTargetAreaName();

                String path = "selected-area." + areaName + ".drops";
                List<FishingDrop> dropItems = (ArrayList<FishingDrop>) plugin.getAreaConfig().getConfig().getList(path);

                oldWeight = dropItems.get(slot).getWeight();
                dropItems.get(slot).setWeight(newWeight);

                player.sendMessage(Msgs.NEW_WEIGHT_SET);

                plugin.getAreaConfig().getConfig().set(path, dropItems);
                plugin.getAreaConfig().saveConfig();

            } else if (input.equals("-")) {
                player.sendMessage(Msgs.CANCEL_MODIFY);
            } else {
                player.sendMessage(Msgs.ONLY_INTEGER);
            }

            plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
                @Override
                public void run() {
                    new EditDropsMenu(FishingLife.getPlayerMenuUtility(player)).open();
                }
            });
        }

    }

}







