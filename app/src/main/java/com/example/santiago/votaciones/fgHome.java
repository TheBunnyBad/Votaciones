package com.example.santiago.votaciones;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class fgHome extends Fragment {
    public fgHome() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fg_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Inicio");
        final ListView lv = getView().findViewById(R.id.list_view_personas);

        switchLv(lv);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                final TextView et = new TextView(getContext());
                et.setText("¿Seguro que desea borrar este candidato?");
                et.setGravity(Gravity.CENTER);
                alertDialogBuilder.setView(et).setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Candidato c = App.getCandidatos().get(position);
                        App.borrarCandidato(c);
                    }
                }).setNeutralButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                alertDialogBuilder.create().show();
                return false;
            }
        });
        //Cargamos la lista al listview
        Spinner spn = getView().findViewById(R.id.spnPersonas);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switchLv(lv);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    private void switchLv(ListView lv){
        switch (((Spinner)getView().findViewById(R.id.spnPersonas)).getSelectedItemPosition()){
            case 0:
                cargarListViewConCandidatos(lv);
                break;
            case 1:
                cargarListViewConPersonas(lv);
                break;
        }
    }
    private void cargarListViewConCandidatos(ListView lv){
        CustomAdapterCandidato ca = new CustomAdapterCandidato(getActivity(),
                R.layout.candidatos_list_view,
                App.getCandidatos());
        lv.setAdapter(ca);
    }

    private void cargarListViewConPersonas(ListView lv){
        ArrayAdapter<Ciudadano> ca = new ArrayAdapter<Ciudadano>(getContext(),
                android.R.layout.simple_list_item_1,
                App.getCiudadanos());
        lv.setAdapter(ca);
    }
}
