package fr.mouton_redstone.myeasyspawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Location spawn = (sender.getServer().getWorld("world").getSpawnLocation());

        if (sender instanceof Player p) {
            if(args.length > 0 && p.hasPermission("myeasyspawn.spawn_others")) {
                Player p2 = Bukkit.getPlayer(args[0]);
                p.teleport(spawn);
                p2.sendMessage(ChatColor.BLUE + "You've been teleported to spawn !");
            }else if(p.hasPermission("myeasyspawn.spawn")){
                p.teleport(spawn);
                p.sendMessage(ChatColor.BLUE + "You've been teleported to spawn !");
            }else{
                p.sendMessage(ChatColor.RED + "You do not have permission");
            }
        }else if(sender instanceof ConsoleCommandSender){
            if(args.length > 0){
                Player p = Bukkit.getPlayer(args[0]);
                p.teleport(spawn);
                p.sendMessage(ChatColor.BLUE + "You've been teleported to spawn !");
            }else{
                System.out.println("[My Easy Spawn] --> Please specify a player after the command");
                System.out.println("[My Easy Spawn] --> /spawn [player username]");
            }
        }

        return true;
    }
}
