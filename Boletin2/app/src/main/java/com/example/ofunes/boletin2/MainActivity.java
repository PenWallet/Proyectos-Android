package com.example.ofunes.boletin2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CheckBox chkNegrita, chkGrande, chkPequenio, chkRojo;
    TextView txtTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chkGrande = (CheckBox)findViewById(R.id.chkGrande);
        chkNegrita = (CheckBox)findViewById(R.id.chkNegrita);
        chkPequenio = (CheckBox)findViewById(R.id.chkPequenio);
        chkRojo = (CheckBox)findViewById(R.id.chkRojo);
        txtTexto = (TextView)findViewById((R.id.txtTexto));
        txtTexto.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
        txtTexto.setTypeface(txtTexto.getTypeface(), Typeface.NORMAL);
        txtTexto.setTextColor(Color.BLACK);
        chkGrande.setOnClickListener(this);
        chkNegrita.setOnClickListener(this);
        chkPequenio.setOnClickListener(this);
        chkRojo.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.chkGrande:
                if(chkGrande.isChecked())
                {
                    txtTexto.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60);
                    chkPequenio.setChecked(false);
                }

                else
                    txtTexto.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
                break;

            case R.id.chkNegrita:
                if(chkNegrita.isChecked())
                    txtTexto.setTypeface(txtTexto.getTypeface(), Typeface.BOLD);
                else
                    txtTexto.setTypeface(null, Typeface.NORMAL);
                break;

            case R.id.chkPequenio:
                if(chkPequenio.isChecked())
                {
                    txtTexto.setTextSize(TypedValue.COMPLEX_UNIT_PX, 10);
                    chkGrande.setChecked(false);
                }
                else
                    txtTexto.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
                break;

            case R.id.chkRojo:
                if(chkRojo.isChecked())
                    txtTexto.setTextColor(Color.RED);
                else
                    txtTexto.setTextColor(Color.BLACK);
                break;
        }
    }
}
