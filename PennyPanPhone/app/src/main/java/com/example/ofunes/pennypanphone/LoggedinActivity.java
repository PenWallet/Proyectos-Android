package com.example.ofunes.pennypanphone;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.widget.Toast;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

public class LoggedinActivity extends FragmentActivity implements OnNavigationItemSelectedListener {

    LoggedinViewModel viewModel;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    FragmentAdmin fragmentAdmin;
    FragmentCart fragmentCart;
    FragmentHome fragmentHome;
    FragmentOrders fragmentOrders;
    FragmentInfo fragmentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);
        viewModel = ViewModelProviders.of(this).get(LoggedinViewModel.class);
        fragmentCart = new FragmentCart();
        fragmentHome = new FragmentHome();
        fragmentOrders = new FragmentOrders();
        fragmentInfo = new FragmentInfo();

        viewModel.setCliente((Cliente)getIntent().getExtras().getParcelable("cliente"));

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.loggedFrame);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navHome);

        if(viewModel.getCliente().isPanadero())
        {
            fragmentAdmin = new FragmentAdmin();
        }
        else
        {
            bottomNavigationView.getMenu().removeItem(R.id.navAdmin);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.navAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentAdmin).commit();
                break;

            case R.id.navInfo:
                getSupportFragmentManager().beginTransaction().replace(R.id.loggedFrame, fragmentInfo).commit();
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
