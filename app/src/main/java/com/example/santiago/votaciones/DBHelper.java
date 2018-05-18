package com.example.santiago.votaciones;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public static final String NAME_TB_CANDIDATOS = "tbCandidatos";
    public static final String NAME_TB_PERSONAS = "tbPersonas";
    public static final String NAME_TB_VOTOS = "tbVotos";
    public static final String DB_NAME = "dbappvotos";

    private static final String createCandidatosTable = "CREATE TABLE " + NAME_TB_CANDIDATOS +
                                "(Nombre TEXT," +
                                "DNI TEXT PRIMARY KEY," +
                                "Partido TEXT," +
                                "urlImg TEXT)";

    private static final String createCiudadanosTable = "CREATE TABLE " + NAME_TB_PERSONAS +
                                "(Nombre TEXT," +
                                "DNI TEXT PRIMARY KEY)";


    private static final String createVotosTable = "CREATE TABLE " + NAME_TB_VOTOS +
            "(id integer primary key autoincrement," +
            "DNIcandidato text," +
            "DNIciudadano text," +
            "FOREIGN KEY(DNIcandidato) REFERENCES " + NAME_TB_CANDIDATOS + "(DNI)," +
            "FOREIGN KEY(DNIciudadano) REFERENCES " + NAME_TB_PERSONAS + "(dni))";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createCandidatosTable);
        db.execSQL(createCiudadanosTable);
        db.execSQL(createVotosTable);
        db.execSQL("INSERT INTO tbCandidatos VALUES('Voto en blanco','1234','Campaña del blanco', '')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME_TB_CANDIDATOS);
        db.execSQL("DROP TABLE IF EXISTS " + NAME_TB_PERSONAS);
        db.execSQL("DROP TABLE IF EXISTS " + NAME_TB_VOTOS);
        db.execSQL(createCandidatosTable);
        db.execSQL(createCiudadanosTable);
        db.execSQL(createVotosTable);
    }
}
