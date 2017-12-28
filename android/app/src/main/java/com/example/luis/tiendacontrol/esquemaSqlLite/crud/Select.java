package com.example.luis.tiendacontrol.esquemaSqlLite.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.luis.tiendacontrol.data.modelo.*;
import com.example.luis.tiendacontrol.data.util.Mensaje;
import com.example.luis.tiendacontrol.data.util.Metodos;
import com.example.luis.tiendacontrol.esquemaSqlLite.ConexionSqliteHelper;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

/**
 * Created by luis on 13/12/2017.
 */

public class Select {
    private static SQLiteDatabase db = null;
    private static ConexionSqliteHelper con = null;
    private static ContentValues values = null;

    private static List<Object> ListaREgistros(Context context,String cTabla) {
        List<Object> listaObjectos = new ArrayList<>();

        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        //cursor con los datos
        Cursor cLista = db.query(cTabla.equals("cliente") ? ClienteTabla.TABLA :
                        cTabla.equals("marca") ? MarcaTabla.TABLA :
                                cTabla.equals("tipo") ? TipoTabla.TABLA :
                                        cTabla.equals("producto") ? ProductoTabla.TABLA :
                                                cTabla.equals("kardex") ? KardexTabla.TABLA : TipoMovimientoTabla.TABLA
                , null, null, null, null, null, null);

    // recorrer y cargar la lista
        while (cLista.moveToNext())
        {
            // segun el caso se acondiciona
            switch (cTabla) {
                case "cliente":
                    listaObjectos.add(
                            new Cliente(
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_ID)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_NOMBRE)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_NUM_TEL)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_REFERENCIA)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_FOTO)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_TIPO))
                            )
                    );
                    break;

                case "marca":

                    listaObjectos.add(
                            new Marca(
                                    cLista.getString(cLista.getColumnIndex(MarcaTabla.CAMPO_ID)),
                                    cLista.getString(cLista.getColumnIndex(MarcaTabla.CAMPO_DESCRI))
                            )

                    );

                    break;

                case "tipo":
                    listaObjectos.add(
                            new Tipo(
                                    cLista.getString(cLista.getColumnIndex(TipoTabla.CAMPO_ID)),
                                    cLista.getString(cLista.getColumnIndex(TipoTabla.CAMPO_DESCRI))
                            )
                    );
                    break;

                case "tipo_movimiento":
                    listaObjectos.add(
                            new TipoMovimiento(
                                    cLista.getString(cLista.getColumnIndex(TipoMovimientoTabla.CAMPO_ID)),
                                    cLista.getString(cLista.getColumnIndex(TipoMovimientoTabla.CAMPO_DESCRI)),
                                    cLista.getString(cLista.getColumnIndex(TipoMovimientoTabla.CAMPO_ING_SAL))
                            )
                    );
                    break;

                case "producto":
                    listaObjectos.add(
                            new Producto(
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_ID)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_DESCRI)),
                                    cLista.getDouble(cLista.getColumnIndex(ProductoTabla.CAMPO_PRECIO)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.MAR_ID)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.TIP_ID)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_RUTA_FOTO))
                            )
                    );

                    break;

                case "kardex":

                    listaObjectos.add(
                            new Kardex(
                                    cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_ID)),
                                    cLista.getString(cLista.getColumnIndex(KardexTabla.CLIE_NOMBRE)),
                                    cLista.getString(cLista.getColumnIndex(KardexTabla.PROD_ID)),
                                    cLista.getDouble(cLista.getColumnIndex(KardexTabla.PROD_PRECIO)),
                                    cLista.getInt(cLista.getColumnIndex(KardexTabla.CAMPO_CANT)),
                                    cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_OBS)),
                                    cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_FE)),
                                    cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_HO)),
                                    cLista.getString(cLista.getColumnIndex(KardexTabla.TIP_MOV_ING_SAL)),
                                    cLista.getString(cLista.getColumnIndex(KardexTabla.TIP_MOV_DESCRI))
                            )
                    );

                    break;
            }
        }
        return listaObjectos;
    }

    public static Object BuscaRegistro(Context context,String codigo,String cTabla)
    {
        Object objCarga = null;
        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        String column = cTabla.equals("cliente") ? ClienteTabla.CAMPO_ID :
                cTabla.equals("marca") ? MarcaTabla.CAMPO_ID:
                        cTabla.equals("tipo") ? TipoTabla.CAMPO_ID:
                                cTabla.equals("producto") ? ProductoTabla.CAMPO_ID:
                                        cTabla.equals("kardex") ? KardexTabla.CAMPO_ID: TipoMovimientoTabla.CAMPO_ID;

        Cursor cLista = db.rawQuery("SELECT * FROM "+cTabla+" WHERE "+ column + " = ?", new String[] {codigo});
        //posicionar al primer registro
        cLista.moveToFirst();

        // segun el caso se acondiciona
        switch (cTabla) {
            case "cliente":
                objCarga =  new Cliente(
                                cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_ID)),
                                cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_NOMBRE)),
                                cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_NUM_TEL)),
                                cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_REFERENCIA)),
                                cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_FOTO)),
                                cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_TIPO)));
                break;

            case "marca":
                objCarga =  new Marca(
                                cLista.getString(cLista.getColumnIndexOrThrow(MarcaTabla.CAMPO_ID)),
                                cLista.getString(cLista.getColumnIndexOrThrow(MarcaTabla.CAMPO_DESCRI))    );
                break;

            case "tipo":
                objCarga =  new Tipo(
                                cLista.getString(cLista.getColumnIndexOrThrow(TipoTabla.CAMPO_ID)),
                                cLista.getString(cLista.getColumnIndexOrThrow(TipoTabla.CAMPO_DESCRI))     );
                break;

            case "tipo_movimiento":
                objCarga =  new TipoMovimiento(
                                cLista.getString(cLista.getColumnIndex(TipoMovimientoTabla.CAMPO_ID)),
                                cLista.getString(cLista.getColumnIndex(TipoMovimientoTabla.CAMPO_DESCRI)),
                                cLista.getString(cLista.getColumnIndex(TipoMovimientoTabla.CAMPO_ING_SAL))  );
                break;

            case "producto":
                objCarga =  new Producto(
                                cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_ID)),
                                cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_DESCRI)),
                                cLista.getDouble(cLista.getColumnIndex(ProductoTabla.CAMPO_PRECIO)),
                                cLista.getString(cLista.getColumnIndex(ProductoTabla.MAR_ID)),
                                cLista.getString(cLista.getColumnIndex(ProductoTabla.TIP_ID)),
                                cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_RUTA_FOTO))  );

                break;

            case "kardex":

                objCarga =  new Kardex(
                                cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_ID)),
                                cLista.getString(cLista.getColumnIndex(KardexTabla.CLIE_NOMBRE)),
                                cLista.getString(cLista.getColumnIndex(KardexTabla.PROD_ID)),
                                cLista.getDouble(cLista.getColumnIndex(KardexTabla.PROD_PRECIO)),
                                cLista.getInt(cLista.getColumnIndex(KardexTabla.CAMPO_CANT)),
                                cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_OBS)),
                                cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_FE)),
                                cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_HO)),
                                cLista.getString(cLista.getColumnIndex(KardexTabla.TIP_MOV_ING_SAL)),
                                cLista.getString(cLista.getColumnIndex(KardexTabla.TIP_MOV_DESCRI))         );

                break;
        }
        return objCarga;
    }

    //Clientes
    public static List<Cliente> SelectCliente(Context context, List<Cliente> lista)
    {
        List<Object> obj = Select.ListaREgistros(context,ClienteTabla.TABLA);
        //recorrido y descarga
        for (Object item : obj) {
            lista.add((Cliente)item);
        }
        return lista;
    }
    //Clientes
    public static List<Cliente> SelectCliente(Context context, List<Cliente> lista,String tipo)
    {
        List<Object> obj = Select.ListaREgistros(context,ClienteTabla.TABLA);
        //recorrido y descarga
        for (Object item : obj) {

            Cliente _item = (Cliente)item;
            if (_item.getClie_tipo().toString().equals(tipo)) {
                lista.add(_item);
            }
        }
        return lista;
    }
    //Tipo
    public static List<Tipo> SelectTipo(Context context, List<Tipo> lista)
    {
        List<Object> obj = Select.ListaREgistros(context,TipoTabla.TABLA);
        //recorrido y descarga
        for (Object item : obj) {
            lista.add((Tipo) item);
        }
        return lista;
    }
    //Marca
    public static List<Marca> SelectMarca(Context context, List<Marca> lista)
    {
        List<Object> obj = Select.ListaREgistros(context,MarcaTabla.TABLA);
        //recorrido y descarga
        for (Object item : obj) {
            lista.add((Marca) item);
        }
        return lista;
    }
    //Tipo Movimiento
    public static List<TipoMovimiento> SelectTipoMovimiento(Context context, List<TipoMovimiento> lista)
    {
        List<Object> obj = Select.ListaREgistros(context,TipoMovimientoTabla.TABLA);
        //recorrido y descarga
        for (Object item : obj) {
            lista.add((TipoMovimiento) item);
        }
        return lista;
    }
    //Tipo Movimiento
    public static List<TipoMovimiento> SelectTipoMovimiento(Context context, List<TipoMovimiento> lista,String ctipo)
    {
        List<Object> obj = Select.ListaREgistros(context,TipoMovimientoTabla.TABLA);
        String v ="";
        //recorrido y descarga
        for (Object item : obj) {
            //ctipo
            TipoMovimiento _item = (TipoMovimiento) item;
            if (_item.getTip_mov_ing_sal().equals(ctipo)) {
                lista.add(_item);
            }
            v+=_item.getTip_mov_descri();
        }
        return lista;
    }
    public static List<Kardex> ListaRegistrosKardex(Context context,List<Kardex> lista,String codProducto,String fecDesde,String fecHasta) {
        List<Object> listaObjectos = new ArrayList<>();

        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        //cursor con los datos
        Cursor cLista;
        String query = Metodos.Concatenar(new Object[]{"select * from ", KardexTabla.TABLA
                ," where " , KardexTabla.PROD_ID , " =? " , "and " , KardexTabla.CAMPO_FE
                , " between ? and ? " ," order by " + KardexTabla.CAMPO_FE , " desc "});

        cLista = db.rawQuery(query, new String[]{codProducto, fecDesde, fecHasta });


        // recorrer y cargar la lista
        while (cLista.moveToNext())
        {   listaObjectos.add(  new Kardex(
                        cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_ID)),
                        cLista.getString(cLista.getColumnIndex(KardexTabla.CLIE_NOMBRE)),
                        cLista.getString(cLista.getColumnIndex(KardexTabla.PROD_ID)),
                        cLista.getDouble(cLista.getColumnIndex(KardexTabla.PROD_PRECIO)),
                        cLista.getInt(cLista.getColumnIndex(KardexTabla.CAMPO_CANT)),
                        cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_OBS)),
                        cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_FE)),
                        cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_HO)),
                        cLista.getString(cLista.getColumnIndex(KardexTabla.TIP_MOV_ING_SAL)),
                        cLista.getString(cLista.getColumnIndex(KardexTabla.TIP_MOV_DESCRI))
                )
        );
        }
        //recorrido y descarga
        for (Object item : listaObjectos) {
            lista.add((Kardex) item);
        }
        return lista;
    }

    //productos con filtro
    public static List<Producto> ListaRegistrosProducto(Context context,List<Producto> lista,String codigoMarca,String codigoTipo) {
        List<Object> listaObjectos = new ArrayList<>();

        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        //cursor con los datos
        Cursor cLista;
        if (codigoMarca.equals("1") && codigoTipo.equals("1")) {
            cLista = db.query(ProductoTabla.TABLA, null, null, null, null, null, null);
        } else {

            if (!codigoMarca.equals("1") && !codigoTipo.equals("1")){
                cLista = db.query(ProductoTabla.TABLA
                        ,null,ProductoTabla.MAR_ID + " = ? and " + ProductoTabla.TIP_ID + " = ?",new String[]{codigoMarca,codigoTipo},null,null,null);

            } else if (!codigoTipo.equals("1")){
                cLista = db.query(ProductoTabla.TABLA
                        ,null,ProductoTabla.TIP_ID + " = ?" ,new String[]{codigoTipo},null,null,null);

            }else {
                cLista = db.query(ProductoTabla.TABLA
                        ,null,ProductoTabla.MAR_ID + " = ?",new String[]{codigoMarca},null,null,null);
            }
        }

        // recorrer y cargar la lista
        while (cLista.moveToNext())
        {   listaObjectos.add(  new Producto(
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_ID)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_DESCRI)),
                                    cLista.getDouble(cLista.getColumnIndex(ProductoTabla.CAMPO_PRECIO)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.MAR_ID)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.TIP_ID)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_RUTA_FOTO))
                            )
                    );
        }
        //recorrido y descarga
        for (Object item : listaObjectos) {
            lista.add((Producto) item);
        }
        return lista;
    }

    //productos con filtro
    public static List<Inventario> ListaKardexProducto(Context context,List<Inventario> lista,String codigoMarca,String codigoTipo) {
        List<Object> listaObjectos = new ArrayList<>();

        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        //cursor con los datos
        Cursor cLista;
        if (codigoMarca.equals("1") && codigoTipo.equals("1")) {
            String query = Metodos.Concatenar(new Object[]{"select sum(", KardexTabla.CAMPO_CANT , ") as " , KardexTabla.CAMPO_CANT , " , ", KardexTabla.PROD_ID , " " , " from ", KardexTabla.TABLA
                    ," group by " , KardexTabla.PROD_ID , " order by " + KardexTabla.CAMPO_FE , " desc ", " limit 10"});

            cLista = db.rawQuery(query, null);

        } else {

            if (!codigoMarca.equals("1") && !codigoTipo.equals("1")){
                cLista = db.query(ProductoTabla.TABLA
                        ,null,ProductoTabla.MAR_ID + " = ? and " + ProductoTabla.TIP_ID + " = ?",new String[]{codigoMarca,codigoTipo},null,null,null);

            } else if (!codigoTipo.equals("1")){
                cLista = db.query(ProductoTabla.TABLA
                        ,null,ProductoTabla.TIP_ID + " = ?" ,new String[]{codigoTipo},null,null,null);

            }else {
                cLista = db.query(ProductoTabla.TABLA
                        ,null,ProductoTabla.MAR_ID + " = ?",new String[]{codigoMarca},null,null,null);
            }
        }
        // si es
        if (codigoMarca.equals("1") && codigoTipo.equals("1")) {
            while (cLista.moveToNext()) {
                String codigo = cLista.getString(cLista.getColumnIndex(KardexTabla.PROD_ID));

                int cantidad = ProductoStock(codigo);
                Double estimado = ProductoDineroEstimado(codigo);

                Producto itemProd = (Producto)BuscaRegistro(context,codigo,ProductoTabla.TABLA);

                listaObjectos.add(new Inventario(itemProd.getProd_id(), itemProd.getProd_descri(), estimado, "1", "1", cantidad +""));
            }
        }
        else {
            // recorrer y cargar la lista
            while (cLista.moveToNext()) {
                Producto itemProd = new Producto(
                        cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_ID)),
                        cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_DESCRI)),
                        cLista.getDouble(cLista.getColumnIndex(ProductoTabla.CAMPO_PRECIO)),
                        cLista.getString(cLista.getColumnIndex(ProductoTabla.MAR_ID)),
                        cLista.getString(cLista.getColumnIndex(ProductoTabla.TIP_ID)),
                        cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_RUTA_FOTO)));

                int cantidad = ProductoStock(itemProd.getProd_id());
                Double estimado = ProductoDineroEstimado(itemProd.getProd_id());

                listaObjectos.add(new Inventario(itemProd.getProd_id(), itemProd.getProd_descri(), estimado, "1", "1", cantidad + ""));
            }
        }
        //recorrido y descarga
        for (Object item : listaObjectos) {
            lista.add((Inventario) item);
        }
        return lista;
    }

    // Movimientos realizados
    public static List<Inventario> ListaKardexProductoMomientos(Context context,List<Inventario> lista,String codigoMarca,String codigoTipo,String fecDesde,String fecHasta) {
        List<Object> listaObjectos = new ArrayList<>();

        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        //cursor con los datos
        Cursor cLista;
        if (codigoMarca.equals("1") && codigoTipo.equals("1")) {
            String query = Metodos.Concatenar(new Object[]{"select sum(", KardexTabla.CAMPO_CANT , ") as " , KardexTabla.CAMPO_CANT , " , ", KardexTabla.PROD_ID , " " , " from ", KardexTabla.TABLA
                    ," group by " , KardexTabla.PROD_ID , " order by " + KardexTabla.CAMPO_FE , " desc ", " limit 10"});

            cLista = db.rawQuery(query, null);

        } else {

            if (!codigoMarca.equals("1") && !codigoTipo.equals("1")){
                cLista = db.query(ProductoTabla.TABLA
                        ,null,ProductoTabla.MAR_ID + " = ? and " + ProductoTabla.TIP_ID + " = ?",new String[]{codigoMarca,codigoTipo},null,null,null);

            } else if (!codigoTipo.equals("1")){
                cLista = db.query(ProductoTabla.TABLA
                        ,null,ProductoTabla.TIP_ID + " = ?" ,new String[]{codigoTipo},null,null,null);

            }else {
                cLista = db.query(ProductoTabla.TABLA
                        ,null,ProductoTabla.MAR_ID + " = ?",new String[]{codigoMarca},null,null,null);
            }
        }
        // si es
        if (codigoMarca.equals("1") && codigoTipo.equals("1")) {
            while (cLista.moveToNext()) {
                String codigo = cLista.getString(cLista.getColumnIndex(KardexTabla.PROD_ID));

                int cantidad = ProductoStock(codigo);
                Double estimado = ProductoDineroEstimado(codigo);

                Producto itemProd = (Producto)BuscaRegistro(context,codigo,ProductoTabla.TABLA);

                listaObjectos.add(new Inventario(itemProd.getProd_id(), itemProd.getProd_descri(), estimado, "1", "1", cantidad +""));
            }
        }
        else {
            // recorrer y cargar la lista
            while (cLista.moveToNext()) {
                Producto itemProd = new Producto(
                        cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_ID)),
                        cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_DESCRI)),
                        cLista.getDouble(cLista.getColumnIndex(ProductoTabla.CAMPO_PRECIO)),
                        cLista.getString(cLista.getColumnIndex(ProductoTabla.MAR_ID)),
                        cLista.getString(cLista.getColumnIndex(ProductoTabla.TIP_ID)),
                        cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_RUTA_FOTO)));

                int cantidad = ProductoStock(itemProd.getProd_id());
                Double estimado = ProductoDineroEstimado(itemProd.getProd_id());

                listaObjectos.add(new Inventario(itemProd.getProd_id(), itemProd.getProd_descri(), estimado, "1", "1", cantidad + ""));
            }
        }
        //recorrido y descarga
        for (Object item : listaObjectos) {
            lista.add((Inventario) item);
        }
        return lista;
    }

    public static int ProductoIngreso(String codigo)
    {
        // ingreso
        String query = Metodos.Concatenar(new Object[]{"select sum(", KardexTabla.CAMPO_CANT , ") as " , KardexTabla.CAMPO_CANT , " from ", KardexTabla.TABLA
                ," where " , KardexTabla.PROD_ID , " =? and " , KardexTabla.TIP_MOV_ING_SAL , " =? "});

        Cursor cIngreso = db.rawQuery(query, new String[]{codigo,"I"});

        cIngreso.moveToFirst();

        return cIngreso.getInt(cIngreso.getColumnIndex(KardexTabla.CAMPO_CANT));
    }

    public static int ProductoSalida(String codigo)
    {
        // ingreso
        String query = Metodos.Concatenar(new Object[]{"select sum(", KardexTabla.CAMPO_CANT , ") as " , KardexTabla.CAMPO_CANT , " from ", KardexTabla.TABLA
                ," where " , KardexTabla.PROD_ID , " =? and " , KardexTabla.TIP_MOV_ING_SAL , " =? "});

        Cursor cSalida = db.rawQuery(query, new String[]{codigo,"S"});
        cSalida.moveToFirst();

        return cSalida.getInt(cSalida.getColumnIndex(KardexTabla.CAMPO_CANT));
    }

    public static int ProductoStock(String codigo)
    {
        return ProductoIngreso(codigo) - ProductoSalida(codigo);
    }

    public static Double ProductoIngresoSoles(String codigo)
    {
        // ingreso
        String query = Metodos.Concatenar(new Object[]{"select sum(", KardexTabla.PROD_PRECIO ," * ", KardexTabla.CAMPO_CANT, ") as " , KardexTabla.PROD_PRECIO , " from ", KardexTabla.TABLA
                ," where " , KardexTabla.PROD_ID , " =? and " , KardexTabla.TIP_MOV_ING_SAL , " =? "});

        Cursor cIngreso = db.rawQuery(query, new String[]{codigo,"I"});

        cIngreso.moveToFirst();
        return cIngreso.getDouble(cIngreso.getColumnIndex(KardexTabla.PROD_PRECIO));
    }

    public static Double ProductoSalidaSoles(String codigo)
    {
        // ingreso
        String query = Metodos.Concatenar(new Object[]{"select sum(", KardexTabla.PROD_PRECIO ," * ", KardexTabla.CAMPO_CANT, ") as " , KardexTabla.PROD_PRECIO , " from ", KardexTabla.TABLA
                ," where " , KardexTabla.PROD_ID , " =? and " , KardexTabla.TIP_MOV_ING_SAL , " =? "});

        Cursor cSalida = db.rawQuery(query, new String[]{codigo,"S"});
        cSalida.moveToFirst();

        return cSalida.getDouble(cSalida.getColumnIndex(KardexTabla.PROD_PRECIO));
    }

    public static Double ProductoDineroEstimado(String codigo)
    {
        return ProductoIngresoSoles(codigo) - ProductoSalidaSoles(codigo);
    }


    public static void Backup(Context context) {
        String compresion = "";
        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        for (String tabla : new String[] {  ClienteTabla.TABLA , MarcaTabla.TABLA ,  TipoTabla.TABLA ,
                                            ProductoTabla.TABLA ,  KardexTabla.TABLA , TipoMovimientoTabla.TABLA}) {

            compresion += "/////" + tabla.toUpperCase() +"////"+ "\n";
            //cursor con los datos
            Cursor cLista = db.query(tabla, null, null, null, null, null, null);

            // recorrer y cargar la lista
            while (cLista.moveToNext())
            {
                // segun el caso se acondiciona
                switch (tabla) {
                    case "cliente":
                        compresion += new Cliente(
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_ID)),
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_NOMBRE)),
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_NUM_TEL)),
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_REFERENCIA)),
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_FOTO)),
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CAMPO_TIPO))
                            ).Componer("|") + "\n";

                        break;

                    case "marca":

                        compresion += new Marca(
                                        cLista.getString(cLista.getColumnIndex(MarcaTabla.CAMPO_ID)),
                                        cLista.getString(cLista.getColumnIndex(MarcaTabla.CAMPO_DESCRI))
                        ).Componer("|") + "\n";
                        break;

                    case "tipo":
                        compresion += new Tipo(
                                        cLista.getString(cLista.getColumnIndex(TipoTabla.CAMPO_ID)),
                                        cLista.getString(cLista.getColumnIndex(TipoTabla.CAMPO_DESCRI))
                                ).Componer("|") + "\n";
                        break;

                    case "tipo_movimiento":

                        compresion += new TipoMovimiento(
                                        cLista.getString(cLista.getColumnIndex(TipoMovimientoTabla.CAMPO_ID)),
                                        cLista.getString(cLista.getColumnIndex(TipoMovimientoTabla.CAMPO_DESCRI)),
                                        cLista.getString(cLista.getColumnIndex(TipoMovimientoTabla.CAMPO_ING_SAL))
                                ).Componer("|") + "\n";
                        break;

                    case "producto":
                        compresion += new Producto(
                                        cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_ID)),
                                        cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_DESCRI)),
                                        cLista.getDouble(cLista.getColumnIndex(ProductoTabla.CAMPO_PRECIO)),
                                        cLista.getString(cLista.getColumnIndex(ProductoTabla.MAR_ID)),
                                        cLista.getString(cLista.getColumnIndex(ProductoTabla.TIP_ID)),
                                        cLista.getString(cLista.getColumnIndex(ProductoTabla.CAMPO_RUTA_FOTO))
                                ).Componer("|") + "\n";
                        break;

                    case "kardex":
                        compresion += new Kardex(
                                        cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_ID)),
                                        cLista.getString(cLista.getColumnIndex(KardexTabla.CLIE_NOMBRE)),
                                        cLista.getString(cLista.getColumnIndex(KardexTabla.PROD_ID)),
                                        cLista.getDouble(cLista.getColumnIndex(KardexTabla.PROD_PRECIO)),
                                        cLista.getInt(cLista.getColumnIndex(KardexTabla.CAMPO_CANT)),
                                        cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_OBS)),
                                        cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_FE)),
                                        cLista.getString(cLista.getColumnIndex(KardexTabla.CAMPO_HO)),
                                        cLista.getString(cLista.getColumnIndex(KardexTabla.TIP_MOV_ING_SAL)),
                                        cLista.getString(cLista.getColumnIndex(KardexTabla.TIP_MOV_DESCRI))
                                ).Componer("|") + "\n";
                        break;
                }
            }
        }

        try {
            String nombre = "tienda.txt";
            File tarjeta = Environment.getExternalStorageDirectory();
            File file = new File(tarjeta.getAbsolutePath(),nombre);

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));
            osw.write(compresion);
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

