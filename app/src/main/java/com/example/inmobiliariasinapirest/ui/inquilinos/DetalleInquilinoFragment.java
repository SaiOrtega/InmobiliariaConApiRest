package com.example.inmobiliariasinapirest.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariasinapirest.databinding.FragmentDetalleInquilinoBinding;
import com.example.inmobiliariasinapirest.modelo.Inmueble;
import com.example.inmobiliariasinapirest.modelo.Inquilino;


public class DetalleInquilinoFragment extends Fragment {

    private DetalleInquilinoViewModel mv;
    private FragmentDetalleInquilinoBinding binding;

    public static DetalleInquilinoFragment newInstance() {
        return new DetalleInquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DetalleInquilinoViewModel.class);
        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundle = getArguments();

        mv.getInquilino().observe(getActivity(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.tvApellidoInquilino.setText(inquilino.getApellido());
                binding.tvCodigoInquilino.setText(inquilino.getId()+"");
                binding.tvDniInquilino.setText(inquilino.getDni().toString());
                binding.tvEmailInquilino.setText(inquilino.getEmail());
                binding.tvInquilinoNombre.setText(inquilino.getNombre());
                binding.tvGaranteInquilino.setText(inquilino.getNombreGarante());
                binding.tvTelefonoInquilino.setText(inquilino.getTelefono());
                binding.tvTelefonoGaranteInquilino.setText(inquilino.getTelefonoGarante());
            }
        });

        mv.setInquilinoMutable((Inmueble) bundle.getSerializable("inmueble"));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}