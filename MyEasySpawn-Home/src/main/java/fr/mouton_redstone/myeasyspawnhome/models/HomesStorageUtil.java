package fr.mouton_redstone.myeasyspawnhome.models;

import com.google.gson.Gson;
import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import fr.mouton_redstone.myeasyspawn.models.CoordinatesSet;
import fr.mouton_redstone.myeasyspawnhome.MyEasySpawnHome;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomesStorageUtil {

    public static Map<List<String>, CoordinatesSet> homes = new HashMap<>();

    public static Location createHome(String name, int number, World world, float x, float y, float z) {
        Location coords = new Location(world, x, y, z);
        List<String> key = Arrays.asList(new String[] {name, Integer.toString(number)});
        homes.put(key, new CoordinatesSet(coords));
        try {
            saveHomes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coords;
    }

    public static Location createHome(String name, int number, Location location) {
        List<String> key = Arrays.asList(new String[] {name, Integer.toString(number)});
        homes.put(key, new CoordinatesSet(location));
        try {
            saveHomes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    public static Location getHome(String name, int number){
        List<String> key = Arrays.asList(new String[] {name, Integer.toString(number)});
        if (homes.containsKey(key)) {
            System.out.println("Home found");
            return homes.get(key).getLocation();
        }else{
            System.out.println("Home not found");
            return null;
        }
    }

    public static void deleteHome(String name, int number){
        List<String> key = Arrays.asList(new String[] {name, Integer.toString(number)});
        homes.remove(key);
        try {
            saveHomes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location updateHome(String name, int number, Location newCoords){
        List<String> key = Arrays.asList(new String[] {name, Integer.toString(number)});
        if (homes.containsKey(key)){
            homes.replace(key, new CoordinatesSet(newCoords));
            try {
                saveHomes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newCoords;
        }else{
            return null;
        }

    }

    public static void saveHomes() throws IOException {
        Gson gson = new Gson();
        File file = new File(MyEasySpawnHome.getPlugin().getDataFolder().getAbsolutePath() + "/homes.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(homes, writer);
        writer.flush();
        writer.close();
        System.out.println("[MyEasySpawn-Home] Saved Homes");
    }

    public static void loadHomes() throws IOException {
        Gson gson = new Gson();
        File file = new File(MyEasySpawnHome.getPlugin().getDataFolder().getAbsolutePath() + "/homes.json");
        if (file.exists()) {
            Reader reader = new FileReader(file);
            Map<List<String>, CoordinatesSet> homesMap = gson.fromJson(reader, (new HashMap<List<String>, CoordinatesSet>()).getClass());
            homes = homesMap;
            System.out.println("[MyEasySpawn-Home] Loaded Homes");
        }
    }
}
