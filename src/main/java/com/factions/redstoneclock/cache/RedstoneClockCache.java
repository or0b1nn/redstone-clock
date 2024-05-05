package com.factions.redstoneclock.cache;

import com.factions.redstoneclock.data.RedstoneClock;
import com.factions.redstoneclock.repository.RedstoneClockRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class RedstoneClockCache {
    private final RedstoneClockRepository redstoneClockRepository;

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
        redstoneClockRepository.create(redstoneClock);
        redstoneClockList.add(redstoneClock);
    }

    public void remove(@NotNull RedstoneClock redstoneClock) {
        redstoneClockRepository.remove(redstoneClock);
        redstoneClockList.remove(redstoneClock);
    }

    public List<RedstoneClock> getAll() {
        return redstoneClockList;
    }
}
