package com.example.ofunes.listaoverwatch.Repositories;

import com.example.ofunes.listaoverwatch.Entidades.Heroe;

public class ManejadoraHeroes {

    public static boolean borrarHeroe(Heroe heroe)
    {
        boolean borrado = false;
        int tamanoPrevio = FuenteDeDatosJeje.listadoHeroes.size();

        FuenteDeDatosJeje.listadoHeroes.remove(heroe);

        if(tamanoPrevio == FuenteDeDatosJeje.listadoHeroes.size()-1)
            borrado = true;

        return borrado;
    }

    public static boolean anadirHeroe(Heroe heroe)
    {
        boolean anadido = false;
        int tamanoPrevio = FuenteDeDatosJeje.listadoHeroes.size();

        FuenteDeDatosJeje.listadoHeroes.add(heroe);

        if(tamanoPrevio == FuenteDeDatosJeje.listadoHeroes.size()+1)
            anadido = true;

        return anadido;
    }
}
