package com.example.luis.tiendacontrol.data.util;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luis.tiendacontrol.R;

/**
 * Created by luis on 12/12/2017.
 */

public class Mensaje {
    private Context context;

    public Mensaje(Context context) {
        this.context = context;
    }

    public  static Dialog mensajeDialogo(Context cnt, LayoutInflater inflater, String _line1, String _line2)
    {
        //LayoutInflater inflater = getLayoutInflater();

        View dialoglayout = inflater.inflate(R.layout.progres_bar, null);

        TextView etAsunto = dialoglayout.findViewById(R.id.etLinea1);
        TextView etMensaje =dialoglayout.findViewById(R.id.etLinea2);

        etAsunto.setText(_line1);
        etMensaje.setText(_line2);

        AlertDialog.Builder builder = new AlertDialog.Builder(cnt);
        builder.setView(dialoglayout);
        Dialog dialog= builder.create();
        return dialog;
    }

    public static void mensajeToas(Context cnt ,Object _mensaje)
    {
        Toast.makeText(cnt, _mensaje.toString(), Toast.LENGTH_LONG).show();
    }
}
