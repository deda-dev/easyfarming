package de.deda.easyfarming.listeners;

import de.deda.easyfarming.Easyfarming;
import de.deda.easyfarming.utils.ConfigManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class PlayerInteractListener implements Listener {

    private final ConfigManager config = new ConfigManager();
    private final String iron_Watering_Can_Name = config.getString("Items.iron_watering_can.empty.displayname");
    private final String diamond_Watering_Can_Name = config.getString("Items.diamond_watering_can.empty.displayname");
    private final String[] iron_Watering_Can_Lore = config.getString("Items.iron_watering_can.empty.lore").split("%NEXT%");
    private final String[] diamond_Watering_Can_Lore = config.getString("Items.diamond_watering_can.empty.lore").split("%NEXT%");

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if(event.getClickedBlock() == null) return;
        Block block = event.getClickedBlock();
        BlockData data = block.getBlockData();
        World world = block.getWorld();
        Location location = block.getLocation();

        //
        // Maybe wenn Ageable Probleme macht
        // https://www.tabnine.com/code/java/methods/org.bukkit.block.data.Ageable/getAge
        //

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            //
            // interaction with hoe
            //
            if(itemInHand.getType() == Material.WOODEN_HOE ||itemInHand.getType() == Material.STONE_HOE || itemInHand.getType() == Material.GOLDEN_HOE ||
                    itemInHand.getType() == Material.IRON_HOE || itemInHand.getType() == Material.DIAMOND_HOE || itemInHand.getType() == Material.NETHERITE_HOE) {
                switch (block.getType()) {

                    //
                    // Maybe Chorus Pflanze hinzufügen
                    //

                    case WHEAT:
                    case CARROTS:
                    case POTATOES:
                    case BEETROOTS:
                    case COCOA:
                    case NETHER_WART:
                        if(!(data instanceof Ageable)) return;
                        Ageable ageable = (Ageable) data;
                        if(ageable.getAge() != ageable.getMaximumAge()) return;

                        block.breakNaturally();
                        world.spawnParticle(Particle.VILLAGER_HAPPY, location.add(0.5,0,0.5), 30, 0.5, 0.5, 0.5,0);
                        ageable.setAge(0);
                        removeDurability(itemInHand);
                        block.setBlockData(ageable);
                        break;
                    case MELON:
                    case PUMPKIN:
                        block.breakNaturally();
                        world.spawnParticle(Particle.VILLAGER_HAPPY, location.add(0.5,0, 0.5), 30, 0.5, 0.5, 0.5,0);
                        removeDurability(itemInHand);
                        break;
                    case SUGAR_CANE:
                    case CACTUS:
                    case BAMBOO:
                    case KELP_PLANT:
                        if(location.subtract(0,1,0).getBlock().getType() == Material.SUGAR_CANE || location.getBlock().getType() == Material.CACTUS ||
                                location.getBlock().getType() == Material.BAMBOO || location.getBlock().getType() == Material.KELP_PLANT || location.getBlock().getType() == Material.CHORUS_PLANT ||
                                location.getBlock().getType() == Material.TWISTING_VINES_PLANT) {
                            block.breakNaturally();
                            world.spawnParticle(Particle.VILLAGER_HAPPY, location.add(0.5,1, 0.5), 30, 0.5, 0.5, 0.5,0);
                            removeDurability(itemInHand);
                            break;
                        }
                        if(location.add(0,2,0).getBlock().getType() != Material.SUGAR_CANE && location.getBlock().getType() != Material.CACTUS &&
                                location.getBlock().getType() != Material.BAMBOO && location.getBlock().getType() != Material.KELP_PLANT && location.getBlock().getType() != Material.CHORUS_PLANT &&
                                location.getBlock().getType() != Material.TWISTING_VINES_PLANT) break;
                        location.getBlock().breakNaturally();
                        world.spawnParticle(Particle.VILLAGER_HAPPY, location.add(0.5,0, 0.5), 30, 0.5, 0.5, 0.5,0);
                        removeDurability(itemInHand);
                        break;
                }
                return;
            }
            //
            // Interact with Iron horse armor (filled watering can)
            //
            if(itemInHand.getType() == Material.IRON_HORSE_ARMOR) {
                if(!itemInHand.getItemMeta().hasCustomModelData()) return;
                List<String> lore = new ArrayList<>();
                if(itemInHand.getItemMeta().getCustomModelData() == 10011) {
                    event.setCancelled(true);
                    player.playSound(player.getLocation(), Sound.ITEM_BUCKET_EMPTY, 1, 1);

                    for(int i=0;i<getBlocksAroundCenter(location,1).size();i++) {
                        Block blocksAround = getBlocksAroundCenter(location,1).get(i);
                        growthCrop(blocksAround.getLocation().add(0.5,0,0.5));
                        blocksAround.getWorld().spawnParticle(Particle.WATER_WAKE, blocksAround.getLocation().add(0.5,0.5,0.5), 25, 0.5f, 0.5f, 0.5f, 0);
                    }

                    itemInHand.setType(Material.BUCKET);
                    ItemMeta meta = itemInHand.getItemMeta();
                    meta.setDisplayName(iron_Watering_Can_Name);
                    lore.clear();
                    for(int i=0;i<iron_Watering_Can_Lore.length;i++)
                        lore.add(iron_Watering_Can_Lore[i]);
                    meta.setLore(lore);
                    meta.setCustomModelData(10001);
                    itemInHand.setItemMeta(meta);
                }
                if(itemInHand.getItemMeta().getCustomModelData() == 10012) {
                    if(event.getHand() != EquipmentSlot.HAND) return;

                    PersistentDataContainer persistentDataContainer = itemInHand.getItemMeta().getPersistentDataContainer();
                    NamespacedKey key = new NamespacedKey(Easyfarming.getPlugin(), "uses");
                    if(!persistentDataContainer.has(key, PersistentDataType.INTEGER)) return;
                    event.setCancelled(true);

                    player.playSound(player.getLocation(), Sound.ITEM_BUCKET_EMPTY, 1, 1);
                    for(int i=0;i<getBlocksAroundCenter(location,2).size();i++) {
                        Block blocksAround = getBlocksAroundCenter(location,2).get(i);
                        growthCrop(blocksAround.getLocation().add(0.5,0,0.5));

                        if(get2DBlocksAroundCenter(location, 2).size() <= i) continue;
                        Block particleLocations = get2DBlocksAroundCenter(location, 2).get(i);
                        particleLocations.getWorld().spawnParticle(Particle.WATER_WAKE, particleLocations.getLocation().add(0.5,0.5,0.5), 25, 0.5, 0.5, 0.5, 0);
                    }

                    if(persistentDataContainer.get(key, PersistentDataType.INTEGER) == 3) {
                        ItemMeta meta = itemInHand.getItemMeta();
                        meta.getPersistentDataContainer().set(key,PersistentDataType.INTEGER, 2);
                        itemInHand.setItemMeta(meta);
                        return;

                    } else if(persistentDataContainer.get(key, PersistentDataType.INTEGER) == 2) {
                        ItemMeta meta = itemInHand.getItemMeta();
                        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
                        itemInHand.setItemMeta(meta);
                        return;
                    }

                    itemInHand.setType(Material.BUCKET);
                    ItemMeta meta = itemInHand.getItemMeta();
                    meta.setDisplayName(diamond_Watering_Can_Name);
                    lore.clear();
                    for(int i=0;i<diamond_Watering_Can_Lore.length;i++)
                        lore.add(diamond_Watering_Can_Lore[i]);
                    meta.setLore(lore);
                    meta.setCustomModelData(10002);
                    persistentDataContainer.remove(new NamespacedKey(Easyfarming.getPlugin(), "uses"));
                    itemInHand.setItemMeta(meta);
                }


                return;
            }



        }
        if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(itemInHand.getType() == Material.WOODEN_HOE || itemInHand.getType() == Material.STONE_HOE || itemInHand.getType() == Material.GOLDEN_HOE ||
                    itemInHand.getType() == Material.IRON_HOE || itemInHand.getType() == Material.DIAMOND_HOE || itemInHand.getType() == Material.NETHERITE_HOE) {
                if(block.getType() != Material.FARMLAND) return;
                block.setType(Material.DIRT);
                return;
            }



            return;
        }
        if(event.getAction() == Action.PHYSICAL) {
            if(block.getType() != Material.FARMLAND) return;
            event.setCancelled(true);
            return;
        }

    }

    private void removeDurability(ItemStack item) {
        ConfigManager config = new ConfigManager();
        ItemMeta meta = item.getItemMeta();
        if(!config.getBoolean("UseDurability")) return;
        if(!(meta instanceof Damageable)) return;
        Damageable damageable = (Damageable) meta;

        if(damageable.getDamage() != item.getType().getMaxDurability()) {
            damageable.setDamage(damageable.getDamage() +1);
            item.setItemMeta(meta);
            return;
        }
        item.setAmount(0);
    }

    private void growthCrop(Location location) {
        Block block = location.getBlock();
        BlockData data = block.getBlockData();

        setGrass(location);
        moistureFarmland(location);

        if(data instanceof Sapling) {
            Sapling sapling = (Sapling) data;
            if(sapling.getStage() == sapling.getMaximumStage()) {
                spawnTree(location);
                return;
            }
            sapling.setStage(sapling.getStage()+1);
            block.setBlockData(sapling);
        }

        if(data instanceof Ageable) {
            Ageable ageable = (Ageable) data;
            if(ageable.getAge() != ageable.getMaximumAge()) ageable.setAge(ageable.getAge() + 1);
            block.setBlockData(ageable);
        }
    }

    private void moistureFarmland(Location location) {
        Block block = location.getBlock();
        BlockData data = block.getBlockData();
        if(data.getMaterial() != Material.FARMLAND) return;
        Farmland farmland = (Farmland) data;

        if(farmland.getMoisture() == farmland.getMaximumMoisture()) return;
        farmland.setMoisture(farmland.getMaximumMoisture());
        block.setBlockData(farmland);
    }

    private void setGrass(Location location) {
        Block block = location.getBlock();
        if(block.getType() != Material.DIRT) return;
        block.setType(Material.GRASS_BLOCK);
    }

    private ArrayList<Block> get2DBlocksAroundCenter(Location location, int radius) {
        ArrayList<Block> blocks = new ArrayList<Block>();

        for(int x = (location.getBlockX()-radius); x <= (location.getBlockX()+radius); x++)
            for(int z = (location.getBlockZ()-radius); z <= (location.getBlockZ()+radius); z++) {
                Location loc = new Location(location.getWorld(), x, location.getY(), z);
                if(blocks.contains(loc.getBlock())) continue;
                blocks.add(loc.getBlock());

                Location locy1 = new Location(location.getWorld(), x, location.getY()+1, z);
                if(blocks.contains(locy1.getBlock())) continue;
                blocks.add(locy1.getBlock());
            }
        return blocks;
    }

    private ArrayList<Block> getBlocksAroundCenter(Location location, int radius) {
        ArrayList<Block> blocks = new ArrayList<Block>();

        for(int x = (location.getBlockX()-radius); x <= (location.getBlockX()+radius); x++)
            for(int y = (location.getBlockY()-radius); y <= (location.getBlockY()+radius); y++)
                for(int z = (location.getBlockZ()-radius); z <= (location.getBlockZ()+radius); z++) {
                    Location loc = new Location(location.getWorld(), x, y, z);

                    if(blocks.contains(loc.getBlock())) continue;
                    blocks.add(loc.getBlock());
                }
        return blocks;
    }

    private void spawnTree(Location location) {
        Material material = location.getBlock().getType();
        switch(material) {
            case OAK_SAPLING:
                location.getWorld().generateTree(location, TreeType.TREE);
                break;
            case SPRUCE_SAPLING:
                location.getWorld().generateTree(location, TreeType.REDWOOD);
                break;
            case BIRCH_SAPLING:
                location.getWorld().generateTree(location, TreeType.BIRCH);
                break;
            case JUNGLE_SAPLING:
                location.getWorld().generateTree(location, TreeType.SMALL_JUNGLE);
                break;
            case ACACIA_SAPLING:
                location.getWorld().generateTree(location, TreeType.ACACIA);
                break;
            case DARK_OAK_SAPLING:
                location.getWorld().generateTree(location, TreeType.DARK_OAK);
                break;




        }

    }

}
