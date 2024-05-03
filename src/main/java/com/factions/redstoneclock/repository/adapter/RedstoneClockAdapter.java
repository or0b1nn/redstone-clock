package com.factions.redstoneclock.repository.adapter;

import com.factions.redstoneclock.data.RedstoneClock;
import com.factions.redstoneclock.utils.LocationSerializer;
import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import org.bukkit.Location;

public class RedstoneClockAdapter implements SQLResultAdapter<RedstoneClock> {


    @Override
    public RedstoneClock adaptResult(SimpleResultSet simpleResultSet) {
        Location location = LocationSerializer.read(simpleResultSet.get("location"));
        String factionTag = simpleResultSet.get("factionTag");
        Double delay = simpleResultSet.get("delay");
        int delayToTick = simpleResultSet.get("delayToTick");



        return new RedstoneClock(location, factionTag, delay, delayToTick);
    }
}
