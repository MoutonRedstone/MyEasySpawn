package fr.mouton_redstone.myeasyspawn.models;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class ConfigItemCreator {

    static Plugin plugin = MyEasySpawn.getPlugin(MyEasySpawn.class);

    public static ItemStack CreateCooldownItem(){
        ItemStack cooldown = new ItemStack(Material.CLOCK);
        ItemMeta cooldown_meta = cooldown.getItemMeta();
        cooldown_meta.setDisplayName("Spawn Command Cooldown");
        List<String> lore = Arrays.asList(plugin.getConfig().getInt("spawnCooldown") + " seconds", "Click to add 5 seconds");
        cooldown_meta.setLore(lore);
        cooldown.setItemMeta(cooldown_meta);

        return cooldown;
    }
}
