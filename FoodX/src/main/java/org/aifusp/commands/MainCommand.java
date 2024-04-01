package org.aifusp.commands;

import me.aifusp.foodx.gui.menu.EditableFoodList;
import me.aifusp.foodx.gui.menu.FoodList;
import me.aifusp.foodx.gui.menu.MainMenu;
import org.aifusp.Main;
import org.aifusp.utils.Food;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;

public class MainCommand implements CommandExecutor {
    private Main plugin;

    public MainCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("FoodX")) {


            if (args.length == 2 && args[0].equalsIgnoreCase("give") && senderIsPlayer(sender)) { //foodx give <Food>
                Player player = (Player) sender;

                if (!checkPlayerHasPermission(player,"give")){return true;}

                File foodFile = getFoodFileByName(args[1]);
                Food food = new Food(foodFile);

                player.getInventory().addItem(food.getItem());
                return true;
            } else if (args.length >= 3 && args[0].equalsIgnoreCase("give")) { //foodx give <Food> <Player> (amount))
                Player player = null;
                if (senderIsPlayer(sender)){
                    player = (Player) sender;
                    if (!checkPlayerHasPermission(player,"give")){return true;}

                }
                
                Player selPlayer = Bukkit.getPlayer(args[2]);
                File foodFile = getFoodFileByName(args[1]);
                Food food = new Food(foodFile);
                int amount = 1;
                if (selPlayer == null && senderIsPlayer(sender)){player.sendMessage("El jugador no ha sido encontrado"); return false;
                } else if ((selPlayer == null && !(senderIsPlayer(sender)))) {Bukkit.getConsoleSender().sendMessage("El jugador no ha sido encontrado");}

                if (args.length == 4){amount = Integer.parseInt(args[3]);}
                ItemStack foodItem = food.getItem();
                foodItem.setAmount(amount);

                player.getInventory().addItem(foodItem);


                return true;
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("main") && senderIsPlayer(sender)) {
                Player player = (Player) sender;
                if (!checkPlayerHasPermission(player,"main")){return true;}

                MainMenu mainMenu = new MainMenu(player,0);
                player.openInventory(mainMenu.getInventory());
                return true;
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("editor") && senderIsPlayer(sender)) {
                Player player = (Player) sender;
                if (!checkPlayerHasPermission(player,"editor")){return true;}

                EditableFoodList editableFoodList = new EditableFoodList(player,0);

                player.openInventory(editableFoodList.getInventory());
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("list") && senderIsPlayer(sender)) { //foodx give <Food> <Player>)
                Player player = (Player) sender;
                if (!checkPlayerHasPermission(player,"give")){return true;}

                FoodList foodList = new FoodList(player,0);
                player.openInventory(foodList.getInventory());
            } else if (args.length == 1 && args[0].equalsIgnoreCase("totalFoods") && !senderIsPlayer(sender)) {
                Bukkit.getConsoleSender().sendMessage(String.valueOf(getFoodsInt2()));

            }

        }
        return false;
    }




    public @Nullable List<String> onTabComplete(CommandSender sender,Command command,String label,String[] args){

        return null;
    }




    private File getFoodFileByName(String foodname){
        File foodFile = new File(plugin.getDataFolder() + "/Foods", foodname + ".json");
        if (foodFile.exists()) { //Si la comida no existe
            return foodFile;
        }
        return null;
    }

    private boolean senderIsPlayer(CommandSender sender){
        if (!(sender instanceof Player)) {
            return false;
        }
        return true;
    }
    private boolean checkPlayerHasPermission(Player player,String permission){
        if (!player.hasPermission("foodx."+permission)) {
            player.sendMessage("No tienes permiso para usar este comando.");
            return false;
        }
        return true;
    }
    public int getFoodsInt2(){
        File foodsFolder = new File(plugin.getDataFolder() + "/Foods");

        if (!foodsFolder.exists() || !foodsFolder.isDirectory()) {
            System.out.println("La carpeta 'Foods' no existe o no es una carpeta válida.");
            return 0;
        }

        File[] archivos = foodsFolder.listFiles();
        if (archivos == null) {
            System.out.println("No se pudo obtener la lista de archivos de la carpeta 'Foods'.");
            return 0;
        }

        return archivos.length;
    }

}

