package com.example.ofunes.pennypanphone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.ComplementoPedido;
import com.example.ofunes.pennypanphone.Entidades.IngredienteBocata;
import com.example.ofunes.pennypanphone.Entidades.FragmentOption;
import com.example.ofunes.pennypanphone.Entidades.PanPedido;
import com.example.ofunes.pennypanphone.Fragments.FragmentCart;
import com.example.ofunes.pennypanphone.Fragments.FragmentCartPaymentMethod;
import com.example.ofunes.pennypanphone.Fragments.FragmentHome;
import com.example.ofunes.pennypanphone.Fragments.FragmentMarket;
import com.example.ofunes.pennypanphone.Fragments.FragmentMarketBread;
import com.example.ofunes.pennypanphone.Fragments.FragmentMarketMiscellaneous;
import com.example.ofunes.pennypanphone.Fragments.FragmentMarketSandwichBread;
import com.example.ofunes.pennypanphone.Fragments.FragmentMarketSandwichIngredients;
import com.example.ofunes.pennypanphone.Fragments.FragmentOrders;
import com.example.ofunes.pennypanphone.Retrofit.GestoraRetrofitLoggedin;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import java.util.ArrayList;

public class LoggedinActivity extends FragmentActivity implements OnNavigationItemSelectedListener {

