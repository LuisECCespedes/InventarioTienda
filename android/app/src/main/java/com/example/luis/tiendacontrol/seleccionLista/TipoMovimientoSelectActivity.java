package com.example.luis.tiendacontrol.seleccionLista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.luis.tiendacontrol.MenuActivity;
import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.adaptador.TipoMovimientoGridAdapter;
import com.example.luis.tiendacontrol.data.modelo.Cliente;
import com.example.luis.tiendacontrol.data.modelo.Inventario;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.data.modelo.TipoMovimiento;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.mantenimiento.ProductoInventarioRegistroActivity;
import com.example.luis.tiendacontrol.mantenimiento.TipoMovimientoDetalleActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipoMovimientoSelectActivity extends AppCompatActivity {
    @BindView(R.id.gvActTipMovSelect)
    ListView gvActTipMovSelect;

    private TipoMovimientoGridAdapter adaptador;
    private List<TipoMovimiento> listTipoMovimiento = new ArrayList<>();

    private TipoMovimiento objTipoMovimiento;

    private Inventario objInventario;
    private Tipo objTipo;
    private Marca objMarca;
    private Cliente objCliente;
    private Producto objProducto;

    @BindView(R.id.rbActTipMovSelIngreso)
    RadioButton rbIngreso;
    @BindView(R.id.rbActTipMovSelsalida)
    RadioButton rbSalida;
    String vcant="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_movimiento_select);

        ButterKnife.bind(this);

        this.setTitle("Tipo Movimiento");

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

        if (getIntent().hasExtra("objCliente"))
        {
            objCliente = (Cliente) getIntent().getSerializableExtra("objCliente");
        }

        if (getIntent().hasExtra("objTipoMovimiento"))
        {
            objTipoMovimiento = (TipoMovimiento) getIntent().getSerializableExtra("objTipoMovimiento");
        }

        if (getIntent().hasExtra("objProducto"))
        {
            objProducto = (Producto) getIntent().getSerializableExtra("objProducto");
        }

        if (getIntent().hasExtra("cantidad"))
        {
            vcant = getIntent().getStringExtra("cantidad");
        }

        rbIngreso.setChecked(objTipoMovimiento.getTip_mov_ing_sal().equals("I"));
        rbSalida.setChecked(objTipoMovimiento.getTip_mov_ing_sal().equals("S"));
        cargarLista();

        gvActTipMovSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objTipoMovimiento = listTipoMovimiento.get(i);
                cambiarActvidad();
            }
        });
    }

    private void cargarLista() {
        listTipoMovimiento.clear();
        Select.SelectTipoMovimiento(getApplicationContext(), listTipoMovimiento,rbIngreso.isChecked() ? "I" : "S");
        adaptador = new TipoMovimientoGridAdapter(getApplicationContext(), 0, listTipoMovimiento);
        gvActTipMovSelect.setAdapter(adaptador);
    }

    @Override
    public void onBackPressed() {
        cambiarActvidad();
    }

    private void cambiarActvidad()
    {   // Salir
        Intent intento = new Intent(getApplicationContext(), ProductoInventarioRegistroActivity.class);
        intento.putExtra("objInventario",objInventario);
        intento.putExtra("objMarca",objMarca);
        intento.putExtra("objTipo",objTipo);
        intento.putExtra("objCliente",objCliente);
        intento.putExtra("objTipoMovimiento",objTipoMovimiento);
        intento.putExtra("objProducto",objProducto);
        intento.putExtra("cantidad",vcant);
        intento.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intento);
    }

    public void onRadioIngresoSalida(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbActTipMovSelIngreso:
                cargarLista();
                    // Listar los tipos Ingreso
                    break;

            case R.id.rbActTipMovSelsalida:
                cargarLista();
                    // Listar los tipos Salida
                    break;
        }
    }

}