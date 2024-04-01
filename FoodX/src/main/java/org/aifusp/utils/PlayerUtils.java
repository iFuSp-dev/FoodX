package org.aifusp.utils;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class PlayerUtils {







    public boolean checkFoodOnInventory (Player player, Food food){
        ItemStack[] inventoryContents = player.getInventory().getContents();
        for (ItemStack itemStack : inventoryContents) {
            if (itemStack != null && itemStack.getType() == Material.PLAYER_HEAD) {
                NBTItem nbtItem = new NBTItem(itemStack);
                if (Objects.equals(nbtItem.getString("Id"), food.getId())) {
                    itemStack.setAmount(itemStack.getAmount() + 1);
                    Bukkit.getConsoleSender().sendMessage("Aumenta");
                    return true;
                }
            }
        }
        return false;
    }
}
