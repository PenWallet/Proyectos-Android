package com.example.ofunes.pennypanphone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.widget.Toast;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.Retrofit.GestoraRetrofitLoggedin;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

import java.util.ArrayList;

public class LoggedinActivity extends FragmentActivity implements OnNavigationItemSelectedListener {

    LoggedinViewModel viewModel;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    FragmentAdmin fragmentAdmin;
    FragmentCart fragmentCart;
    FragmentHome fragmentHome;
    FragmentOrders fragmentOrders;
    FragmentMarket fragmentMarket;
    GestoraRetrofitLoggedin gestoraRetrofitLoggedin;
    LinearLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);
        viewModel = ViewModelProviders.of(this).get(LoggedinViewModel.class);
        fragmentCart = new FragmentCart();
        fragmentOrders = new FragmentOrders();
        fragmentMarket = new FragmentMarket();

        viewModel.setCliente((Cliente)getIntent().getExtras().getParcelable("cliente"));

        gestoraRetrofitLoggedin = new GestoraRetrofitLoggedin(viewModel);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.loggedFrame);
        progressBar = findViewById(R.id.progressBarLoggedin);

        if(viewModel.getCliente().isPanadero())
        {
            fragmentAdmin = new FragmentAdmin();
        }
        else
        {
            bottomNavigationView.getMenu().removeItem(R.id.navAdmin);
        }

        final Observer<ArrayList<Pedido>> ordersObserver = new Observer<ArrayList<Pedido>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Pedido> listadoPedidos) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                if(listadoPedidos == null)
                    viewModel.getHasOrders().setValue(false);
                else
                    viewModel.getHasOrders().setValue(true);
            }
        };

        viewModel.getListadoPedidos().observe(this, ordersObserver);
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
}
