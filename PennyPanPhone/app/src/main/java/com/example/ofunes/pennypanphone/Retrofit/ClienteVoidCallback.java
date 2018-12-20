package com.example.ofunes.pennypanphone.Retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClienteVoidCallback implements Callback<Void>{

	@Override
	public void onFailure(Call<Void> arg0, Throwable arg1) {
		System.out.println("(   )\n" +
				"  (   ) (\n" +
				"   ) _   )\n" +
				"    ( \\_\n" +
				"  _(_\\ \\)__\n" +
				" (____\\___))");
	}

	@Override
	public void onResponse(Call<Void> arg0, Response<Void> resp) {
		//Hacer algo con
	}
	

}
