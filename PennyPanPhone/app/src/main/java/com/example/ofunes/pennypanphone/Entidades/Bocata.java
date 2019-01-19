package com.example.ofunes.pennypanphone.Entidades;

import java.util.ArrayList;

public class Bocata {
    private Pan pan;
    private ArrayList<IngredienteBocata> ingredientes;

    public Bocata()
    {
        this.pan = null;
        this.ingredientes = new ArrayList<>();
    }

    public Bocata(Pan pan, ArrayList<IngredienteBocata> ingredientes) {
        this.pan = pan;
        this.ingredientes = ingredientes;
    }

    public Pan getPan() {
        return pan;
    }

    public void setPan(Pan pan) {
        this.pan = pan;
    }

    public ArrayList<IngredienteBocata> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<IngredienteBocata> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
