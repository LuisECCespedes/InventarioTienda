package com.example.luis.tiendacontrol.data.modelo;

import com.example.luis.tiendacontrol.data.util.Metodos;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by luis on 13/12/2017.
 */

public class Marca implements Serializable {
    private String mar_id;
    private String mar_descri;

    public String getMar_id() {
        return mar_id;
    }

    public void setMar_id(String mar_id) {
        this.mar_id = mar_id;
    }

    public String getMar_descri() {
        return mar_descri;
    }

    public void setMar_descri(String mar_descri) {
        this.mar_descri = mar_descri;
    }

    public Marca(String mar_id, String mar_descri) {

        this.mar_id = mar_id;
        this.mar_descri = mar_descri;
    }

    public Marca() {

    }
    public String Componer(String caracter)
    {
        return Metodos.CadenasComponer(caracter,new String[]{mar_id,mar_descri});
    }

    public Marca Marca(String cadena, String caracter)
    {
        return new Marca(  Metodos.CadenasDescomponer(cadena,1,caracter),
                Metodos.CadenasDescomponer(cadena,2,caracter));
    }
}