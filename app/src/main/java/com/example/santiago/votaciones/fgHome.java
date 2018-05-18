package com.example.santiago.votaciones;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        //Cargamos la lista al listview
        switch ((String)((Spinner)getView().findViewById(R.id.spnPersonas)).getSelectedItem()){
            case "Candidatos":
                cargarListViewConCandidatos();
                break;
            case "Ciudadanos":

                break;

        }
    }

    private void cargarListViewConCandidatos(){
        ListView lv = getView().findViewById(R.id.list_view_personas);
        CustomAdapter ca = new CustomAdapter(getActivity(), App.getCandidatos());
        lv.setAdapter(ca);
        Log.i("OA", "AAAA");
    }

    private void cargarListViewConPersonas(){

    }
}
