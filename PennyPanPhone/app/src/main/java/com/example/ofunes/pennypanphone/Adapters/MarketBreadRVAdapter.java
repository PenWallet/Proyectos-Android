package com.example.ofunes.pennypanphone.Adapters;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ofunes.pennypanphone.Entidades.PanPedido;
import com.example.ofunes.pennypanphone.R;
import com.example.ofunes.pennypanphone.Utiliidades.Utils;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

public class MarketBreadRVAdapter extends RecyclerView.Adapter<MarketBreadRVAdapter.MarketBreadViewHolder> {
    LoggedinViewModel viewModel;

    class MarketBreadViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageProduct, imageMinus, imagePlus, imageClose;
        public TextView txtProductPrice, txtProductName, txtProductQuantity;
        public View view;

        public MarketBreadViewHolder(View view)
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

    public MarketBreadRVAdapter(LoggedinViewModel loggedinViewModel)
    {
        this.viewModel = loggedinViewModel;
    }

    @Override
    public MarketBreadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        MarketBreadViewHolder vh = new MarketBreadViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MarketBreadViewHolder holder, final int position) {
        final PanPedido pan = viewModel.getPanes().getValue().get(position);
        String orderP = String.format(holder.view.getResources().getString(R.string.orderPrice), pan.getPrecio());

        holder.imageClose.setVisibility(View.GONE);

        holder.txtProductName.setText(pan.getNombre());
        holder.txtProductPrice.setText(orderP);
        holder.txtProductQuantity.setText(String.valueOf(pan.getCantidad()));
        holder.imageProduct.setImageResource(pan.isIntegral() ? R.drawable.icon_wholebread128 : R.drawable.icon_bread128);

        //OnImageClicks
        holder.imagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Comprobar que hay menos de 100 panes pedidos
                if(pan.getCantidad() < 100)
                {
                    Utils.animateClick(v);

                    pan.addOne();

                    if(!viewModel.getCesta().getValue().contains(pan))
                        viewModel.getCesta().getValue().add(pan);

                    holder.txtProductQuantity.setText(String.valueOf(pan.getCantidad()));

                    viewModel.addValueCartTotal(pan.getPrecio());
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
                if(pan.getCantidad() != 0)
                {
                    Utils.animateClick(v);

                    pan.substractOne();

                    if(pan.getCantidad() == 1)
                        viewModel.getCesta().getValue().remove(pan);

                    holder.txtProductQuantity.setText(String.valueOf(pan.getCantidad()));

                    viewModel.addValueCartTotal(pan.getPrecio()*-1);
                }
                else
                    Utils.animateError(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.getPanes().getValue().size();
    }
}
