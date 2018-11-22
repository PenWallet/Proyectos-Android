package com.jolsensei.practicaexamen;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class cesta extends Activity {


    ListView cesta;
    private static ArrayList<Producto> contenidoCesta = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);




        cesta = findViewById(android.R.id.list);

        //ArrayAdapter<Producto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contenidoCesta);



        cesta.setAdapter(new MyAdapter<Producto>(this, R.layout.filacesta, contenidoCesta));


    }


    public static void agregarAlArray(Producto producto){

        contenidoCesta.add(producto);

    }





    class MyAdapter<T> extends ArrayAdapter<T> {
        MyAdapter(Context c, int resourceId, ArrayList objects) {
            super(c, resourceId, objects);
        }

        public View getView(int position, View convertView,ViewGroup parent) {

            View row = convertView;
            ViewHolder holder;

            TextView nombre;
            TextView talla;
            TextView color;
            TextView precio;
            ImageView icon;


            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.filacesta, parent, false);

                nombre = (TextView) row.findViewById(R.id.nombre);
                talla = (TextView) row.findViewById(R.id.talla);
                color = (TextView) row.findViewById(R.id.color);
                precio = (TextView) row.findViewById(R.id.precio);
                icon = (ImageView) row.findViewById(R.id.imagen);

                //Con viewholder
                holder = new ViewHolder (icon, nombre, talla, color, precio);
                row.setTag(holder);

            } else {

                holder = (ViewHolder) row.getTag();
            }

            holder.getLab1().setText(contenidoCesta.get(position).getNombre());
            holder.getLab2().setText(contenidoCesta.get(position).getTalla().toString());
            holder.getLab3().setText(contenidoCesta.get(position).getColor());
            holder.getLab4().setText(String.valueOf(contenidoCesta.get(position).getPrecio()));
            holder.getImg().setImageResource(contenidoCesta.get(position).getImagen());

            return (row);
        }


        class ViewHolder {
            ImageView img;
            TextView lab1;
            TextView lab2;
            TextView lab3;
            TextView lab4;


            public ViewHolder (ImageView imgId, TextView labId1,TextView labId2, TextView labId3, TextView labId4){


                this.lab1 = labId1;
                this.lab2 = labId2;
                this.lab3 = labId3;
                this.lab4 = labId4;
                this.img = imgId;
            }

            public TextView getLab1 (){
                return this.lab1;
            }

            public TextView getLab2 (){
                return this.lab2;
            }

            public TextView getLab3 (){
                return this.lab3;
            }

            public TextView getLab4 (){
                return this.lab4;
            }

            public ImageView getImg (){
                return this.img;
            }
        }
    }

}
