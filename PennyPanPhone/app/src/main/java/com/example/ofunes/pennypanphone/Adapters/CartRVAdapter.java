package com.example.ofunes.pennypanphone.Adapters;

import android.content.Context;
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
import com.example.ofunes.pennypanphone.Entidades.ComplementoPedido;
import com.example.ofunes.pennypanphone.Entidades.IngredienteBocata;
import com.example.ofunes.pennypanphone.Entidades.PanPedido;
import com.example.ofunes.pennypanphone.R;
import com.example.ofunes.pennypanphone.Utiliidades.Utils;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    LoggedinViewModel viewModel;

    public CartRVAdapter(LoggedinViewModel loggedinViewModel)
    {
        this.viewModel = loggedinViewModel;
    }

    class CartBMViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageProduct, imageMinus, imagePlus, imageClose;
        public TextView txtProductPrice, txtProductName, txtProductQuantity;
        public View view;

        public CartBMViewHolder(View view)
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

    class CartSViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageClose, imageBread;
        public TextView txtSandwichBreadName, txtSandwichBreadPrice, txtSandwichIngredients;
        public View view;

        public CartSViewHolder(View view)
        {
            super(view);
            this.view = view;
            this.imageBread = view.findViewById(R.id.imgSandwichBread);
            this.txtSandwichBreadName = view.findViewById(R.id.txtSandwichBreadName); txtSandwichBreadName.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.prinsesstartamedium));
            this.txtSandwichBreadPrice = view.findViewById(R.id.txtSandwichBreadPrice); txtSandwichBreadPrice.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.prinsesstartabold));
            this.txtSandwichIngredients = view.findViewById(R.id.txtSandwichIngredients); txtSandwichIngredients.setTypeface(ResourcesCompat.getFont(view.getContext(), R.font.prinsesstartamedium));
            this.imageClose = view.findViewById(R.id.imgSandwichClose);
        }
    }

    //TODO Corregir cuando haga el bocata
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == 0 || viewType == 1)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
            return new CartBMViewHolder(view);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sandwich_list_layout, parent, false);
            return new CartSViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch(holder.getItemViewType())
        {
            case 0:
                final CartBMViewHolder viewHolderBread = (CartBMViewHolder)holder;
                final PanPedido pan = (PanPedido)viewModel.getCesta().getValue().get(position);
                String orderP = String.format(viewHolderBread.view.getResources().getString(R.string.orderPrice), pan.getPrecio());

                //Asignaciones a los txt e imágenes
                viewHolderBread.txtProductName.setText(pan.getNombre());
                viewHolderBread.txtProductPrice.setText(orderP);
                viewHolderBread.txtProductQuantity.setText(String.valueOf(pan.getCantidad()));
                viewHolderBread.imageProduct.setImageResource(pan.isIntegral() ? R.drawable.icon_wholebread128 : R.drawable.icon_bread128);

                //OnClickListeners para los botones
                viewHolderBread.imagePlus.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        //Comprobar que hay menos de 100 panes pedidos
                        if(pan.getCantidad() < 100)
                        {
                            Utils.animateClick(v);

                            pan.addOne();

                            viewHolderBread.txtProductQuantity.setText(String.valueOf(pan.getCantidad()));

                            viewModel.addValueCartTotal(pan.getPrecio());
                        }
                        else
                        {
                            Utils.animateError(v);
                        }

                    }
                });


                viewHolderBread.imageMinus.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        Utils.animateClick(v);

                        if(pan.getCantidad() == 1)
                        {
                            askDeleteProduct(v.getContext(), viewModel, pan);
                        }
                        else
                        {
                            pan.substractOne();
                            viewModel.addValueCartTotal(pan.getPrecio() * -1);
                        }

                        viewHolderBread.txtProductQuantity.setText(String.valueOf(pan.getCantidad()));

                    }
                });

                viewHolderBread.imageClose.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        Utils.animateClick(v);
                        askDeleteProduct(v.getContext(), viewModel, pan);
                    }
                });

                break;

            case 1:
                final CartBMViewHolder viewHolderMisc = (CartBMViewHolder)holder;
                final ComplementoPedido complemento = (ComplementoPedido)viewModel.getCesta().getValue().get(position);
                String orderM = String.format(viewHolderMisc.view.getResources().getString(R.string.orderPrice), complemento.getPrecio());

                //Asignaciones a los txt e imágenes
                viewHolderMisc.txtProductName.setText(complemento.getNombre());
                viewHolderMisc.txtProductPrice.setText(orderM);
                viewHolderMisc.txtProductQuantity.setText(String.valueOf(complemento.getCantidad()));
                viewHolderMisc.imageProduct.setImageResource(R.drawable.icon_miscellaneous128);

                //OnClickListeners para los botones
                viewHolderMisc.imagePlus.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        //Comprobar que hay menos de 100 complementos pedidos
                        if(complemento.getCantidad() < 100)
                        {
                            Utils.animateClick(v);

                            complemento.addOne();

                            viewHolderMisc.txtProductQuantity.setText(String.valueOf(complemento.getCantidad()));

                            viewModel.addValueCartTotal(complemento.getPrecio());
                        }
                        else
                        {
                            Utils.animateError(v);
                        }

                    }
                });


                viewHolderMisc.imageMinus.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        Utils.animateClick(v);

                        if(complemento.getCantidad() == 1)
                            askDeleteProduct(v.getContext(), viewModel, complemento);
                        else
                        {
                            complemento.substractOne();
                            viewModel.addValueCartTotal(complemento.getPrecio() * -1);
                        }


                        viewHolderMisc.txtProductQuantity.setText(String.valueOf(complemento.getCantidad()));

                    }
                });

                viewHolderMisc.imageClose.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        Utils.animateClick(v);
                        askDeleteProduct(v.getContext(), viewModel, complemento);
                    }
                });
                break;

            case 2:
                final CartSViewHolder viewHolderSand = (CartSViewHolder)holder;
                final Bocata bocata = (Bocata)viewModel.getCesta().getValue().get(position);
                String orderS = String.format(viewHolderSand.view.getResources().getString(R.string.orderPrice), bocata.getPan().getPrecio());

                //Asignaciones a los txt e imágenes
                viewHolderSand.txtSandwichBreadName.setText(bocata.getPan().getNombre());
                viewHolderSand.txtSandwichBreadPrice.setText(orderS);
                viewHolderSand.txtSandwichIngredients.setText(ingredientsToString(bocata.getIngredientes()));
                viewHolderSand.imageBread.setImageResource(bocata.getPan().isIntegral() ? R.drawable.icon_wholebread128 : R.drawable.icon_bread128);

                viewHolderSand.imageClose.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        Utils.animateClick(v);
                        askDeleteProduct(v.getContext(), viewModel, bocata);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getCesta().getValue().size();
    }


    /**
     * Devuelve 0 si es un Pan, 1 si es un Complemento, 2 si es un bocata
     * @param position Position
     * @return 0 pan, 1 complemento, 2 bocata
     */
    @Override
    public int getItemViewType(int position) {
        Object object = viewModel.getCesta().getValue().get(position);
        if(object instanceof PanPedido)
            return 0;
        else if (object instanceof ComplementoPedido)
            return 1;
        else
            return 2;
    }

    private void askDeleteProduct(Context context, final LoggedinViewModel viewModel, final Object product)
    {
        new MaterialStyledDialog.Builder(context)
                .setTitle(R.string.deleteItemHeader)
                .setDescription(R.string.deleteItemContent)
                .setStyle(Style.HEADER_WITH_ICON)
                .setIcon(R.drawable.icon_deletecartitem512)
                .setHeaderColor(R.color.ErrorRed)
                .setPositiveText(R.string.deleteItemAffirmative)
                .setNegativeText(R.string.deleteItemCancel)
                .setCancelable(true)
                .withIconAnimation(true)
                .withDialogAnimation(true, Duration.FAST)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(product instanceof PanPedido)
                        {
                            PanPedido p = (PanPedido)product;
                            viewModel.addValueCartTotal(p.getPrecio() * p.getCantidad() * -1);
                            p.setCantidad(0);
                        }
                        else if(product instanceof ComplementoPedido)
                        {
                            ComplementoPedido m = (ComplementoPedido)product;
                            viewModel.addValueCartTotal(m.getPrecio() * m.getCantidad() * -1);
                            m.setCantidad(0);
                        }
                        else
                        {
                            Bocata b = (Bocata)product;
                            viewModel.addValueCartTotal(b.getPan().getPrecio() * -1);
                            for(IngredienteBocata ingrediente : b.getIngredientes())
                            {
                                viewModel.addValueCartTotal(ingrediente.getCantidad() * ingrediente.getPrecio() * -1);
                            }
                        }

                        viewModel.getCesta().getValue().remove(product);
                        notifyDataSetChanged();
                    }
                })
                .show();
    }

    private String ingredientsToString(ArrayList<IngredienteBocata> ingredientes)
    {
        StringBuilder string = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.00");

        for(IngredienteBocata ingrediente : ingredientes)
        {
            string.append(ingrediente.getCantidad()+"x "+ingrediente.getNombre()+"   EUR "+df.format(ingrediente.getCantidad()*ingrediente.getPrecio())+"\n");
        }

        return string.toString();
    }
}
