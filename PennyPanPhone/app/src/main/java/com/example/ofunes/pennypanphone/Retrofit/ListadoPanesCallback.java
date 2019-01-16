package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Pan;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListadoPanesCallback implements Callback<List<Pan>>{

	LoggedinViewModel loggedinViewModel;

	public ListadoPanesCallback(LoggedinViewModel loggedinViewModel)
	{
		this.loggedinViewModel = loggedinViewModel;
	}

	@Override
	public void onResponse(Call<List<Pan>> call, Response<List<Pan>> response) {
		List<Pan> listado = response.body();

		loggedinViewModel.getPanes().setValue(listado == null ? null : new ArrayList<>(listado));
	}

	@Override
	public void onFailure(Call<List<Pan>> call, Throwable t) {
		loggedinViewModel.getPanes().setValue(null);
	}
}
