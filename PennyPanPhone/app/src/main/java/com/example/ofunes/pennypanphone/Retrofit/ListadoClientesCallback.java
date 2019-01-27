package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListadoClientesCallback implements Callback<List<Cliente>>{

	LoggedinViewModel loggedinViewModel;

	public ListadoClientesCallback(LoggedinViewModel loggedinViewModel)
	{
		this.loggedinViewModel = loggedinViewModel;
	}

	@Override
	public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
		List<Cliente> listado = response.body();

		loggedinViewModel.getListadoClientes().setValue(new ArrayList<>(listado));
	}

	@Override
	public void onFailure(Call<List<Cliente>> call, Throwable t) {
		loggedinViewModel.getListadoClientes().setValue(null);
	}
}
