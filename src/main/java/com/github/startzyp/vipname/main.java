package com.github.startzyp.vipname;

import com.github.startzyp.vipname.PapiApi.PapiHook;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class main extends JavaPlugin {

    public static PlayerPoints playerPoints;
    public static Plugin plugin;
    private static int havepoints;
    private int Maxlength;
    @Override
    public void onEnable() {
        File config = new File(getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
        }
        saveDefaultConfig();
        plugin = this;
        loadData();
        if( Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            //Registering placeholder will be use here
            new PapiHook().register();
        }
        havepoints = getConfig().getInt("PlayerPoints");
        Maxlength = getConfig().getInt("Maxlength");
        super.onEnable();
    }

    public static void loadData() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints");
        playerPoints = PlayerPoints.class.cast(plugin);
        System.out.println("§e§l[Vipname]§e§l经济对接中");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!label.equalsIgnoreCase("vipname")){
            return false;
        }

        if (args.length!=1){
            sender.sendMessage("§e§l[Vipname]输入格式不对/vipname <自定义称号>");
            return false;
        }
        if (!(sender instanceof Player)){
            sender.sendMessage("§e§l[Vipname]必须是玩家输入");
        }
        String newname = args[0].replaceAll("&", "§");
        if (newname.length() > Maxlength)
        {
            sender.sendMessage("§e§l[Vipname]§a称号长度超过了最大长度");
            return false;
        }
        Player player = (Player) sender;
        if (playerPoints.getAPI().look(player.getUniqueId())>=havepoints){
            playerPoints.getAPI().take(player.getUniqueId(),havepoints);
            getConfig().set("vipname."+player.getName(),newname);
            saveConfig();
            sender.sendMessage("§e§l[Vipname]§a称号修改成功,已经扣除点券");
        }else {
            sender.sendMessage("§e§l[Vipname]§4您的点券不足，请充值");
        }

        return super.onCommand(sender, command, label, args);
    }
}
