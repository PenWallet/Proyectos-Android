package com.example.ofunes.pennypanphone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.RenderProcessGoneDetail;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Retrofit.GestoraRetrofit;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    public LoginFragment()
    {

    }

    private MainViewModel mViewModel;
    TextView txtRegistration, txtErrorLogin;
    Button btnLogin;
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

        txtRegistration = getView().findViewById(R.id.txtCreateAccount); txtRegistration.setOnClickListener(this);
        btnLogin = getView().findViewById(R.id.btnLogIn); btnLogin.setOnClickListener(this);
        txtErrorLogin = getView().findViewById(R.id.txtErrorLogin);
        editPassword = getView().findViewById(R.id.editLoginPassword);
        editUsername = getView().findViewById(R.id.editLoginUsername);

        final Observer<Cliente> clienteObserver = new Observer<Cliente>() {
            @Override
            public void onChanged(@Nullable Cliente cliente) {
                if(cliente == null)
                {
                    txtErrorLogin.setVisibility(View.VISIBLE);
                    txtErrorLogin.setText(R.string.errorLogIn);
                }
                else
                {
                    txtErrorLogin.setVisibility(View.VISIBLE);
                    txtErrorLogin.setText(cliente.toString());
                }
            }
        };

        final Observer<Boolean> registerSuccessObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean success) {
                if(success == null)
                    Toast.makeText(getActivity(), R.string.nani, Toast.LENGTH_SHORT).show();
                else if(success)
                {
                    Toast.makeText(getActivity(), R.string.registerSuccessful, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), mViewModel.getClienteRegistrado().getValue().toString(), Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getActivity(), R.string.registerUnsuccessful, Toast.LENGTH_SHORT).show();

            }
        };

        mViewModel.getCliente().observe(this, clienteObserver);
        mViewModel.getIsRegistrationSuccessful().observe(this, registerSuccessObserver);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.txtCreateAccount:
                RegisterFragment rF = new RegisterFragment();
                getFragmentManager().beginTransaction().replace(R.id.mainFragment, rF).addToBackStack(null).commit();
                break;

            case R.id.btnLogIn:
                if(checkFields())
                    mViewModel.getGestoraRetrofit().obtenerUsuario(editUsername.getText().toString(), editPassword.getText().toString());
                break;
        }
    }

    private boolean checkFields()
    {
        boolean isOk = true, isUsernameOk = true, isPasswordOk = true;

        /*Validación del nombre de usuario
        if(editUsername.getText().toString().equals(""))
        {
            isUsernameOk = false;
            Toast.makeText(getContext(), R.string.errorEmptyUsername, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), R.string.errorEmptyPassword, Toast.LENGTH_SHORT).show();
        }
        else if(editPassword.getText().toString().length() < 8)
        {
            isPasswordOk = false;
            Toast.makeText(getContext(), R.string.errorInvalidPassword, Toast.LENGTH_SHORT).show();
        }

        isOk = true;*/

        return isOk;
    }
}
