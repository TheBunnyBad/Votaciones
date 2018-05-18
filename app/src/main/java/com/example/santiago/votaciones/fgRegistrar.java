package com.example.santiago.votaciones;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class fgRegistrar extends Fragment {

    Uri imageUri;
    boolean selectedImg = false;
    private static final int PICK_IMAGE = 100;

    public fgRegistrar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fg_registrar, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles}
        ((Button)getView().findViewById(R.id.btnElegirImagen)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        ((Button)getView().findViewById(R.id.btnRegistrarCandidato)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText[] campos = {getView().findViewById(R.id.etNombreCandidato),
                        getView().findViewById(R.id.etDNICandidato),
                        getView().findViewById(R.id.etPartidoCandidato)};
                if(Tools.formularioDiligenciado(campos) && selectedImg){
                    App.guardarCandidato(getContext(), new Candidato(campos[0].getText().toString(),
                            campos[1].getText().toString(),
                            campos[2].getText().toString(),
                            imageUri.toString()));
                    Tools.restartEdiTexts(campos);
                } else {
                    Toast.makeText(getContext(), "POR FAVOR DILIGENCIAR LOS CAMPOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            ((ImageView)getView().findViewById(R.id.imageUri)).setImageURI(imageUri);
            selectedImg = true;
        }
    }
}
