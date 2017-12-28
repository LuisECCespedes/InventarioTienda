package com.example.luis.tiendacontrol.esquemaSqlLite.crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.luis.tiendacontrol.esquemaSqlLite.*;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.*;

/**
 * Created by luis on 13/12/2017.
 */

public class Delete {

    private static SQLiteDatabase db = null;
    private static ConexionSqliteHelper con = null;

    public static void EliminarRegistro(Context context, String codigo,String cTabla)
    {   //Eliminamos el dato
        con = new ConexionSqliteHelper(context);

        db =con.getWritableDatabase();

        // segun el caso se acondiciona
        switch (cTabla)
        {
            case "cliente":
                db.delete(ClienteTabla.TABLA,ClienteTabla.CAMPO_ID + " = ?",new String[]{codigo});
                break;

            case "marca":
                // Validacion -- productos con esta marca , vuelven al codigo 1
                reiniciarTipoMarcaProductos(false,codigo);
                db.delete(MarcaTabla.TABLA,MarcaTabla.CAMPO_ID + " = ?",new String[]{codigo});
                break;

            case "tipo":
                // Validacion -- productos con este tipo , vuelven al codigo 1
                reiniciarTipoMarcaProductos(true,codigo);
                db.delete(TipoTabla.TABLA,TipoTabla.CAMPO_ID + " = ?",new String[]{codigo});
                break;

            case "tipo_movimiento":
                db.delete(TipoMovimientoTabla.TABLA,TipoMovimientoTabla.CAMPO_ID + " = ?",new String[]{codigo});
                break;

            case "producto":
                // validacion producto ausente en kardex

                //if (!comprobarExistenciaEnKardex(codigo)) {
                    db.delete(ProductoTabla.TABLA,ProductoTabla.CAMPO_ID + " = ?",new String[]{codigo});
                //}
                break;
        }

    }

    private static void reiniciarTipoMarcaProductos(boolean bTipo , String codigo) {
        if (bTipo) {

        } else {

        }
    }

    private static boolean comprobarExistenciaEnKardex(String codigo) {

        if (true) {
            //Mensaje de negacion
            return  true;
        } else {
            return  false;
        }
    }
}
