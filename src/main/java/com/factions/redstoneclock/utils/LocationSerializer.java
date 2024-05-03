package com.factions.redstoneclock.utils;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationSerializer {
    public static String write(@NonNull Location location) {
        String worldName = location.getWorld().getName();
        return worldName + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch();
    }

    public static Location read(@NonNull String loction) {
        String[] split = loction.split(";");

        if (split.length < 4) return null;

        World world = Bukkit.getWorld(split[0]);
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        float yaw = split.length >= 5 ? Float.parseFloat(split[4]) : 0.0F;
        float pitch = split.length >= 6 ? Float.parseFloat(split[5]) : 0.0F;

        return new Location(world, x, y, z, yaw, pitch);
    }
}
