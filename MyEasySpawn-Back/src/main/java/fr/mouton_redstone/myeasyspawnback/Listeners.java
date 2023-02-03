package fr.mouton_redstone.myeasyspawnback;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Listeners implements Listener {
    @EventHandler(
            priority = EventPriority.NORMAL
    )
    public void onTeleport(PlayerTeleportEvent e){
        if (!e.isCancelled() && (e.getCause()== PlayerTeleportEvent.TeleportCause.PLUGIN || e.getCause()== PlayerTeleportEvent.TeleportCause.COMMAND)) {
            MyEasySpawnBack.backs.put(e.getPlayer().getUniqueId(), e.getFrom());
        }
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        MyEasySpawnBack.backs.put(e.getEntity().getUniqueId(), e.getEntity().getLocation());
    }
}
