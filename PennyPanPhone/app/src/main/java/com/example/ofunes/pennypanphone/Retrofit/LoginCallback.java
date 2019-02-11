package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Utiliidades.JWTUtils;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginCallback implements Callback<Void>{

	MainViewModel mainVm;

	public LoginCallback(MainViewModel mainVm)
	{
		this.mainVm = mainVm;
	}

	@Override
	public void onResponse(Call<Void> call, Response<Void> response) {
		if(response.code() == 204)
		{
			Headers headers = response.headers();
			String token = headers.get("Authentication").split(" ")[1];
			Cliente cliente = JWTUtils.getClienteFromToken(token);
			cliente.setToken(token);
			mainVm.getCliente().postValue(cliente);
		}
		else if(response.code() == 401)
			mainVm.getCliente().postValue(null);

	}

	@Override
	public void onFailure(Call<Void> call, Throwable t) {
		mainVm.getSomethingWrongwWithLogin().setValue(true);
	}
}
