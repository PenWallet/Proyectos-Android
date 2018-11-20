package com.example.ofunes.pennynotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class anadirNota extends AppCompatActivity {

    TextView editTitle, editNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_nota);
        editTitle = findViewById(R.id.editTitle);
        editNote = findViewById(R.id.editNote);
    }

    public void crearNota(View v)
    {
        String[] arrayNombreNotas;
        boolean nombreIgual = false, creadaCorrectamente;
        String nombreNota;
        Nota n;

        //Comprobar si el título de la nota está vacío
        if(editTitle.getText().toString().equals(""))
            Toast.makeText(this, getString(R.string.emptyTitle), Toast.LENGTH_LONG).show();
        //Si no, comprobar que el texto del título es solo letras y números
        else if(!editTitle.getText().toString().matches("^[a-zA-Z0-9_.-]*$"))
            Toast.makeText(this, getString(R.string.titleNotCorrect), Toast.LENGTH_LONG).show();
        //Si no, comprobar si el texto de la nota está vacío
        else if(editNote.getText().toString().equals(""))
            Toast.makeText(this, getString(R.string.emptyNote), Toast.LENGTH_LONG).show();
        //else if(editTitle.getText().toString().matches("[a-zA-Z]"))
        else
        {
            nombreNota = editTitle.getText().toString();
            arrayNombreNotas = getIntent().getStringArrayExtra("listaNotas");
            //Comprobar que no existe ya una nota con ese nombre
            for(int i = 0; i < arrayNombreNotas.length && !nombreIgual; i++)
            {
                if(arrayNombreNotas[i].equals(nombreNota))
                    nombreIgual = true;
            }

            //Si no existe una nota con ese nombre, se crea
            if(!nombreIgual)
            {
                n = new Nota(editTitle.getText().toString(), editNote.getText().toString());
                creadaCorrectamente = Controller.guardarNota(n, this);
                if(creadaCorrectamente)
                    Toast.makeText(this, getString(R.string.noteCreated), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, getString(R.string.noteNotCreated), Toast.LENGTH_LONG).show();

                //Se termina la actividad se haya creado correctamente o no
                finish();
            }
            else
                Toast.makeText(this, getString(R.string.noteAlreadyExists), Toast.LENGTH_LONG).show();
        }

    }
}
