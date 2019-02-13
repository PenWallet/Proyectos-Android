package com.example.ofunes.pennypanphone.Entidades;

/**
 * Created by ofunes on 13/02/19.
 */

public class ClientePanadero {

    private String username;
    private int panadero;

    public ClientePanadero() {
        this.username = "";
        this.panadero = 0;
    }

    public ClientePanadero(String username, int panadero) {
        this.username = username;
        this.panadero = panadero;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getPanadero() {
        return panadero == 1;
    }

    public void setPanadero(boolean panadero) {
        this.panadero = panadero ? 1 : 0;
    }
}
