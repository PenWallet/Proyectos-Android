package com.example.ofunes.examen20181123;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class EditarActivity extends AppCompatActivity {

    ImageView foto;
    TextView nombre, bText1, bText2, bText3, fText1;
    EditText bTPuntos, bTRebotes, bTAsistencias;
    Futbolista futbolista;
    Baloncestista baloncestista;
    Spinner futbolSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        inicializarComponentes();
    }

    private void inicializarComponentes()
    {
        foto = findViewById(R.id.imgFoto);
        nombre = findViewById(R.id.txtNombre);
        Object jugador = getIntent().getParcelableExtra("jugador");

        //Comprobamos si es un Futbolista o un Baloncestista
        if(jugador instanceof Futbolista)
        {
            futbolista = (Futbolista)jugador;

            //Ponemos visible lo propio de los futbolistas
            fText1 = findViewById(R.id.futbolTextPosicion); fText1.setVisibility(View.VISIBLE);
            futbolSpinner = findViewById(R.id.futbolSpinner); futbolSpinner.setVisibility(View.VISIBLE);

            foto.setImageResource(futbolista.getFoto());
            nombre.setText(futbolista.getNombre());
            futbolSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, futbolista.getPosiciones()));
            futbolSpinner.setSelection(futbolista.getPosicionActual());
        }
        else
        {
            baloncestista = (Baloncestista)jugador;

            bText1 = findViewById(R.id.balonText1); bText1.setVisibility(View.VISIBLE);
            bText2 = findViewById(R.id.balonText2); bText2.setVisibility(View.VISIBLE);
            bText3 = findViewById(R.id.balonText3); bText3.setVisibility(View.VISIBLE);
            bTPuntos = findViewById(R.id.balonTxtPuntos); bTPuntos.setVisibility(View.VISIBLE);
            bTAsistencias = findViewById(R.id.balonTxtAsistencias); bTAsistencias.setVisibility(View.VISIBLE);
            bTRebotes = findViewById(R.id.balonTxtRebotes); bTRebotes.setVisibility(View.VISIBLE);

            baloncestista = (Baloncestista)jugador;
            foto.setImageResource(baloncestista.getFoto());
            nombre.setText(baloncestista.getNombre());
            bTPuntos.setText(String.valueOf(baloncestista.getPuntosPorPartido()));
            bTAsistencias.setText(String.valueOf(baloncestista.getAsistenciasPorPartido()));
            bTRebotes.setText(String.valueOf(baloncestista.getRebotesPorPartido()));
        }
    }
}
