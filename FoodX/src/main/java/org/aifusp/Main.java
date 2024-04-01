package org.aifusp;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.aifusp.foodx.listeners.ClickGui;

import org.aifusp.commands.MainCommand;
import org.aifusp.commands.MainCommandCompleetion;
import org.aifusp.listeners.FoodEventListener;
import org.aifusp.listeners.PlayerInteract;
import org.aifusp.utils.Food;
import org.aifusp.utils.MessageUtils;
import org.aifusp.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

public class Main extends JavaPlugin implements Listener {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public  static Main getInstance() {
        return getPlugin(Main.class);
    }
    @Override
    public void onLoad() {
        getLogger().info("Added class loader to HeadsPluginApi springClassLoaders");


    }
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage("&0&l[&f&l&nFoodX&0&l] Ha sido inicializado"));
        int pluginId = 21111; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SingleLineChart("foods", new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                // (This is useless as there is already a player chart by default.)
                return getFoodsInt();
            }
        }));

        //Foldering
        createConfigFolder();
        createFoodsFolder();
        ExampleFoodCreator();

        registerPlayerInteract();
        registerMainCommand();
        registerFoodEventsListeners();
        registerGUIListeners();
        registerTabCompleter();



    }
    public File getMainFolder(){
        return getDataFolder();
    }

    private void createConfigFolder() {
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    // Método para crear la carpeta de alimentos del plugin
    private void createFoodsFolder() {
        File foodsFolder = new File(getDataFolder(), "Foods");
        if (!foodsFolder.exists()) {
            foodsFolder.mkdirs();
        }
    }





    private void ExampleFoodCreator(){
        createFoodJSON("ExampleFood_Burger",1,5,"ENTITY_PLAYER_BURP",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDQyNzg5MjY3ZjYzYTU3MTU2Y2ZiODEyNTVmNjNiYmQ2MGVjYmY0ODZiYWIwYzUwNDJhY2E2NjM3MTRlMmE1NiJ9fX0=",
                List.of("effect %player% slow 5 2"),
                List.of("A burger!")
        );
        createFoodJSON("ExampleFood_CocoDrink",0,1.5,"ambient_underwater_enter",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDZkMGFmOTQ1NGYzMmNjMDRhZmJlYjVmYjI5OWRiZmVhMjQyMDkxZWI2ZjgxN2ZmZjg2YTAzNzg4ZDk2NTIwZSJ9fX0=",
                List.of("effect %player% nausea 5 6","Delay 3","effect %player% speed 5 1"),
                List.of("a Coco Drink","Without alcohol")
        );
        createFoodJSON("ExampleFood_Apple",1.3,2.5,"ambient_underwater_enter",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmUzNzQ3Y2FiYWU2NWJmNzZkMGIyMGJmNDcwOTNhYWYzMDkwOTk3MjA1OTdjZjk2Zjg0YTAzMmYxMjQ2MmM3MCJ9fX0=",
                List.of(""),
                List.of("")
        );

    }



    private void createFoodJSON(String foodName,double saturation,double feed, String sound,String texture,List<String> commands,List<String> lore) {
        File foodFile = new File(getDataFolder() + "/Foods", foodName + ".json");

        if (!foodFile.exists()) {
            try {
                foodFile.createNewFile();
                Food.FoodData foodData = new Food.FoodData(foodName, saturation, feed,sound,texture, commands, lore);
                saveFoodJSON(foodFile, foodData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFoodJSON(File file, Food.FoodData foodData) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(foodData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage("&0&l[&f&l&nFoodX&0&l] Ha sido  descargado"));
    }

    public void registerMainCommand() {
        this.getCommand("FoodX").setExecutor(new MainCommand(this));
    }
    public void registerTabCompleter(){this.getCommand("FoodX").setTabCompleter(new MainCommandCompleetion());}

    public void registerPlayerInteract() {
        getServer().getPluginManager().registerEvents(new PlayerInteract(this), this);
    }
    public void registerFoodEventsListeners() {
        getServer().getPluginManager().registerEvents(new FoodEventListener(this), this);
    }
    public void registerGUIListeners() {
        getServer().getPluginManager().registerEvents(new ClickGui(this), this);
    }
    public File foodsFolder = new File(getDataFolder() +"/Foods");

    public int getFoodsInt(){

        if (!foodsFolder.exists() || !foodsFolder.isDirectory()) {
            System.out.println("La carpeta 'Foods' no existe o no es una carpeta válida.");
            return 0;
        }

        File[] archivos = foodsFolder.listFiles();
        if (archivos == null) {
            System.out.println("No se pudo obtener la lista de archivos de la carpeta 'Foods'.");
            return 0;
        }

        return archivos.length;
    }
}
