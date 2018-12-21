package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClienteVoidCallback implements Callback<Void>{

	@Override
	public void onResponse(Call<Void> arg0, Response<Void> resp) {
		//Hacer algo con
	}

	@Override
	public void onFailure(Call<Void> arg0, Throwable arg1) {

	}
}
