package com.example.ofunes.pennynotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String[] lista = {"perro", "gato", "loro", "leon", "cebra", "ornitorrinco",
    "girafa", "delfín", "nacho", "hipopotamo", "paloma", "rata"};
    ListView lv;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.listView);
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.fila, R.id.txtTexto2, lista));
        lv.setOnItemClickListener(this);
        txt = findViewById(R.id.txtTop);
    }

    //Mirar método getView

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        txt.setText(lista[i]);
    }
}
