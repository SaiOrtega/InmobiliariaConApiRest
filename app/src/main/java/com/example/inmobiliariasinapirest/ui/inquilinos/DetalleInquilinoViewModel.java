package com.example.inmobiliariasinapirest.ui.inquilinos;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariasinapirest.modelo.Contrato;
import com.example.inmobiliariasinapirest.modelo.Inmueble;
import com.example.inmobiliariasinapirest.modelo.Inquilino;
import com.example.inmobiliariasinapirest.request.ApiClient;
import com.example.inmobiliariasinapirest.request.ApiClientRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetalleInquilinoViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Inquilino> inquilinoMutable;
    private ArrayList<Contrato> contratos;
    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Inquilino> getInquilino(){
        if(inquilinoMutable == null){
            inquilinoMutable = new MutableLiveData<>();
        }
        return inquilinoMutable;
    }

    public void setInquilinoMutable(Inmueble inmueble){
        //inquilinoMutable.setValue(ApiClient.getApi().obtenerInquilino(inmueble));
    String token = ApiClientRetrofit.leerToken(context);
    ApiClientRetrofit.ApiInmobiliaria end = ApiClientRetrofit.getApiInmobiliaria();
        Call<List<Contrato>> call = end.getVigentes(token);
        call.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                if(response.isSuccessful()){
                    contratos = (ArrayList<Contrato>) response.body();
                    Log.d("prueba contrato", contratos.get(0).getInmueble().getDireccion());

                    for (Contrato cont : contratos){
                        inquilinoMutable.postValue(cont.getInquilino());

                        Log.d("salida Inquilino", cont.getInquilino().toString());
                    }


                }else {
                    Log.d("prueba obtenerInmuebles sin respuesta porque", response.code() + ";" + response.message() + ";" + response.headers());
                }
            }
            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "Algo salió mal", Toast.LENGTH_LONG).show();
                Log.d("Lo malo", t.getMessage());
            }
        });



    }
    // TODO: Implement the ViewModel
}