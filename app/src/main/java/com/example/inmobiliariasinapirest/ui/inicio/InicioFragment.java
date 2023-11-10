package com.example.inmobiliariasinapirest.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.inmobiliariasinapirest.databinding.FragmentInicioBinding;
import com.example.inmobiliariasinapirest.R;


import com.google.android.gms.maps.SupportMapFragment;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;
    private InicioViewModel mv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InicioViewModel inicioViewModel =
                new ViewModelProvider(this).get(InicioViewModel.class);

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InicioViewModel.class);
        mv.getMapa().observe(getActivity(), new Observer<InicioViewModel.miMapa>() {
            @Override
            public void onChanged(InicioViewModel.miMapa miMapa) {
                SupportMapFragment smf = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
                smf.getMapAsync(miMapa);
            }
        });
        mv.iniciarMapa();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}