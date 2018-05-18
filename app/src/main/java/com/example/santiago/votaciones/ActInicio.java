package com.example.santiago.votaciones;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        ((EditText)findViewById(R.id.txtNombre))
                .setVisibility(App.getAppMode().equals(App.APP_MODE_REGISTRO) ? View.VISIBLE : View.GONE);
        Button botonCiudadano = findViewById(R.id.btnAccionCiudadano);
        botonCiudadano.setText(App.getAppMode().equals(App.APP_MODE_REGISTRO) ? "REGISTRAR CÉDULA" : "VOTAR");
        botonCiudadano.setOnClickListener(App.getAppMode().equals(App.APP_MODE_REGISTRO) ? registrarCedula() : irAVotaciones());

        if(App.getAppMode().equals(App.APP_MODE_VOTACIONES)){
            mostrarEstadisticasEnVivo();
        }
    }


    private View.OnClickListener registrarCedula(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText form[] = {findViewById(R.id.txtCedula), findViewById(R.id.txtNombre)};
                if(Tools.formularioDiligenciado(form)) {
                    App.guardarCiudadano(getApplicationContext(),
                            new Ciudadano(((EditText) findViewById(R.id.txtNombre)).getText().toString(),
                                            ((EditText) findViewById(R.id.txtCedula)).getText().toString()));
                    Tools.restartEdiTexts(form);
                }
            }
        };
    }

    private View.OnClickListener irAVotaciones(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ciudadano ciud = new Ciudadano("ANON",((EditText)findViewById(R.id.txtCedula)).getText().toString());
                if(!App.votoExistente(getApplicationContext(), ciud) && App.ciudadanoRegistrado(ciud)){
                    startActivity(new Intent(getApplicationContext(), ActVotar.class));
                    finish();
                    App.setDNIVotante(((EditText)findViewById(R.id.txtCedula)).getText().toString());
                }
            }
        };
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

    /**
     * Dibuja los resultados en la activity
     */
    private void mostrarEstadisticasEnVivo() {
        PieChart pieChart = (PieChart)findViewById(R.id.pie);
        List<PieEntry> pieEntries = new ArrayList<>();
        for(Candidato c : App.getCandidatos()) {
            pieEntries.add(new PieEntry(
                    Float.parseFloat(App.contarVotos(c) + ""), c.getNombre()));
        }
        PieDataSet set = new PieDataSet(pieEntries, "Elecciones");
        PieData data = new PieData(set);

        int colors[] = new int[App.getCandidatos().size()];
        Random random = new Random();
        for(int i = 0; i < colors.length; i++) {
            colors[i] = Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255));
        }

        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieChart.setData(data);
        pieChart.invalidate();
    }

}
