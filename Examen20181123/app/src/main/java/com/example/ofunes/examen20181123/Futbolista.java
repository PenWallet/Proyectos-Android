package com.example.ofunes.examen20181123;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ofunes on 23/11/18.
 */

public class Futbolista implements Parcelable {
    //Atributos
    private String nombre;
    private int foto;
    private String[] posiciones;
    private int posicionActual;

    //Constructor por defecto
    public Futbolista()
    {
        this.nombre = "";
        this.foto = 0;
        this.posiciones = new String[]{};
        this.posicionActual = 0;
    }

    public Futbolista(String nombre, int foto, String[] posiciones, int posicionActual)
    {
        this.nombre = nombre;
        this.foto = foto;
        this.posiciones = posiciones;
        this.posicionActual = posicionActual;
    }

    //Getters y setters

    public String getNombre() {
        return nombre;
    }

    public int getFoto() {
        return foto;
    }

    public String[] getPosiciones() {
        return posiciones;
    }

    public int getPosicionActual() {
        return posicionActual;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPosiciones(String[] posiciones) {
        this.posiciones = posiciones;
    }

    public void setPosicionActual(int posicionActual) {
        this.posicionActual = posicionActual;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeInt(this.foto);
        dest.writeStringArray(this.posiciones);
        dest.writeInt(this.posicionActual);
    }

    protected Futbolista(Parcel in) {
        this.nombre = in.readString();
        this.foto = in.readInt();
        this.posiciones = in.createStringArray();
        this.posicionActual = in.readInt();
    }

    public static final Parcelable.Creator<Futbolista> CREATOR = new Parcelable.Creator<Futbolista>() {
        @Override
        public Futbolista createFromParcel(Parcel source) {
            return new Futbolista(source);
        }

        @Override
        public Futbolista[] newArray(int size) {
            return new Futbolista[size];
        }
    };
}
