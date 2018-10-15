package com.example.ofunes.ejercicio3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView texto;
    ImageView fotos;
    int contImagen = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto = (TextView)findViewById(R.id.txtAlinear);
        fotos = (ImageView) findViewById(R.id.fotos);
    }

    public void alinearIzquierda(View v)
    {
        texto.setGravity(Gravity.LEFT);
    }

    public void alinearDerecha(View v)
    {
        texto.setGravity(Gravity.RIGHT);
    }

    public void imgSiguiente(View v)
    {
        if(++contImagen == 4)
            contImagen = 1;
        mostrarFoto();

    }

    public void imgAnterior(View v)
    {
        if(--contImagen < 1)
            contImagen = 3;

        mostrarFoto();

    }

    public void mostrarFoto()
    {
        switch (contImagen)
        {
            case 1: fotos.setImageResource(R.drawable.cat1); break;
            case 2: fotos.setImageResource(R.drawable.cat2); break;
            case 3: fotos.setImageResource(R.drawable.cat3); break;
        }
    }
}
