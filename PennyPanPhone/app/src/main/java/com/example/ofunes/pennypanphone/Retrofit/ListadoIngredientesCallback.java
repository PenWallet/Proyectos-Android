package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Ingrediente;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListadoIngredientesCallback implements Callback<List<Ingrediente>>{

	LoggedinViewModel loggedinViewModel;

	public ListadoIngredientesCallback(LoggedinViewModel loggedinViewModel)
	{
		this.loggedinViewModel = loggedinViewModel;
	}

	@Override
	public void onResponse(Call<List<Ingrediente>> call, Response<List<Ingrediente>> response) {
		List<Ingrediente> listado = response.body();

		loggedinViewModel.getIngredientes().setValue(listado == null ? null : new ArrayList<>(listado));
	}

	@Override
	public void onFailure(Call<List<Ingrediente>> call, Throwable t) {
		loggedinViewModel.getIngredientes().setValue(null);
	}
}
