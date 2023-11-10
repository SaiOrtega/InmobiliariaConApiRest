package com.example.inmobiliariasinapirest;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.inmobiliariasinapirest.modelo.Propietario;
import com.example.inmobiliariasinapirest.request.ApiClient;
import com.example.inmobiliariasinapirest.request.ApiClientRetrofit;
import com.example.inmobiliariasinapirest.ui.perfil.PerfilViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Propietario> propietarioMutable;
    public MenuActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<Propietario> getPropietario(){
        if(propietarioMutable == null){
            propietarioMutable = new MutableLiveData<>();
        }
        return propietarioMutable;
    }
    public void setPropietarioMutable(){
       // propietarioMutable.setValue(ApiClient.getApi().obtenerUsuarioActual());
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria end = ApiClientRetrofit.getApiInmobiliaria();
        Call<Propietario> llamada = end.obtenerPropietario(token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null){
                    Log.d("salida",response.body().getApellido());
                    Propietario propietario = response.body();
                    if (propietario.getAvatar() == 0){
                        propietario.setAvatar(R.drawable.logo);
                    }
                    propietarioMutable.setValue(propietario);
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
    public void observarPerfilenViewModel(final PerfilViewModel perfilViewModel){
        perfilViewModel.getPropietario().observeForever(new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                propietarioMutable.postValue(propietario);
                perfilViewModel.getPropietario().removeObserver(this);
            }
        });
    }

}
