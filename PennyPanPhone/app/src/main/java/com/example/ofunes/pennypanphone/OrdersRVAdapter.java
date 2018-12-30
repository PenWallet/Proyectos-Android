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

import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersRVAdapter extends RecyclerView.Adapter<OrdersRVAdapter.OrdersViewHolder> {
    ArrayList<Pedido> listadoPedidos;

    class OrdersViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView orderImage;
        public TextView orderNumber, orderDate, orderPrice;
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
    }

    @Override
    public int getItemCount() {
        return listadoPedidos.size();
    }

}
