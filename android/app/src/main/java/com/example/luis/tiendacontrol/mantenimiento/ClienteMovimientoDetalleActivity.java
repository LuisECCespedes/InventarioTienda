package com.example.luis.tiendacontrol.mantenimiento;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.data.modelo.Cliente;
import com.example.luis.tiendacontrol.data.modelo.Inventario;
import com.example.luis.tiendacontrol.data.modelo.Kardex;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.data.util.Mensaje;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClienteMovimientoDetalleActivity extends AppCompatActivity {
    @BindView(R.id.ivActCliMovDetFoto)
    ImageView imagen;
    @BindView(R.id.tvActCliMovDetNombre)
    TextView nombre;
    @BindView(R.id.tvActCliMovDetNumCel)
    TextView numCel;
    @BindView(R.id.tvActCliMovDetReferencia)
    TextView referencia;

    @BindView(R.id.rbActCliMovDetCliente)
    RadioButton rbCliente;
    @BindView(R.id.rbActCliMovDetProveedor)
    RadioButton rbProveedor;

    private Inventario objInventario;
    private Tipo objTipo;
    private Marca objMarca;
    private Kardex objKardex;

    private Cliente objCliente;
    private Producto objProducto;
    private String fechaDesde ,fechaHasta;
    private String pathUri = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_movimiento_detalle);
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
        referencia.setText(objCliente.getClie_referencia());
        nombre.setText(objCliente.getClie_nombre());
        numCel.setText(objCliente.getClie_num_tel());
        pathUri = objCliente.getClie_rut_foto();
        rbCliente.setChecked(objCliente.getClie_tipo().equals("C"));
        rbProveedor.setChecked(!objCliente.getClie_tipo().equals("C"));

        if (pathUri.length() > 0) {
            imagen.setImageURI(Uri.parse(objCliente.getClie_rut_foto()));
        }
    }
    public void ClienteMovimientoDetalleLLamar(View view)
    {
        Mensaje.mensajeToas(getApplicationContext(),"llamar" + objCliente.getClie_num_tel());
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
