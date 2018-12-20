package com.example.ofunes.pennypanphone.Entidades;

/**
 * Created by ofunes on 20/12/18.
 */

public class Cliente {
    private String username;
    private String password;
    private String nombre;
    private boolean panadero;

    public Cliente() {
        this.username = "";
        this.password = "";
        this.nombre = "";
        this.panadero = false;
    }

    public Cliente(String username, String password, String nombre, boolean panadero) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.panadero = panadero;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isPanadero() {
        return panadero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
