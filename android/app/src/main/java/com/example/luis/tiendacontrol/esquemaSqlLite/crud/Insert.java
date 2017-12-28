package com.example.luis.tiendacontrol.esquemaSqlLite.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.luis.tiendacontrol.data.modelo.*;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;
import com.example.luis.tiendacontrol.data.util.Mensaje;
import com.example.luis.tiendacontrol.esquemaSqlLite.ConexionSqliteHelper;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.*;
/**
 * Created by luis on 13/12/2017.
 */

public class Insert {

    private static SQLiteDatabase db = null;
    private static ConexionSqliteHelper con = null;
    private static ContentValues values = null;

    public  static void RegistrarRegistro(Context context,Object ObjParam, String cTabla)
    {
        con = new ConexionSqliteHelper(context);

        db =con.getWritableDatabase();

        // segun el caso se acondiciona
        switch (cTabla)
        {
            case "cliente":
                Cliente clie = (Cliente)ObjParam;

                values = new ContentValues();
                values.put(ClienteTabla.CAMPO_ID,clie.getClie_id());
                values.put(ClienteTabla.CAMPO_NOMBRE,clie.getClie_nombre());
                values.put(ClienteTabla.CAMPO_NUM_TEL,clie.getClie_num_tel());
                values.put(ClienteTabla.CAMPO_REFERENCIA,clie.getClie_referencia());
                values.put(ClienteTabla.CAMPO_FOTO,clie.getClie_rut_foto());
                values.put(ClienteTabla.CAMPO_TIPO,clie.getClie_tipo());

                db.insert(ClienteTabla.TABLA,ClienteTabla.CAMPO_ID,values);
                SessionPreferences.get(context).SetCliente(clie.getClie_id());
                break;

            case "marca":
                Marca mar = (Marca)ObjParam;

                values = new ContentValues();
                values.put(MarcaTabla.CAMPO_ID,mar.getMar_id());
                values.put(MarcaTabla.CAMPO_DESCRI,mar.getMar_descri());

                db.insert(MarcaTabla.TABLA,MarcaTabla.CAMPO_ID,values);
                SessionPreferences.get(context).SetMarca(mar.getMar_id());
                break;

            case "tipo":

                Tipo tip = (Tipo)ObjParam;

                values = new ContentValues();
                values.put(TipoTabla.CAMPO_ID,tip.getTip_id());
                values.put(TipoTabla.CAMPO_DESCRI,tip.getTip_descri());

                db.insert(TipoTabla.TABLA,TipoTabla.CAMPO_ID,values);
                SessionPreferences.get(context).SetTipo(tip.getTip_id());
                break;

            case "tipo_movimiento":

                TipoMovimiento tipMov = (TipoMovimiento)ObjParam;

                values = new ContentValues();
                values.put(TipoMovimientoTabla.CAMPO_ID,tipMov.getTip_mov_id());
                values.put(TipoMovimientoTabla.CAMPO_DESCRI,tipMov.getTip_mov_descri());
                values.put(TipoMovimientoTabla.CAMPO_ING_SAL,tipMov.getTip_mov_ing_sal());

                db.insert(TipoMovimientoTabla.TABLA,TipoMovimientoTabla.CAMPO_ID,values);
                SessionPreferences.get(context).SetTipoMovimiento(tipMov.getTip_mov_id());
                break;

            case "producto":

                Producto prod = (Producto)ObjParam;

                values = new ContentValues();
                values.put(ProductoTabla.CAMPO_ID,prod.getProd_id());
                values.put(ProductoTabla.CAMPO_DESCRI,prod.getProd_descri());
                values.put(ProductoTabla.CAMPO_PRECIO,prod.getProd_precio());
                values.put(ProductoTabla.MAR_ID,prod.getMar_id());
                values.put(ProductoTabla.TIP_ID,prod.getTip_id());
                values.put(ProductoTabla.CAMPO_RUTA_FOTO,prod.getRutaFoto());

                db.insert(ProductoTabla.TABLA,ProductoTabla.CAMPO_ID,values);
                SessionPreferences.get(context).SetProducto(prod.getProd_id());
                break;


            case "kardex":

                Kardex kar = (Kardex)ObjParam;

                values = new ContentValues();
                values.put(KardexTabla.CAMPO_ID,kar.getKar_id());
                values.put(KardexTabla.CLIE_NOMBRE,kar.getClie_nombre());
                values.put(KardexTabla.PROD_ID,kar.getProd_id());
                values.put(KardexTabla.PROD_PRECIO,kar.getProd_precio());
                values.put(KardexTabla.CAMPO_CANT,kar.getProd_cant());
                values.put(KardexTabla.CAMPO_OBS,kar.getKar_obs());
                values.put(KardexTabla.CAMPO_FE,kar.getKar_fe());
                values.put(KardexTabla.CAMPO_HO,kar.getKar_ho());
                values.put(KardexTabla.TIP_MOV_ING_SAL,kar.getTip_mov_ing_sal());
                values.put(KardexTabla.TIP_MOV_DESCRI,kar.getTip_mov_descri());

                db.insert(KardexTabla.TABLA,KardexTabla.CAMPO_ID,values);
                SessionPreferences.get(context).SetKardex(kar.getKar_id());
                break;
        }
    }
}
