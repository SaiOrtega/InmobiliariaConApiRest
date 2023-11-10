package com.example.inmobiliariasinapirest.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.inmobiliariasinapirest.modelo.Inmueble;
import com.example.inmobiliariasinapirest.request.ApiClient;
import com.example.inmobiliariasinapirest.request.ApiClientRetrofit;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Inmueble>> listaInmueblesMutable;
    private MutableLiveData<List<Inmueble>> listaInmueblesMutableFoto;



    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Inmueble>> setInmuebles(){
        if(listaInmueblesMutable == null){
            listaInmueblesMutable = new MutableLiveData<>();
        }
        return listaInmueblesMutable;
    }
    public LiveData<List<Inmueble>> setInmueblesFoto(){
        if(listaInmueblesMutableFoto== null){
            listaInmueblesMutableFoto = new MutableLiveData<>();
        }
        return listaInmueblesMutableFoto;
    }


    public void setListaInmueblesMutable(){

        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria end = ApiClientRetrofit.getApiInmobiliaria();
        Call<List<Inmueble>> listaInmueblesCall = end.recuperarInmuebles(token);

        listaInmueblesCall.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()){
                    if(response.body() != null){

                        listaInmueblesMutable.postValue(response.body());
                        Log.d("salidaSetImag", response.body().toString());
                    }
                }else{
                    Log.d("salida",response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Toast.makeText(context, "Error al cargar los inmuebles" , Toast.LENGTH_SHORT).show();
            }
        });

    }


}