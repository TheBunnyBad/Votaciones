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

    //// ARRAYLIST PARA CONTROLAR LOS DATOS
    private static ArrayList<Candidato> candidatos;
    private static ArrayList<Ciudadano> ciudadanos;
    private static ArrayList<Voto> votos;

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
        cargarCandidatos();
        cargarCiudadanos();
        cargarVotos();
        close();
    }

    private static void cargarVotos(){
        votos = new ArrayList<>();
        String columns[] = {"id", "DNIcandidato", "DNIciudadano"};
        Cursor cursor = sqLiteDatabase.query(DBHelper.NAME_TB_VOTOS, columns,
                null, null, null, null, null);
        while(cursor.moveToNext()){
            votos.add(new Voto(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("DNIcandidato")),
                    cursor.getString(cursor.getColumnIndex("DNIciudadano"))));
        }
    }

    private static void cargarCandidatos(){
        candidatos = new ArrayList<>();
        String columns[] = {"Nombre", "DNI", "Partido", "urlImg"};
        Cursor cursor = sqLiteDatabase.query(DBHelper.NAME_TB_CANDIDATOS,
                columns,
                null,null,null,null,null);
        while ((cursor.moveToNext())){
            candidatos.add(new Candidato(cursor.getString(cursor.getColumnIndex("Nombre")),
                    cursor.getString(cursor.getColumnIndex("DNI")),
                    cursor.getString(cursor.getColumnIndex("Partido")),
                    cursor.getString(cursor.getColumnIndex("urlImg"))));
        }
    }

    private static void cargarCiudadanos(){
        ciudadanos  = new ArrayList<>();
        String columns[] = {"Nombre", "DNI"};
        Cursor cursor = sqLiteDatabase.query(DBHelper.NAME_TB_PERSONAS,
                columns,
                null,null,null,null,null);
        while(cursor.moveToNext()){
            ciudadanos.add(new Ciudadano(cursor.getString(cursor.getColumnIndex("Nombre")),
                    cursor.getString(cursor.getColumnIndex("DNI"))));
        }
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

    public static boolean votoExistente(Context ctx, Ciudadano c){
        for(Voto v : votos){
            if(v.getDNIciudadano().equals(c.getDNI())){
                Toast.makeText(ctx, "CIUDADANO YA VOTÓ", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }
    /**
     * Muestra un AlertDialog con dos botones:
     * Uno que resetea la contraseña
     * Y otro que cambia la contraseña a la ingresada
     * @param c es el contexto de la aplicación en la que se va a mostrar el diálogo. En este
     *          caso, en el inicio de la aplicación
     */
    public static void alertDialogForPass(final Context c){
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

    private static void saveAppMode(Context c){
        SharedPreferences prefs = c.getSharedPreferences(MY_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        prefs.edit().putString(MY_PREFERENCES_APP_MODE_KEY, AppMode).apply();
    }

    public static void switchAppMode(Context c){
        AppMode = (AppMode.equals(APP_MODE_REGISTRO)) ? APP_MODE_VOTACIONES : APP_MODE_REGISTRO;
        saveAppMode(c);
    }

    public static void guardarCandidato(Context ctx, Candidato cand) {
        if (!ciudadanoRegistrado(cand)){
            candidatos.add(cand);
            open();
            sqLiteDatabase.insert(DBHelper.NAME_TB_CANDIDATOS, null, cand.getContentValues());
            Toast.makeText(ctx, "CANDIDATO REGISTRADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ctx, "DNI YA EXISTE", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean ciudadanoRegistrado(Ciudadano ciudadano){
        for(Ciudadano c : candidatos) if(c.getDNI().equals(ciudadano.getDNI())) return true;
        for(Ciudadano c : ciudadanos) if(c.getDNI().equals(ciudadano.getDNI())) return true;
        return false;
    }

    public static void guardarCiudadano(Context ctx, Ciudadano ciud){
        if(!ciudadanoRegistrado(ciud)){
            ciudadanos.add(ciud);
            open();
            sqLiteDatabase.insert(DBHelper.NAME_TB_PERSONAS, null, ciud.getContentValues());
            Toast.makeText(ctx, "CÉDULA REGISTRADA EXITOSAMENTE", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ctx, "CÉDULA YA REGISTRADA", Toast.LENGTH_SHORT).show();

        }
    }

    public static void borrarCandidato(Candidato c){
        for(Candidato i : candidatos)if(i.getDNI().equals(c.getDNI())) candidatos.remove(i);
        sqLiteDatabase.execSQL("DELETE FROM " + DBHelper.NAME_TB_CANDIDATOS + " WHERE DNI=" + c.getDNI());
    }

    public static void reiniciarApp(Context ctx){
        sqLiteDatabase.execSQL("DELETE FROM " + DBHelper.NAME_TB_CANDIDATOS);
        sqLiteDatabase.execSQL("DELETE FROM " + DBHelper.NAME_TB_VOTOS);
        sqLiteDatabase.execSQL("DELETE FROM " + DBHelper.NAME_TB_PERSONAS);
        initApp(ctx);
    }

    public static void guardarVoto(Context ctx, Voto voto){
        votos.add(voto);
        open();
        sqLiteDatabase.insert(DBHelper.NAME_TB_VOTOS, null, voto.getContentValues());
    }

    public static int contarVotos(Candidato cand){
        if(getVotos().size() < 1) return 0;
        open();
        Cursor count = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM " +
                DBHelper.NAME_TB_VOTOS + " WHERE DNIcandidato=" + cand.getDNI(), null);
        count.moveToFirst();
        int contador = count.getInt(0);
        close();
        return contador;
    }

    //////////////////////////////////////////////////
    //// CAPSULED FIELDS
    //////////////////////////////////////////////////
    public static String getPass(){return pass;}

    public static void setPass(String p){   pass = p;   }

    public static String getAppMode(){  return AppMode; }

    public static ArrayList<Candidato> getCandidatos(){ return candidatos; }

    public static ArrayList<Ciudadano> getCiudadanos(){ return ciudadanos;  }

    public static ArrayList<Voto> getVotos(){   return votos;   }

    private static String dniVotante = "";
    public static void setDNIVotante(String dni){   dniVotante = dni;   }
    public static String getDNIVotante(){   return dniVotante;  }
    public static void restartDNIVotante(){ dniVotante = "";    }
}
