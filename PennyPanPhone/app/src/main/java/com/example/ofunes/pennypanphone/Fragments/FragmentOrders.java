package com.example.ofunes.pennypanphone.Fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ofunes.pennypanphone.Adapters.OrdersRVAdapter;
import com.example.ofunes.pennypanphone.Entidades.FragmentOption;
import com.example.ofunes.pennypanphone.R;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;


public class FragmentOrders extends Fragment implements View.OnClickListener {

    LoggedinViewModel viewModel;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView emptyRV, txtNoOrders;
    LinearLayout linearLayout;
    ImageView imgNoOrders;

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
        linearLayout = getActivity().findViewById(R.id.linearNoOrders);
        txtNoOrders = getActivity().findViewById(R.id.txtNoOrders); txtNoOrders.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartamedium));
        emptyRV = getActivity().findViewById(R.id.emptyRV); emptyRV.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartabolditalic));
        recyclerView = getActivity().findViewById(R.id.ordersRecyclerView);
        imgNoOrders = getActivity().findViewById(R.id.imgNoOrders); imgNoOrders.setOnClickListener(this);

        if(viewModel.getListadoPedidos().getValue() == null || viewModel.getListadoPedidos().getValue().isEmpty())
        {
            linearLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else
        {
            linearLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            //Iniciando RecyclerView
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new OrdersRVAdapter(viewModel.getListadoPedidos().getValue());

            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.imgNoOrders:
                viewModel.getFragmentOption().setValue(FragmentOption.MARKET);
                break;
        }
    }
}
