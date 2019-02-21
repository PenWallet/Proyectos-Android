package com.example.ofunes.pennypanphone.Adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ofunes.pennypanphone.Entidades.Bocata;
import com.example.ofunes.pennypanphone.Entidades.IngredienteBocata;
import com.example.ofunes.pennypanphone.Entidades.FragmentOption;
import com.example.ofunes.pennypanphone.Entidades.PanPedido;
import com.example.ofunes.pennypanphone.R;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import java.util.ArrayList;

public class MarketSandwichBreadRVAdapter extends RecyclerView.Adapter<MarketSandwichBreadRVAdapter.MarketSandwichBreadViewHolder> {
    LoggedinViewModel viewModel;
    private View lastView;

    class MarketSandwichBreadViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageProduct;
        public TextView txtProductPrice, txtProductName;
        public View view;


        public MarketSandwichBreadViewHolder(View view)
        {
            super(view);
            this.view = view;
            this.txtProductPrice = view.findViewById(R.id.txtSandwichBreadPrice); txtProductPrice.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.prinsesstartabold));
            this.txtProductName = view.findViewById(R.id.txtSandwichBreadName); txtProductName.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.prinsesstartamedium));
            this.imageProduct = view.findViewById(R.id.imgSandwichBread);
        }
    }

    public MarketSandwichBreadRVAdapter(LoggedinViewModel loggedinViewModel)
    {
        this.viewModel = loggedinViewModel;
    }

    @Override
    public MarketSandwichBreadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sandwich_bread_layout, parent, false);
        MarketSandwichBreadViewHolder vh = new MarketSandwichBreadViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MarketSandwichBreadViewHolder holder, final int position) {
        final PanPedido pan = viewModel.getPanes().getValue().get(position);
        String orderP = String.format(holder.view.getResources().getString(R.string.orderPrice), pan.getPrecio());

        holder.txtProductName.setText(pan.getNombre());
        holder.txtProductPrice.setText(orderP);
        holder.imageProduct.setImageResource(pan.isIntegral() ? R.drawable.icon_wholebread128 : R.drawable.icon_bread128);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askSelectBread(holder.view, pan, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.getPanes().getValue().size();
    }

    private void askSelectBread(final View view, final PanPedido panBocata, final int position)
    {
        new MaterialStyledDialog.Builder(view.getContext())
                .setTitle(R.string.sandwichDialogBreadTitle)
                .setDescription(R.string.sandwichDialogBreadContent)
                .setStyle(Style.HEADER_WITH_ICON)
                .setIcon(panBocata.isIntegral() ? R.drawable.icon_wholebread128 : R.drawable.icon_bread128)
                .setHeaderColor(R.color.GreenBread)
                .setPositiveText(R.string.sandwichDialogBreadAffirmative)
                .setNegativeText(R.string.sandwichDialogBreadNegative)
                .setCancelable(true)
                .withIconAnimation(true)
                .withDialogAnimation(true, Duration.FAST)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(view.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                        {
                            Bocata bocata = new Bocata(panBocata, new ArrayList<IngredienteBocata>());
                            viewModel.getCesta().getValue().add(bocata);
                            viewModel.setSandwichInProgress(viewModel.getCesta().getValue().size()-1);
                            viewModel.getFragmentOption().setValue(FragmentOption.SANDWICHINGREDIENTS);
                        }
                        else
                        {
                            Bocata bocata = (Bocata)viewModel.getCesta().getValue().get(viewModel.getCesta().getValue().size() - 1);
                            bocata.setPan(panBocata);
                        }

                        view.setBackgroundColor(view.getContext().getResources().getColor(R.color.LightCyan));

                        if(lastView != null)
                            lastView.setBackgroundColor(Color.TRANSPARENT);

                        lastView = view;
                    }
                })
                .show();
    }
}
