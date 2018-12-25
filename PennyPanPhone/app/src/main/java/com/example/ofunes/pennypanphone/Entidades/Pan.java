package com.example.ofunes.pennypanphone.Entidades;

public class Pan {
    private int id;
    private String nombre;
    private int crujenticidad;
    private int integral;
    private double precio;

    public Pan(int id, String nombre, int crujenticidad, int integral, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.crujenticidad = crujenticidad;
        this.integral = integral;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCrujenticidad() {
        return crujenticidad;
    }

    public void setCrujenticidad(int crujenticidad) {
        this.crujenticidad = crujenticidad;
    }

    public boolean isIntegral() {
        return integral == 1;
    }

    public void setIntegral(boolean integral) {
        this.integral = integral ? 1 : 0;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
