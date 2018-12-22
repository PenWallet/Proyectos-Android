package com.example.ofunes.pennypanphone.Retrofit;

import android.util.Base64;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GestoraRetrofit {
    private Retrofit retrofit;
    private ClienteCallback clienteCallback;
    private RegistrationCallback registrationCallback;
    private ResponseBodyCallback responseBodyCallback;
    private PennyPanAPI pennyPanAPI;
    private final static String SERVER_URL = "http://pennypan.devel:8080";

    public GestoraRetrofit(MainViewModel mainVm)
    {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(SERVER_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        pennyPanAPI = retrofit.create(PennyPanAPI.class);
        clienteCallback = new ClienteCallback(mainVm);
        registrationCallback = new RegistrationCallback(mainVm);
        responseBodyCallback = new ResponseBodyCallback();
    }

    public void obtenerUsuario(String username, String password)
    {
        Cliente cliente = null;
        String token = username+":"+password;
        byte[] tokenByte = token.getBytes();
        String token64 = "Basic " + Base64.encodeToString(tokenByte, Base64.NO_WRAP | Base64.URL_SAFE);

        pennyPanAPI.getCliente(token64, username).enqueue(clienteCallback);
    }

    public void registrarUsuario(Cliente cliente)
    {
        pennyPanAPI.postCliente(cliente).enqueue(registrationCallback);
    }

    public void registrarUsuarioResponse(Cliente cliente)
    {
        pennyPanAPI.postClienteResponse(cliente).enqueue(responseBodyCallback);
    }
}
