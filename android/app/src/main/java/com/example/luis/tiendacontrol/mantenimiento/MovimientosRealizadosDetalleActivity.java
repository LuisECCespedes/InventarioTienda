package com.example.luis.tiendacontrol.mantenimiento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.data.modelo.Cliente;
import com.example.luis.tiendacontrol.data.modelo.Inventario;
import com.example.luis.tiendacontrol.data.modelo.Kardex;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.data.util.Mensaje;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.ClienteTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.KardexTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.ProductoTabla;
import com.example.luis.tiendacontrol.seleccionLista.ClienteSelectActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovimientosRealizadosDetalleActivity extends AppCompatActivity {
//    android:id="@+id/tvActMovReaDet"
    @BindView(R.id.tvActMovReaDetCant)
    TextView tvCantidad;
    @BindView(R.id.tvActMovReaDetClieProv)
    TextView tvClieProve;
    @BindView(R.id.tvActMovReaDetFecha)
    TextView tvFecha;
    @BindView(R.id.tvActMovReaDetHora)
    TextView tvHora;
    @BindView(R.id.tvActMovReaDetMovimiento)
    TextView tvMovimiento;
    @BindView(R.id.tvActMovReaDetObservacion)
    TextView tvObservacion;
    @BindView(R.id.tvActMovReaDetProducto)
    TextView tvProducto;
    @BindView(R.id.tvActMovReaDetPrecio)
    TextView tvPrecio;

    @BindView(R.id.btActMovReaDetCancelar)
    TextView btCancelar;

    private Inventario objInventario;
    private Tipo objTipo;
    private Marca objMarca;
    private Kardex objKardex;

    private Cliente objCliente;
    private Producto objProducto;
    private String fechaDesde ,fechaHasta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos_realizados_detalle);
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

        objCliente = (Cliente) Select.BuscaRegistro(getApplicationContext(),objKardex.getClie_nombre(), ClienteTabla.TABLA);
        objProducto = (Producto) Select.BuscaRegistro(getApplicationContext(),objKardex.getProd_id(), ProductoTabla.TABLA);
        tvCantidad.setText(objKardex.getProd_cant()+"");
        tvFecha.setText(objKardex.getKar_fe());
        tvHora.setText(objKardex.getKar_ho());
        tvMovimiento.setText(objKardex.getTip_mov_descri());
        tvObservacion.setText(objKardex.getKar_obs());
        tvPrecio.setText(objKardex.getProd_precio()+"");
        tvClieProve.setText(objCliente.getClie_nombre());
        tvProducto.setText(objProducto.getProd_descri());

    }

    public void ClickMovimientosRealizadosDetalleCancelar(View view)
    {
        CambiarActividad(MovimientosRealizadosActivity.class);
    }

    public void ClickMovimientosRealizadosDetalleCliente(View view)
    {
        CambiarActividad(ClienteMovimientoDetalleActivity.class);
    }

    public void ClickMovimientosRealizadosDetalleProducto(View view)
    {
        CambiarActividad(ProductoMovimientoDetalleActivity.class);
    }

    private void CambiarActividad(Class<?> act) {
        Intent actividad = new Intent(getApplicationContext(),act);
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

    @Override
    public void onBackPressed() {
        CambiarActividad(MovimientosRealizadosActivity.class);
    }
}
