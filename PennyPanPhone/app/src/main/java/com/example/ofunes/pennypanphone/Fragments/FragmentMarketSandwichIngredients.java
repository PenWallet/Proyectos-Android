package com.example.ofunes.pennypanphone.Fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dd.processbutton.FlatButton;
import com.example.ofunes.pennypanphone.Adapters.MarketSandwichIngredientsRVAdapter;
import com.example.ofunes.pennypanphone.Entidades.Bocata;
import com.example.ofunes.pennypanphone.Entidades.IngredienteBocata;
import com.example.ofunes.pennypanphone.Entidades.FragmentOption;
import com.example.ofunes.pennypanphone.R;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMarketSandwichIngredients extends Fragment implements View.OnClickListener {

    LoggedinViewModel viewModel;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    FlatButton btnFinish;

    public FragmentMarketSandwichIngredients() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_market_sandwich_ingredients, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(LoggedinViewModel.class);

        btnFinish = getActivity().findViewById(R.id.btnFinishSandwich); btnFinish.setOnClickListener(this);
        ((AppCompatTextView)getActivity().findViewById(R.id.sandwichIngredientsTitle)).setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartabolditalic));

        recyclerView = getActivity().findViewById(R.id.marketSandwichIngredientsRecyclerView);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new MarketSandwichIngredientsRVAdapter(viewModel);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnFinishSandwich)
        {
            Bocata bocata = (Bocata)viewModel.getCesta().getValue().get(viewModel.getSandwichInProgress());
            if(bocata.getIngredientes().isEmpty() && bocata.getPan() == null)
                Toast.makeText(getContext(), getResources().getString(R.string.sandwichErrorNothing), Toast.LENGTH_SHORT).show();
            else if(bocata.getIngredientes().isEmpty())
                Toast.makeText(getContext(), getResources().getString(R.string.sandwichErrorNoIngredients), Toast.LENGTH_SHORT).show();
            else if(bocata.getPan() == null)
                Toast.makeText(getContext(), getResources().getString(R.string.sandwichErrorNoBread), Toast.LENGTH_SHORT).show();
            else
                askFinalQuestion(bocata);
        }
    }

    private void askFinalQuestion(Bocata bocata)
    {
        StringBuilder string = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.00");

        for(IngredienteBocata ingrediente : bocata.getIngredientes())
        {
            string.append(ingrediente.getCantidad()+"x "+ingrediente.getNombre()+"   EUR "+df.format(ingrediente.getCantidad()*ingrediente.getPrecio())+"\n");
        }

        new MaterialStyledDialog.Builder(getContext())
                .setTitle(R.string.sandwichFinalQuestionTitle)
                .setDescription(string)
                .setPositiveText(R.string.sandwichFinalQuestionAffirmative)
                .setNegativeText(R.string.sandwichFinalQuestionNegative)
                .setStyle(Style.HEADER_WITH_ICON)
                .setIcon(bocata.getPan().isIntegral() ? R.drawable.icon_wholebread128 : R.drawable.icon_bread128)
                .setHeaderColor(R.color.GreenBread)
                .setCancelable(true)
                .withIconAnimation(true)
                .withDialogAnimation(true, Duration.FAST)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        getActivity().getSupportFragmentManager().popBackStack();
                        for(IngredienteBocata ingrediente : viewModel.getIngredientes().getValue())
                            ingrediente.setCantidad(0);

                        //Añadir el precio del bocata al total
                        viewModel.addValueCartTotal(((Bocata)viewModel.getCesta().getValue().get(viewModel.getSandwichInProgress())).getPan().getPrecio());
                        for(IngredienteBocata ingrediente : ((Bocata)viewModel.getCesta().getValue().get(viewModel.getSandwichInProgress())).getIngredientes())
                            viewModel.addValueCartTotal(ingrediente.getPrecio() * ingrediente.getCantidad());

                        viewModel.setSandwichInProgress(-1);
                        viewModel.getFragmentOption().setValue(FragmentOption.FINISHSANDWICH);
                    }
                })
                .show();
    }


}
