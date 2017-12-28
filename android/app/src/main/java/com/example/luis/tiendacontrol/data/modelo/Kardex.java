package com.example.luis.tiendacontrol.data.modelo;

import com.example.luis.tiendacontrol.data.util.Metodos;

import java.io.Serializable;
import java.sql.Time;
import java.util.UUID;

/**
 * Created by luis on 13/12/2017.
 */

public class Kardex implements Serializable {
    private String kar_id;
    private String clie_nombre;
    private String prod_id;
    private Double prod_precio;
    private int prod_cant;
    private String kar_obs;
    private String kar_fe;
    private String kar_ho;
    private String tip_mov_ing_sal;
    private String tip_mov_descri ;

    public String getKar_id() {
        return kar_id;
    }

    public void setKar_id(String kar_id) {
        this.kar_id = kar_id;
    }

    public String getClie_nombre() {
        return clie_nombre;
    }

    public void setClie_nombre(String clie_nombre) {
        this.clie_nombre = clie_nombre;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public Double getProd_precio() {
        return prod_precio;
    }

    public void setProd_precio(Double prod_precio) {
        this.prod_precio = prod_precio;
    }

    public int getProd_cant() {
        return prod_cant;
    }

    public void setProd_cant(int prod_cant) {
        this.prod_cant = prod_cant;
    }

    public String getKar_obs() {
        return kar_obs;
    }

    public void setKar_obs(String kar_obs) {
        this.kar_obs = kar_obs;
    }

    public String getKar_fe() {
        return kar_fe;
    }

    public void setKar_fe(String kar_fe) {
        this.kar_fe = kar_fe;
    }

    public String getKar_ho() {
        return kar_ho;
    }

    public void setKar_ho(String kar_ho) {
        this.kar_ho = kar_ho;
    }

    public String getTip_mov_ing_sal() {
        return tip_mov_ing_sal;
    }

    public void setTip_mov_ing_sal(String tip_mov_ing_sal) {
        this.tip_mov_ing_sal = tip_mov_ing_sal;
    }

    public String getTip_mov_descri() {
        return tip_mov_descri;
    }

    public void setTip_mov_descri(String tip_mov_descri) {
        this.tip_mov_descri = tip_mov_descri;
    }

    public Kardex(String kar_id, String clie_nombre, String prod_id, Double prod_precio, int prod_cant, String kar_obs, String kar_fe, String kar_ho, String tip_mov_ing_sal, String tip_mov_descri) {

        this.kar_id = kar_id;
        this.clie_nombre = clie_nombre;
        this.prod_id = prod_id;
        this.prod_precio = prod_precio;
        this.prod_cant = prod_cant;
        this.kar_obs = kar_obs;
        this.kar_fe = kar_fe;
        this.kar_ho = kar_ho;
        this.tip_mov_ing_sal = tip_mov_ing_sal;
        this.tip_mov_descri = tip_mov_descri;
    }

    public Kardex() {

    }
    public String Componer(String caracter)
    {
        return Metodos.CadenasComponer(caracter,new Object[]{kar_id,clie_nombre,prod_id,prod_precio,prod_cant,kar_obs,kar_fe,kar_ho,tip_mov_ing_sal,tip_mov_descri});
    }

    public Kardex Kardex(String cadena, String caracter)
    {
        return new Kardex(  Metodos.CadenasDescomponer(cadena,1,caracter),
                Metodos.CadenasDescomponer(cadena,2,caracter),
                Metodos.CadenasDescomponer(cadena,3,caracter),
                Double.parseDouble(Metodos.CadenasDescomponer(cadena,4,caracter)),
                Integer.parseInt(Metodos.CadenasDescomponer(cadena,5,caracter)),
                Metodos.CadenasDescomponer(cadena,6,caracter),
                Metodos.CadenasDescomponer(cadena,7,caracter),
                Metodos.CadenasDescomponer(cadena,8,caracter),
                Metodos.CadenasDescomponer(cadena,9,caracter),
                Metodos.CadenasDescomponer(cadena,10,caracter));
    }
}
