package com.example.ofunes.pennypanphone.Entidades;

public class PanPedido extends Pan{
    private int cantidad;

    public PanPedido(int id, String nombre, int crujenticidad, int integral, double precio, int cantidad) {
        super(id, nombre, crujenticidad, integral, precio);
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
