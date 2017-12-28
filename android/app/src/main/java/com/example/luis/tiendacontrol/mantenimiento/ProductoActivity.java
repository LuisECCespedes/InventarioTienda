package com.example.luis.tiendacontrol.mantenimiento;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import com.example.luis.tiendacontrol.adaptador.ProductoGridAdapter;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.seleccionLista.MarcaSelectActivity;
import com.example.luis.tiendacontrol.seleccionLista.TipoSelectActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductoActivity extends AppCompatActivity{
    @BindView(R.id.gvActProducto)
    ListView gvProducto;
    @BindView(R.id.tvActProdManMarca)
    TextView tvMarca;
    @BindView(R.id.tvActProdManTipo)
    TextView tvTipo;

    @BindView(R.id.fabActProdNuevo)
    FloatingActionButton fbProducto;

    private ProductoGridAdapter adaptador;
    private List<Producto> listProducto= new ArrayList<>();;
    private Producto objProducto;
    private Tipo objTipo;
    private Marca objMarca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        this.setTitle("Productos");
        ButterKnife.bind(this);

        if (getIntent().hasExtra("objMarca"))
        {
            objMarca = (Marca) getIntent().getSerializableExtra("objMarca");
        }

        if (getIntent().hasExtra("objTipo"))
        {
            objTipo = (Tipo) getIntent().getSerializableExtra("objTipo");
        }

        tvMarca.setText(objMarca.getMar_descri());
        tvTipo.setText(objTipo.getTip_descri());

        gvProducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objProducto = listProducto.get(i);
                Intent intProducto = new Intent(getApplicationContext(),ProductoDetalleActivity.class);
                intProducto.putExtra("objProducto",objProducto);
                intProducto.putExtra("objMarca",objMarca);
                intProducto.putExtra("objTipo",objTipo);
                intProducto.putExtra("bnuevo",false);
                intProducto.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                finish();
                startActivity(intProducto);
            }
        });
        cargarProductos();
    }

    private void cargarProductos() {
        listProducto = Select.ListaRegistrosProducto(getApplicationContext(),listProducto,objMarca.getMar_id(),objTipo.getTip_id());
        adaptador = new ProductoGridAdapter(getApplicationContext(), 0, listProducto);
        gvProducto.setAdapter(adaptador);
    }

    public void clickButtonProducto(View view)
    {
        Intent intProducto = new Intent(getApplicationContext(),ProductoDetalleActivity.class);
        intProducto.putExtra("objMarca",objMarca);
        intProducto.putExtra("objTipo",objTipo);
        intProducto.putExtra("objProducto",new Producto("0","",0.0,objMarca.getMar_id(),objTipo.getTip_id(),""));
        intProducto.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intProducto);
    }

    //@OnClick(R.id.btActProMarca)
    public void clickProductoMantenimientoMarca(View view)
    {
        Intent intMarca = new Intent(getApplicationContext(),MarcaSelectActivity.class);
        intMarca.putExtra("objMarca",objMarca);
        intMarca.putExtra("objTipo",objTipo);
        intMarca.putExtra("actividad","Producto");
        intMarca.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intMarca);
    }

    public void clickProductoMantenimientoTipo(View view)
    {
        Intent intTipo = new Intent(getApplicationContext(),TipoSelectActivity.class);
        intTipo.putExtra("objTipo",objTipo);
        intTipo.putExtra("objMarca",objMarca);
        intTipo.putExtra("actividad","Producto");
        startActivity(intTipo);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intTipo = new Intent(getApplicationContext(),MenuActivity.class);
        intTipo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intTipo);
    }
}
