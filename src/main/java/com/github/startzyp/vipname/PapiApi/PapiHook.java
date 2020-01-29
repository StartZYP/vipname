package com.github.startzyp.vipname.PapiApi;


import com.github.startzyp.vipname.main;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PapiHook extends PlaceholderExpansion {


    @Override
    public boolean register() {
        /*
         * Since we override the register method, we need to manually
         * register this hook
         */
        return PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
    }


    @Override
    public String getIdentifier() {
        return "vipname";
    }

    @Override
    public String getAuthor() {
        return "startzyp";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {

        // %example_placeholder1%
        if (identifier.equals("prefix")) {
            String string = main.plugin.getConfig().getString("vipname." + p.getName());
            if (string.equalsIgnoreCase("")) {
                return " ";
            }
            return string;
        }


        return null;
    }


}
