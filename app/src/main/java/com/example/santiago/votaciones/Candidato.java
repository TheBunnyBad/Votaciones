package com.example.santiago.votaciones;

import android.content.ContentValues;

public class Candidato {
    private String nombre;
    private String DNI;
    private String partido;

    public Candidato(String n, String d, String p){
        this.nombre = n;
        this.DNI = d;
        this.partido = p;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }


    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        cv.put("Nombre", nombre);
        cv.put("DNI", DNI);
        cv.put("Partido", partido);
        return cv;
    }

    public String toString(){
        return this.nombre + this.partido;
    }
}
