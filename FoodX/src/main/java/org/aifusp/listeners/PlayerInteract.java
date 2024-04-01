package org.aifusp.listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.aifusp.Main;
import org.aifusp.utils.CommandManager;
import org.aifusp.utils.Food;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PlayerInteract implements Listener {
    private Main plugin;

    public PlayerInteract(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (event.getAction().toString().contains("RIGHT") && item.getType() == Material.PLAYER_HEAD) {
            if (isEatable(item)) {

                NBTItem itemNBT = new NBTItem(item);
                double foodToAdd = itemNBT.getDouble("Feed");
                double saturationToAdd = itemNBT.getDouble("Saturation");

                if (player.getFoodLevel() == 20){
                    event.setCancelled(true);
                    return;
                }

                if (itemNBT.hasTag("Sound")){
                    String soundName = itemNBT.getString("Sound");
                    if (isValidSound(soundName)){
                        player.getWorld().playSound(player.getLocation(), Sound.valueOf(soundName.toUpperCase()), 1.0f, 1.0f);
                    }
                }

                // Obtener comandos del NBT "Commands"
                String commandsString = itemNBT.getString("Commands");
                List<String> commands = new ArrayList<>();
                if (commandsString != null && !commandsString.isEmpty()) {
                    commands = Arrays.asList(commandsString.split(","));
                }
                if (commands.isEmpty()) {
                    System.out.println("La lista de comandos está vacía.");
                }


                double currentSaturation = player.getSaturation();
                double maxSaturation = player.getFoodLevel();
                double newSaturation = Math.min(currentSaturation + saturationToAdd, maxSaturation);

                player.setSaturation((float) newSaturation);
                int newFoodLevel = Math.min(player.getFoodLevel() + (int) foodToAdd, 20);
                player.setFoodLevel(newFoodLevel);

                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                } else {
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                }

                CommandManager commandManager = new CommandManager();
                commandManager.ExecuteCommand(player,commands);
//                executeCommands(player, commands);
                event.setCancelled(true);
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setUseItemInHand(Event.Result.DENY);
            }
        }
    }

    private boolean isEatable(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        return nbtItem.getBoolean("Eatable");
    }

    public void executeCommands(Player player, List<String> commands) {
        Iterator<String> iterator = commands.iterator();
        while (iterator.hasNext()) {
            String command = iterator.next();
            if (command.startsWith("Delay")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    try {
                        int delaySeconds = Integer.parseInt(parts[1]);
                        // Configurar un temporizador para ejecutar los comandos restantes después del retraso
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            List<String> remainingCommands = new ArrayList<>(commands.subList(commands.indexOf(command) + 1, commands.size()));
                            executeCommands(player, remainingCommands);
                        }, delaySeconds * 20L); // Convertir segundos a ticks (20 ticks por segundo)
                        return; // Salir del método para evitar ejecutar otros comandos antes del retraso
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // Ejecutar el comando normalmente
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
            }
        }

    }
    public boolean isValidSound(String soundName) {
        try {
            Sound sound = Sound.valueOf(soundName.toUpperCase());
            // Si no se lanza una excepción, significa que el sonido es válido
            return true;
        } catch (IllegalArgumentException e) {
            // Si se lanza una excepción, significa que el sonido no existe
            return false;
        }
    }
}
