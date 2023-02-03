package fr.mouton.redstone.myeasyspawntpto;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpAcceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p){
            if (!MyEasySpawnTpTo.tpRequests.containsKey(p)){
                p.sendMessage(ChatColor.RED + "You have no teleportation requests");
                return true;
            }
            Location targetLoc = p.getLocation();
            Player requestGuy = MyEasySpawnTpTo.tpRequests.get(p);
            requestGuy.teleport(targetLoc);
            MyEasySpawnTpTo.tpRequests.remove(p);
        }else{
            System.out.println("[MyEasySpawn-TpTo] /back can only be used by player");
        }
        return true;
    }
}
