package com.example.santiago.votaciones;

import android.content.ContentValues;

public class Voto {
    private String DNIcandidato;
    private String DNIciudadano;
    private int id;

    public Voto(String can, String ciud){
        id = App.getVotos().size() + 1;
        this.DNIcandidato = can;
        this.DNIciudadano = ciud;
    }

    public Voto(int i, String can, String ciud){
        this.id = i;
        this.DNIcandidato = can;
        this.DNIciudadano = ciud;
    }

    public String getDNIcandidato() {
        return DNIcandidato;
    }

    public String getDNIciudadano() {
        return DNIciudadano;
    }

    public int getId() {
        return id;
    }

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        cv.put("DNIcandidato", DNIcandidato);
        cv.put("DNIciudadano", DNIciudadano);
        return cv;
    }
}
