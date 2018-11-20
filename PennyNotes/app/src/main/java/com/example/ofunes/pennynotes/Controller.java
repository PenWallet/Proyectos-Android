package com.example.ofunes.pennynotes;

import android.content.Context;
import android.widget.Toast;

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
        for(int i = 0; i < arrayNotas.length; i++)
        {
            noteTitle = arrayNotas[i].getName().split("[.]")[0];
            if(arrayNotas[i].getName().split("[.]")[1].equals("txt") && arrayNotas[i].getName().split("[.]").length == 2)
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

    public static String obtenerTextoNota(String nombreNota, Context context)
    {
        String archivo = context.getExternalFilesDir(null).getPath()+System.getProperty("file.separator")+nombreNota+".txt";
        String linea, texto = "";
        File nota;
        FileReader fr;
        BufferedReader br = null;
        try
        {
            nota = new File(archivo);
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
        } catch (IOException | NullPointerException e) { e.printStackTrace(); }

        return texto;
    }

    public static boolean guardarNota(Nota n1, Context context)
    {
        boolean guardado = false;
        String noteTexto = n1.getText(), noteTitle = n1.getHeader();
        String archivo = context.getExternalFilesDir(null).getPath()+System.getProperty("file.separator")+noteTitle+".txt";
        File nota = new File(archivo);
        FileWriter fw = null;
        BufferedWriter bw = null;

        try
        {
            nota.createNewFile();
            fw = new FileWriter(nota, false); //false para que sobreescriba si hay algo escrito
            bw = new BufferedWriter(fw);

            bw.write(noteTexto);

            guardado = true;
        }catch(IOException e) { Toast.makeText(context, "ERROR: No se ha podido abrir el stream", Toast.LENGTH_LONG).show(); }
        finally
        {
            try
            {
                bw.close();
                fw.close();
            } catch (IOException e) { Toast.makeText(context, "ERROR: No se ha podido cerrar el stream", Toast.LENGTH_LONG).show(); }
        }

        return guardado;
    }

    public static Nota obtenerNota(String nombreNota, Context context)
    {
        String texto = obtenerTextoNota(nombreNota, context);
        Nota nota = new Nota(nombreNota, texto);

        return nota;
    }

    public static boolean borrarNota(Nota nota, Context context)
    {
        boolean borrado = false;
        String txtArchivo = context.getExternalFilesDir(null).getPath()+System.getProperty("file.separator")+nota.getHeader()+".txt";
        File archivo = new File(txtArchivo);

        borrado = archivo.delete();

        return borrado;
    }
}
