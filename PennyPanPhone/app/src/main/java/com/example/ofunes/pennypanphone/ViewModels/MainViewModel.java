package com.example.ofunes.pennypanphone.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ofunes.pennypanphone.Entidades.Cliente;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Cliente> cliente;

    public MainViewModel()
    {
        cliente = new MutableLiveData<>();
    }

    public MutableLiveData<Cliente> getCliente() {
        return cliente;
    }

    public void setCliente(MutableLiveData<Cliente> cliente) {
        this.cliente = cliente;
    }
}
