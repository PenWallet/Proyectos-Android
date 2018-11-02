package com.example.ofunes.pennynotes;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static List<Nota> obtenerListaNotas(String direccionNotas, Context context)
    {
        List<Nota> lista = new ArrayList<>();
        File carpeta = context.getExternalFilesDir(null);
        String prueba;


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
            prueba = obtenerTextoNota(arrayNotas[i]);
            lista.add(new Nota(arrayNotas[i].getName().split("[.]")[0], prueba));
        }

        return lista;
    }

    public static String obtenerTextoNota(File nota)
    {
        String texto = "", linea;
        FileReader fr;
        BufferedReader br = null;
        try {
            fr = new FileReader(nota);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            linea = br.readLine();

            while(linea != null)
            {
                texto = texto + linea + "\n";
                linea = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return texto;
    }
}
