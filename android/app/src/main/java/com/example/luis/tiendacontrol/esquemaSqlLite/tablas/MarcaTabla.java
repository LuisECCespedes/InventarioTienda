package com.example.luis.tiendacontrol.esquemaSqlLite.tablas;

/**
 * Created by luis on 13/12/2017.
 */

public class MarcaTabla{

    // contantes que definene el nombre de tabla y columna
    public static final String TABLA = "marca";
    public static final String CAMPO_ID = "mar_id";
    public static final String CAMPO_DESCRI = "mar_descri";

    //creacion de tabla
    public static final String CREAR_TABLA_MARCA
            = "CREATE TABLE " + TABLA + "("
            + CAMPO_ID + " INT ,"
            + CAMPO_DESCRI + " TEXT "
            + ")";

    //eliminar la tabla si existe
    public static final String ELIMINAR_TABLA_MARCA = "DROP TABLE IF EXISTS" + TABLA + ";";
}
