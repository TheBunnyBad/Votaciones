package com.example.santiago.votaciones;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ActInicio extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_inicio);
        init();
    }

    /**
     * Inicializa los componentes de la aplicación y les añade sus respectivas acciones
     */
    private void init(){
        App.initApp(this);
        (findViewById(R.id.txtAdmin))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLogInPopUp();
                    }
                });

        Button b = findViewById(R.id.btnAccionCiudadano);
        b.setText(App.getAppMode().equals(App.APP_MODE_REGISTRO) ? "REGISTRAR CÉDULA" : "VOTAR");
    }


    /**
     * Muestra un pop up con un EditText, en el cual el usuario va a ingresar la contraseña
     * del administrador para tratar de ingresar a la sección de administrador del app
     */
    private void showLogInPopUp(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final EditText et = new EditText(this);
        et.setHint("Ingrese contraseña administrador");
        et.setGravity(Gravity.CENTER);
        alertDialogBuilder.setView(et);
        alertDialogBuilder.setPositiveButton("ENTRAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(et.getText().toString().equals(App.getPass())){
                    startActivity(new Intent(getApplicationContext(),
                            ActMenuAdmin.class));
                }
            }
        });

        alertDialogBuilder.create()
                .show();
    }
}
