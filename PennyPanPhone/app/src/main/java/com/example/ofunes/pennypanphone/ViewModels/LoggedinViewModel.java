package com.example.ofunes.pennypanphone.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.Retrofit.GestoraRetrofit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoggedinViewModel extends ViewModel {
    private Cliente cliente;
    private ArrayList<Pedido> listadoPedidos;

    public LoggedinViewModel()
    {
        cliente = null;
        listadoPedidos = new ArrayList<>();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Pedido> getListadoPedidos() {
        return listadoPedidos;
    }

    public void setListadoPedidos(ArrayList<Pedido> listadoPedidos) {
        this.listadoPedidos = listadoPedidos;
    }

    public void setListadoPedidos(Pedido[] listadoPedidos) {
        this.listadoPedidos = new ArrayList<>(Arrays.asList(listadoPedidos));
    }

    public void setListadoPedidos(List<Pedido> listadoPedidos) {
        this.listadoPedidos = new ArrayList<>(listadoPedidos);
    }
}
