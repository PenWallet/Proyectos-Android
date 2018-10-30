package com.example.ofunes.listasheterogeneous;

public class Mapa {
    private String nombre;
    private String tipo;
    private int idRImagen;

    public Mapa()
    {
        this.nombre = "";
        this.tipo = "";
        this.idRImagen = R.drawable.missingtexture;
    }

    public Mapa(String nombre, String tipo, int idRImagen)
    {
        this.nombre = nombre;
        this.tipo =  tipo;
        this.idRImagen = idRImagen;
    }

    public void setNombre(String nombre) { this.nombre = nombre; };
    public String getNombre() { return this.nombre; }

    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getTipo() { return tipo; }

    public void setImagenDireccion(int id) { this.idRImagen = id; }
    public int getIdRImagen(){ return this.idRImagen; }

}
