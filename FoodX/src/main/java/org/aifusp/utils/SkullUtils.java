package org.aifusp.utils;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;

public class SkullUtils {

    public static ItemStack getCustomSkull(String texture) {
        // Obtener el perfil del jugador asociado con la textura
        String profile = MojangAPI.getPlayerProfile(texture);

        // Verificar si se obtuvo el perfil correctamente
        if (profile != null) {
            // Decodificar la información del perfil para obtener la textura
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(new StringReader(profile));
            JsonObject profileObject = element.getAsJsonObject();

            // Obtener la textura desde el perfil
            String textureValue = profileObject.getAsJsonObject("textures")
                    .getAsJsonObject("SKIN").get("url").getAsString();

            // Crear y devolver el ItemStack con la textura
            return createSkullWithTexture(textureValue);
        } else {
            // Si hubo un error al obtener el perfil, retornar null o manejar el error según sea necesario
            return null;
        }
    }
    private  static ItemStack createSkullWithTexture(String texture) {
        // Crear el ItemStack de la cabeza del jugador
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        // Establecer la textura personalizada en la cabeza del jugador
        setTexture(meta, texture);

        skull.setItemMeta(meta);
        return skull;
    }
    public static String decodeBase64(String b64) {
        byte[] decodedBytes = Base64.getDecoder().decode(b64);
        String jsonString = new String(decodedBytes);

        // Buscar la URL de la textura
        int startIndex = jsonString.indexOf("http://textures.minecraft.net/texture/");
        if (startIndex != -1) {
            int endIndex = jsonString.indexOf('"', startIndex);
            if (endIndex != -1) {
                Bukkit.getConsoleSender().sendMessage(jsonString.substring(startIndex, endIndex));
                return jsonString.substring(startIndex, endIndex);
            }
        }

        return null;
    }

    private static void setTexture(SkullMeta meta, String url) {




        // Obtener la URL de la textura

        if (url != null) {
            // Crear el perfil del jugador con la URL de la textura
            GameProfile profile = new GameProfile(UUID.fromString("f18ec5b4-a790-4bf9-a22a-0f1d0b160b20"), null);
            profile.getProperties().put("textures", new Property("textures", url));

            try {
                // Establecer el perfil del jugador en el SkullMeta
                Field profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    public static String getBase64Texture(String url) {
        return Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}").getBytes());
    }
    public static ItemStack getPlayerHead(String texture) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();

        // Convertir la URL de la textura en un string base64
//        String base64Texture = Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"" + textureURL + "\"}}}").getBytes());

        // Establecer la textura en la cabeza del jugador
        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, createProfile(texture));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        playerHead.setItemMeta(skullMeta);
        return playerHead;
    }


    private static String getTextureURL(String textureJson) {
        // Parsear el JSON para obtener la URL de la textura
        try {
            JsonObject jsonObject = new JsonParser().parse(textureJson).getAsJsonObject();
            JsonObject textures = jsonObject.getAsJsonObject("textures");
            JsonObject skin = textures.getAsJsonObject("SKIN");
            return skin.get("url").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    private static String getTextureName(String textureJson) {
        // Parsear el JSON para obtener el nombre de la textura
        try {
            JsonObject jsonObject = new JsonParser().parse(textureJson).getAsJsonObject();
            JsonObject textures = jsonObject.getAsJsonObject("textures");
            JsonObject skin = textures.getAsJsonObject("SKIN");
            return skin.get("url").getAsString();
        } catch (JsonSyntaxException | IllegalStateException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static GameProfile createProfile(String texture) {
        UUID uuid = UUID.fromString("f18ec5b4-a790-4bf9-a22a-0f1d0b160b20");
        GameProfile profile = new GameProfile(uuid, "FoodX_By_iFuSp");
        profile.getProperties().put("textures", new Property("textures", texture));
        return profile;
    }



    private static class PropertyMap {
        public void put(String key, com.mojang.authlib.properties.Property value) {
            // No es necesario implementar esta funcionalidad para nuestro caso
        }
    }


}
