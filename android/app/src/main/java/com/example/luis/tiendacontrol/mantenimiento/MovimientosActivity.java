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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luis.tiendacontrol.MenuActivity;
import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.adaptador.InventarioGridAdapter;
import com.example.luis.tiendacontrol.data.modelo.Inventario;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.data.util.Mensaje;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.seleccionLista.MarcaSelectActivity;
import com.example.luis.tiendacontrol.seleccionLista.TipoSelectActivity;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovimientosActivity extends AppCompatActivity {

    @BindView(R.id.tvActMovMarca)
    TextView tvMarca;
    @BindView(R.id.tvActMovTipo)
    TextView tvTipo;

    @BindView(R.id.gvActMovLista)
    ListView gvListaMovimientos;

    private InventarioGridAdapter adaptador;
    private List<Inventario> listInventario = new ArrayList<>();
    private Inventario objInventario;
    private Tipo objTipo;
    private Marca objMarca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);
        this.setTitle("Movimientos Realizados");
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

    gvListaMovimientos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            objInventario = listInventario.get(i);
            Intent intentoInv = new Intent(getApplicationContext(),MovimientosRealizadosActivity.class);
            intentoInv.putExtra("objMarca",objMarca);
            intentoInv.putExtra("objTipo",objTipo);
            intentoInv.putExtra("objInventario",objInventario);
            startActivity(intentoInv);
            finish();
        }
    });

    }
    private void cargarProductos() {
        listInventario.clear();
        listInventario = Select.ListaKardexProducto(getApplicationContext(),listInventario,objMarca.getMar_id(),objTipo.getTip_id());
        adaptador = new InventarioGridAdapter(getApplicationContext(), 0, listInventario);
        gvListaMovimientos.setAdapter(adaptador);
    }
    //@OnClick(R.id.btActProInvMarca)
    public void clickProductoMovimientoMarca(View view)
    {
        Intent intMarca = new Intent(getApplicationContext(),MarcaSelectActivity.class);
        intMarca.putExtra("objMarca",objMarca);
        intMarca.putExtra("objTipo",objTipo);
        intMarca.putExtra("actividad","Movimientos");
        startActivity(intMarca);
        finish();
    }

    //@OnClick(R.id.btActProInvTipo)
    public void clickProductoMovimientoTipo(View view)
    {
        Intent intTipo = new Intent(getApplicationContext(),TipoSelectActivity.class);
        intTipo.putExtra("objTipo",objTipo);
        intTipo.putExtra("objMarca",objMarca);
        intTipo.putExtra("actividad","Movimientos");
        startActivity(intTipo);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent intSalir = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intSalir);
        finish();
    }
}
