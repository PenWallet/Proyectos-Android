package com.example.ofunes.pennynotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    }
}
