package com.example.ofunes.pennypanphone.Entidades;

public class ComplementoPedido extends Complemento{
    private int cantidad;

    public ComplementoPedido(int id, String nombre, double precio, int cantidad)
    {
        super(id, nombre, precio);
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void addOne()
    {
        this.cantidad++;
    }

    public void substractOne()
    {
        this.cantidad--;
    }
}
