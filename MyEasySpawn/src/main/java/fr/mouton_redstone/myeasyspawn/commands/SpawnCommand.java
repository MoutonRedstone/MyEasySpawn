package fr.mouton_redstone.myeasyspawn.commands;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SpawnCommand implements CommandExecutor {

    Plugin plugin = MyEasySpawn.getPlugin(MyEasySpawn.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Location spawn = (sender.getServer().getWorld("world").getSpawnLocation());
        String tpMessage = plugin.getConfig().getString("teleportMessage");
        String tpMessageColor = plugin.getConfig().getString("teleportMessageColor");

        if (sender instanceof Player p) {
            if(args.length > 0 && p.hasPermission("myeasyspawn.spawn_others")) {
                p = Bukkit.getPlayer(args[0]);
                p.teleport(spawn);
                if(tpMessage.contains("{username}")){tpMessage = tpMessage.replace("{username}", p.getDisplayName());}
                if(!tpMessage.equals("None")){p.sendMessage(ChatColor.valueOf(tpMessageColor) + tpMessage);}

            }else if(p.hasPermission("myeasyspawn.spawn")){
                p.teleport(spawn);
                if(tpMessage.contains("{username}")){tpMessage = tpMessage.replace("{username}", p.getDisplayName());}
                if(!tpMessage.equals("None")){p.sendMessage(ChatColor.valueOf(tpMessageColor) + tpMessage);}

            }else{
                p.sendMessage(ChatColor.RED + "You do not have permission");
            }

        }else if(sender instanceof ConsoleCommandSender){
            if(args.length > 0){
                Player p = Bukkit.getPlayer(args[0]);
                p.teleport(spawn);
                if(tpMessage.contains("{username}")){tpMessage = tpMessage.replace("{username}", p.getDisplayName());}
                if(!tpMessage.equals("None")){p.sendMessage(ChatColor.valueOf(tpMessageColor) + tpMessage);}

            }else{
                System.out.println("[My Easy Spawn] --> Please specify a player after the command");
                System.out.println("[My Easy Spawn] --> /spawn [player username]");
            }
        }

        return true;
    }
}
