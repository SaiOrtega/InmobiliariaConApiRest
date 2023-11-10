package com.example.inmobiliariasinapirest.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.inmobiliariasinapirest.modelo.Contrato;
import com.example.inmobiliariasinapirest.modelo.Inmueble;
import com.example.inmobiliariasinapirest.modelo.InmuebleEnviar;
import com.example.inmobiliariasinapirest.modelo.Pago;
import com.example.inmobiliariasinapirest.modelo.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClientRetrofit {
    public static final String URLBASE="http://192.168.0.17:5000/";
    private static ApiInmobiliaria apiInmobiliaria;

    public static ApiInmobiliaria getApiInmobiliaria(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInmobiliaria = retrofit.create(ApiInmobiliaria.class);
        return apiInmobiliaria;
    }

    public interface ApiInmobiliaria{
        @FormUrlEncoded
        @POST("Propietario/login")
        Call<String> login(@Field("Email")String email,@Field("Clave") String clave);

        @GET("Propietario/")
        Call<Propietario> obtenerPropietario(@Header("Authorization") String token);

        @PUT("Propietario/actualizar")
        Call<Propietario> actualizar(@Header("Authorization") String token,  @Body Propietario propietario);

        @GET("Propietario/fotoPerfil/{nombreImagen}")
        Call<ResponseBody> recuperarImagen(@Header("Authorization") String token, @Path("nombreImagen") String nombreImagen);

        @GET("Inmueble")
        Call<List<Inmueble>> recuperarInmuebles(@Header("Authorization") String token);

        @GET("Inmueble/fotoInmueble/{nombreImagen}")
        Call<ResponseBody> recuperarImagenInmueble(@Header("Authorization") String token, @Path("nombreImagen") String nombreImagen);


        @PUT ("Inmueble/CambiarEstado/{id}")
        Call<Inmueble> CambiarEstado(@Header("Authorization") String token,  @Path("id") int id);


        @GET("Contrato/vigentes")
        Call<List<Contrato>> getVigentes(@Header("Authorization") String token);

        @GET("Pago/{Id}")
        Call<List<Pago>> getPagos(@Header("Authorization") String token, @Path("Id") int ContratoId);

        @Multipart
        @POST("Inmueble")
        Call<InmuebleEnviar> crearInmueble(
                @Header("Authorization") String token,
                @Part("Direccion") RequestBody direccion,
                @Part("Uso") RequestBody uso,
                @Part("Tipo") RequestBody tipo,
                @Part("cantidadDeAmbientes") RequestBody cantidadDeAmbientes,
                @Part("Precio") RequestBody precio,
                @Part("Image") RequestBody image,
                @Part MultipartBody.Part imagenFile);

    }

    public static void guardarToken(Context context, String token){
        SharedPreferences sp = context.getSharedPreferences("token.xml", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token",token );
        editor.commit();
    }
    public static String leerToken(Context context){
        SharedPreferences sp = context.getSharedPreferences("token.xml", 0);

        return sp.getString("token","" );

    }
}
