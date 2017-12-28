package com.example.luis.tiendacontrol.mantenimiento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luis.tiendacontrol.MenuActivity;
import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.adaptador.InventarioGridAdapter;
import com.example.luis.tiendacontrol.data.modelo.Inventario;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.seleccionLista.MarcaSelectActivity;
import com.example.luis.tiendacontrol.seleccionLista.TipoSelectActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductoInventarioActivity extends AppCompatActivity {
    @BindView(R.id.gvActProdMovProductoInventario)
    ListView gvProducto;

    @BindView(R.id.tvActProdMovMarca)
    TextView tvMarca;
    @BindView(R.id.tvActProdMovTipo)
    TextView tvTipo;

    private InventarioGridAdapter adaptador;
    private List<Inventario> listInventario = new ArrayList<>();
    private Inventario objInventario;
    private Tipo objTipo;
    private Marca objMarca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_inventario);
        this.setTitle("Inventario");
        ButterKnife.bind(this);

        if (getIntent().hasExtra("objMarca"))
        {
            objMarca = (Marca) getIntent().getSerializableExtra("objMarca");
        }

        if (getIntent().hasExtra("objTipo"))
        {
            objTipo = (Tipo) getIntent().getSerializableExtra("objTipo");
        }

        tvMarca.setText("Marca :"+objMarca.getMar_descri());
        tvTipo.setText("Tipo :"+objTipo.getTip_descri());
        cargarProductos();

        gvProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objInventario = listInventario.get(i);
                Intent intentoInv = new Intent(getApplicationContext(),ProductoInventarioDetalleActivity.class);
                intentoInv.putExtra("objMarca",objMarca);
                intentoInv.putExtra("objTipo",objTipo);
                intentoInv.putExtra("objInventario",objInventario);
                startActivity(intentoInv);
                finish();
            }
        });
    }

    private void cargarProductos() {
        listInventario = Select.ListaKardexProducto(getApplicationContext(),listInventario,objMarca.getMar_id(),objTipo.getTip_id());
        adaptador = new InventarioGridAdapter(getApplicationContext(), 0, listInventario);
        gvProducto.setAdapter(adaptador);
    }
/*
    public void clickButtonProducto(View view)
    {
        Intent intProducto = new Intent(getApplicationContext(),ProductoDetalleActivity.class);
        intProducto.putExtra("objMarca",objMarca);
        intProducto.putExtra("objTipo",objTipo);
        intProducto.putExtra("objProducto",new Producto("0","",0.0,objMarca.getMar_id(),objTipo.getTip_id(),""));
        startActivity(intProducto);
        finish();
    }*/

    //@OnClick(R.id.btActProInvMarca)
    public void clickProductoInventarioMarca(View view)
    {
        Intent intMarca = new Intent(getApplicationContext(),MarcaSelectActivity.class);
        intMarca.putExtra("objMarca",objMarca);
        intMarca.putExtra("objTipo",objTipo);
        intMarca.putExtra("actividad","ProductoInventario");
        startActivity(intMarca);
        finish();
    }

    //@OnClick(R.id.btActProInvTipo)
    public void clickProductoInventarioTipo(View view)
    {
        Intent intTipo = new Intent(getApplicationContext(),TipoSelectActivity.class);
        intTipo.putExtra("objTipo",objTipo);
        intTipo.putExtra("objMarca",objMarca);
        intTipo.putExtra("actividad","ProductoInventario");
        startActivity(intTipo);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intTipo = new Intent(getApplicationContext(),MenuActivity.class);
        startActivity(intTipo);
        finish();
    }
}
