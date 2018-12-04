package com.example.ofunes.listaoverwatch.Entidades;

public class Heroe {
    //Atributos
    private String nombre;
    private int idRImagen;

    //Constructor por defecto
    public Heroe()
    {

    }

    //Constructor con parámetros
    public Heroe(String nombre, int idRImagen)
    {
        this.nombre = nombre;
        this.idRImagen = idRImagen;
    }

    //Getters y setters
    public void setNombre(String nombre) { this.nombre = nombre; };
    public String getNombre() { return this.nombre; }

    public void setImagenDireccion(int id) { this.idRImagen = id; }
    public int getIdRImagen(){ return this.idRImagen; }
}
