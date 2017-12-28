package com.example.luis.tiendacontrol.esquemaSqlLite.tablas;

/**
 * Created by luis on 13/12/2017.
 */

public class TipoMovimientoTabla {

    // contantes que definene el nombre de tabla y columna
    public static final String TABLA = "tipo_movimiento";
    public static final String CAMPO_ID = "tip_mov_id";
    public static final String CAMPO_DESCRI = "tip_mov_descri";
    public static final String CAMPO_ING_SAL = "tip_mov_ing_sal";

    //creacion de tabla
    public static final String CREAR_TABLA_TIPO_MOVIMIENTO
            = "CREATE TABLE " + TABLA + "("
            + CAMPO_ID + " INT ,"
            + CAMPO_DESCRI + " TEXT ,"
            + CAMPO_ING_SAL + " TEXT "
            + ")";

    public static final String ELIMINAR_TABLA_TIPO_MOVIMIENTO = "DROP TABLE IF EXISTS" + TABLA + ";";
}
