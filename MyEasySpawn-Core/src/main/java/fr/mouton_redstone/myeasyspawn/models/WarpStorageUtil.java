package fr.mouton_redstone.myeasyspawn.models;

import com.google.gson.Gson;
import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class WarpStorageUtil {

    public static Map<String, Location> warps = new HashMap<>();

    public static Location createWarp(String name, World world, float x, float y, float z) {
        Location coords = new Location(world, x, y, z);
        warps.put(name, coords);
        try {
            saveWarps();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coords;
    }

    public static Location getWarp(String name){
        if (warps.containsKey(name)) {
            return warps.get(name);
        }else{
            return null;
        }
    }

    public static void deleteWarp(String name){
        warps.remove(name);
        try {
            saveWarps();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location updateWarp(String name, Location newCoords){
        if (warps.containsKey(name)){
            warps.replace(name, newCoords);
            Location coords = warps.get(name);
            try {
                saveWarps();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return coords;
        }else{
            return null;
        }

    }

    public static void saveWarps() throws IOException {
        Gson gson = new Gson();
        File file = new File(MyEasySpawn.getPlugin().getDataFolder().getAbsolutePath() + "/warps.json");
        file.getParentFile().mkdir();
        Writer writer = new FileWriter(file, false);
        gson.toJson(warps, writer);
        writer.flush();
        writer.close();
        System.out.println("[MyEasySpawn] --> Saved warps");
    }
}
