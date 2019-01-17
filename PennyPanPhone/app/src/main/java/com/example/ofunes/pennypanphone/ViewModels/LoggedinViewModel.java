package com.example.ofunes.pennypanphone.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ofunes.pennypanphone.Entidades.Bocata;
import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.Complemento;
import com.example.ofunes.pennypanphone.Entidades.ComplementoPedido;
import com.example.ofunes.pennypanphone.Entidades.Ingrediente;
import com.example.ofunes.pennypanphone.Entidades.IngredienteBocata;
import com.example.ofunes.pennypanphone.Entidades.PanPedido;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.Retrofit.GestoraRetrofit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoggedinViewModel extends ViewModel {
    private Cliente cliente;
    private MutableLiveData<ArrayList<PanPedido>> panes;
    private MutableLiveData<ArrayList<ComplementoPedido>> complementos;
    private MutableLiveData<ArrayList<IngredienteBocata>> ingredientes;
    private MutableLiveData<ArrayList<Bocata>> bocatas;
    private MutableLiveData<ArrayList<Pedido>> listadoPedidos;
    private MutableLiveData<ArrayList<Object>> cesta;
    private MutableLiveData<Boolean> hasOrders;

    public LoggedinViewModel()
    {
        cliente = null;
        listadoPedidos = new MutableLiveData<>();
        hasOrders = new MutableLiveData<>();
        panes = new MutableLiveData<>();
        complementos = new MutableLiveData<>();
        ingredientes = new MutableLiveData<>();
        bocatas = new MutableLiveData<>();
        cesta = new MutableLiveData<>();
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

    public MutableLiveData<ArrayList<PanPedido>> getPanes() {
        return panes;
    }

    public void setPanes(MutableLiveData<ArrayList<PanPedido>> panes) {
        this.panes = panes;
    }

    public MutableLiveData<ArrayList<ComplementoPedido>> getComplementos() {
        return complementos;
    }

    public void setComplementos(MutableLiveData<ArrayList<ComplementoPedido>> complementos) {
        this.complementos = complementos;
    }

    public MutableLiveData<ArrayList<IngredienteBocata>> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(MutableLiveData<ArrayList<IngredienteBocata>> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public MutableLiveData<ArrayList<Bocata>> getBocatas() {
        return bocatas;
    }

    public void setBocatas(MutableLiveData<ArrayList<Bocata>> bocatas) {
        this.bocatas = bocatas;
    }

    public MutableLiveData<ArrayList<Object>> getCesta() {
        return cesta;
    }

    public void setCesta(MutableLiveData<ArrayList<Object>> cesta) {
        this.cesta = cesta;
    }
}
