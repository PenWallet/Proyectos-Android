package com.example.ofunes.listaoverwatch.Repositories;

import com.example.ofunes.listaoverwatch.Entidades.Heroe;

import java.util.ArrayList;

public class ListadosHeroes {
    public static ArrayList<Heroe> obtenerListadoHeroes()
    {
        return FuenteDeDatosJeje.listadoHeroes;
    }

}
