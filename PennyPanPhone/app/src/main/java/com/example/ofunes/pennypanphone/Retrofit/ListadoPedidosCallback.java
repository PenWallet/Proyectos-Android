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
		if(listado == null)
			loggedinViewModel.getListadoPedidos().setValue(new ArrayList<Pedido>());
		else
			loggedinViewModel.getListadoPedidos().setValue(new ArrayList<>(listado));

		loggedinViewModel.getHaveOrdersLoaded().setValue(true);
	}

	@Override
	public void onFailure(Call<List<Pedido>> call, Throwable t) {
		loggedinViewModel.getListadoPedidos().setValue(new ArrayList<Pedido>());
	}
}
