package me.slayz.items;

import me.slayz.Enchantments;
import me.slayz.Envoy;
import me.slayz.Utils;
import me.slayz.files.IConfig;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    public static ItemStack buildItem(IConfig configManager, String path){
        YamlConfiguration c = configManager.getConfig();
        Material material;
        int damage;
        if(c.getString(path + ".id").contains(":")){
            String[] arrayOfID = c.getString(path + ".id").split(":");
            if(Material.getMaterial(arrayOfID[0]) != null){
                material = Material.getMaterial(arrayOfID[0]);
            }else{
                Envoy.getInstance().sendConsoleMessage("&4ERROR: &cIllegal material detected at config path: " + path);
                return null;
            }
            if(Utils.isInteger(arrayOfID[1])){
                damage = Integer.parseInt(arrayOfID[1]);
            }else{
                Envoy.getInstance().sendConsoleMessage("&4ERROR: &cIllegal material data detected at config path: " + path);
                return null;
            }
        }else{
            if(Material.getMaterial(c.getString(path + ".id")) != null){
                material = Material.getMaterial(c.getString(path + ".id"));
                damage = 0;
            }else{
                Envoy.getInstance().sendConsoleMessage("&4ERROR: &cIllegal material detected at config path: " + path);
                return null;
            }
        }
        ItemStack itemStack = new ItemStack(material);
        itemStack.setDurability((short) damage);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(c.contains(path + ".name")){
            itemMeta.setDisplayName(Utils.c(c.getString(path + ".name")));
        }
        if(c.contains(path + ".lore")){
            List<String> loreLines = new ArrayList<>();
            for(String line: c.getStringList(path + ".lore")){
                loreLines.add(Utils.c(line));
            }
            itemMeta.setLore(loreLines);
        }
        itemStack.setItemMeta(itemMeta);
        if(c.contains(path + ".enchants")){
            for(String enchant: c.getStringList(path + ".enchants")){
                String[] arrayOfEnchant = enchant.split(":");
                Enchantment enchantment = Enchantments.getByName(arrayOfEnchant[0]);
                if(enchantment != null){
                    if(Utils.isInteger(arrayOfEnchant[1])){
                        itemStack.addEnchantment(enchantment, Integer.parseInt(arrayOfEnchant[1]));
                    }else{
                        Envoy.getInstance().sendConsoleMessage("&4WARNING: &cEnchant: " + arrayOfEnchant[0] + " has an invalid level (Level: " + arrayOfEnchant[1] + "). Skipping...");
                    }
                }else{
                    Envoy.getInstance().sendConsoleMessage("&4WARNING: &cEnchant: " + arrayOfEnchant[0] + " does not exist! Skipping...");
                }
            }
        }
        return itemStack;
    }

}
