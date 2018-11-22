package com.example.oscar.spinnerycorreo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    final String[] arrayLugares = {
            "Sevilla", "Madrid", "Londres", "Istanbul", "Paris", "Calle Regina", "Washington D.C"
    };
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinnerLugares);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayLugares);
        spinner.setAdapter(adapter);
    }

    public void mapa(View view)
    {
        Uri gmmIntentUri = Uri.parse("geo:0,0?z=10&q="+Uri.encode(spinner.getSelectedItem().toString()));
        Intent intentMapa = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        //intentMapa.setPackage("com.google.android.apps.maps");
        if (intentMapa.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intentMapa);
        }
    }

    public void mandarCorreo(View view) {
        Intent intent = new Intent(this, mandarCorreoActivity.class);
        startActivity(intent);
    }
}
