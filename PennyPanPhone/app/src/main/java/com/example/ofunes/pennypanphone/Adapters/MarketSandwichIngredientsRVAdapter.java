package com.example.ofunes.pennypanphone.Adapters;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ofunes.pennypanphone.Entidades.Bocata;
import com.example.ofunes.pennypanphone.Entidades.IngredienteBocata;
import com.example.ofunes.pennypanphone.R;
import com.example.ofunes.pennypanphone.Utils;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import java.util.HashMap;

public class MarketSandwichIngredientsRVAdapter extends RecyclerView.Adapter<MarketSandwichIngredientsRVAdapter.MarketSIngrViewHolder> {
    LoggedinViewModel viewModel;
    HashMap<IngredienteBocata, Integer> hashMap;

    class MarketSIngrViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageProduct, imageMinus, imagePlus, imageClose;
        public TextView txtProductPrice, txtProductName, txtProductQuantity;
        public View view;

        public MarketSIngrViewHolder(View view)
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

    public MarketSandwichIngredientsRVAdapter(LoggedinViewModel loggedinViewModel)
    {
        this.viewModel = loggedinViewModel;
        this.hashMap = new HashMap<>();
    }

    @Override
    public MarketSIngrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        MarketSIngrViewHolder vh = new MarketSIngrViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MarketSIngrViewHolder holder, final int position) {
        final IngredienteBocata ingr = viewModel.getIngredientes().getValue().get(position);
        String orderP = String.format(holder.view.getResources().getString(R.string.orderPrice), ingr.getPrecio());
        final Bocata bocata = (Bocata)viewModel.getCesta().getValue().get(viewModel.getSandwichInProgress());

        holder.imageClose.setVisibility(View.GONE);

        holder.txtProductName.setText(ingr.getNombre());
        holder.txtProductPrice.setText(orderP);
        holder.txtProductQuantity.setText(String.valueOf(ingr.getCantidad()));
        holder.imageProduct.setImageResource(R.drawable.icon_ingredient128);

        //OnImageClicks
        holder.imagePlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //Comprobar que hay menos de 5 ingredientes añadidos al bocata
                if(ingr.getCantidad() < 5)
                {
                    Utils.animateClick(v);

                    if(!hashMap.containsKey(ingr))
                    {
                        bocata.getIngredientes().add(new IngredienteBocata(ingr));
                        hashMap.put(ingr, bocata.getIngredientes().size()-1);
                    }

                    ingr.addOne();
                    bocata.getIngredientes().get(hashMap.get(ingr)).addOne();

                    holder.txtProductQuantity.setText(String.valueOf(ingr.getCantidad()));
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
                if(ingr.getCantidad() != 0)
                {
                    Utils.animateClick(v);
                    ingr.substractOne();

                    if(ingr.getCantidad() == 0)
                    {
                        bocata.getIngredientes().remove((int)hashMap.get(ingr));
                        hashMap.remove(ingr);
                    }
                    else
                        bocata.getIngredientes().get(hashMap.get(ingr)).substractOne();

                    holder.txtProductQuantity.setText(String.valueOf(ingr.getCantidad()));
                }
                else
                    Utils.animateError(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.getIngredientes().getValue().size();
    }
}
