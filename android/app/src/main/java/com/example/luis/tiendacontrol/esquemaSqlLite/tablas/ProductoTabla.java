package com.example.luis.tiendacontrol.esquemaSqlLite.tablas;

/**
 * Created by luis on 13/12/2017.
 */

public class ProductoTabla {

    // contantes que definene el nombre de tabla y columna
    public static final String TABLA = "producto";
    public static final String CAMPO_ID = "prod_id";
    public static final String CAMPO_DESCRI = "prod_descri";
    public static final String CAMPO_PRECIO = "prod_precio";
    public static final String MAR_ID = "mar_id";
    public static final String TIP_ID = "tip_id";
    public static final String CAMPO_RUTA_FOTO = "ruta_foto";

    //creacion de tabla
    public static final String CREAR_TABLA_PRODUCTO
            = "CREATE TABLE " + TABLA + "("
            + CAMPO_ID + " INT ,"
            + CAMPO_DESCRI + " TEXT ,"
            + CAMPO_PRECIO + " NUMERIC ,"
            + MAR_ID + " INT ,"
            + TIP_ID + " INT ,"
            + CAMPO_RUTA_FOTO + " TEXT "
            + ")";

    public static final String ELIMINAR_TABLA_PRODUCTO = "DROP TABLE IF EXISTS" + TABLA + ";";
}
