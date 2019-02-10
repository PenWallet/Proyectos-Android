package com.example.ofunes.pennypanphone.Utiliidades;

import android.util.Base64;
import android.util.Log;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;

public class JWTUtils {

    public static Cliente getClienteFromToken(String token)
    {
        Cliente cliente = null;

        String[] strings = token.split("\\.");

        String payload = getJson(strings[1]);

        if(payload != null)
        {
            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject)parser.parse(payload);
            JsonObject data = json.getAsJsonObject("data");

            String personaJson = data.toString();

            Gson gson = new GsonBuilder().create();
            cliente = gson.fromJson(personaJson, Cliente.class);
        }

        return cliente;
    }

    public static void decoded(String JWTEncoded) {
        String[] split = JWTEncoded.split("\\.");
        Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
        Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
    }

    private static String getJson(String strEncoded){
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        String json = null;
        try
        {
            json = new String(decodedBytes, "UTF-8");
        }catch (UnsupportedEncodingException e) { Log.e("Error", "Error in getJson in JWTUtils"); }

        return json;
    }
}