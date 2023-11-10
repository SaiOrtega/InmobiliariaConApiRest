package com.example.inmobiliariasinapirest.ui.contratos;

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
import com.example.inmobiliariasinapirest.request.ApiClient;
import com.example.inmobiliariasinapirest.request.ApiClientRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Inmueble>> listaInmueblesMutable;
    private ArrayList<Contrato> contratos;


    public ContratosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Inmueble>> setInmuebles(){
        if(listaInmueblesMutable == null){
            listaInmueblesMutable = new MutableLiveData<>();
        }
        return listaInmueblesMutable;
    }


    public void setListaInmueblesMutable(){
        //listaInmueblesMutable.setValue(ApiClient.getApi().obtenerPropiedadesAlquiladas());
    String token = ApiClientRetrofit.leerToken(context);
    ApiClientRetrofit.ApiInmobiliaria end = ApiClientRetrofit.getApiInmobiliaria();

        Call<List<Contrato>> call = end.getVigentes(token);

        call.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                if(response.isSuccessful()){
                    contratos = (ArrayList<Contrato>) response.body();
                    //Log.d("prueba contrato", contratos.get(0).getInmueble().getDireccion());
                    ArrayList<Inmueble> inmuebles = new ArrayList<>();
                    for (Contrato cont : contratos){
                        inmuebles.add(cont.getInmueble());
                        Log.d("salida listaCont", cont.getInmueble().toString());
                    }
                    listaInmueblesMutable.postValue(inmuebles);

                }else {
                    Log.d("prueba obtenerInmuebles sin respuesta porque", response.code() + ";" + response.message() + ";" + response.headers());
                }
            }
            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "Algo salió mal", Toast.LENGTH_LONG).show();
                Log.d("malo", t.getMessage());
            }
        });

    }

    // TODO: Implement the ViewModel
}