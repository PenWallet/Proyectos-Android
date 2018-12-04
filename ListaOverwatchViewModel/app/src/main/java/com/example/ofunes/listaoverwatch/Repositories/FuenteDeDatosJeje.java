package com.example.ofunes.listaoverwatch.Repositories;

import com.example.ofunes.listaoverwatch.Entidades.Heroe;
import com.example.ofunes.listaoverwatch.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ofunes on 4/12/18.
 */

public class FuenteDeDatosJeje {
    private static final Heroe[] arrayHeroes = {
            new Heroe("Ana", R.drawable.ana),
            new Heroe("Brigitte", R.drawable.brigitte),
            new Heroe("D.Va", R.drawable.dva),
            new Heroe("Junkrat", R.drawable.junkrat),
            new Heroe("Mercy", R.drawable.mercy),
            new Heroe("Moira", R.drawable.moira),
            new Heroe("Reaper", R.drawable.reaper),
            new Heroe("Reinhardt", R.drawable.reinhardt),
            new Heroe("Roadhog", R.drawable.roadhog)
    };

    public static final ArrayList<Heroe> listadoHeroes = new ArrayList<>(Arrays.asList(arrayHeroes));
}
