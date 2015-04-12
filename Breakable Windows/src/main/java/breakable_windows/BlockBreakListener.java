package breakable_windows;

import java.util.LinkedList;
import java.util.Random;

import org.bukkit.event.Listener;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import com.shampaggon.crackshot.events.WeaponExplodeEvent;
import com.shampaggon.crackshot.events.WeaponHitBlockEvent;

public class BlockBreakListener implements Listener {
	
	private Plugin plugin;
	//private static HashMap<Location, Material> blockList;
	private static LinkedList<BlockInfo> blockList;
	private static boolean overFlow;
	
	public BlockBreakListener(Plugin plugin) {
		this.plugin = plugin;
		//blockList = new HashMap<>();
		blockList = new LinkedList<>();
		overFlow = false;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void meleeBlockBreakEvent(BlockBreakEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
			if (isGlass(event.getBlock())) {
				chunckBreak(event.getBlock());
				if (event.getPlayer().getItemInHand() == null ||
						event.getPlayer().getItemInHand().getType() == Material.AIR)
					event.getPlayer().damage(0.5);
			}
			else if (isLight(event.getBlock())) {
				event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.GLASS, 1, 1);
				storeAndBreakBlock(event.getBlock());				
			}
		}
	}
		
	@EventHandler(priority = EventPriority.NORMAL)
	public void projectileBlockBreakEvent(WeaponHitBlockEvent event) {		
		if (isGlass(event.getBlock())) {
			chunckBreak(event.getBlock());
			event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.GLASS, 1, 1);
		}
		else if (isLight(event.getBlock())) {
			storeAndBreakBlock(event.getBlock());
			event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.GLASS, 1, 1);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void explodeBlockBreakEvent(WeaponExplodeEvent event) {
		overFlow = false;
		checkSphere(event.getLocation().getBlock());
	}
	
	public void checkSphere(final Block theBlock) {
		
		//First layer
		checkGlass(theBlock.getRelative(0, 0, 0));
		checkGlass(theBlock.getRelative(1, 0, 0));
		checkGlass(theBlock.getRelative(-1, 0, 0));
		checkGlass(theBlock.getRelative(0, 0, 1));
		checkGlass(theBlock.getRelative(0, 0, -1));
		checkGlass(theBlock.getRelative(2, 0, 0));
		checkGlass(theBlock.getRelative(-2, 0, 0));
		checkGlass(theBlock.getRelative(0, 0, 2));
		checkGlass(theBlock.getRelative(0, 0, -2));
		checkGlass(theBlock.getRelative(1, 0, 1));
		checkGlass(theBlock.getRelative(-1, 0, 1));
		checkGlass(theBlock.getRelative(1, 0, -1));
		checkGlass(theBlock.getRelative(-1, 0, -1));
		
		//Upper sphere
		checkGlass(theBlock.getRelative(0, 1, 0));
		checkGlass(theBlock.getRelative(0, 2, 0));
		checkGlass(theBlock.getRelative(1, 1, 0));
		checkGlass(theBlock.getRelative(-1, 1, 0));
		checkGlass(theBlock.getRelative(0, 1, 1));
		checkGlass(theBlock.getRelative(0, 1, -1));
		
		
		//Lower sphere
		checkGlass(theBlock.getRelative(0, -1, 0));
		checkGlass(theBlock.getRelative(0, -2, 0));
		checkGlass(theBlock.getRelative(1, -1, 0));
		checkGlass(theBlock.getRelative(-1, -1, 0));
		checkGlass(theBlock.getRelative(0, -1, 1));
		checkGlass(theBlock.getRelative(0, -1, -1));
	}
	
	public void checkGlass(final Block theBlock) {
		if (theBlock.getType() == Material.GLASS ||
				theBlock.getType() == Material.THIN_GLASS)
			recursiveBreak(theBlock);
		else if(isLight(theBlock)) {
			this.storeAndBreakBlock(theBlock);
		}
	}
	
	public void storeAndBreakBlock(final Block theBlock) {	
		//blockList.put(theBlock.getLocation(), theBlock.getType());
		blockList.add(new BlockInfo(theBlock.getLocation(), theBlock.getType(), theBlock.getData()));
		theBlock.setType(Material.AIR);				
	}
	
	public boolean isGlass(final Block glassBlock) {
		return (glassBlock.getType() == Material.GLASS
				|| glassBlock.getType() == Material.THIN_GLASS
				|| glassBlock.getType() == Material.STAINED_GLASS
				|| glassBlock.getType() == Material.STAINED_GLASS_PANE);
	}
	
	public boolean isLight(final Block lightBlock) {
		return (lightBlock.getType() == Material.GLOWSTONE
				|| lightBlock.getType() == Material.REDSTONE_LAMP_ON
				|| lightBlock.getType() == Material.REDSTONE_LAMP_OFF
				|| lightBlock.getType() == Material.BEACON
				|| lightBlock.getType() == Material.TORCH);
	}
	
	public void checkToStoreGlass(final Block glassBlock) {
		if (this.isGlass(glassBlock)) 
			this.storeAndBreakBlock(glassBlock);
	}
	
	public void checkConstBLocks(final Block glassBlock) {
		this.checkToStoreGlass(glassBlock.getRelative(1, 0, 0));
        this.checkToStoreGlass(glassBlock.getRelative(-1, 0, 0));
        this.checkToStoreGlass(glassBlock.getRelative(0, 1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(0, -1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(0, 0, 1));
        this.checkToStoreGlass(glassBlock.getRelative(0, 0, -1));
	}
	
	public void checkFormation1(final Block glassBlock) {
		this.checkToStoreGlass(glassBlock.getRelative(1, 1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(-1, -1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(0, 1, 1));
        this.checkToStoreGlass(glassBlock.getRelative(0, -1, -1));
	}
	
	public void checkFormation2(final Block glassBlock) {
		this.checkToStoreGlass(glassBlock.getRelative(1, -1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(-1, 1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(0, -1, 1));
        this.checkToStoreGlass(glassBlock.getRelative(0, 1, -1));
	}
	
	public void checkFormation3(final Block glassBlock) {
		this.checkToStoreGlass(glassBlock.getRelative(1, 1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(-1, 1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(0, 1, 1));
        this.checkToStoreGlass(glassBlock.getRelative(0, 1, -1));
	}
	
	public void checkFormation4(final Block glassBlock) {
		this.checkToStoreGlass(glassBlock.getRelative(1, -1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(-1, -1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(0, -1, 1));
        this.checkToStoreGlass(glassBlock.getRelative(0, -1, -1));
	}
	
	public void checkFormation5(final Block glassBlock) {
		this.checkToStoreGlass(glassBlock.getRelative(-1, 1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(-1, -1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(0, 1, -1));
        this.checkToStoreGlass(glassBlock.getRelative(0, -1, -1));
	}
	
	public void checkFormation6(final Block glassBlock) {
		this.checkToStoreGlass(glassBlock.getRelative(1, 1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(1, -1, 0));
        this.checkToStoreGlass(glassBlock.getRelative(0, 1, 1));
        this.checkToStoreGlass(glassBlock.getRelative(0, -1, 1));
	}
	
	public void chunckBreak(final Block glassBlock) {
		final Random rand = new Random();
		final int randNum = rand.nextInt((6-1) + 1) + 1;
		
		this.storeAndBreakBlock(glassBlock);
		
		this.checkConstBLocks(glassBlock);
			switch(randNum) {
			case 1: this.checkFormation1(glassBlock); break;
			case 2: this.checkFormation2(glassBlock); break;
			case 3: this.checkFormation4(glassBlock); break;
			case 4: this.checkFormation4(glassBlock); break;
			case 5: this.checkFormation5(glassBlock); break;
			case 6: this.checkFormation6(glassBlock); break;
			default: break;
			}
	}
	
	public void recursiveBreak(final Block glassBlock) {
		
		if (!overFlow) {
			try {
				this.storeAndBreakBlock(glassBlock);
		
				if (glassBlock.getRelative(1, 0, 0).getType() == Material.GLASS
						|| glassBlock.getRelative(1, 0, 0).getType() == Material.THIN_GLASS)
					recursiveBreak(glassBlock.getRelative(1, 0, 0));
		
				if (glassBlock.getRelative(-1, 0, 0).getType() == Material.GLASS
						|| glassBlock.getRelative(-1, 0, 0).getType() == Material.THIN_GLASS)
					recursiveBreak(glassBlock.getRelative(-1, 0, 0));
		
				if (glassBlock.getRelative(0, 1, 0).getType() == Material.GLASS
						|| glassBlock.getRelative(0, 1, 0).getType() == Material.THIN_GLASS)
					recursiveBreak(glassBlock.getRelative(0, 1, 0));
		
				if (glassBlock.getRelative(0, -1, 0).getType() == Material.GLASS
						|| glassBlock.getRelative(0, -1, 0).getType() == Material.THIN_GLASS)
					recursiveBreak(glassBlock.getRelative(0, -1, 0));
		
				if (glassBlock.getRelative(0, 0, 1).getType() == Material.GLASS
						|| glassBlock.getRelative(0, 0, 1).getType() == Material.THIN_GLASS)
					recursiveBreak(glassBlock.getRelative(0, 0, 1));
		
				if (glassBlock.getRelative(0, 0, -1).getType() == Material.GLASS
						|| glassBlock.getRelative(0, 0, -1).getType() == Material.THIN_GLASS)
					recursiveBreak(glassBlock.getRelative(0, 0, -1));
			} catch (StackOverflowError e) {
				plugin.getLogger().info("Recursive glass break caused overflow. Returning.");
				overFlow = true;
				return;
			}
		}
	}
	
	public void restoreBlocks() {
		/*
		Block tempBlock;
		for (Entry<Location, Material> entry : blockList.entrySet()) {
		
			tempBlock = entry.getKey().getBlock();
			tempBlock.setType(entry.getValue());
			//entry.getKey().setType(entry.getValue());
		}
		
		blockList.clear();
		blockList = null;
		System.gc();
		blockList = new HashMap<>();
		*/
		Block tempBlock;
		BlockInfo tempBlockInfo;
		
		/*
		for (BlockInfo blockInfo : blockList) {
			tempBlock = blockInfo.getLocation().getBlock();
			tempBlock.setType(blockInfo.getMaterial());
			tempBlock.setData(blockInfo.getData());
		}
		*/
		
		while (!blockList.isEmpty()) {
			tempBlockInfo = blockList.removeFirst();
			tempBlock = tempBlockInfo.getLocation().getBlock();
			tempBlock.setType(tempBlockInfo.getMaterial());
			tempBlock.setData(tempBlockInfo.getData());
		}
		
		blockList.clear();
		System.gc();
	}
	
	public static int getHashMapSize() {
		return blockList.size();
	}

	
}
