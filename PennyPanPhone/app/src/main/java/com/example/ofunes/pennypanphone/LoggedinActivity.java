package com.example.ofunes.pennypanphone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.widget.Toast;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.Complemento;
import com.example.ofunes.pennypanphone.Entidades.ComplementoPedido;
import com.example.ofunes.pennypanphone.Entidades.Ingrediente;
import com.example.ofunes.pennypanphone.Entidades.IngredienteBocata;
import com.example.ofunes.pennypanphone.Entidades.MarketType;
import com.example.ofunes.pennypanphone.Entidades.Pan;
import com.example.ofunes.pennypanphone.Entidades.PanPedido;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.Retrofit.GestoraRetrofitLoggedin;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LoggedinActivity extends FragmentActivity implements OnNavigationItemSelectedListener {

    LoggedinViewModel viewModel;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    FragmentAdmin fragmentAdmin;
    FragmentCart fragmentCart;
    FragmentHome fragmentHome;
    FragmentOrders fragmentOrders;
    FragmentMarket fragmentMarket;
    FragmentMarketBread fragmentMarketBread;
    FragmentMarketMiscellaneous fragmentMarketMiscellaneous;
    FragmentMarketSandwichIngredients fragmentMarketSandwichIngredients;
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
        fragmentMarketSandwichIngredients = new FragmentMarketSandwichIngredients();

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

        //Si es panadero inicializamos el fragment de admin, si no, lo quitamos del menú
        if(viewModel.getCliente().isPanadero())
            fragmentAdmin = new FragmentAdmin();
        else
            bottomNavigationView.getMenu().removeItem(R.id.navAdmin);

        //Observer para saber cuándo han cargado los pedidos
        final Observer<ArrayList<Pedido>> ordersObserver = new Observer<ArrayList<Pedido>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Pedido> listadoPedidos) {

                if(listadoPedidos == null)
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

        final Observer<MarketType> marketOptionObserver = new Observer<MarketType>() {
            @Override
            public void onChanged(@Nullable MarketType marketType) {
                switch(marketType)
                {
                    case BREAD:
                        getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentMarketBread).addToBackStack(null).commit();
                        break;

                    case MISCELLANEOUS:
                        getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentMarketMiscellaneous).addToBackStack(null).commit();
                        break;

                    case SANDWICH:
                        getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentMarketSandwichIngredients).addToBackStack(null).commit();
                        break;
                }
            }
        };

        //Desclarando los observers
        viewModel.getListadoPedidos().observe(this, ordersObserver);
        viewModel.getPanes().observe(this, panesObserver);
        viewModel.getComplementos().observe(this, complementosObserver);
        viewModel.getIngredientes().observe(this, ingredientesObserver);
        viewModel.getMarketOption().observe(this, marketOptionObserver);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        fragmentHome = new FragmentHome();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navHome);

        gestoraRetrofitLoggedin.obtenerListadoPedidos(viewModel.getCliente().getUsername(), viewModel.getCliente().getContrasena());
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        getSupportFragmentManager().popBackStack();

        switch(item.getItemId())
        {
            case R.id.navAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentAdmin).commit();
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
}
