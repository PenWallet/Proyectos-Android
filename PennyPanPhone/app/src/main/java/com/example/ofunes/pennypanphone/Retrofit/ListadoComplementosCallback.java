package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Complemento;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListadoComplementosCallback implements Callback<List<Complemento>>{

	LoggedinViewModel loggedinViewModel;

	public ListadoComplementosCallback(LoggedinViewModel loggedinViewModel)
	{
		this.loggedinViewModel = loggedinViewModel;
	}

	@Override
	public void onResponse(Call<List<Complemento>> call, Response<List<Complemento>> response) {
		List<Complemento> listado = response.body();

		loggedinViewModel.getComplementos().setValue(listado == null ? null : new ArrayList<>(listado));
	}

	@Override
	public void onFailure(Call<List<Complemento>> call, Throwable t) {
		loggedinViewModel.getComplementos().setValue(null);
	}
}
