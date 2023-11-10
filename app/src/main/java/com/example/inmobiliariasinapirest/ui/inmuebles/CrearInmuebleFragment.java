package com.example.inmobiliariasinapirest.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inmobiliariasinapirest.MainActivity;
import com.example.inmobiliariasinapirest.R;
import com.example.inmobiliariasinapirest.databinding.FragmentCrearInmuebleBinding;
import com.example.inmobiliariasinapirest.modelo.Inmueble;
import com.example.inmobiliariasinapirest.modelo.InmuebleEnviar;

import java.io.File;

public class CrearInmuebleFragment extends Fragment {

    private CrearInmuebleViewModel mViewModel;
    private FragmentCrearInmuebleBinding binding;

    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private File f;

    public static CrearInmuebleFragment newInstance() {
        return new CrearInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       mViewModel = new ViewModelProvider(this).get(CrearInmuebleViewModel.class);
       binding = FragmentCrearInmuebleBinding.inflate(inflater, container, false);
       mViewModel.getMAdapterTipo().observe(getViewLifecycleOwner(), stringArrayAdapter -> binding.spTipo.setAdapter(stringArrayAdapter));
       mViewModel.getMAdapterUso().observe(getViewLifecycleOwner(), stringArrayAdapter -> binding.spUso.setAdapter(stringArrayAdapter));
       View root = binding.getRoot();

        binding.tvInmuebleDireccion.setText("");
        binding.tvAmbientesInmueble.setText("");
        binding.tvInmueblePrecio.setText("");
        binding.imgSubida.setImageURI(null);
        binding.btnGuardar.setEnabled(false);




       binding.btnCargarImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            Intent takePictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            takePictureIntent.setType("image/*");//adiccion
            //startActivityForResult(takePictureIntent, PICK_IMAGE);
               startActivityForResult(Intent.createChooser(takePictureIntent, "Seleccione una Imagen"), 10);
               binding.btnGuardar.setEnabled(true);
           }
       });

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InmuebleEnviar inmueble = new InmuebleEnviar(
                        binding.tvInmuebleDireccion.getText().toString(),
                        binding.spTipo.getSelectedItemPosition(),
                        binding.spUso.getSelectedItemPosition(),
                        Integer.parseInt(binding.tvAmbientesInmueble.getText().toString()),
                        Double.parseDouble(binding.tvInmueblePrecio.getText().toString()),
                        imageUri.toString());
                mViewModel.crearInmueble(inmueble, imageUri);
            }
        });
        mViewModel.getInmuebleMutableLiveData().observe(getViewLifecycleOwner(), new Observer<InmuebleEnviar>() {
            @Override
            public void onChanged(InmuebleEnviar inmueble) {
                Navigation.findNavController(CrearInmuebleFragment.this.requireView()).navigate(R.id.nav_inmuebles);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 10) {
            imageUri = data.getData();
            final String realPath = RealPathUtil.getRealPath(getContext(), imageUri);
            this.f = new File(realPath);
            binding.imgSubida.setImageURI(imageUri);

            Log.d("salida ruta imagen", imageUri.toString());

        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrearInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }


}