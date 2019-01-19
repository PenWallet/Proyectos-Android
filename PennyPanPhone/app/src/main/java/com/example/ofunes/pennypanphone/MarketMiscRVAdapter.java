package com.example.ofunes.pennypanphone;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ofunes.pennypanphone.Entidades.ComplementoPedido;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import java.util.ListIterator;

public class MarketMiscRVAdapter extends RecyclerView.Adapter<MarketMiscRVAdapter.MarketMiscViewHolder> {
    LoggedinViewModel viewModel;

    class MarketMiscViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageProduct, imageMinus, imagePlus, imageClose;
        public TextView txtProductPrice, txtProductName, txtProductQuantity;
        public View view;

        public MarketMiscViewHolder(View view)
        {
            super(view);
            this.view = view;
            this.txtProductPrice = view.findViewById(R.id.txtPriceProduct); txtProductPrice.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.prinsesstartabold));
            this.txtProductName = view.findViewById(R.id.txtNameProduct); txtProductName.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.prinsesstartamedium));
            this.txtProductQuantity = view.findViewById(R.id.txtQuantityProduct); txtProductQuantity.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.prinsesstartamedium));
            this.imageProduct = view.findViewById(R.id.imgProduct);
            this.imageMinus = view.findViewById(R.id.imgMinusProduct);
            this.imagePlus = view.findViewById(R.id.imgPlusProduct);
            this.imageClose = view.findViewById(R.id.imgProductClose);
        }
    }

    public MarketMiscRVAdapter(LoggedinViewModel loggedinViewModel)
    {
        this.viewModel = loggedinViewModel;
    }

    @Override
    public MarketMiscViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        MarketMiscViewHolder vh = new MarketMiscViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MarketMiscViewHolder holder, final int position) {
        final ComplementoPedido complemento = viewModel.getComplementos().getValue().get(position);
        String orderP = String.format(holder.view.getResources().getString(R.string.orderPrice), complemento.getPrecio());

        holder.imageClose.setVisibility(View.GONE);

        holder.txtProductName.setText(complemento.getNombre());
        holder.txtProductPrice.setText(orderP);
        holder.txtProductQuantity.setText(String.valueOf(complemento.getCantidad()));
        holder.imageProduct.setImageResource(R.drawable.icon_miscellaneous128);

        //OnImageClicks
        holder.imagePlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //Comprobar que hay menos de 100 complementos pedidos
                if(complemento.getCantidad() < 100)
                {
                    Utils.animateClick(v);

                    complemento.addOne();

                    if(!viewModel.getCesta().getValue().contains(complemento))
                        viewModel.getCesta().getValue().add(complemento);

                    holder.txtProductQuantity.setText(String.valueOf(complemento.getCantidad()));

                    viewModel.addValueCartTotal(complemento.getPrecio());
                }
                else
                    Utils.animateError(v);

            }
        });


        holder.imageMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //Asegurar que no puede bajar de 0
                if(complemento.getCantidad() != 0)
                {
                    Utils.animateClick(v);

                    complemento.substractOne();

                    if(complemento.getCantidad() == 1)
                        viewModel.getCesta().getValue().remove(complemento);

                    holder.txtProductQuantity.setText(String.valueOf(complemento.getCantidad()));

                    viewModel.addValueCartTotal(complemento.getPrecio()*-1);
                }
                else
                    Utils.animateError(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.getComplementos().getValue().size();
    }
}
