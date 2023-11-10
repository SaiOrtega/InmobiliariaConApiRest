package com.example.inmobiliariasinapirest.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariasinapirest.modelo.Contrato;
import com.example.inmobiliariasinapirest.modelo.Inmueble;
import com.example.inmobiliariasinapirest.request.ApiClient;
import com.example.inmobiliariasinapirest.request.ApiClientRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetalleContratosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Contrato> contratoMutable;
    private ArrayList<Contrato> contratos;

    public DetalleContratosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<Contrato> getContrato(){
        if(contratoMutable == null){
            contratoMutable = new MutableLiveData<>();
        }
        return contratoMutable;
    }

    public void setContrato(Inmueble inmueble){
       /* Contrato contrato = ApiClient.getApi().obtenerContratoVigente(inmueble);
        contratoMutable.setValue(contrato);*/
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria end = ApiClientRetrofit   .getApiInmobiliaria();
        Call<List<Contrato>> contratoCall = end.getVigentes(token);
        contratoCall.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                if (response.isSuccessful()){
                    if(response.body() != null) {
                        Contrato contrato = new Contrato();
                        for (Contrato cont : response.body()){
                            if (cont.getInmueble().getIdInmueble() ==inmueble.getIdInmueble()){
                                contrato = cont;
                            }
                        }
                        contratoMutable.postValue(contrato);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable t) {

            }
        });

    }
    // TODO: Implement the ViewModel
}