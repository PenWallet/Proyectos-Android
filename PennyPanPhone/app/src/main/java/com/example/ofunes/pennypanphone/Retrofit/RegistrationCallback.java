package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationCallback implements Callback<Cliente>
{
	MainViewModel mainVm;

	public RegistrationCallback(MainViewModel mainVm)
	{
		this.mainVm = mainVm;
	}

	@Override
	public void onResponse(Call<Cliente> arg0, Response<Cliente> resp) {
		Cliente cliente = resp.body();
		mainVm.getClienteRegistrado().postValue(cliente);
	}

	@Override
	public void onFailure(Call<Cliente> arg0, Throwable arg1) {
		mainVm.getSomethingWrongwWithRegister().setValue(true);
	}
}
