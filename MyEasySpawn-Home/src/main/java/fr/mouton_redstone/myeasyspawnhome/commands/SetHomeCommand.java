package fr.mouton_redstone.myeasyspawnhome.commands;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import fr.mouton_redstone.myeasyspawnhome.MyEasySpawnHome;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SetHomeCommand implements CommandExecutor {

    Plugin plugin = MyEasySpawnHome.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            int number;
            if (args.length>0){
                try{
                    number = Integer.parseInt(args[0]);
                }catch (Exception e){
                    number = 1;
                }
            }else{
                number = 1;
            }

            if (number>plugin.getConfig().getInt("max_homes") || number<1){
                p.sendMessage(ChatColor.RED + "Please limit to " + Integer.toString(plugin.getConfig().getInt("max_homes")) + " homes");
            }else{
                Location playerPos = p.getLocation();
                String worldName = playerPos.getWorld().getName();
                String x = Double.toString(playerPos.getX());
                String y = Double.toString(playerPos.getY());
                String z = Double.toString(playerPos.getZ());
                String yaw = Double.toString(playerPos.getYaw());
                String pitch = Double.toString(playerPos.getPitch());
                MyEasySpawnHome.sql.query("INSERT OR REPLACE INTO Homes (player, id, world, x, y, z, yaw, pitch) " +
                                          "VALUES('"+p.getDisplayName()+"', '"+number+"', '"+worldName+"', "+x+", "+y+", "+z+", "+yaw+", "+pitch+")");

                p.sendMessage(ChatColor.GREEN + "Your home has been set");
            }

        }else{
            System.out.println("[MyEasySpawn-Home] Only players can use this command.");
        }

        return true;
    }
}
