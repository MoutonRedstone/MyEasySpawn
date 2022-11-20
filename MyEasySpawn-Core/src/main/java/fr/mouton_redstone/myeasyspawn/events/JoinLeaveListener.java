package fr.mouton_redstone.myeasyspawn.events;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;


public class JoinLeaveListener implements Listener {

    Plugin plugin = MyEasySpawn.getPlugin(MyEasySpawn.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent p){
        if (plugin.getConfig().getBoolean("changeJoinLeaveMessages")) {
            String joinMessageColor = plugin.getConfig().getString("joinMessageColor");
            String joinMessage = plugin.getConfig().getString("joinMessage");
            Player player = p.getPlayer();
            String username = player.getDisplayName();

            joinMessage = joinMessage.replace("{username}", username);
            p.setJoinMessage(ChatColor.valueOf(joinMessageColor) + joinMessage);
        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent p){
        if (plugin.getConfig().getBoolean("changeJoinLeaveMessage")) {
            String leaveMessageColor = plugin.getConfig().getString("leaveMessageColor");
            String quitMessage = plugin.getConfig().getString("leaveMessage");
            Player player = p.getPlayer();
            String username = player.getDisplayName();

            quitMessage = quitMessage.replace("{username}", username);
            p.setQuitMessage(ChatColor.valueOf(leaveMessageColor) + quitMessage);
        }
    }
}
