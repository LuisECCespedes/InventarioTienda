package com.example.luis.tiendacontrol.data.modelo;

import com.example.luis.tiendacontrol.data.util.Metodos;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by luis on 12/12/2017.
 */

public class Producto implements Serializable {
    private String prod_id ;
    private String prod_descri ;
    private Double prod_precio ;
    private String mar_id;
    private String tip_id;
    private String rutaFoto;

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

    public double getProd_precio() {
        return prod_precio;
    }

    public void setProd_precio(Double prod_precio) {
        this.prod_precio = prod_precio;
    }

    public String getMar_id() {
        return mar_id;
    }

    public void setMar_id(String mar_id) {
        this.mar_id = mar_id;
    }

    public String getTip_id() {
        return tip_id;
    }

    public void setTip_id(String tip_id) {
        this.tip_id = tip_id;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public Producto(String prod_id, String prod_descri, Double prod_precio, String mar_id, String tip_id, String rutaFoto) {

        this.prod_id = prod_id;
        this.prod_descri = prod_descri;
        this.prod_precio = prod_precio;
        this.mar_id = mar_id;
        this.tip_id = tip_id;
        this.rutaFoto = rutaFoto;
    }

    public Producto() {

    }
    public String Componer(String caracter)
    {
        return Metodos.CadenasComponer(caracter,new Object[]{prod_id,prod_descri,prod_precio,mar_id,tip_id,rutaFoto});
    }

    public Producto Producto(String cadena, String caracter)
    {
        return new Producto(  Metodos.CadenasDescomponer(cadena,1,caracter),
                Metodos.CadenasDescomponer(cadena,2,caracter),
                Double.parseDouble(Metodos.CadenasDescomponer(cadena,3,caracter)),
                Metodos.CadenasDescomponer(cadena,4,caracter),
                Metodos.CadenasDescomponer(cadena,5,caracter),
                Metodos.CadenasDescomponer(cadena,6,caracter));
    }
}
