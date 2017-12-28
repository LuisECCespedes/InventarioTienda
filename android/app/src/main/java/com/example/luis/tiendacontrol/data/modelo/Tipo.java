package com.example.luis.tiendacontrol.data.modelo;

import com.example.luis.tiendacontrol.data.util.Metodos;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by luis on 13/12/2017.
 */

public class Tipo implements Serializable{
    private String tip_id;
    private String tip_descri;

    public String getTip_id() {
        return tip_id;
    }

    public void setTip_id(String tip_id) {
        this.tip_id = tip_id;
    }

    public String getTip_descri() {
        return tip_descri;
    }

    public void setTip_descri(String tip_descri) {
        this.tip_descri = tip_descri;
    }

    public Tipo(String tip_id, String tip_descri) {
        this.tip_id = tip_id;
        this.tip_descri = tip_descri;
    }

    public Tipo() {

    }
    public String Componer(String caracter)
    {
        return Metodos.CadenasComponer(caracter,new String[]{tip_id,tip_descri});
    }

    public Tipo Tipo(String cadena, String caracter)
    {
        return new Tipo(    Metodos.CadenasDescomponer(cadena,1,caracter),
                            Metodos.CadenasDescomponer(cadena,2,caracter));
    }
}
