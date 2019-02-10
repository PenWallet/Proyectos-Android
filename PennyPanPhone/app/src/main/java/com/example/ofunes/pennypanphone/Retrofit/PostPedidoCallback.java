package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.FragmentOption;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import okhttp3.Headers;
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
		if(response.code() == 200)
		{
			Pedido pedido = response.body();

			loggedinViewModel.getListadoPedidos().getValue().add(pedido);
			if(!loggedinViewModel.getHasOrders().getValue())
				loggedinViewModel.getHasOrders().setValue(true);
			loggedinViewModel.getPostOK().setValue(true);

			Headers headers = response.headers();
			String token = headers.get("Authentication").split(" ")[1];

			loggedinViewModel.getCliente().setToken(token);
		}
		else if(response.code() == 401)
		{
			loggedinViewModel.getFragmentOption().setValue(FragmentOption.UNAUTHORIZED);
		}
	}

	@Override
	public void onFailure(Call<Pedido> call, Throwable t) {
		loggedinViewModel.getPostOK().setValue(false);
	}
}
