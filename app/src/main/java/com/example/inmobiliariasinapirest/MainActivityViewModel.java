package com.example.inmobiliariasinapirest;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariasinapirest.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Boolean> loginMutable;
    private MutableLiveData<Sensor> llamadaMutable;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<Boolean> getLogin(){
        if(loginMutable == null){
            loginMutable = new MutableLiveData<>();
        }
        return loginMutable;
    }
    public LiveData<Sensor> getLlamada(){
        if(llamadaMutable == null){
            llamadaMutable = new MutableLiveData<>();
        }
        return llamadaMutable;
    }

    public void login(String Email, String clave){

        ApiClientRetrofit.ApiInmobiliaria end =ApiClientRetrofit.getApiInmobiliaria();

                Call<String> call = end.login(Email, clave);

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Log.d("salida",response.body());
                    ApiClientRetrofit.guardarToken(getApplication(),"Bearer "+response.body());
                    loginMutable.setValue(true);
                   /* Intent intent = new Intent(context, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);*/
                }else{
                    Log.d("salida",response.message());
                    Toast.makeText(context, "Error en el inicio de sesion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });
    }

    public void setLlamadaListener(){
        llamadaMutable.setValue(new Sensor(context));
    }
}
