package com.example.santiago.votaciones;

import android.content.ContentValues;
import android.net.Uri;

public class Candidato extends Ciudadano{
    private String partido;
    private String url;

    public Candidato(String n, String d, String p, String url){
        super(n, d);
        this.partido = p;
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public String getPartido() {
        return partido;
    }

    public Uri getURI(){
        return Uri.parse(url);
    }


    public ContentValues getContentValues(){
        ContentValues cv = super.getContentValues();
        cv.put("Partido", partido);
        cv.put("urlImg", url);
        return cv;
    }

    public String toString(){
        return this.nombre + this.partido;
    }
}
