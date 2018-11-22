package com.jolsensei.practicaexamen;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class detalles extends Activity {

    ImageView imagen;
    TextView nombre, precio;
    Spinner color, talla;
    Button annanir, cesta;
    Producto recibido;

    public ArrayList<Talla> tallas = new ArrayList<>();
    public ArrayList<String> colores = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        recibido = (Producto)getIntent().getParcelableExtra("Producto");

        tallas.add(Talla.S);
        tallas.add(Talla.M);
        tallas.add(Talla.L);
        tallas.add(Talla.XL);

        colores.add("Negro");
        colores.add("Blanco");
        colores.add("Azul");
        colores.add("Verde");


        imagen = findViewById(R.id.imagen);
        nombre = findViewById(R.id.nombre);
        precio = findViewById(R.id.precio);
        color = findViewById(R.id.color);
        talla = findViewById(R.id.talla);
        annanir = findViewById(R.id.annanir);
        cesta = findViewById(R.id.cesta);


        ArrayAdapter<String> adapterColores = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colores);

        adapterColores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        color.setAdapter(adapterColores);


        ArrayAdapter<Talla> adapterTallas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tallas);

        adapterTallas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        talla.setAdapter(adapterTallas);


        mostrarDetalles();
    }

    private void mostrarDetalles() {

        if (recibido!=null){

            imagen.setImageResource(recibido.getImagen());
            nombre.setText(recibido.getNombre());
            precio.setText(Double.toString(recibido.getPrecio()));

        }

    }


    public void verCesta(View view) {

        Intent intent = new Intent(this, cesta.class);
        startActivity(intent);
    }


    public void addProducto(View view) {

        Talla tallaAintroducir = (Talla) talla.getSelectedItem();
        String colorAintroducir = (String) color.getSelectedItem();

        Producto aIntroducir = new Producto(recibido.getNombre(), recibido.getPrecio(), tallaAintroducir, colorAintroducir, recibido.getImagen());


        com.jolsensei.practicaexamen.cesta.agregarAlArray(aIntroducir);

        Toast.makeText(this, "Añadido a la cesta", Toast.LENGTH_SHORT).show();

    }
}
