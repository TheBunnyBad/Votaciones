package com.example.santiago.votaciones;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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
        switchLv();
        //Cargamos la lista al listview
        Spinner spn = getView().findViewById(R.id.spnPersonas);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switchLv();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void switchLv(){
        switch (((Spinner)getView().findViewById(R.id.spnPersonas)).getSelectedItemPosition()){
            case 0:
                cargarListViewConCandidatos();
                break;
            case 1:
                cargarListViewConPersonas();
                break;
        }
    }
    private void cargarListViewConCandidatos(){
        ListView lv = getView().findViewById(R.id.list_view_personas);
        CustomAdapterCandidato ca = new CustomAdapterCandidato(getActivity(), App.getCandidatos());
        lv.setAdapter(ca);
    }

    private void cargarListViewConPersonas(){
        ListView lv = getView().findViewById(R.id.list_view_personas);
        ArrayAdapter<Ciudadano> ca = new ArrayAdapter<Ciudadano>(getContext(),
                android.R.layout.simple_list_item_1,
                App.getCiudadanos());
        lv.setAdapter(ca);
    }
}
