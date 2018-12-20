package com.example.ofunes.pennypanphone.Retrofit;

import android.widget.Toast;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.LoginFragment;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClienteCallback implements Callback<Cliente>{

	@Override
	public void onResponse(Call<Cliente> call, Response<Cliente> response) {
		
	}

	@Override
	public void onFailure(Call<Cliente> call, Throwable t) {
		//Hacer algo
	}
}
