package com.example.inmobiliariasinapirest.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.inmobiliariasinapirest.R;
import com.example.inmobiliariasinapirest.databinding.FragmentDetalleContratosBinding;
import com.example.inmobiliariasinapirest.modelo.Contrato;
import com.example.inmobiliariasinapirest.modelo.Inmueble;


public class DetalleContratosFragment extends Fragment {

    private DetalleContratosViewModel mv;
    private FragmentDetalleContratosBinding binding;

    public static DetalleContratosFragment newInstance() {
        return new DetalleContratosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(DetalleContratosViewModel.class);
        binding = FragmentDetalleContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle bundle = getArguments();

        mv.getContrato().observe(getActivity(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) { binding.tvCodigoContrato.setText(contrato.getId()+"");
                binding.tvFechaInContrato.setText(contrato.getFechaInicio());
                binding.tvFechaOutContrato.setText(contrato.getFechaFinal());
                binding.tvInmuebleContrato.setText(contrato.getInmueble().getDireccion());
                binding.tvInquilinoContrato.setText(contrato.getInquilino().getNombre());
                binding.tvMontoContrato.setText(contrato.getMontoMensual()+"");
                binding.btPagosContrato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Bundle bundle = new Bundle();
                       bundle.putSerializable("contrato",contrato);
                       Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_menu).navigate(R.id.nav_pagos,bundle);
                    }
                });
            }
        });
       mv.setContrato((Inmueble) bundle.getSerializable("inmueble"));
        return root;
    }

        @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}


