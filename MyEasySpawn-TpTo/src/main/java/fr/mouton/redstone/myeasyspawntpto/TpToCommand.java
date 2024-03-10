package fr.mouton.redstone.myeasyspawntpto;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpToCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player p) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "You need to give a player name");
                return true;
            }
            String targetName = args[0];
            Player target = Bukkit.getPlayerExact(targetName);
            if (target==null || !target.isOnline()){
                p.sendMessage(ChatColor.RED + "Player doesn't exists or is not online");
                return true;
            }
            MyEasySpawnTpTo.tpRequests.put(target, p);
            target.sendMessage(p.getName() + ChatColor.GOLD + " wants to reach you!");
            target.sendMessage(ChatColor.GOLD + "Type /tpaccept to let him teleport to you.");
            p.sendMessage("Teleport request sent to " + target.getName());
        }else{
            sender.sendMessage("[MyEasySpawn-TpTo] /back can only be used by player");
        }
        return true;
    }
}
