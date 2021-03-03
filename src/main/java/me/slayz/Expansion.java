package me.slayz;

import me.slayz.crates.CrateManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class Expansion extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "envoy";
    }

    @Override
    public String getAuthor() {
        return "TapGB";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if(identifier.equalsIgnoreCase("next_drop")){
            CrateManager crateManager = Envoy.getInstance().getCrateManager();
            return crateManager.formatDelay(crateManager.getSpawnDelay() - crateManager.getTime());
        }
        return null;
    }
}
