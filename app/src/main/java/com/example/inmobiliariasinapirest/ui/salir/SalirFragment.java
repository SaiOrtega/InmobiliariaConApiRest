package com.example.inmobiliariasinapirest.ui.salir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariasinapirest.databinding.FragmentSalirBinding;


public class SalirFragment extends Fragment {

    private FragmentSalirBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSalirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Alert.mostrarDialogoBotones(getActivity());
        binding.btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert.mostrarDialogoBotones(getActivity());
            }
        });

       return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}