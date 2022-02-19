package me.brotherhong.fishinglife.Listener;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.brotherhong.fishinglife.FishingLife;
import me.brotherhong.fishinglife.MyObject.Selection;
import org.bukkit.util.Vector;

public class ToolClickListener implements Listener {
	
	private FishingLife plugin;
	private Material tool;
	
	public ToolClickListener() {
		this.plugin = FishingLife.getPlugin();
		this.tool = Material.getMaterial(plugin.getConfigConfig().getConfig().getString("selection-tool"));
	}
	
	@EventHandler
	public void onToolClick(PlayerInteractEvent event) {
		if (event.getItem() == null) return;
		if (event.getClickedBlock() == null) return;
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

		if (sel.isReady()) {

			Selection sorted = sel.clone();
			sorted.sort();
			// spawn particles
			World world = player.getWorld();
			Location start = sorted.getBlockOne().toLocation(world);
			Location end = sorted.getBlockTwo().toLocation(world);

			Vector vector = end.subtract(start).toVector().add(new Vector(1.0, 1.0, 1.0));
			int count = 10;
			double space = 0.5;
			Particle particle = Particle.VILLAGER_HAPPY;

			// x
			for (double x = 0;x <= vector.getX();x += space) {
				world.spawnParticle(particle, start.clone().add(new Vector(x, 0.0, 0.0)), count);
				world.spawnParticle(particle, start.clone().add(new Vector(x, vector.getY(), 0.0)), count);
				world.spawnParticle(particle, start.clone().add(new Vector(x, 0.0, vector.getZ())), count);
				world.spawnParticle(particle, start.clone().add(new Vector(x, vector.getY(), vector.getZ())), count);
			}
			// y
			for (double y = 0;y <= vector.getY();y += space) {
				world.spawnParticle(particle, start.clone().add(new Vector(0.0, y, 0.0)), count);
				world.spawnParticle(particle, start.clone().add(new Vector(vector.getX(), y, 0.0)), count);
				world.spawnParticle(particle, start.clone().add(new Vector(0.0, y, vector.getZ())), count);
				world.spawnParticle(particle, start.clone().add(new Vector(vector.getX(), y, vector.getZ())), count);
			}
			// z
			for (double z = 0;z <= vector.getZ();z += space) {
				world.spawnParticle(particle, start.clone().add(new Vector(0.0, 0.0, z)), count);
				world.spawnParticle(particle, start.clone().add(new Vector(vector.getX(), 0.0, z)), count);
				world.spawnParticle(particle, start.clone().add(new Vector(0.0, vector.getY(), z)), count);
				world.spawnParticle(particle, start.clone().add(new Vector(vector.getX(), vector.getY(), z)), count);
			}

		}
	}
	
}
