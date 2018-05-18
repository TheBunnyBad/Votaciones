package com.example.santiago.votaciones;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class App {
    //////////////////////////////////////////////////
    //// ATRIBUTOS
    //////////////////////////////////////////////////

    //// DBOpenHelper
    private static DBHelper dbhelper;
    private static SQLiteDatabase sqLiteDatabase;

    //// ARRAYLIST PARA CONTROLAR LOS CANDIDATOS
    private static ArrayList<Candidato> candidatos = new ArrayList<>();

    //// NOMBRE DE LAS SHAREDPREFS
    private static final String MY_PREFERENCES_NAME = "SHAREDPREF_PASS_KEY";

    //// CONSTANTES RELACIONADAS AL PASSWORD
    private static final String MY_PREFERENCES_PASSWORD_KEY = "PASS";
    private static final String DEFAULT_PASS = "admin";

    //// CONSTANTE RELACIONADA AL MODO DE LA APLICACIÓN EN LA QUE NOS ENCONTRAMOS:
    //// 1) PERIODO DE VOTACIONES
    //// 2) PERIODO DE REGISTRO
    private static final String MY_PREFERENCES_APP_MODE_KEY = "APP_MODE";
    public static final String APP_MODE_REGISTRO = "REGISTRO";
    public static final String APP_MODE_VOTACIONES = "VOTACIONES";

    //// Contraseña del administrador
    private static String pass;

    //// Modo de la aplicación
    private static String AppMode;

    //////////////////////////////////////////////////
    //// MÉTODOS
    //////////////////////////////////////////////////

    public static void initApp(Context c){
        iniciarDB(c);
        loadPassword(c);
        loadAppMode(c);
    }

    private static void iniciarDB(Context c){
        dbhelper = new DBHelper(c);
        sqLiteDatabase = dbhelper.getReadableDatabase();

        String columns[] = {"Nombre", "DNI", "Partido"};
        Cursor cursor = sqLiteDatabase.query(DBHelper.NAME_TB_CANDIDATOS,
                columns,
                null,
                null,
                null,
                null,
                null);
        while ((cursor.moveToNext())){
            candidatos.add(new Candidato(cursor.getString(cursor.getColumnIndex("Nombre")),
                    cursor.getString(cursor.getColumnIndex("DNI")),
                    cursor.getString(cursor.getColumnIndex("Partido"))));
        }
        close();
        printCandidatos();
    }

    private static void printCandidatos(){
        for(Candidato c : candidatos) Log.i("CANDIDATO", c.getNombre());
    }
    private static void open(){
        sqLiteDatabase = dbhelper.getWritableDatabase();
    }

    private static void close(){
        sqLiteDatabase.close();
    }


    /**
     * Carga el password del admin de la aplicación al inicio de la ejecución del programa
     * @param c
     */
    private static void loadPassword(Context c){
        SharedPreferences prefs = c.getSharedPreferences(MY_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        String restoredText = prefs.getString(MY_PREFERENCES_PASSWORD_KEY, null);
        if(restoredText != null){
            pass = restoredText;
            Log.i("PASS", pass);
        } else {
            alertDialogForPass(c);
        }
    }

    /**
     * Muestra un AlertDialog con dos botones:
     * Uno que resetea la contraseña
     * Y otro que cambia la contraseña a la ingresada
     * @param c es el contexto de la aplicación en la que se va a mostrar el diálogo. En este
     *          caso, en el inicio de la aplicación
     */
    private static void alertDialogForPass(final Context c){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);

        final EditText et = new EditText(c);
        et.setHint("Ingrese nueva contraseña de administrador");
        et.setGravity(Gravity.CENTER);

        //Añadimos el EditText al Dialog
        alertDialogBuilder.setView(et);

        // set dialog message
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String newPass = et.getText().toString();

                        //Si la contraseña ingresada es válida
                        if(!newPass.equals("")){
                            App.saveNewPassword(newPass, c);
                        } else {
                            alertDialogForPass(c);
                        }
                    }
                }).setNeutralButton("DEFECTO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.saveNewPassword(DEFAULT_PASS, c);
                        Toast.makeText(c,
                                "Use \"admin\" para acceder a la sección de administrador."
                                + "Esta contraseña podrá ser cambiada después.",
                                Toast.LENGTH_SHORT);
                    }
                });

        alertDialogBuilder.create()
                .show();
    }

    public static void saveNewPassword(String newPass, Context c){
        setPass(newPass);
        SharedPreferences prefs = c.getSharedPreferences(MY_PREFERENCES_NAME,
                Context.MODE_PRIVATE);

        prefs.edit().putString(MY_PREFERENCES_PASSWORD_KEY, newPass).apply();
    }

    private static void loadAppMode(Context c){
        SharedPreferences prefs = c.getSharedPreferences(MY_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        String restoredText = prefs.getString(MY_PREFERENCES_APP_MODE_KEY, APP_MODE_REGISTRO);
        AppMode = restoredText;
    }

    public static void switchAppMode(Context c){
        AppMode = (AppMode.equals(APP_MODE_REGISTRO)) ? APP_MODE_VOTACIONES : APP_MODE_REGISTRO;
        initApp(c);
    }

    public static void guardarCandidato(Candidato cand){
        candidatos.add(cand);
        open();
        sqLiteDatabase.insert(DBHelper.NAME_TB_CANDIDATOS, null, cand.getContentValues());
    }
    //////////////////////////////////////////////////
    //// CAPSULED FIELDS
    //////////////////////////////////////////////////
    public static String getPass(){return pass;}

    public static void setPass(String p){   pass = p;   }

    public static String getAppMode(){  return AppMode; }

    public static ArrayList<Candidato> getCandidatos(){ return candidatos; }
}
