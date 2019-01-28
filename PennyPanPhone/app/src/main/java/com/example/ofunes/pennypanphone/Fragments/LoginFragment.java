package com.example.ofunes.pennypanphone.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.LoggedinActivity;
import com.example.ofunes.pennypanphone.R;
import com.example.ofunes.pennypanphone.Utils;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;
import com.google.gson.Gson;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private MainViewModel mViewModel;
    TextView txtRegistration, txtErrorLogin;
    ActionProcessButton btnLogin;
    EditText editUsername, editPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        ((TextView)getView().findViewById(R.id.textView)).setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartabolditalic));
        txtRegistration = getView().findViewById(R.id.txtCreateAccount); txtRegistration.setOnClickListener(this); txtRegistration.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartamedium));
        btnLogin = getView().findViewById(R.id.btnLogIn); btnLogin.setOnClickListener(this); btnLogin.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartamedium)); btnLogin.setMode(ActionProcessButton.Mode.ENDLESS);
        txtErrorLogin = getView().findViewById(R.id.txtErrorLogin); txtErrorLogin.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartamedium));
        editPassword = getView().findViewById(R.id.editLoginPassword); editPassword.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartamedium));
        editUsername = getView().findViewById(R.id.editLoginUsername); editUsername.setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartamedium));
        ((TextView)getView().findViewById(R.id.cbRemember)).setTypeface(ResourcesCompat.getFont(getContext(), R.font.prinsesstartamedium));

        final Observer<Cliente> clienteObserver = new Observer<Cliente>() {
            @Override
            public void onChanged(@Nullable Cliente cliente) {
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                btnLogin.setTextColor(getResources().getColor(R.color.White));
                if(cliente == null)
                {
                    btnLogin.setProgress(0);
                    txtErrorLogin.setText(R.string.errorLogIn);
                }
                else
                {
                    btnLogin.setProgress(100);
                    cliente.setContrasena(editPassword.getText().toString());
                    Intent intent = new Intent(getActivity(), LoggedinActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("cliente", cliente);
                    Utils.saveClienteSharedPreferences(cliente, getContext());
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        };

        final Observer<Boolean> somethingWrongObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean somethingWrong) {
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                btnLogin.setTextColor(getResources().getColor(R.color.White));
                if(somethingWrong)
                {
                    Toast.makeText(getActivity(), R.string.unexpectedLoginError, Toast.LENGTH_SHORT).show();
                    btnLogin.setProgress(-1);
                }
                else
                    btnLogin.setProgress(0);
            }
        };

        mViewModel.getCliente().observe(this, clienteObserver);
        mViewModel.getSomethingWrongwWithLogin().observe(this, somethingWrongObserver);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.txtCreateAccount:
                RegisterFragment rF = new RegisterFragment();
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, rF).addToBackStack(null).commit();
                break;

            case R.id.btnLogIn:
                if(checkFields())
                {
                    btnLogin.setProgress(1);
                    btnLogin.setText(getResources().getString(R.string.loading));
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    btnLogin.setTextColor(getResources().getColor(R.color.Cyan));
                    mViewModel.getGestoraRetrofit().obtenerUsuario(editUsername.getText().toString(), editPassword.getText().toString());
                }
                break;
        }
    }

    private boolean checkFields()
    {
        boolean isOk, isUsernameOk = true, isPasswordOk = true;

        //Validación del nombre de usuario
        if(editUsername.getText().toString().equals(""))
        {
            isUsernameOk = false;
            //Toast.makeText(getContext(), R.string.errorEmptyUsername, Toast.LENGTH_SHORT).show();
        }
        else if(!editUsername.getText().toString().matches("\\w{1,15}"))
        {
            isUsernameOk = false;
            Toast.makeText(getContext(), R.string.errorInvalidUsername, Toast.LENGTH_SHORT).show();
        }

        //Validación de la contraseña
        if(editPassword.getText().toString().equals(""))
        {
            isPasswordOk = false;
            //Toast.makeText(getContext(), R.string.errorEmptyPassword, Toast.LENGTH_SHORT).show();
        }
        else if(editPassword.getText().toString().length() < 8 && !editPassword.getText().toString().equals("1234"))
        {
            isPasswordOk = false;
            Toast.makeText(getContext(), R.string.errorInvalidPassword, Toast.LENGTH_SHORT).show();
        }

        //TODO Borrar antes de release
        if(editUsername.getText().toString().equals("") && editPassword.getText().toString().equals(""))
        {
            isUsernameOk = true;
            isPasswordOk = true;
            editPassword.setText("1234");
            editUsername.setText("oscar1");
        }

        isOk = isUsernameOk && isPasswordOk;

        return isOk;
    }
}
