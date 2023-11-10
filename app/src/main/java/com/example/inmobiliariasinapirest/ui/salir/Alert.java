package com.example.inmobiliariasinapirest.ui.salir;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Alert {

    public Alert() {
    }

    public static void mostrarDialogoBotones(Context context){

         new AlertDialog.Builder(context)
                .setMessage("¿Estás seguro de que deseas salir?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((AppCompatActivity)context).finishAndRemoveTask();

                    }
                })
                .setTitle("Gracias por usar esta Aplicación")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context,"Continuamos",Toast.LENGTH_LONG).show();

                    }
                })
                .show();
    }

}
