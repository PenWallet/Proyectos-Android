package com.example.ofunes.pennypanphone.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Retrofit.GestoraRetrofit;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Cliente> cliente;
    private MutableLiveData<Cliente> clienteRegistrado;
    private GestoraRetrofit gestoraRetrofit;

    public MainViewModel()
    {
        cliente = new MutableLiveData<>();
        clienteRegistrado = new MutableLiveData<>();
        gestoraRetrofit = new GestoraRetrofit(this);
    }

    public MutableLiveData<Cliente> getCliente() {
        return cliente;
    }

    public MutableLiveData<Cliente> getClienteRegistrado() {
        return clienteRegistrado;
    }

    public GestoraRetrofit getGestoraRetrofit() {
        return gestoraRetrofit;
    }
}
