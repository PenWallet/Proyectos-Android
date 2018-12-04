package com.example.ofunes.listaoverwatch;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ofunes.listaoverwatch.Entidades.Heroe;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListAdapter(new IconicAdapter<Heroe>(this, R.layout.row, arrayHeroes));
    }

    class IconicAdapter<T> extends ArrayAdapter<T>
    {
        IconicAdapter(Context c, int resourceId, T[] objects) {
            super(c, resourceId, objects);
        }

        class ViewHolder
        {
            TextView textoHeroe;
            ImageView perfil;

            public ViewHolder(TextView textoHeroe, ImageView perfil)
            {
                this.textoHeroe = textoHeroe;
                this.perfil = perfil;
            }

            public TextView getTextoHeroe(){ return this.textoHeroe; }
            public ImageView getPerfil() { return perfil; }
        }

        //La clase que creemos de ArrayAdapter debe sobreescribir al menos el método getView
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View fila = convertView;
            ViewHolder holder;
            TextView textoHeroe;
            ImageView perfil;

            if (fila==null)
            {
                LayoutInflater inflater=getLayoutInflater();
                fila=inflater.inflate(R.layout.row, parent, false);

                textoHeroe = fila.findViewById(R.id.txtNombre);
                perfil = fila.findViewById(R.id.imgFoto);
                holder = new ViewHolder(textoHeroe, perfil);
                fila.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)fila.getTag();
            }

            holder.getTextoHeroe().setText(arrayHeroes[position].getNombre());
            holder.getPerfil().setImageResource(arrayHeroes[position].getIdRImagen());

            return fila;
        }

    }
}
