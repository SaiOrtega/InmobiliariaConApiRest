package com.example.inmobiliariasinapirest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inmobiliariasinapirest.modelo.Propietario;
import com.example.inmobiliariasinapirest.ui.perfil.PerfilViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inmobiliariasinapirest.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;
    private MenuActivityViewModel mv;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MenuActivityViewModel.class);
        context = getApplicationContext();

        PerfilViewModel perfilViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PerfilViewModel.class);
        mv.observarPerfilenViewModel(perfilViewModel);

        setSupportActionBar(binding.appBarMenu.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_perfil, R.id.nav_salir,R.id.nav_inmuebles, R.id.nav_inquilinos, R.id.nav_contratos,R.id.nav_crearInmueble)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        mv.getPropietario().observe(this, new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                actualizarUIHeaderMenu(propietario);
            }
        });

        mv.setPropietarioMutable();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void actualizarUIHeaderMenu(Propietario propietario){
        View headerView = binding.navView.getHeaderView(0);
        TextView tvHeaderNombre = headerView.findViewById(R.id.tvMenuName);
        TextView tvHeaderEmail = headerView.findViewById(R.id.tvMenuEmail);
        ImageView ivMenuFoto = headerView.findViewById(R.id.ivMenuFoto);

        ivMenuFoto.setImageResource(propietario.getAvatar());//
        tvHeaderNombre.setText(propietario.getNombre());
        tvHeaderEmail.setText(propietario.getEmail());

        int ancho = 170;
        int alto = 170;
        ivMenuFoto.getLayoutParams().width = ancho;
        ivMenuFoto.getLayoutParams().height = alto;
        ivMenuFoto.requestLayout();

    }
}