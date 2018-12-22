package com.example.ofunes.pennypanphone.Entidades;

/**
 * Created by ofunes on 20/12/18.
 */

public class Cliente {
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

    public int isPanadero() {
        return panadero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
