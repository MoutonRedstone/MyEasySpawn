package fr.mouton_redstone.myeasyspawn.models;

import fr.mouton_redstone.myeasyspawn.MyEasySpawn;
import org.bukkit.Location;

public class CoordinatesSet {
    private String world;
    private float x;
    private float y;
    private float z;
    private float yaw;
    private float pitch;

    public CoordinatesSet(String world, float x, float y, float z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CoordinatesSet(Location location) {
        this.world = location.getWorld().getName();
        this.x = (float) location.getX();
        this.y = (float) location.getY();
        this.z = (float) location.getZ();
    }

    public Location getLocation(){
        return new Location(MyEasySpawn.getPlugin().getServer().getWorld(this.world), this.x, this.y, this.z);
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
