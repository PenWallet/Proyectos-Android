package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Pan;
import com.example.ofunes.pennypanphone.Entidades.PanPedido;
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
		if(listado != null && listado.size() != 0)
		{
			ArrayList<PanPedido> listadoPanes = new ArrayList<>();

			for(Pan pan : listado)
			{
				listadoPanes.add(new PanPedido(pan.getId(), pan.getNombre(), pan.getCrujenticidad(), pan.isIntegral(), pan.getPrecio(),0));
			}
			loggedinViewModel.getPanes().setValue(listadoPanes);
		}
		else
		{
			loggedinViewModel.getPanes().setValue(null);
		}

	}

	@Override
	public void onFailure(Call<List<Pan>> call, Throwable t) {
		loggedinViewModel.getPanes().setValue(null);
	}
}
