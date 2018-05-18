package com.example.santiago.votaciones;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class fgAdministrarApp extends Fragment {

    public fgAdministrarApp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fg_administrar_app, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles}
        //Log.i("VIEW:", "" + getView().findViewById(R.id.etDNICandidato));

        getView().findViewById(R.id.btnCambiarPass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.alertDialogForPass(getContext());
            }
        });

        updateUI();
        getView().findViewById(R.id.btnCambiarModoApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.switchAppMode(getContext());
                updateUI();
            }
        });
    }

    private void updateUI(){
        Button btnCambiarModo = getView().findViewById(R.id.btnCambiarModoApp);
        btnCambiarModo.setText(btnCambiarModo.getText().toString().substring(0, 5)
                                + App.getAppMode());
    }
}

