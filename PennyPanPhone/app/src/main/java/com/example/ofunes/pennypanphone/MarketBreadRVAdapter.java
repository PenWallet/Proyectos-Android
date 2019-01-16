package com.example.ofunes.pennypanphone;

import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ofunes.pennypanphone.Entidades.Bocata;
import com.example.ofunes.pennypanphone.Entidades.ComplementoPedido;
import com.example.ofunes.pennypanphone.Entidades.IngredienteBocata;
import com.example.ofunes.pennypanphone.Entidades.Pan;
import com.example.ofunes.pennypanphone.Entidades.PanPedido;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MarketBreadRVAdapter extends RecyclerView.Adapter<MarketBreadRVAdapter.MarketBreadViewHolder> {
    ArrayList<Pan> listadoPan;

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

    public MarketBreadRVAdapter(ArrayList<Pan> listadoPedidos)
    {
        this.listadoPan = listadoPedidos;
    }

    @Override
    public MarketBreadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        MarketBreadViewHolder vh = new MarketBreadViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MarketBreadViewHolder holder, int position) {
        Pan pan = listadoPan.get(position);
        String orderP = String.format(holder.view.getResources().getString(R.string.orderPrice), pan.getPrecio());

        holder.imageClose.setVisibility(View.GONE);

        holder.txtProductName.setText(pan.getNombre());
        holder.txtProductPrice.setText(orderP);
        holder.txtProductQuantity.setText("0");
        holder.imageProduct.setImageResource(pan.isIntegral() ? R.drawable.icon_wholebread128 : R.drawable.icon_bread128);

        //OnImageClicks
        holder.imagePlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                ScaleAnimation animation = new ScaleAnimation(1, 1.2f, 1, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setInterpolator(new CycleInterpolator(1));
                animation.setDuration(200);
                animation.setRepeatCount(0);

                View parent = (View)v.getParent().getParent();
                if(parent != null)
                {
                    TextView textView = parent.findViewById(R.id.txtQuantityProduct);
                    textView.setText(String.valueOf(Integer.parseInt(textView.getText().toString())+1));
                }

                v.startAnimation(animation);
            }
        });

        holder.imageMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                ScaleAnimation animation = new ScaleAnimation(1, 1.2f, 1, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setInterpolator(new CycleInterpolator(1));
                animation.setDuration(200);
                animation.setRepeatCount(0);

                v.startAnimation(animation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listadoPan.size();
    }

}
