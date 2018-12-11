package com.example.ofunes.listaoverwatch;

import android.app.ListActivity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ofunes.listaoverwatch.Entidades.Heroe;
import com.example.ofunes.listaoverwatch.Repositories.FuenteDeDatosJeje;
import com.example.ofunes.listaoverwatch.Repositories.ListadosHeroes;
import com.example.ofunes.listaoverwatch.ViewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MainViewModel mainVM;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainVM = ViewModelProviders.of(this).get(MainViewModel.class);
        lv = findViewById(R.id.listView);
        lv.setAdapter(new IconicAdapter(this, R.layout.row));
    }

    class IconicAdapter extends BaseAdapter
    {
        Context context;
        int filaInt;

        IconicAdapter(Context c, int resourceId) {
            this.context = c;
            this.filaInt = resourceId;
        }

        @Override
        public int getCount() {
            return mainVM.getListadoHeroes().size();
        }

        @Override
        public Object getItem(int position) {
            return mainVM.getListadoHeroes().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount()
        {
            return 1;
        }

        @Override
        public int getItemViewType(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View fila = convertView;
            ViewHolder holder;
            TextView textoHeroe;
            ImageView perfil;

            if (fila==null)
            {
                LayoutInflater inflater=getLayoutInflater();
                fila=inflater.inflate(filaInt, parent, false);

                textoHeroe = fila.findViewById(R.id.txtNombre);
                perfil = fila.findViewById(R.id.imgFoto);
                holder = new ViewHolder(textoHeroe, perfil);
                fila.setTag(holder);
            }
            else
                holder = (ViewHolder)fila.getTag();

            holder.getTextoHeroe().setText(mainVM.getListadoHeroes().get(position).getNombre());
            holder.getPerfil().setImageResource(mainVM.getListadoHeroes().get(position).getIdRImagen());

            return fila;
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
    }
}
