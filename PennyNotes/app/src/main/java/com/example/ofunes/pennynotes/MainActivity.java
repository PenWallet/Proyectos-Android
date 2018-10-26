package com.example.ofunes.pennynotes;

import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends ListActivity {
    List<Nota> listaNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaNotas = Controller.obtenerListaNotas("", this);
        setListAdapter(new IconicAdapter<Nota>(this, R.layout.row, listaNotas));
    }

    class IconicAdapter<T> extends ArrayAdapter<T> {
        IconicAdapter(Context c, int resourceId, List<T> objects) {
            super(c, resourceId, objects);
        }

        //La clase que creemos de ArrayAdapter debe sobreescribir al menos el método getView
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View fila = convertView;

            if (fila==null)
            {
                LayoutInflater inflater=getLayoutInflater();
                fila=inflater.inflate(R.layout.row, parent, false);
            }

            TextView header = fila.findViewById(R.id.txtHeader);
            TextView texto = fila.findViewById(R.id.txtTexto);

            header.setText(listaNotas.get(position).getHeader());
            texto.setText(listaNotas.get(position).getText());


            return fila;
        }

    }
}
