package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClienteCallback implements Callback<Cliente>{

	@Override
	public void onResponse(Call<Cliente> call, Response<Cliente> response) {
		Cliente cliente;
		String contentType;
		int code;
		String message;
		boolean isSuccesful;

		cliente = response.body();

		Headers cabeceras = response.headers();
		contentType = cabeceras.get("Content-Type");
		code = response.code();
		message = response.message();
		isSuccesful = response.isSuccessful();

		//Hacer algo con el usuario
	}

	@Override
	public void onFailure(Call<Cliente> call, Throwable t) {
		//Hacer algo
	}
}
