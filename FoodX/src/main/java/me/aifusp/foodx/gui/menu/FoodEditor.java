package me.aifusp.foodx.gui.menu;

import de.tr7zw.nbtapi.NBTItem;
import me.aifusp.foodx.Buttons;
import me.aifusp.foodx.GuiUtils;
import me.aifusp.foodx.InventoryName;
import org.aifusp.utils.Food;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FoodEditor {
    private Player staff;
    private Food food;

    private Buttons buttons;
    public GuiUtils utils = new GuiUtils();

    private List<Player> onlinePlayers;
    private Inventory inventory;

    public FoodEditor(Player staff, Food food) {
        this.staff = staff;
        this.buttons = new Buttons();
        this.food = food;

        createInventory();
    }

    public void createInventory() {
        inventory = Bukkit.createInventory(staff, InventoryName.FOOD_EDITOR.getSize(), InventoryName.FOOD_EDITOR.getName());
        utils.TopBorders(inventory);
        inventory.setItem(0,food.getItem());
        inventory.setItem(2,addFoodId(buttons.DisplayNameEditor()));
        inventory.setItem(3,addFoodId(buttons.FeedEditor()));
        inventory.setItem(4,addFoodId(buttons.SaturationEditor()));
        inventory.setItem(5,addFoodId(buttons.TextureEditor()));
        inventory.setItem(6,addFoodId(buttons.SoundEditor()));
        inventory.setItem(8,buttons.Back());


    }



    private ItemStack addFoodId(ItemStack item){
        String Id = this.food.getId();
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("Id",Id);
        nbtItem.applyNBT(item);
        return item;
    }




















    public Inventory getInventory() {
        return this.inventory;
    }

}
