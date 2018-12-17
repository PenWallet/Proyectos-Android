package com.example.ofunes.pennypanphone;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView txtRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRegistration = findViewById(R.id.txtCreateAccount);
        txtRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.txtCreateAccount:
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                break;
        }
    }
}
