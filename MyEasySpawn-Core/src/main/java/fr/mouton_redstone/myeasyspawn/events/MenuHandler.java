package fr.mouton_redstone.myeasyspawn.events;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
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
                return;
            }else if(e.getCurrentItem().getType() == Material.CLOCK){
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
                ItemMeta cooldown_meta = e.getCurrentItem().getItemMeta();
                cooldown_meta.setDisplayName("Spawn Command Cooldown : " + plugin.getConfig().getInt("spawnCooldown"));
                e.getCurrentItem().setItemMeta(cooldown_meta);
            }
        }
    }
}
