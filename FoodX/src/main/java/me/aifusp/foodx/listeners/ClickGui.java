package me.aifusp.foodx.listeners;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTItem;
import me.aifusp.foodx.Buttons;
import me.aifusp.foodx.InventoryName;
import me.aifusp.foodx.gui.menu.*;
import org.aifusp.Main;
import org.aifusp.utils.Food;
import org.aifusp.utils.MessageUtils;
import org.aifusp.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClickGui implements Listener {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Main main;
    private Map<Player, String> pendingFoodNames = new HashMap<>();
    private Map<Player, String> pendingEditDisplayName = new HashMap<>();
    private Map<Player, String> pendingEditSaturation = new HashMap<>();
    private Map<Player, String> pendingEditFeed = new HashMap<>();
    private Map<Player, String> pendingEditTexture = new HashMap<>();

    private Map<Player, String> pendingEditSound = new HashMap<>();

    public ClickGui(Main plugin){this.main = plugin;}
    private Buttons buttons = new Buttons();

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e){

        String title = e.getView().getTitle();
        if (!title.equals(InventoryName.MAIN_MENU.getName())
                && !title.equals(InventoryName.FOOD_LIST.getName())
                && !title.equals(InventoryName.FOOD_BUILDER.getName())
                && !title.equals(InventoryName.EDITABLE_FOOD_LIST.getName())
                && !title.equals(InventoryName.FOOD_EDITOR.getName()))
            return;
        e.setCancelled(true);

        for (Integer slot : e.getRawSlots()){
            if (slot < e.getInventory().getSize()){
                return;
            }
        }
        e.setCancelled(false);
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String title = e.getView().getTitle();
        if (!title.equals(InventoryName.MAIN_MENU.getName())
                && !title.equals(InventoryName.FOOD_LIST.getName())
                && !title.equals(InventoryName.FOOD_BUILDER.getName())
                && !title.equals(InventoryName.EDITABLE_FOOD_LIST.getName())
                && !title.equals(InventoryName.FOOD_EDITOR.getName()))
            return;



        e.setCancelled(true);

        Player staff = (Player) e.getWhoClicked();
        ItemStack icon = e.getCurrentItem();

        //Listener for player menu
        if (title.equals(InventoryName.MAIN_MENU.getName())) {
            mainMenu(e,staff, icon);
        }
        if (title.equals(InventoryName.FOOD_LIST.getName())) {
            Bukkit.getConsoleSender().sendMessage("FoodList");
            foodListMenu(e,staff, icon);
        }
        if (title.equals(InventoryName.FOOD_BUILDER.getName())) {
           foodBuilderMenu(e,staff, icon);
        }
        if (title.equals(InventoryName.EDITABLE_FOOD_LIST.getName())) {
            foodEditorListMenu(e,staff, icon);
        }
        if (title.equals(InventoryName.FOOD_EDITOR.getName())) {
            foodEditorMenu(e,staff, icon);
        }




        else {
            e.setCancelled(true);
        }
    }
    private  void foodEditorMenu(InventoryClickEvent e, Player staff, ItemStack icon){
        if ((e.getRawSlot() >= 0 && e.getRawSlot() < InventoryName.FOOD_EDITOR.getSize())) {
            NBTItem nbtItem = new NBTItem(icon);
            if (nbtItem.getBoolean("Edit_DisplayName")){
                Bukkit.getConsoleSender().sendMessage(nbtItem.getString("Id"));
                pendingEditDisplayName.put(staff,nbtItem.getString("Id"));
                staff.sendMessage(ChatColor.YELLOW + "Escribe el nuevo DisplayName:");
                staff.closeInventory();
            }
            if (nbtItem.getBoolean("Edit_Saturation")){
                pendingEditSaturation.put(staff,nbtItem.getString("Id"));
                staff.sendMessage(ChatColor.YELLOW + "Escribe el nuevo DisplayName:");
                staff.closeInventory();
            }
            if (nbtItem.getBoolean("Edit_Feed")){
                pendingEditFeed.put(staff,nbtItem.getString("Id"));
                staff.sendMessage(ChatColor.YELLOW + "Escribe el nuevo DisplayName:");
                staff.closeInventory();
            }
            if (nbtItem.getBoolean("Edit_Texture")){
                pendingEditTexture.put(staff,nbtItem.getString("Id"));
                staff.sendMessage(ChatColor.YELLOW + "Escribe el nuevo DisplayName:");
                staff.closeInventory();
            }
            if (nbtItem.getBoolean("Edit_Sound")){
                pendingEditSound.put(staff,nbtItem.getString("Id"));
                staff.sendMessage(ChatColor.YELLOW + "Escribe el nuevo DisplayName:");
                staff.closeInventory();
            }
            if (nbtItem.getBoolean("Back")){
                MainMenu mainMenu = new MainMenu(staff,0);
                staff.openInventory(mainMenu.getInventory());
            }
        } else {
            if (e.getRawSlot() >= e.getInventory().getSize() && !e.isShiftClick()) {
                e.setCancelled(false);
            }
        }
    }
    private  void foodEditorListMenu(InventoryClickEvent e, Player staff, ItemStack icon){
        if ((e.getRawSlot() >= 0 && e.getRawSlot() < InventoryName.EDITABLE_FOOD_LIST.getSize())) {
            NBTItem nbtItem = new NBTItem(icon);
            if (nbtItem.getBoolean("Eatable")){
                File foodFile = new File(Main.getInstance().getDataFolder() + "/Foods", nbtItem.getString("Id") + ".json");
                Food food = new Food(foodFile);
                FoodEditor foodEditor = new FoodEditor(staff,food);

                staff.openInventory(foodEditor.getInventory());
            }
            if (nbtItem.getBoolean("Back")){
                MainMenu mainMenu = new MainMenu(staff,0);
                staff.openInventory(mainMenu.getInventory());
            }

        } else {
            if (e.getRawSlot() >= e.getInventory().getSize() && !e.isShiftClick()) {
                e.setCancelled(false);
            }
        }
    }

    private void foodBuilderMenu(InventoryClickEvent e, Player staff, ItemStack icon){
        if ((e.getRawSlot() >= 0 && e.getRawSlot() < InventoryName.FOOD_BUILDER.getSize())) {
            NBTItem nbtItem = new NBTItem(icon);
            Bukkit.getConsoleSender().sendMessage("FoodAdder");


            if (nbtItem.getBoolean("FoodAdder")){
                Bukkit.getConsoleSender().sendMessage("FoodAdder");
                staff.sendMessage(ChatColor.YELLOW + "Escribe el id de la comida:");
                pendingFoodNames.put(staff, "");
                staff.closeInventory();
            }



        } else {
            if (e.getRawSlot() >= e.getInventory().getSize() && !e.isShiftClick()) {
                e.setCancelled(false);
            }
        }
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (pendingEditDisplayName.containsKey(player)) {
            event.setCancelled(true);
            String foodName = event.getMessage();
            String id = pendingEditDisplayName.get(player);

            pendingEditDisplayName.remove(player);
            Bukkit.getConsoleSender().sendMessage(id);
            File foodFile = new File(Main.getInstance().getDataFolder() + "/Foods", id + ".json");
            JsonObject jsonObject = readJsonFromFile(foodFile);

            jsonObject.addProperty("DisplayName",foodName);
            player.sendMessage(MessageUtils.getColoredMessage("&aHas cambiado el DisplayName de " + id + " a " + foodName));
            writeJsonToFile(jsonObject, foodFile);
            Bukkit.getScheduler().runTask(Main.getInstance(),() ->{
                Food food = new Food(foodFile);
                FoodEditor foodEditor = new FoodEditor(player,food);
                player.openInventory(foodEditor.getInventory());
            });
        }
        if (pendingEditTexture.containsKey(player)) {
            event.setCancelled(true);
            String texture = event.getMessage();
            String id = pendingEditTexture.get(player);

            pendingEditTexture.remove(player);
            Bukkit.getConsoleSender().sendMessage(id);
            File foodFile = new File(Main.getInstance().getDataFolder() + "/Foods", id + ".json");
            JsonObject jsonObject = readJsonFromFile(foodFile);

            jsonObject.addProperty("texture",texture);
            player.sendMessage(MessageUtils.getColoredMessage("&aHas cambiado la textura de: " + id));
            writeJsonToFile(jsonObject, foodFile);
            Bukkit.getScheduler().runTask(Main.getInstance(),() ->{
               Food food = new Food(foodFile);
               FoodEditor foodEditor = new FoodEditor(player,food);
               player.openInventory(foodEditor.getInventory());
            });


        }
        if (pendingEditFeed.containsKey(player)) {
            event.setCancelled(true);
            String feed = event.getMessage();
            String id = pendingEditFeed.get(player);

            pendingEditFeed.remove(player);
            Bukkit.getConsoleSender().sendMessage(id);
            File foodFile = new File(Main.getInstance().getDataFolder() + "/Foods", id + ".json");
            JsonObject jsonObject = readJsonFromFile(foodFile);

            jsonObject.addProperty("feed", Double.parseDouble(feed));
            player.sendMessage(MessageUtils.getColoredMessage("&aHas cambiado la feed de: " + id));
            writeJsonToFile(jsonObject, foodFile);
            Bukkit.getScheduler().runTask(Main.getInstance(),() ->{
                Food food = new Food(foodFile);
                FoodEditor foodEditor = new FoodEditor(player,food);
                player.openInventory(foodEditor.getInventory());
            });
        }
        if (pendingEditSaturation.containsKey(player)) {
            event.setCancelled(true);
            String saturation = event.getMessage();
            String id = pendingEditSaturation.get(player);

            pendingEditSaturation.remove(player);
            Bukkit.getConsoleSender().sendMessage(id);
            File foodFile = new File(Main.getInstance().getDataFolder() + "/Foods", id + ".json");
            JsonObject jsonObject = readJsonFromFile(foodFile);

            jsonObject.addProperty("Saturation", Double.parseDouble(saturation));
            player.sendMessage(MessageUtils.getColoredMessage("&aHas cambiado la saturacion de: " + id));
            writeJsonToFile(jsonObject, foodFile);
            Bukkit.getScheduler().runTask(Main.getInstance(),() ->{
                Food food = new Food(foodFile);
                FoodEditor foodEditor = new FoodEditor(player,food);
                player.openInventory(foodEditor.getInventory());
            });
        }
        if (pendingEditSound.containsKey(player)) {
            event.setCancelled(true);
            String sound = event.getMessage();
            String id = pendingEditSound.get(player);

            pendingEditSound.remove(player);
            Bukkit.getConsoleSender().sendMessage(id);
            File foodFile = new File(Main.getInstance().getDataFolder() + "/Foods", id + ".json");
            JsonObject jsonObject = readJsonFromFile(foodFile);

            jsonObject.addProperty("sound", sound);
            player.sendMessage(MessageUtils.getColoredMessage("&aHas cambiado la saturacion de: " + id));
            writeJsonToFile(jsonObject, foodFile);
            Bukkit.getScheduler().runTask(Main.getInstance(),() ->{
                Food food = new Food(foodFile);
                FoodEditor foodEditor = new FoodEditor(player,food);
                player.openInventory(foodEditor.getInventory());
            });
        }
        if (pendingFoodNames.containsKey(player)) {
            event.setCancelled(true);
            String foodName = event.getMessage();
            pendingFoodNames.remove(player);

            File foodFile = new File(Main.getInstance().getDataFolder() + "/Foods", foodName + ".json");
            Food.FoodData foodData = new Food.FoodData(foodName,0.0,0.0," "," ", List.of(" "),List.of(" "));
            saveFoodJSON(foodFile,foodData);
            player.sendMessage(ChatColor.GREEN + "Has creado una nueva comida con el ID: " + foodName);

            Bukkit.getScheduler().runTask(Main.getInstance(),() ->{
                Food food = new Food(foodFile);
                FoodEditor foodEditor = new FoodEditor(player,food);
                player.openInventory(foodEditor.getInventory());
            });
        }

    }




    private void mainMenu(InventoryClickEvent e, Player staff, ItemStack icon) {
        if ((e.getRawSlot() >= 0 && e.getRawSlot() < InventoryName.MAIN_MENU.getSize())) {
            NBTItem nbtItem = new NBTItem(icon);


            if (nbtItem.getBoolean("FoodList")){
                FoodList foodList = new FoodList(staff,0);
                staff.openInventory(foodList.getInventory());


            }
            if (nbtItem.getBoolean("FoodBuilder")){
                FoodBuilder foodBuilder = new FoodBuilder(staff);
                staff.openInventory(foodBuilder.getInventory());


            }
            if (nbtItem.getBoolean("FoodListEditor")){
                EditableFoodList editableFoodList = new EditableFoodList(staff,1);
                staff.openInventory(editableFoodList.getInventory());


            }

        } else {
            if (e.getRawSlot() >= e.getInventory().getSize() && !e.isShiftClick()) {
                e.setCancelled(false);
            }
        }
    }
    private void foodListMenu(InventoryClickEvent e, Player staff, ItemStack icon) {
        if ((e.getRawSlot() >= 0 && e.getRawSlot() < InventoryName.FOOD_LIST.getSize())) {
            NBTItem nbtItem = new NBTItem(icon);


            if (nbtItem.getBoolean("Eatable")){
                PlayerUtils playerUtils = new PlayerUtils();
                String id = nbtItem.getString("Id");
                File foodFile = new File(main.getDataFolder() + "/Foods", id + ".json");
                if (!foodFile.exists()) { //Si la comida no existe
                    staff.sendMessage("La comida especificada no existe.");
                    return;
                }
                Food food = new Food(foodFile);
                if (playerUtils.checkFoodOnInventory(staff,food)){return;}

                ItemStack newfood = food.getItem();

                staff.getInventory().addItem(newfood);


            }
            if (nbtItem.getBoolean("Back")){
                MainMenu mainMenu = new MainMenu(staff,0);
                staff.openInventory(mainMenu.getInventory());
            }


        } else {
            if (e.getRawSlot() >= e.getInventory().getSize() && !e.isShiftClick()) {
                e.setCancelled(false);
            }
        }
    }























    private static void writeJsonToFile(JsonObject jsonObject, File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static JsonObject readJsonFromFile(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            JsonParser jsonParser = new JsonParser();
            return jsonParser.parse(fileReader).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void saveFoodJSON(File file, Food.FoodData foodData) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(foodData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
