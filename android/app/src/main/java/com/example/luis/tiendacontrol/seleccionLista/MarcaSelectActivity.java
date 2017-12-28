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
import com.example.luis.tiendacontrol.adaptador.MarcaGridAdapter;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.mantenimiento.MarcaDetalleActivity;
import com.example.luis.tiendacontrol.mantenimiento.MovimientosActivity;
import com.example.luis.tiendacontrol.mantenimiento.ProductoActivity;
import com.example.luis.tiendacontrol.mantenimiento.ProductoDetalleActivity;
import com.example.luis.tiendacontrol.mantenimiento.ProductoInventarioActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarcaSelectActivity extends AppCompatActivity {
    @BindView(R.id.gvActMarcaSelect)
    ListView gvMarca;

    private MarcaGridAdapter adaptador;
    private List<Marca> listMarca= new ArrayList<>();
    private Producto objProducto;
    private Marca objMarca,objMarcaDetalle;
    private Tipo objTipo,objTipoDetalle;
    private Boolean bProducto = false;
    private String actividad = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marca_select);
        ButterKnife.bind(this);
        this.setTitle("Selecciona Marca");
        cargarLista();

        if (getIntent().hasExtra("objMarca"))
        {
            actividad = getIntent().getStringExtra("actividad");
        }

        if (getIntent().hasExtra("objMarca"))
        {
            objMarca = (Marca) getIntent().getSerializableExtra("objMarca");
            objMarcaDetalle = (Marca) getIntent().getSerializableExtra("objMarca");
        }

        if (getIntent().hasExtra("objTipo"))
        {
            objTipo = (Tipo) getIntent().getSerializableExtra("objTipo");
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

        gvMarca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objMarcaDetalle = listMarca.get(i);
                enviarActivity();
            }
        });
    }

    private void cargarLista() {
        Select.SelectMarca(getApplicationContext(),listMarca);
        adaptador = new MarcaGridAdapter(getApplicationContext(), 0, listMarca);
        gvMarca.setAdapter(adaptador);

    }

    public void clickButtonMarca(View view)    {
        // Nuevo Cliente
        Intent intento = new Intent(getApplicationContext(), MarcaDetalleActivity.class);
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
                actividad.equals("Movimientos") ? MovimientosActivity.class:
                actividad.equals("Producto") ? ProductoActivity.class:
                actividad.equals("ProductoInventario") ? ProductoInventarioActivity.class : MenuActivity.class);

        intento.putExtra("objTipo", objTipo);
        intento.putExtra("objMarca", bProducto ? objMarca : objMarcaDetalle);
        intento.putExtra("objTipoDetalle", objTipoDetalle);
        intento.putExtra("objMarcaDetalle", objMarcaDetalle);
        if (bProducto) intento.putExtra("objProducto", objProducto);
        //llamamos a la actividad
        intento.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intento);
    }
}
