package me.aifusp.foodx.gui.menu;

import me.aifusp.foodx.Buttons;
import me.aifusp.foodx.GuiUtils;
import me.aifusp.foodx.InventoryName;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class FoodBuilder {
    private Player staff;
    private GuiUtils utils = new GuiUtils();

    private Inventory inventory;
    private Buttons buttons;
    public FoodBuilder(Player staff) {
        this.staff = staff;
        this.buttons = new Buttons();

        createInventory();
    }


    public void createInventory() {
        inventory = Bukkit.createInventory(staff, InventoryName.FOOD_BUILDER.getSize(), InventoryName.FOOD_BUILDER.getName());
        inventory.setItem(1,buttons.addFood());




    }
    public Inventory getInventory() {
        return this.inventory;
    }

}
