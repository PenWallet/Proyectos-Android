package com.jolsensei.practicaexamen;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {

    private String nombre;
    private double precio;
    private Talla talla;
    private String color;
    private int imagen;


    public Producto(String nombre, double precio, Talla talla, String color, int imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.talla = talla;
        this.color = color;
        this.imagen = imagen;
    }

    public Producto() {
        this.nombre = "";
        this.precio = 0;
        this.talla = Talla.DESCONOCIDO;
        this.color = "";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Talla getTalla() {
        return talla;
    }

    public void setTalla(Talla talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String toString(){

        return nombre;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeDouble(this.precio);
        dest.writeInt(this.talla == null ? -1 : this.talla.ordinal());
        dest.writeString(this.color);
        dest.writeInt(this.imagen);
    }

    protected Producto(Parcel in) {
        this.nombre = in.readString();
        this.precio = in.readDouble();
        int tmpTalla = in.readInt();
        this.talla = tmpTalla == -1 ? null : Talla.values()[tmpTalla];
        this.color = in.readString();
        this.imagen = in.readInt();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel source) {
            return new Producto(source);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };
}
