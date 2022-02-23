package fr.mouton_redstone.myeasyspawn;

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

        String joinMessageColor = plugin.getConfig().getString("joinMessageColor");
        String joinMessage = plugin.getConfig().getString("joinMessage");
        Player player = p.getPlayer();
        String username = player.getDisplayName();

        if(joinMessage.contains("{username}")){
            joinMessage = joinMessage.replace("{username}", username);
        }

        if(!joinMessage.equals("None")){
            p.setJoinMessage(ChatColor.valueOf(joinMessageColor) + joinMessage);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent p){
        String leaveMessageColor = plugin.getConfig().getString("leaveMessageColor");
        String quitMessage = plugin.getConfig().getString("leaveMessage");
        Player player = p.getPlayer();
        String username = player.getDisplayName();

        if(quitMessage.contains("{username}")){
            quitMessage = quitMessage.replace("{username}", username);
        }

        if(!quitMessage.equals("None")){
            p.setQuitMessage(ChatColor.valueOf(leaveMessageColor) + quitMessage);
        }
    }
}
