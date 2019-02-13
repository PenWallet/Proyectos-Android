package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Utiliidades.JWTUtils;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PatchCallback implements Callback<Void>
{
	MainViewModel mainVm;

	public PatchCallback(MainViewModel mainVm)
	{
		this.mainVm = mainVm;
	}

	@Override
	public void onResponse(Call<Void> arg0, Response<Void> response) {
		Headers headers = response.headers();
		String token = headers.get("Authentication").split(" ")[1];
		Cliente cliente = JWTUtils.getClienteFromToken(token);
		cliente.setToken(token);
		mainVm.getClienteRegistrado().setValue(cliente);
		//TODO onResponse PatchCallback
	}

	@Override
	public void onFailure(Call<Void> arg0, Throwable arg1) {
		//TODO onFailure PatchCallback
	}
}
