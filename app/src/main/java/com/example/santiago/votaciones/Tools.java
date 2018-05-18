package com.example.santiago.votaciones;

import android.widget.EditText;

public class Tools {
    public static boolean formularioDiligenciado(EditText[] campos){
        for(EditText et : campos) if(et.getText().toString().equals("")) return false;
        return true;
    }

    public static void restartEdiTexts(EditText[] campos){
        for(EditText et : campos) et.setText("");
    }
}
