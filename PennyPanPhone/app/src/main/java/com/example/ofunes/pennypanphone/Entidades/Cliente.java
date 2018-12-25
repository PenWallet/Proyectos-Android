package com.example.ofunes.pennypanphone.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ofunes on 20/12/18.
 */

public class Cliente implements Parcelable {
    private String username;
    private String contrasena;
    private String nombre;
    private int panadero;

    public Cliente() {
        this.username = "";
        this.contrasena = "";
        this.nombre = "";
        this.panadero = 0;
    }

    public Cliente(String username, String contrasena, String nombre, int panadero) {
        this.username = username;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.panadero = panadero;
    }

    public String getUsername() {
        return username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isPanadero() {
        return panadero == 1;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "username='" + username + '\'' +
                ", password='" + contrasena + '\'' +
                ", nombre='" + nombre + '\'' +
                ", panadero=" + panadero +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.contrasena);
        dest.writeString(this.nombre);
        dest.writeInt(this.panadero);
    }

    protected Cliente(Parcel in) {
        this.username = in.readString();
        this.contrasena = in.readString();
        this.nombre = in.readString();
        this.panadero = in.readInt();
    }

    public static final Parcelable.Creator<Cliente> CREATOR = new Parcelable.Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel source) {
            return new Cliente(source);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };
}
