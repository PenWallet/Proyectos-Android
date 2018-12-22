package com.example.ofunes.pennypanphone.Retrofit;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResponseBodyCallback implements Callback<ResponseBody>{

	@Override
	public void onResponse(Call<ResponseBody> arg0, Response<ResponseBody> resp) {
		int code = resp.code();
		String respuesta;
		try {
			respuesta = resp.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFailure(Call<ResponseBody> arg0, Throwable arg1) {
		int i = 0;

		i = 2;
	}
}
