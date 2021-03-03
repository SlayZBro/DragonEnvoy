package me.slayz.files;

import me.slayz.Envoy;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class IConfig {

    private String name;
    private YamlConfiguration config;
    private File file;
    private String folder;
    private String path;

    public IConfig(String name, String path, String folder) {
        this.name = name;
        this.path = path;
        this.folder = folder;
    }

    public void setup() {
        if (this.folder == null) {
            this.file = new File(this.path, this.name);
        } else {
            new File(this.path + File.separator + folder).mkdir();
            this.file = new File(this.path + File.separator + folder, this.name);
        }
        if(!file.exists()) {
            saveResource();
        }
        loadConfig();
        this.config.options().copyDefaults(true);
        this.saveConfig();
    }


    public void loadConfig() {
        if (this.file.exists()) {
            if (this.folder == null) {
                this.file = new File(this.path, this.name);
            } else {
                this.file = new File(this.path + File.separator + folder, this.name);
            }
            this.config = YamlConfiguration.loadConfiguration(this.file);
        }
    }

    public void saveConfig() {
        try {
            getConfig().save(getFile());

        } catch (IOException e) {
            e.printStackTrace();
            Envoy.getInstance().sendConsoleMessage(this.name + ChatColor.RED + " could not be saved!");
        }
    }

    private void saveResource() {
        if (folder != null) {
            Envoy.getInstance().saveResource(folder + File.separator + name, false);
        } else {
            Envoy.getInstance().saveResource(name, false);
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }
}
