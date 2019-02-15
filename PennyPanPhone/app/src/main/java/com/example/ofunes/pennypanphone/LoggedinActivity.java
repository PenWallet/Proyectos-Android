package com.example.ofunes.pennypanphone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.widget.Toast;

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
import com.example.ofunes.pennypanphone.Fragments.FragmentSettings;
import com.example.ofunes.pennypanphone.Retrofit.GestoraRetrofitLoggedin;
import com.example.ofunes.pennypanphone.Services.BackgroundSoundService;
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
    ConstraintLayout constraintLoadingData;
    TextView txtLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);

        //Crear el viewmodel y los fragments
        viewModel = ViewModelProviders.of(this).get(LoggedinViewModel.class);
        fragmentHome = new FragmentHome();
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
        constraintLoadingData = findViewById(R.id.constraintLoadingData);
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
                if(viewModel.getCliente().isPanadero())
                {
                    gestoraRetrofitLoggedin.obtenerListadoClientes();
                    txtLoading.setText(R.string.loadingClients);
                }
                else
                    stopLoading();
            }
        };

        final Observer<ArrayList<Cliente>> clientesObserver = new Observer<ArrayList<Cliente>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Cliente> listadoClientes) {
                stopLoading();
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

                    case SIGNOFF:
                        removeSharedPreferencesSavedLogin();
                        Intent intent = new Intent(LoggedinActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case UNAUTHORIZED:
                        Toast.makeText(LoggedinActivity.this, getString(R.string.unauthorized), Toast.LENGTH_LONG).show();
                        removeSharedPreferencesSavedLogin();
                        Intent intentUnauth = new Intent(LoggedinActivity.this, MainActivity.class);
                        startActivity(intentUnauth);
                        finish();
                        break;

                    case MARKET:
                        bottomNavigationView.setSelectedItemId(R.id.navMarket);
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
        viewModel.getListadoClientes().observe(this, clientesObserver);

        //Seleccionar Home como la pantalla principal
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navHome);

        //Empezar a cargar datos
        startLoading();

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
        if(getIsMusicOnSharedPreferences())
        {
            Intent music = new Intent(this, BackgroundSoundService.class);
            startService(music);
        }
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

    private void removeSharedPreferencesSavedLogin()
    {
        SharedPreferences sp = getSharedPreferences(getString(R.string.sharedPreferencesName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(getString(R.string.sharedPreferencesCliente));
        editor.apply();
    }

    private boolean getIsMusicOnSharedPreferences()
    {
        return getSharedPreferences(getString(R.string.sharedPreferencesName), Context.MODE_PRIVATE).getBoolean(getString(R.string.sharedPreferencesMusic), true);
    }

    private void stopLoading()
    {
        getWindow().setStatusBarColor(getResources().getColor(R.color.LightCyan));
        bottomNavigationView.setVisibility(View.VISIBLE);
        constraintLoadingData.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void startLoading()
    {
        getWindow().setStatusBarColor(getResources().getColor(R.color.SplashBackground));
        bottomNavigationView.setVisibility(View.GONE);
        constraintLoadingData.setVisibility(View.VISIBLE);
        gestoraRetrofitLoggedin.obtenerListadoPedidos();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

}
