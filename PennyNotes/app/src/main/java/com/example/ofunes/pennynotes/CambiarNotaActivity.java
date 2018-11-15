package com.example.ofunes.pennynotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ResourceBundle;

public class CambiarNotaActivity extends AppCompatActivity {

    EditText editTitle, editNote;
    Nota nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_nota);
        editTitle = findViewById(R.id.editTitle);
        editNote = findViewById(R.id.editNote);

        editTitle.setEnabled(false); //No editable

        String nombreNota = getIntent().getStringExtra("nombreNota");

        nota = Controller.obtenerNota(nombreNota, this);

        editTitle.setText(nota.getHeader());
        editNote.setText(nota.getText());
    }

    public void cambiarNota(View v)
    {
        Nota notaCambiada;
        boolean guardadaCorrectamente;

        if(editNote.getText().toString().equals(""))
            Toast.makeText(this, getString(R.string.emptyNote), Toast.LENGTH_LONG).show();
        else
        {
            notaCambiada = new Nota(nota.getHeader(), editNote.getText().toString());

            guardadaCorrectamente = Controller.guardarNota(notaCambiada, this);
            if(guardadaCorrectamente)
                Toast.makeText(this, getString(R.string.noteChanged), Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, getString(R.string.noteNotChanged), Toast.LENGTH_LONG).show();

            //Se termina la actividad se haya creado correctamente o no
            finish();
        }
    }

}

