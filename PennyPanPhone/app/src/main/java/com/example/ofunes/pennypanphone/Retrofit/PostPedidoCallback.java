package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostPedidoCallback implements Callback<Pedido>{

	LoggedinViewModel loggedinViewModel;

	public PostPedidoCallback(LoggedinViewModel loggedinViewModel)
	{
		this.loggedinViewModel = loggedinViewModel;
	}

	@Override
	public void onResponse(Call<Pedido> call, Response<Pedido> response) {
		Pedido pedido = response.body();

		loggedinViewModel.getListadoPedidos().getValue().add(pedido);
		loggedinViewModel.getPostOK().setValue(true);
	}

	@Override
	public void onFailure(Call<Pedido> call, Throwable t) {
		loggedinViewModel.getPostOK().setValue(false);
	}
}
