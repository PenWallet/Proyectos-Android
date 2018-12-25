package com.example.ofunes.pennypanphone.Entidades;

import java.sql.Date;

public class Pedido {
    private int id;
    private String fechaCompra;
    private double importeTotal;
    private Bocata[] bocatas;
    private PanPedido[] panes;
    private ComplementoPedido[] complementos;

    public Pedido(int id, String fechaCompra, double importeTotal, Bocata[] bocatas, PanPedido[] panes, ComplementoPedido[] complementos) {
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

    public Bocata[] getBocatas() {
        return bocatas;
    }

    public void setBocatas(Bocata[] bocatas) {
        this.bocatas = bocatas;
    }

    public PanPedido[] getPanes() {
        return panes;
    }

    public void setPanes(PanPedido[] panes) {
        this.panes = panes;
    }

    public ComplementoPedido[] getComplementos() {
        return complementos;
    }

    public void setComplementos(ComplementoPedido[] complementos) {
        this.complementos = complementos;
    }
}
