package com.example.luis.tiendacontrol.esquemaSqlLite;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;

import com.example.luis.tiendacontrol.data.util.Mensaje;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by luis on 14/12/2017.
 */

public class BackupRestore {
    /*
    public static void importDB(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "<package name>"
                        + "//databases//" + "<database name>";
                String backupDBPath = "<backup db filename>"; // From SD directory.
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(backupDB).getChannel();
                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Mensaje.mensajeToas(context, "Importacion Exitosa");

            }
        } catch (Exception e) {

            Mensaje.mensajeToas(context, "Importacion Fallida");
        }
    }

    private void exportDB(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "<package name>"
                        + "//databases//" + "<db name>";
                String backupDBPath = "<destination>";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Mensaje.mensajeToas(context, "Importacion Exitosa");
            }
        } catch (Exception e) {
            Mensaje.mensajeToas(context, "Importacion Fallida");
        }
    }*/

    public static void importDB(Context context, Dialog dig) {
        Mensaje.mensajeToas(context, "Sep Import");
        try {
            File sd = Environment.getExternalStorageDirectory();
            if (sd.canWrite()) {
                File backupDB = context.getDatabasePath("ventas");
                String backupDBPath = String.format("%s.bak", "ventas");
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Mensaje.mensajeToas(context, "Importacion Exitosa");
                dig.cancel();
            }
        } catch (Exception e) {
            //Mensaje.mensajeToas(context, e.getMessage());
            Mensaje.mensajeToas(context, "Error");
            //e.printStackTrace();
        }
        //Mensaje.mensajeToas(context, "Salio");
    }

    public static void exportDB(Context context, Dialog dig) {
        Mensaje.mensajeToas(context, "Sep Export");
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String backupDBPath = String.format("%s.bak", "ventas");
                File currentDB = context.getDatabasePath("ventas");
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Mensaje.mensajeToas(context, "Importacion Exitosa");
                dig.cancel();
            }
        } catch (Exception e) {
            //e.printStackTrace();
            Mensaje.mensajeToas(context, e.getMessage());
        }
    }
}
