package com.example.ofunes.pennypanphone.Entidades;

import java.sql.Date;
import java.util.ArrayList;

public class Pedido {
    private int id;
    private String fechaCompra;
    private double importeTotal;
    private ArrayList<Bocata> bocatas;
    private ArrayList<PanPedido> panes;
    private ArrayList<ComplementoPedido> complementos;

    public Pedido(int id, String fechaCompra, double importeTotal, ArrayList<Bocata> bocatas, ArrayList<PanPedido> panes, ArrayList<ComplementoPedido> complementos) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.importeTotal = importeTotal;
        this.bocatas = bocatas;
        this.panes = panes;
        this.complementos = complementos;
    }

    public int getId() {
        return id;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public ArrayList<Bocata> getBocatas() {
        return bocatas;
    }

    public void setBocatas(ArrayList<Bocata> bocatas) {
        this.bocatas = bocatas;
    }

    public ArrayList<PanPedido> getPanes() {
        return panes;
    }

    public void setPanes(ArrayList<PanPedido> panes) {
        this.panes = panes;
    }

    public ArrayList<ComplementoPedido> getComplementos() {
        return complementos;
    }

    public void setComplementos(ArrayList<ComplementoPedido> complementos) {
        this.complementos = complementos;
    }
}
