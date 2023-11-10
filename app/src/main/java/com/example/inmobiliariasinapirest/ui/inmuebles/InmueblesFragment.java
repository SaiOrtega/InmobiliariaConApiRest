package com.example.inmobiliariasinapirest.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.inmobiliariasinapirest.R;
import com.example.inmobiliariasinapirest.databinding.FragmentInmueblesBinding;
import com.example.inmobiliariasinapirest.modelo.Inmueble;
import com.example.inmobiliariasinapirest.ui.InmueblesFragmentAdapter;

import java.util.List;

public class InmueblesFragment extends Fragment {

    private FragmentInmueblesBinding binding;
    private InmueblesViewModel mv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(InmueblesViewModel.class);
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(InmueblesFragment.this.requireView()).navigate(R.id.nav_crearInmueble);
            }
        });

        mv.setInmuebles().observe(getActivity(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                RecyclerView rv = binding.rvInmuebleLista;
                GridLayoutManager grid = new GridLayoutManager(root.getContext(), 2, GridLayoutManager.VERTICAL, false);
                rv.setLayoutManager(grid);
                InmueblesFragmentAdapter adapter = new InmueblesFragmentAdapter(getActivity(), inmuebles, getLayoutInflater(), R.id.nav_detalle_inmueble);

                rv.setAdapter(adapter);
            }
        });
        mv.setListaInmueblesMutable();
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}