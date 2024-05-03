package com.factions.redstoneclock;

import com.factions.redstoneclock.cache.RedstoneClockCache;
import com.factions.redstoneclock.command.CustomCommand;
import com.factions.redstoneclock.command.impl.RedstoneClockCommand;
import com.factions.redstoneclock.listener.PlayerListener;
import com.factions.redstoneclock.repository.RedstoneClockRepository;
import com.factions.redstoneclock.task.RedstoneClockTask;
import com.factions.redstoneclock.view.DelayView;
import lombok.Getter;
import lombok.SneakyThrows;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

@Getter
public class RedstoneClockPlugin extends JavaPlugin {
    private RedstoneClockRepository redstoneClockRepository;
    private RedstoneClockCache redstoneClockCache;
    private ViewFrame viewFrame;

    @Override
    public void onEnable() {
        this.redstoneClockRepository = new RedstoneClockRepository(this);
        this.redstoneClockCache = new RedstoneClockCache();
        this.redstoneClockRepository.selectMany().forEach(redstoneClock -> this.redstoneClockCache.add(redstoneClock));

        this.viewFrame = ViewFrame.of(this, new DelayView()).register();

        this.saveDefaultConfig();
        this.registerRunnables();
        this.registerAllCommands();
        this.registerListeners();
    }

    private void registerRunnables() {
        Bukkit.getScheduler().runTaskTimer(this, new RedstoneClockTask(), 1L, 1L);
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerListener(), this);
    }
    private void registerAllCommands() {
        this.registerCommands(new RedstoneClockCommand());
    }

    @SneakyThrows
    private void registerCommands(CustomCommand... customCommands) {
        final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        commandMapField.setAccessible(true);

        final CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

        for (CustomCommand customCommand : customCommands)
            commandMap.register(customCommand.getName(), customCommand);
    }

    public static RedstoneClockPlugin getInstance(){
        return getPlugin(RedstoneClockPlugin.class);
    }
}
