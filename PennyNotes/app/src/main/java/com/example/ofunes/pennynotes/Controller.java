package com.example.ofunes.pennynotes;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static List<Nota> obtenerListaNotas(String direccionNotas, Context context)
    {
        List<Nota> lista = new ArrayList<>();

        File carpeta = context.getFilesDir();
        for(int i = 0; i < 10; i++)
        {
            File pruebaArchivo = new File(carpeta.getPath()+"/prueba"+i+".txt");
            try {
                pruebaArchivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File[] arrayNotas = carpeta.listFiles();
        lista.add(new Nota(carpeta.getPath(), carpeta.getPath()));
        for(int i = 0; i < arrayNotas.length; i++)
        {
            lista.add(new Nota(arrayNotas[i].getName(), ""));
        }

        return lista;
    }
}
