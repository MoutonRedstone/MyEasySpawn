package fr.mouton_redstone.myeasyspawn.events;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TeleportationListener implements Listener{

    Plugin plugin = MyEasySpawn.getPlugin(MyEasySpawn.class);

    @EventHandler
    public void onTeleport(PlayerTeleportEvent p){
        String teleportMessage = plugin.getConfig().getString("teleportMessage");
        if (!teleportMessage.isBlank()) {
            String teleportMessageColor = plugin.getConfig().getString("teleportMessageColor");
            Player player = p.getPlayer();
            teleportMessage = teleportMessage.replace("{username}", player.getDisplayName());
            player.sendMessage(ChatColor.valueOf(teleportMessageColor) + teleportMessage);
        }
    }
}
