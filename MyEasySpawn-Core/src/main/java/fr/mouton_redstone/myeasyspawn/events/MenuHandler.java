package fr.mouton_redstone.myeasyspawn.events;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class MenuHandler implements Listener {

    Plugin plugin = MyEasySpawn.getPlugin(MyEasySpawn.class);

    @EventHandler
    public void clickEvent(InventoryClickEvent e){
        if (e.getView().getTitle().equalsIgnoreCase("myEasySpawn Configuration :")){
            e.setCancelled(true);
            if(e.getCurrentItem() == null){
                return;
            }else if(e.getCurrentItem().getType() == Material.CLOCK && e.getCurrentItem().getItemMeta().getDisplayName() == "Spawn Command Cooldown"){ // Edit Cooldown
                int cooldown = plugin.getConfig().getInt("spawnCooldown");
                if(cooldown % 5 != 0){
                    cooldown = (int)(Math.ceil(cooldown/5f)*5);
                }else{
                    cooldown = cooldown + 5;
                }
                if(cooldown > 60){
                    cooldown = 0;
                }
                plugin.getConfig().set("spawnCooldown", cooldown);
                // Update Item Data
                ItemMeta cooldown_meta = e.getCurrentItem().getItemMeta();
                cooldown_meta.setDisplayName("Spawn Command Cooldown");
                List<String> lore = Arrays.asList(Integer.toString(plugin.getConfig().getInt("spawnCooldown")) + " seconds");
                cooldown_meta.setLore(lore);
                e.getCurrentItem().setItemMeta(cooldown_meta);
            }else if(e.getCurrentItem().getType() == Material.ENDER_PEARL && e.getCurrentItem().getItemMeta().getDisplayName() == "Edit teleport message"){ // Edit TP message
                Inventory gui = Bukkit.createInventory(e.getWhoClicked(), InventoryType.ANVIL, "teleport_message_editor_anvil");
                ItemStack tpMessage = new ItemStack(Material.PAPER);
                ItemMeta tpMessage_meta = tpMessage.getItemMeta();
                tpMessage_meta.setDisplayName("Write here new teleport message");
                tpMessage.setItemMeta(tpMessage_meta);
            }
        }
    }
}
