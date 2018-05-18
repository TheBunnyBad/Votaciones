package com.example.santiago.votaciones;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterCandidato extends ArrayAdapter<Candidato> {

    private final Activity context;
    private final ArrayList<Candidato> candidatos;


    public CustomAdapterCandidato(Activity context, int layout, ArrayList<Candidato> candidatos) {
        super(context, layout, candidatos);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.candidatos = candidatos;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.candidatos_list_view, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(candidatos.get(position).getNombre());
        imageView.setImageURI(candidatos.get(position).getURI());
        Log.i("DATO", candidatos.get(position).getURI().getPath());
        extratxt.setText("Partido político: "+ candidatos.get(position).getPartido());
        return rowView;

    };

}
