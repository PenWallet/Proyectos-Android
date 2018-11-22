package com.example.oscar.tiendaderopa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private String[] listadoProductos = {"Camiseta", "Pantalon", "Zapatos", "Bufanda", "Guantes"};
    Spinner spinner;
    Button btnVerProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);
        btnVerProducto = findViewById(R.id.btnVerProducto);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listadoProductos);
        spinner.setAdapter(adapter);
    }

    public void verProducto(View view)
    {
        Intent intent = new Intent(this, ComprarActivity.class);
        intent.putExtra("categoriaRopa", spinner.getSelectedItem().toString());
        startActivity(intent);
    }
}
