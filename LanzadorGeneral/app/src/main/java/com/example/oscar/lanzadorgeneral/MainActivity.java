package com.example.oscar.lanzadorgeneral;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.textView);
    }

    public void lanzarCalculadora(View view)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_CALCULATOR);
        intent.putExtra("fromActivity", true);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
        else
        {
            Toast.makeText(this,"No se ha encontrado ninguna app",Toast.LENGTH_SHORT).show();
        }

    }

    public void lanzarNotas(View view)
    {
        Intent intent = new Intent();
        //intent.getPackageManager()
    }

    public void lanzarLista(View view)
    {

    }

    public void lanzarClicker(View view)
    {

    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                txt.setText(String.valueOf(data.getDoubleExtra("resultado", 0)));
            }
        }
    }
}
