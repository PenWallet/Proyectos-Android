package com.example.ofunes.listaoverwatch.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ofunes.listaoverwatch.Entidades.Heroe;
import com.example.ofunes.listaoverwatch.Repositories.ListadosHeroes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ofunes on 11/12/18.
 */

public class MainViewModel extends ViewModel
{
    private MutableLiveData<ArrayList<Heroe>> listadoHeroes;

    public MainViewModel()
    {
        listadoHeroes = new MutableLiveData<>();
        listadoHeroes.setValue(ListadosHeroes.obtenerListadoHeroes());
    }

    public ArrayList<Heroe> getListadoHeroes() {
        return listadoHeroes.getValue();
    }

    public void actualizarListado()
    {
        listadoHeroes.setValue(ListadosHeroes.obtenerListadoHeroes());
    }
}
