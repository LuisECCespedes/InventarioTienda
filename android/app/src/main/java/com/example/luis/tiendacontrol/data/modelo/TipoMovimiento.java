package com.example.luis.tiendacontrol.data.modelo;

import com.example.luis.tiendacontrol.data.util.Metodos;

import java.io.Serializable;

/**
 * Created by luis on 13/12/2017.
 */

public class TipoMovimiento implements Serializable {
    private String tip_mov_id;
    private String tip_mov_descri;
    private String tip_mov_ing_sal;

    public String getTip_mov_id() {
        return tip_mov_id;
    }

    public void setTip_mov_id(String tip_mov_id) {
        this.tip_mov_id = tip_mov_id;
    }

    public String getTip_mov_descri() {
        return tip_mov_descri;
    }

    public void setTip_mov_descri(String tip_mov_descri) {
        this.tip_mov_descri = tip_mov_descri;
    }

    public String getTip_mov_ing_sal() {
        return tip_mov_ing_sal;
    }

    public void setTip_mov_ing_sal(String tip_mov_ing_sal) {
        this.tip_mov_ing_sal = tip_mov_ing_sal;
    }

    public TipoMovimiento(String tip_mov_id, String tip_mov_descri, String tip_mov_ing_sal) {

        this.tip_mov_id = tip_mov_id;
        this.tip_mov_descri = tip_mov_descri;
        this.tip_mov_ing_sal = tip_mov_ing_sal;
    }

    public TipoMovimiento() {

    }

    public String Componer(String caracter)
    {
        return Metodos.CadenasComponer(caracter,new String[]{tip_mov_id,tip_mov_descri,tip_mov_ing_sal});
    }

    public TipoMovimiento TipoMovimiento(String cadena, String caracter)
    {
        return new TipoMovimiento(  Metodos.CadenasDescomponer(cadena,1,caracter),
                                    Metodos.CadenasDescomponer(cadena,2,caracter),
                                    Metodos.CadenasDescomponer(cadena,3,caracter));
    }
}
