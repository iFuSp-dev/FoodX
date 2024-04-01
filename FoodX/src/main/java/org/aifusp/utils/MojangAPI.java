package org.aifusp.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MojangAPI {

    public static String getPlayerProfile(String textureURL) {
        try {
            // Construir la URL para obtener el perfil del jugador asociado con la textura
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + textureURL);

            // Abrir una conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud HTTP
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Leer la respuesta JSON
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Parsear la respuesta JSON
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(new StringReader(response.toString()));
            JsonObject jsonResponse = element.getAsJsonObject();

            // Extraer el perfil del jugador
            String profile = jsonResponse.getAsJsonArray("properties").get(0)
                    .getAsJsonObject().get("value").getAsString();

            // Cerrar la conexión y el lector
            connection.disconnect();
            reader.close();

            return profile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
