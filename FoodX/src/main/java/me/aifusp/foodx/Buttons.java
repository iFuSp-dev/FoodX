package me.aifusp.foodx;

import de.tr7zw.nbtapi.NBTItem;
import org.aifusp.utils.MessageUtils;
import org.aifusp.utils.SkullUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Buttons {

    public ItemStack Back(){
        ItemStack book = new ItemStack(Material.BOOK);
        NBTItem nbtItem = new NBTItem(book);
        nbtItem.setBoolean("Back",true);
        nbtItem.applyNBT(book);
        ItemMeta itemMeta = book.getItemMeta();
        itemMeta.setDisplayName(MessageUtils.getColoredMessage("#2F829D&l&oBack"));
        book.setItemMeta(itemMeta);
        return book;
    }
    public ItemStack DevelperHead(){
        String textureId = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTE0MzMyMzBhYjlmZGNhMDE5ZTQ4MTIwYjBhMzZlN2ZlODE4N2VkZWRhZDVmNGYwZTc3ODNhNjgwYThmYzRmZSJ9fX0=";
        ItemStack playerHead = SkullUtils.getPlayerHead(textureId);

        ItemMeta meta = playerHead.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("#0FF6AC&nDeveloper:&f #F50087&naifusp"));
        lore.add(MessageUtils.getColoredMessage("&f&o&nCheck &f&o&n&laifusp´s &f&o&nplugins"));
        meta.setLore(lore);
        meta.setDisplayName(" ");
        playerHead.setItemMeta(meta);

        return playerHead;
    }
    public ItemStack FoodList(){
        ItemStack itemStack = new ItemStack(Material.FLOWER_BANNER_PATTERN);

        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        meta = itemStack.getItemMeta();

        lore.add(MessageUtils.getColoredMessage("&oClick to see all the"));
        lore.add(MessageUtils.getColoredMessage("&oFood created"));
        meta.setLore(lore);
        meta.setDisplayName(MessageUtils.getColoredMessage("#fd3093&l&nFood #bc246e&n&lList"));
        itemStack.setItemMeta(meta);
        ItemMeta meta2 = itemStack.getItemMeta();
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("FoodList",true);
        itemStack = nbtItem.getItem();
        return itemStack;
    }
    public ItemStack FoodConstructor(){
        ItemStack itemStack = new ItemStack(Material.NETHER_STAR);

        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&oClick to add"));
        lore.add(MessageUtils.getColoredMessage("&fNEW FOOD"));
        meta.setLore(lore);
        meta.setDisplayName(MessageUtils.getColoredMessage("#fd3093&l&nFood #ffffff&n&lCreator"));
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("FoodBuilder",true);
        itemStack = nbtItem.getItem();
        return itemStack;
    }
    public ItemStack InfoPlugin(){
        String textureId ="eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWYzM2U3YmIxMjU2YTEyYjVjODhlNzA1ZjIxMjc0ZmQ4NjE4YmJkZTkzYzBkZDNlMjJkOWRiY2YwYjNhMTJiMyJ9fX0=";
        ItemStack playerHead = SkullUtils.getPlayerHead(textureId);

        ItemMeta meta = playerHead.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&oClick to see"));
        lore.add(MessageUtils.getColoredMessage("&othe Plugin Info"));
        meta.setLore(lore);
        meta.setDisplayName(MessageUtils.getColoredMessage("#F60F0F&l&o&nPlugin&f #F6B40F&l&o&nInfo"));
        playerHead.setItemMeta(meta);
        ItemMeta meta2 = playerHead.getItemMeta();
        NBTItem nbtItem = new NBTItem(playerHead);
        nbtItem.setBoolean("PluginInfo",true);
        playerHead = nbtItem.getItem();
        return playerHead;
    }
    public void CreatePages(Inventory GUI){
        ItemStack itemStack = new ItemStack(Material.MAP);

        for (int i = 1; i<9; i++){
            itemStack.setAmount(i);

            NBTItem nbtItem = new NBTItem(itemStack);
            nbtItem.setInteger("Page",i);
            nbtItem.applyNBT(itemStack);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(MessageUtils.getColoredMessage("#F9F33CPage "+i));
            itemStack.setItemMeta(meta);

            GUI.setItem(36+i,itemStack.clone());
        }
    }
    public ItemStack addFood(){
        ItemStack itemStack = new ItemStack(Material.NETHER_STAR);

        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&oClick to add"));
        lore.add(MessageUtils.getColoredMessage("&othe NEW FOOD"));
        meta.setLore(lore);
        meta.setDisplayName(MessageUtils.getColoredMessage("#F60F0F&l&o&nFood&f #F6B40F&l&o&nBuilder"));
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("FoodAdder",true);
        itemStack = nbtItem.getItem();
        return itemStack;
    }
    public ItemStack FoodListEditor(){
        ItemStack itemStack = new ItemStack(Material.WRITABLE_BOOK);

        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&oClick to edit"));
        lore.add(MessageUtils.getColoredMessage("&oFOODS"));
        meta.setLore(lore);
        meta.setDisplayName(MessageUtils.getColoredMessage("#fd3093&l&nFood #20c6fe&n&lEditor"));
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("FoodListEditor",true);
        itemStack = nbtItem.getItem();
        return itemStack;
    }
    public ItemStack DisplayNameEditor(){
        ItemStack itemStack = new ItemStack(Material.CLOCK);

        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&oClick to edit"));
        lore.add(MessageUtils.getColoredMessage("&oDisplayName"));
        meta.setLore(lore);
        meta.setDisplayName(MessageUtils.getColoredMessage("#2dedfb&n&lDisplay#2dfb45&n&lName"));
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("Edit_DisplayName",true);
        itemStack = nbtItem.getItem();
        return itemStack;
    }
    public ItemStack TextureEditor(){
        ItemStack itemStack = new ItemStack(Material.CLAY_BALL);

        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&oClick to edit"));
        lore.add(MessageUtils.getColoredMessage("&fTexture"));
        meta.setLore(lore);
        meta.setDisplayName(MessageUtils.getColoredMessage("#fb1fff&l&nTexture"));
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("Edit_Texture",true);
        itemStack = nbtItem.getItem();
        return itemStack;
    }
    public ItemStack FeedEditor(){
        ItemStack itemStack = new ItemStack(Material.COOKED_CHICKEN);

        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&oClick to edit"));
        lore.add(MessageUtils.getColoredMessage("&fFeed Amount"));
        meta.setLore(lore);
        meta.setDisplayName(MessageUtils.getColoredMessage("#fb9b2d&l&nFeed#1fffc7 Amount"));
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("Edit_Feed",true);
        itemStack = nbtItem.getItem();
        return itemStack;
    }
    public ItemStack SaturationEditor(){
        ItemStack itemStack = new ItemStack(Material.GOLDEN_CARROT);

        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&oClick to edit"));
        lore.add(MessageUtils.getColoredMessage("&fSaturation Amount"));
        meta.setLore(lore);
        meta.setDisplayName(MessageUtils.getColoredMessage("#fe3410&l&nSaturation#1fffc7 Amount"));
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("Edit_Saturation",true);
        itemStack = nbtItem.getItem();
        return itemStack;
    }
    public ItemStack SoundEditor(){
        ItemStack itemStack = new ItemStack(Material.MUSIC_DISC_PIGSTEP);

        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(MessageUtils.getColoredMessage("&oClick to edit"));
        lore.add(MessageUtils.getColoredMessage("&fSound id"));
        meta.setLore(lore);
        meta.setDisplayName(MessageUtils.getColoredMessage("&6&l&nSound&r #1fffc7Id"));
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("Edit_Sound",true);
        itemStack = nbtItem.getItem();
        return itemStack;
    }
    public ItemMeta getMeta(ItemStack item){
        return item.getItemMeta();
    }

}
