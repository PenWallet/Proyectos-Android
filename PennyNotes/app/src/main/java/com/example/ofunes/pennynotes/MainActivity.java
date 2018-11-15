package com.example.ofunes.pennynotes;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {
    List<Nota> listaNotas;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = getListView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        listaNotas = Controller.obtenerListaNotas(this);
        setListAdapter(new IconicAdapter<>(this, R.layout.row, listaNotas));
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        String prueba = listaNotas.get((int)l).getHeader();
        Intent cambiarNotaIntent = new Intent(this, CambiarNotaActivity.class);
        cambiarNotaIntent.putExtra("nombreNota", prueba);
        startActivity(cambiarNotaIntent);
    }

    class IconicAdapter<T> extends ArrayAdapter<T>
    {
        IconicAdapter(Context c, int resourceId, List<T> objects) {
            super(c, resourceId, objects);
        }

        //La clase que creemos de ArrayAdapter debe sobreescribir al menos el método getView
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View fila = convertView;
            ViewHeader vh = null;
            TextView header = null;
            TextView texto = null;

            if (fila==null)
            {
                LayoutInflater inflater=getLayoutInflater();
                fila=inflater.inflate(R.layout.row, parent, false);
                header = fila.findViewById(R.id.txtHeader);
                texto = fila.findViewById(R.id.txtTexto);
                vh = new ViewHeader(header, texto);
                fila.setTag(vh);
            }
            else
            {
                vh = (ViewHeader)fila.getTag();
                header = vh.getHeader();
                texto = vh.getTexto();
            }

            header.setText(listaNotas.get(position).getHeader());
            texto.setText(listaNotas.get(position).getText());

            return fila;
        }

        class ViewHeader
        {
            TextView header;
            TextView texto;

            public ViewHeader(TextView header, TextView texto)
            {
                this.header = header;
                this.texto = texto;
            }

            public TextView getHeader() {
                return header;
            }

            public TextView getTexto() {
                return texto;
            }
        }
    }

    public void anadirNota(View view)
    {
        Intent intent = new Intent(this, anadirNota.class);
        String[] arrayNombreNotas = new String[listaNotas.size()];
        for(int i = 0; i < listaNotas.size(); i++)
        {
            arrayNombreNotas[i] = listaNotas.get(i).getHeader();
        }
        intent.putExtra("listaNotas", arrayNombreNotas);
        startActivity(intent);
    }
}
