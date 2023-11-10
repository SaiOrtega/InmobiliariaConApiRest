package com.example.inmobiliariasinapirest;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.inmobiliariasinapirest.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel mv;
    private Context context;
    private SensorManager manager;
    private Sensor listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        context = getApplicationContext();
        solicitarPermiso();
        onResume();

        mv.getLogin().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Intent intent = new Intent(context, MenuActivity.class);
                startActivity(intent);
            }
        });
        mv.setLlamadaListener();
        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.login(binding.etEmail.getText().toString(), binding.etClave.getText().toString());
            }
        });

    }

    public void solicitarPermiso() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && (checkSelfPermission(ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) || (checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 1000);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED&& checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)){
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1002);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.etClave.setText("");
        binding.etEmail.setText("");
        manager.unregisterListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mv.getLlamada().observe(this, new Observer<Sensor>() {
            @Override
            public void onChanged(Sensor leeSensor) {
                manager = (SensorManager) getSystemService(SENSOR_SERVICE);
                List<android.hardware.Sensor> sensores = manager.getSensorList(android.hardware.Sensor.TYPE_ACCELEROMETER);
                if (sensores.size() > 0) {
                    android.hardware.Sensor acelerometro = sensores.get(0);
                    listener = leeSensor;
                    manager.registerListener(listener, acelerometro, SensorManager.SENSOR_DELAY_GAME);
                }
            }
        });
    }
}