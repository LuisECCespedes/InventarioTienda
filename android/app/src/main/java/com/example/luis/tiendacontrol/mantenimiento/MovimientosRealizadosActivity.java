package com.example.luis.tiendacontrol.mantenimiento;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.adaptador.InventarioGridAdapter;
import com.example.luis.tiendacontrol.adaptador.KardexGridAdapter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovimientosRealizadosActivity extends AppCompatActivity {

    @BindView(R.id.gvActMovReaLista)
    ListView gvListaMovimientosRealizados;

    @BindView(R.id.btActMovReaFecDesde)
    Button btFechaDesde;
    @BindView(R.id.btActMovReaFecHasta)
    Button btFechaHasta;
    @BindView(R.id.tvActMovReaProducto)
    TextView tvProducto;

    private KardexGridAdapter adaptador;
    private List<Kardex> listKardex = new ArrayList<>();
    private Kardex objKardex;
    private Inventario objInventario;
    private Tipo objTipo;
    private Marca objMarca;
    int anioDesde,mesDesde,diaDesde,anioHasta,mesHasta,diaHasta;
    String fechaDesde = "", fechaHasta = "";
    static final int DILOG_DESDE_ID = 0,DILOG_HASTA_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos_realizados);
        this.setTitle("Movimientos Realizados");
        ButterKnife.bind(this);

        final Calendar cal = Calendar.getInstance();
        anioHasta = anioDesde = cal.get(Calendar.YEAR);
        mesHasta = mesDesde= cal.get(Calendar.MONTH);
        diaHasta = cal.get(Calendar.DAY_OF_MONTH);
        diaDesde = 1;
        String fecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        fechaDesde = anioHasta + "-" + (mesDesde + 1) + "-1";
        fechaHasta= fecha.toString();

        if (getIntent().hasExtra("fechaDesde"))
        {
            fechaDesde = getIntent().getStringExtra("fechaDesde");
        }

        if (getIntent().hasExtra("fechaHasta"))
        {
            fechaHasta = getIntent().getStringExtra("fechaHasta");
        }

        btFechaDesde.setText(fechaDesde);
        btFechaHasta.setText(fechaHasta);

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

        btFechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muestraCalendario(DILOG_DESDE_ID).show();

            }
        });
        cargarLista();
        btFechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muestraCalendario(DILOG_HASTA_ID).show();
            }
        });
        tvProducto.setText(objInventario.getProd_descri());
        gvListaMovimientosRealizados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objKardex = listKardex.get(i);
                CambiarActividad(MovimientosRealizadosDetalleActivity.class);
            }
        });
    }

    private void cargarLista() {
        listKardex.clear();
        Select.ListaRegistrosKardex(getApplicationContext(),listKardex,objInventario.getProd_id(),fechaDesde,fechaHasta);
        adaptador = new KardexGridAdapter(getApplicationContext(), 0, listKardex);
        gvListaMovimientosRealizados.setAdapter(adaptador);
    }

    private Dialog muestraCalendario(int dilogId) {
        if (dilogId == DILOG_DESDE_ID)
        {
            return new DatePickerDialog(MovimientosRealizadosActivity.this,calendarioDesde,anioDesde,mesDesde,diaDesde);
        }else {
            return new DatePickerDialog(MovimientosRealizadosActivity.this,calendarioHasta,anioHasta,mesHasta,diaHasta);
        }
    }

    private DatePickerDialog.OnDateSetListener calendarioDesde = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker vista, int nanio, int nmes, int ndia) {
            anioDesde=nanio;
            mesDesde=nmes;
            //mesDesde=nmes;
            diaDesde=ndia;
            fechaDesde = nanio + "-" + (nmes + 1) + "-" + ndia;
            btFechaDesde.setText(fechaDesde);
            cargarLista();
        }
    };

    private DatePickerDialog.OnDateSetListener calendarioHasta = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker vista, int nanio, int nmes, int ndia) {
            anioHasta=nanio;
            mesHasta=nmes;
            //mesHasta=nmes;
            diaHasta=ndia;
            fechaHasta = nanio + "-" + (nmes + 1) + "-" + ndia;
            btFechaHasta.setText(fechaHasta);
            cargarLista();
        }
    };

    @Override
    public void onBackPressed() {
        CambiarActividad(MovimientosActivity.class);
    }

    private void CambiarActividad(Class<?> actividad) {
        Intent cambiarAct = new Intent(getApplicationContext(),actividad.equals(MovimientosRealizadosDetalleActivity.class) ? MovimientosRealizadosDetalleActivity.class : MovimientosActivity.class);
        cambiarAct.putExtra("objMarca",objMarca);
        cambiarAct.putExtra("objTipo",objTipo);
        cambiarAct.putExtra("objInventario",objInventario);
        cambiarAct.putExtra("objKardex",objKardex);
        cambiarAct.putExtra("fechaDesde",fechaDesde);
        cambiarAct.putExtra("fechaHasta",fechaHasta);
/*
        if (actividad.equals(MovimientosRealizadosDetalleActivity.class)) {
            Cliente objCliente = (Cliente) Select.BuscaRegistro(getApplicationContext(),objKardex.getClie_nombre(), ClienteTabla.TABLA);
            cambiarAct.putExtra("objCliente",objCliente);
            Producto objProducto = (Producto) Select.BuscaRegistro(getApplicationContext(),objKardex.getProd_id(), KardexTabla.TABLA);
            cambiarAct.putExtra("objProducto",objProducto);
        }*/
        startActivity(cambiarAct);
    }
}
