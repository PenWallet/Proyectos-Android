package com.example.ofunes.pennypanphone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Retrofit.GestoraRetrofit;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private MainViewModel mViewModel;

    EditText editName, editUsername, editPassword;
    TextView txtErrorName, txtErrorUsername, txtErrorPassword;
    Button btnRegister;
    ProgressBar progressBar;

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

        progressBar = getView().findViewById(R.id.progressBarRegister);

        final Observer<Cliente> registerObserver = new Observer<Cliente>() {
            @Override
            public void onChanged(@Nullable Cliente cliente) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                btnRegister.setTextColor(getResources().getColor(R.color.White));
                if(cliente == null)
                {
                    txtErrorUsername.setText(R.string.errorTakenUsername);
                }
                else
                {
                    txtErrorUsername.setText("");
                    Intent intent = new Intent(getActivity(), LoggedinActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    cliente.setContrasena(editPassword.getText().toString());
                    intent.putExtra("cliente", cliente);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        };

        final Observer<Boolean> somethingWrongObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean somethingWrong) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                btnRegister.setTextColor(getResources().getColor(R.color.White));
                if(somethingWrong)
                {
                    Toast.makeText(getActivity(), R.string.registerUnsuccessful, Toast.LENGTH_SHORT).show();
                }
            }
        };

        mViewModel.getClienteRegistrado().observe(this, registerObserver);
        mViewModel.getSomethingWrongwWithRegister().observe(this, somethingWrongObserver);

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


        isOk = isNameOk && isUsernameOk && isPasswordOk;

        return isOk;
    }

    @Override
    public void onClick(View view) {
        if(checkFields())
        {
            progressBar.setVisibility(View.VISIBLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            btnRegister.setTextColor(getResources().getColor(R.color.GreenishCyan));
            Cliente cliente = new Cliente(editUsername.getText().toString(), editPassword.getText().toString(), editName.getText().toString(), 0);
            mViewModel.getGestoraRetrofit().registrarUsuario(cliente);
        }
    }
}
