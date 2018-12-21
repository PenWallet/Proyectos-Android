package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClienteCallback implements Callback<Cliente>{

	private MainViewModel mainVM;

	public ClienteCallback(MainViewModel mainVM)
	{
		this.mainVM = mainVM;
	}

	@Override
	public void onResponse(Call<Cliente> call, Response<Cliente> response) {
		Cliente cliente = response.body();
		mainVM.getCliente().postValue(cliente);
	}

	@Override
	public void onFailure(Call<Cliente> call, Throwable t) {
		//Adew
	}
}
