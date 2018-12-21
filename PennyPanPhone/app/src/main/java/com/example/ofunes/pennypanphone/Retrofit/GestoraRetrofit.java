package com.example.ofunes.pennypanphone.Retrofit;

import android.util.Base64;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GestoraRetrofit {
    private Retrofit retrofit;
    private ClienteCallback clienteCallback;
    private ClienteVoidCallback clienteVoidCallback;
    private PennyPanAPI pennyPanAPI;
    private final static String SERVER_URL = "http://pennypan.devel:8080";

    public GestoraRetrofit(MainViewModel mainVM)
    {
        retrofit = new Retrofit.Builder().baseUrl(SERVER_URL).addConverterFactory(GsonConverterFactory.create()).build();
        pennyPanAPI = retrofit.create(PennyPanAPI.class);
        clienteCallback = new ClienteCallback(mainVM);
        clienteVoidCallback = new ClienteVoidCallback();
    }

    public void obtenerUsuario(String username, String password)
    {
        Cliente cliente = null;
        String token = username+":"+password;
        byte[] tokenByte = token.getBytes();
        String token64 = Base64.encodeToString(tokenByte, Base64.DEFAULT);

        pennyPanAPI.getCliente(token64, username).enqueue(clienteCallback);
    }

    public void registrarUsuario(Cliente cliente)
    {
        pennyPanAPI.postCliente(cliente).enqueue(clienteVoidCallback);
    }
}
