package com.example.luis.tiendacontrol.data.modelo;

import com.example.luis.tiendacontrol.data.util.Metodos;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by luis on 13/12/2017.
 */

public class Cliente implements Serializable{
    private String clie_id;
    private String clie_nombre;
    private String clie_num_tel;
    private String clie_referencia;
    private String clie_rut_foto;
    private String clie_tipo;

    public String getClie_id() {
        return clie_id;
    }

    public void setClie_id(String clie_id) {
        this.clie_id = clie_id;
    }

    public String getClie_nombre() {
        return clie_nombre;
    }

    public void setClie_nombre(String clie_nombre) {
        this.clie_nombre = clie_nombre;
    }

    public String getClie_num_tel() {
        return clie_num_tel;
    }

    public void setClie_num_tel(String clie_num_tel) {
        this.clie_num_tel = clie_num_tel;
    }

    public String getClie_referencia() {
        return clie_referencia;
    }

    public void setClie_referencia(String clie_referencia) {
        this.clie_referencia = clie_referencia;
    }

    public String getClie_rut_foto() {
        return clie_rut_foto;
    }

    public void setClie_rut_foto(String clie_rut_foto) {
        this.clie_rut_foto = clie_rut_foto;
    }

    public String getClie_tipo() {
        return clie_tipo;
    }

    public void setClie_tipo(String clie_tipo) {
        this.clie_tipo = clie_tipo;
    }

    public Cliente(String clie_id, String clie_nombre, String clie_num_tel, String clie_referencia, String clie_rut_foto, String clie_tipo) {
        this.clie_id = clie_id;
        this.clie_nombre = clie_nombre;
        this.clie_num_tel = clie_num_tel;
        this.clie_referencia = clie_referencia;
        this.clie_rut_foto = clie_rut_foto;
        this.clie_tipo = clie_tipo;
    }

    public Cliente() {

    }

    public String Componer(String caracter)
    {
        return Metodos.CadenasComponer(caracter,new String[]{clie_id,clie_nombre,clie_num_tel,clie_referencia,clie_rut_foto,clie_tipo});
    }

    public Cliente Cliente(String cadena, String caracter)
    {
        return new Cliente(  Metodos.CadenasDescomponer(cadena,1,caracter),
                Metodos.CadenasDescomponer(cadena,2,caracter),
                Metodos.CadenasDescomponer(cadena,3,caracter),
                Metodos.CadenasDescomponer(cadena,4,caracter),
                Metodos.CadenasDescomponer(cadena,5,caracter),
                Metodos.CadenasDescomponer(cadena,6,caracter));
    }
}
