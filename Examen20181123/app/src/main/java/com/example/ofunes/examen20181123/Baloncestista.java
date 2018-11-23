package com.example.ofunes.examen20181123;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ofunes on 23/11/18.
 */

public class Baloncestista implements Parcelable {
    //Atributos
    private String nombre;
    private int foto;
    private int puntosPorPartido;
    private int rebotesPorPartido;
    private int asistenciasPorPartido;

    //Constructores
    public Baloncestista()
    {
        this.nombre = "";
        this.foto = 0;
        this.puntosPorPartido = 0;
        this.rebotesPorPartido = 0;
        this.asistenciasPorPartido = 0;
    }

    public Baloncestista(String nombre, int foto, int puntosPorPartido, int rebotesPorPartido, int asistenciasPorPartido)
    {
        this.nombre = nombre;
        this.foto = foto;
        this.puntosPorPartido = puntosPorPartido;
        this.rebotesPorPartido = rebotesPorPartido;
        this.asistenciasPorPartido = asistenciasPorPartido;
    }

    //Getters y setters

    public int getAsistenciasPorPartido() {
        return asistenciasPorPartido;
    }

    public int getFoto() {
        return foto;
    }

    public int getPuntosPorPartido() {
        return puntosPorPartido;
    }

    public int getRebotesPorPartido() {
        return rebotesPorPartido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setAsistenciasPorPartido(int asistenciasPorPartido) {
        this.asistenciasPorPartido = asistenciasPorPartido;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntosPorPartido(int puntosPorPartido) {
        this.puntosPorPartido = puntosPorPartido;
    }

    public void setRebotesPorPartido(int rebotesPorPartido) {
        this.rebotesPorPartido = rebotesPorPartido;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeInt(this.foto);
        dest.writeInt(this.puntosPorPartido);
        dest.writeInt(this.rebotesPorPartido);
        dest.writeInt(this.asistenciasPorPartido);
    }

    protected Baloncestista(Parcel in) {
        this.nombre = in.readString();
        this.foto = in.readInt();
        this.puntosPorPartido = in.readInt();
        this.rebotesPorPartido = in.readInt();
        this.asistenciasPorPartido = in.readInt();
    }

    public static final Parcelable.Creator<Baloncestista> CREATOR = new Parcelable.Creator<Baloncestista>() {
        @Override
        public Baloncestista createFromParcel(Parcel source) {
            return new Baloncestista(source);
        }

        @Override
        public Baloncestista[] newArray(int size) {
            return new Baloncestista[size];
        }
    };
}
