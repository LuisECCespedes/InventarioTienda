package com.example.luis.tiendacontrol.data.util;

import android.content.Context;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by luis on 15/12/2017.
 */

public class Controles {

    public static boolean vacio(Context context, List<EditText> etLista, String[] mensaje)
    {
        boolean estado = false;
        int pos = 0;
        for (EditText etNombre : etLista) {
            if (!estado && etNombre.getText().length() == 0) {
                Mensaje.mensajeToas(context,mensaje[pos]);
                etNombre.requestFocus();
                estado = true;
                break;
            }
            pos++;
        }
        return estado;
    }
}
