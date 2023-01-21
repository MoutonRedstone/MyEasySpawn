package fr.mouton_redstone.myeasyspawn.models;

import com.google.gson.Gson;
import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.*;
import java.util.*;

public class WarpStorageUtil {

    public static Map<String, CoordinatesSet> warps = new HashMap<>();

    public static Location createWarp(String name, World world, float x, float y, float z) {
        Location coords = new Location(world, x, y, z);
        warps.put(name, new CoordinatesSet(coords));
        try {
            saveWarps();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coords;
    }

    public static Location createWarp(String name, Location location) {
        warps.put(name, new CoordinatesSet(location));
        try {
            saveWarps();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    public static Location getWarp(String name){
        if (warps.containsKey(name)) {
            return warps.get(name).getLocation();
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
            warps.replace(name, new CoordinatesSet(newCoords));
            try {
                saveWarps();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newCoords;
        }else{
            return null;
        }

    }

    public static void saveWarps() throws IOException {
        Gson gson = new Gson();
        File file = new File(MyEasySpawn.getPlugin().getDataFolder().getAbsolutePath() + "/warps.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(warps, writer);
        writer.flush();
        writer.close();
        System.out.println("[MyEasySpawn] Saved warps");
    }

    public static void loadWarps() throws IOException {
        Gson gson = new Gson();
        File file = new File(MyEasySpawn.getPlugin().getDataFolder().getAbsolutePath() + "/warps.json");
        if (file.exists()) {
            Reader reader = new FileReader(file);
            Map<String, CoordinatesSet> warpsMap = gson.fromJson(reader, Map.class);
            System.out.println("[MyEasySpawn] Loaded Warps");
        }
    }
}
