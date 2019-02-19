package com.example.ofunes.pennypanphone.Fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.ofunes.pennypanphone.Entidades.FragmentOption;
import com.example.ofunes.pennypanphone.R;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMarket extends Fragment implements View.OnClickListener {

    TextView txtMOTD;
    CardView bread, misc, sand;
    LoggedinViewModel viewModel;

    public FragmentMarket() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        return inflater.inflate(R.layout.fragment_market, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(LoggedinViewModel.class);
        txtMOTD = getActivity().findViewById(R.id.txtMarketMOTD); txtMOTD.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartabolditalic));
        bread = getActivity().findViewById(R.id.marketCardViewBread); bread.setOnClickListener(this);
        misc = getActivity().findViewById(R.id.marketCardViewMiscellaneous); misc.setOnClickListener(this);
        sand = getActivity().findViewById(R.id.marketCardViewSandwiches); sand.setOnClickListener(this);
        loadRandomMOTD();
    }

    private void loadRandomMOTD()
    {
        Random random = new Random();
        String[] motd = getResources().getStringArray(R.array.motd);
        int randomNumber = random.nextInt(motd.length - 1);
        txtMOTD.setText(motd[randomNumber]);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch(id)
        {
            case R.id.marketCardViewBread:
                bread.setCardElevation(4);
                misc.setCardElevation(1);
                sand.setCardElevation(1);
                animateEnter(bread);
                break;

            case R.id.marketCardViewMiscellaneous:
                bread.setCardElevation(1);
                misc.setCardElevation(4);
                sand.setCardElevation(1);
                animateEnter(misc);
                break;

            case R.id.marketCardViewSandwiches:
                bread.setCardElevation(1);
                misc.setCardElevation(1);
                sand.setCardElevation(4);
                animateEnter(sand);
                break;
        }


    }

    private void animateEnter(final View v)
    {
        final Animation anim = AnimationUtils.loadAnimation(v.getContext(), R.anim.market_enter_zoomfade);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int id = v.getId();

                switch(id)
                {
                    case R.id.marketCardViewBread:
                        viewModel.getFragmentOption().setValue(FragmentOption.BREAD);
                        break;

                    case R.id.marketCardViewMiscellaneous:
                        viewModel.getFragmentOption().setValue(FragmentOption.MISCELLANEOUS);
                        break;

                    case R.id.marketCardViewSandwiches:
                        viewModel.getFragmentOption().setValue(FragmentOption.SANDWICHBREAD);
                        break;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        v.startAnimation(anim);
    }
}