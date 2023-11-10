package com.example.inmobiliariasinapirest.ui.contratos;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

;

import com.example.inmobiliariasinapirest.modelo.Contrato;
import com.example.inmobiliariasinapirest.modelo.Pago;
import com.example.inmobiliariasinapirest.request.ApiClientRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Pago>> listaPagosMutable;
    public PagosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Pago>> getListaPagos(){
        if(listaPagosMutable == null){
            listaPagosMutable = new MutableLiveData<>();
        }
        return listaPagosMutable;
    }

    public void setListaPagos(Contrato contrato){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria end = ApiClientRetrofit.getApiInmobiliaria();
            int id = contrato.getId();
        Call<List<Pago>> callPagos = end.getPagos(token, id);
        List<Pago> listaPagos = new ArrayList<>();

        callPagos.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()){
                    Log.d("salida pagos", response.body().size()+"");
                    for (Pago pago: response.body()){
                        Pago p = new Pago(pago.getId(), pago.getNumero(), pago.getMonto(),pago.getFechaPago(),pago.getContratoId());
                        Log.d("salida pago", pago.getNumero()+ " " + p.getNumero());
                       Log.d("salida tiene p",p.getMonto()+"");
                        listaPagos.add(p);
                    }
                    listaPagosMutable.postValue(listaPagos);
                }else {
                    Log.d("salida pagos por else", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {

            }
        });

        /* ArrayList<Pago> listaPagos = ApiClient.getApi().obtenerPagos(contrato);
        listaPagosMutable.setValue(listaPagos);*/
    }

    // TODO: Implement the ViewModel
}