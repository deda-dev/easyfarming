package de.deda.easyfarming.commands;

import de.deda.easyfarming.Easyfarming;
import de.deda.easyfarming.Easyfarming;
import de.deda.easyfarming.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EasyFarmingCommand implements CommandExecutor, TabCompleter {

    private final ConfigManager config = new ConfigManager();
    private final String noPerm = config.getPrefixString("NoPermission");
    private final String wrongUsage = config.getPrefixString("WrongCommandUsage");

    public static final Inventory selectRecipeInventory = Bukkit.createInventory(null, InventoryType.HOPPER);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(args.length != 1) { player.sendMessage(wrongUsage); return false; }

        if(args[0].equalsIgnoreCase("recipe")) {
            if(!player.hasPermission("easyfarming.*") || !player.hasPermission("easyfarming.recipe")) { player.sendMessage(noPerm); return false; }
            player.openInventory(getSelectRecipeInventory());
            return true;

        } else if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
            if(!player.hasPermission("easyfarming.*") || !player.hasPermission("easyfarming.reload") || !player.hasPermission("easyfarming.rl")) { player.sendMessage(noPerm); return false; }
            Easyfarming.getPlugin().reloadConfig();
            player.sendMessage(config.getPrefixString("ConfigReloaded"));
            return true;
        }
        player.sendMessage(wrongUsage);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> arguments = new ArrayList<>();
        if(args.length != 1) return null;
        arguments.add("recipe");
        arguments.add("reload");
        arguments.add("rl");
        return arguments;
    }

    private Inventory getSelectRecipeInventory() {
        ItemStack black_glass_pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta black_glass_pane_meta = black_glass_pane.getItemMeta();
        black_glass_pane_meta.setDisplayName(" ");
        black_glass_pane.setItemMeta(black_glass_pane_meta);
        for(int i=0;i<selectRecipeInventory.getSize();i++)
            selectRecipeInventory.setItem(i, black_glass_pane);

        final String iron_Watering_Can_Name = config.getString("Items.iron_watering_can.empty.displayname");
        final String[] iron_Watering_Can_Lore = config.getString("Items.iron_watering_can.empty.lore").split("%NEXT%");
        final String diamond_Watering_Can_Name = config.getString("Items.diamond_watering_can.empty.displayname");
        final String[] diamond_Watering_Can_Lore = config.getString("Items.diamond_watering_can.empty.lore").split("%NEXT%");
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

        ItemStack diamond_watering_can = new ItemStack(Material.BUCKET);
        ItemMeta diamond_watering_can_meta = diamond_watering_can.getItemMeta();
        diamond_watering_can_meta.setDisplayName(diamond_Watering_Can_Name);
        lore.clear();
        for(int i=0;i<diamond_Watering_Can_Lore.length;i++)
            lore.add(diamond_Watering_Can_Lore[i]);
        diamond_watering_can_meta.setLore(lore);
        diamond_watering_can_meta.setCustomModelData(10002);
        diamond_watering_can.setItemMeta(diamond_watering_can_meta);

        selectRecipeInventory.setItem(1, iron_watering_can);
        selectRecipeInventory.setItem(3, diamond_watering_can);

        return selectRecipeInventory;
    }
}
