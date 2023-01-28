package fr.mouton_redstone.myeasyspawn.commands;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWorldSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p){
            try{
                Bukkit.dispatchCommand(sender, "minecraft:setworldspawn");
                MyEasySpawn.updateDatabaseSpawn(p.getWorld());
            }catch(Exception e){

            }
        }else{
            System.out.println("[MyEasySpawn] This command is player only");
        }
        return true;
    }
}
