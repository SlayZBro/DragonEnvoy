package me.slayz;

import me.slayz.commands.corecmds.CmdEnvoy;
import me.slayz.crates.CrateManager;
import me.slayz.files.IConfig;
import com.tapgb.envoy.listeners.*;
import lombok.Getter;
import me.slayz.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class Envoy extends JavaPlugin {

    @Getter
    private static Envoy instance;

    @Getter
    private CrateManager crateManager;

    public void sendConsoleMessage(String msg){
        Bukkit.getConsoleSender().sendMessage(Utils.c(msg));
    }

    private HashMap<String, IConfig> configFiles = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        registerFiles();
        this.crateManager = new CrateManager();
        registerCommands();
        registerListeners(new CrateClickListener(), new SpawnerSpawnListener(), new EntityDamageEntityListener(), new FallingBlockListener(), new BlockGrief());
        if(Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new Expansion().register();
        }
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public IConfig getCfg(String file){
        return configFiles.get(file);
    }

    private void registerFiles(){
        List<String> files = new ArrayList<>(Arrays.asList("config", "crates", "messages"));
        for(String file: files){
            IConfig iConfig = new IConfig(file + ".yml", this.getDataFolder().getAbsolutePath(), null);
            iConfig.setup();
            configFiles.put(file, iConfig);
        }
    }

    private void registerCommands(){
        getCommand("envoy").setExecutor(new CmdEnvoy());
    }

    private void registerListeners(Listener... listeners){
        for(Listener listener: listeners){
            Bukkit.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public static Envoy getInstance() {
        return instance;
    }

    public CrateManager getCrateManager() {
        return crateManager;
    }
}
