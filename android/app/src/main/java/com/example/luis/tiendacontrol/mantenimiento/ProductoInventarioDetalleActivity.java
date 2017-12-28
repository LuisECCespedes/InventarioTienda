package com.example.luis.tiendacontrol.mantenimiento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.data.modelo.Cliente;
import com.example.luis.tiendacontrol.data.modelo.Inventario;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.data.modelo.TipoMovimiento;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.ClienteTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.ProductoTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.TipoMovimientoTabla;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductoInventarioDetalleActivity extends AppCompatActivity {
    @BindView(R.id.btActProInvDetCancelar)
    Button btCancelar;

    @BindView(R.id.btActProInvDetAgregar)
    Button btAgregar;

    @BindView(R.id.etActProInvDetNombre)
    TextView etProducto;
    @BindView(R.id.etActProInvDetStock)
    TextView etStock;
    @BindView(R.id.etActProInvDetValor)
    TextView etValor;

    private Inventario objInventario;
    private Tipo objTipo;
    private Marca objMarca;
    private Producto objProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_inventario_detalle);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("objInventario"))
        {
            objInventario = (Inventario) getIntent().getSerializableExtra("objInventario");
            //buscamos los datos del producto
            objProducto = (Producto)Select.BuscaRegistro(getApplicationContext(),objInventario.getProd_id(), ProductoTabla.TABLA);
        }

        if (getIntent().hasExtra("objMarca"))
        {
            objMarca = (Marca) getIntent().getSerializableExtra("objMarca");
        }

        if (getIntent().hasExtra("objTipo"))
        {
            objTipo = (Tipo) getIntent().getSerializableExtra("objTipo");
        }

        etProducto.setText(objInventario.getProd_descri());
        etStock.setText(objInventario.getInv_cantidad());
        etValor.setText(objInventario.getInv_tot_soles()+"");

        btAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent envio = new Intent(getApplicationContext(),ProductoInventarioRegistroActivity.class);
                envio.putExtra("objInventario",objInventario);
                envio.putExtra("objMarca",objMarca);
                envio.putExtra("objTipo",objTipo);
                envio.putExtra("objProducto",objProducto);
                envio.putExtra("objCliente",(Cliente)Select.BuscaRegistro(getApplicationContext(),"1", ClienteTabla.TABLA));
                envio.putExtra("objTipoMovimiento",(TipoMovimiento)Select.BuscaRegistro(getApplicationContext(),"2", TipoMovimientoTabla.TABLA));
                startActivity(envio);
                finish();
            }
        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salir();
            }
        });
    }

    @Override
    public void onBackPressed() {
        salir();
    }
    void salir()
    {   Intent retorno = new Intent(getApplicationContext(),ProductoInventarioActivity.class);
        retorno.putExtra("objMarca",objMarca);
        retorno.putExtra("objTipo",objTipo);
        startActivity(retorno);
        finish();
    }
}
