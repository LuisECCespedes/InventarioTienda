package com.example.luis.tiendacontrol.data.util;

import android.content.Intent;

/**
 * Created by luis on 15/12/2017.
 */

public class Metodos {
    public  static String CadenasComponer(String caracter ,Object[] lista)
    {
        String cadena = "";
        if (!caracter.isEmpty())
        {
            for (Object ItemLista: lista) {
                cadena += ItemLista.toString() + caracter;
            }
        }
        return cadena.substring(0,cadena.length()-1);
    }
    public static String Concatenar(Object[] lista)
    {
        String cadena = "";
        for (Object ItemLista: lista) {
            cadena += ItemLista.toString();
        }
        return cadena;
    }
    public  static String CadenasDescomponer(String cadena ,int posicion,String caracter)
    {   //cadena
        String cadenaRetorno = cadena;
        // si se pide una posicion mas
        if (posicion > 1)
        {   //recorrido para quitar los demsa
            for (int i = 0; i<posicion-1; i++)
            {   // desconposicion
                cadenaRetorno = cadenaRetorno.substring(cadenaRetorno.indexOf(caracter)+1);
            }
        }//cuando encuentre el caracter
        if (cadenaRetorno.indexOf(caracter) > 0)
        {   //posicion
            cadenaRetorno = cadenaRetorno.substring(0,cadenaRetorno.indexOf(caracter));
        }
        return cadenaRetorno;
    }
}
