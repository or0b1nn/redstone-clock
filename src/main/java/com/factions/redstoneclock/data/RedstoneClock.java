package com.factions.redstoneclock.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class RedstoneClock {
    private final Location location;
    private final String factionsTag;
    private double delay;
    private long delayToTick;
}
