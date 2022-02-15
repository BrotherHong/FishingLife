package me.brotherhong.fishinglife.MyObject;

import org.bukkit.util.BlockVector;

public class Selection {
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
	
	
	
}
