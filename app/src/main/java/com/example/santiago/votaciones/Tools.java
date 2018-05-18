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

}
