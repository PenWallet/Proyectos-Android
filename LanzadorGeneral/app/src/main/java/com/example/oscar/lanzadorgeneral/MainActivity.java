package com.example.oscar.lanzadorgeneral;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void lanzarCalculadora(View view)
    {

    }

    public void lanzarNotas(View view)
    {
        Intent intent = new Intent();
        intent.getPackageManager()
    }

    public void lanzarLista(View view)
    {

    }

    public void lanzarClicker(View view)
    {

    }
}
