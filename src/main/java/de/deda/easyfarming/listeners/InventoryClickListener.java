package de.deda.easyfarming.listeners;

import de.deda.easyfarming.commands.EasyFarmingCommand;
import de.deda.easyfarming.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryClickListener implements Listener {

    private final ConfigManager config = new ConfigManager();

    private final Inventory ironWateringCanRecipeInventory = Bukkit.createInventory(null, 9*3);
    private final Inventory diamondWateringCanRecipeInventory = Bukkit.createInventory(null, 9*3);

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        try {
            if(event.getClickedInventory().equals(EasyFarmingCommand.selectRecipeInventory)) {
                event.setCancelled(true);
                if(event.getSlot() == 1)
                    player.openInventory(getIronWateringCanRecipeInventory());
                else if(event.getSlot() == 3)
                    player.openInventory(getDiamondWateringCanRecipeInventory());

            } else if(event.getClickedInventory().equals(ironWateringCanRecipeInventory)) {
                event.setCancelled(true);
                if(event.getSlot() == 26)
                    player.openInventory(EasyFarmingCommand.selectRecipeInventory);

            } else if(event.getClickedInventory().equals(diamondWateringCanRecipeInventory)) {
                event.setCancelled(true);
                if(event.getSlot() == 26)
                    player.openInventory(EasyFarmingCommand.selectRecipeInventory);
            }
        }catch (Exception e) {
        }
    }

    private Inventory getIronWateringCanRecipeInventory() {
        ItemStack black_glass_pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta black_glass_pane_meta = black_glass_pane.getItemMeta();
        black_glass_pane_meta.setDisplayName(" ");
        black_glass_pane.setItemMeta(black_glass_pane_meta);
        for(int i=0;i<ironWateringCanRecipeInventory.getSize();i++)
            ironWateringCanRecipeInventory.setItem(i, black_glass_pane);

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrier_meta = barrier.getItemMeta();
        barrier_meta.setDisplayName("§4Back");
        barrier.setItemMeta(barrier_meta);

        ItemStack air = new ItemStack(Material.AIR);
        ItemStack iron_ingot = new ItemStack(Material.IRON_INGOT);
        ItemStack bucket = new ItemStack(Material.BUCKET);

        final String iron_Watering_Can_Name = config.getString("Items.iron_watering_can.empty.displayname");
        final String[] iron_Watering_Can_Lore = config.getString("Items.iron_watering_can.empty.lore").split("%NEXT%");
        List<String> lore = new ArrayList<>();

        ItemStack iron_watering_can = new ItemStack(Material.BUCKET);
        ItemMeta iron_watering_can_meta = iron_watering_can.getItemMeta();
        iron_watering_can_meta.setDisplayName(iron_Watering_Can_Name);
        lore.clear();
        for(int i=0;i<iron_Watering_Can_Lore.length;i++)
            lore.add(iron_Watering_Can_Lore[i]);
        iron_watering_can_meta.setLore(lore);
        iron_watering_can_meta.setCustomModelData(10001);
        iron_watering_can.setItemMeta(iron_watering_can_meta);

        ironWateringCanRecipeInventory.setItem(1, iron_ingot);
        ironWateringCanRecipeInventory.setItem(10, iron_ingot);
        ironWateringCanRecipeInventory.setItem(11, bucket);
        ironWateringCanRecipeInventory.setItem(12, iron_ingot);
        ironWateringCanRecipeInventory.setItem(20, iron_ingot);
        ironWateringCanRecipeInventory.setItem(2, air);
        ironWateringCanRecipeInventory.setItem(3, air);
        ironWateringCanRecipeInventory.setItem(19, air);
        ironWateringCanRecipeInventory.setItem(21, air);
        ironWateringCanRecipeInventory.setItem(15, iron_watering_can);
        ironWateringCanRecipeInventory.setItem(26, barrier);

        return ironWateringCanRecipeInventory;
    }

    private Inventory getDiamondWateringCanRecipeInventory() {
        ItemStack black_glass_pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta black_glass_pane_meta = black_glass_pane.getItemMeta();
        black_glass_pane_meta.setDisplayName(" ");
        black_glass_pane.setItemMeta(black_glass_pane_meta);
        for(int i=0;i<diamondWateringCanRecipeInventory.getSize();i++)
            diamondWateringCanRecipeInventory.setItem(i, black_glass_pane);

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrier_meta = barrier.getItemMeta();
        barrier_meta.setDisplayName("§4Back");
        barrier.setItemMeta(barrier_meta);

        ItemStack air = new ItemStack(Material.AIR);
        ItemStack diamond = new ItemStack(Material.DIAMOND);
        ItemStack bucket = new ItemStack(Material.BUCKET);

        final String diamond_Watering_Can_Name = config.getString("Items.diamond_watering_can.empty.displayname");
        final String[] diamond_Watering_Can_Lore = config.getString("Items.diamond_watering_can.empty.lore").split("%NEXT%");
        List<String> lore = new ArrayList<>();

        ItemStack diamond_watering_can = new ItemStack(Material.BUCKET);
        ItemMeta diamond_watering_can_meta = diamond_watering_can.getItemMeta();
        diamond_watering_can_meta.setDisplayName(diamond_Watering_Can_Name);
        lore.clear();
        for(int i=0;i<diamond_Watering_Can_Lore.length;i++)
            lore.add(diamond_Watering_Can_Lore[i]);
        diamond_watering_can_meta.setLore(lore);
        diamond_watering_can_meta.setCustomModelData(10002);
        diamond_watering_can.setItemMeta(diamond_watering_can_meta);

        diamondWateringCanRecipeInventory.setItem(1, diamond);
        diamondWateringCanRecipeInventory.setItem(10, diamond);
        diamondWateringCanRecipeInventory.setItem(11, bucket);
        diamondWateringCanRecipeInventory.setItem(12, diamond);
        diamondWateringCanRecipeInventory.setItem(20, diamond);
        diamondWateringCanRecipeInventory.setItem(2, air);
        diamondWateringCanRecipeInventory.setItem(3, air);
        diamondWateringCanRecipeInventory.setItem(19, air);
        diamondWateringCanRecipeInventory.setItem(21, air);
        diamondWateringCanRecipeInventory.setItem(15, diamond_watering_can);
        diamondWateringCanRecipeInventory.setItem(26, barrier);

        return diamondWateringCanRecipeInventory;
    }
}
