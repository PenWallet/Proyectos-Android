package com.example.ofunes.pennypanphone;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOrders extends Fragment {

    LoggedinViewModel viewModel;
    TextView txt;

    public FragmentOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(LoggedinViewModel.class);

        txt = getActivity().findViewById(R.id.txtOrders);

        Cliente cliente = viewModel.getCliente();

        txt.setText("Esta es la pestaña de orders. "+cliente.toString());
    }
}
