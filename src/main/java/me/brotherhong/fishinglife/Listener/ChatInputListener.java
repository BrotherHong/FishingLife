package me.brotherhong.fishinglife.Listener;

import java.util.ArrayList;
import java.util.List;

import me.brotherhong.fishinglife.MenuSystem.PlayerMenuUtility;
import me.brotherhong.fishinglife.MenuSystem.menus.EditDropsMenu;
import me.brotherhong.fishinglife.Msgs;
import me.brotherhong.fishinglife.MyObject.FishingArea;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.MyObject.FishingDrop;

public class ChatInputListener implements Listener {

    private FishingLife plugin;

    public static List<Player> waiting_chance = new ArrayList<>();
    public static List<Player> waiting_amount = new ArrayList<>();

    public ChatInputListener() {
        this.plugin = FishingLife.getPlugin();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        PlayerMenuUtility playerMenuUtility = FishingLife.getPlayerMenuUtility(player);
        int slot = playerMenuUtility.getTargetSlots();
        String areaName = playerMenuUtility.getTargetAreaName();
        FishingArea fishingArea = FishingArea.getFishingArea(areaName);
        List<FishingDrop> dropItems = fishingArea.getDrops();

        if (waiting_chance.contains(player)) {

            event.setCancelled(true);
            waiting_chance.remove(player);

            String input = event.getMessage();

            if (input.matches(FishingLife.DOUBLE_POSITIVE)) {

                double newChance = Double.parseDouble(input);

                dropItems.get(slot).setChance(newChance);

                player.sendMessage(Msgs.SUCCESS_MODIFY);

                fishingArea.setDrops(dropItems);
                fishingArea.save();

            } else if (input.equals("-")) {
                player.sendMessage(Msgs.CANCEL_MODIFY);
            } else {
                player.sendMessage(Msgs.ONLY_FLOAT);
            }
            reopen(player);
        } else if (waiting_amount.contains(player)) {
            event.setCancelled(true);
            waiting_amount.remove(player);

            String input = event.getMessage();
            if (input.matches(FishingLife.INTEGER_POSITIVE)) {
                int newAmount = Integer.parseInt(input);
                Material type = dropItems.get(slot).getItem().getType();

                if (newAmount > type.getMaxStackSize()) {
                    player.sendMessage(Msgs.STACK_LIMIT);
                    return;
                }

                dropItems.get(slot).getItem().setAmount(newAmount);
                fishingArea.setDrops(dropItems);
                fishingArea.save();

                player.sendMessage(Msgs.SUCCESS_MODIFY);
            } else if (input.equals("-")) {
                player.sendMessage(Msgs.CANCEL_MODIFY);
            } else {
                player.sendMessage(Msgs.ONLY_INTEGER);
            }
            reopen(player);
        }
    }

    private void reopen(Player player) {
        plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {
                new EditDropsMenu(FishingLife.getPlayerMenuUtility(player)).open();
            }
        });
    }

}







