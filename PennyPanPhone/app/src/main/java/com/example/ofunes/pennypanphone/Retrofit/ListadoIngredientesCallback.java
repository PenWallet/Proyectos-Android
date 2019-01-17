package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Ingrediente;
import com.example.ofunes.pennypanphone.Entidades.IngredienteBocata;
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

		if(listado != null && listado.size() != 0)
		{
			ArrayList<IngredienteBocata> listadoIngredientes = new ArrayList<>();

			for(Ingrediente ingrediente : listado)
			{
				listadoIngredientes.add(new IngredienteBocata(ingrediente.getId(), ingrediente.getNombre(), ingrediente.getPrecio(), 0));
			}
			loggedinViewModel.getIngredientes().setValue(listadoIngredientes);
		}
		else
		{
			loggedinViewModel.getPanes().setValue(null);
		}
	}

	@Override
	public void onFailure(Call<List<Ingrediente>> call, Throwable t) {
		loggedinViewModel.getIngredientes().setValue(null);
	}
}
