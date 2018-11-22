package com.jolsensei.practicaexamen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class primerapagina extends Activity implements AdapterView.OnItemSelectedListener  {


    private Button boton;
    Spinner spinner;
    public ArrayList<Producto> listado = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primerapagina);



        listado.add(new Producto("Camiseta",10.0,Talla.XL,"Negra", R.drawable.camiseta));
        listado.add(new Producto("Sudadera",15.0,Talla.M,"Gris", R.drawable.sudadera));
        listado.add(new Producto("Pantalon",11.0,Talla.S,"Negra", R.drawable.pantalon));

        boton = findViewById(R.id.boton);

        spinner = findViewById(R.id.spinner);

        ArrayAdapter<Producto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listado);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


    }



    public void verProducto(View view) {

        Intent intent = new Intent(this, detalles.class);
        intent.putExtra("Producto", listado.get(spinner.getSelectedItemPosition()));
        startActivity(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
