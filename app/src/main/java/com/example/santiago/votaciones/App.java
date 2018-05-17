package com.example.santiago.votaciones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

public class App {
    //////////////////////////////////////////////////
    //// ATRIBUTOS
    //////////////////////////////////////////////////

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
        loadPassword(c);
        loadAppMode(c);
    }

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

    //////////////////////////////////////////////////
    //// CAPSULED FIELDS
    //////////////////////////////////////////////////
    public static String getPass(){return pass;}

    public static void setPass(String p){   pass = p;   }

    public static String getAppMode(){  return AppMode; }
}
