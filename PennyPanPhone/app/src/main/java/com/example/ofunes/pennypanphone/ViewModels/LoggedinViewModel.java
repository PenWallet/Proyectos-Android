package com.example.ofunes.pennypanphone.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Retrofit.GestoraRetrofit;

public class LoggedinViewModel extends ViewModel {
    private Cliente cliente;

    public LoggedinViewModel()
    {
        cliente = null;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
