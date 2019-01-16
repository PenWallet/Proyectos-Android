package com.example.ofunes.pennypanphone.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.Complemento;
import com.example.ofunes.pennypanphone.Entidades.Ingrediente;
import com.example.ofunes.pennypanphone.Entidades.Pan;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.Retrofit.GestoraRetrofit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoggedinViewModel extends ViewModel {
    private Cliente cliente;
    private MutableLiveData<ArrayList<Pan>> panes;
    private MutableLiveData<ArrayList<Complemento>> complementos;
    private MutableLiveData<ArrayList<Ingrediente>> ingredientes;
    private MutableLiveData<ArrayList<Pedido>> listadoPedidos;
    private MutableLiveData<Boolean> hasOrders;

    public LoggedinViewModel()
    {
        cliente = null;
        listadoPedidos = new MutableLiveData<>();
        hasOrders = new MutableLiveData<>();
        panes = new MutableLiveData<>();
        complementos = new MutableLiveData<>();
        ingredientes = new MutableLiveData<>();
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

    public void setListadoPedidos(MutableLiveData<ArrayList<Pedido>> listadoPedidos) {
        this.listadoPedidos = listadoPedidos;
    }

    public MutableLiveData<Boolean> getHasOrders() {
        return hasOrders;
    }

    public MutableLiveData<ArrayList<Pan>> getPanes() {
        return panes;
    }

    public void setPanes(MutableLiveData<ArrayList<Pan>> panes) {
        this.panes = panes;
    }

    public MutableLiveData<ArrayList<Complemento>> getComplementos() {
        return complementos;
    }

    public void setComplementos(MutableLiveData<ArrayList<Complemento>> complementos) {
        this.complementos = complementos;
    }

    public MutableLiveData<ArrayList<Ingrediente>> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(MutableLiveData<ArrayList<Ingrediente>> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
