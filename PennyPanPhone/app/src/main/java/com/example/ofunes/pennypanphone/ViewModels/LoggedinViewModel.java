package com.example.ofunes.pennypanphone.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ofunes.pennypanphone.Entidades.Cliente;
import com.example.ofunes.pennypanphone.Entidades.ComplementoPedido;
import com.example.ofunes.pennypanphone.Entidades.IngredienteBocata;
import com.example.ofunes.pennypanphone.Entidades.FragmentOption;
import com.example.ofunes.pennypanphone.Entidades.PanPedido;
import com.example.ofunes.pennypanphone.Entidades.Pedido;
import com.example.ofunes.pennypanphone.Retrofit.GestoraRetrofitLoggedin;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LoggedinViewModel extends ViewModel {
    private Cliente cliente;
    private GestoraRetrofitLoggedin gestoraRetrofitLoggedin;
    private MutableLiveData<ArrayList<PanPedido>> panes;
    private MutableLiveData<ArrayList<ComplementoPedido>> complementos;
    private MutableLiveData<ArrayList<IngredienteBocata>> ingredientes;
    private MutableLiveData<ArrayList<Pedido>> listadoPedidos;
    private MutableLiveData<ArrayList<Object>> cesta;
    private MutableLiveData<ArrayList<Cliente>> listadoClientes;
    private MutableLiveData<Boolean> hasOrders;
    private MutableLiveData<FragmentOption> fragmentOption;
    private MutableLiveData<Double> cartTotal;
    private MutableLiveData<Boolean> postOK;
    private MutableLiveData<Boolean> haveOrdersLoaded;
    private MutableLiveData<Boolean> patchOK;
    private int sandwichInProgress; //Representa la posición en la cesta del bocata que está en progreso
    private final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.GERMAN);

    public LoggedinViewModel()
    {
        cliente = null;
        listadoPedidos = new MutableLiveData<>(); listadoPedidos.setValue(new ArrayList<Pedido>());
        hasOrders = new MutableLiveData<>();
        panes = new MutableLiveData<>();
        complementos = new MutableLiveData<>();
        ingredientes = new MutableLiveData<>();
        listadoClientes = new MutableLiveData<>();
        postOK = new MutableLiveData<>();
        cesta = new MutableLiveData<>(); cesta.setValue(new ArrayList<>());
        fragmentOption = new MutableLiveData<>();
        cartTotal = new MutableLiveData<>(); cartTotal.setValue(0d);
        haveOrdersLoaded = new MutableLiveData<>();
        patchOK = new MutableLiveData<>();
        gestoraRetrofitLoggedin = new GestoraRetrofitLoggedin(this);
        decimalFormatSymbols.setDecimalSeparator('.');
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public MutableLiveData<ArrayList<Pedido>> getListadoPedidos() {
        return listadoPedidos;
    }

    public MutableLiveData<Boolean> getHasOrders() {
        return hasOrders;
    }

    public MutableLiveData<ArrayList<PanPedido>> getPanes() {
        return panes;
    }

    public MutableLiveData<ArrayList<ComplementoPedido>> getComplementos() {
        return complementos;
    }

    public MutableLiveData<ArrayList<IngredienteBocata>> getIngredientes() {
        return ingredientes;
    }

    public MutableLiveData<ArrayList<Object>> getCesta() {
        return cesta;
    }

    public MutableLiveData<FragmentOption> getFragmentOption() {
        return fragmentOption;
    }

    public MutableLiveData<Double> getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(MutableLiveData<Double> cartTotal) {
        this.cartTotal = cartTotal;
    }

    public MutableLiveData<Boolean> getPostOK() {
        return postOK;
    }

    public void setPostOK(MutableLiveData<Boolean> postOK) {
        this.postOK = postOK;
    }

    public MutableLiveData<Boolean> getHaveOrdersLoaded() {
        return haveOrdersLoaded;
    }

    public int getSandwichInProgress() {
        return sandwichInProgress;
    }

    public void setSandwichInProgress(int sandwichInProgress) {
        this.sandwichInProgress = sandwichInProgress;
    }

    public MutableLiveData<ArrayList<Cliente>> getListadoClientes() {
        return listadoClientes;
    }

    public MutableLiveData<Boolean> getPatchOK() {
        return patchOK;
    }

    public GestoraRetrofitLoggedin getGestoraRetrofitLoggedin() {
        return gestoraRetrofitLoggedin;
    }

    /* Funciones */
    public void addValueCartTotal(double value)
    {
        DecimalFormat df = new DecimalFormat("#.00", decimalFormatSymbols);
        double actualValue = cartTotal.getValue();
        double finalValue = actualValue + value;
        cartTotal.setValue(Double.valueOf(df.format(finalValue)));
    }
}
