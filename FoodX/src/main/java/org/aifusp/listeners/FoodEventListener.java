package org.aifusp.listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.aifusp.Main;
import org.aifusp.utils.Food;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.Objects;

public class FoodEventListener implements Listener {
    private Main plugin;

    public  FoodEventListener(Main plugin){this.plugin = plugin;}



    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
//        ItemStack pickedUpItem = event.getItem().getItemStack();
//        event.getItem().remove();
//        NBTItem nbtItem = new NBTItem(pickedUpItem);
//        if (nbtItem.getBoolean("Eatable")){
//            Food food = getFoodById(nbtItem.getString("Id"));
//            Bukkit.getConsoleSender().sendMessage(Integer.toString(pickedUpItem.getAmount()));
//            givePlayerItems(event.getPlayer(),food,pickedUpItem.getAmount());
//            Bukkit.getConsoleSender().sendMessage("Yass");
//        }
    }

    // Método para verificar si un ítem es un ítem de comida especial
    private boolean esComidaEspecial(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            String displayName = ChatColor.stripColor(item.getItemMeta().getDisplayName()); // Eliminar colores del nombre
            return displayName.equals("Comida Especial");
        }
        return false;
    }
    private boolean isEatable(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        return nbtItem.getBoolean("Eatable");
    }

    // Método para guardar los NBT Tags relacionados con la comida en los metadatos del ítem
    private void guardarTagsComidaEnMetadatos(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        NBTItem itemNBT = new NBTItem(itemStack);
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(
                    new NamespacedKey("foodx", "saturation"),
                    PersistentDataType.DOUBLE,
                    itemNBT.getDouble("Saturation")
            );
            itemMeta.getPersistentDataContainer().set(
                    new NamespacedKey("foodx", "food"),
                    PersistentDataType.DOUBLE,
                    itemNBT.getDouble("Food")
            );
            itemStack.setItemMeta(itemMeta);
        }
    }

    // Método para restaurar los NBT Tags relacionados con la comida desde los metadatos del ítem
    private void restaurarTagsComidaDesdeMetadatos(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            Double saturation = itemMeta.getPersistentDataContainer().get(
                    new NamespacedKey("foodx", "saturation"),
                    PersistentDataType.DOUBLE
            );
            Double food = itemMeta.getPersistentDataContainer().get(
                    new NamespacedKey("foodx", "food"),
                    PersistentDataType.DOUBLE
            );

            // Verificar si los valores se encontraron en los metadatos
            if (saturation != null && food != null) {
                // Actualizar los NBT Tags del ítem
                NBTItem nbtItem = new NBTItem(itemStack);
                nbtItem.setDouble("Saturation", saturation);
                nbtItem.setDouble("Food", food);
                // Aplicar los cambios al ítem
                itemStack.setItemMeta(nbtItem.getItem().getItemMeta());
            }
        }
    }

    private Food getFoodById(String id){
        File foodFile = getFoodFileById(id);
        Food food = new Food(foodFile);
        return food;
    }
    private File getFoodFileById(String id){
        File foodFile = new File(plugin.getDataFolder() + "/Foods", id + ".json");
        if (foodFile.exists()) { //Si la comida no existe
            return foodFile;
        }
        return null;
    }

    private void givePlayerItems(Player player, Food food, int amount) {
        int remainingAmount = amount;
        Bukkit.getConsoleSender().sendMessage("Remaining amount = " + Integer.toString(remainingAmount));
        // Primero verificamos si ya hay ítems en el inventario que coincidan
        ItemStack[] inventoryContents = player.getInventory().getContents();
        for (ItemStack itemStack : inventoryContents) {

            if (itemStack != null && itemStack.getType() == Material.PLAYER_HEAD ) {
                NBTItem nbtItem = new NBTItem(itemStack);
                if (Objects.equals(nbtItem.getString("Id"), food.getId())) {
                    int freeSpace = 64 - itemStack.getAmount(); // Espacio libre en el stack actual
                    if (freeSpace >= remainingAmount) {
                        // El stack actual puede contener todo lo que necesitamos
                        itemStack.setAmount(itemStack.getAmount() + remainingAmount);
                        Bukkit.getConsoleSender().sendMessage("Aumenta en el mismo stack");
                    } else {
                        // No hay suficiente espacio en el stack actual
                        itemStack.setAmount(64); // Llenamos este stack
                        remainingAmount -= freeSpace; // Actualizamos la cantidad restante
                    }
                }
            }
        }

        // Si todavía hay ítems por añadir
        while (remainingAmount > 0) {
            Bukkit.getConsoleSender().sendMessage("Remaining amount = " + Integer.toString(remainingAmount));

            ItemStack newItemStack = food.getItem();

            if (remainingAmount >64){
                remainingAmount -= 64;
                newItemStack.setAmount(64);
                player.getInventory().addItem(newItemStack);
            }else {
                newItemStack.setAmount(remainingAmount);
                player.getInventory().addItem(newItemStack);
                remainingAmount = 0;
            }
        }

    }


}
