package fr.mouton_redstone.myeasyspawn.commands;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class SpawnConfig implements CommandExecutor {

    Plugin plugin = MyEasySpawn.getPlugin(MyEasySpawn.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){

            Inventory gui = Bukkit.createInventory(p, 9, "myEasySpawn Configuration :");

            ItemStack cooldown = new ItemStack(Material.CLOCK);
            ItemMeta cooldown_meta = cooldown.getItemMeta();
            cooldown_meta.setDisplayName("Spawn Command Cooldown");
            List<String> lore = Arrays.asList( Integer.toString(plugin.getConfig().getInt("spawnCooldown")) + " seconds");
            cooldown_meta.setLore(lore);
            cooldown.setItemMeta(cooldown_meta);

            ItemStack tpMessage = new ItemStack(Material.ENDER_PEARL);
            ItemMeta tpMessage_meta = tpMessage.getItemMeta();
            tpMessage_meta.setDisplayName("Edit teleport message");
            lore = Arrays.asList(plugin.getConfig().getString("teleportMessage"));
            tpMessage_meta.setLore(lore);
            tpMessage.setItemMeta(tpMessage_meta);

            ItemStack joinMessage = new ItemStack(Material.GREEN_BED);
            ItemMeta joinMessage_meta = joinMessage.getItemMeta();
            joinMessage_meta.setDisplayName("Edit server's join message");
            lore = Arrays.asList(plugin.getConfig().getString("joinMessage"));
            joinMessage_meta.setLore(lore);
            joinMessage.setItemMeta(joinMessage_meta);

            ItemStack leaveMessage = new ItemStack(Material.RED_BED);
            ItemMeta leaveMessage_meta = leaveMessage.getItemMeta();
            leaveMessage_meta.setDisplayName("Edit server's leave message");
            lore = Arrays.asList(plugin.getConfig().getString("leaveMessage"));
            leaveMessage_meta.setLore(lore);
            leaveMessage.setItemMeta(leaveMessage_meta);

            ItemStack[] home_items = {cooldown, tpMessage, joinMessage, leaveMessage};

            gui.setContents(home_items);
            p.openInventory(gui);

        }else{
            System.out.println("[My Easy Spawn] --> The config menu can only be opened by a player, not by" + sender);
        }
        return true;
    }
}
