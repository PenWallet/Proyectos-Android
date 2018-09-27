package com.example.oscar.clickervillains;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView imgClicker;
    TextView txtAmor;
    Resources res;
    int amor = 0;
    int amorPerClick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgClicker = (ImageView)findViewById(R.id.clicker);
        txtAmor = (TextView)findViewById(R.id.contAmor);
        imgClicker.setImageResource(R.drawable.tier10);
        txtAmor.setText("0");
        res = getResources();
    }

    public void click (View v)
    {
        amor = amor + amorPerClick;
        txtAmor.setText(Integer.toString(amor));
        cambiarImagen();
    }

    public void mejora20Amor(View v)
    {
        if(amor < 20)
            Toast.makeText(MainActivity.this, res.getString(R.string.amorFallo),Toast.LENGTH_SHORT).show();
        else
        {
            amor -= 20;
            txtAmor.setText(Integer.toString(amor));
            cambiarImagen();
            amorPerClick++;
            Toast.makeText(MainActivity.this, res.getString(R.string.amor20exito),Toast.LENGTH_SHORT).show();
        }
    }

    public void mejora100Amor(View v)
    {
        if(amor < 100)
            Toast.makeText(MainActivity.this, res.getString(R.string.amorFallo),Toast.LENGTH_SHORT).show();
        else
        {
            amor -= 100;
            txtAmor.setText(Integer.toString(amor));
            cambiarImagen();
            amorPerClick = amorPerClick + 7;
            Toast.makeText(MainActivity.this, res.getString(R.string.amor100exito),Toast.LENGTH_SHORT).show();
        }
    }

    public void cambiarImagen()
    {
        if(amor >= 3000)
            imgClicker.setImageResource(R.drawable.tier1);
        else
        {
            if(amor >= 2000)
                imgClicker.setImageResource(R.drawable.tier2);
            else
            {
                if(amor >= 1000)
                    imgClicker.setImageResource(R.drawable.tier3);
                else
                {
                    if(amor >= 500)
                        imgClicker.setImageResource(R.drawable.tier4);
                    else
                    {
                        if(amor >= 300)
                            imgClicker.setImageResource(R.drawable.tier5);
                        else
                        {
                            if(amor >= 200)
                                imgClicker.setImageResource(R.drawable.tier6);
                            else
                            {
                                if(amor >= 100)
                                    imgClicker.setImageResource(R.drawable.tier7);
                                else
                                {
                                    if(amor >= 50)
                                        imgClicker.setImageResource(R.drawable.tier8);
                                    else
                                    {
                                        if(amor >= 20)
                                            imgClicker.setImageResource(R.drawable.tier9);
                                        else
                                            imgClicker.setImageResource(R.drawable.tier10);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
