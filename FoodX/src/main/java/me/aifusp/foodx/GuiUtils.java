package me.aifusp.foodx;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiUtils {


    public void TopBorders(Inventory GUI){
        GUI.setItem(0,WhitePane());
        GUI.setItem(1,WhitePane());
        GUI.setItem(2,WhitePane());
        GUI.setItem(3,WhitePane());
        GUI.setItem(4,WhitePane());
        GUI.setItem(5,WhitePane());
        GUI.setItem(6,WhitePane());
        GUI.setItem(7,WhitePane());
        GUI.setItem(8,WhitePane());

    }
    public void mainBorders(Inventory GUI){
        GUI.setItem(0,GrayPane());
        GUI.setItem(1,WhitePane());
        GUI.setItem(7,GrayPane());
        GUI.setItem(8,WhitePane());
        GUI.setItem(10,GrayPane());
        GUI.setItem(9,WhitePane());
        GUI.setItem(17,GrayPane());
        GUI.setItem(16,WhitePane());
        GUI.setItem(18,GrayPane());
        GUI.setItem(19,WhitePane());
        GUI.setItem(25,GrayPane());
        GUI.setItem(26,WhitePane());
    }
















    private ItemStack WhitePane(){
        ItemStack pane = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(" ");
        pane.setItemMeta(meta);

        return pane;
    }
    private  ItemStack GrayPane(){
        ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(" ");
        pane.setItemMeta(meta);
        return pane;
    }
}
