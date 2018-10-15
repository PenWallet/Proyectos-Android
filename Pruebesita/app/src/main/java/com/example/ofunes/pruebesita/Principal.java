package com.example.ofunes.pruebesita;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;

public class Principal extends AppCompatActivity {
    EditText n1, n2;
    TextView resultado, ej2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        n1 = (EditText)findViewById(R.id.n1);
        n2 = (EditText)findViewById(R.id.n2);
        resultado = (TextView)findViewById(R.id.resultado);
        ej2 = (TextView)findViewById(R.id.ej2text);

    }

    public void sumar(View v)
    {
        int number1 = Integer.parseInt(n1.getText().toString());
        int number2 = Integer.parseInt(n2.getText().toString());
        String res = Integer.toString(number1 + number2);
        resultado.setText(res);
    }

    public void cambiarColorRojo(View v)
    {
        ej2.setTextColor(Color.parseColor("#ff0000"));
    }

    public void cambiarColorVerde(View v)
    {
        ej2.setTextColor(Color.parseColor("#00ff00"));
    }

    public void cambiarColorAzul(View v)
    {
        ej2.setTextColor(Color.parseColor("#0000ff"));
    }
}