    LoggedinViewModel viewModel;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    FragmentSettings fragmentSettings;
    FragmentCart fragmentCart;
    FragmentHome fragmentHome;
    FragmentOrders fragmentOrders;
    FragmentMarket fragmentMarket;
    FragmentMarketBread fragmentMarketBread;
    FragmentMarketMiscellaneous fragmentMarketMiscellaneous;
    FragmentMarketSandwichBread fragmentMarketSandwichBread;
    FragmentMarketSandwichIngredients fragmentMarketSandwichIngredients;
    FragmentCartPaymentMethod fragmentCartPaymentMethod;
    GestoraRetrofitLoggedin gestoraRetrofitLoggedin;
    LinearLayout progressBar;
    TextView txtLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);

        //Crear el viewmodel y los fragments
        viewModel = ViewModelProviders.of(this).get(LoggedinViewModel.class);
        fragmentCart = new FragmentCart();
        fragmentOrders = new FragmentOrders();
        fragmentMarket = new FragmentMarket();
        fragmentMarketBread = new FragmentMarketBread();
        fragmentMarketMiscellaneous = new FragmentMarketMiscellaneous();
        fragmentMarketSandwichBread = new FragmentMarketSandwichBread();
        fragmentMarketSandwichIngredients = new FragmentMarketSandwichIngredients();
        fragmentCartPaymentMethod = new FragmentCartPaymentMethod();
        fragmentSettings = new FragmentSettings();

        //Coger los datos del cliente
        viewModel.setCliente((Cliente)getIntent().getExtras().getParcelable("cliente"));

        //Crear la gestora de retrofit para la actividad LoggedIn
        gestoraRetrofitLoggedin = new GestoraRetrofitLoggedin(viewModel);

        //Inicializar el menú de navegación inferior
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //El frameLayout para los fragments
        frameLayout = findViewById(R.id.loggedFrame);

        //Preparando el loading
        progressBar = findViewById(R.id.progressBarLoggedin);
        txtLoading = findViewById(R.id.txtLoading); txtLoading.setTypeface(ResourcesCompat.getFont(this, R.font.prinsesstartabolditalic));

        //Observer para saber cuándo han cargado los pedidos
        final Observer<Boolean> ordersObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean haveOrdersLoaded) {

                if(viewModel.getListadoPedidos().getValue() == null || viewModel.getListadoPedidos().getValue().isEmpty())
                    viewModel.getHasOrders().setValue(false);
                else
                    viewModel.getHasOrders().setValue(true);

                txtLoading.setText(R.string.loadingBread);
                gestoraRetrofitLoggedin.obtenerListadoPanes();
            }
        };

        //Observer para saber cuándo han cargado los panes
        final Observer<ArrayList<PanPedido>> panesObserver = new Observer<ArrayList<PanPedido>>() {
            @Override
            public void onChanged(@Nullable ArrayList<PanPedido> listadoPedidos) {
                txtLoading.setText(R.string.loadingMiscellaneous);
                gestoraRetrofitLoggedin.obtenerListadoComplementos();
            }
        };

        //Observer para saber cuándo han cargado los complementos
        final Observer<ArrayList<ComplementoPedido>> complementosObserver = new Observer<ArrayList<ComplementoPedido>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ComplementoPedido> listadoPedidos) {
                txtLoading.setText(R.string.loadingIngredients);
                gestoraRetrofitLoggedin.obtenerListadoIngredientes();
            }
        };

        //Observer para saber cuándo han cargado los ingredientes
        final Observer<ArrayList<IngredienteBocata>> ingredientesObserver = new Observer<ArrayList<IngredienteBocata>>() {
            @Override
            public void onChanged(@Nullable ArrayList<IngredienteBocata> listadoPedidos) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        };

        final Observer<FragmentOption> marketOptionObserver = new Observer<FragmentOption>() {
            @Override
            public void onChanged(@Nullable FragmentOption marketType) {
                switch(marketType)
                {
                    case BREAD:
                        getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentMarketBread).addToBackStack(null).commit();
                        break;

                    case MISCELLANEOUS:
                        getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentMarketMiscellaneous).addToBackStack(null).commit();
                        break;

                    case SANDWICHBREAD:
                        getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentMarketSandwichBread).addToBackStack("sandwichBread").commit();
                        break;

                    case SANDWICHINGREDIENTS:
                        getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentMarketSandwichIngredients).addToBackStack("sandwichIngredients").commit();
                        break;

                    case FINISHSANDWICH:
                        bottomNavigationView.setSelectedItemId(R.id.navCart);
                        break;

                    case PAYMENTMETHOD:
                        getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentCartPaymentMethod).addToBackStack("paymentMethod").commit();
                        break;

                    case ORDERS:
                        bottomNavigationView.setSelectedItemId(R.id.navOrders);
                        break;

                }
            }
        };

        //Desclarando los observers
        viewModel.getHaveOrdersLoaded().observe(this, ordersObserver);
        viewModel.getPanes().observe(this, panesObserver);
        viewModel.getComplementos().observe(this, complementosObserver);
        viewModel.getIngredientes().observe(this, ingredientesObserver);
        viewModel.getFragmentOption().observe(this, marketOptionObserver);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        fragmentHome = new FragmentHome();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navHome);

        gestoraRetrofitLoggedin.obtenerListadoPedidos(viewModel.getCliente().getUsername(), viewModel.getCliente().getContrasena());
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onNavigationItemSelected(final @NonNull MenuItem item) {

        Object object = getSupportFragmentManager().findFragmentById(R.id.loggedFrame);
        if(object instanceof FragmentMarketSandwichIngredients && viewModel.getSandwichInProgress() != -1)
        {
            new MaterialStyledDialog.Builder(this)
                    .setTitle(R.string.dialogExitSandwichCreationTitle)
                    .setDescription(R.string.dialogExitSandwichCreationContent)
                    .setPositiveText(R.string.dialogExitSandwichCreationAffirmative)
                    .setNegativeText(R.string.dialogExitSandwichCreationNegative)
                    .setStyle(Style.HEADER_WITH_ICON)
                    .setIcon(R.drawable.icon_warning128)
                    .setHeaderColor(R.color.ErrorRed)
                    .setCancelable(true)
                    .withIconAnimation(true)
                    .withDialogAnimation(true, Duration.FAST)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            getSupportFragmentManager().popBackStack("sandwichBread", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                            resetOnProgressSandwich();

                            switch(item.getItemId())
                            {
                                case R.id.navSettings:
                                    bottomNavigationView.getMenu().findItem(R.id.navSettings).setChecked(true);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentSettings).commit();
                                    break;

                                case R.id.navMarket:
                                    bottomNavigationView.getMenu().findItem(R.id.navMarket).setChecked(true);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentMarket).commit();
                                    break;

                                case R.id.navCart:
                                    bottomNavigationView.getMenu().findItem(R.id.navCart).setChecked(true);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentCart).commit();
                                    break;

                                case R.id.navHome:
                                    bottomNavigationView.getMenu().findItem(R.id.navHome).setChecked(true);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentHome).commit();
                                    break;

                                case R.id.navOrders:
                                    bottomNavigationView.getMenu().findItem(R.id.navOrders).setChecked(true);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentOrders).commit();
                                    break;
                            }
                        }
                    })
                    .show();

            return false;
        }
        else
        {
            getSupportFragmentManager().popBackStack();

            switch(item.getItemId())
            {
                case R.id.navSettings:
                    getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentSettings).commit();
                    break;

                case R.id.navMarket:
                    getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentMarket).commit();
                    break;

                case R.id.navCart:
                    getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentCart).commit();
                    break;

                case R.id.navHome:
                    getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentHome).commit();
                    break;

                case R.id.navOrders:
                    getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentOrders).commit();
                    break;
            }

            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, BackgroundSoundService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(this, BackgroundSoundService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent music = new Intent(this, BackgroundSoundService.class);
        startService(music);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if(count == 0)
            super.onBackPressed();
        else
        {
            Object object = getSupportFragmentManager().findFragmentById(R.id.loggedFrame);
            if(object instanceof FragmentMarketSandwichIngredients)
            {
                resetOnProgressSandwich();
            }

            super.onBackPressed();
        }
    }

    private void resetOnProgressSandwich()
    {
        //Resetear los ingredientes a 0 cantidad
        for(IngredienteBocata ingr : viewModel.getIngredientes().getValue())
        {
            ingr.setCantidad(0);
        }

        viewModel.getCesta().getValue().remove(viewModel.getSandwichInProgress());

        viewModel.setSandwichInProgress(-1);
    }
}
