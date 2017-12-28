package com.example.luis.tiendacontrol.esquemaSqlLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.luis.tiendacontrol.data.modelo.TipoMovimiento;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.ClienteTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.KardexTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.MarcaTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.ProductoTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.TipoMovimientoTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.TipoTabla;

/**
 * Created by luis on 13/12/2017.
 */

public class ConexionSqliteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ventas.db";

    public ConexionSqliteHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(ProductoTabla.CREAR_TABLA_PRODUCTO);
        db.execSQL(ClienteTabla.CREAR_TABLA_CLIENTE);
        db.execSQL(KardexTabla.CREAR_TABLA_KARDEX);
        db.execSQL(MarcaTabla.CREAR_TABLA_MARCA);
        db.execSQL(TipoMovimientoTabla.CREAR_TABLA_TIPO_MOVIMIENTO);
        db.execSQL(TipoTabla.CREAR_TABLA_TIPO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL(ProductoTabla.ELIMINAR_TABLA_PRODUCTO);
        db.execSQL(ClienteTabla.ELIMINAR_TABLA_CLIENTE);
        db.execSQL(KardexTabla.ELIMINAR_TABLA_KARDEX);
        db.execSQL(MarcaTabla.ELIMINAR_TABLA_MARCA);
        db.execSQL(TipoMovimientoTabla.ELIMINAR_TABLA_TIPO_MOVIMIENTO);
        db.execSQL(TipoTabla.ELIMINAR_TABLA_TIPO);
        onCreate(db);
    }
}
