package com.example.luis.tiendacontrol.esquemaSqlLite.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.luis.tiendacontrol.data.modelo.*;
import com.example.luis.tiendacontrol.esquemaSqlLite.ConexionSqliteHelper;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.*;

/**
 * Created by luis on 13/12/2017.
 */

public class Update {
    private static SQLiteDatabase db = null;
    private static ConexionSqliteHelper con = null;
    private static ContentValues values = null;

    public  static void ActualizarRegistro(Context context,Object ObjParam, String cTabla)
    {
        con = new ConexionSqliteHelper(context);

        db =con.getWritableDatabase();

        // segun el caso se acondiciona
        switch (cTabla)
        {
            case "cliente":
                Cliente clie = (Cliente)ObjParam;

                values = new ContentValues();
                values.put(ClienteTabla.CAMPO_NOMBRE,clie.getClie_nombre());
                values.put(ClienteTabla.CAMPO_NUM_TEL,clie.getClie_num_tel());
                values.put(ClienteTabla.CAMPO_REFERENCIA,clie.getClie_referencia());
                values.put(ClienteTabla.CAMPO_FOTO,clie.getClie_rut_foto());
                values.put(ClienteTabla.CAMPO_TIPO,clie.getClie_tipo());

                db.update(ClienteTabla.TABLA,values,ClienteTabla.CAMPO_ID + " = ?",new String[] {clie.getClie_id()});

                break;

            case "marca":
                Marca mar = (Marca)ObjParam;

                values = new ContentValues();
                values.put(MarcaTabla.CAMPO_DESCRI,mar.getMar_descri());

                db.update(MarcaTabla.TABLA,values,MarcaTabla.CAMPO_ID + " = ?",new String[] {mar.getMar_id()});

                break;

            case "tipo":

                Tipo tip = (Tipo)ObjParam;

                values = new ContentValues();
                values.put(TipoTabla.CAMPO_DESCRI,tip.getTip_descri());

                db.update(TipoTabla.TABLA,values,TipoTabla.CAMPO_ID + " = ?",new String[] {tip.getTip_id()});

                break;

            case "tipo_movimiento":

                TipoMovimiento tipMov = (TipoMovimiento)ObjParam;

                values = new ContentValues();
                values.put(TipoMovimientoTabla.CAMPO_DESCRI,tipMov.getTip_mov_descri());
                values.put(TipoMovimientoTabla.CAMPO_ING_SAL,tipMov.getTip_mov_ing_sal());

                db.update(TipoMovimientoTabla.TABLA,values,TipoMovimientoTabla.CAMPO_ID + " = ?",new String[] {tipMov.getTip_mov_id()});

                break;

            case "producto":

                Producto prod = (Producto)ObjParam;

                ContentValues values = new ContentValues();
                values.put(ProductoTabla.CAMPO_DESCRI,prod.getProd_descri());
                values.put(ProductoTabla.CAMPO_PRECIO,prod.getProd_precio());
                values.put(ProductoTabla.MAR_ID,prod.getMar_id());
                values.put(ProductoTabla.TIP_ID,prod.getTip_id());
                values.put(ProductoTabla.CAMPO_RUTA_FOTO,prod.getRutaFoto());

                db.update(ProductoTabla.TABLA,values,ProductoTabla.CAMPO_ID + " = ?",new String[] {prod.getProd_id()});

                break;
        }

    }

}
