package de.deda.easyfarming.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class BlockBreackListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if(itemInHand.getType() != Material.WOODEN_HOE || itemInHand.getType() != Material.STONE_HOE || itemInHand.getType() != Material.GOLDEN_HOE ||
                itemInHand.getType() != Material.IRON_HOE || itemInHand.getType() != Material.DIAMOND_HOE || itemInHand.getType() != Material.NETHERITE_HOE) return;

        switch(block.getType()) {
            case OAK_LEAVES:
                dropItem(block.getLocation(), 2, Material.OAK_SAPLING);
                dropItem(block.getLocation(), 10, Material.APPLE);
                break;
            case SPRUCE_LEAVES:
                dropItem(block.getLocation(), 2, Material.SPRUCE_SAPLING);
                break;
            case BIRCH_LEAVES:
                dropItem(block.getLocation(), 2, Material.BIRCH_SAPLING);
                break;
            case JUNGLE_LEAVES:
                dropItem(block.getLocation(), 2, Material.JUNGLE_SAPLING);
                break;
            case ACACIA_LEAVES:
                dropItem(block.getLocation(), 2, Material.ACACIA_SAPLING);
                break;
            case DARK_OAK_LEAVES:
                dropItem(block.getLocation(), 2, Material.DARK_OAK_SAPLING);
                break;
        }

    }

    private boolean randomGenerator(int chance) {
        Random random = new Random();
        if(random.nextInt(chance) == 0) return true;
        return false;
    }

    private void dropItem(Location location, int chance, Material material) {
        if(!randomGenerator(chance)) return;
        ItemStack item = new ItemStack(material);
        location.getWorld().dropItemNaturally(location, item);
    }

}
