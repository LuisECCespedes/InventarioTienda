package com.example.luis.tiendacontrol.seleccionLista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.luis.tiendacontrol.MenuActivity;
import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.adaptador.TipoGridAdapter;
import com.example.luis.tiendacontrol.data.modelo.Inventario;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.mantenimiento.MovimientosActivity;
import com.example.luis.tiendacontrol.mantenimiento.ProductoActivity;
import com.example.luis.tiendacontrol.mantenimiento.ProductoDetalleActivity;
import com.example.luis.tiendacontrol.mantenimiento.ProductoInventarioActivity;
import com.example.luis.tiendacontrol.mantenimiento.TipoDetalleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipoSelectActivity extends AppCompatActivity {
    @BindView(R.id.gvActTipoSelect)
    ListView gvTipo;
    private TipoGridAdapter adaptador;
    private List<Tipo> listTipo= new ArrayList<>();
    private Tipo objTipo,objTipoDetalle = new Tipo("1","Todos");
    private Marca objMarca,objMarcaDetalle = new Marca("1","Todos");;
    private Producto objProducto;
    private Boolean bProducto = false;
    private String actividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_select);

        ButterKnife.bind(this);
        this.setTitle("Selecciona Tipo");
        cargarLista();

        if (getIntent().hasExtra("objMarca"))
        {
            actividad = getIntent().getStringExtra("actividad");
        }

        if (getIntent().hasExtra("objMarca"))
        {
            objMarca = (Marca) getIntent().getSerializableExtra("objMarca");
        }

        if (getIntent().hasExtra("objTipo"))
        {
            objTipo = (Tipo) getIntent().getSerializableExtra("objTipo");
            objTipoDetalle = (Tipo) getIntent().getSerializableExtra("objTipo");
        }

        if (getIntent().hasExtra("objMarcaDetalle"))
        {
            objMarcaDetalle = (Marca) getIntent().getSerializableExtra("objMarcaDetalle");
        }

        if (getIntent().hasExtra("objTipoDetalle"))
        {
            objTipoDetalle = (Tipo) getIntent().getSerializableExtra("objTipoDetalle");
        }

        if (getIntent().hasExtra("objProducto"))
        {
            objProducto = (Producto) getIntent().getSerializableExtra("objProducto");
            bProducto = true;
        }
        gvTipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objTipoDetalle = listTipo.get(i);
                enviarActivity();
            }
        });
    }

    private void cargarLista() {
        Select.SelectTipo(getApplicationContext(),listTipo);
        adaptador = new TipoGridAdapter(getApplicationContext(), 0, listTipo);
        gvTipo.setAdapter(adaptador);
    }

    public void clickButtonTipo(View view)    {
        // Nuevo Cliente
        Intent intento = new Intent(getApplicationContext(), TipoDetalleActivity.class);
        //llamamos a la actividad
        intento.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intento);
    }

    @Override
    public void onBackPressed() {
        enviarActivity();
    }
    public void enviarActivity()
    {
        Intent intento = new Intent(getApplicationContext(),
                actividad.equals("ProductoDetalle") ? ProductoDetalleActivity.class :
                actividad.equals("Producto") ? ProductoActivity.class:
                actividad.equals("Movimientos") ? MovimientosActivity.class:
                actividad.equals("ProductoInventario") ? ProductoInventarioActivity.class : MenuActivity.class);

        intento.putExtra("objTipo", bProducto ? objTipo : objTipoDetalle);
        intento.putExtra("objMarca", objMarca);
        intento.putExtra("objTipoDetalle", objTipoDetalle);
        intento.putExtra("objMarcaDetalle", objMarcaDetalle);
        if (bProducto) intento.putExtra("objProducto", objProducto);
        //llamamos a la actividad
        intento.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intento);
    }
}