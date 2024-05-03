package com.factions.redstoneclock.cache;

import com.factions.redstoneclock.RedstoneClockPlugin;
import com.factions.redstoneclock.data.RedstoneClock;
import lombok.experimental.Delegate;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RedstoneClockCache {
    @Delegate
    final List<RedstoneClock> redstoneClockList = new ArrayList<>();

    public RedstoneClock getByLocation(@NotNull Location location) {
        return this.redstoneClockList
                .stream()
                .filter(redstoneClock -> redstoneClock.getLocation().equals(location))
                .findFirst()
                .orElse(null);
    }

    public void register(@NotNull RedstoneClock redstoneClock) {
        RedstoneClockPlugin.getInstance().getRedstoneClockRepository().create(redstoneClock);
        redstoneClockList.add(redstoneClock);
    }

    public void remove(@NotNull RedstoneClock redstoneClock) {
        RedstoneClockPlugin.getInstance().getRedstoneClockRepository().remove(redstoneClock);
        redstoneClockList.remove(redstoneClock);
    }

    public List<RedstoneClock> getAll() {
        return redstoneClockList;
    }
}
