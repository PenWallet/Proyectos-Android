package com.example.ofunes.pennypanphone;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private MainViewModel mViewModel;

    EditText editName, editUsername, editPassword;
    TextView txtErrorName, txtErrorUsername, txtErrorPassword;
    Button btnRegister;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        editName = getView().findViewById(R.id.txtRegisterName);
        editUsername = getView().findViewById(R.id.txtRegisterUsername);
        editPassword = getView().findViewById(R.id.txtRegisterPassword);

        txtErrorName = getView().findViewById(R.id.txtErrorName);
        txtErrorUsername = getView().findViewById(R.id.txtErrorUsername);
        txtErrorPassword = getView().findViewById(R.id.txtErrorRegisterPassword);

        btnRegister = getView().findViewById(R.id.btnRegister); btnRegister.setOnClickListener(this);

        //Sacar el teclado para que pueda escribir en el primer campo
        if(editName.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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

    @Override
    public void onClick(View view) {
        if(checkFields()) {
            Toast.makeText(getContext(), "Ji ome", Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
        }
    }
}
