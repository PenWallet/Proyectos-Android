package com.example.ofunes.pennypanphone.Retrofit;

/**
 * Created by ofunes on 20/12/18.
 */

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.Complemento;
import com.example.ofunes.pennypanphone.Entidades.Ingrediente;
import com.example.ofunes.pennypanphone.Entidades.Pan;
import com.example.ofunes.pennypanphone.Entidades.Pedido;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PennyPanAPI {

    @GET("/cliente/{username}")
    @Headers({"Accept: application/json"})
    Call<Cliente> getCliente(@Header("Authorization") String token, @Path("username") String username);

    @GET("/cliente")
    @Headers({"Accept: application/json"})
    Call<List<Cliente>> getListCliente(@Header("Authorization") String token);

    @POST("/cliente")
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    Call<Void> postCliente(@Body Cliente cliente);

    @GET("/cliente/{username}/pedido")
    @Headers({"Accept: application/json"})
    Call<List<Pedido>> getListadoPedidos(@Header("Authorization") String token, @Path("username") String username);

    @GET("/pan")
    @Headers({"Accept: application/json"})
    Call<List<Pan>> getListadoPanes();

    @GET("/complemento")
    @Headers({"Accept: application/json"})
    Call<List<Complemento>> getListadoComplementos();

    @GET("/ingrediente")
    @Headers({"Accept: application/json"})
    Call<List<Ingrediente>> getListadoIngredientes();

    @POST("/cliente/{username}/pedido?XDEBUG_SESSION_START=PHPSTORM")
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    Call<Pedido> postPedido(@Header("Authorization") String token, @Path("username") String username, @Body Pedido pedido);
}