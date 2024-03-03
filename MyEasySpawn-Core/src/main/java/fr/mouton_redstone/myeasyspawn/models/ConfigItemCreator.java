package fr.mouton_redstone.myeasyspawn.models;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class ConfigItemCreator {

    static Plugin plugin = MyEasySpawn.getPlugin(MyEasySpawn.class);

    public static ItemStack CooldownItem(){
        ItemStack cooldown = new ItemStack(Material.CLOCK);
        ItemMeta cooldown_meta = cooldown.getItemMeta();
        cooldown_meta.setDisplayName(ChatColor.RESET + "Spawn Command Cooldown");
        List<String> lore = Arrays.asList(plugin.getConfig().getInt("spawnCooldown") + " seconds", "Click to add 5 seconds");
        cooldown_meta.setLore(lore);
        cooldown.setItemMeta(cooldown_meta);

        return cooldown;
    }

    public static ItemStack TpMessageItem() {
        ItemStack tpMessage = new ItemStack(Material.ENDER_PEARL);
        ItemMeta tpMessage_meta = tpMessage.getItemMeta();
        tpMessage_meta.setDisplayName(ChatColor.RESET + "Edit teleport message");
        List<String> lore = Arrays.asList(plugin.getConfig().getString("teleportMessage"));
        tpMessage_meta.setLore(lore);
        tpMessage.setItemMeta(tpMessage_meta);

        return tpMessage;
    }

    public static ItemStack JoinMessageItem() {
        ItemStack joinMessage = new ItemStack(Material.GREEN_BED);
        ItemMeta joinMessage_meta = joinMessage.getItemMeta();
        joinMessage_meta.setDisplayName(ChatColor.RESET + "Edit server's join message");
        List<String> lore = Arrays.asList(plugin.getConfig().getString("joinMessage"));
        joinMessage_meta.setLore(lore);
        joinMessage.setItemMeta(joinMessage_meta);

        return joinMessage;
    }

    public static ItemStack LeaveMessageItem() {
        ItemStack leaveMessage = new ItemStack(Material.RED_BED);
        ItemMeta leaveMessage_meta = leaveMessage.getItemMeta();
        leaveMessage_meta.setDisplayName(ChatColor.RESET + "Edit server's leave message");
        List<String> lore = Arrays.asList(plugin.getConfig().getString("leaveMessage"));
        leaveMessage_meta.setLore(lore);
        leaveMessage.setItemMeta(leaveMessage_meta);

        return leaveMessage;
    }
}
