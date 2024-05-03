package com.factions.redstoneclock.task;

import com.factions.redstoneclock.RedstoneClockPlugin;
import com.factions.redstoneclock.data.RedstoneClock;
import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedstoneClockTask implements Runnable{
    private final Set<BlockFace> BLOCK_FACES = Sets.newHashSet(
            BlockFace.NORTH,
            BlockFace.SOUTH,
            BlockFace.EAST,
            BlockFace.WEST
    );

    @Override
    public void run() {
        for (RedstoneClock redstoneClock : RedstoneClockPlugin.getInstance().getRedstoneClockCache().getAll()) {
            final long delayToTick = redstoneClock.getDelayToTick();

            if (!(delayToTick < System.currentTimeMillis())) return;

            final Location location = redstoneClock.getLocation();
            final Block block = location.getBlock();
            for (BlockFace blockFace : BLOCK_FACES) {
                final Block blockRelative = block.getRelative(blockFace);

                if (blockRelative.getType() != Material.REDSTONE_WIRE) continue;

                block.setType(Material.REDSTONE_BLOCK);

                (new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.STAINED_GLASS);
                        block.setData((byte) 14);
                    }
                }).runTaskLater(RedstoneClockPlugin.getInstance(), 5L);
            }

            redstoneClock.setDelayToTick(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis((long) redstoneClock.getDelay()));
        }
    }
}
