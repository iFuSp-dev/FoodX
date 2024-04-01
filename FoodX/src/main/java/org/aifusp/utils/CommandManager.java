package org.aifusp.utils;

import org.aifusp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandManager {

    private Player player;
    private List<String> args;




    public void ExecuteCommand(Player player,List<String> args){
        Iterator<String> iterator = args.iterator();
        while (iterator.hasNext()) {
            String command = iterator.next();
            if (command.startsWith("Delay")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    try {
                        int delaySeconds = Integer.parseInt(parts[1]);
                        // Configurar un temporizador para ejecutar los comandos restantes después del retraso
                        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                            List<String> remainingCommands = new ArrayList<>(args.subList(args.indexOf(command) + 1, args.size()));
                            ExecuteCommand(player, remainingCommands);
                        }, delaySeconds * 20L); // Convertir segundos a ticks (20 ticks por segundo)
                        return; // Salir del método para evitar ejecutar otros comandos antes del retraso
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            } else if (command.startsWith("effect")) {
                Player rPlayer = player;
                String[] cargs = command.split(" ");
                PotionEffectType effectType = PotionEffectType.getByName(cargs[2]);
                if (effectType == null) {
                Bukkit.getConsoleSender().sendMessage("The provided effect is not valid");
                return;}
                int seconds = Integer.parseInt(cargs[3]) * 20;
                int amplifier = Integer.parseInt(cargs[4]) - 1;


                PotionEffect potionEffect = new PotionEffect(effectType,seconds,amplifier);
                rPlayer.addPotionEffect(potionEffect);

            }else if (command.startsWith("asConsole!")) {
                Player rPlayer = player;
                String acommand = command.substring(0,9);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),acommand.replace("%player%", rPlayer.getName()));


            }


        }
    }
}
