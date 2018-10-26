package com.example.ofunes.pennynotes;

public class Nota {
    //Atributos
    private String header;
    private String text;

    //Constructores
    public Nota()
    {
        this.header = "Defecto";
        this.text = "Lorem ipsum";
    }

    public Nota(String nHeader, String nText)
    {
        this.header = nHeader;
        this.text = nText;
    }

    //Getters y setters
    public String getHeader() { return header; }
    public String getText() { return text; }

    public void setHeader(String header) { this.header = header; }
    public void setText(String text) { this.text = text; }

}
