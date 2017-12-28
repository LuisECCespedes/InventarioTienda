package com.example.luis.tiendacontrol.esquemaSqlLite.tablas;

/**
 * Created by luis on 13/12/2017.
 */

public class ClienteTabla{

    // contantes que definene el nombre de tabla y columna
    public static final String TABLA = "cliente";
    public static final String CAMPO_ID = "clie_id";
    public static final String CAMPO_NOMBRE = "clie_nombre";
    public static final String CAMPO_NUM_TEL = "clie_num_tel";
    public static final String CAMPO_REFERENCIA = "clie_referencia";
    public static final String CAMPO_FOTO = "clie_rut_foto";
    public static final String CAMPO_TIPO = "clie_tipo";

    //creacion de tabla
    public static final String CREAR_TABLA_CLIENTE
            = "CREATE TABLE " + TABLA + "("
            + CAMPO_ID + " INT ,"
            + CAMPO_NOMBRE + " TEXT ,"
            + CAMPO_NUM_TEL + " TEXT ,"
            + CAMPO_REFERENCIA + " TEXT ,"
            + CAMPO_FOTO + " TEXT ,"
            + CAMPO_TIPO + " TEXT "
            + ")";

    //eliminar la tabla si existe
    public static final String ELIMINAR_TABLA_CLIENTE = "DROP TABLE IF EXISTS" + TABLA + ";";

}
