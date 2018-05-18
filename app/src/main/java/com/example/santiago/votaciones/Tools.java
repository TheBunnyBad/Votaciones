package com.example.santiago.votaciones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Tools {
    public static boolean formularioDiligenciado(EditText[] campos){
        for(EditText et : campos) if(et.getText().toString().equals("")) return false;
        return true;
    }

    public static void restartEdiTexts(EditText[] campos){
        for(EditText et : campos) et.setText("");
    }

    /**
     * Muestra un AlertDialog con los botones que se le pasen por parámetros
     * El AlertDialog es no cancelable
     * @param c Contexto de la aplicación
     * @param msgAlert Mensaje que se mostrará en la alerta
     * @param msgNegativo Mensaje que se mostrará en el botón negativo
     * @param listenerNegativo onClickListener del boton negativo
     * @param msgPositivo Mensaje que se mostrará en el botón positivo
     * @param listenerPositivo onClickListener del boton positivo
     * @param msgNeutral Mensaje que se mostrará en el botón neutral
     * @param listenerNeutral onClickListener del boton neutral
     */
    private static void buildAlertDialog(final Context c, String msgAlert,
                                           String msgNeutral, DialogInterface.OnClickListener listenerNeutral,
                                           @Nullable String msgPositivo, DialogInterface.OnClickListener listenerPositivo,
                                           @Nullable String msgNegativo, DialogInterface.OnClickListener listenerNegativo){

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
        final EditText et = new EditText(c);
        et.setHint(msgAlert);
        et.setGravity(Gravity.CENTER);
        alertDialogBuilder.setView(et);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(msgPositivo, listenerNeutral)
                .setNeutralButton(msgNeutral, listenerNeutral)
                .setNegativeButton(msgNegativo, listenerNegativo);

        alertDialogBuilder.create()
                .show();
    }
}
