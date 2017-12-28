package com.example.luis.tiendacontrol.data.preferencia;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by luis on 12/12/2017.
 */

public class SessionPreferences {
    //region constantes Preferencias
    public static final String PREFS_NAME       = "DATA_SESSION";
    public static final String PREFS_CLAVE      = "PREFS_CLAVE";
    public static final String PREFS_LOGIN      = "PREFS_LOGIN";
    public static final String PREFS_PRODUCTO   = "PREFS_PRODUCTO";
    public static final String PREFS_CLIENTE    = "PREFS_CLIENTE";
    public static final String PREFS_MARCA      = "PREFS_MARCA";
    public static final String PREFS_TIPO       = "PREFS_TIPO";
    public static final String PREFS_TIPO_MOVIMIENTO= "PREFS_TIPO_MOVIMIENTO";
    public static final String PREFS_KARDEX     = "PREFS_KARDEX";
    public static final String PREFS_FONDO      = "PREFS_FONDO";
    public static final String PREF_USER_LETRA_SIZE = "PREF_USER_LETRA_SIZE";

    //endregion
    //region Base Preferencia
    // variable y constantes necesarias para el manejo de preferencia
    private final SharedPreferences mPrefs;
    private static SessionPreferences INSTANCE;
    private boolean mIsLoggedIn = false;

    public static SessionPreferences get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SessionPreferences(context);
        }
        return INSTANCE;
    }

    private SessionPreferences(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    //endregion

    //region codigo

    public void SetProducto(String productoCodigo)
     {
         SharedPreferences.Editor objEditor = mPrefs.edit();
         int num = Integer.parseInt(productoCodigo.toString()) + 1;
         objEditor.putString(PREFS_PRODUCTO,num+"");
         objEditor.apply();
     }
     public String GetProducto()
     {
         return mPrefs.getString(PREFS_PRODUCTO,"1");

     }

    public void SetCliente(String clienteCodigo)
     {
         SharedPreferences.Editor objEditor = mPrefs.edit();
         int num = Integer.parseInt(clienteCodigo.toString()) + 1;
         objEditor.putString(PREFS_CLIENTE,num+"");
         objEditor.apply();
     }
     public String GetCliente()
     {
         return mPrefs.getString(PREFS_CLIENTE,"1");

     }

    public void SetMarca(String marcaCodigo)
     {
         SharedPreferences.Editor objEditor = mPrefs.edit();
         int num = Integer.parseInt(marcaCodigo.toString()) + 1;
         objEditor.putString(PREFS_MARCA,num+"");
         objEditor.apply();
     }
     public String GetMarca()
     {
         return mPrefs.getString(PREFS_MARCA,"1");

     }

    public void SetTipo(String tipoCodigo)
     {
         SharedPreferences.Editor objEditor = mPrefs.edit();
         int num = Integer.parseInt(tipoCodigo.toString()) + 1;
         objEditor.putString(PREFS_TIPO,num+"");
         objEditor.apply();
     }
     public String GetTipo()
     {
         return mPrefs.getString(PREFS_TIPO,"1");

     }

    public void SetTipoMovimiento(String tipoMovimientoCodigo)
     {
         SharedPreferences.Editor objEditor = mPrefs.edit();
         int num = Integer.parseInt(tipoMovimientoCodigo.toString()) + 1;
         objEditor.putString(PREFS_TIPO_MOVIMIENTO,num+"");
         objEditor.apply();
     }
     public String GetTipoMovimiento()
     {
         return mPrefs.getString(PREFS_TIPO_MOVIMIENTO,"1");

     }

    public void SetKardex(String movimientoCodigo)
     {
         SharedPreferences.Editor objEditor = mPrefs.edit();
         int num = Integer.parseInt(movimientoCodigo.toString()) + 1;
         objEditor.putString(PREFS_KARDEX,num+"");
         objEditor.apply();
     }
     public String GetKardex()
     {
         return mPrefs.getString(PREFS_KARDEX,"1");

     }

    public void SetClave(String MovimientoCodigo)
     {
         SharedPreferences.Editor objEditor = mPrefs.edit();
         objEditor.putString(PREFS_CLAVE,MovimientoCodigo);
         objEditor.apply();
     }
     public String GetClave()
     {
         return mPrefs.getString(PREFS_CLAVE,"123");
         //return mPrefs.getString(PREFS_CLAVE,"cristina");

     }

    public void Session(Boolean abrirCerrar)
     {
         SharedPreferences.Editor objEditor = mPrefs.edit();
         objEditor.putBoolean(PREFS_LOGIN,abrirCerrar);
         objEditor.apply();
     }
     public Boolean GetSession()
     {
         return mPrefs.getBoolean("PREFS_LOGIN",false);

     }

    public void SetFondo(String FondoTipo)
    {
        SharedPreferences.Editor objEditor = mPrefs.edit();
        objEditor.putString(PREFS_FONDO,FondoTipo);
        objEditor.apply();
    }
    public String GetFondo()
    {
        return mPrefs.getString(PREFS_FONDO,"fondo_1");

    }


    public void setLetraSize(Float _size) {
        // este metodo se encargara de guardar el esquema de trabajo
        SharedPreferences.Editor objEditor = mPrefs.edit();
        objEditor.putFloat(PREF_USER_LETRA_SIZE,_size);
        objEditor.apply();
    }

    public Float getLetraSize() {
        //este metodo retornara
        return mPrefs.getFloat(PREF_USER_LETRA_SIZE, Float.parseFloat("35"));
    }
    //endregion
}
