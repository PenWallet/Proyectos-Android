package com.example.ofunes.pennypanphone;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {

    LoggedinViewModel viewModel;
    TextView txtWelcome, txtLastOrderNumber, txtLastOrderDate, txtLastOrderPrice;
    LinearLayout linearHomeNoOrder, linearHomeLastOrder;
    ImageView lastOrderImage;


    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(LoggedinViewModel.class);
        linearHomeNoOrder = getActivity().findViewById(R.id.linearHomeNoOrder);
        linearHomeLastOrder = getActivity().findViewById(R.id.linearHomeLastOrder);
        txtLastOrderNumber = getActivity().findViewById(R.id.txtLastOrderNumber);
        txtLastOrderDate = getActivity().findViewById(R.id.txtLastOrderDate);
        txtLastOrderPrice = getActivity().findViewById(R.id.txtLastOrderPrice);
        lastOrderImage = getActivity().findViewById(R.id.lastOrderImage);

        final Observer<Boolean> ordersObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean listadoPedidos) {

                if(listadoPedidos)
                {
                    linearHomeNoOrder.setVisibility(View.GONE);
                    linearHomeLastOrder.setVisibility(View.VISIBLE);

                    Pedido pedido = viewModel.getListadoPedidos().getValue().get(viewModel.getListadoPedidos().getValue().size()-1);
                    String orderN = String.format(getResources().getString(R.string.orderNumber), pedido.getId());
                    String orderP = String.format(getResources().getString(R.string.orderPrice), pedido.getImporteTotal());

                    if(pedido.getPanes().length >= pedido.getComplementos().length && pedido.getPanes().length >= pedido.getBocatas().length)
                        lastOrderImage.setImageResource(R.drawable.icon_bread);
                    else if(pedido.getComplementos().length >= pedido.getPanes().length && pedido.getComplementos().length >= pedido.getBocatas().length)
                        lastOrderImage.setImageResource(R.drawable.icon_miscellaneous);
                    else
                        lastOrderImage.setImageResource(R.drawable.icon_sandwich);
                    txtLastOrderDate.setText(pedido.getFechaCompra());
                    txtLastOrderNumber.setText(orderN);
                    txtLastOrderPrice.setText(orderP);
                }
                else
                {
                    linearHomeNoOrder.setVisibility(View.VISIBLE);
                    linearHomeLastOrder.setVisibility(View.GONE);
                }

            }
        };

        txtWelcome = getActivity().findViewById(R.id.txtHome);

        Cliente cliente = viewModel.getCliente();

        String welcome = String.format(getActivity().getResources().getString(R.string.welcome), cliente.getNombre());
        txtWelcome.setText(welcome);

        viewModel.getHasOrders().observe(this, ordersObserver);
    }
}
