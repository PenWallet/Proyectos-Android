package com.example.ofunes.ejercicio5y6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtTamano;
    float size;
    ImageView img;
    boolean cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTamano = (TextView) findViewById(R.id.txtTamano);
        size = txtTamano.getTextSize();
        img = (ImageView)findViewById(R.id.img);
        img.setImageResource(R.drawable.cat); cat = true;
    }

    public void pequenio (View v)
    {
        txtTamano.setTextSize(TypedValue.COMPLEX_UNIT_PX,size-1);
        txtTamano.setText(Float.toString(size));
    }

    public void grandote (View v)
    {
        txtTamano.setTextSize(TypedValue.COMPLEX_UNIT_PX,size+1);
        txtTamano.setText(Float.toString(size));
    }

    public void changeImage(View v)
    {
        cat = !cat;
        if(cat)
            img.setImageResource(R.drawable.cat);
        else
            img.setImageResource(R.drawable.dog);
    }
}
