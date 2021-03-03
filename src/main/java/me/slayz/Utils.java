package me.slayz;

import org.bukkit.ChatColor;

import java.util.Random;

public class Utils {

    public static String c(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static boolean isInteger(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static int randomInt(int minimum, int maximum){
        Random rn = new Random();
        if(minimum == maximum){
            return minimum;
        }
        int range = maximum - minimum;
        return rn.nextInt(range) + minimum;
    }

    public static String getMessage(String id, Placeholder... placeholders){
        return Placeholder.apply(new Placeholder("{prefix}", getPrefix()).apply(Utils.c(Envoy.getInstance().getCfg("messages").getConfig().getString(id))), placeholders);
    }

    public static String getPrefix(){
        return Utils.c(Envoy.getInstance().getCfg("messages").getConfig().getString("prefix"));
    }

}
