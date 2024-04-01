package me.aifusp.foodx.gui.menu;

import me.aifusp.foodx.Buttons;
import me.aifusp.foodx.GuiUtils;
import me.aifusp.foodx.InventoryName;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    private Player staff;

    private  int pagesReq;
    private int pageNum;
    private Buttons buttons;
    public GuiUtils utils = new GuiUtils();
    private int startSelection;
    private int playerHeadLoops;

    private List<Player> onlinePlayers;
    private Inventory inventory;

    public MainMenu(Player staff, int pageNumber) {
        this.staff = staff;
        this.pageNum = pageNumber;
        this.buttons = new Buttons();

        createInventory();
    }

    public void createInventory() {
     inventory = Bukkit.createInventory(staff, InventoryName.MAIN_MENU.getSize(), InventoryName.MAIN_MENU.getName());
        MagentaPane(inventory);
        PinkPane(inventory);


        inventory.setItem(0,buttons.DevelperHead());
        inventory.setItem(3,buttons.FoodList());
        inventory.setItem(4,buttons.FoodConstructor());
        inventory.setItem(8,buttons.InfoPlugin());
        inventory.setItem(5,buttons.FoodListEditor());
    }



    private void PinkPane(Inventory GUI){
        ItemStack itemStack = new ItemStack(Material.PINK_STAINED_GLASS_PANE);
        GUI.setItem(1,itemStack.clone());
        GUI.setItem(7,itemStack.clone());
    }
    private void MagentaPane(Inventory GUI){
        ItemStack itemStack = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        GUI.setItem(2,itemStack.clone());
        GUI.setItem(8,itemStack.clone());
    }


    public Inventory getInventory() {
        return this.inventory;
    }





}
