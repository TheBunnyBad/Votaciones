package com.example.santiago.votaciones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ActVotar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_votar);
        CustomAdapterCandidato ca = new CustomAdapterCandidato(this,
                R.layout.candidatos_votacion_view,
                App.getCandidatos());
        ListView lv = findViewById(R.id.lvCandidatos);
        lv.setAdapter(ca);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                App.guardarVoto(getApplicationContext(),
                        new Voto(App.getCandidatos().get(position).getDNI(),
                                App.getDNIVotante()));
                Toast.makeText(getApplicationContext(), "¡VOTO GUARDADO!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ActInicio.class));
                finish();
            }
        });
    }
}
