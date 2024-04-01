package org.aifusp.commands;

import org.aifusp.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainCommandCompleetion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command,String alias, String[] args){
        if (args.length ==1){
            List<String> complete = new ArrayList<>();
            complete.add("main");
            complete.add("editor");
            complete.add("give");
            complete.add("list");

            return complete;
        }

        if (args.length == 2  && args[0].equalsIgnoreCase("give")){
            return getAllFoods();
        }
        if (args.length == 3  && args[0].equalsIgnoreCase("give")){
            return getPlayers();
        }
        return null;

    }



    public  List<String> getPlayers(){
        List<String> playersList = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (int i = 0; i < players.length; i++){
            playersList.add(players[i].getName());
        }
        return playersList;
    }
    public List<String> getAllFoods(){
        List<String> complete = new ArrayList<>();


        File dataFolder = Main.getInstance().getDataFolder();
        File directory = new File(dataFolder + "/Foods");
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    String fileName = file.getName();
                    String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
                    complete.add(nameWithoutExtension);
                }
            }
            return complete;
        } else {
            System.out.println("El directorio está vacío o no existe.");
            return null;
        }
    }

}
