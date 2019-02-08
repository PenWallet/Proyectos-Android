package com.example.ofunes.pennypanphone.Retrofit;

import com.auth0.android.jwt.JWT;
import com.example.ofunes.pennypanphone.ViewModels.MainViewModel;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationCallback implements Callback<Void>
{
	MainViewModel mainVm;

	public RegistrationCallback(MainViewModel mainVm)
	{
		this.mainVm = mainVm;
	}

	@Override
	public void onResponse(Call<Void> arg0, Response<Void> resp) {
		Headers headers = resp.headers();
		String token = headers.get("Authentication").split(" ")[1];
		JWT jwt = new JWT(token);
		jwt.
	}

	@Override
	public void onFailure(Call<Void> arg0, Throwable arg1) {
		mainVm.getSomethingWrongwWithRegister().setValue(true);
	}
}
