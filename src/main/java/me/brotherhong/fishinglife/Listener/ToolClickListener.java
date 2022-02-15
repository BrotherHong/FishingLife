package me.brotherhong.fishinglife.Listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.MyObject.Selection;

public class ToolClickListener implements Listener {
	
	private FishingLife plugin;
	private Material tool;
	
	public ToolClickListener(FishingLife plugin) {
		this.plugin = plugin;
		this.tool = Material.getMaterial(this.plugin.getConfigConfig().getConfig().getString("selection-tool"));
	}
	
	@EventHandler
	public void onToolClick(PlayerInteractEvent event) {
		if (event.getItem() == null) return;
		if (!event.getPlayer().hasPermission("fishinglife.op")) return;
		if (event.getItem().getType() != this.tool) return;
		event.setCancelled(true);
		
		Player player = event.getPlayer();
		Selection sel = FishingLife.getPlayerSelection(player);
		
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			sel.setBlockOne(event.getClickedBlock().getLocation().toVector().toBlockVector());
			player.sendMessage(FishingLife.getPrefix() + ChatColor.AQUA + "First position has set to (" + sel.getBlockOne().toString() + ")");
		} else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			sel.setBlockTwo(event.getClickedBlock().getLocation().toVector().toBlockVector());
			player.sendMessage(FishingLife.getPrefix() + ChatColor.AQUA + "Second position has set to (" + sel.getBlockTwo().toString() + ")");
		}
	}
	
}
