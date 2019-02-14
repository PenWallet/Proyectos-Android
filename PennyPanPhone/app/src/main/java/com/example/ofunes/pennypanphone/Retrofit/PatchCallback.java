package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.FragmentOption;
import com.example.ofunes.pennypanphone.Entidades.Pan;
import com.example.ofunes.pennypanphone.Entidades.PanPedido;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.Utiliidades.JWTUtils;
import com.example.ofunes.pennypanphone.ViewModels.LoggedinViewModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PatchCallback implements Callback<Void>{

	LoggedinViewModel loggedinViewModel;

	public PatchCallback(LoggedinViewModel loggedinViewModel)
	{
		this.loggedinViewModel = loggedinViewModel;
	}

	@Override
	public void onResponse(Call<Void> call, Response<Void> response) {

		if(response.code() == 204)
		{
			loggedinViewModel.getPatchOK().setValue(true);

			Headers headers = response.headers();
			String token = headers.get("Authentication").split(" ")[1];

			loggedinViewModel.getCliente().setToken(token);
		}
		else if(response.code() == 401)
			loggedinViewModel.getFragmentOption().setValue(FragmentOption.UNAUTHORIZED);

	}

	@Override
	public void onFailure(Call<Void> call, Throwable t) {
		loggedinViewModel.getPatchOK().setValue(false);
	}
}
