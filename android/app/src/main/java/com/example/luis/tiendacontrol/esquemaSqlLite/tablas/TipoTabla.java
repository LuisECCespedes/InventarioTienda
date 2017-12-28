package com.example.luis.tiendacontrol.esquemaSqlLite.tablas;

/**
 * Created by luis on 13/12/2017.
 */

public class TipoTabla {

    // contantes que definene el nombre de tabla y columna
    public static final String TABLA = "tipo";
    public static final String CAMPO_ID = "tip_id";
    public static final String CAMPO_DESCRI = "tip_descri";

    //creacion de tabla
    public static final String CREAR_TABLA_TIPO
            = "CREATE TABLE " + TABLA + "("
            + CAMPO_ID + " INT ,"
            + CAMPO_DESCRI + " TEXT "
            + ")";

    public static final String ELIMINAR_TABLA_TIPO = "DROP TABLE IF EXISTS" + TABLA + ";";
}
