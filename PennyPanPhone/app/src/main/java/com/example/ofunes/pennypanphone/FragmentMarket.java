package com.example.ofunes.pennypanphone;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMarket extends Fragment implements View.OnClickListener {

    TextView txtMOTD;
    CardView bread, misc, sand;

    public FragmentMarket() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_market, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtMOTD = getActivity().findViewById(R.id.txtMarketMOTD); txtMOTD.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartabolditalic));
        bread = getActivity().findViewById(R.id.marketCardViewBread); bread.setOnClickListener(this);
        misc = getActivity().findViewById(R.id.marketCardViewMiscellaneous);
        sand = getActivity().findViewById(R.id.marketCardViewSandwiches);
        loadRandomMOTD();
    }

    private void loadRandomMOTD()
    {
        Random random = new Random();
        String[] MOTDs = getResources().getStringArray(R.array.motd);
        int numberOfMOTD = getResources().getInteger(R.integer.numberOfMOTD);
        int randomNumber = random.nextInt(numberOfMOTD - 1) + 1;
        txtMOTD.setText(MOTDs[randomNumber]);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        Utils.animateEnter(bread, getContext());
    }
}