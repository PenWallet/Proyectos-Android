package com.example.ofunes.pennypanphone.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ofunes on 20/12/18.
 */

public class Cliente implements Parcelable {
    @Expose
    private String username;
    @Expose
    private String nombre;
    @Expose
    private int panadero;

    private String token;
    private String contrasena;

    public Cliente() {
        this.username = "";
        this.contrasena = "";
        this.nombre = "";
        this.panadero = 0;
        this.token = "";
    }

    public Cliente(String username, String contrasena, String nombre, int panadero, String token) {
        this.username = username;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.panadero = panadero;
        this.token = token;
    }

    public Cliente(String username, String contrasena, String nombre, int panadero) {
        this.username = username;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.panadero = panadero;
        this.token = "";
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "username='" + username + '\'' +
                ", password='" + contrasena + '\'' +
                ", nombre='" + nombre + '\'' +
                ", panadero='" + panadero + '\'' +
                ", token=" + token +
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
        dest.writeString(this.token);
    }

    protected Cliente(Parcel in) {
        this.username = in.readString();
        this.contrasena = in.readString();
        this.nombre = in.readString();
        this.panadero = in.readInt();
        this.token = in.readString();
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
