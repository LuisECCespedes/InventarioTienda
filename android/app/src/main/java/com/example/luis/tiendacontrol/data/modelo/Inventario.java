package com.example.luis.tiendacontrol.data.modelo;

import com.example.luis.tiendacontrol.data.util.Metodos;

import java.io.Serializable;

/**
 * Created by luis on 17/12/2017.
 */

public class Inventario implements Serializable {
    private String prod_id ;
    private String prod_descri ;
    private Double inv_tot_soles ;
    private String inv_entrada;
    private String inv_salida;
    private String inv_cantidad;

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_descri() {
        return prod_descri;
    }

    public void setProd_descri(String prod_descri) {
        this.prod_descri = prod_descri;
    }

    public Double getInv_tot_soles() {
        return inv_tot_soles;
    }

    public void setInv_tot_soles(Double inv_tot_soles) {
        this.inv_tot_soles = inv_tot_soles;
    }

    public String getInv_entrada() {
        return inv_entrada;
    }

    public void setInv_entrada(String inv_entrada) {
        this.inv_entrada = inv_entrada;
    }

    public String getInv_salida() {
        return inv_salida;
    }

    public void setInv_salida(String inv_salida) {
        this.inv_salida = inv_salida;
    }

    public String getInv_cantidad() {
        return inv_cantidad;
    }

    public void setInv_cantidad(String inv_cantidad) {
        this.inv_cantidad = inv_cantidad;
    }

    public Inventario(String prod_id, String prod_descri, Double inv_tot_soles, String inv_entrada, String inv_salida, String inv_cantidad) {

        this.prod_id = prod_id;
        this.prod_descri = prod_descri;
        this.inv_tot_soles = inv_tot_soles;
        this.inv_entrada = inv_entrada;
        this.inv_salida = inv_salida;
        this.inv_cantidad = inv_cantidad;
    }

    public Inventario() {

    }
    public String Componer(String caracter)
    {
        return Metodos.CadenasComponer(caracter,new Object[]{prod_id,prod_descri,inv_tot_soles,inv_entrada,inv_salida,inv_cantidad});
    }

    public Inventario Inventario(String cadena, String caracter)
    {
        return new Inventario(  Metodos.CadenasDescomponer(cadena,1,caracter),
                Metodos.CadenasDescomponer(cadena,2,caracter),
                Double.parseDouble(Metodos.CadenasDescomponer(cadena,3,caracter)),
                Metodos.CadenasDescomponer(cadena,4,caracter),
                Metodos.CadenasDescomponer(cadena,5,caracter),
                Metodos.CadenasDescomponer(cadena,6,caracter));
    }
}
