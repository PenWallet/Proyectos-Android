package com.example.ofunes.pennypanphone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Fragments.LoginFragment;
import com.google.gson.Gson;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cliente cliente = checkSharedPreferencesLogin();
        if(cliente == null)
        {
            setContentView(R.layout.activity_main);

            LoginFragment loginFragment = new LoginFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, loginFragment).commit();
        }
        else
        {
            Intent intent = new Intent(this, LoggedinActivity.class);
            intent.putExtra("cliente", cliente);
            startActivity(intent);
            finish();
        }

    }

    private Cliente checkSharedPreferencesLogin()
    {
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.sharedPreferencesName), MODE_PRIVATE);
        if(sharedPreferences.contains(getString(R.string.sharedPreferencesCliente)))
        {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(getString(R.string.sharedPreferencesCliente), "");
            Cliente cliente = gson.fromJson(json, Cliente.class);
            return cliente;
        }
        else
            return null;


    }
}
