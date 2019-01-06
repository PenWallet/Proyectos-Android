package com.example.ofunes.pennypanphone;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ofunes.pennypanphone.Entidades.Bocata;
import com.example.ofunes.pennypanphone.Entidades.ComplementoPedido;
import com.example.ofunes.pennypanphone.Entidades.IngredienteBocata;
import com.example.ofunes.pennypanphone.Entidades.PanPedido;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrdersRVAdapter extends RecyclerView.Adapter<OrdersRVAdapter.OrdersViewHolder> {
    ArrayList<Pedido> listadoPedidos;

    class OrdersViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView orderImage;
        public TextView orderNumber, orderDate, orderPrice, orderPanes, orderPanesTitle, orderComplementos, orderComplementosTitle, orderBocatas, orderBocatasTitle;
        public Resources resources;
        public LinearLayout linearLayout;

        public OrdersViewHolder(View view)
        {
            super(view);
            this.orderImage = view.findViewById(R.id.orderImage);
            this.orderNumber = view.findViewById(R.id.txtOrderNumber);
            this.orderDate = view.findViewById(R.id.txtOrderDate);
            this.orderPrice = view.findViewById(R.id.txtOrderPrice);
            this.linearLayout = view.findViewById(R.id.linearCardExpandInfo);
            this.orderPanes = view.findViewById(R.id.txtOrderPanes);
            this.orderPanesTitle = view.findViewById(R.id.txtOrderPanesTitle);
            this.orderComplementos = view.findViewById(R.id.txtOrderComplementos);
            this.orderComplementosTitle = view.findViewById(R.id.txtOrderComplementosTitle);
            this.orderBocatas = view.findViewById(R.id.txtOrderBocatas);
            this.orderBocatasTitle = view.findViewById(R.id.txtOrderBocatasTitle);
            resources = view.getResources();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(linearLayout.getVisibility() == View.VISIBLE)
                    {
                        linearLayout.setVisibility(View.GONE);
                    }
                    else
                    {
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    public OrdersRVAdapter(ArrayList<Pedido> listadoPedidos)
    {
        this.listadoPedidos = listadoPedidos;
    }

    public OrdersRVAdapter(List<Pedido> listadoPedidos)
    {
        this.listadoPedidos = new ArrayList<>(listadoPedidos);
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card, parent, false);
        OrdersViewHolder vh = new OrdersViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(OrdersViewHolder holder, int position) {
        Pedido pedido = listadoPedidos.get(position);
        String orderN = String.format(holder.resources.getString(R.string.orderNumber), pedido.getId());
        String orderP = String.format(holder.resources.getString(R.string.orderPrice), pedido.getImporteTotal());

        holder.orderImage.setImageResource(R.drawable.loaf);
        holder.orderDate.setText(pedido.getFechaCompra());
        holder.orderNumber.setText(orderN);
        holder.orderPrice.setText(orderP);

        Bocata[] bocatas = pedido.getBocatas();
        PanPedido[] panes = pedido.getPanes();
        ComplementoPedido[] complementos = pedido.getComplementos();
        DecimalFormat df = new DecimalFormat(".##");

        if(panes == null || panes.length == 0)
        {
            holder.orderPanes.setVisibility(View.GONE);
            holder.orderPanesTitle.setVisibility(View.GONE);
        }
        else
        {
            holder.orderPanes.setVisibility(View.VISIBLE);
            holder.orderPanesTitle.setVisibility(View.VISIBLE);

            StringBuilder stringPanes = new StringBuilder();
            PanPedido pan;

            for(int i = 0; i < panes.length; i++)
            {
                pan = panes[i];
                stringPanes.append((i == 0 ? "":"\n")+pan.getCantidad()+"x "+pan.getNombre()+"   EUR "+df.format(pan.getCantidad()*pan.getPrecio()));
            }

            holder.orderPanes.setText(stringPanes);
        }

        if(complementos == null || complementos.length == 0)
        {
            holder.orderComplementos.setVisibility(View.GONE);
            holder.orderComplementosTitle.setVisibility(View.GONE);
        }
        else
        {
            holder.orderComplementos.setVisibility(View.VISIBLE);
            holder.orderComplementosTitle.setVisibility(View.VISIBLE);

            StringBuilder stringComplementos = new StringBuilder();
            ComplementoPedido complemento;

            for(int i = 0; i < complementos.length; i++)
            {
                complemento = complementos[i];
                stringComplementos.append((i == 0 ? "":"\n")+complemento.getCantidad()+"x "+complemento.getNombre()+"   EUR "+df.format(complemento.getCantidad()*complemento.getPrecio()));
            }

            holder.orderComplementos.setText(stringComplementos);
        }

        if(bocatas == null || bocatas.length == 0)
        {
            holder.orderBocatas.setVisibility(View.GONE);
            holder.orderBocatasTitle.setVisibility(View.GONE);
        }
        else
        {
            holder.orderBocatas.setVisibility(View.VISIBLE);
            holder.orderBocatasTitle.setVisibility(View.VISIBLE);

            StringBuilder stringBocatas = new StringBuilder();
            Bocata bocata;
            IngredienteBocata[] ingredientes;
            IngredienteBocata ingrediente;

            for(int i = 0; i < bocatas.length; i++)
            {
                bocata = bocatas[i];
                stringBocatas.append((i == 0 ? "":"\n")+bocata.getPan().getNombre()+"   EUR "+bocata.getPan().getPrecio());

                ingredientes = bocata.getIngredientes();

                for(int j = 0; j < ingredientes.length; j++)
                {
                    ingrediente = ingredientes[i];
                    stringBocatas.append("\n"+ingrediente.getCantidad()+"x "+ingrediente.getNombre()+"   EUR "+df.format(ingrediente.getCantidad()*ingrediente.getPrecio()));
                }
            }

            holder.orderBocatas.setText(stringBocatas);
        }
    }

    @Override
    public int getItemCount() {
        return listadoPedidos.size();
    }

}
