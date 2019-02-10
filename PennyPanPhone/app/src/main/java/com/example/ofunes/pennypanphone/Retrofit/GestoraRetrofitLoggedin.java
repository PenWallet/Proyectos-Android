package com.example.ofunes.pennypanphone.Retrofit;

import android.util.Base64;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.LoggedinActivity;
import com.example.ofunes.pennypanphone.Utiliidades.AuthUtils;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GestoraRetrofitLoggedin {
    private Retrofit retrofit;
    private ListadoPedidosCallback listadoPedidosCallback;
    private ListadoPanesCallback listadoPanesCallback;
    private ListadoComplementosCallback listadoComplementosCallback;
    private ListadoIngredientesCallback listadoIngredientesCallback;
    private ListadoClientesCallback listadoClientesCallback;
    private PostPedidoCallback postPedidoCallback;
    private PennyPanAPI pennyPanAPI;
    private LoggedinViewModel loggedinViewModel;
    private final static String SERVER_URL = "http://pennypan.devel:8080";

    public GestoraRetrofitLoggedin(LoggedinViewModel loggedinViewModel)
    {
        this.loggedinViewModel = loggedinViewModel;
        Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd").create();
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(100, TimeUnit.SECONDS).readTimeout(100,TimeUnit.SECONDS).build();
        retrofit = new Retrofit.Builder().baseUrl(SERVER_URL).client(client).addConverterFactory(GsonConverterFactory.create(gson)).build();
        pennyPanAPI = retrofit.create(PennyPanAPI.class);
        listadoPedidosCallback = new ListadoPedidosCallback(loggedinViewModel);
        listadoPanesCallback = new ListadoPanesCallback(loggedinViewModel);
        listadoComplementosCallback = new ListadoComplementosCallback(loggedinViewModel);
        listadoIngredientesCallback = new ListadoIngredientesCallback(loggedinViewModel);
        postPedidoCallback = new PostPedidoCallback(loggedinViewModel);
        listadoClientesCallback = new ListadoClientesCallback(loggedinViewModel);
    }


    public void obtenerListadoPedidos()
    {
        String bearerToken = AuthUtils.getBearerToken(loggedinViewModel.getCliente().getToken());
        pennyPanAPI.getListadoPedidos(bearerToken, loggedinViewModel.getCliente().getUsername()).enqueue(listadoPedidosCallback);
    }

    public void obtenerListadoPanes()
    {
        pennyPanAPI.getListadoPanes().enqueue(listadoPanesCallback);
    }

    public void obtenerListadoComplementos()
    {
        pennyPanAPI.getListadoComplementos().enqueue(listadoComplementosCallback);
    }

    public void obtenerListadoIngredientes()
    {
        pennyPanAPI.getListadoIngredientes().enqueue(listadoIngredientesCallback);
    }

    public void postPedido(Pedido pedido)
    {
        String bearerToken = AuthUtils.getBearerToken(loggedinViewModel.getCliente().getToken());
        pennyPanAPI.postPedido(bearerToken, loggedinViewModel.getCliente().getUsername(), pedido).enqueue(postPedidoCallback);
    }

    public void obtenerListadoClientes()
    {
        String bearerToken = AuthUtils.getBearerToken(loggedinViewModel.getCliente().getToken());
        pennyPanAPI.getListCliente(bearerToken).enqueue(listadoClientesCallback);
    }
}
