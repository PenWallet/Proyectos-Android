package com.example.ofunes.pennypanphone.Entidades;

public class IngredienteBocata extends Ingrediente{
    private int cantidad;

    public IngredienteBocata(int id, String nombre, double precio, int cantidad)
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
}
