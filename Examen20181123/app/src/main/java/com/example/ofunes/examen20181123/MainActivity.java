package com.example.ofunes.examen20181123;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {

    public static ArrayList<Object> listadoJugadores;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listadoJugadores = Datos.obtenerListadoJugadores();
        setListAdapter(new IconicAdapter<>(this, R.layout.filabaloncestista,
                R.layout.filafutbolista, listadoJugadores));

        lv = getListView(); lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, EditarActivity.class);
        Object jugador = listadoJugadores.get(position);
        if(jugador instanceof Futbolista)
            intent.putExtra("jugador", (Futbolista)jugador);
        else
            intent.putExtra("jugador", (Baloncestista)jugador);

        intent.putExtra("position", position);
        startActivity(intent);
    }

    class IconicAdapter<T> extends BaseAdapter
    {
        Context c;
        int filaFutbolista, filaBaloncestista;
        ArrayList<Object> listadoJug;


        IconicAdapter(Context c, int filaFutbolista, int filaBaloncestista, ArrayList<Object> listadoJug)
        {
            this.c = c;
            this.filaFutbolista = filaFutbolista;
            this.filaBaloncestista = filaBaloncestista;
            this.listadoJug = listadoJug;
        }

        @Override
        public int getCount() {
            return listadoJugadores.size();
        }

        @Override
        public Object getItem(int i)
        {
            return listadoJugadores.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getViewTypeCount()
        {
            return 2;
        }

        @Override
        public int getItemViewType(int position)
        {
            int ret;
            if(listadoJugadores.get(position) instanceof Futbolista)
                ret = 0;
            else
                ret = 1;

            return ret;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View fila = convertView;
            ViewHolderFutbolista vhFutbol;
            ViewHolderBaloncestista vhBalon;
            TextView txtNombreFutbol, txtNombreBalon, txtPosicion, txtPuntos, txtRebotes, txtAsistencias;
            ImageView imgFotoFutbol, imgFotoBalon;
            Futbolista jugFutbol;
            Baloncestista jugBalon;

            if (fila==null)
            {
                LayoutInflater inflater=getLayoutInflater();
                if(getItemViewType(position) == 0)
                {
                    fila = inflater.inflate(R.layout.filafutbolista, parent, false);
                    txtNombreFutbol = fila.findViewById(R.id.txtNombreFutbol);
                    imgFotoFutbol = fila.findViewById(R.id.imgFotoFutbol);
                    txtPosicion = fila.findViewById(R.id.txtPosicion);
                    vhFutbol = new ViewHolderFutbolista(txtNombreFutbol, imgFotoFutbol, txtPosicion);
                    fila.setTag(vhFutbol);
                }
                else if(getItemViewType(position) == 1)
                {
                    fila=inflater.inflate(R.layout.filabaloncestista, parent, false);
                    txtNombreBalon = fila.findViewById(R.id.txtNombreBalon);
                    imgFotoBalon = fila.findViewById(R.id.imgFotoBalon);
                    txtPuntos = fila.findViewById(R.id.txtPuntos);
                    txtRebotes = fila.findViewById(R.id.txtRebotes);
                    txtAsistencias = fila.findViewById(R.id.txtAsistencias);
                    vhBalon = new ViewHolderBaloncestista(txtNombreBalon, imgFotoBalon, txtPuntos, txtRebotes, txtAsistencias);
                    fila.setTag(vhBalon);
                }
            }

            //Metemos datos
            if(getItemViewType(position) == 0)
            {
                vhFutbol = (ViewHolderFutbolista) fila.getTag();
                jugFutbol = (Futbolista)listadoJug.get(position);

                vhFutbol.getTxtNombre().setText(jugFutbol.getNombre());
                vhFutbol.getImgFoto().setImageResource(jugFutbol.getFoto());
                vhFutbol.getTxtPosicion().setText(jugFutbol.getPosiciones()[jugFutbol.getPosicionActual()]);
            }
            else
            {
                vhBalon = (ViewHolderBaloncestista) fila.getTag();
                jugBalon = (Baloncestista) listadoJug.get(position);

                vhBalon.getTxtNombre().setText(jugBalon.getNombre());
                vhBalon.getImgFoto().setImageResource(jugBalon.getFoto());
                vhBalon.getTxtPuntos().setText(String.valueOf(jugBalon.getPuntosPorPartido()));
                vhBalon.getTxtRebotes().setText(String.valueOf(jugBalon.getRebotesPorPartido()));
                vhBalon.getTxtAsistencias().setText(String.valueOf(jugBalon.getAsistenciasPorPartido()));
            }

            return fila;
        }
    }

    class ViewHolderFutbolista
    {
        TextView txtNombre;
        ImageView imgFoto;
        TextView txtPosicion;

        public ViewHolderFutbolista(TextView txtNombre, ImageView imgFoto, TextView txtPosicion)
        {
            this.txtNombre = txtNombre;
            this.imgFoto = imgFoto;
            this.txtPosicion = txtPosicion;
        }

        public TextView getTxtNombre() {
            return txtNombre;
        }

        public ImageView getImgFoto() {
            return imgFoto;
        }

        public TextView getTxtPosicion() {
            return txtPosicion;
        }
    }

    class ViewHolderBaloncestista
    {
        TextView txtNombre;
        ImageView imgFoto;
        TextView txtPuntos;
        TextView txtRebotes;
        TextView txtAsistencias;


        public ViewHolderBaloncestista(TextView txtNombre, ImageView imgFoto, TextView txtPuntos, TextView txtRebotes, TextView txtAsistencias)
        {
            this.txtNombre = txtNombre;
            this.imgFoto = imgFoto;
            this.txtPuntos = txtPuntos;
            this.txtRebotes = txtRebotes;
            this.txtAsistencias = txtAsistencias;
        }

        public ImageView getImgFoto() {
            return imgFoto;
        }

        public TextView getTxtAsistencias() {
            return txtAsistencias;
        }

        public TextView getTxtNombre() {
            return txtNombre;
        }

        public TextView getTxtPuntos() {
            return txtPuntos;
        }

        public TextView getTxtRebotes() {
            return txtRebotes;
        }
    }
}
