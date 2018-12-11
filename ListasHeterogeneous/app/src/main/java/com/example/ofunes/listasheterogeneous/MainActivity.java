package com.example.ofunes.listasheterogeneous;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

    private static final Heroe[] arrayHeroes = {
            new Heroe("Ana", R.drawable.ana),
            new Heroe("Brigitte", R.drawable.brigitte),
            new Heroe("D.Va", R.drawable.dva),
            new Heroe("Junkrat", R.drawable.junkrat),
            new Heroe("Mercy", R.drawable.mercy),
            new Heroe("Moira", R.drawable.moira),
            new Heroe("Reaper", R.drawable.reaper),
            new Heroe("Reinhardt", R.drawable.reinhardt),
            new Heroe("Roadhog", R.drawable.roadhog)
    };

    private static final Mapa[] arrayMapas = {
            new Mapa("Hanamura", "Assault", R.drawable.hanamura),
            new Mapa("Horizon Lunar Colony", "Assault", R.drawable.horizonlunarcolony),
            new Mapa("Route 66", "Escort", R.drawable.route66),
            new Mapa("Gibraltar", "Escort", R.drawable.gibraltar),
            new Mapa("Eichenwalde", "Hybrid", R.drawable.eichenwalde),
            new Mapa("Hollywood", "Hybrid", R.drawable.hollywood),
            new Mapa("Ilios", "Control", R.drawable.hanamura),
            new Mapa("Lijiang Tower", "Control", R.drawable.hanamura),
            new Mapa("Antarctica", "Arena", R.drawable.antarctica)
    };

    ImageView imgPrueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListAdapter(new IconicAdapter(this, R.layout.filaheroe, R.layout.filamapa,
                                            arrayHeroes, arrayMapas));
    }

    class IconicAdapter extends BaseAdapter
    {
        Context c;
        int filaHeroe, filaMapa;
        Heroe[] heroes;
        Mapa[] mapas;


        IconicAdapter(Context c, int filaHeroe, int filaMapa, Heroe[] heroes, Mapa[] mapas)
        {
            this.c = c;
            this.filaHeroe = filaHeroe;
            this.filaMapa = filaMapa;
            this.heroes = heroes;
            this.mapas = mapas;
        }

        @Override
        public int getCount() {
            return arrayHeroes.length+arrayMapas.length;
        }

        @Override
        public Object getItem(int i) {
            Object ret;
            if(getItemViewType(i) == 0)
                ret = heroes[i/2];
            else
                ret = mapas[i/2];

            return ret;
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
            if(position % 2 == 0)
                ret = 0;
            else
                ret = 1;

            return ret;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View fila = convertView;
            ViewHolderHeroe vhHeroe;
            ViewHolderMapa vhMapa;
            TextView textoHeroe, nombreMapa, tipoMapa;
            ImageView perfil, fotoMapa;

            if (fila==null)
            {
                LayoutInflater inflater=getLayoutInflater();
                if(getItemViewType(position) == 0)
                {
                    fila=inflater.inflate(filaHeroe, parent, false);
                    textoHeroe = fila.findViewById(R.id.txtNombre);
                    perfil = fila.findViewById(R.id.imgFoto);
                    vhHeroe = new ViewHolderHeroe(textoHeroe, perfil);
                    fila.setTag(vhHeroe);
                }
                else if(getItemViewType(position) == 1)
                {
                    fila=inflater.inflate(filaMapa, parent, false);
                    nombreMapa = fila.findViewById(R.id.txtNombre);
                    tipoMapa = fila.findViewById(R.id.txtTipo);
                    fotoMapa = fila.findViewById(R.id.imgFoto);
                    vhMapa = new ViewHolderMapa(nombreMapa, tipoMapa, fotoMapa);
                    fila.setTag(vhMapa);
                }
            }

            //Metemos datos
            if(getItemViewType(position) == 0)
            {
                vhHeroe = (ViewHolderHeroe)fila.getTag();
                vhHeroe.getTextoHeroe().setText(heroes[position/2].getNombre());
                vhHeroe.getPerfil().setImageResource(heroes[position/2].getIdRImagen());
            }
            else
            {
                vhMapa = (ViewHolderMapa)fila.getTag();
                vhMapa.getNombreMapa().setText((mapas[position/2].getNombre()));
                vhMapa.getTipoMapa().setText(mapas[position/2].getTipo());
                vhMapa.getFotoMapa().setImageResource(mapas[position/2].getIdRImagen());
            }

            return fila;
        }
    }

    class ViewHolderHeroe
    {
        TextView textoHeroe;
        ImageView perfil;

        public ViewHolderHeroe(TextView textoHeroe, ImageView perfil)
        {
            this.textoHeroe = textoHeroe;
            this.perfil = perfil;
        }

        public TextView getTextoHeroe(){ return this.textoHeroe; }
        public ImageView getPerfil() { return perfil; }
    }

    class ViewHolderMapa
    {
        TextView nombreMapa;
        TextView tipoMapa;
        ImageView fotoMapa;

        public ViewHolderMapa(TextView nombreMapa, TextView tipoMapa, ImageView fotoMapa)
        {
            this.nombreMapa = nombreMapa;
            this.tipoMapa = tipoMapa;
            this.fotoMapa = fotoMapa;
        }

        public TextView getNombreMapa(){ return this.nombreMapa; }
        public TextView getTipoMapa() { return tipoMapa; }
        public ImageView getFotoMapa() { return fotoMapa; }
    }
}
