package me.aifusp.foodx.gui.menu;

import de.tr7zw.nbtapi.NBTItem;
import me.aifusp.foodx.Buttons;
import me.aifusp.foodx.GuiUtils;
import me.aifusp.foodx.InventoryName;
import org.aifusp.Main;
import org.aifusp.utils.Food;
import org.aifusp.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FoodList {
    private Player staff;
    private  int pagesReq;
    private int pageNum;
    private GuiUtils utils = new GuiUtils();

    private Inventory inventory;
    private Buttons buttons;
    public FoodList(Player staff, int pageNumber) {
        this.staff = staff;
        this.pageNum = pageNumber;
        this.buttons = new Buttons();

        createInventory();
    }



    public void createInventory() {
        inventory = Bukkit.createInventory(staff, InventoryName.FOOD_LIST.getSize(), InventoryName.FOOD_LIST.getName());
        utils.TopBorders(inventory);

        CreaeteFoods();



    }

    private void CreaeteFoods(){
        File dataFolder = Main.getInstance().getDataFolder();
        File directory = new File(dataFolder + "/Foods");
        Bukkit.getConsoleSender().sendMessage(directory.getAbsolutePath());
        File[] files = directory.listFiles();
        List<File> jsonFiles = new ArrayList<>();
        if (files != null) {
            Bukkit.getConsoleSender().sendMessage("Files no es null");
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".json")) {
                    jsonFiles.add(file);
                }
            }
        }else {Bukkit.getConsoleSender().sendMessage("Files es null");}

        int index = 9;
        for (File file : jsonFiles){
            Bukkit.getConsoleSender().sendMessage("Creando comida");
            Food food = new Food(file);
            ItemStack comia = food.getItem();
            inventory.setItem(index,comia);
            index++;

        }

        inventory.setItem(36,buttons.Back());
        buttons.CreatePages(inventory);
    }





    public Inventory getInventory(){
        return this.inventory;
    }
}
