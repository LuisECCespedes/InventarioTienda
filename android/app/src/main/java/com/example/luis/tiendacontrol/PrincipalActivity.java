package com.example.luis.tiendacontrol;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.luis.tiendacontrol.data.modelo.Cliente;
import com.example.luis.tiendacontrol.data.modelo.Kardex;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.data.modelo.TipoMovimiento;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;
import com.example.luis.tiendacontrol.data.util.Mensaje;
import com.example.luis.tiendacontrol.esquemaSqlLite.ConexionSqliteHelper;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Delete;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Insert;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Update;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.ClienteTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.KardexTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.MarcaTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.ProductoTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.TipoMovimientoTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.TipoTabla;

import java.util.List;

public class PrincipalActivity extends AppCompatActivity {
    Button btnIns,btnUpd,btnSel,btnDel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // creamos la base de datos y la tabla
        ConexionSqliteHelper conn = new ConexionSqliteHelper(PrincipalActivity.this);

        if (SessionPreferences.get(getApplicationContext()).GetMarca().equals("1"))
        {
            Insert.RegistrarRegistro(getApplicationContext(),new Marca("1","Todos"), MarcaTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Marca("2","esika"), MarcaTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Marca("3","unique"), MarcaTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Marca("4","oriflame"), MarcaTabla.TABLA);

            Insert.RegistrarRegistro(getApplicationContext(),new Tipo("1","Todos"), TipoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Tipo("2","talco"), TipoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Tipo("3","perfume"), TipoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Tipo("4","desodorante"), TipoTabla.TABLA);

            Insert.RegistrarRegistro(getApplicationContext(),new Producto("1","talco esika",12.5,"2","2",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("2","talco unique",15.5,"3","2",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("3","talco oriflame",20.0,"4","2",""), ProductoTabla.TABLA);

            Insert.RegistrarRegistro(getApplicationContext(),new Producto("4","perfume esika",25.0,"2","3",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("5","perfume unique",50.0,"3","3",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("6","perfume oriflame",17.0,"4","3",""), ProductoTabla.TABLA);

            Insert.RegistrarRegistro(getApplicationContext(),new Producto("7","desodorante esika",11.0,"2","4",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("8","desodorante unique",17.0,"3","4",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("9","desodorante oriflame",14.0,"4","4",""), ProductoTabla.TABLA);
/*
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("10","talco esika1",12.5,"2","2",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("11","talco unique2",15.5,"3","2",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("12","talco oriflame3",20.0,"4","2",""), ProductoTabla.TABLA);

            Insert.RegistrarRegistro(getApplicationContext(),new Producto("13","perfume esika4",25.0,"2","3",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("14","perfume unique5",50.0,"3","3",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("15","perfume oriflame6",17.0,"4","3",""), ProductoTabla.TABLA);

            Insert.RegistrarRegistro(getApplicationContext(),new Producto("16","desodorante esika7",11.0,"2","4",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("17","desodorante unique8",17.0,"3","4",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("18","desodorante oriflame9",14.0,"4","4",""), ProductoTabla.TABLA);

            Insert.RegistrarRegistro(getApplicationContext(),new Producto("19","talco esika11",12.5,"2","2",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("20","talco unique21",15.5,"3","2",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("21","talco oriflame31",20.0,"4","2",""), ProductoTabla.TABLA);

            Insert.RegistrarRegistro(getApplicationContext(),new Producto("22","perfume esika41",25.0,"2","3",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("23","perfume unique51",50.0,"3","3",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("24","perfume oriflame61",17.0,"4","3",""), ProductoTabla.TABLA);

            Insert.RegistrarRegistro(getApplicationContext(),new Producto("25","desodorante esika71",11.0,"2","4",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("26","desodorante unique81",17.0,"3","4",""), ProductoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Producto("27","desodorante oriflame91",14.0,"4","4",""), ProductoTabla.TABLA);

*/
            Insert.RegistrarRegistro(getApplicationContext(),new TipoMovimiento("1","Compra","I"), TipoMovimientoTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new TipoMovimiento("2","Venta","S"), TipoMovimientoTabla.TABLA);

            Insert.RegistrarRegistro(getApplicationContext(),new Cliente("1","Cliente","0","","","C"), ClienteTabla.TABLA);
            Insert.RegistrarRegistro(getApplicationContext(),new Cliente("2","Proveedor","0","","","P"), ClienteTabla.TABLA);

        }
        // segun el caso session o login
        startActivity(new Intent(getApplicationContext(), SessionPreferences.get(PrincipalActivity.this).GetSession() ?
                MenuActivity.class : LoginActivity.class));
        finish();

    }
}
