package com.example.inmobiliariasinapirest.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariasinapirest.R;
import com.example.inmobiliariasinapirest.modelo.Propietario;
import com.example.inmobiliariasinapirest.request.ApiClient;
import com.example.inmobiliariasinapirest.request.ApiClientRetrofit;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> editarMutable;
    private MutableLiveData<Boolean> guardarMutable;

    private MutableLiveData<Propietario> propietarioMutable;
    private Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }


    public LiveData<Propietario> getPropietario() {
        if(propietarioMutable == null){
            propietarioMutable = new MutableLiveData<>();
        }
        return propietarioMutable;
    }
    public LiveData<Boolean> getEditar() {
        if(editarMutable == null){
            editarMutable = new MutableLiveData<>();
        }
        return editarMutable;
    }
    public LiveData<Boolean> getGuardar() {
        if(guardarMutable == null){
            guardarMutable = new MutableLiveData<>();
        }
        return guardarMutable;
    }

    //aqui edito el perfil del propietario
    public void editarPropietario(Propietario propietario){
        String token = ApiClientRetrofit.leerToken(context);
        //ApiClient.getApi().actualizarPerfil(propietario);
        ApiClientRetrofit.ApiInmobiliaria end = ApiClientRetrofit.getApiInmobiliaria();
        Call<Propietario> propietarioCall = end.actualizar(token,propietario);
        propietarioCall.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        propietarioMutable.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });
       // propietarioMutable.postValue(propietario);

    }

    //
    public void setPropietarioMutable(){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria end = ApiClientRetrofit.getApiInmobiliaria();

        Call<Propietario> llamada = end.obtenerPropietario(token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null){
                    Log.d("salida",response.body().getApellido());

                    //propietarioMutable.postValue(response.body());
                    Propietario propietario = response.body();
                    if (propietario.getAvatar() == 0){
                        propietario.setAvatar(R.drawable.logo);
                    }
                    propietarioMutable.postValue(propietario);
                }else{
                    Log.d("salida resp", response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("salida falla", t.getMessage());
            }
        });
    }
    /*public void setPropietarioMutable(){
        Propietario propietario = ApiClient.getApi().obtenerUsuarioActual();

        propietarioMutable.setValue(propietario);
    }*/

    public void setEditarMutable(){
        editarMutable.setValue(true);
    }
    public void setGuardarMutable(){
        guardarMutable.setValue(true);
    }
}