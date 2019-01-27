package com.example.ofunes.pennypanphone.Retrofit;

import android.util.Base64;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.LoggedinActivity;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    private final static String SERVER_URL = "http://ofunes.ciclo.iesnervion.es";

    public GestoraRetrofitLoggedin(LoggedinViewModel loggedinViewModel)
    {
        this.loggedinViewModel = loggedinViewModel;
        Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd").create();
        retrofit = new Retrofit.Builder().baseUrl(SERVER_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
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
        String token64 = obtenerToken();

        pennyPanAPI.getListadoPedidos(token64, loggedinViewModel.getCliente().getUsername()).enqueue(listadoPedidosCallback);
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
        String token64 = obtenerToken();

        pennyPanAPI.postPedido(token64, loggedinViewModel.getCliente().getUsername(), pedido).enqueue(postPedidoCallback);
    }

    public void obtenerListadoClientes()
    {
        String token64 = obtenerToken();

        pennyPanAPI.getListCliente(token64).enqueue(listadoClientesCallback);
    }

    private String obtenerToken()
    {
        String username = loggedinViewModel.getCliente().getUsername();
        String password = loggedinViewModel.getCliente().getContrasena();
        String token = username+":"+password;
        byte[] tokenByte = token.getBytes();
        return "Basic " + Base64.encodeToString(tokenByte, Base64.NO_WRAP | Base64.URL_SAFE);
    }
}
