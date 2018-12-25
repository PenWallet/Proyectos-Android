package com.example.ofunes.pennypanphone.Retrofit;

import android.util.Base64;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GestoraRetrofitLoggedin {
    private Retrofit retrofit;
    private ListadoPedidosCallback listadoPedidosCallback;
    private PennyPanAPI pennyPanAPI;
    private final static String SERVER_URL = "http://pennypan.devel:8080";

    public GestoraRetrofitLoggedin(LoggedinViewModel loggedinViewModel)
    {
        Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd").create();
        retrofit = new Retrofit.Builder().baseUrl(SERVER_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        pennyPanAPI = retrofit.create(PennyPanAPI.class);
        listadoPedidosCallback = new ListadoPedidosCallback(loggedinViewModel);
    }


    public void obtenerListadoPedidos(String username, String password)
    {
        String token = username+":"+password;
        byte[] tokenByte = token.getBytes();
        String token64 = "Basic " + Base64.encodeToString(tokenByte, Base64.NO_WRAP | Base64.URL_SAFE);

        pennyPanAPI.getListadoPedidos(token64, username).enqueue(listadoPedidosCallback);
    }
}
