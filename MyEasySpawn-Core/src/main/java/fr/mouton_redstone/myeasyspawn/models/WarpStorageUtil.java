package fr.mouton_redstone.myeasyspawn.models;

import com.google.gson.Gson;
import fr.mouton_redstone.myeasyspawn.MyEasySpawn;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class WarpStorageUtil {

    public static Map<String, CoordinatesSet> warps = new HashMap<>();

    public static CoordinatesSet createWarp(String name, String world, float x, float y, float z) {
        CoordinatesSet coords = new CoordinatesSet(world, x, y, z);
        warps.put(name, coords);
        return coords;
    }

    public static CoordinatesSet getWarp(String name){
        if (warps.containsKey(name)) {
            return warps.get(name);
        }else{
            return null;
        }
    }

    public static void deleteWarp(String name){
        warps.remove(name);
    }

    public static CoordinatesSet updateWarp(String name, CoordinatesSet newCoords){
        if (warps.containsKey(name)){
            CoordinatesSet coords = warps.get(name);
            coords.setWorld(newCoords.getWorld());
            coords.setX(newCoords.getX());
            coords.setY(newCoords.getY());
            coords.setZ(newCoords.getZ());
            return coords;
        }else{
            return null;
        }
    }

    public static void saveNotes() throws IOException {
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
