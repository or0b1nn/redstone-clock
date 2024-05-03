package com.factions.redstoneclock.repository;

import com.factions.redstoneclock.data.RedstoneClock;
import com.factions.redstoneclock.repository.adapter.RedstoneClockAdapter;
import com.factions.redstoneclock.repository.connection.RepositoryProvider;
import com.factions.redstoneclock.utils.LocationSerializer;
import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RedstoneClockRepository extends RepositoryProvider {
    private static final String TABLE_NAME = "redstoneclock";
    private SQLExecutor executor;

    public RedstoneClockRepository(Plugin plugin) {
        super(plugin);
        this.prepare();
        this.createTable();
    }

    @Override
    public SQLConnector prepare() {
        final SQLConnector prepare = super.prepare();
        executor = new SQLExecutor(prepare);
        return prepare;
    }

    public void createTable() {
        executor.updateQuery("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                "location TEXT NOT NULL PRIMARY KEY," +
                "factionTag VARCHAR(4) NOT NULL," +
                "delay DOUBLE NOT NULL," +
                "delayToTick LONG NOT NULL" +
                ");");
    }

    public Set<RedstoneClock> selectMany() {
        return executor.resultManyQuery(
                "SELECT * FROM " + TABLE_NAME, simpleStatement -> {
                }, RedstoneClockAdapter.class);
    }

    public void create(RedstoneClock redstoneClock) {
        CompletableFuture.runAsync(() -> {
            executor.updateQuery("INSERT INTO " + TABLE_NAME + " VALUES(?,?,?,?)", simpleStatement -> {
                simpleStatement.set(1, LocationSerializer.write(redstoneClock.getLocation()));
                simpleStatement.set(2, redstoneClock.getFactionsTag());
                simpleStatement.set(3, redstoneClock.getDelay());
                simpleStatement.set(4, redstoneClock.getDelayToTick());
            });
        });
    }

    public void remove(RedstoneClock redstoneClock) {
        CompletableFuture.runAsync(() ->
                executor.updateQuery("DELETE FROM " + TABLE_NAME + " WHERE location = ?", simpleStatement -> {
                    simpleStatement.set(1, LocationSerializer.write(redstoneClock.getLocation()));
                }));
    }
}
