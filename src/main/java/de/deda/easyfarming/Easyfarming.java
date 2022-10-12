package de.deda.easyfarming;

import de.deda.easyfarming.commands.EasyFarmingCommand;
import de.deda.easyfarming.listeners.*;
import de.deda.easyfarming.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Easyfarming extends JavaPlugin {

    private static Easyfarming plugin;
    private final ConfigManager config = new ConfigManager();

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        addWateringCanRecipe();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerBucketFillListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new BlockBreackListener(), this);
        pm.registerEvents(new MoistureChangeListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);

        getCommand("easyfarming").setExecutor(new EasyFarmingCommand());
        getCommand("easyfarming").setTabCompleter(new EasyFarmingCommand());
    }

    @Override
    public void onDisable() {

    }

    private void addWateringCanRecipe() {
        final String iron_Watering_Can_Name = config.getString("Items.iron_watering_can.empty.displayname");
        final String diamond_Watering_Can_Name = config.getString("Items.diamond_watering_can.empty.displayname");
        final String[] iron_Watering_Can_Lore = config.getString("Items.iron_watering_can.empty.lore").split("%NEXT%");
        final String[] diamond_Watering_Can_Lore = config.getString("Items.diamond_watering_can.empty.lore").split("%NEXT%");

        final String[] iron_watering_can_recipe = config.getString("Recipe.iron_watering_can.shape").substring(1, 14).split(", ");
        final String[] diamond_watering_can_recipe = config.getString("Recipe.diamond_watering_can.shape").substring(1, 14).split(", ");

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

        NamespacedKey iron_watering_can_key = new NamespacedKey(this, "iron_watering_can_key");
        ShapedRecipe iron_watering_can_shaped_recipe = new ShapedRecipe(iron_watering_can_key, iron_watering_can);
        iron_watering_can_shaped_recipe.shape(iron_watering_can_recipe[0],iron_watering_can_recipe[1],iron_watering_can_recipe[2]);

        List<Character> iron_watering_can_recipe_ingredients = getIngredientsKey(iron_watering_can_recipe);
        for(int i=0;i<iron_watering_can_recipe_ingredients.size();i++) {
            String iron_watering_can_recipe_ingredients_material = config.getString("Recipe.iron_watering_can.ingredients."+iron_watering_can_recipe_ingredients.get(i));
            iron_watering_can_shaped_recipe.setIngredient(iron_watering_can_recipe_ingredients.get(i), Material.matchMaterial(iron_watering_can_recipe_ingredients_material));
        }

        NamespacedKey diamond_watering_can_key = new NamespacedKey(this, "diamond_watering_can_key");
        ShapedRecipe diamond_watering_can_shaped_recipe = new ShapedRecipe(diamond_watering_can_key, diamond_watering_can);
        diamond_watering_can_shaped_recipe.shape(diamond_watering_can_recipe[0],diamond_watering_can_recipe[1],diamond_watering_can_recipe[2]);

        List<Character> diamond_watering_can_recipe_ingredients = getIngredientsKey(diamond_watering_can_recipe);
        for(int i=0;i<diamond_watering_can_recipe_ingredients.size();i++) {
            String diamond_watering_can_recipe_ingredients_material = config.getString("Recipe.diamond_watering_can.ingredients."+diamond_watering_can_recipe_ingredients.get(i));
            diamond_watering_can_shaped_recipe.setIngredient(diamond_watering_can_recipe_ingredients.get(i), Material.matchMaterial(diamond_watering_can_recipe_ingredients_material));
        }

        Bukkit.addRecipe(iron_watering_can_shaped_recipe);
        Bukkit.addRecipe(diamond_watering_can_shaped_recipe);
    }

    private List<Character> getIngredientsKey(String[] recipe) {
        List<Character> ingredientsKeys = new ArrayList<>();

        for(int i=0;i<recipe.length;i++) {
            char[] recipe_char = recipe[i].toCharArray();
            for(int x=0;x<recipe_char.length;x++) {
                if(recipe_char[x] == ' ') continue;
                if(ingredientsKeys.contains(recipe_char[x])) continue;
                ingredientsKeys.add(recipe_char[x]);
            }
        }
        return ingredientsKeys;
    }

    private char getIngredients(String[] recipe) {
        char[] recipe_char = recipe[0].toCharArray();
        for(int i=0;i<recipe.length;i++) {
            recipe_char = recipe[i].toCharArray();
            if(recipe_char[i] == ' ') continue;
            return recipe_char[i];
        }
        return recipe_char[0];
    }

    public static Easyfarming getPlugin() {
        return plugin;
    }
}
