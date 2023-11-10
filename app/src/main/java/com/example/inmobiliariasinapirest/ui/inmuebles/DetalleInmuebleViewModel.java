package com.example.inmobiliariasinapirest.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariasinapirest.modelo.EditarInmueble;
import com.example.inmobiliariasinapirest.modelo.Inmueble;
import com.example.inmobiliariasinapirest.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetalleInmuebleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Inmueble> inmuebleMutable;
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Inmueble> getInmueble(){
        if(inmuebleMutable == null){
            inmuebleMutable = new MutableLiveData<>();
        }
        return inmuebleMutable;
    }

    public void setInmueble(Inmueble inmueble){
        //Log.d("salida inmu", inmueble.toString());
        inmuebleMutable.setValue(inmueble);

    }

    //al no setear el id no puedo modificar el estado, el id del inmueble es cero
    public void modificarEstadoInmueble(Boolean disponible){
        Inmueble inmueble = inmuebleMutable.getValue();
        inmueble.setEstado(disponible);
        //ApiClient.getApi().actualizarInmueble(inmueble);
        String token = ApiClientRetrofit.leerToken(context);
        EditarInmueble editarInmueble = new EditarInmueble(inmueble.getIdInmueble(),disponible);
        ApiClientRetrofit.ApiInmobiliaria end = ApiClientRetrofit.getApiInmobiliaria();
        Call<Inmueble> inmuCall = end.CambiarEstado(token,editarInmueble.getId());
        inmuCall.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        Log.d("prueba body put", "entro :" + response.body().isEstado() );
                        inmuebleMutable.postValue(inmueble);

                    }
                }else{
                    Log.d("salida", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(context, "Error al editar", Toast.LENGTH_SHORT).show();
                Log.d("salida",t.getMessage());
            }
        });
    }
    // TODO: Implement the ViewModel
}