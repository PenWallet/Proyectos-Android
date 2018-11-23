package com.example.ofunes.examen20181123;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ofunes on 23/11/18.
 */

public class Datos {

    public static ArrayList<Object> obtenerListadoJugadores()
    {
        ArrayList<Object> listadoJugadores = new ArrayList<Object>();

        listadoJugadores.add(new Futbolista("Kevin de Bruyne", R.drawable.kevin_de_bruyne_f,
                new String[]{"Delantero", "Portero", "Noseque", "Nosecuantos"}, 1));
        listadoJugadores.add(new Baloncestista("Lebron James", R.drawable.lebron_james_b,
                7, 8, 9));
        listadoJugadores.add(new Baloncestista("Marc Gasol", R.drawable.marc_gasol_b,
                4, 1, 9));
        listadoJugadores.add(new Baloncestista("Pau Gasol", R.drawable.pau_gasol_b,
                6, 2, 2));
        listadoJugadores.add(new Futbolista("Romelu Lukaku", R.drawable.romelu_lukaku_f,
                new String[]{"Inventado", "Jeje"}, 0));
        listadoJugadores.add(new Baloncestista("Pau Gasol", R.drawable.pau_gasol_b,
                6, 2, 2));
        listadoJugadores.add(new Baloncestista("Pau Gasol", R.drawable.pau_gasol_b,
                6, 2, 2));
        listadoJugadores.add(new Futbolista("Kevin de Bruyne", R.drawable.kevin_de_bruyne_f,
                new String[]{"Delantero", "Portero", "Noseque", "Nosecuantos"}, 0));
        listadoJugadores.add(new Futbolista("Romelu Lukaku", R.drawable.romelu_lukaku_f,
                new String[]{"Inventado", "Jeje"}, 1));
        listadoJugadores.add(new Futbolista("Romelu Lukaku", R.drawable.romelu_lukaku_f,
                new String[]{"Inventado", "Jeje"}, 1));
        listadoJugadores.add(new Baloncestista("Pau Gasol", R.drawable.pau_gasol_b,
                6, 2, 2));
        listadoJugadores.add(new Baloncestista("Pau Gasol", R.drawable.pau_gasol_b,
                6, 2, 2));
        listadoJugadores.add(new Futbolista("Kevin de Bruyne", R.drawable.kevin_de_bruyne_f,
                new String[]{"Delantero", "Portero", "Noseque", "Nosecuantos"}, 3));
        listadoJugadores.add(new Futbolista("Romelu Lukaku", R.drawable.romelu_lukaku_f,
                new String[]{"Inventado", "Jeje"}, 1));
        listadoJugadores.add(new Baloncestista("Pau Gasol", R.drawable.pau_gasol_b,
                6, 2, 2));

        return listadoJugadores;
    }
}
