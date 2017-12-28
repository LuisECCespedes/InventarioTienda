package com.example.luis.tiendacontrol.esquemaSqlLite.tablas;

/**
 * Created by luis on 13/12/2017.
 */

public class KardexTabla {

    // contantes que definene el nombre de tabla y columna
    public static final String TABLA = "kardex";
    public static final String CAMPO_ID         = "kar_id";
    public static final String CLIE_NOMBRE      = "clie_nombre";
    public static final String PROD_ID          = "prod_id";
    public static final String PROD_PRECIO      = "prod_precio";
    public static final String CAMPO_CANT        = "kar_cant";
    public static final String CAMPO_OBS        = "kar_obs";
    public static final String CAMPO_FE         = "kar_fe";
    public static final String CAMPO_HO         = "kar_ho";
    public static final String TIP_MOV_ING_SAL  = "tip_mov_ing_sal";
    public static final String TIP_MOV_DESCRI   = "tip_mov_descri";

    //creacion de tabla
    public static final String CREAR_TABLA_KARDEX
            = "CREATE TABLE " + TABLA + "("
            + CAMPO_ID + " INT ,"
            + CLIE_NOMBRE + " TEXT ,"
            + PROD_ID + " TEXT ,"
            + PROD_PRECIO + " NUMERIC ,"
            + CAMPO_CANT + " INT ,"
            + CAMPO_OBS + " TEXT ,"
            + CAMPO_FE + " TEXT ,"
            + CAMPO_HO + " TEXT ,"
            + TIP_MOV_ING_SAL + " TEXT ,"
            + TIP_MOV_DESCRI + " TEXT "
            + ")";

    //eliminar la tabla si existe
    public static final String ELIMINAR_TABLA_KARDEX = "DROP TABLE IF EXISTS" + TABLA + ";";
}
