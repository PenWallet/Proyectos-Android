package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListadoPedidosCallback implements Callback<List<Pedido>>{

	LoggedinViewModel loggedinViewModel;

	public ListadoPedidosCallback(LoggedinViewModel loggedinViewModel)
	{
		this.loggedinViewModel = loggedinViewModel;
	}

	@Override
	public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
		List<Pedido> listado = response.body();

		loggedinViewModel.getListadoPedidos().setValue(listado == null ? null : new ArrayList<>(listado));
	}

	@Override
	public void onFailure(Call<List<Pedido>> call, Throwable t) {
		int i = 0;

		i = 4;
		i = 8;
	}
}
