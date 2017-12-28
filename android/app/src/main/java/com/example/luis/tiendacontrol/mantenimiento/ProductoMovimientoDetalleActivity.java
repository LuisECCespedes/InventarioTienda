package com.example.luis.tiendacontrol.mantenimiento;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.data.modelo.Cliente;
import com.example.luis.tiendacontrol.data.modelo.Inventario;
import com.example.luis.tiendacontrol.data.modelo.Kardex;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.MarcaTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.TipoTabla;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductoMovimientoDetalleActivity extends AppCompatActivity {
    @BindView(R.id.ivActProdMovDetFoto)
    ImageView imagen;
    @BindView(R.id.tvActProdMovDetMarca)
    TextView tvmarca;
    @BindView(R.id.tvActProdMovDetNombre)
    TextView tvnombre;
    @BindView(R.id.tvActProdMovDetPrecio)
    TextView tvprecio;
    @BindView(R.id.tvActProdMovDetTipo)
    TextView tvtipo;

    private Inventario objInventario;
    private Tipo objTipo;
    private Marca objMarca;
    private Kardex objKardex;

    private Cliente objCliente;
    private Producto objProducto;
    private String fechaDesde, fechaHasta, pathUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_movimiento_detalle);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("fechaDesde"))
        {
            fechaDesde = getIntent().getStringExtra("fechaDesde");
        }
        if (getIntent().hasExtra("fechaHasta"))
        {
            fechaHasta = getIntent().getStringExtra("fechaHasta");
        }

        if (getIntent().hasExtra("objInventario"))
        {
            objInventario = (Inventario) getIntent().getSerializableExtra("objInventario");
        }

        if (getIntent().hasExtra("objMarca"))
        {
            objMarca = (Marca) getIntent().getSerializableExtra("objMarca");
        }

        if (getIntent().hasExtra("objTipo"))
        {
            objTipo = (Tipo) getIntent().getSerializableExtra("objTipo");
        }

        if (getIntent().hasExtra("objKardex"))
        {
            objKardex = (Kardex) getIntent().getSerializableExtra("objKardex");
        }

        if (getIntent().hasExtra("objCliente"))
        {
            objCliente = (Cliente) getIntent().getSerializableExtra("objCliente");
        }

        if (getIntent().hasExtra("objProducto"))
        {
            objProducto = (Producto) getIntent().getSerializableExtra("objProducto");
        }
        Marca itemMarca = (Marca)Select.BuscaRegistro(getApplicationContext(),objProducto.getProd_id(), MarcaTabla.TABLA);
        tvmarca.setText(itemMarca.getMar_descri());
        Tipo itemTipo = (Tipo)Select.BuscaRegistro(getApplicationContext(),objProducto.getProd_id(), TipoTabla.TABLA);
        tvtipo.setText(itemTipo.getTip_descri());
        tvnombre.setText(objProducto.getProd_descri());
        tvprecio.setText(objProducto.getProd_precio()+"");
        pathUri = objProducto.getRutaFoto();

        if (pathUri.length() > 0) {
            imagen.setImageURI(Uri.parse(objProducto.getRutaFoto()));
        }
    }

    @Override
    public void onBackPressed() {
        Intent actividad = new Intent(getApplicationContext(),MovimientosRealizadosDetalleActivity.class);
        actividad.putExtra("objMarca",objMarca);
        actividad.putExtra("objTipo",objTipo);
        actividad.putExtra("objKardex",objKardex);
        actividad.putExtra("objCliente",objCliente);
        actividad.putExtra("objProducto",objProducto);
        actividad.putExtra("objInventario",objInventario);
        actividad.putExtra("fechaDesde",fechaDesde);
        actividad.putExtra("fechaHasta",fechaHasta);
        startActivity(actividad);
    }
}
