package com.example.santiago.votaciones;

import android.content.ContentValues;

public class Ciudadano {
    protected String nombre;
    protected String DNI;

    public Ciudadano(String nombre, String dni){
        this.nombre = dni;
        this.DNI = dni;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getDNI(){
        return this.DNI;
    }

    public String toString(){
        return this.DNI + ":" + this.nombre;
    }

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        cv.put("Nombre", nombre);
        cv.put("DNI", DNI);
        return cv;
    }
}
