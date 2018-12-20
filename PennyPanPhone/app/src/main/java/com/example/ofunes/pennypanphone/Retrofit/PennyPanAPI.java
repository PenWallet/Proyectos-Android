package com.example.ofunes.pennypanphone.Retrofit;

/**
 * Created by ofunes on 20/12/18.
 */

import com.example.ofunes.pennypanphone.Entidades.Cliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PennyPanAPI {

    @GET("/cliente/{username}")
    Call<Cliente> getCliente(@Header("Authorization") String token, @Path("username") String username);

    @GET("/cliente")
    Call<List<Cliente>> getListCliente(@Header("Authorization") String token, @Path("username") String username);

    @POST("/cliente")
    Call<Void> postCliente(@Body Cliente cliente);

}