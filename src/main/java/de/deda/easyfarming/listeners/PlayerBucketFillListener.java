package de.deda.easyfarming.listeners;

import de.deda.easyfarming.Easyfarming;
import de.deda.easyfarming.utils.ConfigManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class PlayerBucketFillListener implements Listener {

    private final ConfigManager config = new ConfigManager();
    private final String iron_Watering_Can_Name = config.getString("Items.iron_watering_can.filled.displayname");
    private final String diamond_Watering_Can_Name = config.getString("Items.diamond_watering_can.filled.displayname");
    private final String[] iron_Watering_Can_Lore = config.getString("Items.iron_watering_can.filled.lore").split("%NEXT%");
    private final String[] diamond_Watering_Can_Lore = config.getString("Items.diamond_watering_can.filled.lore").split("%NEXT%");

    //
    // fill the glass_bottle (watering can)
    //
    @EventHandler
    public void onFillWateringCanBucket(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        List<String> lore = new ArrayList<>();

        if(itemInHand.getType() == Material.BUCKET) {
            if(!itemInHand.getItemMeta().hasCustomModelData()) return;
            if(itemInHand.getItemMeta().getCustomModelData() == 10001) {
                event.setCancelled(true);

                if(itemInHand.getAmount() == 1) {
                    itemInHand.setType(Material.IRON_HORSE_ARMOR);
                    ItemMeta meta = itemInHand.getItemMeta();
                    meta.setDisplayName(iron_Watering_Can_Name);
                    lore.clear();
                    for(int i=0;i<iron_Watering_Can_Lore.length;i++)
                        lore.add(iron_Watering_Can_Lore[i]);
                    meta.setCustomModelData(10011);
                    itemInHand.setItemMeta(meta);
                    return;
                }
                itemInHand.setAmount(itemInHand.getAmount() - 1);
                ItemStack filledWateringCan = new ItemStack(Material.IRON_HORSE_ARMOR);
                ItemMeta meta = filledWateringCan.getItemMeta();
                meta.setDisplayName(iron_Watering_Can_Name);
                lore.clear();
                for(int i=0;i<iron_Watering_Can_Lore.length;i++)
                    lore.add(iron_Watering_Can_Lore[i]);
                meta.setCustomModelData(10011);
                filledWateringCan.setItemMeta(meta);

                player.getInventory().addItem(filledWateringCan);

            } else if(itemInHand.getItemMeta().getCustomModelData() == 10002) {
                event.setCancelled(true);

                if(itemInHand.getAmount() == 1) {
                    itemInHand.setType(Material.IRON_HORSE_ARMOR);
                    ItemMeta meta = itemInHand.getItemMeta();
                    meta.setDisplayName(diamond_Watering_Can_Name);
                    lore.clear();
                    for(int i=0;i<diamond_Watering_Can_Lore.length;i++)
                        lore.add(diamond_Watering_Can_Lore[i]);
                    meta.setCustomModelData(10012);
                    meta.getPersistentDataContainer().set(new NamespacedKey(Easyfarming.getPlugin(), "uses"), PersistentDataType.INTEGER, 3);
                    itemInHand.setItemMeta(meta);
                    return;
                }
                itemInHand.setAmount(itemInHand.getAmount() - 1);
                ItemStack filledWateringCan = new ItemStack(Material.IRON_HORSE_ARMOR);
                ItemMeta meta = filledWateringCan.getItemMeta();
                meta.setDisplayName(diamond_Watering_Can_Name);
                lore.clear();
                for(int i=0;i<diamond_Watering_Can_Lore.length;i++)
                    lore.add(diamond_Watering_Can_Lore[i]);
                meta.setCustomModelData(10012);
                meta.getPersistentDataContainer().set(new NamespacedKey(Easyfarming.getPlugin(), "uses"), PersistentDataType.INTEGER, 3);
                filledWateringCan.setItemMeta(meta);

                player.getInventory().addItem(filledWateringCan);
            }



        }
    }
}
