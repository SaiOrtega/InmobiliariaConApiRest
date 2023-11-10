package com.example.inmobiliariasinapirest.ui.inmuebles;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariasinapirest.modelo.Inmueble;
import com.example.inmobiliariasinapirest.modelo.InmuebleEnviar;
import com.example.inmobiliariasinapirest.modelo.TipoEnum;
import com.example.inmobiliariasinapirest.modelo.UsoEnum;
import com.example.inmobiliariasinapirest.request.ApiClientRetrofit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearInmuebleViewModel extends AndroidViewModel {

private MutableLiveData<Uri> uriMutableLiveData;

private MutableLiveData<Boolean> guardarMutable;

public Context context;
private MutableLiveData<InmuebleEnviar> inmuebleMutableLiveData;

private MutableLiveData<ArrayAdapter<String>> mAdapterTipo;
    private MutableLiveData<ArrayAdapter<String>> mAdapterUso;

    public CrearInmuebleViewModel(@NonNull Application application) {
        super(application);
        mAdapterTipo = new MutableLiveData<>();
        mAdapterUso = new MutableLiveData<>();
        cargarSpinner();
    }
    public LiveData<ArrayAdapter<String>> getMAdapterTipo(){
        return mAdapterTipo;
    }
    public LiveData<ArrayAdapter<String>> getMAdapterUso(){
        return mAdapterUso;
    }


    public LiveData<Boolean> getGuardar(){
        if(guardarMutable == null){
            guardarMutable = new MutableLiveData<>();
        }
        return guardarMutable;
    }

    public LiveData<InmuebleEnviar> getInmuebleMutableLiveData(){
        if(inmuebleMutableLiveData == null){
            inmuebleMutableLiveData = new MutableLiveData<>();
        }
        return inmuebleMutableLiveData;
    }

        public void crearInmueble(InmuebleEnviar inmueble, Uri uri){
     String token = ApiClientRetrofit.leerToken(getApplication().getApplicationContext());
     ApiClientRetrofit.ApiInmobiliaria end = ApiClientRetrofit.getApiInmobiliaria();

     RequestBody direccion =RequestBody.create(MediaType.parse("application/json"),inmueble.getDireccion());
     RequestBody tipo =RequestBody.create(MediaType.parse("application/json"),inmueble.getTipo()+"");
     RequestBody precio =RequestBody.create(MediaType.parse("application/json"),inmueble.getPrecio()+"");
     RequestBody uso =RequestBody.create(MediaType.parse("application/json"),inmueble.getUso()+"");
     RequestBody cantidadDeAmbites =RequestBody.create(MediaType.parse("application/json"),inmueble.getCantidadDeAmbientes()+"");
     RequestBody img = RequestBody.create(MediaType.parse("application/json"),inmueble.getImage());
     inmueble.setImage(convertirImgBase64(uri));

     String path = RealPathUtil.getRealPath(getApplication().getApplicationContext(), uri);
     File file = new File(path);

     RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
     MultipartBody.Part imagePart = MultipartBody.Part.createFormData("imagenFile", file.getName(), fileBody);

     Call<InmuebleEnviar> call = end.crearInmueble(token, direccion, uso, tipo, cantidadDeAmbites, precio,img,imagePart);
     call.enqueue(new Callback<InmuebleEnviar>() {
         @Override
         public void onResponse(Call<InmuebleEnviar> call, Response<InmuebleEnviar> response) {
             if (response.isSuccessful()) {


                 Log.d("salida del creador",response.body().getDireccion());
                 Toast.makeText(getApplication(), "Inmueble Creado", Toast.LENGTH_SHORT).show();
                    inmuebleMutableLiveData.setValue(response.body());
             }else {
                 Log.d("salida resp", response.raw().message());
                 Toast.makeText(getApplication(), "Error al crear", Toast.LENGTH_SHORT).show();
             }
         }

         @Override
         public void onFailure(Call<InmuebleEnviar> call, Throwable t) {
             Log.d("salida", "ERROR " + t.getMessage());
         }
     });
 }
    public void cargarSpinner() {
        List<String> tipos = Arrays.stream(TipoEnum.values()).map(TipoEnum::name).collect(Collectors.toList());
        mAdapterTipo.setValue(new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, tipos));
        mAdapterTipo.getValue().setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<String> usos = Arrays.stream(UsoEnum.values()).map(UsoEnum::name).collect(Collectors.toList());
        mAdapterUso.setValue(new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, usos));
        mAdapterUso.getValue().setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }


   public String convertirImgBase64(Uri uri) {
        try {
            ContentResolver contentResolver = getApplication().getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(uri);

            if (inputStream != null) {
                // Decodifica la imagen en un objeto Bitmap
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2; // Puedes ajustar este valor para comprimir más o menos
                Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream, null, options);

                // Comprime la imagen (ajusta la calidad según tus necesidades)
                int quality = 50; // Rango de 0 (más comprimido) a 100 (menos comprimido)
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);

                // Convierte la imagen comprimida a Base64
                byte[] imageBytes = byteArrayOutputStream.toByteArray();
                return Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } else {
                Log.d("salida", "Error al cargar la imagen");
                return null;
            }
        } catch (IOException e) {
            // Handle error: Ocurrió un error al leer la imagen.
            e.printStackTrace();
            return null;
        }
    }


    // TODO: Implement the ViewModel
}
