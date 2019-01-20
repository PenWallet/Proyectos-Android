package com.example.ofunes.pennypanphone.Entidades;

public class IngredienteBocata extends Ingrediente{
    private int cantidad;

    public IngredienteBocata(int id, String nombre, double precio, int cantidad)
    {
        super(id, nombre, precio);
        this.cantidad = cantidad;
    }

    public IngredienteBocata(IngredienteBocata ingrediente)
    {
        super(ingrediente.getId(), ingrediente.getNombre(), ingrediente.getPrecio());
        this.cantidad = getCantidad();
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
