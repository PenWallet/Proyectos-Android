package com.example.ofunes.pennypanphone.Retrofit;

import android.util.Base64;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Credentials;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GestoraRetrofit {
    private Retrofit retrofit;
    private LoginCallback loginCallback;
    private RegistrationCallback registrationCallback;
    private PennyPanAPI pennyPanAPI;
    private final static String SERVER_URL = "http://pennypan.devel:8080";

    public GestoraRetrofit(MainViewModel mainVm)
    {
        Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd").create();
        retrofit = new Retrofit.Builder().baseUrl(SERVER_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        pennyPanAPI = retrofit.create(PennyPanAPI.class);
        loginCallback = new LoginCallback(mainVm);
        registrationCallback = new RegistrationCallback(mainVm);
    }

    public void obtenerUsuario(String username, String password)
    {
        pennyPanAPI.getCliente(Credentials.basic(username, password), username).enqueue(loginCallback);
    }

    public void registrarUsuario(Cliente cliente)
    {
        pennyPanAPI.postCliente(cliente).enqueue(registrationCallback);
    }
}
