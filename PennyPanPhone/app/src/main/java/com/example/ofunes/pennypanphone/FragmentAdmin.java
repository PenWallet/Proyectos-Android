package com.example.ofunes.pennypanphone;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdmin extends Fragment implements View.OnClickListener {

    LoggedinViewModel viewModel;
    ImageView imageView;
    Button button;

    public FragmentAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(LoggedinViewModel.class);
        imageView = getActivity().findViewById(R.id.imgPrueba);
        button = getActivity().findViewById(R.id.btnPrueba); button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new CycleInterpolator(2));
        animation.setDuration(200);

        imageView.startAnimation(animation);
    }
}
