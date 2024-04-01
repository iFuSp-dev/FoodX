package org.aifusp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Food {

    private String DisplayName;
    private String id;
    private String texture;
    private double saturationAmount;
    private double feedAmount;
    private List<String> commands;
    private List<String> lores;

    private File file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Food(File file) {
        this.file = file;
        try (FileReader reader = new FileReader(file)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            this.DisplayName = jsonObject.get("DisplayName").getAsString();
            this.saturationAmount = jsonObject.get("saturation").getAsDouble();
            this.feedAmount = jsonObject.get("feed").getAsDouble();
            this.texture = jsonObject.get("texture").getAsString();
            this.id = jsonObject.get("Id").getAsString();
            this.commands = gson.fromJson(jsonObject.get("commands"), new TypeToken<List<String>>() {}.getType());
            this.lores = gson.fromJson(jsonObject.get("lore"), new TypeToken<List<String>>() {}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getDisplayName() {
        return getDisplayName();
    }

    public Double getFeedAmount() {
        return feedAmount;
    }

    public double getSaturationAmount() {
        return saturationAmount;
    }

    public List<String> getCommands() {
        return commands;
    }
    public List<String> getLoreList() {
        return lores;
    }


    // Setters (Not really needed for this implementation)



    // getItem method
    public String getId(){
        return this.id;
    }
    public ItemStack getItem() {
        JsonObject jsonObject;
        double foodToAdd;
        double saturationToAdd;
        String sound;
        String Id;
        String textureId;
        String itemDisplayName;
        File nfile = this.file;
        try (FileReader reader = new FileReader(nfile)) {
             jsonObject = gson.fromJson(reader, JsonObject.class);
             foodToAdd = jsonObject.get("feed").getAsDouble();
             saturationToAdd = jsonObject.get("saturation").getAsDouble();
             textureId = jsonObject.get("texture").getAsString();
             itemDisplayName = jsonObject.get("DisplayName").getAsString();
             Id = jsonObject.get("Id").getAsString();
             sound = jsonObject.get("sound").getAsString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Verificar si ya existe un ItemStack con la misma textura en el inventario del jugador
        // Hay que verificarlo por ItemNBT

        ItemStack playerHead = SkullUtils.getPlayerHead(textureId);

        ItemMeta itemMeta = playerHead.getItemMeta();
        itemMeta.setDisplayName(MessageUtils.getColoredMessage(itemDisplayName));
        playerHead.setItemMeta(itemMeta);

        // Agregar los NBT
        NBTItem nbtItem = new NBTItem(playerHead);
        nbtItem.setString("Id", Id);
        nbtItem.setDouble("Saturation", saturationToAdd);
        nbtItem.setDouble("Feed", foodToAdd);
        nbtItem.setBoolean("Eatable", true);
        if (sound != null){nbtItem.setString("Sound",sound);}

            // Agregar comandos al NBT
        List<String> commandsList = getCommands();
        if (commandsList != null && !commandsList.isEmpty()) {
            String[] commandsArray = commandsList.toArray(new String[0]);
            String commandsString = String.join(",", commandsArray);
            nbtItem.setString("Commands", commandsString);
        }
        playerHead = nbtItem.getItem();

        // Agregar Lore
        List<String> loreList = getLoreList();

        if (loreList != null && !loreList.isEmpty()) {
            // Crear una nueva lista para almacenar el lore formateado
            List<String> formattedLoreList = new ArrayList<>();

            // Iterar sobre cada línea de lore y aplicar el formateo de colores
            for (String loreLine : loreList) {
                String formattedLoreLine = MessageUtils.getColoredMessage(loreLine);
                formattedLoreList.add(formattedLoreLine);
            }

            ItemMeta playerHeadMeta = playerHead.getItemMeta();
            playerHeadMeta.setLore(formattedLoreList);
            playerHead.setItemMeta(playerHeadMeta);
        }


        Bukkit.getConsoleSender().sendMessage("Nueva");
        return playerHead;
    }
    public static class FoodData {
        private String Id;
        private String DisplayName;
        private double saturation;
        private String sound;
        private double feed;
        private String texture;
        private List<String> commands;
        private List<String> lore;

        public FoodData(String Id, double saturation, double feed,String sound, String texture,
                        List<String> commands, List<String> lore) {
            this.DisplayName = Id;
            this.Id = Id;
            this.saturation = saturation;
            this.feed = feed;
            this.sound = sound;
            this.texture = texture;
            this.commands = commands;
            this.lore = lore;
        }
    }


}
