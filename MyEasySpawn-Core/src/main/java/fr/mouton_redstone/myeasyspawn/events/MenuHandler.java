package fr.mouton_redstone.myeasyspawn.events;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import fr.mouton_redstone.myeasyspawn.models.ConfigItemCreator;
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

public class MenuHandler implements Listener {

    Plugin plugin = MyEasySpawn.getPlugin(MyEasySpawn.class);

    @EventHandler
    public void clickEvent(InventoryClickEvent e){
        if (e.getView().getTitle().equalsIgnoreCase("myEasySpawn Configuration :")){
            e.setCancelled(true);
            if(e.getCurrentItem() == null){
            }else if(e.getCurrentItem().isSimilar(ConfigItemCreator.CreateCooldownItem())){ // Edit Cooldown
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
                // Update Item
                ItemStack cooldownItem = ConfigItemCreator.CreateCooldownItem();
                e.getClickedInventory().setItem(0, cooldownItem);
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
