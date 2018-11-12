package com.example.ofunes.pennynotes;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static List<Nota> obtenerListaNotas(Context context)
    {
        List<Nota> lista = new ArrayList<>();
        File carpeta = context.getExternalFilesDir(null);
        String texto, noteTitle;

        File[] arrayNotas = carpeta.listFiles();
        lista.add(new Nota(carpeta.getPath(), carpeta.getPath()));
        for(int i = 0; i < arrayNotas.length; i++)
        {
            noteTitle = arrayNotas[i].getName().split("[.]")[0];
            if(arrayNotas[i].getName().split("[.]")[1].equals("txt") && arrayNotas[i].getName().split("[.]")[2] == null)
            {
                texto = obtenerTextoNota(arrayNotas[i]);
                lista.add(new Nota(noteTitle, texto));
            }
        }

        return lista;
    }

    public static String obtenerTextoNota(File nota)
    {
        String texto = "", linea;
        FileReader fr;
        BufferedReader br = null;
        try
        {
            fr = new FileReader(nota);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) { e.printStackTrace(); }

        try
        {
            linea = br.readLine();

            while(linea != null)
            {
                texto = texto + linea + "\n";
                linea = br.readLine();
            }
        } catch (IOException e) { e.printStackTrace(); }

        return texto;
    }

    public static boolean guardarNota(Nota n1)
    {
        boolean guardado = false;



        return guardado;
    }
}
