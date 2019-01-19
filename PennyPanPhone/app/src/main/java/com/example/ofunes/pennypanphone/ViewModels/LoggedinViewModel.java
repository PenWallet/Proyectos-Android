package com.example.ofunes.pennypanphone.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ofunes.pennypanphone.Entidades.Bocata;
import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.Complemento;
import com.example.ofunes.pennypanphone.Entidades.ComplementoPedido;
import com.example.ofunes.pennypanphone.Entidades.Ingrediente;
import com.example.ofunes.pennypanphone.Entidades.IngredienteBocata;
import com.example.ofunes.pennypanphone.Entidades.MarketType;
import com.example.ofunes.pennypanphone.Entidades.PanPedido;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.Retrofit.GestoraRetrofit;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoggedinViewModel extends ViewModel {
    private Cliente cliente;
    private MutableLiveData<ArrayList<PanPedido>> panes;
    private MutableLiveData<ArrayList<ComplementoPedido>> complementos;
    private MutableLiveData<ArrayList<IngredienteBocata>> ingredientes;
    private MutableLiveData<ArrayList<Pedido>> listadoPedidos;
    private MutableLiveData<ArrayList<Object>> cesta;
    private MutableLiveData<Boolean> hasOrders;
    private MutableLiveData<MarketType> marketOption;
    private MutableLiveData<Double> cartTotal;
    private int sandwichInProgress; //Representa la posición en la cesta del bocata que está en progreso

    public LoggedinViewModel()
    {
        cliente = null;
        listadoPedidos = new MutableLiveData<>();
        hasOrders = new MutableLiveData<>();
        panes = new MutableLiveData<>();
        complementos = new MutableLiveData<>();
        ingredientes = new MutableLiveData<>();
        cesta = new MutableLiveData<>(); cesta.setValue(new ArrayList<>());
        marketOption = new MutableLiveData<>();
        cartTotal = new MutableLiveData<>(); cartTotal.setValue(0d);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public MutableLiveData<ArrayList<Pedido>> getListadoPedidos() {
        return listadoPedidos;
    }

    public MutableLiveData<Boolean> getHasOrders() {
        return hasOrders;
    }

    public MutableLiveData<ArrayList<PanPedido>> getPanes() {
        return panes;
    }

    public MutableLiveData<ArrayList<ComplementoPedido>> getComplementos() {
        return complementos;
    }

    public MutableLiveData<ArrayList<IngredienteBocata>> getIngredientes() {
        return ingredientes;
    }

    public MutableLiveData<ArrayList<Object>> getCesta() {
        return cesta;
    }

    public MutableLiveData<MarketType> getMarketOption() {
        return marketOption;
    }

    public MutableLiveData<Double> getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(MutableLiveData<Double> cartTotal) {
        this.cartTotal = cartTotal;
    }

    public int getSandwichInProgress() {
        return sandwichInProgress;
    }

    public void setSandwichInProgress(int sandwichInProgress) {
        this.sandwichInProgress = sandwichInProgress;
    }

    /* Funciones */
    public void addValueCartTotal(double value)
    {
        NumberFormat df = new DecimalFormat("#.00");
        double actualValue = cartTotal.getValue();
        double finalValue = actualValue + value;
        cartTotal.setValue(Double.valueOf(df.format(finalValue)));
    }
}
