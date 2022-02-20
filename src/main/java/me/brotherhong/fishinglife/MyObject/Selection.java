package me.brotherhong.fishinglife.MyObject;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public class Selection implements Cloneable {
	private BlockVector blockOne = null;
	private BlockVector blockTwo = null;
	
	public void sort() {
		if (blockOne == null || blockTwo == null) return;
		BlockVector newOne = new BlockVector();
		BlockVector newTwo = new BlockVector();
		
		newOne.setX(Math.min(blockOne.getBlockX(), blockTwo.getBlockX()));
		newOne.setY(Math.min(blockOne.getBlockY(), blockTwo.getBlockY()));
		newOne.setZ(Math.min(blockOne.getBlockZ(), blockTwo.getBlockZ()));
		
		newTwo.setX(Math.max(blockOne.getBlockX(), blockTwo.getBlockX()));
		newTwo.setY(Math.max(blockOne.getBlockY(), blockTwo.getBlockY()));
		newTwo.setZ(Math.max(blockOne.getBlockZ(), blockTwo.getBlockZ()));
		
		this.blockOne = newOne;
		this.blockTwo = newTwo;
	}

	public boolean isReady() {
		return (blockOne != null && blockTwo != null);
	}

	public BlockVector getBlockOne() {
		return blockOne;
	}

	public void setBlockOne(BlockVector blockOne) {
		this.blockOne = blockOne;
	}

	public BlockVector getBlockTwo() {
		return blockTwo;
	}

	public void setBlockTwo(BlockVector blockTwo) {
		this.blockTwo = blockTwo;
	}

	public void playParticles(World world) {
		Selection sorted = this.clone();
		sorted.sort();
		// spawn particles
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

	@Override
	public Selection clone() {
		try {
			return (Selection) super.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
