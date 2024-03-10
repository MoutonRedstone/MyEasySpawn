package fr.mouton_redstone.myeasyspawnhome.commands;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ListHomesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p){
            ResultSet result = MyEasySpawn.sql.query("SELECT * FROM Homes WHERE player='"+p.getDisplayName()+"'");
            try {
                Integer count = 0;
                while(result.next()){
                    String id = Integer.toString(result.getInt("id"));
                    String x = Integer.toString((int)result.getDouble("x"));
                    String y = Integer.toString((int)result.getDouble("y"));
                    String z = Integer.toString((int)result.getDouble("z"));
                    p.sendMessage(ChatColor.GREEN + id + " : "+x+", "+y+", "+z);
                    count++;
                }
                if (count == 0){
                    p.sendMessage(ChatColor.RED + "You have 0 homes set");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
