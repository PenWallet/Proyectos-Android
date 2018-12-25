package com.example.ofunes.pennypanphone.Entidades;

import java.util.ArrayList;

public class Bocata {
    private Pan pan;
    private IngredienteBocata[] ingredientes;

    public Bocata()
    {
        this.pan = null;
        this.ingredientes = new IngredienteBocata[0];
    }

    public Bocata(Pan pan, IngredienteBocata[] ingredientes) {
        this.pan = pan;
        this.ingredientes = ingredientes;
    }

    public Pan getPan() {
        return pan;
    }

    public void setPan(Pan pan) {
        this.pan = pan;
    }

    public IngredienteBocata[] getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(IngredienteBocata[] ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setIngredientes(ArrayList<IngredienteBocata> ingredientes) {
        this.ingredientes = (IngredienteBocata[])ingredientes.toArray();
    }
}
