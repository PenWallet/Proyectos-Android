package com.example.ofunes.pennypanphone.Fragments;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ofunes.pennypanphone.Entidades.Bocata;
import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.ComplementoPedido;
import com.example.ofunes.pennypanphone.Entidades.FragmentOption;
import com.example.ofunes.pennypanphone.Entidades.IngredienteBocata;
import com.example.ofunes.pennypanphone.Entidades.PanPedido;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.R;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCartPaymentMethod extends Fragment implements View.OnClickListener {

    LoggedinViewModel viewModel;
    CardView cardViewCash, cardViewCard;
    AppCompatTextView txtTitle;
    LinearLayout linearLayout;

    public FragmentCartPaymentMethod() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart_payment_method, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(LoggedinViewModel.class);

        cardViewCash = getActivity().findViewById(R.id.orderPaymentCash); cardViewCash.setOnClickListener(this);
        cardViewCard = getActivity().findViewById(R.id.orderPaymentCard); cardViewCard.setOnClickListener(this);
        txtTitle = getActivity().findViewById(R.id.txtOrderPaymentMethodTitle); txtTitle.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartabolditalic));
        linearLayout = getActivity().findViewById(R.id.progressBarPost); linearLayout.setVisibility(View.GONE);

        final Observer<Boolean> postOKObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isPostOK) {

                new MaterialStyledDialog.Builder(getContext())
                        .setTitle(isPostOK ? R.string.postOrderOKDialogTitle : R.string.postOrderNotOKDialogTitle)
                        .setDescription(isPostOK ? R.string.postOrderOKDialogContent : R.string.postOrderNotOKDialogContent)
                        .setPositiveText(isPostOK ? R.string.postOrderOKDialogAffirmative : R.string.postOrderNotOKDialogAffirmative)
                        .setStyle(Style.HEADER_WITH_ICON)
                        .setIcon(isPostOK ? R.drawable.icon_success512 : R.drawable.icon_error512)
                        .setHeaderColor(isPostOK ? R.color.GreenMoney : R.color.ErrorRed)
                        .setCancelable(false)
                        .withIconAnimation(true)
                        .withDialogAnimation(true, Duration.SLOW)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //Reseteamos todo lo relacionado con los pedidos
                                viewModel.getCesta().setValue(new ArrayList<>());
                                for(PanPedido panPedido : viewModel.getPanes().getValue())
                                    panPedido.setCantidad(0);
                                for(ComplementoPedido complementoPedido : viewModel.getComplementos().getValue())
                                    complementoPedido.setCantidad(0);
                                for(IngredienteBocata ingrediente : viewModel.getIngredientes().getValue())
                                    ingrediente.setCantidad(0);
                                viewModel.setSandwichInProgress(-1);
                                viewModel.getCartTotal().setValue(0d);

                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                viewModel.setPostOK(new MutableLiveData<Boolean>());

                                viewModel.getFragmentOption().setValue(FragmentOption.ORDERS);
                            }
                        })
                        .show();
            }
        };

        viewModel.getPostOK().observe(this, postOKObserver);
    }

    @Override
    public void onClick(final View view) {
        new MaterialStyledDialog.Builder(getContext())
                .setTitle(R.string.finishOrderDialogTitle)
                .setDescription(R.string.finishOrderDialogContent)
                .setPositiveText(R.string.finishOrderDialogAffirmative)
                .setNegativeText(R.string.finishOrderDialogNegative)
                .setStyle(Style.HEADER_WITH_ICON)
                .setIcon(view.getId() == R.id.orderPaymentCard ? R.drawable.icon_creditcard512 : R.drawable.icon_cash512)
                .setHeaderColor(view.getId() == R.id.orderPaymentCard ? R.color.BlueCreditCard : R.color.GreenMoney)
                .setCancelable(true)
                .withIconAnimation(true)
                .withDialogAnimation(true, Duration.SLOW)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        linearLayout.setVisibility(View.VISIBLE);
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                        Pedido pedido = new Pedido(0, simpleDateFormat.format(Calendar.getInstance().getTime()), viewModel.getCartTotal().getValue(), new ArrayList<Bocata>(), new ArrayList<PanPedido>(), new ArrayList<ComplementoPedido>());

                        ArrayList<Object> objects = viewModel.getCesta().getValue();
                        PanPedido pan;
                        ComplementoPedido comp;
                        Bocata bocata;

                        for(Object object : objects)
                        {
                            if(object instanceof PanPedido)
                            {
                                pan = (PanPedido)object;
                                pedido.getPanes().add(new PanPedido(pan));
                            }
                            else if(object instanceof ComplementoPedido)
                            {
                                comp = (ComplementoPedido) object;
                                pedido.getComplementos().add(new ComplementoPedido(comp));
                            }
                            else
                            {
                                bocata = (Bocata)object;
                                pedido.getBocatas().add(bocata);
                            }
                        }

                        Cliente cliente = viewModel.getCliente();
                        viewModel.getGestoraRetrofitLoggedin().postPedido(pedido);

                    }
                })
                .show();
    }
}
