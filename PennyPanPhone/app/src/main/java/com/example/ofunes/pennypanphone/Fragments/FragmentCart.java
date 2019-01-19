package com.example.ofunes.pennypanphone.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ofunes.pennypanphone.Adapters.CartRVAdapter;
import com.example.ofunes.pennypanphone.R;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCart extends Fragment {

    LoggedinViewModel viewModel;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView txtEmptyCart, txtEmptyCart2, cartTotalTitle, cartTotal;
    LinearLayout linearLayout;
    NestedScrollView scrollView;

    public FragmentCart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(LoggedinViewModel.class);

        recyclerView = getActivity().findViewById(R.id.cartRecyclerView);
        txtEmptyCart = getActivity().findViewById(R.id.txtEmptyCart); txtEmptyCart.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartamedium));
        txtEmptyCart2 = getActivity().findViewById(R.id.txtEmptyCart2); txtEmptyCart2.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartamedium));
        linearLayout = getActivity().findViewById(R.id.linearEmptyCart);
        cartTotalTitle = getActivity().findViewById(R.id.cartTotalTitle); cartTotalTitle.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartamediumitalic));
        cartTotal = getActivity().findViewById(R.id.cartTotal); cartTotal.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartabold));
        scrollView = getActivity().findViewById(R.id.scrollViewCart);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new CartRVAdapter(viewModel);

        if(viewModel.getCesta().getValue() != null && !viewModel.getCesta().getValue().isEmpty())
        {
            linearLayout.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            linearLayout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }

        final Observer<Double> cartTotalObserver = new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double cartTotal) {

                updateCartTotal(cartTotal);

                if(cartTotal == 0)
                {
                    linearLayout.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                }

            }
        };

        viewModel.getCartTotal().observe(this, cartTotalObserver);

    }

    private void updateCartTotal(double cartTotalPrice)
    {
        String orderP = String.format(getResources().getString(R.string.orderPrice), cartTotalPrice);
        cartTotal.setText(orderP);
    }

}
