package com.example.ofunes.pennypanphone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ofunes.pennypanphone.Entidades.Complemento;
import com.example.ofunes.pennypanphone.Entidades.ComplementoPedido;
import com.example.ofunes.pennypanphone.Entidades.PanPedido;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import java.util.Iterator;
import java.util.ListIterator;

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

    //TODO Corregir cuando haga el bocata
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == 0 || viewType == 1)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        }

        return new CartBMViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch(holder.getItemViewType())
        {
            case 0:
                final CartBMViewHolder viewHolder = (CartBMViewHolder)holder;
                final PanPedido pan = (PanPedido)viewModel.getCesta().getValue().get(position);
                //final PanPedido panInMarket = searchBreadInMarket(pan);
                String orderP = String.format(viewHolder.view.getResources().getString(R.string.orderPrice), pan.getPrecio());

                //Asignaciones a los txt e imágenes
                viewHolder.txtProductName.setText(pan.getNombre());
                viewHolder.txtProductPrice.setText(orderP);
                viewHolder.txtProductQuantity.setText(String.valueOf(pan.getCantidad()));
                viewHolder.imageProduct.setImageResource(pan.isIntegral() ? R.drawable.icon_wholebread128 : R.drawable.icon_bread128);

                //OnClickListeners para los botones
                viewHolder.imagePlus.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        //Comprobar que hay menos de 100 panes pedidos
                        if(pan.getCantidad() < 100)
                        {
                            Utils.animateClick(v);

                            pan.addOne();
                            viewModel.getCesta().getValue().add(pan);

                            viewHolder.txtProductQuantity.setText(String.valueOf(pan.getCantidad()));
                        }
                        else
                        {
                            Utils.animateError(v);
                        }

                    }
                });


                viewHolder.imageMinus.setOnClickListener(new View.OnClickListener(){
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
                        }

                        viewHolder.txtProductQuantity.setText(String.valueOf(pan.getCantidad()));

                    }
                });

                viewHolder.imageClose.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        Utils.animateClick(v);
                        askDeleteProduct(v.getContext(), viewModel, pan);
                    }
                });

                break;

            case 1:
                break;

            case 2:
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
                .setHeaderColor(R.color.DeleteRed)
                .setPositiveText(R.string.deleteItemAffirmative)
                .setNegativeText(R.string.deleteItemCancel)
                .setCancelable(true)
                .withIconAnimation(true)
                .withDialogAnimation(true, Duration.FAST)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(product instanceof PanPedido)
                            ((PanPedido)product).setCantidad(0);
                        else if(product instanceof ComplementoPedido)
                            ((ComplementoPedido)product).setCantidad(0);

                        viewModel.getCesta().getValue().remove(product);
                        notifyDataSetChanged();
                    }
                })
                .show();
    }

    private PanPedido searchBreadInMarket(PanPedido pan)
    {
        PanPedido panEncontrado = null;
        Iterator<PanPedido> iterator = viewModel.getPanes().getValue().iterator();
        while(iterator.hasNext())
        {
            panEncontrado = iterator.next();
            if(pan.equals(panEncontrado))
                break;
        }

        return panEncontrado;
    }
}
