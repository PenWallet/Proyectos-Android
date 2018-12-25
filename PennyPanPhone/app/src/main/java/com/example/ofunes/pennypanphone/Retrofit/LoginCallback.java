package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.LoginFragment;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginCallback implements Callback<Cliente>{

	MainViewModel mainVm;

	public LoginCallback(MainViewModel mainVm)
	{
		this.mainVm = mainVm;
	}

	@Override
	public void onResponse(Call<Cliente> call, Response<Cliente> response) {
		Cliente cliente = response.body();
		mainVm.getCliente().postValue(cliente);
	}

	@Override
	public void onFailure(Call<Cliente> call, Throwable t) {
		mainVm.getSomethingWrongwWithLogin().setValue(true);
	}
}
