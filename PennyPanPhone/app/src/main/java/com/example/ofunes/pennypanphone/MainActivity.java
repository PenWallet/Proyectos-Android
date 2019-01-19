package com.example.ofunes.pennypanphone;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.ofunes.pennypanphone.Fragments.LoginFragment;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, loginFragment).commit();
    }
}
