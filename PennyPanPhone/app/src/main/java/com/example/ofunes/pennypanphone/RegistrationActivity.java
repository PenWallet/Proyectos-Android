package com.example.ofunes.pennypanphone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity {

    EditText editName, editUsername, editPassword;
    TextView txtErrorName, txtErrorUsername, txtErrorPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editName = findViewById(R.id.txtRegisterName);
        editUsername = findViewById(R.id.txtRegisterUsername);
        editPassword = findViewById(R.id.txtRegisterPassword);

        txtErrorName = findViewById(R.id.txtErrorName);
        txtErrorUsername = findViewById(R.id.txtErrorUsername);
        txtErrorPassword = findViewById(R.id.txtErrorRegisterPassword);
    }

    public void registerNewUser(View view) {

        if(checkFields())
        {
            Toast.makeText(this, "Ji ome", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkFields()
    {
        boolean isOk, isNameOk = true, isUsernameOk = true, isPasswordOk = true;

        //Validación del nombre
        if(editName.getText().toString().equals(""))
        {
            isNameOk = false;
            txtErrorName.setText(R.string.errorEmptyName);
        }
        else if(!editName.getText().toString().matches("[a-zA-Z ]*"))
        {
            isNameOk = false;
            txtErrorName.setText(R.string.errorInvalidName);
        }
        else
            txtErrorName.setText("");

        //Validación del nombre de usuario
        if(editUsername.getText().toString().equals(""))
        {
            isUsernameOk = false;
            txtErrorUsername.setText(R.string.errorEmptyUsername);
        }
        else if(!editUsername.getText().toString().matches("\\w{1,15}"))
        {
            isUsernameOk = false;
            txtErrorUsername.setText(R.string.errorInvalidUsername);
        }
        else if(false)
        {
            isUsernameOk = false;
            txtErrorUsername.setText(R.string.errorTakenUsername);
        }
        else
            txtErrorUsername.setText("");

        //Validación de la contraseña
        if(editPassword.getText().toString().equals(""))
        {
            isPasswordOk = false;
            txtErrorPassword.setText(R.string.errorEmptyPassword);
        }
        else if(editPassword.getText().toString().length() < 8)
        {
            isPasswordOk = false;
            txtErrorPassword.setText(R.string.errorInvalidPassword);
        }
        else
            txtErrorPassword.setText("");


        if(isNameOk && isUsernameOk && isPasswordOk)
            isOk = true;
        else
            isOk = false;

        return isOk;
    }
}
